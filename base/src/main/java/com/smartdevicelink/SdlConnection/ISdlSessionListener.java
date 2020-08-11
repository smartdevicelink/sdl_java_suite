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
package com.smartdevicelink.SdlConnection;

import androidx.annotation.RestrictTo;

import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.util.Version;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface ISdlSessionListener {

	/**
	 * Called when a transport disconnects
	 * @param info a human readable string including information on the disconnected transport
	 * @param availablePrimary a boolean flag indicating if there is another transport that can
	 *                              be used to connect with the SDL enabled device.
	 * @param transportConfig the previously supplied transport config
	 */
	void onTransportDisconnected(String info, boolean availablePrimary, BaseTransportConfig transportConfig);

	/**
	 * Called when an RPC message has been received from the connected SDL device
	 * @param rpcMessage the RPC message that was received
	 */
	void onRPCMessageReceived(RPCMessage rpcMessage);

	/**
	 * Called to indicate that a session has started with the connected SDL device. This means the
	 * RPC and Bulk service types have also been started.
	 * @param sessionID session ID associated with the session that was established
	 * @param version the protocol version that has been negotiated for this session
	 */
	void onSessionStarted(int sessionID, Version version);

	/**
	 * Called to indicate that the session that was previously established has now ended. This means
	 * that all services previously started on this session are also closed.
	 * @param sessionID the session ID that was assigned to this now closed session
	 */
	void onSessionEnded( int sessionID);

	/**
	 * Called when an auth token has been received. This should always happen after the session
	 * has been created.
	 * @param authToken the actual auth token that has been stringified
	 * @param sessionID the session ID that this auth token is associated with
	 */
	void onAuthTokenReceived(String authToken, int sessionID);
}