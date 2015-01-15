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

    public SetAppIconResponse() {
        super(FunctionID.SET_APP_ICON);
    }
    public SetAppIconResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}