package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Put File Response is sent, when PutFile has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class PutFileResponse extends RPCResponse {
	public static final String KEY_SPACE_AVAILABLE = "spaceAvailable";

	/**
	 * Constructs a new PutFileResponse object
	 */
    public PutFileResponse() {
        super(FunctionID.PUT_FILE.toString());
    }

	/**
	 * Constructs a new PutFileResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public PutFileResponse(Hashtable<String, Object> hash) {
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
