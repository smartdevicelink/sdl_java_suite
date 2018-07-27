package com.smartdevicelink.test.api.audio;

import android.support.annotation.NonNull;

import com.smartdevicelink.api.audio.AudioDecoder;
import com.smartdevicelink.api.audio.AudioDecoderListener;
import com.smartdevicelink.api.audio.AudioStreamManager;
import com.smartdevicelink.api.audio.BaseAudioDecoder;
import com.smartdevicelink.api.audio.SampleBuffer;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.test.api.audio.AudioStreamManagerTest.StreamManagerTestCallback;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MockAudioStreamManager extends AudioStreamManager {

    private StreamManagerTestCallback mCallback;

    public MockAudioStreamManager(@NonNull ISdl sdlInterface, @NonNull @SamplingRate String sampleRate,
                                  @NonNull @BitsPerSample int sampleType, StreamManagerTestCallback cb) {
        super(sdlInterface, sampleRate, sampleType);
        mCallback = cb;
    }

    @Override
    public void pushAudioFile(File audioFile) {
        AudioDecoderListener listener = new AudioDecoderListener() {
            @Override
            public void onAudioDataAvailable(SampleBuffer buffer) {
            }

            @Override
            public void onDecoderFinish() {
                mCallback.onTestResult(true, null);
            }

            @Override
            public void onDecoderError(Exception e) {
                mCallback.onTestResult(false, e);
            }
        };

        Constructor<AudioDecoder> constructor = (Constructor<AudioDecoder>) AudioDecoder.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        BaseAudioDecoder mockDecoder = null;
        Method startMethod = null;
        try {
            mockDecoder = constructor.newInstance(audioFile, 16000, SampleType.SIGNED_16_BIT, listener);
            startMethod = BaseAudioDecoder.class.getDeclaredMethod("start");
            startMethod.setAccessible(true);
            startMethod.invoke(mockDecoder);
        } catch (Exception e) {
            e.printStackTrace();
            mCallback.onTestResult(false, e);
        }
    }
}
