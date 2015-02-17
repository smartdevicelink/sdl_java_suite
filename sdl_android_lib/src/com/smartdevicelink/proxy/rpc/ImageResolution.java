package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

public class ImageResolution extends RPCObject {
	public static final String KEY_RESOLUTION_WIDTH = "resolutionWidth";
	public static final String KEY_RESOLUTION_HEIGHT = "resolutionHeight";
	
	private Integer width, height;
	
    public ImageResolution() {}
    
    /**
     * Creates an ImageResolution object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ImageResolution(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.width = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_RESOLUTION_WIDTH);
            this.height = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_RESOLUTION_HEIGHT);
            break;
        }
    }
    
    public void setResolutionWidth(Integer resolutionWidth) {
        this.width = resolutionWidth;
    }
    
    public Integer getResolutionWidth() {
        return this.width;
    }
    
    public void setResolutionHeight(Integer resolutionHeight) {
        this.height = resolutionHeight;
    }
    
    public Integer getResolutionHeight() {
        return this.height;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_RESOLUTION_HEIGHT, this.height);
            JsonUtils.addToJsonObject(result, KEY_RESOLUTION_WIDTH, this.width);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((width == null) ? 0 : width.hashCode());
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
		ImageResolution other = (ImageResolution) obj;
		if (height == null) {
			if (other.height != null) { 
				return false;
			}
		} else if (!height.equals(other.height)) { 
			return false;
		}
		if (width == null) {
			if (other.width != null) { 
				return false;
			}
		} else if (!width.equals(other.width)) { 
			return false;
		}
		return true;
	}
}
