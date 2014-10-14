package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.proxy.rpc.enums.WiperStatus;
import com.smartdevicelink.util.DebugTool;

public class OnVehicleData extends RPCNotification {
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

    public OnVehicleData() {
        super("OnVehicleData");
    }
    public OnVehicleData(Hashtable hash) {
        super(hash);
    }
    public void setGps(GPSData gps) {
    	if (gps != null) {
    		parameters.put(OnVehicleData.gps, gps);
    	} else {
    		parameters.remove(OnVehicleData.gps);
    	}
    }
    public GPSData getGps() {
    	Object obj = parameters.get(OnVehicleData.gps);
        if (obj instanceof GPSData) {
            return (GPSData) obj;
        } else if (obj instanceof Hashtable) {
        	GPSData theCode = null;
            try {
                theCode = new GPSData((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.gps, e);
            }
            return theCode;
        }
        return null;
    }
    public void setSpeed(Double speed) {
    	if (speed != null) {
    		parameters.put(OnVehicleData.speed, speed);
    	} else {
    		parameters.remove(OnVehicleData.speed);
    	}
    }
    public Double getSpeed() {
    	return (Double) parameters.get(OnVehicleData.speed);
    }
    public void setRpm(Integer rpm) {
    	if (rpm != null) {
    		parameters.put(OnVehicleData.rpm, rpm);
    	} else {
    		parameters.remove(OnVehicleData.rpm);
    	}
    }
    public Integer getRpm() {
    	return (Integer) parameters.get(OnVehicleData.rpm);
    }
    public void setFuelLevel(Double fuelLevel) {
    	if (fuelLevel != null) {
    		parameters.put(OnVehicleData.fuelLevel, fuelLevel);
    	} else {
    		parameters.remove(OnVehicleData.fuelLevel);
    	}
    }
    public Double getFuelLevel() {
    	return (Double) parameters.get(OnVehicleData.fuelLevel);
    }
    public void setFuelLevel_State(ComponentVolumeStatus fuelLevel_State) {
    	if (fuelLevel_State != null) {
    		parameters.put(OnVehicleData.fuelLevel_State, fuelLevel_State);
    	} else {
    		parameters.remove(OnVehicleData.fuelLevel_State);
    	}
    }
    public ComponentVolumeStatus getFuelLevel_State() {
        Object obj = parameters.get(OnVehicleData.fuelLevel_State);
        if (obj instanceof ComponentVolumeStatus) {
            return (ComponentVolumeStatus) obj;
        } else if (obj instanceof String) {
        	ComponentVolumeStatus theCode = null;
            try {
                theCode = ComponentVolumeStatus.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.fuelLevel_State, e);
            }
            return theCode;
        }
        return null;
    }
    public void setInstantFuelConsumption(Double instantFuelConsumption) {
    	if (instantFuelConsumption != null) {
    		parameters.put(OnVehicleData.instantFuelConsumption, instantFuelConsumption);
    	} else {
    		parameters.remove(OnVehicleData.instantFuelConsumption);
    	}
    }
    public Double getInstantFuelConsumption() {
    	return (Double) parameters.get(OnVehicleData.instantFuelConsumption);
    }
    public void setExternalTemperature(Double externalTemperature) {
    	if (externalTemperature != null) {
    		parameters.put(OnVehicleData.externalTemperature, externalTemperature);
    	} else {
    		parameters.remove(OnVehicleData.externalTemperature);
    	}
    }
    public Double getExternalTemperature() {
    	return (Double) parameters.get(OnVehicleData.externalTemperature);
    }
    public void setVin(String vin) {
    	if (vin != null) {
    		parameters.put(OnVehicleData.vin, vin);
    	} else {
    		parameters.remove(OnVehicleData.vin);
    	}
    }
    public String getVin() {
    	return (String) parameters.get(OnVehicleData.vin);
    }
    public void setPrndl(PRNDL prndl) {
    	if (prndl != null) {
    		parameters.put(OnVehicleData.prndl, prndl);
    	} else {
    		parameters.remove(OnVehicleData.prndl);
    	}
    }
    public PRNDL getPrndl() {
        Object obj = parameters.get(OnVehicleData.prndl);
        if (obj instanceof PRNDL) {
            return (PRNDL) obj;
        } else if (obj instanceof String) {
        	PRNDL theCode = null;
            try {
                theCode = PRNDL.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.prndl, e);
            }
            return theCode;
        }
        return null;
    }
    public void setTirePressure(TireStatus tirePressure) {
    	if (tirePressure != null) {
    		parameters.put(OnVehicleData.tirePressure, tirePressure);
    	} else {
    		parameters.remove(OnVehicleData.tirePressure);
    	}
    }
    public TireStatus getTirePressure() {
    	Object obj = parameters.get(OnVehicleData.tirePressure);
        if (obj instanceof TireStatus) {
            return (TireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new TireStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.tirePressure, e);
            }
        }
        return null;
    }
    public void setOdometer(Integer odometer) {
    	if (odometer != null) {
    		parameters.put(OnVehicleData.odometer, odometer);
    	} else {
    		parameters.remove(OnVehicleData.odometer);
    	}
    }
    public Integer getOdometer() {
    	return (Integer) parameters.get(OnVehicleData.odometer);
    }
    public void setBeltStatus(BeltStatus beltStatus) {
        if (beltStatus != null) {
            parameters.put(OnVehicleData.beltStatus, beltStatus);
        } else {
        	parameters.remove(OnVehicleData.beltStatus);
        }
    }
    public BeltStatus getBeltStatus() {
    	Object obj = parameters.get(OnVehicleData.beltStatus);
        if (obj instanceof BeltStatus) {
            return (BeltStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new BeltStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.beltStatus, e);
            }
        }
        return null;
    }
    public void setBodyInformation(BodyInformation bodyInformation) {
        if (bodyInformation != null) {
            parameters.put(OnVehicleData.bodyInformation, bodyInformation);
        } else {
        	parameters.remove(OnVehicleData.bodyInformation);
        }
    }
    public BodyInformation getBodyInformation() {
    	Object obj = parameters.get(OnVehicleData.bodyInformation);
        if (obj instanceof BodyInformation) {
            return (BodyInformation) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new BodyInformation((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.bodyInformation, e);
            }
        }
        return null;
    }
    public void setDeviceStatus(DeviceStatus deviceStatus) {
        if (deviceStatus != null) {
            parameters.put(OnVehicleData.deviceStatus, deviceStatus);
        } else {
        	parameters.remove(OnVehicleData.deviceStatus);
        }
    }
    public DeviceStatus getDeviceStatus() {
    	Object obj = parameters.get(OnVehicleData.deviceStatus);
        if (obj instanceof DeviceStatus) {
            return (DeviceStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new DeviceStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.deviceStatus, e);
            }
        }
        return null;
    }
    public void setDriverBraking(VehicleDataEventStatus driverBraking) {
        if (driverBraking != null) {
            parameters.put(OnVehicleData.driverBraking, driverBraking);
        } else {
        	parameters.remove(OnVehicleData.driverBraking);
        }
    }
    public VehicleDataEventStatus getDriverBraking() {
        Object obj = parameters.get(OnVehicleData.driverBraking);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.driverBraking, e);
            }
            return theCode;
        }
        return null;
    }
    public void setWiperStatus(WiperStatus wiperStatus) {
        if (wiperStatus != null) {
            parameters.put(OnVehicleData.wiperStatus, wiperStatus);
        } else {
        	parameters.remove(OnVehicleData.wiperStatus);
        }
    }
    public WiperStatus getWiperStatus() {
        Object obj = parameters.get(OnVehicleData.wiperStatus);
        if (obj instanceof WiperStatus) {
            return (WiperStatus) obj;
        } else if (obj instanceof String) {
        	WiperStatus theCode = null;
            try {
                theCode = WiperStatus.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.wiperStatus, e);
            }
            return theCode;
        }
        return null;
    }
    public void setHeadLampStatus(HeadLampStatus headLampStatus) {
        if (headLampStatus != null) {
            parameters.put(OnVehicleData.headLampStatus, headLampStatus);
        } else {
        	parameters.remove(OnVehicleData.headLampStatus);
        }
    }
    public HeadLampStatus getHeadLampStatus() {
    	Object obj = parameters.get(OnVehicleData.headLampStatus);
        if (obj instanceof HeadLampStatus) {
            return (HeadLampStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new HeadLampStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.headLampStatus, e);
            }
        }
        return null;
    }
    public void setEngineTorque(Double engineTorque) {
        if (engineTorque != null) {
            parameters.put(OnVehicleData.engineTorque, engineTorque);
        } else {
        	parameters.remove(OnVehicleData.engineTorque);
        }
    }
    public Double getEngineTorque() {
    	return (Double) parameters.get(OnVehicleData.engineTorque);
    }
    public void setAccPedalPosition(Double accPedalPosition) {
        if (accPedalPosition != null) {
            parameters.put(OnVehicleData.accPedalPosition, accPedalPosition);
        } else {
        	parameters.remove(OnVehicleData.accPedalPosition);
        }
    }
    public Double getAccPedalPosition() {
    	return (Double) parameters.get(OnVehicleData.accPedalPosition);
    }
    public void setSteeringWheelAngle(Double steeringWheelAngle) {
        if (steeringWheelAngle != null) {
            parameters.put(OnVehicleData.steeringWheelAngle, steeringWheelAngle);
        } else {
        	parameters.remove(OnVehicleData.steeringWheelAngle);
        }
    }
    public Double getSteeringWheelAngle() {
    	return (Double) parameters.get(OnVehicleData.steeringWheelAngle);
    }
    public void setECallInfo(ECallInfo eCallInfo) {
        if (eCallInfo != null) {
            parameters.put(OnVehicleData.eCallInfo, eCallInfo);
        } else {
        	parameters.remove(OnVehicleData.eCallInfo);
        }
    }
    public ECallInfo getECallInfo() {
    	Object obj = parameters.get(OnVehicleData.eCallInfo);
        if (obj instanceof ECallInfo) {
            return (ECallInfo) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new ECallInfo((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.eCallInfo, e);
            }
        }
        return null;
    }
    public void setAirbagStatus(AirbagStatus airbagStatus) {
        if (airbagStatus != null) {
            parameters.put(OnVehicleData.airbagStatus, airbagStatus);
        } else {
        	parameters.remove(OnVehicleData.airbagStatus);
        }
    }
    public AirbagStatus getAirbagStatus() {
    	Object obj = parameters.get(OnVehicleData.airbagStatus);
        if (obj instanceof AirbagStatus) {
            return (AirbagStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new AirbagStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.airbagStatus, e);
            }
        }
        return null;
    }
    public void setEmergencyEvent(EmergencyEvent emergencyEvent) {
        if (emergencyEvent != null) {
            parameters.put(OnVehicleData.emergencyEvent, emergencyEvent);
        } else {
        	parameters.remove(OnVehicleData.emergencyEvent);
        }
    }
    public EmergencyEvent getEmergencyEvent() {
    	Object obj = parameters.get(OnVehicleData.emergencyEvent);
        if (obj instanceof EmergencyEvent) {
            return (EmergencyEvent) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new EmergencyEvent((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.emergencyEvent, e);
            }
        }
        return null;
    }
    public void setClusterModeStatus(ClusterModeStatus clusterModeStatus) {
        if (clusterModeStatus != null) {
            parameters.put(OnVehicleData.clusterModeStatus, clusterModeStatus);
        } else {
        	parameters.remove(OnVehicleData.clusterModeStatus);
        }
    }
    public ClusterModeStatus getClusterModeStatus() {
    	Object obj = parameters.get(OnVehicleData.clusterModeStatus);
        if (obj instanceof ClusterModeStatus) {
            return (ClusterModeStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new ClusterModeStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.clusterModeStatus, e);
            }
        }
        return null;
    }
    public void setMyKey(MyKey myKey) {
        if (myKey != null) {
            parameters.put(OnVehicleData.myKey, myKey);
        } else {
        	parameters.remove(OnVehicleData.myKey);
        }
    }
    public MyKey getMyKey() {
    	Object obj = parameters.get(OnVehicleData.myKey);
        if (obj instanceof MyKey) {
            return (MyKey) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new MyKey((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnVehicleData.myKey, e);
            }
        }
        return null;
    }    
}
