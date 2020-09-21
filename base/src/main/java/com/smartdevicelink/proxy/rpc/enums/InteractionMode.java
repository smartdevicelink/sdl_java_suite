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
 * For application-initiated interactions (<i>{@linkplain com.smartdevicelink.proxy.rpc.PerformInteraction}</i>), this specifies
 * the mode by which the user is prompted and by which the user's selection is
 * indicated
 *
 * @since SmartDeviceLink 1.0
 */
public enum InteractionMode {
    /**
     * This mode causes the interaction to occur only on the display, meaning
     * the choices are presented and selected only via the display. Selections
     * are viewed with the SEEKRIGHT, SEEKLEFT, TUNEUP, TUNEDOWN buttons. User's
     * selection is indicated with the OK button
     */
    MANUAL_ONLY,
    /**
     * This mode causes the interaction to occur only through TTS and VR. The
     * user is prompted via TTS to select a choice by saying one of the choice's
     * synonyms
     */
    VR_ONLY,
    /**
     * This mode is a combination of MANUAL_ONLY and VR_ONLY, meaning the user
     * is prompted both visually and audibly. The user can make a selection
     * either using the mode described in MANUAL_ONLY or using the mode
     * described in VR_ONLY. If the user views selections as described in
     * MANUAL_ONLY mode, the interaction becomes strictly, and irreversibly, a
     * MANUAL_ONLY interaction (i.e. the VR session is cancelled, although the
     * interaction itself is still in progress). If the user interacts with the
     * VR session in any way (e.g. speaks a phrase, even if it is not a
     * recognized choice), the interaction becomes strictly, and irreversibly, a
     * VR_ONLY interaction (i.e. the MANUAL_ONLY mode forms of interaction will
     * no longer be honored)
     *
     * <p>The TriggerSource parameter of the
     * {@linkplain com.smartdevicelink.proxy.rpc.PerformInteraction} response will
     * indicate which interaction mode the user finally chose to attempt the
     * selection (even if the interaction did not end with a selection being
     * made)</P>
     */
    BOTH;

    /**
     * Returns InteractionMode (MANUAL_ONLY, VR_ONLY or BOTH)
     *
     * @param value a String
     * @return InteractionMode -MANUAL_ONLY, VR_ONLY or BOTH
     */

    public static InteractionMode valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}
