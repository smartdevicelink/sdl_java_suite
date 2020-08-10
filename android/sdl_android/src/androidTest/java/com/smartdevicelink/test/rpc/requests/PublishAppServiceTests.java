package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AppServiceManifest;
import com.smartdevicelink.proxy.rpc.PublishAppService;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static androidx.test.InstrumentationRegistry.getTargetContext;

public class PublishAppServiceTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		PublishAppService msg = new PublishAppService();
		msg.setAppServiceManifest(TestValues.GENERAL_APPSERVICEMANIFEST);
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
			result.put(PublishAppService.KEY_APP_SERVICE_MANIFEST, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_APPSERVICEMANIFEST.getStore()));
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	@Test
	public void testRpcValues () {
		// Test Values
		AppServiceManifest copy = ( (PublishAppService) msg ).getAppServiceManifest();

		// Valid Tests
		assertTrue(Validator.validateAppServiceManifest(TestValues.GENERAL_APPSERVICEMANIFEST, copy));

		// Invalid/Null Tests
		PublishAppService msg = new PublishAppService();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.MATCH, msg.getAppServiceManifest());
	}

	/**
	 * Tests constructor with required params
	 */
	@Test
	public void testRequiredParamsConstructor () {

		PublishAppService msg = new PublishAppService(TestValues.GENERAL_APPSERVICEMANIFEST);
		assertNotNull(TestValues.NOT_NULL, msg);
		// Valid Tests
		assertTrue(Validator.validateAppServiceManifest(TestValues.GENERAL_APPSERVICEMANIFEST, msg.getAppServiceManifest()));
	}

	/**
	 * Tests a valid JSON construction of this RPC message.
	 */
	@Test
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(getTargetContext(), getCommandType(), getMessageType());
		assertNotNull(TestValues.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PublishAppService cmd = new PublishAppService(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			JSONObject appServiceManifestObject = JsonUtils.readJsonObjectFromJsonObject(parameters, PublishAppService.KEY_APP_SERVICE_MANIFEST);
			AppServiceManifest manifestTest = new AppServiceManifest(JsonRPCMarshaller.deserializeJSONObject(appServiceManifestObject));
			assertTrue(TestValues.TRUE,  Validator.validateAppServiceManifest(manifestTest, cmd.getAppServiceManifest()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}