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
package com.smartdevicelink.protocol;

import android.os.Parcel;
import android.os.Parcelable;

public class SdlPacket extends BaseSdlPacket {

    public SdlPacket(int version, boolean encryption, int frameType,
                     int serviceType, int frameInfo, int sessionId,
                     int dataSize, int messageId, byte[] payload) {
        super(version, encryption, frameType, serviceType, frameInfo, sessionId, dataSize, messageId, payload);
    }

    public SdlPacket(int version, boolean encryption, int frameType,
                     int serviceType, int frameInfo, int sessionId,
                     int dataSize, int messageId, byte[] payload, int offset, int bytesToWrite) {
        super(version, encryption, frameType, serviceType, frameInfo, sessionId, dataSize, messageId, payload, offset, bytesToWrite);
    }

    protected  SdlPacket() {
        super();
    }

    protected SdlPacket(BaseSdlPacket packet) {
        super(packet);
    }

    @Deprecated
    public SdlPacket(Parcel p){}

    @Deprecated
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public void writeToParcel(Parcel dest, int flags) {}

    @Deprecated
    public static final Parcelable.Creator<SdlPacket> CREATOR = new Parcelable.Creator<SdlPacket>() {
        public SdlPacket createFromParcel(Parcel in) {
            return new SdlPacket(in);
        }

        @Override
        public SdlPacket[] newArray(int size) {
            return new SdlPacket[size];
        }

    };
}
