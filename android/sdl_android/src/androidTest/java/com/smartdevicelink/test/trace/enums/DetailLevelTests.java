package com.smartdevicelink.test.trace.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.test.Test;
import com.smartdevicelink.trace.enums.DetailLevel;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.trace.enums.DetailLevel}
 */
public class DetailLevelTests extends TestCase {
	
	/**
	 * This is a unit test for the following enum : 
	 * {@link com.smartdevicelink.trace.enums.DetailLevel}
	 */
	public void testDetailLevelEnum () {
		
		// Test Values
		String testOff     = "OFF";
		String testTerse   = "TERSE";
		String testInvalid = "INVALID";
		String testVerbose = "VERBOSE";
		
		try {
			// Comparison Values
			DetailLevel expectedOffEnum        = DetailLevel.OFF;
			DetailLevel expectedTerseEnum      = DetailLevel.TERSE;
			DetailLevel expectedVerboseEnum    = DetailLevel.VERBOSE;
			List<DetailLevel> expectedEnumList = new ArrayList<DetailLevel>();
			expectedEnumList.add(DetailLevel.OFF);
			expectedEnumList.add(DetailLevel.TERSE);
			expectedEnumList.add(DetailLevel.VERBOSE);
			
			DetailLevel actualNullEnum       = DetailLevel.valueForString(null);
			DetailLevel actualOffEnum        = DetailLevel.valueForString(testOff);
			DetailLevel actualTerseEnum      = DetailLevel.valueForString(testTerse);
			DetailLevel actualInvalidEnum    = DetailLevel.valueForString(testInvalid);
			DetailLevel actualVerboseEnum    = DetailLevel.valueForString(testVerbose);
			List<DetailLevel> actualEnumList = Arrays.asList(DetailLevel.values());
			
			// Valid Tests
			assertEquals(Test.MATCH, expectedOffEnum, actualOffEnum);
			assertEquals(Test.MATCH, expectedTerseEnum, actualTerseEnum);
			assertEquals(Test.MATCH, expectedVerboseEnum, actualVerboseEnum);
			assertTrue(Test.ARRAY, expectedEnumList.containsAll(actualEnumList) && actualEnumList.containsAll(expectedEnumList));
			
			// Invalid/Null Tests
			assertNull(Test.NULL, actualInvalidEnum);
			assertNull(Test.NULL, actualNullEnum);
			
		}catch (NullPointerException e) {
			fail("Could not retrieve value for null string, should return null.");
		} catch (IllegalArgumentException e) {
			fail("Could not retrieve value for invalid string, should return null.");
		}
	}
}