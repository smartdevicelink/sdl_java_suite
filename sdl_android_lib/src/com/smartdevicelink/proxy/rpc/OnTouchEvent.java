package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.TouchType;
import com.smartdevicelink.util.JsonUtils;

public class OnTouchEvent extends RPCNotification {
	public static final String KEY_EVENT = "event";
	public static final String KEY_TYPE = "type";
	
	private String type; // represents TouchType enum
	private List<TouchEvent> event;
	
    public OnTouchEvent() {
        super(FunctionID.ON_TOUCH_EVENT);
    }
    /**
     * Creates a OnTouchEvent object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public OnTouchEvent(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.type = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TYPE);
            
            List<JSONObject> eventObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_EVENT);
            if(eventObjs != null){
                this.event = new ArrayList<TouchEvent>(eventObjs.size());
                for(JSONObject eventObj : eventObjs){
                    this.event.add(new TouchEvent(eventObj));
                }
            }
            break;
        }
    }
    
    public void setType(TouchType type) {
    	this.type = (type == null) ? null : type.getJsonName(sdlVersion);
    }
    
    public TouchType getType() {
        return TouchType.valueForJsonName(this.type, sdlVersion);
    }
    
    public void setEvent(List<TouchEvent> event) {
        this.event = event;
    }
    
    public List<TouchEvent> getEvent() {
        return this.event;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_TYPE, this.type);
            JsonUtils.addToJsonObject(result, KEY_EVENT, (this.event == null) ? null : 
                    JsonUtils.createJsonArrayOfJsonObjects(this.event, sdlVersion));
            break;
        }
        
        return result;
    }
}
