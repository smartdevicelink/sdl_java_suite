package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.util.JsonUtils;

/**
 * Subscribe Vehicle Data Response is sent, when SubscribeVehicleData has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class SubscribeVehicleDataResponse extends RPCResponse {
	public static final String KEY_SPEED = "speed";
	public static final String KEY_RPM = "rpm";
	public static final String KEY_FUEL_LEVEL = "fuelLevel";
	public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
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

    // TODO: refactor this class with new VehicleData design
	private VehicleDataResult speed, rpm, externalTemperature, fuelLevel, prndl, tirePressure,
        engineTorque, odometer, gps, fuelLevelState, instantFuelConsumption, beltStatus,
        bodyInformation, deviceStatus, driverBraking, wiperStatus, headLampStatus,
        accPedalPosition, steeringWheelAngle, eCallInfo, airbagStatus, emergencyEvent,
        clusterModeStatus, myKey;

	/**
	 * Constructs a new SubscribeVehicleDataResponse object
	 */
    public SubscribeVehicleDataResponse() {
        super(FunctionID.SUBSCRIBE_VEHICLE_DATA);
    }
    
    /**
     * Creates a SubscribeVehicleDataResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SubscribeVehicleDataResponse(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            JSONObject temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_SPEED);
            if(temp != null){
                this.speed = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_RPM);
            if(temp != null){
                this.rpm = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_EXTERNAL_TEMPERATURE);
            if(temp != null){
                this.externalTemperature = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_FUEL_LEVEL);
            if(temp != null){
                this.fuelLevel = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_PRNDL);
            if(temp != null){
                this.prndl = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_TIRE_PRESSURE);
            if(temp != null){
                this.tirePressure = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_ENGINE_TORQUE);
            if(temp != null){
                this.engineTorque = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_ODOMETER);
            if(temp != null){
                this.odometer = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_GPS);
            if(temp != null){
                this.gps = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_FUEL_LEVEL_STATE);
            if(temp != null){
                this.fuelLevelState = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_INSTANT_FUEL_CONSUMPTION);
            if(temp != null){
                this.instantFuelConsumption = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_BELT_STATUS);
            if(temp != null){
                this.beltStatus = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_BODY_INFORMATION);
            if(temp != null){
                this.bodyInformation = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_DEVICE_STATUS);
            if(temp != null){
                this.deviceStatus = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_DRIVER_BRAKING);
            if(temp != null){
                this.driverBraking = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_WIPER_STATUS);
            if(temp != null){
                this.wiperStatus = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_HEAD_LAMP_STATUS);
            if(temp != null){
                this.headLampStatus = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_ACC_PEDAL_POSITION);
            if(temp != null){
                this.accPedalPosition = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_STEERING_WHEEL_ANGLE);
            if(temp != null){
                this.steeringWheelAngle = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_E_CALL_INFO);
            if(temp != null){
                this.eCallInfo = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_AIRBAG_STATUS);
            if(temp != null){
                this.airbagStatus = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_EMERGENCY_EVENT);
            if(temp != null){
                this.emergencyEvent = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_CLUSTER_MODE_STATUS);
            if(temp != null){
                this.clusterModeStatus = new VehicleDataResult(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_MY_KEY);
            if(temp != null){
                this.myKey = new VehicleDataResult(temp);
            }
            break;
        }
    }
    
    /**
     * Sets gps
     * @param gps
     */
    public void setGps(VehicleDataResult gps) {
        this.gps = gps;
    }
    
    /**
     * Gets gps
     * @return VehicleDataResult 
     */
    public VehicleDataResult getGps() {
    	return this.gps;
    }
    
    /**
     * Sets speed
     * @param speed
     */
    public void setSpeed(VehicleDataResult speed) {
        this.speed = speed;
    }
    
    /**
     * Gets speed
     * @return VehicleDataResult 
     */
    public VehicleDataResult getSpeed() {
    	return this.speed;
    }
    
    /**
     * Sets rpm
     * @param rpm
     */
    public void setRpm(VehicleDataResult rpm) {
        this.rpm = rpm;
    }
    
    /**
     * Gets rpm
     * @return VehicleDataResult 
     */
    public VehicleDataResult getRpm() {
    	return this.rpm;
    }
    
    /**
     * Sets Fuel Level
     * @param fuelLevel
     */
    public void setFuelLevel(VehicleDataResult fuelLevel) {
        this.fuelLevel = fuelLevel;
    }
    
    /**
     * Gets Fuel Level
     * @return VehicleDataResult 
     */
    public VehicleDataResult getFuelLevel() {
    	return this.fuelLevel;
    }
    
    /**
     * Sets Fuel Level State
     * @param fuelLevel_State
     */
    public void setFuelLevel_State(VehicleDataResult fuelLevel_State) {
        this.fuelLevelState = fuelLevel_State;
    }
    
    /**
     * Gets Fuel Level State
     * @return VehicleDataResult 
     */
    public VehicleDataResult getFuelLevel_State() {
    	return this.fuelLevelState;
    }
    
    /**
     * Sets Instant Fuel Comsumption
     * @param instantFuelConsumption
     */
    public void setInstantFuelConsumption(VehicleDataResult instantFuelConsumption) {
        this.instantFuelConsumption = instantFuelConsumption;
    }
    
    /**
     * Gets Instant Fuel Consumption
     * @return VehicleDataResult 
     */
    public VehicleDataResult getInstantFuelConsumption() {
    	return this.instantFuelConsumption;
    }
    
    /**
     * Sets External Temperature
     * @param externalTemperature
     */
    public void setExternalTemperature(VehicleDataResult externalTemperature) {
        this.externalTemperature = externalTemperature;
    }
    
    /**
     * Gets External Temperature
     * @return VehicleDataResult 
     */
    public VehicleDataResult getExternalTemperature() {
    	return this.externalTemperature;
    }
    
    /**
     * Sets currently selected gear data
     * @param prndl
     */
    public void setPrndl(VehicleDataResult prndl) {
        this.prndl = prndl;
    }
    
    /**
     * Gets currently selected gear data
     * @return VehicleDataResult 
     */
    public VehicleDataResult getPrndl() {
    	return this.prndl;
    }
    
    /**
     * Sets Tire Pressure
     * @param tirePressure
     */
    public void setTirePressure(VehicleDataResult tirePressure) {
        this.tirePressure = tirePressure;
    }
    
    /**
     * Gets Tire Pressure
     * @return VehicleDataResult 
     */
    public VehicleDataResult getTirePressure() {
    	return this.tirePressure;
    }
    
    /**
     * Sets Odometer
     * @param odometer
     */
    public void setOdometer(VehicleDataResult odometer) {
        this.odometer = odometer;
    }
    
    /**
     * Gets Odometer
     * @return VehicleDataResult 
     */
    public VehicleDataResult getOdometer() {
    	return this.odometer;
    }
    
    /**
     * Sets Belt Status
     * @param beltStatus
     */
    public void setBeltStatus(VehicleDataResult beltStatus) {
        this.beltStatus = beltStatus;
    }
    
    /**
     * Gets Belt Status
     * @return VehicleDataResult 
     */
    public VehicleDataResult getBeltStatus() {
    	return this.beltStatus;
    }
    
    /**
     * Sets Body Information
     * @param bodyInformation
     */
    public void setBodyInformation(VehicleDataResult bodyInformation) {
        this.bodyInformation = bodyInformation;
    }
    
    /**
     * Gets Body Information
     * @return VehicleDataResult 
     */
    public VehicleDataResult getBodyInformation() {
    	return this.bodyInformation;
    }
    
    /**
     * Sets Device Status
     * @param deviceStatus
     */
    public void setDeviceStatus(VehicleDataResult deviceStatus) {
        this.deviceStatus = deviceStatus;
    }
    
    /**
     * Gets Device Status
     * @return VehicleDataResult 
     */
    public VehicleDataResult getDeviceStatus() {
    	return this.deviceStatus;
    }
    
    /**
     * Sets Driver Barking
     * @param driverBraking
     */
    public void setDriverBraking(VehicleDataResult driverBraking) {
        this.driverBraking = driverBraking;
    }
    
    /**
     * Gets Driver Barking
     * @return VehicleDataResult 
     */
    public VehicleDataResult getDriverBraking() {
    	return this.driverBraking;
    }
    
    /**
     * Sets wiper Status
     * @param wiperStatus
     */
    public void setWiperStatus(VehicleDataResult wiperStatus) {
        this.wiperStatus = wiperStatus;
    }
    
    /**
     * Gets Wiper Status
     * @return VehicleDataResult 
     */
    public VehicleDataResult getWiperStatus() {
    	return this.wiperStatus;
    }
    
    /**
     * Sets Head Lamp Status
     * @param headLampStatus
     */
    public void setHeadLampStatus(VehicleDataResult headLampStatus) {
        this.headLampStatus = headLampStatus;
    }
    
    /**
     * Gets Head Lamp Status
     * @return VehicleDataResult 
     */
    public VehicleDataResult getHeadLampStatus() {
    	return this.headLampStatus;
    }
    
    /**
     * Sets Engine Torque
     * @param engineTorque
     */
    public void setEngineTorque(VehicleDataResult engineTorque) {
        this.engineTorque = engineTorque;
    }
    
    /**
     * Gets Engine Torque
     * @return VehicleDataResult 
     */
    public VehicleDataResult getEngineTorque() {
    	return this.engineTorque;
    }
    
    /**
     * Sets AccPedal Position
     * @param accPedalPosition
     */
    public void setAccPedalPosition(VehicleDataResult accPedalPosition) {
        this.accPedalPosition = accPedalPosition;
    }
    
    /**
     * Gets AccPedal Position
     * @return VehicleDataResult 
     */
    public VehicleDataResult getAccPedalPosition() {
    	return this.accPedalPosition;
    }

    public void setSteeringWheelAngle(VehicleDataResult steeringWheelAngle) {
        this.steeringWheelAngle = steeringWheelAngle;
    }

    public VehicleDataResult getSteeringWheelAngle() {
    	return this.steeringWheelAngle;
    }
    
    public void setECallInfo(VehicleDataResult eCallInfo) {
        this.eCallInfo = eCallInfo;
    }
    
    public VehicleDataResult getECallInfo() {
    	return this.eCallInfo;
    }
    
    public void setAirbagStatus(VehicleDataResult airbagStatus) {
        this.airbagStatus = airbagStatus;
    }
    
    public VehicleDataResult getAirbagStatus() {
    	return this.airbagStatus;
    }
    
    public void setEmergencyEvent(VehicleDataResult emergencyEvent) {
        this.emergencyEvent = emergencyEvent;
    }
    
    public VehicleDataResult getEmergencyEvent() {
    	return this.emergencyEvent;
    }
    
    public void setClusterModeStatus(VehicleDataResult clusterModeStatus) {
        this.clusterModeStatus = clusterModeStatus;
    }
    
    public VehicleDataResult getClusterModeStatus() {
    	return this.clusterModeStatus;
    }
    
    public void setMyKey(VehicleDataResult myKey) {
        this.myKey = myKey;
    }
    
    public VehicleDataResult getMyKey() {
    	return this.myKey;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_ACC_PEDAL_POSITION, (this.accPedalPosition == null) ? null :
                this.accPedalPosition.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_AIRBAG_STATUS, (this.airbagStatus == null) ? null :
                this.airbagStatus.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_BELT_STATUS, (this.beltStatus == null) ? null :
                this.beltStatus.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_BODY_INFORMATION, (this.bodyInformation == null) ? null :
                this.bodyInformation.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_CLUSTER_MODE_STATUS, (this.clusterModeStatus == null) ? null :
                this.clusterModeStatus.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_DEVICE_STATUS, (this.deviceStatus == null) ? null :
                this.deviceStatus.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_DRIVER_BRAKING, (this.driverBraking == null) ? null :
                this.driverBraking.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_E_CALL_INFO, (this.eCallInfo == null) ? null :
                this.eCallInfo.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_EMERGENCY_EVENT, (this.emergencyEvent == null) ? null :
                this.emergencyEvent.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_ENGINE_TORQUE, (this.engineTorque == null) ? null :
                this.engineTorque.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_EXTERNAL_TEMPERATURE, (this.externalTemperature == null) ? null :
                this.externalTemperature.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_FUEL_LEVEL, (this.fuelLevel == null) ? null : 
                this.fuelLevel.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_FUEL_LEVEL_STATE, (this.fuelLevelState == null) ? null :
                this.fuelLevelState.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_GPS, (this.gps == null) ? null :
                this.gps.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_HEAD_LAMP_STATUS, (this.headLampStatus == null) ? null :
                this.headLampStatus.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_INSTANT_FUEL_CONSUMPTION, (this.instantFuelConsumption == null) ? null :
                this.instantFuelConsumption.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_MY_KEY, (this.myKey == null) ? null :
                this.myKey.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_ODOMETER, (this.odometer == null) ? null :
                this.odometer.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_PRNDL, (this.prndl == null) ? null :
                this.prndl.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_RPM, (this.rpm == null) ? null :
                this.rpm.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_SPEED, (this.speed == null) ? null :
                this.speed.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_STEERING_WHEEL_ANGLE, (this.steeringWheelAngle == null) ? null :
                this.steeringWheelAngle.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_TIRE_PRESSURE, (this.tirePressure == null) ? null :
                this.tirePressure.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_WIPER_STATUS, (this.wiperStatus == null) ? null :
                this.wiperStatus.getJsonParameters(sdlVersion));
            break;
        }
        
        return result;
    }
}
