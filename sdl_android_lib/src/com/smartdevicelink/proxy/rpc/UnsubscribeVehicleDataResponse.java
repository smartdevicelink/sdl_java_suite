package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.util.DebugTool;

/**
 * Unsubscribe Vehicle Data Response is sent, when UnsubscribeVehicleData has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class UnsubscribeVehicleDataResponse extends RPCResponse {
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
	 * Constructs a new UnsubscribeVehicleDataResponse object
	 */
    public UnsubscribeVehicleDataResponse() {
        super("UnsubscribeVehicleData");
    }

	/**
	 * Constructs a new UnsubscribeVehicleDataResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public UnsubscribeVehicleDataResponse(Hashtable hash) {
        super(hash);
    }
    /**
     * Sets Gps
     * @param gps
     */
    public void setGps(VehicleDataResult gps) {
        if (gps != null) {
            parameters.put(UnsubscribeVehicleDataResponse.gps, gps);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.gps);
        }
    }
    /**
     * Gets Gps
     * @return VehicleDataResult
     */
    public VehicleDataResult getGps() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.gps);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.gps, e);
            }
        }
        return null;
    }
    /**
     * Sets Speed
     * @param speed
     */
    public void setSpeed(VehicleDataResult speed) {
        if (speed != null) {
            parameters.put(UnsubscribeVehicleDataResponse.speed, speed);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.speed);
        }
    }
    /**
     * Gets Speed
     * @return VehicleDataResult
     */
    public VehicleDataResult getSpeed() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.speed);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.speed, e);
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
            parameters.put(UnsubscribeVehicleDataResponse.rpm, rpm);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.rpm);
        }
    }
    /**
     * Gets rpm
     * @return VehicleDataResult
     */
    public VehicleDataResult getRpm() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.rpm);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.rpm, e);
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
            parameters.put(UnsubscribeVehicleDataResponse.fuelLevel, fuelLevel);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.fuelLevel);
        }
    }
    /**
     * Gets Fuel Level
     * @return VehicleDataResult
     */
    public VehicleDataResult getFuelLevel() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.fuelLevel);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.fuelLevel, e);
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
            parameters.put(UnsubscribeVehicleDataResponse.fuelLevel_State, fuelLevel_State);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.fuelLevel_State);
        }
    }
    /**
     * Gets Fuel Level State
     * @return VehicleDataResult
     */
    public VehicleDataResult getFuelLevel_State() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.fuelLevel_State);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.fuelLevel_State, e);
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
            parameters.put(UnsubscribeVehicleDataResponse.instantFuelConsumption, instantFuelConsumption);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.instantFuelConsumption);
        }
    }
    /**
     * Gets Instant Fuel Comsumption
     * @return VehicleDataResult
     */
    public VehicleDataResult getInstantFuelConsumption() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.instantFuelConsumption);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.instantFuelConsumption, e);
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
            parameters.put(UnsubscribeVehicleDataResponse.externalTemperature, externalTemperature);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.externalTemperature);
        }
    }
    /**
     * Gets External Temperature
     * @return VehicleDataResult
     */
    public VehicleDataResult getExternalTemperature() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.externalTemperature);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.externalTemperature, e);
            }
        }
        return null;
    }
    /**
     * Gets currently selected gear data
     * @param prndl
     */
    public void setPrndl(VehicleDataResult prndl) {
        if (prndl != null) {
            parameters.put(UnsubscribeVehicleDataResponse.prndl, prndl);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.prndl);
        }
    }
    /**
     * Gets currently selected gear data
     * @return VehicleDataResult
     */
    public VehicleDataResult getPrndl() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.prndl);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.prndl, e);
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
            parameters.put(UnsubscribeVehicleDataResponse.tirePressure, tirePressure);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.tirePressure);
        }
    }
    /**
     * Gets Tire Pressure
     * @return VehicleDataResult
     */
    public VehicleDataResult getTirePressure() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.tirePressure);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.tirePressure, e);
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
            parameters.put(UnsubscribeVehicleDataResponse.odometer, odometer);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.odometer);
        }
    }
    /**
     * Gets Odometer
     * @return VehicleDataResult
     */
    public VehicleDataResult getOdometer() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.odometer);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.odometer, e);
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
            parameters.put(UnsubscribeVehicleDataResponse.beltStatus, beltStatus);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.beltStatus);
        }
    }
    /**
     * Gets Belt Status
     * @return VehicleDataResult
     */
    public VehicleDataResult getBeltStatus() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.beltStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.beltStatus, e);
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
            parameters.put(UnsubscribeVehicleDataResponse.bodyInformation, bodyInformation);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.bodyInformation);
        }
    }
    /**
     * Gets Body Information
     * @return VehicleDataResult
     */
    public VehicleDataResult getBodyInformation() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.bodyInformation);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.bodyInformation, e);
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
            parameters.put(UnsubscribeVehicleDataResponse.deviceStatus, deviceStatus);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.deviceStatus);
        }
    }
    /**
     * Gets Device Status
     * @return VehicleDataResult
     */
    public VehicleDataResult getDeviceStatus() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.deviceStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.deviceStatus, e);
            }
        }
        return null;
    }
    /**
     * Sets Driver Braking
     * @param driverBraking
     */
    public void setDriverBraking(VehicleDataResult driverBraking) {
        if (driverBraking != null) {
            parameters.put(UnsubscribeVehicleDataResponse.driverBraking, driverBraking);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.driverBraking);
        }
    }
    /**
     * Gets Driver Braking
     * @return VehicleDataResult
     */
    public VehicleDataResult getDriverBraking() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.driverBraking);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.driverBraking, e);
            }
        }
        return null;
    }
    /**
     * Sets Wiper Status
     * @param wiperStatus
     */
    public void setWiperStatus(VehicleDataResult wiperStatus) {
        if (wiperStatus != null) {
            parameters.put(UnsubscribeVehicleDataResponse.wiperStatus, wiperStatus);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.wiperStatus);
        }
    }
    /**
     * Gets Wiper Status
     * @return VehicleDataResult
     */
    public VehicleDataResult getWiperStatus() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.wiperStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.wiperStatus, e);
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
            parameters.put(UnsubscribeVehicleDataResponse.headLampStatus, headLampStatus);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.headLampStatus);
        }
    }
    /**
     * Gets Head Lamp Status
     * @return VehicleDataResult
     */
    public VehicleDataResult getHeadLampStatus() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.headLampStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.headLampStatus, e);
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
            parameters.put(UnsubscribeVehicleDataResponse.engineTorque, engineTorque);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.engineTorque);
        }
    }
    /**
     * Gets Engine Torque
     * @return VehicleDataResult
     */
    public VehicleDataResult getEngineTorque() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.engineTorque);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.engineTorque, e);
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
            parameters.put(UnsubscribeVehicleDataResponse.accPedalPosition, accPedalPosition);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.accPedalPosition);
        }
    }
    /**
     * Gets AccPedal Position
     * @return VehicleDataResult
     */
    public VehicleDataResult getAccPedalPosition() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.accPedalPosition);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.accPedalPosition, e);
            }
        }
        return null;
    }  
    
    public void setSteeringWheelAngle(VehicleDataResult steeringWheelAngle) {
        if (steeringWheelAngle != null) {
            parameters.put(UnsubscribeVehicleDataResponse.steeringWheelAngle, steeringWheelAngle);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.steeringWheelAngle);
        }
    }

    public VehicleDataResult getSteeringWheelAngle() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.steeringWheelAngle);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.steeringWheelAngle, e);
            }
        }
        return null;
    }    
    
    public void setECallInfo(VehicleDataResult eCallInfo) {
        if (eCallInfo != null) {
            parameters.put(UnsubscribeVehicleDataResponse.eCallInfo, eCallInfo);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.eCallInfo);
        }
    }
    public VehicleDataResult getECallInfo() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.eCallInfo);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.eCallInfo, e);
            }
        }
        return null;
    }
    public void setAirbagStatus(VehicleDataResult airbagStatus) {
        if (airbagStatus != null) {
            parameters.put(UnsubscribeVehicleDataResponse.airbagStatus, airbagStatus);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.airbagStatus);
        }
    }
    public VehicleDataResult getAirbagStatus() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.airbagStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.airbagStatus, e);
            }
        }
        return null;
    }
    public void setEmergencyEvent(VehicleDataResult emergencyEvent) {
        if (emergencyEvent != null) {
            parameters.put(UnsubscribeVehicleDataResponse.emergencyEvent, emergencyEvent);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.emergencyEvent);
        }
    }
    public VehicleDataResult getEmergencyEvent() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.emergencyEvent);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.emergencyEvent, e);
            }
        }
        return null;
    }
    public void setClusterModeStatus(VehicleDataResult clusterModeStatus) {
        if (clusterModeStatus != null) {
            parameters.put(UnsubscribeVehicleDataResponse.clusterModeStatus, clusterModeStatus);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.clusterModeStatus);
        }
    }
    public VehicleDataResult getClusterModeStatus() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.clusterModeStatus);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.clusterModeStatus, e);
            }
        }
        return null;
    }
    public void setMyKey(VehicleDataResult myKey) {
        if (myKey != null) {
            parameters.put(UnsubscribeVehicleDataResponse.myKey, myKey);
        } else {
        	parameters.remove(UnsubscribeVehicleDataResponse.myKey);
        }
    }
    public VehicleDataResult getMyKey() {
    	Object obj = parameters.get(UnsubscribeVehicleDataResponse.myKey);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + UnsubscribeVehicleDataResponse.myKey, e);
            }
        }
        return null;
    }     
}
