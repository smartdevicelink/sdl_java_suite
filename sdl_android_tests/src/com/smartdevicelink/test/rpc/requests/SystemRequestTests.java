package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SystemRequest;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SystemRequest}
 */
public class SystemRequestTests extends BaseRpcTests {
    
	@Override
	protected RPCMessage createMessage() {
		SystemRequest msg = new SystemRequest();

		msg.setLegacyData(Test.GENERAL_STRING_LIST);
		msg.setFileName(Test.GENERAL_STRING);
		msg.setRequestType(Test.GENERAL_REQUESTTYPE);

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
			result.put(SystemRequest.KEY_DATA, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));
			result.put(SystemRequest.KEY_FILE_NAME, Test.GENERAL_STRING);
			result.put(SystemRequest.KEY_REQUEST_TYPE, Test.GENERAL_REQUESTTYPE);			
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
    	RequestType  testRequestType = ( (SystemRequest) msg ).getRequestType();
    	String       testFileName    = ( (SystemRequest) msg ).getFileName();
    	List<String> testLegacyData  = ( (SystemRequest) msg ).getLegacyData();
    	
    	// Valid Tests
	    assertEquals(Test.MATCH, Test.GENERAL_REQUESTTYPE, testRequestType);
	    assertEquals(Test.MATCH, Test.GENERAL_STRING, testFileName);
	    assertTrue(Test.TRUE, Validator.validateStringList(Test.GENERAL_STRING_LIST, testLegacyData));
    	
    	// Invalid/Null Tests
		SystemRequest msg = new SystemRequest();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getLegacyData());
		assertNull(Test.NULL, msg.getFileName());
		assertNull(Test.NULL, msg.getRequestType());
		assertNull(Test.NULL, msg.getBulkData());
	}
	
	/**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SystemRequest cmd = new SystemRequest(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());
		
			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, SystemRequest.KEY_FILE_NAME), cmd.getFileName());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, SystemRequest.KEY_REQUEST_TYPE), cmd.getRequestType().toString());

			List<String> dataList = JsonUtils.readStringListFromJsonObject(parameters, SystemRequest.KEY_DATA);
			List<String> testDataList = cmd.getLegacyData();
			assertEquals(Test.MATCH, dataList.size(), testDataList.size());
			assertTrue(Test.TRUE, Validator.validateStringList(dataList, testDataList));
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }	
}