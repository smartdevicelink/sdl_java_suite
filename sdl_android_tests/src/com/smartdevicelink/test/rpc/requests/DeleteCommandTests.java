package com.smartdevicelink.test.rpc.requests;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.test.BaseRpcTests;

public class DeleteCommandTests extends BaseRpcTests{

    private static final int COMMAND_ID = 1045;

    @Override
    protected RPCMessage createMessage(){
        DeleteCommand msg = new DeleteCommand();

        msg.setCmdID(COMMAND_ID);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DELETE_COMMAND;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(DeleteCommand.KEY_CMD_ID, COMMAND_ID);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testCommandId(){
        int cmdId = ( (DeleteCommand) msg ).getCmdID();
        assertEquals("Command ID didn't match input command ID.", COMMAND_ID, cmdId);
    }

    public void testNull(){
        DeleteCommand msg = new DeleteCommand();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Command ID wasn't set, but getter method returned an object.", msg.getCmdID());
    }

}
