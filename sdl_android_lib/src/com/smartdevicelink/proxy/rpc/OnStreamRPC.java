package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

public class OnStreamRPC extends RPCNotification {
	public static final String KEY_FILENAME = "fileName";
	public static final String KEY_BYTESCOMPLETE = "bytesComplete";
	public static final String KEY_FILESIZE = "fileSize";
	
	public OnStreamRPC() {
		super(FunctionID.ON_STREAM_RPC);
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
	
	public void setBytesComplete(Integer bytesComplete) {
    	if (bytesComplete != null) {
    		parameters.put(KEY_BYTESCOMPLETE, bytesComplete);
    	} else {
    		parameters.remove(KEY_BYTESCOMPLETE);
    	}
	}
	public Integer getBytesComplete() {
		return (Integer) parameters.get(KEY_BYTESCOMPLETE);
	}

	public void setFileSize(Integer fileSize) {
    	if (fileSize != null) {
    		parameters.put(KEY_FILESIZE, fileSize);
    	} else {
    		parameters.remove(KEY_FILESIZE);
    	}
	}
	public Integer getFileSize() {
		return (Integer) parameters.get(KEY_FILESIZE);
	}
}