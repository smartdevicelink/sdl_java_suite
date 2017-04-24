package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

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
}