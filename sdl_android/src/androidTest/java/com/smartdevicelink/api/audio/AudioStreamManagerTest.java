package com.smartdevicelink.api.audio;

import android.content.Context;
import android.media.AudioFormat;
import android.media.MediaFormat;
import android.net.rtp.AudioStream;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.api.audio.AudioStreamManager.SampleType;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

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
        ISdl internalInterface = mock(ISdl.class);
        AudioPassThruCapabilities audioCapabilities = new AudioPassThruCapabilities(SamplingRate._16KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        doReturn(true).when(internalInterface).isConnected();
        doReturn(audioCapabilities).when(internalInterface).getCapability(SystemCapabilityType.PCM_STREAMING);

        AudioStreamManager manager = new AudioStreamManager(internalInterface);
    }

    public void testStartAudioStreamManager() {
        final SdlSession mockSession = mock(SdlSession.class);

        Answer<Void> audioServiceAnswer = new Answer<Void>() {
            ISdlServiceListener serviceListener = null;
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Method method = invocation.getMethod();
                Object[] args = invocation.getArguments();

                if (method.getName().equals("addServiceListener")) {
                    // (SessionType serviceType, ISdlServiceListener sdlServiceListener);
                    SessionType sessionType = (SessionType) args[0];
                    assertEquals(sessionType, SessionType.PCM);

                    serviceListener = (ISdlServiceListener) args[1];
                } else if (method.getName().equals("startAudioService")) {
                    //(boolean encrypted, AudioStreamingCodec codec, AudioStreamingParams params);
                    Boolean encrypted = (Boolean)args[0];
                    serviceListener.onServiceStarted(mockSession, SessionType.PCM, encrypted);
                }

                return null;
            }
        };

        ISdl internalInterface = mock(ISdl.class);
        AudioPassThruCapabilities audioCapabilities = new AudioPassThruCapabilities(SamplingRate._16KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        doReturn(true).when(internalInterface).isConnected();
        doReturn(audioCapabilities).when(internalInterface).getCapability(SystemCapabilityType.PCM_STREAMING);
        doAnswer(audioServiceAnswer).when(internalInterface).addServiceListener(any(SessionType.class), any(ISdlServiceListener.class));
        doAnswer(audioServiceAnswer).when(internalInterface).startAudioService(false);


        AudioStreamManager manager = new AudioStreamManager(internalInterface);
        manager.startAudioService(false);
        manager.stopAudioService();
    }

    public void testCompleteDecoderFlow() {
        IAudioStreamListener audioStreamListener = new IAudioStreamListener() {
            @Override
            public void sendAudio(byte[] data, int offset, int length, long presentationTimeUs) throws ArrayIndexOutOfBoundsException {

            }

            @Override
            public void sendAudio(ByteBuffer data, long presentationTimeUs) {

            }
        };

        final SdlSession mockSession = mock(SdlSession.class);
        doReturn(audioStreamListener).when(mockSession).startAudioStream();

        Answer<Void> audioServiceAnswer = new Answer<Void>() {
            ISdlServiceListener serviceListener = null;
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Method method = invocation.getMethod();
                Object[] args = invocation.getArguments();

                if (method.getName().equals("addServiceListener")) {
                    // (SessionType serviceType, ISdlServiceListener sdlServiceListener);
                    SessionType sessionType = (SessionType) args[0];
                    assertEquals(sessionType, SessionType.PCM);

                    serviceListener = (ISdlServiceListener) args[1];
                } else if (method.getName().equals("startAudioService")) {
                    //(boolean encrypted, AudioStreamingCodec codec, AudioStreamingParams params);
                    Boolean encrypted = (Boolean)args[0];
                    serviceListener.onServiceStarted(mockSession, SessionType.PCM, encrypted);
                }

                return null;
            }
        };

        ISdl internalInterface = mock(ISdl.class);
        AudioPassThruCapabilities audioCapabilities = new AudioPassThruCapabilities(SamplingRate._16KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        doReturn(true).when(internalInterface).isConnected();
        doReturn(audioCapabilities).when(internalInterface).getCapability(any(SystemCapabilityType.class));
        doAnswer(audioServiceAnswer).when(internalInterface).addServiceListener(any(SessionType.class), any(ISdlServiceListener.class));
        doAnswer(audioServiceAnswer).when(internalInterface).startAudioService(any(Boolean.class));

        AudioStreamManager manager = new AudioStreamManager(internalInterface);
        manager.startAudioService(false);
        manager.pushAudioFile(getSampleFile("warning.mp3"));
    }

    public void testSampleAtTargetTimeReturnNull() {
        BaseAudioDecoder mockDecoder = mock(BaseAudioDecoder.class, Mockito.CALLS_REAL_METHODS);
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
        BaseAudioDecoder mockDecoder = mock(BaseAudioDecoder.class, Mockito.CALLS_REAL_METHODS);
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
        BaseAudioDecoder mockDecoder = mock(BaseAudioDecoder.class, Mockito.CALLS_REAL_METHODS);
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
        BaseAudioDecoder mockDecoder = mock(BaseAudioDecoder.class, Mockito.CALLS_REAL_METHODS);
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
        BaseAudioDecoder mockDecoder = mock(BaseAudioDecoder.class, Mockito.CALLS_REAL_METHODS);

        try {
            Field outputChannelCountField = BaseAudioDecoder.class.getDeclaredField("outputChannelCount");
            Field outputSampleRateField = BaseAudioDecoder.class.getDeclaredField("outputSampleRate");
            Field outputSampleTypeField = BaseAudioDecoder.class.getDeclaredField("outputSampleType");

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

                mockDecoder.onOutputFormatChanged(format);

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
}
