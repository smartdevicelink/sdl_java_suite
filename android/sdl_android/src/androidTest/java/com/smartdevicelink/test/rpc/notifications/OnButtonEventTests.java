package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.enums.ButtonEventMode;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnButtonEvent}
 */
public class OnButtonEventTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnButtonEvent msg = new OnButtonEvent();

        msg.setButtonEventMode(TestValues.GENERAL_BUTTONEVENTMODE);
        msg.setButtonName(TestValues.GENERAL_BUTTONNAME);
        msg.setCustomButtonID(TestValues.GENERAL_INT);

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
            result.put(OnButtonEvent.KEY_BUTTON_EVENT_MODE, TestValues.GENERAL_BUTTONEVENTMODE);
            result.put(OnButtonEvent.KEY_BUTTON_NAME, TestValues.GENERAL_BUTTONNAME);
            result.put(OnButtonEvent.KEY_CUSTOM_BUTTON_ID, TestValues.GENERAL_INT);
        }catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    @Test
    public void testRpcValues () { 
    	// Test Values
        int cmdId = ( (OnButtonEvent) msg ).getCustomButtonID();
        ButtonEventMode mode = ( (OnButtonEvent) msg ).getButtonEventMode();
        ButtonName name = ( (OnButtonEvent) msg ).getButtonName();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, cmdId);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BUTTONEVENTMODE, mode);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BUTTONNAME, name);
        
        // Invalid/Null Tests
        OnButtonEvent msg = new OnButtonEvent();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getButtonEventMode());
        assertNull(TestValues.NULL, msg.getButtonName());
        assertNull(TestValues.NULL, msg.getCustomButtonID());
    }
}