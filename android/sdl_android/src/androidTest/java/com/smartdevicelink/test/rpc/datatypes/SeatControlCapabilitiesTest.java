package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.SeatControlCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

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
		msg.setModuleName(TestValues.GENERAL_STRING);
		msg.setHeatingEnabledAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setCoolingEnabledAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setHeatingLevelAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setCoolingLevelAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setHorizontalPositionAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setVerticalPositionAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setFrontVerticalPositionAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setBackVerticalPositionAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setBackTiltAngleAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setHeadSupportVerticalPositionAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setHeadSupportHorizontalPositionAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setMassageEnabledAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setMassageModeAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setMassageCushionFirmnessAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setMemoryAvailable(TestValues.GENERAL_BOOLEAN);
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
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, moduleName);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, heatingEnabledAvailable);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, coolingEnabledAvailable);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, heatingLevelAvailable);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, coolingLevelAvailable);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, horizontalPositionAvailable);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, verticalPositionAvailable);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, frontVerticalPositionAvailable);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, backVerticalPositionAvailable);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, backTiltAngleAvailable);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, headSupportHorizontalPositionAvailable);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, headSupportVerticalPositionAvailable);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, massageEnabledAvailable);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, massageModeAvailable);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, massageCushionFirmnessAvailable);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, memoryAvailable);

		// Invalid/Null Tests
		SeatControlCapabilities msg = new SeatControlCapabilities();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getModuleName());
		assertNull(TestValues.NULL, msg.getHeatingEnabledAvailable());
		assertNull(TestValues.NULL, msg.getCoolingEnabledAvailable());
		assertNull(TestValues.NULL, msg.getHeatingLevelAvailable());
		assertNull(TestValues.NULL, msg.getCoolingLevelAvailable());
		assertNull(TestValues.NULL, msg.getHorizontalPositionAvailable());
		assertNull(TestValues.NULL, msg.getVerticalPositionAvailable());
		assertNull(TestValues.NULL, msg.getFrontVerticalPositionAvailable());
		assertNull(TestValues.NULL, msg.getBackVerticalPositionAvailable());
		assertNull(TestValues.NULL, msg.getBackTiltAngleAvailable());
		assertNull(TestValues.NULL, msg.getHeadSupportHorizontalPositionAvailable());
		assertNull(TestValues.NULL, msg.getHeadSupportVerticalPositionAvailable());
		assertNull(TestValues.NULL, msg.getMassageEnabledAvailable());
		assertNull(TestValues.NULL, msg.getMassageModeAvailable());
		assertNull(TestValues.NULL, msg.getMassageCushionFirmnessAvailable());
		assertNull(TestValues.NULL, msg.getMemoryAvailable());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SeatControlCapabilities.KEY_MODULE_NAME, TestValues.GENERAL_STRING);
			reference.put(SeatControlCapabilities.KEY_HEATING_ENABLED_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_COOLING_ENABLED_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_HEATING_LEVEL_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_COOLING_LEVEL_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_HORIZONTAL_POSITION_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_VERTICAL_POSITION_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_FRONT_VERTICAL_POSITION_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_BACK_VERTICAL_POSITION_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_BACK_TILT_ANGLE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_HEAD_SUPPORT_HORIZONTAL_POSITION_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_HEAD_SUPPORT_VERTICAL_POSITION_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_MASSAGE_ENABLED_AVAILABLE, TestValues.GENERAL_BOOLEAN);

			reference.put(SeatControlCapabilities.KEY_MASSAGE_MODE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SeatControlCapabilities.KEY_MASSAGE_CUSHION_FIRMNESS_AVAILABLE, TestValues.GENERAL_BOOLEAN);

			reference.put(SeatControlCapabilities.KEY_MEMORY_AVAILABLE, TestValues.GENERAL_BOOLEAN);

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