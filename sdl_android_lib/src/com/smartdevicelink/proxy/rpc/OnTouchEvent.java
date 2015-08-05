package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.TouchType;

public class OnTouchEvent extends RPCNotification {
	public static final String KEY_EVENT = "event";
	public static final String KEY_TYPE = "type";
	
    public OnTouchEvent() {
        super(FunctionID.ON_TOUCH_EVENT.toString());
    }
    public OnTouchEvent(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public void setType(TouchType type) {
    	if (type != null) {
    		parameters.put(KEY_TYPE, type);
    	} else {
    		parameters.remove(KEY_TYPE);
    	}
    }
    
    public TouchType getType() {
        Object obj = parameters.get(KEY_TYPE);
        if (obj instanceof TouchType) {
            return (TouchType) obj;
        } else if (obj instanceof String) {
        	return TouchType.valueForString((String) obj);
        }
        return null;
    }
    
    public void setEvent(List<TouchEvent> event) {
        if (event != null) {
            parameters.put(KEY_EVENT, event);
        } else {
        	parameters.remove(KEY_EVENT);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<TouchEvent> getEvent() {
        if (parameters.get(KEY_EVENT) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_EVENT);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TouchEvent) {
	                return (List<TouchEvent>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<TouchEvent> newList = new ArrayList<TouchEvent>();
	                for (Object hashObj : list) {
	                    newList.add(new TouchEvent((Hashtable<String, Object>) hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }
}
