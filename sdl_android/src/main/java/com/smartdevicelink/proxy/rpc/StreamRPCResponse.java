package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;

import static com.smartdevicelink.proxy.constants.Names.timeout;

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
		setParameters(KEY_FILENAME, fileName);
	}
	public String getFileName() {
		return (String) parameters.get(KEY_FILENAME);
	}
	
	public void setFileSize(Long fileSize) {
		setParameters(KEY_FILESIZE, fileSize);
	}
	public Long getFileSize() {
		return (Long) parameters.get(KEY_FILESIZE);
	}		
	
}
