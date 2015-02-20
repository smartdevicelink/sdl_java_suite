package com.smartdevicelink.proxy;

import com.smartdevicelink.proxy.rpc.SdlMsgVersion;

public class Version {
	public static final String VERSION = "VERSION-INFO";
	
	public static final int SDL_1_X = 1;
	public static final int SDL_2_X = 2;
	public static final int SDL_3_X = 3;
	
	public static final int SDL_CURRENT_VERSION = SDL_3_X;
	
	public static int getVersionCode(SdlMsgVersion msgVersion){
	    try{
	        // for RPC messages, we only care about major versions
    	    return (int) msgVersion.getMajorVersion();
	    } catch(NullPointerException e){
	        // input msgVersion was invalid, assume most recent version
	        return SDL_CURRENT_VERSION;
	    }
	}
}
