package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.EmergencyEventType;
import com.smartdevicelink.proxy.rpc.enums.FuelCutoffStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.util.JsonUtils;

public class EmergencyEvent extends RPCObject {
    public static final String KEY_EMERGENCY_EVENT_TYPE = "emergencyEventType";
    public static final String KEY_FUEL_CUTOFF_STATUS = "fuelCutoffStatus";
    public static final String KEY_ROLLOVER_EVENT = "rolloverEvent";
    public static final String KEY_MAXIMUM_CHANGE_VELOCITY = "maximumChangeVelocity";
    public static final String KEY_MULTIPLE_EVENTS = "multipleEvents";

    private String emergencyEventType; // represents EmergencyEvent enum
    private String fuelCutoffStatus; // represents FuelCutoffStatus enum
    private String rolloverEvent, multipleEvents; // represents VehicleDataEventStatus enum
    private Integer maximumChangeVelocity;
    
    public EmergencyEvent() { }
    
    /**
     * Creates an EmergencyEvent object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public EmergencyEvent(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.emergencyEventType = JsonUtils.readStringFromJsonObject(jsonObject, KEY_EMERGENCY_EVENT_TYPE);
            this.fuelCutoffStatus = JsonUtils.readStringFromJsonObject(jsonObject, KEY_FUEL_CUTOFF_STATUS);
            this.rolloverEvent = JsonUtils.readStringFromJsonObject(jsonObject, KEY_ROLLOVER_EVENT);
            this.multipleEvents = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MULTIPLE_EVENTS);
            this.maximumChangeVelocity = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_MAXIMUM_CHANGE_VELOCITY);
            break;
        }
    }
    
    public void setEmergencyEventType(EmergencyEventType emergencyEventType) {
        this.emergencyEventType = (emergencyEventType == null) ? null : emergencyEventType.getJsonName(sdlVersion);
    }
    
    public EmergencyEventType getEmergencyEventType() {
        return EmergencyEventType.valueForJsonName(this.emergencyEventType, sdlVersion);
    }
    
    public void setFuelCutoffStatus(FuelCutoffStatus fuelCutoffStatus) {
        this.fuelCutoffStatus = (fuelCutoffStatus == null) ? null : fuelCutoffStatus.getJsonName(sdlVersion);
    }
    
    public FuelCutoffStatus getFuelCutoffStatus() {
        return FuelCutoffStatus.valueForJsonName(this.fuelCutoffStatus, sdlVersion);
    }
    
    public void setRolloverEvent(VehicleDataEventStatus rolloverEvent) {
        this.rolloverEvent = (rolloverEvent == null) ? null : rolloverEvent.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getRolloverEvent() {
        return VehicleDataEventStatus.valueForJsonName(this.rolloverEvent, sdlVersion);
    }
    
    public void setMaximumChangeVelocity(Integer maximumChangeVelocity) {
        this.maximumChangeVelocity = maximumChangeVelocity;
    }
    
    public Integer getMaximumChangeVelocity() {
    	return this.maximumChangeVelocity;
    }
    
    public void setMultipleEvents(VehicleDataEventStatus multipleEvents) {
        this.multipleEvents = (multipleEvents == null) ? null : multipleEvents.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getMultipleEvents() {
        return VehicleDataEventStatus.valueForJsonName(this.multipleEvents, sdlVersion);
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_EMERGENCY_EVENT_TYPE, this.emergencyEventType);
            JsonUtils.addToJsonObject(result, KEY_FUEL_CUTOFF_STATUS, this.fuelCutoffStatus);
            JsonUtils.addToJsonObject(result, KEY_MAXIMUM_CHANGE_VELOCITY, this.maximumChangeVelocity);
            JsonUtils.addToJsonObject(result, KEY_MULTIPLE_EVENTS, this.multipleEvents);
            JsonUtils.addToJsonObject(result, KEY_ROLLOVER_EVENT, this.rolloverEvent);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emergencyEventType == null) ? 0 : emergencyEventType.hashCode());
		result = prime * result + ((fuelCutoffStatus == null) ? 0 : fuelCutoffStatus.hashCode());
		result = prime * result + ((maximumChangeVelocity == null) ? 0 : maximumChangeVelocity.hashCode());
		result = prime * result + ((multipleEvents == null) ? 0 : multipleEvents.hashCode());
		result = prime * result + ((rolloverEvent == null) ? 0 : rolloverEvent.hashCode());
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
		EmergencyEvent other = (EmergencyEvent) obj;
		if (emergencyEventType == null) {
			if (other.emergencyEventType != null) { 
				return false;
			}
		} 
		else if (!emergencyEventType.equals(other.emergencyEventType)) { 
			return false;
		}
		if (fuelCutoffStatus == null) {
			if (other.fuelCutoffStatus != null) { 
				return false;
			}
		}
		else if (!fuelCutoffStatus.equals(other.fuelCutoffStatus)) { 
			return false;
		}
		if (maximumChangeVelocity == null) {
			if (other.maximumChangeVelocity != null) { 
				return false;
			}
		}
		else if (!maximumChangeVelocity.equals(other.maximumChangeVelocity)) { 
			return false;
		}
		if (multipleEvents == null) {
			if (other.multipleEvents != null) { 
				return false;
			}
		} 
		else if (!multipleEvents.equals(other.multipleEvents)) { 
			return false;
		}
		if (rolloverEvent == null) {
			if (other.rolloverEvent != null) { 
				return false;
			}
		}
		else if (!rolloverEvent.equals(other.rolloverEvent)) { 
			return false;
		}
		return true;
	}
}
