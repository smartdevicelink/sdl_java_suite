package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.util.JsonUtils;

/**
 * Subscribes for specific published vehicle data items. The data will be only
 * sent, if it has changed. The application will be notified by the
 * onVehicleData notification whenever new data is available. The update rate is
 * very much dependent on sensors, vehicle architecture and vehicle type. Be
 * also prepared for the situation that a signal is not available on a vehicle
 * <p>
 * Function Group: Location, VehicleInfo and DrivingChara
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * </p>
 * 
 * @since SmartDeviceLink 2.0
 * @see UnsubscribeVehicleData
 * @see GetVehicleData
 */
public class SubscribeVehicleData extends RPCRequest {
	public static final String KEY_RPM = "rpm";
	public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
	public static final String KEY_FUEL_LEVEL = "fuelLevel";
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
	public static final String KEY_SPEED = "speed";

    // TODO: refactor this class with new VehicleData design
    private Boolean speed, rpm, externalTemperature, fuelLevel, prndl, tirePressure,
        engineTorque, odometer, gps, fuelLevelState, instantFuelConsumption, beltStatus,
        bodyInformation, deviceStatus, driverBraking, wiperStatus, headLampStatus,
        accPedalPosition, steeringWheelAngle, eCallInfo, airbagStatus, emergencyEvent,
        clusterModeStatus, myKey;

	/**
	 * Constructs a new SubscribeVehicleData object
	 */
    public SubscribeVehicleData() {
        super(FunctionID.SUBSCRIBE_VEHICLE_DATA);
    }
    
    /**
     * Creates a SubscribeVehicleData object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SubscribeVehicleData(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.speed = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_SPEED);
            this.rpm = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_RPM);
            this.externalTemperature = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_EXTERNAL_TEMPERATURE);
            this.fuelLevel = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_FUEL_LEVEL);
            this.prndl = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_PRNDL);
            this.tirePressure = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_TIRE_PRESSURE);
            this.engineTorque = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_ENGINE_TORQUE);
            this.odometer = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_ODOMETER);
            this.gps = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_GPS);
            this.fuelLevelState = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_FUEL_LEVEL_STATE);
            this.instantFuelConsumption = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_INSTANT_FUEL_CONSUMPTION);
            this.beltStatus = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_BELT_STATUS);
            this.bodyInformation = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_BODY_INFORMATION);
            this.deviceStatus = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_DEVICE_STATUS);
            this.driverBraking = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_DRIVER_BRAKING);
            this.wiperStatus = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_WIPER_STATUS);
            this.headLampStatus = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_HEAD_LAMP_STATUS);
            this.accPedalPosition = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_ACC_PEDAL_POSITION);
            this.steeringWheelAngle = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_STEERING_WHEEL_ANGLE);
            this.eCallInfo = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_E_CALL_INFO);
            this.airbagStatus = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_AIRBAG_STATUS);
            this.emergencyEvent = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_EMERGENCY_EVENT);
            this.clusterModeStatus = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_CLUSTER_MODE_STATUS);
            this.myKey = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_MY_KEY);
            break;
        }
    }

	/**
	 * Sets a boolean value. If true, subscribes Gps data
	 * 
	 * @param gps
	 *            a boolean value
	 */
    public void setGps(Boolean gps) {
        this.gps = gps;
    }

	/**
	 * Gets a boolean value. If true, means the Gps data has been subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Gps data has been
	 *         subscribed.
	 */
    public Boolean getGps() {
        return this.gps;
    }

	/**
	 * Sets a boolean value. If true, subscribes speed data
	 * 
	 * @param speed
	 *            a boolean value
	 */
    public void setSpeed(Boolean speed) {
        this.speed = speed;
    }

	/**
	 * Gets a boolean value. If true, means the Speed data has been subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Speed data has been
	 *         subscribed.
	 */
    public Boolean getSpeed() {
        return this.speed;
    }

	/**
	 * Sets a boolean value. If true, subscribes rpm data
	 * 
	 * @param rpm
	 *            a boolean value
	 */
    public void setRpm(Boolean rpm) {
        this.rpm = rpm;
    }

	/**
	 * Gets a boolean value. If true, means the rpm data has been subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the rpm data has been
	 *         subscribed.
	 */
    public Boolean getRpm() {
        return this.rpm;
    }

	/**
	 * Sets a boolean value. If true, subscribes FuelLevel data
	 * 
	 * @param fuelLevel
	 *            a boolean value
	 */
    public void setFuelLevel(Boolean fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

	/**
	 * Gets a boolean value. If true, means the FuelLevel data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the FuelLevel data has
	 *         been subscribed.
	 */
    public Boolean getFuelLevel() {
        return this.fuelLevel;
    }

	/**
	 * Sets a boolean value. If true, subscribes fuelLevel_State data
	 * 
	 * @param fuelLevel_State
	 *            a boolean value
	 */
    public void setFuelLevel_State(Boolean fuelLevel_State) {
        this.fuelLevelState = fuelLevel_State;
    }

	/**
	 * Gets a boolean value. If true, means the fuelLevel_State data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the fuelLevel_State data
	 *         has been subscribed.
	 */
    public Boolean getFuelLevel_State() {
        return this.fuelLevelState;
    }

	/**
	 * Sets a boolean value. If true, subscribes instantFuelConsumption data
	 * 
	 * @param instantFuelConsumption
	 *            a boolean value
	 */
    public void setInstantFuelConsumption(Boolean instantFuelConsumption) {
        this.instantFuelConsumption = instantFuelConsumption;
    }

	/**
	 * Gets a boolean value. If true, means the getInstantFuelConsumption data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the getInstantFuelConsumption data
	 *         has been subscribed.
	 */
    public Boolean getInstantFuelConsumption() {
        return this.instantFuelConsumption;
    }

	/**
	 * Sets a boolean value. If true, subscribes externalTemperature data
	 * 
	 * @param externalTemperature
	 *            a boolean value
	 */
    public void setExternalTemperature(Boolean externalTemperature) {
        this.externalTemperature = externalTemperature;
    }

	/**
	 * Gets a boolean value. If true, means the externalTemperature data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the externalTemperature data
	 *         has been subscribed.
	 */
    public Boolean getExternalTemperature() {
        return this.externalTemperature;
    }

	/**
	 * Sets a boolean value. If true, subscribes Currently selected gear data
	 * 
	 * @param prndl
	 *            a boolean value
	 */
    public void setPrndl(Boolean prndl) {
        this.prndl = prndl;
    }

	/**
	 * Gets a boolean value. If true, means the Currently selected gear data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Currently selected gear data
	 *         has been subscribed.
	 */
    public Boolean getPrndl() {
        return this.prndl;
    }

	/**
	 * Sets a boolean value. If true, subscribes tire pressure status data
	 * 
	 * @param tirePressure
	 *            a boolean value
	 */
    public void setTirePressure(Boolean tirePressure) {
        this.tirePressure = tirePressure;
    }

	/**
	 * Gets a boolean value. If true, means the tire pressure status data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the tire pressure status data
	 *         has been subscribed.
	 */
    public Boolean getTirePressure() {
        return this.tirePressure;
    }

	/**
	 * Sets a boolean value. If true, subscribes odometer data
	 * 
	 * @param odometer
	 *            a boolean value
	 */
    public void setOdometer(Boolean odometer) {
        this.odometer = odometer;
    }

	/**
	 * Gets a boolean value. If true, means the odometer data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the odometer data
	 *         has been subscribed.
	 */
    public Boolean getOdometer() {
        return this.odometer;
    }

	/**
	 * Sets a boolean value. If true, subscribes belt Status data
	 * 
	 * @param beltStatus
	 *            a boolean value
	 */
    public void setBeltStatus(Boolean beltStatus) {
        this.beltStatus = beltStatus;
    }

	/**
	 * Gets a boolean value. If true, means the belt Status data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the belt Status data
	 *         has been subscribed.
	 */
    public Boolean getBeltStatus() {
        return this.beltStatus;
    }

	/**
	 * Sets a boolean value. If true, subscribes body Information data
	 * 
	 * @param bodyInformation
	 *            a boolean value
	 */
    public void setBodyInformation(Boolean bodyInformation) {
        this.bodyInformation = bodyInformation;
    }

	/**
	 * Gets a boolean value. If true, means the body Information data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the body Information data
	 *         has been subscribed.
	 */
    public Boolean getBodyInformation() {
        return this.bodyInformation;
    }

	/**
	 * Sets a boolean value. If true, subscribes device Status data
	 * 
	 * @param deviceStatus
	 *            a boolean value
	 */
    public void setDeviceStatus(Boolean deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

	/**
	 * Gets a boolean value. If true, means the device Status data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the device Status data
	 *         has been subscribed.
	 */
    public Boolean getDeviceStatus() {
        return this.deviceStatus;
    }

	/**
	 * Sets a boolean value. If true, subscribes driver Braking data
	 * 
	 * @param driverBraking
	 *            a boolean value
	 */
    public void setDriverBraking(Boolean driverBraking) {
        this.driverBraking = driverBraking;
    }

	/**
	 * Gets a boolean value. If true, means the driver Braking data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the driver Braking data
	 *         has been subscribed.
	 */
    public Boolean getDriverBraking() {
        return this.driverBraking;
    }

	/**
	 * Sets a boolean value. If true, subscribes wiper Status data
	 * 
	 * @param wiperStatus
	 *            a boolean value
	 */
    public void setWiperStatus(Boolean wiperStatus) {
        this.wiperStatus = wiperStatus;
    }

	/**
	 * Gets a boolean value. If true, means the wiper Status data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the wiper Status data
	 *         has been subscribed.
	 */
    public Boolean getWiperStatus() {
        return this.wiperStatus;
    }

	/**
	 * Sets a boolean value. If true, subscribes Head Lamp Status data
	 * 
	 * @param headLampStatus
	 *            a boolean value
	 */
    public void setHeadLampStatus(Boolean headLampStatus) {
        this.headLampStatus = headLampStatus;
    }

	/**
	 * Gets a boolean value. If true, means the Head Lamp Status data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Head Lamp Status data
	 *         has been subscribed.
	 */
    public Boolean getHeadLampStatus() {
        return this.headLampStatus;
    }

	/**
	 * Sets a boolean value. If true, subscribes Engine Torque data
	 * 
	 * @param engineTorque
	 *            a boolean value
	 */
    public void setEngineTorque(Boolean engineTorque) {
        this.engineTorque = engineTorque;
    }

	/**
	 * Gets a boolean value. If true, means the Engine Torque data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Engine Torque data
	 *         has been subscribed.
	 */
    public Boolean getEngineTorque() {
        return this.engineTorque;
    }

	/**
	 * Sets a boolean value. If true, subscribes accPedalPosition data
	 * 
	 * @param accPedalPosition
	 *            a boolean value
	 */
    public void setAccPedalPosition(Boolean accPedalPosition) {
        this.accPedalPosition = accPedalPosition;
    }

	/**
	 * Gets a boolean value. If true, means the accPedalPosition data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the accPedalPosition data
	 *         has been subscribed.
	 */
    public Boolean getAccPedalPosition() {
        return this.accPedalPosition;
    }
  
    public void setSteeringWheelAngle(Boolean steeringWheelAngle) {
        this.steeringWheelAngle = steeringWheelAngle;
    }

    public Boolean getSteeringWheelAngle() {
        return this.steeringWheelAngle;
    }
    
    public void setECallInfo(Boolean eCallInfo) {
        this.eCallInfo = eCallInfo;
    }
    
    public Boolean getECallInfo() {
        return this.eCallInfo;
    }
    
    public void setAirbagStatus(Boolean airbagStatus) {
        this.airbagStatus = airbagStatus;
    }
    
    public Boolean getAirbagStatus() {
        return this.airbagStatus;
    }
    
    public void setEmergencyEvent(Boolean emergencyEvent) {
        this.emergencyEvent = emergencyEvent;
    }
    
    public Boolean getEmergencyEvent() {
        return this.emergencyEvent;
    }
    
    public void setClusterModeStatus(Boolean clusterModeStatus) {
        this.clusterModeStatus = clusterModeStatus;
    }
    
    public Boolean getClusterModeStatus() {
        return this.clusterModeStatus;
    }
    
    public void setMyKey(Boolean myKey) {
        this.myKey = myKey;
    }
    
    public Boolean getMyKey() {
        return this.myKey;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_ACC_PEDAL_POSITION, this.accPedalPosition);
            JsonUtils.addToJsonObject(result, KEY_AIRBAG_STATUS, this.airbagStatus);
            JsonUtils.addToJsonObject(result, KEY_BELT_STATUS, this.beltStatus);
            JsonUtils.addToJsonObject(result, KEY_BODY_INFORMATION, this.bodyInformation);
            JsonUtils.addToJsonObject(result, KEY_CLUSTER_MODE_STATUS, this.clusterModeStatus);
            JsonUtils.addToJsonObject(result, KEY_DEVICE_STATUS, this.deviceStatus);
            JsonUtils.addToJsonObject(result, KEY_DRIVER_BRAKING, this.driverBraking);
            JsonUtils.addToJsonObject(result, KEY_E_CALL_INFO, this.eCallInfo);
            JsonUtils.addToJsonObject(result, KEY_EMERGENCY_EVENT, this.emergencyEvent);
            JsonUtils.addToJsonObject(result, KEY_ENGINE_TORQUE, this.engineTorque);
            JsonUtils.addToJsonObject(result, KEY_EXTERNAL_TEMPERATURE, this.externalTemperature);
            JsonUtils.addToJsonObject(result, KEY_FUEL_LEVEL, this.fuelLevel);
            JsonUtils.addToJsonObject(result, KEY_FUEL_LEVEL_STATE, this.fuelLevelState);
            JsonUtils.addToJsonObject(result, KEY_GPS, this.gps);
            JsonUtils.addToJsonObject(result, KEY_HEAD_LAMP_STATUS, this.headLampStatus);
            JsonUtils.addToJsonObject(result, KEY_INSTANT_FUEL_CONSUMPTION, this.instantFuelConsumption);
            JsonUtils.addToJsonObject(result, KEY_MY_KEY, this.myKey);
            JsonUtils.addToJsonObject(result, KEY_ODOMETER, this.odometer);
            JsonUtils.addToJsonObject(result, KEY_PRNDL, this.prndl);
            JsonUtils.addToJsonObject(result, KEY_RPM, this.rpm);
            JsonUtils.addToJsonObject(result, KEY_SPEED, this.speed);
            JsonUtils.addToJsonObject(result, KEY_STEERING_WHEEL_ANGLE, this.steeringWheelAngle);
            JsonUtils.addToJsonObject(result, KEY_TIRE_PRESSURE, this.tirePressure);
            JsonUtils.addToJsonObject(result, KEY_WIPER_STATUS, this.wiperStatus);
            break;
        }
        
        return result;
    }  
}
