package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.ButtonPressMode;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnButtonPress}
 */
public class OnButtonPressTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnButtonPress msg = new OnButtonPress();

        msg.setButtonName(TestValues.GENERAL_BUTTONNAME);
        msg.setButtonPressMode(TestValues.GENERAL_BUTTONPRESSMODE);
        msg.setCustomButtonID(TestValues.GENERAL_INT);

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
            result.put(OnButtonPress.KEY_CUSTOM_BUTTON_ID, TestValues.GENERAL_INT);
            result.put(OnButtonPress.KEY_BUTTON_NAME, TestValues.GENERAL_BUTTONNAME);
            result.put(OnButtonPress.KEY_BUTTON_PRESS_MODE, TestValues.GENERAL_BUTTONPRESSMODE);
        }catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () { 
    	// Test Values
        int customName = ( (OnButtonPress) msg ).getCustomButtonID();
        ButtonName buttonName = ( (OnButtonPress) msg ).getButtonName();
        ButtonPressMode buttonPressMode = ( (OnButtonPress) msg ).getButtonPressMode();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, customName);
	    assertEquals(TestValues.MATCH, TestValues.GENERAL_BUTTONNAME, buttonName);
	    assertEquals(TestValues.MATCH, TestValues.GENERAL_BUTTONPRESSMODE, buttonPressMode);
    
        // Invalid/Null Tests
        OnButtonPress msg = new OnButtonPress();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getCustomButtonID());
        assertNull(TestValues.NULL, msg.getButtonName());
        assertNull(TestValues.NULL, msg.getButtonPressMode());
    }
}