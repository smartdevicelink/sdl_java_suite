package com.smartdevicelink.transport;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.test.AndroidTestCase;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.test.SdlUnitTestContants;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.ByteArrayMessageAssembler;

import org.junit.Rule;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Testing isolated methods
 */
public class TransportBrokerTest extends AndroidTestCase {
	RouterServiceValidator rsvp;
	TransportBrokerThread brokerThread;
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

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}



	public void testOnServiceUnregisteredFromRouterService()throws Exception{
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		TransportBroker broker = new TransportBroker(mContext, SdlUnitTestContants.TEST_APP_ID,rsvp.getService());
		broker.onServiceUnregisteredFromRouterService(0);

		Field queuedOnTransportConnect = TransportBroker.class.getDeclaredField("queuedOnTransportConnect");
		queuedOnTransportConnect.setAccessible(true);
		TransportType type = (TransportType) queuedOnTransportConnect.get(broker);

		assertNull(type);
	}




	public void testOnHardwareConnected() throws Exception{
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		TransportBroker broker = new TransportBroker(mContext, SdlUnitTestContants.TEST_APP_ID,rsvp.getService());
		broker.onHardwareConnected(TransportType.BLUETOOTH);

		Field queuedOnTransportConnect = TransportBroker.class.getDeclaredField("queuedOnTransportConnect");
		queuedOnTransportConnect.setAccessible(true);
		TransportType type = (TransportType) queuedOnTransportConnect.get(broker);

		assertEquals(type, TransportType.BLUETOOTH);
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


	public void testTBHandlerRegistrationResponseSuccess() throws Exception{
		final Message message = new Message();
		message.what = TransportConstants.ROUTER_REGISTER_CLIENT_RESPONSE;
		message.arg1 = TransportConstants.REGISTRATION_RESPONSE_SUCESS;
		Bundle bundle = new Bundle();
		bundle.putBoolean(TransportConstants.ENABLE_LEGACY_MODE_EXTRA, true);
		bundle.putInt(TransportConstants.ROUTER_SERVICE_VERSION, 1);
		message.setData(bundle);
		brokerThread = new TransportBrokerThread(context, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
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
		brokerThread = new TransportBrokerThread(context, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
		assertNull(brokerThread.broker);

		try {
			while(brokerThread.broker==null) {} // wait for thread to finish instantiation
			brokerThread.broker.clientMessenger.send(message);
			while(!brokerThread.broker.getLegacyModeEnabled()) {} // wait for handle to process switch case
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
		brokerThread = new TransportBrokerThread(context, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
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
		brokerThread = new TransportBrokerThread(context, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
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
		brokerThread = new TransportBrokerThread(context, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
		assertNull(brokerThread.broker);

		try {
			while(brokerThread.broker==null) {} // wait for thread to finish instantiation
			brokerThread.broker.clientMessenger.send(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		assertFalse(brokerThread.broker.registeredWithRouterService);
	}


	public void testTBHandlerRouterReceivedPacketWithByteToSendExtra() throws Exception{
		final Message message = new Message();
		message.what = TransportConstants.ROUTER_RECEIVED_PACKET;
		Bundle bundle = new Bundle();
		SdlPacket packet = new SdlPacket(1,false,1,1,0,0,0,0,null,0,0);
		bundle.putParcelable(TransportConstants.BYTES_TO_SEND_EXTRA_NAME, packet);
		bundle.putInt(TransportConstants.BYTES_TO_SEND_FLAGS, TransportConstants.BYTES_TO_SEND_FLAG_SDL_PACKET_INCLUDED);
		message.setData(bundle);

		brokerThread = new TransportBrokerThread(context, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
		assertNull(brokerThread.broker);

		try {
			while(brokerThread.broker==null) {} // wait for thread to finish instantiation
			brokerThread.broker.clientMessenger.send(message);
			int count = 0;
			while(brokerThread.getBufferedPacket()==null && count<10){
				sleep();
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNull( brokerThread.getBufferedPacket());
		assertNull(brokerThread.getBufferedPayloadAssembler());
		assertFalse(brokerThread.broker.registeredWithRouterService);

	}


	public void testTBHandlerRouterReceivedPacketWithFormatPacketExtra() throws Exception{
		final Message message = new Message();
		message.what = TransportConstants.ROUTER_RECEIVED_PACKET;

		Bundle bundle = new Bundle();
		SdlPacket packet = new SdlPacket(1,false,1,1,0,0,0,0,null,0,0);
		bundle.putParcelable(TransportConstants.FORMED_PACKET_EXTRA_NAME, packet);
		bundle.putInt(TransportConstants.BYTES_TO_SEND_FLAGS, TransportConstants.BYTES_TO_SEND_FLAG_SDL_PACKET_INCLUDED);
		bundle.putBoolean(TransportConstants.HARDWARE_CONNECTED, true);
		message.setData(bundle);

		brokerThread = new TransportBrokerThread(context, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
		assertNull(brokerThread.broker);

		try {
			while(brokerThread.broker==null) {} // wait for thread to finish instantiation
			brokerThread.broker.clientMessenger.send(message);
			int count = 0;
			while(brokerThread.getBufferedPacket()==null && count<10){
				sleep();
				count++;
				}
			} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(packet, brokerThread.getBufferedPacket());
		assertFalse(brokerThread.broker.registeredWithRouterService);
	}

	public void testTBHandlerHardwareConnectionEvent() throws Exception {
		final Message message = new Message();
		message.what = TransportConstants.HARDWARE_CONNECTION_EVENT;
		Bundle bundle = new Bundle();
		bundle.putString(TransportConstants.HARDWARE_CONNECTED, TransportType.BLUETOOTH.name());
		message.setData(bundle);
		brokerThread = new TransportBrokerThread(context, SdlUnitTestContants.TEST_APP_ID, rsvp.getService());
		assertNull(brokerThread.broker);

		try {
			while(brokerThread.broker==null) {} // wait for thread to finish instantiation
			brokerThread.broker.clientMessenger.send(message);
			int count = 0;
			while(brokerThread.getQueuedOnTransportConnect()==null && count<10){
				sleep();
				count++;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		assertEquals(TransportType.BLUETOOTH, brokerThread.getQueuedOnTransportConnect());
		assertFalse(brokerThread.broker.registeredWithRouterService);
	}


	public void testStart() throws Exception{
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		TransportBroker broker = new TransportBroker(mContext, SdlUnitTestContants.TEST_APP_ID,rsvp.getService());
		broker.start();
		broker.stop();
		broker.resetSession();

		Field queuedOnTransportConnect = TransportBroker.class.getDeclaredField("queuedOnTransportConnect");
		queuedOnTransportConnect.setAccessible(true);
		TransportType type = (TransportType) queuedOnTransportConnect.get(broker);

		assertNull(type);
		assertNull(broker.routerServiceMessenger);
	}


	public void testRequestNewSession(){
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		TransportBroker broker = spy(new TransportBroker(mContext, SdlUnitTestContants.TEST_APP_ID, rsvp.getService()));
		broker.requestNewSession();

		ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);
		verify(broker).sendMessageToRouterService(argument.capture());
		assertEquals(TransportConstants.ROUTER_REQUEST_NEW_SESSION, argument.getValue().what);
	}

	public void testRemoveSession(){
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		TransportBroker broker = spy(new TransportBroker(mContext, SdlUnitTestContants.TEST_APP_ID, rsvp.getService()));
		broker.removeSession(0);

		ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);
		verify(broker).sendMessageToRouterService(argument.capture());
		assertEquals(TransportConstants.ROUTER_REMOVE_SESSION, argument.getValue().what);

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
		TransportType queuedOnTransportConnect;


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
					broker = new TransportBroker(context, appId, service);
				}
			}
			threadLooper = Looper.myLooper();
			Looper.loop();
		}


		@SuppressLint("NewApi")
		public synchronized void cancel(){
			if(broker!=null){
				broker.stop();
				broker = null;
			}
			if(threadLooper !=null){
				if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2){
					threadLooper.quitSafely();
				}else{
					threadLooper.quit();
				}
				threadLooper = null;
			}

		}

		public ByteArrayMessageAssembler getBufferedPayloadAssembler() throws Exception{
			Field bufferedPayloadAssembler = TransportBroker.class.getDeclaredField("bufferedPayloadAssembler");
			bufferedPayloadAssembler.setAccessible(true);
			ByteArrayMessageAssembler assembler = (ByteArrayMessageAssembler) bufferedPayloadAssembler.get(broker);
			return assembler;
		}

		public SdlPacket getBufferedPacket() throws Exception{
			Field bufferedPacket = TransportBroker.class.getDeclaredField("bufferedPacket");
			bufferedPacket.setAccessible(true);
			SdlPacket packet = (SdlPacket) bufferedPacket.get(broker);
			return packet;
		}

		public TransportType getQueuedOnTransportConnect() throws Exception{
			Field transportConnect = TransportBroker.class.getDeclaredField("queuedOnTransportConnect");
			transportConnect.setAccessible(true);
			TransportType transportType = (TransportType) transportConnect.get(broker);
			return transportType;
		}

	}


}
