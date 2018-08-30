package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.LightControlData;
import com.smartdevicelink.proxy.rpc.LightState;
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
 * {@link com.smartdevicelink.rpc.LightControlData}
 */
public class LightControlDataTests extends TestCase {

	private LightControlData msg;

	@Override
	public void setUp() {
		msg = new LightControlData();

		msg.setLightState(Test.GENERAL_LIGHTSTATE_LIST);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		List<LightState> lightState = msg.getLightState();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_LIGHTSTATE_LIST.size(), lightState.size());

		assertTrue(Test.TRUE, Validator.validateLightStateList(Test.GENERAL_LIGHTSTATE_LIST, lightState));

		// Invalid/Null Tests
		LightControlData msg = new LightControlData();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getLightState());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(LightControlData.KEY_LIGHT_STATE, Test.GENERAL_LIGHTSTATE_LIST);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				if (key.equals(LightControlData.KEY_LIGHT_STATE)) {
					List<LightState> lsReference = (List<LightState>) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONArray lsArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					int i = 0;
					for (LightState ls : lsReference) {
						assertTrue(Validator.validateLightState(ls, new LightState(JsonRPCMarshaller.deserializeJSONObject(lsArray.getJSONObject(i++)))));
					}
				}
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}