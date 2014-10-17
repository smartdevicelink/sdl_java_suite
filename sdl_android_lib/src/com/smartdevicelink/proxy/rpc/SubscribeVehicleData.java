package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCRequest;

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
	public static final String speed = "speed";

	/**
	 * Constructs a new SubscribeVehicleData object
	 */
    public SubscribeVehicleData() {
        super("SubscribeVehicleData");
    }

	/**
	 * Constructs a new SubscribeVehicleData object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SubscribeVehicleData(Hashtable hash) {
        super(hash);
    }

	/**
	 * Sets a boolean value. If true, subscribes Gps data
	 * 
	 * @param gps
	 *            a boolean value
	 */
    public void setGps(Boolean gps) {
        if (gps != null) {
            parameters.put(SubscribeVehicleData.gps, gps);
        } else {
        	parameters.remove(SubscribeVehicleData.gps);
        }
    }

	/**
	 * Gets a boolean value. If true, means the Gps data has been subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Gps data has been
	 *         subscribed.
	 */
    public Boolean getGps() {
        return (Boolean) parameters.get(SubscribeVehicleData.gps);
    }

	/**
	 * Sets a boolean value. If true, subscribes speed data
	 * 
	 * @param speed
	 *            a boolean value
	 */
    public void setSpeed(Boolean speed) {
        if (speed != null) {
            parameters.put(SubscribeVehicleData.speed, speed);
        } else {
        	parameters.remove(SubscribeVehicleData.speed);
        }
    }

	/**
	 * Gets a boolean value. If true, means the Speed data has been subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Speed data has been
	 *         subscribed.
	 */
    public Boolean getSpeed() {
        return (Boolean) parameters.get(SubscribeVehicleData.speed);
    }

	/**
	 * Sets a boolean value. If true, subscribes rpm data
	 * 
	 * @param rpm
	 *            a boolean value
	 */
    public void setRpm(Boolean rpm) {
        if (rpm != null) {
            parameters.put(SubscribeVehicleData.rpm, rpm);
        } else {
        	parameters.remove(SubscribeVehicleData.rpm);
        }
    }

	/**
	 * Gets a boolean value. If true, means the rpm data has been subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the rpm data has been
	 *         subscribed.
	 */
    public Boolean getRpm() {
        return (Boolean) parameters.get(SubscribeVehicleData.rpm);
    }

	/**
	 * Sets a boolean value. If true, subscribes FuelLevel data
	 * 
	 * @param fuelLevel
	 *            a boolean value
	 */
    public void setFuelLevel(Boolean fuelLevel) {
        if (fuelLevel != null) {
            parameters.put(SubscribeVehicleData.fuelLevel, fuelLevel);
        } else {
        	parameters.remove(SubscribeVehicleData.fuelLevel);
        }
    }

	/**
	 * Gets a boolean value. If true, means the FuelLevel data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the FuelLevel data has
	 *         been subscribed.
	 */
    public Boolean getFuelLevel() {
        return (Boolean) parameters.get(SubscribeVehicleData.fuelLevel);
    }

	/**
	 * Sets a boolean value. If true, subscribes fuelLevel_State data
	 * 
	 * @param fuelLevel_State
	 *            a boolean value
	 */
    public void setFuelLevel_State(Boolean fuelLevel_State) {
        if (fuelLevel_State != null) {
            parameters.put(SubscribeVehicleData.fuelLevel_State, fuelLevel_State);
        } else {
        	parameters.remove(SubscribeVehicleData.fuelLevel_State);
        }
    }

	/**
	 * Gets a boolean value. If true, means the fuelLevel_State data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the fuelLevel_State data
	 *         has been subscribed.
	 */
    public Boolean getFuelLevel_State() {
        return (Boolean) parameters.get(SubscribeVehicleData.fuelLevel_State);
    }

	/**
	 * Sets a boolean value. If true, subscribes instantFuelConsumption data
	 * 
	 * @param instantFuelConsumption
	 *            a boolean value
	 */
    public void setInstantFuelConsumption(Boolean instantFuelConsumption) {
        if (instantFuelConsumption != null) {
            parameters.put(SubscribeVehicleData.instantFuelConsumption, instantFuelConsumption);
        } else {
        	parameters.remove(SubscribeVehicleData.instantFuelConsumption);
        }
    }

	/**
	 * Gets a boolean value. If true, means the getInstantFuelConsumption data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the getInstantFuelConsumption data
	 *         has been subscribed.
	 */
    public Boolean getInstantFuelConsumption() {
        return (Boolean) parameters.get(SubscribeVehicleData.instantFuelConsumption);
    }

	/**
	 * Sets a boolean value. If true, subscribes externalTemperature data
	 * 
	 * @param externalTemperature
	 *            a boolean value
	 */
    public void setExternalTemperature(Boolean externalTemperature) {
        if (externalTemperature != null) {
            parameters.put(SubscribeVehicleData.externalTemperature, externalTemperature);
        } else {
        	parameters.remove(SubscribeVehicleData.externalTemperature);
        }
    }

	/**
	 * Gets a boolean value. If true, means the externalTemperature data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the externalTemperature data
	 *         has been subscribed.
	 */
    public Boolean getExternalTemperature() {
        return (Boolean) parameters.get(SubscribeVehicleData.externalTemperature);
    }

	/**
	 * Sets a boolean value. If true, subscribes Currently selected gear data
	 * 
	 * @param prndl
	 *            a boolean value
	 */
    public void setPrndl(Boolean prndl) {
        if (prndl != null) {
            parameters.put(SubscribeVehicleData.prndl, prndl);
        } else {
        	parameters.remove(SubscribeVehicleData.prndl);
        }
    }

	/**
	 * Gets a boolean value. If true, means the Currently selected gear data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Currently selected gear data
	 *         has been subscribed.
	 */
    public Boolean getPrndl() {
        return (Boolean) parameters.get(SubscribeVehicleData.prndl);
    }

	/**
	 * Sets a boolean value. If true, subscribes tire pressure status data
	 * 
	 * @param tirePressure
	 *            a boolean value
	 */
    public void setTirePressure(Boolean tirePressure) {
        if (tirePressure != null) {
            parameters.put(SubscribeVehicleData.tirePressure, tirePressure);
        } else {
        	parameters.remove(SubscribeVehicleData.tirePressure);
        }
    }

	/**
	 * Gets a boolean value. If true, means the tire pressure status data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the tire pressure status data
	 *         has been subscribed.
	 */
    public Boolean getTirePressure() {
        return (Boolean) parameters.get(SubscribeVehicleData.tirePressure);
    }

	/**
	 * Sets a boolean value. If true, subscribes odometer data
	 * 
	 * @param odometer
	 *            a boolean value
	 */
    public void setOdometer(Boolean odometer) {
        if (odometer != null) {
            parameters.put(SubscribeVehicleData.odometer, odometer);
        } else {
        	parameters.remove(SubscribeVehicleData.odometer);
        }
    }

	/**
	 * Gets a boolean value. If true, means the odometer data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the odometer data
	 *         has been subscribed.
	 */
    public Boolean getOdometer() {
        return (Boolean) parameters.get(SubscribeVehicleData.odometer);
    }

	/**
	 * Sets a boolean value. If true, subscribes belt Status data
	 * 
	 * @param beltStatus
	 *            a boolean value
	 */
    public void setBeltStatus(Boolean beltStatus) {
        if (beltStatus != null) {
            parameters.put(SubscribeVehicleData.beltStatus, beltStatus);
        } else {
        	parameters.remove(SubscribeVehicleData.beltStatus);
        }
    }

	/**
	 * Gets a boolean value. If true, means the belt Status data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the belt Status data
	 *         has been subscribed.
	 */
    public Boolean getBeltStatus() {
        return (Boolean) parameters.get(SubscribeVehicleData.beltStatus);
    }

	/**
	 * Sets a boolean value. If true, subscribes body Information data
	 * 
	 * @param bodyInformation
	 *            a boolean value
	 */
    public void setBodyInformation(Boolean bodyInformation) {
        if (bodyInformation != null) {
            parameters.put(SubscribeVehicleData.bodyInformation, bodyInformation);
        } else {
        	parameters.remove(SubscribeVehicleData.bodyInformation);
        }
    }

	/**
	 * Gets a boolean value. If true, means the body Information data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the body Information data
	 *         has been subscribed.
	 */
    public Boolean getBodyInformation() {
        return (Boolean) parameters.get(SubscribeVehicleData.bodyInformation);
    }

	/**
	 * Sets a boolean value. If true, subscribes device Status data
	 * 
	 * @param deviceStatus
	 *            a boolean value
	 */
    public void setDeviceStatus(Boolean deviceStatus) {
        if (deviceStatus != null) {
            parameters.put(SubscribeVehicleData.deviceStatus, deviceStatus);
        } else {
        	parameters.remove(SubscribeVehicleData.deviceStatus);
        }
    }

	/**
	 * Gets a boolean value. If true, means the device Status data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the device Status data
	 *         has been subscribed.
	 */
    public Boolean getDeviceStatus() {
        return (Boolean) parameters.get(SubscribeVehicleData.deviceStatus);
    }

	/**
	 * Sets a boolean value. If true, subscribes driver Braking data
	 * 
	 * @param driverBraking
	 *            a boolean value
	 */
    public void setDriverBraking(Boolean driverBraking) {
        if (driverBraking != null) {
            parameters.put(SubscribeVehicleData.driverBraking, driverBraking);
        } else {
        	parameters.remove(SubscribeVehicleData.driverBraking);
        }
    }

	/**
	 * Gets a boolean value. If true, means the driver Braking data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the driver Braking data
	 *         has been subscribed.
	 */
    public Boolean getDriverBraking() {
        return (Boolean) parameters.get(SubscribeVehicleData.driverBraking);
    }

	/**
	 * Sets a boolean value. If true, subscribes wiper Status data
	 * 
	 * @param wiperStatus
	 *            a boolean value
	 */
    public void setWiperStatus(Boolean wiperStatus) {
        if (wiperStatus != null) {
            parameters.put(SubscribeVehicleData.wiperStatus, wiperStatus);
        } else {
        	parameters.remove(SubscribeVehicleData.wiperStatus);
        }
    }

	/**
	 * Gets a boolean value. If true, means the wiper Status data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the wiper Status data
	 *         has been subscribed.
	 */
    public Boolean getWiperStatus() {
        return (Boolean) parameters.get(SubscribeVehicleData.wiperStatus);
    }

	/**
	 * Sets a boolean value. If true, subscribes Head Lamp Status data
	 * 
	 * @param headLampStatus
	 *            a boolean value
	 */
    public void setHeadLampStatus(Boolean headLampStatus) {
        if (headLampStatus != null) {
            parameters.put(SubscribeVehicleData.headLampStatus, headLampStatus);
        } else {
        	parameters.remove(SubscribeVehicleData.headLampStatus);
        }
    }

	/**
	 * Gets a boolean value. If true, means the Head Lamp Status data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Head Lamp Status data
	 *         has been subscribed.
	 */
    public Boolean getHeadLampStatus() {
        return (Boolean) parameters.get(SubscribeVehicleData.headLampStatus);
    }

	/**
	 * Sets a boolean value. If true, subscribes Engine Torque data
	 * 
	 * @param engineTorque
	 *            a boolean value
	 */
    public void setEngineTorque(Boolean engineTorque) {
        if (engineTorque != null) {
            parameters.put(SubscribeVehicleData.engineTorque, engineTorque);
        } else {
        	parameters.remove(SubscribeVehicleData.engineTorque);
        }
    }

	/**
	 * Gets a boolean value. If true, means the Engine Torque data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Engine Torque data
	 *         has been subscribed.
	 */
    public Boolean getEngineTorque() {
        return (Boolean) parameters.get(SubscribeVehicleData.engineTorque);
    }

	/**
	 * Sets a boolean value. If true, subscribes accPedalPosition data
	 * 
	 * @param accPedalPosition
	 *            a boolean value
	 */
    public void setAccPedalPosition(Boolean accPedalPosition) {
        if (accPedalPosition != null) {
            parameters.put(SubscribeVehicleData.accPedalPosition, accPedalPosition);
        } else {
        	parameters.remove(SubscribeVehicleData.accPedalPosition);
        }
    }

	/**
	 * Gets a boolean value. If true, means the accPedalPosition data has been
	 * subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the accPedalPosition data
	 *         has been subscribed.
	 */
    public Boolean getAccPedalPosition() {
        return (Boolean) parameters.get(SubscribeVehicleData.accPedalPosition);
    }
  
    public void setSteeringWheelAngle(Boolean steeringWheelAngle) {
        if (steeringWheelAngle != null) {
            parameters.put(SubscribeVehicleData.steeringWheelAngle, steeringWheelAngle);
        } else {
        	parameters.remove(SubscribeVehicleData.steeringWheelAngle);
        }
    }

    public Boolean getSteeringWheelAngle() {
        return (Boolean) parameters.get(SubscribeVehicleData.steeringWheelAngle);
    }    
    public void setECallInfo(Boolean eCallInfo) {
        if (eCallInfo != null) {
            parameters.put(SubscribeVehicleData.eCallInfo, eCallInfo);
        } else {
        	parameters.remove(SubscribeVehicleData.eCallInfo);
        }
    }
    public Boolean getECallInfo() {
        return (Boolean) parameters.get(SubscribeVehicleData.eCallInfo);
    }
    public void setAirbagStatus(Boolean airbagStatus) {
        if (airbagStatus != null) {
            parameters.put(SubscribeVehicleData.airbagStatus, airbagStatus);
        } else {
        	parameters.remove(SubscribeVehicleData.airbagStatus);
        }
    }
    public Boolean getAirbagStatus() {
        return (Boolean) parameters.get(SubscribeVehicleData.airbagStatus);
    }
    public void setEmergencyEvent(Boolean emergencyEvent) {
        if (emergencyEvent != null) {
            parameters.put(SubscribeVehicleData.emergencyEvent, emergencyEvent);
        } else {
        	parameters.remove(SubscribeVehicleData.emergencyEvent);
        }
    }
    public Boolean getEmergencyEvent() {
        return (Boolean) parameters.get(SubscribeVehicleData.emergencyEvent);
    }
    public void setClusterModeStatus(Boolean clusterModeStatus) {
        if (clusterModeStatus != null) {
            parameters.put(SubscribeVehicleData.clusterModeStatus, clusterModeStatus);
        } else {
        	parameters.remove(SubscribeVehicleData.clusterModeStatus);
        }
    }
    public Boolean getClusterModeStatus() {
        return (Boolean) parameters.get(SubscribeVehicleData.clusterModeStatus);
    }
    public void setMyKey(Boolean myKey) {
        if (myKey != null) {
            parameters.put(SubscribeVehicleData.myKey, myKey);
        } else {
        	parameters.remove(SubscribeVehicleData.myKey);
        }
    }
    public Boolean getMyKey() {
        return (Boolean) parameters.get(SubscribeVehicleData.myKey);
    }      
    
}
