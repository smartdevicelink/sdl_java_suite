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

import java.util.Hashtable;

/**
 * Used to delete a file resident on the SDL module in the app's local cache.
 * Not supported on first generation SDL vehicles
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>SmartDeviceLink 2.0</th>
 * 		</tr>
 * 		<tr>
 * 			<td>SDLFileName</td>
 * 			<td>String</td>
 * 			<td> File reference name.</td>
 *                 <td>Y</td>
 *                 <td>maxlength:500</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 *  
 * <p><b>Response </b></p>
 * 
 * <p><b>Non-default Result Codes:</b></p>
 * <p>	SUCCESS</p>
 * <p>	INVALID_DATA</p>
 * <p>	OUT_OF_MEMORY</p>
 * <p>	TOO_MANY_PENDING_REQUESTS</p>
 * <p>	APPLICATION_NOT_REGISTERED</p>
 * 	<p>GENERIC_ERROR</p>
 * 	<p>REJECTED</p> 
 * @since SmartDeviceLink 2.0
 * @see PutFile
 * @see ListFiles
 */
public class DeleteFile extends RPCRequest {
	public static final String KEY_SDL_FILE_NAME = "syncFileName";

	/**
	 * Constructs a new DeleteFile object
	 */
    public DeleteFile() {
        super(FunctionID.DELETE_FILE.toString());
    }

	/**
	 * <p>Constructs a new DeleteFile object indicated by the Hashtable parameter</p>
	 * @param hash The Hashtable to use
	 */
    public DeleteFile(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new DeleteFile object
	 * @param sdlFileName a String value representing a file reference name
	 */
	public DeleteFile(@NonNull String sdlFileName) {
		this();
		setSdlFileName(sdlFileName);
	}

	/**
	 * Sets a file reference name
	 * 
	 * @param sdlFileName
	 *            a String value representing a file reference name
	 */
    public void setSdlFileName(@NonNull String sdlFileName) {
		setParameters(KEY_SDL_FILE_NAME, sdlFileName);
    }

	/**
	 * Gets a file reference name
	 * 
	 * @return String -a String value representing a file reference name
	 */
    public String getSdlFileName() {
        return getString(KEY_SDL_FILE_NAME);
    }
}
