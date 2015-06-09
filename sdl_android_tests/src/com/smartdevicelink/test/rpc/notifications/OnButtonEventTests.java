package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.enums.ButtonEventMode;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnButtonEvent}
 */
public class OnButtonEventTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnButtonEvent msg = new OnButtonEvent();

        msg.setButtonEventMode(Test.GENERAL_BUTTONEVENTMODE);
        msg.setButtonName(Test.GENERAL_BUTTONNAME);
        msg.setCustomButtonID(Test.GENERAL_INT);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_BUTTON_EVENT.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnButtonEvent.KEY_BUTTON_EVENT_MODE, Test.GENERAL_BUTTONEVENTMODE);
            result.put(OnButtonEvent.KEY_BUTTON_NAME, Test.GENERAL_BUTTONNAME);
            result.put(OnButtonEvent.KEY_CUSTOM_BUTTON_ID, Test.GENERAL_INT);
        }catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }

        return result;
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () { 
    	// Test Values
        int cmdId = ( (OnButtonEvent) msg ).getCustomButtonID();
        ButtonEventMode mode = ( (OnButtonEvent) msg ).getButtonEventMode();
        ButtonName name = ( (OnButtonEvent) msg ).getButtonName();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_INT, cmdId);
        assertEquals(Test.MATCH, Test.GENERAL_BUTTONEVENTMODE, mode);
        assertEquals(Test.MATCH, Test.GENERAL_BUTTONNAME, name);
        
        // Invalid/Null Tests
        OnButtonEvent msg = new OnButtonEvent();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getButtonEventMode());
        assertNull(Test.NULL, msg.getButtonName());
        assertNull(Test.NULL, msg.getCustomButtonID());
    }
}