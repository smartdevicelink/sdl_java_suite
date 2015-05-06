package com.smartdevicelink.test.trace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.trace.enums.DetailLevel;

public class DetailLevelTests extends TestCase {

	private final String MSG = "Value did not match expected value.";
	
	public void testValidEnums () {
		
		String test = "OFF";
		DetailLevel enumOff = DetailLevel.valueForString(test);		
		test = "TERSE";
		DetailLevel enumTerse = DetailLevel.valueForString(test);
		test = "VERBOSE";
		DetailLevel enumVerbose = DetailLevel.valueForString(test);
		
		assertNotNull(MSG, enumOff);
		assertNotNull(MSG, enumTerse);
		assertNotNull(MSG, enumVerbose);
	}

	public void testInvalidEnum () {		
		String test = "invalid";
		try {
			DetailLevel temp = DetailLevel.valueForString(test);
			assertNull(MSG, temp);
		} catch (IllegalArgumentException e) {
			fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	public void testNullEnum () {
		String test = null;
		try {
			DetailLevel temp = DetailLevel.valueForString(test);
			assertNull(MSG, temp);
		} catch (NullPointerException e) {
			fail("Invalid enum throws NullPointerException.");
		}
	}
	
	public void testListEnum () {
		List<DetailLevel> enumValueList = Arrays.asList(DetailLevel.values());
		List<DetailLevel> enumTestList  = new ArrayList<DetailLevel>();
		
		enumTestList.add(DetailLevel.OFF);
		enumTestList.add(DetailLevel.TERSE);
		enumTestList.add(DetailLevel.VERBOSE);
		
		assertTrue("Enum value list does not match enum class list.",
				enumValueList.containsAll(enumTestList) &&
				enumTestList.containsAll(enumValueList));		
	}
}