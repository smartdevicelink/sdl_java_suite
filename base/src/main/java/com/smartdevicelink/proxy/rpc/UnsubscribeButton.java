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

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;

import java.util.Hashtable;

/**
 * Deletes a subscription to button notifications for the specified button. For
 * more information about button subscriptions, see {@linkplain SubscribeButton}
 *
 * <p>Application can unsubscribe from a button that is currently being pressed
 * (i.e. has not yet been released), but app will not get button event</p>
 *
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 *
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 *         <tr>
 *             <th>Param Name</th>
 *             <th>Type</th>
 *             <th>Description</th>
 *                 <th> Req.</th>
 *             <th>Notes</th>
 *             <th>Version Available</th>
 *         </tr>
 *         <tr>
 *             <td>buttonName</td>
 *             <td>ButtonName</td>
 *             <td>Name of the button to unsubscribe.</td>
 *                 <td>Y</td>
 *             <td></td>
 *             <td>SmartDeviceLink 1.0</td>
 *         </tr>
 *  </table>
 *
 * <p> <b>Response</b></p>
 * <p><b>Non-default Result Codes:</b></p>
 *  <p>SUCCESS</p>
 *  <p>INVALID_DATA</p>
 *  <p>OUT_OF_MEMORY</p>
 *  <p>TOO_MANY_PENDING_REQUESTS</p>
 *  <p>APPLICATION_NOT_REGISTERED</p>
 *  <p>GENERIC_ERROR </p>
 *  <p>UNSUPPORTED_RESOURCE</p>
 *  <p>IGNORED</p>
 *  <p>REJECTED</p>
 *
 * @see SubscribeButton
 * @since SmartDeviceLink 1.0
 */
public class UnsubscribeButton extends RPCRequest {
    public static final String KEY_BUTTON_NAME = "buttonName";

    /**
     * Constructs a new UnsubscribeButton object
     */
    public UnsubscribeButton() {
        super(FunctionID.UNSUBSCRIBE_BUTTON.toString());
    }

    /**
     * Constructs a new UnsubscribeButton object indicated by the Hashtable
     * parameter
     * <p></p>
     *
     * @param hash The Hashtable to use
     */
    public UnsubscribeButton(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new UnsubscribeButton object
     *
     * @param buttonName Name of the button to unsubscribe.
     */
    public UnsubscribeButton(@NonNull ButtonName buttonName) {
        this();
        setButtonName(buttonName);
    }

    /**
     * Gets a name of the button to unsubscribe from
     *
     * @return ButtonName -an Enumeration value, see <i> {@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonName}</i>
     */
    public ButtonName getButtonName() {
        return (ButtonName) getObject(ButtonName.class, KEY_BUTTON_NAME);
    }

    /**
     * Sets the name of the button to unsubscribe from
     *
     * @param buttonName an enum value, see <i> {@linkplain ButtonName}</i>
     */
    public UnsubscribeButton setButtonName(@NonNull ButtonName buttonName) {
        setParameters(KEY_BUTTON_NAME, buttonName);
        return this;
    }
}
