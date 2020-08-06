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
package com.smartdevicelink.protocol.enums;

/**
 * Control frame payload tags that relate to the respective services. Each class represents a different service, RPC, Audio, and Video services.
 */
public class ControlFrameTags {

	private static class StartServiceACKBase{
		/** Max transport unit to be used for this service */
		public static final String MTU = "mtu";
	}

	private static class NAKBase{
		/** An array of rejected parameters related to the corresponding request*/
		public static final String REJECTED_PARAMS = "rejectedParams";
		public static final String REASON = "reason";
	}

	/**
	 * Control frame payloads that relate to the Remote Procedure Call (RPC) service.
	 */
	public static class RPC {
		public static class StartService {
			/** The max version of the protocol supported by client requesting service to start.<br>
			* Must be in the format "Major.Minor.Patch"
			*/
			public static final String PROTOCOL_VERSION = "protocolVersion";
		}
		public static class StartServiceACK extends StartServiceACKBase{
			/** The negotiated version of the protocol. Must be in the format "Major.Minor.Patch"*/
			public static final String PROTOCOL_VERSION = StartService.PROTOCOL_VERSION;
			/** Hash ID to identify this service and used when sending an EndService control frame*/
			public static final String HASH_ID = "hashId";
			/** HU allowed transport for secondary connection */
			public static final String SECONDARY_TRANSPORTS = "secondaryTransports";
			/** HU allowed transports for audio and video services (1 == Primary, 2 == Secondary) */
			public static final String AUDIO_SERVICE_TRANSPORTS = "audioServiceTransports";
			public static final String VIDEO_SERVICE_TRANSPORTS = "videoServiceTransports";
			/** Auth token to be used for log in into services **/
			public static final String AUTH_TOKEN = "authToken";

		}
		public static class StartServiceNAK extends NAKBase{}
		public static class EndService {
			/** Hash ID supplied in the StartServiceACK for this service type*/
			public static final String HASH_ID = RPC.StartServiceACK.HASH_ID;
		}
		public static class EndServiceACK {}
		public static class EndServiceNAK extends NAKBase{}
		/** This frame is sent from Core to application to indicate that status or configuration of
		 * transport(s) is/are updated. This frame should not be sent prior to Version Negotiation.
		 **/
		public static class TransportEventUpdate {
		    /** The HU reported IP address and port of TCP connection */
			public static final String TCP_IP_ADDRESS = "tcpIpAddress";
		    public static final String TCP_PORT = "tcpPort";
		}
		/**This frame is sent from application to Core to notify that Secondary Transport has been
		 * established. This frame should be only sent on Secondary Transport.
		 **/
		public static class RegisterSecondaryTransport {}
		public static class RegisterSecondaryTransportACK {}
		public static class RegisterSecondaryTransportNAK extends NAKBase {
			public static final String REASON = "reason";
		}
	}

	/**
	 * Control frame payloads that relate to the Audio streaming service. This service has also been referred to as the PCM service.
	 */
	public static class Audio {
		public static class StartService {}
		public static class StartServiceACK extends StartServiceACKBase{}
		public static class StartServiceNAK extends NAKBase{}
		public static class EndService {}
		public static class EndServiceACK {}
		public static class EndServiceNAK extends NAKBase{}
	}

	/**
	 * Control frame payloads that relate to the Video streaming service. This service has also been referred to as the .h264 service.
	 */
	public static class Video {
		public static class StartService {
			/** Desired height in pixels from the client requesting the video service to start*/
			public static final String HEIGHT = "height";
			/** Desired width in pixels from the client requesting the video service to start*/
			public static final String WIDTH = "width";
			/** Desired video protocol to be used*/
			public static final String VIDEO_PROTOCOL = "videoProtocol";
			/** Desired video codec to be used*/
			public static final String VIDEO_CODEC = "videoCodec";
		}
		public static class StartServiceACK extends StartServiceACKBase{
			/** Accepted height in pixels from the client requesting the video service to start*/
			public static final String HEIGHT = StartService.HEIGHT;
			/** Accepted width in pixels from the client requesting the video service to start*/
			public static final String WIDTH = StartService.WIDTH;
			/** Accepted video protocol to be used*/
			public static final String VIDEO_PROTOCOL = StartService.VIDEO_PROTOCOL;
			/** Accepted video codec to be used*/
			public static final String VIDEO_CODEC = StartService.VIDEO_CODEC;
		}

		public static class StartServiceNAK extends NAKBase{}
		public static class EndService {}
		public static class EndServiceACK {}
		public static class EndServiceNAK extends NAKBase{}
	}
}
