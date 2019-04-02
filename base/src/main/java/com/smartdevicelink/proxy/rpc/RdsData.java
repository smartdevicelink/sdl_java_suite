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

import java.util.Hashtable;

/**
 * Include the data defined in Radio Data System, which is a communications protocol standard
 * for embedding small amounts of digital information in conventional FM radio broadcasts.
 */
public class RdsData extends RPCStruct{
    public static final String KEY_PS= "PS";
    public static final String KEY_RT= "RT";
    public static final String KEY_CT= "CT";
    public static final String KEY_PI= "PI";
    public static final String KEY_PTY= "PTY";
    public static final String KEY_TP= "TP";
    public static final String KEY_TA= "TA";
    public static final String KEY_REG= "REG";

    public RdsData() {
    }

    public RdsData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the programService portion of the RdsData class
     *
     * @param programService
     * Program Service Name.
     */
    public void setProgramService(String programService) {
        setValue(KEY_PS, programService);
    }

    /**
     * Gets the programService portion of the RdsData class
     *
     * @return String - Program Service Name.
     */
    public String getProgramService() {
        return getString(KEY_PS);
    }

    /**
     * Sets the radioText portion of the RdsData class
     *
     * @param radioText
     * Radio Text.
     */
    public void setRadioText(String radioText) {
        setValue(KEY_RT, radioText);
    }

    /**
     * Gets the radioText portion of the RdsData class
     *
     * @return String - Radio Text.
     */
    public String getRadioText() {
        return getString(KEY_RT);
    }

    /**
     * Sets the clockText portion of the RdsData class
     *
     * @param clockText
     * The clock text in UTC format as YYYY-MM-DDThh:mm:ss.sTZD.
     */
    public void setClockText(String clockText) {
        setValue(KEY_CT, clockText);
    }

    /**
     * Gets the clockText portion of the RdsData class
     *
     * @return String - The clock text in UTC format as YYYY-MM-DDThh:mm:ss.sTZD.
     */
    public String getClockText() {
        return getString(KEY_CT);
    }

    /**
     * Sets the programIdentification portion of the RdsData class
     *
     * @param programIdentification
     * Program Identification - the call sign for the radio station.
     */
    public void setProgramIdentification(String programIdentification) {
        setValue(KEY_PI, programIdentification);
    }

    /**
     * Gets the programIdentification portion of the RdsData class
     *
     * @return String - Program Identification - the call sign for the radio station.
     */
    public String getProgramIdentification() {
        return getString(KEY_PI);
    }

    /**
     * Sets the region portion of the RdsData class
     *
     * @param region
     * Region.
     */
    public void setRegion(String region) {
        setValue(KEY_REG, region);
    }

    /**
     * Gets the region portion of the RdsData class
     *
     * @return String - Region.
     */
    public String getRegion() {
        return getString(KEY_REG);
    }

    /**
     * Sets the trafficProgram portion of the RdsData class
     *
     * @param trafficProgram
     * Traffic Program Identification - Identifies a station that offers traffic.
     */
    public void setTrafficProgram(Boolean trafficProgram) {
        setValue(KEY_TP, trafficProgram);
    }

    /**
     * Gets the trafficProgram portion of the RdsData class
     *
     * @return Boolean - Traffic Program Identification - Identifies a station that offers traffic.
     */
    public Boolean getTrafficProgram() {
        return getBoolean(KEY_TP);
    }

    /**
     * Sets the trafficAnnouncement portion of the RdsData class
     *
     * @param trafficAnnouncement
     * Traffic Announcement Identification - Indicates an ongoing traffic announcement.
     */
    public void setTrafficAnnouncement(Boolean trafficAnnouncement) {
        setValue(KEY_TA, trafficAnnouncement);
    }

    /**
     * Gets the trafficAnnouncement portion of the RdsData class
     *
     * @return Boolean - Traffic Announcement Identification - Indicates an ongoing traffic announcement.
     */
    public Boolean getTrafficAnnouncement() {
        return getBoolean(KEY_TA);
    }

    /**
     * Sets the programType portion of the RdsData class
     *
     * @param programType
     * The program type - The region should be used to differentiate between EU and North America program types.
     */
    public void setProgramType(Integer programType) {
        setValue(KEY_PTY, programType);
    }

    /**
     * Gets the programType portion of the RdsData class
     *
     * @return Integer - The program type.
     * The region should be used to differentiate between EU and North America program types.
     */
    public Integer getProgramType() {
        return getInteger(KEY_PTY);
    }
}
