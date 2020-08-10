package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Speak;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static androidx.test.InstrumentationRegistry.getTargetContext;

public class SpeakTests extends BaseRpcTests {
	
	@Override
	protected RPCMessage createMessage() {
		Speak msg = new Speak();

		msg.setTtsChunks(TestValues.GENERAL_TTSCHUNK_LIST);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SPEAK.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();
		
		try {	        
			result.put(Speak.KEY_TTS_CHUNKS, TestValues.JSON_TTSCHUNKS);
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
		List<TTSChunk> copy = ( (Speak) msg ).getTtsChunks();
		
		// Valid Tests
	    assertTrue(TestValues.TRUE, Validator.validateTtsChunks(TestValues.GENERAL_TTSCHUNK_LIST, copy));
	
	    // Invalid/Null Tests
		Speak msg = new Speak();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.MATCH, msg.getTtsChunks());
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
			Speak cmd = new Speak(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			JSONArray ttsChunkArray = JsonUtils.readJsonArrayFromJsonObject(parameters, Speak.KEY_TTS_CHUNKS);
			List<TTSChunk> ttsChunkList = new ArrayList<TTSChunk>();
			for (int index = 0; index < ttsChunkArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)ttsChunkArray.get(index)) );
	        	ttsChunkList.add(chunk);
			}
			assertTrue(TestValues.TRUE,  Validator.validateTtsChunks(ttsChunkList, cmd.getTtsChunks()));
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }
}