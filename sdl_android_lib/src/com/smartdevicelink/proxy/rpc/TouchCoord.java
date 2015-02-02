package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

public class TouchCoord extends RPCObject {
    public static final String KEY_X = "x";
    public static final String KEY_Y = "y";
    
    private Integer x, y;
    
    public TouchCoord() {}
    
    /**
     * Creates a TouchCoord object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public TouchCoord(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.x = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_X);
            this.y = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_Y);
            break;
        }
    }
    
    public void setX(Integer x) {
        this.x = x;
    }
    
    public Integer getX() {
        return this.x;
    }
    
    public void setY(Integer y) {
        this.y = y;
    }
    
    public Integer getY() {
        return this.y;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_X, this.x);
            JsonUtils.addToJsonObject(result, KEY_Y, this.y);
            break;
        }
        
        return result;
    }
}
