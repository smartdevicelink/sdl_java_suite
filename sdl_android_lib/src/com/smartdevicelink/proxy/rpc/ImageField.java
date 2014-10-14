package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.util.DebugTool;

public class ImageField extends RPCStruct {
    public static final String imageTypeSupported = "imageTypeSupported";
    public static final String imageResolution = "imageResolution";
    public static final String name = "name";
    public ImageField() { }
   
    public ImageField(Hashtable hash) {
        super(hash);
    }
    public ImageFieldName getName() {
        Object obj = store.get(ImageField.name);
        if (obj instanceof ImageFieldName) {
            return (ImageFieldName) obj;
        } else if (obj instanceof String) {
        	ImageFieldName theCode = null;
            try {
                theCode = ImageFieldName.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + ImageField.name, e);
            }
            return theCode;
        }
        return null;
    } 
    public void setName( ImageFieldName name ) {
        if (name != null) {
            store.put(ImageField.name, name );
        }
        else {
        	store.remove(ImageField.name);
        }        
    } 
    public FileType getImageTypeSupported() {
        Object obj = store.get(ImageField.imageTypeSupported);
        if (obj instanceof FileType) {
            return (FileType) obj;
        } else if (obj instanceof String) {
        	FileType theCode = null;
            try {
                theCode = FileType.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + ImageField.imageTypeSupported, e);
            }
            return theCode;
        }
        return null;
    } 
    public void setImageTypeSupported( FileType imageTypeSupported ) {
        if (imageTypeSupported != null) {
            store.put(ImageField.imageTypeSupported, imageTypeSupported );
        }
        else {
        	store.remove(ImageField.imageTypeSupported);
        }         
    }
    public ImageResolution getImageResolution() {
    	Object obj = store.get(ImageField.imageResolution);
        if (obj instanceof ImageResolution) {
            return (ImageResolution) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new ImageResolution((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + ImageField.imageResolution, e);
            }
        }
        return null;
    } 
    public void setImageResolution( ImageResolution imageResolution ) {
        if (imageResolution != null) {
            store.put(ImageField.imageResolution, imageResolution );
        }
        else {
        	store.remove(ImageField.imageResolution);
        }        
    }      
}
