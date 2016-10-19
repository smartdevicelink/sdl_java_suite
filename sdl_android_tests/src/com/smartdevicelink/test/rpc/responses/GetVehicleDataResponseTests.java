package com.smartdevicelink.test.rpc.responses;

import java.util.Hashtable;
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
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.HeadLampStatus;
import com.smartdevicelink.proxy.rpc.MyKey;
import com.smartdevicelink.proxy.rpc.SingleTireStatus;
import com.smartdevicelink.proxy.rpc.TireStatus;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.VehicleDataHelper;
import com.smartdevicelink.test.json.rpc.JsonFileReader;


/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.GetVehicleDataResponse}
 */
public class GetVehicleDataResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
    	return VehicleDataHelper.VEHICLE_DATA_RESPONSE;
    }
    
    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GET_VEHICLE_DATA.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(GetVehicleDataResponse.KEY_SPEED, VehicleDataHelper.SPEED);
            result.put(GetVehicleDataResponse.KEY_RPM, VehicleDataHelper.RPM);
            result.put(GetVehicleDataResponse.KEY_EXTERNAL_TEMPERATURE, VehicleDataHelper.EXTERNAL_TEMPERATURE);
            result.put(GetVehicleDataResponse.KEY_FUEL_LEVEL, VehicleDataHelper.FUEL_LEVEL);
            result.put(GetVehicleDataResponse.KEY_VIN, VehicleDataHelper.VIN);
            result.put(GetVehicleDataResponse.KEY_PRNDL, VehicleDataHelper.PRNDL_FINAL);
            result.put(GetVehicleDataResponse.KEY_TIRE_PRESSURE, VehicleDataHelper.TIRE_PRESSURE.serializeJSON());
            result.put(GetVehicleDataResponse.KEY_ENGINE_TORQUE, VehicleDataHelper.ENGINE_TORQUE);
            result.put(GetVehicleDataResponse.KEY_ODOMETER, VehicleDataHelper.ODOMETER);
            result.put(GetVehicleDataResponse.KEY_GPS, VehicleDataHelper.GPS.serializeJSON());
            result.put(GetVehicleDataResponse.KEY_FUEL_LEVEL_STATE, VehicleDataHelper.FUEL_LEVEL_STATE);
            result.put(GetVehicleDataResponse.KEY_INSTANT_FUEL_CONSUMPTION, VehicleDataHelper.INSTANT_FUEL_CONSUMPTION);
            result.put(GetVehicleDataResponse.KEY_BELT_STATUS, VehicleDataHelper.BELT_STATUS.serializeJSON());
            result.put(GetVehicleDataResponse.KEY_BODY_INFORMATION, VehicleDataHelper.BODY_INFORMATION.serializeJSON());
            result.put(GetVehicleDataResponse.KEY_DEVICE_STATUS, VehicleDataHelper.DEVICE_STATUS.serializeJSON());
            result.put(GetVehicleDataResponse.KEY_DRIVER_BRAKING, VehicleDataHelper.DRIVER_BRAKING);
            result.put(GetVehicleDataResponse.KEY_WIPER_STATUS, VehicleDataHelper.WIPER_STATUS);
            result.put(GetVehicleDataResponse.KEY_HEAD_LAMP_STATUS, VehicleDataHelper.HEAD_LAMP_STATUS.serializeJSON());
            result.put(GetVehicleDataResponse.KEY_ACC_PEDAL_POSITION, VehicleDataHelper.ACC_PEDAL_POSITION);
            result.put(GetVehicleDataResponse.KEY_STEERING_WHEEL_ANGLE, VehicleDataHelper.STEERING_WHEEL_ANGLE);
            result.put(GetVehicleDataResponse.KEY_E_CALL_INFO, VehicleDataHelper.E_CALL_INFO.serializeJSON());
            result.put(GetVehicleDataResponse.KEY_AIRBAG_STATUS, VehicleDataHelper.AIRBAG_STATUS.serializeJSON());
            result.put(GetVehicleDataResponse.KEY_EMERGENCY_EVENT, VehicleDataHelper.EMERGENCY_EVENT.serializeJSON());
            result.put(GetVehicleDataResponse.KEY_CLUSTER_MODE_STATUS, VehicleDataHelper.CLUSTER_MODE_STATUS.serializeJSON());
            result.put(GetVehicleDataResponse.KEY_MY_KEY, VehicleDataHelper.MY_KEY.serializeJSON());
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }

        return result;
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
			//set up the JSONObject to represent GetVehicleDataResponse
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
			
			reference.put(GetVehicleDataResponse.KEY_SPEED, VehicleDataHelper.SPEED);
			reference.put(GetVehicleDataResponse.KEY_RPM, VehicleDataHelper.RPM);
			reference.put(GetVehicleDataResponse.KEY_EXTERNAL_TEMPERATURE, VehicleDataHelper.EXTERNAL_TEMPERATURE);
			reference.put(GetVehicleDataResponse.KEY_FUEL_LEVEL, VehicleDataHelper.FUEL_LEVEL);
			reference.put(GetVehicleDataResponse.KEY_VIN, VehicleDataHelper.VIN);
			reference.put(GetVehicleDataResponse.KEY_PRNDL, VehicleDataHelper.PRNDL_FINAL);
			reference.put(GetVehicleDataResponse.KEY_TIRE_PRESSURE, tireStatusObj);
			reference.put(GetVehicleDataResponse.KEY_ENGINE_TORQUE, VehicleDataHelper.ENGINE_TORQUE);
			reference.put(GetVehicleDataResponse.KEY_ODOMETER, VehicleDataHelper.ODOMETER);
			reference.put(GetVehicleDataResponse.KEY_GPS, GPSDataObj);
			reference.put(GetVehicleDataResponse.KEY_FUEL_LEVEL_STATE, VehicleDataHelper.FUEL_LEVEL_STATE);
			reference.put(GetVehicleDataResponse.KEY_INSTANT_FUEL_CONSUMPTION, VehicleDataHelper.INSTANT_FUEL_CONSUMPTION);
			reference.put(GetVehicleDataResponse.KEY_BELT_STATUS, beltStatusObj);
			reference.put(GetVehicleDataResponse.KEY_BODY_INFORMATION, bodyInformationObj);
			reference.put(GetVehicleDataResponse.KEY_DEVICE_STATUS, deviceStatusObj);
			reference.put(GetVehicleDataResponse.KEY_DRIVER_BRAKING, VehicleDataHelper.DRIVER_BRAKING);
			reference.put(GetVehicleDataResponse.KEY_WIPER_STATUS, VehicleDataHelper.WIPER_STATUS);
			reference.put(GetVehicleDataResponse.KEY_HEAD_LAMP_STATUS, headLampStatusObj);
			reference.put(GetVehicleDataResponse.KEY_ACC_PEDAL_POSITION, VehicleDataHelper.ACC_PEDAL_POSITION);
			reference.put(GetVehicleDataResponse.KEY_STEERING_WHEEL_ANGLE, VehicleDataHelper.STEERING_WHEEL_ANGLE);
			reference.put(GetVehicleDataResponse.KEY_E_CALL_INFO, ECallInfoObj);
			reference.put(GetVehicleDataResponse.KEY_AIRBAG_STATUS, airbagStatusObj);
			reference.put(GetVehicleDataResponse.KEY_EMERGENCY_EVENT, emergencyEventObj);
			reference.put(GetVehicleDataResponse.KEY_CLUSTER_MODE_STATUS, clusterModeStatusObj);
			reference.put(GetVehicleDataResponse.KEY_MY_KEY, myKeyObj);
			
			JSONObject underTest = msg.serializeJSON();
			
			//go inside underTest and only return the JSONObject inside the parameters key inside the response key
			underTest = underTest.getJSONObject("response").getJSONObject("parameters");

			assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if (key.equals(GetVehicleDataResponse.KEY_TIRE_PRESSURE)) {
					JSONObject tirePressureReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject tirePressureTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateTireStatus(
									new TireStatus(JsonRPCMarshaller.deserializeJSONObject(tirePressureReference)),
									new TireStatus(JsonRPCMarshaller.deserializeJSONObject(tirePressureTest))));
					
				}
				else if (key.equals(GetVehicleDataResponse.KEY_GPS)) {
					JSONObject GPSObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject GPSObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateGpsData(
									new GPSData(JsonRPCMarshaller.deserializeJSONObject(GPSObjReference)),
									new GPSData(JsonRPCMarshaller.deserializeJSONObject(GPSObjTest))));
				}
				else if (key.equals(GetVehicleDataResponse.KEY_BELT_STATUS)) {
					JSONObject beltObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject beltObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateBeltStatus(
									new BeltStatus(JsonRPCMarshaller.deserializeJSONObject(beltObjReference)),
									new BeltStatus(JsonRPCMarshaller.deserializeJSONObject(beltObjTest))));
				}
				else if (key.equals(GetVehicleDataResponse.KEY_BODY_INFORMATION)) {
					JSONObject bodyInfoObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject bodyInfoObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateBodyInformation(
									new BodyInformation(JsonRPCMarshaller.deserializeJSONObject(bodyInfoObjReference)),
									new BodyInformation(JsonRPCMarshaller.deserializeJSONObject(bodyInfoObjTest))));
				}
				else if (key.equals(GetVehicleDataResponse.KEY_DEVICE_STATUS)) {
					JSONObject deviceObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject deviceObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateDeviceStatus(
									new DeviceStatus(JsonRPCMarshaller.deserializeJSONObject(deviceObjReference)),
									new DeviceStatus(JsonRPCMarshaller.deserializeJSONObject(deviceObjTest))));
				}
				else if (key.equals(GetVehicleDataResponse.KEY_HEAD_LAMP_STATUS)) {
					JSONObject headLampObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject headLampObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateHeadLampStatus(
									new HeadLampStatus(JsonRPCMarshaller.deserializeJSONObject(headLampObjReference)),
									new HeadLampStatus(JsonRPCMarshaller.deserializeJSONObject(headLampObjTest))));
				}
				else if (key.equals(GetVehicleDataResponse.KEY_E_CALL_INFO)) {
					JSONObject callInfoObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject callInfoObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateECallInfo(
									new ECallInfo(JsonRPCMarshaller.deserializeJSONObject(callInfoObjReference)),
									new ECallInfo(JsonRPCMarshaller.deserializeJSONObject(callInfoObjTest))));
				}
				else if (key.equals(GetVehicleDataResponse.KEY_AIRBAG_STATUS)) {
					JSONObject airbagObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject airbagObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateAirbagStatus(
									new AirbagStatus(JsonRPCMarshaller.deserializeJSONObject(airbagObjReference)),
									new AirbagStatus(JsonRPCMarshaller.deserializeJSONObject(airbagObjTest))));
				}
				else if (key.equals(GetVehicleDataResponse.KEY_EMERGENCY_EVENT)) {
					JSONObject emergencyObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject emergencyObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateEmergencyEvent(
									new EmergencyEvent(JsonRPCMarshaller.deserializeJSONObject(emergencyObjReference)),
									new EmergencyEvent(JsonRPCMarshaller.deserializeJSONObject(emergencyObjTest))));
				}
				else if (key.equals(GetVehicleDataResponse.KEY_CLUSTER_MODE_STATUS)) {
					JSONObject clusterModeObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject clusterModeObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateClusterModeStatus(
									new ClusterModeStatus(JsonRPCMarshaller.deserializeJSONObject(clusterModeObjReference)),
									new ClusterModeStatus(JsonRPCMarshaller.deserializeJSONObject(clusterModeObjTest))));
				}
				else if (key.equals(GetVehicleDataResponse.KEY_MY_KEY)) {
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
			fail(Test.JSON_FAIL);
		}
    }
    
    /**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues(){		
		// Valid Tests
		assertEquals(Test.MATCH, VehicleDataHelper.SPEED, ( (GetVehicleDataResponse) msg ).getSpeed());
		assertEquals(Test.MATCH, (Integer) VehicleDataHelper.RPM, ( (GetVehicleDataResponse) msg ).getRpm());
		assertEquals(Test.MATCH, VehicleDataHelper.EXTERNAL_TEMPERATURE, ( (GetVehicleDataResponse) msg ).getExternalTemperature());
		assertEquals(Test.MATCH, VehicleDataHelper.FUEL_LEVEL, ( (GetVehicleDataResponse) msg ).getFuelLevel());
		assertEquals(Test.MATCH, VehicleDataHelper.PRNDL_FINAL, ( (GetVehicleDataResponse) msg ).getPrndl());
		assertEquals(Test.MATCH, VehicleDataHelper.TIRE_PRESSURE, ( (GetVehicleDataResponse) msg ).getTirePressure());
		assertEquals(Test.MATCH, VehicleDataHelper.ENGINE_TORQUE, ( (GetVehicleDataResponse) msg ).getEngineTorque());
		assertEquals(Test.MATCH, (Integer) VehicleDataHelper.ODOMETER, ( (GetVehicleDataResponse) msg ).getOdometer());
		assertEquals(Test.MATCH, VehicleDataHelper.GPS, ( (GetVehicleDataResponse) msg ).getGps());
		assertEquals(Test.MATCH, VehicleDataHelper.FUEL_LEVEL_STATE, ( (GetVehicleDataResponse) msg ).getFuelLevelState());
		assertEquals(Test.MATCH, VehicleDataHelper.INSTANT_FUEL_CONSUMPTION, ( (GetVehicleDataResponse) msg ).getInstantFuelConsumption());
		assertEquals(Test.MATCH, VehicleDataHelper.BELT_STATUS, ( (GetVehicleDataResponse) msg ).getBeltStatus());
		assertEquals(Test.MATCH, VehicleDataHelper.BODY_INFORMATION, ( (GetVehicleDataResponse) msg ).getBodyInformation());
		assertEquals(Test.MATCH, VehicleDataHelper.DEVICE_STATUS, ( (GetVehicleDataResponse) msg ).getDeviceStatus());
		assertEquals(Test.MATCH, VehicleDataHelper.DRIVER_BRAKING, ( (GetVehicleDataResponse) msg ).getDriverBraking());
		assertEquals(Test.MATCH, VehicleDataHelper.WIPER_STATUS, ( (GetVehicleDataResponse) msg ).getWiperStatus());
		assertEquals(Test.MATCH, VehicleDataHelper.HEAD_LAMP_STATUS, ( (GetVehicleDataResponse) msg ).getHeadLampStatus());
		assertEquals(Test.MATCH, VehicleDataHelper.ACC_PEDAL_POSITION, ( (GetVehicleDataResponse) msg ).getAccPedalPosition());
		assertEquals(Test.MATCH, VehicleDataHelper.STEERING_WHEEL_ANGLE, ( (GetVehicleDataResponse) msg ).getSteeringWheelAngle());
		assertEquals(Test.MATCH, VehicleDataHelper.E_CALL_INFO, ( (GetVehicleDataResponse) msg ).getECallInfo());
		assertEquals(Test.MATCH, VehicleDataHelper.AIRBAG_STATUS, ( (GetVehicleDataResponse) msg ).getAirbagStatus());
		assertEquals(Test.MATCH, VehicleDataHelper.EMERGENCY_EVENT, ( (GetVehicleDataResponse) msg ).getEmergencyEvent());
		assertEquals(Test.MATCH, VehicleDataHelper.CLUSTER_MODE_STATUS, ( (GetVehicleDataResponse) msg ).getClusterModeStatus());
		assertEquals(Test.MATCH, VehicleDataHelper.MY_KEY, ( (GetVehicleDataResponse) msg ).getMyKey());
		
		// Invalid/Null Tests
		GetVehicleDataResponse msg = new GetVehicleDataResponse();
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
			GetVehicleDataResponse cmd = new GetVehicleDataResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_SPEED), cmd.getSpeed());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetVehicleDataResponse.KEY_RPM), cmd.getRpm());
			assertEquals(Test.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_EXTERNAL_TEMPERATURE), cmd.getExternalTemperature());
			assertEquals(Test.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_FUEL_LEVEL), cmd.getFuelLevel());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetVehicleDataResponse.KEY_VIN), cmd.getVin());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetVehicleDataResponse.KEY_PRNDL), cmd.getPrndl().toString());
			
			JSONObject tireStatusObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_TIRE_PRESSURE);
			TireStatus tireStatus = new TireStatus(JsonRPCMarshaller.deserializeJSONObject(tireStatusObj));
			assertTrue(Test.TRUE, Validator.validateTireStatus(tireStatus, cmd.getTirePressure()) );
			
			assertEquals(Test.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_ENGINE_TORQUE), cmd.getEngineTorque());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetVehicleDataResponse.KEY_ODOMETER), cmd.getOdometer());
			
			JSONObject gpsDataObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_GPS);
			GPSData gpsData = new GPSData(JsonRPCMarshaller.deserializeJSONObject(gpsDataObj));
			assertTrue(Test.TRUE, Validator.validateGpsData(gpsData, cmd.getGps()) );
			
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetVehicleDataResponse.KEY_FUEL_LEVEL_STATE), cmd.getFuelLevelState().toString());
			assertEquals(Test.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_INSTANT_FUEL_CONSUMPTION), cmd.getInstantFuelConsumption());
			
			JSONObject beltStatusObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_BELT_STATUS);
			BeltStatus beltStatus = new BeltStatus(JsonRPCMarshaller.deserializeJSONObject(beltStatusObj));
			assertTrue(Test.TRUE, Validator.validateBeltStatus(beltStatus, cmd.getBeltStatus()) );
			
			JSONObject bodyInformationObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_BODY_INFORMATION);
			BodyInformation bodyInformation = new BodyInformation(JsonRPCMarshaller.deserializeJSONObject(bodyInformationObj));
			assertTrue(Test.TRUE, Validator.validateBodyInformation(bodyInformation, cmd.getBodyInformation()) );
			
			JSONObject deviceStatusObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_DEVICE_STATUS);
			DeviceStatus deviceStatus = new DeviceStatus(JsonRPCMarshaller.deserializeJSONObject(deviceStatusObj));
			assertTrue(Test.TRUE, Validator.validateDeviceStatus(deviceStatus, cmd.getDeviceStatus()) );
			
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetVehicleDataResponse.KEY_DRIVER_BRAKING), cmd.getDriverBraking().toString());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetVehicleDataResponse.KEY_WIPER_STATUS), cmd.getWiperStatus().toString());
			
			JSONObject headLampStatusObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_HEAD_LAMP_STATUS);
			HeadLampStatus headLampStatus = new HeadLampStatus(JsonRPCMarshaller.deserializeJSONObject(headLampStatusObj));
			assertTrue(Test.TRUE, Validator.validateHeadLampStatus(headLampStatus, cmd.getHeadLampStatus()) );
			
			assertEquals(Test.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_ACC_PEDAL_POSITION), cmd.getAccPedalPosition());
			assertEquals(Test.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_STEERING_WHEEL_ANGLE), cmd.getSteeringWheelAngle());
			
			JSONObject eCallInfoObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_E_CALL_INFO);
			ECallInfo eCallInfo = new ECallInfo(JsonRPCMarshaller.deserializeJSONObject(eCallInfoObj));
			assertTrue(Test.TRUE, Validator.validateECallInfo(eCallInfo, cmd.getECallInfo()) );
			
			JSONObject airbagStatusObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_AIRBAG_STATUS);
			AirbagStatus airbagStatus = new AirbagStatus(JsonRPCMarshaller.deserializeJSONObject(airbagStatusObj));
			assertTrue(Test.TRUE, Validator.validateAirbagStatus(airbagStatus, cmd.getAirbagStatus()) );
			
			JSONObject emergencyEventObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_EMERGENCY_EVENT);
			EmergencyEvent emergencyEvent = new EmergencyEvent(JsonRPCMarshaller.deserializeJSONObject(emergencyEventObj));
			assertTrue(Test.TRUE, Validator.validateEmergencyEvent(emergencyEvent, cmd.getEmergencyEvent()) );
			
			JSONObject clusterModeStatusObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_CLUSTER_MODE_STATUS);
			ClusterModeStatus clusterModeStatus = new ClusterModeStatus(JsonRPCMarshaller.deserializeJSONObject(clusterModeStatusObj));
			assertTrue(Test.TRUE, Validator.validateClusterModeStatus(clusterModeStatus, cmd.getClusterModeStatus()) );
			
			JSONObject myKeyObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_MY_KEY);
			MyKey myKey = new MyKey(JsonRPCMarshaller.deserializeJSONObject(myKeyObj));
			assertTrue(Test.TRUE, Validator.validateMyKey(myKey, cmd.getMyKey()) );
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }    
}