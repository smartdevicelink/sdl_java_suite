package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SpeakResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class SpeakResponseTest extends BaseRpcTests {

	@Override
    protected RPCMessage createMessage(){
        return new SpeakResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.SPEAK;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
    	SpeakResponse msg = new SpeakResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
