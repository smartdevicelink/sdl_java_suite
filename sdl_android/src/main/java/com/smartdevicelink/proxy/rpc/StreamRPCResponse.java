package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;

public class StreamRPCResponse extends RPCResponse {
	public static final String KEY_FILENAME = "fileName";
	public static final String KEY_FILESIZE = "fileSize";
	
    public StreamRPCResponse() {
        super(FunctionID.STREAM_RPC.toString());
    }
    public StreamRPCResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    
	public void setFileName(String fileName) {
		setParameter(KEY_FILENAME, fileName);
	}
	public String getFileName() {
		return getString(KEY_FILENAME);
	}
	
	public void setFileSize(Long fileSize) {
		setParameter(KEY_FILESIZE, fileSize);
	}
	public Long getFileSize() {
		return getLong(KEY_FILESIZE);
	}		
	
}
