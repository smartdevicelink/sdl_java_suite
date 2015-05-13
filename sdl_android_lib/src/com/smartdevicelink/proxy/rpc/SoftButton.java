package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.util.DebugTool;

public class SoftButton extends RPCStruct {

	public static final String KEY_IS_HIGHLIGHTED = "isHighlighted";
	public static final String KEY_SOFT_BUTTON_ID = "softButtonID";
	public static final String KEY_SYSTEM_ACTION = "systemAction";
	public static final String KEY_TEXT = "text";
	public static final String KEY_TYPE = "type";
	public static final String KEY_IMAGE = "image";
	
    public SoftButton() { }
    public SoftButton(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setType(SoftButtonType type) {
        if (type != null) {
            store.put(KEY_TYPE, type);
        } else {
        	store.remove(KEY_TYPE);
        }
    }
    public SoftButtonType getType() {
    	Object obj = store.get(KEY_TYPE);
        if (obj instanceof SoftButtonType) {
            return (SoftButtonType) obj;
        } else if (obj instanceof String) {
        	return SoftButtonType.valueForString((String) obj);
        }
        return null;
    }
    public void setText(String text) {
        if (text != null) {
            store.put(KEY_TEXT, text);
        } else {
        	store.remove(KEY_TEXT);
        }
    }
    public String getText() {
        return (String) store.get(KEY_TEXT);
    }
    public void setImage(Image image) {
        if (image != null) {
            store.put(KEY_IMAGE, image);
        } else {
        	store.remove(KEY_IMAGE);
        }
    }
    @SuppressWarnings("unchecked")
    public Image getImage() {
    	Object obj = store.get(KEY_IMAGE);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new Image((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_IMAGE, e);
            }
        }
        return null;
    }
    public void setIsHighlighted(Boolean isHighlighted) {
        if (isHighlighted != null) {
            store.put(KEY_IS_HIGHLIGHTED, isHighlighted);
        } else {
        	store.remove(KEY_IS_HIGHLIGHTED);
        }
    }
    public Boolean getIsHighlighted() {
        return (Boolean) store.get(KEY_IS_HIGHLIGHTED);
    }
    public void setSoftButtonID(Integer softButtonID) {
        if (softButtonID != null) {
            store.put(KEY_SOFT_BUTTON_ID, softButtonID);
        } else {
        	store.remove(KEY_SOFT_BUTTON_ID);
        }
    }
    public Integer getSoftButtonID() {
        return (Integer) store.get(KEY_SOFT_BUTTON_ID);
    }
    public void setSystemAction(SystemAction systemAction) {
        if (systemAction != null) {
            store.put(KEY_SYSTEM_ACTION, systemAction);
        } else {
        	store.remove(KEY_SYSTEM_ACTION);
        }
    }
    public SystemAction getSystemAction() {
    	Object obj = store.get(KEY_SYSTEM_ACTION);
        if (obj instanceof SystemAction) {
            return (SystemAction) obj;
        } else if (obj instanceof String) {
        	return SystemAction.valueForString((String) obj);
        }
        return null;
    }
}
