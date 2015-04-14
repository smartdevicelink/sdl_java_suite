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
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class AlertManeuverTests extends BaseRpcTests {
	
	private final List<TTSChunk> TTS_CHUNK_LIST = new ArrayList<TTSChunk>();
	private static final String TTS_CHUNK_TEXT_1 = "Welcome to the jungle";
	private static final SpeechCapabilities TTS_CHUNK_SPEECH_1 = SpeechCapabilities.TEXT;
	private static final String TTS_CHUNK_TEXT_2 = "Say a command";
	private static final SpeechCapabilities TTS_CHUNK_SPEECH_2 = SpeechCapabilities.LHPLUS_PHONEMES;

    private final List<SoftButton> SOFT_BUTTON_LIST = new ArrayList<SoftButton>();
    private static final Boolean SOFT_BUTTON_HIGHLIGHTED = true;
    private static final Integer SOFT_BUTTON_ID = 236;
	private static final SystemAction SOFT_BUTTON_SYSTEM_ACTION = SystemAction.STEAL_FOCUS;
    private static final String SOFT_BUTTON_TEXT = "Hello";
    private static final SoftButtonType SOFT_BUTTON_KEY_TYPE = SoftButtonType.SBT_TEXT;
    
    private static final Image SOFT_BUTTON_IMAGE = new Image();
    private static final String SOFT_BUTTON_VALUE = "buttonImage.jpg";
    private static final ImageType SOFT_BUTTON_IMAGE_TYPE = ImageType.STATIC;

	@Override
	protected RPCMessage createMessage() {
		AlertManeuver msg = new AlertManeuver();
		
		createCustomObjects();
		
		msg.setTtsChunks(TTS_CHUNK_LIST);
        msg.setSoftButtons(SOFT_BUTTON_LIST);
        
		return msg;
	}
	
	private void createCustomObjects() {
		TTSChunk ttsChunk = new TTSChunk();
		ttsChunk.setText(TTS_CHUNK_TEXT_1);
		ttsChunk.setType(TTS_CHUNK_SPEECH_1);
		TTS_CHUNK_LIST.add(ttsChunk);
		
		ttsChunk = new TTSChunk();
		ttsChunk.setText(TTS_CHUNK_TEXT_2);
		ttsChunk.setType(TTS_CHUNK_SPEECH_2);
		TTS_CHUNK_LIST.add(ttsChunk);
		
		SOFT_BUTTON_IMAGE.setValue(SOFT_BUTTON_VALUE);
		SOFT_BUTTON_IMAGE.setImageType(SOFT_BUTTON_IMAGE_TYPE);
		
		SoftButton softButton = new SoftButton();
		softButton.setIsHighlighted(SOFT_BUTTON_HIGHLIGHTED);
		softButton.setSoftButtonID(SOFT_BUTTON_ID);
		softButton.setSystemAction(SOFT_BUTTON_SYSTEM_ACTION);
		softButton.setText(SOFT_BUTTON_TEXT);
		softButton.setType(SOFT_BUTTON_KEY_TYPE);
		softButton.setImage(SOFT_BUTTON_IMAGE);
		SOFT_BUTTON_LIST.add(softButton);
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.ALERT_MANEUVER;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();
        JSONArray ttsChunks = new JSONArray();
        JSONArray softButtons = new JSONArray();
        
        try {
        	
        	JSONObject ttsChunk = new JSONObject();
			ttsChunk.put(TTSChunk.KEY_TEXT, TTS_CHUNK_TEXT_1);
			ttsChunk.put(TTSChunk.KEY_TYPE, TTS_CHUNK_SPEECH_1);
			ttsChunks.put(ttsChunk);
			
			ttsChunk = new JSONObject();
			ttsChunk.put(TTSChunk.KEY_TEXT, TTS_CHUNK_TEXT_2);
			ttsChunk.put(TTSChunk.KEY_TYPE, TTS_CHUNK_SPEECH_2);
			ttsChunks.put(ttsChunk);
			
			JSONObject softButton = new JSONObject();
			softButton.put(SoftButton.KEY_IS_HIGHLIGHTED , SOFT_BUTTON_HIGHLIGHTED);
			softButton.put(SoftButton.KEY_SOFT_BUTTON_ID, SOFT_BUTTON_ID);
			softButton.put(SoftButton.KEY_SYSTEM_ACTION, SOFT_BUTTON_SYSTEM_ACTION);
			softButton.put(SoftButton.KEY_TEXT, SOFT_BUTTON_TEXT);
			softButton.put(SoftButton.KEY_TYPE, SOFT_BUTTON_KEY_TYPE);
			softButton.put(SoftButton.KEY_IMAGE, SOFT_BUTTON_IMAGE.serializeJSON());
			softButtons.put(softButton);
			
			result.put(AlertManeuver.KEY_TTS_CHUNKS, ttsChunks);
            result.put(AlertManeuver.KEY_SOFT_BUTTONS, softButtons);
			
        } catch(JSONException e){
            /* do nothing */
        }
        
        return result;
	}
	
	public void testTtsChunks () {
		List<TTSChunk> copy = ( (AlertManeuver) msg ).getTtsChunks();
		
		assertTrue("Input value didn't match expected value.", 
					Validator.validateTtsChunks(TTS_CHUNK_LIST, copy));
	}
	
	public void testSoftButtons () {
		List<SoftButton> copy = ( (AlertManeuver) msg ).getSoftButtons();
		
		assertTrue("Input value didn't match expected value.", 
					Validator.validateSoftButtons(SOFT_BUTTON_LIST, copy));
	}
	
	public void testNull(){
        AlertManeuver msg = new AlertManeuver();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("TTS chunks wasn't set, but getter method returned an object.", msg.getTtsChunks());
        assertNull("Soft buttons wasn't set, but getter method returned an object.", msg.getSoftButtons());
    }
	
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(getCommandType(),  getMessageType());
		assertNotNull("Command object is null", commandJson);
		
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			AlertManeuver cmd = new AlertManeuver(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", 
						  JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), 
						  cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", 
						  JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), 
						  cmd.getCorrelationID());
			
			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			JSONArray ttsChunkArray = JsonUtils.readJsonArrayFromJsonObject(parameters, AlertManeuver.KEY_TTS_CHUNKS);
			
			List<TTSChunk> ttsChunkList = new ArrayList<TTSChunk>();
			for (int index = 0; index < ttsChunkArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)ttsChunkArray.get(index)));
	        	ttsChunkList.add(chunk);
			}
			assertTrue("TTSChunk list doesn't match input TTSChunk list", 
						Validator.validateTtsChunks(ttsChunkList, cmd.getTtsChunks()));
			
			JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, AlertManeuver.KEY_SOFT_BUTTONS);
			List<SoftButton> softButtonList = new ArrayList<SoftButton>();
			for (int index = 0; index < softButtonArray.length(); index++) {
				SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject((JSONObject)softButtonArray.get(index)));
				softButtonList.add(chunk);
			}
			assertTrue("Soft button list doesn't match input button list", 
						Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}