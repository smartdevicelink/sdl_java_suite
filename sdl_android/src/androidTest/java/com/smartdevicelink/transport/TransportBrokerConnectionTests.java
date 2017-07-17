package com.smartdevicelink.transport;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.smartdevicelink.test.SdlUnitTestContants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertTrue;

/**
 * Testing the connection workflow of Transport Broker
 */
@RunWith(AndroidJUnit4.class)
public class TransportBrokerConnectionTests {
	RouterServiceValidator rsvp;
	SdlRouterService routerService;
	ComponentName serviceComponentName;
	Context context = InstrumentationRegistry.getTargetContext();

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Rule
	public final ServiceTestRule mServiceRule = new ServiceTestRule();

	@Before
	public void setUp() throws Exception {
		rsvp = new RouterServiceValidator(context);
		rsvp.validate();
		}


	@Test
	public void testInitializeConnectionWithRouterService() throws TimeoutException, NoSuchFieldException, IllegalAccessException {
		// Create the service Intent.
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		serviceComponentName = new ComponentName("com.smartdevicelink.transport", "com.smartdevicelink.transport.FakeSdlRouterService");
//		serviceComponentName = new ComponentName("com.smartdevicelink.transport", "com.smartdevicelink.transport.SdlRouterService");
		Intent serviceIntent = new Intent(context, FakeSdlRouterService.class);
//		serviceIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_CLIENT);

		context.startService(serviceIntent);

		TransportBroker broker = new TransportBroker(context, SdlUnitTestContants.TEST_APP_ID, serviceComponentName);
//		assertTrue(broker.start());

	}



}
