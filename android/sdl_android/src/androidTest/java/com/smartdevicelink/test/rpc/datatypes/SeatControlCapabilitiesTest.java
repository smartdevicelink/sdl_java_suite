package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.SeatControlCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.SeatControlCapabilities}
 */
public class SeatControlCapabilitiesTest extends TestCase {

	private SeatControlCapabilities msg;

	@Override
	public void setUp() {
		msg = new SeatControlCapabilities();
		msg.setModuleName(Test.GENERAL_STRING);
		msg.setHeatingEnabledAvailable(Test.GENERAL_BOOLEAN);
		msg.setCoolingEnabledAvailable(Test.GENERAL_BOOLEAN);
		msg.setHeatingLevelAvailable(Test.GENERAL_BOOLEAN);
		msg.setCoolingLevelAvailable(Test.GENERAL_BOOLEAN);
		msg.setHorizontalPositionAvailable(Test.GENERAL_BOOLEAN);
		msg.setVerticalPositionAvailable(Test.GENERAL_BOOLEAN);
		msg.setFrontVerticalPositionAvailable(Test.GENERAL_BOOLEAN);
		msg.setBackVerticalPositionAvailable(Test.GENERAL_BOOLEAN);
		msg.setBackTiltAngleAvailable(Test.GENERAL_BOOLEAN);
		msg.setHeadSupportVerticalPositionAvailable(Test.GENERAL_BOOLEAN);
		msg.setHeadSupportHorizontalPositionAvailable(Test.GENERAL_BOOLEAN);
		msg.setMassageEnabledAvailable(Test.GENERAL_BOOLEAN);
		msg.setMassageModeAvailable(Test.GENERAL_BOOLEAN);
		msg.setMassageCushionFirmnessAvailable(Test.GENERAL_BOOLEAN);
		msg.setMemoryAvailable(Test.GENERAL_BOOLEAN);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		String moduleName = msg.getModuleName();
		Boolean heatingEnabledAvailable = msg.getHeatingEnabledAvailable();
		Boolean coolingEnabledAvailable = msg.getCoolingEnabledAvailable();
		Boolean heatingLevelAvailable = msg.getHeatingLevelAvailable();
		Boolean coolingLevelAvailable = msg.getCoolingLevelAvailable();
		Boolean horizontalPositionAvailable = msg.getHorizontalPositionAvailable();
		Boolean verticalPositionAvailable = msg.getVerticalPositionAvailable();
		Boolean frontVerticalPositionAvailable = msg.getFrontVerticalPositionAvailable();
		Boolean backVerticalPositionAvailable = msg.getBackVerticalPositionAvailable();
		Boolean backTiltAngleAvailable = msg.getBackTiltAngleAvailable();
		Boolean headSupportHorizontalPositionAvailable = msg.getHeadSupportHorizontalPositionAvailable();
		Boolean headSupportVerticalPositionAvailable = msg.getHeadSupportVerticalPositionAvailable();
		Boolean massageEnabledAvailable = msg.getMassageEnabledAvailable();
		Boolean massageModeAvailable = msg.getMassageModeAvailable();
		Boolean massageCushionFirmnessAvailable = msg.getMassageCushionFirmnessAvailable();

		Boolean memoryAvailable = msg.getMemoryAvailable();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, moduleName);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, heatingEnabledAvailable);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, coolingEnabledAvailable);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, heatingLevelAvailable);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, coolingLevelAvailable);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, horizontalPositionAvailable);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, verticalPositionAvailable);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, frontVerticalPositionAvailable);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, backVerticalPositionAvailable);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, backTiltAngleAvailable);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, headSupportHorizontalPositionAvailable);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, headSupportVerticalPositionAvailable);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, massageEnabledAvailable);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, massageModeAvailable);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, massageCushionFirmnessAvailable);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, memoryAvailable);

		// Invalid/Null Tests
		SeatControlCapabilities msg = new SeatControlCapabilities();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getModuleName());
		assertNull(Test.NULL, msg.getHeatingEnabledAvailable());
		assertNull(Test.NULL, msg.getCoolingEnabledAvailable());
		assertNull(Test.NULL, msg.getHeatingLevelAvailable());
		assertNull(Test.NULL, msg.getCoolingLevelAvailable());
		assertNull(Test.NULL, msg.getHorizontalPositionAvailable());
		assertNull(Test.NULL, msg.getVerticalPositionAvailable());
		assertNull(Test.NULL, msg.getFrontVerticalPositionAvailable());
		assertNull(Test.NULL, msg.getBackVerticalPositionAvailable());
		assertNull(Test.NULL, msg.getBackTiltAngleAvailable());
		assertNull(Test.NULL, msg.getHeadSupportHorizontalPositionAvailable());
		assertNull(Test.NULL, msg.getHeadSupportVerticalPositionAvailable());
		assertNull(Test.NULL, msg.getMassageEnabledAvailable());
		assertNull(Test.NULL, msg.getMassageModeAvailable());
		assertNull(Test.NULL, msg.getMassageCushionFirmnessAvailable());
		assertNull(Test.NULL, msg.getMemoryAvailable());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SeatControlCapabilities.KEY_MODULE_NAME, Test.GENERAL_STRING);
			reference.put(SeatControlCapabilities.KEY_HEATING_ENABLED_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_COOLING_ENABLED_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_HEATING_LEVEL_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_COOLING_LEVEL_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_HORIZONTAL_POSITION_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_VERTICAL_POSITION_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_FRONT_VERTICAL_POSITION_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_BACK_VERTICAL_POSITION_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_BACK_TILT_ANGLE_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_HEAD_SUPPORT_HORIZONTAL_POSITION_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_HEAD_SUPPORT_VERTICAL_POSITION_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_MASSAGE_ENABLED_AVAILABLE, Test.GENERAL_BOOLEAN);

			reference.put(SeatControlCapabilities.KEY_MASSAGE_MODE_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_MASSAGE_CUSHION_FIRMNESS_AVAILABLE, Test.GENERAL_BOOLEAN);

			reference.put(SeatControlCapabilities.KEY_MEMORY_AVAILABLE, Test.GENERAL_BOOLEAN);

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