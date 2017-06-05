package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.util.DebugTool;

import static android.provider.Contacts.SettingsColumns.KEY;

/**
 * Unsubscribe Vehicle Data Response is sent, when UnsubscribeVehicleData has been called.
 * 
 * @since SmartDeviceLink 2.0
 */
public class UnsubscribeVehicleDataResponse extends RPCResponse {
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

	/**
	 * Constructs a new UnsubscribeVehicleDataResponse object
	 */
    public UnsubscribeVehicleDataResponse() {
        super(FunctionID.UNSUBSCRIBE_VEHICLE_DATA.toString());
    }

	/**
	 * Constructs a new UnsubscribeVehicleDataResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public UnsubscribeVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Sets Gps
     * @param gps
     */
    public void setGps(VehicleDataResult gps) {
        setParameters(KEY_GPS, gps);
    }
    /**
     * Gets Gps
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getGps() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_GPS);
    }
    /**
     * Sets Speed
     * @param speed
     */
    public void setSpeed(VehicleDataResult speed) {
        setParameters(KEY_SPEED, speed);
    }
    /**
     * Gets Speed
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getSpeed() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_SPEED);
    }
    /**
     * Sets rpm
     * @param rpm
     */
    public void setRpm(VehicleDataResult rpm) {
        setParameters(KEY_RPM, rpm);
    }
    /**
     * Gets rpm
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getRpm() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_RPM);
    }
    /**
     * Sets Fuel Level
     * @param fuelLevel
     */
    public void setFuelLevel(VehicleDataResult fuelLevel) {
        setParameters(KEY_FUEL_LEVEL, fuelLevel);
    }
    /**
     * Gets Fuel Level
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getFuelLevel() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_FUEL_LEVEL);
    }
    /**
     * Sets Fuel Level State
     * @param fuelLevel_State
     */
    @Deprecated
    public void setFuelLevel_State(VehicleDataResult fuelLevel_State) {
        setFuelLevel(fuelLevel_State);
    }
    /**
     * Gets Fuel Level State
     * @return VehicleDataResult
     */
    @Deprecated
    public VehicleDataResult getFuelLevel_State() {
        return getFuelLevelState();
    }
    /**
     * Sets Fuel Level State
     * @param fuelLevel_State
     */
    public void setFuelLevelState(VehicleDataResult fuelLevelState) {
        setParameters(KEY_FUEL_LEVEL_STATE, fuelLevelState);
    }
    /**
     * Gets Fuel Level State
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getFuelLevelState() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_FUEL_LEVEL_STATE);
    }
    /**
     * Sets Instant Fuel Comsumption
     * @param instantFuelConsumption
     */
    public void setInstantFuelConsumption(VehicleDataResult instantFuelConsumption) {
        setParameters(KEY_INSTANT_FUEL_CONSUMPTION, instantFuelConsumption);
    }
    /**
     * Gets Instant Fuel Comsumption
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getInstantFuelConsumption() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_INSTANT_FUEL_CONSUMPTION);
    }
    /**
     * Sets External Temperature
     * @param externalTemperature
     */
    public void setExternalTemperature(VehicleDataResult externalTemperature) {
        setParameters(KEY_EXTERNAL_TEMPERATURE, externalTemperature);
    }
    /**
     * Gets External Temperature
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getExternalTemperature() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_EXTERNAL_TEMPERATURE);
    }
    /**
     * Gets currently selected gear data
     * @param prndl
     */
    public void setPrndl(VehicleDataResult prndl) {
        setParameters(KEY_PRNDL, prndl);
    }
    /**
     * Gets currently selected gear data
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getPrndl() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_PRNDL);
    }
    /**
     * Sets Tire Pressure
     * @param tirePressure
     */
    public void setTirePressure(VehicleDataResult tirePressure) {
        setParameters(KEY_TIRE_PRESSURE, tirePressure);
    }
    /**
     * Gets Tire Pressure
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getTirePressure() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_TIRE_PRESSURE);
    }
    /**
     * Sets Odometer
     * @param odometer
     */
    public void setOdometer(VehicleDataResult odometer) {
        setParameters(KEY_ODOMETER, odometer);
    }
    /**
     * Gets Odometer
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getOdometer() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_ODOMETER);
    }
    /**
     * Sets Belt Status
     * @param beltStatus
     */
    public void setBeltStatus(VehicleDataResult beltStatus) {
        setParameters(KEY_BELT_STATUS, beltStatus);
    }
    /**
     * Gets Belt Status
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getBeltStatus() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_BELT_STATUS);
    }
    /**
     * Sets Body Information
     * @param bodyInformation
     */
    public void setBodyInformation(VehicleDataResult bodyInformation) {
        setParameters(KEY_BODY_INFORMATION, bodyInformation);
    }
    /**
     * Gets Body Information
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getBodyInformation() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_BODY_INFORMATION);
    }
    /**
     * Sets Device Status
     * @param deviceStatus
     */
    public void setDeviceStatus(VehicleDataResult deviceStatus) {
        setParameters(KEY_DEVICE_STATUS, deviceStatus);
    }
    /**
     * Gets Device Status
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getDeviceStatus() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_DEVICE_STATUS);
    }
    /**
     * Sets Driver Braking
     * @param driverBraking
     */
    public void setDriverBraking(VehicleDataResult driverBraking) {
        setParameters(KEY_DRIVER_BRAKING, driverBraking);
    }
    /**
     * Gets Driver Braking
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getDriverBraking() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_DRIVER_BRAKING);
    }
    /**
     * Sets Wiper Status
     * @param wiperStatus
     */
    public void setWiperStatus(VehicleDataResult wiperStatus) {
        setParameters(KEY_WIPER_STATUS, wiperStatus);
    }
    /**
     * Gets Wiper Status
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getWiperStatus() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_WIPER_STATUS);
    }
    /**
     * Sets Head Lamp Status
     * @param headLampStatus
     */
    public void setHeadLampStatus(VehicleDataResult headLampStatus) {
        setParameters(KEY_HEAD_LAMP_STATUS, headLampStatus);
    }
    /**
     * Gets Head Lamp Status
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getHeadLampStatus() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_HEAD_LAMP_STATUS);
    }
    /**
     * Sets Engine Torque
     * @param engineTorque
     */
    public void setEngineTorque(VehicleDataResult engineTorque) {
        setParameters(KEY_ENGINE_TORQUE, engineTorque);
    }
    /**
     * Gets Engine Torque
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getEngineTorque() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_ENGINE_TORQUE);
    }
    /**
     * Sets AccPedal Position
     * @param accPedalPosition
     */
    public void setAccPedalPosition(VehicleDataResult accPedalPosition) {
        setParameters(KEY_ACC_PEDAL_POSITION, accPedalPosition);
    }
    /**
     * Gets AccPedal Position
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getAccPedalPosition() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_ACC_PEDAL_POSITION);
    }  
    
    public void setSteeringWheelAngle(VehicleDataResult steeringWheelAngle) {
        setParameters(KEY_STEERING_WHEEL_ANGLE, steeringWheelAngle);
    }

    @SuppressWarnings("unchecked")
    public VehicleDataResult getSteeringWheelAngle() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_STEERING_WHEEL_ANGLE);
    }    
    
    public void setECallInfo(VehicleDataResult eCallInfo) {
        setParameters(KEY_E_CALL_INFO, eCallInfo);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getECallInfo() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_E_CALL_INFO);
    }
    public void setAirbagStatus(VehicleDataResult airbagStatus) {
        setParameters(KEY_AIRBAG_STATUS, airbagStatus);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getAirbagStatus() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_AIRBAG_STATUS);
    }
    public void setEmergencyEvent(VehicleDataResult emergencyEvent) {
        setParameters(KEY_EMERGENCY_EVENT, emergencyEvent);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getEmergencyEvent() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_EMERGENCY_EVENT);
    }
    public void setClusterModeStatus(VehicleDataResult clusterModeStatus) {
        setParameters(KEY_CLUSTER_MODE_STATUS, clusterModeStatus);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getClusterModeStatus() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_CLUSTER_MODE_STATUS);
    }
    public void setMyKey(VehicleDataResult myKey) {
        setParameters(KEY_MY_KEY, myKey);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getMyKey() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_MY_KEY);
    }     
}
