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
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.SetGlobalProperties;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SetGlobalProperties}
 */
public class SetGlobalPropertiesTests extends BaseRpcTests {
		
	@Override
	protected RPCMessage createMessage() {
		SetGlobalProperties msg = new SetGlobalProperties();
		
		msg.setVrHelpTitle(Test.GENERAL_STRING);
		msg.setMenuTitle(Test.GENERAL_STRING);
		msg.setMenuIcon(Test.GENERAL_IMAGE);
		msg.setVrHelp(Test.GENERAL_VRHELPITEM_LIST);
		msg.setHelpPrompt(Test.GENERAL_TTSCHUNK_LIST);
		msg.setTimeoutPrompt(Test.GENERAL_TTSCHUNK_LIST);
		msg.setKeyboardProperties(Test.GENERAL_KEYBOARDPROPERTIES);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SET_GLOBAL_PROPERTIES.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();
		
		try {			
			result.put(SetGlobalProperties.KEY_MENU_ICON, Test.JSON_IMAGE);	
			result.put(SetGlobalProperties.KEY_VR_HELP, Test.JSON_VRHELPITEMS);
			result.put(SetGlobalProperties.KEY_HELP_PROMPT, Test.JSON_TTSCHUNKS);
			result.put(SetGlobalProperties.KEY_TIMEOUT_PROMPT, Test.JSON_TTSCHUNKS);
			result.put(SetGlobalProperties.KEY_MENU_TITLE, Test.GENERAL_STRING);
			result.put(SetGlobalProperties.KEY_VR_HELP_TITLE, Test.GENERAL_STRING);							
			result.put(SetGlobalProperties.KEY_KEYBOARD_PROPERTIES, Test.JSON_KEYBOARDPROPERTIES);			
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
    	Image              testImage       = ( (SetGlobalProperties) msg ).getMenuIcon();
    	String             testVrHelpTitle = ( (SetGlobalProperties) msg ).getVrHelpTitle();
    	String             testMenuTitle   = ( (SetGlobalProperties) msg ).getMenuTitle();
		List<TTSChunk>     testHelpPrompt  = ( (SetGlobalProperties) msg ).getHelpPrompt();
		List<TTSChunk>     testTimeout     = ( (SetGlobalProperties) msg ).getTimeoutPrompt();
		List<VrHelpItem>   testVrHelpItems = ( (SetGlobalProperties) msg ).getVrHelp();
		KeyboardProperties testKeyboardProperties = ( (SetGlobalProperties) msg ).getKeyboardProperties();
		
		// Valid Tests		
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testMenuTitle);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testVrHelpTitle);
		assertTrue(Test.TRUE, Validator.validateImage(Test.GENERAL_IMAGE, testImage));
		assertTrue(Test.TRUE, Validator.validateVrHelpItems(Test.GENERAL_VRHELPITEM_LIST, testVrHelpItems));
		assertTrue(Test.TRUE, Validator.validateTtsChunks(Test.GENERAL_TTSCHUNK_LIST, testHelpPrompt));
		assertTrue(Test.TRUE, Validator.validateTtsChunks(Test.GENERAL_TTSCHUNK_LIST, testTimeout));
		assertTrue(Test.TRUE, Validator.validateKeyboardProperties(Test.GENERAL_KEYBOARDPROPERTIES, testKeyboardProperties));
		
		// Invalid/Null Tests
		SetGlobalProperties msg = new SetGlobalProperties();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getMenuIcon());
		assertNull(Test.NULL, msg.getMenuTitle());
		assertNull(Test.NULL, msg.getVrHelp());
		assertNull(Test.NULL, msg.getHelpPrompt());
		assertNull(Test.NULL, msg.getTimeoutPrompt());
		assertNull(Test.NULL, msg.getKeyboardProperties());
		assertNull(Test.NULL, msg.getVrHelpTitle());
	}
	
	/**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SetGlobalProperties cmd = new SetGlobalProperties(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, SetGlobalProperties.KEY_VR_HELP_TITLE), cmd.getVrHelpTitle());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, SetGlobalProperties.KEY_MENU_TITLE), cmd.getMenuTitle());
			
			JSONObject menuIcon = JsonUtils.readJsonObjectFromJsonObject(parameters, SetGlobalProperties.KEY_MENU_ICON);
			Image referenceMenuIcon = new Image(JsonRPCMarshaller.deserializeJSONObject(menuIcon));
			assertTrue(Test.TRUE, Validator.validateImage(referenceMenuIcon, cmd.getMenuIcon()));
			
			JSONObject keyboardProperties = JsonUtils.readJsonObjectFromJsonObject(parameters, SetGlobalProperties.KEY_KEYBOARD_PROPERTIES);
			KeyboardProperties referenceKeyboardProperties = new KeyboardProperties(JsonRPCMarshaller.deserializeJSONObject(keyboardProperties));
			assertTrue(Test.TRUE, Validator.validateKeyboardProperties(referenceKeyboardProperties, cmd.getKeyboardProperties()));
			
			JSONArray helpPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SetGlobalProperties.KEY_HELP_PROMPT);
			List<TTSChunk> helpPromptList = new ArrayList<TTSChunk>();
			for (int index = 0; index < helpPromptArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)helpPromptArray.get(index)) );
	        	helpPromptList.add(chunk);
			}
			assertTrue(Test.TRUE, Validator.validateTtsChunks(helpPromptList, cmd.getHelpPrompt()));
			
			JSONArray timeoutPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SetGlobalProperties.KEY_TIMEOUT_PROMPT);
			List<TTSChunk> timeoutPromptList = new ArrayList<TTSChunk>();
			for (int index = 0; index < timeoutPromptArray.length(); index++) {
				TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)timeoutPromptArray.get(index)) );
				timeoutPromptList.add(chunk);
			}
			assertTrue(Test.TRUE, Validator.validateTtsChunks(timeoutPromptList, cmd.getTimeoutPrompt()));
			
			JSONArray vrHelpArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SetGlobalProperties.KEY_VR_HELP);
			List<VrHelpItem> vrHelpList = new ArrayList<VrHelpItem>();
			for (int index = 0; index < vrHelpArray.length(); index++) {
				VrHelpItem chunk = new VrHelpItem(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)vrHelpArray.get(index)) );
				vrHelpList.add(chunk);
			}
			assertTrue(Test.TRUE, Validator.validateVrHelpItems(vrHelpList, cmd.getVrHelp()));			
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }
}