package com.smartdevicelink.SdlConnection;

import android.test.AndroidTestCase;

import com.smartdevicelink.test.SdlUnitTestContants;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.RouterServiceValidator;
import com.smartdevicelink.transport.USBTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;

public class SdlConnectionTest extends AndroidTestCase {
	
	private static final String TAG = "SdlConnection Tests";
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testForceConnectEvent(){
		SdlConnection.enableLegacyMode(false, null);
		assertNull(SdlConnectionTestClass.cachedMultiConfig);
		
		RouterServiceValidator rsvp = new RouterServiceValidator(this.mContext);
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_PERFORM_ALL_CHECKS);
		MultiplexTransportConfig config = new MultiplexTransportConfig(this.mContext,SdlUnitTestContants.TEST_APP_ID);
		SdlConnectionTestClass connection = new SdlConnectionTestClass(config,rsvp);
		assertNotNull(SdlConnectionTestClass.cachedMultiConfig);
		
		assertTrue(SdlConnectionTestClass.isLegacyModeEnabled());
		
		assertEquals(TransportType.BLUETOOTH, connection.getCurrentTransportType());
		
		assertNotNull(SdlConnectionTestClass.cachedMultiConfig);
		
		
		
		connection.forceHardwareConnectEvent(TransportType.MULTIPLEX);
		assertFalse(connection.connected);
		
		assertNotNull(SdlConnectionTestClass.cachedMultiConfig.getService());

		
		assertEquals(TransportType.MULTIPLEX, connection.getCurrentTransportType());

		
	}
	
	public void testMultiplexConstructor(){
		SdlConnection.enableLegacyMode(false, null);
		RouterServiceValidator rsvp = new RouterServiceValidator(this.mContext);
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_NONE);
		MultiplexTransportConfig config = new MultiplexTransportConfig(this.mContext,SdlUnitTestContants.TEST_APP_ID);
		SdlConnection connection = new SdlConnection(config,rsvp);
		assertEquals(TransportType.MULTIPLEX, connection.getCurrentTransportType());
	}
	
	public void testMultiplexConstructorInsalledFrom(){
		SdlConnection.enableLegacyMode(false, null);
		RouterServiceValidator rsvp = new RouterServiceValidator(this.mContext);
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_INSTALLED_FROM_CHECK);
		MultiplexTransportConfig config = new MultiplexTransportConfig(this.mContext,SdlUnitTestContants.TEST_APP_ID);
		SdlConnection connection = new SdlConnection(config,rsvp);
		boolean didValidate = rsvp.validate();
		if(didValidate){
			assertEquals(TransportType.MULTIPLEX, connection.getCurrentTransportType());
		}else{
			assertEquals(TransportType.BLUETOOTH, connection.getCurrentTransportType());

		}
	}
	
	public void testMultiplexConstructorTrustedPackage(){
		SdlConnection.enableLegacyMode(false, null);
		RouterServiceValidator rsvp = new RouterServiceValidator(this.mContext);
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_PACKAGE_CHECK);
		MultiplexTransportConfig config = new MultiplexTransportConfig(this.mContext,SdlUnitTestContants.TEST_APP_ID);
		SdlConnection connection = new SdlConnection(config,rsvp);
		boolean didValidate = rsvp.validate();
		if(didValidate){
			assertEquals(TransportType.MULTIPLEX, connection.getCurrentTransportType());
		}else{
			assertEquals(TransportType.BLUETOOTH, connection.getCurrentTransportType());

		}
	}
	
	public void testMultiplexConstructorTrustedPackageAndVersion(){
		SdlConnection.enableLegacyMode(false, null);
		RouterServiceValidator rsvp = new RouterServiceValidator(this.mContext);
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_VERSION_CHECK);
		MultiplexTransportConfig config = new MultiplexTransportConfig(this.mContext,SdlUnitTestContants.TEST_APP_ID);
		SdlConnection connection = new SdlConnection(config,rsvp);
		boolean didValidate = rsvp.validate();
		if(didValidate){
			assertEquals(TransportType.MULTIPLEX, connection.getCurrentTransportType());
		}else{
			assertEquals(TransportType.BLUETOOTH, connection.getCurrentTransportType());

		}	}
	
	public void testMultiplexConstructorAllFlags(){
		SdlConnection.enableLegacyMode(false, null);
		RouterServiceValidator rsvp = new RouterServiceValidator(this.mContext);
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_PERFORM_ALL_CHECKS);
		MultiplexTransportConfig config = new MultiplexTransportConfig(this.mContext,SdlUnitTestContants.TEST_APP_ID);
		SdlConnection connection = new SdlConnection(config,rsvp);
		boolean didValidate = rsvp.validate();
		if(didValidate){
			assertEquals(TransportType.MULTIPLEX, connection.getCurrentTransportType());
		}else{
			assertEquals(TransportType.BLUETOOTH, connection.getCurrentTransportType());

		}	}
	
	public void testBluetoothConstructor(){
		SdlConnection.enableLegacyMode(false, null);
		BTTransportConfig btConfig = new BTTransportConfig(true);
		SdlConnection connection = new SdlConnection(btConfig);
		assertEquals(TransportType.BLUETOOTH, connection.getCurrentTransportType());
	}

	public void testUsbConstructor(){
		SdlConnection.enableLegacyMode(false, null);
		USBTransportConfig btConfig = new USBTransportConfig(mContext);
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
			RouterServiceValidator rsvp2 = new RouterServiceValidator(mContext);
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
