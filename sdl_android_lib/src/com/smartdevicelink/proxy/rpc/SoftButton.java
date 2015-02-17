package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.util.JsonUtils;

public class SoftButton extends RPCObject {

	public static final String KEY_IS_HIGHLIGHTED = "isHighlighted";
	public static final String KEY_SOFT_BUTTON_ID = "softButtonID";
	public static final String KEY_SYSTEM_ACTION = "systemAction";
	public static final String KEY_TEXT = "text";
	public static final String KEY_TYPE = "type";
	public static final String KEY_IMAGE = "image";
	
	private String type; // represents SoftButtonType enum
	private String systemAction; // represents SystemAction enum
	private String text;
	private Image image;
	private Boolean isHighlighted;
	private Integer id;
	
    public SoftButton() { }
    
    /**
     * Creates a SoftButton object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SoftButton(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.type = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TYPE);
            this.systemAction = JsonUtils.readStringFromJsonObject(jsonObject, KEY_SYSTEM_ACTION);
            this.text = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TEXT);
            this.isHighlighted = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_IS_HIGHLIGHTED);
            this.id = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_SOFT_BUTTON_ID);
            
            JSONObject imageObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_IMAGE);
            if(imageObj != null){
                this.image = new Image(imageObj);
            }
            break;
        }
    }
    
    public void setType(SoftButtonType type) {
        this.type = (type == null) ? null : type.getJsonName(sdlVersion);
    }
    
    public SoftButtonType getType() {
    	return SoftButtonType.valueForJsonName(this.type, sdlVersion);
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
    
    public void setIsHighlighted(Boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }
    
    public Boolean getIsHighlighted() {
        return this.isHighlighted;
    }
    
    public void setSoftButtonID(Integer softButtonID) {
        this.id = softButtonID;
    }
    
    public Integer getSoftButtonID() {
        return this.id;
    }
    
    public void setSystemAction(SystemAction systemAction) {
        this.systemAction = (systemAction == null) ? null : systemAction.getJsonName(sdlVersion);
    }
    
    public SystemAction getSystemAction() {
    	return SystemAction.valueForJsonName(this.systemAction, sdlVersion);
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_IS_HIGHLIGHTED, this.isHighlighted);
            JsonUtils.addToJsonObject(result, KEY_SOFT_BUTTON_ID, this.id);
            JsonUtils.addToJsonObject(result, KEY_SYSTEM_ACTION, this.systemAction);
            JsonUtils.addToJsonObject(result, KEY_TEXT, this.text);
            JsonUtils.addToJsonObject(result, KEY_TYPE, this.type);
            
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((isHighlighted == null) ? 0 : isHighlighted.hashCode());
		result = prime * result + ((systemAction == null) ? 0 : systemAction.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		SoftButton other = (SoftButton) obj;
		if (id == null) {
			if (other.id != null) { 
				return false;
			}
		} 
		else if (!id.equals(other.id)) { 
			return false;
		}
		if (image == null) {
			if (other.image != null) { 
				return false;
			}
		}
		else if (!image.equals(other.image)) { 
			return false;
		}
		if (isHighlighted == null) {
			if (other.isHighlighted != null) { 
				return false;
			}
		} 
		else if (!isHighlighted.equals(other.isHighlighted)) { 
			return false;
		}
		if (systemAction == null) {
			if (other.systemAction != null) { 
				return false;
			}
		}
		else if (!systemAction.equals(other.systemAction)) { 
			return false;
		}
		if (text == null) {
			if (other.text != null) { 
				return false;
			}
		} 
		else if (!text.equals(other.text)) { 
			return false;
		}
		if (type == null) {
			if (other.type != null) { 
				return false;
			}
		} 
		else if (!type.equals(other.type)) { 
			return false;
		}
		return true;
	}
}
