package com.smartdevicelink.test.rpc.enums;

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
		    DriverDistractionState temp = DriverDistractionState.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
		    DriverDistractionState temp = DriverDistractionState.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
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
