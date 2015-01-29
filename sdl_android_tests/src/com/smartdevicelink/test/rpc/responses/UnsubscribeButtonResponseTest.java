package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class UnsubscribeButtonResponseTest extends BaseRpcTests {

	@Override
    protected RPCMessage createMessage(){
        return new UnsubscribeButtonResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.UNSUBSCRIBE_BUTTON;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
    	UnsubscribeButtonResponse msg = new UnsubscribeButtonResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }
}
