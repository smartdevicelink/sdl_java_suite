package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Add SubMenu Response is sent, when AddSubMenu has been called
 * @since SmartDeviceLink 1.0
 */
public class AddSubMenuResponse extends RPCResponse {

    public AddSubMenuResponse() {
        super("AddSubMenu");
    }
    public AddSubMenuResponse(Hashtable hash) {
        super(hash);
    }
}