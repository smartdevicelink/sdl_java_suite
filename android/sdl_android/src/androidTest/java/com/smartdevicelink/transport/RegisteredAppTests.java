package com.smartdevicelink.transport;

import android.os.Looper;
import android.os.Messenger;

import com.smartdevicelink.AndroidTestCase2;


/**
 * Created by brettywhite on 4/4/17.
 */

public class RegisteredAppTests extends AndroidTestCase2 {

    private static final String APP_ID = "123451123";
    private static final Messenger messenger = null;
    private static byte[] bytes = new byte[1];

    public void testHandleMessage() {

        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        // Instantiate SdlRouterService and Registered App class
        BaseRouterService router = new BaseRouterService();
        BaseRouterService.RegisteredApp app = router.new RegisteredApp(APP_ID, messenger);

        // Call Handle Message
        app.handleMessage(TransportConstants.BYTES_TO_SEND_FLAG_LARGE_PACKET_START,bytes);

        // Insure that the buffer is not null, if it is the test will fail
        assertNotNull(app.buffer);


    }

    public void testNullBuffer() {

        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        // Instantiate SdlRouterService and Registered App class
        BaseRouterService router = new BaseRouterService();
        BaseRouterService.RegisteredApp app = router.new RegisteredApp(APP_ID, messenger);

        // Force Null Buffer
        app.buffer = null;

        // Call Handle Message - Making sure it doesn't init buffer
        app.handleMessage(TransportConstants.BYTES_TO_SEND_FLAG_NONE,bytes);

        // Insure that the buffer is null. and no NPE
        assertNull(app.buffer);

    }
}

