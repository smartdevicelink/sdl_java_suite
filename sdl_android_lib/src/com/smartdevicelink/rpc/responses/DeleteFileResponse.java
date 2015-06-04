package com.smartdevicelink.rpc.responses;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Delete File Response is sent, when DeleteFile has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class DeleteFileResponse extends RpcResponse {
	public static final String KEY_SPACE_AVAILABLE = "spaceAvailable";

    public DeleteFileResponse() {
        super(FunctionId.DELETE_FILE.toString());
    }
    public DeleteFileResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setSpaceAvailable(Integer spaceAvailable) {
        if (spaceAvailable != null) {
            parameters.put(KEY_SPACE_AVAILABLE, spaceAvailable);
        } else {
        	parameters.remove(KEY_SPACE_AVAILABLE);
        }
    }
    public Integer getSpaceAvailable() {
        return (Integer) parameters.get(KEY_SPACE_AVAILABLE);
    }
}
