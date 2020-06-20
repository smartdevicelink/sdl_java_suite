package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class WindowStatus extends RPCStruct {

    public static final String KEY_LOCATION = "location";
    public static final String KEY_WINDOW_STATE = "state";

    /**
     * Constructs a newly allocated WindowStatus object
     */
    public WindowStatus() {}

    /**
     * Constructs a newly allocated WindowStatus object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public WindowStatus(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a newly allocated WindowStatus object
     * @param location describes location of Window of Door/liftgate
     * @param state describes state of Window of Door/liftgate
     */
    public WindowStatus(@NonNull Grid location, @NonNull WindowState state) {
        this();
        setLocation(location);
        setWindowState(state);
    }


    public void setLocation(Grid location) {
        setValue(KEY_LOCATION, location);
    }

    public Grid getLocation(){
        return (Grid) getObject(Grid.class, KEY_LOCATION);
    }

    public void setWindowState(WindowState state) {
        setValue(KEY_WINDOW_STATE, state);
    }

    public WindowState getWindowState() {
        return (WindowState) getObject(WindowState.class, KEY_WINDOW_STATE);
    }

}
