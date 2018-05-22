package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

public class OnStreamRPC extends RPCNotification {
	public static final String KEY_FILENAME = "fileName";
	public static final String KEY_BYTESCOMPLETE = "bytesComplete";
	public static final String KEY_FILESIZE = "fileSize";
	
	public OnStreamRPC() {
		super(FunctionID.ON_STREAM_RPC.toString());
	}
		
	public void setFileName(String fileName) {
		setParameters(KEY_FILENAME, fileName);
	}
	public String getFileName() {
		return getString(KEY_FILENAME);
	}	
	
	public void setBytesComplete(Long bytesComplete) {
		setParameters(KEY_BYTESCOMPLETE, bytesComplete);
	}
	public Long getBytesComplete() {
		return getLong(KEY_BYTESCOMPLETE);
	}

	public void setFileSize(Long fileSize) {
		setParameters(KEY_FILESIZE, fileSize);
	}
	public Long getFileSize() {
		return getLong(KEY_FILESIZE);
	}
}