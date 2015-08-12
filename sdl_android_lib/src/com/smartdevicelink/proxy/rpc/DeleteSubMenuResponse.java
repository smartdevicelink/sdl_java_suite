package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Delete SubMenu Response is sent, when DeleteSubMenu has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class DeleteSubMenuResponse extends RPCResponse {
	/**
	 * Constructs a new DeleteSubMenuResponse object
	 */

    public DeleteSubMenuResponse() {
        super(FunctionID.DELETE_SUB_MENU.toString());
    }
    public DeleteSubMenuResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}