package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnTBTClientState;
import com.smartdevicelink.proxy.rpc.enums.TBTState;
import com.smartdevicelink.test.BaseRpcTests;

public class OnTBTClientStateTests extends BaseRpcTests{

    private static final TBTState STATE = TBTState.NEXT_TURN_REQUEST;

    @Override
    protected RPCMessage createMessage(){
        OnTBTClientState msg = new OnTBTClientState();

        msg.setState(STATE);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_TBT_CLIENT_STATE;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnTBTClientState.KEY_STATE, STATE);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testState(){
        TBTState data = ( (OnTBTClientState) msg ).getState();
        assertEquals("Data didn't match input data.", STATE, data);
    }

    public void testNull(){
        OnTBTClientState msg = new OnTBTClientState();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("State wasn't set, but getter method returned an object.", msg.getState());
    }
}
