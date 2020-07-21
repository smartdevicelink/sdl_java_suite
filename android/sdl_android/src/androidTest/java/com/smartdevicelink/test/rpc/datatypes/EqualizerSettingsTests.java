package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.EqualizerSettings;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

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

		msg.setChannelId(TestValues.GENERAL_INT);
		msg.setChannelName(TestValues.GENERAL_STRING);
		msg.setChannelSetting(TestValues.GENERAL_INT);
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
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, channelId);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, channelName);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, channelSetting);

		// Invalid/Null Tests
		EqualizerSettings msg = new EqualizerSettings();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getChannelId());
		assertNull(TestValues.NULL, msg.getChannelName());
		assertNull(TestValues.NULL, msg.getChannelSetting());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {

			reference.put(EqualizerSettings.KEY_CHANNEL_ID, TestValues.GENERAL_INT);
			reference.put(EqualizerSettings.KEY_CHANNEL_NAME, TestValues.GENERAL_STRING);
			reference.put(EqualizerSettings.KEY_CHANNEL_SETTING, TestValues.GENERAL_INT);

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