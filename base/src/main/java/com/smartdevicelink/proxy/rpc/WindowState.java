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

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>approximatePosition</td>
 *      <td>Integer</td>
 *      <td>The approximate percentage that the window is open - 0 being fully closed, 100 being fullyopen</td>
 *      <td>Y</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>deviation</td>
 *      <td>Integer</td>
 *      <td>The percentage deviation of the approximatePosition. e.g. If the approximatePosition is 50and the deviation is 10, then the window's location is somewhere between 40 and 60.</td>
 *      <td>Y</td>
 *      <td></td>
 *  </tr>
 * </table>
 * @since SmartDeviceLink 7.0.0
 */
public class WindowState extends RPCStruct {
    public static final String KEY_APPROXIMATE_POSITION = "approximatePosition";
    public static final String KEY_DEVIATION = "deviation";

    /**
     * Constructs a new WindowState object
     */
    public WindowState() { }

    /**
     * Constructs a new WindowState object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public WindowState(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new WindowState object
     *
     * @param approximatePosition The approximate percentage that the window is open - 0 being fully closed, 100 being fully
     * open
     * @param deviation The percentage deviation of the approximatePosition. e.g. If the approximatePosition is 50
     * and the deviation is 10, then the window's location is somewhere between 40 and 60.
     */
    public WindowState(@NonNull Integer approximatePosition, @NonNull Integer deviation) {
        this();
        setApproximatePosition(approximatePosition);
        setDeviation(deviation);
    }

    /**
     * Sets the approximatePosition.
     *
     * @param approximatePosition The approximate percentage that the window is open - 0 being fully closed, 100 being fully
     * open
     */
    public void setApproximatePosition(@NonNull Integer approximatePosition) {
        setValue(KEY_APPROXIMATE_POSITION, approximatePosition);
    }

    /**
     * Gets the approximatePosition.
     *
     * @return Integer The approximate percentage that the window is open - 0 being fully closed, 100 being fully
     * open
     */
    public Integer getApproximatePosition() {
        return getInteger(KEY_APPROXIMATE_POSITION);
    }

    /**
     * Sets the deviation.
     *
     * @param deviation The percentage deviation of the approximatePosition. e.g. If the approximatePosition is 50
     * and the deviation is 10, then the window's location is somewhere between 40 and 60.
     */
    public void setDeviation(@NonNull Integer deviation) {
        setValue(KEY_DEVIATION, deviation);
    }

    /**
     * Gets the deviation.
     *
     * @return Integer The percentage deviation of the approximatePosition. e.g. If the approximatePosition is 50
     * and the deviation is 10, then the window's location is somewhere between 40 and 60.
     */
    public Integer getDeviation() {
        return getInteger(KEY_DEVIATION);
    }
}
