package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.LightState;
import com.smartdevicelink.proxy.rpc.RGBColor;
import com.smartdevicelink.proxy.rpc.enums.LightName;
import com.smartdevicelink.proxy.rpc.enums.LightStatus;
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
 * {@link com.smartdevicelink.rpc.LightState}
 */
public class LightStateTests extends TestCase {

	private LightState msg;

	@Override
	public void setUp() {
		msg = new LightState();

		msg.setId(TestValues.GENERAL_LIGHTNAME);
		msg.setStatus(TestValues.GENERAL_LIGHTSTATUS);
		msg.setDensity(TestValues.GENERAL_FLOAT);
		msg.setColor(TestValues.GENERAL_RGBCOLOR);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		LightName id = msg.getId();
		LightStatus status = msg.getStatus();
		Float density = msg.getDensity();
		RGBColor color = msg.getColor();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_LIGHTNAME, id);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_LIGHTSTATUS, status);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_FLOAT, (float) density);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_RGBCOLOR, color);

		// Invalid/Null Tests
		LightState msg = new LightState();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getId());
		assertNull(TestValues.NULL, msg.getStatus());
		assertNull(TestValues.NULL, msg.getDensity());
		assertNull(TestValues.NULL, msg.getColor());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(LightState.KEY_ID, TestValues.GENERAL_LIGHTNAME);
			reference.put(LightState.KEY_STATUS, TestValues.GENERAL_LIGHTSTATUS);
			reference.put(LightState.KEY_DENSITY, TestValues.GENERAL_FLOAT);
			reference.put(LightState.KEY_COLOR, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_RGBCOLOR.getStore()));

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				if (key.equals(LightState.KEY_COLOR)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateRGBColor(new RGBColor(hashReference), new RGBColor(hashTest)));
				} else {
					assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}