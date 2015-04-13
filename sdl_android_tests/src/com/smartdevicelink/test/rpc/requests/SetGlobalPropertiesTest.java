package com.smartdevicelink.test.rpc.requests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.SetGlobalProperties;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class SetGlobalPropertiesTest extends BaseRpcTests {

	private static final String HELP_TITLE = "helpTitle";
	private static final String MENU_TITLE = "menuTitle";
	private static final Image MENU_ICON = new Image();	
	
	private List<VrHelpItem>   vrHelp;
	private List<TTSChunk>     helpPrompt;
	private List<TTSChunk>     timeoutPrompt;
	private KeyboardProperties keyboardProperties;
	
	// VrHelpItem Constants
	private final Image IMAGE_1  = new Image();
	private final Image IMAGE_2  = new Image();
	
	// KeyboardProperties Constants
	private final List<String> CHAR_LIST = Arrays.asList(new String[]{ "a", "b", "c" });
	
	@Override
	protected RPCMessage createMessage() {
		SetGlobalProperties msg = new SetGlobalProperties();
		
		createCustomObjects();

		msg.setVrHelpTitle(HELP_TITLE);
		msg.setMenuTitle(MENU_TITLE);
		msg.setMenuIcon(MENU_ICON);
		msg.setVrHelp(vrHelp);
		msg.setHelpPrompt(helpPrompt);
		msg.setTimeoutPrompt(timeoutPrompt);
		msg.setKeyboardProperties(keyboardProperties);

		return msg;
	}
	
	public void createCustomObjects () {
		vrHelp        = new ArrayList<VrHelpItem>(2);
		helpPrompt    = new ArrayList<TTSChunk>(2);
		timeoutPrompt = new ArrayList<TTSChunk>(2);
		keyboardProperties = new KeyboardProperties();
		
		keyboardProperties.setAutoCompleteText("autoCompleteText");
		keyboardProperties.setKeypressMode(KeypressMode.SINGLE_KEYPRESS);
		keyboardProperties.setKeyboardLayout(KeyboardLayout.QWERTY);
		keyboardProperties.setLanguage(Language.EN_US);
		keyboardProperties.setLimitedCharacterList(CHAR_LIST);
		
		helpPrompt.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Welcome to the jungle"));
        helpPrompt.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Say a command"));
        
        timeoutPrompt.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Welcome to the jungle"));
        timeoutPrompt.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Say a command"));
    	
    	IMAGE_1.setValue("value1");
    	IMAGE_1.setImageType(ImageType.DYNAMIC);
    	IMAGE_2.setValue("value2");
    	IMAGE_2.setImageType(ImageType.STATIC);
        
    	VrHelpItem vrItem1 = new VrHelpItem();
    	vrItem1.setText("text");
    	vrItem1.setImage(IMAGE_1);
    	vrItem1.setPosition(0);
    	
    	VrHelpItem vrItem2 = new VrHelpItem();
    	vrItem2.setText("help");
    	vrItem2.setImage(IMAGE_2);
    	vrItem2.setPosition(1);
    	
        vrHelp.add(vrItem1);
        vrHelp.add(vrItem2);
        
		MENU_ICON.setValue("value");
		MENU_ICON.setImageType(ImageType.DYNAMIC);
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SET_GLOBAL_PROPERTIES;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result         = new JSONObject(),
				   ttsChunk       = new JSONObject(),
				   vrHelpItem     = new JSONObject(),
				   image          = new JSONObject(),
				   keyboard		  = new JSONObject();
		JSONArray  helpPrompts    = new JSONArray(), 
				   timeoutPrompts = new JSONArray(),
				   vrHelpItems    = new JSONArray();
		
		try {		
			ttsChunk.put(TTSChunk.KEY_TEXT, "Welcome to the jungle");
			ttsChunk.put(TTSChunk.KEY_TYPE, SpeechCapabilities.TEXT);
			helpPrompts.put(ttsChunk);
			timeoutPrompts.put(ttsChunk);
			
			ttsChunk = new JSONObject();
			ttsChunk.put(TTSChunk.KEY_TEXT, "Say a command");
			ttsChunk.put(TTSChunk.KEY_TYPE, SpeechCapabilities.TEXT);
			helpPrompts.put(ttsChunk);
			timeoutPrompts.put(ttsChunk);
			
			image.put(Image.KEY_IMAGE_TYPE, ImageType.DYNAMIC);
			image.put(Image.KEY_VALUE, "value1");			
			vrHelpItem.put(VrHelpItem.KEY_TEXT, "text");
			vrHelpItem.put(VrHelpItem.KEY_IMAGE, image);
			vrHelpItem.put(VrHelpItem.KEY_POSITION, 0);
			vrHelpItems.put(vrHelpItem);
			
			image = new JSONObject();
			image.put(Image.KEY_IMAGE_TYPE, ImageType.STATIC);
			image.put(Image.KEY_VALUE, "value2");	
			vrHelpItem = new JSONObject();
			vrHelpItem.put(VrHelpItem.KEY_TEXT, "help");
			vrHelpItem.put(VrHelpItem.KEY_IMAGE, image);
			vrHelpItem.put(VrHelpItem.KEY_POSITION, 1);
			vrHelpItems.put(vrHelpItem);
			
			image = new JSONObject();
			image.put(Image.KEY_IMAGE_TYPE, ImageType.DYNAMIC);
			image.put(Image.KEY_VALUE, "value");
			
			keyboard.put(KeyboardProperties.KEY_LANGUAGE, Language.EN_US);
			keyboard.put(KeyboardProperties.KEY_KEYPRESS_MODE, KeypressMode.SINGLE_KEYPRESS);
			keyboard.put(KeyboardProperties.KEY_KEYBOARD_LAYOUT, KeyboardLayout.QWERTY);
			keyboard.put(KeyboardProperties.KEY_LIMITED_CHARACTER_LIST, JsonUtils.createJsonArray(CHAR_LIST));
			keyboard.put(KeyboardProperties.KEY_AUTO_COMPLETE_TEXT, "autoCompleteText");
			
			result.put(SetGlobalProperties.KEY_MENU_ICON,      image);	
			result.put(SetGlobalProperties.KEY_VR_HELP,        vrHelpItems);
			result.put(SetGlobalProperties.KEY_HELP_PROMPT,    helpPrompts);
			result.put(SetGlobalProperties.KEY_TIMEOUT_PROMPT, timeoutPrompts);
			result.put(SetGlobalProperties.KEY_MENU_TITLE, MENU_TITLE);
			result.put(SetGlobalProperties.KEY_VR_HELP_TITLE, HELP_TITLE);							
			result.put(SetGlobalProperties.KEY_KEYBOARD_PROPERTIES, keyboard);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testHelpPrompt () {
		List<TTSChunk> copy = ( (SetGlobalProperties) msg ).getHelpPrompt();
		
		assertTrue("Input value didn't match expected value.", Validator.validateTtsChunks(helpPrompt, copy));
	}
	
	public void testMenuTitle () {
		String copy = ( (SetGlobalProperties) msg ).getMenuTitle();
		
		assertEquals("Data didn't match input data.", MENU_TITLE, copy);
	}
	
	public void testMenuIcon () {
		Image copy = ( (SetGlobalProperties) msg ).getMenuIcon();
		
		assertTrue("Input value didn't match expected value.", Validator.validateImage(MENU_ICON, copy));
	}
	
	public void testVrHelp () {
		List<VrHelpItem> copy = ( (SetGlobalProperties) msg ).getVrHelp();
		
		assertTrue("Input value didn't match expected value.", Validator.validateVrHelpItems(vrHelp, copy));
	}
	
	public void testHelpTitle () {
		String copy = ( (SetGlobalProperties) msg ).getVrHelpTitle();
		
		assertEquals("Data didn't match input data.", HELP_TITLE, copy);
	}
	
	public void testTimeoutPrompt () {
		List<TTSChunk> copy = ( (SetGlobalProperties) msg ).getTimeoutPrompt();
		
		assertTrue("Input value didn't match expected value.", Validator.validateTtsChunks(timeoutPrompt, copy));
	}
	
	public void testKeyboardProperties () {
		KeyboardProperties copy = ( (SetGlobalProperties) msg ).getKeyboardProperties();
		
		assertTrue("Input value didn't match expected value.", Validator.validateKeyboardProperties(keyboardProperties, copy));
	}

	public void testNull() {
		SetGlobalProperties msg = new SetGlobalProperties();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Menu icon wasn't set, but getter method returned an object.", msg.getMenuIcon());
		assertNull("Menu title wasn't set, but getter method returned an object.", msg.getMenuTitle());
		assertNull("Vr help wasn't set, but getter method returned an object.", msg.getVrHelp());
		assertNull("Help prompt wasn't set, but getter method returned an object.", msg.getHelpPrompt());
		assertNull("Timeout prompt wasn't set, but getter method returned an object.", msg.getTimeoutPrompt());
		assertNull("Keyboard properties wasn't set, but getter method returned an object.", msg.getKeyboardProperties());
		assertNull("Help title wasn't set, but getter method returned an object.", msg.getVrHelpTitle());
	}
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SetGlobalProperties cmd = new SetGlobalProperties(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("VR help title doesn't match input title", 
					JsonUtils.readStringFromJsonObject(parameters, SetGlobalProperties.KEY_VR_HELP_TITLE), cmd.getVrHelpTitle());
			assertEquals("Menu title doesn't match input title", 
					JsonUtils.readStringFromJsonObject(parameters, SetGlobalProperties.KEY_MENU_TITLE), cmd.getMenuTitle());
			
			JSONObject menuIcon = JsonUtils.readJsonObjectFromJsonObject(parameters, SetGlobalProperties.KEY_MENU_ICON);
			Image referenceMenuIcon = new Image(JsonRPCMarshaller.deserializeJSONObject(menuIcon));
			assertTrue("Menu icon doesn't match expected menu icon", Validator.validateImage(referenceMenuIcon, cmd.getMenuIcon()));
			
			JSONObject keyboardProperties = JsonUtils.readJsonObjectFromJsonObject(parameters, SetGlobalProperties.KEY_KEYBOARD_PROPERTIES);
			KeyboardProperties referenceKeyboardProperties = new KeyboardProperties(JsonRPCMarshaller.deserializeJSONObject(keyboardProperties));
			assertTrue("Keyboard properties doesn't match expected properties", 
					Validator.validateKeyboardProperties(referenceKeyboardProperties, cmd.getKeyboardProperties()));
			
			JSONArray helpPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SetGlobalProperties.KEY_HELP_PROMPT);
			List<TTSChunk> helpPromptList = new ArrayList<TTSChunk>();
			for (int index = 0; index < helpPromptArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)helpPromptArray.get(index)) );
	        	helpPromptList.add(chunk);
			}
			assertTrue("Help prompt doesn't match input help prompt",  Validator.validateTtsChunks(helpPromptList, cmd.getHelpPrompt()));
			
			JSONArray timeoutPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SetGlobalProperties.KEY_TIMEOUT_PROMPT);
			List<TTSChunk> timeoutPromptList = new ArrayList<TTSChunk>();
			for (int index = 0; index < timeoutPromptArray.length(); index++) {
				TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)timeoutPromptArray.get(index)) );
				timeoutPromptList.add(chunk);
			}
			assertTrue("Timeout prompt doesn't match input timeout prompt",  Validator.validateTtsChunks(timeoutPromptList, cmd.getTimeoutPrompt()));
			
			JSONArray vrHelpArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SetGlobalProperties.KEY_VR_HELP);
			List<VrHelpItem> vrHelpList = new ArrayList<VrHelpItem>();
			for (int index = 0; index < vrHelpArray.length(); index++) {
				VrHelpItem chunk = new VrHelpItem(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)vrHelpArray.get(index)) );
				vrHelpList.add(chunk);
			}
			assertTrue("VR help list doesn't match input help list",  Validator.validateVrHelpItems(vrHelpList, cmd.getVrHelp()));
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
