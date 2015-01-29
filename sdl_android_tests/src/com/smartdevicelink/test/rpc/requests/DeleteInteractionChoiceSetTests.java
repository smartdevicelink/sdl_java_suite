package com.smartdevicelink.test.rpc.requests;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSet;
import com.smartdevicelink.test.BaseRpcTests;

public class DeleteInteractionChoiceSetTests extends BaseRpcTests{

    private static final int CHOICE_SET_ID = 15904;

    @Override
    protected RPCMessage createMessage(){
        DeleteInteractionChoiceSet msg = new DeleteInteractionChoiceSet();

        msg.setInteractionChoiceSetID(CHOICE_SET_ID);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DELETE_INTERACTION_CHOICE_SET;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(DeleteInteractionChoiceSet.KEY_INTERACTION_CHOICE_SET_ID, CHOICE_SET_ID);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testChoiceSetId(){
        int cmdId = ( (DeleteInteractionChoiceSet) msg ).getInteractionChoiceSetID();
        assertEquals("Choice set ID didn't match input choice set ID.", CHOICE_SET_ID, cmdId);
    }

    public void testNull(){
        DeleteInteractionChoiceSet msg = new DeleteInteractionChoiceSet();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Choice set ID wasn't set, but getter method returned an object.", msg.getInteractionChoiceSetID());
    }
}
