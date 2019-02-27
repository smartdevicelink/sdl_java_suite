package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.FileType;

import java.util.Hashtable;

/**
 * This request is sent to the module to retrieve a file
 */
public class GetFile extends RPCRequest {

	public static final String KEY_FILE_NAME = "fileName";
	public static final String KEY_APP_SERVICE_ID = "appServiceId";
	public static final String KEY_FILE_TYPE = "fileType";
	public static final String KEY_OFFSET = "offset";
	public static final String KEY_LENGTH = "length";

	// Constructors

	public GetFile() {
		super(FunctionID.GET_FILE.toString());
	}

	public GetFile(Hashtable<String, Object> hash) {
		super(hash);
	}

	public GetFile(@NonNull String fileName) {
		this();
		setFileName(fileName);
	}

	// Setters and Getters

	/**
	 * File name that should be retrieved.
	 * maxlength="255"
	 * @param fileName -
	 */
	public void setFileName(@NonNull String fileName){
		setParameters(KEY_FILE_NAME, fileName);
	}

	/**
	 * File name that should be retrieved.
	 * maxlength="255"
	 * @return fileName
	 */
	public String getFileName(){
		return getString(KEY_FILE_NAME);
	}

	/**
	 * ID of the service that should have uploaded the requested file
	 * @param appServiceId -
	 */
	public void setAppServiceId(String appServiceId){
		setParameters(KEY_APP_SERVICE_ID, appServiceId);
	}

	/**
	 * ID of the service that should have uploaded the requested file
	 * @return appServiceId
	 */
	public String getAppServiceId(){
		return getString(KEY_APP_SERVICE_ID);
	}

	/**
	 * Selected file type.
	 * @param fileType -
	 */
	public void setFileType(FileType fileType){
		setParameters(KEY_FILE_TYPE, fileType);
	}

	/**
	 * Selected file type.
	 * @return fileType
	 */
	public FileType getFileType(){
		return (FileType) getObject(FileType.class, KEY_FILE_TYPE);
	}

	/**
	 * Optional offset in bytes for resuming partial data chunks
	 * minvalue="0" maxvalue="2000000000"
	 * @param offset -
	 */
	public void setOffset(Integer offset){
		setParameters(KEY_OFFSET, offset);
	}

	/**
	 * Optional offset in bytes for resuming partial data chunks
	 * minvalue="0" maxvalue="2000000000"
	 * @return offset
	 */
	public Integer getOffset(){
		return getInteger(KEY_OFFSET);
	}

	/**
	 * Optional length in bytes for resuming partial data chunks if offset is set to 0, then length
	 * is the total length of the file to be downloaded
	 * minvalue="0" maxvalue="2000000000"
	 * @param length -
	 */
	public void setLength(Integer length){
		setParameters(KEY_LENGTH, length);
	}

	/**
	 * Optional length in bytes for resuming partial data chunks if offset is set to 0, then length
	 * is the total length of the file to be downloaded
	 * minvalue="0" maxvalue="2000000000"
	 * @return length
	 */
	public Integer getLength(){
		return getInteger(KEY_LENGTH);
	}

}
