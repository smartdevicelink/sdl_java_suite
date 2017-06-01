package com.smartdevicelink.test.proxy;

import android.test.AndroidTestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.callbacks.InternalProxyMessage}
 */
public class InternalProxyMessageTests extends AndroidTestCase {
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.proxy.callbacks.InternalProxyMessage#InternalProxyMessage(String)}
	 */
	public void testValues () {
		// Valid Tests
		String test = mContext.getString(R.string.function_name);
		InternalProxyMessage testIPM = new InternalProxyMessage(test);
		assertEquals(Test.MATCH, test, testIPM.getFunctionName());
		
		test = mContext.getString(R.string.on_proxy_error);
		assertEquals(Test.MATCH, test, InternalProxyMessage.OnProxyError);		
		test = mContext.getString(R.string.on_proxy_opened);
		assertEquals(Test.MATCH, test, InternalProxyMessage.OnProxyOpened);		
		test = mContext.getString(R.string.on_proxy_closed);
		assertEquals(Test.MATCH, test, InternalProxyMessage.OnProxyClosed);
		
		// Invalid/Null Tests
		testIPM = new InternalProxyMessage(null);
		assertNull(Test.NULL, testIPM.getFunctionName());
	}
}