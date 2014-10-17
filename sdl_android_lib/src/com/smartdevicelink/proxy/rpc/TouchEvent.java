package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.DebugTool;

public class TouchEvent extends RPCStruct {
    public static final String id = "id";
    public static final String ts = "ts";
    public static final String c = "c";
    public TouchEvent() { }
  
    public TouchEvent(Hashtable hash) {
        super(hash);
    }
    public void setId(Integer id) {
        if (id != null) {
            store.put(TouchEvent.id, id);
        } else {
        	store.remove(TouchEvent.id);
        }
    }    
    public Integer getId() {
        return (Integer) store.get(TouchEvent.id);
    }    
    public List<Integer> getTs() {
    	if(store.get(TouchEvent.ts) instanceof List<?>){
    		List<?> list = (List<?>)store.get(TouchEvent.ts);
    		if(list != null && list.size()>0){
        		Object obj = list.get(0);
        		if(obj instanceof Integer){
        			return (List<Integer>) list;
        		}
    		}
    	}
        return null;
    }
    public void setTs(List<Integer> ts) {
        if (ts != null) {
            store.put(TouchEvent.ts, ts);
        } else {
        	store.remove(TouchEvent.ts);
        }
    }
    public List<TouchCoord> getC() {
        if (store.get(TouchEvent.c) instanceof List<?>) {
        	List<?> list = (List<?>)store.get(TouchEvent.c);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TouchCoord) {
	                return (List<TouchCoord>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<TouchCoord> newList = new ArrayList<TouchCoord>();
	                for (Object hashObj : list) {
	                    newList.add(new TouchCoord((Hashtable) hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    } 
    public void setC( List<TouchCoord> c ) {
        if (c != null) {
            store.put(TouchEvent.c, c );
        } else {
        	store.remove(TouchEvent.c);
        }        
    }          
}
