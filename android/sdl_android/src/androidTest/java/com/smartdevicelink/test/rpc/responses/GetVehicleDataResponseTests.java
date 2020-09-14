package com.smartdevicelink.test.rpc.responses;

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
import com.smartdevicelink.proxy.rpc.GearStatus;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.HeadLampStatus;
import com.smartdevicelink.proxy.rpc.MyKey;
import com.smartdevicelink.proxy.rpc.SingleTireStatus;
import com.smartdevicelink.proxy.rpc.StabilityControlsStatus;
import com.smartdevicelink.proxy.rpc.TireStatus;
import com.smartdevicelink.proxy.rpc.WindowStatus;
import com.smartdevicelink.proxy.rpc.enums.TurnSignal;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.VehicleDataHelper;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;


/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.GetVehicleDataResponse}
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
            result.put(GetVehicleDataResponse.KEY_ENGINE_OIL_LIFE, VehicleDataHelper.ENGINE_OIL_LIFE);
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
            result.put(GetVehicleDataResponse.KEY_FUEL_RANGE, VehicleDataHelper.JSON_FUEL_RANGE);
            result.put(GetVehicleDataResponse.KEY_TURN_SIGNAL, VehicleDataHelper.TURN_SIGNAL);
            result.put(GetVehicleDataResponse.KEY_ELECTRONIC_PARK_BRAKE_STATUS, VehicleDataHelper.ELECTRONIC_PARK_BRAKE_STATUS);
			result.put(GetVehicleDataResponse.KEY_WINDOW_STATUS, VehicleDataHelper.WINDOW_STATUS_LIST);
            result.put(GetVehicleDataResponse.KEY_HANDS_OFF_STEERING, VehicleDataHelper.HANDS_OFF_STEERING);
            result.put(GetVehicleDataResponse.KEY_GEAR_STATUS, VehicleDataHelper.GEAR_STATUS);
			result.put(GetVehicleDataResponse.KEY_STABILITY_CONTROLS_STATUS, VehicleDataHelper.STABILITY_CONTROLS_STATUS);
			result.put(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME, VehicleDataHelper.OEM_CUSTOM_VEHICLE_DATA_STATE);
        } catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    @Test
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
		JSONObject stabilityControlsStatusObj = new JSONObject();
		JSONObject fuelRangeObj = new JSONObject();
		JSONObject windowStatusObj = new JSONObject();
		JSONObject gearStatusObj = new JSONObject();
		JSONArray  fuelRangeArrayObj = new JSONArray();
		JSONArray  windowStatusArrayObj = new JSONArray();

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

			// STABILITY_CONTROL_STATUS
			stabilityControlsStatusObj.put(StabilityControlsStatus.KEY_ESC_SYSTEM, VehicleDataHelper.ESC_SYSTEM);
			stabilityControlsStatusObj.put(StabilityControlsStatus.KEY_TRAILER_SWAY_CONTROL, VehicleDataHelper.S_WAY_TRAILER);

			//MY_KEY
			myKeyObj.put(MyKey.KEY_E_911_OVERRIDE, VehicleDataHelper.MY_KEY_E_911_OVERRIDE);

			// FUEL_RANGE
			fuelRangeObj.put(FuelRange.KEY_TYPE, VehicleDataHelper.FUEL_RANGE_TYPE);
			fuelRangeObj.put(FuelRange.KEY_RANGE, VehicleDataHelper.FUEL_RANGE_RANGE);
			fuelRangeObj.put(FuelRange.KEY_CAPACITY, VehicleDataHelper.FUEL_RANGE_CAPACITY);
			fuelRangeObj.put(FuelRange.KEY_CAPACITY_UNIT, VehicleDataHelper.FUEL_RANGE_CAPACITY_UNIT);
			fuelRangeObj.put(FuelRange.KEY_LEVEL, VehicleDataHelper.FUEL_RANGE_LEVEL);
			fuelRangeObj.put(FuelRange.KEY_LEVEL_STATE, VehicleDataHelper.FUEL_RANGE_LEVEL_STATE);
			fuelRangeArrayObj.put(fuelRangeObj);

			//GEAR_STATUS
			gearStatusObj.put(GearStatus.KEY_USER_SELECTED_GEAR, VehicleDataHelper.USER_SELECTED_GEAR);
			gearStatusObj.put(GearStatus.KEY_TRANSMISSION_TYPE, VehicleDataHelper.TRANSMISSION_TYPE);
			gearStatusObj.put(GearStatus.KEY_ACTUAL_GEAR, VehicleDataHelper.ACTUAL_GEAR);

			// WINDOW_STATUS
			windowStatusObj.put(WindowStatus.KEY_LOCATION, VehicleDataHelper.LOCATION_GRID);
			windowStatusObj.put(WindowStatus.KEY_STATE, VehicleDataHelper.WINDOW_STATE);
			windowStatusArrayObj.put(windowStatusObj);

			reference.put(GetVehicleDataResponse.KEY_SPEED, VehicleDataHelper.SPEED);
			reference.put(GetVehicleDataResponse.KEY_RPM, VehicleDataHelper.RPM);
			reference.put(GetVehicleDataResponse.KEY_EXTERNAL_TEMPERATURE, VehicleDataHelper.EXTERNAL_TEMPERATURE);
			reference.put(GetVehicleDataResponse.KEY_FUEL_LEVEL, VehicleDataHelper.FUEL_LEVEL);
			reference.put(GetVehicleDataResponse.KEY_VIN, VehicleDataHelper.VIN);
			reference.put(GetVehicleDataResponse.KEY_PRNDL, VehicleDataHelper.PRNDL_FINAL);
			reference.put(GetVehicleDataResponse.KEY_TIRE_PRESSURE, tireStatusObj);
			reference.put(GetVehicleDataResponse.KEY_ENGINE_TORQUE, VehicleDataHelper.ENGINE_TORQUE);
			reference.put(GetVehicleDataResponse.KEY_ENGINE_OIL_LIFE, VehicleDataHelper.ENGINE_OIL_LIFE);
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
			reference.put(GetVehicleDataResponse.KEY_FUEL_RANGE, fuelRangeArrayObj);
			reference.put(GetVehicleDataResponse.KEY_TURN_SIGNAL, TurnSignal.OFF);
			reference.put(GetVehicleDataResponse.KEY_GEAR_STATUS, gearStatusObj);
			reference.put(GetVehicleDataResponse.KEY_ELECTRONIC_PARK_BRAKE_STATUS, VehicleDataHelper.ELECTRONIC_PARK_BRAKE_STATUS);
			reference.put(GetVehicleDataResponse.KEY_WINDOW_STATUS, windowStatusArrayObj);
			reference.put(GetVehicleDataResponse.KEY_HANDS_OFF_STEERING, VehicleDataHelper.HANDS_OFF_STEERING);
			reference.put(GetVehicleDataResponse.KEY_STABILITY_CONTROLS_STATUS, stabilityControlsStatusObj);
			reference.put(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME, VehicleDataHelper.OEM_CUSTOM_VEHICLE_DATA_STATE);

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
				else if (key.equals(GetVehicleDataResponse.KEY_ENGINE_OIL_LIFE)) {
					assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
							JsonUtils.readDoubleFromJsonObject(reference, key), JsonUtils.readDoubleFromJsonObject(underTest, key));
				}
				else if (key.equals(GetVehicleDataResponse.KEY_GEAR_STATUS)) {
					JSONObject myKeyObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject myKeyObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);

					assertTrue(TestValues.TRUE, Validator.validateGearStatuses(
							new GearStatus(JsonRPCMarshaller.deserializeJSONObject(myKeyObjReference)),
							new GearStatus(JsonRPCMarshaller.deserializeJSONObject(myKeyObjTest))));
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
				else if (key.equals(GetVehicleDataResponse.KEY_FUEL_RANGE)) {
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

					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateFuelRange(
									fuelRangeRefereceList,
									fuelRangeUnderTestList));
				}
				else if (key.equals(GetVehicleDataResponse.KEY_WINDOW_STATUS)) {
					JSONArray windowStatusArrayObjReference = JsonUtils.readJsonArrayFromJsonObject(reference, key);
					List<WindowStatus> windowStatusReferenceList = new ArrayList<>();
					for (int index = 0; index < windowStatusArrayObjReference.length(); index++) {
						WindowStatus windowStatus = new WindowStatus(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)windowStatusArrayObjReference.get(index) ));
						windowStatusReferenceList.add(windowStatus);
					}

					JSONArray windowStatusArrayObjTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					List<WindowStatus> windowStatusUnderTestList = new ArrayList<>();
					for (int index = 0; index < windowStatusArrayObjTest.length(); index++) {
						WindowStatus windowStatus = new WindowStatus(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)windowStatusArrayObjTest.get(index) ));
						windowStatusUnderTestList.add(windowStatus);
					}

					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateWindowStatuses(
									windowStatusReferenceList,
									windowStatusUnderTestList));
				}
				else {
					assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
							JsonUtils.readObjectFromJsonObject(reference, key),
							JsonUtils.readObjectFromJsonObject(underTest, key));
	            }
				
			}
			
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    @Test
	public void testRpcValues(){
		// Valid Tests
		assertEquals(TestValues.MATCH, VehicleDataHelper.SPEED, ( (GetVehicleDataResponse) msg ).getSpeed());
		assertEquals(TestValues.MATCH, (Integer) VehicleDataHelper.RPM, ( (GetVehicleDataResponse) msg ).getRpm());
		assertEquals(TestValues.MATCH, VehicleDataHelper.EXTERNAL_TEMPERATURE, ( (GetVehicleDataResponse) msg ).getExternalTemperature());
		assertEquals(TestValues.MATCH, VehicleDataHelper.FUEL_LEVEL, ( (GetVehicleDataResponse) msg ).getFuelLevel());
		assertEquals(TestValues.MATCH, VehicleDataHelper.PRNDL_FINAL, ( (GetVehicleDataResponse) msg ).getPrndl());
		assertEquals(TestValues.MATCH, VehicleDataHelper.TIRE_PRESSURE, ( (GetVehicleDataResponse) msg ).getTirePressure());
		assertEquals(TestValues.MATCH, VehicleDataHelper.ENGINE_TORQUE, ( (GetVehicleDataResponse) msg ).getEngineTorque());
		assertEquals(TestValues.MATCH, VehicleDataHelper.ENGINE_OIL_LIFE, ( (GetVehicleDataResponse) msg ).getEngineOilLife());
		assertEquals(TestValues.MATCH, (Integer) VehicleDataHelper.ODOMETER, ( (GetVehicleDataResponse) msg ).getOdometer());
		assertEquals(TestValues.MATCH, VehicleDataHelper.GPS, ( (GetVehicleDataResponse) msg ).getGps());
		assertEquals(TestValues.MATCH, VehicleDataHelper.FUEL_LEVEL_STATE, ( (GetVehicleDataResponse) msg ).getFuelLevelState());
		assertEquals(TestValues.MATCH, VehicleDataHelper.INSTANT_FUEL_CONSUMPTION, ( (GetVehicleDataResponse) msg ).getInstantFuelConsumption());
		assertEquals(TestValues.MATCH, VehicleDataHelper.BELT_STATUS, ( (GetVehicleDataResponse) msg ).getBeltStatus());
		assertEquals(TestValues.MATCH, VehicleDataHelper.BODY_INFORMATION, ( (GetVehicleDataResponse) msg ).getBodyInformation());
		assertEquals(TestValues.MATCH, VehicleDataHelper.DEVICE_STATUS, ( (GetVehicleDataResponse) msg ).getDeviceStatus());
		assertEquals(TestValues.MATCH, VehicleDataHelper.DRIVER_BRAKING, ( (GetVehicleDataResponse) msg ).getDriverBraking());
		assertEquals(TestValues.MATCH, VehicleDataHelper.WIPER_STATUS, ( (GetVehicleDataResponse) msg ).getWiperStatus());
		assertEquals(TestValues.MATCH, VehicleDataHelper.HEAD_LAMP_STATUS, ( (GetVehicleDataResponse) msg ).getHeadLampStatus());
		assertEquals(TestValues.MATCH, VehicleDataHelper.ACC_PEDAL_POSITION, ( (GetVehicleDataResponse) msg ).getAccPedalPosition());
		assertEquals(TestValues.MATCH, VehicleDataHelper.STEERING_WHEEL_ANGLE, ( (GetVehicleDataResponse) msg ).getSteeringWheelAngle());
		assertEquals(TestValues.MATCH, VehicleDataHelper.E_CALL_INFO, ( (GetVehicleDataResponse) msg ).getECallInfo());
		assertEquals(TestValues.MATCH, VehicleDataHelper.AIRBAG_STATUS, ( (GetVehicleDataResponse) msg ).getAirbagStatus());
		assertEquals(TestValues.MATCH, VehicleDataHelper.EMERGENCY_EVENT, ( (GetVehicleDataResponse) msg ).getEmergencyEvent());
		assertEquals(TestValues.MATCH, VehicleDataHelper.CLUSTER_MODE_STATUS, ( (GetVehicleDataResponse) msg ).getClusterModeStatus());
		assertEquals(TestValues.MATCH, VehicleDataHelper.MY_KEY, ( (GetVehicleDataResponse) msg ).getMyKey());
		assertEquals(TestValues.MATCH, VehicleDataHelper.TURN_SIGNAL, ( (GetVehicleDataResponse) msg ).getTurnSignal());
		assertEquals(TestValues.MATCH, VehicleDataHelper.ELECTRONIC_PARK_BRAKE_STATUS, ( (GetVehicleDataResponse) msg ).getElectronicParkBrakeStatus());
		assertEquals(TestValues.MATCH, VehicleDataHelper.WINDOW_STATUS_LIST, ( (GetVehicleDataResponse) msg ).getWindowStatus());
		assertEquals(TestValues.MATCH, VehicleDataHelper.GEAR_STATUS, ( (GetVehicleDataResponse) msg ).getGearStatus());
		assertEquals(TestValues.MATCH, VehicleDataHelper.HANDS_OFF_STEERING, ( (GetVehicleDataResponse) msg ).getHandsOffSteering());
		assertEquals(TestValues.MATCH, VehicleDataHelper.STABILITY_CONTROLS_STATUS, ( (GetVehicleDataResponse) msg ).getStabilityControlsStatus());
		assertEquals(TestValues.MATCH, VehicleDataHelper.OEM_CUSTOM_VEHICLE_DATA_STATE, ( (GetVehicleDataResponse) msg ).getOEMCustomVehicleData(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME));
		
		// Invalid/Null Tests
		GetVehicleDataResponse msg = new GetVehicleDataResponse();
		assertNotNull(TestValues.NOT_NULL, msg);
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
        assertNull(TestValues.NULL, msg.getTurnSignal());
        assertNull(TestValues.NULL, msg.getElectronicParkBrakeStatus());
        assertNull(TestValues.NULL, msg.getGearStatus());
		assertNull(TestValues.NULL, msg.getHandsOffSteering());
        assertNull(TestValues.NULL, msg.getStabilityControlsStatus());
        assertNull(TestValues.NULL, msg.getWindowStatus());
        assertNull(TestValues.NULL, msg.getOEMCustomVehicleData(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME));
    }
    

	/**
     * Tests a valid JSON construction of this RPC message.
     */
	@Test
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getInstrumentation().getTargetContext(), getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			GetVehicleDataResponse cmd = new GetVehicleDataResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_SPEED), cmd.getSpeed());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetVehicleDataResponse.KEY_RPM), cmd.getRpm());
			assertEquals(TestValues.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_EXTERNAL_TEMPERATURE), cmd.getExternalTemperature());
			assertEquals(TestValues.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_FUEL_LEVEL), cmd.getFuelLevel());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetVehicleDataResponse.KEY_VIN), cmd.getVin());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetVehicleDataResponse.KEY_PRNDL), cmd.getPrndl().toString());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetVehicleDataResponse.KEY_HANDS_OFF_STEERING), cmd.getHandsOffSteering());
			JSONObject tireStatusObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_TIRE_PRESSURE);
			TireStatus tireStatus = new TireStatus(JsonRPCMarshaller.deserializeJSONObject(tireStatusObj));
			assertTrue(TestValues.TRUE, Validator.validateTireStatus(tireStatus, cmd.getTirePressure()) );

			assertEquals(TestValues.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_ENGINE_TORQUE), cmd.getEngineTorque());
			assertEquals(TestValues.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_ENGINE_OIL_LIFE), cmd.getEngineOilLife(), 0.0002);
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetVehicleDataResponse.KEY_ODOMETER), cmd.getOdometer());
			
			JSONObject gpsDataObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_GPS);
			GPSData gpsData = new GPSData(JsonRPCMarshaller.deserializeJSONObject(gpsDataObj));
			assertTrue(TestValues.TRUE, Validator.validateGpsData(gpsData, cmd.getGps()) );
			
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetVehicleDataResponse.KEY_FUEL_LEVEL_STATE), cmd.getFuelLevelState().toString());
			assertEquals(TestValues.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_INSTANT_FUEL_CONSUMPTION), cmd.getInstantFuelConsumption());
			
			JSONObject beltStatusObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_BELT_STATUS);
			BeltStatus beltStatus = new BeltStatus(JsonRPCMarshaller.deserializeJSONObject(beltStatusObj));
			assertTrue(TestValues.TRUE, Validator.validateBeltStatus(beltStatus, cmd.getBeltStatus()) );
			
			JSONObject bodyInformationObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_BODY_INFORMATION);
			BodyInformation bodyInformation = new BodyInformation(JsonRPCMarshaller.deserializeJSONObject(bodyInformationObj));
			assertTrue(TestValues.TRUE, Validator.validateBodyInformation(bodyInformation, cmd.getBodyInformation()) );
			
			JSONObject deviceStatusObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_DEVICE_STATUS);
			DeviceStatus deviceStatus = new DeviceStatus(JsonRPCMarshaller.deserializeJSONObject(deviceStatusObj));
			assertTrue(TestValues.TRUE, Validator.validateDeviceStatus(deviceStatus, cmd.getDeviceStatus()) );
			
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetVehicleDataResponse.KEY_DRIVER_BRAKING), cmd.getDriverBraking().toString());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetVehicleDataResponse.KEY_WIPER_STATUS), cmd.getWiperStatus().toString());
			
			JSONObject headLampStatusObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_HEAD_LAMP_STATUS);
			HeadLampStatus headLampStatus = new HeadLampStatus(JsonRPCMarshaller.deserializeJSONObject(headLampStatusObj));
			assertTrue(TestValues.TRUE, Validator.validateHeadLampStatus(headLampStatus, cmd.getHeadLampStatus()) );
			
			assertEquals(TestValues.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_ACC_PEDAL_POSITION), cmd.getAccPedalPosition());
			assertEquals(TestValues.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, GetVehicleDataResponse.KEY_STEERING_WHEEL_ANGLE), cmd.getSteeringWheelAngle());
			
			JSONObject eCallInfoObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_E_CALL_INFO);
			ECallInfo eCallInfo = new ECallInfo(JsonRPCMarshaller.deserializeJSONObject(eCallInfoObj));
			assertTrue(TestValues.TRUE, Validator.validateECallInfo(eCallInfo, cmd.getECallInfo()) );
			
			JSONObject airbagStatusObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_AIRBAG_STATUS);
			AirbagStatus airbagStatus = new AirbagStatus(JsonRPCMarshaller.deserializeJSONObject(airbagStatusObj));
			assertTrue(TestValues.TRUE, Validator.validateAirbagStatus(airbagStatus, cmd.getAirbagStatus()) );
			
			JSONObject emergencyEventObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_EMERGENCY_EVENT);
			EmergencyEvent emergencyEvent = new EmergencyEvent(JsonRPCMarshaller.deserializeJSONObject(emergencyEventObj));
			assertTrue(TestValues.TRUE, Validator.validateEmergencyEvent(emergencyEvent, cmd.getEmergencyEvent()) );
			
			JSONObject clusterModeStatusObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_CLUSTER_MODE_STATUS);
			ClusterModeStatus clusterModeStatus = new ClusterModeStatus(JsonRPCMarshaller.deserializeJSONObject(clusterModeStatusObj));
			assertTrue(TestValues.TRUE, Validator.validateClusterModeStatus(clusterModeStatus, cmd.getClusterModeStatus()) );

			JSONObject gearStatusObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_GEAR_STATUS);
			GearStatus gearStatus = new GearStatus(JsonRPCMarshaller.deserializeJSONObject(gearStatusObj));
			GearStatus cmdStatus = cmd.getGearStatus();
			assertTrue(TestValues.TRUE, Validator.validateGearStatuses(gearStatus, cmdStatus));

			JSONObject myKeyObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_MY_KEY);
			MyKey myKey = new MyKey(JsonRPCMarshaller.deserializeJSONObject(myKeyObj));
			assertTrue(TestValues.TRUE, Validator.validateMyKey(myKey, cmd.getMyKey()) );

			JSONArray windowStatusArray = JsonUtils.readJsonArrayFromJsonObject(parameters, GetVehicleDataResponse.KEY_WINDOW_STATUS);

			List<WindowStatus> windowStatus = new ArrayList<>();
			for (int index = 0; index < windowStatusArray.length(); index++) {
				WindowStatus status = new WindowStatus(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)windowStatusArray.get(index)) );
				windowStatus.add(status);
			}
			assertTrue(TestValues.TRUE, Validator.validateWindowStatuses(windowStatus, cmd.getWindowStatus()) );

			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetVehicleDataResponse.KEY_TURN_SIGNAL), cmd.getTurnSignal().toString());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetVehicleDataResponse.KEY_ELECTRONIC_PARK_BRAKE_STATUS), cmd.getElectronicParkBrakeStatus().toString());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME), cmd.getOEMCustomVehicleData(TestValues.GENERAL_OEM_CUSTOM_VEHICLE_DATA_NAME));

			JSONObject stabilityControlStatusObj = JsonUtils.readJsonObjectFromJsonObject(parameters, GetVehicleDataResponse.KEY_STABILITY_CONTROLS_STATUS);
			StabilityControlsStatus stabilityControlsStatus = new StabilityControlsStatus(JsonRPCMarshaller.deserializeJSONObject(stabilityControlStatusObj));
			assertTrue(TestValues.TRUE, Validator.validateStabilityControlStatus(stabilityControlsStatus, cmd.getStabilityControlsStatus()));
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }    
}
