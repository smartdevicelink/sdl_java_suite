package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.SeekIndicatorType;

import java.util.Hashtable;

public class SeekStreamingIndicator extends RPCStruct {

    public static final String KEY_TYPE = "type";
    public static final String KEY_SEEK_TIME = "seekTime";

    public SeekStreamingIndicator() {
    }

    public SeekStreamingIndicator(Hashtable<String, Object> hash) {
        super(hash);
    }

    public SeekStreamingIndicator setType(@NonNull SeekIndicatorType type) {
        setValue(KEY_TYPE, type);
        return this;
    }

    public SeekIndicatorType getType() {
        return (SeekIndicatorType) getObject(SeekIndicatorType.class, KEY_TYPE);
    }

    public SeekStreamingIndicator setType(int seekTime) {
        setValue(KEY_SEEK_TIME, seekTime);
        return this;
    }

    public int getSeekTime() {
        return getInteger(KEY_SEEK_TIME);
    }
}
