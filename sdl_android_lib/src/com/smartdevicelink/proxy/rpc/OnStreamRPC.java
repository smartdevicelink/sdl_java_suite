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
    	if (fileName != null) {
    		parameters.put(KEY_FILENAME, fileName);
    	} else {
    		parameters.remove(KEY_FILENAME);
    	}
	}
	public String getFileName() {
		return (String) parameters.get(KEY_FILENAME);
	}	
	
	public void setBytesComplete(Long bytesComplete) {
    	if (bytesComplete != null) {
    		parameters.put(KEY_BYTESCOMPLETE, bytesComplete);
    	} else {
    		parameters.remove(KEY_BYTESCOMPLETE);
    	}
	}
	public Long getBytesComplete() {
		return (Long) parameters.get(KEY_BYTESCOMPLETE);
	}

	public void setFileSize(Long fileSize) {
    	if (fileSize != null) {
    		parameters.put(KEY_FILESIZE, fileSize);
    	} else {
    		parameters.remove(KEY_FILESIZE);
    	}
	}
	public Long getFileSize() {
		return (Long) parameters.get(KEY_FILESIZE);
	}
}