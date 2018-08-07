package com.smartdevicelink.api.audio;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import com.smartdevicelink.api.audio.AudioStreamManager.SampleType;

import java.io.File;
import java.nio.ByteBuffer;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class AudioDecoder extends BaseAudioDecoder {
    private static final String TAG = AudioDecoder.class.getSimpleName();

    AudioDecoder(File audioFile, int sampleRate, @SampleType int sampleType, AudioDecoderListener listener) {
        super(audioFile, sampleRate, sampleType, listener);
    }

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
                        listener.onDecoderFinish();
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
            stop();
        }
    }
}