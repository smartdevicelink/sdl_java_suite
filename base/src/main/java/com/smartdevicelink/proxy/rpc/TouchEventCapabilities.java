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

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
/**
 * Types of screen touch events available in screen area.
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>SmartDeviceLink Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>pressAvailable</td>
 * 			<td>Boolean</td>
 * 			<td></td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *            <tr>
 * 			<td>multiTouchAvailable</td>
 * 			<td>Boolean</td>
 * 			<td></td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>doublePressAvailable</td>
 * 			<td>Boolean</td>
 * 			<td></td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table>
 *
 */
public class TouchEventCapabilities extends RPCStruct {
    public static final String KEY_PRESS_AVAILABLE = "pressAvailable";
    public static final String KEY_MULTI_TOUCH_AVAILABLE = "multiTouchAvailable";
    public static final String KEY_DOUBLE_PRESS_AVAILABLE = "doublePressAvailable";
    public TouchEventCapabilities() {}
    
    public TouchEventCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Types of screen touch events available in screen area.
	 * @param pressAvailable if press is available
	 * @param multiTouchAvailable if multi touch is available
	 * @param doublePressAvailable if double press is available
	 */
	public TouchEventCapabilities(@NonNull Boolean pressAvailable, @NonNull Boolean multiTouchAvailable, @NonNull Boolean doublePressAvailable){
		this();
		setPressAvailable(pressAvailable);
		setMultiTouchAvailable(multiTouchAvailable);
		setDoublePressAvailable(doublePressAvailable);
	}
    
    public void setPressAvailable(@NonNull Boolean pressAvailable) {
        setValue(KEY_PRESS_AVAILABLE, pressAvailable);
    }
    
    public Boolean getPressAvailable() {
        return getBoolean(KEY_PRESS_AVAILABLE);
    }
    
    public void setMultiTouchAvailable(@NonNull Boolean multiTouchAvailable) {
        setValue(KEY_MULTI_TOUCH_AVAILABLE, multiTouchAvailable);
    }
    
    public Boolean getMultiTouchAvailable() {
        return getBoolean(KEY_MULTI_TOUCH_AVAILABLE);
    }
    
    public void setDoublePressAvailable(@NonNull Boolean doublePressAvailable) {
        setValue(KEY_DOUBLE_PRESS_AVAILABLE, doublePressAvailable);
    }
    
    public Boolean getDoublePressAvailable() {
        return getBoolean(KEY_DOUBLE_PRESS_AVAILABLE);
    }
}
