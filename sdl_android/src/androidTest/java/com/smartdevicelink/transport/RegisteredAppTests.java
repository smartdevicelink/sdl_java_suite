package com.smartdevicelink.transport;

import android.os.Handler;
import android.os.Looper;
import android.os.Messenger;
import android.test.AndroidTestCase;


/**
 * Created by brettywhite on 4/4/17.
 */

public class RegisteredAppTests extends AndroidTestCase {

    private static final String APP_ID = "123451123";
    private static final Messenger messenger = null;
    private static byte[] bytes = new byte[1];

    public void testHandleMessage() {

        // Run Test in Main Thread
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                // Instantiate SdlRouterService and Registered App class
                SdlRouterService router = new SdlRouterService();
                SdlRouterService.RegisteredApp app = router.new RegisteredApp(APP_ID, messenger);

                // Call Handle Message
                app.handleMessage(TransportConstants.BYTES_TO_SEND_FLAG_LARGE_PACKET_START,bytes);

                // Insure that the buffer is not null, if it is the test will fail
                assertNotNull(app.buffer);

            }
        });
    }

    public void testNullBuffer() {

        // Run Test in Main Thread
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                // Instantiate SdlRouterService and Registered App class
                SdlRouterService router = new SdlRouterService();
                SdlRouterService.RegisteredApp app = router.new RegisteredApp(APP_ID, messenger);

                // Force Null Buffer
                app.buffer = null;

                // Call Handle Message - Making sure it doesn't init buffer
                app.handleMessage(TransportConstants.BYTES_TO_SEND_FLAG_NONE,bytes);

                // Insure that the buffer is null. and no NPE
                assertNull(app.buffer);

            }
        });
    }
}

