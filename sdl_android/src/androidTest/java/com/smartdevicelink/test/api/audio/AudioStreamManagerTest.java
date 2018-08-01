package com.smartdevicelink.test.api.audio;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.smartdevicelink.api.audio.AudioDecoderCompat;
import com.smartdevicelink.api.audio.AudioDecoderListener;
import com.smartdevicelink.api.audio.AudioStreamManager;
import com.smartdevicelink.api.audio.BaseAudioDecoder;
import com.smartdevicelink.api.audio.SampleBuffer;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.api.audio.AudioStreamManager.SamplingRate;
import com.smartdevicelink.api.audio.AudioStreamManager.BitsPerSample;
import com.smartdevicelink.api.audio.AudioStreamManager.SampleType;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.ByteOrder;

public class AudioStreamManagerTest extends TestCase {
    public static final String TAG = AudioStreamManagerTest.class.getSimpleName();
    Context mContext;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mContext = InstrumentationRegistry.getContext();
    }

    public void testCreatingAudioStreamManager() {
        AudioStreamManager manager = new AudioStreamManager(sdlInterface, SamplingRate.SIXTEEN_KHZ, BitsPerSample.SIXTEEN_BIT);
    }

    public void testStartAudioStreamManager() {
        AudioStreamManager manager = new AudioStreamManager(sdlInterface, SamplingRate.SIXTEEN_KHZ, BitsPerSample.SIXTEEN_BIT);
        manager.startAudioService(false);
    }

    public void testCompleteDecoderFlow() {
        MockAudioStreamManager manager = new MockAudioStreamManager(sdlInterface, SamplingRate.SIXTEEN_KHZ, BitsPerSample.SIXTEEN_BIT,
                new StreamManagerTestCallback() {
                    @Override
                    public void onTestResult(boolean isPassed, Exception e) {
                        Log.v(TAG, "test complete flow " + (isPassed ? "passed" : ("failed " + e)));
                        assertTrue(isPassed);
                    }
                });
        manager.startAudioService(false);
        manager.pushAudioFile(getSampleFile("warning.mp3"));
    }

    public void testSampleAtTargetTime() {
        Constructor<AudioDecoderCompat> constructor = (Constructor<AudioDecoderCompat>) AudioDecoderCompat.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        BaseAudioDecoder mockDecoder = null;
        try {
            mockDecoder = constructor.newInstance(getSampleFile("sample.mp3"), 16000, SampleType.SIGNED_16_BIT,
                    new AudioDecoderListener() {
                        @Override
                        public void onAudioDataAvailable(SampleBuffer sampleBuffer) {
                        }

                        @Override
                        public void onDecoderFinish() {
                        }

                        @Override
                        public void onDecoderError(Exception e) {
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        Method sampleAtTargetMethod = null;
        try {
            sampleAtTargetMethod = BaseAudioDecoder.class.getDeclaredMethod("sampleAtTargetTime",
                    double.class, SampleBuffer.class, long.class, long.class, long.class);
            sampleAtTargetMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        }

        // TODO: supply range of values here to test
        SampleBuffer sample = SampleBuffer.allocate(1, SampleType.SIGNED_16_BIT, ByteOrder.LITTLE_ENDIAN, 1);
        Double result = null;
        try {
            // TODO: supply range of values here to test
            result = (Double) sampleAtTargetMethod.invoke(mockDecoder, 1.0, sample, 1, 2, 3);
            // TODO: depending on supplied values, assert result's value

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private File getSampleFile(String fileName) {
        File file = new File(mContext.getCacheDir() + "/" + fileName);
        try {
            InputStream is = mContext.getAssets().open(fileName);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        return file;
    }

    public interface StreamManagerTestCallback {
        void onTestResult(boolean isPassed, Exception e);
    }

    ISdl sdlInterface = new ISdl() {
        @Override
        public void start() {

        }

        @Override
        public void stop() {

        }

        @Override
        public boolean isConnected() {
            return false;
        }

        @Override
        public void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {

        }

        @Override
        public void removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {

        }

        @Override
        public void startVideoService(VideoStreamingParameters parameters, boolean encrypted) {

        }

        @Override
        public void stopVideoService() {

        }

        @Override
        public void startAudioService(boolean encrypted) {

        }

        @Override
        public void stopAudioService() {

        }

        @Override
        public void sendRPCRequest(RPCRequest message) {

        }

        @Override
        public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {

        }

        @Override
        public boolean removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {
            return false;
        }
    };
}
