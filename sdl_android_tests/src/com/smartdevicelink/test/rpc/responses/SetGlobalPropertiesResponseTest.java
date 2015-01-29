package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SetGlobalPropertiesResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class SetGlobalPropertiesResponseTest extends BaseRpcTests {

	@Override
    protected RPCMessage createMessage(){
        return new SetGlobalPropertiesResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.SET_GLOBAL_PROPERTIES;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
    	SetGlobalPropertiesResponse msg = new SetGlobalPropertiesResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
