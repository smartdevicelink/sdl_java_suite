package com.smartdevicelink.test.rpc.responses;


import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.VehicleDataResult;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.UnsubscribeVehicleDataResponse}
 */
public class UnsubscribeVehicleDataResponseTest extends BaseRpcTests {

	// TODO : Test different result codes.
	
	@Override
	protected RPCMessage createMessage() {
		UnsubscribeVehicleDataResponse msg = new UnsubscribeVehicleDataResponse();

		msg.setSpeed(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_SPEED.ordinal()));
		msg.setRpm(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_RPM.ordinal()));
		msg.setExternalTemperature(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_EXTERNTEMP.ordinal()));
		msg.setFuelLevel(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_FUELLEVEL.ordinal()));
		msg.setPrndl(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_PRNDL.ordinal()));
		msg.setTirePressure(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_TIREPRESSURE.ordinal()));
		msg.setEngineTorque(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ENGINETORQUE.ordinal()));
		msg.setOdometer(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ODOMETER.ordinal()));
		msg.setGps(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_GPS.ordinal()));
		msg.setFuelLevelState(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_FUELLEVEL_STATE.ordinal()));
		msg.setInstantFuelConsumption(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_FUELCONSUMPTION.ordinal()));
		msg.setBeltStatus(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_BELTSTATUS.ordinal()));
		msg.setBodyInformation(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_BODYINFO.ordinal()));
		msg.setDeviceStatus(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_DEVICESTATUS.ordinal()));
		msg.setDriverBraking(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_BRAKING.ordinal()));
		msg.setWiperStatus(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_WIPERSTATUS.ordinal()));
		msg.setHeadLampStatus(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_HEADLAMPSTATUS.ordinal()));
		msg.setAccPedalPosition(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ACCPEDAL.ordinal()));
		msg.setSteeringWheelAngle(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_STEERINGWHEEL.ordinal()));
		msg.setECallInfo(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ECALLINFO.ordinal()));
		msg.setAirbagStatus(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_AIRBAGSTATUS.ordinal()));
		msg.setEmergencyEvent(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_EMERGENCYEVENT.ordinal()));
		msg.setClusterModeStatus(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_CLUSTERMODESTATUS.ordinal()));
		msg.setMyKey(Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_MYKEY.ordinal()));

		return msg;
	}
	
	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.UNSUBSCRIBE_VEHICLE_DATA.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();
		
        try {

        	// Note: If the key values stored in SubscribeVehicleDataResponse
        	// were to be in a list then this could be easily looped through
        	// instead of individually set.
        	
			result.put(SubscribeVehicleDataResponse.KEY_SPEED, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_SPEED.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_RPM, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_RPM.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_EXTERNAL_TEMPERATURE, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_EXTERNTEMP.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_FUEL_LEVEL, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_FUELLEVEL.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_PRNDL, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_PRNDL.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_TIRE_PRESSURE, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_TIREPRESSURE.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_ENGINE_TORQUE, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ENGINETORQUE.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_ODOMETER, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ODOMETER.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_GPS, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_GPS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_FUEL_LEVEL_STATE, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_FUELLEVEL_STATE.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_INSTANT_FUEL_CONSUMPTION, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_FUELCONSUMPTION.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_BELT_STATUS, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_BELTSTATUS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_BODY_INFORMATION, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_BODYINFO.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_DEVICE_STATUS, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_DEVICESTATUS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_DRIVER_BRAKING, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_BRAKING.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_WIPER_STATUS, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_WIPERSTATUS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_HEAD_LAMP_STATUS, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_HEADLAMPSTATUS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_ACC_PEDAL_POSITION, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ACCPEDAL.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_STEERING_WHEEL_ANGLE, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_STEERINGWHEEL.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_E_CALL_INFO, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ECALLINFO.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_AIRBAG_STATUS, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_AIRBAGSTATUS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_EMERGENCY_EVENT, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_EMERGENCYEVENT.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_CLUSTER_MODE_STATUS, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_CLUSTERMODESTATUS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_MY_KEY, Test.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_MYKEY.ordinal()).serializeJSON());
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {   
    	// Test Values
		VehicleDataResult testGps            = ( (UnsubscribeVehicleDataResponse) msg ).getGps();
		VehicleDataResult testOdometer       = ( (UnsubscribeVehicleDataResponse) msg ).getOdometer();
		VehicleDataResult testTirePressure   = ( (UnsubscribeVehicleDataResponse) msg ).getTirePressure();
		VehicleDataResult testBeltStatus     = ( (UnsubscribeVehicleDataResponse) msg ).getBeltStatus();
		VehicleDataResult testBodyInfo       = ( (UnsubscribeVehicleDataResponse) msg ).getBodyInformation();
		VehicleDataResult testDeviceStatus   = ( (UnsubscribeVehicleDataResponse) msg ).getDeviceStatus();
		VehicleDataResult testHeadLampStatus = ( (UnsubscribeVehicleDataResponse) msg ).getHeadLampStatus();
		VehicleDataResult testECallInfo      = ( (UnsubscribeVehicleDataResponse) msg ).getECallInfo();
		VehicleDataResult testAirbagStatus   = ( (UnsubscribeVehicleDataResponse) msg ).getAirbagStatus();
		VehicleDataResult testEmergencyEvent = ( (UnsubscribeVehicleDataResponse) msg ).getEmergencyEvent();
		VehicleDataResult testClusterMode    = ( (UnsubscribeVehicleDataResponse) msg ).getClusterModeStatus();
		VehicleDataResult testMyKey          = ( (UnsubscribeVehicleDataResponse) msg ).getMyKey();
		VehicleDataResult testSpeed          = ( (UnsubscribeVehicleDataResponse) msg ).getSpeed();
		VehicleDataResult testRpm            = ( (UnsubscribeVehicleDataResponse) msg ).getRpm();
		VehicleDataResult testFuelLevel      = ( (UnsubscribeVehicleDataResponse) msg ).getFuelLevel();
		VehicleDataResult testConsumption    = ( (UnsubscribeVehicleDataResponse) msg ).getInstantFuelConsumption();
		VehicleDataResult testExternalTemp   = ( (UnsubscribeVehicleDataResponse) msg ).getExternalTemperature();
		VehicleDataResult testEngineTorque   = ( (UnsubscribeVehicleDataResponse) msg ).getEngineTorque();
		VehicleDataResult testAccPedal       = ( (UnsubscribeVehicleDataResponse) msg ).getAccPedalPosition();
		VehicleDataResult testSteeringWheel  = ( (UnsubscribeVehicleDataResponse) msg ).getSteeringWheelAngle();
		VehicleDataResult testFuelLevelState = ( (UnsubscribeVehicleDataResponse) msg ).getFuelLevelState();
		VehicleDataResult testPrndl          = ( (UnsubscribeVehicleDataResponse) msg ).getPrndl();
		VehicleDataResult testBraking        = ( (UnsubscribeVehicleDataResponse) msg ).getDriverBraking();
		VehicleDataResult testWiperStatus    = ( (UnsubscribeVehicleDataResponse) msg ).getWiperStatus();
		
		// Valid Tests
		assertTrue(Test.TRUE, testGps.getDataType().equals(VehicleDataType.VEHICLEDATA_GPS));
		assertTrue(Test.TRUE, testOdometer.getDataType().equals(VehicleDataType.VEHICLEDATA_ODOMETER));
		assertTrue(Test.TRUE, testTirePressure.getDataType().equals(VehicleDataType.VEHICLEDATA_TIREPRESSURE));
	    assertTrue(Test.TRUE, testBeltStatus.getDataType().equals(VehicleDataType.VEHICLEDATA_BELTSTATUS));
	    assertTrue(Test.TRUE, testBodyInfo.getDataType().equals(VehicleDataType.VEHICLEDATA_BODYINFO));
	    assertTrue(Test.TRUE, testDeviceStatus.getDataType().equals(VehicleDataType.VEHICLEDATA_DEVICESTATUS));
	    assertTrue(Test.TRUE, testHeadLampStatus.getDataType().equals(VehicleDataType.VEHICLEDATA_HEADLAMPSTATUS));
	    assertTrue(Test.TRUE, testECallInfo.getDataType().equals(VehicleDataType.VEHICLEDATA_ECALLINFO));
	    assertTrue(Test.TRUE, testAirbagStatus.getDataType().equals(VehicleDataType.VEHICLEDATA_AIRBAGSTATUS));
	    assertTrue(Test.TRUE, testEmergencyEvent.getDataType().equals(VehicleDataType.VEHICLEDATA_EMERGENCYEVENT));
	    assertTrue(Test.TRUE, testClusterMode.getDataType().equals(VehicleDataType.VEHICLEDATA_CLUSTERMODESTATUS));
	    assertTrue(Test.TRUE, testMyKey.getDataType().equals(VehicleDataType.VEHICLEDATA_MYKEY));
	    assertTrue(Test.TRUE, testSpeed.getDataType().equals(VehicleDataType.VEHICLEDATA_SPEED));
	    assertTrue(Test.TRUE, testRpm.getDataType().equals(VehicleDataType.VEHICLEDATA_RPM));
	    assertTrue(Test.TRUE, testFuelLevel.getDataType().equals(VehicleDataType.VEHICLEDATA_FUELLEVEL));
	    assertTrue(Test.TRUE, testConsumption.getDataType().equals(VehicleDataType.VEHICLEDATA_FUELCONSUMPTION));
	    assertTrue(Test.TRUE, testExternalTemp.getDataType().equals(VehicleDataType.VEHICLEDATA_EXTERNTEMP));
	    assertTrue(Test.TRUE, testEngineTorque.getDataType().equals(VehicleDataType.VEHICLEDATA_ENGINETORQUE));
	    assertTrue(Test.TRUE, testAccPedal.getDataType().equals(VehicleDataType.VEHICLEDATA_ACCPEDAL));
	    assertTrue(Test.TRUE, testSteeringWheel.getDataType().equals(VehicleDataType.VEHICLEDATA_STEERINGWHEEL));
	    assertTrue(Test.TRUE, testFuelLevelState.getDataType().equals(VehicleDataType.VEHICLEDATA_FUELLEVEL_STATE));
	    assertTrue(Test.TRUE, testPrndl.getDataType().equals(VehicleDataType.VEHICLEDATA_PRNDL));
	    assertTrue(Test.TRUE, testBraking.getDataType().equals(VehicleDataType.VEHICLEDATA_BRAKING));
	    assertTrue(Test.TRUE, testWiperStatus.getDataType().equals(VehicleDataType.VEHICLEDATA_WIPERSTATUS));
   
        // Invalid/Null Tests
		UnsubscribeVehicleDataResponse msg = new UnsubscribeVehicleDataResponse();
        assertNotNull("Null object creation failed.", msg);        
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
			UnsubscribeVehicleDataResponse cmd = new UnsubscribeVehicleDataResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			JSONObject speed = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_SPEED);
			VehicleDataResult referenceSpeed = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(speed));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceSpeed, cmd.getSpeed()));
			
			JSONObject rpm = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_RPM);
			VehicleDataResult referenceRpm = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(rpm));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceRpm, cmd.getRpm()));
			
			JSONObject fuelLevel = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_FUEL_LEVEL);
			VehicleDataResult referenceFuelLevel = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(fuelLevel));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceFuelLevel, cmd.getFuelLevel()));
			
			JSONObject externalTemperature = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_EXTERNAL_TEMPERATURE);
			VehicleDataResult referenceExternalTemperature = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(externalTemperature));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceExternalTemperature, cmd.getExternalTemperature()));
			
			JSONObject prndl = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_PRNDL);
			VehicleDataResult referencePrndl = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(prndl));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referencePrndl, cmd.getPrndl()));
			
			JSONObject tirePressure = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_TIRE_PRESSURE);
			VehicleDataResult referenceTirePressure = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(tirePressure));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceTirePressure, cmd.getTirePressure()));
			
			JSONObject engineTorque = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_ENGINE_TORQUE);
			VehicleDataResult referenceEngineTorque = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(engineTorque));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceEngineTorque, cmd.getEngineTorque()));
			
			JSONObject odometer = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_ODOMETER);
			VehicleDataResult referenceOdometer = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(odometer));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceOdometer, cmd.getOdometer()));
			
			JSONObject gps = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_GPS);
			VehicleDataResult referenceGps = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(gps));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceGps, cmd.getGps()));
			
			JSONObject fuelLevelState = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_FUEL_LEVEL_STATE);
			VehicleDataResult referenceFuelLevelState = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(fuelLevelState));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceFuelLevelState, cmd.getFuelLevelState()));
			
			JSONObject fuelConsumption = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_INSTANT_FUEL_CONSUMPTION);
			VehicleDataResult referenceFuelConsumption = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(fuelConsumption));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceFuelConsumption, cmd.getInstantFuelConsumption()));
			
			JSONObject beltStatus = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_BELT_STATUS);
			VehicleDataResult referenceBeltStatus = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(beltStatus));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceBeltStatus, cmd.getBeltStatus()));
			
			JSONObject bodyInformation = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_BODY_INFORMATION);
			VehicleDataResult referenceBodyInformation = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(bodyInformation));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceBodyInformation, cmd.getBodyInformation()));
			
			JSONObject deviceStatus = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_DEVICE_STATUS);
			VehicleDataResult referenceDeviceStatus = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(deviceStatus));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceDeviceStatus, cmd.getDeviceStatus()));
			
			JSONObject driverBraking = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_DRIVER_BRAKING);
			VehicleDataResult referenceDriverBraking = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(driverBraking));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceDriverBraking, cmd.getDriverBraking()));
			
			JSONObject wiperStatus = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_WIPER_STATUS);
			VehicleDataResult referenceWiperStatus = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(wiperStatus));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceWiperStatus, cmd.getWiperStatus()));
			
			JSONObject headLampStatus = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_HEAD_LAMP_STATUS);
			VehicleDataResult referenceHeadLampStatus = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(headLampStatus));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceHeadLampStatus, cmd.getHeadLampStatus()));
			
			JSONObject accPedalPosition = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_ACC_PEDAL_POSITION);
			VehicleDataResult referenceAccPedalPosition = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(accPedalPosition));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceAccPedalPosition, cmd.getAccPedalPosition()));
			
			JSONObject steeringWheelAngle = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_STEERING_WHEEL_ANGLE);
			VehicleDataResult referenceSteeringWheelAngle = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(steeringWheelAngle));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceSteeringWheelAngle, cmd.getSteeringWheelAngle()));
			
			JSONObject eCallInfo = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_E_CALL_INFO);
			VehicleDataResult referenceECallInfo = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(eCallInfo));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceECallInfo, cmd.getECallInfo()));
			
			JSONObject airbagStatus = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_AIRBAG_STATUS);
			VehicleDataResult referenceAirbagStatus = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(airbagStatus));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceAirbagStatus, cmd.getAirbagStatus()));
			
			JSONObject emergencyEvent = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_EMERGENCY_EVENT);
			VehicleDataResult referenceEmergencyEvent = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(emergencyEvent));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceEmergencyEvent, cmd.getEmergencyEvent()));
			
			JSONObject clusterModeStatus = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_CLUSTER_MODE_STATUS);
			VehicleDataResult referenceClusterModeStatus = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(clusterModeStatus));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceClusterModeStatus, cmd.getClusterModeStatus()));
			
			JSONObject myKey = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_MY_KEY);
			VehicleDataResult referenceMyKey = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(myKey));
			assertTrue(Test.TRUE, Validator.validateVehicleDataResult(referenceMyKey, cmd.getMyKey()));
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }
}