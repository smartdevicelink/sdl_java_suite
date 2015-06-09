package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Requests the current list of resident filenames for the registered app. Not
 * supported on First generation SDL vehicles
 * <p>
 * 
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
	 * Constructs a new ListFiles object indicated by the Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ListFiles(Hashtable<String, Object> hash) {
        super(hash);
    }
}