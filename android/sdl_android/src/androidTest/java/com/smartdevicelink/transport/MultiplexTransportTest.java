package com.smartdevicelink.transport;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.test.runner.AndroidJUnit4;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.test.SdlUnitTestContants;
import com.smartdevicelink.transport.enums.TransportType;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MultiplexTransportTest {

	private static final int TIMEOUT = 2000;
	
	RouterServiceValidator rsvp;
	ITransportListener transportListener;

	
	@Before
	public void setUp() throws Exception {
		rsvp = new RouterServiceValidator(getContext());
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
	
	@Test
	public void testThreadRecoverability(){
		MultiplexTransportConfig config = new MultiplexTransportConfig(getContext(),SdlUnitTestContants.TEST_APP_ID);
		//	public MultiplexTransport(MultiplexTransportConfig transportConfig, final ITransportListener transportListener){
		MultiplexTransport trans = new MultiplexTransport(config,transportListener);
		
		assertTrue(trans.brokerThread.isAlive());
		if(trans.brokerThread.broker==null){
			synchronized(trans.brokerThread){
				try {
					trans.brokerThread.wait(TIMEOUT);;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		assertNotNull(trans.brokerThread.broker);
		//Force this to true so we can test thread 
		trans.brokerThread.connected = true;
		trans.brokerThread.broker.onHardwareDisconnected(TransportType.BLUETOOTH);
		
		assertNull(trans.brokerThread);
		
		trans = new MultiplexTransport(config,transportListener);
		assertTrue(trans.brokerThread.isAlive());

		// Send a null config object in the constructor and expect an IllegalArgumentException
		try {
			trans = new MultiplexTransport(null, transportListener);
		} catch (IllegalArgumentException e) {
			assertEquals("Null transportConfig in MultiplexTransport constructor", e.getMessage());
		} catch (NullPointerException e) {
			Assert.fail("NPE in MultiplexTransport constructor");
		}
	}

	// test for setting error state.
	public void testSetState() {
		final Bundle bundle = new Bundle();
		MultiplexBluetoothTransport btTransport = new MultiplexBluetoothTransport(new Handler(Looper.getMainLooper()) {
			@Override
			public void handleMessage(Message message) {
				assertTrue(message.getData().equals(bundle));
			}
		});
		btTransport.start();
		bundle.putByte(MultiplexBaseTransport.ERROR_REASON_KEY, MultiplexBaseTransport.REASON_SPP_ERROR);
		btTransport.setState(MultiplexBaseTransport.STATE_ERROR, bundle);
	}
}
