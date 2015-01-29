package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SystemRequestResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class SystemRequestResponseTest extends BaseRpcTests {
	
	@Override
    protected RPCMessage createMessage(){
        return new SystemRequestResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.SYSTEM_REQUEST;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
    	SystemRequestResponse msg = new SystemRequestResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
