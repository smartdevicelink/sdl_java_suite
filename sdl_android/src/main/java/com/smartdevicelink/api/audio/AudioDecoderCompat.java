package com.smartdevicelink.api.audio;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.nio.ByteBuffer;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class AudioDecoderCompat extends BaseAudioDecoder{

    public static final String TAG = AudioDecoderCompat.class.getSimpleName();
    public static final int DEQUEUE_TIMEOUT = 3000;

    public AudioDecoderCompat(File inFile, int sampleRate, SampleType sampleType) {
        super(inFile, sampleRate, sampleType);
    }

    public void start(AudioDecoderListener listener) {
        mListener = listener;
        initMediaComponents();
        mDecoder.start();
        new DecodeAsync().execute(mInputFile);
    }

    private class DecodeAsync extends AsyncTask<File, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(File... files) {
            ByteBuffer[] inputBuffersArray = mDecoder.getInputBuffers();
            ByteBuffer[] outputBuffersArray = mDecoder.getOutputBuffers();
            MediaCodec.BufferInfo outputBufferInfo = new MediaCodec.BufferInfo();
            while (true) {
                int inputBuffersArrayIndex = 0;
                while (inputBuffersArrayIndex != MediaCodec.INFO_TRY_AGAIN_LATER) {
                    inputBuffersArrayIndex = mDecoder.dequeueInputBuffer(DEQUEUE_TIMEOUT);
                    ByteBuffer tempBuffer;
                    if (inputBuffersArrayIndex >= 0) {
                        long sampleTime = mExtractor.getSampleTime();
                        tempBuffer = inputBuffersArray[inputBuffersArrayIndex];
                        int counter = 0;
                        int result;
                        do {
                            result = mExtractor.readSampleData(tempBuffer, counter);
                            if (result >= 0) {
                                mExtractor.advance();
                                counter += result;
                            } else {
                                break;
                            }
                        }
                        while (counter < (tempBuffer.capacity() - result));
                        int flags = result < 0 ? MediaCodec.BUFFER_FLAG_END_OF_STREAM : 0;
                        mDecoder.queueInputBuffer(inputBuffersArrayIndex, 0, counter, sampleTime, flags);
                    }
                }

                int outputBuffersArrayIndex = 0;
                while (outputBuffersArrayIndex != MediaCodec.INFO_TRY_AGAIN_LATER) {
                    outputBuffersArrayIndex = mDecoder.dequeueOutputBuffer(outputBufferInfo, DEQUEUE_TIMEOUT);
                    if (outputBuffersArrayIndex >= 0) {
                        ByteBuffer decodedData = outputBuffersArray[outputBuffersArrayIndex];
                        if ((outputBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0 && outputBufferInfo.size != 0) {
                            mDecoder.releaseOutputBuffer(outputBuffersArrayIndex, false);
                        } else {
                            SampleBuffer buffer = AudioDecoderCompat.super.onOutputBufferAvailable(decodedData);
                            mListener.onAudioDataAvailable(buffer.getByteBuffer());
                            mDecoder.releaseOutputBuffer(outputBuffersArrayIndex, false);
                        }
                    } else if (outputBuffersArrayIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                        MediaFormat newFormat = mDecoder.getOutputFormat();
                        AudioDecoderCompat.super.onOutputFormatChanged(newFormat);
                    }
                }
                if (outputBufferInfo.flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM) {
                    break;
                }
            }
            if (mListener != null) {
                mListener.onDecoderFinish();
            }
            stop();

            return null;
        }
    }
}
