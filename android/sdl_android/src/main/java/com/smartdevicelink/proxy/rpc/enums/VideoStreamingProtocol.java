package com.smartdevicelink.proxy.rpc.enums;

/**
 * Enum for each type of video streaming protocol type.
 */

public enum VideoStreamingProtocol {
	/**
	 * Raw stream bytes that contains no timestamp data and is the lowest supported video streaming
	 */
	RAW,
	/**
	 * RTP facilitates the transfer of real-time data. Information provided by this protocol include
	 * timestamps (for synchronization), sequence numbers (for packet loss and reordering detection)
	 * and the payload format which indicates the encoded format of the data.
	 */
	RTP,
	/**
	 * The transmission of streaming data itself is not a task of RTSP. Most RTSP servers use the
	 * Real-time Transport Protocol (RTP) in conjunction with Real-time Control Protocol (RTCP) for
	 * media stream delivery. However, some vendors implement proprietary transport protocols.
	 */
	RTSP,
	/**
	 * Real-Time Messaging Protocol (RTMP) was initially a proprietary protocol developed by
	 * Macromedia for streaming audio, video and data over the Internet, between a Flash player and
	 * a server. Macromedia is now owned by Adobe, which has released an incomplete version of the
	 * specification of the protocol for public use.
	 */
	RTMP,
	/**
	 * The WebM container is based on a profile of Matroska. WebM initially supported VP8 video and
	 * Vorbis audio streams. In 2013 it was updated to accommodate VP9 video and Opus audio.
	 */
	WEBM;

	public static VideoStreamingProtocol valueForString(String value) {
		try{
			return valueOf(value);
		}catch(Exception e){
			return null;
		}
	}
}
