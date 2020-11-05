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

public class OasisAddress extends RPCStruct {
    public static final String KEY_COUNTRY_NAME = "countryName";
    public static final String KEY_COUNTRY_CODE = "countryCode";
    public static final String KEY_POSTAL_CODE = "postalCode";
    public static final String KEY_ADMINISTRATIVE_AREA = "administrativeArea";
    public static final String KEY_SUB_ADMINISTRATIVE_AREA = "subAdministrativeArea";
    public static final String KEY_LOCALITY = "locality";
    public static final String KEY_SUB_LOCALITY = "subLocality";
    public static final String KEY_THOROUGH_FARE = "thoroughfare";
    public static final String KEY_SUB_THOROUGH_FARE = "subThoroughfare";

    /**
     * OASIS Address - A standard based address class that has been established by The Organization for the Advancement of Structured Information Standards (OASIS).
     * Oasis is a global nonprofit consortium that works on the development, convergence, and adoption of standards for security,
     * Internet of Things, energy, content technologies, emergency management, and other areas.
     */
    public OasisAddress() {
    }

    public OasisAddress(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Gets the localized Name of the country associated with the OasisAddress class.
     *
     * @return String - The localized Name of the country associated with the OasisAddress class.
     */
    public String getCountryName() {
        return getString(KEY_COUNTRY_NAME);
    }

    /**
     * Sets the localized Name of the country associated with the OasisAddress class.
     *
     * @param countryName The localized Name of the country associated with the OasisAddress class.
     */
    public OasisAddress setCountryName(String countryName) {
        setValue(KEY_COUNTRY_NAME, countryName);
        return this;
    }

    /**
     * Gets the country code in ISO 3166-2 format associated with the OasisAddress class.
     *
     * @return String - The country code in ISO 3166-2 format associated with the OasisAddress class.
     */
    public String getCountryCode() {
        return getString(KEY_COUNTRY_CODE);
    }

    /**
     * Sets the country code in ISO 3166-2 format associated with the OasisAddress class.
     *
     * @param countryCode The country code in ISO 3166-2 format associated with the OasisAddress class.
     */
    public OasisAddress setCountryCode(String countryCode) {
        setValue(KEY_COUNTRY_CODE, countryCode);
        return this;
    }

    /**
     * Gets the Postal Code associated with the OasisAddress class.
     *
     * @return String - The Postal Code associated with the OasisAddress class.
     */
    public String getPostalCode() {
        return getString(KEY_POSTAL_CODE);
    }

    /**
     * Sets the Postal Code associated with the OasisAddress class.
     *
     * @param postalCode The Postal Code associated with the OasisAddress class.
     */
    public OasisAddress setPostalCode(String postalCode) {
        setValue(KEY_POSTAL_CODE, postalCode);
        return this;
    }

    /**
     * Gets the Administrative Area associated with the OasisAddress class. A portion of the country - Administrative Area's can include details of the top-level area division in the country, such as state, district, province, island, region, etc.
     *
     * @return String - The Administrative Area associated with the OasisAddress class.
     */
    public String getAdministrativeArea() {
        return getString(KEY_ADMINISTRATIVE_AREA);
    }

    /**
     * Sets the Administrative Area associated with the OasisAddress class.   A portion of the country - Administrative Area can include details of the top-level area division in the country, such as state, district, province, island, region, etc.
     *
     * @param administrativeArea The Administrative Area associated with the OasisAddress class.
     */
    public OasisAddress setAdministrativeArea(String administrativeArea) {
        setValue(KEY_ADMINISTRATIVE_AREA, administrativeArea);
        return this;
    }

    /**
     * Gets the SubAdministrative Area associated with the OasisAddress class. A portion of the administrativeArea - The next level down division of the area. E.g. state / county, province / reservation.
     *
     * @return String - The SubAdministrative Area associated with the OasisAddress class.
     */
    public String getSubAdministrativeArea() {
        return getString(KEY_SUB_ADMINISTRATIVE_AREA);
    }

    /**
     * Sets the SubAdministrative Area associated with the OasisAddress class.   A portion of the administrativeArea - The next level down division of the area. E.g. state / county, province / reservation.
     *
     * @param subAdministrativeArea The SubAdministrative Area associated with the OasisAddress class.
     */
    public OasisAddress setSubAdministrativeArea(String subAdministrativeArea) {
        setValue(KEY_SUB_ADMINISTRATIVE_AREA, subAdministrativeArea);
        return this;
    }

    /**
     * Gets the Locality associated with the OasisAddress class. - A hypernym for city/village
     *
     * @return String -  The Locality associated with the OasisAddress class.
     */
    public String getLocality() {
        return getString(KEY_LOCALITY);
    }

    /**
     * Sets the Locality associated with the OasisAddress class. - A hypernym for city/village.
     *
     * @param locality The Locality associated with the OasisAddress class.
     */
    public OasisAddress setLocality(String locality) {
        setValue(KEY_LOCALITY, locality);
        return this;
    }

    /**
     * Gets the Sub-Locality associated with the OasisAddress class. - Hypernym for district.
     *
     * @return String -  The Sub-Locality associated with the OasisAddress class.
     */
    public String getSubLocality() {
        return getString(KEY_SUB_LOCALITY);
    }

    /**
     * Sets the Sub-Locality associated with the OasisAddress class.   A hypernym for district.
     *
     * @param subLocality The Sub-Locality associated with the OasisAddress class.
     */
    public OasisAddress setSubLocality(String subLocality) {
        setValue(KEY_SUB_LOCALITY, subLocality);
        return this;
    }

    /**
     * Gets the Thoroughfare associated with the OasisAddress class. - A hypernym for street, road etc.
     *
     * @return String -  The Thoroughfare associated with the OasisAddress class.
     */
    public String getThoroughfare() {
        return getString(KEY_THOROUGH_FARE);
    }

    /**
     * Sets the Thoroughfare associated with the OasisAddress class.   A hypernym for street, road etc.
     *
     * @param thoroughFare The Thoroughfare associated with the OasisAddress class.
     */
    public OasisAddress setThoroughfare(String thoroughFare) {
        setValue(KEY_THOROUGH_FARE, thoroughFare);
        return this;
    }

    /**
     * Gets the Sub-Thoroughfare associated with the OasisAddress class. - A Portion of thoroughfare (e.g. house number).
     *
     * @return String -  The Sub-Thoroughfare associated with the OasisAddress class.
     */
    public String getSubThoroughfare() {
        return getString(KEY_SUB_THOROUGH_FARE);
    }

    /**
     * Sets the Sub-Thoroughfare associated with the OasisAddress class. - A Portion of thoroughfare (e.g. house number).
     *
     * @param subThoroughfare The Sub-Thoroughfare associated with the OasisAddress class.
     */
    public OasisAddress setSubThoroughfare(String subThoroughfare) {
        setValue(KEY_SUB_THOROUGH_FARE, subThoroughfare);
        return this;
    }
}
