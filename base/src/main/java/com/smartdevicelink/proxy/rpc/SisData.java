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

public class SisData extends RPCStruct {
    public static final String KEY_STATION_SHORT_NAME = "stationShortName";
    public static final String KEY_STATION_ID_NUMBER = "stationIDNumber";
    public static final String KEY_STATION_LONG_NAME = "stationLongName";
    public static final String KEY_STATION_LOCATION = "stationLocation";
    public static final String KEY_STATION_MESSAGE = "stationMessage";

    /**
     * Constructs a new SisData object
     */
    public SisData() {
    }

    /**
     * <p>Constructs a new SisData object indicated by the Hashtable parameter
     * </p>
     *
     * @param hash The Hashtable to use
     */
    public SisData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the stationShortName portion of the SisData class
     *
     * @param stationShortName Identifies the 4-alpha-character station call sign plus an optional (-FM) extension.
     */
    public SisData setStationShortName(String stationShortName) {
        setValue(KEY_STATION_SHORT_NAME, stationShortName);
        return this;
    }

    /**
     * Gets the stationShortName portion of the SisData class
     *
     * @return String - Identifies the 4-alpha-character station call sign plus an optional (-FM) extension.
     */
    public String getStationShortName() {
        return getString(KEY_STATION_SHORT_NAME);
    }

    /**
     * Sets the stationIDNumber which is used for network Application. Consists of Country Code and FCC Facility ID
     *
     * @param stationIDNumber Consists of Country Code and FCC Facility ID
     */
    public SisData setStationIDNumber(StationIDNumber stationIDNumber) {
        setValue(KEY_STATION_ID_NUMBER, stationIDNumber);
        return this;
    }

    /**
     * Gets the stationIDNumber which is used for network Application. Consists of Country Code and FCC Facility ID
     *
     * @return StationIDNumber.
     */
    public StationIDNumber getStationIDNumber() {
        return (StationIDNumber) getObject(StationIDNumber.class, KEY_STATION_ID_NUMBER);
    }

    /**
     * Sets the stationLongName portion of the SisData class
     *
     * @param stationLongName Identifies the station call sign or other identifying information in the long format.
     */
    public SisData setStationLongName(String stationLongName) {
        setValue(KEY_STATION_LONG_NAME, stationLongName);
        return this;
    }

    /**
     * Gets the stationLongName portion of the SisData class
     *
     * @return String - Identifies the station call sign or other identifying information in the long format.
     */
    public String getStationLongName() {
        return getString(KEY_STATION_LONG_NAME);
    }

    /**
     * Sets the stationLocation portion of the SisData class
     *
     * @param stationLocation Provides the 3-dimensional geographic station location.
     */
    public SisData setStationLocation(GPSData stationLocation) {
        setValue(KEY_STATION_LOCATION, stationLocation);
        return this;
    }

    /**
     * Gets the stationLocation portion of the SisData class
     *
     * @return GPSData - Provides the 3-dimensional geographic station location.
     */
    public GPSData getStationLocation() {
        return (GPSData) getObject(GPSData.class, KEY_STATION_LOCATION);
    }

    /**
     * Sets the stationMessage portion of the SisData class
     *
     * @param stationMessage May be used to convey textual information of general interest to the consumer such as weather forecasts or public service announcements.
     *                       Includes a high priority delivery feature to convey emergencies that may be in the listening area.
     */
    public SisData setStationMessage(String stationMessage) {
        setValue(KEY_STATION_MESSAGE, stationMessage);
        return this;
    }

    /**
     * Gets the stationMessage portion of the SisData class
     *
     * @return String - May be used to convey textual information of general interest to the consumer such as weather forecasts or public service announcements.
     * Includes a high priority delivery feature to convey emergencies that may be in the listening area.
     */
    public String getStationMessage() {
        return getString(KEY_STATION_MESSAGE);
    }
}
