package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.LightCapabilities;
import com.smartdevicelink.proxy.rpc.enums.LightName;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.LightCapabilities}
 */
public class LightCapabilitiesTests extends TestCase {

	private LightCapabilities msg;

	@Override
	public void setUp() {
		msg = new LightCapabilities();

		msg.setName(Test.GENERAL_LIGHTNAME);
		msg.setDensityAvailable(Test.GENERAL_BOOLEAN);
		msg.setRGBColorSpaceAvailable(Test.GENERAL_BOOLEAN);
		msg.setStatusAvailable(Test.GENERAL_BOOLEAN);
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
		assertEquals(Test.MATCH, Test.GENERAL_LIGHTNAME, name);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) densityAvailable);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) rgbColorSpaceAvailable);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) statusAvailable);

		// Invalid/Null Tests
		LightCapabilities msg = new LightCapabilities();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getName());
		assertNull(Test.NULL, msg.getDensityAvailable());
		assertNull(Test.NULL, msg.getRGBColorSpaceAvailable());
		assertNull(Test.NULL, msg.getStatusAvailable());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(LightCapabilities.KEY_NAME, Test.GENERAL_LIGHTNAME);
			reference.put(LightCapabilities.KEY_DENSITY_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(LightCapabilities.KEY_RGB_COLOR_SPACE_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(LightCapabilities.KEY_STATUS_AVAILABLE, Test.GENERAL_BOOLEAN);

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