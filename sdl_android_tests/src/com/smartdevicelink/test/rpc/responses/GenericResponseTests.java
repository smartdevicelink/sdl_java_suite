package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GenericResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class GenericResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        return new GenericResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GENERIC_RESPONSE;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
        GenericResponse msg = new GenericResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }
}
