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
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.PerformInteraction}
 */
public class PerformInteractionTests extends BaseRpcTests {
	
	@Override
	protected RPCMessage createMessage() {
		PerformInteraction msg = new PerformInteraction();

		msg.setInitialPrompt(Test.GENERAL_TTSCHUNK_LIST);
		msg.setHelpPrompt(Test.GENERAL_TTSCHUNK_LIST);
		msg.setTimeoutPrompt(Test.GENERAL_TTSCHUNK_LIST);
		msg.setVrHelp(Test.GENERAL_VRHELPITEM_LIST);
		msg.setInteractionChoiceSetIDList(Test.GENERAL_INTEGER_LIST);
		msg.setInteractionLayout(Test.GENERAL_LAYOUTMODE);
		msg.setInitialText(Test.GENERAL_STRING);
		msg.setInteractionMode(Test.GENERAL_INTERACTIONMODE);
		msg.setTimeout(Test.GENERAL_INT);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.PERFORM_INTERACTION.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {			
			result.put(PerformInteraction.KEY_INITIAL_PROMPT, Test.JSON_TTSCHUNKS);
			result.put(PerformInteraction.KEY_HELP_PROMPT, Test.JSON_TTSCHUNKS);
			result.put(PerformInteraction.KEY_TIMEOUT_PROMPT, Test.JSON_TTSCHUNKS);
			result.put(PerformInteraction.KEY_VR_HELP, Test.JSON_VRHELPITEMS);
			result.put(PerformInteraction.KEY_INTERACTION_CHOICE_SET_ID_LIST, JsonUtils.createJsonArray(Test.GENERAL_INTEGER_LIST));
			result.put(PerformInteraction.KEY_INTERACTION_LAYOUT, Test.GENERAL_LAYOUTMODE);
			result.put(PerformInteraction.KEY_INITIAL_TEXT, Test.GENERAL_STRING);
			result.put(PerformInteraction.KEY_INTERACTION_MODE, Test.GENERAL_INTERACTIONMODE);
			result.put(PerformInteraction.KEY_TIMEOUT, Test.GENERAL_INT);			
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
		List<TTSChunk> testInitialPrompt = ( (PerformInteraction) msg).getInitialPrompt();
		List<TTSChunk> testHelpPrompt    = ( (PerformInteraction) msg).getHelpPrompt();
		List<TTSChunk> testTimeoutPrompt = ( (PerformInteraction) msg).getTimeoutPrompt();
		List<VrHelpItem> testVrHelpItems = ( (PerformInteraction) msg).getVrHelp();
		List<Integer> testChoiceSetIds   = ( (PerformInteraction) msg).getInteractionChoiceSetIDList();
		LayoutMode testLayout    = ( (PerformInteraction) msg).getInteractionLayout();
		String testInitialText   = ( (PerformInteraction) msg).getInitialText();
		InteractionMode testMode = ( (PerformInteraction) msg).getInteractionMode();
		Integer testTimeout      = ( (PerformInteraction) msg).getTimeout();
		
		// Valid Tests
		assertTrue(Test.TRUE, Validator.validateTtsChunks(Test.GENERAL_TTSCHUNK_LIST, testInitialPrompt));
		assertTrue(Test.TRUE, Validator.validateTtsChunks(Test.GENERAL_TTSCHUNK_LIST, testHelpPrompt));
		assertTrue(Test.TRUE, Validator.validateTtsChunks(Test.GENERAL_TTSCHUNK_LIST, testTimeoutPrompt));
		assertTrue(Test.TRUE, Validator.validateVrHelpItems(Test.GENERAL_VRHELPITEM_LIST, testVrHelpItems));
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER_LIST, testChoiceSetIds);
		assertEquals(Test.MATCH, Test.GENERAL_LAYOUTMODE, testLayout);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testInitialText);
		assertEquals(Test.MATCH, Test.GENERAL_INTERACTIONMODE, testMode);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, testTimeout);
	
		// Invald/Null Tests
		PerformInteraction msg = new PerformInteraction();
		assertNotNull(Test.NOT_NULL, msg);

		testNullBase(msg);

		assertNull(Test.NULL, msg.getInitialPrompt());
		assertNull(Test.NULL, msg.getHelpPrompt());
		assertNull(Test.NULL, msg.getTimeoutPrompt());
		assertNull(Test.NULL, msg.getVrHelp());
		assertNull(Test.NULL, msg.getInteractionChoiceSetIDList());
		assertNull(Test.NULL, msg.getInteractionLayout());
		assertNull(Test.NULL, msg.getInitialText());
		assertNull(Test.NULL, msg.getInteractionMode());
		assertNull(Test.NULL, msg.getTimeout());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PerformInteraction cmd = new PerformInteraction(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformInteraction.KEY_INITIAL_TEXT), cmd.getInitialText());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformInteraction.KEY_INTERACTION_MODE), cmd.getInteractionMode().toString());

			List<Integer> interactionIDList = JsonUtils.readIntegerListFromJsonObject(parameters, PerformInteraction.KEY_INTERACTION_CHOICE_SET_ID_LIST);
			List<Integer> testIDList = cmd.getInteractionChoiceSetIDList();
			assertEquals(Test.MATCH, interactionIDList.size(), testIDList.size());
			assertTrue(Test.TRUE, Validator.validateIntegerList(interactionIDList, testIDList));
			
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformInteraction.KEY_INTERACTION_LAYOUT), cmd.getInteractionLayout().toString());
			
			JSONArray initalPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformInteraction.KEY_INITIAL_PROMPT);
			List<TTSChunk> initalPromptList = new ArrayList<TTSChunk>();
			for (int index = 0; index < initalPromptArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)initalPromptArray.get(index)) );
	        	initalPromptList.add(chunk);
			}
			assertTrue(Test.TRUE,  Validator.validateTtsChunks(initalPromptList, cmd.getInitialPrompt()));
			
			JSONArray helpPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformInteraction.KEY_HELP_PROMPT);
			List<TTSChunk> helpPromptList = new ArrayList<TTSChunk>();
			for (int index = 0; index < helpPromptArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)helpPromptArray.get(index)) );
	        	helpPromptList.add(chunk);
			}
			assertTrue(Test.TRUE, Validator.validateTtsChunks(helpPromptList, cmd.getHelpPrompt()));
			
			JSONArray timeoutPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformInteraction.KEY_TIMEOUT_PROMPT);
			List<TTSChunk> timeoutPromptList = new ArrayList<TTSChunk>();
			for (int index = 0; index < timeoutPromptArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)timeoutPromptArray.get(index)) );
	        	timeoutPromptList.add(chunk);
			}
			assertTrue(Test.TRUE,  Validator.validateTtsChunks(timeoutPromptList, cmd.getTimeoutPrompt()));
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, PerformInteraction.KEY_TIMEOUT), cmd.getTimeout());
			
			JSONArray vrHelpArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformInteraction.KEY_VR_HELP);
			List<VrHelpItem> vrHelpList= new ArrayList<VrHelpItem>();
			for (int index = 0; index < vrHelpArray.length(); index++) {
				VrHelpItem vrHelpItem = new VrHelpItem(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)vrHelpArray.get(index)) );
				vrHelpList.add(vrHelpItem);
			}
			assertTrue(Test.TRUE,  Validator.validateVrHelpItems(vrHelpList, cmd.getVrHelp()) );
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }    
}