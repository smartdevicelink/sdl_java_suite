package com.smartdevicelink.test.rpc.requests;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetVehicleData;
import com.smartdevicelink.test.BaseRpcTests;

public class GetVehicleDataTests extends BaseRpcTests{

    public static final boolean KEY_SPEED = true;
	public static final boolean KEY_RPM = true;
	public static final boolean KEY_EXTERNAL_TEMPERATURE = true;
	public static final boolean KEY_FUEL_LEVEL = true;
	public static final boolean KEY_VIN = true;
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
    protected RPCMessage createMessage(){
        GetVehicleData msg = new GetVehicleData();

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
		msg.setVin(KEY_VIN);
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
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GET_VEHICLE_DATA;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(GetVehicleData.KEY_SPEED, KEY_SPEED);
            result.put(GetVehicleData.KEY_RPM, KEY_RPM);
            result.put(GetVehicleData.KEY_EXTERNAL_TEMPERATURE, KEY_EXTERNAL_TEMPERATURE);
            result.put(GetVehicleData.KEY_FUEL_LEVEL, KEY_FUEL_LEVEL);
            result.put(GetVehicleData.KEY_PRNDL, KEY_PRNDL);
            result.put(GetVehicleData.KEY_TIRE_PRESSURE, KEY_TIRE_PRESSURE);
            result.put(GetVehicleData.KEY_ENGINE_TORQUE, KEY_ENGINE_TORQUE);
            result.put(GetVehicleData.KEY_ODOMETER, KEY_ODOMETER);
            result.put(GetVehicleData.KEY_GPS, KEY_GPS);
            result.put(GetVehicleData.KEY_FUEL_LEVEL_STATE, KEY_FUEL_LEVEL_STATE);
            result.put(GetVehicleData.KEY_INSTANT_FUEL_CONSUMPTION, KEY_INSTANT_FUEL_CONSUMPTION);
            result.put(GetVehicleData.KEY_VIN, KEY_VIN);
            result.put(GetVehicleData.KEY_BELT_STATUS, KEY_BELT_STATUS);
            result.put(GetVehicleData.KEY_BODY_INFORMATION, KEY_BODY_INFORMATION);
            result.put(GetVehicleData.KEY_DEVICE_STATUS, KEY_DEVICE_STATUS);
            result.put(GetVehicleData.KEY_DRIVER_BRAKING, KEY_DRIVER_BRAKING);
            result.put(GetVehicleData.KEY_WIPER_STATUS, KEY_WIPER_STATUS);
            result.put(GetVehicleData.KEY_HEAD_LAMP_STATUS, KEY_HEAD_LAMP_STATUS);
            result.put(GetVehicleData.KEY_ACC_PEDAL_POSITION, KEY_ACC_PEDAL_POSITION);
            result.put(GetVehicleData.KEY_STEERING_WHEEL_ANGLE, KEY_STEERING_WHEEL_ANGLE);
            result.put(GetVehicleData.KEY_E_CALL_INFO, KEY_E_CALL_INFO);
            result.put(GetVehicleData.KEY_AIRBAG_STATUS, KEY_AIRBAG_STATUS);
            result.put(GetVehicleData.KEY_EMERGENCY_EVENT, KEY_EMERGENCY_EVENT);
            result.put(GetVehicleData.KEY_CLUSTER_MODE_STATUS, KEY_CLUSTER_MODE_STATUS);
            result.put(GetVehicleData.KEY_MY_KEY, KEY_MY_KEY);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }
    
	public void testBatchAdd(){		
		assertTrue("Speed vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getSpeed());
		assertTrue("RPM vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getRpm());
		assertTrue("External temperature vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getExternalTemperature());
		assertTrue("Fuel level vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getFuelLevel());
		assertTrue("PRNDL vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getPrndl());
		assertTrue("Tire pressure vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getTirePressure());
		assertTrue("Engine torque vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getEngineTorque());
		assertTrue("Odometer vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getOdometer());
		assertTrue("GPS vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getGps());
		assertTrue("Fuel level state vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getFuelLevel_State());
		assertTrue("Instant fuel consumption vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getInstantFuelConsumption());
		assertTrue("VIN vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getVin());
		assertTrue("Belt status vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getBeltStatus());
		assertTrue("Body information vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getBodyInformation());
		assertTrue("VIN vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getVin());
		assertTrue("Device status vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getDeviceStatus());
		assertTrue("Driver braking vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getDriverBraking());
		assertTrue("Wiper status vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getWiperStatus());
		assertTrue("Head lamp status vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getHeadLampStatus());
		assertTrue("Acceleration pedal position vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getAccPedalPosition());
		assertTrue("Steering wheel angle vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getSteeringWheelAngle());
		assertTrue("Emergency call info vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getECallInfo());
		assertTrue("Airbag status vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getAirbagStatus());
		assertTrue("Emergency event vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getEmergencyEvent());
		assertTrue("Cluster mode status vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getClusterModeStatus());
		assertTrue("My key vehicle data wasn't set correctly.",( (GetVehicleData) msg ).getMyKey());
    }

    public void testNull(){
        GetVehicleData msg = new GetVehicleData();
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
        assertNull("Vin data wasn't set, but getter method returned an object.", msg.getVin());
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
}
