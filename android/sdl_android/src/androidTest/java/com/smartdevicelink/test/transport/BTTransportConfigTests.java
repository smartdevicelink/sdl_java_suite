package com.smartdevicelink.test.transport;

import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.transport.BtTransportConfig}
 */
public class BTTransportConfigTests extends TestCase {
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.transport.BtTransportConfig#getTransportType()}
	 * {@link com.smartdevicelink.transport.BtTransportConfig#setKeepSocketActive(boolean)}
	 * {@link com.smartdevicelink.transport.BtTransportConfig#getKeepSocketActive}
	 */
	public void testConfigs () {
		
		// Test Values
		boolean testBoolean = true;
		BTTransportConfig testConfig1 = new BTTransportConfig();
		BTTransportConfig testConfig2 = new BTTransportConfig(testBoolean);
		
		// Comparison Values
		TransportType expectedTransportType = TransportType.BLUETOOTH;
		boolean       actualShareConnection = testConfig2.shareConnection();
		TransportType actualTransportType   = testConfig1.getTransportType();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, expectedTransportType, actualTransportType);
		assertTrue(TestValues.TRUE, actualShareConnection);
		
		
		testConfig1.setKeepSocketActive(true);
		assertTrue(TestValues.TRUE, testConfig1.getKeepSocketActive());
		testConfig1.setKeepSocketActive(false);
		assertFalse(TestValues.FALSE, testConfig1.getKeepSocketActive());
	}	
}