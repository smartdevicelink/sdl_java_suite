package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

public class GetFileResponse extends RPCResponse {

	public static final String KEY_OFFSET = "offset";
	public static final String KEY_LENGTH = "length";
	public static final String KEY_FILE_TYPE = "fileType";
	public static final String KEY_CRC = "crc";
	/**
	 * Constructs a new PublishAppServiceResponse object
	 */
	public GetFileResponse() {
		super(FunctionID.GET_FILE.toString());
	}

	public GetFileResponse(Hashtable<String, Object> hash) {
		super(hash);
	}
	/**
	 * Constructs a new PublishAppServiceResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public GetFileResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}

	// Custom Getters / Setters

	/**
	 * File type that is being sent in response
	 * @param fileType -
	 */
	public void setFileType(FileType fileType){
		setParameters(KEY_FILE_TYPE, fileType);
	}

	/**
	 * File type that is being sent in response
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

	/**
	 * Additional CRC32 checksum to protect data integrity up to 512 Mbits
	 * minvalue="0" maxvalue="4294967295"
	 * @param crc -
	 */
	public void setCRC(Integer crc){
		setParameters(KEY_CRC, crc);
	}

	/**
	 * Additional CRC32 checksum to protect data integrity up to 512 Mbits
	 * minvalue="0" maxvalue="4294967295"
	 * @return crc
	 */
	public Integer getCRC(){
		return getInteger(KEY_CRC);
	}
}