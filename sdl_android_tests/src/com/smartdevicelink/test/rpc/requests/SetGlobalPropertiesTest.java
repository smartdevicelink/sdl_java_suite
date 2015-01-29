package com.smartdevicelink.test.rpc.requests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
			image.put(Image.KEY_IMAGE_TYPE, ImageType.STATIC);
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
		
		assertNotSame("Help prompt was not defensive copied", helpPrompt, copy);
		assertTrue("Input value didn't match expected value.", Validator.validateTtsChunks(helpPrompt, copy));
	}
	
	public void testMenuTitle () {
		String copy = ( (SetGlobalProperties) msg ).getMenuTitle();
		
		assertEquals("Data didn't match input data.", MENU_TITLE, copy);
	}
	
	public void testMenuIcon () {
		Image copy = ( (SetGlobalProperties) msg ).getMenuIcon();
		
		assertNotSame("Image was not defensive copied", MENU_ICON, copy);
		assertTrue("Input value didn't match expected value.", Validator.validateImage(MENU_ICON, copy));
	}
	
	public void testVrHelp () {
		List<VrHelpItem> copy = ( (SetGlobalProperties) msg ).getVrHelp();
		
		assertNotSame("Vr help was not defensive copied", vrHelp, copy);
		assertTrue("Input value didn't match expected value.", Validator.validateVrHelpItems(vrHelp, copy));
	}
	
	public void testHelpTitle () {
		String copy = ( (SetGlobalProperties) msg ).getVrHelpTitle();
		
		assertEquals("Data didn't match input data.", HELP_TITLE, copy);
	}
	
	public void testTimeoutPrompt () {
		List<TTSChunk> copy = ( (SetGlobalProperties) msg ).getTimeoutPrompt();
		
		assertNotSame("Timeout prompt was not defensive copied", timeoutPrompt, copy);
		assertTrue("Input value didn't match expected value.", Validator.validateTtsChunks(timeoutPrompt, copy));
	}
	
	public void testKeyboardProperties () {
		KeyboardProperties copy = ( (SetGlobalProperties) msg ).getKeyboardProperties();
		
		assertNotSame("Keyboard properties was not defensive copied", keyboardProperties, copy);
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

}
