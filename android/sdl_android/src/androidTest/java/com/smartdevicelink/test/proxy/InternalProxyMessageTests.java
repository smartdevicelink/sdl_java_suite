package com.smartdevicelink.test.proxy;

import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.callbacks.InternalProxyMessage}
 */
public class InternalProxyMessageTests extends TestCase {
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.proxy.callbacks.InternalProxyMessage#InternalProxyMessage(String)}
	 */
	public void testValues () {
		// Valid Tests
		String test = "functionName";
		InternalProxyMessage testIPM = new InternalProxyMessage(test);
		assertEquals(Test.MATCH, test, testIPM.getFunctionName());
		
		test = "OnProxyError";
		assertEquals(Test.MATCH, test, InternalProxyMessage.OnProxyError);		
		test = "OnProxyOpened";
		assertEquals(Test.MATCH, test, InternalProxyMessage.OnProxyOpened);		
		test = "OnProxyClosed";
		assertEquals(Test.MATCH, test, InternalProxyMessage.OnProxyClosed);
		
		// Invalid/Null Tests
		testIPM = new InternalProxyMessage(null);
		assertNull(Test.NULL, testIPM.getFunctionName());
	}
}