package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetCloudAppProperties;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

import org.json.JSONException;
import org.json.JSONObject;

public class GetCloudAppPropertiesTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage(){
		GetCloudAppProperties msg = new GetCloudAppProperties();

		msg.setAppID(Test.GENERAL_STRING);

		return msg;
	}

	@Override
	protected String getMessageType(){
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType(){
		return FunctionID.GET_CLOUD_APP_PROPERTIES.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion){
		JSONObject result = new JSONObject();

		try{
			result.put(GetCloudAppProperties.KEY_APP_ID, Test.GENERAL_STRING);
		}catch(JSONException e){
			fail(Test.JSON_FAIL);
		}

		return result;
	}

}
