package com.smartdevicelink.proxy.rpc;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.VehicleData;

/**
 * This function is used to unsubscribe the notifications from the
 * subscribeVehicleData function
 * <p>
 * Function Group: Location, VehicleInfo and DrivingChara
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * </p>
 * 
 * @since SmartDeviceLink 2.0
 * @see SubscribeVehicleData
 * @see GetVehicleData
 */
public class UnsubscribeVehicleData extends RPCRequest {

	/**
	 * Constructs a new UnsubscribeVehicleData object
	 */
    public UnsubscribeVehicleData() {
        super(FunctionID.UNSUBSCRIBE_VEHICLE_DATA);
    }

	/**
	 * Constructs a new UnsubscribeVehicleData object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public UnsubscribeVehicleData(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public void setVehicleData(Boolean toSet, VehicleData... vehicleData){
        for(VehicleData data : vehicleData){
            if(toSet == null){
                parameters.remove(data.getJsonName());
            }
            else{
                parameters.put(data.getJsonName(), toSet);
            }
        }
    }
    
    public Boolean getVehicleData(VehicleData vehicleData){
        return (Boolean) parameters.get(vehicleData.getJsonName());
    }
    
    public Map<VehicleData, Boolean> getVehicleData(VehicleData... vehicleData){
        Map<VehicleData, Boolean> result = new HashMap<VehicleData, Boolean>();
        
        for(VehicleData data : vehicleData){
            Boolean isSet = (Boolean) parameters.get(data.getJsonName());
            if(isSet != null){
                result.put(data, isSet);
            }
        }
        
        return result;
    }

    /**
     * Sets a boolean value. If true, subscribes Gps data
     * 
     * @param gps
     *            a boolean value
     */
    @Deprecated
    public void setGps(Boolean gps) {
        setVehicleData(gps, VehicleData.GPS);
    }

    /**
     * Gets a boolean value. If true, means the Gps data has been subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the Gps data has been
     *         subscribed.
     */
    @Deprecated
    public Boolean getGps() {
        return getVehicleData(VehicleData.GPS);
    }

    /**
     * Sets a boolean value. If true, subscribes speed data
     * 
     * @param speed
     *            a boolean value
     */
    @Deprecated
    public void setSpeed(Boolean speed) {
        setVehicleData(speed, VehicleData.SPEED);
    }

    /**
     * Gets a boolean value. If true, means the Speed data has been subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the Speed data has been
     *         subscribed.
     */
    @Deprecated
    public Boolean getSpeed() {
        return getVehicleData(VehicleData.SPEED);
    }

    /**
     * Sets a boolean value. If true, subscribes rpm data
     * 
     * @param rpm
     *            a boolean value
     */
    @Deprecated
    public void setRpm(Boolean rpm) {
        setVehicleData(rpm, VehicleData.RPM);
    }

    /**
     * Gets a boolean value. If true, means the rpm data has been subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the rpm data has been
     *         subscribed.
     */
    @Deprecated
    public Boolean getRpm() {
        return getVehicleData(VehicleData.RPM);
    }

    /**
     * Sets a boolean value. If true, subscribes FuelLevel data
     * 
     * @param fuelLevel
     *            a boolean value
     */
    @Deprecated
    public void setFuelLevel(Boolean fuelLevel) {
        setVehicleData(fuelLevel, VehicleData.FUEL_LEVEL);
    }

    /**
     * Gets a boolean value. If true, means the FuelLevel data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the FuelLevel data has
     *         been subscribed.
     */
    @Deprecated
    public Boolean getFuelLevel() {
        return getVehicleData(VehicleData.FUEL_LEVEL);
    }

    /**
     * Sets a boolean value. If true, subscribes fuelLevel_State data
     * 
     * @param fuelLevel_State
     *            a boolean value
     */
    @Deprecated
    public void setFuelLevel_State(Boolean fuelLevel_State) {
        setVehicleData(fuelLevel_State, VehicleData.FUEL_LEVEL_STATE);
    }

    /**
     * Gets a boolean value. If true, means the fuelLevel_State data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the fuelLevel_State data
     *         has been subscribed.
     */
    @Deprecated
    public Boolean getFuelLevel_State() {
        return getVehicleData(VehicleData.FUEL_LEVEL_STATE);
    }

    /**
     * Sets a boolean value. If true, subscribes instantFuelConsumption data
     * 
     * @param instantFuelConsumption
     *            a boolean value
     */
    @Deprecated
    public void setInstantFuelConsumption(Boolean instantFuelConsumption) {
        setVehicleData(instantFuelConsumption, VehicleData.INSTANT_FUEL_CONSUMPTION);
    }

    /**
     * Gets a boolean value. If true, means the getInstantFuelConsumption data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the getInstantFuelConsumption data
     *         has been subscribed.
     */
    @Deprecated
    public Boolean getInstantFuelConsumption() {
        return getVehicleData(VehicleData.INSTANT_FUEL_CONSUMPTION);
    }

    /**
     * Sets a boolean value. If true, subscribes externalTemperature data
     * 
     * @param externalTemperature
     *            a boolean value
     */
    @Deprecated
    public void setExternalTemperature(Boolean externalTemperature) {
        setVehicleData(externalTemperature, VehicleData.EXTERNAL_TEMPERATURE);
    }

    /**
     * Gets a boolean value. If true, means the externalTemperature data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the externalTemperature data
     *         has been subscribed.
     */
    @Deprecated
    public Boolean getExternalTemperature() {
        return getVehicleData(VehicleData.EXTERNAL_TEMPERATURE);
    }

    /**
     * Sets a boolean value. If true, subscribes Currently selected gear data
     * 
     * @param prndl
     *            a boolean value
     */
    @Deprecated
    public void setPrndl(Boolean prndl) {
        setVehicleData(prndl, VehicleData.PRNDL);
    }

    /**
     * Gets a boolean value. If true, means the Currently selected gear data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the Currently selected gear data
     *         has been subscribed.
     */
    @Deprecated
    public Boolean getPrndl() {
        return getVehicleData(VehicleData.PRNDL);
    }

    /**
     * Sets a boolean value. If true, subscribes tire pressure status data
     * 
     * @param tirePressure
     *            a boolean value
     */
    @Deprecated
    public void setTirePressure(Boolean tirePressure) {
        setVehicleData(tirePressure, VehicleData.TIRE_PRESSURE);
    }

    /**
     * Gets a boolean value. If true, means the tire pressure status data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the tire pressure status data
     *         has been subscribed.
     */
    @Deprecated
    public Boolean getTirePressure() {
        return getVehicleData(VehicleData.TIRE_PRESSURE);
    }

    /**
     * Sets a boolean value. If true, subscribes odometer data
     * 
     * @param odometer
     *            a boolean value
     */
    @Deprecated
    public void setOdometer(Boolean odometer) {
        setVehicleData(odometer, VehicleData.ODOMETER);
    }

    /**
     * Gets a boolean value. If true, means the odometer data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the odometer data
     *         has been subscribed.
     */
    @Deprecated
    public Boolean getOdometer() {
        return getVehicleData(VehicleData.ODOMETER);
    }

    /**
     * Sets a boolean value. If true, subscribes belt Status data
     * 
     * @param beltStatus
     *            a boolean value
     */
    @Deprecated
    public void setBeltStatus(Boolean beltStatus) {
        setVehicleData(beltStatus, VehicleData.BELT_STATUS);
    }

    /**
     * Gets a boolean value. If true, means the belt Status data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the belt Status data
     *         has been subscribed.
     */
    @Deprecated
    public Boolean getBeltStatus() {
        return getVehicleData(VehicleData.BELT_STATUS);
    }

    /**
     * Sets a boolean value. If true, subscribes body Information data
     * 
     * @param bodyInformation
     *            a boolean value
     */
    @Deprecated
    public void setBodyInformation(Boolean bodyInformation) {
        setVehicleData(bodyInformation, VehicleData.BODY_INFORMATION);
    }

    /**
     * Gets a boolean value. If true, means the body Information data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the body Information data
     *         has been subscribed.
     */
    @Deprecated
    public Boolean getBodyInformation() {
        return getVehicleData(VehicleData.BODY_INFORMATION);
    }

    /**
     * Sets a boolean value. If true, subscribes device Status data
     * 
     * @param deviceStatus
     *            a boolean value
     */
    @Deprecated
    public void setDeviceStatus(Boolean deviceStatus) {
        setVehicleData(deviceStatus, VehicleData.DEVICE_STATUS);
    }

    /**
     * Gets a boolean value. If true, means the device Status data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the device Status data
     *         has been subscribed.
     */
    @Deprecated
    public Boolean getDeviceStatus() {
        return getVehicleData(VehicleData.DEVICE_STATUS);
    }

    /**
     * Sets a boolean value. If true, subscribes driver Braking data
     * 
     * @param driverBraking
     *            a boolean value
     */
    @Deprecated
    public void setDriverBraking(Boolean driverBraking) {
        setVehicleData(driverBraking, VehicleData.DRIVER_BRAKING);
    }

    /**
     * Gets a boolean value. If true, means the driver Braking data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the driver Braking data
     *         has been subscribed.
     */
    @Deprecated
    public Boolean getDriverBraking() {
        return getVehicleData(VehicleData.DRIVER_BRAKING);
    }

    /**
     * Sets a boolean value. If true, subscribes wiper Status data
     * 
     * @param wiperStatus
     *            a boolean value
     */
    @Deprecated
    public void setWiperStatus(Boolean wiperStatus) {
        setVehicleData(wiperStatus, VehicleData.WIPER_STATUS);
    }

    /**
     * Gets a boolean value. If true, means the wiper Status data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the wiper Status data
     *         has been subscribed.
     */
    @Deprecated
    public Boolean getWiperStatus() {
        return getVehicleData(VehicleData.WIPER_STATUS);
    }

    /**
     * Sets a boolean value. If true, subscribes Head Lamp Status data
     * 
     * @param headLampStatus
     *            a boolean value
     */
    @Deprecated
    public void setHeadLampStatus(Boolean headLampStatus) {
        setVehicleData(headLampStatus, VehicleData.HEAD_LAMP_STATUS);
    }

    /**
     * Gets a boolean value. If true, means the Head Lamp Status data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the Head Lamp Status data
     *         has been subscribed.
     */
    @Deprecated
    public Boolean getHeadLampStatus() {
        return getVehicleData(VehicleData.HEAD_LAMP_STATUS);
    }

    /**
     * Sets a boolean value. If true, subscribes Engine Torque data
     * 
     * @param engineTorque
     *            a boolean value
     */
    @Deprecated
    public void setEngineTorque(Boolean engineTorque) {
        setVehicleData(engineTorque, VehicleData.ENGINE_TORQUE);
    }

    /**
     * Gets a boolean value. If true, means the Engine Torque data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the Engine Torque data
     *         has been subscribed.
     */
    @Deprecated
    public Boolean getEngineTorque() {
        return getVehicleData(VehicleData.ENGINE_TORQUE);
    }

    /**
     * Sets a boolean value. If true, subscribes accPedalPosition data
     * 
     * @param accPedalPosition
     *            a boolean value
     */
    @Deprecated
    public void setAccPedalPosition(Boolean accPedalPosition) {
        setVehicleData(accPedalPosition, VehicleData.ACC_PEDAL_POSITION);
    }

    /**
     * Gets a boolean value. If true, means the accPedalPosition data has been
     * subscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the accPedalPosition data
     *         has been subscribed.
     */
    @Deprecated
    public Boolean getAccPedalPosition() {
        return getVehicleData(VehicleData.ACC_PEDAL_POSITION);
    }

    @Deprecated
    public void setSteeringWheelAngle(Boolean steeringWheelAngle) {
        setVehicleData(steeringWheelAngle, VehicleData.STEERING_WHEEL_ANGLE);
    }

    @Deprecated
    public Boolean getSteeringWheelAngle() {
        return getVehicleData(VehicleData.STEERING_WHEEL_ANGLE);
    }    
    @Deprecated
    public void setECallInfo(Boolean eCallInfo) {
        setVehicleData(eCallInfo, VehicleData.E_CALL_INFO);
    }
    @Deprecated
    public Boolean getECallInfo() {
        return getVehicleData(VehicleData.E_CALL_INFO);
    }
    @Deprecated
    public void setAirbagStatus(Boolean airbagStatus) {
        setVehicleData(airbagStatus, VehicleData.AIRBAG_STATUS);
    }
    @Deprecated
    public Boolean getAirbagStatus() {
        return getVehicleData(VehicleData.AIRBAG_STATUS);
    }
    @Deprecated
    public void setEmergencyEvent(Boolean emergencyEvent) {
        setVehicleData(emergencyEvent, VehicleData.EMERGENCY_EVENT);
    }
    @Deprecated
    public Boolean getEmergencyEvent() {
        return getVehicleData(VehicleData.EMERGENCY_EVENT);
    }
    @Deprecated
    public void setClusterModeStatus(Boolean clusterModeStatus) {
        setVehicleData(clusterModeStatus, VehicleData.CLUSTER_MODE_STATUS);
    }
    @Deprecated
    public Boolean getClusterModeStatus() {
        return getVehicleData(VehicleData.CLUSTER_MODE_STATUS);
    }
    @Deprecated
    public void setMyKey(Boolean myKey) {
        setVehicleData(myKey, VehicleData.MY_KEY);
    }
    @Deprecated
    public Boolean getMyKey() {
        return getVehicleData(VehicleData.MY_KEY);
    }
}
