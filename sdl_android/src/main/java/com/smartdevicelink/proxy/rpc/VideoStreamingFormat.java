package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;

import java.util.Hashtable;

/**
 * Video streaming formats and their specifications.
 */

public class VideoStreamingFormat extends RPCStruct {
	public static final String KEY_PROTOCOL = "protocol";
	public static final String KEY_CODEC = "codec";

	public VideoStreamingFormat(){}
	public VideoStreamingFormat(Hashtable<String, Object> hash){super(hash);}

	public VideoStreamingFormat(VideoStreamingCodec codec,VideoStreamingProtocol protocol){
		setCodec(codec);
		setProtocol(protocol);
	}

	public void setProtocol(VideoStreamingProtocol protocol){
		setValue(KEY_PROTOCOL, protocol);
	}

	public VideoStreamingProtocol getProtocol(){
		return (VideoStreamingProtocol) getObject(VideoStreamingProtocol.class, KEY_PROTOCOL);
	}

	public void setCodec(VideoStreamingCodec codec){
		setValue(KEY_CODEC, codec);
	}

	public VideoStreamingCodec getCodec(){
		return (VideoStreamingCodec) getObject(VideoStreamingCodec.class, KEY_CODEC);
	}

	@Override
	public String toString() {
		return "codec=" + String.valueOf(getCodec()) +
				", protocol=" + String.valueOf(getProtocol());
	}
}
