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
 * Describes the hour, minute and second values used to set the media clock.
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 *         <tr>
 *             <th>Name</th>
 *             <th>Type</th>
 *             <th>Description</th>
 *             <th>SmartDeviceLink Ver. Available</th>
 *         </tr>
 *         <tr>
 *             <td>hours</td>
 *             <td>Integer</td>
 *             <td>The hour. Minvalue="0", maxvalue="59"
 *                     <p><b>Note:</b></p>Some display types only support a max value of 19. If out of range, it will be rejected.
 *             </td>
 *             <td>SmartDeviceLink 1.0</td>
 *         </tr>
 *         <tr>
 *             <td>minutes</td>
 *             <td>Integer</td>
 *             <td>The minute. Minvalue="0", maxvalue="59".</td>
 *             <td>SmartDeviceLink 1.0</td>
 *         </tr>
 *     <tr>
 *             <td>seconds</td>
 *             <td>Integer</td>
 *             <td>The second. Minvalue="0", maxvalue="59".</td>
 *             <td>SmartDeviceLink 1.0</td>
 *         </tr>
 * </table>
 *
 * @since SmartDeviceLink 1.0
 */
public class StartTime extends RPCStruct {
    public static final String KEY_MINUTES = "minutes";
    public static final String KEY_SECONDS = "seconds";
    public static final String KEY_HOURS = "hours";

    /**
     * Constructs a newly allocated StartTime object
     */
    public StartTime() {
    }

    /**
     * Constructs a newly allocated StartTime object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public StartTime(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated StartTime object
     *
     * @param timeInterval time interval in seconds
     */
    public StartTime(@NonNull Integer timeInterval) {
        this();
        int hours = timeInterval / 3600;
        int minutes = (timeInterval % 3600) / 60;
        int seconds = timeInterval % 60;
        setHours(hours);
        setMinutes(minutes);
        setSeconds(seconds);
    }

    /**
     * Constructs a newly allocated StartTime object
     *
     * @param hours   The hour
     * @param minutes The minute
     * @param seconds The second
     */
    public StartTime(@NonNull Integer hours, @NonNull Integer minutes, @NonNull Integer seconds) {
        this();
        setHours(hours);
        setMinutes(minutes);
        setSeconds(seconds);
    }

    /**
     * Get the hour. Minvalue="0", maxvalue="59"
     * <p><b>Note:</b></p>Some display types only support a max value of 19. If out of range, it will be rejected.
     *
     * @return hours Minvalue="0", maxvalue="59"
     */
    public Integer getHours() {
        return getInteger(KEY_HOURS);
    }

    /**
     * Set the hour. Minvalue="0", maxvalue="59"
     * <p><b>Note:</b></p>Some display types only support a max value of 19. If out of range, it will be rejected.
     *
     * @param hours min: 0; max: 59
     */
    public StartTime setHours(@NonNull Integer hours) {
        setValue(KEY_HOURS, hours);
        return this;
    }

    /**
     * Get the minute. Minvalue="0", maxvalue="59".
     *
     * @return minutes Minvalue="0", maxvalue="59"
     */
    public Integer getMinutes() {
        return getInteger(KEY_MINUTES);
    }

    /**
     * Set the minute. Minvalue="0", maxvalue="59".
     *
     * @param minutes min: 0; max: 59
     */
    public StartTime setMinutes(@NonNull Integer minutes) {
        setValue(KEY_MINUTES, minutes);
        return this;
    }

    /**
     * Get the second. Minvalue="0", maxvalue="59".
     *
     * @return seconds. Minvalue="0", maxvalue="59".
     */
    public Integer getSeconds() {
        return getInteger(KEY_SECONDS);
    }

    /**
     * Set the second. Minvalue="0", maxvalue="59".
     *
     * @param seconds min: 0 max: 59
     */
    public StartTime setSeconds(@NonNull Integer seconds) {
        setValue(KEY_SECONDS, seconds);
        return this;
    }
}
