package com.smartdevicelink.test.transport;

import junit.framework.TestCase;

import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.TransportType;

public class BaseTransportConfigTests extends TestCase {
	
	public void testValues () {
		
		MockBTC mock = new MockBTC();
		assertNotNull("Mock value should not be null", mock);
		assertTrue("Value should be true", mock.shareConnection());
		assertEquals("Values do not match", Integer.MAX_VALUE, mock.getHeartBeatTimeout());
		
		mock.setHeartBeatTimeout(10);
		assertEquals("Values do not match", 10, mock.getHeartBeatTimeout());
	}
}

class MockBTC extends BaseTransportConfig {	
	public MockBTC () { }
	@Override public TransportType getTransportType() { return null; }
}