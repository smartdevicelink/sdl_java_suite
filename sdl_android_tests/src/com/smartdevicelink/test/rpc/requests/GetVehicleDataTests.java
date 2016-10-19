package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetVehicleData;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.GetVehicleData}
 */
public class GetVehicleDataTests extends BaseRpcTests {
        
	@Override
    protected RPCMessage createMessage(){
        GetVehicleData msg = new GetVehicleData();

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
		msg.setVin(Test.GENERAL_BOOLEAN);
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
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GET_VEHICLE_DATA.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(GetVehicleData.KEY_SPEED, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_RPM, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_EXTERNAL_TEMPERATURE, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_FUEL_LEVEL, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_PRNDL, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_TIRE_PRESSURE, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_ENGINE_TORQUE, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_ODOMETER, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_GPS, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_FUEL_LEVEL_STATE, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_INSTANT_FUEL_CONSUMPTION, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_VIN, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_BELT_STATUS, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_BODY_INFORMATION, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_DEVICE_STATUS, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_DRIVER_BRAKING, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_WIPER_STATUS, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_HEAD_LAMP_STATUS, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_ACC_PEDAL_POSITION, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_STEERING_WHEEL_ANGLE, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_E_CALL_INFO, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_AIRBAG_STATUS, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_EMERGENCY_EVENT, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_CLUSTER_MODE_STATUS, Test.GENERAL_BOOLEAN);
            result.put(GetVehicleData.KEY_MY_KEY, Test.GENERAL_BOOLEAN);
        }catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }

        return result;
    }
    
    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {    	
    	// Valid Tests	
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getSpeed());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getRpm());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getExternalTemperature());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getFuelLevel());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getPrndl());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getTirePressure());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getEngineTorque());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getOdometer());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getGps());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getFuelLevelState());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getInstantFuelConsumption());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getVin());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getBeltStatus());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getBodyInformation());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getVin());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getDeviceStatus());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getDriverBraking());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getWiperStatus());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getHeadLampStatus());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getAccPedalPosition());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getSteeringWheelAngle());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getECallInfo());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getAirbagStatus());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getEmergencyEvent());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getClusterModeStatus());
		assertTrue(Test.TRUE, ( (GetVehicleData) msg ).getMyKey());
    
		// Invalid/Null Tests
        GetVehicleData msg = new GetVehicleData();
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
        assertNull(Test.NULL, msg.getVin());
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
			GetVehicleData cmd = new GetVehicleData(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_SPEED), cmd.getSpeed());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_RPM), cmd.getRpm());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_EXTERNAL_TEMPERATURE), cmd.getExternalTemperature());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_FUEL_LEVEL), cmd.getFuelLevel());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_VIN), cmd.getVin());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_PRNDL), cmd.getPrndl());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_TIRE_PRESSURE), cmd.getTirePressure());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_ENGINE_TORQUE), cmd.getEngineTorque());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_ODOMETER), cmd.getOdometer());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_GPS), cmd.getGps());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_FUEL_LEVEL_STATE), cmd.getFuelLevelState());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_INSTANT_FUEL_CONSUMPTION), cmd.getInstantFuelConsumption());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_BELT_STATUS), cmd.getBeltStatus());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_BODY_INFORMATION), cmd.getBodyInformation());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_DEVICE_STATUS), cmd.getDeviceStatus());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_DRIVER_BRAKING), cmd.getDriverBraking());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_WIPER_STATUS), cmd.getWiperStatus());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_HEAD_LAMP_STATUS), cmd.getHeadLampStatus());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_ACC_PEDAL_POSITION), cmd.getAccPedalPosition());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_STEERING_WHEEL_ANGLE), cmd.getSteeringWheelAngle());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_E_CALL_INFO), cmd.getECallInfo());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_AIRBAG_STATUS), cmd.getAirbagStatus());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_EMERGENCY_EVENT), cmd.getEmergencyEvent());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_CLUSTER_MODE_STATUS), cmd.getClusterModeStatus());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleData.KEY_MY_KEY), cmd.getMyKey());
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }
}