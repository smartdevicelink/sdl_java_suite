package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.SetAppIcon;
import com.smartdevicelink.proxy.rpc.SetVideoConfig;
import com.smartdevicelink.proxy.rpc.VideoConfig;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class SetVideoConfigTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		SetVideoConfig msg = new SetVideoConfig();

		msg.setAppID(Test.GENERAL_STRING);
		msg.setConfig(Test.GENERAL_VIDEOCONFIG);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SET_VIDEO_CONFIG.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(SetVideoConfig.KEY_CONFIG, Test.GENERAL_VIDEOCONFIG.serializeJSON());
			result.put(SetVideoConfig.KEY_APPID, Test.GENERAL_STRING);
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		VideoConfig config = ( (SetVideoConfig) msg ).getConfig();
		String appID = ( (SetVideoConfig) msg).getAppID();

		// Valid Tests
		assertTrue(Test.GENERAL_STRING.equals(appID));
		assertEquals(Test.MATCH, Test.GENERAL_VIDEOCONFIG, config);

		// Invalid/Null Tests
		SetVideoConfig msg = new SetVideoConfig();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getAppID());
		assertNull(Test.NULL, msg.getConfig());
	}

	/**
	 * Tests a valid JSON construction of this RPC message.
	 */
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
		assertNotNull(Test.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SetVideoConfig cmd = new SetVideoConfig(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, SetVideoConfig.KEY_APPID), cmd.getAppID());

			assertTrue(Test.TRUE, Validator.validateVideoConfig(
					new VideoConfig(JsonRPCMarshaller.deserializeJSONObject(JsonUtils.readJsonObjectFromJsonObject(parameters, SetVideoConfig.KEY_CONFIG))),
					cmd.getConfig()));
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}
