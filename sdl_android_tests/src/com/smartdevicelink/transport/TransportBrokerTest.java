package com.smartdevicelink.transport;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.test.AndroidTestCase;

import com.smartdevicelink.test.SdlUnitTestContants;

public class TransportBrokerTest extends AndroidTestCase {
	RouterServiceValidator rsvp;
	//		public TransportBrokerThread(Context context, String appId, ComponentName service){

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		rsvp = new RouterServiceValidator(this.mContext);
		rsvp.validate();
		
	}
	
	private void sleep(){
		try{
			Thread.sleep(500);
		}catch(Exception e){}
	}
	
	public void testStart(){
		TransportBroker broker = new TransportBroker(mContext, SdlUnitTestContants.TEST_APP_ID,rsvp.getService());
		assertTrue(broker.start());
		broker.stop();
	}
	
	public void testSendPacket(){
		TransportBroker broker = new TransportBroker(mContext, SdlUnitTestContants.TEST_APP_ID,rsvp.getService());
		assertTrue(broker.start());
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		assertNotNull(adapter);
		assertTrue(adapter.isEnabled());
		
		//Not ideal, but not implementing callbacks just for unit tests
		int count = 0;
		while(broker.routerServiceMessenger == null && count<10){
			sleep();
			count++;
		}
		assertNotNull(broker.routerServiceMessenger);
		
		
		//assertFalse(broker.sendPacketToRouterService(null, 0, 0));
		//assertFalse(broker.sendPacketToRouterService(new byte[3], -1, 0));
		//assertFalse(broker.sendPacketToRouterService(new byte[3], 0, 4));
		//assertTrue(broker.sendPacketToRouterService(new byte[3],0, 3));
		
		broker.stop();
	}
	
	public void testOnPacketReceived(){
		TransportBroker broker = new TransportBroker(mContext, SdlUnitTestContants.TEST_APP_ID,rsvp.getService());
		assertTrue(broker.start());
	}
	
	public void testSendMessageToRouterService(){
		TransportBroker broker = new TransportBroker(mContext, SdlUnitTestContants.TEST_APP_ID,rsvp.getService());
		Handler handler = new Handler();
		Message message =  new Message();
		broker.routerServiceMessenger = null;
		broker.isBound = true;
		assertFalse(broker.sendMessageToRouterService(message));
		
		broker.routerServiceMessenger = new Messenger(handler); //So it's not ambiguous
		broker.isBound = false;
		assertFalse(broker.sendMessageToRouterService(message));
		
		broker.isBound = true;
		broker.registeredWithRouterService = true;
		
		message = null;
		assertFalse(broker.sendMessageToRouterService(message));
		
		message = new Message();
		
		assertTrue(broker.sendMessageToRouterService(message));
		

		
		
		
	}
	
	

}
