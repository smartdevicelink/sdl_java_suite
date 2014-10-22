package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.util.DebugTool;

/**
 * Subscribe Vehicle Data Response is sent, when SubscribeVehicleData has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class SubscribeVehicleDataResponse extends RPCResponse {
	public static final String speed = "speed";
	public static final String rpm = "rpm";
	public static final String fuelLevel = "fuelLevel";
	public static final String externalTemperature = "externalTemperature";
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
    public SubscribeVehicleDataResponse(Hashtable hash) {
        super(hash);
    }
    /**
     * Sets gps
     * @param gps
     */
    public void setGps(VehicleDataResult gps) {
        if (gps != null) {
            parameters.put(SubscribeVehicleDataResponse.gps, gps);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.gps);
        }
    }
    /**
     * Gets gps
     * @return VehicleDataResult 
     */
    public VehicleDataResult getGps() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.gps);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.gps, e);
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
            parameters.put(SubscribeVehicleDataResponse.speed, speed);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.speed);
        }
    }
    /**
     * Gets speed
     * @return VehicleDataResult 
     */
    public VehicleDataResult getSpeed() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.speed);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.speed, e);
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
            parameters.put(SubscribeVehicleDataResponse.rpm, rpm);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.rpm);
        }
    }
    /**
     * Gets rpm
     * @return VehicleDataResult 
     */
    public VehicleDataResult getRpm() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.rpm);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.rpm, e);
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
            parameters.put(SubscribeVehicleDataResponse.fuelLevel, fuelLevel);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.fuelLevel);
        }
    }
    /**
     * Gets Fuel Level
     * @return VehicleDataResult 
     */
    public VehicleDataResult getFuelLevel() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.fuelLevel);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.fuelLevel, e);
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
            parameters.put(SubscribeVehicleDataResponse.fuelLevel_State, fuelLevel_State);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.fuelLevel_State);
        }
    }
    /**
     * Gets Fuel Level State
     * @return VehicleDataResult 
     */
    public VehicleDataResult getFuelLevel_State() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.fuelLevel_State);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.fuelLevel_State, e);
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
            parameters.put(SubscribeVehicleDataResponse.instantFuelConsumption, instantFuelConsumption);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.instantFuelConsumption);
        }
    }
    /**
     * Gets Instant Fuel Consumption
     * @return VehicleDataResult 
     */
    public VehicleDataResult getInstantFuelConsumption() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.instantFuelConsumption);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.instantFuelConsumption, e);
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
            parameters.put(SubscribeVehicleDataResponse.externalTemperature, externalTemperature);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.externalTemperature);
        }
    }
    /**
     * Gets External Temperature
     * @return VehicleDataResult 
     */
    public VehicleDataResult getExternalTemperature() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.externalTemperature);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.externalTemperature, e);
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
            parameters.put(SubscribeVehicleDataResponse.prndl, prndl);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.prndl);
        }
    }
    /**
     * Gets currently selected gear data
     * @return VehicleDataResult 
     */
    public VehicleDataResult getPrndl() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.prndl);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.prndl, e);
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
            parameters.put(SubscribeVehicleDataResponse.tirePressure, tirePressure);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.tirePressure);
        }
    }
    /**
     * Gets Tire Pressure
     * @return VehicleDataResult 
     */
    public VehicleDataResult getTirePressure() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.tirePressure);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.tirePressure, e);
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
            parameters.put(SubscribeVehicleDataResponse.odometer, odometer);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.odometer);
        }
    }
    /**
     * Gets Odometer
     * @return VehicleDataResult 
     */
    public VehicleDataResult getOdometer() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.odometer);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.odometer, e);
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
            parameters.put(SubscribeVehicleDataResponse.beltStatus, beltStatus);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.beltStatus);
        }
    }
    /**
     * Gets Belt Status
     * @return VehicleDataResult 
     */
    public VehicleDataResult getBeltStatus() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.beltStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.beltStatus, e);
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
            parameters.put(SubscribeVehicleDataResponse.bodyInformation, bodyInformation);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.bodyInformation);
        }
    }
    /**
     * Gets Body Information
     * @return VehicleDataResult 
     */
    public VehicleDataResult getBodyInformation() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.bodyInformation);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.bodyInformation, e);
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
            parameters.put(SubscribeVehicleDataResponse.deviceStatus, deviceStatus);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.deviceStatus);
        }
    }
    /**
     * Gets Device Status
     * @return VehicleDataResult 
     */
    public VehicleDataResult getDeviceStatus() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.deviceStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.deviceStatus, e);
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
            parameters.put(SubscribeVehicleDataResponse.driverBraking, driverBraking);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.driverBraking);
        }
    }
    /**
     * Gets Driver Barking
     * @return VehicleDataResult 
     */
    public VehicleDataResult getDriverBraking() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.driverBraking);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.driverBraking, e);
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
            parameters.put(SubscribeVehicleDataResponse.wiperStatus, wiperStatus);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.wiperStatus);
        }
    }
    /**
     * Gets Wiper Status
     * @return VehicleDataResult 
     */
    public VehicleDataResult getWiperStatus() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.wiperStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.wiperStatus, e);
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
            parameters.put(SubscribeVehicleDataResponse.headLampStatus, headLampStatus);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.headLampStatus);
        }
    }
    /**
     * Gets Head Lamp Status
     * @return VehicleDataResult 
     */
    public VehicleDataResult getHeadLampStatus() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.headLampStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.headLampStatus, e);
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
            parameters.put(SubscribeVehicleDataResponse.engineTorque, engineTorque);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.engineTorque);
        }
    }
    /**
     * Gets Engine Torque
     * @return VehicleDataResult 
     */
    public VehicleDataResult getEngineTorque() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.engineTorque);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.engineTorque, e);
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
            parameters.put(SubscribeVehicleDataResponse.accPedalPosition, accPedalPosition);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.accPedalPosition);
        }
    }
    /**
     * Gets AccPedal Position
     * @return VehicleDataResult 
     */
    public VehicleDataResult getAccPedalPosition() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.accPedalPosition);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.accPedalPosition, e);
            }
        }
        return null;
    }

    public void setSteeringWheelAngle(VehicleDataResult steeringWheelAngle) {
        if (steeringWheelAngle != null) {
            parameters.put(SubscribeVehicleDataResponse.steeringWheelAngle, steeringWheelAngle);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.steeringWheelAngle);
        }
    }
 
    public VehicleDataResult getSteeringWheelAngle() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.steeringWheelAngle);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.steeringWheelAngle, e);
            }
        }
        return null;
    }
    
    public void setECallInfo(VehicleDataResult eCallInfo) {
        if (eCallInfo != null) {
            parameters.put(SubscribeVehicleDataResponse.eCallInfo, eCallInfo);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.eCallInfo);
        }
    }
    public VehicleDataResult getECallInfo() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.eCallInfo);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.eCallInfo, e);
            }
        }
        return null;
    }
    public void setAirbagStatus(VehicleDataResult airbagStatus) {
        if (airbagStatus != null) {
            parameters.put(SubscribeVehicleDataResponse.airbagStatus, airbagStatus);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.airbagStatus);
        }
    }
    public VehicleDataResult getAirbagStatus() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.airbagStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.airbagStatus, e);
            }
        }
        return null;
    }
    public void setEmergencyEvent(VehicleDataResult emergencyEvent) {
        if (emergencyEvent != null) {
            parameters.put(SubscribeVehicleDataResponse.emergencyEvent, emergencyEvent);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.emergencyEvent);
        }
    }
    public VehicleDataResult getEmergencyEvent() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.emergencyEvent);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.emergencyEvent, e);
            }
        }
        return null;
    }
    public void setClusterModeStatus(VehicleDataResult clusterModeStatus) {
        if (clusterModeStatus != null) {
            parameters.put(SubscribeVehicleDataResponse.clusterModeStatus, clusterModeStatus);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.clusterModeStatus);
        }
    }
    public VehicleDataResult getClusterModeStatus() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.clusterModeStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.clusterModeStatus, e);
            }
        }
        return null;
    }
    public void setMyKey(VehicleDataResult myKey) {
        if (myKey != null) {
            parameters.put(SubscribeVehicleDataResponse.myKey, myKey);
        } else {
        	parameters.remove(SubscribeVehicleDataResponse.myKey);
        }
    }
    public VehicleDataResult getMyKey() {
    	Object obj = parameters.get(SubscribeVehicleDataResponse.myKey);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SubscribeVehicleDataResponse.myKey, e);
            }
        }
        return null;
    }       
}
