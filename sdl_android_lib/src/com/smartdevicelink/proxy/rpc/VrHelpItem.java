package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

public class VrHelpItem extends RPCObject {
	public static final String KEY_POSITION = "position";
	public static final String KEY_TEXT = "text";
	public static final String KEY_IMAGE = "image";

	private String text;
	private Integer position;
	private Image image;
	
    public VrHelpItem() { }
    
    /**
     * Creates a VrHelpItem object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public VrHelpItem(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.text = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TEXT);
            this.position = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_POSITION);
            
            JSONObject imageObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_IMAGE);
            if(imageObj != null){
                this.image = new Image(imageObj);
            }
            break;
        }
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setImage(Image image) {
        this.image = image;
    }
    
    public Image getImage() {
    	return this.image;
    }
    
    public void setPosition(Integer position) {
        this.position = position;
    }
    
    public Integer getPosition() {
        return this.position;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_TEXT, this.text);
            JsonUtils.addToJsonObject(result, KEY_POSITION, this.position);
            JsonUtils.addToJsonObject(result, KEY_IMAGE, (this.image == null) ? null :
                this.image.getJsonParameters(sdlVersion));
            break;
        }
        
        return result;
    }
}
