/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium Inc. nor the names of
 * its contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
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

import java.util.Hashtable;

/**
 * This notification tells an app to update the AddSubMenu or its 'sub' AddCommand and
 * AddSubMenus with the requested data
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>menuID</td>
 *      <td>Integer</td>
 *      <td>This menuID must match a menuID in the current menu structure</td>
 *      <td>Y</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>updateSubCells</td>
 *      <td>Boolean</td>
 *      <td>If not set, assume false. If true, the app should send AddCommands with parentIDs matching the menuID. These AddCommands will then be attached to the submenu and displayed if the submenu is selected.</td>
 *      <td>N</td>
 *      <td></td>
 *  </tr>
 * </table>
 *
 * @since SmartDeviceLink 7.0.0
 */
public class OnUpdateSubMenu extends RPCNotification {
    public static final String KEY_MENU_ID = "menuID";
    public static final String KEY_UPDATE_SUB_CELLS = "updateSubCells";

    /**
     * Constructs a new OnUpdateSubMenu object
     */
    public OnUpdateSubMenu() {
        super(FunctionID.ON_UPDATE_SUB_MENU.toString());
    }

    /**
     * Constructs a new OnUpdateSubMenu object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public OnUpdateSubMenu(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new OnUpdateSubMenu object
     *
     * @param menuID This menuID must match a menuID in the current menu structure
     */
    public OnUpdateSubMenu(@NonNull Integer menuID) {
        this();
        setMenuID(menuID);
    }

    /**
     * Sets the menuID.
     *
     * @param menuID This menuID must match a menuID in the current menu structure
     */
    public OnUpdateSubMenu setMenuID(@NonNull Integer menuID) {
        setParameters(KEY_MENU_ID, menuID);
        return this;
    }

    /**
     * Gets the menuID.
     *
     * @return Integer This menuID must match a menuID in the current menu structure
     */
    public Integer getMenuID() {
        return getInteger(KEY_MENU_ID);
    }

    /**
     * Sets the updateSubCells.
     *
     * @param updateSubCells If not set, assume false. If true, the app should send AddCommands with parentIDs matching
     * the menuID. These AddCommands will then be attached to the submenu and displayed if the
     * submenu is selected.
     */
    public OnUpdateSubMenu setUpdateSubCells( Boolean updateSubCells) {
        setParameters(KEY_UPDATE_SUB_CELLS, updateSubCells);
        return this;
    }

    /**
     * Gets the updateSubCells.
     *
     * @return Boolean If not set, assume false. If true, the app should send AddCommands with parentIDs matching
     * the menuID. These AddCommands will then be attached to the submenu and displayed if the
     * submenu is selected.
     */
    public Boolean getUpdateSubCells() {
        return getBoolean(KEY_UPDATE_SUB_CELLS);
    }
}
