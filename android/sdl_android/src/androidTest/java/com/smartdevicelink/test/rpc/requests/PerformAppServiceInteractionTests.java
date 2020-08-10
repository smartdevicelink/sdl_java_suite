package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.PerformAppServiceInteraction;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;
import static androidx.test.platform.app.InstrumentationRegistry.getTargetContext;


public class PerformAppServiceInteractionTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		PerformAppServiceInteraction msg = new PerformAppServiceInteraction();

		msg.setServiceUri(TestValues.GENERAL_STRING);
		msg.setServiceID(TestValues.GENERAL_STRING);
		msg.setOriginApp(TestValues.GENERAL_STRING);
		msg.setRequestServiceActive(TestValues.GENERAL_BOOLEAN);

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
			result.put(PerformAppServiceInteraction.KEY_SERVICE_URI, TestValues.GENERAL_STRING);
			result.put(PerformAppServiceInteraction.KEY_SERVICE_ID, TestValues.GENERAL_STRING);
			result.put(PerformAppServiceInteraction.KEY_ORIGIN_APP, TestValues.GENERAL_STRING);
			result.put(PerformAppServiceInteraction.KEY_REQUEST_SERVICE_ACTIVE, TestValues.GENERAL_BOOLEAN);
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
		String serviceUri = ( (PerformAppServiceInteraction) msg ).getServiceUri();
		String appServiceId = ( (PerformAppServiceInteraction) msg ).getServiceID();
		String originApp = ( (PerformAppServiceInteraction) msg ).getOriginApp();
		boolean requestServiceActive = ( (PerformAppServiceInteraction) msg ).getRequestServiceActive();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, serviceUri);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, appServiceId);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, originApp);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, requestServiceActive);

		// Invalid/Null Tests
		PerformAppServiceInteraction msg = new PerformAppServiceInteraction();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getServiceUri());
		assertNull(TestValues.NULL, msg.getServiceID());
		assertNull(TestValues.NULL, msg.getOriginApp());
		assertNull(TestValues.NULL, msg.getRequestServiceActive());
	}

	/**
	 * Tests constructor with required params
	 */
	@Test
	public void testRequiredParamsConstructor () {
		// test with param in constructor
		PerformAppServiceInteraction msg = new PerformAppServiceInteraction(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING, TestValues.GENERAL_STRING);
		String serviceUri = msg.getServiceUri();
		String appServiceId = msg.getServiceID();
		String originApp = msg.getOriginApp();
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, serviceUri);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, appServiceId);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, originApp);
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
			PerformAppServiceInteraction cmd = new PerformAppServiceInteraction(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAppServiceInteraction.KEY_SERVICE_ID), cmd.getServiceID());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAppServiceInteraction.KEY_SERVICE_URI), cmd.getServiceUri());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAppServiceInteraction.KEY_ORIGIN_APP), cmd.getOriginApp());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, PerformAppServiceInteraction.KEY_REQUEST_SERVICE_ACTIVE), cmd.getRequestServiceActive());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
