package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import java.util.Hashtable;
import java.util.List;

public class RemoteControlCapabilities extends RPCStruct{
    public static final String KEY_CLIMATE_CONTROL_CAPABILITIES= "climateControlCapabilities";
    public static final String KEY_RADIO_CONTROL_CAPABILITIES = "radioControlCapabilities";
    public static final String KEY_BUTTON_CAPABILITIES = "buttonCapabilities";

    public RemoteControlCapabilities() {
    }

    public RemoteControlCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setClimateControlCapabilities(List<ClimateControlCapabilities> climateControlCapabilities) {
        setValue(KEY_CLIMATE_CONTROL_CAPABILITIES, climateControlCapabilities);
    }

    public List<ClimateControlCapabilities> getClimateControlCapabilities() {
        return (List<ClimateControlCapabilities>) getObject(ClimateControlCapabilities.class, KEY_CLIMATE_CONTROL_CAPABILITIES);
    }

    public void setRadioControlCapabilities(List<RadioControlCapabilities> radioControlCapabilities) {
        setValue(KEY_RADIO_CONTROL_CAPABILITIES, radioControlCapabilities);
    }

    public List<RadioControlCapabilities> getRadioControlCapabilities() {
        return (List<RadioControlCapabilities>) getObject(RadioControlCapabilities.class, KEY_RADIO_CONTROL_CAPABILITIES);
    }

    public void setButtonCapabilities(List<ButtonCapabilities> buttonCapabilities) {
        setValue(KEY_BUTTON_CAPABILITIES, buttonCapabilities);
    }

    public List<ButtonCapabilities> getButtonCapabilities() {
        return (List<ButtonCapabilities>) getObject(ButtonCapabilities.class, KEY_BUTTON_CAPABILITIES);
    }
}
