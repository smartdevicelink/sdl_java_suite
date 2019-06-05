package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.PerformAppServiceInteraction;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;


public class PerformAppServiceInteractionTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		PerformAppServiceInteraction msg = new PerformAppServiceInteraction();

		msg.setServiceUri(Test.GENERAL_STRING);
		msg.setServiceID(Test.GENERAL_STRING);
		msg.setOriginApp(Test.GENERAL_STRING);
		msg.setRequestServiceActive(Test.GENERAL_BOOLEAN);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.PERFORM_APP_SERVICES_INTERACTION.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(PerformAppServiceInteraction.KEY_SERVICE_URI, Test.GENERAL_STRING);
			result.put(PerformAppServiceInteraction.KEY_SERVICE_ID, Test.GENERAL_STRING);
			result.put(PerformAppServiceInteraction.KEY_ORIGIN_APP, Test.GENERAL_STRING);
			result.put(PerformAppServiceInteraction.KEY_REQUEST_SERVICE_ACTIVE, Test.GENERAL_BOOLEAN);
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
		String serviceUri = ( (PerformAppServiceInteraction) msg ).getServiceUri();
		String appServiceId = ( (PerformAppServiceInteraction) msg ).getServiceID();
		String originApp = ( (PerformAppServiceInteraction) msg ).getOriginApp();
		boolean requestServiceActive = ( (PerformAppServiceInteraction) msg ).getRequestServiceActive();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, serviceUri);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, appServiceId);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, originApp);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, requestServiceActive);

		// Invalid/Null Tests
		PerformAppServiceInteraction msg = new PerformAppServiceInteraction();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getServiceUri());
		assertNull(Test.NULL, msg.getServiceID());
		assertNull(Test.NULL, msg.getOriginApp());
		assertNull(Test.NULL, msg.getRequestServiceActive());
	}

	/**
	 * Tests constructor with required params
	 */
	public void testRequiredParamsConstructor () {
		// test with param in constructor
		PerformAppServiceInteraction msg = new PerformAppServiceInteraction(Test.GENERAL_STRING,Test.GENERAL_STRING,Test.GENERAL_STRING);
		String serviceUri = msg.getServiceUri();
		String appServiceId = msg.getServiceID();
		String originApp = msg.getOriginApp();
		assertEquals(Test.MATCH, Test.GENERAL_STRING, serviceUri);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, appServiceId);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, originApp);
	}

	/**
	 * Tests a valid JSON construction of this RPC message.
	 */
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
		assertNotNull(Test.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PerformAppServiceInteraction cmd = new PerformAppServiceInteraction(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAppServiceInteraction.KEY_SERVICE_ID), cmd.getServiceID());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAppServiceInteraction.KEY_SERVICE_URI), cmd.getServiceUri());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAppServiceInteraction.KEY_ORIGIN_APP), cmd.getOriginApp());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, PerformAppServiceInteraction.KEY_REQUEST_SERVICE_ACTIVE), cmd.getRequestServiceActive());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
