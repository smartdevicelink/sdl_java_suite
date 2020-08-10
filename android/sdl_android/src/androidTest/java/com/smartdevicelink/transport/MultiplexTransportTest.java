package com.smartdevicelink.transport;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.test.runner.AndroidJUnit4;

import com.smartdevicelink.protocol.SdlPacket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MultiplexTransportTest {

	RouterServiceValidator rsvp;
	ITransportListener transportListener;

	
	@Before
	public void setUp() throws Exception {
		rsvp = new RouterServiceValidator(getTargetContext());
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_NONE);
		rsvp.validate();
		
		transportListener = new ITransportListener(){

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
		
	}
	// test for setting error state.
	@Test
	public void testSetState() {
		MultiplexBluetoothTransport btTransport = new MultiplexBluetoothTransport(new Handler(Looper.getMainLooper()) {
			@Override
			public void handleMessage(Message message) {
				assertNotNull(message);
				if (message.arg1 == MultiplexBaseTransport.STATE_ERROR) {
					assertNotNull(message.getData());
					assertEquals(MultiplexBaseTransport.REASON_SPP_ERROR, message.getData().getByte(MultiplexBaseTransport.ERROR_REASON_KEY));
				} else {
					//It will first listen before the error state
					assertEquals(MultiplexBaseTransport.STATE_LISTEN, message.arg1);
				}
			}
		});
		btTransport.start();
		final Bundle bundle = new Bundle();
		bundle.putByte(MultiplexBaseTransport.ERROR_REASON_KEY, MultiplexBaseTransport.REASON_SPP_ERROR);
		btTransport.setState(MultiplexBaseTransport.STATE_ERROR, bundle);
	}
}
