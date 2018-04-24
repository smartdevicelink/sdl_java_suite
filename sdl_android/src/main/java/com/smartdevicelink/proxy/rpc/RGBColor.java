package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import java.util.Hashtable;

public class RGBColor extends RPCStruct{
    public static final String KEY_RED = "red";
    public static final String KEY_GREEN = "green";
    public static final String KEY_BLUE = "blue";

    public RGBColor(Integer red, Integer green, Integer blue) {
        Hashtable<String, Object> hash = new Hashtable<>();
        hash.put(KEY_RED, red);
        hash.put(KEY_GREEN, green);
        hash.put(KEY_BLUE, blue);
        this.store = hash;
    }

    public void setRed(Integer color) {
        setValue(KEY_RED, color);
    }

    public Integer getRed() {
        return getInteger(KEY_RED);
    }

    public void setGreen(Integer color) {
        setValue(KEY_GREEN, color);
    }

    public Integer getGreen() {
        return getInteger(KEY_GREEN);
    }

    public void setBlue(Integer color) {
        setValue(KEY_BLUE, color);
    }

    public Integer getBlue() {
        return getInteger(KEY_BLUE);
    }
}
