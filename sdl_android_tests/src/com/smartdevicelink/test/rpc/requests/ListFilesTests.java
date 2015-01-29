package com.smartdevicelink.test.rpc.requests;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.test.BaseRpcTests;

public class ListFilesTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        return new ListFiles();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.LIST_FILES;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
        ListFiles msg = new ListFiles();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
