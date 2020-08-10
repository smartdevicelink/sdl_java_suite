package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ReadDID;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static androidx.test.InstrumentationRegistry.getTargetContext;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ReadDID}
 */
public class ReadDidTests extends BaseRpcTests {
	
	@Override
	protected RPCMessage createMessage() {
		ReadDID msg = new ReadDID();

		msg.setEcuName(TestValues.GENERAL_INT);
		msg.setDidLocation(TestValues.GENERAL_INTEGER_LIST);

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
			result.put(ReadDID.KEY_ECU_NAME, TestValues.GENERAL_INT);
			result.put(ReadDID.KEY_DID_LOCATION, JsonUtils.createJsonArray(TestValues.GENERAL_INTEGER_LIST));
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
		Integer testEcuName = ( (ReadDID) msg ).getEcuName();
		List<Integer> testDidLocation = ( (ReadDID) msg ).getDidLocation();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, testEcuName);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER_LIST, testDidLocation);
		
		// Invalid/Null Tests
		ReadDID msg = new ReadDID();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getEcuName());
		assertNull(TestValues.NULL, msg.getDidLocation());
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
			ReadDID cmd = new ReadDID(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, ReadDID.KEY_ECU_NAME), cmd.getEcuName());

			List<Integer> didLocationList = JsonUtils.readIntegerListFromJsonObject(parameters, ReadDID.KEY_DID_LOCATION);
			List<Integer> testLocationList = cmd.getDidLocation();
			assertEquals(TestValues.MATCH, didLocationList.size(), testLocationList.size());
			assertTrue(TestValues.TRUE, Validator.validateIntegerList(didLocationList, testLocationList));
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}    	
    }	
}