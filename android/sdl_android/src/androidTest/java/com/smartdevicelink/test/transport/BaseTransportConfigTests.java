package com.smartdevicelink.test.transport;

import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.transport.BaseTransportConfig}
 */
public class BaseTransportConfigTests extends TestCase {
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.transport.BaseTransportConfig#getTransportType()}
	 * {@link com.smartdevicelink.transport.BaseTransportConfig#getHeartBeatTimeout()}
	 * {@link com.smartdevicelink.transport.BaseTransportConfig#setHeartBeatTimeout(int)}
	 */
	public void testConfigs () {
		
		// Test Values
		int testInt = 10;
		MockBaseTransportConfig testBaseTransportConfig = new MockBaseTransportConfig();
		
		// Comparison Values
		int     expectedMaxValue      = Integer.MAX_VALUE;
		int     actualMaxValue        = testBaseTransportConfig.getHeartBeatTimeout();
		
		// Valid Tests
		assertNotNull(TestValues.NOT_NULL, testBaseTransportConfig);
		assertEquals(TestValues.MATCH, expectedMaxValue, actualMaxValue);

		testBaseTransportConfig.setHeartBeatTimeout(testInt);
		assertEquals(TestValues.MATCH, testInt, testBaseTransportConfig.getHeartBeatTimeout());
	}
}

/**
 * This is a mock class for testing the following : 
 * {@link com.smartdevicelink.transport.BaseTransportConfig}
 */
class MockBaseTransportConfig extends BaseTransportConfig {	
	public MockBaseTransportConfig () { }
	@Override public TransportType getTransportType() { return null; }
}