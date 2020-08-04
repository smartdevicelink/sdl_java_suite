/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.managers.audio;

import android.content.Context;
import android.media.AudioFormat;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;

import com.smartdevicelink.managers.audio.AudioStreamManager.SampleType;
import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("WeakerAccess")
public abstract class BaseAudioDecoder {
    private static final String TAG = AudioDecoder.class.getSimpleName();

    protected final int targetSampleRate;
    protected @SampleType final int targetSampleType;

    private int outputChannelCount;
    private int outputSampleRate;
    private @SampleType int outputSampleType;

    private double lastOutputSample = 0;

    private double lastOutputPresentationTimeUs = 0;
    private double lastTargetPresentationTimeUs = 0;

    protected MediaExtractor extractor;
    protected MediaCodec decoder;

    protected final Uri audioSource;
    protected final WeakReference<Context> contextWeakReference;
    protected final AudioDecoderListener listener;

    /**
     *
     * @param audioSource Uri of the audio source to be converted
     * @param context the context
     * @param sampleRate can be either 8000, 16000, 22050 or 44100
     * @see AudioPassThruCapabilities#getSamplingRate()
     * @param sampleType can be either UNSIGNED_8_BIT, SIGNED_16_BIT, FLOAT
     * @see SampleType
     * @param listener listener for event callbacks
     */
    public BaseAudioDecoder(@NonNull Uri audioSource, @NonNull Context context, int sampleRate, @SampleType int sampleType, AudioDecoderListener listener) {
        this.audioSource = audioSource;
        this.contextWeakReference = new WeakReference<>(context);
        this.listener = listener;

        targetSampleRate = sampleRate;
        targetSampleType = sampleType;
    }

    protected void initMediaComponents() throws Exception {
        if(targetSampleRate <= 0){
            throw new InstantiationException("Target sample rate of " + targetSampleRate + " is unsupported");
        }

        extractor = new MediaExtractor();
        Context contextRef = contextWeakReference.get();
        if(contextRef == null){
            throw new InstantiationException("Context reference was null");
        }
        extractor.setDataSource(contextRef, audioSource, null);
        MediaFormat format = null;
        String mime = null;

        // Select the first audio track we find.
        int numTracks = extractor.getTrackCount();
        for (int i = 0; i < numTracks; ++i) {
            MediaFormat f = extractor.getTrackFormat(i);
            String m = f.getString(MediaFormat.KEY_MIME);
            if (m.startsWith("audio/")) {
                format = f;
                mime = m;
                extractor.selectTrack(i);
                break;
            }
        }

        if (mime == null) {
            throw new Exception("The audio file " + audioSource.getPath() + " doesn't contain an audio track.");
        }

        decoder = MediaCodec.createDecoderByType(mime);
        decoder.configure(format, null, null, 0);
    }

    private Double sampleAtTargetTime(double lastOutputSample, SampleBuffer outputSampleBuffer, double outputPresentationTimeUs, double outputDurationPerSampleUs, double targetPresentationTimeUs) {
        double timeDiff = targetPresentationTimeUs - outputPresentationTimeUs;
        double index = timeDiff / outputDurationPerSampleUs;

        // the "last known sample" allows an index from -1.0 to 0
        // the index cannot exceed the last sample. it must be stored to become the "last known sample" in the next iteration
        if (index < -1.0 || Math.ceil(index) >= outputSampleBuffer.limit()) {
            return null;
        }

        if (index == -1.0) {
            // the index points exactly to the last known sample
            return lastOutputSample;
        } else if (index % 1 == 0) {
            // index has no digits. therefore current index points to a known sample
            return outputSampleBuffer.get((int) index);
        } else {
            // the first sample can be the last known one
            double first = index < 0.0 ? lastOutputSample : outputSampleBuffer.get((int) index);
            double second = outputSampleBuffer.get((int) Math.ceil(index));
            double rel = index % 1;

            // if the relative is between -1 and 0
            if (rel < 0.0) {
                rel = 1 + rel;
            }

            return first + (second - first) * rel;
        }
    }

    protected MediaCodec.BufferInfo onInputBufferAvailable(@NonNull MediaExtractor extractor, @NonNull ByteBuffer inputBuffer) {
        long sampleTime = extractor.getSampleTime();
        int counter = 0;
        int maxresult = 0;
        int result;
        boolean advanced = false;

        do {
            result = extractor.readSampleData(inputBuffer, counter);
            if (result >= 0) {
                advanced = extractor.advance();
                maxresult = Math.max(maxresult, result);
                counter += result;
            }
        } while (result >= 0 && advanced && inputBuffer.capacity() - inputBuffer.limit() > maxresult);
        // the remaining capacity should be more than enough for another sample data block

        // queue the input buffer. At end of file counter will be 0 and flags marks end of stream
        // offset MUST be 0. The output buffer code cannot handle offsets
        // result < 0 means the end of the file input is reached
        int flags = advanced ? 0 : MediaCodec.BUFFER_FLAG_END_OF_STREAM;

        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        bufferInfo.set(0, counter, sampleTime, flags);

        return bufferInfo;
    }

    protected SampleBuffer onOutputBufferAvailable(@NonNull ByteBuffer outputBuffer) {
        double outputPresentationTimeUs = lastOutputPresentationTimeUs;
        double outputDurationPerSampleUs = 1000000.0 / (double)outputSampleRate;

        double targetPresentationTimeUs = lastTargetPresentationTimeUs;
        double targetDurationPerSampleUs = 1000000.0 / (double)targetSampleRate;

        // wrap the output buffer to make it provide audio samples
        SampleBuffer outputSampleBuffer = SampleBuffer.wrap(outputBuffer, outputSampleType, outputChannelCount, (long)outputPresentationTimeUs);
        outputSampleBuffer.position(0);

        // the buffer size is related to the output and target sample rate
        // add 2 samples to round up and add an extra sample
        int sampleSize = outputSampleBuffer.limit() * targetSampleRate / outputSampleRate + 2;

        SampleBuffer targetSampleBuffer = SampleBuffer.allocate(sampleSize, targetSampleType, ByteOrder.LITTLE_ENDIAN, (long)targetPresentationTimeUs);
        Double sample;

        do {
            sample = sampleAtTargetTime(lastOutputSample, outputSampleBuffer, outputPresentationTimeUs, outputDurationPerSampleUs, targetPresentationTimeUs);
            if (sample != null) {
                targetSampleBuffer.put(sample);
                targetPresentationTimeUs += targetDurationPerSampleUs;
            }
        } while (sample != null);

        lastTargetPresentationTimeUs = targetPresentationTimeUs;
        lastOutputPresentationTimeUs += outputSampleBuffer.limit() * outputDurationPerSampleUs;
        lastOutputSample = outputSampleBuffer.get(outputSampleBuffer.limit() - 1);

        targetSampleBuffer.limit(targetSampleBuffer.position());
        targetSampleBuffer.position(0);

        return targetSampleBuffer;
    }

    protected void onOutputFormatChanged(@NonNull MediaFormat mediaFormat) {
        if (mediaFormat.containsKey(MediaFormat.KEY_CHANNEL_COUNT)) {
            outputChannelCount = mediaFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
        }

        if (mediaFormat.containsKey(MediaFormat.KEY_SAMPLE_RATE)) {
            outputSampleRate = mediaFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N && mediaFormat.containsKey(MediaFormat.KEY_PCM_ENCODING)) {
            int key = mediaFormat.getInteger(MediaFormat.KEY_PCM_ENCODING);
            switch (key) {
                case AudioFormat.ENCODING_PCM_8BIT:
                    outputSampleType = SampleType.UNSIGNED_8_BIT;
                    break;
                case AudioFormat.ENCODING_PCM_FLOAT:
                    outputSampleType = SampleType.FLOAT;
                    break;
                case AudioFormat.ENCODING_PCM_16BIT:
                default:
                    // by default we fallback to signed 16 bit samples
                    outputSampleType = SampleType.SIGNED_16_BIT;
                    break;
            }
        } else {
            outputSampleType = SampleType.SIGNED_16_BIT;
        }
    }

    protected void onMediaCodecError(@NonNull MediaCodec.CodecException e) {
        DebugTool.logError(TAG, "MediaCodec.onError: " + e.getLocalizedMessage());
        if (listener != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                listener.onDecoderError(e);
            } else {
                listener.onDecoderError(new Exception("Error decoding audio file"));
            }
        }
    }

    public abstract void start();

    public void stop() {
        if (decoder != null) {
            decoder.stop();
            decoder.release();
            decoder = null;
        }

        if (extractor != null) {
            extractor = null;
        }
    }
}
