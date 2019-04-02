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

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

import java.util.Hashtable;

/**
 * 	Notification containing an updated hashID which can be used over connection cycles (i.e. loss of connection, ignition cycles, etc.).
 * Sent after initial registration and subsequently after any change in the calculated hash of all persisted app data.
 * <p></p>
 *  <p><b>Parameter List</b></p>
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
 * 			<td>hashID</td>
 * 			<td>String</td>
 * 			<td>Calculated hash ID to be referenced during RegisterAppInterface.</td>
 *                 <td>Y</td>
 *                 <td>maxlength: 100</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 3.0
 *
 */
public class OnHashChange extends RPCNotification {
	public static final String KEY_HASH_ID = "hashID";
	/**
	 * Constructs a new OnHashChange object
	 */

    public OnHashChange() {
        super(FunctionID.ON_HASH_CHANGE.toString());
    }
    /**
	* <p>
	* Constructs a new OnKeyboardInput object indicated by the Hashtable
	* parameter
	* </p>
	* 
	* @param hash
	*            The Hashtable to use
	*/

    public OnHashChange(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new OnHashChange object
     */
    public OnHashChange(@NonNull String hashID) {
        this();
        setHashID(hashID);
    }
    
    public String getHashID() {
        return getString(KEY_HASH_ID);
    }
   
    public void setHashID(@NonNull String hashID) {
        setParameters(KEY_HASH_ID, hashID);
    }   
    
}
