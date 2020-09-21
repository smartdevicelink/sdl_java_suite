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
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.MassageMode;
import com.smartdevicelink.proxy.rpc.enums.MassageZone;

import java.util.Hashtable;

/**
 * Specify the mode of a massage zone.
 */
public class MassageModeData extends RPCStruct {
    public static final String KEY_MASSAGE_ZONE = "massageZone";
    public static final String KEY_MASSAGE_MODE = "massageMode";

    /**
     * Constructs a new MassageModeData object
     */
    public MassageModeData() {
    }

    /**
     * <p>Constructs a new MassageModeData object indicated by the Hashtable parameter
     * </p>
     *
     * @param hash The Hashtable to use to create this RPC
     */
    public MassageModeData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated MassageModeData object
     *
     * @param massageZone type of MassageZone for multi-contour massage seat
     * @param massageMode mode of massage zone.
     */
    public MassageModeData(@NonNull MassageZone massageZone, @NonNull MassageMode massageMode) {
        this();
        setMassageZone(massageZone);
        setMassageMode(massageMode);
    }

    /**
     * Sets the massageZone that will be associated with the supplied massage mode
     *
     * @param massageZone the zone of a multi-contour massage seat
     */
    public MassageModeData setMassageZone(@NonNull MassageZone massageZone) {
        setValue(KEY_MASSAGE_ZONE, massageZone);
        return this;
    }

    /**
     * Gets the massageZone that will be associated with the supplied massage mode
     *
     * @return the zone of a multi-contour massage seat.
     */
    public MassageZone getMassageZone() {
        return (MassageZone) getObject(MassageZone.class, KEY_MASSAGE_ZONE);
    }

    /**
     * Gets the massageMode that will be associated with the supplied massage zone
     *
     * @return MassageMode
     */
    public MassageMode getMassageMode() {
        return (MassageMode) getObject(MassageMode.class, KEY_MASSAGE_MODE);
    }

    /**
     * Sets the massageMode that will be associated with the supplied massage zone
     *
     * @param massageMode mode of massage to be used (OFF, LOW, HIGH)
     */
    public MassageModeData setMassageMode(@NonNull MassageMode massageMode) {
        setValue(KEY_MASSAGE_MODE, massageMode);
        return this;
    }
}
