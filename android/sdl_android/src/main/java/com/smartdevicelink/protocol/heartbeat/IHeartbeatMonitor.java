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
package com.smartdevicelink.protocol.heartbeat;

import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface IHeartbeatMonitor {
    /**
     * Starts the monitor. If the monitor is already started, nothing happens.
     */
    void start();

    /**
     * Stops the monitor. Does nothing if it is already stopped.
     */
    void stop();

    /**
     * Returns the heartbeat messages interval.
     *
     * @return interval in milliseconds
     */
    int getInterval();

    /**
     * Sets the interval for sending heartbeat messages if nothing is sent over
     * transport.
     *
     * @param interval interval in milliseconds (min/max values depend on
     *                 concrete implementations)
     */
    void setInterval(int interval);

    /**
     * Returns the listener.
     *
     * @return the listener
     */
    IHeartbeatMonitorListener getListener();

    /**
     * Sets the heartbeat's listener.
     *
     * @param listener the new listener
     */
    void setListener(IHeartbeatMonitorListener listener);

    /**
     * Notifies the monitor about sent/received messages.
     */
    void notifyTransportActivity();

    /**
     * Notifies the monitor about a received heartbeat ACK message.
     */
    void heartbeatACKReceived();

    /**
     * Notifies the monitor about a received heartbeat message.
     */
    void heartbeatReceived();
}