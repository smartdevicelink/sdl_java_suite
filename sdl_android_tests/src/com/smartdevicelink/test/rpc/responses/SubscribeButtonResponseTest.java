package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SubscribeButtonResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class SubscribeButtonResponseTest extends BaseRpcTests {

	@Override
    protected RPCMessage createMessage(){
        return new SubscribeButtonResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.SUBSCRIBE_BUTTON;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
    	SubscribeButtonResponse msg = new SubscribeButtonResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
