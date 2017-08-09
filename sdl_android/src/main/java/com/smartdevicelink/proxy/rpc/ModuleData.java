package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;
import java.util.Hashtable;

public class ModuleData extends RPCStruct{
    public static final String KEY_MODULE_TYPE= "moduleType";
    public static final String KEY_RADIO_CONTROL_DATA = "radioControlData";
    public static final String KEY_CLIMATE_CONTROL_DATA = "climateControlData";

    public ModuleData() {
    }

    public ModuleData(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setModuleType(ModuleType moduleType) {
        setValue(KEY_MODULE_TYPE, moduleType);
    }

    public ModuleType getModuleType() {
        return (ModuleType) getObject(ModuleType.class, KEY_MODULE_TYPE);
    }

    public void setRadioControlData(RadioControlData radioControlData) {
        setValue(KEY_RADIO_CONTROL_DATA, radioControlData);
    }

    public RadioControlData getRadioControlData() {
        return (RadioControlData) getObject(RadioControlData.class, KEY_RADIO_CONTROL_DATA);
    }

    public void setClimateControlData(ClimateControlData climateControlData) {
        setValue(KEY_CLIMATE_CONTROL_DATA, climateControlData);
    }

    public ClimateControlData getClimateControlData() {
        return (ClimateControlData) getObject(ClimateControlData.class, KEY_CLIMATE_CONTROL_DATA);
    }
}
