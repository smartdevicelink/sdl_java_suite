package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AlertManeuver;
import com.smartdevicelink.proxy.rpc.SoftButton;
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

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.AlertManeuver}
 */
public class AlertManeuverTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		AlertManeuver msg = new AlertManeuver();
				
		msg.setTtsChunks(TestValues.GENERAL_TTSCHUNK_LIST);
        msg.setSoftButtons(TestValues.GENERAL_SOFTBUTTON_LIST);
        
		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.ALERT_MANEUVER.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();
        
        try {
			result.put(AlertManeuver.KEY_TTS_CHUNKS, TestValues.JSON_TTSCHUNKS);
            result.put(AlertManeuver.KEY_SOFT_BUTTONS, TestValues.JSON_SOFTBUTTONS);
        } catch(JSONException e){
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
		List<TTSChunk> testTtsChunks = ( (AlertManeuver) msg ).getTtsChunks();
		List<SoftButton> testSoftButtons = ( (AlertManeuver) msg ).getSoftButtons();
		
		// Valid Tests
		assertTrue(TestValues.TRUE, Validator.validateSoftButtons(TestValues.GENERAL_SOFTBUTTON_LIST, testSoftButtons));
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(TestValues.GENERAL_TTSCHUNK_LIST, testTtsChunks));
		
		// Invalid/Null Tests
        AlertManeuver msg = new AlertManeuver();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getTtsChunks());
        assertNull(TestValues.NULL, msg.getSoftButtons());
    }
	
	/**
     * Tests a valid JSON construction of this RPC message.
     */
	@Test
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(getTargetContext(), getCommandType(),  getMessageType());
		assertNotNull(TestValues.NOT_NULL, commandJson);
		
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			AlertManeuver cmd = new AlertManeuver(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());
			
			JSONObject parameters   = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			JSONArray ttsChunkArray = JsonUtils.readJsonArrayFromJsonObject(parameters, AlertManeuver.KEY_TTS_CHUNKS);
			
			List<TTSChunk> ttsChunkList = new ArrayList<TTSChunk>();
			for (int index = 0; index < ttsChunkArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)ttsChunkArray.get(index)));
	        	ttsChunkList.add(chunk);
			}
			assertTrue(TestValues.TRUE, Validator.validateTtsChunks(ttsChunkList, cmd.getTtsChunks()));
			
			JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, AlertManeuver.KEY_SOFT_BUTTONS);
			List<SoftButton> softButtonList = new ArrayList<SoftButton>();
			for (int index = 0; index < softButtonArray.length(); index++) {
				SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject((JSONObject)softButtonArray.get(index)));
				softButtonList.add(chunk);
			}
			assertTrue(TestValues.TRUE, Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}