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
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.TransmissionType;

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
 *      <td>userSelectedGear</td>
 *      <td>PRNDL</td>
 *      <td>Gear position selected by the user i.e. Park, Drive, Reverse</td>
 *      <td>N</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>actualGear</td>
 *      <td>PRNDL</td>
 *      <td>Actual Gear in use by the transmission</td>
 *      <td>N</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>transmissionType</td>
 *      <td>TransmissionType</td>
 *      <td>Tells the transmission type</td>
 *      <td>N</td>
 *      <td></td>
 *  </tr>
 * </table>
 * @since SmartDeviceLink 7.0.0
 */
public class GearStatus extends RPCStruct {
    public static final String KEY_USER_SELECTED_GEAR = "userSelectedGear";
    public static final String KEY_ACTUAL_GEAR = "actualGear";
    public static final String KEY_TRANSMISSION_TYPE = "transmissionType";

    /**
     * Constructs a newly allocated GearStatus object
     */
    public GearStatus() {}

    /**
     * Constructs a new GearStatus object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public GearStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the userSelectedGear.
     *
     * @param userSelectedGear Gear position selected by the user i.e. Park, Drive, Reverse
     */
    public GearStatus setUserSelectedGear( PRNDL selectedGear) {
        setValue(KEY_USER_SELECTED_GEAR, selectedGear);
        return this;
    }

    /**
     * Gets the userSelectedGear.
     *
     * @return PRNDL Gear position selected by the user i.e. Park, Drive, Reverse
     */
    @SuppressWarnings("unchecked")
    public PRNDL getUserSelectedGear(){
        return (PRNDL)getObject(PRNDL.class, KEY_USER_SELECTED_GEAR);
    }

    /**
     * Sets the actualGear.
     *
     * @param actualGear Actual Gear in use by the transmission
     */
    public GearStatus setActualGear( PRNDL actualGear) {
        setValue(KEY_ACTUAL_GEAR, actualGear);
        return this;
    }

    /**
     * Gets the actualGear.
     *
     * @return PRNDL Actual Gear in use by the transmission
     */
    @SuppressWarnings("unchecked")
    public PRNDL getActualGear(){
        return (PRNDL)getObject(PRNDL.class, KEY_ACTUAL_GEAR);
    }

    /**
     * Sets the transmissionType.
     *
     * @param transmissionType Tells the transmission type
     */
    public GearStatus setTransmissionType( TransmissionType transmissionType) {
        setValue(KEY_TRANSMISSION_TYPE, transmissionType);
        return this;
    }

    /**
     * Gets the transmissionType.
     *
     * @return TransmissionType Tells the transmission type
     */
    public TransmissionType getTransmissionType(){
        return  (TransmissionType)getObject(TransmissionType.class, KEY_TRANSMISSION_TYPE);
    }
}
