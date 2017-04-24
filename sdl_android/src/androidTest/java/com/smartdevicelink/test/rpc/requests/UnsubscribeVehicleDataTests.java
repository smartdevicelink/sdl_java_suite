package com.smartdevicelink.test.rpc.requests;


import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleData;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.UnsubscribeVehicleData}
 */
public class UnsubscribeVehicleDataTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		UnsubscribeVehicleData msg = new UnsubscribeVehicleData();

		msg.setSpeed(Test.GENERAL_BOOLEAN);
		msg.setRpm(Test.GENERAL_BOOLEAN);
		msg.setExternalTemperature(Test.GENERAL_BOOLEAN);
		msg.setFuelLevel(Test.GENERAL_BOOLEAN);
		msg.setPrndl(Test.GENERAL_BOOLEAN);
		msg.setTirePressure(Test.GENERAL_BOOLEAN);
		msg.setEngineTorque(Test.GENERAL_BOOLEAN);
		msg.setOdometer(Test.GENERAL_BOOLEAN);
		msg.setGps(Test.GENERAL_BOOLEAN);
		msg.setFuelLevelState(Test.GENERAL_BOOLEAN);
		msg.setInstantFuelConsumption(Test.GENERAL_BOOLEAN);
		msg.setBeltStatus(Test.GENERAL_BOOLEAN);
		msg.setBodyInformation(Test.GENERAL_BOOLEAN);
		msg.setDeviceStatus(Test.GENERAL_BOOLEAN);
		msg.setDriverBraking(Test.GENERAL_BOOLEAN);
		msg.setWiperStatus(Test.GENERAL_BOOLEAN);
		msg.setHeadLampStatus(Test.GENERAL_BOOLEAN);
		msg.setAccPedalPosition(Test.GENERAL_BOOLEAN);
		msg.setSteeringWheelAngle(Test.GENERAL_BOOLEAN);
		msg.setECallInfo(Test.GENERAL_BOOLEAN);
		msg.setAirbagStatus(Test.GENERAL_BOOLEAN);
		msg.setEmergencyEvent(Test.GENERAL_BOOLEAN);
		msg.setClusterModeStatus(Test.GENERAL_BOOLEAN);
		msg.setMyKey(Test.GENERAL_BOOLEAN);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.UNSUBSCRIBE_VEHICLE_DATA.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
            result.put(UnsubscribeVehicleData.KEY_SPEED, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_RPM, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_EXTERNAL_TEMPERATURE, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_FUEL_LEVEL, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_PRNDL, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_TIRE_PRESSURE, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_ENGINE_TORQUE, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_ODOMETER, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_GPS, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_FUEL_LEVEL_STATE, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_INSTANT_FUEL_CONSUMPTION, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_BELT_STATUS, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_BODY_INFORMATION, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_DEVICE_STATUS, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_DRIVER_BRAKING, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_WIPER_STATUS, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_HEAD_LAMP_STATUS, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_ACC_PEDAL_POSITION, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_STEERING_WHEEL_ANGLE, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_E_CALL_INFO, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_AIRBAG_STATUS, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_EMERGENCY_EVENT, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_CLUSTER_MODE_STATUS, Test.GENERAL_BOOLEAN);
            result.put(UnsubscribeVehicleData.KEY_MY_KEY, Test.GENERAL_BOOLEAN);            
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues(){		
		// Valid Tests
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getSpeed());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getRpm());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getExternalTemperature());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getFuelLevel());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getPrndl());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getTirePressure());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getEngineTorque());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getOdometer());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getGps());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getFuelLevelState());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getInstantFuelConsumption());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getBeltStatus());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getBodyInformation());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getDeviceStatus());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getDriverBraking());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getWiperStatus());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getHeadLampStatus());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getAccPedalPosition());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getSteeringWheelAngle());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getECallInfo());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getAirbagStatus());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getEmergencyEvent());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getClusterModeStatus());
		assertTrue(Test.TRUE,( (UnsubscribeVehicleData) msg ).getMyKey());
		
		// Invalid/Null Tests
		UnsubscribeVehicleData msg = new UnsubscribeVehicleData();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);
		
		assertNull(Test.NULL, msg.getAccPedalPosition());
        assertNull(Test.NULL, msg.getAirbagStatus());
        assertNull(Test.NULL, msg.getBeltStatus());
        assertNull(Test.NULL, msg.getDriverBraking());
        assertNull(Test.NULL, msg.getFuelLevel());
        assertNull(Test.NULL, msg.getTirePressure());
        assertNull(Test.NULL, msg.getWiperStatus());
        assertNull(Test.NULL, msg.getGps());
        assertNull(Test.NULL, msg.getSpeed());
        assertNull(Test.NULL, msg.getRpm());
        assertNull(Test.NULL, msg.getFuelLevelState());
        assertNull(Test.NULL, msg.getInstantFuelConsumption());
        assertNull(Test.NULL, msg.getExternalTemperature());
        assertNull(Test.NULL, msg.getPrndl());
        assertNull(Test.NULL, msg.getOdometer());
        assertNull(Test.NULL, msg.getBodyInformation());
        assertNull(Test.NULL, msg.getDeviceStatus());
        assertNull(Test.NULL, msg.getHeadLampStatus());
        assertNull(Test.NULL, msg.getEngineTorque());
        assertNull(Test.NULL, msg.getSteeringWheelAngle());
        assertNull(Test.NULL, msg.getECallInfo());
        assertNull(Test.NULL, msg.getEmergencyEvent());
        assertNull(Test.NULL, msg.getClusterModeStatus());
        assertNull(Test.NULL, msg.getMyKey());		
    }

	/**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			UnsubscribeVehicleData cmd = new UnsubscribeVehicleData(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_SPEED), cmd.getSpeed());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_RPM), cmd.getRpm());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_EXTERNAL_TEMPERATURE), cmd.getExternalTemperature());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_FUEL_LEVEL), cmd.getFuelLevel());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_PRNDL), cmd.getPrndl());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_TIRE_PRESSURE), cmd.getTirePressure());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_ENGINE_TORQUE), cmd.getEngineTorque());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_ODOMETER), cmd.getOdometer());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_GPS), cmd.getGps());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_FUEL_LEVEL_STATE), cmd.getFuelLevelState());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_INSTANT_FUEL_CONSUMPTION), cmd.getInstantFuelConsumption());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_BELT_STATUS), cmd.getBeltStatus());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_BODY_INFORMATION), cmd.getBodyInformation());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_DEVICE_STATUS), cmd.getDeviceStatus());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_DRIVER_BRAKING), cmd.getDriverBraking());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_WIPER_STATUS), cmd.getWiperStatus());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_HEAD_LAMP_STATUS), cmd.getHeadLampStatus());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_ACC_PEDAL_POSITION), cmd.getAccPedalPosition());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_STEERING_WHEEL_ANGLE), cmd.getSteeringWheelAngle());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_E_CALL_INFO), cmd.getECallInfo());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_AIRBAG_STATUS), cmd.getAirbagStatus());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_EMERGENCY_EVENT), cmd.getEmergencyEvent());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_CLUSTER_MODE_STATUS), cmd.getClusterModeStatus());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_MY_KEY), cmd.getMyKey());			
		} 
		catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }	
}