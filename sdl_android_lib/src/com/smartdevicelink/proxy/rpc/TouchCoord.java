package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class TouchCoord extends RPCStruct {
    public static final String x = "x";
    public static final String y = "y";
    public TouchCoord() {}
    
    public TouchCoord(Hashtable hash) {
        super(hash);
    }
    
    public void setX(Integer x) {
        if (x != null) {
            store.put(TouchCoord.x, x);
        } else {
        	store.remove(TouchCoord.x);
        }
    }
    
    public Integer getX() {
        return (Integer) store.get(TouchCoord.x);
    }
    
    public void setY(Integer y) {
        if (y != null) {
            store.put(TouchCoord.y, y);
        } else {
        	store.remove(TouchCoord.y);
        }
    }
    
    public Integer getY() {
        return (Integer) store.get(TouchCoord.y);
    }
    
}
