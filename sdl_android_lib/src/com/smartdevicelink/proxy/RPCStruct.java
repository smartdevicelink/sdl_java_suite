package com.smartdevicelink.proxy;

import org.json.JSONObject;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonParameters;

/**
 * RPCStruct represents a base RPC class that creates methods that all RPC
 * classes must adhere to.  In addition, it provides static system-level
 * variables to subclasses.
 * 
 * @author Mike Burke
 *
 */
public abstract class RPCStruct implements JsonParameters {

	protected static int sdlVersion = Version.SDL_CURRENT_VERSION;
	
	/**
	 * This abstract method creates the entire JSON object that will be sent through the proxy to SDL core.
	 * 
	 * @return The fully-constructed JSON object for the RPC
	 */
	public abstract JSONObject toJson(int sdlVersion);
	
	@Override
	public JSONObject getJsonParameters(int sdlVersion) {
		return new JSONObject();
	}
	
	public static void setSdlVersion(int sdlVersionIn){
		sdlVersion = sdlVersionIn;
	}
	
	public static int getSdlVersion(){
		return sdlVersion;
	}

}
