package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

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
    /**
     * Constructs a new DeleteSubMenuResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public DeleteSubMenuResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
}