package com.smartdevicelink.test.rpc.responses;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AlertResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class AlertResponseTests extends BaseRpcTests{

    private static final int TRY_AGAIN_TIME = 400;

    @Override
    protected RPCMessage createMessage(){
    	AlertResponse alert = new AlertResponse();
    	alert.setTryAgainTime(TRY_AGAIN_TIME);
        return alert;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ALERT;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(AlertResponse.KEY_TRY_AGAIN_TIME, TRY_AGAIN_TIME);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testTryAgainTime(){
        int tryAgainTime = ( (AlertResponse) msg ).getTryAgainTime();
        assertEquals("Try again time didn't match expected time.", TRY_AGAIN_TIME, tryAgainTime);
    }

    public void testNull(){
        AlertResponse msg = new AlertResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Try again time wasn't set, but getter method returned an object.", msg.getTryAgainTime());
    }

}
