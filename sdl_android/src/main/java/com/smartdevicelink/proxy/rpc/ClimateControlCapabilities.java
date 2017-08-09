package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.DefrostZone;
import com.smartdevicelink.proxy.rpc.enums.VentilationMode;
import java.util.Hashtable;
import java.util.List;

public class ClimateControlCapabilities extends RPCStruct{
    public static final String KEY_MODULE_NAME= "moduleName";
    public static final String KEY_FAN_SPEED_AVAILABLE= "fanSpeedAvailable";
    public static final String KEY_DESIRED_TEMPERATURE_AVAILABLE= "desiredTemperatureAvailable";
    public static final String KEY_AC_ENABLE_AVAILABLE= "acEnableAvailable";
    public static final String KEY_AC_MAX_ENABLE_AVAILABLE= "acMaxEnableAvailable";
    public static final String KEY_CIRCULATE_AIR_ENABLE_AVAILABLE= "circulateAirEnableAvailable";
    public static final String KEY_AUTO_MODE_ENABLE_AVAILABLE= "autoModeEnableAvailable";
    public static final String KEY_DUAL_MODE_ENABLE_AVAILABLE= "dualModeEnableAvailable";
    public static final String KEY_DEFROST_ZONE_AVAILABLE= "defrostZoneAvailable";
    public static final String KEY_DEFROST_ZONE= "defrostZone";
    public static final String KEY_VENTILATION_MODE_AVAILABLE= "ventilationModeAvailable";
    public static final String KEY_VENTILATION_MODE= "ventilationMode";

    public ClimateControlCapabilities() {
    }

    public ClimateControlCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setModuleName(String moduleName) {
        setValue(KEY_MODULE_NAME, moduleName);
    }
    public String getModuleName() {
        return getString(KEY_MODULE_NAME);
    }

    public void setFanSpeedAvailable(Boolean fanSpeedAvailable) {
        setValue(KEY_FAN_SPEED_AVAILABLE, fanSpeedAvailable);
    }

    public Boolean getFanSpeedAvailable() {
        return getBoolean(KEY_FAN_SPEED_AVAILABLE);
    }

    public void setDesiredTemperatureAvailable(Boolean desiredTemperatureAvailable) {
        setValue(KEY_DESIRED_TEMPERATURE_AVAILABLE, desiredTemperatureAvailable);
    }

    public Boolean getDesiredTemperatureAvailable() {
        return getBoolean(KEY_DESIRED_TEMPERATURE_AVAILABLE);
    }

    public void setAcEnableAvailable(Boolean acEnableAvailable) {
        setValue(KEY_AC_ENABLE_AVAILABLE, acEnableAvailable);
    }

    public Boolean getAcEnableAvailable() {
        return getBoolean(KEY_AC_ENABLE_AVAILABLE);
    }

    public void setAcMaxEnableAvailable(Boolean acMaxEnableAvailable) {
        setValue(KEY_AC_MAX_ENABLE_AVAILABLE, acMaxEnableAvailable);
    }

    public Boolean getAcMaxEnableAvailable() {
        return getBoolean(KEY_AC_MAX_ENABLE_AVAILABLE);
    }

    public void setCirculateAirEnableAvailable(Boolean circulateAirEnableAvailable) {
        setValue(KEY_CIRCULATE_AIR_ENABLE_AVAILABLE, circulateAirEnableAvailable);
    }

    public Boolean getCirculateAirEnableAvailable() {
        return getBoolean(KEY_CIRCULATE_AIR_ENABLE_AVAILABLE);
    }

    public void setAutoModeEnableAvailable(Boolean autoModeEnableAvailable) {
        setValue(KEY_AUTO_MODE_ENABLE_AVAILABLE, autoModeEnableAvailable);
    }

    public Boolean getAutoModeEnableAvailable() {
        return getBoolean(KEY_AUTO_MODE_ENABLE_AVAILABLE);
    }

    public void setDualModeEnableAvailable(Boolean dualModeEnableAvailable) {
        setValue(KEY_DUAL_MODE_ENABLE_AVAILABLE, dualModeEnableAvailable);
    }

    public Boolean getDualModeEnableAvailable() {
        return getBoolean(KEY_DUAL_MODE_ENABLE_AVAILABLE);
    }

    public void setDefrostZoneAvailable(Boolean defrostZoneAvailable) {
        setValue(KEY_DEFROST_ZONE_AVAILABLE, defrostZoneAvailable);
    }

    public Boolean getDefrostZoneAvailable() {
        return getBoolean(KEY_DEFROST_ZONE_AVAILABLE);
    }

    public List<DefrostZone> getDefrostZone() {
        return (List<DefrostZone>) getObject(DefrostZone.class, KEY_DEFROST_ZONE);
    }

    public void setDefrostZone(List<DefrostZone> defrostZone) {
        setValue(KEY_DEFROST_ZONE, defrostZone);
    }

    public void setVentilationModeAvailable(Boolean ventilationModeAvailable) {
        setValue(KEY_VENTILATION_MODE_AVAILABLE, ventilationModeAvailable);
    }

    public Boolean getVentilationModeAvailable() {
        return getBoolean(KEY_VENTILATION_MODE_AVAILABLE);
    }

    public List<VentilationMode> getVentilationMode() {
        return (List<VentilationMode>) getObject(VentilationMode.class, KEY_VENTILATION_MODE);
    }


    public void setVentilationMode(List<VentilationMode> ventilationMode) {
        setValue(KEY_VENTILATION_MODE, ventilationMode);
    }
}
