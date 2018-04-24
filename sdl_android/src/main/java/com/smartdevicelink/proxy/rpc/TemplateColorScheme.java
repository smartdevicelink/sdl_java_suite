package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import java.util.Hashtable;

public class TemplateColorScheme extends RPCStruct {

    public static final String KEY_PRIMARY_COLOR = "primaryColor";
    public static final String KEY_SECONDARY_COLOR = "secondaryColor";
    public static final String KEY_BACKGROUND_COLOR = "backgroundColor";

    public TemplateColorScheme(RGBColor primaryColor, RGBColor secondaryColor, RGBColor backgroundColor) {
        Hashtable<String, Object> hash = new Hashtable<>();
        hash.put(KEY_PRIMARY_COLOR, primaryColor);
        hash.put(KEY_SECONDARY_COLOR, secondaryColor);
        hash.put(KEY_BACKGROUND_COLOR, backgroundColor);
        this.store = hash;
    }

    public void setPrimaryColor(RGBColor color) {
        setValue(KEY_PRIMARY_COLOR, color);
    }

    public RGBColor getPrimaryColor() {
        return (RGBColor) getObject(RGBColor.class, KEY_PRIMARY_COLOR);
    }

    public void setSecondaryColor(RGBColor color) {
        setValue(KEY_SECONDARY_COLOR, color);
    }

    public RGBColor getSecondaryColor() {
        return (RGBColor) getObject(RGBColor.class, KEY_SECONDARY_COLOR);
    }

    public void setBackgroundColor(RGBColor color) {
        setValue(KEY_BACKGROUND_COLOR, color);
    }

    public RGBColor getBackgroundColor() {
        return (RGBColor) getObject(RGBColor.class, KEY_BACKGROUND_COLOR);
    }
}



