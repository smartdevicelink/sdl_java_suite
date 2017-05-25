package com.smartdevicelink.test.transport;

import android.test.AndroidTestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.transport.TCPTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.transport.TcpTransportConfig}
 */
public class TCPTransportConfigTests extends AndroidTestCase {
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.transport.TcpTransportConfig#getPort()}
	 * {@link com.smartdevicelink.transport.TcpTransportConfig#getIPAddress()}
	 * {@link com.smartdevicelink.transport.TcpTransportConfig#getAutoReconnect()}
	 * {@link com.smartdevicelink.transport.TcpTransportConfig#getTransportType()}
	 */
	public void testConfigs () {
		
		// Test Values
		int     testInt     = 123;
		String  testString  = mContext.getString(R.string.test);
		boolean testBoolean = true;
		TCPTransportConfig testConfig1 = new TCPTransportConfig(testInt, testString, testBoolean);
		TCPTransportConfig testConfig2 = new TCPTransportConfig(testInt, null, testBoolean);
		
		// Comparison Values
		TransportType expectedTransportType = TransportType.TCP;
		String        expectedToString1     = mContext.getString(R.string.tcp_transport_test);
		String        expectedToString2     = mContext.getString(R.string.tcp_transport_null);
		int           actualPort            = testConfig1.getPort();
		String        actualIpAddress       = testConfig1.getIPAddress();
		String        actualToString1       = testConfig1.toString();
		String        actualToString2       = testConfig2.toString();
		String        actualNullString      = testConfig2.getIPAddress();
		boolean       actualAutoReconnect   = testConfig1.getAutoReconnect();
		TransportType actualTransportType   = testConfig1.getTransportType();
		
		// Valid Tests
		assertEquals(Test.MATCH, testInt, actualPort);
		assertEquals(Test.MATCH, testString, actualIpAddress);
		assertEquals(Test.MATCH, testBoolean, actualAutoReconnect);
		assertEquals(Test.MATCH, expectedTransportType, actualTransportType);
		assertEquals(Test.MATCH, expectedToString1, actualToString1);
		assertEquals(Test.MATCH, expectedToString2, actualToString2);
		
		// Invalid/Null Tests
		assertNull(Test.NULL, actualNullString);
	}
}