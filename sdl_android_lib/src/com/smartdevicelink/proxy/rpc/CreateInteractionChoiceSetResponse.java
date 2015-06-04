package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Create Interaction ChoiceSet Response is sent, when CreateInteractionChoiceSet
 * has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class CreateInteractionChoiceSetResponse extends RpcResponse {

    public CreateInteractionChoiceSetResponse() {
        super(FunctionId.CREATE_INTERACTION_CHOICE_SET.toString());
    }
    public CreateInteractionChoiceSetResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}