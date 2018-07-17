package com.smartdevicelink.api.audio;

import android.media.AudioFormat;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/**
 *
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SdlAudioDecoder extends MediaCodec.Callback {
    public enum SampleType { UNSIGNED_8_BIT, SIGNED_16_BIT, FLOAT }

    private static final String TAG = SdlAudioDecoder.class.getSimpleName();

    private int targetSampleRate;
    private SampleType targetSampleType;

    private SdlAudioFile audioFile;
    private MediaExtractor extractor;
    private MediaCodec decoder;

    private int outputSampleRate;
    private SampleType outputSampleType;

    private double lastOutputSample = 0;

    private long lastOutputPresentationTimeUs = 0;
    private long lastTargetPresentationTimeUs = 0;

    public SdlAudioDecoder(SdlAudioFile audioFile, int sampleRate, SampleType sampleType) {
        this.audioFile = audioFile;
        this.targetSampleRate = sampleRate;
        this.targetSampleType = sampleType;
    }

    void start() {
        try {
            extractor = new MediaExtractor();
            extractor.setDataSource(audioFile.getInputFile().getPath());

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


            decoder = MediaCodec.createDecoderByType(mime);
            decoder.configure(format, null, null, 0);
            decoder.setCallback(this);
            decoder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Double sampleAtTargetTime(double lastOutputSample, ShortBuffer outputShortBuffer, long outputPresentationTimeUs, long outputDurationPerSampleUs, long targetPresentationTimeUs) {
        double timeDiff = targetPresentationTimeUs - outputPresentationTimeUs;
        double index = timeDiff / outputDurationPerSampleUs;

        // the "last known sample" allows an index from -1.0 to 0
        // the index cannot exceed the last sample. it must be stored to become the "last known sample" in the next iteration
        if (index < -1.0 || Math.ceil(index) >= outputShortBuffer.limit()) {
            return null;
        }

        if (index == -1.0) {
            // the index points exactly to the last known sample
            return lastOutputSample;
        } else if (index % 1 == 0) {
            // index has no digits. therefore current index points to a known sample
            return (double) outputShortBuffer.get((int) index);
        } else {
            // the first sample can be the last known one
            double first = index < 0.0 ? lastOutputSample : outputShortBuffer.get((int) index);
            double second = outputShortBuffer.get((int) Math.ceil(index));
            double rel = index % 1;

            // if the relative is between -1 and 0
            if (rel < 0.0) {
                rel = 1 + rel;
            }

            return (double)(first + (second - first) * rel);
        }
    }

    @Override
    public void onInputBufferAvailable(@NonNull MediaCodec mediaCodec, int i) {
        ByteBuffer inputBuffer = mediaCodec.getInputBuffer(i);
        long sampleTime = extractor.getSampleTime();
        int counter = 0;
        int result;

        do {
            result = extractor.readSampleData(inputBuffer, counter);
            if (result >= 0) {

                extractor.advance();
                counter += result;
            } else {
                break;
            }
        } while (counter < (inputBuffer.capacity() - result)); //-result is the amount of data expected next

        // queue the input buffer. At end of file counter will be 0 and flags marks end of stream
        // offset MUST be 0. The output buffer code cannot handle offsets
        // result < 0 means the end of the file input is reached
        int flags = result < 0 ? MediaCodec.BUFFER_FLAG_END_OF_STREAM : 0;
        mediaCodec.queueInputBuffer(i, 0, counter, sampleTime, flags);
    }

    @Override
    public void onOutputBufferAvailable(@NonNull MediaCodec mediaCodec, int i, @NonNull MediaCodec.BufferInfo bufferInfo) {
        ByteBuffer outputBuffer = mediaCodec.getOutputBuffer(i);
        if (outputBuffer == null) return;

        outputBuffer.position(0);
        int outputSamples = outputBuffer.limit() >> 1;
        ShortBuffer outputShortBuffer = outputBuffer.asShortBuffer();

        long outputPresentationTimeUs = lastOutputPresentationTimeUs;
        long outputDurationPerSampleUs = 1000000 / outputSampleRate;

        long targetPresentationTimeUs = lastTargetPresentationTimeUs;
        long targetDurationPerSampleUs = 1000000 / targetSampleRate;

        // the buffer size is related to the output and target sample rate
        // multiply by two for short samples, add 2 to round up and add an extra sample
        int bufferSize = outputSamples * targetSampleRate / outputSampleRate * 2 + 4;

        ByteBuffer targetBuffer = ByteBuffer.allocate(bufferSize);
        targetBuffer.order(ByteOrder.LITTLE_ENDIAN);
        ShortBuffer targetShortBuffer = targetBuffer.asShortBuffer();
        Double sample;

        do {
            sample = sampleAtTargetTime(lastOutputSample, outputShortBuffer, outputPresentationTimeUs, outputDurationPerSampleUs, targetPresentationTimeUs);
            if (sample != null) {
                targetShortBuffer.put((short)Math.round(sample));
                targetPresentationTimeUs += targetDurationPerSampleUs;
            }
        } while (sample != null);

        lastTargetPresentationTimeUs = targetPresentationTimeUs;
        lastOutputPresentationTimeUs += outputSamples * outputDurationPerSampleUs;
        lastOutputSample = outputShortBuffer.get(outputSamples - 1);

        targetBuffer.limit(targetShortBuffer.position() * 2);

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(outputFile, true);
            fileOutputStream.getChannel().write(targetBuffer);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaCodec.releaseOutputBuffer(i, false);
    }

    @Override
    public void onOutputFormatChanged(@NonNull MediaCodec mediaCodec, @NonNull MediaFormat mediaFormat) {
        outputSampleRate = mediaFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE);;

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
                    outputSampleType = SampleType.SIGNED_16_BIT;
                    break;
            }
        } else {
            outputSampleType = SampleType.SIGNED_16_BIT;
        }
    }

    @Override
    public void onError(@NonNull MediaCodec mediaCodec, @NonNull MediaCodec.CodecException e) {
        Log.e(TAG, "MediaCodec.onError: " + e.getLocalizedMessage());
    }
}