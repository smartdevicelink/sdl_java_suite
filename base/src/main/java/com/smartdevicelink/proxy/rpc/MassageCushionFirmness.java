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
import com.smartdevicelink.proxy.rpc.enums.MassageCushion;

import java.util.Hashtable;

/**
 * The intensity or firmness of a cushion.
 */
public class MassageCushionFirmness extends RPCStruct {
    public static final String KEY_CUSHION = "cushion";
    public static final String KEY_FIRMNESS = "firmness";

    /**
     * Constructs a new MassageCushionFirmness object
     */
    public MassageCushionFirmness() {
    }

    /**
     * <p>Constructs a new MassageCushionFirmness object indicated by the Hashtable parameter
     * </p>
     *
     * @param hash The Hashtable to use to create this RPC
     */
    public MassageCushionFirmness(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated MassageCushionFirmness object
     *
     * @param cushion  type of MassageCushion for multi-contour massage seat
     * @param firmness Min: 0  Max: 100
     */
    public MassageCushionFirmness(@NonNull MassageCushion cushion, @NonNull Integer firmness) {
        this();
        setCushion(cushion);
        setFirmness(firmness);
    }

    /**
     * Sets the type of MassageCushion for multi-contour massage seat
     *
     * @param cushion type of MassageCushion for multi-contour massage seat
     */
    public MassageCushionFirmness setCushion(@NonNull MassageCushion cushion) {
        setValue(KEY_CUSHION, cushion);
        return this;
    }

    /**
     * Gets the type of MassageCushion for multi-contour massage seat
     *
     * @return the type of MassageCushion for multi-contour massage seat.
     */
    public MassageCushion getCushion() {
        return (MassageCushion) getObject(MassageCushion.class, KEY_CUSHION);
    }

    /**
     * Sets the firmness associated with the supplied MassageCushion
     *
     * @param firmness firmness of the supplied MassageCushion (Min: 0  Max: 100)
     */
    public MassageCushionFirmness setFirmness(@NonNull Integer firmness) {
        setValue(KEY_FIRMNESS, firmness);
        return this;
    }

    /**
     * Gets the firmness associated with the supplied MassageCushion
     *
     * @return firmness of the supplied MassageCushion (Min: 0  Max: 100)
     */
    public Integer getFirmness() {
        return getInteger(KEY_FIRMNESS);
    }
}
