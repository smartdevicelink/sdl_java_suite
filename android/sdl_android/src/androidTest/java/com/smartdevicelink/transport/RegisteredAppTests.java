package com.smartdevicelink.transport;

import android.os.Looper;
import android.os.Messenger;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.transport.enums.TransportType;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;


/**
 * Created by brettywhite on 4/4/17.
 */
@RunWith(AndroidJUnit4.class)
public class RegisteredAppTests {

    private static final String APP_ID = "123451123";
    private static final Messenger messenger = null;
    private static byte[] bytes = new byte[1];

    @Test
    public void testHandleMessage() {

        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        // Instantiate SdlRouterService and Registered App class
        SdlRouterService router = new SdlRouterService();
        SdlRouterService.RegisteredApp app = router.new RegisteredApp(APP_ID, 1, messenger);

        // Call Handle Message
        app.handleMessage(TransportConstants.BYTES_TO_SEND_FLAG_LARGE_PACKET_START,bytes, TransportType.BLUETOOTH);

        // Insure that the buffer is not null, if it is the test will fail
        assertNotNull(app.buffer);


    }

    @Test
    public void testNullBuffer() {

        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        // Instantiate SdlRouterService and Registered App class
        SdlRouterService router = new SdlRouterService();
        SdlRouterService.RegisteredApp app = router.new RegisteredApp(APP_ID, 1, messenger);

        // Force Null Buffer
        app.buffer = null;

        // Call Handle Message - Making sure it doesn't init buffer
        app.handleMessage(TransportConstants.BYTES_TO_SEND_FLAG_NONE, bytes, TransportType.BLUETOOTH);

        // Insure that the buffer is null. and no NPE
        assertNull(app.buffer);

    }
}

