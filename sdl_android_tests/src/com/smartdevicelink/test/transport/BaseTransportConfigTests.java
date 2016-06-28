package com.smartdevicelink.test.transport;

import junit.framework.TestCase;

import com.smartdevicelink.test.Test;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.transport.BaseTransportConfig}
 */
public class BaseTransportConfigTests extends TestCase {
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.transport.BaseTransportConfig#getTransportType()}
	 * {@link com.smartdevicelink.transport.BaseTransportConfig#shareConnection()}
	 * {@link com.smartdevicelink.transport.BaseTransportConfig#getHeartBeatTimeout()}
	 * {@link com.smartdevicelink.transport.BaseTransportConfig#setHeartBeatTimeout(int)}
	 */
	public void testConfigs () {
		
		// Test Values
		int testInt = 10;
		MockBaseTransportConfig testBaseTransportConfig = new MockBaseTransportConfig();
		
		// Comparison Values
		int     expectedMaxValue      = Integer.MAX_VALUE;
		boolean actualShareConnection = testBaseTransportConfig.shareConnection();
		int     actualMaxValue        = testBaseTransportConfig.getHeartBeatTimeout();
		
		// Valid Tests
		assertNotNull(Test.NOT_NULL, testBaseTransportConfig);		
		assertEquals(Test.MATCH, expectedMaxValue, actualMaxValue);
		assertTrue(Test.TRUE, actualShareConnection);
		
		testBaseTransportConfig.setHeartBeatTimeout(testInt);
		assertEquals(Test.MATCH, testInt, testBaseTransportConfig.getHeartBeatTimeout());		
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