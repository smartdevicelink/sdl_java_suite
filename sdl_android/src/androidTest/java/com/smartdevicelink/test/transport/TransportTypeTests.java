package com.smartdevicelink.test.transport;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.transport.enums.TransportType;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.transport.TransportType}
 */
public class TransportTypeTests extends AndroidTestCase {
	
	/**
	 * This is a unit test for the following enum : 
	 * {@link com.smartdevicelink.transport.TransportType}
	 */
	public void testTransportTypeEnum () {
		
		// Test Values
		String testTcp       	= mContext.getString(R.string.tcp_caps);
		String testUsb       	= mContext.getString(R.string.usb_caps);
		String testInvalid   	= mContext.getString(R.string.invalid_caps);
		String testBluetooth 	= mContext.getString(R.string.bluetooth_caps);
		String testMultiplexing = mContext.getString(R.string.multiplex_caps);
		
		try {
			// Comparison Values
			TransportType expectedTcpEnum        	= TransportType.TCP;
			TransportType expectedUsbEnum        	= TransportType.USB;
			TransportType expectedBluetoothEnum  	= TransportType.BLUETOOTH;
			TransportType expectedMultiplexingEnum  = TransportType.MULTIPLEX;
			
			List<TransportType> expectedEnumList = new ArrayList<TransportType>();
			expectedEnumList.add(TransportType.BLUETOOTH);
			expectedEnumList.add(TransportType.TCP);
			expectedEnumList.add(TransportType.USB);
			expectedEnumList.add(TransportType.MULTIPLEX);
			
			TransportType actualNullEnum       		= TransportType.valueForString(null);
			TransportType actualTcpEnum        		= TransportType.valueForString(testTcp);
			TransportType actualUsbEnum        		= TransportType.valueForString(testUsb);
			TransportType actualInvalidEnum    		= TransportType.valueForString(testInvalid);
			TransportType actualBluetoothEnum  		= TransportType.valueForString(testBluetooth);
			TransportType actualMultiplexingEnum 	= TransportType.valueForString(testMultiplexing);

			List<TransportType> actualEnumList = Arrays.asList(TransportType.values());
			
			// Valid Tests
			assertEquals(Test.MATCH, expectedTcpEnum, actualTcpEnum);
			assertEquals(Test.MATCH, expectedUsbEnum, actualUsbEnum);
			assertEquals(Test.MATCH, expectedBluetoothEnum, actualBluetoothEnum);
			assertEquals(Test.MATCH, expectedMultiplexingEnum, actualMultiplexingEnum);
			assertTrue(Test.ARRAY, expectedEnumList.containsAll(actualEnumList) && actualEnumList.containsAll(expectedEnumList));
			
			// Invalid/Null Tests
			assertNull(Test.NULL, actualInvalidEnum);
			assertNull(Test.NULL, actualNullEnum);
		
		} catch (NullPointerException e) {
			fail(mContext.getString(R.string.could_not_retrieve_value_for_null_string));
		} catch (IllegalArgumentException e) {
			fail(mContext.getString(R.string.could_not_retrieve_value_for_invalid_string));
		}
	}
}