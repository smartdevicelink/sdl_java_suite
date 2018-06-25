package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;
import com.smartdevicelink.proxy.rpc.enums.Result;

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

    public ModuleData(@NonNull ModuleType moduleType) {
        this();
        setModuleType(moduleType);
    }

    /**
     * Sets the moduleType portion of the ModuleData class
     *
     * @param moduleType
     * The moduleType indicates which type of data should be changed and identifies which data object exists in this struct.
     * For example, if the moduleType is CLIMATE then a "climateControlData" should exist
     */
    public void setModuleType(@NonNull ModuleType moduleType) {
        setValue(KEY_MODULE_TYPE, moduleType);
    }

    /**
     * Gets the moduleType portion of the ModuleData class
     *
     * @return ModuleType - The moduleType indicates which type of data should be changed and identifies which data object exists in this struct.
     * For example, if the moduleType is CLIMATE then a "climateControlData" should exist.
     */
    public ModuleType getModuleType() {
        return (ModuleType) getObject(ModuleType.class, KEY_MODULE_TYPE);
    }

    /**
     * Sets the radioControlData portion of the ModuleData class
     *
     * @param radioControlData
     */
    public void setRadioControlData(RadioControlData radioControlData) {
        setValue(KEY_RADIO_CONTROL_DATA, radioControlData);
    }

    /**
     * Gets the radioControlData portion of the ModuleData class
     *
     * @return RadioControlData
     */
    public RadioControlData getRadioControlData() {
        return (RadioControlData) getObject(RadioControlData.class, KEY_RADIO_CONTROL_DATA);
    }

    /**
     * Sets the climateControlData portion of the ModuleData class
     *
     * @param climateControlData
     */
    public void setClimateControlData(ClimateControlData climateControlData) {
        setValue(KEY_CLIMATE_CONTROL_DATA, climateControlData);
    }

    /**
     * Gets the climateControlData portion of the ModuleData class
     *
     * @return ClimateControlData
     */
    public ClimateControlData getClimateControlData() {
        return (ClimateControlData) getObject(ClimateControlData.class, KEY_CLIMATE_CONTROL_DATA);
    }
}
