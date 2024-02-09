package com.smartdevicelink.managers.audio;

import android.content.Context;
import android.media.AudioFormat;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.livio.taskmaster.Taskmaster;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.audio.AudioStreamManager.SampleType;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.protocol.ISdlServiceListener;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.session.SdlSession;
import com.smartdevicelink.streaming.audio.IAudioStreamListener;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AudioStreamManagerTest extends TestCase {
    public static final String TAG = AudioStreamManagerTest.class.getSimpleName();
    private Context mContext;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mContext = InstrumentationRegistry.getInstrumentation().getContext();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        mContext = null;
    }

    public void testCreatingAudioStreamManager() {
        ISdl internalInterface = mock(ISdl.class);
        SystemCapabilityManager systemCapabilityManager = mock(SystemCapabilityManager.class);
        doReturn(systemCapabilityManager).when(internalInterface).getSystemCapabilityManager();
        AudioPassThruCapabilities audioCapabilities = new AudioPassThruCapabilities(SamplingRate._16KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        doReturn(true).when(internalInterface).isConnected();
        doReturn(audioCapabilities).when(systemCapabilityManager).getCapability(eq(SystemCapabilityType.PCM_STREAMING), (OnSystemCapabilityListener) isNull(), anyBoolean());

        new AudioStreamManager(internalInterface, mContext);
    }

    public void testStartAudioStreamManager() {
        final SdlSession mockSession = mock(SdlSession.class);

        Answer<Void> audioServiceAnswer = new Answer<Void>() {
            ISdlServiceListener serviceListener = null;

            @Override
            public Void answer(InvocationOnMock invocation) {
                Method method = invocation.getMethod();
                Object[] args = invocation.getArguments();

                switch (method.getName()) {
                    case "addServiceListener":
                        // parameters (SessionType serviceType, ISdlServiceListener sdlServiceListener);
                        SessionType sessionType = (SessionType) args[0];
                        assertEquals(sessionType, SessionType.PCM);
                        serviceListener = (ISdlServiceListener) args[1];
                        break;
                    case "startAudioService":
                        // parameters (boolean encrypted, AudioStreamingCodec codec, AudioStreamingParams params);
                        Boolean encrypted = (Boolean) args[0];
                        serviceListener.onServiceStarted(mockSession, SessionType.PCM, encrypted);
                        break;
                }

                return null;
            }
        };

        ISdl internalInterface = createISdlMock();
        SystemCapabilityManager systemCapabilityManager = mock(SystemCapabilityManager.class);
        doReturn(systemCapabilityManager).when(internalInterface).getSystemCapabilityManager();
        AudioPassThruCapabilities audioCapabilities = new AudioPassThruCapabilities(SamplingRate._16KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        doReturn(true).when(internalInterface).isConnected();
        doReturn(audioCapabilities).when(systemCapabilityManager).getCapability(eq(SystemCapabilityType.PCM_STREAMING), (OnSystemCapabilityListener) isNull(), anyBoolean());
        doAnswer(audioServiceAnswer).when(internalInterface).addServiceListener(any(SessionType.class), any(ISdlServiceListener.class));
        doAnswer(audioServiceAnswer).when(internalInterface).startAudioService(any(Boolean.class));

        CompletionListener completionListener = new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                assertEquals(true, success);
            }
        };

        CompletionListener mockListener = spy(completionListener);
        AudioStreamManager manager = new AudioStreamManager(internalInterface, mContext);

        manager.startAudioStream(false, mockListener);
        manager.stopAudioStream(mockListener);
        verify(mockListener, timeout(10000).times(2)).onComplete(any(Boolean.class));
    }

    public void testWithSquareSampleAudio16BitAnd8KhzApi16() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 16);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._8KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(8000, SampleType.SIGNED_16_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio16BitAnd16KhzApi16() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 16);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._16KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(16000, SampleType.SIGNED_16_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio16BitAnd22KhzApi16() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 16);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._22KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(22050, SampleType.SIGNED_16_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio16BitAnd44KhzApi16() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 16);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._44KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(44100, SampleType.SIGNED_16_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio8BitAnd8KhzApi16() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 16);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._8KHZ, BitsPerSample._8_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(8000, SampleType.UNSIGNED_8_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio8BitAnd16KhzApi16() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 16);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._16KHZ, BitsPerSample._8_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(16000, SampleType.UNSIGNED_8_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio8BitAnd22KhzApi16() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 16);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._22KHZ, BitsPerSample._8_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(22050, SampleType.UNSIGNED_8_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio8BitAnd44KhzApi16() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 16);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._44KHZ, BitsPerSample._8_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(44100, SampleType.UNSIGNED_8_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio16BitAnd8KhzApi21() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 21);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._8KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(8000, SampleType.SIGNED_16_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio16BitAnd16KhzApi21() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 21);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._16KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(16000, SampleType.SIGNED_16_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio16BitAnd22KhzApi21() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 21);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._22KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(22050, SampleType.SIGNED_16_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio16BitAnd44KhzApi21() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 21);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._44KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(44100, SampleType.SIGNED_16_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio8BitAnd8KhzApi21() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 21);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._8KHZ, BitsPerSample._8_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(8000, SampleType.UNSIGNED_8_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio8BitAnd16KhzApi21() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 21);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._16KHZ, BitsPerSample._8_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(16000, SampleType.UNSIGNED_8_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio8BitAnd22KhzApi21() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 21);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._22KHZ, BitsPerSample._8_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(22050, SampleType.UNSIGNED_8_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    public void testWithSquareSampleAudio8BitAnd44KhzApi21() throws Exception {
        int versionCode = Build.VERSION.SDK_INT;
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 21);
        AudioPassThruCapabilities audioPassThruCapabilities = new AudioPassThruCapabilities(SamplingRate._44KHZ, BitsPerSample._8_BIT, AudioType.PCM);
        runFullAudioManagerDecodeFlowWithSquareSampleAudio(44100, SampleType.UNSIGNED_8_BIT, audioPassThruCapabilities);
        setFinalStatic(Build.VERSION.class.getField("SDK_INT"), versionCode);
    }

    private int testFullAudioManagerDecodeFlowCorrectCounter = 0;
    private int testFullAudioManagerDecodeFlowWrongCounter = 0;

    private void runFullAudioManagerDecodeFlowWithSquareSampleAudio(final int sampleRate, final @SampleType int sampleType, final AudioPassThruCapabilities audioCapabilities) {
        testFullAudioManagerDecodeFlowCorrectCounter = 0;
        testFullAudioManagerDecodeFlowWrongCounter = 0;

        final IAudioStreamListener audioStreamListener = new IAudioStreamListener() {
            @Override
            public void sendAudio(byte[] data, int offset, int length, long presentationTimeUs) throws ArrayIndexOutOfBoundsException {
                ByteBuffer buffer = ByteBuffer.wrap(data, offset, length);
                this.sendAudio(buffer, presentationTimeUs, null);
            }

            @Override
            public void sendAudio(ByteBuffer data, long presentationTimeUs, CompletionListener listener) {
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

        Answer<Void> audioServiceAnswer = new Answer<Void>() {
            ISdlServiceListener serviceListener = null;

            @Override
            public Void answer(InvocationOnMock invocation) {
                Method method = invocation.getMethod();
                Object[] args = invocation.getArguments();

                switch (method.getName()) {
                    case "addServiceListener":
                        // (SessionType serviceType, ISdlServiceListener sdlServiceListener);
                        SessionType sessionType = (SessionType) args[0];
                        assertEquals(sessionType, SessionType.PCM);

                        serviceListener = (ISdlServiceListener) args[1];
                        break;
                    case "startAudioService":
                        //(boolean encrypted, AudioStreamingCodec codec, AudioStreamingParams params);
                        Boolean encrypted = (Boolean) args[0];
                        serviceListener.onServiceStarted(mockSession, SessionType.PCM, encrypted);
                        break;
                }

                return null;
            }
        };

        ISdl internalInterface = createISdlMock();
        SystemCapabilityManager systemCapabilityManager = mock(SystemCapabilityManager.class);
        doReturn(systemCapabilityManager).when(internalInterface).getSystemCapabilityManager();
        doReturn(true).when(internalInterface).isConnected();
        doReturn(audioCapabilities).when(systemCapabilityManager).getCapability(any(SystemCapabilityType.class), (OnSystemCapabilityListener) isNull(), anyBoolean());
        doAnswer(audioServiceAnswer).when(internalInterface).addServiceListener(any(SessionType.class), any(ISdlServiceListener.class));
        doAnswer(audioServiceAnswer).when(internalInterface).startAudioService(any(Boolean.class));

        CompletionListener fileCompletionListener = new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                assertEquals(true, success);

                // not more than 2.5 percent samples must be wrong
                double relation = 100.0 * (double) testFullAudioManagerDecodeFlowWrongCounter / (double) testFullAudioManagerDecodeFlowCorrectCounter;
                Log.v(TAG, "Validating number of correct samples (" + Math.round(relation) + "%)");
                if (relation > 2.5) {
                    fail("Validating raw audio failed. " + Math.round(relation) + " % wrong samples detected. Correct: " + testFullAudioManagerDecodeFlowCorrectCounter + ", Wrong: " + testFullAudioManagerDecodeFlowWrongCounter);
                }
            }
        };

        final CompletionListener mockFileListener = spy(fileCompletionListener);

        final AudioStreamManager manager = new AudioStreamManager(internalInterface, mContext) {
            @Override
            public IAudioStreamListener startAudioStream(SdlSession session) {
                return audioStreamListener;
            }
        };
        manager.startAudioStream(false, new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                assertEquals(true, success);

                manager.pushResource(com.smartdevicelink.test.R.raw.test_audio_square_250hz_80amp_1s, mockFileListener);
            }
        });

        verify(mockFileListener, timeout(10000)).onComplete(any(Boolean.class));
    }

    public void testSampleAtTargetTimeReturnNull() {
        BaseAudioDecoder mockDecoder = mock(BaseAudioDecoder.class, Mockito.CALLS_REAL_METHODS);
        Method sampleAtTargetMethod = getSampleAtTargetMethod();
        SampleBuffer sample = SampleBuffer.allocate(1, SampleType.SIGNED_16_BIT, ByteOrder.LITTLE_ENDIAN, 1);
        Double result;
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
        Double result;
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
        Double result;
        try {
            result = (Double) sampleAtTargetMethod.invoke(mockDecoder, 1.0, sample, 1, 1, 2);
            assertTrue(result == sample.get(1));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testSampleAtTargetTime() {
        BaseAudioDecoder mockDecoder = mock(BaseAudioDecoder.class, Mockito.CALLS_REAL_METHODS);
        Method sampleAtTargetMethod = getSampleAtTargetMethod();
        SampleBuffer sample = SampleBuffer.allocate(10, SampleType.SIGNED_16_BIT, ByteOrder.LITTLE_ENDIAN, 1);
        Double result;
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
            int[][] tests = new int[][]{
                    {47, 42000, AudioFormat.ENCODING_PCM_8BIT, SampleType.UNSIGNED_8_BIT},
                    {2, 16000, AudioFormat.ENCODING_PCM_16BIT, SampleType.SIGNED_16_BIT},
                    {1, 22050, AudioFormat.ENCODING_PCM_FLOAT, SampleType.FLOAT},
                    {3, 48000, AudioFormat.ENCODING_INVALID, SampleType.SIGNED_16_BIT},
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

                // in case the phone version is old the method does not take sample type into account but
                // always expected 16 bit. See https://developer.android.com/reference/android/media/MediaFormat.html#KEY_PCM_ENCODING
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
                    sample_type_result = SampleType.SIGNED_16_BIT;
                }

                mockDecoder.onOutputFormatChanged(format);

                int output_channel_count = outputChannelCountField.getInt(mockDecoder);
                int output_sample_rate = outputSampleRateField.getInt(mockDecoder);
                int output_sample_type = outputSampleTypeField.getInt(mockDecoder);

                // changing from assertEquals to if and fail so travis gives better results

                if (channel_count != output_channel_count) {
                    fail("AssertEqualsFailed: channel_count == output_channel_count (" + channel_count + " == " + output_channel_count + ")");
                }

                if (sample_rate != output_sample_rate) {
                    fail("AssertEqualsFailed: sample_rate == output_sample_rate (" + sample_rate + " == " + output_sample_rate + ")");
                }

                if (sample_type_result != output_sample_type) {
                    fail("Assert: sample_type_result == output_sample_type (" + sample_type_result + " == " + output_sample_type + ")");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testPlayAudioFileForManualTest() throws IOException {
        AudioPassThruCapabilities audioCapabilities = new AudioPassThruCapabilities(SamplingRate._16KHZ, BitsPerSample._16_BIT, AudioType.PCM);
        final int sampleType = SampleType.SIGNED_16_BIT;
        final int sampleRate = 16000;

        final File outputFile = new File(mContext.getCacheDir(), "test_audio_file.wav");
        assertNotNull((outputFile));
        final FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        assertNotNull(fileOutputStream);
        writeWaveHeader(fileOutputStream, sampleRate, sampleType << 3);

        IAudioStreamListener audioStreamListener = new IAudioStreamListener() {
            long audioLength = 0;

            @Override
            public void sendAudio(byte[] data, int offset, int length, long presentationTimeUs) throws ArrayIndexOutOfBoundsException {
                ByteBuffer buffer = ByteBuffer.wrap(data, offset, length);
                this.sendAudio(buffer, presentationTimeUs, null);
            }

            @Override
            public void sendAudio(ByteBuffer data, long presentationTimeUs, CompletionListener listener) {
                try {
                    long length = data.limit();
                    byte[] d = data.array();
                    fileOutputStream.write(d, 0, (int) length);

                    audioLength += length;
                    RandomAccessFile raf = new RandomAccessFile(outputFile, "rw");
                    updateWaveHeaderLength(raf, audioLength);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        final SdlSession mockSession = mock(SdlSession.class);

        Answer<Void> audioServiceAnswer = new Answer<Void>() {
            ISdlServiceListener serviceListener = null;

            @Override
            public Void answer(InvocationOnMock invocation) {
                Method method = invocation.getMethod();
                Object[] args = invocation.getArguments();

                switch (method.getName()) {
                    case "addServiceListener":
                        // (SessionType serviceType, ISdlServiceListener sdlServiceListener);
                        SessionType sessionType = (SessionType) args[0];
                        assertEquals(sessionType, SessionType.PCM);

                        serviceListener = (ISdlServiceListener) args[1];
                        break;
                    case "startAudioService":
                        //(boolean encrypted, AudioStreamingCodec codec, AudioStreamingParams params);
                        Boolean encrypted = (Boolean) args[0];
                        serviceListener.onServiceStarted(mockSession, SessionType.PCM, encrypted);
                        break;
                }

                return null;
            }
        };

        ISdl internalInterface = createISdlMock();
        SystemCapabilityManager systemCapabilityManager = mock(SystemCapabilityManager.class);
        doReturn(systemCapabilityManager).when(internalInterface).getSystemCapabilityManager();
        doReturn(true).when(internalInterface).isConnected();
        doReturn(audioCapabilities).when(systemCapabilityManager).getCapability(any(SystemCapabilityType.class), (OnSystemCapabilityListener) isNull(), anyBoolean());
        doAnswer(audioServiceAnswer).when(internalInterface).addServiceListener(any(SessionType.class), any(ISdlServiceListener.class));
        doAnswer(audioServiceAnswer).when(internalInterface).startAudioService(any(Boolean.class));

        final MediaPlayer.OnCompletionListener mockPlayerCompletionListener = mock(MediaPlayer.OnCompletionListener.class);
        final MediaPlayer player = new MediaPlayer();
        player.setOnCompletionListener(mockPlayerCompletionListener);

        CompletionListener fileCompletionListener = new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();

                    player.setDataSource(outputFile.getPath());
                    player.prepare();
                    player.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        final CompletionListener mockFileListener = spy(fileCompletionListener);

        final AudioStreamManager manager = new AudioStreamManager(internalInterface, mContext);
        manager.startAudioStream(false, new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                assertEquals(true, success);

                manager.pushResource(com.smartdevicelink.test.R.raw.test_audio_square_250hz_80amp_1s, mockFileListener);
            }
        });

        verify(mockFileListener, timeout(10000)).onComplete(any(Boolean.class));
        verify(mockPlayerCompletionListener, timeout(10000)).onCompletion(any(MediaPlayer.class));
    }

    public void testPlayRawAudio() {
        AudioPassThruCapabilities audioCapabilities = new AudioPassThruCapabilities(SamplingRate._16KHZ, BitsPerSample._16_BIT, AudioType.PCM);

        final IAudioStreamListener audioStreamListener = mock(IAudioStreamListener.class);

        final CompletionListener completionListener = mock(CompletionListener.class);

        final SdlSession mockSession = mock(SdlSession.class);

        Answer<Void> audioServiceAnswer = new Answer<Void>() {
            ISdlServiceListener serviceListener = null;

            @Override
            public Void answer(InvocationOnMock invocation) {
                Method method = invocation.getMethod();
                Object[] args = invocation.getArguments();

                switch (method.getName()) {
                    case "addServiceListener":
                        SessionType sessionType = (SessionType) args[0];
                        assertEquals(sessionType, SessionType.PCM);

                        serviceListener = (ISdlServiceListener) args[1];
                        break;
                    case "startAudioService":
                        Boolean encrypted = (Boolean) args[0];
                        serviceListener.onServiceStarted(mockSession, SessionType.PCM, encrypted);
                        break;
                }

                return null;
            }
        };

        ISdl internalInterface = createISdlMock();
        SystemCapabilityManager systemCapabilityManager = mock(SystemCapabilityManager.class);
        doReturn(systemCapabilityManager).when(internalInterface).getSystemCapabilityManager();
        doReturn(true).when(internalInterface).isConnected();
        doReturn(audioCapabilities).when(systemCapabilityManager).getCapability(any(SystemCapabilityType.class), (OnSystemCapabilityListener) isNull(), anyBoolean());
        doAnswer(audioServiceAnswer).when(internalInterface).addServiceListener(any(SessionType.class), any(ISdlServiceListener.class));
        doAnswer(audioServiceAnswer).when(internalInterface).startAudioService(any(Boolean.class));

        final AudioStreamManager manager = new AudioStreamManager(internalInterface, mContext) {
            @Override
            public IAudioStreamListener startAudioStream(SdlSession session) {
                return audioStreamListener;
            }
        };

        manager.startAudioStream(false, new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                assertTrue(success);
                byte[] buffer = new byte[100];
                manager.pushBuffer(ByteBuffer.wrap(buffer), completionListener);
            }
        });

        verify(audioStreamListener, timeout(10000)).sendAudio(any(ByteBuffer.class), any(Long.class), eq(completionListener));
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

    private void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        field.set(null, newValue);
    }

    private void writeWaveHeader(OutputStream stream, long samplerate, long bitspersample) throws IOException {
        byte[] header = new byte[44];
        // the data header is 36 bytes large
        long datalength = 36;
        long audiolength = 0;
        long format = 1; // 1 = PCM
        long channels = 1;
        long blockalign = (channels * bitspersample) >> 3;
        long byterate = (samplerate * channels * bitspersample) >> 3;

        // RIFF header.
        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        // Total data length (UInt32).
        header[4] = (byte) ((datalength) & 0xff);
        header[5] = (byte) ((datalength >> 8) & 0xff);
        header[6] = (byte) ((datalength >> 16) & 0xff);
        header[7] = (byte) ((datalength >> 24) & 0xff);
        // WAVE header.
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        // Format (fmt) header.
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        // Format header size (UInt32).
        header[16] = 16;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        // Format type (UInt16). Set 1 for PCM.
        header[20] = (byte) ((format) & 0xff);
        header[21] = (byte) ((format >> 8) & 0xff);
        // Channels
        header[22] = (byte) ((channels) & 0xff);
        header[23] = (byte) ((channels >> 8) & 0xff);
        // Sample rate (UInt32).
        header[24] = (byte) ((samplerate) & 0xff);
        header[25] = (byte) ((samplerate >> 8) & 0xff);
        header[26] = (byte) ((samplerate >> 16) & 0xff);
        header[27] = (byte) ((samplerate >> 24) & 0xff);
        // Byte rate (UInt32).
        header[28] = (byte) ((byterate) & 0xff);
        header[29] = (byte) ((byterate >> 8) & 0xff);
        header[30] = (byte) ((byterate >> 16) & 0xff);
        header[31] = (byte) ((byterate >> 24) & 0xff);
        // Block alignment (UInt16).
        header[32] = (byte) ((blockalign) & 0xff);
        header[33] = (byte) ((blockalign >> 8) & 0xff);
        // Bits per sample (UInt16).
        header[34] = (byte) ((bitspersample) & 0xff);
        header[35] = (byte) ((bitspersample >> 8) & 0xff);
        // Data header
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        // Total audio length (UInt32).
        header[40] = (byte) ((audiolength) & 0xff);
        header[41] = (byte) ((audiolength >> 8) & 0xff);
        header[42] = (byte) ((audiolength >> 16) & 0xff);
        header[43] = (byte) ((audiolength >> 24) & 0xff);

        stream.write(header, 0, header.length);
    }

    /**
     * Updates the data length and audio length of an existing RIFF/WAVE header in the file pointed by the RandomAccessFile object.
     */
    private void updateWaveHeaderLength(RandomAccessFile stream, long audiolength) throws IOException {
        // the data header is 36 bytes large
        long datalength = 36 + audiolength;

        // Seek from the beginning to data length
        stream.seek(4);
        // Overwrite total data length
        stream.write((int) ((datalength) & 0xff));
        stream.write((int) ((datalength >> 8) & 0xff));
        stream.write((int) ((datalength >> 16) & 0xff));
        stream.write((int) ((datalength >> 24) & 0xff));
        // Seek from the end of data length to audio length
        stream.seek(40);
        // overwrite total audio length
        stream.write((int) ((audiolength) & 0xff));
        stream.write((int) ((audiolength >> 8) & 0xff));
        stream.write((int) ((audiolength >> 16) & 0xff));
        stream.write((int) ((audiolength >> 24) & 0xff));
    }

    private ISdl createISdlMock() {
        ISdl internalInterface = mock(ISdl.class);
        Taskmaster taskmaster = new Taskmaster.Builder().build();
        taskmaster.start();

        when(internalInterface.getTaskmaster()).thenReturn(taskmaster);
        return internalInterface;
    }
}
