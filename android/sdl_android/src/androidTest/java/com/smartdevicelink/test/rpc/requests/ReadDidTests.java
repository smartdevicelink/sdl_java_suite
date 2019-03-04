package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ReadDID;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ReadDID}
 */
public class ReadDidTests extends BaseRpcTests {
	
	@Override
	protected RPCMessage createMessage() {
		ReadDID msg = new ReadDID();

		msg.setEcuName(Test.GENERAL_INT);
		msg.setDidLocation(Test.GENERAL_INTEGER_LIST);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.READ_DID.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(ReadDID.KEY_ECU_NAME, Test.GENERAL_INT);
			result.put(ReadDID.KEY_DID_LOCATION, JsonUtils.createJsonArray(Test.GENERAL_INTEGER_LIST));			
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
		Integer testEcuName = ( (ReadDID) msg ).getEcuName();
		List<Integer> testDidLocation = ( (ReadDID) msg ).getDidLocation();
		
		// Valid Tests
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, testEcuName);
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER_LIST, testDidLocation);
		
		// Invalid/Null Tests
		ReadDID msg = new ReadDID();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getEcuName());
		assertNull(Test.NULL, msg.getDidLocation());
	}

	/**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			ReadDID cmd = new ReadDID(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, ReadDID.KEY_ECU_NAME), cmd.getEcuName());

			List<Integer> didLocationList = JsonUtils.readIntegerListFromJsonObject(parameters, ReadDID.KEY_DID_LOCATION);
			List<Integer> testLocationList = cmd.getDidLocation();
			assertEquals(Test.MATCH, didLocationList.size(), testLocationList.size());
			assertTrue(Test.TRUE, Validator.validateIntegerList(didLocationList, testLocationList));
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }	
}