package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleData;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.SubscribeVehicleData}
 */
public class SubscribeVehicleDataTests extends BaseRpcTests {
		
	@Override
	protected RPCMessage createMessage() {
		SubscribeVehicleData msg = new SubscribeVehicleData();

		msg.setSpeed(TestValues.GENERAL_BOOLEAN);
		msg.setRpm(TestValues.GENERAL_BOOLEAN);
		msg.setExternalTemperature(TestValues.GENERAL_BOOLEAN);
		msg.setFuelLevel(TestValues.GENERAL_BOOLEAN);
		msg.setPrndl(TestValues.GENERAL_BOOLEAN);
		msg.setTirePressure(TestValues.GENERAL_BOOLEAN);
		msg.setEngineTorque(TestValues.GENERAL_BOOLEAN);
		msg.setEngineOilLife(TestValues.GENERAL_BOOLEAN);
		msg.setOdometer(TestValues.GENERAL_BOOLEAN);
		msg.setGps(TestValues.GENERAL_BOOLEAN);
		msg.setFuelLevelState(TestValues.GENERAL_BOOLEAN);
		msg.setInstantFuelConsumption(TestValues.GENERAL_BOOLEAN);
		msg.setBeltStatus(TestValues.GENERAL_BOOLEAN);
		msg.setBodyInformation(TestValues.GENERAL_BOOLEAN);
		msg.setDeviceStatus(TestValues.GENERAL_BOOLEAN);
		msg.setDriverBraking(TestValues.GENERAL_BOOLEAN);
		msg.setWiperStatus(TestValues.GENERAL_BOOLEAN);
		msg.setHeadLampStatus(TestValues.GENERAL_BOOLEAN);
		msg.setAccPedalPosition(TestValues.GENERAL_BOOLEAN);
		msg.setSteeringWheelAngle(TestValues.GENERAL_BOOLEAN);
		msg.setECallInfo(TestValues.GENERAL_BOOLEAN);
		msg.setAirbagStatus(TestValues.GENERAL_BOOLEAN);
		msg.setEmergencyEvent(TestValues.GENERAL_BOOLEAN);
		msg.setClusterModeStatus(TestValues.GENERAL_BOOLEAN);
		msg.setMyKey(TestValues.GENERAL_BOOLEAN);
		msg.setFuelRange(TestValues.GENERAL_BOOLEAN);
		msg.setTurnSignal(TestValues.GENERAL_BOOLEAN);
		msg.setElectronicParkBrakeStatus(TestValues.GENERAL_BOOLEAN);
		msg.setHandsOffSteering(TestValues.GENERAL_BOOLEAN);
		msg.setWindowStatus(TestValues.GENERAL_BOOLEAN);
		msg.setOEMCustomVehicleData(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME, TestValues.GENERAL_BOOLEAN);
		
		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SUBSCRIBE_VEHICLE_DATA.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
            result.put(SubscribeVehicleData.KEY_SPEED, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_RPM, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_EXTERNAL_TEMPERATURE, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_FUEL_LEVEL, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_PRNDL, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_TIRE_PRESSURE, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_ENGINE_TORQUE, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_ENGINE_OIL_LIFE, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_ODOMETER, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_GPS, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_FUEL_LEVEL_STATE, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_INSTANT_FUEL_CONSUMPTION, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_BELT_STATUS, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_BODY_INFORMATION, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_DEVICE_STATUS, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_DRIVER_BRAKING, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_WIPER_STATUS, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_HEAD_LAMP_STATUS, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_ACC_PEDAL_POSITION, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_STEERING_WHEEL_ANGLE, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_E_CALL_INFO, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_AIRBAG_STATUS, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_EMERGENCY_EVENT, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_CLUSTER_MODE_STATUS, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_MY_KEY, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_FUEL_RANGE, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_TURN_SIGNAL, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_ELECTRONIC_PARK_BRAKE_STATUS, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_HANDS_OFF_STEERING, TestValues.GENERAL_BOOLEAN);
            result.put(SubscribeVehicleData.KEY_WINDOW_STATUS, TestValues.GENERAL_BOOLEAN);
            result.put(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME, TestValues.GENERAL_BOOLEAN);
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	@Test
    public void testRpcValues () {
    	
    	// Valid Tests
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getSpeed());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getRpm());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getExternalTemperature());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getFuelLevel());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getPrndl());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getTirePressure());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getEngineTorque());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getEngineOilLife());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getOdometer());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getGps());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getFuelLevelState());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getInstantFuelConsumption());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getBeltStatus());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getBodyInformation());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getDeviceStatus());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getDriverBraking());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getWiperStatus());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getHeadLampStatus());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getAccPedalPosition());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getSteeringWheelAngle());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getECallInfo());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getAirbagStatus());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getEmergencyEvent());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getClusterModeStatus());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getMyKey());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getFuelRange());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getTurnSignal());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getElectronicParkBrakeStatus());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getHandsOffSteering());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getWindowStatus());
		assertTrue(TestValues.MATCH,( (SubscribeVehicleData) msg ).getOEMCustomVehicleData(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME));
    
		// Invalid/Null Tests
		SubscribeVehicleData msg = new SubscribeVehicleData();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

        assertNull(TestValues.NULL, msg.getAccPedalPosition());
        assertNull(TestValues.NULL, msg.getAirbagStatus());
        assertNull(TestValues.NULL, msg.getBeltStatus());
        assertNull(TestValues.NULL, msg.getDriverBraking());
        assertNull(TestValues.NULL, msg.getFuelLevel());
        assertNull(TestValues.NULL, msg.getTirePressure());
        assertNull(TestValues.NULL, msg.getWiperStatus());
        assertNull(TestValues.NULL, msg.getGps());
        assertNull(TestValues.NULL, msg.getSpeed());
        assertNull(TestValues.NULL, msg.getRpm());
        assertNull(TestValues.NULL, msg.getFuelLevelState());
        assertNull(TestValues.NULL, msg.getInstantFuelConsumption());
        assertNull(TestValues.NULL, msg.getExternalTemperature());
        assertNull(TestValues.NULL, msg.getPrndl());
        assertNull(TestValues.NULL, msg.getOdometer());
        assertNull(TestValues.NULL, msg.getBodyInformation());
        assertNull(TestValues.NULL, msg.getDeviceStatus());
        assertNull(TestValues.NULL, msg.getHeadLampStatus());
        assertNull(TestValues.NULL, msg.getEngineTorque());
        assertNull(TestValues.NULL, msg.getEngineOilLife());
        assertNull(TestValues.NULL, msg.getSteeringWheelAngle());
        assertNull(TestValues.NULL, msg.getECallInfo());
        assertNull(TestValues.NULL, msg.getEmergencyEvent());
        assertNull(TestValues.NULL, msg.getClusterModeStatus());
        assertNull(TestValues.NULL, msg.getMyKey());
        assertNull(TestValues.NULL, msg.getFuelRange());
        assertNull(TestValues.NULL, msg.getTurnSignal());
        assertNull(TestValues.NULL, msg.getElectronicParkBrakeStatus());
        assertNull(TestValues.NULL, msg.getHandsOffSteering());
        assertNull(TestValues.NULL, msg.getWindowStatus());
        assertNull(TestValues.NULL, msg.getOEMCustomVehicleData(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME));
	}
	
    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getTargetContext(), getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SubscribeVehicleData cmd = new SubscribeVehicleData(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_SPEED), cmd.getSpeed());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_RPM), cmd.getRpm());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_EXTERNAL_TEMPERATURE), cmd.getExternalTemperature());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_FUEL_LEVEL), cmd.getFuelLevel());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_PRNDL), cmd.getPrndl());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_TIRE_PRESSURE), cmd.getTirePressure());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_ENGINE_TORQUE), cmd.getEngineTorque());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_ENGINE_OIL_LIFE), cmd.getEngineOilLife());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_ODOMETER), cmd.getOdometer());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_GPS), cmd.getGps());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_FUEL_LEVEL_STATE), cmd.getFuelLevelState());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_INSTANT_FUEL_CONSUMPTION), cmd.getInstantFuelConsumption());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_BELT_STATUS), cmd.getBeltStatus());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_BODY_INFORMATION), cmd.getBodyInformation());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_DEVICE_STATUS), cmd.getDeviceStatus());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_DRIVER_BRAKING), cmd.getDriverBraking());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_WIPER_STATUS), cmd.getWiperStatus());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_HEAD_LAMP_STATUS), cmd.getHeadLampStatus());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_ACC_PEDAL_POSITION), cmd.getAccPedalPosition());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_STEERING_WHEEL_ANGLE), cmd.getSteeringWheelAngle());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_E_CALL_INFO), cmd.getECallInfo());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_AIRBAG_STATUS), cmd.getAirbagStatus());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_EMERGENCY_EVENT), cmd.getEmergencyEvent());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_CLUSTER_MODE_STATUS), cmd.getClusterModeStatus());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_MY_KEY), cmd.getMyKey());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_FUEL_RANGE), cmd.getFuelRange());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_TURN_SIGNAL), cmd.getTurnSignal());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_ELECTRONIC_PARK_BRAKE_STATUS), cmd.getElectronicParkBrakeStatus());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_HANDS_OFF_STEERING), cmd.getHandsOffSteering());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, SubscribeVehicleData.KEY_WINDOW_STATUS), cmd.getWindowStatus());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME), cmd.getOEMCustomVehicleData(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME));
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}    	
    }
}