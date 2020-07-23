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

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;

import java.util.Hashtable;

/**
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
 *      <td>escSystem</td>
 *      <td>VehicleDataStatus</td>
 *      <td>true if vehicle stability control is ON, else false</td>
 *      <td>N</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>trailerSwayControl</td>
 *      <td>VehicleDataStatus</td>
 *      <td>true if vehicle trailer sway control is ON, else false</td>
 *      <td>N</td>
 *      <td></td>
 *  </tr>
 * </table>
 *
 * @since SmartDeviceLink 6.2.0
 */
public class StabilityControlsStatus extends RPCStruct {
    public static final String KEY_ESC_SYSTEM = "escSystem";
    public static final String KEY_TRAILER_SWAY_CONTROL = "trailerSwayControl";


    /**
     * Constructs a new StabilityControlsStatus object
     */
    public StabilityControlsStatus() {
    }

    /**
     * <p>Constructs a new StabilityControlsStatus object indicated by the Hashtable parameter
     * </p>
     *
     * @param hash The Hashtable to use
     */
    public StabilityControlsStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

    /***
     * @return VehicleDataStatus for escSystem
     */
    public VehicleDataStatus getEscSystem() {
        return (VehicleDataStatus) getObject(VehicleDataStatus.class, KEY_ESC_SYSTEM);
    }

    /***
     * sets EscSystem
     * @param status VehicleDataStatus
     */
    public void setEscSystem(VehicleDataStatus escSystem) {
        setValue(KEY_ESC_SYSTEM, escSystem);
    }

    /***
     * sets TrailerSwayControl
     * @param status VehicleDataStatus
     */
    public void setTrailerSwayControl(VehicleDataStatus status) {
        setValue(KEY_TRAILER_SWAY_CONTROL, status);
    }

    /***
     * @return VehicleDataStatus for trailerSwayControl
     */
    public VehicleDataStatus getTrailerSWayControl() {
        return (VehicleDataStatus) getObject(VehicleDataStatus.class, KEY_TRAILER_SWAY_CONTROL);
    }
}
