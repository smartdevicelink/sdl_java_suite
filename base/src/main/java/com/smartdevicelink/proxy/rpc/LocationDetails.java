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
import java.util.List;

public class LocationDetails extends RPCStruct{
    public static final String KEY_COORDINATE = "coordinate";
    public static final String KEY_LOCATION_NAME = "locationName";
    public static final String KEY_ADDRESS_LINES = "addressLines";
    public static final String KEY_LOCATION_DESCRIPTION = "locationDescription";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_LOCATION_IMAGE = "locationImage";
    public static final String KEY_SEARCH_ADDRESS = "searchAddress";

    public LocationDetails() {
    }

    public LocationDetails(Hashtable<String, Object> hash) {
        super(hash);
    }

    public Coordinate getCoordinate() {
        return (Coordinate) getObject(Coordinate.class, KEY_COORDINATE);
    }
    public LocationDetails setCoordinate( Coordinate coordinate) {
        setValue(KEY_COORDINATE, coordinate);
        return this;
    }

    public String getLocationName() {
        return getString(KEY_LOCATION_NAME);
    }

    public LocationDetails setLocationName( String locationName) {
        setValue(KEY_LOCATION_NAME, locationName);
        return this;
    }

    @SuppressWarnings("unchecked")
    public List<String> getAddressLines() {
        return (List<String>) getObject(String.class, KEY_ADDRESS_LINES);
    }

    public LocationDetails setAddressLines( List<String> addressLines) {
        setValue(KEY_ADDRESS_LINES, addressLines);
        return this;
    }

    public String getLocationDescription() {
        return getString(KEY_LOCATION_DESCRIPTION);
    }

    public LocationDetails setLocationDescription( String locationDescription) {
        setValue(KEY_LOCATION_DESCRIPTION, locationDescription);
        return this;
    }

    public String getPhoneNumber() {
        return getString(KEY_PHONE_NUMBER);
    }

    public LocationDetails setPhoneNumber( String phoneNumber) {
        setValue(KEY_PHONE_NUMBER, phoneNumber);
        return this;
    }

    public Image getLocationImage() {
        return (Image) getObject(Image.class, KEY_LOCATION_IMAGE);
    }

    public LocationDetails setLocationImage( Image locationImage) {
        setValue(KEY_LOCATION_IMAGE, locationImage);
        return this;
    }

    public OasisAddress getSearchAddress() {
        return (OasisAddress) getObject(OasisAddress.class, KEY_SEARCH_ADDRESS);
    }
    public LocationDetails setSearchAddress( OasisAddress searchAddress) {
        setValue(KEY_SEARCH_ADDRESS, searchAddress);
        return this;
    }
}
