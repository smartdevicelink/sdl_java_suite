package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.ButtonPressMode;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnButtonPress}
 */
public class OnButtonPressTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnButtonPress msg = new OnButtonPress();

        msg.setButtonName(Test.GENERAL_BUTTONNAME);
        msg.setButtonPressMode(Test.GENERAL_BUTTONPRESSMODE);
        msg.setCustomButtonName(Test.GENERAL_INT);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_BUTTON_PRESS.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnButtonPress.KEY_CUSTOM_BUTTON_ID, Test.GENERAL_INT);
            result.put(OnButtonPress.KEY_BUTTON_NAME, Test.GENERAL_BUTTONNAME);
            result.put(OnButtonPress.KEY_BUTTON_PRESS_MODE, Test.GENERAL_BUTTONPRESSMODE);
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
        int customName = ( (OnButtonPress) msg ).getCustomButtonName();
        ButtonName buttonName = ( (OnButtonPress) msg ).getButtonName();
        ButtonPressMode buttonPressMode = ( (OnButtonPress) msg ).getButtonPressMode();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_INT, customName);
	    assertEquals(Test.MATCH, Test.GENERAL_BUTTONNAME, buttonName);
	    assertEquals(Test.MATCH, Test.GENERAL_BUTTONPRESSMODE, buttonPressMode);
    
        // Invalid/Null Tests
        OnButtonPress msg = new OnButtonPress();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getCustomButtonName());
        assertNull(Test.NULL, msg.getButtonName());
        assertNull(Test.NULL, msg.getButtonPressMode());
    }
}