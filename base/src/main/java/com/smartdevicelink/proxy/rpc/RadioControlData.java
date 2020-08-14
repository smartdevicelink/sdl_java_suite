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

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.RadioBand;
import com.smartdevicelink.proxy.rpc.enums.RadioState;

import java.util.Hashtable;
import java.util.List;

/**
 * Include information (both read-only and changeable data) about a
 * remote control radio module.
 */
public class RadioControlData extends RPCStruct{
    public static final String KEY_FREQUENCY_INTEGER= "frequencyInteger";
    public static final String KEY_FREQUENCY_FRACTION= "frequencyFraction";
    public static final String KEY_BAND= "band";
    public static final String KEY_RDS_DATA= "rdsData";
    @Deprecated public static final String KEY_AVAILABLE_HDS= "availableHDs";
    public static final String KEY_HD_CHANNEL= "hdChannel";
    public static final String KEY_SIGNAL_STRENGTH= "signalStrength";
    public static final String KEY_SIGNAL_CHANGE_THRESHOLD= "signalChangeThreshold";
    public static final String KEY_RADIO_ENABLE= "radioEnable";
    public static final String KEY_STATE= "state";
    public static final String KEY_HD_RADIO_ENABLE = "hdRadioEnable";
    public static final String KEY_SIS_DATA = "sisData";
    public static final String KEY_AVAILABLE_HD_CHANNELS= "availableHdChannels";

    public RadioControlData() {
    }

    public RadioControlData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the frequencyInteger portion of the RadioControlData class
     *
     * @param frequencyInteger
     * The integer part of the frequency i.e. for 101.7 this value should be 101
     */
    public void setFrequencyInteger(Integer frequencyInteger) {
        setValue(KEY_FREQUENCY_INTEGER, frequencyInteger);
    }

    /**
     * Gets the frequencyInteger portion of the RadioControlData class
     *
     * @return Integer - The integer part of the frequency i.e. for 101.7 this value should be 101.
     */
    public Integer getFrequencyInteger() {
        return getInteger(KEY_FREQUENCY_INTEGER);
    }

    /**
     * Sets the frequencyFraction portion of the RadioControlData class
     *
     * @param frequencyFraction
     * The fractional part of the frequency i.e. for 101.7 is 7.
     */
    public void setFrequencyFraction(Integer frequencyFraction) {
        setValue(KEY_FREQUENCY_FRACTION, frequencyFraction);
    }

    /**
     * Gets the frequencyFraction portion of the RadioControlData class
     *
     * @return Integer - The fractional part of the frequency i.e. for 101.7 is 7.
     */
    public Integer getFrequencyFraction() {
        return getInteger(KEY_FREQUENCY_FRACTION);
    }

    /**
     * Sets the band portion of the RadioControlData class
     *
     * @param band
     * The radio band (AM|FM|XM) of the radio tuner.
     */
    public void setBand(RadioBand band) {
        setValue(KEY_BAND, band);
    }

    /**
     * Gets the band portion of the RadioControlData class
     *
     * @return RadioBand - The radio band (AM|FM|XM) of the radio tuner.
     */
    public RadioBand getBand() {
        return (RadioBand) getObject(RadioBand.class, KEY_BAND);
    }

    /**
     * Sets the rdsData portion of the RadioControlData class
     *
     * @param rdsData
     * Read only parameter. See RdsData data type for details.
     */
    public void setRdsData(RdsData rdsData) {
        setValue(KEY_RDS_DATA, rdsData);
    }

    /**
     * Gets the rdsData portion of the RadioControlData class
     *
     * @return RdsData - Read only parameter. See RdsData data type for details.
     */
    public RdsData getRdsData() {
        return (RdsData) getObject(RdsData.class, KEY_RDS_DATA);
    }

    /**
     * Sets the availableHDs portion of the RadioControlData class
     *
     * @param availableHDs
     * Number of HD sub-channels if available.
     */
    @Deprecated
    public void setAvailableHDs(Integer availableHDs) {
        setValue(KEY_AVAILABLE_HDS, availableHDs);
    }

    /**
     * Gets the availableHDs portion of the RadioControlData class
     *
     * @return Integer - Number of HD sub-channels if available.
     */
    @Deprecated
    public Integer getAvailableHDs() {
        return getInteger(KEY_AVAILABLE_HDS);
    }

    /**
     * Sets the hdChannel portion of the RadioControlData class
     *
     * @param hdChannel
     * Current HD sub-channel if available.
     */
    public void setHdChannel(Integer hdChannel) {
        setValue(KEY_HD_CHANNEL, hdChannel);
    }

    /**
     * Gets the hdChannel portion of the RadioControlData class
     *
     * @return Integer - Current HD sub-channel if available.
     */
    public Integer getHdChannel() {
        return getInteger(KEY_HD_CHANNEL);
    }

    /**
     * Sets the signalStrength portion of the RadioControlData class
     *
     * @param signalStrength
     * Read only parameter. Indicates the strength of receiving radio signal in current frequency.
     */
    public void setSignalStrength(Integer signalStrength) {
        setValue(KEY_SIGNAL_STRENGTH, signalStrength);
    }

    /**
     * Gets the signalStrength portion of the RadioControlData class
     *
     * @return Integer - Read only parameter. Indicates the strength of receiving radio signal in current frequency.
     */
    public Integer getSignalStrength() {
        return getInteger(KEY_SIGNAL_STRENGTH);
    }

    /**
     * Sets the signalChangeThreshold portion of the RadioControlData class
     *
     * @param signalChangeThreshold
     * If the signal strength falls below the set value for this parameter, the radio will tune to an alternative frequency.
     */
    public void setSignalChangeThreshold(Integer signalChangeThreshold) {
        setValue(KEY_SIGNAL_CHANGE_THRESHOLD, signalChangeThreshold);
    }

    /**
     * Gets the signalChangeThreshold portion of the RadioControlData class
     *
     * @return Integer - If the signal strength falls below the set value for this parameter, the radio will tune to an alternative frequency.
     */
    public Integer getSignalChangeThreshold() {
        return getInteger(KEY_SIGNAL_CHANGE_THRESHOLD);
    }

    /**
     * Sets the radioEnable portion of the RadioControlData class
     * <br><b>Note: </b> If this is set to false, no other data will be included.
     * <br><b>Note: </b> This setting is normally a <b>READ-ONLY</b> setting.
     *
     * @param radioEnable
     * True if the radio is on, false is the radio is off.
     */
    public void setRadioEnable(Boolean radioEnable) {
        setValue(KEY_RADIO_ENABLE, radioEnable);
    }

    /**
     * Gets the radioEnable portion of the RadioControlData class
     * <br><b>Note: </b> If this is set to false, no other data will be included.
     *
     * @return Boolean - True if the radio is on, false is the radio is off.
     */
    public Boolean getRadioEnable() {
        return getBoolean(KEY_RADIO_ENABLE);
    }

    /**
     * Sets the state portion of the RadioControlData class
     *
     * @param state
     * Read only parameter. See RadioState data type for details.
     */
    public void setState(RadioState state) {
        setValue(KEY_STATE, state);
    }

    /**
     * Gets the state portion of the RadioControlData class
     *
     * @return RadioState - Read only parameter. See RadioState data type for details.
     */
    public RadioState getState() {
        return (RadioState) getObject(RadioState.class, KEY_STATE);
    }

    /**
     * Sets the hdRadioEnable portion of the RadioControlData class
     *
     * @param hdRadioEnable True if the hd radio is on, false if the radio is off.
     */
    public void setHdRadioEnable(Boolean hdRadioEnable) {
        setValue(KEY_HD_RADIO_ENABLE, hdRadioEnable);
    }

    /**
     * Gets the hdRadioEnable portion of the RadioControlData class
     *
     * @return Boolean - True if the hd radio is on, false if the radio is off.
     */
    public Boolean getHdRadioEnable() {
        return getBoolean(KEY_HD_RADIO_ENABLE);
    }

    /**
     * Sets the sisData portion of the RadioControlData class
     *
     * @param sisData Read-only Station Information Service (SIS) data provides basic information about the station such as call sign, as well as information not displayable to the consumer such as the station identification number.
     */
    public void setSisData(SisData sisData) {
        setValue(KEY_SIS_DATA, sisData);
    }

    /**
     * Gets the sisData portion of the RadioControlData class
     *
     * @return SisData - Read-only Station Information Service (SIS) data provides basic information about the station such as call sign, as well as information not displayable to the consumer such as the station identification number.
     */
    public SisData getSisData() {
        return (SisData) getObject(SisData.class, KEY_SIS_DATA);
    }

    /**
     * Sets the availableHdChannels portion of the RadioControlData class
     *
     * @param availableHdChannels List of available hd sub-channel indexes, empty list means no Hd channel is available, read-only.
     */
    public void setAvailableHdChannels(List<Integer> availableHdChannels){
        setValue(KEY_AVAILABLE_HD_CHANNELS, availableHdChannels);
    }

    /**
     * Gets the availableHdChannels portion of the RadioControlData class
     *
     * @return List<Integer> - List of available hd sub-channel indexes, empty list means no Hd channel is available, read-only.
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getAvailableHdChannels(){
        return (List<Integer>) getObject(Integer.class,KEY_AVAILABLE_HD_CHANNELS);
    }
}
