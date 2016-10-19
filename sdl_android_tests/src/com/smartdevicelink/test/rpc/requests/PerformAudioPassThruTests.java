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
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.PerformAudioPassThru}
 */
public class PerformAudioPassThruTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		PerformAudioPassThru msg = new PerformAudioPassThru();
				
		msg.setInitialPrompt(Test.GENERAL_TTSCHUNK_LIST);
		msg.setAudioPassThruDisplayText1(Test.GENERAL_STRING);
		msg.setAudioPassThruDisplayText2(Test.GENERAL_STRING);
		msg.setSamplingRate(Test.GENERAL_SAMPLINGRATE);
		msg.setAudioType(Test.GENERAL_AUDIOTYPE);
		msg.setBitsPerSample(Test.GENERAL_BITSPERSAMPLE);
		msg.setMaxDuration(Test.GENERAL_INT);
		msg.setMuteAudio(Test.GENERAL_BOOLEAN);
		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.PERFORM_AUDIO_PASS_THRU.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(PerformAudioPassThru.KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_1, Test.GENERAL_STRING);
			result.put(PerformAudioPassThru.KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_2, Test.GENERAL_STRING);			
			result.put(PerformAudioPassThru.KEY_MUTE_AUDIO, Test.GENERAL_BOOLEAN);	
			result.put(PerformAudioPassThru.KEY_MAX_DURATION, Test.GENERAL_INT);
			result.put(PerformAudioPassThru.KEY_AUDIO_TYPE, Test.GENERAL_AUDIOTYPE);
			result.put(PerformAudioPassThru.KEY_SAMPLING_RATE, Test.GENERAL_SAMPLINGRATE);
			result.put(PerformAudioPassThru.KEY_BITS_PER_SAMPLE, Test.GENERAL_BITSPERSAMPLE);
			result.put(PerformAudioPassThru.KEY_INITIAL_PROMPT,  Test.JSON_TTSCHUNKS);			
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
    	boolean testMuteAudio = ( (PerformAudioPassThru) msg ).getMuteAudio();
    	int testMaxDuration = ( (PerformAudioPassThru) msg ).getMaxDuration();
    	String testText2 = ( (PerformAudioPassThru) msg ).getAudioPassThruDisplayText2();
    	String testText1 = ( (PerformAudioPassThru) msg ).getAudioPassThruDisplayText1();
    	SamplingRate testSamplingRate = ( (PerformAudioPassThru) msg ).getSamplingRate();
    	AudioType testAudioType = ( (PerformAudioPassThru) msg ).getAudioType();
    	BitsPerSample testBitsPerSample = ( (PerformAudioPassThru) msg ).getBitsPerSample();
		List<TTSChunk> testInitialPrompt = ( (PerformAudioPassThru) msg ).getInitialPrompt();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_TTSCHUNK_LIST.size(), testInitialPrompt.size());
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testText1);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testText2);
		assertEquals(Test.MATCH, Test.GENERAL_SAMPLINGRATE, testSamplingRate);
		assertEquals(Test.MATCH, Test.GENERAL_BITSPERSAMPLE, testBitsPerSample);
		assertEquals(Test.MATCH, Test.GENERAL_AUDIOTYPE, testAudioType);
		assertEquals(Test.MATCH, Test.GENERAL_INT, testMaxDuration);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, testMuteAudio);
		for (int i = 0; i < Test.GENERAL_TTSCHUNK_LIST.size(); i++) {
			assertEquals(Test.MATCH, Test.GENERAL_TTSCHUNK_LIST.get(i), testInitialPrompt.get(i));
		}
	
		// Invalid/Null Tests
		PerformAudioPassThru msg = new PerformAudioPassThru();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getInitialPrompt());
		assertNull(Test.NULL, msg.getAudioPassThruDisplayText1());
		assertNull(Test.NULL, msg.getAudioPassThruDisplayText2());
		assertNull(Test.NULL, msg.getSamplingRate());
		assertNull(Test.NULL, msg.getBitsPerSample());
		assertNull(Test.NULL, msg.getAudioType());
		assertNull(Test.NULL, msg.getMaxDuration());
		assertNull(Test.NULL, msg.getMuteAudio());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PerformAudioPassThru cmd = new PerformAudioPassThru(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, PerformAudioPassThru.KEY_MAX_DURATION), (Integer)cmd.getMaxDuration());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_1), cmd.getAudioPassThruDisplayText1());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_2), cmd.getAudioPassThruDisplayText2());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, PerformAudioPassThru.KEY_MUTE_AUDIO), cmd.getMuteAudio());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_SAMPLING_RATE), cmd.getSamplingRate().toString());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_AUDIO_TYPE), cmd.getAudioType().toString());

			JSONArray ttsChunkArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformAudioPassThru.KEY_INITIAL_PROMPT);
			List<TTSChunk> ttsChunkList = new ArrayList<TTSChunk>();
			for (int index = 0; index < ttsChunkArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)ttsChunkArray.get(index)) );
	        	ttsChunkList.add(chunk);
			}
			assertTrue(Test.TRUE,  Validator.validateTtsChunks(ttsChunkList, cmd.getInitialPrompt()));
			
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_BITS_PER_SAMPLE), cmd.getBitsPerSample().toString());
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }
}