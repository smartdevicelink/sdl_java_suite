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
package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

/**
 * Defines the metadata types that can be applied to text fields
 */
public enum MetadataType {

    /**
     * The data in this field contains the title of the currently playing audio track.
     */
    MEDIA_TITLE("mediaTitle"),

    /**
     * The data in this field contains the artist or creator of the currently playing audio track.
     */
    MEDIA_ARTIST("mediaArtist"),

    /**
     * The data in this field contains the album title of the currently playing audio track.
     */
    MEDIA_ALBUM("mediaAlbum"),

    /**
     * The data in this field contains the creation year of the currently playing audio track.
     */
    MEDIA_YEAR("mediaYear"),

    /**
     * The data in this field contains the genre of the currently playing audio track.
     */
    MEDIA_GENRE("mediaGenre"),

    /**
     * The data in this field contains the name of the current source for the media.
     */
    MEDIA_STATION("mediaStation"),

    /**
     * The data in this field is a rating.
     */
    RATING("rating"),

    /**
     * The data in this field is the current temperature.
     */
    CURRENT_TEMPERATURE("currentTemperature"),

    /**
     * The data in this field is the maximum temperature for the day.
     */
    MAXIMUM_TEMPERATURE("maximumTemperature"),

    /**
     * The data in this field is the minimum temperature for the day.
     */
    MINIMUM_TEMPERATURE("minimumTemperature"),

    /**
     * The data in this field describes the current weather (ex. cloudy, clear, etc.).
     */
    WEATHER_TERM("weatherTerm"),

    /**
     * The data in this field describes the current humidity value.
     */
    HUMIDITY("humidity"),


    ;

    private final String VALUE;

    private MetadataType(String value) {
        this.VALUE = value;
    }

    public String toString() {
        return this.VALUE;
    }

    public static MetadataType valueForString(String value) {
        if (value == null) {
            return null;
        }

        for (MetadataType anEnum : EnumSet.allOf(MetadataType.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
