package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnKeyboardInput;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;
import com.smartdevicelink.test.BaseRpcTests;

public class OnKeyboardInputTests extends BaseRpcTests{

    private static final String        DATA           = "A";
    private static final KeyboardEvent KEYBOARD_EVENT = KeyboardEvent.KEYPRESS;

    @Override
    protected RPCMessage createMessage(){
        OnKeyboardInput msg = new OnKeyboardInput();

        msg.setData(DATA);
        msg.setEvent(KEYBOARD_EVENT);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_KEYBOARD_INPUT;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnKeyboardInput.KEY_DATA, DATA);
            result.put(OnKeyboardInput.KEY_EVENT, KEYBOARD_EVENT);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testData(){
        String cmdId = ( (OnKeyboardInput) msg ).getData();
        assertEquals("Data didn't match input data.", DATA, cmdId);
    }

    public void testKeyboardEvent(){
        KeyboardEvent cmdId = ( (OnKeyboardInput) msg ).getEvent();
        assertEquals("Keyboard event didn't match input event.", KEYBOARD_EVENT, cmdId);
    }

    public void testNull(){
        OnKeyboardInput msg = new OnKeyboardInput();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Data wasn't set, but getter method returned an object.", msg.getData());
        assertNull("Keyboard event wasn't set, but getter method returned an object.", msg.getEvent());
    }
}
