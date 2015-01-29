package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ResetGlobalPropertiesResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class ResetGlobalPropertiesResponseTest extends BaseRpcTests {

	@Override
    protected RPCMessage createMessage(){
        return new ResetGlobalPropertiesResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.RESET_GLOBAL_PROPERTIES;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
    	ResetGlobalPropertiesResponse msg = new ResetGlobalPropertiesResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
