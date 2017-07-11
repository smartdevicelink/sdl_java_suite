package com.smartdevicelink.transport;

import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.test.AndroidTestCase;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.test.SdlUnitTestContants;
import com.smartdevicelink.test.util.DeviceUtil;

import org.junit.Rule;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransportBrokerTest extends AndroidTestCase {
	RouterServiceValidator rsvp;
	//		public TransportBrokerThread(Context context, String appId, ComponentName service){

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	Context context;


	@Override
	protected void setUp() throws Exception {
		super.setUp();
		rsvp = new RouterServiceValidator(this.mContext);
		rsvp.validate();
		context = InstrumentationRegistry.getTargetContext();
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
		broker.resetSession();
		assertFalse(broker.isBound);

	}



	public void testSendMultipleSizePacketsToService(){  
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}

		SdlPacket spyPacket = spy(SdlPacket.class);
		when(spyPacket.constructPacket()).thenReturn(new byte[1000001]);

		ComponentName componentName = new ComponentName("","");
		TransportBroker transportBroker = new TransportBroker(context, "appId", componentName);
		IBinder mockBinder = mock(IBinder.class);
		transportBroker.routerServiceMessenger = new Messenger(mockBinder);

		boolean result = transportBroker.sendPacketToRouterService(spyPacket);
		assertTrue(result);

		when(spyPacket.constructPacket()).thenReturn(new byte[10001]);
		result = transportBroker.sendPacketToRouterService(spyPacket);
		assertTrue(result);
	}


	public void testTBHandlerRegistrationResponseSuccess(){
		final Message message = new Message();
		message.what = TransportConstants.ROUTER_REGISTER_CLIENT_RESPONSE;
		message.arg1 = TransportConstants.REGISTRATION_RESPONSE_SUCESS;
		Bundle bundle = new Bundle();
		bundle.putBoolean(TransportConstants.ENABLE_LEGACY_MODE_EXTRA, true);
		bundle.putBoolean(TransportConstants.HARDWARE_CONNECTED, true);
		bundle.putInt(TransportConstants.ROUTER_SERVICE_VERSION, 1);
		message.setData(bundle);
		TransportBrokerThread brokerThread = new TransportBrokerThread(context, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
		assertNull(brokerThread.broker);

		try {
			while(brokerThread.broker==null) {} // wait for thread to finish instantiation
			brokerThread.broker.clientMessenger.send(message);
			while(!brokerThread.broker.registeredWithRouterService) {} // wait for handle to process switch case
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		assertTrue(brokerThread.broker.registeredWithRouterService);
	}


	public void testTBHandlerRegistrationResponseDeniedLegacyModeEnabled(){
		final Message message = new Message();
		message.what = TransportConstants.ROUTER_REGISTER_CLIENT_RESPONSE;
		message.arg1 = TransportConstants.REGISTRATION_RESPONSE_DENIED_LEGACY_MODE_ENABLED;
		TransportBrokerThread brokerThread = new TransportBrokerThread(context, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
		assertNull(brokerThread.broker);

		try {
			while(brokerThread.broker==null) {} // wait for thread to finish instantiation
//			SystemClock.sleep(800);
			brokerThread.broker.clientMessenger.send(message);
			while(!brokerThread.broker.getLegacyModeEnabled()) {} // wait for handle to process switch case
//			SystemClock.sleep(800);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		assertFalse(brokerThread.broker.registeredWithRouterService);
		assertTrue(brokerThread.broker.getLegacyModeEnabled());
	}

	public void testTBHandlerRegistrationResponseDefault(){
		final Message message = new Message();
		message.what = TransportConstants.ROUTER_REGISTER_CLIENT_RESPONSE;
		message.arg1 = TransportConstants.REGISTRATION_RESPONSE_DENIED_UNKNOWN;
		TransportBrokerThread brokerThread = new TransportBrokerThread(context, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
		assertNull(brokerThread.broker);

		try {
			while(brokerThread.broker==null) {} // wait for thread to finish instantiation
			brokerThread.broker.registeredWithRouterService = true;
			brokerThread.broker.clientMessenger.send(message);
			while(brokerThread.broker.registeredWithRouterService) {} // wait for handle to process switch case
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		assertFalse(brokerThread.broker.registeredWithRouterService);
	}



	public void testTBHandlerUnregisterClientResponse(){
		final Message message = new Message();
		message.what = TransportConstants.ROUTER_UNREGISTER_CLIENT_RESPONSE;
		message.arg1 = TransportConstants.UNREGISTRATION_RESPONSE_SUCESS;
		TransportBrokerThread brokerThread = new TransportBrokerThread(context, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
		assertNull(brokerThread.broker);

		try {
			while(brokerThread.broker==null) {} // wait for thread to finish instantiation
			brokerThread.broker.clientMessenger.send(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		assertFalse(brokerThread.broker.registeredWithRouterService);
	}

	public void testTBHandlerUnregisterClientResponseNotSuccess() {
		final Message message = new Message();
		message.what = TransportConstants.ROUTER_UNREGISTER_CLIENT_RESPONSE;
		message.arg1 = TransportConstants.UNREGISTRATION_RESPONSE_FAILED_APP_ID_NOT_FOUND;
		TransportBrokerThread brokerThread = new TransportBrokerThread(context, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
		assertNull(brokerThread.broker);

		try {
			while(brokerThread.broker==null) {} // wait for thread to finish instantiation
			brokerThread.broker.clientMessenger.send(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		assertFalse(brokerThread.broker.registeredWithRouterService);
	}

	public void testTBHandlerRouterReceivedPacket() {
		final Message message = new Message();
		message.what = TransportConstants.ROUTER_RECEIVED_PACKET;

		Bundle bundle = new Bundle();
		SdlPacket packet = new SdlPacket(1,false,1,1,0,0,0,0,null,0,0);
		bundle.putParcelable(TransportConstants.FORMED_PACKET_EXTRA_NAME, packet);
		message.setData(bundle);

		TransportBrokerThread brokerThread = new TransportBrokerThread(context, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
		assertNull(brokerThread.broker);

		try {
			while(brokerThread.broker==null) {} // wait for thread to finish instantiation
			brokerThread.broker.clientMessenger.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertFalse(brokerThread.broker.registeredWithRouterService);
	}


		///////////////////////////////////////////End mock testing///////////////////////////////////


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

	public void testConvertAppId(){
		assertEquals(Long.valueOf(234234), TransportBroker.convertAppId("234234"));
		assertEquals(Long.valueOf(-1L), TransportBroker.convertAppId(null));
	}



	class TransportBrokerThread extends Thread{
		TransportBroker broker;
		final Context context;
		final String appId;
		final ComponentName service;
		Looper threadLooper = null;
		Thread runner;
		/**
		 * Thread will automatically start to prepare its looper.
		 * @param context
		 * @param appId
		 */
		public TransportBrokerThread(Context context, String appId, ComponentName service){
			super();
			this.context = context;
			this.appId = appId;
			this.service = service;
			this.runner = new Thread(this);
			this.runner.start();
		}

		@Override
		public void run() {
			Looper.prepare();
			if (broker == null) {
				synchronized (this) {
					broker = spy(new TransportBroker(context, appId, service));
				}
			}
			threadLooper = Looper.myLooper();
			Looper.loop();
		}
	}



}
