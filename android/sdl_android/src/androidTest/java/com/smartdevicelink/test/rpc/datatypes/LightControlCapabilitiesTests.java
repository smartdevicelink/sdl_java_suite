package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.LightCapabilities;
import com.smartdevicelink.proxy.rpc.LightControlCapabilities;
import com.smartdevicelink.proxy.rpc.ModuleInfo;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
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

		msg.setModuleName(TestValues.GENERAL_STRING);
		msg.setSupportedLights(TestValues.GENERAL_LIGHTCAPABILITIES_LIST);
		msg.setModuleInfo(TestValues.GENERAL_MODULE_INFO);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		String moduleName = msg.getModuleName();
		List<LightCapabilities> supportedLights = msg.getSupportedLights();
		ModuleInfo info = msg.getModuleInfo();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, moduleName);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_LIGHTCAPABILITIES_LIST.size(), supportedLights.size());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_MODULE_INFO, info);

		assertTrue(TestValues.TRUE, Validator.validateLightCapabilitiesList(TestValues.GENERAL_LIGHTCAPABILITIES_LIST, supportedLights));

		// Invalid/Null Tests
		LightControlCapabilities msg = new LightControlCapabilities();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getModuleName());
		assertNull(TestValues.NULL, msg.getSupportedLights());
		assertNull(TestValues.NULL, msg.getModuleInfo());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(LightControlCapabilities.KEY_MODULE_NAME, TestValues.GENERAL_STRING);
			reference.put(LightControlCapabilities.KEY_SUPPORTED_LIGHTS, TestValues.GENERAL_LIGHTCAPABILITIES_LIST);
			reference.put(LightControlCapabilities.KEY_MODULE_INFO, TestValues.JSON_MODULE_INFO);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

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
				} else if(key.equals(LightControlCapabilities.KEY_MODULE_INFO)) {
					JSONObject o1 = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject o2 = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> h1 = JsonRPCMarshaller.deserializeJSONObject(o1);
					Hashtable<String, Object> h2 = JsonRPCMarshaller.deserializeJSONObject(o2);
					assertTrue(TestValues.TRUE, Validator.validateModuleInfo(new ModuleInfo(h1), new ModuleInfo(h2)));
				}
				else {
					assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}