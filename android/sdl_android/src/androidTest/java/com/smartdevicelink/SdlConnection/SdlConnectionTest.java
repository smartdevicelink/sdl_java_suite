package com.smartdevicelink.SdlConnection;

import android.support.test.runner.AndroidJUnit4;

import com.smartdevicelink.test.SdlUnitTestContants;
import com.smartdevicelink.test.util.DeviceUtil;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.RouterServiceValidator;
import com.smartdevicelink.transport.USBTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;


@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.JVM)
public class SdlConnectionTest {

	private static final String TAG = "SdlConnection Tests";

	@Test
	public void testForceConnectEvent(){
		SdlConnection.enableLegacyMode(false, null);
		assertNull(SdlConnectionTestClass.cachedMultiConfig);

		RouterServiceValidator rsvp = new RouterServiceValidator(getTargetContext());
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_PERFORM_ALL_CHECKS);
		MultiplexTransportConfig config = new MultiplexTransportConfig(getTargetContext(),SdlUnitTestContants.TEST_APP_ID);
		SdlConnectionTestClass connection = new SdlConnectionTestClass(config,rsvp);
		assertNotNull(SdlConnectionTestClass.cachedMultiConfig);

		assertTrue(SdlConnectionTestClass.isLegacyModeEnabled());

		assertEquals(TransportType.BLUETOOTH, connection.getCurrentTransportType());

		assertNotNull(SdlConnectionTestClass.cachedMultiConfig);

		connection.forceHardwareConnectEvent(TransportType.MULTIPLEX);
		assertFalse(connection.connected);

		SdlConnectionTestClass.cachedMultiConfig.setSecurityLevel(MultiplexTransportConfig.FLAG_MULTI_SECURITY_OFF);

		if(!DeviceUtil.isEmulator()) { // Cannot perform MBT operations in emulator
			assertNotNull(SdlConnectionTestClass.cachedMultiConfig.getService());
			assertEquals(TransportType.MULTIPLEX, connection.getCurrentTransportType());
		}

		// Test for handling of null service
		MultiplexTransportConfig null_service_config = new MultiplexTransportConfig(getTargetContext(),SdlUnitTestContants.TEST_APP_ID);
		null_service_config.setService(null);
		SdlConnectionTestClass null_service_connection = new SdlConnectionTestClass(null_service_config, null);
		Exception exception = null;
		try{
			null_service_connection.forceHardwareConnectEvent(TransportType.MULTIPLEX);
		}catch(NullPointerException e){
			exception = e;
		}

		assertNull(exception); // Failed, unhandled NPE
	}

	@Test
	public void testMultiplexConstructor(){
		SdlConnection.enableLegacyMode(false, null);
		RouterServiceValidator rsvp = new RouterServiceValidator(getTargetContext());
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_NONE);
		MultiplexTransportConfig config = new MultiplexTransportConfig(getTargetContext(),SdlUnitTestContants.TEST_APP_ID);
		SdlConnection connection = new SdlConnection(config,rsvp);
		if(DeviceUtil.isEmulator()){ // Cannot perform MBT operations in emulator
			assertEquals(TransportType.BLUETOOTH, connection.getCurrentTransportType());
		}else{
			assertEquals(TransportType.MULTIPLEX, connection.getCurrentTransportType());
		}
	}

	@Test
	public void testMultiplexConstructorInsalledFrom(){
		SdlConnection.enableLegacyMode(false, null);
		RouterServiceValidator rsvp = new RouterServiceValidator(getTargetContext());
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_INSTALLED_FROM_CHECK);
		MultiplexTransportConfig config = new MultiplexTransportConfig(getTargetContext(),SdlUnitTestContants.TEST_APP_ID);
		SdlConnection connection = new SdlConnection(config,rsvp);
		boolean didValidate = rsvp.validate();
		if(didValidate){
			assertEquals(TransportType.MULTIPLEX, connection.getCurrentTransportType());
		}else{
			assertEquals(TransportType.BLUETOOTH, connection.getCurrentTransportType());

		}
	}

	@Test
	public void testMultiplexConstructorTrustedPackage(){
		SdlConnection.enableLegacyMode(false, null);
		RouterServiceValidator rsvp = new RouterServiceValidator(getTargetContext());
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_PACKAGE_CHECK);
		MultiplexTransportConfig config = new MultiplexTransportConfig(getTargetContext(),SdlUnitTestContants.TEST_APP_ID);
		SdlConnection connection = new SdlConnection(config,rsvp);
		boolean didValidate = rsvp.validate();
		if(didValidate){
			assertEquals(TransportType.MULTIPLEX, connection.getCurrentTransportType());
		}else{
			assertEquals(TransportType.BLUETOOTH, connection.getCurrentTransportType());

		}
	}

	@Test
	public void testMultiplexConstructorTrustedPackageAndVersion(){
		SdlConnection.enableLegacyMode(false, null);
		RouterServiceValidator rsvp = new RouterServiceValidator(getTargetContext());
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_VERSION_CHECK);
		MultiplexTransportConfig config = new MultiplexTransportConfig(getTargetContext(),SdlUnitTestContants.TEST_APP_ID);
		SdlConnection connection = new SdlConnection(config,rsvp);
		boolean didValidate = rsvp.validate();
		if(didValidate){
			assertEquals(TransportType.MULTIPLEX, connection.getCurrentTransportType());
		}else{
			assertEquals(TransportType.BLUETOOTH, connection.getCurrentTransportType());

		}
	}

	@Test
	public void testMultiplexConstructorAllFlags(){
		SdlConnection.enableLegacyMode(false, null);
		RouterServiceValidator rsvp = new RouterServiceValidator(getTargetContext());
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_PERFORM_ALL_CHECKS);
		MultiplexTransportConfig config = new MultiplexTransportConfig(getTargetContext(),SdlUnitTestContants.TEST_APP_ID);
		SdlConnection connection = new SdlConnection(config,rsvp);
		boolean didValidate = rsvp.validate();
		if(didValidate){
			assertEquals(TransportType.MULTIPLEX, connection.getCurrentTransportType());
		}else{
			assertEquals(TransportType.BLUETOOTH, connection.getCurrentTransportType());

		}
	}

	@Test
	public void testBluetoothConstructor(){
		SdlConnection.enableLegacyMode(false, null);
		BTTransportConfig btConfig = new BTTransportConfig(true);
		SdlConnection connection = new SdlConnection(btConfig);
		assertEquals(TransportType.BLUETOOTH, connection.getCurrentTransportType());
	}

	@Test
	public void testUsbConstructor(){
		SdlConnection.enableLegacyMode(false, null);
		USBTransportConfig btConfig = new USBTransportConfig(getTargetContext());
		SdlConnection connection = new SdlConnection(btConfig,null);
		assertFalse(SdlConnection.isLegacyModeEnabled());
		assertEquals(TransportType.USB, connection.getCurrentTransportType());

		SdlConnection connection2 = new SdlConnection(btConfig);
		assertEquals(TransportType.USB, connection2.getCurrentTransportType());
	}

	protected class SdlConnectionTestClass extends SdlConnection{
		protected boolean connected = false;
		public SdlConnectionTestClass(BaseTransportConfig transportConfig) {
			super(transportConfig);
		}

		protected SdlConnectionTestClass(BaseTransportConfig transportConfig,RouterServiceValidator rsvp){
			super(transportConfig,rsvp);
		}

		@Override
		public void onTransportConnected() {
			super.onTransportConnected();
			connected = true;
		}

		@Override
		public void onTransportDisconnected(String info) {
			connected = false;
			//Grab a currently running router service
			RouterServiceValidator rsvp2 = new RouterServiceValidator(getTargetContext());
			rsvp2.setFlags(RouterServiceValidator.FLAG_DEBUG_NONE);
			assertTrue(rsvp2.validate());
			assertNotNull(rsvp2.getService());
			SdlConnectionTestClass.cachedMultiConfig.setService(rsvp2.getService());
			super.onTransportDisconnected(info);
		}

		@Override
		public void onTransportError(String info, Exception e) {
			connected = false;
			super.onTransportError(info, e);
		}
		
		
		
	}
	
}
