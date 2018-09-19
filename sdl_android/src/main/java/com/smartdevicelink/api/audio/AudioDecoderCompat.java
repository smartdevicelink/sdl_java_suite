package com.smartdevicelink.api.audio;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.smartdevicelink.api.audio.AudioStreamManager.SampleType;

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
    private final Handler mHandler;

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
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Starts the audio decoding asynchronously.
     */
    public void start() {
        try {
            initMediaComponents();
            decoder.start();
            WeakReference<AudioDecoderCompat> weakReference = new WeakReference<>(this);
            final AudioDecoderCompat reference = weakReference.get();
            sRunnable = new Runnable() {
                @Override
                public void run() {
                    decodeAudio(reference);
                }
            };
            mHandler.post(sRunnable);
        } catch (Exception e) {
            e.printStackTrace();
            this.listener.onDecoderError(e);
            this.listener.onDecoderFinish(false);
            stop();
        }
    }

    /**
     * Decodes all audio data from source
     * @param reference the weak reference of this class
     */
    private void decodeAudio(AudioDecoderCompat reference) {
        if (reference == null) {
            listener.onDecoderFinish(false);
            return;
        }
        ByteBuffer[] inputBuffersArray = reference.decoder.getInputBuffers();
        ByteBuffer[] outputBuffersArray = reference.decoder.getOutputBuffers();
        MediaCodec.BufferInfo outputBufferInfo = new MediaCodec.BufferInfo();
        ByteBuffer inputBuffer;
        ByteBuffer outputBuffer;
        SampleBuffer sampleBuffer;

        while (true) {
            int inputBuffersArrayIndex = 0;
            while (inputBuffersArrayIndex != MediaCodec.INFO_TRY_AGAIN_LATER) {
                inputBuffersArrayIndex = reference.decoder.dequeueInputBuffer(DEQUEUE_TIMEOUT);
                if (inputBuffersArrayIndex >= 0) {
                    inputBuffer = inputBuffersArray[inputBuffersArrayIndex];
                    MediaCodec.BufferInfo inputBufferInfo = onInputBufferAvailable(extractor, inputBuffer);
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
                        sampleBuffer = onOutputBufferAvailable(outputBuffer);
                        listener.onAudioDataAvailable(sampleBuffer);
                        reference.decoder.releaseOutputBuffer(outputBuffersArrayIndex, false);
                    }
                } else if (outputBuffersArrayIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    MediaFormat newFormat = reference.decoder.getOutputFormat();
                    onOutputFormatChanged(newFormat);
                }
            }

            if (outputBufferInfo.flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM) {
                if (listener != null) {
                    listener.onDecoderFinish(true);
                }
                stop();
                break;
            }
        }
    }
}
