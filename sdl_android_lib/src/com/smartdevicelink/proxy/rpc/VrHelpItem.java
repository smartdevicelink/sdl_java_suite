package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.DebugTool;

public class VrHelpItem extends RPCStruct {
	public static final String KEY_POSITION = "position";
	public static final String KEY_TEXT = "text";
	public static final String KEY_IMAGE = "image";

    public VrHelpItem() { }
    public VrHelpItem(Hashtable<String, Object> hash) {
        super(hash);
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
    public void setPosition(Integer position) {
        if (position != null) {
            store.put(KEY_POSITION, position);
        } else {
        	store.remove(KEY_POSITION);
        }
    }
    public Integer getPosition() {
        return (Integer) store.get(KEY_POSITION);
    }
}
