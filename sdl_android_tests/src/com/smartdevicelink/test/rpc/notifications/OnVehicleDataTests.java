package com.smartdevicelink.test.rpc.notifications;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.smartdevicelink.proxy.rpc.GPSData;
import com.smartdevicelink.proxy.rpc.HeadLampStatus;
import com.smartdevicelink.proxy.rpc.MyKey;
import com.smartdevicelink.proxy.rpc.OnVehicleData;
import com.smartdevicelink.proxy.rpc.SingleTireStatus;
import com.smartdevicelink.proxy.rpc.TireStatus;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.proxy.rpc.enums.WiperStatus;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;
import com.smartdevicelink.test.utils.VehicleDataHelper;


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
    	return FunctionID.ON_VEHICLE_DATA;
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
            
        }catch(JSONException e){
            //do nothing 
        }

        return result;
    }
    
    public void testSpeed() {
    	Double copy = ( (OnVehicleData) msg).getSpeed();
    	assertEquals("Speed does not match input speed", VehicleDataHelper.SPEED, copy);
    }
    
    public void testRpm() {
    	int copy = ( (OnVehicleData) msg).getRpm();
    	assertEquals("RPM does not match input RPM", VehicleDataHelper.RPM, copy);
    }
    
    public void testExternalTemperature() {
    	Double copy = ( (OnVehicleData) msg).getExternalTemperature();
    	assertEquals("External temperature does not match input external temperature", VehicleDataHelper.EXTERNAL_TEMPERATURE, copy);
    }
    
    public void testFuelLevel() {
    	Double copy = ( (OnVehicleData) msg).getFuelLevel();
    	assertEquals("Fuel level does not match input fuel level", VehicleDataHelper.FUEL_LEVEL, copy);
    }
    
    public void testVin() {
    	String copy = ( (OnVehicleData) msg).getVin();
    	assertEquals("VIN does not match input VIN", VehicleDataHelper.VIN, copy);
    }
    
    public void testPRNDL() {
    	PRNDL copy = ( (OnVehicleData) msg).getPrndl();
    	assertEquals("PRNDL does not match input PRNDL", VehicleDataHelper.PRNDL_FINAL, copy);
    }
    
    public void testTirePressure() {
    	TireStatus copy = ( (OnVehicleData) msg).getTirePressure();
    	assertTrue("Tire pressure does not match input tire pressure", Validator.validateTireStatus(VehicleDataHelper.TIRE_PRESSURE, copy));
    }
    
    public void testEngineTorque() {
    	Double copy = ( (OnVehicleData) msg).getEngineTorque();
    	assertEquals("Engine torque does not match input engine torque", VehicleDataHelper.ENGINE_TORQUE, copy);
    }
    
    public void testOdometer() {
    	int copy = ( (OnVehicleData) msg).getOdometer();
    	assertEquals("Odometer does not match input odometer", VehicleDataHelper.ODOMETER, copy);
    }
    
    public void testGps() {
    	GPSData copy = ( (OnVehicleData) msg).getGps();
    	assertTrue("GPS does not match input GPS", Validator.validateGpsData(VehicleDataHelper.GPS, copy));
    }
    
    public void testFuelLevel_State() {
    	ComponentVolumeStatus copy = ( (OnVehicleData) msg).getFuelLevel_State();
    	assertEquals("Fuel level does not match input fuel level", VehicleDataHelper.FUEL_LEVEL_STATE, copy);
    }
    
    public void testInstantFuelConsumption() {
    	Double copy = ( (OnVehicleData) msg).getInstantFuelConsumption();
    	assertEquals("Instant fuel consumption does not match input instant fuel consumption", VehicleDataHelper.INSTANT_FUEL_CONSUMPTION, copy);
    }
    
    public void testBeltStatus() {
    	BeltStatus copy = ( (OnVehicleData) msg).getBeltStatus();
    	assertTrue("Belt status does not match input belt status", Validator.validateBeltStatus(VehicleDataHelper.BELT_STATUS, copy));
    }
    
    public void testBodyInformation() {
    	BodyInformation copy = ( (OnVehicleData) msg).getBodyInformation();
    	assertTrue("Body information does not match input body information", Validator.validateBodyInformation(VehicleDataHelper.BODY_INFORMATION, copy));
    }
    
    public void testDeviceStatus() {
    	DeviceStatus copy = ( (OnVehicleData) msg).getDeviceStatus();
    	assertTrue("Device status does not match input device status", Validator.validateDeviceStatus(VehicleDataHelper.DEVICE_STATUS, copy));
    }
    
    public void testDriverBraking() {
    	VehicleDataEventStatus copy = ( (OnVehicleData) msg).getDriverBraking();
    	assertEquals("Driver braking does not match input driver braking", VehicleDataHelper.DRIVER_BRAKING, copy);
    }
    
    public void testWiperStatus() {
    	WiperStatus copy = ( (OnVehicleData) msg).getWiperStatus();
    	assertEquals("Wiper status does not match input wiper status", VehicleDataHelper.WIPER_STATUS, copy);
    }
    
    public void testHeadLampStatus() {
    	HeadLampStatus copy = ( (OnVehicleData) msg).getHeadLampStatus();
    	assertTrue("Head lamp status does not match input head lamp status", Validator.validateHeadLampStatus(VehicleDataHelper.HEAD_LAMP_STATUS, copy));
    }
    
    public void testAccPedalPosition() {
    	Double copy = ( (OnVehicleData) msg).getAccPedalPosition();
    	assertEquals("Acc pedal position does not match input acc pedal position", VehicleDataHelper.ACC_PEDAL_POSITION, copy);
    }
    
    public void testSteeringWheelAngle() {
    	Double copy = ( (OnVehicleData) msg).getSteeringWheelAngle();
    	assertEquals("Steering wheel angle does not match input steering wheel angle", VehicleDataHelper.STEERING_WHEEL_ANGLE, copy);
    }
    
    public void testECallInfo() {
    	ECallInfo copy = ( (OnVehicleData) msg).getECallInfo();
    	assertTrue("Emergency call info does not match input emergency call info", Validator.validateECallInfo(VehicleDataHelper.E_CALL_INFO, copy));
    }
    
    public void testAirbagStatus() {
    	AirbagStatus copy = ( (OnVehicleData) msg).getAirbagStatus();
    	assertTrue("Airbag status does not match input airbag status", Validator.validateAirbagStatus(VehicleDataHelper.AIRBAG_STATUS, copy));
    }
    
    public void testEmergencyEvent() {
    	EmergencyEvent copy = ( (OnVehicleData) msg).getEmergencyEvent();
    	assertTrue("Emergency event does not match input emergency event", Validator.validateEmergencyEvent(VehicleDataHelper.EMERGENCY_EVENT, copy));
    }
    
    public void testClusterModeStatus() {
    	ClusterModeStatus copy = ( (OnVehicleData) msg).getClusterModeStatus();
    	assertTrue("Cluster mode status does not match cluster mode status", Validator.validateClusterModeStatus(VehicleDataHelper.CLUSTER_MODE_STATUS, copy));
    }
    
    public void testMyKey() {
    	MyKey copy = ( (OnVehicleData) msg).getMyKey();
    	assertTrue("My key does not match my key", Validator.validateMyKey(VehicleDataHelper.MY_KEY, copy));
    }

    public void testJson() {
		JSONObject reference = new JSONObject();
		
		//objects needed on the first level
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
		
		try {
			//set up the JSONObject to represent OnVehicleData
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
			beltStatusObj.put(BeltStatus.KEY_REAR_INFLATABLE_BELTED, VehicleDataHelper.BELT_STATUS_LEFT_REAR_INFLATABLE_BELTED);
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
			
			reference.put(OnVehicleData.KEY_SPEED, VehicleDataHelper.SPEED);
			reference.put(OnVehicleData.KEY_RPM, VehicleDataHelper.RPM);
			reference.put(OnVehicleData.KEY_EXTERNAL_TEMPERATURE, VehicleDataHelper.EXTERNAL_TEMPERATURE);
			reference.put(OnVehicleData.KEY_FUEL_LEVEL, VehicleDataHelper.FUEL_LEVEL);
			reference.put(OnVehicleData.KEY_VIN, VehicleDataHelper.VIN);
			reference.put(OnVehicleData.KEY_PRNDL, VehicleDataHelper.PRNDL_FINAL);
			reference.put(OnVehicleData.KEY_TIRE_PRESSURE, tireStatusObj);
			reference.put(OnVehicleData.KEY_ENGINE_TORQUE, VehicleDataHelper.ENGINE_TORQUE);
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
			
			JSONObject underTest = msg.serializeJSON();
			//go inside underTest and only return the JSONObject inside the parameters key inside the notification key
			underTest = underTest.getJSONObject("notification").getJSONObject("parameters");
			
			assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if (key.equals(OnVehicleData.KEY_TIRE_PRESSURE)) {
					JSONObject tirePressureReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject tirePressureTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateTireStatus(
									new TireStatus(JsonRPCMarshaller.deserializeJSONObject(tirePressureReference)),
									new TireStatus(JsonRPCMarshaller.deserializeJSONObject(tirePressureTest))));
					
				}
				else if (key.equals(OnVehicleData.KEY_GPS)) {
					JSONObject GPSObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject GPSObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateGpsData(
									new GPSData(JsonRPCMarshaller.deserializeJSONObject(GPSObjReference)),
									new GPSData(JsonRPCMarshaller.deserializeJSONObject(GPSObjTest))));
				}
				else if (key.equals(OnVehicleData.KEY_BELT_STATUS)) {
					JSONObject beltObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject beltObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateBeltStatus(
									new BeltStatus(JsonRPCMarshaller.deserializeJSONObject(beltObjReference)),
									new BeltStatus(JsonRPCMarshaller.deserializeJSONObject(beltObjTest))));
				}
				else if (key.equals(OnVehicleData.KEY_BODY_INFORMATION)) {
					JSONObject bodyInfoObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject bodyInfoObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateBodyInformation(
									new BodyInformation(JsonRPCMarshaller.deserializeJSONObject(bodyInfoObjReference)),
									new BodyInformation(JsonRPCMarshaller.deserializeJSONObject(bodyInfoObjTest))));
				}
				else if (key.equals(OnVehicleData.KEY_DEVICE_STATUS)) {
					JSONObject deviceObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject deviceObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateDeviceStatus(
									new DeviceStatus(JsonRPCMarshaller.deserializeJSONObject(deviceObjReference)),
									new DeviceStatus(JsonRPCMarshaller.deserializeJSONObject(deviceObjTest))));
				}
				else if (key.equals(OnVehicleData.KEY_HEAD_LAMP_STATUS)) {
					JSONObject headLampObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject headLampObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateHeadLampStatus(
									new HeadLampStatus(JsonRPCMarshaller.deserializeJSONObject(headLampObjReference)),
									new HeadLampStatus(JsonRPCMarshaller.deserializeJSONObject(headLampObjTest))));
				}
				else if (key.equals(OnVehicleData.KEY_E_CALL_INFO)) {
					JSONObject callInfoObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject callInfoObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateECallInfo(
									new ECallInfo(JsonRPCMarshaller.deserializeJSONObject(callInfoObjReference)),
									new ECallInfo(JsonRPCMarshaller.deserializeJSONObject(callInfoObjTest))));
				}
				else if (key.equals(OnVehicleData.KEY_AIRBAG_STATUS)) {
					JSONObject airbagObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject airbagObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateAirbagStatus(
									new AirbagStatus(JsonRPCMarshaller.deserializeJSONObject(airbagObjReference)),
									new AirbagStatus(JsonRPCMarshaller.deserializeJSONObject(airbagObjTest))));
				}
				else if (key.equals(OnVehicleData.KEY_EMERGENCY_EVENT)) {
					JSONObject emergencyObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject emergencyObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateEmergencyEvent(
									new EmergencyEvent(JsonRPCMarshaller.deserializeJSONObject(emergencyObjReference)),
									new EmergencyEvent(JsonRPCMarshaller.deserializeJSONObject(emergencyObjTest))));
				}
				else if (key.equals(OnVehicleData.KEY_CLUSTER_MODE_STATUS)) {
					JSONObject clusterModeObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject clusterModeObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateClusterModeStatus(
									new ClusterModeStatus(JsonRPCMarshaller.deserializeJSONObject(clusterModeObjReference)),
									new ClusterModeStatus(JsonRPCMarshaller.deserializeJSONObject(clusterModeObjTest))));
				}
				else if (key.equals(OnVehicleData.KEY_MY_KEY)) {
					JSONObject myKeyObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject myKeyObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateMyKey(
									new MyKey(JsonRPCMarshaller.deserializeJSONObject(myKeyObjReference)),
									new MyKey(JsonRPCMarshaller.deserializeJSONObject(myKeyObjTest))));
				}
				else {
					assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
							JsonUtils.readObjectFromJsonObject(reference, key),
							JsonUtils.readObjectFromJsonObject(underTest, key));
	            }
				
			}
			
		} catch (JSONException e) {
			/* do nothing */
		}
    }
	

    public void testNull(){
        OnVehicleData msg = new OnVehicleData();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Speed wasn't set, but getter method returned an object.", msg.getSpeed());
        assertNull("RPM wasn't set, but getter method returned an object.", msg.getRpm());
        assertNull("External temperature wasn't set, but getter method returned an object.", msg.getExternalTemperature());
        assertNull("Fuel level wasn't set, but getter method returned an object.", msg.getFuelLevel());
        assertNull("VIN wasn't set, but getter method returned an object.", msg.getVin());
        assertNull("PRNDL wasn't set, but getter method returned an object.", msg.getPrndl());
        assertNull("Tire pressure wasn't set, but getter method returned an object.", msg.getTirePressure());
        assertNull("Engine torque wasn't set, but getter method returned an object.", msg.getEngineTorque());
        assertNull("Odometer wasn't set, but getter method returned an object.", msg.getOdometer());
        assertNull("GPS wasn't set, but getter method returned an object.", msg.getGps());
        assertNull("Fuel level state wasn't set, but getter method returned an object.", msg.getFuelLevel_State());
        assertNull("Instant fuel consumption wasn't set, but getter method returned an object.", msg.getInstantFuelConsumption());
        assertNull("Belt status wasn't set, but getter method returned an object.", msg.getBeltStatus());
        assertNull("Body information wasn't set, but getter method returned an object.", msg.getBodyInformation());
        assertNull("Device status wasn't set, but getter method returned an object.", msg.getDeviceStatus());
        assertNull("Driver braking wasn't set, but getter method returned an object.", msg.getDriverBraking());
        assertNull("Wiper status wasn't set, but getter method returned an object.", msg.getWiperStatus());
        assertNull("Head lamp status wasn't set, but getter method returned an object.", msg.getHeadLampStatus());
        assertNull("Acceleration pedal position wasn't set, but getter method returned an object.", msg.getAccPedalPosition());
        assertNull("Steering wheel angle wasn't set, but getter method returned an object.", msg.getSteeringWheelAngle());
        assertNull("Emergency call info wasn't set, but getter method returned an object.", msg.getECallInfo());
        assertNull("Airbag status wasn't set, but getter method returned an object.", msg.getAirbagStatus());
        assertNull("Emergency event wasn't set, but getter method returned an object.", msg.getEmergencyEvent());
        assertNull("Cluster mode status wasn't set, but getter method returned an object.", msg.getClusterModeStatus());
        assertNull("My key wasn't set, but getter method returned an object.", msg.getMyKey());
    	
    }
    

}
