package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.MediaServiceManifest;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.MediaServiceManifest}
 */
public class MediaServiceManifestTests extends TestCase {

	@Override
	public void setUp(){
		// nothing to setup yet, this class doesn't contain anything
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values

		// Invalid/Null Tests
		MediaServiceManifest msg = new MediaServiceManifest();
		assertNotNull(Test.NOT_NULL, msg);
	}

}