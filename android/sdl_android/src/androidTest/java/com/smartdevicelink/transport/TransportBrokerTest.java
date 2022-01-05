package com.smartdevicelink.transport;

import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.test.SdlUnitTestContants;
import com.smartdevicelink.test.util.DeviceUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TransportBrokerTest { //FIXME this test class needs to be fixed. At this point these tests are not helpful
    RouterServiceValidator rsvp;
    //        public TransportBrokerThread(Context context, String appId, ComponentName service){

    @Before
    public void setUp() throws Exception {
        rsvp = new RouterServiceValidator(getInstrumentation().getTargetContext());
        rsvp.validateAsync(new RouterServiceValidator.ValidationStatusCallback() {
            @Override
            public void onFinishedValidation(boolean valid, ComponentName name) {

            }
        });

    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
    }

    @Test
    public void testStart() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        TransportBroker broker = new TransportBroker(getInstrumentation().getTargetContext(), SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
        if (!DeviceUtil.isEmulator()) { // Cannot perform MBT operations in emulator
            assertTrue(broker.start());
        }
        broker.stop();

    }

    @Test
    public void testSendPacket() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        TransportBroker broker = new TransportBroker(getInstrumentation().getTargetContext(), SdlUnitTestContants.TEST_APP_ID, rsvp.getService());

        if (!DeviceUtil.isEmulator()) { // Cannot perform MBT operations in emulator
            assertTrue(broker.start());
        }
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (!DeviceUtil.isEmulator()) { // Cannot perform BT adapter operations in emulator
            assertNotNull(adapter);
            assertTrue(adapter.isEnabled());
        }
        //Not ideal, but not implementing callbacks just for unit tests
        int count = 0;
        while (broker.routerServiceMessenger == null && count < 10) {
            sleep();
            count++;
        }
        if (!DeviceUtil.isEmulator()) { // Cannot perform BT adapter operations in emulator
            assertNotNull(broker.routerServiceMessenger);
        }

        //assertFalse(broker.sendPacketToRouterService(null, 0, 0));
        //assertFalse(broker.sendPacketToRouterService(new byte[3], -1, 0));
        //assertFalse(broker.sendPacketToRouterService(new byte[3], 0, 4));
        //assertTrue(broker.sendPacketToRouterService(new byte[3],0, 3));

        broker.stop();

    }

    @Test
    public void testOnPacketReceived() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        TransportBroker broker = new TransportBroker(getInstrumentation().getTargetContext(), SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
        if (!DeviceUtil.isEmulator()) { // Cannot perform MBT operations in emulator
            assertTrue(broker.start());
        }

    }

    @Test
    public void testSendMessageToRouterService() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        TransportBroker broker = new TransportBroker(getInstrumentation().getTargetContext(), SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
        Handler handler = new Handler();
        Message message = new Message();
        broker.routerServiceMessenger = null;
        broker.isBound = true;

        assertFalse(broker.sendMessageToRouterService(message));

        broker.routerServiceMessenger = new Messenger(handler); //So it's not ambiguous

        broker.isBound = false;

        assertFalse(broker.sendMessageToRouterService(message));

        broker.isBound = true;
        broker.registeredWithRouterService = true;

        message = null;

        assertFalse(broker.sendMessageToRouterService(message));

        message = new Message();

        assertTrue(broker.sendMessageToRouterService(message));

    }

}
