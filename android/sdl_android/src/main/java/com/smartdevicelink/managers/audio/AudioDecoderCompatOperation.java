package com.smartdevicelink.managers.audio;

import android.media.MediaCodec;
import android.media.MediaFormat;

import com.livio.taskmaster.Task;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

class AudioDecoderCompatOperation extends Task {
    private static final String TAG = "AudioDecoderCompatOperation";
    private final WeakReference<AudioDecoderCompat> audioDecoderCompatWeakReference;
    private static final int DEQUEUE_TIMEOUT = 3000;

    AudioDecoderCompatOperation(AudioDecoderCompat audioDecoderCompat) {
        super("AudioDecoderCompatOperation");
        this.audioDecoderCompatWeakReference = new WeakReference<>(audioDecoderCompat);
    }

    @Override
    public void onExecute() {
        start();
    }

    private void start() {
        if (getState() == Task.CANCELED) {
            return;
        }

        final AudioDecoderCompat audioDecoderCompat = audioDecoderCompatWeakReference.get();
        if (audioDecoderCompat == null) {
            DebugTool.logWarning(TAG, "AudioDecoderCompat reference was null");
            return;
        }
        final ByteBuffer[] inputBuffersArray = audioDecoderCompat.decoder.getInputBuffers();
        final ByteBuffer[] outputBuffersArray = audioDecoderCompat.decoder.getOutputBuffers();
        MediaCodec.BufferInfo outputBufferInfo = new MediaCodec.BufferInfo();
        MediaCodec.BufferInfo inputBufferInfo;
        ByteBuffer inputBuffer, outputBuffer;
        SampleBuffer sampleBuffer;

        while (true) {
            int inputBuffersArrayIndex = 0;
            while (inputBuffersArrayIndex != MediaCodec.INFO_TRY_AGAIN_LATER) {
                inputBuffersArrayIndex = audioDecoderCompat.decoder.dequeueInputBuffer(DEQUEUE_TIMEOUT);
                if (inputBuffersArrayIndex >= 0) {
                    inputBuffer = inputBuffersArray[inputBuffersArrayIndex];
                    inputBufferInfo = audioDecoderCompat.onInputBufferAvailable(audioDecoderCompat.extractor, inputBuffer);
                    audioDecoderCompat.decoder.queueInputBuffer(inputBuffersArrayIndex, inputBufferInfo.offset, inputBufferInfo.size, inputBufferInfo.presentationTimeUs, inputBufferInfo.flags);
                }
            }

            int outputBuffersArrayIndex = 0;
            while (outputBuffersArrayIndex != MediaCodec.INFO_TRY_AGAIN_LATER) {
                outputBuffersArrayIndex = audioDecoderCompat.decoder.dequeueOutputBuffer(outputBufferInfo, DEQUEUE_TIMEOUT);
                if (outputBuffersArrayIndex >= 0) {
                    outputBuffer = outputBuffersArray[outputBuffersArrayIndex];
                    if ((outputBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0 && outputBufferInfo.size != 0) {
                        audioDecoderCompat.decoder.releaseOutputBuffer(outputBuffersArrayIndex, false);
                    } else if (outputBuffer.limit() > 0) {
                        sampleBuffer = audioDecoderCompat.onOutputBufferAvailable(outputBuffer);
                        if (audioDecoderCompat.listener != null) {
                            audioDecoderCompat.listener.onAudioDataAvailable(sampleBuffer);
                        }
                        audioDecoderCompat.decoder.releaseOutputBuffer(outputBuffersArrayIndex, false);
                    }
                } else if (outputBuffersArrayIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    MediaFormat newFormat = audioDecoderCompat.decoder.getOutputFormat();
                    audioDecoderCompat.onOutputFormatChanged(newFormat);
                }
            }

            if (outputBufferInfo.flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM) {
                if (audioDecoderCompat.listener != null) {
                    audioDecoderCompat.listener.onDecoderFinish(true);
                }
                audioDecoderCompat.stop();
                break;
            }
        }
    }
}
