package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SystemRequest;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SystemRequest}
 */
public class SystemRequestTests extends BaseRpcTests {
    
	@Override
	protected RPCMessage createMessage() {
		SystemRequest msg = new SystemRequest();

		msg.setLegacyData(TestValues.GENERAL_STRING_LIST);
		msg.setFileName(TestValues.GENERAL_STRING);
		msg.setRequestType(TestValues.GENERAL_REQUESTTYPE);
		msg.setRequestSubType(TestValues.GENERAL_STRING);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SYSTEM_REQUEST.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(SystemRequest.KEY_DATA, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
			result.put(SystemRequest.KEY_FILE_NAME, TestValues.GENERAL_STRING);
			result.put(SystemRequest.KEY_REQUEST_TYPE, TestValues.GENERAL_REQUESTTYPE);
			result.put(SystemRequest.KEY_REQUEST_SUB_TYPE, TestValues.GENERAL_STRING);
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
    	RequestType  testRequestType = ( (SystemRequest) msg ).getRequestType();
    	String  testRequestSubType   = ( (SystemRequest) msg ).getRequestSubType();
    	String       testFileName    = ( (SystemRequest) msg ).getFileName();
    	List<String> testLegacyData  = ( (SystemRequest) msg ).getLegacyData();
    	
    	// Valid Tests
	    assertEquals(TestValues.MATCH, TestValues.GENERAL_REQUESTTYPE, testRequestType);
	    assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testRequestSubType);
	    assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testFileName);
	    assertTrue(TestValues.TRUE, Validator.validateStringList(TestValues.GENERAL_STRING_LIST, testLegacyData));
    	
    	// Invalid/Null Tests
		SystemRequest msg = new SystemRequest();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getLegacyData());
		assertNull(TestValues.NULL, msg.getFileName());
		assertNull(TestValues.NULL, msg.getRequestType());
		assertNull(TestValues.NULL, msg.getRequestSubType());
		assertNull(TestValues.NULL, msg.getBulkData());
	}
	
	/**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SystemRequest cmd = new SystemRequest(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());
		
			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, SystemRequest.KEY_FILE_NAME), cmd.getFileName());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, SystemRequest.KEY_REQUEST_TYPE), cmd.getRequestType().toString());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, SystemRequest.KEY_REQUEST_SUB_TYPE), cmd.getRequestSubType());

			List<String> dataList = JsonUtils.readStringListFromJsonObject(parameters, SystemRequest.KEY_DATA);
			List<String> testDataList = cmd.getLegacyData();
			assertEquals(TestValues.MATCH, dataList.size(), testDataList.size());
			assertTrue(TestValues.TRUE, Validator.validateStringList(dataList, testDataList));
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}    	
    }	
}