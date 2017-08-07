package com.smartdevicelink.protocol.enums;

public class BsonTags {
	public static class RPC{
		public static class StartService {
			// The max version of the protocol supported by client requesting service to start.
			// Must be in the format "Major.Minor.Patch"
			public static final String PROTOCOL_VERSION = "protocolVersion";
		}
		public static class StartServiceACK {
			// Hash ID to identify this service and used when sending an EndService control frame
			public static final String HASH_ID = "hashId";
			// Max transport unit to be used for this service
			public static final String MTU = "mtu";
			// The negotiated version of the protocol. Must be in the format "Major.Minor.Patch"
			public static final String PROTOCOL_VERSION = "protocolVersion";
		}
		public static class StartServiceNAK {
			// An array of rejected parameters
			public static final String REJECTED_PARAMS = "rejectedParams";
		}
		public static class EndService {
			// Hash ID supplied in the StartServiceACK for this service type
			public static final String HASH_ID = "hashId";
		}
		public static class EndServiceACK {}
		public static class EndServiceNAK {
			// An array of rejected parameters such as: [hashId]
			public static final String REJECTED_PARAMS = "rejectedParams";
		}
	}
	public static class PCM{
		public static class StartService {}
		public static class StartServiceACK {
			// Hash ID to identify this service and used when sending an EndService control frame
			public static final String HASH_ID = "hashId";
			// Max transport unit to be used for this service. If not included the client
			// should use the one set via the RPC service or protocol version default.
			public static final String MTU = "mtu";
		}
		public static class StartServiceNAK {
			// An array of rejected parameters such as: [videoProtocol, videoProtocol]
			public static final String REJECTED_PARAMS = "rejectedParams";
		}
		public static class EndService {
			// Hash ID supplied in the StartServiceACK for this service type
			public static final String HASH_ID = "hashId";
		}
		public static class EndServiceACK {}
		public static class EndServiceNAK {
			// An array of rejected parameters such as: [hashId]
			public static final String REJECTED_PARAMS = "rejectedParams";
		}
	}
	public static class VIDEO{
		public static class StartService {
			// Desired height in pixels from the client requesting the video service to start
			public static final String HEIGHT = "height";
			// Desired width in pixels from the client requesting the video service to start
			public static final String WIDTH = "width";
			// Desired video protocol to be used
			public static final String VIDEO_PROTOCOL = "videoProtocol";
			// Desired video codec to be used
			public static final String VIDEO_CODEC = "videoCodec";
		}
		public static class StartServiceACK {
			// Hash ID to identify this service and used when sending an EndService control frame
			public static final String HASH_ID = "hashId";
			// Max transport unit to be used for this service. If not included the client should use
			// the one set via the RPC service or protocol version default.
			public static final String MTU = "mtu";
			// Accepted height in pixels from the client requesting the video service to start
			public static final String HEIGHT = "height";
			// Accepted width in pixels from the client requesting the video service to start
			public static final String WIDTH = "width";
			// Accepted video protocol to be used
			public static final String VIDEO_PROTOCOL = "videoProtocol";
			// Accepted video codec to be used
			public static final String VIDEO_CODEC = "videoCodec";
		}
		public static class StartServiceNAK {
			// An array of rejected parameters such as: [videoProtocol, videoProtocol]
			public static final String REJECTED_PARAMS = "rejectedParams";
		}
		public static class EndService {
			// Hash ID supplied in the StartServiceACK for this service type
			public static final String HASH_ID = "hashId";
		}
		public static class EndServiceACK {}
		public static class EndServiceNAK {
			// An array of rejected parameters such as: [hashId]
			public static final String REJECTED_PARAMS = "rejectedParams";
		}
	}
}
