package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.util.JsonUtils;

public class AirbagStatus extends RPCObject {
    public static final String KEY_DRIVER_AIRBAG_DEPLOYED = "driverAirbagDeployed";
    public static final String KEY_DRIVER_SIDE_AIRBAG_DEPLOYED = "driverSideAirbagDeployed";
    public static final String KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED = "driverCurtainAirbagDeployed";
    public static final String KEY_DRIVER_KNEE_AIRBAG_DEPLOYED = "driverKneeAirbagDeployed";
    public static final String KEY_PASSENGER_AIRBAG_DEPLOYED = "passengerAirbagDeployed";
    public static final String KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED = "passengerSideAirbagDeployed";
    public static final String KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED = "passengerCurtainAirbagDeployed";
    public static final String KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED = "passengerKneeAirbagDeployed";
    
    // TODO: refactor these strings into a HashMap<String, String> object and implement AirbagZone enum for different
    //       airbags.  This will allow us to loop and utilize a single API instead of having multiple setter/getters
    //       for the same type of data.  Similar to VehicleData re-design.
    private String driverAirbagDeployed, driverSideAirbagDeployed, driverCurtainAirbagDeployed,
        driverKneeAirbagDeployed, passengerAirbagDeployed, passengerSideAirbagDeployed, passengerCurtainAirbagDeployed,
        passengerKneeAirbagDeployed;
    
    public AirbagStatus() { }
    
    /**
     * Creates an AirbagStatus object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public AirbagStatus(JSONObject json){
        switch(sdlVersion){
        default:
            this.driverAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_DRIVER_AIRBAG_DEPLOYED);
            this.driverSideAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_DRIVER_SIDE_AIRBAG_DEPLOYED);
            this.driverCurtainAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED);
            this.driverKneeAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_DRIVER_KNEE_AIRBAG_DEPLOYED);
            this.passengerAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_PASSENGER_AIRBAG_DEPLOYED);
            this.passengerSideAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED);
            this.passengerCurtainAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED);
            this.passengerKneeAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED);
            break;
        }
    }

    public void setDriverAirbagDeployed(VehicleDataEventStatus driverAirbagDeployed) {
        this.driverAirbagDeployed = (driverAirbagDeployed == null) ? null : driverAirbagDeployed.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getDriverAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.driverAirbagDeployed, sdlVersion);
    }
    
    public void setDriverSideAirbagDeployed(VehicleDataEventStatus driverSideAirbagDeployed) {
        this.driverSideAirbagDeployed = (driverSideAirbagDeployed == null) ? null : driverSideAirbagDeployed.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getDriverSideAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.driverSideAirbagDeployed, sdlVersion);
    }
    
    public void setDriverCurtainAirbagDeployed(VehicleDataEventStatus driverCurtainAirbagDeployed) {
        this.driverCurtainAirbagDeployed = (driverCurtainAirbagDeployed == null) ? null : driverCurtainAirbagDeployed.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getDriverCurtainAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.driverCurtainAirbagDeployed, sdlVersion);
    }
    
    public void setPassengerAirbagDeployed(VehicleDataEventStatus passengerAirbagDeployed) {
        this.passengerAirbagDeployed = (passengerAirbagDeployed == null) ? null : passengerAirbagDeployed.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getPassengerAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.passengerAirbagDeployed, sdlVersion);
    }
    
    public void setPassengerCurtainAirbagDeployed(VehicleDataEventStatus passengerCurtainAirbagDeployed) {
        this.passengerCurtainAirbagDeployed = (passengerCurtainAirbagDeployed == null) ? null : passengerCurtainAirbagDeployed.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getPassengerCurtainAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.passengerCurtainAirbagDeployed, sdlVersion);
    }
    
    public void setDriverKneeAirbagDeployed(VehicleDataEventStatus driverKneeAirbagDeployed) {
        this.driverKneeAirbagDeployed = (driverKneeAirbagDeployed == null) ? null : driverKneeAirbagDeployed.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getDriverKneeAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.driverKneeAirbagDeployed, sdlVersion);
    }
    
    public void setPassengerSideAirbagDeployed(VehicleDataEventStatus passengerSideAirbagDeployed) {
        this.passengerSideAirbagDeployed = (passengerSideAirbagDeployed == null) ? null : passengerSideAirbagDeployed.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getPassengerSideAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.passengerSideAirbagDeployed, sdlVersion);
    }
    
    public void setPassengerKneeAirbagDeployed(VehicleDataEventStatus passengerKneeAirbagDeployed) {
        this.passengerKneeAirbagDeployed = (passengerKneeAirbagDeployed == null) ? null : passengerKneeAirbagDeployed.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getPassengerKneeAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.passengerKneeAirbagDeployed, sdlVersion);
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_DRIVER_AIRBAG_DEPLOYED, this.driverAirbagDeployed);
            JsonUtils.addToJsonObject(result, KEY_DRIVER_SIDE_AIRBAG_DEPLOYED, this.driverSideAirbagDeployed);
            JsonUtils.addToJsonObject(result, KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED, this.driverCurtainAirbagDeployed);
            JsonUtils.addToJsonObject(result, KEY_DRIVER_KNEE_AIRBAG_DEPLOYED, this.driverKneeAirbagDeployed);
            JsonUtils.addToJsonObject(result, KEY_PASSENGER_AIRBAG_DEPLOYED, this.passengerAirbagDeployed);
            JsonUtils.addToJsonObject(result, KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED, this.passengerSideAirbagDeployed);
            JsonUtils.addToJsonObject(result, KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED, this.passengerCurtainAirbagDeployed);
            JsonUtils.addToJsonObject(result, KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED, this.passengerKneeAirbagDeployed);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driverAirbagDeployed == null) ? 0 : driverAirbagDeployed.hashCode());
		result = prime * result + ((driverCurtainAirbagDeployed == null) ? 0 : driverCurtainAirbagDeployed.hashCode());
		result = prime * result + ((driverKneeAirbagDeployed == null) ? 0 : driverKneeAirbagDeployed.hashCode());
		result = prime * result + ((driverSideAirbagDeployed == null) ? 0 : driverSideAirbagDeployed.hashCode());
		result = prime * result + ((passengerAirbagDeployed == null) ? 0 : passengerAirbagDeployed.hashCode());
		result = prime * result + ((passengerCurtainAirbagDeployed == null) ? 0 : passengerCurtainAirbagDeployed.hashCode());
		result = prime * result + ((passengerKneeAirbagDeployed == null) ? 0 : passengerKneeAirbagDeployed.hashCode());
		result = prime * result + ((passengerSideAirbagDeployed == null) ? 0 : passengerSideAirbagDeployed.hashCode());
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
		AirbagStatus other = (AirbagStatus) obj;
		if (driverAirbagDeployed == null) {
			if (other.driverAirbagDeployed != null) { 
				return false;
			}
		} else if (!driverAirbagDeployed.equals(other.driverAirbagDeployed)) { 
			return false;
		}
		if (driverCurtainAirbagDeployed == null) {
			if (other.driverCurtainAirbagDeployed != null) { 
				return false;
			}
		} else if (!driverCurtainAirbagDeployed.equals(other.driverCurtainAirbagDeployed)) { 
			return false;
		}
		if (driverKneeAirbagDeployed == null) {
			if (other.driverKneeAirbagDeployed != null) { 
				return false;
			}
		} else if (!driverKneeAirbagDeployed.equals(other.driverKneeAirbagDeployed)) { 
			return false;
		}
		if (driverSideAirbagDeployed == null) {
			if (other.driverSideAirbagDeployed != null) { 
				return false;
			}
		} else if (!driverSideAirbagDeployed.equals(other.driverSideAirbagDeployed)) { 
			return false;
		}
		if (passengerAirbagDeployed == null) {
			if (other.passengerAirbagDeployed != null) { 
				return false;
			}
		} else if (!passengerAirbagDeployed.equals(other.passengerAirbagDeployed)) { 
			return false;
		}
		if (passengerCurtainAirbagDeployed == null) {
			if (other.passengerCurtainAirbagDeployed != null) { 
				return false;
			}
		} else if (!passengerCurtainAirbagDeployed.equals(other.passengerCurtainAirbagDeployed)) { 
			return false;
		}
		if (passengerKneeAirbagDeployed == null) {
			if (other.passengerKneeAirbagDeployed != null) { 
				return false;
			}
		} else if (!passengerKneeAirbagDeployed.equals(other.passengerKneeAirbagDeployed)) { 
			return false;
		}
		if (passengerSideAirbagDeployed == null) {
			if (other.passengerSideAirbagDeployed != null) { 
				return false;
			}
		} else if (!passengerSideAirbagDeployed.equals(other.passengerSideAirbagDeployed)) { 
			return false;
		}
		return true;
	}
}
