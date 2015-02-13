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
import com.smartdevicelink.proxy.rpc.PerformAudioPassThru;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class PerformAudioPassThruTests extends BaseRpcTests {
	
	private static final List<TTSChunk> INITIAL_PROMPT  = new ArrayList<TTSChunk>();
	private static final String TEXT_1                  = "Text 1";
	private static final String TEXT_2                  = "Text 2";
	private static final SamplingRate SAMPLING_RATE     = SamplingRate._8KHZ;
	private static final AudioType AUDIO_TYPE           = AudioType.PCM;
	private static final BitsPerSample BITS_PER_SAMPLE  = BitsPerSample._8_BIT;
	private static final int MAX_DURATION               = 5;
	private static final boolean MUTE_AUDIO             = true;
	private static final SpeechCapabilities TEST_SPEECH = SpeechCapabilities.TEXT;
	private static final String HELLO_STRING 			= "Hello";
	
	private TTSChunk ttsChunk;
	
	@Override
	protected RPCMessage createMessage() {
		PerformAudioPassThru msg = new PerformAudioPassThru();
		
		createCustomObjects();
		
		msg.setInitialPrompt(INITIAL_PROMPT);
		msg.setAudioPassThruDisplayText1(TEXT_1);
		msg.setAudioPassThruDisplayText2(TEXT_2);
		msg.setSamplingRate(SAMPLING_RATE);
		msg.setAudioType(AUDIO_TYPE);
		msg.setBitsPerSample(BITS_PER_SAMPLE);
		msg.setMaxDuration(MAX_DURATION);
		msg.setMuteAudio(MUTE_AUDIO);
		return msg;
	}
	
	public void createCustomObjects () { 
		ttsChunk = new TTSChunk();
		ttsChunk.setType(TEST_SPEECH);
		ttsChunk.setText(HELLO_STRING);
		INITIAL_PROMPT.add(ttsChunk);
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.PERFORM_AUDIO_PASS_THRU;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(PerformAudioPassThru.KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_1,           TEXT_1);
			result.put(PerformAudioPassThru.KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_2,           TEXT_2);			
			result.put(PerformAudioPassThru.KEY_MUTE_AUDIO,      MUTE_AUDIO);	
			result.put(PerformAudioPassThru.KEY_MAX_DURATION,    MAX_DURATION);
			result.put(PerformAudioPassThru.KEY_AUDIO_TYPE,      AUDIO_TYPE);
			result.put(PerformAudioPassThru.KEY_SAMPLING_RATE,   SAMPLING_RATE);
			result.put(PerformAudioPassThru.KEY_BITS_PER_SAMPLE, BITS_PER_SAMPLE);
			
			JSONObject initialPromptObj = new JSONObject();
			initialPromptObj.put(TTSChunk.KEY_TYPE, TEST_SPEECH);
			initialPromptObj.put(TTSChunk.KEY_TEXT, HELLO_STRING);
			JSONArray initialPromptArray = new JSONArray();
			initialPromptArray.put(initialPromptObj);
			result.put(PerformAudioPassThru.KEY_INITIAL_PROMPT,  JsonUtils.createJsonArray(INITIAL_PROMPT));
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testInitialPrompt () {
		List<TTSChunk> copy = ( (PerformAudioPassThru) msg ).getInitialPrompt();
		assertNotSame("Initial prompt was not defensive copied.", INITIAL_PROMPT, copy);
		assertEquals("List size didn't match expected size.", INITIAL_PROMPT.size(), copy.size());
		
		for (int i = 0; i < INITIAL_PROMPT.size(); i++) {
			assertEquals("Input value didn't match expected value.", INITIAL_PROMPT.get(i), copy.get(i));
		}
	}
	
	public void testAudioPassThruDisplayText1 () {
		String copy = ( (PerformAudioPassThru) msg ).getAudioPassThruDisplayText1();
		
		assertEquals("Input value didn't match expected value.", TEXT_1, copy);
	}
	
	public void testAudioPassThruDisplayText2 () {
		String copy = ( (PerformAudioPassThru) msg ).getAudioPassThruDisplayText2();
		
		assertEquals("Input value didn't match expected value.", TEXT_2, copy);
	}
	
	public void testSamplingRate () {
		SamplingRate copy = ( (PerformAudioPassThru) msg ).getSamplingRate();
		
		assertEquals("Input value didn't match expected value.", SAMPLING_RATE, copy);
	}
	
	public void testBitsPerSample () {
		BitsPerSample copy = ( (PerformAudioPassThru) msg ).getBitsPerSample();
		
		assertEquals("Input value didn't match expected value.", BITS_PER_SAMPLE, copy);
	}
	
	public void testAudioType () {
		AudioType copy = ( (PerformAudioPassThru) msg ).getAudioType();
		
		assertEquals("Input value didn't match expected value.", AUDIO_TYPE, copy);
	}
	
	public void testMaxDuration () {
		int copy = ( (PerformAudioPassThru) msg ).getMaxDuration();

		assertEquals("Input value didn't match expected value.", MAX_DURATION, copy);
	}
	
	public void testMuteAudio () {
		boolean copy = ( (PerformAudioPassThru) msg ).getMuteAudio();
		
		assertEquals("Input value didn't match expected value.", MUTE_AUDIO, copy);
	}

	public void testNull() {
		PerformAudioPassThru msg = new PerformAudioPassThru();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Initial prompt wasn't set, but getter method returned an object.", msg.getInitialPrompt());
		assertNull("Text 1 wasn't set, but getter method returned an object.", msg.getAudioPassThruDisplayText1());
		assertNull("Text 2  wasn't set, but getter method returned an object.", msg.getAudioPassThruDisplayText2());
		assertNull("Sampling rate wasn't set, but getter method returned an object.", msg.getSamplingRate());
		assertNull("Bits per sample wasn't set, but getter method returned an object.", msg.getBitsPerSample());
		assertNull("Audio type wasn't set, but getter method returned an object.", msg.getAudioType());
		assertNull("Max duration wasn't set, but getter method returned an object.", msg.getMaxDuration());
		assertNull("Mute audio wasn't set, but getter method returned an object.", msg.getMuteAudio());
	}
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PerformAudioPassThru cmd = new PerformAudioPassThru(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			//TODO: remove the Integer cast once that method stops returning int
			assertEquals("Max duration doesn't match input duration", 
					JsonUtils.readIntegerFromJsonObject(parameters, PerformAudioPassThru.KEY_MAX_DURATION), (Integer)cmd.getMaxDuration());
			assertEquals("Audio pass-through display text 1 doesn't match input text", 
					JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_1), cmd.getAudioPassThruDisplayText1());
			assertEquals("Audio pass-through display text 2 doesn't match input text", 
					JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_2), cmd.getAudioPassThruDisplayText2());
			assertEquals("Mute audio doesn't match input mute audio", 
					JsonUtils.readBooleanFromJsonObject(parameters, PerformAudioPassThru.KEY_MUTE_AUDIO), cmd.getMuteAudio());
			assertEquals("Sampling rate doesn't match input rate", 
					JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_SAMPLING_RATE), cmd.getSamplingRate().toString());
			assertEquals("Audio type doesn't match input type", 
					JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_AUDIO_TYPE), cmd.getAudioType().toString());

			JSONArray ttsChunkArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformAudioPassThru.KEY_INITIAL_PROMPT);
			List<TTSChunk> ttsChunkList = new ArrayList<TTSChunk>();
			for (int index = 0; index < ttsChunkArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)ttsChunkArray.get(index)) );
	        	ttsChunkList.add(chunk);
			}
			assertTrue("Initial prompt list doesn't match input prompt list",  Validator.validateTtsChunks(ttsChunkList, cmd.getInitialPrompt()));
			
			assertEquals("Bits per sample doesn't match input bits per sample", 
					JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_BITS_PER_SAMPLE), cmd.getBitsPerSample().toString());
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
