package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

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
    /**
     * Constructs a new AddSubMenuResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public AddSubMenuResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
}