package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.HMISettingsControlData;
import com.smartdevicelink.proxy.rpc.enums.DisplayMode;
import com.smartdevicelink.proxy.rpc.enums.DistanceUnit;
import com.smartdevicelink.proxy.rpc.enums.TemperatureUnit;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.HMISettingsControlData}
 */
public class HMISettingsControlDataTests extends TestCase {

	private HMISettingsControlData msg;

	@Override
	public void setUp() {
		msg = new HMISettingsControlData();

		msg.setDisplayMode(TestValues.GENERAL_DISPLAYMODE);
		msg.setTemperatureUnit(TestValues.GENERAL_TEMPERATUREUNIT);
		msg.setDistanceUnit(TestValues.GENERAL_DISTANCEUNIT);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		DisplayMode displayMode = msg.getDisplayMode();
		TemperatureUnit temperatureUnit = msg.getTemperatureUnit();
		DistanceUnit distanceUnit = msg.getDistanceUnit();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_DISPLAYMODE, displayMode);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_TEMPERATUREUNIT, temperatureUnit);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_DISTANCEUNIT, distanceUnit);

		// Invalid/Null Tests
		HMISettingsControlData msg = new HMISettingsControlData();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getDisplayMode());
		assertNull(TestValues.NULL, msg.getTemperatureUnit());
		assertNull(TestValues.NULL, msg.getDistanceUnit());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(HMISettingsControlData.KEY_DISPLAY_MODE, TestValues.GENERAL_DISPLAYMODE);
			reference.put(HMISettingsControlData.KEY_TEMPERATURE_UNIT, TestValues.GENERAL_TEMPERATUREUNIT);
			reference.put(HMISettingsControlData.KEY_DISTANCE_UNIT, TestValues.GENERAL_DISTANCEUNIT);

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