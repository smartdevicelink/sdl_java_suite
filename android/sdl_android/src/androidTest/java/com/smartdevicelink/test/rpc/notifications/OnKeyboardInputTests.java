package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnKeyboardInput;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;
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
 * {@link com.smartdevicelink.proxy.rpc.OnKeyboardInput}
 */
public class OnKeyboardInputTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnKeyboardInput msg = new OnKeyboardInput();

        msg.setData(TestValues.GENERAL_STRING);
        msg.setEvent(TestValues.GENERAL_KEYBOARDEVENT);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_KEYBOARD_INPUT.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnKeyboardInput.KEY_DATA, TestValues.GENERAL_STRING);
            result.put(OnKeyboardInput.KEY_EVENT, TestValues.GENERAL_KEYBOARDEVENT);
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
        KeyboardEvent event = ( (OnKeyboardInput) msg ).getEvent();
        String data = ( (OnKeyboardInput) msg ).getData();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_KEYBOARDEVENT, event);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, data);
    
        // Invalid/Null Tests
        OnKeyboardInput msg = new OnKeyboardInput();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getData());
        assertNull(TestValues.NULL, msg.getEvent());
    }
}