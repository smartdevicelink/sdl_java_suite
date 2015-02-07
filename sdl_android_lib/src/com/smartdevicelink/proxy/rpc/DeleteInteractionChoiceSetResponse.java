package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * Delete Interaction ChoiceSet Response is sent, when DeleteInteractionChoiceSet has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class DeleteInteractionChoiceSetResponse extends RPCResponse {

    public DeleteInteractionChoiceSetResponse() {
        super(FunctionID.DELETE_INTERACTION_CHOICE_SET);
    }

    /**
     * Creates a DeleteInteractionChoiceSetResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public DeleteInteractionChoiceSetResponse(JSONObject jsonObject) {
        super(SdlCommand.DELETE_INTERACTION_CHOICE_SET, jsonObject);
    }
}