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

import java.nio.ByteBuffer;

/**
 * The audio decoder to decode a single audio file to PCM.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class AudioDecoder extends BaseAudioDecoder {
    private static final String TAG = AudioDecoder.class.getSimpleName();

    /**
     * Creates a new object of AudioDecoder.
     * @param audioSource The audio source to decode.
     * @param context The context object to use to open the audio source.
     * @param sampleRate The desired sample rate for decoded audio data.
     * @param sampleType The desired sample type (8bit, 16bit, float).
     * @param listener A listener who receives the decoded audio.
     */
    AudioDecoder(Uri audioSource, Context context, int sampleRate, @SampleType int sampleType, AudioDecoderListener listener) {
        super(audioSource, context, sampleRate, sampleType, listener);
    }

    /**
     * Starts the audio decoding asynchronously.
     */
    public void start() {
        try {
            initMediaComponents();

            decoder.setCallback(new MediaCodec.Callback() {
                @Override
                public void onInputBufferAvailable(@NonNull MediaCodec mediaCodec, int i) {
                    ByteBuffer inputBuffer = mediaCodec.getInputBuffer(i);
                    if (inputBuffer == null) return;

                    MediaCodec.BufferInfo info = AudioDecoder.super.onInputBufferAvailable(extractor, inputBuffer);
                    mediaCodec.queueInputBuffer(i, info.offset, info.size, info.presentationTimeUs, info.flags);
                }

                @Override
                public void onOutputBufferAvailable(@NonNull MediaCodec mediaCodec, int i, @NonNull MediaCodec.BufferInfo bufferInfo) {
                    ByteBuffer outputBuffer = mediaCodec.getOutputBuffer(i);
                    if (outputBuffer == null) return;

                    if (outputBuffer.limit() > 0) {
                        SampleBuffer targetSampleBuffer = AudioDecoder.super.onOutputBufferAvailable(outputBuffer);
                        AudioDecoder.this.listener.onAudioDataAvailable(targetSampleBuffer);
                    } else {
                        Log.w(TAG, "output buffer empty. Chance that silence was detected");
                    }

                    mediaCodec.releaseOutputBuffer(i, false);

                    if (bufferInfo.flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM) {
                        listener.onDecoderFinish(true);
                        stop();
                    }
                }

                @Override
                public void onOutputFormatChanged(@NonNull MediaCodec mediaCodec, @NonNull MediaFormat mediaFormat) {
                    AudioDecoder.super.onOutputFormatChanged(mediaFormat);
                }

                @Override
                public void onError(@NonNull MediaCodec mediaCodec, @NonNull MediaCodec.CodecException e) {
                    AudioDecoder.super.onMediaCodecError(e);
                }
            });

            decoder.start();
        } catch (Exception e) {
            e.printStackTrace();
            listener.onDecoderError(e);
            listener.onDecoderFinish(false);
            stop();
        }
    }
}