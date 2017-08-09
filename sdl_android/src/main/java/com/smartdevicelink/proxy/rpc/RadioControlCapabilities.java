package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import java.util.Hashtable;

public class RadioControlCapabilities extends RPCStruct{
    public static final String KEY_MODULE_NAME= "moduleName";
    public static final String KEY_RADIO_ENABLE_AVAILABLE= "radioEnableAvailable";
    public static final String KEY_RADIO_BAND_AVAILABLE= "radioBandAvailable";
    public static final String KEY_RADIO_FREQUENCY_AVAILABLE= "radioFrequencyAvailable";
    public static final String KEY_HD_CHANNEL_AVAILABLE= "hdChannelAvailable";
    public static final String KEY_RDS_DATA_AVAILABLE= "rdsDataAvailable";
    public static final String KEY_AVAILABLE_HDS_AVAILABLE= "availableHDsAvailable";
    public static final String KEY_STATE_AVAILABLE= "stateAvailable";
    public static final String KEY_SIGNAL_STRENGTH_AVAILABLE= "signalStrengthAvailable";
    public static final String KEY_SIGNAL_CHANGE_THRESHOLD_AVAILABLE= "signalChangeThresholdAvailable";

    public RadioControlCapabilities() {
    }

    public RadioControlCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setModuleName(String moduleName) {
        setValue(KEY_MODULE_NAME, moduleName);
    }
    public String getModuleName() {
        return getString(KEY_MODULE_NAME);
    }

    public void setRadioEnableAvailable(Boolean radioEnableAvailable) {
        setValue(KEY_RADIO_ENABLE_AVAILABLE, radioEnableAvailable);
    }

    public Boolean getRadioEnableAvailable() {
        return getBoolean(KEY_RADIO_ENABLE_AVAILABLE);
    }

    public void setRadioBandAvailable(Boolean radioBandAvailable) {
        setValue(KEY_RADIO_BAND_AVAILABLE, radioBandAvailable);
    }

    public Boolean getRadioBandAvailable() {
        return getBoolean(KEY_RADIO_BAND_AVAILABLE);
    }

    public void setRadioFrequencyAvailable(Boolean radioFrequencyAvailable) {
        setValue(KEY_RADIO_FREQUENCY_AVAILABLE, radioFrequencyAvailable);
    }

    public Boolean getRadioFrequencyAvailable() {
        return getBoolean(KEY_RADIO_FREQUENCY_AVAILABLE);
    }

    public void setHdChannelAvailable(Boolean hdChannelAvailable) {
        setValue(KEY_HD_CHANNEL_AVAILABLE, hdChannelAvailable);
    }

    public Boolean getHdChannelAvailable() {
        return getBoolean(KEY_HD_CHANNEL_AVAILABLE);
    }

    public void setRdsDataAvailable(Boolean rdsDataAvailable) {
        setValue(KEY_RDS_DATA_AVAILABLE, rdsDataAvailable);
    }

    public Boolean getRdsDataAvailable() {
        return getBoolean(KEY_RDS_DATA_AVAILABLE);
    }

    public void setAvailableHDsAvailable(Boolean availableHDsAvailable) {
        setValue(KEY_AVAILABLE_HDS_AVAILABLE, availableHDsAvailable);
    }

    public Boolean getAvailableHDsAvailable() {
        return getBoolean(KEY_AVAILABLE_HDS_AVAILABLE);
    }

    public void setStateAvailable(Boolean stateAvailable) {
        setValue(KEY_STATE_AVAILABLE, stateAvailable);
    }

    public Boolean getStateAvailable() {
        return getBoolean(KEY_STATE_AVAILABLE);
    }

    public void setSignalStrengthAvailable(Boolean signalStrengthAvailable) {
        setValue(KEY_SIGNAL_STRENGTH_AVAILABLE, signalStrengthAvailable);
    }

    public Boolean getSignalStrengthAvailable() {
        return getBoolean(KEY_SIGNAL_STRENGTH_AVAILABLE);
    }

    public void setSignalChangeThresholdAvailable(Boolean signalChangeThresholdAvailable) {
        setValue(KEY_SIGNAL_CHANGE_THRESHOLD_AVAILABLE, signalChangeThresholdAvailable);
    }

    public Boolean getSignalChangeThresholdAvailable() {
        return getBoolean(KEY_SIGNAL_CHANGE_THRESHOLD_AVAILABLE);
    }
}
