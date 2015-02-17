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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doublePressAvailable == null) ? 0 : doublePressAvailable.hashCode());
		result = prime * result + ((multiTouchAvailable == null) ? 0 : multiTouchAvailable.hashCode());
		result = prime * result + ((pressAvailable == null) ? 0 : pressAvailable.hashCode());
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
		TouchEventCapabilities other = (TouchEventCapabilities) obj;
		if (doublePressAvailable == null) {
			if (other.doublePressAvailable != null) { 
				return false;
			}
		}
		else if (!doublePressAvailable.equals(other.doublePressAvailable)) { 
			return false;
		}
		if (multiTouchAvailable == null) {
			if (other.multiTouchAvailable != null) { 
				return false;
			}
		} 
		else if (!multiTouchAvailable.equals(other.multiTouchAvailable)) { 
			return false;
		}
		if (pressAvailable == null) {
			if (other.pressAvailable != null) { 
				return false;
			}
		} 
		else if (!pressAvailable.equals(other.pressAvailable)) { 
			return false;
		}
		return true;
	}
}
