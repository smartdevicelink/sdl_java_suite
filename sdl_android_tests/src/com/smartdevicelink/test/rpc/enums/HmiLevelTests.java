package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import junit.framework.TestCase;

public class HmiLevelTests extends TestCase {

	public void testValidEnums () {	
		String example = "FULL";
		HMILevel enumFull = HMILevel.valueForString(example);
		example = "LIMITED";
		HMILevel enumLimited = HMILevel.valueForString(example);
		example = "BACKGROUND";
		HMILevel enumBackground = HMILevel.valueForString(example);
		example = "NONE";
		HMILevel enumNone = HMILevel.valueForString(example);
		
		assertNotNull("FULL returned null", enumFull);
		assertNotNull("LIMITED returned null", enumLimited);
		assertNotNull("BACKGROUND returned null", enumBackground);
		assertNotNull("NONE returned null", enumNone);
	}

	//use this test if it's supposed to throw an exception
	public void testInvalidEnum () {
		String example = "fUlL";
		try {
		    HMILevel temp = HMILevel.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	//use this test if it's supposed to throw an exception
	public void testNullEnum () {
		String example = null;
		try {
		    HMILevel temp = HMILevel.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}
	
	public void testListEnum() {
 		List<HMILevel> enumValueList = Arrays.asList(HMILevel.values());

		List<HMILevel> enumTestList = new ArrayList<HMILevel>();
		enumTestList.add(HMILevel.HMI_FULL);
		enumTestList.add(HMILevel.HMI_LIMITED);
		enumTestList.add(HMILevel.HMI_BACKGROUND);
		enumTestList.add(HMILevel.HMI_NONE);
		
		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
