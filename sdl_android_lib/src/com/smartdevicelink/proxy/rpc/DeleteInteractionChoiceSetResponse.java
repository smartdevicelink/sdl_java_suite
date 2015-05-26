package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

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
}