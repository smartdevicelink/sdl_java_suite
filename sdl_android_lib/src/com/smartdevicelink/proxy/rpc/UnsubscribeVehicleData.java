package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCRequest;

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
	public static final String speed = "speed";
	public static final String rpm = "rpm";
	public static final String externalTemperature = "externalTemperature";
	public static final String fuelLevel = "fuelLevel";
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
	

	/**
	 * Constructs a new UnsubscribeVehicleData object
	 */
    public UnsubscribeVehicleData() {
        super("UnsubscribeVehicleData");
    }

	/**
	 * Constructs a new UnsubscribeVehicleData object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public UnsubscribeVehicleData(Hashtable hash) {
        super(hash);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes Gps data
	 * 
	 * @param gps
	 *            a boolean value
	 */
    public void setGps(Boolean gps) {
        if (gps != null) {
            parameters.put(UnsubscribeVehicleData.gps, gps);
        } else {
        	parameters.remove(UnsubscribeVehicleData.gps);
        }
    }

	/**
	 * Gets a boolean value. If true, means the Gps data has been unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Gps data has been
	 *         unsubscribed.
	 */
    public Boolean getGps() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.gps);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes speed data
	 * 
	 * @param speed
	 *            a boolean value
	 */
    public void setSpeed(Boolean speed) {
        if (speed != null) {
            parameters.put(UnsubscribeVehicleData.speed, speed);
        } else {
        	parameters.remove(UnsubscribeVehicleData.speed);
        }
    }

	/**
	 * Gets a boolean value. If true, means the Speed data has been unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Speed data has been
	 *         unsubscribed.
	 */
    public Boolean getSpeed() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.speed);
    }

	/**
	 * Sets a boolean value. If true, unsubscribe data
	 * 
	 * @param rpm
	 *            a boolean value
	 */
    public void setRpm(Boolean rpm) {
        if (rpm != null) {
            parameters.put(UnsubscribeVehicleData.rpm, rpm);
        } else {
        	parameters.remove(UnsubscribeVehicleData.rpm);
        }
    }

	/**
	 * Gets a boolean value. If true, means the rpm data has been unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the rpm data has been
	 *         unsubscribed.
	 */
    public Boolean getRpm() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.rpm);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes FuelLevel data
	 * 
	 * @param fuelLevel
	 *            a boolean value
	 */
    public void setFuelLevel(Boolean fuelLevel) {
        if (fuelLevel != null) {
            parameters.put(UnsubscribeVehicleData.fuelLevel, fuelLevel);
        } else {
        	parameters.remove(UnsubscribeVehicleData.fuelLevel);
        }
    }

	/**
	 * Gets a boolean value. If true, means the FuelLevel data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the FuelLevel data has
	 *         been unsubscribed.
	 */
    public Boolean getFuelLevel() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.fuelLevel);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes fuelLevel_State data
	 * 
	 * @param fuelLevel_State
	 *            a boolean value
	 */
    public void setFuelLevel_State(Boolean fuelLevel_State) {
        if (fuelLevel_State != null) {
            parameters.put(UnsubscribeVehicleData.fuelLevel_State, fuelLevel_State);
        } else {
        	parameters.remove(UnsubscribeVehicleData.fuelLevel_State);
        }
    }

	/**
	 * Gets a boolean value. If true, means the fuelLevel_State data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the fuelLevel_State data
	 *         has been unsubscribed.
	 */
    public Boolean getFuelLevel_State() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.fuelLevel_State);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes instantFuelConsumption data
	 * 
	 * @param instantFuelConsumption
	 *            a boolean value
	 */
    public void setInstantFuelConsumption(Boolean instantFuelConsumption) {
        if (instantFuelConsumption != null) {
            parameters.put(UnsubscribeVehicleData.instantFuelConsumption, instantFuelConsumption);
        } else {
        	parameters.remove(UnsubscribeVehicleData.instantFuelConsumption);
        }
    }

	/**
	 * Gets a boolean value. If true, means the getInstantFuelConsumption data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the getInstantFuelConsumption data
	 *         has been unsubscribed.
	 */
    public Boolean getInstantFuelConsumption() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.instantFuelConsumption);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes externalTemperature data
	 * 
	 * @param externalTemperature
	 *            a boolean value
	 */
    public void setExternalTemperature(Boolean externalTemperature) {
        if (externalTemperature != null) {
            parameters.put(UnsubscribeVehicleData.externalTemperature, externalTemperature);
        } else {
        	parameters.remove(UnsubscribeVehicleData.externalTemperature);
        }
    }

	/**
	 * Gets a boolean value. If true, means the externalTemperature data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the externalTemperature data
	 *         has been unsubscribed.
	 */
    public Boolean getExternalTemperature() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.externalTemperature);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes Currently selected gear data
	 * 
	 * @param prndl
	 *            a boolean value
	 */
    public void setPrndl(Boolean prndl) {
        if (prndl != null) {
            parameters.put(UnsubscribeVehicleData.prndl, prndl);
        } else {
        	parameters.remove(UnsubscribeVehicleData.prndl);
        }
    }

	/**
	 * Gets a boolean value. If true, means the Currently selected gear data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Currently selected gear data
	 *         has been unsubscribed.
	 */
    public Boolean getPrndl() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.prndl);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes tire pressure status data
	 * 
	 * @param tirePressure
	 *            a boolean value
	 */
    public void setTirePressure(Boolean tirePressure) {
        if (tirePressure != null) {
            parameters.put(UnsubscribeVehicleData.tirePressure, tirePressure);
        } else {
        	parameters.remove(UnsubscribeVehicleData.tirePressure);
        }
    }

	/**
	 * Gets a boolean value. If true, means the tire pressure status data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the tire pressure status data
	 *         has been unsubscribed.
	 */
    public Boolean getTirePressure() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.tirePressure);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes odometer data
	 * 
	 * @param odometer
	 *            a boolean value
	 */
    public void setOdometer(Boolean odometer) {
        if (odometer != null) {
            parameters.put(UnsubscribeVehicleData.odometer, odometer);
        } else {
        	parameters.remove(UnsubscribeVehicleData.odometer);
        }
    }

	/**
	 * Gets a boolean value. If true, means the odometer data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the odometer data
	 *         has been unsubscribed.
	 */
    public Boolean getOdometer() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.odometer);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes belt Status data
	 * 
	 * @param beltStatus
	 *            a boolean value
	 */
    public void setBeltStatus(Boolean beltStatus) {
        if (beltStatus != null) {
            parameters.put(UnsubscribeVehicleData.beltStatus, beltStatus);
        } else {
        	parameters.remove(UnsubscribeVehicleData.beltStatus);
        }
    }

	/**
	 * Gets a boolean value. If true, means the belt Status data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the belt Status data
	 *         has been unsubscribed.
	 */
    public Boolean getBeltStatus() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.beltStatus);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes body Information data
	 * 
	 * @param bodyInformation
	 *            a boolean value
	 */
    public void setBodyInformation(Boolean bodyInformation) {
        if (bodyInformation != null) {
            parameters.put(UnsubscribeVehicleData.bodyInformation, bodyInformation);
        } else {
        	parameters.remove(UnsubscribeVehicleData.bodyInformation);
        }
    }

	/**
	 * Gets a boolean value. If true, means the body Information data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the body Information data
	 *         has been unsubscribed.
	 */
    public Boolean getBodyInformation() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.bodyInformation);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes device Status data
	 * 
	 * @param deviceStatus
	 *            a boolean value
	 */
    public void setDeviceStatus(Boolean deviceStatus) {
        if (deviceStatus != null) {
            parameters.put(UnsubscribeVehicleData.deviceStatus, deviceStatus);
        } else {
        	parameters.remove(UnsubscribeVehicleData.deviceStatus);
        }
    }

	/**
	 * Gets a boolean value. If true, means the device Status data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the device Status data
	 *         has been unsubscribed.
	 */
    public Boolean getDeviceStatus() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.deviceStatus);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes driver Braking data
	 * 
	 * @param driverBraking
	 *            a boolean value
	 */
    public void setDriverBraking(Boolean driverBraking) {
        if (driverBraking != null) {
            parameters.put(UnsubscribeVehicleData.driverBraking, driverBraking);
        } else {
        	parameters.remove(UnsubscribeVehicleData.driverBraking);
        }
    }

	/**
	 * Gets a boolean value. If true, means the driver Braking data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the driver Braking data
	 *         has been unsubscribed.
	 */
    public Boolean getDriverBraking() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.driverBraking);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes wiper Status data
	 * 
	 * @param wiperStatus
	 *            a boolean value
	 */
    public void setWiperStatus(Boolean wiperStatus) {
        if (wiperStatus != null) {
            parameters.put(UnsubscribeVehicleData.wiperStatus, wiperStatus);
        } else {
        	parameters.remove(UnsubscribeVehicleData.wiperStatus);
        }
    }

	/**
	 * Gets a boolean value. If true, means the wiper Status data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the wiper Status data
	 *         has been unsubscribed.
	 */
    public Boolean getWiperStatus() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.wiperStatus);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes Head Lamp Status data
	 * 
	 * @param headLampStatus
	 *            a boolean value
	 */
    public void setHeadLampStatus(Boolean headLampStatus) {
        if (headLampStatus != null) {
            parameters.put(UnsubscribeVehicleData.headLampStatus, headLampStatus);
        } else {
        	parameters.remove(UnsubscribeVehicleData.headLampStatus);
        }
    }

	/**
	 * Gets a boolean value. If true, means the Head Lamp Status data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Head Lamp Status data
	 *         has been unsubscribed.
	 */
    public Boolean getHeadLampStatus() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.headLampStatus);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes Engine Torque data
	 * 
	 * @param engineTorque
	 *            a boolean value
	 */
    public void setEngineTorque(Boolean engineTorque) {
        if (engineTorque != null) {
            parameters.put(UnsubscribeVehicleData.engineTorque, engineTorque);
        } else {
        	parameters.remove(UnsubscribeVehicleData.engineTorque);
        }
    }

	/**
	 * Gets a boolean value. If true, means the Engine Torque data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Engine Torque data
	 *         has been unsubscribed.
	 */
    public Boolean getEngineTorque() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.engineTorque);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes accPedalPosition data
	 * 
	 * @param accPedalPosition
	 *            a boolean value
	 */
    public void setAccPedalPosition(Boolean accPedalPosition) {
        if (accPedalPosition != null) {
            parameters.put(UnsubscribeVehicleData.accPedalPosition, accPedalPosition);
        } else {
        	parameters.remove(UnsubscribeVehicleData.accPedalPosition);
        }
    }

	/**
	 * Gets a boolean value. If true, means the accPedalPosition data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the accPedalPosition data
	 *         has been unsubscribed.
	 */
    public Boolean getAccPedalPosition() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.accPedalPosition);
    }

    public void setSteeringWheelAngle(Boolean steeringWheelAngle) {
        if (steeringWheelAngle != null) {
            parameters.put(UnsubscribeVehicleData.steeringWheelAngle, steeringWheelAngle);
        } else {
        	parameters.remove(UnsubscribeVehicleData.steeringWheelAngle);
        }
    }

    public Boolean getSteeringWheelAngle() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.steeringWheelAngle);
    }    
        
    public void setECallInfo(Boolean eCallInfo) {
        if (eCallInfo != null) {
            parameters.put(UnsubscribeVehicleData.eCallInfo, eCallInfo);
        } else {
        	parameters.remove(UnsubscribeVehicleData.eCallInfo);
        }
    }
    public Boolean getECallInfo() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.eCallInfo);
    }
    public void setAirbagStatus(Boolean airbagStatus) {
        if (airbagStatus != null) {
            parameters.put(UnsubscribeVehicleData.airbagStatus, airbagStatus);
        } else {
        	parameters.remove(UnsubscribeVehicleData.airbagStatus);
        }
    }
    public Boolean getAirbagStatus() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.airbagStatus);
    }
    public void setEmergencyEvent(Boolean emergencyEvent) {
        if (emergencyEvent != null) {
            parameters.put(UnsubscribeVehicleData.emergencyEvent, emergencyEvent);
        } else {
        	parameters.remove(UnsubscribeVehicleData.emergencyEvent);
        }
    }
    public Boolean getEmergencyEvent() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.emergencyEvent);
    }
    public void setClusterModeStatus(Boolean clusterModeStatus) {
        if (clusterModeStatus != null) {
            parameters.put(UnsubscribeVehicleData.clusterModeStatus, clusterModeStatus);
        } else {
        	parameters.remove(UnsubscribeVehicleData.clusterModeStatus);
        }
    }
    public Boolean getClusterModeStatus() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.clusterModeStatus);
    }
    public void setMyKey(Boolean myKey) {
        if (myKey != null) {
            parameters.put(UnsubscribeVehicleData.myKey, myKey);
        } else {
        	parameters.remove(UnsubscribeVehicleData.myKey);
        }
    }
    public Boolean getMyKey() {
        return (Boolean) parameters.get(UnsubscribeVehicleData.myKey);
    }    
}
