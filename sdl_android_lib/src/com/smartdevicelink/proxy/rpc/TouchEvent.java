package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.util.DebugTool;

public class TouchEvent extends RPCStruct {
    public TouchEvent() { }
  
    public TouchEvent(Hashtable hash) {
        super(hash);
    }
    public void setId(Integer id) {
        if (id != null) {
            store.put(Names.id, id);
        } else {
        	store.remove(Names.id);
        }
    }    
    public Integer getId() {
        return (Integer) store.get(Names.id);
    }    
    public Vector<Integer> getTs() {
    	if(store.get(Names.ts) instanceof Vector<?>){
    		Vector<?> list = (Vector<?>)store.get(Names.ts);
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
            store.put(Names.ts, ts);
        } else {
        	store.remove(Names.ts);
        }
    }
    public Vector<TouchCoord> getC() {
        if (store.get(Names.c) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)store.get(Names.c);
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
            store.put(Names.c, c );
        } else {
        	store.remove(Names.c);
        }        
    }          
}
