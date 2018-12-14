package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnKeyboardInput;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnKeyboardInput}
 */
public class OnKeyboardInputTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnKeyboardInput msg = new OnKeyboardInput();

        msg.setData(Test.GENERAL_STRING);
        msg.setEvent(Test.GENERAL_KEYBOARDEVENT);

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
            result.put(OnKeyboardInput.KEY_DATA, Test.GENERAL_STRING);
            result.put(OnKeyboardInput.KEY_EVENT, Test.GENERAL_KEYBOARDEVENT);
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
        KeyboardEvent event = ( (OnKeyboardInput) msg ).getEvent();
        String data = ( (OnKeyboardInput) msg ).getData();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_KEYBOARDEVENT, event);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, data);
    
        // Invalid/Null Tests
        OnKeyboardInput msg = new OnKeyboardInput();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getData());
        assertNull(Test.NULL, msg.getEvent());
    }
}