package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

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
    public Vector<Integer> getTs() {
    	if(store.get(TouchEvent.ts) instanceof Vector<?>){
    		Vector<?> list = (Vector<?>)store.get(TouchEvent.ts);
    		if(list != null && list.size()>0){
        		Object obj = list.get(0);
        		if(obj instanceof Integer){
        			return (Vector<Integer>) list;
        		}
    		}
    	}
        return null;
    }
    public void setTs(Vector<Integer> ts) {
        if (ts != null) {
            store.put(TouchEvent.ts, ts);
        } else {
        	store.remove(TouchEvent.ts);
        }
    }
    public Vector<TouchCoord> getC() {
        if (store.get(TouchEvent.c) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)store.get(TouchEvent.c);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TouchCoord) {
	                return (Vector<TouchCoord>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<TouchCoord> newList = new Vector<TouchCoord>();
	                for (Object hashObj : list) {
	                    newList.add(new TouchCoord((Hashtable) hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    } 
    public void setC( Vector<TouchCoord> c ) {
        if (c != null) {
            store.put(TouchEvent.c, c );
        } else {
        	store.remove(TouchEvent.c);
        }        
    }          
}
