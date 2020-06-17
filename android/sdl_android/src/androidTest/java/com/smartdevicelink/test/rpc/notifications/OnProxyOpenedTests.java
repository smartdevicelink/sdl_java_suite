package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;
import com.smartdevicelink.proxy.callbacks.OnProxyOpened;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.junit.Test;

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
	@Test
	public void testMethods () {		
		OnProxyOpened testOnProxyOpened = new OnProxyOpened();
		assertNotNull(TestValues.NOT_NULL, testOnProxyOpened);
		assertEquals(TestValues.MATCH, InternalProxyMessage.OnProxyOpened, testOnProxyOpened.getFunctionName());
	}
}