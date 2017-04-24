package com.smartdevicelink.test.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.test.Test;
import com.smartdevicelink.util.NativeLogTool;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.util.NativeLogTool}
 */
public class NativeLogToolTests extends TestCase {
	
	/**
	 * This is a unit test for the following enum : 
	 * {@link com.smartdevicelink.util.NativeLogTool.LogTarget}
	 */
	public void testLogTargetEnum () {
		
		// Test Values
		String testInfo    = "Info";
		String testError   = "Error";
		String testInvalid = "Invalid";
		String testWarning = "Warning";
		
		try {
			// Comparison Values 
			NativeLogTool.LogTarget expectedInfoEnum       = NativeLogTool.LogTarget.Info;
			NativeLogTool.LogTarget expectedErrorEnum      = NativeLogTool.LogTarget.Error;
			NativeLogTool.LogTarget expectedWarningEnum    = NativeLogTool.LogTarget.Warning;
			List<NativeLogTool.LogTarget> expectedEnumList = new ArrayList<NativeLogTool.LogTarget>();
			expectedEnumList.add(NativeLogTool.LogTarget.Info);			
			expectedEnumList.add(NativeLogTool.LogTarget.Error);
			expectedEnumList.add(NativeLogTool.LogTarget.Warning);
			
			NativeLogTool.LogTarget actualNullEnum       = NativeLogTool.LogTarget.valueForString(null);
			NativeLogTool.LogTarget actualInfoEnum       = NativeLogTool.LogTarget.valueForString(testInfo);
			NativeLogTool.LogTarget actualErrorEnum      = NativeLogTool.LogTarget.valueForString(testError);
			NativeLogTool.LogTarget actualInvalidEnum    = NativeLogTool.LogTarget.valueForString(testInvalid);
			NativeLogTool.LogTarget actualWarningEnum    = NativeLogTool.LogTarget.valueForString(testWarning);
			List<NativeLogTool.LogTarget> actualEnumList = Arrays.asList(NativeLogTool.LogTarget.values());
			
			// Valid Tests
			assertEquals(Test.MATCH, expectedInfoEnum,    actualInfoEnum);
			assertEquals(Test.MATCH, expectedErrorEnum,   actualErrorEnum);
			assertEquals(Test.MATCH, expectedWarningEnum, actualWarningEnum);
			assertTrue("Contents should match.", expectedEnumList.containsAll(actualEnumList) && actualEnumList.containsAll(expectedEnumList));
			
			// Invalid/Null Tests
			assertNull(Test.NULL, actualInvalidEnum);
			assertNull(Test.NULL, actualNullEnum);
		
		} catch (NullPointerException e) {
			fail("Could not retrieve value for null string, should return null.");
		} catch (IllegalArgumentException e) {
			fail("Could not retrieve value for invalid string, should return null.");
		}
	}
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.util.NativeLogTool#setEnableState(boolean)}
	 * {@link com.smartdevicelink.util.NativeLogTool#getEnableState()}
	 */
	public void testEnabled () {
		NativeLogTool.setEnableState(false);
		assertFalse("Value should be false.", NativeLogTool.isEnabled());		
		NativeLogTool.setEnableState(true);
		assertTrue("Valueshould be true.", NativeLogTool.isEnabled());
	}
	
	// NOTE : No testing can currently be done for the logging methods.
}