package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.PresetBankCapabilities}
 */
public class PresetBankCapabilitiesTest extends TestCase {
	
	private PresetBankCapabilities msg;

	@Override
	public void setUp() {
		msg = new PresetBankCapabilities();
		
		msg.setOnScreenPresetsAvailable(TestValues.GENERAL_BOOLEAN);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		boolean presets = msg.onScreenPresetsAvailable();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, presets);
		
		// Invalid/Null Tests
		PresetBankCapabilities msg = new PresetBankCapabilities();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.onScreenPresetsAvailable());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(PresetBankCapabilities.KEY_ON_SCREEN_PRESETS_AVAILABLE, TestValues.GENERAL_BOOLEAN);

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