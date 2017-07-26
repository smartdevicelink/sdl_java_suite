package com.smartdevicelink.transport;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.smartdevicelink.test.SdlUnitTestContants;
import com.smartdevicelink.transport.enums.TransportType;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Testing the connection workflow of Transport Broker
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class TransportBrokerConnectionTests {


	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	RouterServiceValidator rsvp;
	IBinder binder;
	Intent serviceIntent;
	TransportBrokerThread brokerThread;

	@Rule
	public final ServiceTestRule mServiceRule = new ServiceTestRule();

	@Test
	public void testBindingWithNoAppId() throws TimeoutException, NoSuchFieldException, IllegalAccessException, RemoteException {
		initRouterServiceParts();
		while(brokerThread.broker==null){}

		brokerThread.setAppIdToEmpty();

		brokerThread.callOnServiceConnectedWithBinder(binder);

		assertFalse(brokerThread.broker.registeredWithRouterService);
	}

	@Test
	public void testRequestNewSession() throws TimeoutException, NoSuchFieldException, IllegalAccessException, RemoteException {
//		initRouterServiceParts();
		serviceIntent = new Intent(InstrumentationRegistry.getTargetContext(), SdlRouterService.class);
		rsvp = new RouterServiceValidator(InstrumentationRegistry.getTargetContext());
		rsvp.validate();
		serviceIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_CLIENT);
		mServiceRule.startService(serviceIntent);
		int it = 0;
		while((binder = mServiceRule.bindService(serviceIntent)) == null && it < 100){
			it++;
		}
		brokerThread = new TransportBrokerThread(InstrumentationRegistry.getTargetContext(), SdlUnitTestContants.TEST_APP_ID, rsvp.getService());

		while(brokerThread.broker==null) {} // wait for thread to finish instantiation
		brokerThread.callOnServiceConnectedWithBinder(binder);

		brokerThread.requestNewSession();

		assertTrue(brokerThread.broker.isBound);

	}



	@Test
	public void testUnregisterWithRouterService() throws TimeoutException, NoSuchFieldException, IllegalAccessException, RemoteException {
		serviceIntent = new Intent(InstrumentationRegistry.getTargetContext(), SdlRouterService.class);
		rsvp = new RouterServiceValidator(InstrumentationRegistry.getTargetContext());
		rsvp.validate();
		serviceIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_CLIENT);
		mServiceRule.startService(serviceIntent);
		int it = 0;
		while((binder = mServiceRule.bindService(serviceIntent)) == null && it < 100){
			it++;
		}
		brokerThread = new TransportBrokerThread(InstrumentationRegistry.getTargetContext(), SdlUnitTestContants.TEST_APP_ID, rsvp.getService());

		while(brokerThread.broker==null){}
		brokerThread.callOnServiceConnectedWithBinder(binder);

		brokerThread.callStop();

//		assertFalse(brokerThread.broker.isBound); since broker's unregister switch case does nothing, not sure what to assert

	}


	private void initRouterServiceParts() throws TimeoutException {
		serviceIntent = new Intent(InstrumentationRegistry.getTargetContext(), SdlRouterService.class);
		rsvp = new RouterServiceValidator(InstrumentationRegistry.getTargetContext());
		rsvp.validate();
		serviceIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_CLIENT);
		mServiceRule.startService(serviceIntent);
		int it = 0;
		while((binder = mServiceRule.bindService(serviceIntent)) == null && it < 100){
			it++;
		}
		brokerThread = new TransportBrokerThread(InstrumentationRegistry.getTargetContext(), SdlUnitTestContants.TEST_APP_ID, rsvp.getService());

	}


	class TransportBrokerThread extends Thread{
		TransportBroker broker;
		final Context context;
		final String appId;
		final ComponentName service;
		Looper threadLooper = null;
		Thread runner;
		TransportType queuedOnTransportConnect;

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

		public void setAppIdToEmpty() throws NoSuchFieldException, IllegalAccessException {
			Field appIdField = TransportBroker.class.getDeclaredField("appId");
			appIdField.setAccessible(true);
			appIdField.set(broker, "");
		}

		public void callOnServiceConnectedWithBinder(IBinder binder) throws NoSuchFieldException, IllegalAccessException {
			ComponentName routerServiceName = new ComponentName("com.smartdevicelink.transport", "com.smartdevicelink.transport.SdlRouterService");

			Field servConnectionField = TransportBroker.class.getDeclaredField("routerConnection");
			servConnectionField.setAccessible(true);
			ServiceConnection serviceConnection = (ServiceConnection) servConnectionField.get(broker);

			SdlRouterService.registeredApps = new HashMap<>();
			broker.registeredWithRouterService = true;
			serviceConnection.onServiceConnected(routerServiceName, binder);
			int i = 0;
			while(i<2){
				sleep();
				i++;
			}
		}

		public void callStop() throws NoSuchFieldException, IllegalAccessException {
			SdlRouterService.registeredApps = new HashMap<>();
			broker.stop();
			int i = 0;
			while(i<2){
				sleep();
				i++;
			}
		}

		public void requestNewSession(){
			broker.requestNewSession();
			int i = 0;
			while(i<2){
				sleep();
				i++;
			}
		}
		private void sleep(){
			try{
				Thread.sleep(1000);
			}catch(Exception e){}
		}

	}

}