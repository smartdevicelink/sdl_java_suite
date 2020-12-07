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
 * Enum for each type of video streaming protocol type.
 */

public enum VideoStreamingProtocol {
    /**
     * Raw stream bytes that contains no timestamp data and is the lowest supported video streaming
     */
    RAW,
    /**
     * RTP facilitates the transfer of real-time data. Information provided by this protocol include
     * timestamps (for synchronization), sequence numbers (for packet loss and reordering detection)
     * and the payload format which indicates the encoded format of the data.
     */
    RTP,
    /**
     * The transmission of streaming data itself is not a task of RTSP. Most RTSP servers use the
     * Real-time Transport Protocol (RTP) in conjunction with Real-time Control Protocol (RTCP) for
     * media stream delivery. However, some vendors implement proprietary transport protocols.
     */
    RTSP,
    /**
     * Real-Time Messaging Protocol (RTMP) was initially a proprietary protocol developed by
     * Macromedia for streaming audio, video and data over the Internet, between a Flash player and
     * a server. Macromedia is now owned by Adobe, which has released an incomplete version of the
     * specification of the protocol for public use.
     */
    RTMP,
    /**
     * The WebM container is based on a profile of Matroska. WebM initially supported VP8 video and
     * Vorbis audio streams. In 2013 it was updated to accommodate VP9 video and Opus audio.
     */
    WEBM;

    public static VideoStreamingProtocol valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}
