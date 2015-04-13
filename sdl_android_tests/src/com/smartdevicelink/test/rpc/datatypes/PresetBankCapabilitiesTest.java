package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.test.utils.JsonUtils;

public class PresetBankCapabilitiesTest extends TestCase {

	private static final boolean PRESETS_AVAILABLE = true;
	
	private PresetBankCapabilities msg;

	@Override
	public void setUp() {
		msg = new PresetBankCapabilities();
		
		msg.setOnScreenPresetsAvailable(PRESETS_AVAILABLE);
	}

	public void testPresetsAvailable() {
		boolean copy = msg.onScreenPresetsAvailable();
		
		assertEquals("Input value didn't match expected value.", PRESETS_AVAILABLE, copy);
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(PresetBankCapabilities.KEY_ON_SCREEN_PRESETS_AVAILABLE, PRESETS_AVAILABLE);

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
		PresetBankCapabilities msg = new PresetBankCapabilities();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Presets available wasn't set, but getter method returned an object.", msg.onScreenPresetsAvailable());
	}
}
