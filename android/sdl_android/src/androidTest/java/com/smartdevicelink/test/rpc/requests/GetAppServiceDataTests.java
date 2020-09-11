package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetAppServiceData;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

public class GetAppServiceDataTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		GetAppServiceData msg = new GetAppServiceData();
		msg.setServiceType(TestValues.GENERAL_STRING);
		msg.setSubscribe(TestValues.GENERAL_BOOLEAN);
		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.GET_APP_SERVICE_DATA.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(GetAppServiceData.KEY_SERVICE_TYPE, TestValues.GENERAL_STRING);
			result.put(GetAppServiceData.KEY_SUBSCRIBE, TestValues.GENERAL_BOOLEAN);
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
		String serviceType = ( (GetAppServiceData) msg ).getServiceType();
		boolean subscribe = ( (GetAppServiceData) msg ).getSubscribe();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, serviceType);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, subscribe);

		// Invalid/Null Tests
		GetAppServiceData msg = new GetAppServiceData();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(msg.getServiceType());
		assertNull(msg.getSubscribe());
	}

	/**
	 * Tests constructor with required params
	 */
	@Test
	public void testRequiredParamsConstructor () {

		GetAppServiceData msg = new GetAppServiceData(TestValues.GENERAL_STRING);
		assertNotNull(TestValues.NOT_NULL, msg);
		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, msg.getServiceType());
	}

	/**
	 * Tests a valid JSON construction of this RPC message.
	 */
	@Test
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(getInstrumentation().getTargetContext(), getCommandType(), getMessageType());
		assertNotNull(TestValues.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			GetAppServiceData cmd = new GetAppServiceData(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, GetAppServiceData.KEY_SUBSCRIBE), cmd.getSubscribe());
			assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetAppServiceData.KEY_SERVICE_TYPE).toString(), cmd.getServiceType().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}