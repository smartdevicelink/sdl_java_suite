package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSetResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class CreateInteractionChoiceSetResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        return new CreateInteractionChoiceSetResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.CREATE_INTERACTION_CHOICE_SET;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
        CreateInteractionChoiceSetResponse msg = new CreateInteractionChoiceSetResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
