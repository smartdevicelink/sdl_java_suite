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
import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.Speak;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class SpeakTest extends BaseRpcTests {

	private List<TTSChunk> ttsChunks;
	
	@Override
	protected RPCMessage createMessage() {
		Speak msg = new Speak();
		
		createCustomObjects();

		msg.setTtsChunks(ttsChunks);

		return msg;
	}
	
	private void createCustomObjects () {
		ttsChunks  = new ArrayList<TTSChunk>(2);
		
		ttsChunks.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Welcome to the jungle"));
		ttsChunks.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Say a command"));
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SPEAK;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();
		JSONArray ttsChunk = new JSONArray();
		
		try {
		    JSONObject chunk = new JSONObject();
			chunk.put(TTSChunk.KEY_TEXT, "Welcome to the jungle");
	        chunk.put(TTSChunk.KEY_TYPE, SpeechCapabilities.TEXT);
	        ttsChunk.put(chunk);
	        
	        chunk = new JSONObject();
			chunk.put(TTSChunk.KEY_TEXT, "Say a command");
			chunk.put(TTSChunk.KEY_TYPE, SpeechCapabilities.TEXT);
			ttsChunk.put(chunk);
	        
			result.put(Speak.KEY_TTS_CHUNKS, ttsChunk);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testTtsChunks() {
		List<TTSChunk> copy = ( (Speak) msg ).getTtsChunks();
		
		assertNotSame("Tts chunks was not defensive copied", ttsChunks, copy);
	    assertTrue("Input value didn't match expected value.", Validator.validateTtsChunks(ttsChunks, copy));
	}

	public void testNull() {
		Speak msg = new Speak();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Tts chunks wasn't set, but getter method returned an object.", msg.getTtsChunks());
	}
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			Speak cmd = new Speak(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			JSONArray ttsChunkArray = JsonUtils.readJsonArrayFromJsonObject(parameters, Speak.KEY_TTS_CHUNKS);
			List<TTSChunk> ttsChunkList = new ArrayList<TTSChunk>();
			for (int index = 0; index < ttsChunkArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)ttsChunkArray.get(index)) );
	        	ttsChunkList.add(chunk);
			}
			assertTrue("TTSChunk list doesn't match input TTSChunk list",  Validator.validateTtsChunks(ttsChunkList, cmd.getTtsChunks()));
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
