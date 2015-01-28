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
}
