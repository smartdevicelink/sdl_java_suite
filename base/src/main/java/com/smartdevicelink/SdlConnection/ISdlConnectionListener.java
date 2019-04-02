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

import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.transport.BaseTransportConfig;

import java.util.List;


public interface ISdlConnectionListener {
	@Deprecated
	void onTransportDisconnected(String info);

	void onTransportDisconnected(String info, boolean availablePrimary, BaseTransportConfig transportConfig);


	void onTransportError(String info, Exception e);
	
	void onProtocolMessageReceived(ProtocolMessage msg);
	
	void onProtocolSessionStartedNACKed(SessionType sessionType,
			byte sessionID, byte version, String correlationID, List<String> rejectedParams);
	
	void onProtocolSessionStarted(SessionType sessionType,
			byte sessionID, byte version, String correlationID, int hashID, boolean isEncrypted);
	
	void onProtocolSessionEnded(SessionType sessionType,
			byte sessionID, String correlationID);
	
	void onProtocolSessionEndedNACKed(SessionType sessionType,
	byte sessionID, String correlationID);
	
	void onProtocolError(String info, Exception e);

	@Deprecated
	void onHeartbeatTimedOut(byte sessionID);
	
	void onProtocolServiceDataACK(SessionType sessionType, int dataSize, byte sessionID);

	void onAuthTokenReceived(String authToken, byte sessionID);
}
