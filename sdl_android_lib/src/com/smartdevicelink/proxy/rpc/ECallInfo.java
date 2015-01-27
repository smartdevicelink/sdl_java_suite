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
        this.eCallNotificationStatus = eCallNotificationStatus.getJsonName(sdlVersion);
    }
    
    public VehicleDataNotificationStatus getECallNotificationStatus() {
        return VehicleDataNotificationStatus.valueForJsonName(this.eCallNotificationStatus, sdlVersion);
    }
    
    public void setAuxECallNotificationStatus(VehicleDataNotificationStatus auxECallNotificationStatus) {
        this.auxECallNotificationStatus = auxECallNotificationStatus.getJsonName(sdlVersion);
    }
    
    public VehicleDataNotificationStatus getAuxECallNotificationStatus() {
        return VehicleDataNotificationStatus.valueForJsonName(this.auxECallNotificationStatus, sdlVersion);
    }
    
    public void setECallConfirmationStatus(ECallConfirmationStatus eCallConfirmationStatus) {
        this.eCallConfirmationstatus = eCallConfirmationStatus.getJsonName(sdlVersion);
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
}
