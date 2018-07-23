package com.smartdevicelink.api.audio;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.File;
import java.nio.ByteBuffer;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class AudioDecoder extends BaseAudioDecoder {
    private static final String TAG = AudioDecoder.class.getSimpleName();

    public AudioDecoder(File audioFile, int sampleRate, SampleType sampleType) {
        super(audioFile, sampleRate, sampleType);
        this.targetSampleRate = sampleRate;
        this.targetSampleType = sampleType;
    }

    public void start(AudioDecoderListener listener) {
        try {
            mListener = listener;
            initMediaComponents();
            mDecoder.setCallback(new MediaCodec.Callback() {
                @Override
                public void onInputBufferAvailable(@NonNull MediaCodec mediaCodec, int i) {
                    ByteBuffer inputBuffer = mediaCodec.getInputBuffer(i);
                    if (inputBuffer == null) return;

                    MediaCodec.BufferInfo info = AudioDecoder.super.onInputBufferAvailable(mExtractor, inputBuffer);
                    mediaCodec.queueInputBuffer(i, info.offset, info.size, info.presentationTimeUs, info.flags);
                }

                @Override
                public void onOutputBufferAvailable(@NonNull MediaCodec mediaCodec, int i, @NonNull MediaCodec.BufferInfo bufferInfo) {
                    ByteBuffer outputBuffer = mediaCodec.getOutputBuffer(i);
                    if (outputBuffer == null) return;

                    SampleBuffer targetSampleBuffer = AudioDecoder.super.onOutputBufferAvailable(outputBuffer);
                    mediaCodec.releaseOutputBuffer(i, false);
                    mListener.onAudioDataAvailable(targetSampleBuffer.getByteBuffer());
                    if (bufferInfo.flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM) {
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
            mDecoder.start();
        } catch (Exception e) {
            e.printStackTrace();
            mListener.onDecoderError(e);
            stop();
        }
    }
}