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
 * <p>The x or y coordinate of the touch.</p>
 * 
 * 
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th> Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>x</td>
 * 			<td>Integer</td>
 * 			<td>The x coordinate of the touch.</td>
 *                 <td>Y</td>
 *                 <td>minvalue = 0; maxvalue = 10000</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>y</td>
 * 			<td>Integer</td>
 * 			<td>The y coordinate of the touch.</td>
 *                 <td>Y</td>
 *                 <td>minvalue = 0; maxvalue = 10000</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table>
 *@see SoftButtonCapabilities
 *@see ButtonCapabilities
 *@see OnButtonPress
 *
 *@since SmartDeviceLink 3.0
 */

public class TouchCoord extends RPCStruct {
    public static final String KEY_X = "x";
    public static final String KEY_Y = "y";

    public TouchCoord() {}

	 /**
		 * <p>Constructs a new TouchCoord object indicated by the Hashtable parameter</p>
		 *
		 * @param hash The Hashtable to use
		 */
    public TouchCoord(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new TouchCoord object
	 * @param x The x coordinate of the touch.
	 * @param y The y coordinate of the touch.
	 */
	public TouchCoord(@NonNull Integer x, @NonNull Integer y){
		this();
		setX(x);
		setY(y);
	}
    
    public void setX(@NonNull Integer x) {
        setValue(KEY_X, x);
    }
    
    public Integer getX() {
        return getInteger(KEY_X);
    }
    
    public void setY(@NonNull Integer y) {
        setValue(KEY_Y, y);
    }
    
    public Integer getY() {
        return getInteger(KEY_Y);
    }
    
}
