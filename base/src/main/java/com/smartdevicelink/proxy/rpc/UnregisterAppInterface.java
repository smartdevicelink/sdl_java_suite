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

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

/**
 * Terminates an application's interface registration. This causes SDL&reg; to
 * dispose of all resources associated with the application's interface
 * registration (e.g. Command Menu items, Choice Sets, button subscriptions,
 * etc.)
 * 
 * <p>After the UnregisterAppInterface operation is performed, no other operations
 * can be performed until a new app interface registration is established by
 * calling <i>{@linkplain RegisterAppInterface}</i></p>
 *
 * @see RegisterAppInterface
 * @see OnAppInterfaceUnregistered
 */
public class UnregisterAppInterface extends RPCRequest {
	/**
	 * Constructs a new UnregisterAppInterface object
	 */
    public UnregisterAppInterface() {
        super(FunctionID.UNREGISTER_APP_INTERFACE.toString());
    }
	/**
	 * <p>Constructs a new UnregisterAppInterface object indicated by the Hashtable
	 * parameter</p>
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */    
    public UnregisterAppInterface(Hashtable<String, Object> hash) {
        super(hash);
    }
}
