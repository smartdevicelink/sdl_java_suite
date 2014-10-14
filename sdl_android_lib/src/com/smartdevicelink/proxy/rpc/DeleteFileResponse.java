package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Delete File Response is sent, when DeleteFile has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class DeleteFileResponse extends RPCResponse {
	public static final String spaceAvailable = "spaceAvailable";

    public DeleteFileResponse() {
        super("DeleteFile");
    }
    public DeleteFileResponse(Hashtable hash) {
        super(hash);
    }
    public void setSpaceAvailable(Integer spaceAvailable) {
        if (spaceAvailable != null) {
            parameters.put(DeleteFileResponse.spaceAvailable, spaceAvailable);
        } else {
        	parameters.remove(DeleteFileResponse.spaceAvailable);
        }
    }
    public Integer getSpaceAvailable() {
        return (Integer) parameters.get(DeleteFileResponse.spaceAvailable);
    }
}
