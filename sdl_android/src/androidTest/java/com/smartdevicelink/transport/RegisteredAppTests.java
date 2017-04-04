package com.smartdevicelink.transport;

import android.os.Handler;
import android.os.Looper;
import android.os.Messenger;
import android.test.AndroidTestCase;


/**
 * Created by brett on 4/4/17.
 */

public class RegisteredAppTests extends AndroidTestCase {

    private static final String APP_ID = "123451123";


    public void testHandleMessage() {


        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                SdlRouterService router = new SdlRouterService();

                Messenger messenger = null;

                SdlRouterService.RegisteredApp app = router.new RegisteredApp(APP_ID, messenger);

                byte[] bytes = new byte[1];
                app.handleMessage(TransportConstants.BYTES_TO_SEND_FLAG_LARGE_PACKET_START,bytes);
                assertNotNull(app.buffer);
            }
        });
    }
}

