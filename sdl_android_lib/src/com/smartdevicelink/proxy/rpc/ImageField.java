package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
        	return ImageFieldName.valueForString((String) obj);
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
    @SuppressWarnings("unchecked")
	public List<FileType> getImageTypeSupported() {
        if (store.get(KEY_IMAGE_TYPE_SUPPORTED) instanceof List<?>) {
           List<?> list = (List<?>)store.get(KEY_IMAGE_TYPE_SUPPORTED);
              if (list != null && list.size() > 0) {
                  Object obj = list.get(0);
                  if (obj instanceof FileType) {
                      return (List<FileType>) list;
                  } else if (obj instanceof String) {
                      List<FileType> newList = new ArrayList<FileType>();
                      for (Object hashObj : list) {
                        String strFormat = (String)hashObj;
                        FileType theCode = FileType.valueForString(strFormat);
                        if (theCode != null) {
                            newList.add(theCode);
                        }
                    }
                    return newList;
                 }
             }
         }
         return null;
    }
    public void setImageTypeSupported( List<FileType> imageTypeSupported ) {
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
