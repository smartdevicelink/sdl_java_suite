package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.PerformAppServiceInteractionResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;


/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.PerformAppServiceInteractionResponse}
 */
public class PerformAppServiceInteractionResponseTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage(){

		PerformAppServiceInteractionResponse msg = new PerformAppServiceInteractionResponse();

		msg.setServiceSpecificResult(TestValues.GENERAL_STRING);

		return msg;
	}

	@Override
	protected String getMessageType(){
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType(){
		return FunctionID.PERFORM_APP_SERVICES_INTERACTION.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion){
		JSONObject result = new JSONObject();

		try{
			result.put(PerformAppServiceInteractionResponse.KEY_SERVICE_SPECIFIC_RESULT, TestValues.GENERAL_STRING);
		}catch(JSONException e){
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
		String serviceSpecificResult = ( (PerformAppServiceInteractionResponse) msg ).getServiceSpecificResult();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, serviceSpecificResult);

		// Invalid/Null Tests
		PerformAppServiceInteractionResponse msg = new PerformAppServiceInteractionResponse();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getServiceSpecificResult());
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
			PerformAppServiceInteractionResponse cmd = new PerformAppServiceInteractionResponse (hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAppServiceInteractionResponse.KEY_SERVICE_SPECIFIC_RESULT), cmd.getServiceSpecificResult());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
