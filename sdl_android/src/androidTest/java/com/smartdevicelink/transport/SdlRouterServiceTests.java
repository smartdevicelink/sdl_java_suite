package com.smartdevicelink.transport;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.test.AndroidTestCase;
import android.util.Log;

import com.smartdevicelink.protocol.SdlPacket;

import junit.framework.Assert;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SdlRouterServiceTests extends AndroidTestCase {

    public static final String TAG = "SdlRouterServiceTests";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        //Nothing here for now
    }
    
    /**
     * Test writeBytesToTransport method for handling null byte array in bundle
     *
     * @see SdlRouterService#writeBytesToTransport(Bundle)
     */
    public void testWriteBytesToTransport() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        Method method;
        Field field = null;
        Object sdlRouterService = null;
        try {
            sdlRouterService = Class.forName("com.smartdevicelink.transport.SdlRouterService").newInstance();
            //Send a null bundle
            method = SdlRouterService.class.getDeclaredMethod("writeBytesToTransport", Bundle.class);
            Bundle bundle = null;
            method.invoke(sdlRouterService, bundle);

            //Send a non-null bundle with a null bytes array
            //First, set mSerialService to the correct state so we get to test packet being null
            MultiplexBluetoothTransport transport = MultiplexBluetoothTransport.getBluetoothSerialServerInstance(null);
            transport.setStateManually(MultiplexBluetoothTransport.STATE_CONNECTED);
            field = SdlRouterService.class.getDeclaredField("mSerialService");
            field.setAccessible(true);
            field.set(sdlRouterService, transport);
            bundle = new Bundle();
            bundle.putByteArray(TransportConstants.BYTES_TO_SEND_EXTRA_NAME, null);
            method.invoke(sdlRouterService, bundle);
        } catch (Exception e) {
            Assert.fail("Exception in testWriteBytesToTransport, " + e);
        }

        //Return mSerialService to previous state
        if (field != null && sdlRouterService != null) {
            try {
                field.set(sdlRouterService, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
