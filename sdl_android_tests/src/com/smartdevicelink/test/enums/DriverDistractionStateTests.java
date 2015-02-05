package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;

public class DriverDistractionStateTests extends TestCase {

	public void testValidEnums () {	
		String example = "DD_ON";
		DriverDistractionState enumDdOn = DriverDistractionState.valueForString(example);
		example = "DD_OFF";
		DriverDistractionState enumDdOff = DriverDistractionState.valueForString(example);
		
		assertNotNull("DD_ON returned null", enumDdOn);
		assertNotNull("DD_OFF returned null", enumDdOff);
	}
	
	public void testInvalidEnum () {
		String example = "dD_oN";
		try {
			DriverDistractionState.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			DriverDistractionState.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<DriverDistractionState> enumValueList = Arrays.asList(DriverDistractionState.values());

		List<DriverDistractionState> enumTestList = new ArrayList<DriverDistractionState>();
		enumTestList.add(DriverDistractionState.DD_ON);
		enumTestList.add(DriverDistractionState.DD_OFF);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
