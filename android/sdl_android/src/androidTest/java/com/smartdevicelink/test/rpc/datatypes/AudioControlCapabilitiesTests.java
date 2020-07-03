package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AudioControlCapabilities;
import com.smartdevicelink.proxy.rpc.ModuleInfo;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.AudioControlCapabilities}
 */
public class AudioControlCapabilitiesTests extends TestCase {

	private AudioControlCapabilities msg;

	@Override
	public void setUp() {
		msg = new AudioControlCapabilities();

		msg.setModuleName(TestValues.GENERAL_STRING);
		msg.setSourceAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setKeepContextAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setVolumeAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setEqualizerAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setVolumeAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setEqualizerMaxChannelId(TestValues.GENERAL_INT);
		msg.setModuleInfo(TestValues.GENERAL_MODULE_INFO);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values

		String moduleName = msg.getModuleName();
		Boolean sourceAvailable = msg.getSourceAvailable();
		Boolean keepContextAvailable = msg.getKeepContextAvailable();
		Boolean volumeAvailable = msg.getVolumeAvailable();
		Boolean equalizerAvailable = msg.getEqualizerAvailable();
		int equalizerMaxChannelId = msg.getEqualizerMaxChannelId();
		ModuleInfo info = msg.getModuleInfo();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, moduleName);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) sourceAvailable);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) keepContextAvailable);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) volumeAvailable);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) equalizerAvailable);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, equalizerMaxChannelId);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_MODULE_INFO, info);

		// Invalid/Null Tests
		AudioControlCapabilities msg = new AudioControlCapabilities();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getModuleName());
		assertNull(TestValues.NULL, msg.getSourceAvailable());
		assertNull(TestValues.NULL, msg.getKeepContextAvailable());
		assertNull(TestValues.NULL, msg.getVolumeAvailable());
		assertNull(TestValues.NULL, msg.getEqualizerAvailable());
		assertNull(TestValues.NULL, msg.getEqualizerMaxChannelId());
		assertNull(TestValues.NULL, msg.getModuleInfo());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {

			reference.put(AudioControlCapabilities.KEY_MODULE_NAME, TestValues.GENERAL_STRING);
			reference.put(AudioControlCapabilities.KEY_SOURCE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(AudioControlCapabilities.KEY_KEEP_CONTEXT_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(AudioControlCapabilities.KEY_VOLUME_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(AudioControlCapabilities.KEY_EQUALIZER_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(AudioControlCapabilities.KEY_EQUALIZER_MAX_CHANNEL_ID, TestValues.GENERAL_INT);
			reference.put(AudioControlCapabilities.KEY_MODULE_INFO, TestValues.JSON_MODULE_INFO);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				if (key.equals(AudioControlCapabilities.KEY_MODULE_INFO)) {
					JSONObject o1 = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject o2 = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> h1 = JsonRPCMarshaller.deserializeJSONObject(o1);
					Hashtable<String, Object> h2 = JsonRPCMarshaller.deserializeJSONObject(o2);
					assertTrue(TestValues.TRUE, Validator.validateModuleInfo(new ModuleInfo(h1), new ModuleInfo(h2)));
				} else {
					assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}