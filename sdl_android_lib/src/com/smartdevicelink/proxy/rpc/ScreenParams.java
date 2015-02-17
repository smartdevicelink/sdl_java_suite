package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

public class ScreenParams extends RPCObject {
    public static final String KEY_RESOLUTION = "resolution";
    public static final String KEY_TOUCH_EVENT_AVAILABLE = "touchEventAvailable";

    private ImageResolution imageResolution;
    private TouchEventCapabilities touchEventCapabilities;
    
	public ScreenParams() { }
  
    /**
     * Creates a ScreenParams object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ScreenParams(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            JSONObject imageResolutionObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_RESOLUTION);
            if(imageResolutionObj != null){
                this.imageResolution = new ImageResolution(imageResolutionObj);
            }
            
            JSONObject touchEventCapabilitiesObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_TOUCH_EVENT_AVAILABLE);
            if(touchEventCapabilitiesObj != null){
                this.touchEventCapabilities = new TouchEventCapabilities(touchEventCapabilitiesObj);
            }
            break;
        }
    }
    
    public ImageResolution getImageResolution() {
    	return this.imageResolution;
    }
    
    public void setImageResolution( ImageResolution resolution ) {
        this.imageResolution = resolution;
    }
    
    public TouchEventCapabilities getTouchEventAvailable() {
    	return this.touchEventCapabilities;
    }
    
    public void setTouchEventAvailable( TouchEventCapabilities touchEventAvailable ) {
        this.touchEventCapabilities = touchEventAvailable;        
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_RESOLUTION, (this.imageResolution == null) ? null : 
                this.imageResolution.getJsonParameters(sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_TOUCH_EVENT_AVAILABLE, (this.touchEventCapabilities == null) ? null : 
                this.touchEventCapabilities.getJsonParameters(sdlVersion));
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imageResolution == null) ? 0 : imageResolution.hashCode());
		result = prime * result + ((touchEventCapabilities == null) ? 0 : touchEventCapabilities.hashCode());
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
		ScreenParams other = (ScreenParams) obj;
		if (imageResolution == null) {
			if (other.imageResolution != null) { 
				return false;
			}
		} else if (!imageResolution.equals(other.imageResolution)) { 
			return false;
		}
		if (touchEventCapabilities == null) {
			if (other.touchEventCapabilities != null) { 
				return false;
			}
		} else if (!touchEventCapabilities.equals(other.touchEventCapabilities)) { 
			return false;
		}
		return true;
	}
}
