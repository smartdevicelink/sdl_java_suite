package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.util.DebugTool;

public class ImageField extends RPCStruct {
    public static final String KEY_IMAGE_TYPE_SUPPORTED = "imageTypeSupported";
    public static final String KEY_IMAGE_RESOLUTION = "imageResolution";
    public static final String KEY_NAME = "name";
    
    
    public ImageField() { }
   
    public ImageField(Hashtable<String, Object> hash) {
        super(hash);
    }
    public ImageFieldName getName() {
        Object obj = store.get(KEY_NAME);
        if (obj instanceof ImageFieldName) {
            return (ImageFieldName) obj;
        } else if (obj instanceof String) {
        	ImageFieldName theCode = null;
            try {
                theCode = ImageFieldName.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_NAME, e);
            }
            return theCode;
        }
        return null;
    } 
    public void setName( ImageFieldName name ) {
        if (name != null) {
            store.put(KEY_NAME, name );
        }
        else {
        	store.remove(KEY_NAME);
        }        
    } 
    public FileType getImageTypeSupported() {
        Object obj = store.get(KEY_IMAGE_TYPE_SUPPORTED);
        if (obj instanceof FileType) {
            return (FileType) obj;
        } else if (obj instanceof String) {
        	FileType theCode = null;
            try {
                theCode = FileType.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_IMAGE_TYPE_SUPPORTED, e);
            }
            return theCode;
        }
        return null;
    } 
    public void setImageTypeSupported( FileType imageTypeSupported ) {
        if (imageTypeSupported != null) {
            store.put(KEY_IMAGE_TYPE_SUPPORTED, imageTypeSupported );
        }
        else {
        	store.remove(KEY_IMAGE_TYPE_SUPPORTED);
        }         
    }
    @SuppressWarnings("unchecked")
    public ImageResolution getImageResolution() {
    	Object obj = store.get(KEY_IMAGE_RESOLUTION);
        if (obj instanceof ImageResolution) {
            return (ImageResolution) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new ImageResolution((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_IMAGE_RESOLUTION, e);
            }
        }
        return null;
    } 
    public void setImageResolution( ImageResolution imageResolution ) {
        if (imageResolution != null) {
            store.put(KEY_IMAGE_RESOLUTION, imageResolution );
        }
        else {
        	store.remove(KEY_IMAGE_RESOLUTION);
        }        
    }      
}
