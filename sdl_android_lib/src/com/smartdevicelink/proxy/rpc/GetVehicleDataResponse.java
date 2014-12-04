package com.smartdevicelink.proxy.rpc;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.VehicleData;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.proxy.rpc.enums.WiperStatus;

public class GetVehicleDataResponse extends RPCResponse {

    public static final String KEY_VIN = "vin";

    public GetVehicleDataResponse() {
        super(FunctionID.GET_VEHICLE_DATA);
    }
    
    public GetVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public void setVehicleData(VehicleData data, Object obj){
        if(obj == null){
            this.parameters.remove(data.getJsonName());
        }
        else{
            this.parameters.put(data.getJsonName(), obj);
        }
    }
    
    @SuppressWarnings("unchecked")
    public Object getVehicleData(VehicleData data){
        Object result = parameters.get(data.getJsonName());
        Class<?> castType = data.getClassType();
        
        if(result instanceof Hashtable){
            // we have a hashtable, representing an object to return
            try{
                Constructor<?> constructor = castType.getConstructor(Hashtable.class);
                result = constructor.newInstance((Hashtable<String, Object>) result);
            } catch(Exception e){
                // exception means there is no Hashtable constructor. // TODO: should we return null here, or return the raw object?
                e.printStackTrace();
            }
        }
        else if( ! castType.equals(String.class) && result instanceof String){
            // we have a string represenging an enum object

            try{
                Method lookupMethod = castType.getMethod("valueForString", String.class);
                result = lookupMethod.invoke(null, (String) result);
            } catch(Exception e){
                // exception means there is no "valueForString" class defined in the enum.  // TODO: should we return null here, or the raw object?
                e.printStackTrace();
            }
        }
        
        return result;
    }
    
    @Deprecated
    public void setGps(GPSData gps) {
    	setVehicleData(VehicleData.GPS, gps);
    }
    
    @Deprecated
    public GPSData getGps() {
    	return (GPSData) getVehicleData(VehicleData.GPS);
    }
    
    @Deprecated
    public void setSpeed(Double speed) {
        setVehicleData(VehicleData.SPEED, speed);
    }
    
    @Deprecated
    public Double getSpeed() {
    	return (Double) getVehicleData(VehicleData.SPEED);
    }
    
    @Deprecated
    public void setRpm(Integer rpm) {
        setVehicleData(VehicleData.RPM, rpm);
    }
    
    @Deprecated
    public Integer getRpm() {
    	return (Integer) getVehicleData(VehicleData.RPM);
    }
    
    @Deprecated
    public void setFuelLevel(Double fuelLevel) {
        setVehicleData(VehicleData.FUEL_LEVEL, fuelLevel);
    }
    
    @Deprecated
    public Double getFuelLevel() {
    	return (Double) getVehicleData(VehicleData.FUEL_LEVEL);
    }
    
    @Deprecated
    public void setFuelLevel_State(ComponentVolumeStatus fuelLevel_State) {
        setVehicleData(VehicleData.FUEL_LEVEL_STATE, fuelLevel_State);
    }
    
    @Deprecated
    public ComponentVolumeStatus getFuelLevel_State() {
        return (ComponentVolumeStatus) getVehicleData(VehicleData.FUEL_LEVEL_STATE);
    }
    
    @Deprecated
    public void setInstantFuelConsumption(Double instantFuelConsumption) {
        setVehicleData(VehicleData.INSTANT_FUEL_CONSUMPTION, instantFuelConsumption);
    }
    
    @Deprecated
    public Double getInstantFuelConsumption() {
    	return (Double) getVehicleData(VehicleData.INSTANT_FUEL_CONSUMPTION);
    }
    
    @Deprecated
    public void setExternalTemperature(Double externalTemperature) {
        setVehicleData(VehicleData.EXTERNAL_TEMPERATURE, externalTemperature);
    }
    
    @Deprecated
    public Double getExternalTemperature() {
    	return (Double) getVehicleData(VehicleData.EXTERNAL_TEMPERATURE);
    }
    
    @Deprecated
    public void setVin(String vin) {
        if(vin == null){
            this.parameters.remove(KEY_VIN);
        }
        else{
            this.parameters.put(KEY_VIN, vin);
        }
    }
    
    @Deprecated
    public String getVin() {
    	return (String) this.parameters.get(KEY_VIN);
    }
    
    @Deprecated
    public void setPrndl(PRNDL prndl) {
        setVehicleData(VehicleData.PRNDL, prndl);
    }
    
    @Deprecated
    public PRNDL getPrndl() {
        return (PRNDL) getVehicleData(VehicleData.PRNDL);
    }
    
    @Deprecated
    public void setTirePressure(TireStatus tirePressure) {
        setVehicleData(VehicleData.TIRE_PRESSURE, tirePressure);
    }
    
    @Deprecated
    public TireStatus getTirePressure() {
    	return (TireStatus) getVehicleData(VehicleData.TIRE_PRESSURE);
    }
    
    @Deprecated
    public void setOdometer(Integer odometer) {
        setVehicleData(VehicleData.ODOMETER, odometer);
    }
    
    @Deprecated
    public Integer getOdometer() {
    	return (Integer) getVehicleData(VehicleData.ODOMETER);
    }
    
    @Deprecated
    public void setBeltStatus(BeltStatus beltStatus) {
        setVehicleData(VehicleData.BELT_STATUS, beltStatus);
    }
    
    @Deprecated
    public BeltStatus getBeltStatus() {
    	return (BeltStatus) getVehicleData(VehicleData.BELT_STATUS);
    }
    
    @Deprecated
    public void setBodyInformation(BodyInformation bodyInformation) {
        setVehicleData(VehicleData.BODY_INFORMATION, bodyInformation);
    }
    
    @Deprecated
    public BodyInformation getBodyInformation() {
    	return (BodyInformation) getVehicleData(VehicleData.BODY_INFORMATION);
    }
    
    @Deprecated
    public void setDeviceStatus(DeviceStatus deviceStatus) {
        setVehicleData(VehicleData.DEVICE_STATUS, deviceStatus);
    }
    
    @Deprecated
    public DeviceStatus getDeviceStatus() {
        return (DeviceStatus) getVehicleData(VehicleData.DEVICE_STATUS);
    }
    
    @Deprecated
    public void setDriverBraking(VehicleDataEventStatus driverBraking) {
        setVehicleData(VehicleData.DRIVER_BRAKING, driverBraking);
    }
    
    @Deprecated
    public VehicleDataEventStatus getDriverBraking() {
        return (VehicleDataEventStatus) getVehicleData(VehicleData.DRIVER_BRAKING);
    }
    
    @Deprecated
    public void setWiperStatus(WiperStatus wiperStatus) {
        setVehicleData(VehicleData.WIPER_STATUS, wiperStatus);
    }
    
    @Deprecated
    public WiperStatus getWiperStatus() {
        return (WiperStatus) getVehicleData(VehicleData.WIPER_STATUS);
    }
    
    @Deprecated
    public void setHeadLampStatus(HeadLampStatus headLampStatus) {
        setVehicleData(VehicleData.HEAD_LAMP_STATUS, headLampStatus);
    }
    
    @Deprecated
    public HeadLampStatus getHeadLampStatus() {
        return (HeadLampStatus) getVehicleData(VehicleData.HEAD_LAMP_STATUS);
    }
    
    @Deprecated
    public void setEngineTorque(Double engineTorque) {
        setVehicleData(VehicleData.ENGINE_TORQUE, engineTorque);
    }
    
    @Deprecated
    public Double getEngineTorque() {
    	return (Double) getVehicleData(VehicleData.ENGINE_TORQUE);
    }
    
    @Deprecated
    public void setAccPedalPosition(Double accPedalPosition) {
        setVehicleData(VehicleData.ACC_PEDAL_POSITION, accPedalPosition);
    }
    
    @Deprecated
    public Double getAccPedalPosition() {
    	return (Double) getVehicleData(VehicleData.ACC_PEDAL_POSITION);
    }
    
    @Deprecated
    public void setSteeringWheelAngle(Double steeringWheelAngle) {
        setVehicleData(VehicleData.STEERING_WHEEL_ANGLE, steeringWheelAngle);
    }
    
    @Deprecated
    public Double getSteeringWheelAngle() {
    	return (Double) getVehicleData(VehicleData.STEERING_WHEEL_ANGLE);
    }
    
    @Deprecated
    public void setECallInfo(ECallInfo eCallInfo) {
        setVehicleData(VehicleData.E_CALL_INFO, eCallInfo);
    }
    
    @Deprecated
    public ECallInfo getECallInfo() {
        return (ECallInfo) getVehicleData(VehicleData.E_CALL_INFO);
    }
    
    @Deprecated
    public void setAirbagStatus(AirbagStatus airbagStatus) {
        setVehicleData(VehicleData.AIRBAG_STATUS, airbagStatus);
    }
    
    @Deprecated
    public AirbagStatus getAirbagStatus() {
    	return (AirbagStatus) getVehicleData(VehicleData.AIRBAG_STATUS);
    }
    
    @Deprecated
    public void setEmergencyEvent(EmergencyEvent emergencyEvent) {
        setVehicleData(VehicleData.EMERGENCY_EVENT, emergencyEvent);
    }
    
    @Deprecated
    public EmergencyEvent getEmergencyEvent() {
        return (EmergencyEvent) getVehicleData(VehicleData.EMERGENCY_EVENT);
    }
    
    @Deprecated
    public void setClusterModeStatus(ClusterModeStatus clusterModeStatus) {
        setVehicleData(VehicleData.CLUSTER_MODE_STATUS, clusterModeStatus);
    }
    
    @Deprecated
    public ClusterModeStatus getClusterModeStatus() {
        return (ClusterModeStatus) getVehicleData(VehicleData.CLUSTER_MODE_STATUS);
    }
    
    @Deprecated
    public void setMyKey(MyKey myKey) {
        setVehicleData(VehicleData.MY_KEY, myKey);
    }
    
    @Deprecated
    public MyKey getMyKey() {
    	return (MyKey) getVehicleData(VehicleData.MY_KEY);
    }        
}
