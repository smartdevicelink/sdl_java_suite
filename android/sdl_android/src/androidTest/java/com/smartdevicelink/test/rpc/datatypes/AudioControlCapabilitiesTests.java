package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AudioControlCapabilities;
import com.smartdevicelink.proxy.rpc.ModuleInfo;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
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

		msg.setModuleName(Test.GENERAL_STRING);
		msg.setSourceAvailable(Test.GENERAL_BOOLEAN);
		msg.setKeepContextAvailable(Test.GENERAL_BOOLEAN);
		msg.setVolumeAvailable(Test.GENERAL_BOOLEAN);
		msg.setEqualizerAvailable(Test.GENERAL_BOOLEAN);
		msg.setVolumeAvailable(Test.GENERAL_BOOLEAN);
		msg.setEqualizerMaxChannelId(Test.GENERAL_INT);
		msg.setModuleInfo(Test.GENERAL_MODULE_INFO);
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
		assertEquals(Test.MATCH, Test.GENERAL_STRING, moduleName);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) sourceAvailable);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) keepContextAvailable);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) volumeAvailable);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) equalizerAvailable);
		assertEquals(Test.MATCH, Test.GENERAL_INT, equalizerMaxChannelId);
		assertEquals(Test.MATCH, Test.GENERAL_MODULE_INFO, info);

		// Invalid/Null Tests
		AudioControlCapabilities msg = new AudioControlCapabilities();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getModuleName());
		assertNull(Test.NULL, msg.getSourceAvailable());
		assertNull(Test.NULL, msg.getKeepContextAvailable());
		assertNull(Test.NULL, msg.getVolumeAvailable());
		assertNull(Test.NULL, msg.getEqualizerAvailable());
		assertNull(Test.NULL, msg.getEqualizerMaxChannelId());
		assertNull(Test.NULL, msg.getModuleInfo());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {

			reference.put(AudioControlCapabilities.KEY_MODULE_NAME, Test.GENERAL_STRING);
			reference.put(AudioControlCapabilities.KEY_SOURCE_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(AudioControlCapabilities.KEY_KEEP_CONTEXT_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(AudioControlCapabilities.KEY_VOLUME_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(AudioControlCapabilities.KEY_EQUALIZER_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(AudioControlCapabilities.KEY_EQUALIZER_MAX_CHANNEL_ID, Test.GENERAL_INT);
			reference.put(AudioControlCapabilities.KEY_MODULE_INFO, Test.JSON_MODULE_INFO);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				if (key.equals(AudioControlCapabilities.KEY_MODULE_INFO)) {
					JSONObject o1 = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject o2 = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> h1 = JsonRPCMarshaller.deserializeJSONObject(o1);
					Hashtable<String, Object> h2 = JsonRPCMarshaller.deserializeJSONObject(o2);
					assertTrue(Test.TRUE, Validator.validateModuleInfo(new ModuleInfo(h1), new ModuleInfo(h2)));
				} else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}