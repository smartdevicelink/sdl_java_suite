package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import java.util.Hashtable;

/**
 * Contains information about a radio control module's capabilities.
 */
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

    /**
     * Constructs a new RadioControlCapabilities object
     * @param moduleName
     * The short friendly name of the climate control module.
     * It should not be used to identify a module by mobile application.
     */
    public RadioControlCapabilities(@NonNull String moduleName) {
        this();
        setModuleName(moduleName);
    }
    /**
     * Sets the moduleName portion of the RadioControlCapabilities class
     *
     * @param moduleName
     * The short friendly name of the climate control module.
     * It should not be used to identify a module by mobile application.
     */
    public void setModuleName(@NonNull String moduleName) {
        setValue(KEY_MODULE_NAME, moduleName);
    }

    /**
     * Gets the moduleName portion of the RadioControlCapabilities class
     *
     * @return String - Short friendly name of the climate control module.
     */
    public String getModuleName() {
        return getString(KEY_MODULE_NAME);
    }

    /**
     * Sets the radioEnableAvailable portion of the RadioControlCapabilities class
     *
     * @param radioEnableAvailable
     * Availability of the control of enable/disable radio.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public void setRadioEnableAvailable(Boolean radioEnableAvailable) {
        setValue(KEY_RADIO_ENABLE_AVAILABLE, radioEnableAvailable);
    }

    /**
     * Gets the radioEnableAvailable portion of the RadioControlCapabilities class
     *
     * @return Boolean - Availability of the control of enable/disable radio.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getRadioEnableAvailable() {
        return getBoolean(KEY_RADIO_ENABLE_AVAILABLE);
    }

    /**
     * Sets the radioBandAvailable portion of the RadioControlCapabilities class
     *
     * @param radioBandAvailable
     * Availability of the control of radio band.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public void setRadioBandAvailable(Boolean radioBandAvailable) {
        setValue(KEY_RADIO_BAND_AVAILABLE, radioBandAvailable);
    }

    /**
     * Gets the radioBandAvailable portion of the RadioControlCapabilities class
     *
     * @return Boolean - Availability of the control of radio band.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getRadioBandAvailable() {
        return getBoolean(KEY_RADIO_BAND_AVAILABLE);
    }

    /**
     * Sets the radioFrequencyAvailable portion of the RadioControlCapabilities class
     *
     * @param radioFrequencyAvailable
     * Availability of the control of radio frequency.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public void setRadioFrequencyAvailable(Boolean radioFrequencyAvailable) {
        setValue(KEY_RADIO_FREQUENCY_AVAILABLE, radioFrequencyAvailable);
    }

    /**
     * Gets the radioFrequencyAvailable portion of the RadioControlCapabilities class
     *
     * @return Boolean - Availability of the control of radio frequency.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getRadioFrequencyAvailable() {
        return getBoolean(KEY_RADIO_FREQUENCY_AVAILABLE);
    }

    /**
     * Sets the hdChannelAvailable portion of the RadioControlCapabilities class
     *
     * @param hdChannelAvailable
     * Availability of the control of HD radio channel.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public void setHdChannelAvailable(Boolean hdChannelAvailable) {
        setValue(KEY_HD_CHANNEL_AVAILABLE, hdChannelAvailable);
    }

    /**
     * Gets the hdChannelAvailable portion of the RadioControlCapabilities class
     *
     * @return Boolean - Availability of the control of HD radio channel.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getHdChannelAvailable() {
        return getBoolean(KEY_HD_CHANNEL_AVAILABLE);
    }

    /**
     * Sets the rdsDataAvailable portion of the RadioControlCapabilities class
     *
     * @param rdsDataAvailable
     * Availability of the getting Radio Data System (RDS) data.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public void setRdsDataAvailable(Boolean rdsDataAvailable) {
        setValue(KEY_RDS_DATA_AVAILABLE, rdsDataAvailable);
    }

    /**
     * Gets the rdsDataAvailable portion of the RadioControlCapabilities class
     *
     * @return Boolean - Availability of the getting Radio Data System (RDS) data.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getRdsDataAvailable() {
        return getBoolean(KEY_RDS_DATA_AVAILABLE);
    }

    /**
     * Sets the availableHDsAvailable portion of the RadioControlCapabilities class
     *
     * @param availableHDsAvailable
     * Availability of the getting the number of available HD channels.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public void setAvailableHDsAvailable(Boolean availableHDsAvailable) {
        setValue(KEY_AVAILABLE_HDS_AVAILABLE, availableHDsAvailable);
    }

    /**
     * Gets the availableHDsAvailable portion of the RadioControlCapabilities class
     *
     * @return Boolean - Availability of the getting the number of available HD channels.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getAvailableHDsAvailable() {
        return getBoolean(KEY_AVAILABLE_HDS_AVAILABLE);
    }

    /**
     * Sets the stateAvailable portion of the RadioControlCapabilities class
     *
     * @param stateAvailable
     * Availability of the getting the Radio state.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public void setStateAvailable(Boolean stateAvailable) {
        setValue(KEY_STATE_AVAILABLE, stateAvailable);
    }

    /**
     * Gets the stateAvailable portion of the RadioControlCapabilities class
     *
     * @return Boolean - Availability of the getting the Radio state.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getStateAvailable() {
        return getBoolean(KEY_STATE_AVAILABLE);
    }

    /**
     * Sets the signalStrengthAvailable portion of the RadioControlCapabilities class
     *
     * @param signalStrengthAvailable
     * Availability of the getting the signal strength.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public void setSignalStrengthAvailable(Boolean signalStrengthAvailable) {
        setValue(KEY_SIGNAL_STRENGTH_AVAILABLE, signalStrengthAvailable);
    }

    /**
     * Gets the signalStrengthAvailable portion of the RadioControlCapabilities class
     *
     * @return Boolean - Availability of the getting the signal strength.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getSignalStrengthAvailable() {
        return getBoolean(KEY_SIGNAL_STRENGTH_AVAILABLE);
    }

    /**
     * Sets the signalChangeThresholdAvailable portion of the RadioControlCapabilities class
     *
     * @param signalChangeThresholdAvailable
     * Availability of the getting the signal Change Threshold.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public void setSignalChangeThresholdAvailable(Boolean signalChangeThresholdAvailable) {
        setValue(KEY_SIGNAL_CHANGE_THRESHOLD_AVAILABLE, signalChangeThresholdAvailable);
    }

    /**
     * Gets the signalChangeThresholdAvailable portion of the RadioControlCapabilities class
     *
     * @return Boolean - Availability of the getting the signal Change Threshold.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getSignalChangeThresholdAvailable() {
        return getBoolean(KEY_SIGNAL_CHANGE_THRESHOLD_AVAILABLE);
    }
}
