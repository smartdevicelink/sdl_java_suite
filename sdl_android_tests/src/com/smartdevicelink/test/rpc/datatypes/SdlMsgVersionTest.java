package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SdlMsgVersion}
 */
public class SdlMsgVersionTest extends TestCase {
	
	private SdlMsgVersion msg;

	@Override
	public void setUp() {
		msg = new SdlMsgVersion();

		msg.setMajorVersion(Test.GENERAL_INT);
		msg.setMinorVersion(Test.GENERAL_INT);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		Integer major = msg.getMajorVersion();
		Integer minor = msg.getMinorVersion();
		
		// Valid Tests
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, major);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, minor);
		
		// Invalid/Null Tests
		SdlMsgVersion msg = new SdlMsgVersion();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getMajorVersion());
		assertNull(Test.NULL, msg.getMinorVersion());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SdlMsgVersion.KEY_MAJOR_VERSION, Test.GENERAL_INT);
			reference.put(SdlMsgVersion.KEY_MINOR_VERSION, Test.GENERAL_INT);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}