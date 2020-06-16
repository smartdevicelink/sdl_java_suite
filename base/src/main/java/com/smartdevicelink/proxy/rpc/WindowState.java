package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class WindowState extends RPCStruct {
    public static final String KEY_APPROXIMATE_POSITION = "approximatePosition";
    public static final String KEY_DEVIATION = "deviation";

    /**
     * Constructs a newly allocated WindowStatus object
     */
    public WindowState() {}

    /**
     * Constructs a newly allocated WindowStatus object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public WindowState(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a newly allocated WindowStatus object
     */
    public WindowState(@NonNull Integer approximatePosition, @NonNull Integer deviation) {
        this();
        setApproximatePosition(approximatePosition);
        setDeviation(deviation);
    }

    public void setApproximatePosition(Integer approximatePosition) {
        setValue(KEY_APPROXIMATE_POSITION, approximatePosition);
    }

    public Integer getApproximatePosition() {
        return getInteger(KEY_APPROXIMATE_POSITION);
    }

    public void setDeviation(Integer deviation) {
        setValue(KEY_DEVIATION, deviation);
    }

    public Integer getDeviation() {
        return getInteger(KEY_DEVIATION);
    }
}
