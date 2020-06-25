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
import com.smartdevicelink.transport.enums.TransportType;

class BaseTransportRecord{

    protected TransportType type;
    protected String address;

    BaseTransportRecord(TransportType transportType, String address){
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

    BaseTransportRecord(Parcel p) {
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
}
