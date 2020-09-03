/*
 * Copyright (c)  2019 Livio, Inc.
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
 *
 * Created by Nicole Yarroch on 7/17/19 8:40 AM
 */

package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

/*
 *  Used to dismiss a modal view programmatically without needing to wait for the timeout to complete. Can be used to dismiss alerts, scrollable messages, sliders, and perform interactions (i.e. pop-up menus).
 *
 *  @see Alert, ScrollableMessage, Slider, PerformInteraction
 */
public class CancelInteraction extends RPCRequest {
    public static final String KEY_CANCEL_ID = "cancelID";
    public static final String KEY_FUNCTION_ID = "functionID";

    // Constructors

    /**
     * Constructs a new CancelInteraction object
     */
    public CancelInteraction() {
        super(FunctionID.CANCEL_INTERACTION.toString());
    }

    /**
     * Constructs a new CancelInteraction object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public CancelInteraction(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Convenience init for dismissing an interaction type.
     * @param functionID - The ID of the type of interaction to dismiss
     */
    public CancelInteraction(@NonNull Integer functionID) {
        this();
        setInteractionFunctionID(functionID);
    }

    /**
     * Convenience init for dismissing a specific interaction.
     * @param functionID - The ID of the type of interaction to dismiss
     * @param cancelID - The ID of the specific interaction to dismiss
     */
    public CancelInteraction(@NonNull Integer functionID, Integer cancelID) {
        this();
        setInteractionFunctionID(functionID);
        setCancelID(cancelID);
    }

    // Custom Getters / Setters

    /**
     * Gets the ID of the type of interaction the developer wants to dismiss.
     * Only values 10 (PerformInteractionID), 12 (AlertID), 25 (ScrollableMessageID), 26 (SliderID), and 64 (SubtleAlertID) are permitted.
     * @return - the functionID
     */
    public Integer getInteractionFunctionID() {
        return getInteger(KEY_FUNCTION_ID);
    }

    /**
     * Sets the ID of the type of interaction the developer wants to dismiss.
     * Only values 10 (PerformInteractionID), 12 (AlertID), 25 (ScrollableMessageID), 26 (SliderID), and 64 (SubtleAlertID) are permitted.
     * @param functionID - the functionID
     */
    public CancelInteraction setInteractionFunctionID(@NonNull Integer functionID) {
        setParameters(KEY_FUNCTION_ID, functionID);
        return this;
    }

    /**
     * The ID of the specific interaction to dismiss. If not set, the most recent of the RPC type set in functionID will be dismissed.
     * @return - the cancelID
     */
    public Integer getCancelID() {
        return getInteger(KEY_CANCEL_ID);
    }

    /**
     * The ID of the specific interaction to dismiss. If not set, the most recent of the RPC type set in functionID will be dismissed.
     * @param cancelID - the cancelID
     */
    public CancelInteraction setCancelID( Integer cancelID) {
        setParameters(KEY_CANCEL_ID, cancelID);
        return this;
    }
}