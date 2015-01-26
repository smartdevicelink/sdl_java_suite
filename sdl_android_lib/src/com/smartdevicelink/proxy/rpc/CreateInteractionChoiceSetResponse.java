package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Create Interaction ChoiceSet Response is sent, when CreateInteractionChoiceSet
 * has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class CreateInteractionChoiceSetResponse extends RPCResponse {

    public CreateInteractionChoiceSetResponse() {
        super(FunctionID.CREATE_INTERACTION_CHOICE_SET);
    }

    /**
     * Creates a CreateInteractionChoiceSetResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public CreateInteractionChoiceSetResponse(JSONObject jsonObject) {
        super(jsonObject);
    }
}