/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

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
    @Deprecated public static final String KEY_AVAILABLE_HDS_AVAILABLE= "availableHDsAvailable";
    public static final String KEY_STATE_AVAILABLE= "stateAvailable";
    public static final String KEY_SIGNAL_STRENGTH_AVAILABLE= "signalStrengthAvailable";
    public static final String KEY_SIGNAL_CHANGE_THRESHOLD_AVAILABLE= "signalChangeThresholdAvailable";
    public static final String KEY_HD_RADIO_ENABLE_AVAILABLE = "hdRadioEnableAvailable";
    public static final String KEY_SIRIUS_XM_RADIO_AVAILABLE = "siriusxmRadioAvailable";
    public static final String KEY_SIS_DATA_AVAILABLE = "sisDataAvailable";
    public static final String KEY_MODULE_INFO = "moduleInfo";
    public static final String KEY_AVAILABLE_HD_CHANNELS_AVAILABLE = "availableHdChannelsAvailable";

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
    @Deprecated
    public void setAvailableHDsAvailable(Boolean availableHDsAvailable) {
        setValue(KEY_AVAILABLE_HDS_AVAILABLE, availableHDsAvailable);
    }

    /**
     * Gets the availableHDsAvailable portion of the RadioControlCapabilities class
     *
     * @return Boolean - Availability of the getting the number of available HD channels.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    @Deprecated
    public Boolean getAvailableHDsAvailable() {
        return getBoolean(KEY_AVAILABLE_HDS_AVAILABLE);
    }

    /**
     * Sets the availableHdChannelsAvailable portion of the RadioControlCapabilities class
     *
     * @param availableHdChannelsAvailable
     * Availability of the list of available HD sub-channel indexes.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public void setAvailableHdChannelsAvailable(Boolean availableHdChannelsAvailable) {
        setValue(KEY_AVAILABLE_HD_CHANNELS_AVAILABLE, availableHdChannelsAvailable);
    }

    /**
     * Gets the availableHdChannelsAvailable portion of the RadioControlCapabilities class
     *
     * @return Boolean - Availability of the list of available HD sub-channel indexes.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getAvailableHdChannelsAvailable() {
        return getBoolean(KEY_AVAILABLE_HD_CHANNELS_AVAILABLE);
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

    /**
     * Sets the hdRadioEnableAvailable portion of the RadioControlCapabilities class
     *
     * @param hdRadioEnableAvailable Availability of the control of enable/disable HD radio.
     *                               True: Available, False: Not Available, Not present: Not Available.
     */
    public void setHdRadioEnableAvailable(Boolean hdRadioEnableAvailable) {
        setValue(KEY_HD_RADIO_ENABLE_AVAILABLE, hdRadioEnableAvailable);
    }

    /**
     * Gets the hdRadioEnableAvailable portion of the RadioControlCapabilities class
     *
     * @return Boolean - Availability of the control of enable/disable HD radio.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getHdRadioEnableAvailable() {
        return getBoolean(KEY_HD_RADIO_ENABLE_AVAILABLE);
    }

    /**
     * Sets the siriusxmRadioAvailable portion of the RadioControlCapabilities class
     *
     * @param siriusxmRadioAvailable Availability of sirius XM radio.
     *                               True: Available, False: Not Available, Not present: Not Available.
     */
    public void setSiriusXMRadioAvailable(Boolean siriusxmRadioAvailable) {
        setValue(KEY_SIRIUS_XM_RADIO_AVAILABLE, siriusxmRadioAvailable);
    }

    /**
     * Gets the siriusxmRadioAvailable portion of the RadioControlCapabilities class
     *
     * @return Boolean - Availability of sirius XM radio.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getSiriusXMRadioAvailable() {
        return getBoolean(KEY_SIRIUS_XM_RADIO_AVAILABLE);
    }

    /**
     * Sets the sisDataAvailable portion of the RadioControlCapabilities class
     *
     * @param sisDataAvailable Availability of the getting HD radio Station Information Service (SIS) data.
     *                         True: Available, False: Not Available, Not present: Not Available.
     */
    public void setSisDataAvailable(Boolean sisDataAvailable) {
        setValue(KEY_SIS_DATA_AVAILABLE, sisDataAvailable);
    }

    /**
     * Gets the sisDataAvailable portion of the RadioControlCapabilities class
     *
     * @return Boolean - Availability of the getting HD radio Station Information Service (SIS) data.
     * True: Available, False: Not Available, Not present: Not Available.
     */
    public Boolean getSisDataAvailable() {
        return getBoolean(KEY_SIS_DATA_AVAILABLE);
    }

    /**
     * Sets ModuleInfo for this capability
     * @param info the ModuleInfo to be set
     */
    public void setModuleInfo(ModuleInfo info) {
        setValue(KEY_MODULE_INFO, info);
    }

    /**
     * Gets a ModuleInfo of this capability
     * @return module info of this capability
     */
    public ModuleInfo getModuleInfo() {
        return (ModuleInfo) getObject(ModuleInfo.class, KEY_MODULE_INFO);
    }
}
