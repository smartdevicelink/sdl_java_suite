package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Delete SubMenu Response is sent, when DeleteSubMenu has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class DeleteSubMenuResponse extends RPCResponse {

    public DeleteSubMenuResponse() {
        super("DeleteSubMenu");
    }
    public DeleteSubMenuResponse(Hashtable hash) {
        super(hash);
    }
}