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
import com.smartdevicelink.proxy.rpc.enums.AppInterfaceUnregisteredReason;

import java.util.Hashtable;

/**
 * <p>Notifies an application that its interface registration has been terminated. This means that all SDL resources 
 * associated with the application are discarded, including the Command Menu, Choice Sets, button subscriptions, etc.</p>
 * For more information about SDL resources related to an interface registration, see {@linkplain RegisterAppInterface}.
 * <p></p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel: 
 * <ul><li>Any</li></ul>
 * AudioStreamingState: 
 * <ul><li>Any</li></ul>
 * SystemContext: 
 * <ul><li>Any</li></ul>
 * </ul>
 * <p></p>
 * <b>Parameter List:</b>
 * <table  border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>reason</td>
 * <td>{@linkplain AppInterfaceUnregisteredReason}</td>
 * <td>The reason the application's interface registration was terminated</td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * </table>
 * @since SmartDeviceLink 1.0
 * @see RegisterAppInterface
 */
public class OnAppInterfaceUnregistered extends RPCNotification {
	public static final String KEY_REASON = "reason";
	/**
	*Constructs a newly allocated OnAppInterfaceUnregistered object
	*/ 
    public OnAppInterfaceUnregistered() {
        super(FunctionID.ON_APP_INTERFACE_UNREGISTERED.toString());
    }
    /**
    *<p>Constructs a newly allocated OnAppInterfaceUnregistered object indicated by the Hashtable parameter</p>
    *@param hash The Hashtable to use
    */    
    public OnAppInterfaceUnregistered(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     *Constructs a newly allocated OnAppInterfaceUnregistered object
     * @param reason The reason application's interface registration was terminated
     */
    public OnAppInterfaceUnregistered(@NonNull AppInterfaceUnregisteredReason reason) {
        this();
        setReason(reason);
    }
    /**
     * <p>Get the reason the registration was terminated</p>
     * @return {@linkplain AppInterfaceUnregisteredReason} the reason the application's interface registration was terminated
     */    
    public AppInterfaceUnregisteredReason getReason() {
        return (AppInterfaceUnregisteredReason) getObject(AppInterfaceUnregisteredReason.class, KEY_REASON);
    }
    /**
     * <p>Set the reason application's interface was terminated</p>
     * @param reason The reason application's interface registration was terminated
     */    
    public void setReason( @NonNull AppInterfaceUnregisteredReason reason ) {
        setParameters(KEY_REASON, reason);
    }
}
