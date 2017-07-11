package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimerResponse;
import com.smartdevicelink.proxy.rpc.SetVideoConfig;
import com.smartdevicelink.proxy.rpc.SetVideoConfigResponse;
import com.smartdevicelink.proxy.rpc.SliderResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;

public class SetVideoConfigResponseTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage(){
		SetVideoConfigResponse msg = new SetVideoConfigResponse();

		msg.setRejectedParams(Test.GENERAL_STRING_LIST);

		return msg;
	}

	@Override
	protected String getMessageType(){
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType(){
		return FunctionID.SET_VIDEO_CONFIG.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion){
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();

		for(String s : Test.GENERAL_STRING_LIST){
			array.put(s);
		}

		try {
			result.put(SetVideoConfigResponse.KEY_REJECTED_PARAMS, array);

		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Invalid/Null Tests
		SetVideoConfigResponse msg = new SetVideoConfigResponse();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);
	}

	/**
	 * Tests a valid JSON construction of this RPC message.
	 */
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
		assertNotNull(Test.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SetVideoConfigResponse cmd = new SetVideoConfigResponse(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			List<String> cmdRejectParams = cmd.getRejectedParams();
			JSONArray refRejectParams = JsonUtils.readJsonArrayFromJsonObject(body.getJSONObject("parameters"), SetVideoConfigResponse.KEY_REJECTED_PARAMS);

			int i = 0;
			for(String s : cmdRejectParams){
				assertTrue(s.equals(refRejectParams.getString(i++)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}