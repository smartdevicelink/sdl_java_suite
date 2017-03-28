package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;
import com.smartdevicelink.proxy.callbacks.OnProxyClosed;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.callbacks.OnProxyClosed}
 */
public class OnProxyClosedTests extends TestCase {
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.proxy.callbacks.OnProxyClosed#OnProxyClosed()}
	 * {@link com.smartdevicelink.proxy.callbacks.OnProxyClosed#OnProxyClosed(String, Exception, SdlDisconnectedReason)}
	 */
	public void testValues () {
		// Valid Tests
		OnProxyClosed testOnProxyClosed = new OnProxyClosed();
		assertEquals(Test.MATCH, InternalProxyMessage.OnProxyClosed, testOnProxyClosed.getFunctionName());
		
		Exception testE = new Exception();
		SdlDisconnectedReason testReason = SdlDisconnectedReason.DEFAULT;
		testOnProxyClosed = new OnProxyClosed(Test.GENERAL_STRING, testE, testReason);
		assertEquals(Test.MATCH, InternalProxyMessage.OnProxyClosed, testOnProxyClosed.getFunctionName());
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testOnProxyClosed.getInfo());
		assertEquals(Test.MATCH, testE, testOnProxyClosed.getException());
		assertEquals(Test.MATCH, testReason, testOnProxyClosed.getReason());
		
		// Invalid/Null Tests
		testOnProxyClosed = new OnProxyClosed(null, null, null);
		assertEquals(Test.MATCH, InternalProxyMessage.OnProxyClosed, testOnProxyClosed.getFunctionName());
		assertNull(Test.NULL, testOnProxyClosed.getInfo());
		assertNull(Test.NULL, testOnProxyClosed.getException());
		assertNull(Test.NULL, testOnProxyClosed.getReason());
	}
}