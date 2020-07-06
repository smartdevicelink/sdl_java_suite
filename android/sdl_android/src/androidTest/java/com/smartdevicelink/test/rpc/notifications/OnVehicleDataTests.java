package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AirbagStatus;
import com.smartdevicelink.proxy.rpc.BeltStatus;
import com.smartdevicelink.proxy.rpc.BodyInformation;
import com.smartdevicelink.proxy.rpc.ClusterModeStatus;
import com.smartdevicelink.proxy.rpc.DeviceStatus;
import com.smartdevicelink.proxy.rpc.ECallInfo;
import com.smartdevicelink.proxy.rpc.EmergencyEvent;
import com.smartdevicelink.proxy.rpc.FuelRange;
import com.smartdevicelink.proxy.rpc.GPSData;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.HeadLampStatus;
import com.smartdevicelink.proxy.rpc.MyKey;
import com.smartdevicelink.proxy.rpc.OnVehicleData;
import com.smartdevicelink.proxy.rpc.SingleTireStatus;
import com.smartdevicelink.proxy.rpc.StabilityControlsStatus;
import com.smartdevicelink.proxy.rpc.TireStatus;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.ElectronicParkBrakeStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.TurnSignal;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.proxy.rpc.enums.WiperStatus;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.VehicleDataHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.OnVehicleData}
 */
public class OnVehicleDataTests extends BaseRpcTests{
	
    @Override
    protected RPCMessage createMessage(){
    	return VehicleDataHelper.VEHICLE_DATA;
    }

    @Override
    protected String getMessageType(){
    	return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
    	return FunctionID.ON_VEHICLE_DATA.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnVehicleData.KEY_SPEED, VehicleDataHelper.SPEED);
            result.put(OnVehicleData.KEY_RPM, VehicleDataHelper.RPM);
            result.put(OnVehicleData.KEY_EXTERNAL_TEMPERATURE, VehicleDataHelper.EXTERNAL_TEMPERATURE);
            result.put(OnVehicleData.KEY_FUEL_LEVEL, VehicleDataHelper.FUEL_LEVEL);
            result.put(OnVehicleData.KEY_VIN, VehicleDataHelper.VIN);
            result.put(OnVehicleData.KEY_PRNDL, VehicleDataHelper.PRNDL_FINAL);
            result.put(OnVehicleData.KEY_TIRE_PRESSURE, VehicleDataHelper.TIRE_PRESSURE.serializeJSON());
            result.put(OnVehicleData.KEY_ENGINE_TORQUE, VehicleDataHelper.ENGINE_TORQUE);
            result.put(OnVehicleData.KEY_ENGINE_OIL_LIFE, VehicleDataHelper.ENGINE_OIL_LIFE);
            result.put(OnVehicleData.KEY_ODOMETER, VehicleDataHelper.ODOMETER);
            result.put(OnVehicleData.KEY_GPS, VehicleDataHelper.GPS.serializeJSON());
            result.put(OnVehicleData.KEY_FUEL_LEVEL_STATE, VehicleDataHelper.FUEL_LEVEL_STATE);
            result.put(OnVehicleData.KEY_INSTANT_FUEL_CONSUMPTION, VehicleDataHelper.INSTANT_FUEL_CONSUMPTION);
            result.put(OnVehicleData.KEY_BELT_STATUS, VehicleDataHelper.BELT_STATUS.serializeJSON());
            result.put(OnVehicleData.KEY_BODY_INFORMATION, VehicleDataHelper.BODY_INFORMATION.serializeJSON());
            result.put(OnVehicleData.KEY_DEVICE_STATUS, VehicleDataHelper.DEVICE_STATUS.serializeJSON());
            result.put(OnVehicleData.KEY_DRIVER_BRAKING, VehicleDataHelper.DRIVER_BRAKING);
            result.put(OnVehicleData.KEY_WIPER_STATUS, VehicleDataHelper.WIPER_STATUS);
            result.put(OnVehicleData.KEY_HEAD_LAMP_STATUS, VehicleDataHelper.HEAD_LAMP_STATUS.serializeJSON());
            result.put(OnVehicleData.KEY_ACC_PEDAL_POSITION, VehicleDataHelper.ACC_PEDAL_POSITION);
            result.put(OnVehicleData.KEY_STEERING_WHEEL_ANGLE, VehicleDataHelper.STEERING_WHEEL_ANGLE);
            result.put(OnVehicleData.KEY_E_CALL_INFO, VehicleDataHelper.E_CALL_INFO.serializeJSON());
            result.put(OnVehicleData.KEY_AIRBAG_STATUS, VehicleDataHelper.AIRBAG_STATUS.serializeJSON());
            result.put(OnVehicleData.KEY_EMERGENCY_EVENT, VehicleDataHelper.EMERGENCY_EVENT.serializeJSON());
            result.put(OnVehicleData.KEY_CLUSTER_MODE_STATUS, VehicleDataHelper.CLUSTER_MODE_STATUS.serializeJSON());
            result.put(OnVehicleData.KEY_MY_KEY, VehicleDataHelper.MY_KEY.serializeJSON());
            result.put(OnVehicleData.KEY_FUEL_RANGE, VehicleDataHelper.JSON_FUEL_RANGE);
			result.put(OnVehicleData.KEY_TURN_SIGNAL, VehicleDataHelper.TURN_SIGNAL);
			result.put(OnVehicleData.KEY_ELECTRONIC_PARK_BRAKE_STATUS, VehicleDataHelper.ELECTRONIC_PARK_BRAKE_STATUS);
			result.put(OnVehicleData.KEY_STABILITY_CONTROLS_STATUS, VehicleDataHelper.STABILITY_CONTROLS_STATUS);
			result.put(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME, VehicleDataHelper.OEM_CUSTOM_VEHICLE_DATA_STATE);
        } catch(JSONException e) {
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
    	Double speed = ( (OnVehicleData) msg).getSpeed();
    	int rpm = ( (OnVehicleData) msg).getRpm();
    	Double external = ( (OnVehicleData) msg).getExternalTemperature();
    	Double fuelLevel = ( (OnVehicleData) msg).getFuelLevel();
    	String vin = ( (OnVehicleData) msg).getVin();
    	PRNDL prndl = ( (OnVehicleData) msg).getPrndl();
    	TireStatus pressure = ( (OnVehicleData) msg).getTirePressure();
    	Double torque = ( (OnVehicleData) msg).getEngineTorque();
    	Float engineOilLife = ( (OnVehicleData) msg).getEngineOilLife();
    	int odometer = ( (OnVehicleData) msg).getOdometer();
    	GPSData gps = ( (OnVehicleData) msg).getGps();
    	ComponentVolumeStatus state = ( (OnVehicleData) msg).getFuelLevelState();
    	Double consumption = ( (OnVehicleData) msg).getInstantFuelConsumption();
    	BeltStatus belt = ( (OnVehicleData) msg).getBeltStatus();
    	BodyInformation body = ( (OnVehicleData) msg).getBodyInformation();
    	DeviceStatus device = ( (OnVehicleData) msg).getDeviceStatus();
    	VehicleDataEventStatus brake = ( (OnVehicleData) msg).getDriverBraking();
    	WiperStatus wiper = ( (OnVehicleData) msg).getWiperStatus();
    	HeadLampStatus lamp = ( (OnVehicleData) msg).getHeadLampStatus();
    	Double pedal = ( (OnVehicleData) msg).getAccPedalPosition();
    	Double wheel = ( (OnVehicleData) msg).getSteeringWheelAngle();
    	ECallInfo ecall = ( (OnVehicleData) msg).getECallInfo();
    	AirbagStatus airbag = ( (OnVehicleData) msg).getAirbagStatus();
    	EmergencyEvent event = ( (OnVehicleData) msg).getEmergencyEvent();
    	ClusterModeStatus cluster = ( (OnVehicleData) msg).getClusterModeStatus();
    	MyKey key = ( (OnVehicleData) msg).getMyKey();
    	List<FuelRange> fuelRangeList = ( (OnVehicleData) msg).getFuelRange();
    	TurnSignal turnSignal = ( (OnVehicleData) msg).getTurnSignal();
    	ElectronicParkBrakeStatus electronicParkBrakeStatus = ( (OnVehicleData) msg).getElectronicParkBrakeStatus();
    	StabilityControlsStatus stabilityControlsStatus = ( (OnVehicleData) msg).getStabilityControlsStatus();
    	Object oemCustomVehicleData = ( (OnVehicleData) msg).getOEMCustomVehicleData(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME);
    	
    	// Valid Tests
    	assertEquals(TestValues.MATCH, VehicleDataHelper.SPEED, speed);
    	assertEquals(TestValues.MATCH, VehicleDataHelper.RPM, rpm);
    	assertEquals(TestValues.MATCH, VehicleDataHelper.EXTERNAL_TEMPERATURE, external);
    	assertEquals(TestValues.MATCH, VehicleDataHelper.FUEL_LEVEL, fuelLevel);
    	assertEquals(TestValues.MATCH, VehicleDataHelper.VIN, vin);
    	assertEquals(TestValues.MATCH, VehicleDataHelper.PRNDL_FINAL, prndl);
    	assertTrue(TestValues.MATCH, Validator.validateTireStatus(VehicleDataHelper.TIRE_PRESSURE, pressure));
    	assertEquals(TestValues.MATCH, VehicleDataHelper.ENGINE_TORQUE, torque);
    	assertEquals(TestValues.MATCH, VehicleDataHelper.ENGINE_OIL_LIFE, engineOilLife);
    	assertEquals(TestValues.MATCH, VehicleDataHelper.ODOMETER, odometer);
    	assertTrue(TestValues.MATCH, Validator.validateGpsData(VehicleDataHelper.GPS, gps));
    	assertEquals(TestValues.MATCH, VehicleDataHelper.FUEL_LEVEL_STATE, state);
    	assertEquals(TestValues.MATCH, VehicleDataHelper.INSTANT_FUEL_CONSUMPTION, consumption);
    	assertTrue(TestValues.TRUE, Validator.validateBeltStatus(VehicleDataHelper.BELT_STATUS, belt));
    	assertTrue(TestValues.TRUE, Validator.validateBodyInformation(VehicleDataHelper.BODY_INFORMATION, body));
    	assertTrue(TestValues.TRUE, Validator.validateDeviceStatus(VehicleDataHelper.DEVICE_STATUS, device));
    	assertEquals(TestValues.MATCH, VehicleDataHelper.DRIVER_BRAKING, brake);
    	assertEquals(TestValues.MATCH, VehicleDataHelper.WIPER_STATUS, wiper);
    	assertTrue(TestValues.TRUE, Validator.validateHeadLampStatus(VehicleDataHelper.HEAD_LAMP_STATUS, lamp));
		assertEquals(TestValues.MATCH, VehicleDataHelper.ACC_PEDAL_POSITION, pedal);
    	assertEquals(TestValues.MATCH, VehicleDataHelper.STEERING_WHEEL_ANGLE, wheel);
    	assertTrue(TestValues.TRUE, Validator.validateECallInfo(VehicleDataHelper.E_CALL_INFO, ecall));
    	assertTrue(TestValues.TRUE, Validator.validateAirbagStatus(VehicleDataHelper.AIRBAG_STATUS, airbag));
	    assertTrue(TestValues.TRUE, Validator.validateEmergencyEvent(VehicleDataHelper.EMERGENCY_EVENT, event));
	    assertTrue(TestValues.TRUE, Validator.validateClusterModeStatus(VehicleDataHelper.CLUSTER_MODE_STATUS, cluster));
	    assertTrue(TestValues.TRUE, Validator.validateMyKey(VehicleDataHelper.MY_KEY, key));
	    assertTrue(TestValues.TRUE, Validator.validateFuelRange(VehicleDataHelper.FUEL_RANGE_LIST, fuelRangeList));
	    assertEquals(TestValues.MATCH, VehicleDataHelper.TURN_SIGNAL, turnSignal);
	    assertEquals(TestValues.MATCH, VehicleDataHelper.ELECTRONIC_PARK_BRAKE_STATUS, electronicParkBrakeStatus);
	    assertEquals(TestValues.MATCH, VehicleDataHelper.STABILITY_CONTROLS_STATUS, stabilityControlsStatus);
	    assertEquals(TestValues.MATCH, VehicleDataHelper.OEM_CUSTOM_VEHICLE_DATA_STATE, oemCustomVehicleData);
    
	    // Invalid/Null Tests
        OnVehicleData msg = new OnVehicleData();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getSpeed());
        assertNull(TestValues.NULL, msg.getRpm());
        assertNull(TestValues.NULL, msg.getExternalTemperature());
        assertNull(TestValues.NULL, msg.getFuelLevel());
        assertNull(TestValues.NULL, msg.getVin());
        assertNull(TestValues.NULL, msg.getPrndl());
        assertNull(TestValues.NULL, msg.getTirePressure());
        assertNull(TestValues.NULL, msg.getEngineTorque());
        assertNull(TestValues.NULL, msg.getEngineOilLife());
        assertNull(TestValues.NULL, msg.getOdometer());
        assertNull(TestValues.NULL, msg.getGps());
        assertNull(TestValues.NULL, msg.getFuelLevelState());
        assertNull(TestValues.NULL, msg.getInstantFuelConsumption());
        assertNull(TestValues.NULL, msg.getBeltStatus());
        assertNull(TestValues.NULL, msg.getBodyInformation());
        assertNull(TestValues.NULL, msg.getDeviceStatus());
        assertNull(TestValues.NULL, msg.getDriverBraking());
        assertNull(TestValues.NULL, msg.getWiperStatus());
        assertNull(TestValues.NULL, msg.getHeadLampStatus());
        assertNull(TestValues.NULL, msg.getAccPedalPosition());
        assertNull(TestValues.NULL, msg.getSteeringWheelAngle());
        assertNull(TestValues.NULL, msg.getECallInfo());
        assertNull(TestValues.NULL, msg.getAirbagStatus());
        assertNull(TestValues.NULL, msg.getEmergencyEvent());
        assertNull(TestValues.NULL, msg.getClusterModeStatus());
        assertNull(TestValues.NULL, msg.getMyKey());
        assertNull(TestValues.NULL, msg.getFuelRange());
        assertNull(TestValues.NULL, msg.getTurnSignal());
        assertNull(TestValues.NULL, msg.getElectronicParkBrakeStatus());
        assertNull(TestValues.NULL, msg.getStabilityControlsStatus());
        assertNull(TestValues.NULL, msg.getOEMCustomVehicleData(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME));
	}

	@Test
    public void testJson() {
		JSONObject reference = new JSONObject();
		
		//Objects needed on the first level.
		JSONObject tireStatusObj = new JSONObject();
		JSONObject GPSDataObj = new JSONObject();
		JSONObject beltStatusObj = new JSONObject();
		JSONObject bodyInformationObj = new JSONObject();
		JSONObject deviceStatusObj = new JSONObject();
		JSONObject headLampStatusObj = new JSONObject();
		JSONObject ECallInfoObj = new JSONObject();
		JSONObject airbagStatusObj = new JSONObject();
		JSONObject emergencyEventObj = new JSONObject();
		JSONObject clusterModeStatusObj = new JSONObject();
		JSONObject myKeyObj = new JSONObject();
		JSONObject fuelRangeObj = new JSONObject();
		JSONObject stabilityControlStatusObj = new JSONObject();
		JSONArray fuelRangeArrayObj = new JSONArray();

		try {
			//Set up the JSONObject to represent OnVehicleData:
			
			//TIRE_PRESSURE
			tireStatusObj.put(TireStatus.KEY_PRESSURE_TELL_TALE, VehicleDataHelper.TIRE_PRESSURE_TELL_TALE);
			JSONObject tireLeftFront = new JSONObject();
			tireLeftFront.put(SingleTireStatus.KEY_STATUS, VehicleDataHelper.TIRE_PRESSURE_LEFT_FRONT);
			tireStatusObj.put(TireStatus.KEY_LEFT_FRONT, tireLeftFront);
			JSONObject tireRightFront = new JSONObject();
			tireRightFront.put(SingleTireStatus.KEY_STATUS, VehicleDataHelper.TIRE_PRESSURE_RIGHT_FRONT);
			tireStatusObj.put(TireStatus.KEY_RIGHT_FRONT, tireRightFront);
			JSONObject tireLeftRear = new JSONObject();
			tireLeftRear.put(SingleTireStatus.KEY_STATUS, VehicleDataHelper.TIRE_PRESSURE_LEFT_REAR);
			tireStatusObj.put(TireStatus.KEY_LEFT_REAR, tireLeftRear);
			JSONObject tireRightRear = new JSONObject();
			tireRightRear.put(SingleTireStatus.KEY_STATUS, VehicleDataHelper.TIRE_PRESSURE_RIGHT_REAR);
			tireStatusObj.put(TireStatus.KEY_RIGHT_REAR, tireRightRear);
			JSONObject tireInnerLeftRear = new JSONObject();
			tireInnerLeftRear.put(SingleTireStatus.KEY_STATUS, VehicleDataHelper.TIRE_PRESSURE_INNER_LEFT_REAR);
			tireStatusObj.put(TireStatus.KEY_INNER_LEFT_REAR, tireInnerLeftRear);
			JSONObject tireInnerRightRear = new JSONObject();
			tireInnerRightRear.put(SingleTireStatus.KEY_STATUS, VehicleDataHelper.TIRE_PRESSURE_INNER_RIGHT_REAR);
			tireStatusObj.put(TireStatus.KEY_INNER_RIGHT_REAR, tireInnerRightRear);
			
			//GPS
			GPSDataObj.put(GPSData.KEY_LONGITUDE_DEGREES, VehicleDataHelper.GPS_LONGITUDE);
			GPSDataObj.put(GPSData.KEY_LATITUDE_DEGREES, VehicleDataHelper.GPS_LATITUDE);
			GPSDataObj.put(GPSData.KEY_UTC_YEAR, VehicleDataHelper.GPS_YEAR);
			GPSDataObj.put(GPSData.KEY_UTC_MONTH, VehicleDataHelper.GPS_MONTH);
			GPSDataObj.put(GPSData.KEY_UTC_DAY, VehicleDataHelper.GPS_DAY);
			GPSDataObj.put(GPSData.KEY_UTC_HOURS, VehicleDataHelper.GPS_HOURS);
			GPSDataObj.put(GPSData.KEY_UTC_MINUTES, VehicleDataHelper.GPS_MINUTES);
			GPSDataObj.put(GPSData.KEY_UTC_SECONDS, VehicleDataHelper.GPS_SECONDS);
			GPSDataObj.put(GPSData.KEY_COMPASS_DIRECTION, VehicleDataHelper.GPS_DIRECTION);
			GPSDataObj.put(GPSData.KEY_PDOP, VehicleDataHelper.GPS_PDOP);
			GPSDataObj.put(GPSData.KEY_VDOP, VehicleDataHelper.GPS_VDOP);
			GPSDataObj.put(GPSData.KEY_HDOP, VehicleDataHelper.GPS_HDOP);
			GPSDataObj.put(GPSData.KEY_ACTUAL, VehicleDataHelper.GPS_ACTUAL);
			GPSDataObj.put(GPSData.KEY_SATELLITES, VehicleDataHelper.GPS_SATELLITES);
			GPSDataObj.put(GPSData.KEY_DIMENSION, VehicleDataHelper.GPS_DIMENSION);
			GPSDataObj.put(GPSData.KEY_ALTITUDE, VehicleDataHelper.GPS_ALTITUDE);
			GPSDataObj.put(GPSData.KEY_HEADING, VehicleDataHelper.GPS_HEADING);
			GPSDataObj.put(GPSData.KEY_SPEED, VehicleDataHelper.GPS_SPEED);
			GPSDataObj.put(GPSData.KEY_SHIFTED, VehicleDataHelper.GPS_SHIFTED);
			
			//BELT_STATUS
			beltStatusObj.put(BeltStatus.KEY_DRIVER_BELT_DEPLOYED, VehicleDataHelper.BELT_STATUS_DRIVER_DEPLOYED);
			beltStatusObj.put(BeltStatus.KEY_PASSENGER_BELT_DEPLOYED, VehicleDataHelper.BELT_STATUS_PASSENGER_DEPLOYED);
			beltStatusObj.put(BeltStatus.KEY_PASSENGER_BUCKLE_BELTED, VehicleDataHelper.BELT_STATUS_PASSENGER_BELTED);
			beltStatusObj.put(BeltStatus.KEY_DRIVER_BUCKLE_BELTED, VehicleDataHelper.BELT_STATUS_DRIVER_BELTED);
			beltStatusObj.put(BeltStatus.KEY_LEFT_ROW_2_BUCKLE_BELTED, VehicleDataHelper.BELT_STATUS_LEFT_ROW_2_BELTED);
			beltStatusObj.put(BeltStatus.KEY_PASSENGER_CHILD_DETECTED, VehicleDataHelper.BELT_STATUS_PASSENGER_CHILD);
			beltStatusObj.put(BeltStatus.KEY_RIGHT_ROW_2_BUCKLE_BELTED, VehicleDataHelper.BELT_STATUS_RIGHT_ROW_2_BELTED);
			beltStatusObj.put(BeltStatus.KEY_MIDDLE_ROW_2_BUCKLE_BELTED, VehicleDataHelper.BELT_STATUS_MIDDLE_ROW_2_BELTED);
			beltStatusObj.put(BeltStatus.KEY_MIDDLE_ROW_3_BUCKLE_BELTED, VehicleDataHelper.BELT_STATUS_MIDDLE_ROW_3_BELTED);
			beltStatusObj.put(BeltStatus.KEY_LEFT_ROW_3_BUCKLE_BELTED, VehicleDataHelper.BELT_STATUS_LEFT_ROW_3_BELTED);
			beltStatusObj.put(BeltStatus.KEY_RIGHT_ROW_3_BUCKLE_BELTED, VehicleDataHelper.BELT_STATUS_RIGHT_ROW_3_BELTED);
			beltStatusObj.put(BeltStatus.KEY_LEFT_REAR_INFLATABLE_BELTED, VehicleDataHelper.BELT_STATUS_LEFT_REAR_INFLATABLE_BELTED);
			beltStatusObj.put(BeltStatus.KEY_RIGHT_REAR_INFLATABLE_BELTED, VehicleDataHelper.BELT_STATUS_RIGHT_REAR_INFLATABLE_BELTED);
			beltStatusObj.put(BeltStatus.KEY_MIDDLE_ROW_1_BELT_DEPLOYED, VehicleDataHelper.BELT_STATUS_MIDDLE_ROW_1_DEPLOYED);
			beltStatusObj.put(BeltStatus.KEY_MIDDLE_ROW_1_BUCKLE_BELTED, VehicleDataHelper.BELT_STATUS_MIDDLE_ROW_1_BELTED);
			
			//BODY_INFORMATION
			bodyInformationObj.put(BodyInformation.KEY_PARK_BRAKE_ACTIVE, VehicleDataHelper.BODY_INFORMATION_PARK_BRAKE);
			bodyInformationObj.put(BodyInformation.KEY_IGNITION_STABLE_STATUS, VehicleDataHelper.BODY_INFORMATION_IGNITION_STATUS);
			bodyInformationObj.put(BodyInformation.KEY_IGNITION_STATUS, VehicleDataHelper.BODY_INFORMATION_IGNITION_STABLE_STATUS);
			bodyInformationObj.put(BodyInformation.KEY_DRIVER_DOOR_AJAR, VehicleDataHelper.BODY_INFORMATION_DRIVER_AJAR);
			bodyInformationObj.put(BodyInformation.KEY_PASSENGER_DOOR_AJAR, VehicleDataHelper.BODY_INFORMATION_PASSENGER_AJAR);
			bodyInformationObj.put(BodyInformation.KEY_REAR_LEFT_DOOR_AJAR, VehicleDataHelper.BODY_INFORMATION_REAR_LEFT_AJAR);
			bodyInformationObj.put(BodyInformation.KEY_REAR_RIGHT_DOOR_AJAR, VehicleDataHelper.BODY_INFORMATION_REAR_RIGHT_AJAR);
			
			//DEVICE_STATUS
			deviceStatusObj.put(DeviceStatus.KEY_VOICE_REC_ON, VehicleDataHelper.DEVICE_STATUS_VOICE_REC);
			deviceStatusObj.put(DeviceStatus.KEY_BT_ICON_ON, VehicleDataHelper.DEVICE_STATUS_BT_ICON);
			deviceStatusObj.put(DeviceStatus.KEY_CALL_ACTIVE, VehicleDataHelper.DEVICE_STATUS_CALL_ACTIVE);
			deviceStatusObj.put(DeviceStatus.KEY_PHONE_ROAMING, VehicleDataHelper.DEVICE_STATUS_PHONE_ROAMING);
			deviceStatusObj.put(DeviceStatus.KEY_TEXT_MSG_AVAILABLE, VehicleDataHelper.DEVICE_STATUS_TEXT_MSG_AVAILABLE);
			deviceStatusObj.put(DeviceStatus.KEY_BATT_LEVEL_STATUS, VehicleDataHelper.DEVICE_STATUS_BATT_LEVEL_STATUS);
			deviceStatusObj.put(DeviceStatus.KEY_STEREO_AUDIO_OUTPUT_MUTED, VehicleDataHelper.DEVICE_STATUS_STEREO_MUTED);
			deviceStatusObj.put(DeviceStatus.KEY_MONO_AUDIO_OUTPUT_MUTED, VehicleDataHelper.DEVICE_STATUS_MONO_MUTED);
			deviceStatusObj.put(DeviceStatus.KEY_SIGNAL_LEVEL_STATUS, VehicleDataHelper.DEVICE_STATUS_SIGNAL_LEVEL_STATUS);
			deviceStatusObj.put(DeviceStatus.KEY_PRIMARY_AUDIO_SOURCE, VehicleDataHelper.DEVICE_STATUS_PRIMARY_AUDIO);
			deviceStatusObj.put(DeviceStatus.KEY_E_CALL_EVENT_ACTIVE, VehicleDataHelper.DEVICE_STATUS_E_CALL_ACTIVE);
			
			//HEAD_LAMP_STATUS
			headLampStatusObj.put(HeadLampStatus.KEY_AMBIENT_LIGHT_SENSOR_STATUS, VehicleDataHelper.HEAD_LAMP_STATUS_AMBIENT_STATUS);
			headLampStatusObj.put(HeadLampStatus.KEY_HIGH_BEAMS_ON, VehicleDataHelper.HEAD_LAMP_HIGH_BEAMS);
			headLampStatusObj.put(HeadLampStatus.KEY_LOW_BEAMS_ON, VehicleDataHelper.HEAD_LAMP_LOW_BEAMS);
			
			//E_CALL_INFO
			ECallInfoObj.put(ECallInfo.KEY_E_CALL_NOTIFICATION_STATUS, VehicleDataHelper.E_CALL_INFO_E_CALL_NOTIFICATION_STATUS);
			ECallInfoObj.put(ECallInfo.KEY_AUX_E_CALL_NOTIFICATION_STATUS, VehicleDataHelper.E_CALL_INFO_AUX_E_CALL_NOTIFICATION_STATUS);
			ECallInfoObj.put(ECallInfo.KEY_E_CALL_CONFIRMATION_STATUS, VehicleDataHelper.E_CALL_INFO_CONFIRMATION_STATUS);
	    	
			//AIRBAG_STATUS
			airbagStatusObj.put(AirbagStatus.KEY_DRIVER_AIRBAG_DEPLOYED, VehicleDataHelper.AIRBAG_STATUS_DRIVER_DEPLOYED);
			airbagStatusObj.put(AirbagStatus.KEY_DRIVER_SIDE_AIRBAG_DEPLOYED, VehicleDataHelper.AIRBAG_STATUS_DRIVER_SIDE_DEPLOYED);
			airbagStatusObj.put(AirbagStatus.KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED, VehicleDataHelper.AIRBAG_STATUS_DRIVER_CURTAIN_DEPLOYED);
			airbagStatusObj.put(AirbagStatus.KEY_DRIVER_KNEE_AIRBAG_DEPLOYED, VehicleDataHelper.AIRBAG_STATUS_DRIVER_KNEE_DEPLOYED);
			airbagStatusObj.put(AirbagStatus.KEY_PASSENGER_AIRBAG_DEPLOYED, VehicleDataHelper.AIRBAG_STATUS_PASSENGER_DEPLOYED);
			airbagStatusObj.put(AirbagStatus.KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED, VehicleDataHelper.AIRBAG_STATUS_PASSENGER_SIDE_DEPLOYED);
			airbagStatusObj.put(AirbagStatus.KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED, VehicleDataHelper.AIRBAG_STATUS_PASSENGER_CURTAIN_DEPLOYED);
			airbagStatusObj.put(AirbagStatus.KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED, VehicleDataHelper.AIRBAG_STATUS_PASSENGER_KNEE_DEPLOYED);
			
			//EMERGENCY_EVENT
			emergencyEventObj.put(EmergencyEvent.KEY_EMERGENCY_EVENT_TYPE, VehicleDataHelper.EMERGENCY_EVENT_TYPE);
			emergencyEventObj.put(EmergencyEvent.KEY_FUEL_CUTOFF_STATUS, VehicleDataHelper.EMERGENCY_EVENT_FUEL_CUTOFF_STATUS);
			emergencyEventObj.put(EmergencyEvent.KEY_ROLLOVER_EVENT, VehicleDataHelper.EMERGENCY_EVENT_ROLLOVER_EVENT);
			emergencyEventObj.put(EmergencyEvent.KEY_MAXIMUM_CHANGE_VELOCITY, VehicleDataHelper.EMERGENCY_EVENT_MAX_CHANGE_VELOCITY);
			emergencyEventObj.put(EmergencyEvent.KEY_MULTIPLE_EVENTS, VehicleDataHelper.EMERGENCY_EVENT_MULTIPLE_EVENTS);
			
			//CLUSTER_MODE_STATUS
			clusterModeStatusObj.put(ClusterModeStatus.KEY_POWER_MODE_ACTIVE, VehicleDataHelper.CLUSTER_MODE_STATUS_POWER_MODE_ACTIVE);
			clusterModeStatusObj.put(ClusterModeStatus.KEY_POWER_MODE_QUALIFICATION_STATUS, VehicleDataHelper.CLUSTER_MODE_STATUS_POWER_MODE_QUALIFICATION_STATUS);
			clusterModeStatusObj.put(ClusterModeStatus.KEY_CAR_MODE_STATUS, VehicleDataHelper.CLUSTER_MODE_STATUS_CAR_MODE_STATUS);
			clusterModeStatusObj.put(ClusterModeStatus.KEY_POWER_MODE_STATUS, VehicleDataHelper.CLUSTER_MODE_STATUS_POWER_MODE_STATUS);
			
			//MY_KEY
			myKeyObj.put(MyKey.KEY_E_911_OVERRIDE, VehicleDataHelper.MY_KEY_E_911_OVERRIDE);

			// FUEL_RANGE
			fuelRangeObj.put(FuelRange.KEY_TYPE, VehicleDataHelper.FUEL_RANGE_TYPE);
			fuelRangeObj.put(FuelRange.KEY_RANGE, VehicleDataHelper.FUEL_RANGE_RANGE);
			fuelRangeArrayObj.put(fuelRangeObj);

			// STABILITY_CONTROLS_STATU
			stabilityControlStatusObj.put(StabilityControlsStatus.KEY_ESC_SYSTEM, VehicleDataHelper.ESC_SYSTEM);
			stabilityControlStatusObj.put(StabilityControlsStatus.KEY_TRAILER_SWAY_CONTROL, VehicleDataHelper.S_WAY_TRAILER);

			reference.put(OnVehicleData.KEY_SPEED, VehicleDataHelper.SPEED);
			reference.put(OnVehicleData.KEY_RPM, VehicleDataHelper.RPM);
			reference.put(OnVehicleData.KEY_EXTERNAL_TEMPERATURE, VehicleDataHelper.EXTERNAL_TEMPERATURE);
			reference.put(OnVehicleData.KEY_FUEL_LEVEL, VehicleDataHelper.FUEL_LEVEL);
			reference.put(OnVehicleData.KEY_VIN, VehicleDataHelper.VIN);
			reference.put(OnVehicleData.KEY_PRNDL, VehicleDataHelper.PRNDL_FINAL);
			reference.put(OnVehicleData.KEY_TIRE_PRESSURE, tireStatusObj);
			reference.put(OnVehicleData.KEY_ENGINE_TORQUE, VehicleDataHelper.ENGINE_TORQUE);
			reference.put(OnVehicleData.KEY_ENGINE_OIL_LIFE, VehicleDataHelper.ENGINE_OIL_LIFE);
			reference.put(OnVehicleData.KEY_ODOMETER, VehicleDataHelper.ODOMETER);
			reference.put(OnVehicleData.KEY_GPS, GPSDataObj);
			reference.put(OnVehicleData.KEY_FUEL_LEVEL_STATE, VehicleDataHelper.FUEL_LEVEL_STATE);
			reference.put(OnVehicleData.KEY_INSTANT_FUEL_CONSUMPTION, VehicleDataHelper.INSTANT_FUEL_CONSUMPTION);
			reference.put(OnVehicleData.KEY_BELT_STATUS, beltStatusObj);
			reference.put(OnVehicleData.KEY_BODY_INFORMATION, bodyInformationObj);
			reference.put(OnVehicleData.KEY_DEVICE_STATUS, deviceStatusObj);
			reference.put(OnVehicleData.KEY_DRIVER_BRAKING, VehicleDataHelper.DRIVER_BRAKING);
			reference.put(OnVehicleData.KEY_WIPER_STATUS, VehicleDataHelper.WIPER_STATUS);
			reference.put(OnVehicleData.KEY_HEAD_LAMP_STATUS, headLampStatusObj);
			reference.put(OnVehicleData.KEY_ACC_PEDAL_POSITION, VehicleDataHelper.ACC_PEDAL_POSITION);
			reference.put(OnVehicleData.KEY_STEERING_WHEEL_ANGLE, VehicleDataHelper.STEERING_WHEEL_ANGLE);
			reference.put(OnVehicleData.KEY_E_CALL_INFO, ECallInfoObj);
			reference.put(OnVehicleData.KEY_AIRBAG_STATUS, airbagStatusObj);
			reference.put(OnVehicleData.KEY_EMERGENCY_EVENT, emergencyEventObj);
			reference.put(OnVehicleData.KEY_CLUSTER_MODE_STATUS, clusterModeStatusObj);
			reference.put(OnVehicleData.KEY_MY_KEY, myKeyObj);
			reference.put(OnVehicleData.KEY_FUEL_RANGE, fuelRangeArrayObj);
			reference.put(OnVehicleData.KEY_TURN_SIGNAL, VehicleDataHelper.TURN_SIGNAL);
			reference.put(OnVehicleData.KEY_ELECTRONIC_PARK_BRAKE_STATUS, VehicleDataHelper.ELECTRONIC_PARK_BRAKE_STATUS);
			reference.put(OnVehicleData.KEY_STABILITY_CONTROLS_STATUS, stabilityControlStatusObj);
			reference.put(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME, VehicleDataHelper.OEM_CUSTOM_VEHICLE_DATA_STATE);
			
			JSONObject underTest = msg.serializeJSON();
			//go inside underTest and only return the JSONObject inside the parameters key inside the notification key
			underTest = underTest.getJSONObject("notification").getJSONObject("parameters");
			
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if (key.equals(OnVehicleData.KEY_TIRE_PRESSURE)) {
					JSONObject tirePressureReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject tirePressureTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(TestValues.TRUE, Validator.validateTireStatus(
						new TireStatus(JsonRPCMarshaller.deserializeJSONObject(tirePressureReference)),
						new TireStatus(JsonRPCMarshaller.deserializeJSONObject(tirePressureTest))));
					
				} else if (key.equals(OnVehicleData.KEY_GPS)) {
					JSONObject GPSObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject GPSObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(TestValues.TRUE, Validator.validateGpsData(
						new GPSData(JsonRPCMarshaller.deserializeJSONObject(GPSObjReference)),
						new GPSData(JsonRPCMarshaller.deserializeJSONObject(GPSObjTest))));
				} else if (key.equals(OnVehicleData.KEY_BELT_STATUS)) {
					JSONObject beltObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject beltObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(TestValues.TRUE, Validator.validateBeltStatus(
						new BeltStatus(JsonRPCMarshaller.deserializeJSONObject(beltObjReference)),
						new BeltStatus(JsonRPCMarshaller.deserializeJSONObject(beltObjTest))));
				} else if (key.equals(OnVehicleData.KEY_BODY_INFORMATION)) {
					JSONObject bodyInfoObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject bodyInfoObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(TestValues.TRUE, Validator.validateBodyInformation(
						new BodyInformation(JsonRPCMarshaller.deserializeJSONObject(bodyInfoObjReference)),
						new BodyInformation(JsonRPCMarshaller.deserializeJSONObject(bodyInfoObjTest))));
				} else if (key.equals(OnVehicleData.KEY_DEVICE_STATUS)) {
					JSONObject deviceObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject deviceObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(TestValues.TRUE, Validator.validateDeviceStatus(
						new DeviceStatus(JsonRPCMarshaller.deserializeJSONObject(deviceObjReference)),
						new DeviceStatus(JsonRPCMarshaller.deserializeJSONObject(deviceObjTest))));
				} else if (key.equals(OnVehicleData.KEY_HEAD_LAMP_STATUS)) {
					JSONObject headLampObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject headLampObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(TestValues.TRUE, Validator.validateHeadLampStatus(
						new HeadLampStatus(JsonRPCMarshaller.deserializeJSONObject(headLampObjReference)),
						new HeadLampStatus(JsonRPCMarshaller.deserializeJSONObject(headLampObjTest))));
				} else if (key.equals(OnVehicleData.KEY_E_CALL_INFO)) {
					JSONObject callInfoObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject callInfoObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(TestValues.TRUE, Validator.validateECallInfo(
						new ECallInfo(JsonRPCMarshaller.deserializeJSONObject(callInfoObjReference)),
						new ECallInfo(JsonRPCMarshaller.deserializeJSONObject(callInfoObjTest))));
				} else if (key.equals(OnVehicleData.KEY_AIRBAG_STATUS)) {
					JSONObject airbagObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject airbagObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(TestValues.TRUE, Validator.validateAirbagStatus(
						new AirbagStatus(JsonRPCMarshaller.deserializeJSONObject(airbagObjReference)),
						new AirbagStatus(JsonRPCMarshaller.deserializeJSONObject(airbagObjTest))));
				}
				else if (key.equals(OnVehicleData.KEY_EMERGENCY_EVENT)) {
					JSONObject emergencyObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject emergencyObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(TestValues.TRUE, Validator.validateEmergencyEvent(
						new EmergencyEvent(JsonRPCMarshaller.deserializeJSONObject(emergencyObjReference)),
						new EmergencyEvent(JsonRPCMarshaller.deserializeJSONObject(emergencyObjTest))));
				}
				else if (key.equals(OnVehicleData.KEY_CLUSTER_MODE_STATUS)) {
					JSONObject clusterModeObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject clusterModeObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(TestValues.TRUE, Validator.validateClusterModeStatus(
						new ClusterModeStatus(JsonRPCMarshaller.deserializeJSONObject(clusterModeObjReference)),
						new ClusterModeStatus(JsonRPCMarshaller.deserializeJSONObject(clusterModeObjTest))));
				}
				else if (key.equals(OnVehicleData.KEY_MY_KEY)) {
					JSONObject myKeyObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject myKeyObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(TestValues.TRUE, Validator.validateMyKey(
						new MyKey(JsonRPCMarshaller.deserializeJSONObject(myKeyObjReference)),
						new MyKey(JsonRPCMarshaller.deserializeJSONObject(myKeyObjTest))));
				}
				else if (key.equals(OnVehicleData.KEY_ENGINE_OIL_LIFE)) {
					assertEquals(JsonUtils.readDoubleFromJsonObject(reference, key), JsonUtils.readDoubleFromJsonObject(underTest, key));
				}
				else if (key.equals(OnVehicleData.KEY_FUEL_RANGE)) {
					JSONArray fuelRangeArrayObjReference = JsonUtils.readJsonArrayFromJsonObject(reference, key);
					List<FuelRange> fuelRangeRefereceList = new ArrayList<FuelRange>();
					for (int index = 0; index < fuelRangeArrayObjReference.length(); index++) {
						FuelRange fuelRange = new FuelRange(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)fuelRangeArrayObjReference.get(index) ));
						fuelRangeRefereceList.add(fuelRange);
					}

					JSONArray fuelRangeArrayObjTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					List<FuelRange> fuelRangeUnderTestList = new ArrayList<FuelRange>();
					for (int index = 0; index < fuelRangeArrayObjTest.length(); index++) {
						FuelRange fuelRange = new FuelRange(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)fuelRangeArrayObjTest.get(index) ));
						fuelRangeUnderTestList.add(fuelRange);
					}

					assertTrue(TestValues.TRUE, Validator.validateFuelRange(
									fuelRangeRefereceList,
									fuelRangeUnderTestList));
				}
				else if (key.equals(GetVehicleDataResponse.KEY_STABILITY_CONTROLS_STATUS)) {
					JSONObject myKeyObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject myKeyObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);

					StabilityControlsStatus status1 = new StabilityControlsStatus(JsonRPCMarshaller.deserializeJSONObject(myKeyObjReference));
					StabilityControlsStatus status2 = new StabilityControlsStatus(JsonRPCMarshaller.deserializeJSONObject(myKeyObjTest));

					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateStabilityControlStatus(
									status1,
									status2
							)
					);
				}
				else {
					assertEquals(TestValues.TRUE, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
	            }				
			}			
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
    }
}
