package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AppServiceRecord;
import com.smartdevicelink.proxy.rpc.PublishAppServiceResponse;
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
import static android.support.test.InstrumentationRegistry.getContext;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.PublishAppServiceResponse}
 */
public class PublishAppServiceResponseTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage(){

		PublishAppServiceResponse msg = new PublishAppServiceResponse();

		msg.setServiceRecord(TestValues.GENERAL_APPSERVICERECORD);

		return msg;
	}

	@Override
	protected String getMessageType(){
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType(){
		return FunctionID.PUBLISH_APP_SERVICE.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion){
		JSONObject result = new JSONObject();

		try{
			result.put(PublishAppServiceResponse.KEY_APP_SERVICE_RECORD, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_APPSERVICERECORD.getStore()));
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
		AppServiceRecord testRecord = ( (PublishAppServiceResponse) msg ).getServiceRecord();

		// Valid Tests
		assertTrue(TestValues.TRUE, Validator.validateAppServiceRecord(TestValues.GENERAL_APPSERVICERECORD, testRecord));

		// Invalid/Null Tests
		PublishAppServiceResponse msg = new PublishAppServiceResponse();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getServiceRecord());
	}

	/**
	 * Tests a valid JSON construction of this RPC message.
	 */
	@Test
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(getContext(), getCommandType(), getMessageType());
		assertNotNull(TestValues.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PublishAppServiceResponse cmd = new PublishAppServiceResponse (hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			JSONObject appServiceRecordObject = JsonUtils.readJsonObjectFromJsonObject(parameters, PublishAppServiceResponse.KEY_APP_SERVICE_RECORD);
			AppServiceRecord recordTest = new AppServiceRecord(JsonRPCMarshaller.deserializeJSONObject(appServiceRecordObject));
			assertTrue(TestValues.TRUE,  Validator.validateAppServiceRecord(recordTest, cmd.getServiceRecord()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
