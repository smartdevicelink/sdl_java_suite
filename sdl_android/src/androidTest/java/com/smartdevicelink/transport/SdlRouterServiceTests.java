package com.smartdevicelink.transport;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import android.test.AndroidTestCase;
import android.util.Log;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.enums.FrameType;

import junit.framework.Assert;

import org.junit.Rule;
import org.mockito.Mock;

import java.lang.ref.WeakReference;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SdlRouterServiceTests extends AndroidTestCase {

	public static final String TAG = "SdlRouterServiceTests";
	SdlRouterService mockRouterService;
	Context context;

//	@Mock
//	SdlRouterService annotationMockedRouterService;

	@Rule
	public final ServiceTestRule mServiceRule = new ServiceTestRule();



	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		context = InstrumentationRegistry.getTargetContext();

		//Create mock class
//		mockRouterService = mock(SdlRouterService.class);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		//Nothing here for now
	}

	public void testOnBindChoseCorrectMessenger() throws RemoteException {

		SdlRouterService routerService = new SdlRouterService();
		//Create Mock Intent
		Intent mockIntent = mock(Intent.class);
		//Mock method to return specific type
		when(mockIntent.getAction()).thenReturn(TransportConstants.BIND_REQUEST_TYPE_CLIENT);

		//Call onBind(). Also check binder status for verification.
		IBinder binder = routerService.onBind(mockIntent);
		assertNotNull(binder);

		//check to see received correct messenger
		assertEquals(binder, routerService.routerMessenger.getBinder());
		assertNotSame(binder, routerService.altTransportMessenger.getBinder());
		assertNotSame(binder, routerService.routerStatusMessenger.getBinder());
	}

	public void testOnBindNullIntent() throws RemoteException {

		SdlRouterService routerService = new SdlRouterService();

		IBinder binder = routerService.onBind(null);

		assertNull(binder);
	}

	public void testOnUnbind(){
		Intent randomIntent = new Intent();
		//mock onUnbind() to return true and to not call super.onUnbind()
		SdlRouterService spyRouterService = spy(new SdlRouterService());

		//Note: You can pass in more than just primitives with the any() method
		when(spyRouterService.onUnbind(any(Intent.class))).thenReturn(true);

		//Call onUnbind()
		boolean isSuccess = spyRouterService.onUnbind(randomIntent);

		//Verify method was called
		verify(spyRouterService).onUnbind(randomIntent);

		//Check if desired value was returned
		assertTrue(isSuccess);
	}

	public void testOnPacketRead(){
		//Create spy
		SdlRouterService routerService = new SdlRouterService();
		SdlRouterService spy = spy(routerService);

		//Mock sendPacketToRegisteredApp()
		when(spy.sendPacketToRegisteredApp(any(SdlPacket.class))).thenReturn(true);

		//Create mock Packet
		SdlPacket packet = mock(SdlPacket.class);

		//Mock getVersion()
		when(packet.getVersion()).thenReturn(2);

		//Call onPacketRead()
		spy.onPacketRead(packet);

		//Check to see method was called
		verify(spy).onPacketRead(any(SdlPacket.class));

		assertFalse(spy.isLegacyMode());

		//Make sure sendPacket...() was never called
		verify(spy).sendPacketToRegisteredApp(packet);

		//Alter mock to be a legacy packet
		when(packet.getVersion()).thenReturn(1);
		when(packet.getFrameType()).thenReturn(FrameType.Control);
		when(packet.getFrameInfo()).thenReturn(SdlPacket.FRAME_INFO_START_SERVICE_ACK);

		//Call onPacketRead()
		spy.onPacketRead(packet);

		//Check to see legacy mode on
		assertTrue(spy.isLegacyMode());

	}

	public void testBroadcastReceiversNotNull() {
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		SdlRouterService routerService = new SdlRouterService();
		assertNotNull(routerService.mainServiceReceiver);
		assertNotNull(routerService.mListenForDisconnect);
		assertNotNull(routerService.registerAnInstanceOfSerialServer);
	}

	public void testOnTransportDisconnected(){


	}







	//Testing Broadcast Receivers
//	public void testMainBroadcastReceiver() throws TimeoutException {
//		SdlRouterService routerService = new SdlRouterService();
//
//		Intent registrationIntent = new Intent();
////		registrationIntent.setAction(action);
////		registrationIntent.putExtra(TransportConstants.BIND_LOCATION_PACKAGE_NAME_EXTRA, this.getPackageName());
////		registrationIntent.putExtra(TransportConstants.BIND_LOCATION_CLASS_NAME_EXTRA, this.getClass().getName());
//
//		// 1. Create mock BroadcastReceiver
//		BroadcastReceiver fakeServiceReceiver = mock(BroadcastReceiver.class);
//
//		// 2. Tell fakeServiceReceiver to throw exception if onReceive is called
//		doThrow(new RuntimeException()).when(fakeServiceReceiver).onReceive(context,registrationIntent);
//
//		// 3. Set fakeBR to mocked service
//		routerService.setMainServiceReceiver(fakeServiceReceiver);
//
//		// 4. Mock mockRouterService to have no action when sendBroadcast() is called
//		mServiceRule.startService(new Intent(context, SdlRouterService.class));
////		doNothing().when(routerService).sendBroadcast(any(Intent.class));
//
//		// 5. invoke onReceive()
//		//NEED TO CALL SERVICE to start it
//		fakeServiceReceiver.onReceive(context, registrationIntent); // no context in sendBroadcast() call
//
//		// 6. Check to see if onReceive method was called
//		verify(routerService.mainServiceReceiver).onReceive(context, any(Intent.class));

		// 7. Check to see if the inner method was called
//		verify(routerService).sendBroadcast(registrationIntent);
//	}




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


/////////////
//	@Test
//	public void testBinding() throws TimeoutException {
////		Intent serviceIntent = new Intent(InstrumentationRegistry.getTargetContext(), SdlRouterService.class);
//
////		IBinder binder = mServiceRule.bindService(serviceIntent); //
////		 mServiceRule.startService(serviceIntent);
//
////		SdlRouterService routerService = ((SdlRouterService.LocalBinder)binder).getService();
//
////		assertNotNull(routerService.registerAnInstanceOfSerialServer);
//
//
//	}
//
//	@Test
//	public void testMainBroadcastReceiver() throws TimeoutException {
//		Intent serviceIntent = new Intent(InstrumentationRegistry.getTargetContext(), SdlRouterService.class);
//		serviceIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_CLIENT);
//		IBinder binder = mServiceRule.bindService(serviceIntent);
//
//		SdlRouterService routerService = ((SdlRouterService.LocalBinder)binder).getService();
//
//		doNothing().when(routerService).sendBroadcast(serviceIntent);
//		verify(routerService).onCreate();
//
//		Intent registrationIntent = new Intent();
//		registrationIntent.setAction(SdlRouterService.REGISTER_WITH_ROUTER_ACTION);
//		registrationIntent.putExtra(TransportConstants.BIND_LOCATION_PACKAGE_NAME_EXTRA, mockRouterService.getPackageName());
//		registrationIntent.putExtra(TransportConstants.BIND_LOCATION_CLASS_NAME_EXTRA, mockRouterService.getClass().getName());
//		routerService.mainServiceReceiver.onReceive(InstrumentationRegistry.getTargetContext(), serviceIntent); // no context in sendBroadcast() call
//		verify(routerService).sendBroadcast(serviceIntent);
//	}
//
//	@Test
//	public void testRegisterBroadcastReceiver() throws TimeoutException {
//		Intent serviceIntent = new Intent(InstrumentationRegistry.getTargetContext(), SdlRouterService.class);
//		serviceIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_CLIENT);
//		IBinder binder = mServiceRule.bindService(serviceIntent);
//		SdlRouterService routerService = ((SdlRouterService.LocalBinder)binder).getService();
//
//		Intent disconnectIntent = new Intent(InstrumentationRegistry.getTargetContext(), SdlRouterService.class);
//		disconnectIntent.setAction("android.bluetooth.adapter.action.STATE_CHANGED");
//		disconnectIntent.putExtra("senderintent", "Bar");
//		disconnectIntent.putExtra(SdlBroadcastReceiver.LOCAL_ROUTER_SERVICE_EXTRA, "Bar");
//
////		mockRouterService.registerAnInstanceOfSerialServer.onReceive(context, disconnectIntent); // context
////		assertFalse(isReached);
////		verify(mockRouterService.sdlMultiList, atLeastOnce()).remove(any());
//
//	}
//
//	@Test
//	public void testDisconnectBroadcastReceiver() {
//
//	}
//
//	@Test
//	public void testRouterHandler() throws TimeoutException {
//		Intent serviceIntent = new Intent(InstrumentationRegistry.getTargetContext(), SdlRouterService.class);
//		serviceIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_CLIENT);
//		IBinder binder = mServiceRule.bindService(serviceIntent);
//		SdlRouterService routerService = ((SdlRouterService.LocalBinder)binder).getService();
//
//		Intent requestTypeIntent = new Intent();
//		requestTypeIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_CLIENT);
//
//		SdlRouterService mockRouterService = mock(SdlRouterService.class);
//		mockRouterService.onBind(requestTypeIntent);
//		Message message = Message.obtain(); //Do we need to always obtain new? or can we just swap bundles?
//		message.what = TransportConstants.ROUTER_SEND_PACKET;
//		Bundle bundle = new Bundle();
//		bundle.putInt(TransportConstants.BYTES_TO_SEND_EXTRA_OFFSET, 0);
//		message.setData(bundle);
//
////		verify(mockRouterService.routerMessenger)
//
//	}
//


}










