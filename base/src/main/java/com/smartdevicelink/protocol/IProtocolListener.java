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
package com.smartdevicelink.protocol;


import com.smartdevicelink.protocol.enums.SessionType;

import java.util.List;

public interface IProtocolListener {
	// Called to indicate that these bytes are to be sent as part of a message.
	// This call includes the part of the message.
	void onProtocolMessageBytesToSend(SdlPacket packet);

	// Called to indicate that a complete message (RPC, BULK, etc.) has been
	// received.  This call includes the message.
	void onProtocolMessageReceived(ProtocolMessage msg);

	// Called to indicate that a protocol session has been started (from either side)
	void onProtocolSessionStarted(SessionType sessionType, byte sessionID, byte version, String correlationID, int hashID, boolean isEncrypted);
	
	void onProtocolSessionNACKed(SessionType sessionType, byte sessionID, byte version,
	                             String correlationID, List<String> rejectedParams);

	// Called to indicate that a protocol session has ended (from either side)
	void onProtocolSessionEnded(SessionType sessionType, byte sessionID, String correlationID /*, String info, Exception ex*/);
 	
	void onProtocolSessionEndedNACKed(SessionType sessionType, byte sessionID, String correlationID /*, String info, Exception ex*/);

	void onProtocolHeartbeat(SessionType sessionType, byte sessionID);
	
	/**
     * Called when a protocol heartbeat ACK message has been received from SDL.
     */
    void onProtocolHeartbeatACK(SessionType sessionType, byte sessionID);
    
    void onProtocolServiceDataACK(SessionType sessionType, int dataSize, byte sessionID);

    void onResetOutgoingHeartbeat(SessionType sessionType, byte sessionID);

    void onResetIncomingHeartbeat(SessionType sessionType, byte sessionID);

	// Called to indicate that a protocol error was detected in received data.
	void onProtocolError(String info, Exception e);

} // end-interfCe
