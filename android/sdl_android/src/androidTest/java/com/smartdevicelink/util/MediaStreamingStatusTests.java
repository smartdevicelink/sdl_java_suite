package com.smartdevicelink.util;

import android.Manifest;
import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import com.smartdevicelink.managers.SdlManager;
import com.smartdevicelink.managers.SdlManagerListener;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.transport.MultiplexTransportConfig;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Vector;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@RunWith(AndroidJUnit4.class)
public class MediaStreamingStatusTests {
    @Rule
    public GrantPermissionRule btRuntimePermissionRule;

    @Mock
    private AudioManager audioManager = mock(AudioManager.class);

    @Mock
    Context mockedContext;

    MediaStreamingStatus defaultMediaStreamingStatus;

    private Answer<Object> onGetSystemService = new Answer<Object>() {
        @Override
        public Object answer(InvocationOnMock invocation) {
            Object[] args = invocation.getArguments();
            String serviceName = (String) args[0];
            if (serviceName != null && serviceName.equalsIgnoreCase(Context.AUDIO_SERVICE)) {
                return audioManager;
            } else {
                return null;
            }
        }
    };


    @Before
    public void setUp() throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            btRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.BLUETOOTH_CONNECT);
        }
        mockedContext = mock(Context.class);
        doAnswer(onGetSystemService).when(mockedContext).getSystemService(Context.AUDIO_SERVICE);
        defaultMediaStreamingStatus = new MediaStreamingStatus(mockedContext, mock(MediaStreamingStatus.Callback.class));
    }


    @Test
    public void testEmptyAudioDeviceInfoList() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            assertNotNull(mockedContext);
            MediaStreamingStatus mediaStreamingStatus = new MediaStreamingStatus(mockedContext, new MediaStreamingStatus.Callback() {
                @Override
                public void onAudioNoLongerAvailable() {

                }
            });
            doAnswer(new Answer() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    return new AudioDeviceInfo[0];
                }
            }).when(audioManager).getDevices(AudioManager.GET_DEVICES_OUTPUTS);


            assertFalse(mediaStreamingStatus.isAudioOutputAvailable());
        }
    }

    @Test
    public void testNullAudioDeviceInfoList() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            assertNotNull(mockedContext);
            MediaStreamingStatus mediaStreamingStatus = new MediaStreamingStatus(mockedContext, mock(MediaStreamingStatus.Callback.class));
            doAnswer(new Answer() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    return null;
                }
            }).when(audioManager).getDevices(AudioManager.GET_DEVICES_OUTPUTS);

            assertFalse(mediaStreamingStatus.isAudioOutputAvailable());
        }
    }


    @Test
    public void testSdlManagerMedia() {
        SdlManager.Builder builder = new SdlManager.Builder(getInstrumentation().getTargetContext(), TestValues.GENERAL_FULL_APP_ID, TestValues.GENERAL_STRING, mock(SdlManagerListener.class));
        Vector<AppHMIType> appType = new Vector<>();
        appType.add(AppHMIType.MEDIA);
        builder.setAppTypes(appType);
        MultiplexTransportConfig multiplexTransportConfig = new MultiplexTransportConfig(getInstrumentation().getTargetContext(), TestValues.GENERAL_FULL_APP_ID);

        assertNull(multiplexTransportConfig.requiresAudioSupport());
        builder.setTransportType(multiplexTransportConfig);

        SdlManager manager = builder.build();
        manager.start();

        //Original reference should be updated
        assertTrue(multiplexTransportConfig.requiresAudioSupport());
    }

    @Test
    public void testSdlManagerNonMedia() {
        SdlManager.Builder builder = new SdlManager.Builder(getInstrumentation().getTargetContext(), TestValues.GENERAL_FULL_APP_ID, TestValues.GENERAL_STRING, mock(SdlManagerListener.class));
        Vector<AppHMIType> appType = new Vector<>();
        appType.add(AppHMIType.DEFAULT);
        builder.setAppTypes(appType);
        MultiplexTransportConfig multiplexTransportConfig = new MultiplexTransportConfig(getInstrumentation().getTargetContext(), TestValues.GENERAL_FULL_APP_ID);

        assertNull(multiplexTransportConfig.requiresAudioSupport());
        builder.setTransportType(multiplexTransportConfig);

        SdlManager manager = builder.build();
        manager.start();

        //Original reference should be updated
        assertFalse(multiplexTransportConfig.requiresAudioSupport());
    }

    @Test
    public void testAcceptedBTDevices() {
        MediaStreamingStatus mediaStreamingStatus = spy(new MediaStreamingStatus(getInstrumentation().getTargetContext(), mock(MediaStreamingStatus.Callback.class)));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return true;
            }
        }).when(mediaStreamingStatus).isBluetoothActuallyAvailable();

        assertTrue(mediaStreamingStatus.isBluetoothActuallyAvailable());
        assertTrue(mediaStreamingStatus.isSupportedAudioDevice(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP));
    }

    @Test
    public void testAcceptedUSBDevices() {
        MediaStreamingStatus mediaStreamingStatus = spy(new MediaStreamingStatus(getInstrumentation().getTargetContext(), mock(MediaStreamingStatus.Callback.class)));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return true;
            }
        }).when(mediaStreamingStatus).isUsbActuallyConnected();

        assertTrue(mediaStreamingStatus.isUsbActuallyConnected());
        assertTrue(mediaStreamingStatus.isSupportedAudioDevice(AudioDeviceInfo.TYPE_USB_DEVICE));
        assertTrue(mediaStreamingStatus.isSupportedAudioDevice(AudioDeviceInfo.TYPE_USB_ACCESSORY));
        assertTrue(mediaStreamingStatus.isSupportedAudioDevice(AudioDeviceInfo.TYPE_USB_HEADSET));
        assertTrue(mediaStreamingStatus.isSupportedAudioDevice(AudioDeviceInfo.TYPE_DOCK));
    }

    @Test
    public void testAcceptedLineDevices() {
        assertTrue(defaultMediaStreamingStatus.isSupportedAudioDevice(AudioDeviceInfo.TYPE_LINE_ANALOG));
        assertTrue(defaultMediaStreamingStatus.isSupportedAudioDevice(AudioDeviceInfo.TYPE_LINE_DIGITAL));
        assertTrue(defaultMediaStreamingStatus.isSupportedAudioDevice(AudioDeviceInfo.TYPE_AUX_LINE));
        assertTrue(defaultMediaStreamingStatus.isSupportedAudioDevice(AudioDeviceInfo.TYPE_WIRED_HEADSET));
        assertTrue(defaultMediaStreamingStatus.isSupportedAudioDevice(AudioDeviceInfo.TYPE_WIRED_HEADPHONES));
    }


}
