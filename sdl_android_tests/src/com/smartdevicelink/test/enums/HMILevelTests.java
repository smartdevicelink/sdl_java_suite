package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import junit.framework.TestCase;

public class HMILevelTests extends TestCase {

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
			HMILevel.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	//use this test if it's supposed to return null
	public void testInvalidEnum2 () {
		String example = "no_FiX";
		HMILevel result = HMILevel.valueForString(example);
		assertNull("Invalid string didn't return null", result);
	}
	
	//use this test if it's supposed to throw an exception
	public void testNullEnum () {
		String example = null;
		try {
			HMILevel.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
	//use this test if it's supposed to return null
	public void testNullEnum2 () {
		String example = null;
		HMILevel result = HMILevel.valueForString(example);
		assertNull("Null string didn't return null", result);
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
