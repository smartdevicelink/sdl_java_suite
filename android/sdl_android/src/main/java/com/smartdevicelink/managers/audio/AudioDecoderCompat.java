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
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.smartdevicelink.managers.audio.AudioStreamManager.SampleType;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * The audio decoder to decode a single audio file to PCM.
 * This decoder supports phones with api < 21 but uses methods deprecated with api 21.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class AudioDecoderCompat extends BaseAudioDecoder {
    private static final String TAG = AudioDecoderCompat.class.getSimpleName();
    private static final int DEQUEUE_TIMEOUT = 3000;
    private static Runnable sRunnable;
    private Thread mThread;

    /**
     * Creates a new object of AudioDecoder.
     * @param audioSource The audio source to decode.
     * @param context The context object to use to open the audio source.
     * @param sampleRate The desired sample rate for decoded audio data.
     * @param sampleType The desired sample type (8bit, 16bit, float).
     * @param listener A listener who receives the decoded audio.
     */
    AudioDecoderCompat(@NonNull Uri audioSource, @NonNull Context context, int sampleRate, @SampleType int sampleType, AudioDecoderListener listener) {
        super(audioSource, context, sampleRate, sampleType, listener);
    }

    /**
     * Starts the audio decoding asynchronously.
     */
    public void start() {
        try {
            initMediaComponents();
            decoder.start();
            mThread = new Thread(new DecoderRunnable(AudioDecoderCompat.this));
            mThread.start();

        } catch (Exception e) {
            e.printStackTrace();
            if(this.listener != null) {
                this.listener.onDecoderError(e);
                this.listener.onDecoderFinish(false);
            }
            stop();
        }
    }

    @Override
    public void stop() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
        super.stop();
    }

    /**
     * Runnable to decode audio data
     */
    private static class DecoderRunnable implements Runnable {
        WeakReference<AudioDecoderCompat> weakReference;

        /**
         * Decodes all audio data from source
         * @param audioDecoderCompat instance of this class
         */
        DecoderRunnable(@NonNull AudioDecoderCompat audioDecoderCompat){
            weakReference = new WeakReference<>(audioDecoderCompat);

        }
        @Override
        public void run() {
            final AudioDecoderCompat reference = weakReference.get();
            try {
                if (reference == null) {
                    DebugTool.logWarning(TAG, "AudioDecoderCompat reference was null");
                    return;
                }
                if(reference.decoder == null){
                    DebugTool.logWarning(TAG, "AudioDecoderCompat decoder was null");
                    return;
                }
                while (reference!= null && !reference.mThread.isInterrupted()) {
                    if( AudioDecoder(reference,reference.decoder.getInputBuffers(),reference.decoder.getOutputBuffers())){
                        break;
                    }
                }
            } catch (Exception e) {
                DebugTool.logWarning(TAG, "DecoderRunnable Exception:" + e);
            } finally {
                if (reference != null && reference.mThread != null) {
                    try {
                        reference.mThread.interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        reference.mThread = null;
                    }
                }
            }
        }

        boolean AudioDecoder(final AudioDecoderCompat reference,final ByteBuffer[] inputBuffersArray,final ByteBuffer[] outputBuffersArray){
            MediaCodec.BufferInfo outputBufferInfo = new MediaCodec.BufferInfo();
            MediaCodec.BufferInfo inputBufferInfo;
            ByteBuffer inputBuffer, outputBuffer;
            SampleBuffer sampleBuffer;
            int inputBuffersArrayIndex = 0;
            while (inputBuffersArrayIndex != MediaCodec.INFO_TRY_AGAIN_LATER) {
                try {
                    inputBuffersArrayIndex = reference.decoder.dequeueInputBuffer(DEQUEUE_TIMEOUT);
                    if (inputBuffersArrayIndex >= 0) {
                        inputBuffer = inputBuffersArray[inputBuffersArrayIndex];
                        inputBufferInfo = reference.onInputBufferAvailable(reference.extractor, inputBuffer);
                        reference.decoder.queueInputBuffer(inputBuffersArrayIndex, inputBufferInfo.offset, inputBufferInfo.size, inputBufferInfo.presentationTimeUs, inputBufferInfo.flags);
                    }
                } catch (Exception e) {
                    return true;
                }
            }
            int outputBuffersArrayIndex = 0;
            while (outputBuffersArrayIndex != MediaCodec.INFO_TRY_AGAIN_LATER) {
                try {
                    outputBuffersArrayIndex = reference.decoder.dequeueOutputBuffer(outputBufferInfo, DEQUEUE_TIMEOUT);
                    if (outputBuffersArrayIndex >= 0) {
                        outputBuffer = outputBuffersArray[outputBuffersArrayIndex];
                        if ((outputBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0 && outputBufferInfo.size != 0) {
                            reference.decoder.releaseOutputBuffer(outputBuffersArrayIndex, false);
                        } else if (outputBuffer.limit() > 0) {
                            ArrayList<SampleBuffer> sampleBufferList = reference.onOutputBufferAvailable(outputBuffer);
                            if (reference.listener != null) {
                                reference.listener.onAudioDataAvailable(sampleBufferList, outputBufferInfo.flags);
                            }
                            reference.decoder.releaseOutputBuffer(outputBuffersArrayIndex, false);
                        }
                    } else if (outputBuffersArrayIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                        MediaFormat newFormat = reference.decoder.getOutputFormat();
                        reference.onOutputFormatChanged(newFormat);
                    }
                } catch (Exception e) {
                    return true;
                }
            }
            if (outputBufferInfo.flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM) {
                reference.listener.onAudioDataAvailable(null,outputBufferInfo.flags);
                if (reference.listener != null) {
                    reference.listener.onDecoderFinish(true);
                }
                reference.stop();
                return true;
            }
            return false;
        }
    }
}
