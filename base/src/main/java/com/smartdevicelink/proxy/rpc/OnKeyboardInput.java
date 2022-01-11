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
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;

import java.util.Hashtable;

/**
 * On-screen keyboard event. Can be full string or individual key presses depending on keyboard mode.
 * <p></p>
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>event</td>
 * 			<td>KeyboardEvent</td>
 * 			<td>On-screen keyboard input data.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>data</td>
 * 			<td>String</td>
 * 			<td>On-screen keyboard input data.For dynamic keypress events, this will be the current compounded string of entry text.For entry cancelled and entry aborted events, this data param will be omitted.</td>
 *                 <td></td>
 *                 <td>Maxlength: 500</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *
 *  </table>
 */

public class OnKeyboardInput extends RPCNotification {
    public static final String KEY_DATA = "data";
    public static final String KEY_EVENT = "event";

    /**
     * Constructs a new OnKeyboardInput object
     */
    public OnKeyboardInput() {
        super(FunctionID.ON_KEYBOARD_INPUT.toString());
    }

    /**
     * <p>
     * Constructs a new OnKeyboardInput object indicated by the Hashtable
     * parameter
     * </p>
     *
     * @param hash The Hashtable to use
     */

    public OnKeyboardInput(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new OnKeyboardInput object
     */
    public OnKeyboardInput(@NonNull KeyboardEvent event) {
        this();
        setEvent(event);
    }

    public KeyboardEvent getEvent() {
        return (KeyboardEvent) getObject(KeyboardEvent.class, KEY_EVENT);
    }

    public OnKeyboardInput setEvent(@NonNull KeyboardEvent event) {
        setParameters(KEY_EVENT, event);
        return this;
    }

    public OnKeyboardInput setData(String data) {
        setParameters(KEY_DATA, data);
        return this;
    }

    public String getData() {
        Object obj = getParameters(KEY_DATA);
        if (obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    @Override
    public String toString() {
        return this.getFunctionName() + ": " + " data: " + this.getData() + " event:" + this.getEvent().toString();
    }

}
