package com.smartdevicelink.proxy.rpc.enums;

/**
 * Enum for each type of video streaming codec.
 */

public enum VideoStreamingCodec {
	/**
	 * A block-oriented motion-compensation-based video compression standard. As of 2014 it is one
	 * of the most commonly used formats for the recording, compression, and distribution of video
	 * content.
	 */
	H264,
	/**
	 * High Efficiency Video Coding (HEVC), also known as H.265 and MPEG-H Part 2, is a video
	 * compression standard, one of several potential successors to the widely used AVC
	 * (H.264 or MPEG-4 Part 10). In comparison to AVC, HEVC offers about double the data
	 * compression ratio at the same level of video quality, or substantially improved video quality
	 * at the same bit rate. It supports resolutions up to 8192x4320, including 8K UHD.
	 */
	H265,
	/**
	 * Theora is derived from the formerly proprietary VP3 codec, released into the public domain by
	 * On2 Technologies. It is broadly comparable in design and bitrate efficiency to MPEG-4 Part 2,
	 * early versions of Windows Media Video, and RealVideo while lacking some of the features
	 * present in some of these other codecs. It is comparable in open standards philosophy to the
	 * BBC's Dirac codec.
	 */
	Theora,
	/**
	 * VP8 can be multiplexed into the Matroska-based container format WebM along with Vorbis and
	 * Opus audio. The image format WebP is based on VP8's intra-frame coding. VP8's direct
	 * successor, VP9, and the emerging royalty-free internet video format AV1 from the Alliance
	 * for Open Media (AOMedia) are based on VP8.
	 */
	VP8,
	/**
	 * Similar to VP8, but VP9 is customized for video resolutions beyond high-definition video
	 * (UHD) and also enables lossless compression.
	 */
	VP9;

	public static VideoStreamingCodec valueForString(String value) {
		try{
			return valueOf(value);
		}catch(Exception e){
			return null;
		}
	}
}
