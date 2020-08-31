/*
 * Copyright (c)  2019 Livio, Inc.
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
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
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
 *
 * Created by brettywhite on 7/16/19 1:44 PM
 *
 */

package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

public class ShowAppMenu extends RPCRequest {

	public static final String KEY_MENU_ID = "menuID";

	/**
	 * Constructs a new ShowAppMenu object
	 */
	public ShowAppMenu() {
		super(FunctionID.SHOW_APP_MENU.toString());
	}

	/**
	 * Constructs a new ShowAppMenu object indicated by the Hashtable parameter
	 *
	 * @param hash The Hashtable to use
	 */
	public ShowAppMenu(Hashtable<String, Object> hash) {
		super(hash);
	}

	// SETTERS AND GETTERS

	/**
	 * If omitted the HMI opens the apps menu.
	 * If set to a sub-menu ID the HMI opens the corresponding sub-menu
	 * previously added using `AddSubMenu`.
	 * @param menuID - The SubMenu ID to open
	 */
	public ShowAppMenu setMenuID( Integer menuID) {
        setParameters(KEY_MENU_ID, menuID);
        return this;
    }

	/**
	 * If omitted the HMI opens the apps menu.
	 * If set to a sub-menu ID the HMI opens the corresponding sub-menu
	 * previously added using `AddSubMenu`.
	 * @return - MenuID int
	 */
	public Integer getMenuID(){
		return getInteger(KEY_MENU_ID);
	}

}
