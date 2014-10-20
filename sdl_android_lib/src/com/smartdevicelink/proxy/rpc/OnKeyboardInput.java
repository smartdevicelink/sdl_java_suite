package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;
import com.smartdevicelink.util.DebugTool;

import java.util.Hashtable;


public class OnKeyboardInput extends RPCNotification {

    public OnKeyboardInput() {
        super("OnKeyboardInput");
    }

    public OnKeyboardInput(Hashtable<String, Object> hash) {
        super(hash);
    }

    public KeyboardEvent getEvent() {
        Object obj = parameters.get(Names.event);
        if (obj instanceof KeyboardEvent) {
            return (KeyboardEvent) obj;
        } else if (obj instanceof String) {
            KeyboardEvent theCode = null;
            try {
                theCode = KeyboardEvent.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.event, e);
            }
            return theCode;
        }
        return null;
    }

    public void setEvent(KeyboardEvent event) {
        if (event != null) {
            parameters.put(Names.event, event);
        } else {
            parameters.remove(Names.event);
        }
    }

    public void setData(String data) {
        if (data != null) {
            parameters.put(Names.data, data);
        } else {
            parameters.remove(Names.data);
        }
    }
    public String getData() {
        Object obj = parameters.get(Names.data);
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
