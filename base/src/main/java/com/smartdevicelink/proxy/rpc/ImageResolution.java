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
/** The image resolution of this field.
 * 
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
 * 		<tr>
 * 			<td>resolutionWidth</td>
 * 			<td>Integer</td>
 * 			<td>The image resolution width.</td>
 *                 <td></td>
 *                 <td>minvalue:1; maxvalue: 10000</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>resolutionHeight</td>
 * 			<td>Integer</td>
 * 			<td>The image resolution height.</td>
 *                 <td></td>
 *                 <td>minvalue:1;  maxvalue: 10000</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		
 *  </table>
 * @since SmartDeviceLink 3.0
 *  
 */

public class ImageResolution extends RPCStruct {
	public static final String KEY_RESOLUTION_WIDTH = "resolutionWidth";
	public static final String KEY_RESOLUTION_HEIGHT = "resolutionHeight";
	
    public ImageResolution() {}
    /**
	* <p>
	* Constructs a new ImageResolution object indicated by the Hashtable
	* parameter
	* </p>
	* 
	* @param hash
	*            The Hashtable to use
	*/    

    public ImageResolution(Hashtable<String, Object> hash) {
        super(hash);
    }

    public ImageResolution(@NonNull Integer resolutionWidth, @NonNull Integer resolutionHeight) {
        this();
        setResolutionWidth(resolutionWidth);
        setResolutionHeight(resolutionHeight);
    }

    /**
     * @param resolutionWidth the desired resolution width. Odds values cause problems in
     *                        the Android H264 decoder, as a workaround the odd value is
     *                        converted to a pair value.
     */
    public void setResolutionWidth(@NonNull Integer resolutionWidth) {
        if(resolutionWidth != null && resolutionWidth % 2 != 0) {
            resolutionWidth++;
        }
        setValue(KEY_RESOLUTION_WIDTH, resolutionWidth);
    }
    
    public Integer getResolutionWidth() {
        return getInteger(KEY_RESOLUTION_WIDTH);
    }

    /**
     * @param resolutionHeight the desired resolution height. Odds values cause problems in
     *                        the Android H264 decoder, as a workaround the odd value is
     *                        converted to a pair value.
     */
    public void setResolutionHeight(@NonNull Integer resolutionHeight) {
        if(resolutionHeight != null && resolutionHeight % 2 != 0) {
            resolutionHeight++;
        }
        setValue(KEY_RESOLUTION_HEIGHT, resolutionHeight);
    }
    
    public Integer getResolutionHeight() {
        return getInteger(KEY_RESOLUTION_HEIGHT);
    }

    @Override
    public String toString() {
        return "width=" + String.valueOf(getResolutionWidth()) +
               ", height=" + String.valueOf(getResolutionHeight());
    }
}
