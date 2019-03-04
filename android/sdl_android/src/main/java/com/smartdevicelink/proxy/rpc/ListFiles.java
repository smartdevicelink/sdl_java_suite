package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Requests the current list of resident filenames for the registered app. Not
 * supported on First generation SDL vehicles.
 * 
 * <p><b>Request</b></p>
 * <p>No parameters.</p>
 * <p><b>Response:</b></p>
 * Returns the current list of resident filenames for the registered app along with the current space available.
 * <p><b>Non-default Result Codes:</b></p>
 * <p>SUCCESS</p>
 * <p>INVALID_DATA</p>
 * <p>OUT_OF_MEMORY</p>
 * <p>TOO_MANY_PENDING_REQUESTS</p>
 * <p>APPLICATION_NOT_REGISTERED</p>
 * <p>GENERIC_ERROR </p>   
 * <p>REJECTED</p>
 * @since SmartDeviceLink 2.0
 */
public class ListFiles extends RPCRequest {

	/**
	 * Constructs a new ListFiles object
	 */
    public ListFiles() {
        super(FunctionID.LIST_FILES.toString());
    }

	/**
	 * <p>Constructs a new ListFiles object indicated by the Hashtable parameter
	 * </p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ListFiles(Hashtable<String, Object> hash) {
        super(hash);
    }
}