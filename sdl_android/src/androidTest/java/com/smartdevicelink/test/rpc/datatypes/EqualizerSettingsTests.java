package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.EqualizerSettings;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.EqualizerSettings}
 */
public class EqualizerSettingsTests extends TestCase {

	private EqualizerSettings msg;

	@Override
	public void setUp() {
		msg = new EqualizerSettings();

		msg.setChannelId(Test.GENERAL_INT);
		msg.setChannelName(Test.GENERAL_STRING);
		msg.setChannelSetting(Test.GENERAL_INT);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values

		int channelId = msg.getChannelId();
		String channelName = msg.getChannelName();
		int channelSetting = msg.getChannelSetting();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_INT, channelId);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, channelName);
		assertEquals(Test.MATCH, Test.GENERAL_INT, channelSetting);

		// Invalid/Null Tests
		EqualizerSettings msg = new EqualizerSettings();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getChannelId());
		assertNull(Test.NULL, msg.getChannelName());
		assertNull(Test.NULL, msg.getChannelSetting());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {

			reference.put(EqualizerSettings.KEY_CHANNEL_ID, Test.GENERAL_INT);
			reference.put(EqualizerSettings.KEY_CHANNEL_NAME, Test.GENERAL_STRING);
			reference.put(EqualizerSettings.KEY_CHANNEL_SETTING, Test.GENERAL_INT);

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