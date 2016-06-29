package com.smartdevicelink.test.rpc.requests;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AlertManeuver;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.AlertManeuver}
 */
public class AlertManeuverTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		AlertManeuver msg = new AlertManeuver();
				
		msg.setTtsChunks(Test.GENERAL_TTSCHUNK_LIST);
        msg.setSoftButtons(Test.GENERAL_SOFTBUTTON_LIST);
        
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
			result.put(AlertManeuver.KEY_TTS_CHUNKS, Test.JSON_TTSCHUNKS);
            result.put(AlertManeuver.KEY_SOFT_BUTTONS, Test.JSON_SOFTBUTTONS);			
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
        
        return result;
	}
	
	/**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {  
		// Test Values
		List<TTSChunk> testTtsChunks = ( (AlertManeuver) msg ).getTtsChunks();
		List<SoftButton> testSoftButtons = ( (AlertManeuver) msg ).getSoftButtons();
		
		// Valid Tests
		assertTrue(Test.TRUE, Validator.validateSoftButtons(Test.GENERAL_SOFTBUTTON_LIST, testSoftButtons));
		assertTrue(Test.TRUE, Validator.validateTtsChunks(Test.GENERAL_TTSCHUNK_LIST, testTtsChunks));
		
		// Invalid/Null Tests
        AlertManeuver msg = new AlertManeuver();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getTtsChunks());
        assertNull(Test.NULL, msg.getSoftButtons());
    }
	
	/**
     * Tests a valid JSON construction of this RPC message.
     */
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(),  getMessageType());
		assertNotNull(Test.NOT_NULL, commandJson);
		
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			AlertManeuver cmd = new AlertManeuver(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());
			
			JSONObject parameters   = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			JSONArray ttsChunkArray = JsonUtils.readJsonArrayFromJsonObject(parameters, AlertManeuver.KEY_TTS_CHUNKS);
			
			List<TTSChunk> ttsChunkList = new ArrayList<TTSChunk>();
			for (int index = 0; index < ttsChunkArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)ttsChunkArray.get(index)));
	        	ttsChunkList.add(chunk);
			}
			assertTrue(Test.TRUE, Validator.validateTtsChunks(ttsChunkList, cmd.getTtsChunks()));
			
			JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, AlertManeuver.KEY_SOFT_BUTTONS);
			List<SoftButton> softButtonList = new ArrayList<SoftButton>();
			for (int index = 0; index < softButtonArray.length(); index++) {
				SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject((JSONObject)softButtonArray.get(index)));
				softButtonList.add(chunk);
			}
			assertTrue(Test.TRUE, Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));			
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}