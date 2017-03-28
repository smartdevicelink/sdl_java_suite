package com.smartdevicelink.transport;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.test.SdlUnitTestContants;
import com.smartdevicelink.transport.enums.TransportType;

import android.test.AndroidTestCase;

public class MultiplexTransportTest extends AndroidTestCase {

	private static final int TIMEOUT = 2000;
	
	RouterServiceValidator rsvp;
	ITransportListener transportListener;

	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		rsvp = new RouterServiceValidator(this.mContext);
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
	
	
	public void testThreadRecoverability(){
		MultiplexTransportConfig config = new MultiplexTransportConfig(this.mContext,SdlUnitTestContants.TEST_APP_ID);
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
		
		
		
	}
}
