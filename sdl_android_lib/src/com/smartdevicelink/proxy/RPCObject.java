package com.smartdevicelink.proxy;

import org.json.JSONObject;

public abstract class RPCObject extends RPCStruct {

	@Override
	public JSONObject toJson(int sdlVersion) {
		return getJsonParameters(sdlVersion);
	}

}
