package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.ECallConfirmationStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataNotificationStatus;
import com.smartdevicelink.util.JsonUtils;

public class ECallInfo extends RPCObject {
    public static final String KEY_E_CALL_NOTIFICATION_STATUS = "eCallNotificationStatus";
    public static final String KEY_AUX_E_CALL_NOTIFICATION_STATUS = "auxECallNotificationStatus";
    public static final String KEY_E_CALL_CONFIRMATION_STATUS = "eCallConfirmationStatus";

    private String eCallNotificationStatus, auxECallNotificationStatus; // represents VehicleDataNotificationStatus enum
    private String eCallConfirmationstatus; // represents ECallConfirmationStatus enum
    
    public ECallInfo() { }
    
    /**
     * Creates an ECallInfo object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ECallInfo(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.eCallNotificationStatus = JsonUtils.readStringFromJsonObject(jsonObject, KEY_E_CALL_NOTIFICATION_STATUS);
            this.auxECallNotificationStatus = JsonUtils.readStringFromJsonObject(jsonObject, KEY_AUX_E_CALL_NOTIFICATION_STATUS);
            this.eCallConfirmationstatus = JsonUtils.readStringFromJsonObject(jsonObject, KEY_E_CALL_CONFIRMATION_STATUS);
            break;
        }
    }

    public void setECallNotificationStatus(VehicleDataNotificationStatus eCallNotificationStatus) {
        this.eCallNotificationStatus = (eCallNotificationStatus == null) ? null : eCallNotificationStatus.getJsonName(sdlVersion);
    }
    
    public VehicleDataNotificationStatus getECallNotificationStatus() {
        return VehicleDataNotificationStatus.valueForJsonName(this.eCallNotificationStatus, sdlVersion);
    }
    
    public void setAuxECallNotificationStatus(VehicleDataNotificationStatus auxECallNotificationStatus) {
        this.auxECallNotificationStatus = (auxECallNotificationStatus == null) ? null : auxECallNotificationStatus.getJsonName(sdlVersion);
    }
    
    public VehicleDataNotificationStatus getAuxECallNotificationStatus() {
        return VehicleDataNotificationStatus.valueForJsonName(this.auxECallNotificationStatus, sdlVersion);
    }
    
    public void setECallConfirmationStatus(ECallConfirmationStatus eCallConfirmationStatus) {
        this.eCallConfirmationstatus = (eCallConfirmationStatus == null) ? null : eCallConfirmationStatus.getJsonName(sdlVersion);
    }
    
    public ECallConfirmationStatus getECallConfirmationStatus() {
        return ECallConfirmationStatus.valueForJsonName(this.eCallConfirmationstatus, sdlVersion);
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_AUX_E_CALL_NOTIFICATION_STATUS, this.auxECallNotificationStatus);
            JsonUtils.addToJsonObject(result, KEY_E_CALL_CONFIRMATION_STATUS, this.eCallConfirmationstatus);
            JsonUtils.addToJsonObject(result, KEY_E_CALL_NOTIFICATION_STATUS, this.eCallNotificationStatus);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auxECallNotificationStatus == null) ? 0 : auxECallNotificationStatus.hashCode());
		result = prime * result + ((eCallConfirmationstatus == null) ? 0 : eCallConfirmationstatus.hashCode());
		result = prime * result + ((eCallNotificationStatus == null) ? 0 : eCallNotificationStatus.hashCode());
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
		ECallInfo other = (ECallInfo) obj;
		if (auxECallNotificationStatus == null) {
			if (other.auxECallNotificationStatus != null) { 
				return false;
			}
		} else if (!auxECallNotificationStatus.equals(other.auxECallNotificationStatus)) { 
			return false;
		}
		if (eCallConfirmationstatus == null) {
			if (other.eCallConfirmationstatus != null) { 
				return false;
			}
		} else if (!eCallConfirmationstatus.equals(other.eCallConfirmationstatus)) { 
			return false;
		}
		if (eCallNotificationStatus == null) {
			if (other.eCallNotificationStatus != null) { 
				return false;
			}
		} else if (!eCallNotificationStatus.equals(other.eCallNotificationStatus)) { 
			return false;
		}
		return true;
	}
}
