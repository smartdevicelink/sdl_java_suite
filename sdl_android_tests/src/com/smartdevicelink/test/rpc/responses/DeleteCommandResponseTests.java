package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DeleteCommandResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class DeleteCommandResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        return new DeleteCommandResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DELETE_COMMAND;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
        DeleteCommandResponse msg = new DeleteCommandResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }
}
