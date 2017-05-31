package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.util.DebugTool;

/**
 * Unsubscribe Vehicle Data Response is sent, when UnsubscribeVehicleData has been called.
 * 
 * @since SmartDeviceLink 2.0
 */
public class UnsubscribeVehicleDataResponse extends RPCResponse {
	public static final String KEY_SPEED = "speed";
	public static final String KEY_RPM = "rpm";
	public static final String KEY_FUEL_LEVEL = "fuelLevel";
	public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
	public static final String KEY_PRNDL = "prndl";
	public static final String KEY_TIRE_PRESSURE = "tirePressure";
	public static final String KEY_ENGINE_TORQUE = "engineTorque";
	public static final String KEY_ODOMETER = "odometer";
	public static final String KEY_GPS = "gps";
	public static final String KEY_FUEL_LEVEL_STATE = "fuelLevel_State";
	public static final String KEY_INSTANT_FUEL_CONSUMPTION = "instantFuelConsumption";
	public static final String KEY_BELT_STATUS = "beltStatus";
	public static final String KEY_BODY_INFORMATION = "bodyInformation";
	public static final String KEY_DEVICE_STATUS = "deviceStatus";
	public static final String KEY_DRIVER_BRAKING = "driverBraking";
	public static final String KEY_WIPER_STATUS = "wiperStatus";
	public static final String KEY_HEAD_LAMP_STATUS = "headLampStatus";
	public static final String KEY_ACC_PEDAL_POSITION = "accPedalPosition";
	public static final String KEY_STEERING_WHEEL_ANGLE = "steeringWheelAngle";
	public static final String KEY_E_CALL_INFO = "eCallInfo";
	public static final String KEY_AIRBAG_STATUS = "airbagStatus";
	public static final String KEY_EMERGENCY_EVENT = "emergencyEvent";
	public static final String KEY_CLUSTER_MODE_STATUS = "clusterModeStatus";
	public static final String KEY_MY_KEY = "myKey";

	/**
	 * Constructs a new UnsubscribeVehicleDataResponse object
	 */
    public UnsubscribeVehicleDataResponse() {
        super(FunctionID.UNSUBSCRIBE_VEHICLE_DATA.toString());
    }

	/**
	 * Constructs a new UnsubscribeVehicleDataResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public UnsubscribeVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Sets Gps
     * @param gps
     */
    public void setGps(VehicleDataResult gps) {
        setParameters(KEY_GPS, gps);
    }
    /**
     * Gets Gps
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getGps() {
    	Object obj = parameters.get(KEY_GPS);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_GPS, e);
            }
        }
        return null;
    }
    /**
     * Sets Speed
     * @param speed
     */
    public void setSpeed(VehicleDataResult speed) {
        setParameters(KEY_SPEED, speed);
    }
    /**
     * Gets Speed
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getSpeed() {
    	Object obj = parameters.get(KEY_SPEED);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_SPEED, e);
            }
        }
        return null;
    }
    /**
     * Sets rpm
     * @param rpm
     */
    public void setRpm(VehicleDataResult rpm) {
        setParameters(KEY_RPM, rpm);
    }
    /**
     * Gets rpm
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getRpm() {
    	Object obj = parameters.get(KEY_RPM);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_RPM, e);
            }
        }
        return null;
    }
    /**
     * Sets Fuel Level
     * @param fuelLevel
     */
    public void setFuelLevel(VehicleDataResult fuelLevel) {
        setParameters(KEY_FUEL_LEVEL, fuelLevel);
    }
    /**
     * Gets Fuel Level
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getFuelLevel() {
    	Object obj = parameters.get(KEY_FUEL_LEVEL);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_FUEL_LEVEL, e);
            }
        }
        return null;
    }
    /**
     * Sets Fuel Level State
     * @param fuelLevel_State
     */
    @Deprecated
    public void setFuelLevel_State(VehicleDataResult fuelLevel_State) {
        setFuelLevel(fuelLevel_State);
    }
    /**
     * Gets Fuel Level State
     * @return VehicleDataResult
     */
    @Deprecated
    public VehicleDataResult getFuelLevel_State() {
        return getFuelLevelState();
    }
    /**
     * Sets Fuel Level State
     * @param fuelLevel_State
     */
    public void setFuelLevelState(VehicleDataResult fuelLevelState) {
        setParameters(KEY_FUEL_LEVEL_STATE, fuelLevelState);
    }
    /**
     * Gets Fuel Level State
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getFuelLevelState() {
        Object obj = parameters.get(KEY_FUEL_LEVEL_STATE);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
            return new VehicleDataResult((Hashtable<String, Object>) obj);
        }
        return null;
    }
    /**
     * Sets Instant Fuel Comsumption
     * @param instantFuelConsumption
     */
    public void setInstantFuelConsumption(VehicleDataResult instantFuelConsumption) {
        setParameters(KEY_INSTANT_FUEL_CONSUMPTION, instantFuelConsumption);
    }
    /**
     * Gets Instant Fuel Comsumption
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getInstantFuelConsumption() {
    	Object obj = parameters.get(KEY_INSTANT_FUEL_CONSUMPTION);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_INSTANT_FUEL_CONSUMPTION, e);
            }
        }
        return null;
    }
    /**
     * Sets External Temperature
     * @param externalTemperature
     */
    public void setExternalTemperature(VehicleDataResult externalTemperature) {
        setParameters(KEY_EXTERNAL_TEMPERATURE, externalTemperature);
    }
    /**
     * Gets External Temperature
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getExternalTemperature() {
    	Object obj = parameters.get(KEY_EXTERNAL_TEMPERATURE);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_EXTERNAL_TEMPERATURE, e);
            }
        }
        return null;
    }
    /**
     * Gets currently selected gear data
     * @param prndl
     */
    public void setPrndl(VehicleDataResult prndl) {
        setParameters(KEY_PRNDL, prndl);
    }
    /**
     * Gets currently selected gear data
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getPrndl() {
    	Object obj = parameters.get(KEY_PRNDL);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_PRNDL, e);
            }
        }
        return null;
    }
    /**
     * Sets Tire Pressure
     * @param tirePressure
     */
    public void setTirePressure(VehicleDataResult tirePressure) {
        setParameters(KEY_TIRE_PRESSURE, tirePressure);
    }
    /**
     * Gets Tire Pressure
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getTirePressure() {
    	Object obj = parameters.get(KEY_TIRE_PRESSURE);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_TIRE_PRESSURE, e);
            }
        }
        return null;
    }
    /**
     * Sets Odometer
     * @param odometer
     */
    public void setOdometer(VehicleDataResult odometer) {
        setParameters(KEY_ODOMETER, odometer);
    }
    /**
     * Gets Odometer
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getOdometer() {
    	Object obj = parameters.get(KEY_ODOMETER);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_ODOMETER, e);
            }
        }
        return null;
    }
    /**
     * Sets Belt Status
     * @param beltStatus
     */
    public void setBeltStatus(VehicleDataResult beltStatus) {
        setParameters(KEY_BELT_STATUS, beltStatus);
    }
    /**
     * Gets Belt Status
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getBeltStatus() {
    	Object obj = parameters.get(KEY_BELT_STATUS);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_BELT_STATUS, e);
            }
        }
        return null;
    }
    /**
     * Sets Body Information
     * @param bodyInformation
     */
    public void setBodyInformation(VehicleDataResult bodyInformation) {
        setParameters(KEY_BODY_INFORMATION, bodyInformation);
    }
    /**
     * Gets Body Information
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getBodyInformation() {
    	Object obj = parameters.get(KEY_BODY_INFORMATION);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_BODY_INFORMATION, e);
            }
        }
        return null;
    }
    /**
     * Sets Device Status
     * @param deviceStatus
     */
    public void setDeviceStatus(VehicleDataResult deviceStatus) {
        setParameters(KEY_DEVICE_STATUS, deviceStatus);
    }
    /**
     * Gets Device Status
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getDeviceStatus() {
    	Object obj = parameters.get(KEY_DEVICE_STATUS);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_DEVICE_STATUS, e);
            }
        }
        return null;
    }
    /**
     * Sets Driver Braking
     * @param driverBraking
     */
    public void setDriverBraking(VehicleDataResult driverBraking) {
        setParameters(KEY_DRIVER_BRAKING, driverBraking);
    }
    /**
     * Gets Driver Braking
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getDriverBraking() {
    	Object obj = parameters.get(KEY_DRIVER_BRAKING);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_DRIVER_BRAKING, e);
            }
        }
        return null;
    }
    /**
     * Sets Wiper Status
     * @param wiperStatus
     */
    public void setWiperStatus(VehicleDataResult wiperStatus) {
        setParameters(KEY_WIPER_STATUS, wiperStatus);
    }
    /**
     * Gets Wiper Status
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getWiperStatus() {
    	Object obj = parameters.get(KEY_WIPER_STATUS);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_WIPER_STATUS, e);
            }
        }
        return null;
    }
    /**
     * Sets Head Lamp Status
     * @param headLampStatus
     */
    public void setHeadLampStatus(VehicleDataResult headLampStatus) {
        setParameters(KEY_HEAD_LAMP_STATUS, headLampStatus);
    }
    /**
     * Gets Head Lamp Status
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getHeadLampStatus() {
    	Object obj = parameters.get(KEY_HEAD_LAMP_STATUS);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_HEAD_LAMP_STATUS, e);
            }
        }
        return null;
    }
    /**
     * Sets Engine Torque
     * @param engineTorque
     */
    public void setEngineTorque(VehicleDataResult engineTorque) {
        setParameters(KEY_ENGINE_TORQUE, engineTorque);
    }
    /**
     * Gets Engine Torque
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getEngineTorque() {
    	Object obj = parameters.get(KEY_ENGINE_TORQUE);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_ENGINE_TORQUE, e);
            }
        }
        return null;
    }
    /**
     * Sets AccPedal Position
     * @param accPedalPosition
     */
    public void setAccPedalPosition(VehicleDataResult accPedalPosition) {
        setParameters(KEY_ACC_PEDAL_POSITION, accPedalPosition);
    }
    /**
     * Gets AccPedal Position
     * @return VehicleDataResult
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getAccPedalPosition() {
    	Object obj = parameters.get(KEY_ACC_PEDAL_POSITION);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_ACC_PEDAL_POSITION, e);
            }
        }
        return null;
    }  
    
    public void setSteeringWheelAngle(VehicleDataResult steeringWheelAngle) {
        setParameters(KEY_STEERING_WHEEL_ANGLE, steeringWheelAngle);
    }

    @SuppressWarnings("unchecked")
    public VehicleDataResult getSteeringWheelAngle() {
    	Object obj = parameters.get(KEY_STEERING_WHEEL_ANGLE);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_STEERING_WHEEL_ANGLE, e);
            }
        }
        return null;
    }    
    
    public void setECallInfo(VehicleDataResult eCallInfo) {
        setParameters(KEY_E_CALL_INFO, eCallInfo);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getECallInfo() {
    	Object obj = parameters.get(KEY_E_CALL_INFO);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_E_CALL_INFO, e);
            }
        }
        return null;
    }
    public void setAirbagStatus(VehicleDataResult airbagStatus) {
        setParameters(KEY_AIRBAG_STATUS, airbagStatus);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getAirbagStatus() {
    	Object obj = parameters.get(KEY_AIRBAG_STATUS);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_AIRBAG_STATUS, e);
            }
        }
        return null;
    }
    public void setEmergencyEvent(VehicleDataResult emergencyEvent) {
        setParameters(KEY_EMERGENCY_EVENT, emergencyEvent);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getEmergencyEvent() {
    	Object obj = parameters.get(KEY_EMERGENCY_EVENT);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_EMERGENCY_EVENT, e);
            }
        }
        return null;
    }
    public void setClusterModeStatus(VehicleDataResult clusterModeStatus) {
        setParameters(KEY_CLUSTER_MODE_STATUS, clusterModeStatus);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getClusterModeStatus() {
    	Object obj = parameters.get(KEY_CLUSTER_MODE_STATUS);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_CLUSTER_MODE_STATUS, e);
            }
        }
        return null;
    }
    public void setMyKey(VehicleDataResult myKey) {
        setParameters(KEY_MY_KEY, myKey);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getMyKey() {
    	Object obj = parameters.get(KEY_MY_KEY);
        if (obj instanceof VehicleDataResult) {
            return (VehicleDataResult) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new VehicleDataResult((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_MY_KEY, e);
            }
        }
        return null;
    }     
}
