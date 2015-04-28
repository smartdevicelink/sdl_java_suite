package com.smartdevicelink.test.utils;


import com.smartdevicelink.util.NativeLogTool;

import junit.framework.TestCase;

public class NativeLogToolTests extends TestCase {
	
	public void testValidEnums () {		
		String test = "Info";
		NativeLogTool.LogTarget testInfo = NativeLogTool.LogTarget.valueForString(test);
		test = "Warning";
		NativeLogTool.LogTarget testWarning = NativeLogTool.LogTarget.valueForString(test);
		test = "Error";
		NativeLogTool.LogTarget testError = NativeLogTool.LogTarget.valueForString(test);
		
		assertNotNull("Info enum was null", testInfo);
		assertNotNull("Warning enum was null", testWarning);
		assertNotNull("Error enum was null", testError);
	}
	
	public void testEnabled () {
		NativeLogTool.setEnableState(false);
		assertFalse("Enabled status should be false.", NativeLogTool.isEnabled());
		
		NativeLogTool.setEnableState(true);
		assertTrue("Enabled status should be true.", NativeLogTool.isEnabled());
	}
	
	// No need to test logging.
}