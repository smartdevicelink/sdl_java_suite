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
 * Removes a command from the Command Menu.
 * 
 * <p><b>HMI Status Requirements:</b></p>
 * <p>HMILevel: FULL, LIMITED or BACKGROUND</p>
 * AudioStreamingState: N/A
 * <p>SystemContext: Should not be attempted when VRSESSION or MENU</p>
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
 * 			<td>cmdID</td>
 * 			<td>Integer</td>
 * 			<td>Unique ID that identifies the Command to be deleted from Command Menu</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 0;Max Value: 2000000000</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 *   
 *<p><b> Response:</b></p>
 *
 * Indicates that the corresponding request either failed or succeeded. If the response returns with a SUCCESS result code,this means a command was removed from the Command Menu successfully. 
 * 
 *<p><b> Non-default Result Codes:</b></p>
 * 	<p>SUCCESS</p>
 * 	<p>INVALID_DATA</p>
 * 	<p>OUT_OF_MEMORY</p>
 * 	<p>TOO_MANY_PENDING_REQUESTS</p>
 * 	<p>APPLICATION_NOT_REGISTERED</p>
 * <p>	GENERIC_ERROR</p>
 * 	<p>REJECTED</p> 
 *  <p>  INVALID_ID</p>
 *   <p> IN_USER</p>  
 * @since SmartDeviceLink 1.0
 * @see AddCommand
 * @see AddSubMenu
 * @see DeleteSubMenu
 */
public class DeleteCommand extends RPCRequest {
	public static final String KEY_CMD_ID = "cmdID";

	/**
	 * Constructs a new DeleteCommand object
	 */
	public DeleteCommand() {
        super(FunctionID.DELETE_COMMAND.toString());
    }
	/**
	 * Constructs a new DeleteCommand object indicated by the Hashtable
	 * parameter
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */    
	public DeleteCommand(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Constructs a new DeleteCommand object
	 * @param cmdID: an Integer value representing Command ID
	 */
	public DeleteCommand(@NonNull Integer cmdID) {
		this();
		setCmdID(cmdID);
	}
	/**
	 * Gets the Command ID that identifies the Command to be deleted from
	 * Command Menu
	 * 
	 * @return Integer - Integer value representing Command ID that identifies
	 *         the Command to be deleted from Command Menu
	 */	
    public Integer getCmdID() {
        return getInteger( KEY_CMD_ID );
    }
	/**
	 * Sets the Command ID that identifies the Command to be deleted from Command Menu
	 *
	 * @param cmdID
	 *            an Integer value representing Command ID
	 *
	 *            <p><b>Notes: </b>Min Value: 0; Max Value: 2000000000</p>
	 */
    public DeleteCommand setCmdID(@NonNull Integer cmdID) {
        setParameters(KEY_CMD_ID, cmdID);
        return this;
    }
}
