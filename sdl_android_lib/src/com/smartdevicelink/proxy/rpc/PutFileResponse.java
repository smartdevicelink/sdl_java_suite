package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.constants.Names;

/**
 * Put File Response is sent, when PutFile has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class PutFileResponse extends RPCResponse {

	/**
	 * Constructs a new PutFileResponse object
	 */
    public PutFileResponse() {
        super("PutFile");
    }

	/**
	 * Constructs a new PutFileResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public PutFileResponse(Hashtable hash) {
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