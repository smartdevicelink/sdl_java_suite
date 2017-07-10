package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;

import java.util.Hashtable;

/**
 * Request from SDL to HMI to ask whether HMI accepts a video stream with given configuration.
 */

public class SetVideoConfig extends RPCRequest{
	public static final String KEY_CONFIG = "config";
	public static final String KEY_APPID = "appID";

	public SetVideoConfig() {
		super(FunctionID.SET_VIDEO_CONFIG.toString());
	}

	public SetVideoConfig(Hashtable<String, Object> hash){super(hash);}

	public void setConfig(VideoConfig config){
		setValue(KEY_CONFIG, config);
	}

	public VideoConfig getConfig(){
		return (VideoConfig) getObject(VideoConfig.class, KEY_CONFIG);
	}

	public void setAppID(String appID){
		setValue(KEY_APPID, appID);
	}

	public String getAppID(){
		return getString(KEY_APPID);
	}
}
