package com.smartdevicelink.api.audio;

import android.content.Context;
import android.media.AudioFormat;
import android.media.MediaFormat;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.api.CompletionListener;
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
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

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
        manager.startAudioStream(false);
        manager.stopAudioStream();
    }

    public void testWithSquareSampleAudio16BitAnd8Khz() {
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._8KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(8000, SampleType.SIGNED_16_BIT, audioPassThruCapabilities);
    }

    public void testWithSquareSampleAudio16BitAnd16Khz() {
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._16KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(16000, SampleType.SIGNED_16_BIT, audioPassThruCapabilities);
    }

    public void testWithSquareSampleAudio16BitAnd22Khz() {
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._22KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(22050, SampleType.SIGNED_16_BIT, audioPassThruCapabilities);
    }

    public void testWithSquareSampleAudio16BitAnd44Khz() {
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._44KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(44100, SampleType.SIGNED_16_BIT, audioPassThruCapabilities);
    }

    public void testWithSquareSampleAudio8BitAnd8Khz() {
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._8KHZ, BitsPerSample._8_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(8000, SampleType.UNSIGNED_8_BIT, audioPassThruCapabilities);
    }

    public void testWithSquareSampleAudio8BitAnd16Khz() {
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._16KHZ, BitsPerSample._8_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(16000, SampleType.UNSIGNED_8_BIT, audioPassThruCapabilities);
    }

    public void testWithSquareSampleAudio8BitAnd22Khz() {
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._22KHZ, BitsPerSample._8_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(22050, SampleType.UNSIGNED_8_BIT, audioPassThruCapabilities);
    }

    public void testWithSquareSampleAudio8BitAnd44Khz() {
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._44KHZ, BitsPerSample._8_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(44100, SampleType.UNSIGNED_8_BIT, audioPassThruCapabilities);
    }

    private int testFullAudioManagerDecodeFlowCorrectCounter = 0;
    private int testFullAudioManagerDecodeFlowWrongCounter = 0;
    private void runFullAudioManagerDecodeFlowWithSquareSampleAudio(final int sampleRate, final @SampleType int sampleType, final AudioPassThruCapabilities audioCapabilities) {
        testFullAudioManagerDecodeFlowCorrectCounter = 0;
        testFullAudioManagerDecodeFlowWrongCounter = 0;

        IAudioStreamListener audioStreamListener = new IAudioStreamListener() {
            @Override
            public void sendAudio(byte[] data, int offset, int length, long presentationTimeUs) throws ArrayIndexOutOfBoundsException {
                ByteBuffer buffer = ByteBuffer.wrap(data, offset, length);
                this.sendAudio(buffer, presentationTimeUs);
            }

            @Override
            public void sendAudio(ByteBuffer data, long presentationTimeUs) {
                SampleBuffer samples = SampleBuffer.wrap(data, sampleType, presentationTimeUs);
                double timeUs = presentationTimeUs;
                double sampleDurationUs = 1000000.0 / sampleRate;

                for (int i = 0; i < samples.limit(); ++i) {
                    double sample = samples.get(i);
                    double edge = timeUs % 4000.0;

                    if (edge > 2000.0) {
                        // swap sample as it's negative expected
                        sample = sample * -1.0;
                    }

                    edge = edge % 2000.0;

                    // at the edge of a wave the sample can be lower than 0.7
                    if ((sample > 0.7 && sample < 0.95) || (edge < sampleDurationUs || (2000.0 - sampleDurationUs) < edge)) {
                        testFullAudioManagerDecodeFlowCorrectCounter++;
                    } else {
                        testFullAudioManagerDecodeFlowWrongCounter++;
                    }

                    timeUs += sampleDurationUs;
                }
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
        doReturn(true).when(internalInterface).isConnected();
        doReturn(audioCapabilities).when(internalInterface).getCapability(any(SystemCapabilityType.class));
        doAnswer(audioServiceAnswer).when(internalInterface).addServiceListener(any(SessionType.class), any(ISdlServiceListener.class));
        doAnswer(audioServiceAnswer).when(internalInterface).startAudioService(any(Boolean.class));

        File file = getSampleFile("raw/test_audio_square_250hz_80amp_1s.mp3");

        CompletionListener completionListener = new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                assertEquals(true, success);

                // not more than 2.5 percent samples must be wrong
                double relation = 100.0 * (double)testFullAudioManagerDecodeFlowWrongCounter / (double)testFullAudioManagerDecodeFlowCorrectCounter;
                Log.v(TAG, "Validating number of correct samples (" + Math.round(relation) + "%)");
                if (relation > 2.5) {
                    fail("Validating raw audio failed. " + Math.round(relation) + " % wrong samples detected. Correct: " + testFullAudioManagerDecodeFlowCorrectCounter + ", Wrong: " + testFullAudioManagerDecodeFlowWrongCounter);
                }
            }
        };

        CompletionListener mockListener = Mockito.spy(completionListener);

        AudioStreamManager manager = new AudioStreamManager(internalInterface);
        manager.startAudioStream(false);
        manager.pushAudioFile(file, mockListener);

        verify(mockListener, timeout(10000)).onComplete(any(Boolean.class));
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

                int output_channel_count = outputChannelCountField.getInt(mockDecoder);
                int output_sample_rate = outputSampleRateField.getInt(mockDecoder);
                int output_sample_type = outputSampleTypeField.getInt(mockDecoder);

                Log.v(TAG, "Assert: channel_count == output_channel_count (" + channel_count + " == " + output_channel_count + ")");
                assertEquals(channel_count, output_channel_count);
                Log.v(TAG, "Assert: sample_rate == output_sample_rate (" + sample_rate + " == " + output_sample_rate + ")");
                assertEquals(sample_rate, output_sample_rate);
                Log.v(TAG, "Assert: sample_type_result == output_sample_type (" + sample_type_result + " == " + output_sample_type + ")");
                assertEquals(sample_type_result, output_sample_type);
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
        file.getParentFile().mkdirs();

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
