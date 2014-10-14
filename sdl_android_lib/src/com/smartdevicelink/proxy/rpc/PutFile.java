package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.util.DebugTool;

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
	public static final String persistentFile = "persistentFile";
    public static final String systemFile = "systemFile";
    public static final String fileType = "fileType";
    public static final String smartDeviceLinkFileName = "syncFileName";
    public static final String offset = "offset";
    public static final String length = "length";
    

	/**
	 * Constructs a new PutFile object
	 */
    public PutFile() {
        super("PutFile");
    }

	/**
	 * Constructs a new PutFile object indicated by the Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public PutFile(Hashtable hash) {
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
            parameters.put(PutFile.smartDeviceLinkFileName, sdlFileName);
        } else {
        	parameters.remove(PutFile.smartDeviceLinkFileName);
        }
    }

	/**
	 * Gets a file reference name
	 * 
	 * @return String - a String value representing a file reference name
	 */
    public String getSdlFileName() {
        return (String) parameters.get(PutFile.smartDeviceLinkFileName);
    }

	/**
	 * Sets file type
	 * 
	 * @param fileType
	 *            a FileType value representing a selected file type
	 */
    public void setFileType(FileType fileType) {
        if (fileType != null) {
            parameters.put(PutFile.fileType, fileType);
        } else {
        	parameters.remove(PutFile.fileType);
        }
    }

	/**
	 * Gets a file type
	 * 
	 * @return FileType -a FileType value representing a selected file type
	 */
    public FileType getFileType() {
        Object obj = parameters.get(PutFile.fileType);
        if (obj instanceof FileType) {
            return (FileType) obj;
        } else if (obj instanceof String) {
        	FileType theCode = null;
            try {
                theCode = FileType.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + PutFile.fileType, e);
            }
            return theCode;
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
            parameters.put(PutFile.persistentFile, persistentFile);
        } else {
        	parameters.remove(PutFile.persistentFile);
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
        return (Boolean) parameters.get(PutFile.persistentFile);
    }
    public void setFileData(byte[] fileData) {
        if (fileData != null) {
            parameters.put(RPCRequest.bulkData, fileData);
        } else {
        	parameters.remove(RPCRequest.bulkData);
        }
    }
    public byte[] getFileData() {
        return (byte[]) parameters.get(RPCRequest.bulkData);
    }
    
    public void setOffset(Integer offset) {
        if (offset != null) {
            parameters.put(PutFile.offset, offset);
        } else {
            parameters.remove(PutFile.offset);
        }
    }

    public Integer getOffset() {
        final Object o = parameters.get(PutFile.offset);
        if (o instanceof Integer) {
            return (Integer) o;
        }

        return null;
    }

    public void setLength(Integer length) {
        if (length != null) {
            parameters.put(PutFile.length, length);
        } else {
            parameters.remove(PutFile.length);
        }
    }

    public Integer getLength() {
        final Object o = parameters.get(PutFile.length);
        if (o instanceof Integer) {
            return (Integer) o;
        }

        return null;
    }

    public void setSystemFile(Boolean systemFile) {
        if (systemFile != null) {
            parameters.put(PutFile.systemFile, systemFile);
        } else {
            parameters.remove(PutFile.systemFile);
        }
    }

    public Boolean getSystemFile() {
        final Object o = parameters.get(PutFile.systemFile);
        if (o instanceof Boolean) {
            return (Boolean) o;
        }
        else
        	return null;
    }
}
