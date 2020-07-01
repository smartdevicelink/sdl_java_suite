package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;
import com.smartdevicelink.proxy.callbacks.OnProxyClosed;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.junit.Test;

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
	@Test
	public void testValues () {
		// Valid Tests
		OnProxyClosed testOnProxyClosed = new OnProxyClosed();
		assertEquals(TestValues.MATCH, InternalProxyMessage.OnProxyClosed, testOnProxyClosed.getFunctionName());
		
		Exception testE = new Exception();
		SdlDisconnectedReason testReason = SdlDisconnectedReason.DEFAULT;
		testOnProxyClosed = new OnProxyClosed(TestValues.GENERAL_STRING, testE, testReason);
		assertEquals(TestValues.MATCH, InternalProxyMessage.OnProxyClosed, testOnProxyClosed.getFunctionName());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testOnProxyClosed.getInfo());
		assertEquals(TestValues.MATCH, testE, testOnProxyClosed.getException());
		assertEquals(TestValues.MATCH, testReason, testOnProxyClosed.getReason());
		
		// Invalid/Null Tests
		testOnProxyClosed = new OnProxyClosed(null, null, null);
		assertEquals(TestValues.MATCH, InternalProxyMessage.OnProxyClosed, testOnProxyClosed.getFunctionName());
		assertNull(TestValues.NULL, testOnProxyClosed.getInfo());
		assertNull(TestValues.NULL, testOnProxyClosed.getException());
		assertNull(TestValues.NULL, testOnProxyClosed.getReason());
	}
}