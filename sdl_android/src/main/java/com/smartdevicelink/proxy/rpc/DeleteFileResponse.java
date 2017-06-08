package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;

/**
 * Delete File Response is sent, when DeleteFile has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class DeleteFileResponse extends RPCResponse {
	public static final String KEY_SPACE_AVAILABLE = "spaceAvailable";
	/** Constructs a new DeleteFileResponse object
	 * 
	 */

    public DeleteFileResponse() {
        super(FunctionID.DELETE_FILE.toString());
    }
    public DeleteFileResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setSpaceAvailable(Integer spaceAvailable) {
        setParameters(KEY_SPACE_AVAILABLE, spaceAvailable);
    }
    public Integer getSpaceAvailable() {
        return getInteger(KEY_SPACE_AVAILABLE);
    }
}
