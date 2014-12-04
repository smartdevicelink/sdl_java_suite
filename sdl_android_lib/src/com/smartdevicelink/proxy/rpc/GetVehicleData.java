package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.VehicleData;

public class GetVehicleData extends RPCRequest {
    
    public GetVehicleData() {
        super(FunctionID.GET_VEHICLE_DATA);
    }
    
    public GetVehicleData(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public Boolean getVehicleData(VehicleData data){
        return (Boolean) parameters.get(data.getJsonName());
    }
    
    public void setVehicleData(Boolean toSet, VehicleData... vehicleData){
        for(VehicleData data : vehicleData){
            if(toSet != null){
                parameters.put(data.getJsonName(), toSet);
            }
            else{
                parameters.remove(data.getJsonName());
            }
        }
    }
    
    @Deprecated
    public void setGps(Boolean gps) {
        setVehicleData(gps, VehicleData.GPS);
    }
    
    @Deprecated
    public Boolean getGps() {
        return getVehicleData(VehicleData.GPS);
    }
    
    @Deprecated
    public void setSpeed(Boolean speed) {
        setVehicleData(speed, VehicleData.SPEED);
    }
    
    @Deprecated
    public Boolean getSpeed() {
        return getVehicleData(VehicleData.SPEED);
    }
    
    @Deprecated
    public void setRpm(Boolean rpm) {
        setVehicleData(rpm, VehicleData.RPM);
    }
    
    @Deprecated
    public Boolean getRpm() {
        return getVehicleData(VehicleData.RPM);
    }
    
    @Deprecated
    public void setFuelLevel(Boolean fuelLevel) {
        setVehicleData(fuelLevel, VehicleData.FUEL_LEVEL);
    }
    
    @Deprecated
    public Boolean getFuelLevel() {
        return getVehicleData(VehicleData.FUEL_LEVEL);
    }
    
    @Deprecated
    public void setFuelLevel_State(Boolean fuelLevel_State) {
        setVehicleData(fuelLevel_State, VehicleData.FUEL_LEVEL_STATE);
    }
    
    @Deprecated
    public Boolean getFuelLevel_State() {
        return getVehicleData(VehicleData.FUEL_LEVEL_STATE);
    }
    
    @Deprecated
    public void setInstantFuelConsumption(Boolean instantFuelConsumption) {
        setVehicleData(instantFuelConsumption, VehicleData.INSTANT_FUEL_CONSUMPTION);
    }
    
    @Deprecated
    public Boolean getInstantFuelConsumption() {
        return getVehicleData(VehicleData.INSTANT_FUEL_CONSUMPTION);
    }
    
    @Deprecated
    public void setExternalTemperature(Boolean externalTemperature) {
        setVehicleData(externalTemperature, VehicleData.EXTERNAL_TEMPERATURE);
    }
    
    @Deprecated
    public Boolean getExternalTemperature() {
        return getVehicleData(VehicleData.EXTERNAL_TEMPERATURE);
    }
    
    @Deprecated
    public void setVin(Boolean vin) {
        setVehicleData(vin, VehicleData.VIN);
    }
    
    @Deprecated
    public Boolean getVin() {
        return getVehicleData(VehicleData.VIN);
    }
    
    @Deprecated
    public void setPrndl(Boolean prndl) {
        setVehicleData(prndl, VehicleData.PRNDL);
    }
    
    @Deprecated
    public Boolean getPrndl() {
        return getVehicleData(VehicleData.PRNDL);
    }
    
    @Deprecated
    public void setTirePressure(Boolean tirePressure) {
        setVehicleData(tirePressure, VehicleData.TIRE_PRESSURE);
    }
    
    @Deprecated
    public Boolean getTirePressure() {
        return getVehicleData(VehicleData.TIRE_PRESSURE);
    }
    
    @Deprecated
    public void setOdometer(Boolean odometer) {
        setVehicleData(odometer, VehicleData.ODOMETER);
    }
    
    @Deprecated
    public Boolean getOdometer() {
        return getVehicleData(VehicleData.ODOMETER);
    }
    
    @Deprecated
    public void setBeltStatus(Boolean beltStatus) {
        setVehicleData(beltStatus, VehicleData.BELT_STATUS);
    }
    
    @Deprecated
    public Boolean getBeltStatus() {
        return getVehicleData(VehicleData.BELT_STATUS);
    }
    
    @Deprecated
    public void setBodyInformation(Boolean bodyInformation) {
        setVehicleData(bodyInformation, VehicleData.BODY_INFORMATION);
    }
    
    @Deprecated
    public Boolean getBodyInformation() {
        return getVehicleData(VehicleData.BODY_INFORMATION);
    }
    
    @Deprecated
    public void setDeviceStatus(Boolean deviceStatus) {
        setVehicleData(deviceStatus, VehicleData.DEVICE_STATUS);
    }
    
    @Deprecated
    public Boolean getDeviceStatus() {
        return getVehicleData(VehicleData.DEVICE_STATUS);
    }
    
    @Deprecated
    public void setDriverBraking(Boolean driverBraking) {
        setVehicleData(driverBraking, VehicleData.DRIVER_BRAKING);
    }
    
    @Deprecated
    public Boolean getDriverBraking() {
        return getVehicleData(VehicleData.DRIVER_BRAKING);
    }
    
    @Deprecated
    public void setWiperStatus(Boolean wiperStatus) {
        setVehicleData(wiperStatus, VehicleData.WIPER_STATUS);
    }
    
    @Deprecated
    public Boolean getWiperStatus() {
        return getVehicleData(VehicleData.WIPER_STATUS);
    }
    
    @Deprecated
    public void setHeadLampStatus(Boolean headLampStatus) {
        setVehicleData(headLampStatus, VehicleData.HEAD_LAMP_STATUS);
    }
    
    @Deprecated
    public Boolean getHeadLampStatus() {
        return getVehicleData(VehicleData.HEAD_LAMP_STATUS);
    }
    
    @Deprecated
    public void setEngineTorque(Boolean engineTorque) {
        setVehicleData(engineTorque, VehicleData.ENGINE_TORQUE);
    }
    
    @Deprecated
    public Boolean getEngineTorque() {
        return getVehicleData(VehicleData.ENGINE_TORQUE);
    }
    
    @Deprecated
    public void setAccPedalPosition(Boolean accPedalPosition) {
        setVehicleData(accPedalPosition, VehicleData.ACC_PEDAL_POSITION);
    }
    
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
