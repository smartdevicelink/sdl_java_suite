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
import com.smartdevicelink.proxy.rpc.enums.TouchType;

import java.util.Hashtable;
import java.util.List;

/**
 * 
 * Notifies about touch events on the screen's prescribed area.
 * <p><b>Parameter List</b></p>
 * 
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>Version</th>
 * 		</tr>
 *            <tr>
 * 			<td>type</td>
 * 			<td>TouchType</td>
 * 			<td>The type of touch event.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>event</td>
 * 			<td>TouchEvent</td>
 * 			<td>List of all individual touches involved in this event.</td>
 *                 <td>Y</td>
 *                 <td>minsize:1; maxsize:10</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table> 
 *  
 * <p><b>Note:</b></p>
 * <p>SDL needs to be informed about every User`s touching the touch screen.</p>
 * 
 */
public class OnTouchEvent extends RPCNotification {
	public static final String KEY_EVENT = "event";
	public static final String KEY_TYPE = "type";
	/**
	 * Constructs a new OnTouchEvent object
	 */

	public OnTouchEvent() {
		super(FunctionID.ON_TOUCH_EVENT.toString());
	}
	/**
	* <p>
	* Constructs a new OnTouchEvent object indicated by the Hashtable
	* parameter
	* </p>
	* 
	* @param hash
	*            The Hashtable to use
	*/  

    public OnTouchEvent(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new OnTouchEvent object
	 */
	public OnTouchEvent(@NonNull TouchType type, @NonNull List<TouchEvent> event) {
		this();
		setType(type);
		setEvent(event);
	}
    
    public OnTouchEvent setType(@NonNull TouchType type) {
        setParameters(KEY_TYPE, type);
        return this;
    }
    
    public TouchType getType() {
		return (TouchType) getObject(TouchType.class, KEY_TYPE);
    }
    
    public OnTouchEvent setEvent(@NonNull List<TouchEvent> event) {
        setParameters(KEY_EVENT, event);
        return this;
    }
    
    @SuppressWarnings("unchecked")
    public List<TouchEvent> getEvent() {
		return (List<TouchEvent>) getObject(TouchEvent.class, KEY_EVENT);
    }
}
