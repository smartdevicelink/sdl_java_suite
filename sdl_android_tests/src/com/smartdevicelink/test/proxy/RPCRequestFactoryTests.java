package com.smartdevicelink.test.proxy;

import junit.framework.TestCase;

public class RPCRequestFactoryTests extends TestCase {

	public void testBuildSystemRequest () {
		
		// Test -- buildSystemRequest(String data, Integer correlationID)
		
		// Test -- buildSystemRequestLegacy(Vector<String> data, Integer correlationID)
		
	}
	
	public void testBuildAddCommand () {
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Integer parentID, Integer position,Vector<String> vrCommands, Image cmdIcon, Integer correlationID)
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Integer parentID, Integer position, Vector<String> vrCommands, String IconValue, ImageType IconType, Integer correlationID)
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Integer parentID, Integer position, Vector<String> vrCommands, Integer correlationID)
		
		// Test -- buildAddCommand(Integer commandID, Vector<String> vrCommands, Integer correlationID)
		
	}
	
	public void testBuildAddSubMenu () {
		
		// Test -- buildAddSubMenu(Integer menuID, String menuName, Integer correlationID)
		
		// Test -- buildAddSubMenu(Integer menuID, String menuName, Integer position, Integer correlationID)
		
	}
	
	public void testBuildAlert () {
		
		// Test -- buildAlert(String ttsText, Boolean playTone, Vector<SoftButton> softButtons, Integer correlationID)
		
		// Test -- buildAlert(String alertText1, String alertText2, String alertText3, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		
		// Test -- buildAlert(String ttsText, String alertText1, String alertText2, String alertText3, Boolean playTone, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		
		// Test -- buildAlert(Vector<TTSChunk> chunks, Boolean playTone, Vector<SoftButton> softButtons, Integer correlationID)
		
		// Test -- buildAlert(Vector<TTSChunk> ttsChunks, String alertText1, String alertText2, String alertText3, Boolean playTone, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		
		// Test -- buildAlert(String ttsText, Boolean playTone, Integer correlationID)
			
		// Test -- buildAlert(String alertText1, String alertText2, Integer duration, Integer correlationID)
	
		// Test -- buildAlert(String ttsText, String alertText1, String alertText2, Boolean playTone, Integer duration, Integer correlationID)
		
		// Test -- buildAlert(Vector<TTSChunk> chunks, Boolean playTone, Integer correlationID)
		
		// Test -- buildAlert(Vector<TTSChunk> ttsChunks, String alertText1, String alertText2, Boolean playTone, Integer duration, Integer correlationID)
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