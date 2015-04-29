package com.smartdevicelink.test.proxy;

import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;
import com.smartdevicelink.proxy.callbacks.OnError;

import junit.framework.TestCase;

public class OnErrorTests extends TestCase {
	
	public void testValues () {
		
		OnError testOE = new OnError();
		assertEquals("Function name did not match expected value",
					  InternalProxyMessage.OnProxyError,
					  testOE.getFunctionName());
		
		String testInfo = "info";
		Exception testE = new Exception();
		testOE = new OnError(testInfo, testE);
		assertEquals("Function name did not match expected value",
				  InternalProxyMessage.OnProxyError,
				  testOE.getFunctionName());
		assertEquals("Info did not match expected value", testInfo, testOE.getInfo());
		assertEquals("Exception did not match expected value", testE, testOE.getException());
		
		testOE = new OnError(null, null);
		assertEquals("Function name did not match expected value",
				  InternalProxyMessage.OnProxyError,
				  testOE.getFunctionName());
		assertNull("Info was not null", testOE.getInfo());
		assertNull("Exception was not null", testOE.getException());		
	}
}