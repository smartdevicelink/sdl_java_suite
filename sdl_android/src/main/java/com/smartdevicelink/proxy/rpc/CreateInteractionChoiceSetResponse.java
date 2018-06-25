package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * Create Interaction ChoiceSet Response is sent, when CreateInteractionChoiceSet
 * has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class CreateInteractionChoiceSetResponse extends RPCResponse {
	/** 
	 * Constructs a new CreateInteractionChoiceSetResponse object
	 */

    public CreateInteractionChoiceSetResponse() {
        super(FunctionID.CREATE_INTERACTION_CHOICE_SET.toString());
    }
    public CreateInteractionChoiceSetResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new CreateInteractionChoiceSetResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public CreateInteractionChoiceSetResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
}