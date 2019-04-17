package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;
import com.smartdevicelink.proxy.callbacks.OnError;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.callbacks.OnError}
 */
public class OnErrorTests extends TestCase {
		
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.proxy.callbacks.OnError#OnError()}
	 * {@link com.smartdevicelink.proxy.callbacks.OnError#OnError(String, Exception)}
	 */
	public void testValues () {		
		// Valid Tests
		OnError testOnError = new OnError();
		assertEquals(Test.MATCH, InternalProxyMessage.OnProxyError, testOnError.getFunctionName());
		
		Exception testE = new Exception();
		testOnError = new OnError(Test.GENERAL_STRING, testE);
		assertEquals(Test.MATCH, InternalProxyMessage.OnProxyError, testOnError.getFunctionName());
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testOnError.getInfo());
		assertEquals(Test.MATCH, testE, testOnError.getException());
		
		// Invalid/Null Tests
		testOnError = new OnError(null, null);
		assertEquals(Test.MATCH, InternalProxyMessage.OnProxyError, testOnError.getFunctionName());
		assertNull(Test.NULL, testOnError.getInfo());
		assertNull(Test.NULL, testOnError.getException());		
	}
}