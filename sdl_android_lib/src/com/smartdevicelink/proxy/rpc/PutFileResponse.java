package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Put File Response is sent, when PutFile has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class PutFileResponse extends RPCResponse {
	public static final String spaceAvailable = "spaceAvailable";

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
            parameters.put(PutFileResponse.spaceAvailable, spaceAvailable);
        } else {
        	parameters.remove(PutFileResponse.spaceAvailable);
        }
    }
    public Integer getSpaceAvailable() {
        return (Integer) parameters.get(PutFileResponse.spaceAvailable);
    }
}
