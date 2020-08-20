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

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
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
 *      <td>menuLength</td>
 *      <td>Integer</td>
 *      <td>The number of items allowed in a Choice Set or Command menu while the driver is distracted</td>
 *      <td>N</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>subMenuDepth</td>
 *      <td>Integer</td>
 *      <td>The depth of submenus allowed when the driver is distracted. e.g. 3 == top level menu ->submenu -> submenu; 1 == top level menu only</td>
 *      <td>N</td>
 *      <td></td>
 *  </tr>
 * </table>
 * @since SmartDeviceLink 7.0.0
 */
public class DriverDistractionCapability extends RPCStruct {
    public static final String KEY_MENU_LENGTH = "menuLength";
    public static final String KEY_SUB_MENU_DEPTH = "subMenuDepth";

    /**
     * Constructs a new DriverDistractionCapability object
     */
    public DriverDistractionCapability() { }

    /**
     * Constructs a new DriverDistractionCapability object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public DriverDistractionCapability(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the menuLength.
     *
     * @param menuLength The number of items allowed in a Choice Set or Command menu while the driver is distracted
     */
    public void setMenuLength(Integer menuLength) {
        setValue(KEY_MENU_LENGTH, menuLength);
    }

    /**
     * Gets the menuLength.
     *
     * @return Integer The number of items allowed in a Choice Set or Command menu while the driver is distracted
     */
    public Integer getMenuLength() {
        return getInteger(KEY_MENU_LENGTH);
    }

    /**
     * Sets the subMenuDepth.
     *
     * @param subMenuDepth The depth of submenus allowed when the driver is distracted. e.g. 3 == top level menu ->
     * submenu -> submenu; 1 == top level menu only
     */
    public void setSubMenuDepth(Integer subMenuDepth) {
        setValue(KEY_SUB_MENU_DEPTH, subMenuDepth);
    }

    /**
     * Gets the subMenuDepth.
     *
     * @return Integer The depth of submenus allowed when the driver is distracted. e.g. 3 == top level menu ->
     * submenu -> submenu; 1 == top level menu only
     */
    public Integer getSubMenuDepth() {
        return getInteger(KEY_SUB_MENU_DEPTH);
    }
}
