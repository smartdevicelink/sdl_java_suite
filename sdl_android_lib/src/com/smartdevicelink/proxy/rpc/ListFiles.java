package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

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
        super(FunctionID.LIST_FILES);
    }

    /**
     * Creates a ListFiles object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ListFiles(JSONObject jsonObject) {
        super(jsonObject);
    }
}