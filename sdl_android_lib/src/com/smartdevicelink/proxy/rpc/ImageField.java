package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.util.JsonUtils;

public class ImageField extends RPCObject {
    public static final String KEY_IMAGE_TYPE_SUPPORTED = "imageTypeSupported";
    public static final String KEY_IMAGE_RESOLUTION = "imageResolution";
    public static final String KEY_NAME = "name";
    
    private ImageResolution imageResolution;
    private String imageFieldName; // represents ImageFieldName enum
    private String imageTypeSupported; // represents FileType enum
    
    public ImageField() { }
   
    /**
     * Creates an AddCommand object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ImageField(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.imageFieldName = JsonUtils.readStringFromJsonObject(jsonObject, KEY_NAME);
            this.imageTypeSupported = JsonUtils.readStringFromJsonObject(jsonObject, KEY_IMAGE_TYPE_SUPPORTED);
            
            JSONObject imageResolutionObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_IMAGE_RESOLUTION);
            if(imageResolutionObj != null){
                this.imageResolution = new ImageResolution(imageResolutionObj);
            }
            break;
        }
    }
    
    public ImageFieldName getName() {
        return ImageFieldName.valueForJsonName(this.imageFieldName, sdlVersion);
    }
    
    public void setName( ImageFieldName name ) {
        this.imageFieldName = name.getJsonName(sdlVersion);
    }
    
    public FileType getImageTypeSupported() {
        return FileType.valueForJsonName(this.imageTypeSupported, sdlVersion);
    }
    
    public void setImageTypeSupported( FileType imageTypeSupported ) {
        this.imageTypeSupported = imageTypeSupported.getJsonName(sdlVersion);
    }
    
    public ImageResolution getImageResolution() {
    	return this.imageResolution;
    }
    
    public void setImageResolution( ImageResolution imageResolution ) {
        this.imageResolution = imageResolution;        
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_IMAGE_TYPE_SUPPORTED, this.imageTypeSupported);
            JsonUtils.addToJsonObject(result, KEY_NAME, this.imageFieldName);
            JsonUtils.addToJsonObject(result, KEY_IMAGE_RESOLUTION, 
                    this.imageResolution.getJsonParameters(sdlVersion));
            break;
        }
        
        return result;
    }
}
