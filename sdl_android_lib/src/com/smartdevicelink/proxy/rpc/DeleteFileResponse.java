package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.constants.Names;

/**
 * Delete File Response is sent, when DeleteFile has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class DeleteFileResponse extends RPCResponse {

    public DeleteFileResponse() {
        super("DeleteFile");
    }
    public DeleteFileResponse(Hashtable hash) {
        super(hash);
    }
    public void setSpaceAvailable(Integer spaceAvailable) {
        if (spaceAvailable != null) {
            parameters.put(Names.spaceAvailable, spaceAvailable);
        } else {
        	parameters.remove(Names.spaceAvailable);
        }
    }
    public Integer getSpaceAvailable() {
        return (Integer) parameters.get(Names.spaceAvailable);
    }
}