package com.smartdevicelink.rpc.responses;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Delete SubMenu Response is sent, when DeleteSubMenu has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class DeleteSubMenuResponse extends RpcResponse {

    public DeleteSubMenuResponse() {
        super(FunctionId.DELETE_SUB_MENU.toString());
    }
    public DeleteSubMenuResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}