package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AudioControlData;
import com.smartdevicelink.proxy.rpc.EqualizerSettings;
import com.smartdevicelink.proxy.rpc.enums.PrimaryAudioSource;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.AudioControlData}
 */
public class AudioControlDataTests extends TestCase {

	private AudioControlData msg;

	@Override
	public void setUp() {
		msg = new AudioControlData();

		msg.setSource(Test.GENERAL_PRIMARYAUDIOSOURCE);
		msg.setKeepContext(Test.GENERAL_BOOLEAN);
		msg.setVolume(Test.GENERAL_INT);
		msg.setEqualizerSettings(Test.GENERAL_EQUALIZERSETTINGS_LIST);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		PrimaryAudioSource source = msg.getSource();
		Boolean keepContext = msg.getKeepContext();
		int volume = msg.getVolume();
		List<EqualizerSettings> equalizerSettings = msg.getEqualizerSettings();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_PRIMARYAUDIOSOURCE, source);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) keepContext);
		assertEquals(Test.MATCH, Test.GENERAL_INT, volume);
		assertEquals(Test.MATCH, Test.GENERAL_EQUALIZERSETTINGS_LIST.size(), equalizerSettings.size());

		assertTrue(Test.TRUE, Validator.validateEqualizerSettingsList(Test.GENERAL_EQUALIZERSETTINGS_LIST, equalizerSettings));

		// Invalid/Null Tests
		AudioControlData msg = new AudioControlData();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getSource());
		assertNull(Test.NULL, msg.getVolume());
		assertNull(Test.NULL, msg.getKeepContext());
		assertNull(Test.NULL, msg.getEqualizerSettings());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(AudioControlData.KEY_SOURCE, Test.GENERAL_PRIMARYAUDIOSOURCE);
			reference.put(AudioControlData.KEY_KEEP_CONTEXT, Test.GENERAL_BOOLEAN);
			reference.put(AudioControlData.KEY_VOLUME, Test.GENERAL_INT);
			reference.put(AudioControlData.KEY_EQUALIZER_SETTINGS, Test.GENERAL_EQUALIZERSETTINGS_LIST);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				if (key.equals(AudioControlData.KEY_EQUALIZER_SETTINGS)) {
					List<EqualizerSettings> esReference = (List<EqualizerSettings>) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONArray esArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					int i = 0;
					for (EqualizerSettings es : esReference) {
						assertTrue(Validator.validateEqualizerSettings(es, new EqualizerSettings(JsonRPCMarshaller.deserializeJSONObject(esArray.getJSONObject(i++)))));
					}
				} else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}

			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}