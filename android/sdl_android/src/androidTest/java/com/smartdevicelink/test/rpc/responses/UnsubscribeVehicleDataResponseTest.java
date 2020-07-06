package com.smartdevicelink.test.rpc.responses;


import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.VehicleDataResult;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
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
import static android.support.test.InstrumentationRegistry.getContext;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse}
 */
public class UnsubscribeVehicleDataResponseTest extends BaseRpcTests {

	// TODO : Test different result codes.
	
	@Override
	protected RPCMessage createMessage() {
		UnsubscribeVehicleDataResponse msg = new UnsubscribeVehicleDataResponse();

		msg.setSpeed(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_SPEED.ordinal()));
		msg.setRpm(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_RPM.ordinal()));
		msg.setExternalTemperature(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_EXTERNTEMP.ordinal()));
		msg.setFuelLevel(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_FUELLEVEL.ordinal()));
		msg.setPrndl(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_PRNDL.ordinal()));
		msg.setTirePressure(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_TIREPRESSURE.ordinal()));
		msg.setEngineTorque(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ENGINETORQUE.ordinal()));
		msg.setEngineOilLife(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ENGINEOILLIFE.ordinal()));
		msg.setOdometer(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ODOMETER.ordinal()));
		msg.setGps(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_GPS.ordinal()));
		msg.setFuelLevelState(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_FUELLEVEL_STATE.ordinal()));
		msg.setInstantFuelConsumption(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_FUELCONSUMPTION.ordinal()));
		msg.setBeltStatus(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_BELTSTATUS.ordinal()));
		msg.setBodyInformation(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_BODYINFO.ordinal()));
		msg.setDeviceStatus(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_DEVICESTATUS.ordinal()));
		msg.setDriverBraking(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_BRAKING.ordinal()));
		msg.setWiperStatus(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_WIPERSTATUS.ordinal()));
		msg.setHeadLampStatus(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_HEADLAMPSTATUS.ordinal()));
		msg.setAccPedalPosition(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ACCPEDAL.ordinal()));
		msg.setSteeringWheelAngle(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_STEERINGWHEEL.ordinal()));
		msg.setECallInfo(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ECALLINFO.ordinal()));
		msg.setAirbagStatus(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_AIRBAGSTATUS.ordinal()));
		msg.setEmergencyEvent(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_EMERGENCYEVENT.ordinal()));
		msg.setClusterModeStatus(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_CLUSTERMODESTATUS.ordinal()));
		msg.setMyKey(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_MYKEY.ordinal()));
		msg.setFuelRange(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_FUELRANGE.ordinal()));
		msg.setTurnSignal(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_TURNSIGNAL.ordinal()));
		msg.setElectronicParkBrakeStatus(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ELECTRONICPARKBRAKESTATUS.ordinal()));
		msg.setWindowStatus(TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_WINDOWSTATUS.ordinal()));
		msg.setOEMCustomVehicleData(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME, TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA);

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
        	
			result.put(SubscribeVehicleDataResponse.KEY_SPEED, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_SPEED.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_RPM, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_RPM.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_EXTERNAL_TEMPERATURE, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_EXTERNTEMP.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_FUEL_LEVEL, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_FUELLEVEL.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_PRNDL, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_PRNDL.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_TIRE_PRESSURE, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_TIREPRESSURE.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_ENGINE_TORQUE, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ENGINETORQUE.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_ENGINE_OIL_LIFE, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ENGINEOILLIFE.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_ODOMETER, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ODOMETER.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_GPS, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_GPS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_FUEL_LEVEL_STATE, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_FUELLEVEL_STATE.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_INSTANT_FUEL_CONSUMPTION, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_FUELCONSUMPTION.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_BELT_STATUS, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_BELTSTATUS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_BODY_INFORMATION, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_BODYINFO.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_DEVICE_STATUS, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_DEVICESTATUS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_DRIVER_BRAKING, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_BRAKING.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_WIPER_STATUS, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_WIPERSTATUS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_HEAD_LAMP_STATUS, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_HEADLAMPSTATUS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_ACC_PEDAL_POSITION, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ACCPEDAL.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_STEERING_WHEEL_ANGLE, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_STEERINGWHEEL.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_E_CALL_INFO, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ECALLINFO.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_AIRBAG_STATUS, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_AIRBAGSTATUS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_EMERGENCY_EVENT, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_EMERGENCYEVENT.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_CLUSTER_MODE_STATUS, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_CLUSTERMODESTATUS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_MY_KEY, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_MYKEY.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_FUEL_RANGE, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_FUELRANGE.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_TURN_SIGNAL, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_TURNSIGNAL.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_ELECTRONIC_PARK_BRAKE_STATUS, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_ELECTRONICPARKBRAKESTATUS.ordinal()).serializeJSON());
	        result.put(SubscribeVehicleDataResponse.KEY_WINDOW_STATUS, TestValues.GENERAL_VEHICLEDATARESULT_LIST.get(VehicleDataType.VEHICLEDATA_WINDOWSTATUS.ordinal()).serializeJSON());
	        result.put(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME, TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA.serializeJSON());
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
		VehicleDataResult testEngineOilLife  = ( (UnsubscribeVehicleDataResponse) msg ).getEngineOilLife();
		VehicleDataResult testAccPedal       = ( (UnsubscribeVehicleDataResponse) msg ).getAccPedalPosition();
		VehicleDataResult testSteeringWheel  = ( (UnsubscribeVehicleDataResponse) msg ).getSteeringWheelAngle();
		VehicleDataResult testFuelLevelState = ( (UnsubscribeVehicleDataResponse) msg ).getFuelLevelState();
		VehicleDataResult testPrndl          = ( (UnsubscribeVehicleDataResponse) msg ).getPrndl();
		VehicleDataResult testBraking        = ( (UnsubscribeVehicleDataResponse) msg ).getDriverBraking();
		VehicleDataResult testWiperStatus    = ( (UnsubscribeVehicleDataResponse) msg ).getWiperStatus();
		VehicleDataResult testFuelRange      = ( (UnsubscribeVehicleDataResponse) msg ).getFuelRange();
		VehicleDataResult testTurnSignal     = ( (UnsubscribeVehicleDataResponse) msg ).getTurnSignal();
		VehicleDataResult testEBrakeStatus   = ( (UnsubscribeVehicleDataResponse) msg ).getElectronicParkBrakeStatus();
		VehicleDataResult testWindowStatus   = ( (UnsubscribeVehicleDataResponse) msg ).getWindowStatus();
		VehicleDataResult testOemCustomData   = ( (UnsubscribeVehicleDataResponse) msg ).getOEMCustomVehicleData(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME);
		
		// Valid Tests
		assertTrue(TestValues.TRUE, testGps.getDataType().equals(VehicleDataType.VEHICLEDATA_GPS));
		assertTrue(TestValues.TRUE, testOdometer.getDataType().equals(VehicleDataType.VEHICLEDATA_ODOMETER));
		assertTrue(TestValues.TRUE, testTirePressure.getDataType().equals(VehicleDataType.VEHICLEDATA_TIREPRESSURE));
	    assertTrue(TestValues.TRUE, testBeltStatus.getDataType().equals(VehicleDataType.VEHICLEDATA_BELTSTATUS));
	    assertTrue(TestValues.TRUE, testBodyInfo.getDataType().equals(VehicleDataType.VEHICLEDATA_BODYINFO));
	    assertTrue(TestValues.TRUE, testDeviceStatus.getDataType().equals(VehicleDataType.VEHICLEDATA_DEVICESTATUS));
	    assertTrue(TestValues.TRUE, testHeadLampStatus.getDataType().equals(VehicleDataType.VEHICLEDATA_HEADLAMPSTATUS));
	    assertTrue(TestValues.TRUE, testECallInfo.getDataType().equals(VehicleDataType.VEHICLEDATA_ECALLINFO));
	    assertTrue(TestValues.TRUE, testAirbagStatus.getDataType().equals(VehicleDataType.VEHICLEDATA_AIRBAGSTATUS));
	    assertTrue(TestValues.TRUE, testEmergencyEvent.getDataType().equals(VehicleDataType.VEHICLEDATA_EMERGENCYEVENT));
	    assertTrue(TestValues.TRUE, testClusterMode.getDataType().equals(VehicleDataType.VEHICLEDATA_CLUSTERMODESTATUS));
	    assertTrue(TestValues.TRUE, testMyKey.getDataType().equals(VehicleDataType.VEHICLEDATA_MYKEY));
	    assertTrue(TestValues.TRUE, testSpeed.getDataType().equals(VehicleDataType.VEHICLEDATA_SPEED));
	    assertTrue(TestValues.TRUE, testRpm.getDataType().equals(VehicleDataType.VEHICLEDATA_RPM));
	    assertTrue(TestValues.TRUE, testFuelLevel.getDataType().equals(VehicleDataType.VEHICLEDATA_FUELLEVEL));
	    assertTrue(TestValues.TRUE, testConsumption.getDataType().equals(VehicleDataType.VEHICLEDATA_FUELCONSUMPTION));
	    assertTrue(TestValues.TRUE, testExternalTemp.getDataType().equals(VehicleDataType.VEHICLEDATA_EXTERNTEMP));
	    assertTrue(TestValues.TRUE, testEngineTorque.getDataType().equals(VehicleDataType.VEHICLEDATA_ENGINETORQUE));
	    assertTrue(TestValues.TRUE, testEngineOilLife.getDataType().equals(VehicleDataType.VEHICLEDATA_ENGINEOILLIFE));
	    assertTrue(TestValues.TRUE, testAccPedal.getDataType().equals(VehicleDataType.VEHICLEDATA_ACCPEDAL));
	    assertTrue(TestValues.TRUE, testSteeringWheel.getDataType().equals(VehicleDataType.VEHICLEDATA_STEERINGWHEEL));
	    assertTrue(TestValues.TRUE, testFuelLevelState.getDataType().equals(VehicleDataType.VEHICLEDATA_FUELLEVEL_STATE));
	    assertTrue(TestValues.TRUE, testPrndl.getDataType().equals(VehicleDataType.VEHICLEDATA_PRNDL));
	    assertTrue(TestValues.TRUE, testBraking.getDataType().equals(VehicleDataType.VEHICLEDATA_BRAKING));
	    assertTrue(TestValues.TRUE, testWiperStatus.getDataType().equals(VehicleDataType.VEHICLEDATA_WIPERSTATUS));
	    assertTrue(TestValues.TRUE, testFuelRange.getDataType().equals(VehicleDataType.VEHICLEDATA_FUELRANGE));
	    assertTrue(TestValues.TRUE, testTurnSignal.getDataType().equals(VehicleDataType.VEHICLEDATA_TURNSIGNAL));
	    assertTrue(TestValues.TRUE, testEBrakeStatus.getDataType().equals(VehicleDataType.VEHICLEDATA_ELECTRONICPARKBRAKESTATUS));
	    assertTrue(TestValues.TRUE, testWindowStatus.getDataType().equals(VehicleDataType.VEHICLEDATA_WINDOWSTATUS));
	    assertTrue(TestValues.TRUE, testOemCustomData.getOEMCustomVehicleDataType().equals(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME));
   
        // Invalid/Null Tests
		UnsubscribeVehicleDataResponse msg = new UnsubscribeVehicleDataResponse();
        assertNotNull("Null object creation failed.", msg);        
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
        assertNull(TestValues.NULL, msg.getWindowStatus());
        assertNull(TestValues.NULL, msg.getOEMCustomVehicleData(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME));
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getContext(), getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			UnsubscribeVehicleDataResponse cmd = new UnsubscribeVehicleDataResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			JSONObject speed = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_SPEED);
			VehicleDataResult referenceSpeed = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(speed));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceSpeed, cmd.getSpeed()));
			
			JSONObject rpm = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_RPM);
			VehicleDataResult referenceRpm = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(rpm));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceRpm, cmd.getRpm()));
			
			JSONObject fuelLevel = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_FUEL_LEVEL);
			VehicleDataResult referenceFuelLevel = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(fuelLevel));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceFuelLevel, cmd.getFuelLevel()));
			
			JSONObject externalTemperature = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_EXTERNAL_TEMPERATURE);
			VehicleDataResult referenceExternalTemperature = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(externalTemperature));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceExternalTemperature, cmd.getExternalTemperature()));
			
			JSONObject prndl = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_PRNDL);
			VehicleDataResult referencePrndl = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(prndl));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referencePrndl, cmd.getPrndl()));
			
			JSONObject tirePressure = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_TIRE_PRESSURE);
			VehicleDataResult referenceTirePressure = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(tirePressure));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceTirePressure, cmd.getTirePressure()));

			JSONObject engineTorque = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_ENGINE_TORQUE);
			VehicleDataResult referenceEngineTorque = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(engineTorque));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceEngineTorque, cmd.getEngineTorque()));

			JSONObject engineOilLife = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_ENGINE_OIL_LIFE);
			VehicleDataResult referenceEngineOilLife = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(engineOilLife));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceEngineOilLife, cmd.getEngineOilLife()));

			JSONObject odometer = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_ODOMETER);
			VehicleDataResult referenceOdometer = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(odometer));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceOdometer, cmd.getOdometer()));
			
			JSONObject gps = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_GPS);
			VehicleDataResult referenceGps = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(gps));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceGps, cmd.getGps()));
			
			JSONObject fuelLevelState = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_FUEL_LEVEL_STATE);
			VehicleDataResult referenceFuelLevelState = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(fuelLevelState));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceFuelLevelState, cmd.getFuelLevelState()));
			
			JSONObject fuelConsumption = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_INSTANT_FUEL_CONSUMPTION);
			VehicleDataResult referenceFuelConsumption = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(fuelConsumption));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceFuelConsumption, cmd.getInstantFuelConsumption()));
			
			JSONObject beltStatus = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_BELT_STATUS);
			VehicleDataResult referenceBeltStatus = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(beltStatus));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceBeltStatus, cmd.getBeltStatus()));
			
			JSONObject bodyInformation = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_BODY_INFORMATION);
			VehicleDataResult referenceBodyInformation = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(bodyInformation));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceBodyInformation, cmd.getBodyInformation()));
			
			JSONObject deviceStatus = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_DEVICE_STATUS);
			VehicleDataResult referenceDeviceStatus = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(deviceStatus));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceDeviceStatus, cmd.getDeviceStatus()));
			
			JSONObject driverBraking = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_DRIVER_BRAKING);
			VehicleDataResult referenceDriverBraking = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(driverBraking));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceDriverBraking, cmd.getDriverBraking()));
			
			JSONObject wiperStatus = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_WIPER_STATUS);
			VehicleDataResult referenceWiperStatus = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(wiperStatus));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceWiperStatus, cmd.getWiperStatus()));
			
			JSONObject headLampStatus = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_HEAD_LAMP_STATUS);
			VehicleDataResult referenceHeadLampStatus = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(headLampStatus));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceHeadLampStatus, cmd.getHeadLampStatus()));
			
			JSONObject accPedalPosition = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_ACC_PEDAL_POSITION);
			VehicleDataResult referenceAccPedalPosition = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(accPedalPosition));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceAccPedalPosition, cmd.getAccPedalPosition()));
			
			JSONObject steeringWheelAngle = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_STEERING_WHEEL_ANGLE);
			VehicleDataResult referenceSteeringWheelAngle = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(steeringWheelAngle));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceSteeringWheelAngle, cmd.getSteeringWheelAngle()));
			
			JSONObject eCallInfo = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_E_CALL_INFO);
			VehicleDataResult referenceECallInfo = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(eCallInfo));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceECallInfo, cmd.getECallInfo()));
			
			JSONObject airbagStatus = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_AIRBAG_STATUS);
			VehicleDataResult referenceAirbagStatus = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(airbagStatus));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceAirbagStatus, cmd.getAirbagStatus()));
			
			JSONObject emergencyEvent = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_EMERGENCY_EVENT);
			VehicleDataResult referenceEmergencyEvent = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(emergencyEvent));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceEmergencyEvent, cmd.getEmergencyEvent()));
			
			JSONObject clusterModeStatus = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_CLUSTER_MODE_STATUS);
			VehicleDataResult referenceClusterModeStatus = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(clusterModeStatus));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceClusterModeStatus, cmd.getClusterModeStatus()));
			
			JSONObject myKey = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_MY_KEY);
			VehicleDataResult referenceMyKey = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(myKey));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceMyKey, cmd.getMyKey()));

			JSONObject fuelRange = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_FUEL_RANGE);
			VehicleDataResult referenceFuelRange = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(fuelRange));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceFuelRange, cmd.getFuelRange()));

			JSONObject turnSignal = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_TURN_SIGNAL);
			VehicleDataResult referenceTurnSignal = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(turnSignal));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceTurnSignal, cmd.getTurnSignal()));

			JSONObject eBrakeStatus = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_ELECTRONIC_PARK_BRAKE_STATUS);
			VehicleDataResult referenceEBrakeStatus = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(eBrakeStatus));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceEBrakeStatus, cmd.getElectronicParkBrakeStatus()));

			JSONObject windowStatus = JsonUtils.readJsonObjectFromJsonObject(parameters, UnsubscribeVehicleDataResponse.KEY_WINDOW_STATUS);
			VehicleDataResult referenceWindowStatus = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(windowStatus));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceWindowStatus, cmd.getWindowStatus()));

			JSONObject oemCustomData = JsonUtils.readJsonObjectFromJsonObject(parameters, TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME);
			VehicleDataResult referenceOemCustomData = new VehicleDataResult(JsonRPCMarshaller.deserializeJSONObject(oemCustomData));
			assertTrue(TestValues.TRUE, Validator.validateVehicleDataResult(referenceOemCustomData, cmd.getOEMCustomVehicleData(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
}