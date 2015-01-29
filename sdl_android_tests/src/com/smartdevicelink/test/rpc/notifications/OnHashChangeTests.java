package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnHashChange;
import com.smartdevicelink.test.BaseRpcTests;

public class OnHashChangeTests extends BaseRpcTests{

    private static final String HASH_ID = "agh4lg2hb1g9gq3";

    @Override
    protected RPCMessage createMessage(){
        OnHashChange msg = new OnHashChange();

        msg.setHashID(HASH_ID);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_HASH_CHANGE;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnHashChange.KEY_HASH_ID, HASH_ID);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testHashID(){
        String hashId = ( (OnHashChange) msg ).getHashID();
        assertEquals("Hash ID didn't match input hash ID.", HASH_ID, hashId);
    }

    public void testNull(){
        OnHashChange msg = new OnHashChange();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Hash ID wasn't set, but getter method returned an object.", msg.getHashID());
    }
}
