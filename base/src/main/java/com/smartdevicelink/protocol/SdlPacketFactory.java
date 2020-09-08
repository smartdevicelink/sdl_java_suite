/*
 * Copyright (c) 2019, Livio, Inc.
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
 */
package com.smartdevicelink.protocol;

import androidx.annotation.RestrictTo;

import com.smartdevicelink.protocol.enums.ControlFrameTags;
import com.smartdevicelink.protocol.enums.FrameDataControlFrameType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.util.BitConverter;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class SdlPacketFactory {

	/* 
	 * public SdlPacket(int version, boolean compression, int frameType,
			int serviceType, int frameInfo, int sessionId,
			int dataSize, int messageId, byte[] payload) {
	 */
	public static SdlPacket createStartSession(SessionType serviceType, int messageID, byte version, byte sessionID, boolean encrypted) {

		return new SdlPacket(version,encrypted,SdlPacket.FRAME_TYPE_CONTROL,
				serviceType.getValue(),SdlPacket.FRAME_INFO_START_SERVICE,sessionID,
				0,messageID,null);
	}

    public static SdlPacket createHeartbeat(SessionType serviceType, byte sessionID, byte version) {
    
    	return new SdlPacket(version,false,SdlPacket.FRAME_TYPE_CONTROL,
				serviceType.getValue(),FrameDataControlFrameType.Heartbeat.value(),sessionID,
				0,0,null);

    }

	public static SdlPacket createHeartbeatACK(SessionType serviceType, byte sessionID, byte version) {
		return new SdlPacket(version,false,SdlPacket.FRAME_TYPE_CONTROL,
				serviceType.getValue(),FrameDataControlFrameType.HeartbeatACK.value(),sessionID,
				0,0,null);
		}
	
	public static SdlPacket createStartSessionACK(SessionType serviceType, byte sessionID, int messageID, byte version) {
		
		return new SdlPacket(version,false,SdlPacket.FRAME_TYPE_CONTROL,
				serviceType.getValue(),FrameDataControlFrameType.StartSessionACK.value(),sessionID,
				0,messageID,null);

	}

	public static SdlPacket createStartSessionNACK(SessionType serviceType, byte sessionID, int messageID, byte version) {
		
		return new SdlPacket(version,false,SdlPacket.FRAME_TYPE_CONTROL,
				serviceType.getValue(),SdlPacket.FRAME_INFO_START_SERVICE_NAK,sessionID,
				0,messageID,null);
	}

	public static SdlPacket createEndSession(SessionType serviceType, byte sessionID, int messageID, byte version, byte[] payload) {
		return new SdlPacket(version,false,SdlPacket.FRAME_TYPE_CONTROL,
				serviceType.getValue(),SdlPacket.FRAME_INFO_END_SERVICE,sessionID,
				payload.length,messageID,payload);
	}

	public static SdlPacket createEndSession(SessionType serviceType, byte sessionID, int messageID, byte version, int hashID) {
		if (version < 5) {
			byte[] payload = BitConverter.intToByteArray(hashID);
			return new SdlPacket(version, false, SdlPacket.FRAME_TYPE_CONTROL,
					serviceType.getValue(), SdlPacket.FRAME_INFO_END_SERVICE, sessionID,
					payload.length, messageID, payload);
		} else {
			SdlPacket endSession = new SdlPacket(version, false, SdlPacket.FRAME_TYPE_CONTROL,
					serviceType.getValue(), SdlPacket.FRAME_INFO_END_SERVICE, sessionID,
					0, messageID, null);
			endSession.putTag(ControlFrameTags.RPC.EndService.HASH_ID, hashID);
			return endSession;
		}
	}

	public static SdlPacket createSingleSendData(SessionType serviceType, byte sessionID,
			int dataLength, int messageID, byte version, byte[] payload, boolean encrypted) {
		
		return new SdlPacket(version,encrypted,SdlPacket.FRAME_TYPE_SINGLE,
				serviceType.getValue(),0,sessionID,
				payload.length,messageID,payload);
	}

	public static SdlPacket createMultiSendDataFirst(SessionType serviceType, byte sessionID, 
			int messageID, byte version, byte[] payload, boolean encrypted) {
		
		return new SdlPacket(version,encrypted,SdlPacket.FRAME_TYPE_FIRST,
				serviceType.getValue(),0,sessionID,
				8,messageID,payload);

	}

	public static SdlPacket createMultiSendDataRest(SessionType serviceType, byte sessionID,
			int dataLength, byte frameSequenceNumber, int messageID, byte version, byte[] payload,int offset,int length, boolean encrypted) {
		
		return new SdlPacket(version,encrypted,SdlPacket.FRAME_TYPE_CONSECUTIVE,
				serviceType.getValue(),frameSequenceNumber,sessionID,
				length,messageID,payload,offset,length);
	}

	public static SdlPacket createRegisterSecondaryTransport(byte sessionID, byte version) {
		return new SdlPacket(version, false, SdlPacket.FRAME_TYPE_CONTROL,
				SessionType.CONTROL.getValue(), SdlPacket.FRAME_INFO_REGISTER_SECONDARY_TRANSPORT,
				sessionID, 0, 0x01, null);
	}
	
	public static BinaryFrameHeader createBinaryFrameHeader(byte rpcType, int functionID, int corrID, int jsonSize) {
		BinaryFrameHeader msg = new BinaryFrameHeader();
		msg.setRPCType(rpcType);
		msg.setFunctionID(functionID);
		msg.setCorrID(corrID);
		msg.setJsonSize(jsonSize);
		
		return msg;
	}
	
}
