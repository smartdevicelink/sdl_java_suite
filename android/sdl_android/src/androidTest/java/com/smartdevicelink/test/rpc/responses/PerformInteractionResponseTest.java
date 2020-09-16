package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.PerformInteractionResponse}
 */
public class PerformInteractionResponseTest extends BaseRpcTests {
    
	@Override
	protected RPCMessage createMessage() {
		PerformInteractionResponse msg = new PerformInteractionResponse();

		msg.setChoiceID(TestValues.GENERAL_INT);
		msg.setTriggerSource(TestValues.GENERAL_TRIGGERSOURCE);
		msg.setManualTextEntry(TestValues.GENERAL_STRING);

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
			result.put(PerformInteractionResponse.KEY_CHOICE_ID, TestValues.GENERAL_INT);
			result.put(PerformInteractionResponse.KEY_TRIGGER_SOURCE, TestValues.GENERAL_TRIGGERSOURCE);
			result.put(PerformInteractionResponse.KEY_MANUAL_TEXT_ENTRY, TestValues.GENERAL_STRING);
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
		Integer testId = ( (PerformInteractionResponse) msg).getChoiceID();
		TriggerSource testSource = ( (PerformInteractionResponse) msg).getTriggerSource();
		String testText = ( (PerformInteractionResponse) msg).getManualTextEntry();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, testId);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_TRIGGERSOURCE, testSource);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testText);
	
		// Invalid/Null Tests
		PerformInteractionResponse msg = new PerformInteractionResponse();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getChoiceID());
		assertNull(TestValues.NULL, msg.getTriggerSource());
		assertNull(TestValues.NULL, msg.getManualTextEntry());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getInstrumentation().getContext(), getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PerformInteractionResponse cmd = new PerformInteractionResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformInteractionResponse.KEY_MANUAL_TEXT_ENTRY), cmd.getManualTextEntry());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformInteractionResponse.KEY_TRIGGER_SOURCE), cmd.getTriggerSource().toString());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, PerformInteractionResponse.KEY_CHOICE_ID), cmd.getChoiceID());
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }
}