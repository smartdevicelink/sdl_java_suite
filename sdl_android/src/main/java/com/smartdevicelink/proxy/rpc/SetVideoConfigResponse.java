package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Response from HMI to SDL whether the configuration is accepted. In a negative response, a list of rejected parameters are supplied.
 */

public class SetVideoConfigResponse extends RPCResponse {
	public static final String KEY_REJECTED_PARAMS = "rejectedParams";

	public SetVideoConfigResponse() {
		super(FunctionID.SET_VIDEO_CONFIG.toString());
	}
	public SetVideoConfigResponse(Hashtable<String, Object> hash) {
		super(hash);
	}

	public void setRejectedParams(List<String> rejectedParams){
		setParameters(KEY_REJECTED_PARAMS, rejectedParams);
	}

	public List<String> getRejectedParams(){
		return (List<String>) getObject(String.class, KEY_REJECTED_PARAMS);
	}
}
