package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.listeners.OnPutFileUpdateListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

/**
 * Used to push a binary data onto the SDL module from a mobile device, such as
 * icons and album art
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 * @see DeleteFile
 * @see ListFiles
 */
public class PutFile extends RPCRequest {
	public static final String KEY_PERSISTENT_FILE = "persistentFile";
    public static final String KEY_SYSTEM_FILE = "systemFile";
    public static final String KEY_FILE_TYPE = "fileType";
    public static final String KEY_SDL_FILE_NAME = "syncFileName";
    public static final String KEY_OFFSET = "offset";
    public static final String KEY_LENGTH = "length";
    

	/**
	 * Constructs a new PutFile object
	 */
    public PutFile() {
        super(FunctionID.PUT_FILE.toString());
    }

	/**
	 * Constructs a new PutFile object indicated by the Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public PutFile(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Sets a file reference name
	 * 
	 * @param sdlFileName
	 *            a String value representing a file reference name
	 *            <p>
	 *            <b>Notes: </b>Maxlength=500
	 */
    public void setSdlFileName(String sdlFileName) {
        if (sdlFileName != null) {
            parameters.put(KEY_SDL_FILE_NAME, sdlFileName);
        } else {
        	parameters.remove(KEY_SDL_FILE_NAME);
        }
    }

	/**
	 * Gets a file reference name
	 * 
	 * @return String - a String value representing a file reference name
	 */
    public String getSdlFileName() {
        return (String) parameters.get(KEY_SDL_FILE_NAME);
    }

	/**
	 * Sets file type
	 * 
	 * @param fileType
	 *            a FileType value representing a selected file type
	 */
    public void setFileType(FileType fileType) {
        if (fileType != null) {
            parameters.put(KEY_FILE_TYPE, fileType);
        } else {
        	parameters.remove(KEY_FILE_TYPE);
        }
    }

	/**
	 * Gets a file type
	 * 
	 * @return FileType -a FileType value representing a selected file type
	 */
    public FileType getFileType() {
        Object obj = parameters.get(KEY_FILE_TYPE);
        if (obj instanceof FileType) {
            return (FileType) obj;
        } else if (obj instanceof String) {
        	return FileType.valueForString((String) obj);
        }
        return null;
    }

	/**
	 * Sets a value to indicates if the file is meant to persist between
	 * sessions / ignition cycles. If set to TRUE, then the system will aim to
	 * persist this file through session / cycles. While files with this
	 * designation will have priority over others, they are subject to deletion
	 * by the system at any time. In the event of automatic deletion by the
	 * system, the app will receive a rejection and have to resend the file. If
	 * omitted, the value will be set to false
	 * <p>
	 * 
	 * @param persistentFile
	 *            a Boolean value
	 */
    public void setPersistentFile(Boolean persistentFile) {
        if (persistentFile != null) {
            parameters.put(KEY_PERSISTENT_FILE, persistentFile);
        } else {
        	parameters.remove(KEY_PERSISTENT_FILE);
        }
    }

	/**
	 * Gets a value to Indicates if the file is meant to persist between
	 * sessions / ignition cycles
	 * 
	 * @return Boolean -a Boolean value to indicates if the file is meant to
	 *         persist between sessions / ignition cycles
	 */
    public Boolean getPersistentFile() {
        return (Boolean) parameters.get(KEY_PERSISTENT_FILE);
    }
    public void setFileData(byte[] fileData) {
        setBulkData(fileData);
    }
    public byte[] getFileData() {
        return getBulkData();
    }
    
    /**
     * @deprecated as of SmartDeviceLink 4.0
     * @param offset
     */
    public void setOffset(Integer offset) {
    	if(offset == null){
    		setOffset((Long)null);
    	}else{
    		setOffset(offset.longValue());
    	}
    }
    
    public void setOffset(Long offset) {
        if (offset != null) {
            parameters.put(KEY_OFFSET, offset);
        } else {
            parameters.remove(KEY_OFFSET);
        }
    }

    public Long getOffset() {
        final Object o = parameters.get(KEY_OFFSET);
        if (o == null){
        	return null;
        }
        if (o instanceof Integer) {
            return ((Integer) o).longValue();
        }else if(o instanceof Long){
        	return (Long) o;
        }


        return null;
    }

    /**
     * @deprecated as of SmartDeviceLink 4.0
     * @param length
     */
    public void setLength(Integer length) {
    	if(length == null){
    		setLength((Long)null);
    	}else{
    		setLength(length.longValue());
    	}
    }
    
    public void setLength(Long length) {
        if (length != null) {
            parameters.put(KEY_LENGTH, length);
        } else {
            parameters.remove(KEY_LENGTH);
        }
    }

    public Long getLength() {
        final Object o = parameters.get(KEY_LENGTH);
        if (o == null){
        	return null;
        }
        if (o instanceof Integer) {
            return ((Integer) o).longValue();
        }else if(o instanceof Long){
        	return (Long) o;
        }

        return null;
    }

    public void setSystemFile(Boolean systemFile) {
        if (systemFile != null) {
            parameters.put(KEY_SYSTEM_FILE, systemFile);
        } else {
            parameters.remove(KEY_SYSTEM_FILE);
        }
    }

    public Boolean getSystemFile() {
        final Object o = parameters.get(KEY_SYSTEM_FILE);
        if (o instanceof Boolean) {
            return (Boolean) o;
        }
        else
        	return null;
    }


	@Override
	public final void setOnRPCResponseListener(OnRPCResponseListener listener) {
		super.setOnRPCResponseListener(listener);
	}
    
	public void setOnPutFileUpdateListener(OnPutFileUpdateListener listener) {
		super.setOnRPCResponseListener(listener); //We can use the same method because it get stored as a parent class
	}
    
	public OnPutFileUpdateListener getOnPutFileUpdateListener() {
		return (OnPutFileUpdateListener)getOnRPCResponseListener();
	}
}
