package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.SetGlobalProperties;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static androidx.test.platform.app.InstrumentationRegistry.getTargetContext;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.SetGlobalProperties}
 */
public class SetGlobalPropertiesTests extends BaseRpcTests {
		
	@Override
	protected RPCMessage createMessage() {
		SetGlobalProperties msg = new SetGlobalProperties();
		
		msg.setVrHelpTitle(TestValues.GENERAL_STRING);
		msg.setMenuTitle(TestValues.GENERAL_STRING);
		msg.setMenuIcon(TestValues.GENERAL_IMAGE);
		msg.setVrHelp(TestValues.GENERAL_VRHELPITEM_LIST);
		msg.setHelpPrompt(TestValues.GENERAL_TTSCHUNK_LIST);
		msg.setTimeoutPrompt(TestValues.GENERAL_TTSCHUNK_LIST);
		msg.setKeyboardProperties(TestValues.GENERAL_KEYBOARDPROPERTIES);
		msg.setMenuLayout(TestValues.GENERAL_MENU_LAYOUT);

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
			result.put(SetGlobalProperties.KEY_MENU_ICON, TestValues.JSON_IMAGE);
			result.put(SetGlobalProperties.KEY_VR_HELP, TestValues.JSON_VRHELPITEMS);
			result.put(SetGlobalProperties.KEY_HELP_PROMPT, TestValues.JSON_TTSCHUNKS);
			result.put(SetGlobalProperties.KEY_TIMEOUT_PROMPT, TestValues.JSON_TTSCHUNKS);
			result.put(SetGlobalProperties.KEY_MENU_TITLE, TestValues.GENERAL_STRING);
			result.put(SetGlobalProperties.KEY_VR_HELP_TITLE, TestValues.GENERAL_STRING);
			result.put(SetGlobalProperties.KEY_KEYBOARD_PROPERTIES, TestValues.JSON_KEYBOARDPROPERTIES);
			result.put(SetGlobalProperties.KEY_MENU_LAYOUT, TestValues.GENERAL_MENU_LAYOUT);
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
    	Image              testImage       = ( (SetGlobalProperties) msg ).getMenuIcon();
    	String             testVrHelpTitle = ( (SetGlobalProperties) msg ).getVrHelpTitle();
    	String             testMenuTitle   = ( (SetGlobalProperties) msg ).getMenuTitle();
		List<TTSChunk>     testHelpPrompt  = ( (SetGlobalProperties) msg ).getHelpPrompt();
		List<TTSChunk>     testTimeout     = ( (SetGlobalProperties) msg ).getTimeoutPrompt();
		List<VrHelpItem>   testVrHelpItems = ( (SetGlobalProperties) msg ).getVrHelp();
		KeyboardProperties testKeyboardProperties = ( (SetGlobalProperties) msg ).getKeyboardProperties();
		MenuLayout testMenuLayout = ( (SetGlobalProperties) msg ).getMenuLayout();
		
		// Valid Tests		
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testMenuTitle);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testVrHelpTitle);
		assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, testImage));
		assertTrue(TestValues.TRUE, Validator.validateVrHelpItems(TestValues.GENERAL_VRHELPITEM_LIST, testVrHelpItems));
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(TestValues.GENERAL_TTSCHUNK_LIST, testHelpPrompt));
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(TestValues.GENERAL_TTSCHUNK_LIST, testTimeout));
		assertTrue(TestValues.TRUE, Validator.validateKeyboardProperties(TestValues.GENERAL_KEYBOARDPROPERTIES, testKeyboardProperties));
		assertEquals(TestValues.MATCH, TestValues.GENERAL_MENU_LAYOUT, testMenuLayout);
		
		// Invalid/Null Tests
		SetGlobalProperties msg = new SetGlobalProperties();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getMenuIcon());
		assertNull(TestValues.NULL, msg.getMenuTitle());
		assertNull(TestValues.NULL, msg.getVrHelp());
		assertNull(TestValues.NULL, msg.getHelpPrompt());
		assertNull(TestValues.NULL, msg.getTimeoutPrompt());
		assertNull(TestValues.NULL, msg.getKeyboardProperties());
		assertNull(TestValues.NULL, msg.getVrHelpTitle());
		assertNull(TestValues.NULL, msg.getMenuLayout());
	}
	
	/**
     * Tests a valid JSON construction of this RPC message.
     */
	@Test
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getTargetContext(), getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SetGlobalProperties cmd = new SetGlobalProperties(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, SetGlobalProperties.KEY_VR_HELP_TITLE), cmd.getVrHelpTitle());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, SetGlobalProperties.KEY_MENU_TITLE), cmd.getMenuTitle());

			assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, SetGlobalProperties.KEY_MENU_LAYOUT), cmd.getMenuLayout());
			
			JSONObject menuIcon = JsonUtils.readJsonObjectFromJsonObject(parameters, SetGlobalProperties.KEY_MENU_ICON);
			Image referenceMenuIcon = new Image(JsonRPCMarshaller.deserializeJSONObject(menuIcon));
			assertTrue(TestValues.TRUE, Validator.validateImage(referenceMenuIcon, cmd.getMenuIcon()));
			
			JSONObject keyboardProperties = JsonUtils.readJsonObjectFromJsonObject(parameters, SetGlobalProperties.KEY_KEYBOARD_PROPERTIES);
			KeyboardProperties referenceKeyboardProperties = new KeyboardProperties(JsonRPCMarshaller.deserializeJSONObject(keyboardProperties));
			assertTrue(TestValues.TRUE, Validator.validateKeyboardProperties(referenceKeyboardProperties, cmd.getKeyboardProperties()));
			
			JSONArray helpPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SetGlobalProperties.KEY_HELP_PROMPT);
			List<TTSChunk> helpPromptList = new ArrayList<TTSChunk>();
			for (int index = 0; index < helpPromptArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)helpPromptArray.get(index)) );
	        	helpPromptList.add(chunk);
			}
			assertTrue(TestValues.TRUE, Validator.validateTtsChunks(helpPromptList, cmd.getHelpPrompt()));
			
			JSONArray timeoutPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SetGlobalProperties.KEY_TIMEOUT_PROMPT);
			List<TTSChunk> timeoutPromptList = new ArrayList<TTSChunk>();
			for (int index = 0; index < timeoutPromptArray.length(); index++) {
				TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)timeoutPromptArray.get(index)) );
				timeoutPromptList.add(chunk);
			}
			assertTrue(TestValues.TRUE, Validator.validateTtsChunks(timeoutPromptList, cmd.getTimeoutPrompt()));
			
			JSONArray vrHelpArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SetGlobalProperties.KEY_VR_HELP);
			List<VrHelpItem> vrHelpList = new ArrayList<VrHelpItem>();
			for (int index = 0; index < vrHelpArray.length(); index++) {
				VrHelpItem chunk = new VrHelpItem(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)vrHelpArray.get(index)) );
				vrHelpList.add(chunk);
			}
			assertTrue(TestValues.TRUE, Validator.validateVrHelpItems(vrHelpList, cmd.getVrHelp()));
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}    	
    }
}