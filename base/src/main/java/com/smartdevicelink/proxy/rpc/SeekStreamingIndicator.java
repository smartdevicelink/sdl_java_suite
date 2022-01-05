/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium Inc. nor the names of
 * its contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
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
import com.smartdevicelink.proxy.rpc.enums.SeekIndicatorType;

import java.util.Hashtable;

/**
 * The seek next / skip previous subscription buttons' content
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Notes</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>type</td>
 *      <td>SeekIndicatorType</td>
 *      <td></td>
 *      <td>Y</td>
 *      <td></td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>seekTime</td>
 *      <td>Integer</td>
 *      <td>If the type is TIME, this number of seconds may be present alongside the skip indicator.It will indicate the number of seconds that the currently playing media will skip forward or backward.</td>
 *      <td>N</td>
 *      <td>{"num_min_value": 1, "num_max_value": 99}</td>
 *      <td></td>
 *  </tr>
 * </table>
 *
 * @since SmartDeviceLink 7.1.0
 */
public class SeekStreamingIndicator extends RPCStruct {
    public static final String KEY_TYPE = "type";
    public static final String KEY_SEEK_TIME = "seekTime";

    /**
     * Constructs a new SeekStreamingIndicator object
     */
    public SeekStreamingIndicator() {
    }

    /**
     * Constructs a new SeekStreamingIndicator object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public SeekStreamingIndicator(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new SeekStreamingIndicator object
     *
     * @param type
     */
    public SeekStreamingIndicator(@NonNull SeekIndicatorType type) {
        this();
        setType(type);
    }

    /**
     * Sets the type.
     *
     * @param type
     */
    public SeekStreamingIndicator setType(@NonNull SeekIndicatorType type) {
        setValue(KEY_TYPE, type);
        return this;
    }

    /**
     * Gets the type.
     *
     * @return SeekIndicatorType
     */
    public SeekIndicatorType getType() {
        return (SeekIndicatorType) getObject(SeekIndicatorType.class, KEY_TYPE);
    }

    /**
     * Sets the seekTime.
     *
     * @param seekTime If the type is TIME, this number of seconds may be present alongside the skip indicator.
     *                 It will indicate the number of seconds that the currently playing media will skip forward
     *                 or backward.
     *                 {"num_min_value": 1, "num_max_value": 99}
     */
    public SeekStreamingIndicator setSeekTime(Integer seekTime) {
        setValue(KEY_SEEK_TIME, seekTime);
        return this;
    }

    /**
     * Gets the seekTime.
     *
     * @return Integer If the type is TIME, this number of seconds may be present alongside the skip indicator.
     * It will indicate the number of seconds that the currently playing media will skip forward
     * or backward.
     * {"num_min_value": 1, "num_max_value": 99}
     */
    public Integer getSeekTime() {
        return getInteger(KEY_SEEK_TIME);
    }
}
