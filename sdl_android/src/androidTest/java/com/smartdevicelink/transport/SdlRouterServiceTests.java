package com.smartdevicelink.transport;


import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import android.test.AndroidTestCase;
import android.util.Log;

import org.junit.Rule;

import java.lang.reflect.Field;
import java.util.concurrent.TimeoutException;

public class SdlRouterServiceTests extends AndroidTestCase {

    private static final String TAG = "SdlRouterServiceTests";
    private SdlRouterService mService;
    @Rule
    private final ServiceTestRule mServiceRule = new ServiceTestRule();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //Bind to SdlRouterService and get an instance to use for subsequent test cases
        //The service is automatically unbound when all tests are completed
        Intent i = new Intent(InstrumentationRegistry.getTargetContext(), SdlRouterService.class);
        i.setAction(TransportConstants.ROUTER_SERVICE_TEST_INTENT_ACTION);
        try {
            IBinder binder = mServiceRule.bindService(i);
            if (binder != null) {
                mService = ((SdlRouterService.LocalBinder) binder).getService();
            }
        } catch (TimeoutException e) {
            Log.v(TAG, "Timeout exception while setting up SdlRouterServiceTests");
            e.printStackTrace();
        }
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
        //Sending a null bundle
        assertFalse(mService.writeBytesToTransport(null));

        //Sending a non-null bundle with null byte array
        //First, set mSerialService to the correct state so we get to test packets being null
        MultiplexBluetoothTransport transport = MultiplexBluetoothTransport.getBluetoothSerialServerInstance(null);
        transport.setStateManually(MultiplexBluetoothTransport.STATE_CONNECTED);
        Field field = null;
        try {
            field = SdlRouterService.class.getDeclaredField("mSerialService");
            field.setAccessible(true);
            field.set(mService, transport);
        } catch (NoSuchFieldException e) {
            Log.v(TAG, "No such field: mSerialService in SdlRouterService class");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.v(TAG, "Error setting mSerialService field");
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putByteArray(TransportConstants.BYTES_TO_SEND_EXTRA_NAME, null);
        assertFalse(mService.writeBytesToTransport(bundle));

        //Return mSerialService to previous state
        try {
            if (field != null) {
                field.set(mService, null);
            }
        } catch (IllegalAccessException e) {
            Log.v(TAG, "Error resetting mSerialService field");
            e.printStackTrace();
        }
    }
}
