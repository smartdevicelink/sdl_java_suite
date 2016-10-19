package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.PresetBankCapabilities}
 */
public class PresetBankCapabilitiesTest extends TestCase {
	
	private PresetBankCapabilities msg;

	@Override
	public void setUp() {
		msg = new PresetBankCapabilities();
		
		msg.setOnScreenPresetsAvailable(Test.GENERAL_BOOLEAN);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		boolean presets = msg.onScreenPresetsAvailable();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, presets);
		
		// Invalid/Null Tests
		PresetBankCapabilities msg = new PresetBankCapabilities();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.onScreenPresetsAvailable());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(PresetBankCapabilities.KEY_ON_SCREEN_PRESETS_AVAILABLE, Test.GENERAL_BOOLEAN);

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