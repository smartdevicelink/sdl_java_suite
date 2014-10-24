package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class TouchCoord extends RPCStruct {
    public static final String KEY_X = "x";
    public static final String KEY_Y = "y";
    public TouchCoord() {}
    
    public TouchCoord(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public void setX(Integer x) {
        if (x != null) {
            store.put(KEY_X, x);
        } else {
        	store.remove(KEY_X);
        }
    }
    
    public Integer getX() {
        return (Integer) store.get(KEY_X);
    }
    
    public void setY(Integer y) {
        if (y != null) {
            store.put(KEY_Y, y);
        } else {
        	store.remove(KEY_Y);
        }
    }
    
    public Integer getY() {
        return (Integer) store.get(KEY_Y);
    }
    
}
