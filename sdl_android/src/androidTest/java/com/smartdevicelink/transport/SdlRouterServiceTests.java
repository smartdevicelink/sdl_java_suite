package com.smartdevicelink.transport;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.enums.FrameType;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.ref.WeakReference;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class SdlRouterServiceTests {

	public static final String TAG = "SdlRouterServiceTests";
	Context context= InstrumentationRegistry.getTargetContext();

	@Rule
	public final ServiceTestRule mServiceRule = new ServiceTestRule();

	@After
	public void finish(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			Looper.myLooper().quitSafely();
		} else {
			Looper.myLooper().quit();
		}

	}


	@Test
	public void testOnPacketRead(){
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
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


	@Test
	public void testOnBindChoseCorrectMessenger() throws RemoteException {
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
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


	@Test
	public void testOnBindNullIntent() throws RemoteException {
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		SdlRouterService routerService = new SdlRouterService();

		IBinder binder = routerService.onBind(null);

		assertNull(binder);
	}


	@Test
	public void testOnUnbind(){
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
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



	@Test
	public void testBroadcastReceiversNotNull() {
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		SdlRouterService routerService = new SdlRouterService();
		assertNotNull(routerService.mainServiceReceiver);
		assertNotNull(routerService.mListenForDisconnect);
		assertNotNull(routerService.registerAnInstanceOfSerialServer);
	}


	@Test
	public void testShouldServiceRemainOpen(){
		//Create spy
		SdlRouterService spyRouterService = spy(new SdlRouterService());
		//Create mock with custom Answer
		Intent customIntent = mock(Intent.class, new Answer() {
			boolean firstTime = true;
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				if(firstTime) {
					firstTime = false;
					return null;
				}else{
					return "BIND_REQUEST_TYPE_ALT_TRANSPORT";
				}
			}
		});
		//mock out inner method
		when(spyRouterService.bluetoothAvailable()).thenReturn(false);
		//mock out void method
		doNothing().when(spyRouterService).closeSelf();
		boolean result = spyRouterService.shouldServiceRemainOpen(customIntent);
		//assert to see bluetooth was unavailable
		assertFalse(result);
		//Call shouldServiceRemainOpen() again
		result = spyRouterService.shouldServiceRemainOpen(customIntent);

		assertTrue(result);
		result = spyRouterService.shouldServiceRemainOpen(customIntent);
		assertTrue(result);
		verify(spyRouterService, times(1)).closeSelf();
	}




	/**
	 * Test null bundle handling in AltTransportHandler when handling messages. Only test the case of
	 * msg.what == TransportConstants.ROUTER_RECEIVED_PACKET
	 */
	@Test
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


}










