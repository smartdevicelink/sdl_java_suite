package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ShowResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class ShowResponseTest extends BaseRpcTests {

	@Override
    protected RPCMessage createMessage(){
        return new ShowResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.SHOW;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
    	ShowResponse msg = new ShowResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
