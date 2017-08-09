package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.RadioBand;
import com.smartdevicelink.proxy.rpc.enums.RadioState;
import java.util.Hashtable;

public class RadioControlData extends RPCStruct{
    public static final String KEY_FREQUENCY_INTEGER= "frequencyInteger";
    public static final String KEY_FREQUENCY_FRACTION= "frequencyFraction";
    public static final String KEY_BAND= "band";
    public static final String KEY_RDS_DATA= "rdsData";
    public static final String KEY_AVAILABLE_HDS= "availableHDs";
    public static final String KEY_HD_CHANNEL= "hdChannel";
    public static final String KEY_SIGNAL_STRENGTH= "signalStrength";
    public static final String KEY_SIGNAL_CHANGE_THRESHOLD= "signalChangeThreshold";
    public static final String KEY_RADIO_ENABLE= "radioEnable";
    public static final String KEY_STATE= "state";

    public RadioControlData() {
    }

    public RadioControlData(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setFrequencyInteger(Integer frequencyInteger) {
        setValue(KEY_FREQUENCY_INTEGER, frequencyInteger);
    }

    public Integer getFrequencyInteger() {
        return getInteger(KEY_FREQUENCY_INTEGER);
    }

    public void setFrequencyFraction(Integer frequencyFraction) {
        setValue(KEY_FREQUENCY_FRACTION, frequencyFraction);
    }

    public Integer getFrequencyFraction() {
        return getInteger(KEY_FREQUENCY_FRACTION);
    }

    public void setBand(RadioBand band) {
        setValue(KEY_BAND, band);
    }

    public RadioBand getBand() {
        return (RadioBand) getObject(RadioBand.class, KEY_BAND);
    }

    public void setRdsData(RdsData rdsData) {
        setValue(KEY_RDS_DATA, rdsData);
    }

    public RdsData getRdsData() {
        return (RdsData) getObject(RdsData.class, KEY_RDS_DATA);
    }

    public void setAvailableHDs(Integer availableHDs) {
        setValue(KEY_AVAILABLE_HDS, availableHDs);
    }

    public Integer getAvailableHDs() {
        return getInteger(KEY_AVAILABLE_HDS);
    }

    public void setHdChannel(Integer hdChannel) {
        setValue(KEY_HD_CHANNEL, hdChannel);
    }

    public Integer getHdChannel() {
        return getInteger(KEY_HD_CHANNEL);
    }

    public void setSignalStrength(Integer signalStrength) {
        setValue(KEY_SIGNAL_STRENGTH, signalStrength);
    }

    public Integer getSignalStrength() {
        return getInteger(KEY_SIGNAL_STRENGTH);
    }

    public void setSignalChangeThreshold(Integer signalChangeThreshold) {
        setValue(KEY_SIGNAL_CHANGE_THRESHOLD, signalChangeThreshold);
    }

    public Integer getSignalChangeThreshold() {
        return getInteger(KEY_SIGNAL_CHANGE_THRESHOLD);
    }

    public void setRadioEnable(Boolean radioEnable) {
        setValue(KEY_RADIO_ENABLE, radioEnable);
    }

    public Boolean getRadioEnable() {
        return getBoolean(KEY_RADIO_ENABLE);
    }

    public void setState(RadioState state) {
        setValue(KEY_STATE, state);
    }

    public RadioState getState() {
        return (RadioState) getObject(RadioState.class, KEY_STATE);
    }
}
