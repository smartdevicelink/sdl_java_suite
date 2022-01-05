/*
 * Copyright (c) 2017 - 2021, SmartDeviceLink Consortium, Inc.
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
import com.smartdevicelink.proxy.rpc.enums.AppCapabilityType;

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
 *      <th>Notes</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>appCapabilityType</td>
 *      <td>AppCapabilityType</td>
 *      <td>Used as a descriptor of what data to expect in this struct. The corresponding param to this enum should be included and the only other param included.</td>
 *      <td>Y</td>
 *      <td></td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>videoStreamingCapability</td>
 *      <td>VideoStreamingCapability</td>
 *      <td>Describes supported capabilities for video streaming</td>
 *      <td>N</td>
 *      <td></td>
 *      <td></td>
 *  </tr>
 * </table>
 *
 * @since SmartDeviceLink 7.1.0
 */
public class AppCapability extends RPCStruct {
    public static final String KEY_APP_CAPABILITY_TYPE = "appCapabilityType";
    public static final String KEY_VIDEO_STREAMING_CAPABILITY = "videoStreamingCapability";

    /**
     * Constructs a new AppCapability object
     */
    public AppCapability() {
    }

    /**
     * Constructs a new AppCapability object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public AppCapability(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new AppCapability object
     *
     * @param appCapabilityType Used as a descriptor of what data to expect in this struct. The corresponding param to
     *                          this enum should be included and the only other param included.
     */
    public AppCapability(@NonNull AppCapabilityType appCapabilityType) {
        this();
        setAppCapabilityType(appCapabilityType);
    }

    /**
     * Sets the appCapabilityType.
     *
     * @param appCapabilityType Used as a descriptor of what data to expect in this struct. The corresponding param to
     *                          this enum should be included and the only other param included.
     */
    public AppCapability setAppCapabilityType(@NonNull AppCapabilityType appCapabilityType) {
        setValue(KEY_APP_CAPABILITY_TYPE, appCapabilityType);
        return this;
    }

    /**
     * Gets the appCapabilityType.
     *
     * @return AppCapabilityType Used as a descriptor of what data to expect in this struct. The corresponding param to
     * this enum should be included and the only other param included.
     */
    public AppCapabilityType getAppCapabilityType() {
        return (AppCapabilityType) getObject(AppCapabilityType.class, KEY_APP_CAPABILITY_TYPE);
    }

    /**
     * Sets the videoStreamingCapability.
     *
     * @param videoStreamingCapability Describes supported capabilities for video streaming
     */
    public AppCapability setVideoStreamingCapability(VideoStreamingCapability videoStreamingCapability) {
        setValue(KEY_VIDEO_STREAMING_CAPABILITY, videoStreamingCapability);
        return this;
    }

    /**
     * Gets the videoStreamingCapability.
     *
     * @return VideoStreamingCapability Describes supported capabilities for video streaming
     */
    public VideoStreamingCapability getVideoStreamingCapability() {
        return (VideoStreamingCapability) getObject(VideoStreamingCapability.class, KEY_VIDEO_STREAMING_CAPABILITY);
    }
}
