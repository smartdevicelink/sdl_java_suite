package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
/**
 * Set App Icon Response is sent, when SetAppIcon has been called.
 * 
 * @since SmartDeviceLink 2.0
 */
public class SetAppIconResponse extends RPCResponse {
	/**
	 * Constructs a new SetAppIconResponse object
	 */

    public SetAppIconResponse() {
        super(FunctionID.SET_APP_ICON.toString());
    }
    /**
	 * Constructs a new SetAppIconResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */

    public SetAppIconResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}