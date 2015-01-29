package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ChangeRegistrationResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class ChangeRegistrationResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        return new ChangeRegistrationResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.CHANGE_REGISTRATION;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
        ChangeRegistrationResponse msg = new ChangeRegistrationResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
