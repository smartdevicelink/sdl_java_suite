package com.smartdevicelink.test.rpc.requests;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.UnregisterAppInterface;
import com.smartdevicelink.test.BaseRpcTests;

public class UnregisterAppInterfaceTest extends BaseRpcTests {

	@Override
    protected RPCMessage createMessage(){
        return new UnregisterAppInterface();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.UNREGISTER_APP_INTERFACE;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
    	UnregisterAppInterface msg = new UnregisterAppInterface();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }
}
