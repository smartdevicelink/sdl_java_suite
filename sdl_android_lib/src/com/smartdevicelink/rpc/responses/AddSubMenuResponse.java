package com.smartdevicelink.rpc.responses;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Add SubMenu Response is sent, when AddSubMenu has been called
 * @since SmartDeviceLink 1.0
 */
public class AddSubMenuResponse extends RpcResponse {

    public AddSubMenuResponse() {
        super(FunctionId.ADD_SUB_MENU.toString());
    }
    public AddSubMenuResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}