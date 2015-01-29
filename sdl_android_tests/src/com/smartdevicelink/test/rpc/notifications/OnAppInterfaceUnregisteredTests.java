package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnAppInterfaceUnregistered;
import com.smartdevicelink.proxy.rpc.enums.AppInterfaceUnregisteredReason;
import com.smartdevicelink.test.BaseRpcTests;

public class OnAppInterfaceUnregisteredTests extends BaseRpcTests{

    private static final AppInterfaceUnregisteredReason REASON = AppInterfaceUnregisteredReason.BLUETOOTH_OFF;

    @Override
    protected RPCMessage createMessage(){
        OnAppInterfaceUnregistered msg = new OnAppInterfaceUnregistered();

        msg.setReason(REASON);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_APP_INTERFACE_UNREGISTERED;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnAppInterfaceUnregistered.KEY_REASON, REASON);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testReason(){
        AppInterfaceUnregisteredReason reason = ( (OnAppInterfaceUnregistered) msg ).getReason();
        assertEquals("Reason didn't match input reason.", REASON, reason);
    }

    public void testNull(){
        OnAppInterfaceUnregistered msg = new OnAppInterfaceUnregistered();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Reason wasn't set, but getter method returned an object.", msg.getReason());
    }
}
