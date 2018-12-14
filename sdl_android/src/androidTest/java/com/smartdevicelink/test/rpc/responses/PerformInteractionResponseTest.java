package com.smartdevicelink.test.rpc.responses;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.PerformInteractionResponse}
 */
public class PerformInteractionResponseTest extends BaseRpcTests {
    
	@Override
	protected RPCMessage createMessage() {
		PerformInteractionResponse msg = new PerformInteractionResponse();

		msg.setChoiceID(Test.GENERAL_INT);
		msg.setTriggerSource(Test.GENERAL_TRIGGERSOURCE);
		msg.setManualTextEntry(Test.GENERAL_STRING);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.PERFORM_INTERACTION.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(PerformInteractionResponse.KEY_CHOICE_ID, Test.GENERAL_INT);
			result.put(PerformInteractionResponse.KEY_TRIGGER_SOURCE, Test.GENERAL_TRIGGERSOURCE);
			result.put(PerformInteractionResponse.KEY_MANUAL_TEXT_ENTRY, Test.GENERAL_STRING);
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
		Integer testId = ( (PerformInteractionResponse) msg).getChoiceID();
		TriggerSource testSource = ( (PerformInteractionResponse) msg).getTriggerSource();
		String testText = ( (PerformInteractionResponse) msg).getManualTextEntry();
		
		// Valid Tests
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, testId);
		assertEquals(Test.MATCH, Test.GENERAL_TRIGGERSOURCE, testSource);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testText);
	
		// Invalid/Null Tests
		PerformInteractionResponse msg = new PerformInteractionResponse();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getChoiceID());
		assertNull(Test.NULL, msg.getTriggerSource());
		assertNull(Test.NULL, msg.getManualTextEntry());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PerformInteractionResponse cmd = new PerformInteractionResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformInteractionResponse.KEY_MANUAL_TEXT_ENTRY), cmd.getManualTextEntry());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformInteractionResponse.KEY_TRIGGER_SOURCE), cmd.getTriggerSource().toString());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, PerformInteractionResponse.KEY_CHOICE_ID), cmd.getChoiceID());
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }
}