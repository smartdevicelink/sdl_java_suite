package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.AmbientLightStatus;
import com.smartdevicelink.util.JsonUtils;

public class HeadLampStatus extends RPCObject {
	public static final String KEY_AMBIENT_LIGHT_SENSOR_STATUS = "ambientLightSensorStatus";
	public static final String KEY_HIGH_BEAMS_ON = "highBeamsOn";
    public static final String KEY_LOW_BEAMS_ON = "lowBeamsOn";

    private String ambientLightStatus; // represents AmbientLightStatus enum
    private Boolean highBeamsOn, lowBeamsOn;
    
    public HeadLampStatus() {}
    
    /**
     * Creates a HeadLampStatus object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public HeadLampStatus(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.ambientLightStatus = JsonUtils.readStringFromJsonObject(jsonObject, KEY_AMBIENT_LIGHT_SENSOR_STATUS);
            this.highBeamsOn = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_HIGH_BEAMS_ON);
            this.lowBeamsOn = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_LOW_BEAMS_ON);
            break;
        }
    }
    
    public void setAmbientLightStatus(AmbientLightStatus ambientLightSensorStatus) {
        this.ambientLightStatus = (ambientLightSensorStatus == null) ? null : ambientLightSensorStatus.getJsonName(sdlVersion);
    }
    
    public AmbientLightStatus getAmbientLightStatus() {
        return AmbientLightStatus.valueForJsonName(this.ambientLightStatus, sdlVersion);
    }
    
    public void setHighBeamsOn(Boolean highBeamsOn) {
        this.highBeamsOn = highBeamsOn;
    }
    
    public Boolean getHighBeamsOn() {
    	return this.highBeamsOn;
    }
    
    public void setLowBeamsOn(Boolean lowBeamsOn) {
        this.lowBeamsOn = lowBeamsOn;
    }
    
    public Boolean getLowBeamsOn() {
    	return this.lowBeamsOn;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_AMBIENT_LIGHT_SENSOR_STATUS, this.ambientLightStatus);
            JsonUtils.addToJsonObject(result, KEY_HIGH_BEAMS_ON, this.highBeamsOn);
            JsonUtils.addToJsonObject(result, KEY_LOW_BEAMS_ON, this.lowBeamsOn);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ambientLightStatus == null) ? 0 : ambientLightStatus.hashCode());
		result = prime * result + ((highBeamsOn == null) ? 0 : highBeamsOn.hashCode());
		result = prime * result + ((lowBeamsOn == null) ? 0 : lowBeamsOn.hashCode());
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
		HeadLampStatus other = (HeadLampStatus) obj;
		if (ambientLightStatus == null) {
			if (other.ambientLightStatus != null) { 
				return false;
			}
		} else if (!ambientLightStatus.equals(other.ambientLightStatus)) { 
			return false;
		}
		if (highBeamsOn == null) {
			if (other.highBeamsOn != null) { 
				return false;
			}
		} else if (!highBeamsOn.equals(other.highBeamsOn)) { 
			return false;
		}
		if (lowBeamsOn == null) {
			if (other.lowBeamsOn != null) { 
				return false;
			}
		} else if (!lowBeamsOn.equals(other.lowBeamsOn)) { 
			return false;
		}
		return true;
	}
}
