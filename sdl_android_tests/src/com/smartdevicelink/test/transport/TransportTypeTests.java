package com.smartdevicelink.test.transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.transport.TransportType;

import junit.framework.TestCase;

public class TransportTypeTests extends TestCase {
	
	private final String MSG = "Value did not match expected value.";
	
	public void testValidEnums () {
		
		String test = "BLUETOOTH";
		TransportType enumBluetooth = TransportType.valueForString(test);
		test = "TCP";
		TransportType enumTCP = TransportType.valueForString(test);
		test = "USB";
		TransportType enumUSB = TransportType.valueForString(test);
		
		assertNotNull(MSG, enumBluetooth);
		assertNotNull(MSG, enumTCP);
		assertNotNull(MSG, enumUSB);
	}
	
	public void testInvalidEnum () {		
		String test = "invalid";
		try {
			TransportType temp = TransportType.valueForString(test);
			assertNull(MSG, temp);
		} catch (IllegalArgumentException e) {
			fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	public void testNullEnum () {
		String test = null;
		try {
			TransportType temp = TransportType.valueForString(test);
			assertNull(MSG, temp);
		} catch (NullPointerException e) {
			fail("Invalid enum throws NullPointerException.");
		}
	}
	
	public void testListEnum () {
		List<TransportType> enumValueList = Arrays.asList(TransportType.values());
		List<TransportType> enumTestList  = new ArrayList<TransportType>();
		
		enumTestList.add(TransportType.BLUETOOTH);
		enumTestList.add(TransportType.TCP);
		enumTestList.add(TransportType.USB);
		
		assertTrue("Enum value list does not match enum class list.",
				enumValueList.containsAll(enumTestList) &&
				enumTestList.containsAll(enumValueList));		
	}
}