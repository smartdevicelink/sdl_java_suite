package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCRequest;

public class GetVehicleData extends RPCRequest {
	public static final String speed = "speed";
	public static final String rpm = "rpm";
	public static final String externalTemperature = "externalTemperature";
	public static final String fuelLevel = "fuelLevel";
	public static final String vin = "vin";
	public static final String prndl = "prndl";
	public static final String tirePressure = "tirePressure";
	public static final String engineTorque = "engineTorque";
	public static final String odometer = "odometer";
	public static final String gps = "gps";
	public static final String fuelLevel_State = "fuelLevel_State";
	public static final String instantFuelConsumption = "instantFuelConsumption";
	public static final String beltStatus = "beltStatus";
	public static final String bodyInformation = "bodyInformation";
	public static final String deviceStatus = "deviceStatus";
	public static final String driverBraking = "driverBraking";
	public static final String wiperStatus = "wiperStatus";
	public static final String headLampStatus = "headLampStatus";
	public static final String accPedalPosition = "accPedalPosition";
	public static final String steeringWheelAngle = "steeringWheelAngle";
	public static final String eCallInfo = "eCallInfo";
	public static final String airbagStatus = "airbagStatus";
	public static final String emergencyEvent = "emergencyEvent";
	public static final String clusterModeStatus = "clusterModeStatus";
	public static final String myKey = "myKey";

    public GetVehicleData() {
        super("GetVehicleData");
    }
    public GetVehicleData(Hashtable hash) {
        super(hash);
    }
    public void setGps(Boolean gps) {
        if (gps != null) {
            parameters.put(GetVehicleData.gps, gps);
        } else {
        	parameters.remove(GetVehicleData.gps);
        }
    }
    public Boolean getGps() {
        return (Boolean) parameters.get(GetVehicleData.gps);
    }
    public void setSpeed(Boolean speed) {
        if (speed != null) {
            parameters.put(GetVehicleData.speed, speed);
        } else {
        	parameters.remove(GetVehicleData.speed);
        }
    }
    public Boolean getSpeed() {
        return (Boolean) parameters.get(GetVehicleData.speed);
    }
    public void setRpm(Boolean rpm) {
        if (rpm != null) {
            parameters.put(GetVehicleData.rpm, rpm);
        } else {
        	parameters.remove(GetVehicleData.rpm);
        }
    }
    public Boolean getRpm() {
        return (Boolean) parameters.get(GetVehicleData.rpm);
    }
    public void setFuelLevel(Boolean fuelLevel) {
        if (fuelLevel != null) {
            parameters.put(GetVehicleData.fuelLevel, fuelLevel);
        } else {
        	parameters.remove(GetVehicleData.fuelLevel);
        }
    }
    public Boolean getFuelLevel() {
        return (Boolean) parameters.get(GetVehicleData.fuelLevel);
    }
    public void setFuelLevel_State(Boolean fuelLevel_State) {
        if (fuelLevel_State != null) {
            parameters.put(GetVehicleData.fuelLevel_State, fuelLevel_State);
        } else {
        	parameters.remove(GetVehicleData.fuelLevel_State);
        }
    }
    public Boolean getFuelLevel_State() {
        return (Boolean) parameters.get(GetVehicleData.fuelLevel_State);
    }
    public void setInstantFuelConsumption(Boolean instantFuelConsumption) {
        if (instantFuelConsumption != null) {
            parameters.put(GetVehicleData.instantFuelConsumption, instantFuelConsumption);
        } else {
        	parameters.remove(GetVehicleData.instantFuelConsumption);
        }
    }
    public Boolean getInstantFuelConsumption() {
        return (Boolean) parameters.get(GetVehicleData.instantFuelConsumption);
    }
    public void setExternalTemperature(Boolean externalTemperature) {
        if (externalTemperature != null) {
            parameters.put(GetVehicleData.externalTemperature, externalTemperature);
        } else {
        	parameters.remove(GetVehicleData.externalTemperature);
        }
    }
    public Boolean getExternalTemperature() {
        return (Boolean) parameters.get(GetVehicleData.externalTemperature);
    }
    
    public void setVin(Boolean vin) {
        if (vin != null) {
            parameters.put(GetVehicleData.vin, vin);
        } else {
        	parameters.remove(GetVehicleData.vin);
        }
    }
    public Boolean getVin() {
        return (Boolean) parameters.get(GetVehicleData.vin);
    }
    
    public void setPrndl(Boolean prndl) {
        if (prndl != null) {
            parameters.put(GetVehicleData.prndl, prndl);
        } else {
        	parameters.remove(GetVehicleData.prndl);
        }
    }
    public Boolean getPrndl() {
        return (Boolean) parameters.get(GetVehicleData.prndl);
    }
    public void setTirePressure(Boolean tirePressure) {
        if (tirePressure != null) {
            parameters.put(GetVehicleData.tirePressure, tirePressure);
        } else {
        	parameters.remove(GetVehicleData.tirePressure);
        }
    }
    public Boolean getTirePressure() {
        return (Boolean) parameters.get(GetVehicleData.tirePressure);
    }
    public void setOdometer(Boolean odometer) {
        if (odometer != null) {
            parameters.put(GetVehicleData.odometer, odometer);
        } else {
        	parameters.remove(GetVehicleData.odometer);
        }
    }
    public Boolean getOdometer() {
        return (Boolean) parameters.get(GetVehicleData.odometer);
    }
    public void setBeltStatus(Boolean beltStatus) {
        if (beltStatus != null) {
            parameters.put(GetVehicleData.beltStatus, beltStatus);
        } else {
        	parameters.remove(GetVehicleData.beltStatus);
        }
    }
    public Boolean getBeltStatus() {
        return (Boolean) parameters.get(GetVehicleData.beltStatus);
    }
    public void setBodyInformation(Boolean bodyInformation) {
        if (bodyInformation != null) {
            parameters.put(GetVehicleData.bodyInformation, bodyInformation);
        } else {
        	parameters.remove(GetVehicleData.bodyInformation);
        }
    }
    public Boolean getBodyInformation() {
        return (Boolean) parameters.get(GetVehicleData.bodyInformation);
    }
    public void setDeviceStatus(Boolean deviceStatus) {
        if (deviceStatus != null) {
            parameters.put(GetVehicleData.deviceStatus, deviceStatus);
        } else {
        	parameters.remove(GetVehicleData.deviceStatus);
        }
    }
    public Boolean getDeviceStatus() {
        return (Boolean) parameters.get(GetVehicleData.deviceStatus);
    }
    public void setDriverBraking(Boolean driverBraking) {
        if (driverBraking != null) {
            parameters.put(GetVehicleData.driverBraking, driverBraking);
        } else {
        	parameters.remove(GetVehicleData.driverBraking);
        }
    }
    public Boolean getDriverBraking() {
        return (Boolean) parameters.get(GetVehicleData.driverBraking);
    }
    public void setWiperStatus(Boolean wiperStatus) {
        if (wiperStatus != null) {
            parameters.put(GetVehicleData.wiperStatus, wiperStatus);
        } else {
        	parameters.remove(GetVehicleData.wiperStatus);
        }
    }
    public Boolean getWiperStatus() {
        return (Boolean) parameters.get(GetVehicleData.wiperStatus);
    }
    public void setHeadLampStatus(Boolean headLampStatus) {
        if (headLampStatus != null) {
            parameters.put(GetVehicleData.headLampStatus, headLampStatus);
        } else {
        	parameters.remove(GetVehicleData.headLampStatus);
        }
    }
    public Boolean getHeadLampStatus() {
        return (Boolean) parameters.get(GetVehicleData.headLampStatus);
    }
    public void setEngineTorque(Boolean engineTorque) {
        if (engineTorque != null) {
            parameters.put(GetVehicleData.engineTorque, engineTorque);
        } else {
        	parameters.remove(GetVehicleData.engineTorque);
        }
    }
    public Boolean getEngineTorque() {
        return (Boolean) parameters.get(GetVehicleData.engineTorque);
    }
    public void setAccPedalPosition(Boolean accPedalPosition) {
        if (accPedalPosition != null) {
            parameters.put(GetVehicleData.accPedalPosition, accPedalPosition);
        } else {
        	parameters.remove(GetVehicleData.accPedalPosition);
        }
    }
    public Boolean getAccPedalPosition() {
        return (Boolean) parameters.get(GetVehicleData.accPedalPosition);
    }
        
    public void setSteeringWheelAngle(Boolean steeringWheelAngle) {
        if (steeringWheelAngle != null) {
            parameters.put(GetVehicleData.steeringWheelAngle, steeringWheelAngle);
        } else {
        	parameters.remove(GetVehicleData.steeringWheelAngle);
        }
    }
    public Boolean getSteeringWheelAngle() {
        return (Boolean) parameters.get(GetVehicleData.steeringWheelAngle);
    }                
    public void setECallInfo(Boolean eCallInfo) {
        if (eCallInfo != null) {
            parameters.put(GetVehicleData.eCallInfo, eCallInfo);
        } else {
        	parameters.remove(GetVehicleData.eCallInfo);
        }
    }
    public Boolean getECallInfo() {
        return (Boolean) parameters.get(GetVehicleData.eCallInfo);
    }    
    
    
    public void setAirbagStatus(Boolean airbagStatus) {
        if (airbagStatus != null) {
            parameters.put(GetVehicleData.airbagStatus, airbagStatus);
        } else {
        	parameters.remove(GetVehicleData.airbagStatus);
        }
    }
    public Boolean getAirbagStatus() {
        return (Boolean) parameters.get(GetVehicleData.airbagStatus);
    }
    public void setEmergencyEvent(Boolean emergencyEvent) {
        if (emergencyEvent != null) {
            parameters.put(GetVehicleData.emergencyEvent, emergencyEvent);
        } else {
        	parameters.remove(GetVehicleData.emergencyEvent);
        }
    }
    public Boolean getEmergencyEvent() {
        return (Boolean) parameters.get(GetVehicleData.emergencyEvent);
    }
    public void setClusterModeStatus(Boolean clusterModeStatus) {
        if (clusterModeStatus != null) {
            parameters.put(GetVehicleData.clusterModeStatus, clusterModeStatus);
        } else {
        	parameters.remove(GetVehicleData.clusterModeStatus);
        }
    }
    public Boolean getClusterModeStatus() {
        return (Boolean) parameters.get(GetVehicleData.clusterModeStatus);
    }
    public void setMyKey(Boolean myKey) {
        if (myKey != null) {
            parameters.put(GetVehicleData.myKey, myKey);
        } else {
        	parameters.remove(GetVehicleData.myKey);
        }
    }
    public Boolean getMyKey() {
        return (Boolean) parameters.get(GetVehicleData.myKey);
    }        
}
