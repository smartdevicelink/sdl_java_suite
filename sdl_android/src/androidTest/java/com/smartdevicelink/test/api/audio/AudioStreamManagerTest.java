package com.smartdevicelink.test.api.audio;

import android.content.Context;
import android.media.AudioFormat;
import android.media.MediaFormat;
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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteOrder;

import javax.annotation.Nullable;

public class AudioStreamManagerTest extends TestCase {
    public static final String TAG = AudioStreamManagerTest.class.getSimpleName();
    private Context mContext;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mContext = InstrumentationRegistry.getContext();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        mContext = null;
    }

    public void testCreatingAudioStreamManager() {
        AudioStreamManager manager = new AudioStreamManager(sdlInterface, SamplingRate.SIXTEEN_KHZ, BitsPerSample.SIXTEEN_BIT);
    }

    public void testStartAudioStreamManager() {
        AudioStreamManager manager = new AudioStreamManager(sdlInterface, SamplingRate.SIXTEEN_KHZ, BitsPerSample.SIXTEEN_BIT);
        manager.startAudioService(false);
        manager.stopAudioService();
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

    public void testSampleAtTargetTimeReturnNull() {
        BaseAudioDecoder mockDecoder = createMockBaseAudioDecoder();
        Method sampleAtTargetMethod = getSampleAtTargetMethod();
        SampleBuffer sample = SampleBuffer.allocate(1, SampleType.SIGNED_16_BIT, ByteOrder.LITTLE_ENDIAN, 1);
        Double result = new Double(5.0);
        try {
            result = (Double) sampleAtTargetMethod.invoke(mockDecoder, 1.0, sample, 1, 3, 2);
            assertNull(result);
            result = (Double) sampleAtTargetMethod.invoke(mockDecoder, 1.0, sample, 5, 3, 1);
            assertNull(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testSampleAtTargetTimeReturnLastOutputSample() {
        BaseAudioDecoder mockDecoder = createMockBaseAudioDecoder();
        Method sampleAtTargetMethod = getSampleAtTargetMethod();
        SampleBuffer sample = SampleBuffer.allocate(1, SampleType.SIGNED_16_BIT, ByteOrder.LITTLE_ENDIAN, 1);
        Double result = null;
        Double lastOutputSample = 15.0;
        try {
            result = (Double) sampleAtTargetMethod.invoke(mockDecoder, lastOutputSample, sample, 6, 1, 5);
            assertTrue(result.doubleValue() == lastOutputSample);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testSampleAtTargetTimeReturnOutputSampleGet() {
        BaseAudioDecoder mockDecoder = createMockBaseAudioDecoder();
        Method sampleAtTargetMethod = getSampleAtTargetMethod();
        SampleBuffer sample = SampleBuffer.allocate(10, SampleType.SIGNED_16_BIT, ByteOrder.LITTLE_ENDIAN, 1);
        Double result = null;
        try {
            result = (Double) sampleAtTargetMethod.invoke(mockDecoder, 1.0, sample, 1, 1, 2);
            assertTrue(result.doubleValue() == sample.get(1));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testSampleAtTargetTime() {
        BaseAudioDecoder mockDecoder = createMockBaseAudioDecoder();
        Method sampleAtTargetMethod = getSampleAtTargetMethod();
        SampleBuffer sample = SampleBuffer.allocate(10, SampleType.SIGNED_16_BIT, ByteOrder.LITTLE_ENDIAN, 1);
        Double result = null;
        try {
            result = (Double) sampleAtTargetMethod.invoke(mockDecoder, 1.0, sample, 1, 3, 2);
            assertNotNull(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testOutputFormatChanged() {
        BaseAudioDecoder mockDecoder = createMockBaseAudioDecoder();
        try {
            Method onOutputFormatChangedMethod = BaseAudioDecoder.class.getDeclaredMethod("onOutputFormatChanged", MediaFormat.class);

            Field outputChannelCountField = BaseAudioDecoder.class.getDeclaredField("outputChannelCount");
            Field outputSampleRateField = BaseAudioDecoder.class.getDeclaredField("outputSampleRate");
            Field outputSampleTypeField = BaseAudioDecoder.class.getDeclaredField("outputSampleType");

            onOutputFormatChangedMethod.setAccessible(true);
            outputChannelCountField.setAccessible(true);
            outputSampleRateField.setAccessible(true);
            outputSampleTypeField.setAccessible(true);

            // channel count, sample rate, sample type
            int key_channel_count = 0, key_sample_rate = 1, key_sample_type = 2, key_sample_type_result = 3;
            int[][] tests = new int[][] {
                    { 47, 42000, AudioFormat.ENCODING_PCM_8BIT, SampleType.UNSIGNED_8_BIT },
                    { 2, 16000, AudioFormat.ENCODING_PCM_16BIT, SampleType.SIGNED_16_BIT },
                    { 1, 22050, AudioFormat.ENCODING_PCM_FLOAT, SampleType.FLOAT },
                    { 3, 48000, AudioFormat.ENCODING_INVALID, SampleType.SIGNED_16_BIT },
            };

            for (int[] test : tests) {
                int channel_count = test[key_channel_count];
                int sample_rate = test[key_sample_rate];
                int sample_type = test[key_sample_type];
                int sample_type_result = test[key_sample_type_result];

                MediaFormat format = new MediaFormat();

                format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, channel_count);
                format.setInteger(MediaFormat.KEY_SAMPLE_RATE, sample_rate);
                format.setInteger(MediaFormat.KEY_PCM_ENCODING, sample_type);

                onOutputFormatChangedMethod.invoke(mockDecoder, format);

                assertEquals(channel_count, outputChannelCountField.getInt(mockDecoder));
                assertEquals(sample_rate, outputSampleRateField.getInt(mockDecoder));
                assertEquals(sample_type_result, outputSampleTypeField.getInt(mockDecoder));
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private Method getSampleAtTargetMethod() {
        Method method = null;
        try {
            method = BaseAudioDecoder.class.getDeclaredMethod("sampleAtTargetTime",
                    double.class, SampleBuffer.class, double.class, double.class, double.class);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        }
        return method;
    }

    private BaseAudioDecoder createMockBaseAudioDecoder() {
        AudioDecoderListener listener = new AudioDecoderListener() {
            @Override
            public void onAudioDataAvailable(SampleBuffer sampleBuffer) {

            }

            @Override
            public void onDecoderFinish() {

            }

            @Override
            public void onDecoderError(Exception e) {

            }
        };

        return new BaseAudioDecoder(getSampleFile("sample.mp3"), 16000, SampleType.SIGNED_16_BIT, listener) {
            @Override
            public void start() {

            }
        };
    }

    private File getSampleFile(String fileName) {
        File file = new File(mContext.getCacheDir() + "/" + fileName);
        try {
            InputStream is = mContext.getAssets().open(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1 << 12];
            int length;
            do {
                length = is.read(buffer);
                if (length > 0) {
                    fos.write(buffer, 0, length);
                }
            } while (length > 0);

            fos.flush();
            fos.close();
            is.close();
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
