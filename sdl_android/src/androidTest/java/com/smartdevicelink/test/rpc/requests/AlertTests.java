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
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.Alert}
 */
public class AlertTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        Alert msg = new Alert();

        msg.setDuration(Test.GENERAL_INT);
        msg.setAlertText1(Test.GENERAL_STRING);
        msg.setAlertText2(Test.GENERAL_STRING);
        msg.setAlertText3(Test.GENERAL_STRING);
        msg.setPlayTone(Test.GENERAL_BOOLEAN);
        msg.setProgressIndicator(Test.GENERAL_BOOLEAN);
        msg.setTtsChunks(Test.GENERAL_TTSCHUNK_LIST);
        msg.setSoftButtons(Test.GENERAL_SOFTBUTTON_LIST);
        
        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ALERT.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();
        
        try{        	
            result.put(Alert.KEY_DURATION, Test.GENERAL_INT);
            result.put(Alert.KEY_ALERT_TEXT_1, Test.GENERAL_STRING);
            result.put(Alert.KEY_ALERT_TEXT_2, Test.GENERAL_STRING);
            result.put(Alert.KEY_ALERT_TEXT_3, Test.GENERAL_STRING);
            result.put(Alert.KEY_PLAY_TONE, Test.GENERAL_BOOLEAN);
            result.put(Alert.KEY_PROGRESS_INDICATOR, Test.GENERAL_BOOLEAN);
            result.put(Alert.KEY_TTS_CHUNKS, Test.JSON_TTSCHUNKS);
            result.put(Alert.KEY_SOFT_BUTTONS, Test.JSON_SOFTBUTTONS);
        }catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }

        return result;
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () { 
		// Test Values
        int testDuration      = ( (Alert) msg ).getDuration();        
        String testAlertText1 = ( (Alert) msg ).getAlertText1();        
        String testAlertText2 = ( (Alert) msg ).getAlertText2();        
        String testAlertText3 = ( (Alert) msg ).getAlertText3();
        boolean testPlayTone  = ( (Alert) msg ).getPlayTone();
    	boolean testProgressIndicator    = ( (Alert) msg ).getProgressIndicator();
		List<TTSChunk> testTtsChunks     = ( (Alert) msg ).getTtsChunks();
		List<SoftButton> testSoftButtons = ( (Alert) msg ).getSoftButtons();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_INT, testDuration);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testAlertText1);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testAlertText2);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testAlertText3);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, testPlayTone);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, testProgressIndicator);
		assertTrue(Test.TRUE, Validator.validateSoftButtons(Test.GENERAL_SOFTBUTTON_LIST, testSoftButtons));
		assertTrue(Test.TRUE, Validator.validateTtsChunks(Test.GENERAL_TTSCHUNK_LIST, testTtsChunks));
	    	
    	// Invalid/Null Tests
        Alert msg = new Alert();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getAlertText1());
        assertNull(Test.NULL, msg.getAlertText2());
        assertNull(Test.NULL, msg.getAlertText3());
        assertNull(Test.NULL, msg.getDuration());
        assertNull(Test.NULL, msg.getPlayTone());
        assertNull(Test.NULL, msg.getProgressIndicator());
        assertNull(Test.NULL, msg.getTtsChunks());
        assertNull(Test.NULL, msg.getSoftButtons());
    }
    
    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			Alert cmd = new Alert(hash);
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, Alert.KEY_PLAY_TONE), cmd.getPlayTone());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, Alert.KEY_DURATION), cmd.getDuration());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, Alert.KEY_ALERT_TEXT_1), cmd.getAlertText1());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, Alert.KEY_ALERT_TEXT_2), cmd.getAlertText2());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, Alert.KEY_ALERT_TEXT_3), cmd.getAlertText3());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, Alert.KEY_PROGRESS_INDICATOR), cmd.getProgressIndicator());
			
			JSONArray ttsChunkArray = JsonUtils.readJsonArrayFromJsonObject(parameters, Alert.KEY_TTS_CHUNKS);
			List<TTSChunk> ttsChunkList = new ArrayList<TTSChunk>();
			for (int index = 0; index < ttsChunkArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)ttsChunkArray.get(index)) );
	        	ttsChunkList.add(chunk);
			}
			assertTrue(Test.TRUE,  Validator.validateTtsChunks(ttsChunkList, cmd.getTtsChunks()));
			
			JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, Alert.KEY_SOFT_BUTTONS);
			List<SoftButton> softButtonList = new ArrayList<SoftButton>();
			for (int index = 0; index < softButtonArray.length(); index++) {
				SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)softButtonArray.get(index)) );
				softButtonList.add(chunk);
			}
			assertTrue(Test.TRUE,  Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }
}