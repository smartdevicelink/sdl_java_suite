package com.smartdevicelink.test.proxy;

import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;
import com.smartdevicelink.proxy.callbacks.OnProxyOpened;

import junit.framework.TestCase;

public class OnProxyOpenedTests extends TestCase {
	
	public void testFunctionName () {
		
		OnProxyOpened testOPO = new OnProxyOpened();
		assertEquals("Function name did not match expected value",
					  InternalProxyMessage.OnProxyOpened ,
					  testOPO.getFunctionName());
	
	}
}