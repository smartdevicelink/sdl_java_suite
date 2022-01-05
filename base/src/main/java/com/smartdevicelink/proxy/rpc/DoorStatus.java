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
import com.smartdevicelink.proxy.rpc.enums.DoorStatusType;

import java.util.Hashtable;

/**
 * Describes the status of a parameter of door.
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
 *      <td>location</td>
 *      <td>Grid</td>
 *      <td></td>
 *      <td>Y</td>
 *      <td></td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>status</td>
 *      <td>DoorStatusType</td>
 *      <td></td>
 *      <td>Y</td>
 *      <td></td>
 *      <td></td>
 *  </tr>
 * </table>
 *
 * @since SmartDeviceLink 7.1.0
 */
public class DoorStatus extends RPCStruct {
    public static final String KEY_LOCATION = "location";
    public static final String KEY_STATUS = "status";

    /**
     * Constructs a new DoorStatus object
     */
    public DoorStatus() {
    }

    /**
     * Constructs a new DoorStatus object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public DoorStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new DoorStatus object
     *
     * @param location
     * @param status
     */
    public DoorStatus(@NonNull Grid location, @NonNull DoorStatusType status) {
        this();
        setLocation(location);
        setStatus(status);
    }

    /**
     * Sets the location.
     *
     * @param location
     */
    public DoorStatus setLocation(@NonNull Grid location) {
        setValue(KEY_LOCATION, location);
        return this;
    }

    /**
     * Gets the location.
     *
     * @return Grid
     */
    public Grid getLocation() {
        return (Grid) getObject(Grid.class, KEY_LOCATION);
    }

    /**
     * Sets the status.
     *
     * @param status
     */
    public DoorStatus setStatus(@NonNull DoorStatusType status) {
        setValue(KEY_STATUS, status);
        return this;
    }

    /**
     * Gets the status.
     *
     * @return DoorStatusType
     */
    public DoorStatusType getStatus() {
        return (DoorStatusType) getObject(DoorStatusType.class, KEY_STATUS);
    }
}
