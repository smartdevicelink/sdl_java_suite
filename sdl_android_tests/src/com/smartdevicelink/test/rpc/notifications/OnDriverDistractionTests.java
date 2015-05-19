package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;
import com.smartdevicelink.test.BaseRpcTests;

public class OnDriverDistractionTests extends BaseRpcTests{

    private static final DriverDistractionState STATUS = DriverDistractionState.DD_ON;

    @Override
    protected RPCMessage createMessage(){
        OnDriverDistraction msg = new OnDriverDistraction();

        msg.setState(STATUS);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_DRIVER_DISTRACTION;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnDriverDistraction.KEY_STATE, STATUS);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testState(){
        DriverDistractionState cmdId = ( (OnDriverDistraction) msg ).getState();
        assertEquals("Driver distraction state didn't match input state.", STATUS, cmdId);
    }

    public void testNull(){
        OnDriverDistraction msg = new OnDriverDistraction();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

         assertNull("State wasn't set, but getter method returned an object.", msg.getState());
    }
}
