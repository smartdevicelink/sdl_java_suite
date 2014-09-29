package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.constants.Names;

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
            parameters.put(Names.gps, gps);
        } else {
        	parameters.remove(Names.gps);
        }
    }

	/**
	 * Gets a boolean value. If true, means the Gps data has been subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Gps data has been
	 *         subscribed.
	 */
    public Boolean getGps() {
        return (Boolean) parameters.get(Names.gps);
    }

	/**
	 * Sets a boolean value. If true, subscribes speed data
	 * 
	 * @param speed
	 *            a boolean value
	 */
    public void setSpeed(Boolean speed) {
        if (speed != null) {
            parameters.put(Names.speed, speed);
        } else {
        	parameters.remove(Names.speed);
        }
    }

	/**
	 * Gets a boolean value. If true, means the Speed data has been subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Speed data has been
	 *         subscribed.
	 */
    public Boolean getSpeed() {
        return (Boolean) parameters.get(Names.speed);
    }

	/**
	 * Sets a boolean value. If true, subscribes rpm data
	 * 
	 * @param rpm
	 *            a boolean value
	 */
    public void setRpm(Boolean rpm) {
        if (rpm != null) {
            parameters.put(Names.rpm, rpm);
        } else {
        	parameters.remove(Names.rpm);
        }
    }

	/**
	 * Gets a boolean value. If true, means the rpm data has been subscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the rpm data has been
	 *         subscribed.
	 */
    public Boolean getRpm() {
        return (Boolean) parameters.get(Names.rpm);
    }

	/**
	 * Sets a boolean value. If true, subscribes FuelLevel data
	 * 
	 * @param fuelLevel
	 *            a boolean value
	 */
    public void setFuelLevel(Boolean fuelLevel) {
        if (fuelLevel != null) {
            parameters.put(Names.fuelLevel, fuelLevel);
        } else {
        	parameters.remove(Names.fuelLevel);
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
        return (Boolean) parameters.get(Names.fuelLevel);
    }

	/**
	 * Sets a boolean value. If true, subscribes fuelLevel_State data
	 * 
	 * @param fuelLevel_State
	 *            a boolean value
	 */
    public void setFuelLevel_State(Boolean fuelLevel_State) {
        if (fuelLevel_State != null) {
            parameters.put(Names.fuelLevel_State, fuelLevel_State);
        } else {
        	parameters.remove(Names.fuelLevel_State);
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
        return (Boolean) parameters.get(Names.fuelLevel_State);
    }

	/**
	 * Sets a boolean value. If true, subscribes instantFuelConsumption data
	 * 
	 * @param instantFuelConsumption
	 *            a boolean value
	 */
    public void setInstantFuelConsumption(Boolean instantFuelConsumption) {
        if (instantFuelConsumption != null) {
            parameters.put(Names.instantFuelConsumption, instantFuelConsumption);
        } else {
        	parameters.remove(Names.instantFuelConsumption);
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
        return (Boolean) parameters.get(Names.instantFuelConsumption);
    }

	/**
	 * Sets a boolean value. If true, subscribes externalTemperature data
	 * 
	 * @param externalTemperature
	 *            a boolean value
	 */
    public void setExternalTemperature(Boolean externalTemperature) {
        if (externalTemperature != null) {
            parameters.put(Names.externalTemperature, externalTemperature);
        } else {
        	parameters.remove(Names.externalTemperature);
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
        return (Boolean) parameters.get(Names.externalTemperature);
    }

	/**
	 * Sets a boolean value. If true, subscribes Currently selected gear data
	 * 
	 * @param prndl
	 *            a boolean value
	 */
    public void setPrndl(Boolean prndl) {
        if (prndl != null) {
            parameters.put(Names.prndl, prndl);
        } else {
        	parameters.remove(Names.prndl);
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
        return (Boolean) parameters.get(Names.prndl);
    }

	/**
	 * Sets a boolean value. If true, subscribes tire pressure status data
	 * 
	 * @param tirePressure
	 *            a boolean value
	 */
    public void setTirePressure(Boolean tirePressure) {
        if (tirePressure != null) {
            parameters.put(Names.tirePressure, tirePressure);
        } else {
        	parameters.remove(Names.tirePressure);
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
        return (Boolean) parameters.get(Names.tirePressure);
    }

	/**
	 * Sets a boolean value. If true, subscribes odometer data
	 * 
	 * @param odometer
	 *            a boolean value
	 */
    public void setOdometer(Boolean odometer) {
        if (odometer != null) {
            parameters.put(Names.odometer, odometer);
        } else {
        	parameters.remove(Names.odometer);
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
        return (Boolean) parameters.get(Names.odometer);
    }

	/**
	 * Sets a boolean value. If true, subscribes belt Status data
	 * 
	 * @param beltStatus
	 *            a boolean value
	 */
    public void setBeltStatus(Boolean beltStatus) {
        if (beltStatus != null) {
            parameters.put(Names.beltStatus, beltStatus);
        } else {
        	parameters.remove(Names.beltStatus);
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
        return (Boolean) parameters.get(Names.beltStatus);
    }

	/**
	 * Sets a boolean value. If true, subscribes body Information data
	 * 
	 * @param bodyInformation
	 *            a boolean value
	 */
    public void setBodyInformation(Boolean bodyInformation) {
        if (bodyInformation != null) {
            parameters.put(Names.bodyInformation, bodyInformation);
        } else {
        	parameters.remove(Names.bodyInformation);
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
        return (Boolean) parameters.get(Names.bodyInformation);
    }

	/**
	 * Sets a boolean value. If true, subscribes device Status data
	 * 
	 * @param deviceStatus
	 *            a boolean value
	 */
    public void setDeviceStatus(Boolean deviceStatus) {
        if (deviceStatus != null) {
            parameters.put(Names.deviceStatus, deviceStatus);
        } else {
        	parameters.remove(Names.deviceStatus);
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
        return (Boolean) parameters.get(Names.deviceStatus);
    }

	/**
	 * Sets a boolean value. If true, subscribes driver Braking data
	 * 
	 * @param driverBraking
	 *            a boolean value
	 */
    public void setDriverBraking(Boolean driverBraking) {
        if (driverBraking != null) {
            parameters.put(Names.driverBraking, driverBraking);
        } else {
        	parameters.remove(Names.driverBraking);
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
        return (Boolean) parameters.get(Names.driverBraking);
    }

	/**
	 * Sets a boolean value. If true, subscribes wiper Status data
	 * 
	 * @param wiperStatus
	 *            a boolean value
	 */
    public void setWiperStatus(Boolean wiperStatus) {
        if (wiperStatus != null) {
            parameters.put(Names.wiperStatus, wiperStatus);
        } else {
        	parameters.remove(Names.wiperStatus);
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
        return (Boolean) parameters.get(Names.wiperStatus);
    }

	/**
	 * Sets a boolean value. If true, subscribes Head Lamp Status data
	 * 
	 * @param headLampStatus
	 *            a boolean value
	 */
    public void setHeadLampStatus(Boolean headLampStatus) {
        if (headLampStatus != null) {
            parameters.put(Names.headLampStatus, headLampStatus);
        } else {
        	parameters.remove(Names.headLampStatus);
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
        return (Boolean) parameters.get(Names.headLampStatus);
    }

	/**
	 * Sets a boolean value. If true, subscribes Engine Torque data
	 * 
	 * @param engineTorque
	 *            a boolean value
	 */
    public void setEngineTorque(Boolean engineTorque) {
        if (engineTorque != null) {
            parameters.put(Names.engineTorque, engineTorque);
        } else {
        	parameters.remove(Names.engineTorque);
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
        return (Boolean) parameters.get(Names.engineTorque);
    }

	/**
	 * Sets a boolean value. If true, subscribes accPedalPosition data
	 * 
	 * @param accPedalPosition
	 *            a boolean value
	 */
    public void setAccPedalPosition(Boolean accPedalPosition) {
        if (accPedalPosition != null) {
            parameters.put(Names.accPedalPosition, accPedalPosition);
        } else {
        	parameters.remove(Names.accPedalPosition);
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
        return (Boolean) parameters.get(Names.accPedalPosition);
    }
  
    public void setSteeringWheelAngle(Boolean steeringWheelAngle) {
        if (steeringWheelAngle != null) {
            parameters.put(Names.steeringWheelAngle, steeringWheelAngle);
        } else {
        	parameters.remove(Names.steeringWheelAngle);
        }
    }

    public Boolean getSteeringWheelAngle() {
        return (Boolean) parameters.get(Names.steeringWheelAngle);
    }    
    public void setECallInfo(Boolean eCallInfo) {
        if (eCallInfo != null) {
            parameters.put(Names.eCallInfo, eCallInfo);
        } else {
        	parameters.remove(Names.eCallInfo);
        }
    }
    public Boolean getECallInfo() {
        return (Boolean) parameters.get(Names.eCallInfo);
    }
    public void setAirbagStatus(Boolean airbagStatus) {
        if (airbagStatus != null) {
            parameters.put(Names.airbagStatus, airbagStatus);
        } else {
        	parameters.remove(Names.airbagStatus);
        }
    }
    public Boolean getAirbagStatus() {
        return (Boolean) parameters.get(Names.airbagStatus);
    }
    public void setEmergencyEvent(Boolean emergencyEvent) {
        if (emergencyEvent != null) {
            parameters.put(Names.emergencyEvent, emergencyEvent);
        } else {
        	parameters.remove(Names.emergencyEvent);
        }
    }
    public Boolean getEmergencyEvent() {
        return (Boolean) parameters.get(Names.emergencyEvent);
    }
    public void setClusterModeStatus(Boolean clusterModeStatus) {
        if (clusterModeStatus != null) {
            parameters.put(Names.clusterModeStatus, clusterModeStatus);
        } else {
        	parameters.remove(Names.clusterModeStatus);
        }
    }
    public Boolean getClusterModeStatus() {
        return (Boolean) parameters.get(Names.clusterModeStatus);
    }
    public void setMyKey(Boolean myKey) {
        if (myKey != null) {
            parameters.put(Names.myKey, myKey);
        } else {
        	parameters.remove(Names.myKey);
        }
    }
    public Boolean getMyKey() {
        return (Boolean) parameters.get(Names.myKey);
    }      
    
}
