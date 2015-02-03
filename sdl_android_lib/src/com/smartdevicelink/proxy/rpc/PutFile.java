package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.interfaces.BulkData;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.util.JsonUtils;

/**
 * Used to push a binary data onto the SDL module from a mobile device, such as
 * icons and album art
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 * @see DeleteFile
 * @see ListFiles
 */
public class PutFile extends RPCRequest implements BulkData{
	public static final String KEY_PERSISTENT_FILE = "persistentFile";
    public static final String KEY_SYSTEM_FILE = "systemFile";
    public static final String KEY_FILE_TYPE = "fileType";
    public static final String KEY_SDL_FILE_NAME = "syncFileName";
    public static final String KEY_OFFSET = "offset";
    public static final String KEY_LENGTH = "length";
    
    private String filename;
    private String fileType; // represents FileType enum
    private Boolean persistentFile, systemFile;
    private Integer offset, length;
    
    private byte[] bulkData;
    
	/**
	 * Constructs a new PutFile object
	 */
    public PutFile() {
        super(FunctionID.PUT_FILE);
    }

    /**
     * Creates a PutFile object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public PutFile(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.filename = JsonUtils.readStringFromJsonObject(jsonObject, KEY_SDL_FILE_NAME);
            this.fileType = JsonUtils.readStringFromJsonObject(jsonObject, KEY_FILE_TYPE);
            this.persistentFile = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_PERSISTENT_FILE);
            this.systemFile = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_SYSTEM_FILE);
            this.offset = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_OFFSET);
            this.length = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_LENGTH);
            break;
        }
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
        this.filename = sdlFileName;
    }

	/**
	 * Gets a file reference name
	 * 
	 * @return String - a String value representing a file reference name
	 */
    public String getSdlFileName() {
        return this.filename;
    }

	/**
	 * Sets file type
	 * 
	 * @param fileType
	 *            a FileType value representing a selected file type
	 */
    public void setFileType(FileType fileType) {
        this.fileType = (fileType == null) ? null : fileType.getJsonName(sdlVersion);
    }

	/**
	 * Gets a file type
	 * 
	 * @return FileType -a FileType value representing a selected file type
	 */
    public FileType getFileType() {
        return FileType.valueForJsonName(this.fileType, sdlVersion);
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
        this.persistentFile = persistentFile;
    }

	/**
	 * Gets a value to Indicates if the file is meant to persist between
	 * sessions / ignition cycles
	 * 
	 * @return Boolean -a Boolean value to indicates if the file is meant to
	 *         persist between sessions / ignition cycles
	 */
    public Boolean getPersistentFile() {
        return this.persistentFile;
    }
    
    @Deprecated
    public void setFileData(byte[] fileData) {
        setBulkData(fileData);
    }
    
    @Deprecated
    public byte[] getFileData() {
        return getBulkData();
    }
    
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getLength() {
        return this.length;
    }

    public void setSystemFile(Boolean systemFile) {
        this.systemFile = systemFile;
    }

    public Boolean getSystemFile() {
        return this.systemFile;
    }

    @Override
    public byte[] getBulkData(){
        return this.bulkData;
    }

    @Override
    public void setBulkData(byte[] rawData){
        this.bulkData = rawData;
    }
    
    @Override
public JSONObject getJsonParameters(int sdlVersion){
    JSONObject result = super.getJsonParameters(sdlVersion);
    
    switch(sdlVersion){
    default:
        JsonUtils.addToJsonObject(result, KEY_SDL_FILE_NAME, this.filename);
        JsonUtils.addToJsonObject(result, KEY_FILE_TYPE, this.fileType);
        JsonUtils.addToJsonObject(result, KEY_PERSISTENT_FILE, this.persistentFile);
        JsonUtils.addToJsonObject(result, KEY_SYSTEM_FILE, this.systemFile);
        JsonUtils.addToJsonObject(result, KEY_OFFSET, this.offset);
        JsonUtils.addToJsonObject(result, KEY_LENGTH, this.length);
        break;
    }
    
    return result;
}
}
