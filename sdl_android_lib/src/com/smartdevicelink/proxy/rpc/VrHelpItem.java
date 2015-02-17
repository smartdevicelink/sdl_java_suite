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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		VrHelpItem other = (VrHelpItem) obj;
		if (image == null) {
			if (other.image != null) { 
				return false;
			}
		} else if (!image.equals(other.image)) { 
			return false;
		}
		if (position == null) {
			if (other.position != null) { 
				return false;
			}
		} else if (!position.equals(other.position)) { 
			return false;
		}
		if (text == null) {
			if (other.text != null) { 
				return false;
			}
		} else if (!text.equals(other.text)) { 
			return false;
		}
		return true;
	}
}
