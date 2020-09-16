package com.smartdevicelink.test.rpc.requests;

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

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.PerformAudioPassThru}
 */
public class PerformAudioPassThruTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		PerformAudioPassThru msg = new PerformAudioPassThru();
				
		msg.setInitialPrompt(TestValues.GENERAL_TTSCHUNK_LIST);
		msg.setAudioPassThruDisplayText1(TestValues.GENERAL_STRING);
		msg.setAudioPassThruDisplayText2(TestValues.GENERAL_STRING);
		msg.setSamplingRate(TestValues.GENERAL_SAMPLINGRATE);
		msg.setAudioType(TestValues.GENERAL_AUDIOTYPE);
		msg.setBitsPerSample(TestValues.GENERAL_BITSPERSAMPLE);
		msg.setMaxDuration(TestValues.GENERAL_INT);
		msg.setMuteAudio(TestValues.GENERAL_BOOLEAN);
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
			result.put(PerformAudioPassThru.KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_1, TestValues.GENERAL_STRING);
			result.put(PerformAudioPassThru.KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_2, TestValues.GENERAL_STRING);
			result.put(PerformAudioPassThru.KEY_MUTE_AUDIO, TestValues.GENERAL_BOOLEAN);
			result.put(PerformAudioPassThru.KEY_MAX_DURATION, TestValues.GENERAL_INT);
			result.put(PerformAudioPassThru.KEY_AUDIO_TYPE, TestValues.GENERAL_AUDIOTYPE);
			result.put(PerformAudioPassThru.KEY_SAMPLING_RATE, TestValues.GENERAL_SAMPLINGRATE);
			result.put(PerformAudioPassThru.KEY_BITS_PER_SAMPLE, TestValues.GENERAL_BITSPERSAMPLE);
			result.put(PerformAudioPassThru.KEY_INITIAL_PROMPT,  TestValues.JSON_TTSCHUNKS);
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
    	boolean testMuteAudio = ( (PerformAudioPassThru) msg ).getMuteAudio();
    	int testMaxDuration = ( (PerformAudioPassThru) msg ).getMaxDuration();
    	String testText2 = ( (PerformAudioPassThru) msg ).getAudioPassThruDisplayText2();
    	String testText1 = ( (PerformAudioPassThru) msg ).getAudioPassThruDisplayText1();
    	SamplingRate testSamplingRate = ( (PerformAudioPassThru) msg ).getSamplingRate();
    	AudioType testAudioType = ( (PerformAudioPassThru) msg ).getAudioType();
    	BitsPerSample testBitsPerSample = ( (PerformAudioPassThru) msg ).getBitsPerSample();
		List<TTSChunk> testInitialPrompt = ( (PerformAudioPassThru) msg ).getInitialPrompt();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_TTSCHUNK_LIST.size(), testInitialPrompt.size());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testText1);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testText2);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_SAMPLINGRATE, testSamplingRate);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BITSPERSAMPLE, testBitsPerSample);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_AUDIOTYPE, testAudioType);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, testMaxDuration);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, testMuteAudio);
		for (int i = 0; i < TestValues.GENERAL_TTSCHUNK_LIST.size(); i++) {
			assertEquals(TestValues.MATCH, TestValues.GENERAL_TTSCHUNK_LIST.get(i), testInitialPrompt.get(i));
		}
	
		// Invalid/Null Tests
		PerformAudioPassThru msg = new PerformAudioPassThru();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getInitialPrompt());
		assertNull(TestValues.NULL, msg.getAudioPassThruDisplayText1());
		assertNull(TestValues.NULL, msg.getAudioPassThruDisplayText2());
		assertNull(TestValues.NULL, msg.getSamplingRate());
		assertNull(TestValues.NULL, msg.getBitsPerSample());
		assertNull(TestValues.NULL, msg.getAudioType());
		assertNull(TestValues.NULL, msg.getMaxDuration());
		assertNull(TestValues.NULL, msg.getMuteAudio());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getInstrumentation().getTargetContext(), getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PerformAudioPassThru cmd = new PerformAudioPassThru(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, PerformAudioPassThru.KEY_MAX_DURATION), (Integer)cmd.getMaxDuration());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_1), cmd.getAudioPassThruDisplayText1());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_2), cmd.getAudioPassThruDisplayText2());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, PerformAudioPassThru.KEY_MUTE_AUDIO), cmd.getMuteAudio());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_SAMPLING_RATE), cmd.getSamplingRate().toString());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_AUDIO_TYPE), cmd.getAudioType().toString());

			JSONArray ttsChunkArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformAudioPassThru.KEY_INITIAL_PROMPT);
			List<TTSChunk> ttsChunkList = new ArrayList<TTSChunk>();
			for (int index = 0; index < ttsChunkArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)ttsChunkArray.get(index)) );
	        	ttsChunkList.add(chunk);
			}
			assertTrue(TestValues.TRUE,  Validator.validateTtsChunks(ttsChunkList, cmd.getInitialPrompt()));
			
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformAudioPassThru.KEY_BITS_PER_SAMPLE), cmd.getBitsPerSample().toString());
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}    	
    }
}