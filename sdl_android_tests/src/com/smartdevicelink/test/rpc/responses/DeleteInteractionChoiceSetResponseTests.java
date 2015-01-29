package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSetResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class DeleteInteractionChoiceSetResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        return new DeleteInteractionChoiceSetResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DELETE_INTERACTION_CHOICE_SET;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
        DeleteInteractionChoiceSetResponse msg = new DeleteInteractionChoiceSetResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
