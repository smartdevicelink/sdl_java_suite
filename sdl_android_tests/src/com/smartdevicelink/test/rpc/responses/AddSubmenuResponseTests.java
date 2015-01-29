package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AddSubMenuResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class AddSubmenuResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        return new AddSubMenuResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ADD_SUB_MENU;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
        AddSubMenuResponse msg = new AddSubMenuResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
