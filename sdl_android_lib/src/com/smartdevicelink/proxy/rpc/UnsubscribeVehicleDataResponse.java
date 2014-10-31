package com.smartdevicelink.proxy.rpc;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.VehicleData;

/**
 * Unsubscribe Vehicle Data Response is sent, when UnsubscribeVehicleData has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class UnsubscribeVehicleDataResponse extends RPCResponse {

	/**
	 * Constructs a new UnsubscribeVehicleDataResponse object
	 */
    public UnsubscribeVehicleDataResponse() {
        super(FunctionID.UNSUBSCRIBE_VEHICLE_DATA);
    }

	/**
	 * Constructs a new UnsubscribeVehicleDataResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public UnsubscribeVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public void setVehicleData(VehicleData key, VehicleDataResult value){
        if(value == null){
            parameters.remove(key.getJsonName());
        }
        else{
            parameters.put(key.getJsonName(), value);
        }
    }
    
    public void setVehicleData(Map<VehicleData, VehicleDataResult> valueMap){
        for(Map.Entry<VehicleData, VehicleDataResult> entry : valueMap.entrySet()){
            setVehicleData(entry.getKey(), entry.getValue());
        }
    }
    
    @SuppressWarnings("unchecked")
    public VehicleDataResult getVehicleData(VehicleData key){
        Object obj = parameters.get(key.getJsonName());
        if(obj instanceof VehicleDataResult){
            return (VehicleDataResult) obj;
        }
        else if(obj instanceof Hashtable){
            return new VehicleDataResult((Hashtable<String, Object>) obj);
        }
        return null;
    }
    
    public Map<VehicleData, VehicleDataResult> getVehicleData(VehicleData... keys){
        Map<VehicleData, VehicleDataResult> result = new HashMap<VehicleData, VehicleDataResult>();
        
        for(VehicleData data : keys){
            VehicleDataResult temp = getVehicleData(data);
            result.put(data, temp);
        }
        
        return result;
    }
    
    /**
     * Sets gps
     * @param gps
     */
    @Deprecated
    public void setGps(VehicleDataResult gps) {
        setVehicleData(VehicleData.GPS, gps);
    }
    /**
     * Gets gps
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getGps() {
        return getVehicleData(VehicleData.GPS);
    }
    /**
     * Sets speed
     * @param speed
     */
    @Deprecated
    public void setSpeed(VehicleDataResult speed) {
        setVehicleData(VehicleData.SPEED, speed);
    }
    /**
     * Gets speed
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getSpeed() {
        return getVehicleData(VehicleData.SPEED);
    }
    /**
     * Sets rpm
     * @param rpm
     */
    @Deprecated
    public void setRpm(VehicleDataResult rpm) {
        setVehicleData(VehicleData.RPM, rpm);
    }
    /**
     * Gets rpm
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getRpm() {
        return getVehicleData(VehicleData.RPM);
    }
    /**
     * Sets Fuel Level
     * @param fuelLevel
     */
    @Deprecated
    public void setFuelLevel(VehicleDataResult fuelLevel) {
        setVehicleData(VehicleData.FUEL_LEVEL, fuelLevel);
    }
    /**
     * Gets Fuel Level
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getFuelLevel() {
        return getVehicleData(VehicleData.FUEL_LEVEL);
    }
    /**
     * Sets Fuel Level State
     * @param fuelLevel_State
     */
    @Deprecated
    public void setFuelLevel_State(VehicleDataResult fuelLevel_State) {
        setVehicleData(VehicleData.FUEL_LEVEL_STATE, fuelLevel_State);
    }
    /**
     * Gets Fuel Level State
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getFuelLevel_State() {
        return getVehicleData(VehicleData.FUEL_LEVEL_STATE);
    }
    /**
     * Sets Instant Fuel Comsumption
     * @param instantFuelConsumption
     */
    @Deprecated
    public void setInstantFuelConsumption(VehicleDataResult instantFuelConsumption) {
        setVehicleData(VehicleData.INSTANT_FUEL_CONSUMPTION, instantFuelConsumption);
    }
    /**
     * Gets Instant Fuel Consumption
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getInstantFuelConsumption() {
        return getVehicleData(VehicleData.INSTANT_FUEL_CONSUMPTION);
    }
    /**
     * Sets External Temperature
     * @param externalTemperature
     */
    @Deprecated
    public void setExternalTemperature(VehicleDataResult externalTemperature) {
        setVehicleData(VehicleData.EXTERNAL_TEMPERATURE, externalTemperature);
    }
    /**
     * Gets External Temperature
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getExternalTemperature() {
        return getVehicleData(VehicleData.EXTERNAL_TEMPERATURE);
    }
    /**
     * Sets currently selected gear data
     * @param prndl
     */
    @Deprecated
    public void setPrndl(VehicleDataResult prndl) {
        setVehicleData(VehicleData.PRNDL, prndl);
    }
    /**
     * Gets currently selected gear data
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getPrndl() {
        return getVehicleData(VehicleData.PRNDL);
    }
    /**
     * Sets Tire Pressure
     * @param tirePressure
     */
    @Deprecated
    public void setTirePressure(VehicleDataResult tirePressure) {
        setVehicleData(VehicleData.TIRE_PRESSURE, tirePressure);
    }
    /**
     * Gets Tire Pressure
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getTirePressure() {
        return getVehicleData(VehicleData.TIRE_PRESSURE);
    }
    /**
     * Sets Odometer
     * @param odometer
     */
    @Deprecated
    public void setOdometer(VehicleDataResult odometer) {
        setVehicleData(VehicleData.ODOMETER, odometer);
    }
    /**
     * Gets Odometer
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getOdometer() {
        return getVehicleData(VehicleData.ODOMETER);
    }
    /**
     * Sets Belt Status
     * @param beltStatus
     */
    @Deprecated
    public void setBeltStatus(VehicleDataResult beltStatus) {
        setVehicleData(VehicleData.BELT_STATUS, beltStatus);
    }
    /**
     * Gets Belt Status
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getBeltStatus() {
        return getVehicleData(VehicleData.BELT_STATUS);
    }
    /**
     * Sets Body Information
     * @param bodyInformation
     */
    @Deprecated
    public void setBodyInformation(VehicleDataResult bodyInformation) {
        setVehicleData(VehicleData.BODY_INFORMATION, bodyInformation);
    }
    /**
     * Gets Body Information
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getBodyInformation() {
        return getVehicleData(VehicleData.BODY_INFORMATION);
    }
    /**
     * Sets Device Status
     * @param deviceStatus
     */
    @Deprecated
    public void setDeviceStatus(VehicleDataResult deviceStatus) {
        setVehicleData(VehicleData.DEVICE_STATUS, deviceStatus);
    }
    /**
     * Gets Device Status
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getDeviceStatus() {
        return getVehicleData(VehicleData.DEVICE_STATUS);
    }
    /**
     * Sets Driver Barking
     * @param driverBraking
     */
    @Deprecated
    public void setDriverBraking(VehicleDataResult driverBraking) {
        setVehicleData(VehicleData.DRIVER_BRAKING, driverBraking);
    }
    /**
     * Gets Driver Barking
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getDriverBraking() {
        return getVehicleData(VehicleData.DRIVER_BRAKING);
    }
    /**
     * Sets wiper Status
     * @param wiperStatus
     */
    @Deprecated
    public void setWiperStatus(VehicleDataResult wiperStatus) {
        setVehicleData(VehicleData.WIPER_STATUS, wiperStatus);
    }
    /**
     * Gets Wiper Status
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getWiperStatus() {
        return getVehicleData(VehicleData.WIPER_STATUS);
    }
    /**
     * Sets Head Lamp Status
     * @param headLampStatus
     */
    @Deprecated
    public void setHeadLampStatus(VehicleDataResult headLampStatus) {
        setVehicleData(VehicleData.HEAD_LAMP_STATUS, headLampStatus);
    }
    /**
     * Gets Head Lamp Status
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getHeadLampStatus() {
        return getVehicleData(VehicleData.HEAD_LAMP_STATUS);
    }
    /**
     * Sets Engine Torque
     * @param engineTorque
     */
    @Deprecated
    public void setEngineTorque(VehicleDataResult engineTorque) {
        setVehicleData(VehicleData.ENGINE_TORQUE, engineTorque);
    }
    /**
     * Gets Engine Torque
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getEngineTorque() {
        return getVehicleData(VehicleData.ENGINE_TORQUE);
    }
    /**
     * Sets AccPedal Position
     * @param accPedalPosition
     */
    @Deprecated
    public void setAccPedalPosition(VehicleDataResult accPedalPosition) {
        setVehicleData(VehicleData.ACC_PEDAL_POSITION, accPedalPosition);
    }
    /**
     * Gets AccPedal Position
     * @return VehicleDataResult 
     */
    @Deprecated
    public VehicleDataResult getAccPedalPosition() {
        return getVehicleData(VehicleData.ACC_PEDAL_POSITION);
    }

    @Deprecated
    public void setSteeringWheelAngle(VehicleDataResult steeringWheelAngle) {
        setVehicleData(VehicleData.STEERING_WHEEL_ANGLE, steeringWheelAngle);
    }

    @Deprecated
    public VehicleDataResult getSteeringWheelAngle() {
        return getVehicleData(VehicleData.STEERING_WHEEL_ANGLE);
    }

    @Deprecated
    public void setECallInfo(VehicleDataResult eCallInfo) {
        setVehicleData(VehicleData.E_CALL_INFO, eCallInfo);
    }
    @Deprecated
    public VehicleDataResult getECallInfo() {
        return getVehicleData(VehicleData.E_CALL_INFO);
    }
    @Deprecated
    public void setAirbagStatus(VehicleDataResult airbagStatus) {
        setVehicleData(VehicleData.AIRBAG_STATUS, airbagStatus);
    }
    @Deprecated
    public VehicleDataResult getAirbagStatus() {
        return getVehicleData(VehicleData.AIRBAG_STATUS);
    }
    @Deprecated
    public void setEmergencyEvent(VehicleDataResult emergencyEvent) {
        setVehicleData(VehicleData.EMERGENCY_EVENT, emergencyEvent);
    }
    @Deprecated
    public VehicleDataResult getEmergencyEvent() {
        return getVehicleData(VehicleData.EMERGENCY_EVENT);
    }
    @Deprecated
    public void setClusterModeStatus(VehicleDataResult clusterModeStatus) {
        setVehicleData(VehicleData.CLUSTER_MODE_STATUS, clusterModeStatus);
    }
    @Deprecated
    public VehicleDataResult getClusterModeStatus() {
        return getVehicleData(VehicleData.CLUSTER_MODE_STATUS);
    }
    @Deprecated
    public void setMyKey(VehicleDataResult myKey) {
        setVehicleData(VehicleData.MY_KEY, myKey);
    }
    @Deprecated
    public VehicleDataResult getMyKey() {
        return getVehicleData(VehicleData.MY_KEY);
    }       
}
