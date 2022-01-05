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

import java.util.Hashtable;
import java.util.List;

/**
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
 *      <td>seatsOccupied</td>
 *      <td>List<SeatStatus></td>
 *      <td>Seat status array containing location and whether the seats are occupied.</td>
 *      <td>N</td>
 *      <td>{"array_min_size": 0, "array_max_size": 100}</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>seatsBelted</td>
 *      <td>List<SeatStatus></td>
 *      <td>Seat status array containing location and whether the seats are belted.</td>
 *      <td>N</td>
 *      <td>{"array_min_size": 0, "array_max_size": 100}</td>
 *      <td></td>
 *  </tr>
 * </table>
 *
 * @since SmartDeviceLink 7.1.0
 */
public class SeatOccupancy extends RPCStruct {
    public static final String KEY_SEATS_OCCUPIED = "seatsOccupied";
    public static final String KEY_SEATS_BELTED = "seatsBelted";

    /**
     * Constructs a new SeatOccupancy object
     */
    public SeatOccupancy() {
    }

    /**
     * Constructs a new SeatOccupancy object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public SeatOccupancy(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the seatsOccupied.
     *
     * @param seatsOccupied Seat status array containing location and whether the seats are occupied.
     *                      {"array_min_size": 0, "array_max_size": 100}
     */
    public SeatOccupancy setSeatsOccupied(List<SeatStatus> seatsOccupied) {
        setValue(KEY_SEATS_OCCUPIED, seatsOccupied);
        return this;
    }

    /**
     * Gets the seatsOccupied.
     *
     * @return List<SeatStatus> Seat status array containing location and whether the seats are occupied.
     * {"array_min_size": 0, "array_max_size": 100}
     */
    @SuppressWarnings("unchecked")
    public List<SeatStatus> getSeatsOccupied() {
        return (List<SeatStatus>) getObject(SeatStatus.class, KEY_SEATS_OCCUPIED);
    }

    /**
     * Sets the seatsBelted.
     *
     * @param seatsBelted Seat status array containing location and whether the seats are belted.
     *                    {"array_min_size": 0, "array_max_size": 100}
     */
    public SeatOccupancy setSeatsBelted(List<SeatStatus> seatsBelted) {
        setValue(KEY_SEATS_BELTED, seatsBelted);
        return this;
    }

    /**
     * Gets the seatsBelted.
     *
     * @return List<SeatStatus> Seat status array containing location and whether the seats are belted.
     * {"array_min_size": 0, "array_max_size": 100}
     */
    @SuppressWarnings("unchecked")
    public List<SeatStatus> getSeatsBelted() {
        return (List<SeatStatus>) getObject(SeatStatus.class, KEY_SEATS_BELTED);
    }
}
