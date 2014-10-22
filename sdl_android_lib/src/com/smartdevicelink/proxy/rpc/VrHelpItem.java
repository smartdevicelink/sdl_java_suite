package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.DebugTool;

public class VrHelpItem extends RPCStruct {
	public static final String position = "position";
	public static final String text = "text";
	public static final String image = "image";

    public VrHelpItem() { }
    public VrHelpItem(Hashtable hash) {
        super(hash);
    }
    public void setText(String text) {
        if (text != null) {
            store.put(VrHelpItem.text, text);
        } else {
        	store.remove(VrHelpItem.text);
        }
    }
    public String getText() {
        return (String) store.get(VrHelpItem.text);
    }
    public void setImage(Image image) {
        if (image != null) {
            store.put(VrHelpItem.image, image);
        } else {
        	store.remove(VrHelpItem.image);
        }
    }
    public Image getImage() {
    	Object obj = store.get(VrHelpItem.image);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new Image((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + VrHelpItem.image, e);
            }
        }
        return null;
    }
    public void setPosition(Integer position) {
        if (position != null) {
            store.put(VrHelpItem.position, position);
        } else {
        	store.remove(VrHelpItem.position);
        }
    }
    public Integer getPosition() {
        return (Integer) store.get(VrHelpItem.position);
    }
}
