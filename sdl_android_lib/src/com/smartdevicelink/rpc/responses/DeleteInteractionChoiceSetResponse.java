package com.smartdevicelink.rpc.responses;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Delete Interaction ChoiceSet Response is sent, when DeleteInteractionChoiceSet has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class DeleteInteractionChoiceSetResponse extends RpcResponse {

    public DeleteInteractionChoiceSetResponse() {
        super(FunctionId.DELETE_INTERACTION_CHOICE_SET.toString());
    }
    public DeleteInteractionChoiceSetResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}