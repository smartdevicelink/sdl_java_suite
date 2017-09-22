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
     * Test null bundle handling in AltTransportHandler when handling messages. Only test the case of
     * msg.what == TransportConstants.ROUTER_RECEIVED_PACKET
     */
    public void testAlTransportHandlerHandleNullBundle() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        class AltTransportHandler extends Handler {
            ClassLoader loader;
            final WeakReference<SdlRouterService> provider;

            public AltTransportHandler(SdlRouterService provider) {
                this.provider = new WeakReference<SdlRouterService>(provider);
                loader = getClass().getClassLoader();
            }

            @Override
            public void handleMessage(Message msg) {
                SdlRouterService service = this.provider.get();
                Bundle receivedBundle = msg.getData();
                switch (msg.what) {
                    case TransportConstants.ROUTER_RECEIVED_PACKET:
                        if (receivedBundle != null) {
                            receivedBundle.setClassLoader(loader);//We do this because loading a custom parceable object isn't possible without it
                            if (receivedBundle.containsKey(TransportConstants.FORMED_PACKET_EXTRA_NAME)) {
                                SdlPacket packet = receivedBundle.getParcelable(TransportConstants.FORMED_PACKET_EXTRA_NAME);
                                if (packet != null && service != null) {
                                    service.onPacketRead(packet);
                                } else {
                                    Log.w(TAG, "Received null packet from alt transport service");
                                }
                            } else {
                                Log.w(TAG, "Flase positive packet reception");
                            }
                        } else {
                            Log.e(TAG, "Bundle was null while sending packet to router service from alt transport");
                        }
                        break;
                    default:
                        super.handleMessage(msg);
                }

            }
        }
        AltTransportHandler testHandler = new AltTransportHandler(null);
        Message msg = Message.obtain(null, TransportConstants.ROUTER_RECEIVED_PACKET);
        //Send a null bundle
        msg.setData(null);
        try {
            testHandler.handleMessage(msg);
        } catch (Exception e) {
            Assert.fail("Exception in testAlTransportHandlerHandleNullBundle, " + e);
        }
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
            MultiplexBluetoothTransport transport = new MultiplexBluetoothTransport(null);
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
