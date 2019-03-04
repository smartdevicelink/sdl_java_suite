package com.smartdevicelink.test.rpc.responses;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DIDResult;
import com.smartdevicelink.proxy.rpc.ReadDIDResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ReadDIDResponse}
 */
public class ReadDIDResponseTest extends BaseRpcTests {
	
	@Override
	protected RPCMessage createMessage() {
		ReadDIDResponse msg = new ReadDIDResponse();
		
		msg.setDidResult(Test.GENERAL_DIDRESULT_LIST);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.READ_DID.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {			
			result.put(ReadDIDResponse.KEY_DID_RESULT, Test.JSON_DIDRESULTS);			
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
		List<DIDResult> testResults = ( (ReadDIDResponse) msg ).getDidResult();
		
		// Valid Tests
		assertTrue("Did results didn't match input data.", Validator.validateDIDResults(Test.GENERAL_DIDRESULT_LIST, testResults));
	
		// Invalid/Null Tests
		ReadDIDResponse msg = new ReadDIDResponse();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getDidResult());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			ReadDIDResponse cmd = new ReadDIDResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			JSONArray didResultArray = JsonUtils.readJsonArrayFromJsonObject(parameters, ReadDIDResponse.KEY_DID_RESULT);
			List<DIDResult> didResultList = new ArrayList<DIDResult>();
			for (int index = 0; index < didResultArray.length(); index++) {
				DIDResult chunk = new DIDResult(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)didResultArray.get(index)) );
				didResultList.add(chunk);
			}
			assertTrue(Test.MATCH,  Validator.validateDIDResults(didResultList, cmd.getDidResult()));
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }
}