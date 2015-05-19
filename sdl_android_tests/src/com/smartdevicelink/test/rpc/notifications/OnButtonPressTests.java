package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.ButtonPressMode;
import com.smartdevicelink.test.BaseRpcTests;

public class OnButtonPressTests extends BaseRpcTests{

    private static final ButtonName      BUTTON_NAME       = ButtonName.CUSTOM_BUTTON;
    private static final ButtonPressMode BUTTON_PRESS_MODE = ButtonPressMode.LONG;
    private static final int             BUTTON_ID         = 825;

    @Override
    protected RPCMessage createMessage(){
        OnButtonPress msg = new OnButtonPress();

        msg.setButtonName(BUTTON_NAME);
        msg.setButtonPressMode(BUTTON_PRESS_MODE);
        msg.setCustomButtonName(BUTTON_ID);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_BUTTON_PRESS;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnButtonPress.KEY_CUSTOM_BUTTON_ID, BUTTON_ID);
            result.put(OnButtonPress.KEY_BUTTON_NAME, BUTTON_NAME);
            result.put(OnButtonPress.KEY_BUTTON_PRESS_MODE, BUTTON_PRESS_MODE);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testButtonId(){
        int cmdId = ( (OnButtonPress) msg ).getCustomButtonName();
        assertEquals("Button ID didn't match input button ID.", BUTTON_ID, cmdId);
    }

    public void testButtonName(){
        ButtonName cmdId = ( (OnButtonPress) msg ).getButtonName();
        assertEquals("Button name didn't match input button name.", BUTTON_NAME, cmdId);
    }

    public void testButtonPressMode(){
        ButtonPressMode cmdId = ( (OnButtonPress) msg ).getButtonPressMode();
        assertEquals("Button press mode didn't match input button press mode.", BUTTON_PRESS_MODE, cmdId);
    }

    public void testNull(){
        OnButtonPress msg = new OnButtonPress();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Button ID wasn't set, but getter method returned an object.", msg.getCustomButtonName());
        assertNull("Button name wasn't set, but getter method returned an object.", msg.getButtonName());
        assertNull("Button press mode wasn't set, but getter method returned an object.", msg.getButtonPressMode());
    }
}
