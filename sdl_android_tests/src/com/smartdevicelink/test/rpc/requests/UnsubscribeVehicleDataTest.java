package com.smartdevicelink.test.rpc.requests;


import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleData;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;

@SuppressWarnings("deprecation")
public class UnsubscribeVehicleDataTest extends BaseRpcTests {

    public static final boolean KEY_SPEED = true;
	public static final boolean KEY_RPM = true;
	public static final boolean KEY_EXTERNAL_TEMPERATURE = true;
	public static final boolean KEY_FUEL_LEVEL = true;
	public static final boolean KEY_PRNDL = true;
	public static final boolean KEY_TIRE_PRESSURE = true;
	public static final boolean KEY_ENGINE_TORQUE = true;
	public static final boolean KEY_ODOMETER = true;
	public static final boolean KEY_GPS = true;
	public static final boolean KEY_FUEL_LEVEL_STATE = true;
	public static final boolean KEY_INSTANT_FUEL_CONSUMPTION = true;
	public static final boolean KEY_BELT_STATUS = true;
	public static final boolean KEY_BODY_INFORMATION = true;
	public static final boolean KEY_DEVICE_STATUS = true;
	public static final boolean KEY_DRIVER_BRAKING = true;
	public static final boolean KEY_WIPER_STATUS = true;
	public static final boolean KEY_HEAD_LAMP_STATUS = true;
	public static final boolean KEY_ACC_PEDAL_POSITION = true;
	public static final boolean KEY_STEERING_WHEEL_ANGLE = true;
	public static final boolean KEY_E_CALL_INFO = true;
	public static final boolean KEY_AIRBAG_STATUS = true;
	public static final boolean KEY_EMERGENCY_EVENT = true;
	public static final boolean KEY_CLUSTER_MODE_STATUS = true;
	public static final boolean KEY_MY_KEY = true;

	@Override
	protected RPCMessage createMessage() {
		UnsubscribeVehicleData msg = new UnsubscribeVehicleData();

		msg.setSpeed(KEY_SPEED);
		msg.setRpm(KEY_RPM);
		msg.setExternalTemperature(KEY_EXTERNAL_TEMPERATURE);
		msg.setFuelLevel(KEY_FUEL_LEVEL);
		msg.setPrndl(KEY_PRNDL);
		msg.setTirePressure(KEY_TIRE_PRESSURE);
		msg.setEngineTorque(KEY_ENGINE_TORQUE);
		msg.setOdometer(KEY_ODOMETER);
		msg.setGps(KEY_GPS);
		msg.setFuelLevel_State(KEY_FUEL_LEVEL_STATE);
		msg.setInstantFuelConsumption(KEY_INSTANT_FUEL_CONSUMPTION);
		msg.setBeltStatus(KEY_BELT_STATUS);
		msg.setBodyInformation(KEY_BODY_INFORMATION);
		msg.setDeviceStatus(KEY_DEVICE_STATUS);
		msg.setDriverBraking(KEY_DRIVER_BRAKING);
		msg.setWiperStatus(KEY_WIPER_STATUS);
		msg.setHeadLampStatus(KEY_HEAD_LAMP_STATUS);
		msg.setAccPedalPosition(KEY_ACC_PEDAL_POSITION);
		msg.setSteeringWheelAngle(KEY_STEERING_WHEEL_ANGLE);
		msg.setECallInfo(KEY_E_CALL_INFO);
		msg.setAirbagStatus(KEY_AIRBAG_STATUS);
		msg.setEmergencyEvent(KEY_EMERGENCY_EVENT);
		msg.setClusterModeStatus(KEY_CLUSTER_MODE_STATUS);
		msg.setMyKey(KEY_MY_KEY);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.UNSUBSCRIBE_VEHICLE_DATA;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
            result.put(UnsubscribeVehicleData.KEY_SPEED, KEY_SPEED);
            result.put(UnsubscribeVehicleData.KEY_RPM, KEY_RPM);
            result.put(UnsubscribeVehicleData.KEY_EXTERNAL_TEMPERATURE, KEY_EXTERNAL_TEMPERATURE);
            result.put(UnsubscribeVehicleData.KEY_FUEL_LEVEL, KEY_FUEL_LEVEL);
            result.put(UnsubscribeVehicleData.KEY_PRNDL, KEY_PRNDL);
            result.put(UnsubscribeVehicleData.KEY_TIRE_PRESSURE, KEY_TIRE_PRESSURE);
            result.put(UnsubscribeVehicleData.KEY_ENGINE_TORQUE, KEY_ENGINE_TORQUE);
            result.put(UnsubscribeVehicleData.KEY_ODOMETER, KEY_ODOMETER);
            result.put(UnsubscribeVehicleData.KEY_GPS, KEY_GPS);
            result.put(UnsubscribeVehicleData.KEY_FUEL_LEVEL_STATE, KEY_FUEL_LEVEL_STATE);
            result.put(UnsubscribeVehicleData.KEY_INSTANT_FUEL_CONSUMPTION, KEY_INSTANT_FUEL_CONSUMPTION);
            result.put(UnsubscribeVehicleData.KEY_BELT_STATUS, KEY_BELT_STATUS);
            result.put(UnsubscribeVehicleData.KEY_BODY_INFORMATION, KEY_BODY_INFORMATION);
            result.put(UnsubscribeVehicleData.KEY_DEVICE_STATUS, KEY_DEVICE_STATUS);
            result.put(UnsubscribeVehicleData.KEY_DRIVER_BRAKING, KEY_DRIVER_BRAKING);
            result.put(UnsubscribeVehicleData.KEY_WIPER_STATUS, KEY_WIPER_STATUS);
            result.put(UnsubscribeVehicleData.KEY_HEAD_LAMP_STATUS, KEY_HEAD_LAMP_STATUS);
            result.put(UnsubscribeVehicleData.KEY_ACC_PEDAL_POSITION, KEY_ACC_PEDAL_POSITION);
            result.put(UnsubscribeVehicleData.KEY_STEERING_WHEEL_ANGLE, KEY_STEERING_WHEEL_ANGLE);
            result.put(UnsubscribeVehicleData.KEY_E_CALL_INFO, KEY_E_CALL_INFO);
            result.put(UnsubscribeVehicleData.KEY_AIRBAG_STATUS, KEY_AIRBAG_STATUS);
            result.put(UnsubscribeVehicleData.KEY_EMERGENCY_EVENT, KEY_EMERGENCY_EVENT);
            result.put(UnsubscribeVehicleData.KEY_CLUSTER_MODE_STATUS, KEY_CLUSTER_MODE_STATUS);
            result.put(UnsubscribeVehicleData.KEY_MY_KEY, KEY_MY_KEY);
            
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testBatchAdd(){
		assertTrue("Speed vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getSpeed());
		assertTrue("RPM vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getRpm());
		assertTrue("External temperature vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getExternalTemperature());
		assertTrue("Fuel level vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getFuelLevel());
		assertTrue("PRNDL vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getPrndl());
		assertTrue("Tire pressure vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getTirePressure());
		assertTrue("Engine torque vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getEngineTorque());
		assertTrue("Odometer vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getOdometer());
		assertTrue("GPS vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getGps());
		assertTrue("Fuel level state vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getFuelLevel_State());
		assertTrue("Instant fuel consumption vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getInstantFuelConsumption());
		assertTrue("Belt status vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getBeltStatus());
		assertTrue("Body information vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getBodyInformation());
		assertTrue("Device status vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getDeviceStatus());
		assertTrue("Driver braking vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getDriverBraking());
		assertTrue("Wiper status vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getWiperStatus());
		assertTrue("Head lamp status vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getHeadLampStatus());
		assertTrue("Acceleration pedal position vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getAccPedalPosition());
		assertTrue("Steering wheel angle vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getSteeringWheelAngle());
		assertTrue("Emergency call info vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getECallInfo());
		assertTrue("Airbag status vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getAirbagStatus());
		assertTrue("Emergency event vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getEmergencyEvent());
		assertTrue("Cluster mode status vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getClusterModeStatus());
		assertTrue("My key vehicle data wasn't set correctly.",( (UnsubscribeVehicleData) msg ).getMyKey());
    }

	public void testNull() {
		UnsubscribeVehicleData msg = new UnsubscribeVehicleData();
		assertNotNull("Null object creation failed.", msg);
		testNullBase(msg);

        assertNull("Accel pedal vehicle data wasn't set, but getter method returned an object.", msg.getAccPedalPosition());
        assertNull("Airbag vehicle data wasn't set, but getter method returned an object.", msg.getAirbagStatus());
        assertNull("Belt vehicle data wasn't set, but getter method returned an object.", msg.getBeltStatus());
        assertNull("Driver braking vehicle data wasn't set, but getter method returned an object.", msg.getDriverBraking());
        assertNull("Fuel level vehicle data wasn't set, but getter method returned an object.", msg.getFuelLevel());
        assertNull("Tire pressure vehicle data wasn't set, but getter method returned an object.", msg.getTirePressure());
        assertNull("Wiper vehicle data wasn't set, but getter method returned an object.", msg.getWiperStatus());
        assertNull("Gps data wasn't set, but getter method returned an object.", msg.getGps());
        assertNull("Speed data wasn't set, but getter method returned an object.", msg.getSpeed());
        assertNull("Rpm data wasn't set, but getter method returned an object.", msg.getRpm());
        assertNull("Fuel level state data wasn't set, but getter method returned an object.", msg.getFuelLevel_State());
        assertNull("Fuel consumption data wasn't set, but getter method returned an object.", msg.getInstantFuelConsumption());
        assertNull("External temperature data wasn't set, but getter method returned an object.", msg.getExternalTemperature());
        assertNull("PRNDL data wasn't set, but getter method returned an object.", msg.getPrndl());
        assertNull("Odometer data wasn't set, but getter method returned an object.", msg.getOdometer());
        assertNull("Body information data wasn't set, but getter method returned an object.", msg.getBodyInformation());
        assertNull("Device status data wasn't set, but getter method returned an object.", msg.getDeviceStatus());
        assertNull("Head lamp status data wasn't set, but getter method returned an object.", msg.getHeadLampStatus());
        assertNull("Engine torque data wasn't set, but getter method returned an object.", msg.getEngineTorque());
        assertNull("Steering wheel angle data wasn't set, but getter method returned an object.", msg.getSteeringWheelAngle());
        assertNull("ECall info data wasn't set, but getter method returned an object.", msg.getECallInfo());
        assertNull("Emergency event data wasn't set, but getter method returned an object.", msg.getEmergencyEvent());
        assertNull("Cluster mode data wasn't set, but getter method returned an object.", msg.getClusterModeStatus());
        assertNull("My key data wasn't set, but getter method returned an object.", msg.getMyKey());
	}

    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			UnsubscribeVehicleData cmd = new UnsubscribeVehicleData(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Speed doesn't match input speed", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_SPEED), cmd.getSpeed());
			assertEquals("Rpm doesn't match input rpm", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_RPM), cmd.getRpm());
			assertEquals("External temperature doesn't match input temperature", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_EXTERNAL_TEMPERATURE), cmd.getExternalTemperature());
			assertEquals("Fuel level doesn't match input level", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_FUEL_LEVEL), cmd.getFuelLevel());
			assertEquals("PRNDL doesn't match input PRDNL", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_PRNDL), cmd.getPrndl());
			assertEquals("Tire pressure doesn't match input pressure", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_TIRE_PRESSURE), cmd.getTirePressure());
			assertEquals("Engine torque doesn't match input torque", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_ENGINE_TORQUE), cmd.getEngineTorque());
			assertEquals("Odometer doesn't match input odometer", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_ODOMETER), cmd.getOdometer());
			assertEquals("GPS doesn't match input GPS", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_GPS), cmd.getGps());
			assertEquals("Fuel level state doesn't match input state", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_FUEL_LEVEL_STATE), cmd.getFuelLevel_State());
			assertEquals("Instant fuel consumption doesn't match input consumption", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_INSTANT_FUEL_CONSUMPTION), cmd.getInstantFuelConsumption());
			assertEquals("Belt status doesn't match input status", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_BELT_STATUS), cmd.getBeltStatus());
			assertEquals("Body information doesn't match input information", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_BODY_INFORMATION), cmd.getBodyInformation());
			assertEquals("Device status doesn't match input status", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_DEVICE_STATUS), cmd.getDeviceStatus());
			assertEquals("Driver braking doesn't match input braking", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_DRIVER_BRAKING), cmd.getDriverBraking());
			assertEquals("Wiper status doesn't match input status", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_WIPER_STATUS), cmd.getWiperStatus());
			assertEquals("Head lamp status doesn't match input status", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_HEAD_LAMP_STATUS), cmd.getHeadLampStatus());
			assertEquals("Acceleration pedal position doesn't match input position", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_ACC_PEDAL_POSITION), cmd.getAccPedalPosition());
			assertEquals("Steering wheel angle doesn't match input angle", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_STEERING_WHEEL_ANGLE), cmd.getSteeringWheelAngle());
			assertEquals("Emergency call info doesn't match input info", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_E_CALL_INFO), cmd.getECallInfo());
			assertEquals("Airbag status doesn't match input status", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_AIRBAG_STATUS), cmd.getAirbagStatus());
			assertEquals("Emergency event doesn't match input event", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_EMERGENCY_EVENT), cmd.getEmergencyEvent());
			assertEquals("Cluster mode status doesn't match input status", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_CLUSTER_MODE_STATUS), cmd.getClusterModeStatus());
			assertEquals("My key doesn't match input key", 
					JsonUtils.readBooleanFromJsonObject(parameters, UnsubscribeVehicleData.KEY_MY_KEY), cmd.getMyKey());
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
	
}
