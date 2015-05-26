package com.smartdevicelink.test;

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
import com.smartdevicelink.proxy.rpc.OnVehicleData;
import com.smartdevicelink.proxy.rpc.SingleTireStatus;
import com.smartdevicelink.proxy.rpc.TireStatus;
import com.smartdevicelink.proxy.rpc.enums.AmbientLightStatus;
import com.smartdevicelink.proxy.rpc.enums.CarModeStatus;
import com.smartdevicelink.proxy.rpc.enums.CompassDirection;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.DeviceLevelStatus;
import com.smartdevicelink.proxy.rpc.enums.Dimension;
import com.smartdevicelink.proxy.rpc.enums.ECallConfirmationStatus;
import com.smartdevicelink.proxy.rpc.enums.EmergencyEventType;
import com.smartdevicelink.proxy.rpc.enums.FuelCutoffStatus;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.PowerModeQualificationStatus;
import com.smartdevicelink.proxy.rpc.enums.PowerModeStatus;
import com.smartdevicelink.proxy.rpc.enums.PrimaryAudioSource;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataNotificationStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;
import com.smartdevicelink.proxy.rpc.enums.WarningLightStatus;
import com.smartdevicelink.proxy.rpc.enums.WiperStatus;

public class VehicleDataHelper{
	//top level variables for OnVehicleData
    public static final double SPEED = 35.6;
	public static final int RPM = 2500;
	public static final double EXTERNAL_TEMPERATURE = 140.1;
	public static final double FUEL_LEVEL = 3.7;
	public static final String VIN = "FIUE4WHR3984579THIRU";
	public static final PRNDL PRNDL_FINAL = PRNDL.SECOND;
	public static final TireStatus TIRE_PRESSURE = new TireStatus();
	public static final double ENGINE_TORQUE = 518.3;
	public static final int ODOMETER = 140000;
	public static final GPSData GPS = new GPSData();
	public static final ComponentVolumeStatus FUEL_LEVEL_STATE = ComponentVolumeStatus.ALERT;
	public static final double INSTANT_FUEL_CONSUMPTION = 2.76;
	public static final BeltStatus BELT_STATUS = new BeltStatus();
	public static final BodyInformation BODY_INFORMATION = new BodyInformation();
	public static final DeviceStatus DEVICE_STATUS = new DeviceStatus();
	public static final VehicleDataEventStatus DRIVER_BRAKING = VehicleDataEventStatus.NO;
	public static final WiperStatus WIPER_STATUS = WiperStatus.COURTESYWIPE;
	public static final HeadLampStatus HEAD_LAMP_STATUS = new HeadLampStatus();
	public static final double ACC_PEDAL_POSITION = 28.29;
	public static final double STEERING_WHEEL_ANGLE = 70.5;
	public static final ECallInfo E_CALL_INFO = new ECallInfo();
	public static final AirbagStatus AIRBAG_STATUS = new AirbagStatus();
	public static final EmergencyEvent EMERGENCY_EVENT = new EmergencyEvent();
	public static final ClusterModeStatus CLUSTER_MODE_STATUS = new ClusterModeStatus();
	public static final MyKey MY_KEY = new MyKey();
	
	//other variables inside some of the above objects
    // tire status
	public static final WarningLightStatus 	  TIRE_PRESSURE_TELL_TALE = WarningLightStatus.ON;
	public static final ComponentVolumeStatus TIRE_PRESSURE_LEFT_FRONT = ComponentVolumeStatus.NORMAL;
	public static final ComponentVolumeStatus TIRE_PRESSURE_RIGHT_FRONT = ComponentVolumeStatus.FAULT;
	public static final ComponentVolumeStatus TIRE_PRESSURE_LEFT_REAR = ComponentVolumeStatus.LOW;
	public static final ComponentVolumeStatus TIRE_PRESSURE_RIGHT_REAR = ComponentVolumeStatus.NORMAL;
	public static final ComponentVolumeStatus TIRE_PRESSURE_INNER_LEFT_REAR = ComponentVolumeStatus.LOW;
	public static final ComponentVolumeStatus TIRE_PRESSURE_INNER_RIGHT_REAR = ComponentVolumeStatus.ALERT;
	
    // GPS data
	public static final Double GPS_LONGITUDE = 104.2;
	public static final Double GPS_LATITUDE = 56.3;
	public static final Integer GPS_YEAR = 2015;
	public static final Integer GPS_MONTH = 7;
	public static final Integer GPS_DAY = 14;
	public static final Integer GPS_HOURS = 11;
	public static final Integer GPS_MINUTES = 38;
	public static final Integer GPS_SECONDS = 12;
	public static final CompassDirection GPS_DIRECTION = CompassDirection.NORTHWEST;
	public static final Double GPS_PDOP = 4.1;
	public static final Double GPS_HDOP = 2.4;
	public static final Double GPS_VDOP = 5.5;
	public static final Boolean GPS_ACTUAL = true;
	public static final Integer GPS_SATELLITES = 3;
	public static final Dimension GPS_DIMENSION = Dimension._2D;
	public static final Double GPS_ALTITUDE = 768.5;
	public static final Double GPS_HEADING = 315.0;
	public static final Double GPS_SPEED = 30.5;
	
    // belt status
	public static final VehicleDataEventStatus BELT_STATUS_DRIVER_DEPLOYED = VehicleDataEventStatus.NO;
	public static final VehicleDataEventStatus BELT_STATUS_PASSENGER_DEPLOYED = VehicleDataEventStatus.YES;
	public static final VehicleDataEventStatus BELT_STATUS_PASSENGER_BELTED = VehicleDataEventStatus.NO;
	public static final VehicleDataEventStatus BELT_STATUS_DRIVER_BELTED = VehicleDataEventStatus.NO;
	public static final VehicleDataEventStatus BELT_STATUS_LEFT_ROW_2_BELTED = VehicleDataEventStatus.NO_EVENT;
	public static final VehicleDataEventStatus BELT_STATUS_PASSENGER_CHILD = VehicleDataEventStatus.NO;
	public static final VehicleDataEventStatus BELT_STATUS_RIGHT_ROW_2_BELTED = VehicleDataEventStatus.NO_EVENT;
	public static final VehicleDataEventStatus BELT_STATUS_MIDDLE_ROW_2_BELTED = VehicleDataEventStatus.YES;
	public static final VehicleDataEventStatus BELT_STATUS_MIDDLE_ROW_3_BELTED = VehicleDataEventStatus.NO_EVENT;
	public static final VehicleDataEventStatus BELT_STATUS_LEFT_ROW_3_BELTED = VehicleDataEventStatus.NO;
	public static final VehicleDataEventStatus BELT_STATUS_RIGHT_ROW_3_BELTED = VehicleDataEventStatus.NO_EVENT;
	public static final VehicleDataEventStatus BELT_STATUS_LEFT_REAR_INFLATABLE_BELTED = VehicleDataEventStatus.NO;
	public static final VehicleDataEventStatus BELT_STATUS_RIGHT_REAR_INFLATABLE_BELTED = VehicleDataEventStatus.NO_EVENT;
	public static final VehicleDataEventStatus BELT_STATUS_MIDDLE_ROW_1_DEPLOYED = VehicleDataEventStatus.NO;
	public static final VehicleDataEventStatus BELT_STATUS_MIDDLE_ROW_1_BELTED = VehicleDataEventStatus.YES;
	
    // body information
	public static final Boolean BODY_INFORMATION_PARK_BRAKE = false;
	public static final IgnitionStableStatus BODY_INFORMATION_IGNITION_STATUS = IgnitionStableStatus.IGNITION_SWITCH_NOT_STABLE;
	public static final IgnitionStatus BODY_INFORMATION_IGNITION_STABLE_STATUS = IgnitionStatus.ACCESSORY;
	public static final Boolean BODY_INFORMATION_DRIVER_AJAR = true;
	public static final Boolean BODY_INFORMATION_PASSENGER_AJAR = true;
	public static final Boolean BODY_INFORMATION_REAR_LEFT_AJAR = false;
	public static final Boolean BODY_INFORMATION_REAR_RIGHT_AJAR = true;

    // device status
	public static final Boolean DEVICE_STATUS_VOICE_REC = true;
	public static final Boolean DEVICE_STATUS_BT_ICON = true;
	public static final Boolean DEVICE_STATUS_CALL_ACTIVE = true;
	public static final Boolean DEVICE_STATUS_PHONE_ROAMING = false;
	public static final Boolean DEVICE_STATUS_TEXT_MSG_AVAILABLE = false;
	public static final DeviceLevelStatus DEVICE_STATUS_BATT_LEVEL_STATUS = DeviceLevelStatus.TWO_LEVEL_BARS;
	public static final Boolean DEVICE_STATUS_STEREO_MUTED = false;
	public static final Boolean DEVICE_STATUS_MONO_MUTED = true;
	public static final DeviceLevelStatus DEVICE_STATUS_SIGNAL_LEVEL_STATUS = DeviceLevelStatus.ONE_LEVEL_BARS;
	public static final PrimaryAudioSource DEVICE_STATUS_PRIMARY_AUDIO = PrimaryAudioSource.BLUETOOTH_STEREO_BTST;
	public static final Boolean DEVICE_STATUS_E_CALL_ACTIVE = false;
	
	// head lamp status
	public static final AmbientLightStatus HEAD_LAMP_STATUS_AMBIENT_STATUS = AmbientLightStatus.TWILIGHT_3;
	public static final Boolean HEAD_LAMP_HIGH_BEAMS = true;
	public static final Boolean HEAD_LAMP_LOW_BEAMS = false;
	
	// e call info
	public static final VehicleDataNotificationStatus E_CALL_INFO_E_CALL_NOTIFICATION_STATUS = VehicleDataNotificationStatus.NOT_SUPPORTED;
	public static final VehicleDataNotificationStatus E_CALL_INFO_AUX_E_CALL_NOTIFICATION_STATUS = VehicleDataNotificationStatus.NOT_USED;
	public static final ECallConfirmationStatus E_CALL_INFO_CONFIRMATION_STATUS = ECallConfirmationStatus.ECALL_CONFIGURED_OFF;
	
	// airbag status
	public static final VehicleDataEventStatus AIRBAG_STATUS_DRIVER_DEPLOYED = VehicleDataEventStatus.NO;
	public static final VehicleDataEventStatus AIRBAG_STATUS_DRIVER_SIDE_DEPLOYED = VehicleDataEventStatus.NO;
	public static final VehicleDataEventStatus AIRBAG_STATUS_DRIVER_CURTAIN_DEPLOYED = VehicleDataEventStatus.YES;
	public static final VehicleDataEventStatus AIRBAG_STATUS_PASSENGER_DEPLOYED = VehicleDataEventStatus.NO;
	public static final VehicleDataEventStatus AIRBAG_STATUS_PASSENGER_CURTAIN_DEPLOYED = VehicleDataEventStatus.NO;
	public static final VehicleDataEventStatus AIRBAG_STATUS_DRIVER_KNEE_DEPLOYED = VehicleDataEventStatus.NO;
	public static final VehicleDataEventStatus AIRBAG_STATUS_PASSENGER_SIDE_DEPLOYED = VehicleDataEventStatus.YES;
	public static final VehicleDataEventStatus AIRBAG_STATUS_PASSENGER_KNEE_DEPLOYED = VehicleDataEventStatus.NO;
	
	// emergency event
	public static final EmergencyEventType EMERGENCY_EVENT_TYPE = EmergencyEventType.FRONTAL;
	public static final FuelCutoffStatus EMERGENCY_EVENT_FUEL_CUTOFF_STATUS = FuelCutoffStatus.TERMINATE_FUEL;
	public static final VehicleDataEventStatus EMERGENCY_EVENT_ROLLOVER_EVENT = VehicleDataEventStatus.YES;
	public static final Integer EMERGENCY_EVENT_MAX_CHANGE_VELOCITY = 5;
	public static final VehicleDataEventStatus EMERGENCY_EVENT_MULTIPLE_EVENTS = VehicleDataEventStatus.NO;
	
	// cluster mode status
	public static final Boolean CLUSTER_MODE_STATUS_POWER_MODE_ACTIVE = true;
	public static final PowerModeQualificationStatus CLUSTER_MODE_STATUS_POWER_MODE_QUALIFICATION_STATUS = 
			PowerModeQualificationStatus.POWER_MODE_EVALUATION_IN_PROGRESS;
	public static final CarModeStatus CLUSTER_MODE_STATUS_CAR_MODE_STATUS = CarModeStatus.TRANSPORT;
	public static final PowerModeStatus CLUSTER_MODE_STATUS_POWER_MODE_STATUS = PowerModeStatus.POST_ACCESORY_0;
	
	// my key
	public static final VehicleDataStatus MY_KEY_E_911_OVERRIDE = VehicleDataStatus.NO_DATA_EXISTS;
	
	//the OnVehicleData which stores all the information above
	public static final OnVehicleData VEHICLE_DATA = new OnVehicleData();
	//GetVehicleDataResponse data which stores the same things
	public static final GetVehicleDataResponse VEHICLE_DATA_RESPONSE = new GetVehicleDataResponse();
	
	static {
    	//TIRE_PRESSURE set up    	
    	TIRE_PRESSURE.setPressureTellTale(TIRE_PRESSURE_TELL_TALE);
    	SingleTireStatus tireLeftFront = new SingleTireStatus();
    	tireLeftFront.setStatus(TIRE_PRESSURE_LEFT_FRONT);
    	TIRE_PRESSURE.setLeftFront(tireLeftFront);
    	SingleTireStatus tireRightFront = new SingleTireStatus();
    	tireRightFront.setStatus(TIRE_PRESSURE_RIGHT_FRONT);
    	TIRE_PRESSURE.setRightFront(tireRightFront);
    	SingleTireStatus tireLeftRear = new SingleTireStatus();
    	tireLeftRear.setStatus(TIRE_PRESSURE_LEFT_REAR);
    	TIRE_PRESSURE.setLeftRear(tireLeftRear);
    	SingleTireStatus tireRightRear = new SingleTireStatus();
    	tireRightRear.setStatus(TIRE_PRESSURE_RIGHT_REAR);
    	TIRE_PRESSURE.setRightRear(tireRightRear);
    	SingleTireStatus tireInnerLeftRear = new SingleTireStatus();
    	tireInnerLeftRear.setStatus(TIRE_PRESSURE_INNER_LEFT_REAR);
    	TIRE_PRESSURE.setInnerLeftRear(tireInnerLeftRear);
    	SingleTireStatus tireInnerRightRear = new SingleTireStatus();
    	tireInnerRightRear.setStatus(TIRE_PRESSURE_INNER_RIGHT_REAR);
    	TIRE_PRESSURE.setInnerRightRear(tireInnerRightRear);
    	
    	//GPS set up
    	GPS.setLongitudeDegrees(GPS_LONGITUDE);
    	GPS.setLatitudeDegrees(GPS_LATITUDE);
    	GPS.setUtcYear(GPS_YEAR);
    	GPS.setUtcMonth(GPS_MONTH);
    	GPS.setUtcDay(GPS_DAY);
    	GPS.setUtcHours(GPS_HOURS);
    	GPS.setUtcMinutes(GPS_MINUTES);
    	GPS.setUtcSeconds(GPS_SECONDS);
    	GPS.setCompassDirection(GPS_DIRECTION);
    	GPS.setPdop(GPS_PDOP);
    	GPS.setHdop(GPS_HDOP);
    	GPS.setVdop(GPS_VDOP);
    	GPS.setActual(GPS_ACTUAL);
    	GPS.setSatellites(GPS_SATELLITES);
    	GPS.setDimension(GPS_DIMENSION);
    	GPS.setAltitude(GPS_ALTITUDE);
    	GPS.setHeading(GPS_HEADING);
    	GPS.setSpeed(GPS_SPEED);

    	//BELT_STATUS set up
    	BELT_STATUS.setDriverBeltDeployed(BELT_STATUS_DRIVER_DEPLOYED);
    	BELT_STATUS.setPassengerBeltDeployed(BELT_STATUS_PASSENGER_DEPLOYED);
    	BELT_STATUS.setPassengerBuckleBelted(BELT_STATUS_PASSENGER_BELTED);
    	BELT_STATUS.setDriverBuckleBelted(BELT_STATUS_DRIVER_BELTED);
    	BELT_STATUS.setLeftRow2BuckleBelted(BELT_STATUS_LEFT_ROW_2_BELTED);
    	BELT_STATUS.setPassengerChildDetected(BELT_STATUS_PASSENGER_CHILD);
    	BELT_STATUS.setRightRow2BuckleBelted(BELT_STATUS_RIGHT_ROW_2_BELTED);
    	BELT_STATUS.setMiddleRow2BuckleBelted(BELT_STATUS_MIDDLE_ROW_2_BELTED);
    	BELT_STATUS.setMiddleRow3BuckleBelted(BELT_STATUS_MIDDLE_ROW_3_BELTED);
    	BELT_STATUS.setLeftRow3BuckleBelted(BELT_STATUS_LEFT_ROW_3_BELTED);
    	BELT_STATUS.setRightRow3BuckleBelted(BELT_STATUS_RIGHT_ROW_3_BELTED);
    	BELT_STATUS.setLeftRearInflatableBelted(BELT_STATUS_LEFT_REAR_INFLATABLE_BELTED);
    	BELT_STATUS.setRightRearInflatableBelted(BELT_STATUS_RIGHT_REAR_INFLATABLE_BELTED);
    	BELT_STATUS.setMiddleRow1BeltDeployed(BELT_STATUS_MIDDLE_ROW_1_DEPLOYED);
    	BELT_STATUS.setMiddleRow1BuckleBelted(BELT_STATUS_MIDDLE_ROW_1_BELTED);
    	
    	//BODY_INFORMATION set up
    	BODY_INFORMATION.setParkBrakeActive(BODY_INFORMATION_PARK_BRAKE);
    	BODY_INFORMATION.setIgnitionStableStatus(BODY_INFORMATION_IGNITION_STATUS);
    	BODY_INFORMATION.setIgnitionStatus(BODY_INFORMATION_IGNITION_STABLE_STATUS);
    	BODY_INFORMATION.setDriverDoorAjar(BODY_INFORMATION_DRIVER_AJAR);
    	BODY_INFORMATION.setPassengerDoorAjar(BODY_INFORMATION_PASSENGER_AJAR);
    	BODY_INFORMATION.setRearLeftDoorAjar(BODY_INFORMATION_REAR_LEFT_AJAR);
    	BODY_INFORMATION.setRearRightDoorAjar(BODY_INFORMATION_REAR_RIGHT_AJAR);
    	
    	//DEVICE_STATUS set up
    	DEVICE_STATUS.setVoiceRecOn(DEVICE_STATUS_VOICE_REC);
    	DEVICE_STATUS.setBtIconOn(DEVICE_STATUS_BT_ICON);
    	DEVICE_STATUS.setCallActive(DEVICE_STATUS_CALL_ACTIVE);
    	DEVICE_STATUS.setPhoneRoaming(DEVICE_STATUS_PHONE_ROAMING);
    	DEVICE_STATUS.setTextMsgAvailable(DEVICE_STATUS_TEXT_MSG_AVAILABLE);
    	DEVICE_STATUS.setBattLevelStatus(DEVICE_STATUS_BATT_LEVEL_STATUS);
    	DEVICE_STATUS.setStereoAudioOutputMuted(DEVICE_STATUS_STEREO_MUTED);
    	DEVICE_STATUS.setMonoAudioOutputMuted(DEVICE_STATUS_MONO_MUTED);
    	DEVICE_STATUS.setSignalLevelStatus(DEVICE_STATUS_SIGNAL_LEVEL_STATUS);
    	DEVICE_STATUS.setPrimaryAudioSource(DEVICE_STATUS_PRIMARY_AUDIO);
    	DEVICE_STATUS.setECallEventActive(DEVICE_STATUS_E_CALL_ACTIVE);
    	
    	//HEAD_LAMP_STATUS set up
    	HEAD_LAMP_STATUS.setAmbientLightStatus(HEAD_LAMP_STATUS_AMBIENT_STATUS);
    	HEAD_LAMP_STATUS.setHighBeamsOn(HEAD_LAMP_HIGH_BEAMS);
    	HEAD_LAMP_STATUS.setLowBeamsOn(HEAD_LAMP_LOW_BEAMS);
    	
    	//E_CALL_INFO set up
    	E_CALL_INFO.setECallNotificationStatus(E_CALL_INFO_E_CALL_NOTIFICATION_STATUS);
    	E_CALL_INFO.setAuxECallNotificationStatus(E_CALL_INFO_AUX_E_CALL_NOTIFICATION_STATUS);
    	E_CALL_INFO.setECallConfirmationStatus(E_CALL_INFO_CONFIRMATION_STATUS);
    	
    	//AIRBAG_STATUS set up
    	AIRBAG_STATUS.setDriverAirbagDeployed(AIRBAG_STATUS_DRIVER_DEPLOYED);
    	AIRBAG_STATUS.setDriverSideAirbagDeployed(AIRBAG_STATUS_DRIVER_SIDE_DEPLOYED);
    	AIRBAG_STATUS.setDriverCurtainAirbagDeployed(AIRBAG_STATUS_DRIVER_CURTAIN_DEPLOYED);
    	AIRBAG_STATUS.setPassengerAirbagDeployed(AIRBAG_STATUS_PASSENGER_DEPLOYED);
    	AIRBAG_STATUS.setPassengerCurtainAirbagDeployed(AIRBAG_STATUS_PASSENGER_CURTAIN_DEPLOYED);
    	AIRBAG_STATUS.setDriverKneeAirbagDeployed(AIRBAG_STATUS_DRIVER_KNEE_DEPLOYED);
    	AIRBAG_STATUS.setPassengerSideAirbagDeployed(AIRBAG_STATUS_PASSENGER_SIDE_DEPLOYED);
    	AIRBAG_STATUS.setPassengerKneeAirbagDeployed(AIRBAG_STATUS_PASSENGER_KNEE_DEPLOYED);
    	
    	//EMERGENCY_EVENT set up
    	EMERGENCY_EVENT.setEmergencyEventType(EMERGENCY_EVENT_TYPE);
    	EMERGENCY_EVENT.setFuelCutoffStatus(EMERGENCY_EVENT_FUEL_CUTOFF_STATUS);
    	EMERGENCY_EVENT.setRolloverEvent(EMERGENCY_EVENT_ROLLOVER_EVENT);
    	EMERGENCY_EVENT.setMaximumChangeVelocity(EMERGENCY_EVENT_MAX_CHANGE_VELOCITY);
    	EMERGENCY_EVENT.setMultipleEvents(EMERGENCY_EVENT_MULTIPLE_EVENTS);
    	
    	//CLUSTER_MODE_STATUS set up
    	CLUSTER_MODE_STATUS.setPowerModeActive(CLUSTER_MODE_STATUS_POWER_MODE_ACTIVE);
    	CLUSTER_MODE_STATUS.setPowerModeQualificationStatus(CLUSTER_MODE_STATUS_POWER_MODE_QUALIFICATION_STATUS);
    	CLUSTER_MODE_STATUS.setCarModeStatus(CLUSTER_MODE_STATUS_CAR_MODE_STATUS);
    	CLUSTER_MODE_STATUS.setPowerModeStatus(CLUSTER_MODE_STATUS_POWER_MODE_STATUS);
    	
    	//MY_KEY set up
    	MY_KEY.setE911Override(MY_KEY_E_911_OVERRIDE);
	
    	
    	//set up the OnVehicleData object
		VEHICLE_DATA.setSpeed(SPEED);
		VEHICLE_DATA.setRpm(RPM);
		VEHICLE_DATA.setExternalTemperature(EXTERNAL_TEMPERATURE);
		VEHICLE_DATA.setFuelLevel(FUEL_LEVEL);
		VEHICLE_DATA.setVin(VIN);
		VEHICLE_DATA.setPrndl(PRNDL_FINAL);
		VEHICLE_DATA.setTirePressure(TIRE_PRESSURE);
		VEHICLE_DATA.setEngineTorque(ENGINE_TORQUE);
		VEHICLE_DATA.setOdometer(ODOMETER);
		VEHICLE_DATA.setGps(GPS);
		VEHICLE_DATA.setFuelLevelState(FUEL_LEVEL_STATE);
		VEHICLE_DATA.setInstantFuelConsumption(INSTANT_FUEL_CONSUMPTION);
		VEHICLE_DATA.setBeltStatus(BELT_STATUS);
		VEHICLE_DATA.setBodyInformation(BODY_INFORMATION);
		VEHICLE_DATA.setDeviceStatus(DEVICE_STATUS);
		VEHICLE_DATA.setDriverBraking(DRIVER_BRAKING);
		VEHICLE_DATA.setWiperStatus(WIPER_STATUS);
		VEHICLE_DATA.setHeadLampStatus(HEAD_LAMP_STATUS);
		VEHICLE_DATA.setAccPedalPosition(ACC_PEDAL_POSITION);
		VEHICLE_DATA.setSteeringWheelAngle(STEERING_WHEEL_ANGLE);
		VEHICLE_DATA.setECallInfo(E_CALL_INFO);
		VEHICLE_DATA.setAirbagStatus(AIRBAG_STATUS);
		VEHICLE_DATA.setEmergencyEvent(EMERGENCY_EVENT);
		VEHICLE_DATA.setClusterModeStatus(CLUSTER_MODE_STATUS);
		VEHICLE_DATA.setMyKey(MY_KEY);
		
		//set up the GetVehicleDataResponse object
		VEHICLE_DATA_RESPONSE.setSpeed(SPEED);
		VEHICLE_DATA_RESPONSE.setRpm(RPM);
		VEHICLE_DATA_RESPONSE.setExternalTemperature(EXTERNAL_TEMPERATURE);
		VEHICLE_DATA_RESPONSE.setFuelLevel(FUEL_LEVEL);
		VEHICLE_DATA_RESPONSE.setVin(VIN);
		VEHICLE_DATA_RESPONSE.setPrndl(PRNDL_FINAL);
		VEHICLE_DATA_RESPONSE.setTirePressure(TIRE_PRESSURE);
		VEHICLE_DATA_RESPONSE.setEngineTorque(ENGINE_TORQUE);
		VEHICLE_DATA_RESPONSE.setOdometer(ODOMETER);
		VEHICLE_DATA_RESPONSE.setGps(GPS);
		VEHICLE_DATA_RESPONSE.setFuelLevelState(FUEL_LEVEL_STATE);
		VEHICLE_DATA_RESPONSE.setInstantFuelConsumption(INSTANT_FUEL_CONSUMPTION);
		VEHICLE_DATA_RESPONSE.setBeltStatus(BELT_STATUS);
		VEHICLE_DATA_RESPONSE.setBodyInformation(BODY_INFORMATION);
		VEHICLE_DATA_RESPONSE.setDeviceStatus(DEVICE_STATUS);
		VEHICLE_DATA_RESPONSE.setDriverBraking(DRIVER_BRAKING);
		VEHICLE_DATA_RESPONSE.setWiperStatus(WIPER_STATUS);
		VEHICLE_DATA_RESPONSE.setHeadLampStatus(HEAD_LAMP_STATUS);
		VEHICLE_DATA_RESPONSE.setAccPedalPosition(ACC_PEDAL_POSITION);
		VEHICLE_DATA_RESPONSE.setSteeringWheelAngle(STEERING_WHEEL_ANGLE);
		VEHICLE_DATA_RESPONSE.setECallInfo(E_CALL_INFO);
		VEHICLE_DATA_RESPONSE.setAirbagStatus(AIRBAG_STATUS);
		VEHICLE_DATA_RESPONSE.setEmergencyEvent(EMERGENCY_EVENT);
		VEHICLE_DATA_RESPONSE.setClusterModeStatus(CLUSTER_MODE_STATUS);
		VEHICLE_DATA_RESPONSE.setMyKey(MY_KEY);
	}
	
    private VehicleDataHelper(){}	
	
}
