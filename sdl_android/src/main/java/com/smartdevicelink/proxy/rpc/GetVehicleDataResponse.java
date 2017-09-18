package com.smartdevicelink.proxy.rpc;


import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.proxy.rpc.enums.WiperStatus;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;

/**
 * Get Vehicle Data Response is sent, when GetVehicleData has been called.
 * 
 * @since SmartDeviceLink 2.0
 */
public class GetVehicleDataResponse extends RPCResponse {
	public static final String KEY_SPEED = "speed";
	public static final String KEY_RPM = "rpm";
	public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
	public static final String KEY_FUEL_LEVEL = "fuelLevel";
	public static final String KEY_VIN = "vin";
	public static final String KEY_PRNDL = "prndl";
	public static final String KEY_TIRE_PRESSURE = "tirePressure";
	public static final String KEY_ENGINE_TORQUE = "engineTorque";
	public static final String KEY_ODOMETER = "odometer";
	public static final String KEY_GPS = "gps";
	public static final String KEY_FUEL_LEVEL_STATE = "fuelLevel_State";
	public static final String KEY_INSTANT_FUEL_CONSUMPTION = "instantFuelConsumption";
	public static final String KEY_BELT_STATUS = "beltStatus";
	public static final String KEY_BODY_INFORMATION = "bodyInformation";
	public static final String KEY_DEVICE_STATUS = "deviceStatus";
	public static final String KEY_DRIVER_BRAKING = "driverBraking";
	public static final String KEY_WIPER_STATUS = "wiperStatus";
	public static final String KEY_HEAD_LAMP_STATUS = "headLampStatus";
	public static final String KEY_ACC_PEDAL_POSITION = "accPedalPosition";
	public static final String KEY_STEERING_WHEEL_ANGLE = "steeringWheelAngle";
	public static final String KEY_E_CALL_INFO = "eCallInfo";
	public static final String KEY_AIRBAG_STATUS = "airbagStatus";
	public static final String KEY_EMERGENCY_EVENT = "emergencyEvent";
	public static final String KEY_CLUSTER_MODE_STATUS = "clusterModeStatus";
	public static final String KEY_MY_KEY = "myKey";

	/** 
	 * Constructs a new GetVehicleDataResponse object
	 */

    public GetVehicleDataResponse() {
        super(FunctionID.GET_VEHICLE_DATA.toString());
    }
    public GetVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setGps(GPSData gps) {
        setParameters(KEY_GPS, gps);
    }
    @SuppressWarnings("unchecked")
    public GPSData getGps() {
        return (GPSData) getObject(GPSData.class, KEY_GPS);
    }
    public void setSpeed(Double speed) {
        setParameters(KEY_SPEED, speed);
    }
    public Double getSpeed() {
    	Object object = getParameters(KEY_SPEED);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    public void setRpm(Integer rpm) {
        setParameters(KEY_RPM, rpm);
    }
    public Integer getRpm() {
    	return getInteger(KEY_RPM);
    }
    public void setFuelLevel(Double fuelLevel) {
        setParameters(KEY_FUEL_LEVEL, fuelLevel);
    }
    public Double getFuelLevel() {
    	Object object = getParameters(KEY_FUEL_LEVEL);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    @Deprecated
    public void setFuelLevel_State(ComponentVolumeStatus fuelLevel_State) {
        setFuelLevelState(fuelLevel_State);
    }
    @Deprecated
    public ComponentVolumeStatus getFuelLevel_State() {
        return getFuelLevelState();
    }
    public void setFuelLevelState(ComponentVolumeStatus fuelLevelState) {
        setParameters(KEY_FUEL_LEVEL_STATE, fuelLevelState);
    }
    public ComponentVolumeStatus getFuelLevelState() {
        return (ComponentVolumeStatus) getObject(ComponentVolumeStatus.class, KEY_FUEL_LEVEL_STATE);
    }
    public void setInstantFuelConsumption(Double instantFuelConsumption) {
        setParameters(KEY_INSTANT_FUEL_CONSUMPTION, instantFuelConsumption);
    }
    public Double getInstantFuelConsumption() {
    	Object object = getParameters(KEY_INSTANT_FUEL_CONSUMPTION);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    public void setExternalTemperature(Double externalTemperature) {
        setParameters(KEY_EXTERNAL_TEMPERATURE, externalTemperature);
    }
    public Double getExternalTemperature() {
    	Object object = getParameters(KEY_EXTERNAL_TEMPERATURE);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    public void setVin(String vin) {
        setParameters(KEY_VIN, vin);
    }
    public String getVin() {
    	return getString(KEY_VIN);
    }
    public void setPrndl(PRNDL prndl) {
        setParameters(KEY_PRNDL, prndl);
    }
    public PRNDL getPrndl() {
        return (PRNDL) getObject(PRNDL.class, KEY_PRNDL);
    }
    public void setTirePressure(TireStatus tirePressure) {
        setParameters(KEY_TIRE_PRESSURE, tirePressure);
    }
    @SuppressWarnings("unchecked")
    public TireStatus getTirePressure() {
        return (TireStatus) getObject(TireStatus.class, KEY_TIRE_PRESSURE);
    }
    public void setOdometer(Integer odometer) {
        setParameters(KEY_ODOMETER, odometer);
    }
    public Integer getOdometer() {
    	return getInteger(KEY_ODOMETER);
    }
    public void setBeltStatus(BeltStatus beltStatus) {
        setParameters(KEY_BELT_STATUS, beltStatus);
    }
    @SuppressWarnings("unchecked")
    public BeltStatus getBeltStatus() {
        return (BeltStatus) getObject(BeltStatus.class, KEY_BELT_STATUS);
    }
    public void setBodyInformation(BodyInformation bodyInformation) {
        setParameters(KEY_BODY_INFORMATION, bodyInformation);
    }
    @SuppressWarnings("unchecked")
    public BodyInformation getBodyInformation() {
        return (BodyInformation) getObject(BodyInformation.class, KEY_BODY_INFORMATION);
    }
    public void setDeviceStatus(DeviceStatus deviceStatus) {
        setParameters(KEY_DEVICE_STATUS, deviceStatus);
    }
    @SuppressWarnings("unchecked")
    public DeviceStatus getDeviceStatus() {
        return (DeviceStatus) getObject(DeviceStatus.class, KEY_DEVICE_STATUS);
    }
    public void setDriverBraking(VehicleDataEventStatus driverBraking) {
        setParameters(KEY_DRIVER_BRAKING, driverBraking);
    }
    public VehicleDataEventStatus getDriverBraking() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_DRIVER_BRAKING);
    }
    public void setWiperStatus(WiperStatus wiperStatus) {
        setParameters(KEY_WIPER_STATUS, wiperStatus);
    }
    public WiperStatus getWiperStatus() {
        return (WiperStatus) getObject(WiperStatus.class, KEY_WIPER_STATUS);
    }
  
    public void setHeadLampStatus(HeadLampStatus headLampStatus) {
        setParameters(KEY_HEAD_LAMP_STATUS, headLampStatus);
    }
    @SuppressWarnings("unchecked")
    public HeadLampStatus getHeadLampStatus() {
        return (HeadLampStatus) getObject(HeadLampStatus.class, KEY_HEAD_LAMP_STATUS);
    }
    public void setEngineTorque(Double engineTorque) {
        setParameters(KEY_ENGINE_TORQUE, engineTorque);
    }
    public Double getEngineTorque() {
    	Object object = getParameters(KEY_ENGINE_TORQUE);
    	return SdlDataTypeConverter.objectToDouble(object);
    }

    public void setAccPedalPosition(Double accPedalPosition) {
        setParameters(KEY_ACC_PEDAL_POSITION, accPedalPosition);
    }
    public Double getAccPedalPosition() {
    	Object object = getParameters(KEY_ACC_PEDAL_POSITION);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
        
    public void setSteeringWheelAngle(Double steeringWheelAngle) {
        setParameters(KEY_STEERING_WHEEL_ANGLE, steeringWheelAngle);
    }
    public Double getSteeringWheelAngle() {
    	Object object = getParameters(KEY_STEERING_WHEEL_ANGLE);
    	return SdlDataTypeConverter.objectToDouble(object);
    }    

    public void setECallInfo(ECallInfo eCallInfo) {
        setParameters(KEY_E_CALL_INFO, eCallInfo);
    }
    @SuppressWarnings("unchecked")
    public ECallInfo getECallInfo() {
        return (ECallInfo) getObject(ECallInfo.class, KEY_E_CALL_INFO);
    }	
	
    public void setAirbagStatus(AirbagStatus airbagStatus) {
        setParameters(KEY_AIRBAG_STATUS, airbagStatus);
    }
    @SuppressWarnings("unchecked")
    public AirbagStatus getAirbagStatus() {
        return (AirbagStatus) getObject(AirbagStatus.class, KEY_AIRBAG_STATUS);
    }	

    public void setEmergencyEvent(EmergencyEvent emergencyEvent) {
        setParameters(KEY_EMERGENCY_EVENT, emergencyEvent);
    }
    @SuppressWarnings("unchecked")
    public EmergencyEvent getEmergencyEvent() {
        return (EmergencyEvent) getObject(EmergencyEvent.class, KEY_EMERGENCY_EVENT);
    }
    public void setClusterModeStatus(ClusterModeStatus clusterModeStatus) {
        setParameters(KEY_CLUSTER_MODE_STATUS, clusterModeStatus);
    }
    @SuppressWarnings("unchecked")
    public ClusterModeStatus getClusterModeStatus() {
        return (ClusterModeStatus) getObject(ClusterModeStatus.class, KEY_CLUSTER_MODE_STATUS);
    }
    public void setMyKey(MyKey myKey) {
        setParameters(KEY_MY_KEY, myKey);
    }
    @SuppressWarnings("unchecked")
    public MyKey getMyKey() {
        return (MyKey) getObject(MyKey.class, KEY_MY_KEY);
    }        
}
