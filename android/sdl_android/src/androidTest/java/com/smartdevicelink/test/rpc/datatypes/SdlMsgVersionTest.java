package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SdlMsgVersion}
 */
public class SdlMsgVersionTest extends TestCase {
	
	private SdlMsgVersion msg;

	@Override
	public void setUp() {
		msg = new SdlMsgVersion();

		msg.setMajorVersion(TestValues.GENERAL_INT);
		msg.setMinorVersion(TestValues.GENERAL_INT);
		msg.setPatchVersion(TestValues.GENERAL_INT);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		Integer major = msg.getMajorVersion();
		Integer minor = msg.getMinorVersion();
		Integer patch = msg.getPatchVersion();

		// Valid Tests
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, major);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, minor);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, patch);

		// Invalid/Null Tests
		SdlMsgVersion msg = new SdlMsgVersion();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getMajorVersion());
		assertNull(TestValues.NULL, msg.getMinorVersion());
		assertNull(TestValues.NULL, msg.getPatchVersion());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SdlMsgVersion.KEY_MAJOR_VERSION, TestValues.GENERAL_INT);
			reference.put(SdlMsgVersion.KEY_MINOR_VERSION, TestValues.GENERAL_INT);
			reference.put(SdlMsgVersion.KEY_PATCH_VERSION, TestValues.GENERAL_INT);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}