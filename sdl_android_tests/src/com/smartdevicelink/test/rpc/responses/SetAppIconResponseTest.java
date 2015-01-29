package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SetAppIconResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class SetAppIconResponseTest extends BaseRpcTests {

	@Override
    protected RPCMessage createMessage(){
        return new SetAppIconResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.SET_APP_ICON;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
    	SetAppIconResponse msg = new SetAppIconResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
