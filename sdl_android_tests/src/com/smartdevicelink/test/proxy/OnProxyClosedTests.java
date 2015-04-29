package com.smartdevicelink.test.proxy;

import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;
import com.smartdevicelink.proxy.callbacks.OnProxyClosed;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;

import junit.framework.TestCase;

public class OnProxyClosedTests extends TestCase {
	
	public void testValues () {
		
		OnProxyClosed testOPC = new OnProxyClosed();
		assertEquals("Function name did not match expected value.",
					  InternalProxyMessage.OnProxyClosed,
					  testOPC.getFunctionName());
		
		String testInfo = "info";
		Exception testE = new Exception();
		SdlDisconnectedReason testReason = SdlDisconnectedReason.DEFAULT;
		testOPC = new OnProxyClosed(testInfo, testE, testReason);
		assertEquals("Function name did not match expected value.",
				  InternalProxyMessage.OnProxyClosed,
				  testOPC.getFunctionName());
		assertEquals("Info did not match expected value", testInfo, testOPC.getInfo());
		assertEquals("Exception did not match expected value", testE, testOPC.getException());
		assertEquals("Reason did not match expected value", testReason, testOPC.getReason());
		
		testOPC = new OnProxyClosed(null, null, null);
		assertEquals("Function name did not match expected value.",
				  InternalProxyMessage.OnProxyClosed,
				  testOPC.getFunctionName());
		assertNull("Info was not null", testOPC.getInfo());
		assertNull("Exception was not null", testOPC.getException());
		assertNull("Reason was not null", testOPC.getReason());
	}
}