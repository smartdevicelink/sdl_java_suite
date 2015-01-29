package com.smartdevicelink.test.rpc.responses;


import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.VehicleDataResult;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataType;
import com.smartdevicelink.test.BaseRpcTests;

public class UnsubscribeVehicleDataResponseTest extends BaseRpcTests {

    public static final VehicleDataResult KEY_SPEED = new VehicleDataResult();
	public static final VehicleDataResult KEY_RPM = new VehicleDataResult();
	public static final VehicleDataResult KEY_EXTERNAL_TEMPERATURE = new VehicleDataResult();
	public static final VehicleDataResult KEY_FUEL_LEVEL = new VehicleDataResult();
	public static final VehicleDataResult KEY_PRNDL = new VehicleDataResult();
	public static final VehicleDataResult KEY_TIRE_PRESSURE = new VehicleDataResult();
	public static final VehicleDataResult KEY_ENGINE_TORQUE = new VehicleDataResult();
	public static final VehicleDataResult KEY_ODOMETER = new VehicleDataResult();
	public static final VehicleDataResult KEY_GPS = new VehicleDataResult();
	public static final VehicleDataResult KEY_FUEL_LEVEL_STATE = new VehicleDataResult();
	public static final VehicleDataResult KEY_INSTANT_FUEL_CONSUMPTION = new VehicleDataResult();
	public static final VehicleDataResult KEY_BELT_STATUS = new VehicleDataResult();
	public static final VehicleDataResult KEY_BODY_INFORMATION = new VehicleDataResult();
	public static final VehicleDataResult KEY_DEVICE_STATUS = new VehicleDataResult();
	public static final VehicleDataResult KEY_DRIVER_BRAKING = new VehicleDataResult();
	public static final VehicleDataResult KEY_WIPER_STATUS = new VehicleDataResult();
	public static final VehicleDataResult KEY_HEAD_LAMP_STATUS = new VehicleDataResult();
	public static final VehicleDataResult KEY_ACC_PEDAL_POSITION = new VehicleDataResult();
	public static final VehicleDataResult KEY_STEERING_WHEEL_ANGLE = new VehicleDataResult();
	public static final VehicleDataResult KEY_E_CALL_INFO = new VehicleDataResult();
	public static final VehicleDataResult KEY_AIRBAG_STATUS = new VehicleDataResult();
	public static final VehicleDataResult KEY_EMERGENCY_EVENT = new VehicleDataResult();
	public static final VehicleDataResult KEY_CLUSTER_MODE_STATUS = new VehicleDataResult();
	public static final VehicleDataResult KEY_MY_KEY = new VehicleDataResult();
	
	//this method makes setting up the VehicleDataResult objects easier. assumes VehicleDataResultCode.SUCCESS for all result codes
	private VehicleDataResult populateResultObject (VehicleDataResult data, VehicleDataType type) {
		data.setDataType(type);
		data.setResultCode(VehicleDataResultCode.SUCCESS);
		return data;
	}
	
	@Override
	protected RPCMessage createMessage() {
		UnsubscribeVehicleDataResponse msg = new UnsubscribeVehicleDataResponse();

		msg.setSpeed( populateResultObject(KEY_SPEED, VehicleDataType.VEHICLEDATA_SPEED) );
		msg.setRpm( populateResultObject(KEY_RPM, VehicleDataType.VEHICLEDATA_RPM) );
		msg.setExternalTemperature( populateResultObject(KEY_EXTERNAL_TEMPERATURE, VehicleDataType.VEHICLEDATA_EXTERNTEMP) );
		msg.setFuelLevel( populateResultObject(KEY_FUEL_LEVEL, VehicleDataType.VEHICLEDATA_FUELLEVEL) );
		msg.setPrndl( populateResultObject(KEY_PRNDL, VehicleDataType.VEHICLEDATA_PRNDL) );
		msg.setTirePressure( populateResultObject(KEY_TIRE_PRESSURE, VehicleDataType.VEHICLEDATA_TIREPRESSURE) );
		msg.setEngineTorque( populateResultObject(KEY_ENGINE_TORQUE, VehicleDataType.VEHICLEDATA_ENGINETORQUE) );
		msg.setOdometer( populateResultObject(KEY_ODOMETER, VehicleDataType.VEHICLEDATA_ODOMETER) );
		msg.setGps( populateResultObject(KEY_GPS, VehicleDataType.VEHICLEDATA_GPS) );
		msg.setFuelLevel_State( populateResultObject(KEY_FUEL_LEVEL_STATE, VehicleDataType.VEHICLEDATA_FUELLEVEL_STATE) );
		msg.setInstantFuelConsumption( populateResultObject(KEY_INSTANT_FUEL_CONSUMPTION, VehicleDataType.VEHICLEDATA_FUELCONSUMPTION) );
		msg.setBeltStatus( populateResultObject(KEY_BELT_STATUS, VehicleDataType.VEHICLEDATA_BELTSTATUS) );
		msg.setBodyInformation( populateResultObject(KEY_BODY_INFORMATION, VehicleDataType.VEHICLEDATA_BODYINFO) );
		msg.setDeviceStatus( populateResultObject(KEY_DEVICE_STATUS, VehicleDataType.VEHICLEDATA_DEVICESTATUS) );
		msg.setDriverBraking( populateResultObject(KEY_DRIVER_BRAKING, VehicleDataType.VEHICLEDATA_BRAKING) );
		msg.setWiperStatus( populateResultObject(KEY_WIPER_STATUS, VehicleDataType.VEHICLEDATA_WIPERSTATUS) );
		msg.setHeadLampStatus( populateResultObject(KEY_HEAD_LAMP_STATUS, VehicleDataType.VEHICLEDATA_HEADLAMPSTATUS) );
		msg.setAccPedalPosition( populateResultObject(KEY_ACC_PEDAL_POSITION, VehicleDataType.VEHICLEDATA_ACCPEDAL) );
		msg.setSteeringWheelAngle( populateResultObject(KEY_STEERING_WHEEL_ANGLE, VehicleDataType.VEHICLEDATA_STEERINGWHEEL) );
		msg.setECallInfo( populateResultObject(KEY_E_CALL_INFO, VehicleDataType.VEHICLEDATA_ECALLINFO) );
		msg.setAirbagStatus( populateResultObject(KEY_AIRBAG_STATUS, VehicleDataType.VEHICLEDATA_AIRBAGSTATUS) );
		msg.setEmergencyEvent( populateResultObject(KEY_EMERGENCY_EVENT, VehicleDataType.VEHICLEDATA_EMERGENCYEVENT) );
		msg.setClusterModeStatus( populateResultObject(KEY_CLUSTER_MODE_STATUS, VehicleDataType.VEHICLEDATA_CLUSTERMODESTATUS) );
		msg.setMyKey( populateResultObject(KEY_MY_KEY, VehicleDataType.VEHICLEDATA_MYKEY) );
		
		return msg;
	}
	
	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.UNSUBSCRIBE_VEHICLE_DATA;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();
		//TODO: correct?
        try {
			result.put(UnsubscribeVehicleDataResponse.KEY_SPEED, KEY_SPEED.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_RPM, KEY_RPM.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_EXTERNAL_TEMPERATURE, KEY_EXTERNAL_TEMPERATURE.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_FUEL_LEVEL, KEY_FUEL_LEVEL.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_PRNDL, KEY_PRNDL.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_TIRE_PRESSURE, KEY_TIRE_PRESSURE.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_ENGINE_TORQUE, KEY_ENGINE_TORQUE.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_ODOMETER, KEY_ODOMETER.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_GPS, KEY_GPS.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_FUEL_LEVEL_STATE, KEY_FUEL_LEVEL_STATE.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_INSTANT_FUEL_CONSUMPTION, KEY_INSTANT_FUEL_CONSUMPTION.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_BELT_STATUS, KEY_BELT_STATUS.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_BODY_INFORMATION, KEY_BODY_INFORMATION.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_DEVICE_STATUS, KEY_DEVICE_STATUS.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_DRIVER_BRAKING, KEY_DRIVER_BRAKING.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_WIPER_STATUS, KEY_WIPER_STATUS.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_HEAD_LAMP_STATUS, KEY_HEAD_LAMP_STATUS.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_ACC_PEDAL_POSITION, KEY_ACC_PEDAL_POSITION.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_STEERING_WHEEL_ANGLE, KEY_STEERING_WHEEL_ANGLE.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_E_CALL_INFO, KEY_E_CALL_INFO.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_AIRBAG_STATUS, KEY_AIRBAG_STATUS.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_EMERGENCY_EVENT, KEY_EMERGENCY_EVENT.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_CLUSTER_MODE_STATUS, KEY_CLUSTER_MODE_STATUS.serializeJSON());
	        result.put(UnsubscribeVehicleDataResponse.KEY_MY_KEY, KEY_MY_KEY.serializeJSON());
		} catch (JSONException e) {
			// do nothing
		}

		return result;
	}

	/* The VehicleDataResult class contains the following retrievable variables:
	 *    (VehicleDataType)       dataType     Enum value being tested
	 *    (VehicleDataResultCode) resultCode   Enum value being ignored
	 *    
	 * Should we test the result code as follows? Are we to assume success?
	 * 
	 * assertTrue("Result code didn't match expected value.", data.getResultCode().equals(VehicleDataResultCode.SUCCESS));
	 */
	
	public void testGps(){
		VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getGps();
		
		assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_GPS));
    }
	
	public void testOdometer(){
		VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getOdometer();
		
		assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_ODOMETER));
    }

    public void testTireStatus(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getTirePressure();
        
        assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_TIREPRESSURE));
    }

    public void testBeltStatus(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getBeltStatus();
    	
    	assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_BELTSTATUS));
    }

    public void testBodyInformation(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getBodyInformation();
    	
    	assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_BODYINFO));
    }

    public void testDeviceStatus(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getDeviceStatus();
    	
    	assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_DEVICESTATUS));
    }

    public void testHeadLampStatus(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getHeadLampStatus();
    	
    	assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_HEADLAMPSTATUS));
    }

    public void testECallInfo(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getECallInfo();
    	
    	assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_ECALLINFO));
    }

    public void testAirbagStatus(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getAirbagStatus();
    	
    	assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_AIRBAGSTATUS));
    }

    public void testEmergencyEvent(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getEmergencyEvent();
    	
    	assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_EMERGENCYEVENT));
    }

    public void testClusterModeStatus(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getClusterModeStatus();
    	
    	assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_CLUSTERMODESTATUS));
    }

    public void testMyKey(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getMyKey();
    	
    	assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_MYKEY));
    }

    public void testSpeed(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getSpeed();
    	
    	assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_SPEED));
    }

    public void testRpm(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getRpm();
    	
        assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_RPM));
    }

    public void testFuelLevel(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getFuelLevel();
    	
        assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_FUELLEVEL));
    }

    public void testInstantFuelConsumption(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getInstantFuelConsumption();
    	
        assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_FUELCONSUMPTION));
    }

    public void testExternalTemperature(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getExternalTemperature();
    	
    	assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_EXTERNTEMP));
    }

    public void testEngineTorque(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getEngineTorque();
    	
    	assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_ENGINETORQUE));
    }

    public void testAccPedalPosition(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getAccPedalPosition();
    	
    	assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_ACCPEDAL));
    }

    public void testSteeringWheelAngle(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getSteeringWheelAngle();
    	
        assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_STEERINGWHEEL));
    }

    public void testFuelLevelState(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getFuelLevel_State();
    	
        assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_FUELLEVEL_STATE));
    }

    public void testPRNDL(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getPrndl();
    	
        assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_PRNDL));
    }

    public void testDriverBraking(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getDriverBraking();
    	
        assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_BRAKING));
    }

    public void testWiperStatus(){
    	VehicleDataResult data = ( (UnsubscribeVehicleDataResponse) msg ).getWiperStatus();
    	
        assertTrue("Returned data was not of expected type.", data.getDataType().equals(VehicleDataType.VEHICLEDATA_WIPERSTATUS));
    }

	public void testNull() {
		UnsubscribeVehicleDataResponse msg = new UnsubscribeVehicleDataResponse();
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

}
