package com.smartdevicelink.managers.audio;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.smartdevicelink.managers.audio.AudioStreamManager.SampleType;

import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

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
            if (reference == null) {
                Log.w(TAG, "AudioDecoderCompat reference was null");
                return;
            }
            final ByteBuffer[] inputBuffersArray = reference.decoder.getInputBuffers();
            final ByteBuffer[] outputBuffersArray = reference.decoder.getOutputBuffers();
            MediaCodec.BufferInfo outputBufferInfo = new MediaCodec.BufferInfo();
            MediaCodec.BufferInfo inputBufferInfo;
            ByteBuffer inputBuffer, outputBuffer;
            SampleBuffer sampleBuffer;

            while (reference!= null && !reference.mThread.isInterrupted()) {
                int inputBuffersArrayIndex = 0;
                while (inputBuffersArrayIndex != MediaCodec.INFO_TRY_AGAIN_LATER) {
                    inputBuffersArrayIndex = reference.decoder.dequeueInputBuffer(DEQUEUE_TIMEOUT);
                    if (inputBuffersArrayIndex >= 0) {
                        inputBuffer = inputBuffersArray[inputBuffersArrayIndex];
                        inputBufferInfo = reference.onInputBufferAvailable(reference.extractor, inputBuffer);
                        reference.decoder.queueInputBuffer(inputBuffersArrayIndex, inputBufferInfo.offset, inputBufferInfo.size, inputBufferInfo.presentationTimeUs, inputBufferInfo.flags);
                    }
                }

                int outputBuffersArrayIndex = 0;
                while (outputBuffersArrayIndex != MediaCodec.INFO_TRY_AGAIN_LATER) {
                    outputBuffersArrayIndex = reference.decoder.dequeueOutputBuffer(outputBufferInfo, DEQUEUE_TIMEOUT);
                    if (outputBuffersArrayIndex >= 0) {
                        outputBuffer = outputBuffersArray[outputBuffersArrayIndex];
                        if ((outputBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0 && outputBufferInfo.size != 0) {
                            reference.decoder.releaseOutputBuffer(outputBuffersArrayIndex, false);
                        } else if (outputBuffer.limit() > 0) {
                            sampleBuffer = reference.onOutputBufferAvailable(outputBuffer);
                            if(reference.listener!=null){
                                reference.listener.onAudioDataAvailable(sampleBuffer);
                            }
                            reference.decoder.releaseOutputBuffer(outputBuffersArrayIndex, false);
                        }
                    } else if (outputBuffersArrayIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                        MediaFormat newFormat = reference.decoder.getOutputFormat();
                        reference.onOutputFormatChanged(newFormat);
                    }
                }

                if (outputBufferInfo.flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM) {
                    if (reference.listener != null) {
                        reference.listener.onDecoderFinish(true);
                    }
                    reference.stop();
                    try {
                        reference.mThread.interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        reference.mThread = null;
                        break;
                    }
                }
            }
        }
    }
}
