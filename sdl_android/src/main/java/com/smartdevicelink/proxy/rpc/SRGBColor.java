package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class SRGBColor extends RPCStruct {
    public static final String KEY_RED = "red";
    public static final String KEY_GREEN = "green";
    public static final String KEY_BLUE = "blue";

    public SRGBColor() {}

    public SRGBColor(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the red portion of the SRGBColor class
     *
     * @param red
     */
    public void setRed(Integer red) {
        setValue(KEY_RED, red);
    }

    /**
     * Gets the red portion of the SRGBColor class
     *
     * @return Integer
     */
    public Integer getRed() {
        return getInteger(KEY_RED);
    }

    /**
     * Sets the green portion of the SRGBColor class
     *
     * @param green
     */
    public void setGreen(Integer green) {
        setValue(KEY_GREEN, green);
    }

    /**
     * Gets the green portion of the SRGBColor class
     *
     * @return Integer
     */
    public Integer getGreen() {
        return getInteger(KEY_GREEN);
    }

    /**
     * Sets the blue portion of the SRGBColor class
     *
     * @param blue
     */
    public void setBlue(Integer blue) {
        setValue(KEY_BLUE, blue);
    }

    /**
     * Gets the blue portion of the SRGBColor class
     *
     * @return Integer
     */
    public Integer getBlue() {
        return getInteger(KEY_BLUE);
    }

}
