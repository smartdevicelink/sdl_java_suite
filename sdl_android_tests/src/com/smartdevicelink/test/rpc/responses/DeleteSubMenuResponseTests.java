package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DeleteSubMenuResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class DeleteSubMenuResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        return new DeleteSubMenuResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DELETE_SUB_MENU;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
        DeleteSubMenuResponse msg = new DeleteSubMenuResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
