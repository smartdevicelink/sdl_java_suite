package com.smartdevicelink.proxy.rpc;


import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.proxy.rpc.enums.WiperStatus;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.SdlDataTypeConverter;

/**
 * Get Vehicle Data Response is sent, when GetVehicleData has been called.
 * 
 * @since SmartDeviceLink 2.0
 */
public class GetVehicleDataResponse extends RPCResponse {
	public static final String KEY_SPEED = "speed";
	public static final String KEY_RPM = "rpm";
	public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
	public static final String KEY_FUEL_LEVEL = "fuelLevel";
	public static final String KEY_VIN = "vin";
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
	 * Constructs a new GetVehicleDataResponse object
	 */

    public GetVehicleDataResponse() {
        super(FunctionID.GET_VEHICLE_DATA.toString());
    }
    public GetVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setGps(GPSData gps) {
    	if (gps != null) {
    		parameters.put(KEY_GPS, gps);
    	} else {
    		parameters.remove(KEY_GPS);
    	}
    }
    @SuppressWarnings("unchecked")
    public GPSData getGps() {
    	Object obj = parameters.get(KEY_GPS);
        if (obj instanceof GPSData) {
            return (GPSData) obj;
        } else if (obj instanceof Hashtable) {
        	GPSData theCode = null;
            try {
                theCode = new GPSData((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_GPS, e);
            }
            return theCode;
        }
        return null;
    }
    public void setSpeed(Double speed) {
    	if (speed != null) {
    		parameters.put(KEY_SPEED, speed);
    	} else {
    		parameters.remove(KEY_SPEED);
    	}
    }
    public Double getSpeed() {
    	Object object = parameters.get(KEY_SPEED);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    public void setRpm(Integer rpm) {
    	if (rpm != null) {
    		parameters.put(KEY_RPM, rpm);
    	} else {
    		parameters.remove(KEY_RPM);
    	}
    }
    public Integer getRpm() {
    	return (Integer) parameters.get(KEY_RPM);
    }
    public void setFuelLevel(Double fuelLevel) {
    	if (fuelLevel != null) {
    		parameters.put(KEY_FUEL_LEVEL, fuelLevel);
    	} else {
    		parameters.remove(KEY_FUEL_LEVEL);
    	}
    }
    public Double getFuelLevel() {
    	Object object = parameters.get(KEY_FUEL_LEVEL);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    @Deprecated
    public void setFuelLevel_State(ComponentVolumeStatus fuelLevel_State) {
        setFuelLevelState(fuelLevel_State);
    }
    @Deprecated
    public ComponentVolumeStatus getFuelLevel_State() {
        return getFuelLevelState();
    }
    public void setFuelLevelState(ComponentVolumeStatus fuelLevelState) {
        if (fuelLevelState != null) {
            parameters.put(KEY_FUEL_LEVEL_STATE, fuelLevelState);
        } else {
            parameters.remove(KEY_FUEL_LEVEL_STATE);
        }
    }
    public ComponentVolumeStatus getFuelLevelState() {
        Object obj = parameters.get(KEY_FUEL_LEVEL_STATE);
        if (obj instanceof ComponentVolumeStatus) {
            return (ComponentVolumeStatus) obj;
        } else if (obj instanceof String) {
            ComponentVolumeStatus theCode = null;
            try {
                theCode = ComponentVolumeStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_FUEL_LEVEL_STATE, e);
            }
            return theCode;
        }
        return null;
    }
    public void setInstantFuelConsumption(Double instantFuelConsumption) {
    	if (instantFuelConsumption != null) {
    		parameters.put(KEY_INSTANT_FUEL_CONSUMPTION, instantFuelConsumption);
    	} else {
    		parameters.remove(KEY_INSTANT_FUEL_CONSUMPTION);
    	}
    }
    public Double getInstantFuelConsumption() {
    	Object object = parameters.get(KEY_INSTANT_FUEL_CONSUMPTION);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    public void setExternalTemperature(Double externalTemperature) {
    	if (externalTemperature != null) {
    		parameters.put(KEY_EXTERNAL_TEMPERATURE, externalTemperature);
    	} else {
    		parameters.remove(KEY_EXTERNAL_TEMPERATURE);
    	}
    }
    public Double getExternalTemperature() {
    	Object object = parameters.get(KEY_EXTERNAL_TEMPERATURE);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    public void setVin(String vin) {
    	if (vin != null) {
    		parameters.put(KEY_VIN, vin);
    	} else {
    		parameters.remove(KEY_VIN);
    	}
    }
    public String getVin() {
    	return (String) parameters.get(KEY_VIN);
    }
    public void setPrndl(PRNDL prndl) {
    	if (prndl != null) {
    		parameters.put(KEY_PRNDL, prndl);
    	} else {
    		parameters.remove(KEY_PRNDL);
    	}
    }
    public PRNDL getPrndl() {
        Object obj = parameters.get(KEY_PRNDL);
        if (obj instanceof PRNDL) {
            return (PRNDL) obj;
        } else if (obj instanceof String) {
        	return PRNDL.valueForString((String) obj);
        }
        return null;
    }
    public void setTirePressure(TireStatus tirePressure) {
    	if (tirePressure != null) {
    		parameters.put(KEY_TIRE_PRESSURE, tirePressure);
    	} else {
    		parameters.remove(KEY_TIRE_PRESSURE);
    	}
    }
    @SuppressWarnings("unchecked")
    public TireStatus getTirePressure() {
    	Object obj = parameters.get(KEY_TIRE_PRESSURE);
        if (obj instanceof TireStatus) {
            return (TireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new TireStatus((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_TIRE_PRESSURE, e);
            }
        }
        return null;
    }
    public void setOdometer(Integer odometer) {
    	if (odometer != null) {
    		parameters.put(KEY_ODOMETER, odometer);
    	} else {
    		parameters.remove(KEY_ODOMETER);
    	}
    }
    public Integer getOdometer() {
    	return (Integer) parameters.get(KEY_ODOMETER);
    }
    public void setBeltStatus(BeltStatus beltStatus) {
        if (beltStatus != null) {
            parameters.put(KEY_BELT_STATUS, beltStatus);
        } else {
        	parameters.remove(KEY_BELT_STATUS);
        }
    }
    @SuppressWarnings("unchecked")
    public BeltStatus getBeltStatus() {
    	Object obj = parameters.get(KEY_BELT_STATUS);
        if (obj instanceof BeltStatus) {
            return (BeltStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new BeltStatus((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_BELT_STATUS, e);
            }
        }
        return null;
    }
    public void setBodyInformation(BodyInformation bodyInformation) {
        if (bodyInformation != null) {
            parameters.put(KEY_BODY_INFORMATION, bodyInformation);
        } else {
        	parameters.remove(KEY_BODY_INFORMATION);
        }
    }
    @SuppressWarnings("unchecked")
    public BodyInformation getBodyInformation() {
    	Object obj = parameters.get(KEY_BODY_INFORMATION);
        if (obj instanceof BodyInformation) {
            return (BodyInformation) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new BodyInformation((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_BODY_INFORMATION, e);
            }
        }
        return null;
    }
    public void setDeviceStatus(DeviceStatus deviceStatus) {
        if (deviceStatus != null) {
            parameters.put(KEY_DEVICE_STATUS, deviceStatus);
        } else {
        	parameters.remove(KEY_DEVICE_STATUS);
        }
    }
    @SuppressWarnings("unchecked")
    public DeviceStatus getDeviceStatus() {
    	Object obj = parameters.get(KEY_DEVICE_STATUS);
        if (obj instanceof DeviceStatus) {
            return (DeviceStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new DeviceStatus((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_DEVICE_STATUS, e);
            }
        }
        return null;
    }
    public void setDriverBraking(VehicleDataEventStatus driverBraking) {
        if (driverBraking != null) {
            parameters.put(KEY_DRIVER_BRAKING, driverBraking);
        } else {
        	parameters.remove(KEY_DRIVER_BRAKING);
        }
    }
    public VehicleDataEventStatus getDriverBraking() {
        Object obj = parameters.get(KEY_DRIVER_BRAKING);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setWiperStatus(WiperStatus wiperStatus) {
        if (wiperStatus != null) {
            parameters.put(KEY_WIPER_STATUS, wiperStatus);
        } else {
        	parameters.remove(KEY_WIPER_STATUS);
        }
    }
    public WiperStatus getWiperStatus() {
        Object obj = parameters.get(KEY_WIPER_STATUS);
        if (obj instanceof WiperStatus) {
            return (WiperStatus) obj;
        } else if (obj instanceof String) {
        	return WiperStatus.valueForString((String) obj);
        }
        return null;
    }
  
    public void setHeadLampStatus(HeadLampStatus headLampStatus) {
        if (headLampStatus != null) {
            parameters.put(KEY_HEAD_LAMP_STATUS, headLampStatus);
        } else {
        	parameters.remove(KEY_HEAD_LAMP_STATUS);
        }
    }
    @SuppressWarnings("unchecked")
    public HeadLampStatus getHeadLampStatus() {
    	Object obj = parameters.get(KEY_HEAD_LAMP_STATUS);
        if (obj instanceof HeadLampStatus) {
            return (HeadLampStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new HeadLampStatus((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_HEAD_LAMP_STATUS, e);
            }
        }
        return null;
    }
    public void setEngineTorque(Double engineTorque) {
        if (engineTorque != null) {
            parameters.put(KEY_ENGINE_TORQUE, engineTorque);
        } else {
        	parameters.remove(KEY_ENGINE_TORQUE);
        }
    }
    public Double getEngineTorque() {
    	Object object = parameters.get(KEY_ENGINE_TORQUE);
    	return SdlDataTypeConverter.objectToDouble(object);
    }

    public void setAccPedalPosition(Double accPedalPosition) {
        if (accPedalPosition != null) {
            parameters.put(KEY_ACC_PEDAL_POSITION, accPedalPosition);
        } else {
        	parameters.remove(KEY_ACC_PEDAL_POSITION);
        }
    }
    public Double getAccPedalPosition() {
    	Object object = parameters.get(KEY_ACC_PEDAL_POSITION);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
        
    public void setSteeringWheelAngle(Double steeringWheelAngle) {
        if (steeringWheelAngle != null) {
            parameters.put(KEY_STEERING_WHEEL_ANGLE, steeringWheelAngle);
        } else {
        	parameters.remove(KEY_STEERING_WHEEL_ANGLE);
        }
    }
    public Double getSteeringWheelAngle() {
    	Object object = parameters.get(KEY_STEERING_WHEEL_ANGLE);
    	return SdlDataTypeConverter.objectToDouble(object);
    }    

    public void setECallInfo(ECallInfo eCallInfo) {
        if (eCallInfo != null) {
        	parameters.put(KEY_E_CALL_INFO, eCallInfo);
        } else {
        	parameters.remove(KEY_E_CALL_INFO);
        }
    }
    @SuppressWarnings("unchecked")
    public ECallInfo getECallInfo() {
    	Object obj = parameters.get(KEY_E_CALL_INFO);
        if (obj instanceof ECallInfo) {
            return (ECallInfo) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new ECallInfo((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_E_CALL_INFO, e);
            }
        }
        return null;
    }	
	
    public void setAirbagStatus(AirbagStatus airbagStatus) {
        if (airbagStatus != null) {
        	parameters.put(KEY_AIRBAG_STATUS, airbagStatus);
        } else {
        	parameters.remove(KEY_AIRBAG_STATUS);
        }
    }
    @SuppressWarnings("unchecked")
    public AirbagStatus getAirbagStatus() {
    	Object obj = parameters.get(KEY_AIRBAG_STATUS);
        if (obj instanceof AirbagStatus) {
            return (AirbagStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new AirbagStatus((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_AIRBAG_STATUS, e);
            }
        }
        return null;
    }	

    public void setEmergencyEvent(EmergencyEvent emergencyEvent) {
        if (emergencyEvent != null) {
            parameters.put(KEY_EMERGENCY_EVENT, emergencyEvent);
        } else {
        	parameters.remove(KEY_EMERGENCY_EVENT);
        }
    }
    @SuppressWarnings("unchecked")
    public EmergencyEvent getEmergencyEvent() {
    	Object obj = parameters.get(KEY_EMERGENCY_EVENT);
        if (obj instanceof EmergencyEvent) {
            return (EmergencyEvent) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new EmergencyEvent((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_EMERGENCY_EVENT, e);
            }
        }
        return null;
    }
    public void setClusterModeStatus(ClusterModeStatus clusterModeStatus) {
        if (clusterModeStatus != null) {
            parameters.put(KEY_CLUSTER_MODE_STATUS, clusterModeStatus);
        } else {
        	parameters.remove(KEY_CLUSTER_MODE_STATUS);
        }
    }
    @SuppressWarnings("unchecked")
    public ClusterModeStatus getClusterModeStatus() {
    	Object obj = parameters.get(KEY_CLUSTER_MODE_STATUS);
        if (obj instanceof ClusterModeStatus) {
            return (ClusterModeStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new ClusterModeStatus((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_CLUSTER_MODE_STATUS, e);
            }
        }
        return null;
    }
    public void setMyKey(MyKey myKey) {
        if (myKey != null) {
            parameters.put(KEY_MY_KEY, myKey);
        } else {
        	parameters.remove(KEY_MY_KEY);
        }
    }
    @SuppressWarnings("unchecked")
    public MyKey getMyKey() {
    	Object obj = parameters.get(KEY_MY_KEY);
        if (obj instanceof MyKey) {
            return (MyKey) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new MyKey((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_MY_KEY, e);
            }
        }
        return null;
    }        
}
