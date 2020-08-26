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

import java.util.Hashtable;
/** The resolution of the prescribed screen area.
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>resolution</td>
 * 			<td>ImageResolution</td>
 * 			<td>The resolution of the prescribed screen area.</td>
 *                 <td>Y</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.3.2</td>
 * 		</tr>
 * 		<tr>
 * 			<td>touchEventAvailable</td>
 * 			<td>TouchEventCapabilities</td>
 * 			<td>Types of screen touch events available in screen area.</td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.3.2</td>
 * 		</tr>
 *
 *  </table>
 * @since SmartDeviceLink 2.3.2
 *
 */

public class ScreenParams extends RPCStruct {
    public static final String KEY_RESOLUTION = "resolution";
    public static final String KEY_TOUCH_EVENT_AVAILABLE = "touchEventAvailable";
	/**
	 * Constructs a new ScreenParams object
	 */  

	public ScreenParams() { }
	/**
	* <p>
	* Constructs a new ScreenParamst object indicated by the Hashtable
	* parameter
	* </p>
	* 
	* @param hash
	*            The Hashtable to use
	*/  

    public ScreenParams(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new ScreenParams object
     */

    public ScreenParams(@NonNull ImageResolution resolution) {
        this();
        setImageResolution(resolution);
    }

    @SuppressWarnings("unchecked")
    public ImageResolution getImageResolution() {
        return (ImageResolution) getObject(ImageResolution.class, KEY_RESOLUTION);
    } 
    public void setImageResolution( @NonNull ImageResolution resolution ) {
        setValue(KEY_RESOLUTION, resolution);
    }
    @SuppressWarnings("unchecked")
    public TouchEventCapabilities getTouchEventAvailable() {
    	return (TouchEventCapabilities) getObject(TouchEventCapabilities.class, KEY_TOUCH_EVENT_AVAILABLE);
    } 
    public void setTouchEventAvailable( TouchEventCapabilities touchEventAvailable ) {
    	setValue(KEY_TOUCH_EVENT_AVAILABLE, touchEventAvailable);
    }     
}
