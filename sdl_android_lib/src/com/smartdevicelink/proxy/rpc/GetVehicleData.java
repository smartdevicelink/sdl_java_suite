package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

public class GetVehicleData extends RPCRequest {
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

	// TODO: refactor this class with new VehicleData design
	private Boolean speed, rpm, externalTemperature, fuelLevel, vin, prndl, tirePressure,
	    engineTorque, odometer, gps, fuelLevelState, instantFuelConsumption, beltStatus,
	    bodyInformation, deviceStatus, driverBraking, wiperStatus, headLampStatus,
	    accPedalPosition, steeringWheelAngle, eCallInfo, airbagStatus, emergencyEvent,
	    clusterModeStatus, myKey;
	
    public GetVehicleData() {
        super(FunctionID.GET_VEHICLE_DATA);
    }
    
    /**
     * Creates a GetVehicleData object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public GetVehicleData(JSONObject jsonObject) {
        super(SdlCommand.GET_VEHICLE_DATA, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.speed = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_SPEED);
            this.rpm = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_RPM);
            this.externalTemperature = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_EXTERNAL_TEMPERATURE);
            this.fuelLevel = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_FUEL_LEVEL);
            this.vin = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_VIN);
            this.prndl = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_PRNDL);
            this.tirePressure = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_TIRE_PRESSURE);
            this.engineTorque = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_ENGINE_TORQUE);
            this.odometer = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_ODOMETER);
            this.gps = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_GPS);
            this.fuelLevelState = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_FUEL_LEVEL_STATE);
            this.instantFuelConsumption = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_INSTANT_FUEL_CONSUMPTION);
            this.beltStatus = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_BELT_STATUS);
            this.bodyInformation = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_BODY_INFORMATION);
            this.deviceStatus = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_DEVICE_STATUS);
            this.driverBraking = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_DRIVER_BRAKING);
            this.wiperStatus = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_WIPER_STATUS);
            this.headLampStatus = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_HEAD_LAMP_STATUS);
            this.accPedalPosition = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_ACC_PEDAL_POSITION);
            this.steeringWheelAngle = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_STEERING_WHEEL_ANGLE);
            this.eCallInfo = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_E_CALL_INFO);
            this.airbagStatus = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_AIRBAG_STATUS);
            this.emergencyEvent = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_EMERGENCY_EVENT);
            this.clusterModeStatus = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_CLUSTER_MODE_STATUS);
            this.myKey = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_MY_KEY);
            break;
        }
    }
    
    public void setGps(Boolean gps) {
        this.gps = gps;
    }
    
    public Boolean getGps() {
        return this.gps;
    }
    
    public void setSpeed(Boolean speed) {
        this.speed = speed;
    }
    
    public Boolean getSpeed() {
        return this.speed;
    }
    
    public void setRpm(Boolean rpm) {
        this.rpm = rpm;
    }
    
    public Boolean getRpm() {
        return this.rpm;
    }
    
    public void setFuelLevel(Boolean fuelLevel) {
        this.fuelLevel = fuelLevel;
    }
    
    public Boolean getFuelLevel() {
        return this.fuelLevel;
    }
    
    public void setFuelLevel_State(Boolean fuelLevel_State) {
        this.fuelLevelState = fuelLevel_State;
    }
    
    public Boolean getFuelLevel_State() {
        return this.fuelLevelState;
    }
    
    public void setInstantFuelConsumption(Boolean instantFuelConsumption) {
        this.instantFuelConsumption = instantFuelConsumption;
    }
    
    public Boolean getInstantFuelConsumption() {
        return this.instantFuelConsumption;
    }
    
    public void setExternalTemperature(Boolean externalTemperature) {
        this.externalTemperature = externalTemperature;
    }
    
    public Boolean getExternalTemperature() {
        return this.externalTemperature;
    }
    
    public void setVin(Boolean vin) {
        this.vin = vin;
    }
    
    public Boolean getVin() {
        return this.vin;
    }
    
    public void setPrndl(Boolean prndl) {
        this.prndl = prndl;
    }
    
    public Boolean getPrndl() {
        return this.prndl;
    }
    
    public void setTirePressure(Boolean tirePressure) {
        this.tirePressure = tirePressure;
    }
    
    public Boolean getTirePressure() {
        return this.tirePressure;
    }
    
    public void setOdometer(Boolean odometer) {
        this.odometer = odometer;
    }
    
    public Boolean getOdometer() {
        return this.odometer;
    }
    
    public void setBeltStatus(Boolean beltStatus) {
        this.beltStatus = beltStatus;
    }
    
    public Boolean getBeltStatus() {
        return this.beltStatus;
    }
    
    public void setBodyInformation(Boolean bodyInformation) {
        this.bodyInformation = bodyInformation;
    }
    
    public Boolean getBodyInformation() {
        return this.bodyInformation;
    }
    
    public void setDeviceStatus(Boolean deviceStatus) {
        this.deviceStatus = deviceStatus;
    }
    
    public Boolean getDeviceStatus() {
        return this.deviceStatus;
    }
    
    public void setDriverBraking(Boolean driverBraking) {
        this.driverBraking = driverBraking;
    }
    
    public Boolean getDriverBraking() {
        return this.driverBraking;
    }
    
    public void setWiperStatus(Boolean wiperStatus) {
        this.wiperStatus = wiperStatus;
    }
    
    public Boolean getWiperStatus() {
        return this.wiperStatus;
    }
    
    public void setHeadLampStatus(Boolean headLampStatus) {
        this.headLampStatus = headLampStatus;
    }
    
    public Boolean getHeadLampStatus() {
        return this.headLampStatus;
    }
    
    public void setEngineTorque(Boolean engineTorque) {
        this.engineTorque = engineTorque;
    }
    
    public Boolean getEngineTorque() {
        return this.engineTorque;
    }
    
    public void setAccPedalPosition(Boolean accPedalPosition) {
        this.accPedalPosition = accPedalPosition;
    }
    
    public Boolean getAccPedalPosition() {
        return this.accPedalPosition;
    }
        
    public void setSteeringWheelAngle(Boolean steeringWheelAngle) {
        this.steeringWheelAngle = steeringWheelAngle;
    }
    
    public Boolean getSteeringWheelAngle() {
        return this.steeringWheelAngle;
    }
    
    public void setECallInfo(Boolean eCallInfo) {
        this.eCallInfo = eCallInfo;
    }
    
    public Boolean getECallInfo() {
        return this.eCallInfo;
    }
    
    public void setAirbagStatus(Boolean airbagStatus) {
        this.airbagStatus = airbagStatus;
    }
    
    public Boolean getAirbagStatus() {
        return this.airbagStatus;
    }
    
    public void setEmergencyEvent(Boolean emergencyEvent) {
        this.emergencyEvent = emergencyEvent;
    }
    
    public Boolean getEmergencyEvent() {
        return this.emergencyEvent;
    }
    
    public void setClusterModeStatus(Boolean clusterModeStatus) {
        this.clusterModeStatus = clusterModeStatus;
    }
    
    public Boolean getClusterModeStatus() {
        return this.clusterModeStatus;
    }
    
    public void setMyKey(Boolean myKey) {
        this.myKey = myKey;
    }
    
    public Boolean getMyKey() {
        return this.myKey;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_ACC_PEDAL_POSITION, this.accPedalPosition);
            JsonUtils.addToJsonObject(result, KEY_AIRBAG_STATUS, this.airbagStatus);
            JsonUtils.addToJsonObject(result, KEY_BELT_STATUS, this.beltStatus);
            JsonUtils.addToJsonObject(result, KEY_BODY_INFORMATION, this.bodyInformation);
            JsonUtils.addToJsonObject(result, KEY_CLUSTER_MODE_STATUS, this.clusterModeStatus);
            JsonUtils.addToJsonObject(result, KEY_DEVICE_STATUS, this.deviceStatus);
            JsonUtils.addToJsonObject(result, KEY_DRIVER_BRAKING, this.driverBraking);
            JsonUtils.addToJsonObject(result, KEY_E_CALL_INFO, this.eCallInfo);
            JsonUtils.addToJsonObject(result, KEY_EMERGENCY_EVENT, this.emergencyEvent);
            JsonUtils.addToJsonObject(result, KEY_ENGINE_TORQUE, this.engineTorque);
            JsonUtils.addToJsonObject(result, KEY_EXTERNAL_TEMPERATURE, this.externalTemperature);
            JsonUtils.addToJsonObject(result, KEY_FUEL_LEVEL, this.fuelLevel);
            JsonUtils.addToJsonObject(result, KEY_FUEL_LEVEL_STATE, this.fuelLevelState);
            JsonUtils.addToJsonObject(result, KEY_GPS, this.gps);
            JsonUtils.addToJsonObject(result, KEY_HEAD_LAMP_STATUS, this.headLampStatus);
            JsonUtils.addToJsonObject(result, KEY_INSTANT_FUEL_CONSUMPTION, this.instantFuelConsumption);
            JsonUtils.addToJsonObject(result, KEY_MY_KEY, this.myKey);
            JsonUtils.addToJsonObject(result, KEY_ODOMETER, this.odometer);
            JsonUtils.addToJsonObject(result, KEY_PRNDL, this.prndl);
            JsonUtils.addToJsonObject(result, KEY_RPM, this.rpm);
            JsonUtils.addToJsonObject(result, KEY_SPEED, this.speed);
            JsonUtils.addToJsonObject(result, KEY_STEERING_WHEEL_ANGLE, this.steeringWheelAngle);
            JsonUtils.addToJsonObject(result, KEY_TIRE_PRESSURE, this.tirePressure);
            JsonUtils.addToJsonObject(result, KEY_VIN, this.vin);
            JsonUtils.addToJsonObject(result, KEY_WIPER_STATUS, this.wiperStatus);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accPedalPosition == null) ? 0 : accPedalPosition.hashCode());
		result = prime * result + ((airbagStatus == null) ? 0 : airbagStatus.hashCode());
		result = prime * result + ((beltStatus == null) ? 0 : beltStatus.hashCode());
		result = prime * result + ((bodyInformation == null) ? 0 : bodyInformation.hashCode());
		result = prime * result + ((clusterModeStatus == null) ? 0 : clusterModeStatus.hashCode());
		result = prime * result + ((deviceStatus == null) ? 0 : deviceStatus.hashCode());
		result = prime * result + ((driverBraking == null) ? 0 : driverBraking.hashCode());
		result = prime * result + ((eCallInfo == null) ? 0 : eCallInfo.hashCode());
		result = prime * result + ((emergencyEvent == null) ? 0 : emergencyEvent.hashCode());
		result = prime * result + ((engineTorque == null) ? 0 : engineTorque.hashCode());
		result = prime * result + ((externalTemperature == null) ? 0 : externalTemperature.hashCode());
		result = prime * result + ((fuelLevel == null) ? 0 : fuelLevel.hashCode());
		result = prime * result + ((fuelLevelState == null) ? 0 : fuelLevelState.hashCode());
		result = prime * result + ((gps == null) ? 0 : gps.hashCode());
		result = prime * result + ((headLampStatus == null) ? 0 : headLampStatus.hashCode());
		result = prime * result + ((instantFuelConsumption == null) ? 0 : instantFuelConsumption.hashCode());
		result = prime * result + ((myKey == null) ? 0 : myKey.hashCode());
		result = prime * result + ((odometer == null) ? 0 : odometer.hashCode());
		result = prime * result + ((prndl == null) ? 0 : prndl.hashCode());
		result = prime * result + ((rpm == null) ? 0 : rpm.hashCode());
		result = prime * result + ((speed == null) ? 0 : speed.hashCode());
		result = prime * result + ((steeringWheelAngle == null) ? 0 : steeringWheelAngle.hashCode());
		result = prime * result + ((tirePressure == null) ? 0 : tirePressure.hashCode());
		result = prime * result + ((vin == null) ? 0 : vin.hashCode());
		result = prime * result + ((wiperStatus == null) ? 0 : wiperStatus.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		GetVehicleData other = (GetVehicleData) obj;
		if (accPedalPosition == null) {
			if (other.accPedalPosition != null) { 
				return false;
			}
		} 
		else if (!accPedalPosition.equals(other.accPedalPosition)) { 
			return false;
		}
		if (airbagStatus == null) {
			if (other.airbagStatus != null) { 
				return false;
			}
		} 
		else if (!airbagStatus.equals(other.airbagStatus)) { 
			return false;
		}
		if (beltStatus == null) {
			if (other.beltStatus != null) { 
				return false;
			}
		} 
		else if (!beltStatus.equals(other.beltStatus)) { 
			return false;
		}
		if (bodyInformation == null) {
			if (other.bodyInformation != null) { 
				return false;
			}
		} 
		else if (!bodyInformation.equals(other.bodyInformation)) { 
			return false;
		}
		if (clusterModeStatus == null) {
			if (other.clusterModeStatus != null) { 
				return false;
			}
		} 
		else if (!clusterModeStatus.equals(other.clusterModeStatus)) { 
			return false;
		}
		if (deviceStatus == null) {
			if (other.deviceStatus != null) { 
				return false;
			}
		}
		else if (!deviceStatus.equals(other.deviceStatus)) { 
			return false;
		}
		if (driverBraking == null) {
			if (other.driverBraking != null) { 
				return false;
			}
		} 
		else if (!driverBraking.equals(other.driverBraking)) { 
			return false;
		}
		if (eCallInfo == null) {
			if (other.eCallInfo != null) { 
				return false;
			}
		} 
		else if (!eCallInfo.equals(other.eCallInfo)) { 
			return false;
		}
		if (emergencyEvent == null) {
			if (other.emergencyEvent != null) { 
				return false;
			}
		} 
		else if (!emergencyEvent.equals(other.emergencyEvent)) { 
			return false;
		}
		if (engineTorque == null) {
			if (other.engineTorque != null) { 
				return false;
			}
		}
		else if (!engineTorque.equals(other.engineTorque)) { 
			return false;
		}
		if (externalTemperature == null) {
			if (other.externalTemperature != null) { 
				return false;
			}
		} 
		else if (!externalTemperature.equals(other.externalTemperature)) { 
			return false;
		}
		if (fuelLevel == null) {
			if (other.fuelLevel != null) { 
				return false;
			}
		}
		else if (!fuelLevel.equals(other.fuelLevel)) { 
			return false;
		}
		if (fuelLevelState == null) {
			if (other.fuelLevelState != null) { 
				return false;
			}
		} 
		else if (!fuelLevelState.equals(other.fuelLevelState)) { 
			return false;
		}
		if (gps == null) {
			if (other.gps != null) { 
				return false;
			}
		} 
		else if (!gps.equals(other.gps)) { 
			return false;
		}
		if (headLampStatus == null) {
			if (other.headLampStatus != null) { 
				return false;
			}
		} 
		else if (!headLampStatus.equals(other.headLampStatus)) { 
			return false;
		}
		if (instantFuelConsumption == null) {
			if (other.instantFuelConsumption != null) { 
				return false;
			}
		} 
		else if (!instantFuelConsumption.equals(other.instantFuelConsumption)) { 
			return false;
		}
		if (myKey == null) {
			if (other.myKey != null) { 
				return false;
			}
		} 
		else if (!myKey.equals(other.myKey)) { 
			return false;
		}
		if (odometer == null) {
			if (other.odometer != null) { 
				return false;
			}
		}
		else if (!odometer.equals(other.odometer)) { 
			return false;
		}
		if (prndl == null) {
			if (other.prndl != null) { 
				return false;
			}
		}
		else if (!prndl.equals(other.prndl)) { 
			return false;
		}
		if (rpm == null) {
			if (other.rpm != null) { 
				return false;
			}
		} 
		else if (!rpm.equals(other.rpm)) { 
			return false;
		}
		if (speed == null) {
			if (other.speed != null) { 
				return false;
			}
		} 
		else if (!speed.equals(other.speed)) { 
			return false;
		}
		if (steeringWheelAngle == null) {
			if (other.steeringWheelAngle != null) { 
				return false;
			}
		} 
		else if (!steeringWheelAngle.equals(other.steeringWheelAngle)) { 
			return false;
		}
		if (tirePressure == null) {
			if (other.tirePressure != null) { 
				return false;
			}
		}
		else if (!tirePressure.equals(other.tirePressure)) { 
			return false;
		}
		if (vin == null) {
			if (other.vin != null) { 
				return false;
			}
		}
		else if (!vin.equals(other.vin)) { 
			return false;
		}
		if (wiperStatus == null) {
			if (other.wiperStatus != null) { 
				return false;
			}
		} 
		else if (!wiperStatus.equals(other.wiperStatus)) { 
			return false;
		}
		return true;
	}
}
