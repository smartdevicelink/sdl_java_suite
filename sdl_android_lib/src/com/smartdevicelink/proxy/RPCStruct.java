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

	// TODO: Set Version number from SdlProxyBase when connection occurs
	protected static int sdlVersion = 0; // TODO: need to default to pre-defined values when versioning system improves
	
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
	
	/* default */static void setSdlVersion(int sdlVersionIn){
		sdlVersion = sdlVersionIn;
	}
	
	protected static int getSdlVersion(){
		return sdlVersion;
	}

}
