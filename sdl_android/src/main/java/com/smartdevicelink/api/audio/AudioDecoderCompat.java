package com.smartdevicelink.api.audio;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.File;
import java.nio.ByteBuffer;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
class AudioDecoderCompat extends BaseAudioDecoder{
    private static final String TAG = AudioDecoderCompat.class.getSimpleName();
    private static final int DEQUEUE_TIMEOUT = 3000;

    AudioDecoderCompat(File audioFile, int sampleRate, SampleType sampleType) {
        super(audioFile, sampleRate, sampleType);
    }

    void start(AudioDecoderListener listener) {
        try {
            this.listener = listener;
            initMediaComponents();
            decoder.start();
            new DecodeAsync().execute(audioFile);
        } catch (Exception e) {
            e.printStackTrace();
            this.listener.onDecoderError(e);
            stop();
        }
    }

    private class DecodeAsync extends AsyncTask<File, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.v(TAG, " DecodeAsync onPostExecute");
            if (listener != null) {
                listener.onDecoderFinish();
            }
            stop();
        }

        @Override
        protected Void doInBackground(File... files) {
            Log.v(TAG, " DecodeAsync doInBackground starts");
            ByteBuffer[] inputBuffersArray = decoder.getInputBuffers();
            ByteBuffer[] outputBuffersArray = decoder.getOutputBuffers();
            MediaCodec.BufferInfo outputBufferInfo = new MediaCodec.BufferInfo();
            while (true) {
                int inputBuffersArrayIndex = 0;
                ByteBuffer inputBuffer;
                MediaCodec.BufferInfo info;
                while (inputBuffersArrayIndex != MediaCodec.INFO_TRY_AGAIN_LATER) {
                    inputBuffersArrayIndex = decoder.dequeueInputBuffer(DEQUEUE_TIMEOUT);
                    if (inputBuffersArrayIndex >= 0) {
                        inputBuffer = inputBuffersArray[inputBuffersArrayIndex];
                        info = AudioDecoderCompat.super.onInputBufferAvailable(extractor, inputBuffer);
                        decoder.queueInputBuffer(inputBuffersArrayIndex, info.offset, info.size, info.presentationTimeUs, info.flags);
                    }
                }

                int outputBuffersArrayIndex = 0;
                ByteBuffer decodedData;
                SampleBuffer buffer;
                while (outputBuffersArrayIndex != MediaCodec.INFO_TRY_AGAIN_LATER) {
                    outputBuffersArrayIndex = decoder.dequeueOutputBuffer(outputBufferInfo, DEQUEUE_TIMEOUT);
                    if (outputBuffersArrayIndex >= 0) {
                        decodedData = outputBuffersArray[outputBuffersArrayIndex];
                        if ((outputBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0 && outputBufferInfo.size != 0) {
                            decoder.releaseOutputBuffer(outputBuffersArrayIndex, false);
                        } else {
                            buffer = AudioDecoderCompat.super.onOutputBufferAvailable(decodedData);
                            listener.onAudioDataAvailable(buffer.getByteBuffer());
                            decoder.releaseOutputBuffer(outputBuffersArrayIndex, false);
                        }
                    } else if (outputBuffersArrayIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                        MediaFormat newFormat = decoder.getOutputFormat();
                        AudioDecoderCompat.super.onOutputFormatChanged(newFormat);
                    }
                }

                if (outputBufferInfo.flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM) {
                    break;
                }
            }

            return null;
        }
    }
}
