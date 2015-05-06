package com.smartdevicelink.test.transport;

import junit.framework.TestCase;

import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.TransportType;

public class BTTransportConfigTests extends TestCase {
	
	public void testValues () {
		
		BTTransportConfig test1 = new BTTransportConfig();
		BTTransportConfig test2 = new BTTransportConfig(false);		
		assertEquals("Values should match.", TransportType.BLUETOOTH, test1.getTransportType());
		assertEquals("Values should match.", TransportType.BLUETOOTH, test2.getTransportType());
		
		assertTrue("Values should match.", test1.shareConnection());
		assertFalse("Values should match.", test2.shareConnection());
		
		test1.setKeepSocketActive(true);
		assertTrue("Value should be true.", test1.getKeepSocketActive());
		test1.setKeepSocketActive(false);
		assertFalse("Value should be false.", test1.getKeepSocketActive());
	}	
}