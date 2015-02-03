package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;
import com.smartdevicelink.util.JsonUtils;

public class MyKey extends RPCObject {
    public static final String KEY_E_911_OVERRIDE = "e911Override";

    private String e911Override; // represents VehicleDataStatus enum
    
    public MyKey() { }
    
    /**
     * Creates a MyKey object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public MyKey(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.e911Override = JsonUtils.readStringFromJsonObject(jsonObject, KEY_E_911_OVERRIDE);
            break;
        }
    }

    public void setE911Override(VehicleDataStatus e911Override) {
        this.e911Override = (e911Override == null) ? null : e911Override.getJsonName(sdlVersion);
    }
    
    public VehicleDataStatus getE911Override() {
        return VehicleDataStatus.valueForJsonName(this.e911Override, sdlVersion);
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_E_911_OVERRIDE, this.e911Override);
            break;
        }
        
        return result;
    }
}
