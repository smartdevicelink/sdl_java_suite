package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.enums.ButtonEventMode;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.test.BaseRpcTests;

public class OnButtonEventTests extends BaseRpcTests{

    private static final ButtonName      BUTTON_NAME       = ButtonName.CUSTOM_BUTTON;
    private static final ButtonEventMode BUTTON_EVENT_MODE = ButtonEventMode.BUTTONUP;
    private static final int             BUTTON_ID         = 156145;

    @Override
    protected RPCMessage createMessage(){
        OnButtonEvent msg = new OnButtonEvent();

        msg.setButtonEventMode(BUTTON_EVENT_MODE);
        msg.setButtonName(BUTTON_NAME);
        msg.setCustomButtonID(BUTTON_ID);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_BUTTON_EVENT;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnButtonEvent.KEY_BUTTON_EVENT_MODE, BUTTON_EVENT_MODE);
            result.put(OnButtonEvent.KEY_BUTTON_NAME, BUTTON_NAME);
            result.put(OnButtonEvent.KEY_CUSTOM_BUTTON_ID, BUTTON_ID);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testCustomButtonID(){
        int cmdId = ( (OnButtonEvent) msg ).getCustomButtonID();
        assertEquals("Command ID didn't match input command ID.", BUTTON_ID, cmdId);
    }

    public void testButtonEventMode(){
        ButtonEventMode mode = ( (OnButtonEvent) msg ).getButtonEventMode();
        assertEquals("Button event mode didn't match input button event mode.", BUTTON_EVENT_MODE, mode);
    }

    public void testButtonName(){
        ButtonName name = ( (OnButtonEvent) msg ).getButtonName();
        assertEquals("Command ID didn't match input command ID.", BUTTON_NAME, name);
    }

    public void testNull(){
        OnButtonEvent msg = new OnButtonEvent();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Button event mode wasn't set, but getter method returned an object.", msg.getButtonEventMode());
        assertNull("Button name wasn't set, but getter method returned an object.", msg.getButtonName());
        assertNull("Custom button ID wasn't set, but getter method returned an object.", msg.getCustomButtonID());
    }
}
