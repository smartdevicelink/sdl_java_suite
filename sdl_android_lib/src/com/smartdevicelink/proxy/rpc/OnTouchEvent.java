package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
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
        super(SdlCommand.ON_TOUCH_EVENT, jsonObject);
        jsonObject = getParameters(jsonObject);
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		OnTouchEvent other = (OnTouchEvent) obj;
		if (event == null) {
			if (other.event != null) { 
				return false;
			}
		} else if (!event.equals(other.event)) { 
			return false;
		}
		if (type == null) {
			if (other.type != null) { 
				return false;
			}
		} else if (!type.equals(other.type)) { 
			return false;
		}
		return true;
	}
}
