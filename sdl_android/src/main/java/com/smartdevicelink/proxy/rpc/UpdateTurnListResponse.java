package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * Update Turn List Response is sent, when UpdateTurnList has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class UpdateTurnListResponse extends RPCResponse{

    /**
     * Constructs a new UpdateTurnListResponse object
     */
    public UpdateTurnListResponse() {
        super(FunctionID.UPDATE_TURN_LIST.toString());
    }
    
    public UpdateTurnListResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new UpdateTurnListResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public UpdateTurnListResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}

}
