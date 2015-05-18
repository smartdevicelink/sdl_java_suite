package com.smartdevicelink.test.transport;

import com.smartdevicelink.transport.TCPTransportConfig;
import com.smartdevicelink.transport.TransportType;

import junit.framework.TestCase;

public class TcpTransportConfigTests extends TestCase {
	
	public void testValues () {
		
		TCPTransportConfig test = new TCPTransportConfig(123, "test", true);
		assertEquals("Values should match.", 123, test.getPort());
		assertEquals("Values should match.", "test", test.getIPAddress());
		assertTrue("Value should be true.", test.getAutoReconnect());
		assertEquals("Values should match.", TransportType.TCP, test.getTransportType());
		
		String expected = "TCPTransportConfig{Port=123, IpAddress='test', AutoReconnect=true}";
		assertEquals("Values should match.", expected, test.toString());
	}
}