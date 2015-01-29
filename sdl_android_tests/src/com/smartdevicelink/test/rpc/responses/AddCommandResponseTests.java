package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class AddCommandResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        return new AddCommandResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ADD_COMMAND;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
        AddCommandResponse msg = new AddCommandResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
