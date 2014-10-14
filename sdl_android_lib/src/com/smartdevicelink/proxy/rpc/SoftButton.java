package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.util.DebugTool;

public class SoftButton extends RPCStruct {

	public static final String isHighlighted = "isHighlighted";
	public static final String softButtonID = "softButtonID";
	public static final String systemAction = "systemAction";
	public static final String text = "text";
	public static final String type = "type";
	public static final String image = "image";
    public SoftButton() { }
    public SoftButton(Hashtable hash) {
        super(hash);
    }
    public void setType(SoftButtonType type) {
        if (type != null) {
            store.put(SoftButton.type, type);
        } else {
        	store.remove(SoftButton.type);
        }
    }
    public SoftButtonType getType() {
    	Object obj = store.get(SoftButton.type);
        if (obj instanceof SoftButtonType) {
            return (SoftButtonType) obj;
        } else if (obj instanceof String) {
        	SoftButtonType theCode = null;
            try {
                theCode = SoftButtonType.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SoftButton.type, e);
            }
            return theCode;
        }
        return null;
    }
    public void setText(String text) {
        if (text != null) {
            store.put(SoftButton.text, text);
        } else {
        	store.remove(SoftButton.text);
        }
    }
    public String getText() {
        return (String) store.get(SoftButton.text);
    }
    public void setImage(Image image) {
        if (image != null) {
            store.put(SoftButton.image, image);
        } else {
        	store.remove(SoftButton.image);
        }
    }
    public Image getImage() {
    	Object obj = store.get(SoftButton.image);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new Image((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SoftButton.image, e);
            }
        }
        return null;
    }
    public void setIsHighlighted(Boolean isHighlighted) {
        if (isHighlighted != null) {
            store.put(SoftButton.isHighlighted, isHighlighted);
        } else {
        	store.remove(SoftButton.isHighlighted);
        }
    }
    public Boolean getIsHighlighted() {
        return (Boolean) store.get(SoftButton.isHighlighted);
    }
    public void setSoftButtonID(Integer softButtonID) {
        if (softButtonID != null) {
            store.put(SoftButton.softButtonID, softButtonID);
        } else {
        	store.remove(SoftButton.softButtonID);
        }
    }
    public Integer getSoftButtonID() {
        return (Integer) store.get(SoftButton.softButtonID);
    }
    public void setSystemAction(SystemAction systemAction) {
        if (systemAction != null) {
            store.put(SoftButton.systemAction, systemAction);
        } else {
        	store.remove(SoftButton.systemAction);
        }
    }
    public SystemAction getSystemAction() {
    	Object obj = store.get(SoftButton.systemAction);
        if (obj instanceof SystemAction) {
            return (SystemAction) obj;
        } else if (obj instanceof String) {
        	SystemAction theCode = null;
            try {
                theCode = SystemAction.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + SoftButton.systemAction, e);
            }
            return theCode;
        }
        return null;
    }
}
