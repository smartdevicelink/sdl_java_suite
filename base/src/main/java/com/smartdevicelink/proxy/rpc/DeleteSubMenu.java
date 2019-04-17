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
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

/**
 * Deletes a submenu from the Command Menu.
 * 
 * <p><b>Notes: </b>When an app deletes a submenu that has child commands, those
 * child commands are also deleted</p>
 * 
 * <p><b>HMILevel needs to be  FULL, LIMITED or BACKGROUND</b></p>
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
 * 			<th>Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>menuID</td>
 * 			<td>Integer</td>
 * 			<td>Unique ID that identifies the SubMenu to be delete</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 0; Max Value: 2000000000</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 *
 *<p> <b>Response </b></p>
 * 
 * <p><b>Non-default Result Codes:</b></p>
 *<p>SUCCESS</p>
 *<p> 	INVALID_DATA</p>
 * <p>	OUT_OF_MEMORY</p>
 * <p>	TOO_MANY_PENDING_REQUESTS</p>
 * <p>	APPLICATION_NOT_REGISTERED</p>
 * <p>	GENERIC_ERROR</p>
 * <p>	REJECTED</p>
 * <p> INVALID_ID</p>
 * <p> IN_USE   </p>   
 * 
 * @since SmartDeviceLink 1.0
 * @see AddCommand
 * @see AddSubMenu
 * @see DeleteCommand
 */
public class DeleteSubMenu extends RPCRequest {
	public static final String KEY_MENU_ID = "menuID";
	/**
	* Constructs a new DeleteSubMenu object
	*/
	public DeleteSubMenu() {
        super(FunctionID.DELETE_SUB_MENU.toString());
    }
    /**
     * Constructs a new DeleteSubMenu object indicated by the Hashtable parameter    
     * @param hash The Hashtable to use
     */
    public DeleteSubMenu(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a new DeleteSubMenu object
     * @param menuID an Integer value representing menuID that identifies the SubMenu to be delete
     *
     * <p><b>Notes: </b>Min Value: 0; Max Value: 2000000000</p>
     */
    public DeleteSubMenu(@NonNull Integer menuID) {
        this();
        setMenuID(menuID);
    }
    /**
     * Gets the Menu ID that identifies the SubMenu to be delete
     * @return Integer -an Integer value representing menuID that identifies the SubMenu to be delete
     */    
    public Integer getMenuID() {
        return getInteger( KEY_MENU_ID );
    }
    /**
     * Sets the MenuID that identifies the SubMenu to be delete  
     * @param menuID an Integer value representing menuID that identifies the SubMenu to be delete
     * 
     * <p><b>Notes: </b>Min Value: 0; Max Value: 2000000000</p>
     */    
    public void setMenuID( @NonNull Integer menuID ) {
        setParameters(KEY_MENU_ID, menuID);
    }
}
