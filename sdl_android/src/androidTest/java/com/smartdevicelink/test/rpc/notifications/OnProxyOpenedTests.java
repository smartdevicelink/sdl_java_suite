package com.smartdevicelink.test.rpc.notifications;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;
import com.smartdevicelink.proxy.callbacks.OnProxyOpened;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.callbacks.OnProxyOpened}
 */
public class OnProxyOpenedTests extends TestCase {
	

	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.proxy.callbacks.OnProxyOpened#OnProxyOpened()}
	 * {@link com.smartdevicelink.proxy.callbacks.OnProxyOpened#getFunctionName()}
	 */
	public void testMethods () {		
		OnProxyOpened testOnProxyOpened = new OnProxyOpened();
		assertNotNull(Test.NOT_NULL, testOnProxyOpened);
		assertEquals(Test.MATCH, InternalProxyMessage.OnProxyOpened, testOnProxyOpened.getFunctionName());		
	}
}