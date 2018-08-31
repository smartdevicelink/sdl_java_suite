package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.HMISettingsControlCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.HMISettingsControlCapabilities}
 */
public class HMISettingsControlCapabilitiesTests extends TestCase {

	private HMISettingsControlCapabilities msg;

	@Override
	public void setUp() {
		msg = new HMISettingsControlCapabilities();

		msg.setModuleName(Test.GENERAL_STRING);
		msg.setDistanceUnitAvailable(Test.GENERAL_BOOLEAN);
		msg.setTemperatureUnitAvailable(Test.GENERAL_BOOLEAN);
		msg.setDisplayModeUnitAvailable(Test.GENERAL_BOOLEAN);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		String moduleName = msg.getModuleName();
		Boolean distanceUnitAvailable = msg.getDistanceUnitAvailable();
		Boolean temperatureUnitAvailable = msg.getTemperatureUnitAvailable();
		Boolean displayModeUnitAvailable = msg.getDisplayModeUnitAvailable();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, moduleName);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) distanceUnitAvailable);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) temperatureUnitAvailable);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) displayModeUnitAvailable);

		// Invalid/Null Tests
		HMISettingsControlCapabilities msg = new HMISettingsControlCapabilities();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getModuleName());
		assertNull(Test.NULL, msg.getDistanceUnitAvailable());
		assertNull(Test.NULL, msg.getTemperatureUnitAvailable());
		assertNull(Test.NULL, msg.getDisplayModeUnitAvailable());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(HMISettingsControlCapabilities.KEY_MODULE_NAME, Test.GENERAL_STRING);
			reference.put(HMISettingsControlCapabilities.KEY_DISTANCE_UNIT_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(HMISettingsControlCapabilities.KEY_TEMPERATURE_UNIT_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(HMISettingsControlCapabilities.KEY_DISPLAY_MODE_UNIT_AVAILABLE, Test.GENERAL_BOOLEAN);

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