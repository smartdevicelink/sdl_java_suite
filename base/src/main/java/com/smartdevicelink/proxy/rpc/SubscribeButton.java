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
 *<p> Establishes a subscription to button notifications for HMI buttons. Buttons
 * are not necessarily physical buttons, but can also be "soft" buttons on a
 * touch screen, depending on the display in the vehicle. Once subscribed to a
 * particular button, an application will receive both
 * {@linkplain OnButtonEvent} and {@linkplain OnButtonPress} notifications
 * whenever that button is pressed. The application may also unsubscribe from
 * notifications for a button by invoking the {@linkplain UnsubscribeButton}
 * operation</p>
 * 
 * <p>When a button is depressed, an {@linkplain OnButtonEvent} notification is
 * sent to the application with a ButtonEventMode of BUTTONDOWN. When that same
 * button is released, an {@linkplain OnButtonEvent} notification is sent to the
 * application with a ButtonEventMode of BUTTONUP</p>
 * 
 * <p>When the duration of a button depression (that is, time between depression
 * and release) is less than two seconds, an {@linkplain OnButtonPress}
 * notification is sent to the application (at the moment the button is
 * released) with a ButtonPressMode of SHORT. When the duration is two or more
 * seconds, an {@linkplain OnButtonPress} notification is sent to the
 * application (at the moment the two seconds have elapsed) with a
 * ButtonPressMode of LONG</p>
 * 
 * The purpose of {@linkplain OnButtonPress} notifications is to allow for
 * programmatic detection of long button presses similar to those used to store
 * presets while listening to the radio, for example
 * 
 * <p>When a button is depressed and released, the sequence in which notifications
 * will be sent to the application is as follows:</p>
 * 
 * <p>For short presses:</p>
 * <ul>
 * <li>OnButtonEvent (ButtonEventMode = BUTTONDOWN)</li>
 * <li>OnButtonEvent (ButtonEventMode = BUTTONUP)</li>
 * <li>OnButtonPress (ButtonPressMode = SHORT)</li>
 * </ul>
 * 
 * <p>For long presses:</p>
 * <ul>
 * <li>OnButtonEvent (ButtonEventMode = BUTTONDOWN)</li>
 * <li>OnButtonEvent (ButtonEventMode = BUTTONUP)</li>
 * <li>OnButtonPress (ButtonPressMode = LONG)</li>
 * </ul>
 * 
 *<p> <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 * 
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
 * 			<td>buttonName</td>
 * 			<td>ButtonName</td>
 * 			<td>Name of the button to subscribe.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 1.0 </td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 1.0
 * @see UnsubscribeButton
 */
public class SubscribeButton extends RPCRequest {
	public static final String KEY_BUTTON_NAME = "buttonName";

	/**
	 * Constructs a new SubscribeButton object
	 */
    public SubscribeButton() {
        super(FunctionID.SUBSCRIBE_BUTTON.toString());
    }
	/**
	 * <p>Constructs a new SubscribeButton object indicated by the Hashtable
	 * parameter</p>
	 * 
	 * 
	 * @param hash The Hashtable to use
	 */    
    public SubscribeButton(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new SubscribeButton object
	 * @param buttonName Name of the button to subscribe.
	 */
	public SubscribeButton(@NonNull ButtonName buttonName){
		this();
		setButtonName(buttonName);
	}
	/**
	 * Gets the name of the button to subscribe to
	 * @return ButtonName -an enum value, see <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonName}</i>
	 */    
    public ButtonName getButtonName() {
		return (ButtonName) getObject(ButtonName.class, KEY_BUTTON_NAME);
    }
	/**
	 * Sets a name of the button to subscribe to
	 * @param buttonName a <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonName}</i> value
	 */    
    public void setButtonName(@NonNull ButtonName buttonName ) {
        setParameters(KEY_BUTTON_NAME, buttonName);
    }
}
