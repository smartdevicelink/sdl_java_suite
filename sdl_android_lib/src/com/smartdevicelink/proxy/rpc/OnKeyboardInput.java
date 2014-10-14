package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;
import com.smartdevicelink.util.DebugTool;

import java.util.Hashtable;


public class OnKeyboardInput extends RPCNotification {
	public static final String data = "data";
	public static final String event = "event";

    public OnKeyboardInput() {
        super("OnKeyboardInput");
    }

    public OnKeyboardInput(Hashtable hash) {
        super(hash);
    }

    public KeyboardEvent getEvent() {
        Object obj = parameters.get(OnKeyboardInput.event);
        if (obj instanceof KeyboardEvent) {
            return (KeyboardEvent) obj;
        } else if (obj instanceof String) {
            KeyboardEvent theCode = null;
            try {
                theCode = KeyboardEvent.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnKeyboardInput.event, e);
            }
            return theCode;
        }
        return null;
    }

    public void setEvent(KeyboardEvent event) {
        if (event != null) {
            parameters.put(OnKeyboardInput.event, event);
        } else {
            parameters.remove(OnKeyboardInput.event);
        }
    }

    public void setData(String data) {
        if (data != null) {
            parameters.put(OnKeyboardInput.data, data);
        } else {
            parameters.remove(OnKeyboardInput.data);
        }
    }
    public String getData() {
        Object obj = parameters.get(OnKeyboardInput.data);
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
