package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.TouchType;
import com.smartdevicelink.util.DebugTool;

import java.util.Hashtable;
import java.util.Vector;

public class OnTouchEvent extends RPCNotification {
	public static final String event = "event";
	public static final String type = "type";
    public OnTouchEvent() {
        super("OnTouchEvent");
    }
    public OnTouchEvent(Hashtable hash) {
        super(hash);
    }
    
    public void setType(TouchType type) {
    	if (type != null) {
    		parameters.put(OnTouchEvent.type, type);
    	} else {
    		parameters.remove(OnTouchEvent.type);
    	}
    }
    
    public TouchType getType() {
        Object obj = parameters.get(OnTouchEvent.type);
        if (obj instanceof TouchType) {
            return (TouchType) obj;
        } else if (obj instanceof String) {
        	TouchType theCode = null;
            try {
                theCode = TouchType.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnTouchEvent.type, e);
            }
            return theCode;
        }
        return null;
    }
    
    public void setEvent(Vector<TouchEvent> event) {
        if (event != null) {
            parameters.put(OnTouchEvent.event, event);
        } else {
        	parameters.remove(OnTouchEvent.event);
        }
    }
    
    public Vector<TouchEvent> getEvent() {
        if (parameters.get(OnTouchEvent.event) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)parameters.get(OnTouchEvent.event);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TouchEvent) {
	                return (Vector<TouchEvent>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<TouchEvent> newList = new Vector<TouchEvent>();
	                for (Object hashObj : list) {
	                    newList.add(new TouchEvent((Hashtable) hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }
}
