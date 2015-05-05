package com.smartdevicelink.test.proxy;

import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;

import junit.framework.TestCase;

public class InternalProxyMessageTests extends TestCase {
	
	public void testValues () {
		String test = "functionName";
		InternalProxyMessage testIPM = new InternalProxyMessage(test);
		assertEquals("Function name did not match expected value.", test,
					  testIPM.getFunctionName());
		
		testIPM = new InternalProxyMessage(null);
		assertNull("Function name was not null", testIPM.getFunctionName());
		
		test = "OnProxyError";
		assertEquals("OnProxyError did not match expected value.", test,
					  InternalProxyMessage.OnProxyError);
		
		test = "OnProxyOpened";
		assertEquals("OnProxyOpened did not match expected value.", test,
				      InternalProxyMessage.OnProxyOpened);
		
		test = "OnProxyClosed";
		assertEquals("OnProxyClosed did not match expected value.", test,
				      InternalProxyMessage.OnProxyClosed);
	}
}