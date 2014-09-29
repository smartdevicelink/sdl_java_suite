package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Create Interaction ChoiceSet Response is sent, when CreateInteractionChoiceSet
 * has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class CreateInteractionChoiceSetResponse extends RPCResponse {

    public CreateInteractionChoiceSetResponse() {
        super("CreateInteractionChoiceSet");
    }
    public CreateInteractionChoiceSetResponse(Hashtable hash) {
        super(hash);
    }
}