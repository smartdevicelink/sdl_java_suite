package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.util.DebugTool;

/**
 * Subscribe Vehicle Data Response is sent, when SubscribeVehicleData has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class SubscribeVehicleDataResponse extends RPCResponse {

	/**
	 * Constructs a new SubscribeVehicleDataResponse object
	 */
    public SubscribeVehicleDataResponse() {
        super("SubscribeVehicleData");
    }

	/**
	 * Constructs a new SubscribeVehicleDataResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SubscribeVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Sets gps
     * @param gps
     */
    public void setGps(VehicleDataResult gps) {
        if (gps != null) {
            parameters.put(Names.gps, gps);
        } else {
        	parameters.remove(Names.gps);
        }
    }
    /**
     * Gets gps
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getGps() {
    	Object obj = parameters.get(Names.gps);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.gps, e);
            }
        }
        return null;
    }
    /**
     * Sets speed
     * @param speed
     */
    public void setSpeed(VehicleDataResult speed) {
        if (speed != null) {
            parameters.put(Names.speed, speed);
        } else {
        	parameters.remove(Names.speed);
        }
    }
    /**
     * Gets speed
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getSpeed() {
    	Object obj = parameters.get(Names.speed);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.speed, e);
            }
        }
        return null;
    }
    /**
     * Sets rpm
     * @param rpm
     */
    public void setRpm(VehicleDataResult rpm) {
        if (rpm != null) {
            parameters.put(Names.rpm, rpm);
        } else {
        	parameters.remove(Names.rpm);
        }
    }
    /**
     * Gets rpm
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getRpm() {
    	Object obj = parameters.get(Names.rpm);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.rpm, e);
            }
        }
        return null;
    }
    /**
     * Sets Fuel Level
     * @param fuelLevel
     */
    public void setFuelLevel(VehicleDataResult fuelLevel) {
        if (fuelLevel != null) {
            parameters.put(Names.fuelLevel, fuelLevel);
        } else {
        	parameters.remove(Names.fuelLevel);
        }
    }
    /**
     * Gets Fuel Level
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getFuelLevel() {
    	Object obj = parameters.get(Names.fuelLevel);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.fuelLevel, e);
            }
        }
        return null;
    }
    /**
     * Sets Fuel Level State
     * @param fuelLevel_State
     */
    public void setFuelLevel_State(VehicleDataResult fuelLevel_State) {
        if (fuelLevel_State != null) {
            parameters.put(Names.fuelLevel_State, fuelLevel_State);
        } else {
        	parameters.remove(Names.fuelLevel_State);
        }
    }
    /**
     * Gets Fuel Level State
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getFuelLevel_State() {
    	Object obj = parameters.get(Names.fuelLevel_State);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.fuelLevel_State, e);
            }
        }
        return null;
    }
    /**
     * Sets Instant Fuel Comsumption
     * @param instantFuelConsumption
     */
    public void setInstantFuelConsumption(VehicleDataResult instantFuelConsumption) {
        if (instantFuelConsumption != null) {
            parameters.put(Names.instantFuelConsumption, instantFuelConsumption);
        } else {
        	parameters.remove(Names.instantFuelConsumption);
        }
    }
    /**
     * Gets Instant Fuel Consumption
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getInstantFuelConsumption() {
    	Object obj = parameters.get(Names.instantFuelConsumption);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.instantFuelConsumption, e);
            }
        }
        return null;
    }
    /**
     * Sets External Temperature
     * @param externalTemperature
     */
    public void setExternalTemperature(VehicleDataResult externalTemperature) {
        if (externalTemperature != null) {
            parameters.put(Names.externalTemperature, externalTemperature);
        } else {
        	parameters.remove(Names.externalTemperature);
        }
    }
    /**
     * Gets External Temperature
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getExternalTemperature() {
    	Object obj = parameters.get(Names.externalTemperature);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.externalTemperature, e);
            }
        }
        return null;
    }
    /**
     * Sets currently selected gear data
     * @param prndl
     */
    public void setPrndl(VehicleDataResult prndl) {
        if (prndl != null) {
            parameters.put(Names.prndl, prndl);
        } else {
        	parameters.remove(Names.prndl);
        }
    }
    /**
     * Gets currently selected gear data
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getPrndl() {
    	Object obj = parameters.get(Names.prndl);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.prndl, e);
            }
        }
        return null;
    }
    /**
     * Sets Tire Pressure
     * @param tirePressure
     */
    public void setTirePressure(VehicleDataResult tirePressure) {
        if (tirePressure != null) {
            parameters.put(Names.tirePressure, tirePressure);
        } else {
        	parameters.remove(Names.tirePressure);
        }
    }
    /**
     * Gets Tire Pressure
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getTirePressure() {
    	Object obj = parameters.get(Names.tirePressure);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.tirePressure, e);
            }
        }
        return null;
    }
    /**
     * Sets Odometer
     * @param odometer
     */
    public void setOdometer(VehicleDataResult odometer) {
        if (odometer != null) {
            parameters.put(Names.odometer, odometer);
        } else {
        	parameters.remove(Names.odometer);
        }
    }
    /**
     * Gets Odometer
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getOdometer() {
    	Object obj = parameters.get(Names.odometer);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.odometer, e);
            }
        }
        return null;
    }
    /**
     * Sets Belt Status
     * @param beltStatus
     */
    public void setBeltStatus(VehicleDataResult beltStatus) {
        if (beltStatus != null) {
            parameters.put(Names.beltStatus, beltStatus);
        } else {
        	parameters.remove(Names.beltStatus);
        }
    }
    /**
     * Gets Belt Status
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getBeltStatus() {
    	Object obj = parameters.get(Names.beltStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.beltStatus, e);
            }
        }
        return null;
    }
    /**
     * Sets Body Information
     * @param bodyInformation
     */
    public void setBodyInformation(VehicleDataResult bodyInformation) {
        if (bodyInformation != null) {
            parameters.put(Names.bodyInformation, bodyInformation);
        } else {
        	parameters.remove(Names.bodyInformation);
        }
    }
    /**
     * Gets Body Information
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getBodyInformation() {
    	Object obj = parameters.get(Names.bodyInformation);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.bodyInformation, e);
            }
        }
        return null;
    }
    /**
     * Sets Device Status
     * @param deviceStatus
     */
    public void setDeviceStatus(VehicleDataResult deviceStatus) {
        if (deviceStatus != null) {
            parameters.put(Names.deviceStatus, deviceStatus);
        } else {
        	parameters.remove(Names.deviceStatus);
        }
    }
    /**
     * Gets Device Status
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getDeviceStatus() {
    	Object obj = parameters.get(Names.deviceStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.deviceStatus, e);
            }
        }
        return null;
    }
    /**
     * Sets Driver Barking
     * @param driverBraking
     */
    public void setDriverBraking(VehicleDataResult driverBraking) {
        if (driverBraking != null) {
            parameters.put(Names.driverBraking, driverBraking);
        } else {
        	parameters.remove(Names.driverBraking);
        }
    }
    /**
     * Gets Driver Barking
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getDriverBraking() {
    	Object obj = parameters.get(Names.driverBraking);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.driverBraking, e);
            }
        }
        return null;
    }
    /**
     * Sets wiper Status
     * @param wiperStatus
     */
    public void setWiperStatus(VehicleDataResult wiperStatus) {
        if (wiperStatus != null) {
            parameters.put(Names.wiperStatus, wiperStatus);
        } else {
        	parameters.remove(Names.wiperStatus);
        }
    }
    /**
     * Gets Wiper Status
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getWiperStatus() {
    	Object obj = parameters.get(Names.wiperStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.wiperStatus, e);
            }
        }
        return null;
    }
    /**
     * Sets Head Lamp Status
     * @param headLampStatus
     */
    public void setHeadLampStatus(VehicleDataResult headLampStatus) {
        if (headLampStatus != null) {
            parameters.put(Names.headLampStatus, headLampStatus);
        } else {
        	parameters.remove(Names.headLampStatus);
        }
    }
    /**
     * Gets Head Lamp Status
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getHeadLampStatus() {
    	Object obj = parameters.get(Names.headLampStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.headLampStatus, e);
            }
        }
        return null;
    }
    /**
     * Sets Engine Torque
     * @param engineTorque
     */
    public void setEngineTorque(VehicleDataResult engineTorque) {
        if (engineTorque != null) {
            parameters.put(Names.engineTorque, engineTorque);
        } else {
        	parameters.remove(Names.engineTorque);
        }
    }
    /**
     * Gets Engine Torque
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getEngineTorque() {
    	Object obj = parameters.get(Names.engineTorque);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.engineTorque, e);
            }
        }
        return null;
    }
    /**
     * Sets AccPedal Position
     * @param accPedalPosition
     */
    public void setAccPedalPosition(VehicleDataResult accPedalPosition) {
        if (accPedalPosition != null) {
            parameters.put(Names.accPedalPosition, accPedalPosition);
        } else {
        	parameters.remove(Names.accPedalPosition);
        }
    }
    /**
     * Gets AccPedal Position
     * @return VehicleDataResult 
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getAccPedalPosition() {
    	Object obj = parameters.get(Names.accPedalPosition);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.accPedalPosition, e);
            }
        }
        return null;
    }

    public void setSteeringWheelAngle(VehicleDataResult steeringWheelAngle) {
        if (steeringWheelAngle != null) {
            parameters.put(Names.steeringWheelAngle, steeringWheelAngle);
        } else {
        	parameters.remove(Names.steeringWheelAngle);
        }
    }

    @SuppressWarnings("unchecked")
    public VehicleDataResult getSteeringWheelAngle() {
    	Object obj = parameters.get(Names.steeringWheelAngle);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.steeringWheelAngle, e);
            }
        }
        return null;
    }
    
    public void setECallInfo(VehicleDataResult eCallInfo) {
        if (eCallInfo != null) {
            parameters.put(Names.eCallInfo, eCallInfo);
        } else {
        	parameters.remove(Names.eCallInfo);
        }
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getECallInfo() {
    	Object obj = parameters.get(Names.eCallInfo);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.eCallInfo, e);
            }
        }
        return null;
    }
    public void setAirbagStatus(VehicleDataResult airbagStatus) {
        if (airbagStatus != null) {
            parameters.put(Names.airbagStatus, airbagStatus);
        } else {
        	parameters.remove(Names.airbagStatus);
        }
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getAirbagStatus() {
    	Object obj = parameters.get(Names.airbagStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.airbagStatus, e);
            }
        }
        return null;
    }
    public void setEmergencyEvent(VehicleDataResult emergencyEvent) {
        if (emergencyEvent != null) {
            parameters.put(Names.emergencyEvent, emergencyEvent);
        } else {
        	parameters.remove(Names.emergencyEvent);
        }
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getEmergencyEvent() {
    	Object obj = parameters.get(Names.emergencyEvent);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.emergencyEvent, e);
            }
        }
        return null;
    }
    public void setClusterModeStatus(VehicleDataResult clusterModeStatus) {
        if (clusterModeStatus != null) {
            parameters.put(Names.clusterModeStatus, clusterModeStatus);
        } else {
        	parameters.remove(Names.clusterModeStatus);
        }
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getClusterModeStatus() {
    	Object obj = parameters.get(Names.clusterModeStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.clusterModeStatus, e);
            }
        }
        return null;
    }
    public void setMyKey(VehicleDataResult myKey) {
        if (myKey != null) {
            parameters.put(Names.myKey, myKey);
        } else {
        	parameters.remove(Names.myKey);
        }
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getMyKey() {
    	Object obj = parameters.get(Names.myKey);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.myKey, e);
            }
        }
        return null;
    }       
}