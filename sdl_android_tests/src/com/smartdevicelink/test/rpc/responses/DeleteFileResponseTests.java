package com.smartdevicelink.test.rpc.responses;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class DeleteFileResponseTests extends BaseRpcTests{

    private static final int SPACE_AVAILABLE = 58534924;

    @Override
    protected RPCMessage createMessage(){
        DeleteFileResponse msg = new DeleteFileResponse();

        msg.setSpaceAvailable(SPACE_AVAILABLE);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DELETE_FILE;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(DeleteFileResponse.KEY_SPACE_AVAILABLE, SPACE_AVAILABLE);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testSpaceAvailable(){
        int spaceAvailable = ( (DeleteFileResponse) msg ).getSpaceAvailable();
        assertEquals("Space available didn't match input space available.", SPACE_AVAILABLE, spaceAvailable);
    }

    public void testNull(){
        DeleteFileResponse msg = new DeleteFileResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Space available wasn't set, but getter method returned an object.", msg.getSpaceAvailable());
    }
}
