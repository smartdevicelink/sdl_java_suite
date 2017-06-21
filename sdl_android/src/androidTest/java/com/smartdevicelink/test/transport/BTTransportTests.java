package com.smartdevicelink.test.transport;

import android.bluetooth.BluetoothSocket;
import android.test.AndroidTestCase;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.test.util.DeviceUtil;
import com.smartdevicelink.transport.BTTransport;
import com.smartdevicelink.transport.ITransportListener;
import com.smartdevicelink.transport.SdlPsm;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.transport.BTTransport}
 */

public class BTTransportTests extends AndroidTestCase {


    public ITransportListener listener;
    public BTTransport btTransport;

    protected void setUp() throws Exception{
        super.setUp();
        listener = new ITransportListener() {
            @Override
            public void onTransportPacketReceived(SdlPacket packet) {
            }

            @Override
            public void onTransportConnected() {
            }

            @Override
            public void onTransportDisconnected(String info) {
            }

            @Override
            public void onTransportError(String info, Exception e) {
            }
        };
        btTransport = new BTTransport(listener);

    }

    /**
     * This is a unit test for the following methods :
     * {@link com.smartdevicelink.transport.BTTransport#getTransportType()}
     */
    public void testGetBTSocket () {
		if(!DeviceUtil.isEmulator()) {
			Exception exception = null;
			try {
				btTransport.openConnection();
			} catch (Exception e) {
				exception = e;
			}
			assertEquals(exception, null);
		}
       }

       public void testGetNullChannel(){
            assertEquals(-1, btTransport.getChannel(null));
       }

}
