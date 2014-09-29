package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Delete Interaction ChoiceSet Response is sent, when DeleteInteractionChoiceSet has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class DeleteInteractionChoiceSetResponse extends RPCResponse {

    public DeleteInteractionChoiceSetResponse() {
        super("DeleteInteractionChoiceSet");
    }
    public DeleteInteractionChoiceSetResponse(Hashtable hash) {
        super(hash);
    }
}