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

/**
 * Describes possible states of turn-by-turn module.
 *
 * @since SmartDeviceLink 1.0
 */
public enum TBTState {
    /**
     * Indicates that driver requested a route update.
     */
    ROUTE_UPDATE_REQUEST,
    /**
     * Confirmation from HMI about accepting the route.
     */

    ROUTE_ACCEPTED,
    /**
     * Information from HMI about the route refusal.
     */

    ROUTE_REFUSED,
    /**
     * Information from HMI about canceling the route.
     */

    ROUTE_CANCELLED,
    /**
     * Request from HMI for Estimated time of arrival.
     */

    ETA_REQUEST,
    /**
     * Request from HMI for the information of the next turn.
     */

    NEXT_TURN_REQUEST,
    /**
     * Request from HMI for the route status.
     */

    ROUTE_STATUS_REQUEST,
    /**
     * Request from HMI for the route summary.
     */

    ROUTE_SUMMARY_REQUEST,
    /**
     * Request from HMI for the information about trip status.
     */

    TRIP_STATUS_REQUEST,
    /**
     * Request from HMI for the timeout for waiting for the route updating.
     */

    ROUTE_UPDATE_REQUEST_TIMEOUT;

    /**
     * Convert String to TBTState
     *
     * @param value String
     * @return TBTState
     */
    public static TBTState valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}
