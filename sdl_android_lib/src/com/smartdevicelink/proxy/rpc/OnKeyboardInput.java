package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;


public class OnKeyboardInput extends RPCNotification {
	public static final String KEY_DATA = "data";
	public static final String KEY_EVENT = "event";

    public OnKeyboardInput() {
        super(FunctionID.ON_KEYBOARD_INPUT.toString());
    }

    public OnKeyboardInput(Hashtable<String, Object> hash) {
        super(hash);
    }

    public KeyboardEvent getEvent() {
        Object obj = parameters.get(KEY_EVENT);
        if (obj instanceof KeyboardEvent) {
            return (KeyboardEvent) obj;
        } else if (obj instanceof String) {
            return KeyboardEvent.valueForString((String) obj);
        }
        return null;
    }

    public void setEvent(KeyboardEvent event) {
        if (event != null) {
            parameters.put(KEY_EVENT, event);
        } else {
            parameters.remove(KEY_EVENT);
        }
    }

    public void setData(String data) {
        if (data != null) {
            parameters.put(KEY_DATA, data);
        } else {
            parameters.remove(KEY_DATA);
        }
    }
    public String getData() {
        Object obj = parameters.get(KEY_DATA);
        if (obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    @Override
    public String toString(){
        String result =  this.getFunctionName() +": " + " data: " + this.getData() + " event:" + this.getEvent().toString();
        return result;
    }

}
