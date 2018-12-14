package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.LightCapabilities;
import com.smartdevicelink.proxy.rpc.LightControlCapabilities;
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
 * {@link com.smartdevicelink.rpc.LightControlCapabilities}
 */
public class LightControlCapabilitiesTests extends TestCase {

	private LightControlCapabilities msg;

	@Override
	public void setUp() {
		msg = new LightControlCapabilities();

		msg.setModuleName(Test.GENERAL_STRING);
		msg.setSupportedLights(Test.GENERAL_LIGHTCAPABILITIES_LIST);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		String moduleName = msg.getModuleName();
		List<LightCapabilities> supportedLights = msg.getSupportedLights();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, moduleName);
		assertEquals(Test.MATCH, Test.GENERAL_LIGHTCAPABILITIES_LIST.size(), supportedLights.size());

		assertTrue(Test.TRUE, Validator.validateLightCapabilitiesList(Test.GENERAL_LIGHTCAPABILITIES_LIST, supportedLights));

		// Invalid/Null Tests
		LightControlCapabilities msg = new LightControlCapabilities();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getModuleName());
		assertNull(Test.NULL, msg.getSupportedLights());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(LightControlCapabilities.KEY_MODULE_NAME, Test.GENERAL_STRING);
			reference.put(LightControlCapabilities.KEY_SUPPORTED_LIGHTS, Test.GENERAL_LIGHTCAPABILITIES_LIST);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				if (key.equals(LightControlCapabilities.KEY_SUPPORTED_LIGHTS)) {
					List<LightCapabilities> lcReference = (List<LightCapabilities>) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONArray lsArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					int i = 0;
					for (LightCapabilities lc : lcReference) {
						assertTrue(Validator.validateLightCapabilities(lc, new LightCapabilities(JsonRPCMarshaller.deserializeJSONObject(lsArray.getJSONObject(i++)))));
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