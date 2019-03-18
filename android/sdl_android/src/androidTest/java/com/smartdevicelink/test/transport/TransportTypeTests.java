package com.smartdevicelink.test.transport;

import com.smartdevicelink.test.Test;
import com.smartdevicelink.transport.enums.TransportType;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.transport.enums.TransportType}
 */
public class TransportTypeTests extends TestCase {
	
	/**
	 * This is a unit test for the following enum : 
	 * {@link com.smartdevicelink.transport.enums.TransportType}
	 */
	public void testTransportTypeEnum () {
		
		// Test Values
		String testTcp       		= "TCP";
		String testUsb       		= "USB";
		String testInvalid   		= "INVALID";
		String testBluetooth 		= "BLUETOOTH";
		String testMultiplexing 	= "MULTIPLEX";
		String testWebSocketServer 	= "WEB_SOCKET_SERVER";
		String testCustom 			= "CUSTOM";

		try {
			// Comparison Values
			TransportType expectedTcpEnum        		= TransportType.TCP;
			TransportType expectedUsbEnum        		= TransportType.USB;
			TransportType expectedBluetoothEnum  		= TransportType.BLUETOOTH;
			TransportType expectedMultiplexingEnum  	= TransportType.MULTIPLEX;
			TransportType expectedWebSocketServerEnum  	= TransportType.WEB_SOCKET_SERVER;
			TransportType expectedCustomEnum  			= TransportType.CUSTOM;

			List<TransportType> expectedEnumList = new ArrayList<TransportType>();
			expectedEnumList.add(TransportType.BLUETOOTH);
			expectedEnumList.add(TransportType.TCP);
			expectedEnumList.add(TransportType.USB);
			expectedEnumList.add(TransportType.MULTIPLEX);
			expectedEnumList.add(TransportType.WEB_SOCKET_SERVER);
			expectedEnumList.add(TransportType.CUSTOM);

			TransportType actualNullEnum       			= TransportType.valueForString(null);
			TransportType actualTcpEnum        			= TransportType.valueForString(testTcp);
			TransportType actualUsbEnum        			= TransportType.valueForString(testUsb);
			TransportType actualInvalidEnum    			= TransportType.valueForString(testInvalid);
			TransportType actualBluetoothEnum  			= TransportType.valueForString(testBluetooth);
			TransportType actualMultiplexingEnum 		= TransportType.valueForString(testMultiplexing);
			TransportType actualWebSocketServerEnum 	= TransportType.valueForString(testWebSocketServer);
			TransportType actualCustomEnum			 	= TransportType.valueForString(testCustom);

			List<TransportType> actualEnumList = Arrays.asList(TransportType.values());
			
			// Valid Tests
			assertEquals(Test.MATCH, expectedTcpEnum, actualTcpEnum);
			assertEquals(Test.MATCH, expectedUsbEnum, actualUsbEnum);
			assertEquals(Test.MATCH, expectedBluetoothEnum, actualBluetoothEnum);
			assertEquals(Test.MATCH, expectedMultiplexingEnum, actualMultiplexingEnum);
			assertEquals(Test.MATCH, expectedWebSocketServerEnum, actualWebSocketServerEnum);
			assertEquals(Test.MATCH, expectedCustomEnum, actualCustomEnum);
			assertTrue(Test.ARRAY, expectedEnumList.containsAll(actualEnumList) && actualEnumList.containsAll(expectedEnumList));
			
			// Invalid/Null Tests
			assertNull(Test.NULL, actualInvalidEnum);
			assertNull(Test.NULL, actualNullEnum);
		
		} catch (NullPointerException e) {
			fail("Could not retrieve value for null string, should return null.");
		} catch (IllegalArgumentException e) {
			fail("Could not retrieve value for invalid string, should return null.");
		}
	}
}