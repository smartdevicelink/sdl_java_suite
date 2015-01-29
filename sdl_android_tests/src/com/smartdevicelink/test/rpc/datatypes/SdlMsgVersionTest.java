package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.test.utils.JsonUtils;

public class SdlMsgVersionTest extends TestCase {

	private static final Integer MAJOR_VERSION = 0;
	private static final Integer MINOR_VERSION = 0;
	
	private SdlMsgVersion msg;

	@Override
	public void setUp() {
		msg = new SdlMsgVersion();

		msg.setMajorVersion(MAJOR_VERSION);
		msg.setMinorVersion(MINOR_VERSION);
	}

	public void testMajorVersion() {
		Integer copy = msg.getMajorVersion();
		
		assertEquals("Input value didn't match expected value.", MAJOR_VERSION, copy);
	}
	
	public void testMinorVersion() {
		Integer copy = msg.getMinorVersion();
		
		assertEquals("Input value didn't match expected value.", MINOR_VERSION, copy);
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SdlMsgVersion.KEY_MAJOR_VERSION, MAJOR_VERSION);
			reference.put(SdlMsgVersion.KEY_MINOR_VERSION, MINOR_VERSION);

			JSONObject underTest = msg.serializeJSON();

			assertEquals("JSON size didn't match expected size.",
					reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				assertEquals(
						"JSON value didn't match expected value for key \""
								+ key + "\".",
						JsonUtils.readObjectFromJsonObject(reference, key),
						JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch (JSONException e) {
			/* do nothing */
		}
	}

	public void testNull() {
		SdlMsgVersion msg = new SdlMsgVersion();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Major version wasn't set, but getter method returned an object.", msg.getMajorVersion());
		assertNull("Minor version wasn't set, but getter method returned an object.", msg.getMinorVersion());
	}
}
