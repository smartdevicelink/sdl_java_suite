package com.smartdevicelink.transport;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.test.SdlUnitTestContants;
import com.smartdevicelink.test.util.DeviceUtil;

public class TransportBrokerTest extends AndroidTestCase2 {
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
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		TransportBroker broker = new TransportBroker(mContext, SdlUnitTestContants.TEST_APP_ID,rsvp.getService());
		if(!DeviceUtil.isEmulator()){ // Cannot perform MBT operations in emulator
			assertTrue(broker.start());
		}
		broker.stop();

	}
	
	public void testSendPacket(){
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}

		TransportBroker broker = new TransportBroker(mContext, SdlUnitTestContants.TEST_APP_ID,rsvp.getService());

		if(!DeviceUtil.isEmulator()){ // Cannot perform MBT operations in emulator
			assertTrue(broker.start());
		}
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if(!DeviceUtil.isEmulator()){ // Cannot perform BT adapter operations in emulator
			assertNotNull(adapter);
			assertTrue(adapter.isEnabled());
		}
		//Not ideal, but not implementing callbacks just for unit tests
		int count = 0;
		while(broker.routerServiceMessenger == null && count<10){
			sleep();
			count++;
		}
		if(!DeviceUtil.isEmulator()){ // Cannot perform BT adapter operations in emulator
			assertNotNull(broker.routerServiceMessenger);
		}

		//assertFalse(broker.sendPacketToRouterService(null, 0, 0));
		//assertFalse(broker.sendPacketToRouterService(new byte[3], -1, 0));
		//assertFalse(broker.sendPacketToRouterService(new byte[3], 0, 4));
		//assertTrue(broker.sendPacketToRouterService(new byte[3],0, 3));

		broker.stop();

	}
	
	public void testOnPacketReceived(){
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		TransportBroker broker = new TransportBroker(mContext, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
		if(!DeviceUtil.isEmulator()){ // Cannot perform MBT operations in emulator
			assertTrue(broker.start());
		}

	}
	
	public void testSendMessageToRouterService(){
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}

		TransportBroker broker = new TransportBroker(mContext, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
		Handler handler = new Handler();
		Message message = new Message();
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
