package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.HMISettingsControlData;
import com.smartdevicelink.proxy.rpc.enums.DisplayMode;
import com.smartdevicelink.proxy.rpc.enums.DistanceUnit;
import com.smartdevicelink.proxy.rpc.enums.TemperatureUnit;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

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

		msg.setDisplayMode(Test.GENERAL_DISPLAYMODE);
		msg.setTemperatureUnit(Test.GENERAL_TEMPERATUREUNIT);
		msg.setDistanceUnit(Test.GENERAL_DISTANCEUNIT);
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
		assertEquals(Test.MATCH, Test.GENERAL_DISPLAYMODE, displayMode);
		assertEquals(Test.MATCH, Test.GENERAL_TEMPERATUREUNIT, temperatureUnit);
		assertEquals(Test.MATCH, Test.GENERAL_DISTANCEUNIT, distanceUnit);

		// Invalid/Null Tests
		HMISettingsControlData msg = new HMISettingsControlData();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getDisplayMode());
		assertNull(Test.NULL, msg.getTemperatureUnit());
		assertNull(Test.NULL, msg.getDistanceUnit());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(HMISettingsControlData.KEY_DISPLAY_MODE, Test.GENERAL_DISPLAYMODE);
			reference.put(HMISettingsControlData.KEY_TEMPERATURE_UNIT, Test.GENERAL_TEMPERATUREUNIT);
			reference.put(HMISettingsControlData.KEY_DISTANCE_UNIT, Test.GENERAL_DISTANCEUNIT);

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