package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

public class TouchEventCapabilities extends RPCObject {
    public static final String KEY_PRESS_AVAILABLE = "pressAvailable";
    public static final String KEY_MULTI_TOUCH_AVAILABLE = "multiTouchAvailable";
    public static final String KEY_DOUBLE_PRESS_AVAILABLE = "doublePressAvailable";
    
    private Boolean pressAvailable, doublePressAvailable, multiTouchAvailable;
    
    public TouchEventCapabilities() {}
    
    /**
     * Creates a TouchEventCapabilities object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public TouchEventCapabilities(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.pressAvailable = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_PRESS_AVAILABLE);
            this.doublePressAvailable = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_DOUBLE_PRESS_AVAILABLE);
            this.multiTouchAvailable = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_MULTI_TOUCH_AVAILABLE);
            break;
        }
    }
    
    public void setPressAvailable(Boolean pressAvailable) {
        this.pressAvailable = pressAvailable;
    }
    
    public Boolean getPressAvailable() {
        return this.pressAvailable;
    }
    
    public void setMultiTouchAvailable(Boolean multiTouchAvailable) {
        this.multiTouchAvailable = multiTouchAvailable;
    }
    
    public Boolean getMultiTouchAvailable() {
        return this.multiTouchAvailable;
    }
    
    public void setDoublePressAvailable(Boolean doublePressAvailable) {
        this.doublePressAvailable = doublePressAvailable;
    }
    
    public Boolean getDoublePressAvailable() {
        return this.doublePressAvailable;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_PRESS_AVAILABLE, this.pressAvailable);
            JsonUtils.addToJsonObject(result, KEY_DOUBLE_PRESS_AVAILABLE, this.doublePressAvailable);
            JsonUtils.addToJsonObject(result, KEY_MULTI_TOUCH_AVAILABLE, this.multiTouchAvailable);
            break;
        }
        
        return result;
    }
}
