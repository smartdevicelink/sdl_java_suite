package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * Delete Interaction ChoiceSet Response is sent, when DeleteInteractionChoiceSet has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class DeleteInteractionChoiceSetResponse extends RPCResponse {

    public DeleteInteractionChoiceSetResponse() {
        super(FunctionID.DELETE_INTERACTION_CHOICE_SET.toString());
    }
    public DeleteInteractionChoiceSetResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a new DeleteInteractionChoiceSetResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public DeleteInteractionChoiceSetResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
}