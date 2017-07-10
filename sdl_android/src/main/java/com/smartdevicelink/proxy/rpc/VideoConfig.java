package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;

import java.util.Hashtable;

/**
 * Configuration of a video stream.
 */

public class VideoConfig extends RPCStruct{
	public static final String KEY_PROTOCOL = "protocol";
	public static final String KEY_CODEC = "codec";
	public static final String KEY_WIDTH = "width";
	public static final String KEY_HEIGHT = "height";


	public VideoConfig(){}
	public VideoConfig(Hashtable<String, Object> hash){super(hash);}

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

	public void setWidth(Integer width){
		setValue(KEY_WIDTH, width);
	}

	public Integer getWidth(){
		return getInteger(KEY_WIDTH);
	}

	public void setHeight(Integer height){
		setValue(KEY_HEIGHT, height);
	}

	public Integer getHeight(){
		return getInteger(KEY_HEIGHT);
	}
}
