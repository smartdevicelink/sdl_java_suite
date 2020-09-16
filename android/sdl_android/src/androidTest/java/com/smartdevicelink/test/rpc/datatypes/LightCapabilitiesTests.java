package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.LightCapabilities;
import com.smartdevicelink.proxy.rpc.enums.LightName;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.LightCapabilities}
 */
public class LightCapabilitiesTests extends TestCase {

	private LightCapabilities msg;

	@Override
	public void setUp() {
		msg = new LightCapabilities();

		msg.setName(TestValues.GENERAL_LIGHTNAME);
		msg.setDensityAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setRGBColorSpaceAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setStatusAvailable(TestValues.GENERAL_BOOLEAN);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		LightName name = msg.getName();
		Boolean densityAvailable = msg.getDensityAvailable();
		Boolean rgbColorSpaceAvailable = msg.getRGBColorSpaceAvailable();
		Boolean statusAvailable = msg.getStatusAvailable();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_LIGHTNAME, name);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) densityAvailable);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) rgbColorSpaceAvailable);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) statusAvailable);

		// Invalid/Null Tests
		LightCapabilities msg = new LightCapabilities();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getName());
		assertNull(TestValues.NULL, msg.getDensityAvailable());
		assertNull(TestValues.NULL, msg.getRGBColorSpaceAvailable());
		assertNull(TestValues.NULL, msg.getStatusAvailable());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(LightCapabilities.KEY_NAME, TestValues.GENERAL_LIGHTNAME);
			reference.put(LightCapabilities.KEY_DENSITY_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(LightCapabilities.KEY_RGB_COLOR_SPACE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(LightCapabilities.KEY_STATUS_AVAILABLE, TestValues.GENERAL_BOOLEAN);

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