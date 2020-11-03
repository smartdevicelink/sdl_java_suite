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
package com.smartdevicelink.protocol.enums;

import com.smartdevicelink.util.ByteEnumer;

import java.util.Vector;

public class FrameDataControlFrameType extends ByteEnumer {
    private static final Vector<FrameDataControlFrameType> theList = new Vector<>();

    public static Vector<FrameDataControlFrameType> getList() {
        return theList;
    }

    private byte _i = 0x00;

    protected FrameDataControlFrameType(byte value, String name) {
        super(value, name);
    }

    public final static FrameDataControlFrameType Heartbeat = new FrameDataControlFrameType((byte) 0x0, "Heartbeat");
    public final static FrameDataControlFrameType StartSession = new FrameDataControlFrameType((byte) 0x01, "StartSession");
    public final static FrameDataControlFrameType StartSessionACK = new FrameDataControlFrameType((byte) 0x02, "StartSessionACK");
    public final static FrameDataControlFrameType StartSessionNACK = new FrameDataControlFrameType((byte) 0x03, "StartSessionNACK");
    public final static FrameDataControlFrameType EndSession = new FrameDataControlFrameType((byte) 0x04, "EndSession");
    public final static FrameDataControlFrameType EndSessionACK = new FrameDataControlFrameType((byte) 0x05, "EndSessionACK");
    public final static FrameDataControlFrameType EndSessionNACK = new FrameDataControlFrameType((byte) 0x06, "EndSessionNACK");
    public final static FrameDataControlFrameType RegisterSecondaryTransport = new FrameDataControlFrameType((byte) 0x07, "RegisterSecondaryTransport");
    public final static FrameDataControlFrameType RegisterSecondaryTransportACK = new FrameDataControlFrameType((byte) 0x08, "RegisterSecondaryTransportACK");
    public final static FrameDataControlFrameType RegisterSecondaryTransportNACK = new FrameDataControlFrameType((byte) 0x09, "RegisterSecondaryTransportNACK");
    public final static FrameDataControlFrameType TransportEventUpdate = new FrameDataControlFrameType((byte) 0xFD, "TransportEventUpdate");
    public final static FrameDataControlFrameType ServiceDataACK = new FrameDataControlFrameType((byte) 0xFE, "ServiceDataACK");
    public final static FrameDataControlFrameType HeartbeatACK = new FrameDataControlFrameType((byte) 0xFF, "HeartbeatACK");

    static {
        theList.addElement(Heartbeat);
        theList.addElement(StartSession);
        theList.addElement(StartSessionACK);
        theList.addElement(StartSessionNACK);
        theList.addElement(EndSession);
        theList.addElement(EndSessionACK);
        theList.addElement(EndSessionNACK);
        theList.addElement(RegisterSecondaryTransport);
        theList.addElement(RegisterSecondaryTransportACK);
        theList.addElement(RegisterSecondaryTransportNACK);
        theList.addElement(TransportEventUpdate);
        theList.addElement(ServiceDataACK);
        theList.addElement(HeartbeatACK);
    }

    public static FrameDataControlFrameType valueOf(String passedButton) {
        return (FrameDataControlFrameType) get(theList, passedButton);
    }

    public static FrameDataControlFrameType[] values() {
        return theList.toArray(new FrameDataControlFrameType[theList.size()]);
    }

}
