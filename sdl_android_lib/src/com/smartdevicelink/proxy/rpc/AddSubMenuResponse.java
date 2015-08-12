package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Add SubMenu Response is sent, when AddSubMenu has been called
 * @since SmartDeviceLink 1.0
 */
public class AddSubMenuResponse extends RPCResponse {
	/**
	 * Constructs a new AddSubMenuResponse object
	 */

    public AddSubMenuResponse() {
        super(FunctionID.ADD_SUB_MENU.toString());
    }
    public AddSubMenuResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}