package com.smartdevicelink.test.utils;

import com.smartdevicelink.util.NativLogTool;

import junit.framework.TestCase;

public class NativeLogToolTests extends TestCase {
	
	public void testValidEnums () {
		String test = "Info";
		LogTarget testInfo = LogTarget.valueForString(test);
		test = "Warning";
		LogTarget testWarning = LogTarget.valueForString(test);
		test = "Error";
		LogTarget testError = LogTarget.valueForString(test);
	}
	
	public void testEnabled () {
		NativeLogTool.setEnableState(false);
		assertFalse("Enabled status should be false.", NativeLogTool.isEnabled());
		
		NativeLogTool.setEnableState(true);
		assertTrue("Enabled status should be true.", NativeLogTool.isEnabled());
	}
	
	// No need to test logging.
}