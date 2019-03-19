package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AppServiceManifest;
import com.smartdevicelink.proxy.rpc.PublishAppService;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class PublishAppServiceTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		PublishAppService msg = new PublishAppService();
		msg.setAppServiceManifest(Test.GENERAL_APPSERVICEMANIFEST);
		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.PUBLISH_APP_SERVICE.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(PublishAppService.KEY_APP_SERVICE_MANIFEST, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_APPSERVICEMANIFEST.getStore()));
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
		AppServiceManifest copy = ( (PublishAppService) msg ).getAppServiceManifest();

		// Valid Tests
		assertTrue(Validator.validateAppServiceManifest(Test.GENERAL_APPSERVICEMANIFEST, copy));

		// Invalid/Null Tests
		PublishAppService msg = new PublishAppService();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.MATCH, msg.getAppServiceManifest());
	}

	/**
	 * Tests constructor with required params
	 */
	public void testRequiredParamsConstructor () {

		PublishAppService msg = new PublishAppService(Test.GENERAL_APPSERVICEMANIFEST);
		assertNotNull(Test.NOT_NULL, msg);
		// Valid Tests
		assertTrue(Validator.validateAppServiceManifest(Test.GENERAL_APPSERVICEMANIFEST, msg.getAppServiceManifest()));
	}

	/**
	 * Tests a valid JSON construction of this RPC message.
	 */
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
		assertNotNull(Test.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PublishAppService cmd = new PublishAppService(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			JSONObject appServiceManifestObject = JsonUtils.readJsonObjectFromJsonObject(parameters, PublishAppService.KEY_APP_SERVICE_MANIFEST);
			AppServiceManifest manifestTest = new AppServiceManifest(JsonRPCMarshaller.deserializeJSONObject(appServiceManifestObject));
			assertTrue(Test.TRUE,  Validator.validateAppServiceManifest(manifestTest, cmd.getAppServiceManifest()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}