package com.smartdevicelink.test.proxy;

import java.util.Vector;

import com.smartdevicelink.proxy.RPCRequestFactory;
import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SystemRequest;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.test.utils.Validator;

import junit.framework.TestCase;

public class RPCRequestFactoryTests extends TestCase {
	
	private final String MSG = "Response value did not match tested value.";

	public void testBuildSystemRequest () {
		
		String         testData;
		Integer        testInt;
		SystemRequest  testBSR;
		Vector<String> testVData;
		
		// Test -- buildSystemRequest(String data, Integer correlationID)
		testData = "test";
		testInt  = 0;
		testBSR  = RPCRequestFactory.buildSystemRequest(testData, testInt);
		assertEquals(MSG, testData.getBytes(), testBSR.getBulkData());
		assertEquals(MSG, testInt, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequest(testData, null);
		assertNull(MSG, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequest(null, testInt);
		assertNull(MSG, testBSR);
				
		// Test -- buildSystemRequestLegacy(Vector<String> data, Integer correlationID)
		testVData = new Vector<String>();
		testVData.add("Test A");
		testVData.add("Test B");
		testVData.add("Test C");
		testBSR   = RPCRequestFactory.buildSystemRequestLegacy(testVData, testInt);
		assertEquals(MSG, testVData, new Vector<String>(testBSR.getLegacyData()));
		assertEquals(MSG, testInt, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequestLegacy(testVData, null);
		assertNull(MSG, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequestLegacy(null, testInt);
		assertNull(MSG, testBSR);
		
		// Issue #166 -- Null values within the Vector<String> parameter.
		// TODO: Once resolved, add the following test.
		//testVData = new Vector<String>();
		//testVData.add("Test A");
		//testVData.add("Test B");
		//testVData.add(null);
		//testBSR   = RPCRequestFactory.buildSystemRequestLegacy(testVData, testInt);
		//assertNull(MSG, testBSR);		
	}
	
	public void testBuildAddCommand () {
		
		Image          testImage;
		String         testMenuText, testIconValue;		
		Integer        testCommandID, testParentID, testPosition, testCorrelationID;
		ImageType      testIconType;
		AddCommand     testBAC;
		Vector<String> testVrCommands;
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Integer parentID, Integer position,Vector<String> vrCommands, Image cmdIcon, Integer correlationID)
		testImage         = new Image();
		testMenuText      = "menu";
		testPosition      = 1;
		testParentID      = 2;
		testCommandID     = 3;
		testCorrelationID = 4;
		testVrCommands    = new Vector<String>();
		testImage.setImageType(ImageType.STATIC);
		testImage.setValue("image");
		testVrCommands.add("Test A");
		testVrCommands.add("Test B");
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testMenuText, testParentID, testPosition, testVrCommands, testImage, testCorrelationID);
		assertEquals(MSG, testCommandID, testBAC.getCmdID());
		assertEquals(MSG, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(MSG, testParentID, testBAC.getMenuParams().getParentID());
		assertEquals(MSG, testPosition, testBAC.getMenuParams().getPosition());
		assertEquals(MSG, testVrCommands, testBAC.getVrCommands());
		assertEquals(MSG, Validator.validateImage(testImage, testBAC.getCmdIcon()));
		assertEquals(MSG, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null, null, null, null);
		assertNull(MSG, testBAC.getCmdID());
		assertNull(MSG, testBAC.getMenuParams().getMenuName());
		assertNull(MSG, testBAC.getMenuParams().getParentID());
		assertNull(MSG, testBAC.getMenuParams().getPosition());
		assertNull(MSG, testBAC.getVrCommands());
		assertNull(MSG, testBAC.getCmdIcon());
		assertNull(MSG, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Integer parentID, Integer position, Vector<String> vrCommands, String IconValue, ImageType IconType, Integer correlationID)
		testIconValue = "icon";
		testIconType  = ImageType.STATIC;
		testImage     = new Image();
		testImage.setValue(testIconValue);
		testImage.setImageType(testIconType);
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testMenuText, testParentID, testPosition, testVrCommands, testIconValue, testIconType, testCorrelationID);
		assertEquals(MSG, testCommandID, testBAC.getCmdID());
		assertEquals(MSG, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(MSG, testParentID, testBAC.getMenuParams().getParentID());
		assertEquals(MSG, testPosition, testBAC.getMenuParams().getPosition());
		assertEquals(MSG, testVrCommands, testBAC.getVrCommands());
		assertEquals(MSG, testCorrelationID, testBAC.getCorrelationID());		
		assertEquals(MSG, Validator.validateImage(testImage, testBAC.getCmdIcon()));
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null, null, null, null, null);
		assertNull(MSG, testBAC.getCmdID());
		assertNull(MSG, testBAC.getMenuParams().getMenuName());
		assertNull(MSG, testBAC.getMenuParams().getParentID());
		assertNull(MSG, testBAC.getMenuParams().getPosition());
		assertNull(MSG, testBAC.getVrCommands());
		assertNull(MSG, testBAC.getCmdIcon().getValue());
		assertNull(MSG, testBAC.getCmdIcon().getImageType());
		assertNull(MSG, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Integer parentID, Integer position, Vector<String> vrCommands, Integer correlationID)
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testMenuText, testParentID, testPosition, testVrCommands, testCorrelationID);
		assertEquals(MSG, testCommandID, testBAC.getCmdID());
		assertEquals(MSG, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(MSG, testParentID, testBAC.getMenuParams().getParentID());
		assertEquals(MSG, testPosition, testBAC.getMenuParams().getPosition());
		assertEquals(MSG, testVrCommands, testBAC.getVrCommands());
		assertEquals(MSG, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null, null, null);
		assertNull(MSG, testBAC.getCmdID());
		assertNull(MSG, testBAC.getMenuParams().getMenuName());
		assertNull(MSG, testBAC.getMenuParams().getParentID());
		assertNull(MSG, testBAC.getMenuParams().getPosition());
		assertNull(MSG, testBAC.getVrCommands());
		assertNull(MSG, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Vector<String> vrCommands, Integer correlationID)
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testMenuText, testVrCommands, testCorrelationID);
		assertEquals(MSG, testCommandID, testBAC.getCmdID());
		assertEquals(MSG, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(MSG, testVrCommands, testBAC.getVrCommands());
		assertEquals(MSG, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null);
		assertNull(MSG, testBAC.getCmdID());
		assertNull(MSG, testBAC.getMenuParams().getMenuName());
		assertNull(MSG, testBAC.getVrCommands());
		assertNull(MSG, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, Vector<String> vrCommands, Integer correlationID)
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testVrCommands, testCorrelationID);
		assertEquals(MSG, testCommandID, testBAC.getCmdID());
		assertEquals(MSG, testVrCommands, testBAC.getVrCommands());
		assertEquals(MSG, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null);
		assertNull(MSG, testBAC.getCmdID());
		assertNull(MSG, testBAC.getVrCommands());
		assertNull(MSG, testBAC.getCorrelationID());
	}
	
	public void testBuildAddSubMenu () {
		
		Integer    testMenuID, testCorrelationID, testPosition;
		String     testMenuName;
		AddSubMenu testBASM;
		
		// Test -- buildAddSubMenu(Integer menuID, String menuName, Integer correlationID)
		// ^ Calls another build method.
		// Test -- buildAddSubMenu(Integer menuID, String menuName, Integer position, Integer correlationID)
		testMenuID        = 0;
		testMenuName      = "name";
		testPosition      = 1;
		testCorrelationID = 2;
		testBASM = RPCRequestFactory.buildAddSubMenu(testMenuID, testMenuName, testPosition, testCorrelationID);
		assertEquals(MSG, testMenuID, testBASM.getMenuID());
		assertEquals(MSG, testMenuName, testBASM.getMenuName());
		assertEquals(MSG, testPosition, testBASM.getPosition());
		assertEquals(MSG, testCorrelationID, testBASM.getCorrelationID());
		
		testBASM = RPCRequestFactory.buildAddSubMenu(null, null, null, null);
		assertNull(MSG, testBASM.getMenuID());
		assertNull(MSG, testBASM.getMenuName());
		assertNull(MSG, testBASM.getPosition());
		assertNull(MSG, testBASM.getCorrelationID());
	}
	
	public void testBuildAlert () {
		
		Alert              testAlert;
		String             testTTSText, testAlertText1, testAlertText2, testAlertText3;
		Integer            testCorrelationID, testDuration;
		Boolean            testPlayTone;
		Vector<SoftButton> testSoftButtons;
		Vector<TTSChunk>   testTtsChunks;
				
		// Test -- buildAlert(String ttsText, Boolean playTone, Vector<SoftButton> softButtons, Integer correlationID)
		testTTSText       = "simple test";
		testCorrelationID = 0;
		testPlayTone      = true;
		testSoftButtons   = new Vector<SoftButton>();
		SoftButton test1  = new SoftButton();
		test1.setText("test 1");
		SoftButton test2  = new SoftButton();
		test2.setText("test 2");
		testSoftButtons.add(test1);
		testSoftButtons.add(test2);		
		testAlert = RPCRequestFactory.buildAlert(testTTSText, testPlayTone, testSoftButtons, testCorrelationID);
		assertTrue(MSG, Validator.validateTtsChunks(TTSChunkFactory.createSimpleTTSChunks(testTTSText), testAlert.getTtsChunks()));
		// ^ Calls another build method.
		
		// Test -- buildAlert(String alertText1, String alertText2, String alertText3, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		testAlertText1 = "test 1";
		testAlertText2 = "test 2";
		testAlertText3 = "test 3";
		testDuration   = 1;	
		// ^ Calls another build method.
		
		// Test -- buildAlert(String ttsText, String alertText1, String alertText2, String alertText3, Boolean playTone, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		testAlert = RPCRequestFactory.buildAlert(testTTSText, testAlertText1, testAlertText2, testAlertText3, testPlayTone, testDuration, testSoftButtons, testCorrelationID);
		assertTrue(MSG, Validator.validateTtsChunks(TTSChunkFactory.createSimpleTTSChunks(testTTSText), testAlert.getTtsChunks()));
		// ^ Calls another build method.
		
		// Test -- buildAlert(Vector<TTSChunk> chunks, Boolean playTone, Vector<SoftButton> softButtons, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildAlert(Vector<TTSChunk> ttsChunks, String alertText1, String alertText2, String alertText3, Boolean playTone, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		testTtsChunks = TTSChunkFactory.createSimpleTTSChunks(testTTSText);
		testAlert = RPCRequestFactory.buildAlert(testTtsChunks, testAlertText1, testAlertText2, testAlertText3, testPlayTone, testDuration, testSoftButtons, testCorrelationID);
		assertTrue(MSG, Validator.validateTtsChunks(testTtsChunks, testAlert.getTtsChunks()));
		assertEquals(MSG, testAlertText1, testAlert.getAlertText1());
		assertEquals(MSG, testAlertText2, testAlert.getAlertText2());
		assertEquals(MSG, testAlertText3, testAlert.getAlertText3());
		assertEquals(MSG, testPlayTone, testAlert.getPlayTone());
		assertEquals(MSG, testDuration, testAlert.getDuration());
		assertTrue(MSG, Validator.validateSoftButtons(testSoftButtons, testAlert.getSoftButtons()));
		assertEquals(MSG, testCorrelationID, testAlert.getCorrelationID());
		
		testAlert = RPCRequestFactory.buildAlert((Vector<TTSChunk>) null, null, null, null, null, null, null, null);
		assertNull(MSG, testAlert.getTtsChunks());
		assertNull(MSG, testAlert.getAlertText1());
		assertNull(MSG, testAlert.getAlertText2());
		assertNull(MSG, testAlert.getAlertText3());
		assertNull(MSG, testAlert.getPlayTone());
		assertNull(MSG, testAlert.getDuration());
		assertNull(MSG, testAlert.getSoftButtons());
		assertNull(MSG, testAlert.getCorrelationID());
		
		// Test -- buildAlert(String ttsText, Boolean playTone, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildAlert(String alertText1, String alertText2, Integer duration, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildAlert(String ttsText, String alertText1, String alertText2, Boolean playTone, Integer duration, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildAlert(Vector<TTSChunk> chunks, Boolean playTone, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildAlert(Vector<TTSChunk> ttsChunks, String alertText1, String alertText2, Boolean playTone, Integer duration, Integer correlationID)
		testAlert = RPCRequestFactory.buildAlert(testTtsChunks, testAlertText1, testAlertText2, testPlayTone, testDuration, testCorrelationID);
		assertTrue(MSG, Validator.validateTtsChunks(testTtsChunks, testAlert.getTtsChunks()));
		assertEquals(MSG, testAlertText1, testAlert.getAlertText1());
		assertEquals(MSG, testAlertText2, testAlert.getAlertText2());
		assertEquals(MSG, testPlayTone, testAlert.getPlayTone());
		assertEquals(MSG, testDuration, testAlert.getDuration());
		assertEquals(MSG, testCorrelationID, testAlert.getCorrelationID());
		
		testAlert = RPCRequestFactory.buildAlert((Vector<TTSChunk>) null, null, null, null, null, null);
		assertNull(MSG, testAlert.getTtsChunks());
		assertNull(MSG, testAlert.getAlertText1());
		assertNull(MSG, testAlert.getAlertText2());
		assertNull(MSG, testAlert.getPlayTone());
		assertNull(MSG, testAlert.getDuration());
		assertNull(MSG, testAlert.getCorrelationID());
	}
	
	public void testCreateInteractionChoiceSet () {
		
		// Test --buildCreateInteractionChoiceSet(Vector<Choice> choiceSet, Integer interactionChoiceSetID, Integer correlationID)
		
	}
	
	public void testDeleteCommand () {
		
		// Test -- buildDeleteCommand(Integer commandID, Integer correlationID)
		
	}
	
	public void testDeleteFile () {
		
		// Test --buildDeleteFile(String sdlFileName, Integer correlationID)
		
	}
	
	public void testDeleteInteractionChoiceSet () {
		
		// Test -- buildDeleteInteractionChoiceSet(Integer interactionChoiceSetID, Integer correlationID)
		
	}
	
	public void testDeleteSubMenu () {
		
		// Test -- buildDeleteSubMenu(Integer menuID, Integer correlationID)
		
	}
	
	public void testBuildListFiles () {
		
		// Test -- buildListFiles(Integer correlationID) 
	}
	
	public void testBuildPerformInteraction () {
		
		// Test -- buildPerformInteraction(Vector<TTSChunk> initChunks, String displayText, Vector<Integer> interactionChoiceSetIDList, Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp, Integer correlationID)
	
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Vector<Integer> interactionChoiceSetIDList, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp, Integer correlationID)
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp, Integer correlationID)
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, Vector<VrHelpItem> vrHelp, Integer correlationID)
	
		// Test -- buildPerformInteraction(Vector<TTSChunk> initChunks, String displayText, Vector<Integer> interactionChoiceSetIDList, Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Vector<Integer> interactionChoiceSetIDList, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, Integer correlationID)
		
		// Test -- buildPerformInteraction(Vector<TTSChunk> initChunks, String displayText, Vector<Integer> interactionChoiceSetIDList, Vector<TTSChunk> helpChunks, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Vector<Integer> interactionChoiceSetIDList, String helpPrompt, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		
	}
	
	public void testBuildPutFiles () {
		
		// Test -- buildPutFile(String sdlFileName, FileType fileType, Boolean persistentFile, byte[] fileData, Integer correlationID)
		
		// Test -- buildPutFile(String sdlFileName, Integer iOffset, Integer iLength)
		
		// Test -- buildPutFile(String syncFileName, Integer iOffset, Integer iLength, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile)
				
	}
	
	public void testBuildRegisterAppInterface () {
		
		// Test -- buildRegisterAppInterface(String appName, Boolean isMediaApp, String appID)
	
		// Test -- buildRegisterAppInterface(SdlMsgVersion sdlMsgVersion, String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp,  Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String appID, Integer correlationID)
	}
	
	public void testBuildSetAppIcon () {
		
		// Test -- buildSetAppIcon(String sdlFileName, Integer correlationID)
		
	}
	
	public void testBuildSetGlobalProperties () {
		
		// Test -- buildSetGlobalProperties(String helpPrompt, String timeoutPrompt, Integer correlationID)
		
		// Test -- buildSetGlobalProperties(Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, Integer correlationID)
		
		// Test -- buildSetGlobalProperties(String helpPrompt, String timeoutPrompt, String vrHelpTitle, Vector<VrHelpItem> vrHelp, Integer correlationID)
		
		// Test -- buildSetGlobalProperties(Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, String vrHelpTitle, Vector<VrHelpItem> vrHelp, Integer correlationID)
				
	}
	
	public void testBuildSetMediaClockTimer () {
		
		// Test -- buildSetMediaClockTimer(Integer hours, Integer minutes, Integer seconds, UpdateMode updateMode, Integer correlationID)
	
		// Test -- buildSetMediaClockTimer(UpdateMode updateMode, Integer correlationID)
	}
	
	public void testBuildShow () {
		
		// Test -- buildShow(String mainText1, String mainText2, String mainText3, String mainText4, String statusBar, String mediaClock, String mediaTrack, Image graphic, Vector<SoftButton> softButtons, Vector <String> customPresets, TextAlignment alignment, Integer correlationID)
		
		// Test -- buildShow(String mainText1, String mainText2, String mainText3, String mainText4, TextAlignment alignment, Integer correlationID)
		
		// Test -- buildShow(String mainText1, String mainText2, String statusBar, String mediaClock, String mediaTrack, TextAlignment alignment, Integer correlationID)
	
		// Test -- buildShow(String mainText1, String mainText2, TextAlignment alignment, Integer correlationID)
		
	}
	
	public void testBuildSpeak () {
		
		// Test -- buildSpeak(String ttsText, Integer correlationID)
		
		// Test -- buildSpeak(Vector<TTSChunk> ttsChunks, Integer correlationID)
	}
	
	public void testBuildSubscribeButton () {
		
		// Test -- buildSubscribeButton(ButtonName buttonName, Integer correlationID)
		
	}
	
	public void testBuildUnregisterAppInterface () {
		
		// Test -- buildUnregisterAppInterface(Integer correlationID)
	}
	
	public void testBuildUnsubscribeButton () {
		
		// Test -- buildUnsubscribeButton(ButtonName buttonName, Integer correlationID)
		
	}
	
	public void testBuildSubscribeVehicleData () {
		
		// Test -- BuildSubscribeVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State, boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus, boolean driverBraking, Integer correlationID) 
		
		// Test -- BuildUnsubscribeVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State, boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus, boolean driverBraking, Integer correlationID) 
		
		// Test -- BuildGetVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State, boolean instantFuelConsumption, boolean externalTemperature, boolean vin, boolean prndl, boolean tirePressure, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus, boolean driverBraking, Integer correlationID)
		
	}
	
	public void testBuildScrollableMessage () {
		
		// Test -- BuildScrollableMessage(String scrollableMessageBody, Integer timeout, Vector<SoftButton> softButtons, Integer correlationID)	
		
	}
	
	public void testBuildSlider () {
		
		// Test -- BuildSlider(Integer numTicks, Integer position, String sliderHeader, Vector<String> sliderFooter, Integer timeout, Integer correlationID)
		
	}
	
	public void testBuildChangeRegistration () {
		
		// Test -- BuildChangeRegistration(Language language, Language hmiDisplayLanguage, Integer correlationID)
		
	}
	
	public void testBuildSetDisplayLayout () {
		
		// Test -- BuildSetDisplayLayout(String displayLayout, Integer correlationID)
		
	}
	
	public void testBuildPerformAudioPassThru () {
		
		// Test -- BuildPerformAudioPassThru(String ttsText, String audioPassThruDisplayText1, String audioPassThruDisplayText2, SamplingRate samplingRate, Integer maxDuration, BitsPerSample bitsPerSample, AudioType audioType, Boolean muteAudio, Integer correlationID)

		// Test -- BuildPerformAudioPassThru(Vector<TTSChunk> initialPrompt, String audioPassThruDisplayText1, String audioPassThruDisplayText2, SamplingRate samplingRate, Integer maxDuration, BitsPerSample bitsPerSample, AudioType audioType, Boolean muteAudio, Integer correlationID)

	}
	
	public void testBuildEndAudioPassThru () {
		
		// Test -- BuildEndAudioPassThru(Integer correlationID)
	}	
}