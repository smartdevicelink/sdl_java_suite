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

    private File audioFile;
    private AudioDecoderListener listener;

    private MediaExtractor extractor;
    private MediaCodec decoder;

    public AudioDecoder(File audioFile, int sampleRate, SampleType sampleType) {
        this.audioFile = audioFile;
        this.targetSampleRate = sampleRate;
        this.targetSampleType = sampleType;
    }

    public void start(AudioDecoderListener listener) {
        try {
            extractor = new MediaExtractor();
            extractor.setDataSource(audioFile.getPath());

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
                throw new Exception("The input file does not contain an audio track.");
            }

            this.listener = listener;

            decoder = MediaCodec.createDecoderByType(mime);
            decoder.configure(format, null, null, 0);
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

                    SampleBuffer targetSampleBuffer = AudioDecoder.super.onOutputBufferAvailable(outputBuffer);
                    mediaCodec.releaseOutputBuffer(i, false);
                    AudioDecoder.this.listener.onAudioDataAvailable(targetSampleBuffer.getByteBuffer());
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
            decoder.start();

        } catch (Exception e) {
            e.printStackTrace();

            listener.onDecoderError(e);

            stop();
        }
    }

    public void stop() {
        if (decoder != null) {
            decoder.stop();
            decoder.release();
            decoder = null;
        }

        if (extractor != null) {
            extractor = null;
        }

        if (this.listener != null) {
            listener.onDecoderFinish();

            this.listener = null;
        }
    }
}