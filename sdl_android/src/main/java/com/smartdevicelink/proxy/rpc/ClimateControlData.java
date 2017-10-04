package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.DefrostZone;
import com.smartdevicelink.proxy.rpc.enums.VentilationMode;
import java.util.Hashtable;

public class ClimateControlData extends RPCStruct{
    public static final String KEY_FAN_SPEED= "fanSpeed";
    public static final String KEY_CURRENT_TEMPERATURE= "currentTemperature";
    public static final String KEY_DESIRED_TEMPERATURE= "desiredTemperature";
    public static final String KEY_AC_ENABLE= "acEnable";
    public static final String KEY_CIRCULATE_AIR_ENABLE= "circulateAirEnable";
    public static final String KEY_AUTO_MODE_ENABLE= "autoModeEnable";
    public static final String KEY_DEFROST_ZONE= "defrostZone";
    public static final String KEY_DUAL_MODE_ENABLE= "dualModeEnable";
    public static final String KEY_AC_MAX_ENABLE= "acMaxEnable";
    public static final String KEY_VENTILATION_MODE= "ventilationMode";

    public ClimateControlData() {
    }

    public ClimateControlData(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setFanSpeed(Integer fanSpeed) {
        setValue(KEY_FAN_SPEED, fanSpeed);
    }

    public Integer getFanSpeed() {
        return getInteger(KEY_FAN_SPEED);
    }

    public void setCurrentTemperature(Temperature currentTemperature) {
        setValue(KEY_CURRENT_TEMPERATURE, currentTemperature);
    }

    public Temperature getCurrentTemperature() {
        return (Temperature) getObject(Temperature.class, KEY_CURRENT_TEMPERATURE);
    }

    public void setDesiredTemperature(Temperature desiredTemperature) {
        setValue(KEY_DESIRED_TEMPERATURE, desiredTemperature);
    }

    public Temperature getDesiredTemperature() {
        return (Temperature) getObject(Temperature.class, KEY_DESIRED_TEMPERATURE);
    }

    public void setAcEnable(Boolean acEnable) {
        setValue(KEY_AC_ENABLE, acEnable);
    }

    public Boolean getAcEnable() {
        return getBoolean(KEY_AC_ENABLE);
    }

    public void setCirculateAirEnable(Boolean circulateAirEnable) {
        setValue(KEY_CIRCULATE_AIR_ENABLE, circulateAirEnable);
    }

    public Boolean getCirculateAirEnable() {
        return getBoolean(KEY_CIRCULATE_AIR_ENABLE);
    }

    public void setAutoModeEnable(Boolean autoModeEnable) {
        setValue(KEY_AUTO_MODE_ENABLE, autoModeEnable);
    }

    public Boolean getAutoModeEnable() {
        return getBoolean(KEY_AUTO_MODE_ENABLE);
    }

    public void setDefrostZone(DefrostZone defrostZone) {
        setValue(KEY_DEFROST_ZONE, defrostZone);
    }

    public DefrostZone getDefrostZone() {
        return (DefrostZone) getObject(DefrostZone.class, KEY_DEFROST_ZONE);
    }

    public void setDualModeEnable(Boolean dualModeEnable) {
        setValue(KEY_DUAL_MODE_ENABLE, dualModeEnable);
    }

    public Boolean getDualModeEnable() {
        return getBoolean(KEY_DUAL_MODE_ENABLE);
    }

    public void setAcMaxEnable(Boolean acMaxEnable) {
        setValue(KEY_AC_MAX_ENABLE, acMaxEnable);
    }

    public Boolean getAcMaxEnable() {
        return getBoolean(KEY_AC_MAX_ENABLE);
    }

    public void setVentilationMode(VentilationMode ventilationMode) {
        setValue(KEY_VENTILATION_MODE, ventilationMode);
    }

    public VentilationMode getVentilationMode() {
        return (VentilationMode) getObject(VentilationMode.class, KEY_VENTILATION_MODE);
    }
}
