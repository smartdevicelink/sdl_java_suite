package com.smartdevicelink.proxy.rpc;


import java.util.Hashtable;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.proxy.rpc.enums.WiperStatus;
import com.smartdevicelink.util.DebugTool;

public class GetVehicleDataResponse extends RPCResponse {
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


    public GetVehicleDataResponse() {
        super("GetVehicleData");
    }
    public GetVehicleDataResponse(Hashtable hash) {
        super(hash);
    }
    public void setGps(GPSData gps) {
    	if (gps != null) {
    		parameters.put(GetVehicleDataResponse.gps, gps);
    	} else {
    		parameters.remove(GetVehicleDataResponse.gps);
    	}
    }
    public GPSData getGps() {
    	Object obj = parameters.get(GetVehicleDataResponse.gps);
        if (obj instanceof GPSData) {
            return (GPSData) obj;
        } else if (obj instanceof Hashtable) {
        	GPSData theCode = null;
            try {
                theCode = new GPSData((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.gps, e);
            }
            return theCode;
        }
        return null;
    }
    public void setSpeed(Double speed) {
    	if (speed != null) {
    		parameters.put(GetVehicleDataResponse.speed, speed);
    	} else {
    		parameters.remove(GetVehicleDataResponse.speed);
    	}
    }
    public Double getSpeed() {
    	return (Double) parameters.get(GetVehicleDataResponse.speed);
    }
    public void setRpm(Integer rpm) {
    	if (rpm != null) {
    		parameters.put(GetVehicleDataResponse.rpm, rpm);
    	} else {
    		parameters.remove(GetVehicleDataResponse.rpm);
    	}
    }
    public Integer getRpm() {
    	return (Integer) parameters.get(GetVehicleDataResponse.rpm);
    }
    public void setFuelLevel(Double fuelLevel) {
    	if (fuelLevel != null) {
    		parameters.put(GetVehicleDataResponse.fuelLevel, fuelLevel);
    	} else {
    		parameters.remove(GetVehicleDataResponse.fuelLevel);
    	}
    }
    public Double getFuelLevel() {
    	return (Double) parameters.get(GetVehicleDataResponse.fuelLevel);
    }
    public void setFuelLevel_State(ComponentVolumeStatus fuelLevel_State) {
    	if (fuelLevel_State != null) {
    		parameters.put(GetVehicleDataResponse.fuelLevel_State, fuelLevel_State);
    	} else {
    		parameters.remove(GetVehicleDataResponse.fuelLevel_State);
    	}
    }
    public ComponentVolumeStatus getFuelLevel_State() {
        Object obj = parameters.get(GetVehicleDataResponse.fuelLevel_State);
        if (obj instanceof ComponentVolumeStatus) {
            return (ComponentVolumeStatus) obj;
        } else if (obj instanceof String) {
        	ComponentVolumeStatus theCode = null;
            try {
                theCode = ComponentVolumeStatus.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.fuelLevel_State, e);
            }
            return theCode;
        }
        return null;
    }
    public void setInstantFuelConsumption(Double instantFuelConsumption) {
    	if (instantFuelConsumption != null) {
    		parameters.put(GetVehicleDataResponse.instantFuelConsumption, instantFuelConsumption);
    	} else {
    		parameters.remove(GetVehicleDataResponse.instantFuelConsumption);
    	}
    }
    public Double getInstantFuelConsumption() {
    	return (Double) parameters.get(GetVehicleDataResponse.instantFuelConsumption);
    }
    public void setExternalTemperature(Double externalTemperature) {
    	if (externalTemperature != null) {
    		parameters.put(GetVehicleDataResponse.externalTemperature, externalTemperature);
    	} else {
    		parameters.remove(GetVehicleDataResponse.externalTemperature);
    	}
    }
    public Double getExternalTemperature() {
    	return (Double) parameters.get(GetVehicleDataResponse.externalTemperature);
    }
    public void setVin(String vin) {
    	if (vin != null) {
    		parameters.put(GetVehicleDataResponse.vin, vin);
    	} else {
    		parameters.remove(GetVehicleDataResponse.vin);
    	}
    }
    public String getVin() {
    	return (String) parameters.get(GetVehicleDataResponse.vin);
    }
    public void setPrndl(PRNDL prndl) {
    	if (prndl != null) {
    		parameters.put(GetVehicleDataResponse.prndl, prndl);
    	} else {
    		parameters.remove(GetVehicleDataResponse.prndl);
    	}
    }
    public PRNDL getPrndl() {
        Object obj = parameters.get(GetVehicleDataResponse.prndl);
        if (obj instanceof PRNDL) {
            return (PRNDL) obj;
        } else if (obj instanceof String) {
        	PRNDL theCode = null;
            try {
                theCode = PRNDL.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.prndl, e);
            }
            return theCode;
        }
        return null;
    }
    public void setTirePressure(TireStatus tirePressure) {
    	if (tirePressure != null) {
    		parameters.put(GetVehicleDataResponse.tirePressure, tirePressure);
    	} else {
    		parameters.remove(GetVehicleDataResponse.tirePressure);
    	}
    }
    public TireStatus getTirePressure() {
    	Object obj = parameters.get(GetVehicleDataResponse.tirePressure);
        if (obj instanceof TireStatus) {
            return (TireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new TireStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.tirePressure, e);
            }
        }
        return null;
    }
    public void setOdometer(Integer odometer) {
    	if (odometer != null) {
    		parameters.put(GetVehicleDataResponse.odometer, odometer);
    	} else {
    		parameters.remove(GetVehicleDataResponse.odometer);
    	}
    }
    public Integer getOdometer() {
    	return (Integer) parameters.get(GetVehicleDataResponse.odometer);
    }
    public void setBeltStatus(BeltStatus beltStatus) {
        if (beltStatus != null) {
            parameters.put(GetVehicleDataResponse.beltStatus, beltStatus);
        } else {
        	parameters.remove(GetVehicleDataResponse.beltStatus);
        }
    }
    public BeltStatus getBeltStatus() {
    	Object obj = parameters.get(GetVehicleDataResponse.beltStatus);
        if (obj instanceof BeltStatus) {
            return (BeltStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new BeltStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.beltStatus, e);
            }
        }
        return null;
    }
    public void setBodyInformation(BodyInformation bodyInformation) {
        if (bodyInformation != null) {
            parameters.put(GetVehicleDataResponse.bodyInformation, bodyInformation);
        } else {
        	parameters.remove(GetVehicleDataResponse.bodyInformation);
        }
    }
    public BodyInformation getBodyInformation() {
    	Object obj = parameters.get(GetVehicleDataResponse.bodyInformation);
        if (obj instanceof BodyInformation) {
            return (BodyInformation) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new BodyInformation((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.bodyInformation, e);
            }
        }
        return null;
    }
    public void setDeviceStatus(DeviceStatus deviceStatus) {
        if (deviceStatus != null) {
            parameters.put(GetVehicleDataResponse.deviceStatus, deviceStatus);
        } else {
        	parameters.remove(GetVehicleDataResponse.deviceStatus);
        }
    }
    public DeviceStatus getDeviceStatus() {
    	Object obj = parameters.get(GetVehicleDataResponse.deviceStatus);
        if (obj instanceof DeviceStatus) {
            return (DeviceStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new DeviceStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.deviceStatus, e);
            }
        }
        return null;
    }
    public void setDriverBraking(VehicleDataEventStatus driverBraking) {
        if (driverBraking != null) {
            parameters.put(GetVehicleDataResponse.driverBraking, driverBraking);
        } else {
        	parameters.remove(GetVehicleDataResponse.driverBraking);
        }
    }
    public VehicleDataEventStatus getDriverBraking() {
        Object obj = parameters.get(GetVehicleDataResponse.driverBraking);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.driverBraking, e);
            }
            return theCode;
        }
        return null;
    }
    public void setWiperStatus(WiperStatus wiperStatus) {
        if (wiperStatus != null) {
            parameters.put(GetVehicleDataResponse.wiperStatus, wiperStatus);
        } else {
        	parameters.remove(GetVehicleDataResponse.wiperStatus);
        }
    }
    public WiperStatus getWiperStatus() {
        Object obj = parameters.get(GetVehicleDataResponse.wiperStatus);
        if (obj instanceof WiperStatus) {
            return (WiperStatus) obj;
        } else if (obj instanceof String) {
        	WiperStatus theCode = null;
            try {
                theCode = WiperStatus.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.wiperStatus, e);
            }
            return theCode;
        }
        return null;
    }
  
    public void setHeadLampStatus(HeadLampStatus headLampStatus) {
        if (headLampStatus != null) {
            parameters.put(GetVehicleDataResponse.headLampStatus, headLampStatus);
        } else {
        	parameters.remove(GetVehicleDataResponse.headLampStatus);
        }
    }
    public HeadLampStatus getHeadLampStatus() {
    	Object obj = parameters.get(GetVehicleDataResponse.headLampStatus);
        if (obj instanceof HeadLampStatus) {
            return (HeadLampStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new HeadLampStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.headLampStatus, e);
            }
        }
        return null;
    }
    public void setEngineTorque(Double engineTorque) {
        if (engineTorque != null) {
            parameters.put(GetVehicleDataResponse.engineTorque, engineTorque);
        } else {
        	parameters.remove(GetVehicleDataResponse.engineTorque);
        }
    }
    public Double getEngineTorque() {
    	return (Double) parameters.get(GetVehicleDataResponse.engineTorque);
    }

    public void setAccPedalPosition(Double accPedalPosition) {
        if (accPedalPosition != null) {
            parameters.put(GetVehicleDataResponse.accPedalPosition, accPedalPosition);
        } else {
        	parameters.remove(GetVehicleDataResponse.accPedalPosition);
        }
    }
    public Double getAccPedalPosition() {
    	return (Double) parameters.get(GetVehicleDataResponse.accPedalPosition);
    }
        
    public void setSteeringWheelAngle(Double steeringWheelAngle) {
        if (steeringWheelAngle != null) {
            parameters.put(GetVehicleDataResponse.steeringWheelAngle, steeringWheelAngle);
        } else {
        	parameters.remove(GetVehicleDataResponse.steeringWheelAngle);
        }
    }
    public Double getSteeringWheelAngle() {
    	return (Double) parameters.get(GetVehicleDataResponse.steeringWheelAngle);
    }    

    public void setECallInfo(ECallInfo eCallInfo) {
        if (eCallInfo != null) {
        	parameters.put(GetVehicleDataResponse.eCallInfo, eCallInfo);
        } else {
        	parameters.remove(GetVehicleDataResponse.eCallInfo);
        }
    }
    public ECallInfo getECallInfo() {
    	Object obj = parameters.get(GetVehicleDataResponse.eCallInfo);
        if (obj instanceof ECallInfo) {
            return (ECallInfo) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new ECallInfo((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.eCallInfo, e);
            }
        }
        return null;
    }	
	
    public void setAirbagStatus(AirbagStatus airbagStatus) {
        if (airbagStatus != null) {
        	parameters.put(GetVehicleDataResponse.airbagStatus, airbagStatus);
        } else {
        	parameters.remove(GetVehicleDataResponse.airbagStatus);
        }
    }
    public AirbagStatus getAirbagStatus() {
    	Object obj = parameters.get(GetVehicleDataResponse.airbagStatus);
        if (obj instanceof AirbagStatus) {
            return (AirbagStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new AirbagStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.airbagStatus, e);
            }
        }
        return null;
    }	

    public void setEmergencyEvent(EmergencyEvent emergencyEvent) {
        if (emergencyEvent != null) {
            parameters.put(GetVehicleDataResponse.emergencyEvent, emergencyEvent);
        } else {
        	parameters.remove(GetVehicleDataResponse.emergencyEvent);
        }
    }
    public EmergencyEvent getEmergencyEvent() {
    	Object obj = parameters.get(GetVehicleDataResponse.emergencyEvent);
        if (obj instanceof EmergencyEvent) {
            return (EmergencyEvent) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new EmergencyEvent((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.emergencyEvent, e);
            }
        }
        return null;
    }
    public void setClusterModeStatus(ClusterModeStatus clusterModeStatus) {
        if (clusterModeStatus != null) {
            parameters.put(GetVehicleDataResponse.clusterModeStatus, clusterModeStatus);
        } else {
        	parameters.remove(GetVehicleDataResponse.clusterModeStatus);
        }
    }
    public ClusterModeStatus getClusterModeStatus() {
    	Object obj = parameters.get(GetVehicleDataResponse.clusterModeStatus);
        if (obj instanceof ClusterModeStatus) {
            return (ClusterModeStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new ClusterModeStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.clusterModeStatus, e);
            }
        }
        return null;
    }
    public void setMyKey(MyKey myKey) {
        if (myKey != null) {
            parameters.put(GetVehicleDataResponse.myKey, myKey);
        } else {
        	parameters.remove(GetVehicleDataResponse.myKey);
        }
    }
    public MyKey getMyKey() {
    	Object obj = parameters.get(GetVehicleDataResponse.myKey);
        if (obj instanceof MyKey) {
            return (MyKey) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new MyKey((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GetVehicleDataResponse.myKey, e);
            }
        }
        return null;
    }        
}
