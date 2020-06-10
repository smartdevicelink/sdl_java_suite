/*
 * Copyright (c) 2018 Livio, Inc.
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
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
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

public class BaseTransportRecord implements Parcelable{

    private TransportType type;
    private String address;

    public BaseTransportRecord(TransportType transportType, String address){
        this.type = transportType;
        this.address = address;
    }

    public TransportType getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if (obj instanceof BaseTransportRecord) {
            BaseTransportRecord record = (BaseTransportRecord) obj;
            return record.type != null && record.type.equals(type)  //Transport type is the same
                    && ((record.address == null && address == null) //Both addresses are null
                    || (record.address != null && record.address.equals(address))); //Or they match
        }

        return super.equals(obj);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Transport Type: ");
        builder.append(type.name());
        builder.append(" Address: ");
        builder.append(address);
        return builder.toString();
    }

    public BaseTransportRecord(Parcel p){
        if (p.readInt() == 1) { //We should have a transport type attached
            String transportName = p.readString();
            if(transportName != null){
                this.type = TransportType.valueOf(transportName);
            }
        }

        if (p.readInt() == 1) { //We should have a transport address attached
            address = p.readString();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type!=null? 1 : 0);
        if(type != null){
            dest.writeString(type.name());
        }

        dest.writeInt(address !=null? 1 : 0);
        if(address != null){
            dest.writeString(address);
        }
    }

    public static final Parcelable.Creator<BaseTransportRecord> CREATOR = new Parcelable.Creator<BaseTransportRecord>() {
        public BaseTransportRecord createFromParcel(Parcel in) {
            return new BaseTransportRecord(in);
        }

        @Override
        public BaseTransportRecord[] newArray(int size) {
            return new BaseTransportRecord[size];
        }

    };
}
