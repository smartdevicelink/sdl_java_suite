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
package com.smartdevicelink.transport.utl;

import android.os.Parcel;
import android.os.Parcelable;
import com.smartdevicelink.transport.enums.TransportType;

public class TransportRecord extends BaseTransportRecord {
    private TransportType type;
    private String address;

    public TransportRecord(TransportType transportType, String address) {
        super(transportType, address);
        this.type = transportType;
        this.address = address;
    }

    @Deprecated
    public TransportRecord(Parcel p) {
        super(null, "");
    }

    @Deprecated
    public int describeContents() {
        return 0;
    };

    @Deprecated
    public void writeToParcel(Parcel dest, int flags) {}

    @Deprecated
    public static final Parcelable.Creator<TransportRecord> CREATOR = new Parcelable.Creator<TransportRecord>() {
        public TransportRecord createFromParcel(Parcel in) {
            return new TransportRecord(in);
        }

        @Override
        public TransportRecord[] newArray(int size) {
            return new TransportRecord[size];
        }

    };
}
