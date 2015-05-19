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
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class PerformInteractionTest extends BaseRpcTests {

	
	private List<TTSChunk>   initialPrompt;
	private List<TTSChunk>   helpPrompt;
	private List<TTSChunk>   timeoutPrompt;
	private List<VrHelpItem> vrHelp;	
	
	private static final Integer          TIMEOUT            = 0;
	private static final String           INITIAL_TEXT       = "initialText";
	private static final LayoutMode       INTERACTION_LAYOUT = LayoutMode.ICON_ONLY;
	private static final InteractionMode  MODE               = InteractionMode.VR_ONLY;	
	private final List<Integer>    CHOICE_SET_IDS     = Arrays.asList(new Integer[]{0,1});
	
	// VrHelpItem Constants
	private final Image IMAGE_1  = new Image();
	private final Image IMAGE_2  = new Image();
	
	@Override
	protected RPCMessage createMessage() {
		PerformInteraction msg = new PerformInteraction();

		createCustomObjects();
		
		msg.setInitialPrompt(initialPrompt);
		msg.setHelpPrompt(helpPrompt);
		msg.setTimeoutPrompt(timeoutPrompt);
		msg.setVrHelp(vrHelp);
		msg.setInteractionChoiceSetIDList(CHOICE_SET_IDS);
		msg.setInteractionLayout(INTERACTION_LAYOUT);
		msg.setInitialText(INITIAL_TEXT);
		msg.setInteractionMode(MODE);
		msg.setTimeout(TIMEOUT);

		return msg;
	}
	
	private void createCustomObjects () {
		helpPrompt    = new ArrayList<TTSChunk>(2);
		initialPrompt = new ArrayList<TTSChunk>(2);
		timeoutPrompt = new ArrayList<TTSChunk>(2);
		vrHelp        = new ArrayList<VrHelpItem>(2);
		
		
        helpPrompt.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Welcome to the jungle"));
        helpPrompt.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Say a command"));
        
        initialPrompt.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Welcome to the jungle"));
        initialPrompt.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Say a command"));
        
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
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.PERFORM_INTERACTION;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result         = new JSONObject(),
				   ttsChunk       = new JSONObject(),
				   vrHelpItem     = new JSONObject(),
				   image          = new JSONObject();
		JSONArray  helpPrompts    = new JSONArray(), 
				   initialPrompts = new JSONArray(), 
				   timeoutPrompts = new JSONArray(),
				   vrHelpItems    = new JSONArray();

		try {
			ttsChunk.put(TTSChunk.KEY_TEXT, "Welcome to the jungle");
			ttsChunk.put(TTSChunk.KEY_TYPE, SpeechCapabilities.TEXT);
			helpPrompts.put(ttsChunk);
			initialPrompts.put(ttsChunk);
			timeoutPrompts.put(ttsChunk);
			
			ttsChunk = new JSONObject();
			ttsChunk.put(TTSChunk.KEY_TEXT, "Say a command");
			ttsChunk.put(TTSChunk.KEY_TYPE, SpeechCapabilities.TEXT);
			helpPrompts.put(ttsChunk);
			initialPrompts.put(ttsChunk);
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
			
			result.put(PerformInteraction.KEY_INITIAL_PROMPT,     initialPrompts);
			result.put(PerformInteraction.KEY_HELP_PROMPT,        helpPrompts);
			result.put(PerformInteraction.KEY_TIMEOUT_PROMPT,     timeoutPrompts);
			result.put(PerformInteraction.KEY_VR_HELP,            vrHelpItems);
			result.put(PerformInteraction.KEY_INTERACTION_CHOICE_SET_ID_LIST,     JsonUtils.createJsonArray(CHOICE_SET_IDS));
			result.put(PerformInteraction.KEY_INTERACTION_LAYOUT, INTERACTION_LAYOUT);
			result.put(PerformInteraction.KEY_INITIAL_TEXT,       INITIAL_TEXT);
			result.put(PerformInteraction.KEY_INTERACTION_MODE,   MODE);
			result.put(PerformInteraction.KEY_TIMEOUT,            TIMEOUT);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testInitialPrompt() {
		List<TTSChunk> copy = ( (PerformInteraction) msg).getInitialPrompt();
		
		assertNotNull("Initial prompt were null.", copy);
		assertTrue("Intial prompt didn't match input TTS chunks.", Validator.validateTtsChunks(initialPrompt, copy));
	}
	
	public void testHelpPrompt () {
		List<TTSChunk> copy = ( (PerformInteraction) msg).getHelpPrompt();
		
		assertNotNull("Help prompt were null.", copy);
		assertTrue("Help prompt didn't match input TTS chunks.", Validator.validateTtsChunks(helpPrompt, copy));
	}
	
	public void testTimeoutPrompt () {
		List<TTSChunk> copy = ( (PerformInteraction) msg).getTimeoutPrompt();
		
		assertNotNull("Timeout prompt were null.", copy);
		assertTrue("Timeout prompt didn't match input TTS chunks.", Validator.validateTtsChunks(timeoutPrompt, copy));
	}
	
	public void testVrHelp () {
		List<VrHelpItem> copy = ( (PerformInteraction) msg).getVrHelp();
		
		assertNotNull("Vr help were null.", copy);
		assertTrue("Vr help items didn't match input data.", Validator.validateVrHelpItems(vrHelp, copy));
	}
	
	public void testChoiceSetIds () {
		List<Integer> copy = ( (PerformInteraction) msg).getInteractionChoiceSetIDList();
		
		assertEquals("Choice set ids didn't match input data.", CHOICE_SET_IDS, copy);
	}
	
	public void testInteractionLayout () {
		LayoutMode copy = ( (PerformInteraction) msg).getInteractionLayout();
		
		assertEquals("Interaction layout didn't match input data.", INTERACTION_LAYOUT, copy);
	}
	
	public void testInitialText () {
		String copy = ( (PerformInteraction) msg).getInitialText();
		
		assertEquals("Initial text didn't match input data.", INITIAL_TEXT, copy);
	}

	public void testInteractionMode () {
		InteractionMode copy = ( (PerformInteraction) msg).getInteractionMode();
		
		assertEquals("Interaction mode didn't match input data.", MODE, copy);
	}
	
	public void testTimeout () {
		Integer copy = ( (PerformInteraction) msg).getTimeout();
		
		assertEquals("Timeout didn't match input data.", TIMEOUT, copy);
	}
	
	public void testNull() {
		PerformInteraction msg = new PerformInteraction();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Initial prompt wasn't set, but getter method returned an object.",     msg.getInitialPrompt());
		assertNull("Help prompt wasn't set, but getter method returned an object.",        msg.getHelpPrompt());
		assertNull("Timeout prompt wasn't set, but getter method returned an object.",     msg.getTimeoutPrompt());
		assertNull("Vr help wasn't set, but getter method returned an object.",            msg.getVrHelp());
		assertNull("Choice set ids wasn't set, but getter method returned an object.",     msg.getInteractionChoiceSetIDList());
		assertNull("Interaction layout wasn't set, but getter method returned an object.", msg.getInteractionLayout());
		assertNull("Initial text wasn't set, but getter method returned an object.",       msg.getInitialText());
		assertNull("Mode wasn't set, but getter method returned an object.",               msg.getInteractionMode());
		assertNull("Timeout wasn't set, but getter method returned an object.",            msg.getTimeout());
	}
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PerformInteraction cmd = new PerformInteraction(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Initial text doesn't match input text", 
					JsonUtils.readStringFromJsonObject(parameters, PerformInteraction.KEY_INITIAL_TEXT), cmd.getInitialText());
			assertEquals("Interaction mode doesn't match input mode", 
					JsonUtils.readStringFromJsonObject(parameters, PerformInteraction.KEY_INTERACTION_MODE), cmd.getInteractionMode().toString());

			List<Integer> interactionIDList = JsonUtils.readIntegerListFromJsonObject(parameters, PerformInteraction.KEY_INTERACTION_CHOICE_SET_ID_LIST);
			List<Integer> testIDList = cmd.getInteractionChoiceSetIDList();
			assertEquals("Interaction choice set ID list length not same as reference choice set ID list", interactionIDList.size(), testIDList.size());
			assertTrue("Interaction choice set ID list doesn't match input ID list", Validator.validateIntegerList(interactionIDList, testIDList));
			
			assertEquals("Interaction layout doesn't match input layout", 
					JsonUtils.readStringFromJsonObject(parameters, PerformInteraction.KEY_INTERACTION_LAYOUT), cmd.getInteractionLayout().toString());
			
			JSONArray initalPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformInteraction.KEY_INITIAL_PROMPT);
			List<TTSChunk> initalPromptList = new ArrayList<TTSChunk>();
			for (int index = 0; index < initalPromptArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)initalPromptArray.get(index)) );
	        	initalPromptList.add(chunk);
			}
			assertTrue("Initial prompt list doesn't match input prompt list",  Validator.validateTtsChunks(initalPromptList, cmd.getInitialPrompt()));
			
			JSONArray helpPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformInteraction.KEY_HELP_PROMPT);
			List<TTSChunk> helpPromptList = new ArrayList<TTSChunk>();
			for (int index = 0; index < helpPromptArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)helpPromptArray.get(index)) );
	        	helpPromptList.add(chunk);
			}
			assertTrue("Help prompt list doesn't match input prompt list",  Validator.validateTtsChunks(helpPromptList, cmd.getHelpPrompt()));
			
			JSONArray timeoutPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformInteraction.KEY_TIMEOUT_PROMPT);
			List<TTSChunk> timeoutPromptList = new ArrayList<TTSChunk>();
			for (int index = 0; index < timeoutPromptArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)timeoutPromptArray.get(index)) );
	        	timeoutPromptList.add(chunk);
			}
			assertTrue("Timeout prompt list doesn't match input prompt list",  Validator.validateTtsChunks(timeoutPromptList, cmd.getTimeoutPrompt()));
			
			assertEquals("Timeout doesn't match input timeout", JsonUtils.readIntegerFromJsonObject(parameters, PerformInteraction.KEY_TIMEOUT), cmd.getTimeout());
			
			JSONArray vrHelpArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformInteraction.KEY_VR_HELP);
			List<VrHelpItem> vrHelpList= new ArrayList<VrHelpItem>();
			for (int index = 0; index < vrHelpArray.length(); index++) {
				VrHelpItem vrHelpItem = new VrHelpItem(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)vrHelpArray.get(index)) );
				vrHelpList.add(vrHelpItem);
			}
			assertTrue("VR help item doesn't match input item",  Validator.validateVrHelpItems(vrHelpList, cmd.getVrHelp()) );

		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
    
}
