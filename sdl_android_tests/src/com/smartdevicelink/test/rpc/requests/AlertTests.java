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

public class AlertTests extends BaseRpcTests{
    private static final int     DURATION           = 500;
    private static final String  ALERT_TEXT_1       = "Line #1";
    private static final String  ALERT_TEXT_2       = "Line #2";
    private static final String  ALERT_TEXT_3       = "Line #3";
    private static final boolean PLAY_TONE          = true;
    private static final boolean PROGRESS_INDICATOR = true;
    
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
    //TODO: everytime this method is called two additional tts chunks and one additional soft button is added to the lists.
    //this is the cause for the failing of the testJson tests. ensure that this doesn't happen (fixed if defensive copying works?)
    //the same problem is for ScrollableMessageTest
    @Override
    protected RPCMessage createMessage(){
        Alert msg = new Alert();

        createCustomObjects();
        
        msg.setDuration(DURATION);
        msg.setAlertText1(ALERT_TEXT_1);
        msg.setAlertText2(ALERT_TEXT_2);
        msg.setAlertText3(ALERT_TEXT_3);
        msg.setPlayTone(PLAY_TONE);
        msg.setProgressIndicator(PROGRESS_INDICATOR);
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
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ALERT;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();
        JSONArray ttsChunks = new JSONArray();
        JSONArray softButtons = new JSONArray();
        
        try{
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
        	
            result.put(Alert.KEY_DURATION, DURATION);
            result.put(Alert.KEY_ALERT_TEXT_1, ALERT_TEXT_1);
            result.put(Alert.KEY_ALERT_TEXT_2, ALERT_TEXT_2);
            result.put(Alert.KEY_ALERT_TEXT_3, ALERT_TEXT_3);
            result.put(Alert.KEY_PLAY_TONE, PLAY_TONE);
            result.put(Alert.KEY_PROGRESS_INDICATOR, PROGRESS_INDICATOR);
            result.put(Alert.KEY_TTS_CHUNKS, ttsChunks);
            result.put(Alert.KEY_SOFT_BUTTONS, softButtons);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testDuration(){
        int duration = ( (Alert) msg ).getDuration();
        assertEquals("Duration didn't match input duration.", DURATION, duration);
    }

    public void testAlertText1(){
        String alertText1 = ( (Alert) msg ).getAlertText1();
        assertEquals("Alert text 1 didn't match input text.", ALERT_TEXT_1, alertText1);
    }

    public void testAlertText2(){
        String alertText2 = ( (Alert) msg ).getAlertText2();
        assertEquals("Alert text 2 didn't match input text.", ALERT_TEXT_2, alertText2);
    }

    public void testAlertText3(){
        String alertText3 = ( (Alert) msg ).getAlertText3();
        assertEquals("Alert text 3 didn't match input text.", ALERT_TEXT_3, alertText3);
    }

    public void testPlayTone(){
        boolean playTone = ( (Alert) msg ).getPlayTone();
        assertEquals("Play tone didn't match input play tone.", PLAY_TONE, playTone);
    }

    public void testProgressIndicator(){
        boolean progressIndicator = ( (Alert) msg ).getProgressIndicator();
        assertEquals("Progress indicator didn't match input progress indicator.", PROGRESS_INDICATOR, progressIndicator);
    }

	public void testTtsChunks () {
		List<TTSChunk> copy = ( (Alert) msg ).getTtsChunks();
		
		assertTrue("Input value didn't match expected value.", Validator.validateTtsChunks(TTS_CHUNK_LIST, copy));

	}
	
	public void testSoftButtons () {
		List<SoftButton> copy = ( (Alert) msg ).getSoftButtons();
		
		assertTrue("Input value didn't match expected value.", Validator.validateSoftButtons(SOFT_BUTTON_LIST, copy));
	}
    
    public void testNull(){
        Alert msg = new Alert();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Alert text 1 wasn't set, but getter method returned an object.", msg.getAlertText1());
        assertNull("Alert text 2 wasn't set, but getter method returned an object.", msg.getAlertText2());
        assertNull("Alert text 3 wasn't set, but getter method returned an object.", msg.getAlertText3());
        assertNull("Duration wasn't set, but getter method returned an object.", msg.getDuration());
        assertNull("Play tone wasn't set, but getter method returned an object.", msg.getPlayTone());
        assertNull("Progress indicator wasn't set, but getter method returned an object.", msg.getProgressIndicator());
        assertNull("TTS chunks wasn't set, but getter method returned an object.", msg.getTtsChunks());
        assertNull("Soft buttons wasn't set, but getter method returned an object.", msg.getSoftButtons());
    }
    
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			Alert cmd = new Alert(hash);
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Play tone doesn't match input tone", JsonUtils.readBooleanFromJsonObject(parameters, Alert.KEY_PLAY_TONE), cmd.getPlayTone());
			assertEquals("Duration doesn't match input duration", JsonUtils.readIntegerFromJsonObject(parameters, Alert.KEY_DURATION), cmd.getDuration());
			assertEquals("Alert text 1 doesn't match input text", JsonUtils.readStringFromJsonObject(parameters, Alert.KEY_ALERT_TEXT_1), cmd.getAlertText1());
			assertEquals("Alert text 2 doesn't match input text", JsonUtils.readStringFromJsonObject(parameters, Alert.KEY_ALERT_TEXT_2), cmd.getAlertText2());
			assertEquals("Alert text 3 doesn't match input text", JsonUtils.readStringFromJsonObject(parameters, Alert.KEY_ALERT_TEXT_3), cmd.getAlertText3());
			assertEquals("Progress indicator doesn't match input indicator", 
					JsonUtils.readBooleanFromJsonObject(parameters, Alert.KEY_PROGRESS_INDICATOR), cmd.getProgressIndicator());
			
			JSONArray ttsChunkArray = JsonUtils.readJsonArrayFromJsonObject(parameters, Alert.KEY_TTS_CHUNKS);
			List<TTSChunk> ttsChunkList = new ArrayList<TTSChunk>();
			for (int index = 0; index < ttsChunkArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)ttsChunkArray.get(index)) );
	        	ttsChunkList.add(chunk);
			}
			assertTrue("TTSChunk list doesn't match input TTSChunk list",  Validator.validateTtsChunks(ttsChunkList, cmd.getTtsChunks()));
			
			JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, Alert.KEY_SOFT_BUTTONS);
			List<SoftButton> softButtonList = new ArrayList<SoftButton>();
			for (int index = 0; index < softButtonArray.length(); index++) {
				SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)softButtonArray.get(index)) );
				softButtonList.add(chunk);
			}
			assertTrue("Soft button list doesn't match input button list",  Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));
			
						
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
