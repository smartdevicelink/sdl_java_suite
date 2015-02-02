package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

public class TouchEvent extends RPCObject {
    public static final String KEY_ID = "id";
    public static final String KEY_TS = "ts";
    public static final String KEY_C = "c";
    
    private Integer id;
    private List<Integer> timeStamps;
    private List<TouchCoord> touchCoords;
    
    public TouchEvent() { }
    
    /**
     * Creates a TouchEvent object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public TouchEvent(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.id = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_ID);
            this.timeStamps = JsonUtils.readIntegerListFromJsonObject(jsonObject, KEY_TS);
            
            List<JSONObject> touchCoordObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_C);
            if(touchCoordObjs != null){
                this.touchCoords = new ArrayList<TouchCoord>(touchCoordObjs.size());
                for(JSONObject touchCoordObj : touchCoordObjs){
                    this.touchCoords.add(new TouchCoord(touchCoordObj));
                }
            }
            break;
        }
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public List<Integer> getTs() {
    	return this.timeStamps;
    }
    
    public void setTs(List<Integer> ts) {
        this.timeStamps = ts;
    }
    
    public List<TouchCoord> getC() {
        return this.touchCoords;
    }
    
    public void setC( List<TouchCoord> c ) {
        this.touchCoords = c;        
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_ID, this.id);
            JsonUtils.addToJsonObject(result, KEY_TS, (this.timeStamps == null) ? null :
                JsonUtils.createJsonArray(this.timeStamps));
            JsonUtils.addToJsonObject(result, KEY_C, (this.touchCoords == null) ? null :
                JsonUtils.createJsonArrayOfJsonObjects(this.touchCoords, sdlVersion));
            break;
        }
        
        return result;
    }
}
