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
import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import java.util.Hashtable;
import java.util.List;

/**
 * Defining sets of HMI levels, which are permitted or prohibited for a given RPC.
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 *         <tr>
 *             <th>Name</th>
 *             <th>Type</th>
 *             <th>Description</th>
 *             <th>SmartDeviceLink Ver. Available</th>
 *         </tr>
 *         <tr>
 *             <td>allowed</td>
 *             <td>HMILevel</td>
 *             <td>A set of all HMI levels that are permitted for this given RPC.
 *                     <ul>
 *                     <li>Min: 0</li>
 *                     <li>Max: 100</li>
 *                     </ul>
 *             </td>
 *             <td>SmartDeviceLink 2.0</td>
 *         </tr>
 *         <tr>
 *             <td>userDisallowed</td>
 *             <td>HMILevel</td>
 *             <td>A set of all HMI levels that are prohibited for this given RPC.
 *                     <ul>
 *                     <li>Min: 0</li>
 *                     <li>Max: 100</li>
 *                     </ul>
 *             </td>
 *             <td>SmartDeviceLink 2.0</td>
 *         </tr>
 *  </table>
 *
 * @since SmartDeviceLink 2.0
 */
public class HMIPermissions extends RPCStruct {
    public static final String KEY_ALLOWED = "allowed";
    public static final String KEY_USER_DISALLOWED = "userDisallowed";

    /**
     * Constructs a newly allocated HMIPermissions object
     */
    public HMIPermissions() {
    }

    /**
     * Constructs a newly allocated HMIPermissions object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public HMIPermissions(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated HMIPermissions object
     *
     * @param allowed        HMI level that is permitted for this given RPC
     * @param userDisallowed HMI level that is prohibited for this given RPC
     */
    public HMIPermissions(@NonNull List<HMILevel> allowed, @NonNull List<HMILevel> userDisallowed) {
        this();
        setAllowed(allowed);
        setUserDisallowed(userDisallowed);
    }

    /**
     * get a set of all HMI levels that are permitted for this given RPC.
     *
     * @return a set of all HMI levels that are permitted for this given RPC
     */
    @SuppressWarnings("unchecked")
    public List<HMILevel> getAllowed() {
        return (List<HMILevel>) getObject(HMILevel.class, KEY_ALLOWED);
    }

    /**
     * set  HMI level that is permitted for this given RPC.
     *
     * @param allowed HMI level that is permitted for this given RPC
     */
    public HMIPermissions setAllowed(@NonNull List<HMILevel> allowed) {
        setValue(KEY_ALLOWED, allowed);
        return this;
    }

    /**
     * get a set of all HMI levels that are prohibited for this given RPC
     *
     * @return a set of all HMI levels that are prohibited for this given RPC
     */
    @SuppressWarnings("unchecked")
    public List<HMILevel> getUserDisallowed() {
        return (List<HMILevel>) getObject(HMILevel.class, KEY_USER_DISALLOWED);
    }

    /**
     * set a set of all HMI levels that are prohibited for this given RPC
     *
     * @param userDisallowed HMI level that is prohibited for this given RPC
     */
    public HMIPermissions setUserDisallowed(@NonNull List<HMILevel> userDisallowed) {
        setValue(KEY_USER_DISALLOWED, userDisallowed);
        return this;
    }
}
