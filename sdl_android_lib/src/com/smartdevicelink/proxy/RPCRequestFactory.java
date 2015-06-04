package com.smartdevicelink.proxy;

import java.util.Vector;

import com.smartdevicelink.rpc.datatypes.Choice;
import com.smartdevicelink.rpc.datatypes.Image;
import com.smartdevicelink.rpc.datatypes.MenuParams;
import com.smartdevicelink.rpc.datatypes.SdlMsgVersion;
import com.smartdevicelink.rpc.datatypes.SoftButton;
import com.smartdevicelink.rpc.datatypes.StartTime;
import com.smartdevicelink.rpc.datatypes.TtsChunk;
import com.smartdevicelink.rpc.datatypes.VrHelpItem;
import com.smartdevicelink.rpc.enums.AppHmiType;
import com.smartdevicelink.rpc.enums.AudioType;
import com.smartdevicelink.rpc.enums.BitsPerSample;
import com.smartdevicelink.rpc.enums.ButtonName;
import com.smartdevicelink.rpc.enums.FileType;
import com.smartdevicelink.rpc.enums.ImageType;
import com.smartdevicelink.rpc.enums.InteractionMode;
import com.smartdevicelink.rpc.enums.Language;
import com.smartdevicelink.rpc.enums.RequestType;
import com.smartdevicelink.rpc.enums.SamplingRate;
import com.smartdevicelink.rpc.enums.TextAlignment;
import com.smartdevicelink.rpc.enums.UpdateMode;
import com.smartdevicelink.rpc.requests.AddCommand;
import com.smartdevicelink.rpc.requests.AddSubMenu;
import com.smartdevicelink.rpc.requests.Alert;
import com.smartdevicelink.rpc.requests.ChangeRegistration;
import com.smartdevicelink.rpc.requests.CreateInteractionChoiceSet;
import com.smartdevicelink.rpc.requests.DeleteCommand;
import com.smartdevicelink.rpc.requests.DeleteFile;
import com.smartdevicelink.rpc.requests.DeleteInteractionChoiceSet;
import com.smartdevicelink.rpc.requests.DeleteSubMenu;
import com.smartdevicelink.rpc.requests.EndAudioPassThru;
import com.smartdevicelink.rpc.requests.GetVehicleData;
import com.smartdevicelink.rpc.requests.ListFiles;
import com.smartdevicelink.rpc.requests.PerformAudioPassThru;
import com.smartdevicelink.rpc.requests.PerformInteraction;
import com.smartdevicelink.rpc.requests.PutFile;
import com.smartdevicelink.rpc.requests.RegisterAppInterface;
import com.smartdevicelink.rpc.requests.ScrollableMessage;
import com.smartdevicelink.rpc.requests.SetAppIcon;
import com.smartdevicelink.rpc.requests.SetDisplayLayout;
import com.smartdevicelink.rpc.requests.SetGlobalProperties;
import com.smartdevicelink.rpc.requests.SetMediaClockTimer;
import com.smartdevicelink.rpc.requests.Show;
import com.smartdevicelink.rpc.requests.Slider;
import com.smartdevicelink.rpc.requests.Speak;
import com.smartdevicelink.rpc.requests.SubscribeButton;
import com.smartdevicelink.rpc.requests.SubscribeVehicleData;
import com.smartdevicelink.rpc.requests.UnregisterAppInterface;
import com.smartdevicelink.rpc.requests.UnsubscribeButton;
import com.smartdevicelink.rpc.requests.UnsubscribeVehicleData;
import com.smartdevicelink.rpc.responses.SystemRequest;

public class RpcRequestFactory {

	public static final int SDL_MSG_MAJOR_VERSION = 1;
	public static final int SDL_MSG_MINOR_VERSION = 0;

	
	public static SystemRequest buildSystemRequest(
			String data, Integer correlationId) {
		
		if(data == null) return null;
		
		SystemRequest msg = new SystemRequest();
		msg.setRequestType(RequestType.PROPRIETARY);
		msg.setCorrelationId(correlationId);
		msg.setBulkData(data.getBytes());
		return msg;
	}	
	
	
	public static SystemRequest buildSystemRequestLegacy(
			Vector<String> data, Integer correlationId) {
		
		if(data == null) return null;
		
		SystemRequest msg = new SystemRequest(true);
		msg.setCorrelationId(correlationId);
		msg.setLegacyData(data);
		return msg;
	}	
	

	public static AddCommand buildAddCommand(Integer commandId,
			String menuText, Integer parentId, Integer position,
			Vector<String> vrCommands, Image cmdIcon, Integer correlationId) {
		AddCommand msg = new AddCommand();
		msg.setCorrelationId(correlationId);
		msg.setCmdId(commandId);
		msg.setVrCommands(vrCommands);
		
		if (cmdIcon != null) msg.setCmdIcon(cmdIcon);
		
		if(menuText != null || parentId != null || position != null) {
			MenuParams menuParams = new MenuParams();
			menuParams.setMenuName(menuText);
			menuParams.setPosition(position);
			menuParams.setParentId(parentId);
			msg.setMenuParams(menuParams);
		}
		
		return msg;
	}
	
	public static AddCommand buildAddCommand(Integer commandId,
			String menuText, Integer parentId, Integer position,
			Vector<String> vrCommands, String IconValue, ImageType IconType, Integer correlationId) {
		AddCommand msg = new AddCommand();
		msg.setCorrelationId(correlationId);
		msg.setCmdId(commandId);
		
		if (vrCommands != null) msg.setVrCommands(vrCommands);
		
		Image cmdIcon = null;
		
		if (IconValue != null && IconType != null)
		{
			cmdIcon = new Image();
			cmdIcon.setValue(IconValue);			
			cmdIcon.setImageType(IconType);			
		}				
		
		if (cmdIcon != null) msg.setCmdIcon(cmdIcon);
		
		if(menuText != null || parentId != null || position != null) {
			MenuParams menuParams = new MenuParams();
			menuParams.setMenuName(menuText);
			menuParams.setPosition(position);
			menuParams.setParentId(parentId);
			msg.setMenuParams(menuParams);
		}
		
		return msg;
	}

		
	public static AddCommand buildAddCommand(Integer commandId,
			String menuText, Integer parentId, Integer position,
			Vector<String> vrCommands, Integer correlationId) {
		AddCommand msg = new AddCommand();
		msg.setCorrelationId(correlationId);
		msg.setCmdId(commandId);
		msg.setVrCommands(vrCommands);
		
		if(menuText != null || parentId != null || position != null) {
			MenuParams menuParams = new MenuParams();
			menuParams.setMenuName(menuText);
			menuParams.setPosition(position);
			menuParams.setParentId(parentId);
			msg.setMenuParams(menuParams);
		}
		
		return msg;
	}
	
	public static AddCommand buildAddCommand(Integer commandId,
			String menuText, Vector<String> vrCommands, Integer correlationId) {
		AddCommand msg = buildAddCommand(commandId, menuText, null, null,
				vrCommands, correlationId);
		return msg;
	}
	
	public static AddCommand buildAddCommand(Integer commandId,
			Vector<String> vrCommands, Integer correlationId) {
		AddCommand msg = new AddCommand();
		msg.setCorrelationId(correlationId);
		msg.setCmdId(commandId);
		msg.setVrCommands(vrCommands);

		return msg;
	}

	public static AddSubMenu buildAddSubMenu(Integer menuId, String menuName,
			Integer correlationId) {
		AddSubMenu msg = buildAddSubMenu(menuId, menuName, null, correlationId);
		return msg;
	}

	public static AddSubMenu buildAddSubMenu(Integer menuId, String menuName,
			Integer position, Integer correlationId) {
		AddSubMenu msg = new AddSubMenu();
		msg.setCorrelationId(correlationId);
		msg.setMenuName(menuName);
		msg.setMenuId(menuId);
		msg.setPosition(position);

		return msg;
	}
	

	public static Alert buildAlert(String ttsText, Boolean playTone, Vector<SoftButton> softButtons,
			Integer correlationId) {
		Vector<TtsChunk> chunks = TtsChunkFactory
				.createSimpleTtsChunks(ttsText);
		Alert msg = buildAlert(chunks, null, null, null, playTone, null, softButtons,
				correlationId);
		return msg;
	}
	
	public static Alert buildAlert(String alertText1, String alertText2, String alertText3,
			Integer duration, Vector<SoftButton> softButtons, Integer correlationId) {
		Alert msg = buildAlert((Vector<TtsChunk>) null, alertText1, alertText2, alertText3,
				null, duration, softButtons, correlationId);
		return msg;
	}
	
	public static Alert buildAlert(String ttsText, String alertText1,
			String alertText2, String alertText3, Boolean playTone, Integer duration, Vector<SoftButton> softButtons,
			Integer correlationId) {
		Vector<TtsChunk> chunks = TtsChunkFactory
				.createSimpleTtsChunks(ttsText);
		Alert msg = buildAlert(chunks, alertText1, alertText2, alertText3, playTone, 
				duration, softButtons, correlationId);
		return msg;
	}
	
	public static Alert buildAlert(Vector<TtsChunk> chunks, Boolean playTone, Vector<SoftButton> softButtons,
			Integer correlationId) {
		Alert msg = buildAlert(chunks, null, null, null, playTone, null, softButtons, correlationId);
		return msg;
	}
	
	public static Alert buildAlert(Vector<TtsChunk> ttsChunks,
			String alertText1, String alertText2, String alertText3, Boolean playTone,
			Integer duration, Vector<SoftButton> softButtons, Integer correlationId) {
		Alert msg = new Alert();
		msg.setCorrelationId(correlationId);
		msg.setAlertText1(alertText1);
		msg.setAlertText2(alertText2);
		msg.setAlertText3(alertText3);
		msg.setDuration(duration);
		msg.setPlayTone(playTone);
		msg.setTtsChunks(ttsChunks);
		msg.setSoftButtons(softButtons);

		return msg;
	}

	
	
	public static Alert buildAlert(String ttsText, Boolean playTone,
			Integer correlationId) {
		Vector<TtsChunk> chunks = TtsChunkFactory
				.createSimpleTtsChunks(ttsText);
		Alert msg = buildAlert(chunks, null, null, playTone, null,
				correlationId);
		return msg;
	}
	
	public static Alert buildAlert(String alertText1, String alertText2,
			Integer duration, Integer correlationId) {
		Alert msg = buildAlert((Vector<TtsChunk>) null, alertText1, alertText2,
				null, duration, correlationId);
		return msg;
	}
	
	public static Alert buildAlert(String ttsText, String alertText1,
			String alertText2, Boolean playTone, Integer duration,
			Integer correlationId) {
		Vector<TtsChunk> chunks = TtsChunkFactory
				.createSimpleTtsChunks(ttsText);
		Alert msg = buildAlert(chunks, alertText1, alertText2, playTone,
				duration, correlationId);
		return msg;
	}
	
	public static Alert buildAlert(Vector<TtsChunk> chunks, Boolean playTone,
			Integer correlationId) {
		Alert msg = buildAlert(chunks, null, null, playTone, null,
				correlationId);
		return msg;
	}
	
	public static Alert buildAlert(Vector<TtsChunk> ttsChunks,
			String alertText1, String alertText2, Boolean playTone,
			Integer duration, Integer correlationId) {
		Alert msg = new Alert();
		msg.setCorrelationId(correlationId);
		msg.setAlertText1(alertText1);
		msg.setAlertText2(alertText2);
		msg.setDuration(duration);
		msg.setPlayTone(playTone);
		msg.setTtsChunks(ttsChunks);

		return msg;
	}
	
	public static CreateInteractionChoiceSet buildCreateInteractionChoiceSet(
			Vector<Choice> choiceSet, Integer interactionChoiceSetId,
			Integer correlationId) {
		CreateInteractionChoiceSet msg = new CreateInteractionChoiceSet();
		msg.setChoiceSet(choiceSet);
		msg.setInteractionChoiceSetId(interactionChoiceSetId);
		msg.setCorrelationId(correlationId);
		return msg;
	}
	
	public static DeleteCommand buildDeleteCommand(Integer commandId,
			Integer correlationId) {
		DeleteCommand msg = new DeleteCommand();
		msg.setCmdId(commandId);
		msg.setCorrelationId(correlationId);
		return msg;
	}
	
	public static DeleteFile buildDeleteFile(String sdlFileName,
			Integer correlationId) {
		DeleteFile deleteFile = new DeleteFile();
		deleteFile.setCorrelationId(correlationId);
		deleteFile.setSdlFileName(sdlFileName);
		return deleteFile;
	}
	
	public static DeleteInteractionChoiceSet buildDeleteInteractionChoiceSet(
			Integer interactionChoiceSetId, Integer correlationId) {
		DeleteInteractionChoiceSet msg = new DeleteInteractionChoiceSet();
		msg.setInteractionChoiceSetId(interactionChoiceSetId);
		msg.setCorrelationId(correlationId);

		return msg;
	}
	
	public static DeleteSubMenu buildDeleteSubMenu(Integer menuId,
			Integer correlationId) {
		DeleteSubMenu msg = new DeleteSubMenu();
		msg.setCorrelationId(correlationId);
		msg.setMenuId(menuId);

		return msg;
	}
	
	public static ListFiles buildListFiles(Integer correlationId) {
		ListFiles listFiles = new ListFiles();
		listFiles.setCorrelationId(correlationId);
		return listFiles;
	}

	
	public static PerformInteraction buildPerformInteraction(
			Vector<TtsChunk> initChunks, String displayText,
			Vector<Integer> interactionChoiceSetIdList,
			Vector<TtsChunk> helpChunks, Vector<TtsChunk> timeoutChunks,
			InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp,
			Integer correlationId) {
		PerformInteraction msg = new PerformInteraction();
		msg.setInitialPrompt(initChunks);
		msg.setInitialText(displayText);
		msg.setInteractionChoiceSetIdList(interactionChoiceSetIdList);
		msg.setInteractionMode(interactionMode);
		msg.setTimeout(timeout);
		msg.setHelpPrompt(helpChunks);
		msg.setTimeoutPrompt(timeoutChunks);
		msg.setVrHelp(vrHelp);
		msg.setCorrelationId(correlationId);
		
		return msg;
	}

	public static PerformInteraction buildPerformInteraction(
			String initPrompt, 	String displayText, 
			Vector<Integer> interactionChoiceSetIdList,
			String helpPrompt, String timeoutPrompt,
			InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp,
			Integer correlationId) {
		Vector<TtsChunk> initChunks = TtsChunkFactory
				.createSimpleTtsChunks(initPrompt);
		Vector<TtsChunk> helpChunks = TtsChunkFactory
				.createSimpleTtsChunks(helpPrompt);
		Vector<TtsChunk> timeoutChunks = TtsChunkFactory
				.createSimpleTtsChunks(timeoutPrompt);
		return buildPerformInteraction(initChunks,
				displayText, interactionChoiceSetIdList, helpChunks,
				timeoutChunks, interactionMode, timeout, vrHelp, correlationId);
	}
	
	public static PerformInteraction buildPerformInteraction(
			String initPrompt, 	String displayText, 
			Integer interactionChoiceSetId,
			String helpPrompt, String timeoutPrompt,
			InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp,
			Integer correlationId) {
		Vector<Integer> interactionChoiceSetIDs = new Vector<Integer>();
			interactionChoiceSetIDs.add(interactionChoiceSetId);
		
		return buildPerformInteraction(
				initPrompt, displayText, interactionChoiceSetIDs, 
				helpPrompt, timeoutPrompt, interactionMode, 
				timeout, vrHelp, correlationId);
	}
	
	public static PerformInteraction buildPerformInteraction(String initPrompt,
			String displayText, Integer interactionChoiceSetId, Vector<VrHelpItem> vrHelp,
			Integer correlationId) {

		return buildPerformInteraction(initPrompt, displayText, 
				interactionChoiceSetId, null, null,
				InteractionMode.BOTH, null, vrHelp, correlationId);
	}
	
	
	public static PerformInteraction buildPerformInteraction(
			Vector<TtsChunk> initChunks, String displayText,
			Vector<Integer> interactionChoiceSetIdList,
			Vector<TtsChunk> helpChunks, Vector<TtsChunk> timeoutChunks,
			InteractionMode interactionMode, Integer timeout,
			Integer correlationId) {
		PerformInteraction msg = new PerformInteraction();
		msg.setInitialPrompt(initChunks);
		msg.setInitialText(displayText);
		msg.setInteractionChoiceSetIdList(interactionChoiceSetIdList);
		msg.setInteractionMode(interactionMode);
		msg.setTimeout(timeout);
		msg.setHelpPrompt(helpChunks);
		msg.setTimeoutPrompt(timeoutChunks);
		msg.setCorrelationId(correlationId);
		
		return msg;
	}

	public static PerformInteraction buildPerformInteraction(
			String initPrompt, 	String displayText, 
			Vector<Integer> interactionChoiceSetIdList,
			String helpPrompt, String timeoutPrompt,
			InteractionMode interactionMode, Integer timeout,
			Integer correlationId) {
		Vector<TtsChunk> initChunks = TtsChunkFactory
				.createSimpleTtsChunks(initPrompt);
		Vector<TtsChunk> helpChunks = TtsChunkFactory
				.createSimpleTtsChunks(helpPrompt);
		Vector<TtsChunk> timeoutChunks = TtsChunkFactory
				.createSimpleTtsChunks(timeoutPrompt);
		return buildPerformInteraction(initChunks,
				displayText, interactionChoiceSetIdList, helpChunks,
				timeoutChunks, interactionMode, timeout, correlationId);
	}
	
	public static PerformInteraction buildPerformInteraction(
			String initPrompt, 	String displayText, 
			Integer interactionChoiceSetId,
			String helpPrompt, String timeoutPrompt,
			InteractionMode interactionMode, Integer timeout,
			Integer correlationId) {
		Vector<Integer> interactionChoiceSetIds = new Vector<Integer>();
			interactionChoiceSetIds.add(interactionChoiceSetId);
		
		return buildPerformInteraction(
				initPrompt, displayText, interactionChoiceSetIds, 
				helpPrompt, timeoutPrompt, interactionMode, 
				timeout, correlationId);
	}
	
	public static PerformInteraction buildPerformInteraction(String initPrompt,
			String displayText, Integer interactionChoiceSetId,
			Integer correlationId) {

		return buildPerformInteraction(initPrompt, displayText, 
				interactionChoiceSetId, null, null,
				InteractionMode.BOTH, null, correlationId);
	}
	
	@Deprecated
	public static PerformInteraction buildPerformInteraction(
			Vector<TtsChunk> initChunks, String displayText,
			Vector<Integer> interactionChoiceSetIdList,
			Vector<TtsChunk> helpChunks, InteractionMode interactionMode,
			Integer timeout, Integer correlationId) {
		PerformInteraction msg = new PerformInteraction();
		msg.setInitialPrompt(initChunks);
		msg.setInitialText(displayText);
		msg.setInteractionChoiceSetIdList(interactionChoiceSetIdList);
		msg.setInteractionMode(interactionMode);
		msg.setTimeout(timeout);
		msg.setHelpPrompt(helpChunks);
		msg.setCorrelationId(correlationId);
		return msg;
	}
	
	@Deprecated
	public static PerformInteraction buildPerformInteraction(String initPrompt,
			String displayText, Vector<Integer> interactionChoiceSetIdList,
			String helpPrompt, InteractionMode interactionMode,
			Integer timeout, Integer correlationId) {
		Vector<TtsChunk> initChunks = TtsChunkFactory
				.createSimpleTtsChunks(initPrompt);
		Vector<TtsChunk> helpChunks = TtsChunkFactory
				.createSimpleTtsChunks(helpPrompt);
		PerformInteraction msg = buildPerformInteraction(initChunks,
				displayText, interactionChoiceSetIdList, helpChunks,
				interactionMode, timeout, correlationId);
		return msg;
	}
	
	public static PutFile buildPutFile(String sdlFileName, FileType fileType,
			Boolean persistentFile, byte[] fileData, Integer correlationId) {
		PutFile putFile = new PutFile();
		putFile.setCorrelationId(correlationId);
		putFile.setSdlFileName(sdlFileName);
		putFile.setFileType(fileType);
		putFile.setPersistentFile(persistentFile);
		putFile.setBulkData(fileData);
		return putFile;
	}
	
	@SuppressWarnings("deprecation")
	public static PutFile buildPutFile(String sdlFileName, Integer iOffset, Integer iLength) {
		PutFile putFile = new PutFile();
		putFile.setCorrelationId(10000);
		putFile.setSdlFileName(sdlFileName);		
		putFile.setFileType(FileType.BINARY);
		putFile.setSystemFile(true);
		putFile.setOffset(iOffset);
		putFile.setLength(iLength);
		return putFile;
	}	

	@SuppressWarnings("deprecation")
	public static PutFile buildPutFile(String syncFileName, Integer iOffset, Integer iLength, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile) {
		PutFile putFile = new PutFile();
		putFile.setCorrelationId(10000);
		putFile.setSdlFileName(syncFileName);
		putFile.setFileType(fileType);
		putFile.setPersistentFile(bPersistentFile);
		putFile.setSystemFile(bSystemFile);
		putFile.setOffset(iOffset);
		putFile.setLength(iLength);
		return putFile;
	}
	
	@SuppressWarnings("deprecation")
	public static PutFile buildPutFile(String sdlFileName, Integer iOffset, Integer iLength, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile, Integer iCorrelationId) {
		PutFile putFile = new PutFile();
		putFile.setCorrelationId(iCorrelationId);
		putFile.setSdlFileName(sdlFileName);
		putFile.setFileType(fileType);
		putFile.setPersistentFile(bPersistentFile);
		putFile.setSystemFile(bSystemFile);
		putFile.setOffset(iOffset);
		putFile.setLength(iLength);
		return putFile;
	}	
		
	public static RegisterAppInterface buildRegisterAppInterface(String appName, String appId) {
		return buildRegisterAppInterface(appName, false, appId);
	}
		
	public static RegisterAppInterface buildRegisterAppInterface(
			String appName, Boolean isMediaApp, String appId) {
		
		return buildRegisterAppInterface(null, appName, null, null, null, isMediaApp, 
				null, null, null, appId, null); 
	}	
		
	public static RegisterAppInterface buildRegisterAppInterface(
			SdlMsgVersion sdlMsgVersion, String appName, Vector<TtsChunk> ttsName, 
			String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHmiType> appType,
			String appId, Integer correlationId) {
		RegisterAppInterface msg = new RegisterAppInterface();
		
		if (correlationId == null) {
			correlationId = 1;
		}
		msg.setCorrelationId(correlationId);
		
		if (sdlMsgVersion == null) {
			sdlMsgVersion = new SdlMsgVersion();
			sdlMsgVersion.setMajorVersion(Integer.valueOf(SDL_MSG_MAJOR_VERSION));
			sdlMsgVersion.setMinorVersion(Integer.valueOf(SDL_MSG_MINOR_VERSION));
		} 
		msg.setSdlMsgVersion(sdlMsgVersion);
		
		msg.setAppName(appName);
		
		msg.setTtsName(ttsName);
		
		if (ngnMediaScreenAppName == null) {
			ngnMediaScreenAppName = appName;
		}
		
		msg.setNgnMediaScreenAppName(ngnMediaScreenAppName);
		
		if (vrSynonyms == null) {
			vrSynonyms = new Vector<String>();
			vrSynonyms.add(appName);
		}
		msg.setVrSynonyms(vrSynonyms);
		
		msg.setIsMediaApplication(isMediaApp);
		
		if (languageDesired == null) {
			languageDesired = Language.EN_US;
		}
		msg.setLanguageDesired(languageDesired);
		
		if (hmiDisplayLanguageDesired == null) {
			hmiDisplayLanguageDesired = Language.EN_US;
		}		
		
		msg.setHmiDisplayLanguageDesired(hmiDisplayLanguageDesired);
		
		msg.setAppHmiType(appType);
		
		msg.setAppId(appId);

		return msg;
	}
	
	public static SetAppIcon buildSetAppIcon(String sdlFileName,
			Integer correlationId) {
		SetAppIcon setAppIcon = new SetAppIcon();
		setAppIcon.setCorrelationId(correlationId);
		setAppIcon.setSdlFileName(sdlFileName);
		return setAppIcon;
	}
	
	public static SetGlobalProperties buildSetGlobalProperties(
			String helpPrompt, String timeoutPrompt, Integer correlationId) {
		return buildSetGlobalProperties(TtsChunkFactory
				.createSimpleTtsChunks(helpPrompt), TtsChunkFactory
				.createSimpleTtsChunks(timeoutPrompt), correlationId);
	}
	
	public static SetGlobalProperties buildSetGlobalProperties(
			Vector<TtsChunk> helpChunks, Vector<TtsChunk> timeoutChunks,
			Integer correlationId) {
		SetGlobalProperties req = new SetGlobalProperties();
		req.setCorrelationId(correlationId);

		req.setHelpPrompt(helpChunks);
		req.setTimeoutPrompt(timeoutChunks);

		return req;
	}

	
	public static SetGlobalProperties buildSetGlobalProperties(
			String helpPrompt, String timeoutPrompt, String vrHelpTitle, 
			Vector<VrHelpItem> vrHelp, Integer correlationId) {
		return buildSetGlobalProperties(TtsChunkFactory
				.createSimpleTtsChunks(helpPrompt), TtsChunkFactory
				.createSimpleTtsChunks(timeoutPrompt), vrHelpTitle, vrHelp, correlationId);
	}


	public static SetGlobalProperties buildSetGlobalProperties(
			Vector<TtsChunk> helpChunks, Vector<TtsChunk> timeoutChunks, 
			String vrHelpTitle, Vector<VrHelpItem> vrHelp,
			Integer correlationId) {
		SetGlobalProperties req = new SetGlobalProperties();
		req.setCorrelationId(correlationId);

		req.setHelpPrompt(helpChunks);
		req.setTimeoutPrompt(timeoutChunks);
		
		req.setVrHelpTitle(vrHelpTitle);		
		req.setVrHelp(vrHelp);

		return req;
	}
	
	public static SetMediaClockTimer buildSetMediaClockTimer(Integer hours,
			Integer minutes, Integer seconds, UpdateMode updateMode,
			Integer correlationId) {

		SetMediaClockTimer msg = new SetMediaClockTimer();
		if (hours != null || minutes != null || seconds != null) {
			StartTime startTime = new StartTime();
			msg.setStartTime(startTime);
			startTime.setHours(hours);
			startTime.setMinutes(minutes);
			startTime.setSeconds(seconds);
		}

		msg.setUpdateMode(updateMode);
		msg.setCorrelationId(correlationId);

		return msg;
	}
	
	@Deprecated
	public static SetMediaClockTimer buildSetMediaClockTimer(
			UpdateMode updateMode, Integer correlationId) {
		Integer hours = null;
		Integer minutes = null;
		Integer seconds = null;

		SetMediaClockTimer msg = buildSetMediaClockTimer(hours, minutes,
				seconds, updateMode, correlationId);
		return msg;
	}
	
	@SuppressWarnings("deprecation")
    public static Show buildShow(String mainText1, String mainText2,
			String mainText3, String mainText4,
			String statusBar, String mediaClock, String mediaTrack,
			Image graphic, Vector<SoftButton> softButtons, Vector <String> customPresets,
			TextAlignment alignment, Integer correlationId) {
		Show msg = new Show();
		msg.setCorrelationId(correlationId);
		msg.setMainField1(mainText1);
		msg.setMainField2(mainText2);
		msg.setStatusBar(statusBar);
		msg.setMediaClock(mediaClock);
		msg.setMediaTrack(mediaTrack);
		msg.setAlignment(alignment);
		msg.setMainField3(mainText3);
		msg.setMainField4(mainText4);
		msg.setGraphic(graphic);
		msg.setSoftButtons(softButtons);
		msg.setCustomPresets(customPresets);		

		return msg;
	}
	
	public static Show buildShow(String mainText1, String mainText2, String mainText3, String mainText4,
			TextAlignment alignment, Integer correlationId) {
		Show msg = buildShow(mainText1, mainText2, mainText3, mainText4, null, null, null, null, null, null, alignment,
				correlationId);
		return msg;
	}
		
	@SuppressWarnings("deprecation")
    public static Show buildShow(String mainText1, String mainText2,
			String statusBar, String mediaClock, String mediaTrack,
			TextAlignment alignment, Integer correlationId) {
		Show msg = new Show();
		msg.setCorrelationId(correlationId);
		msg.setMainField1(mainText1);
		msg.setMainField2(mainText2);
		msg.setStatusBar(statusBar);
		msg.setMediaClock(mediaClock);
		msg.setMediaTrack(mediaTrack);
		msg.setAlignment(alignment);

		return msg;
	}
	
	public static Show buildShow(String mainText1, String mainText2,
			TextAlignment alignment, Integer correlationId) {
		Show msg = buildShow(mainText1, mainText2, null, null, null, alignment,
				correlationId);
		return msg;
	}
	
	public static Speak buildSpeak(String ttsText, Integer correlationId) {
		Speak msg = buildSpeak(TtsChunkFactory.createSimpleTtsChunks(ttsText),
				correlationId);
		return msg;
	}
	
	public static Speak buildSpeak(Vector<TtsChunk> ttsChunks,
			Integer correlationId) {

		Speak msg = new Speak();
		msg.setCorrelationId(correlationId);

		msg.setTtsChunks(ttsChunks);

		return msg;
	}
	
	public static SubscribeButton buildSubscribeButton(ButtonName buttonName,
			Integer correlationId) {

		SubscribeButton msg = new SubscribeButton();
		msg.setCorrelationId(correlationId);
		msg.setButtonName(buttonName);

		return msg;
	}
	
	public static UnregisterAppInterface buildUnregisterAppInterface(
			Integer correlationId) {
		UnregisterAppInterface msg = new UnregisterAppInterface();
		msg.setCorrelationId(correlationId);

		return msg;
	}
	
	public static UnsubscribeButton buildUnsubscribeButton(
			ButtonName buttonName, Integer correlationId) {

		UnsubscribeButton msg = new UnsubscribeButton();
		msg.setCorrelationId(correlationId);
		msg.setButtonName(buttonName);

		return msg;
	}	
	
	@SuppressWarnings("deprecation")
    public static SubscribeVehicleData buildSubscribeVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State,
																 boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure,
																 boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
																 boolean driverBraking, Integer correlationId) 
	{
		SubscribeVehicleData msg = new SubscribeVehicleData();
		msg.setGps(gps);
		msg.setSpeed(speed);
		msg.setRpm(rpm);
		msg.setFuelLevel(fuelLevel);
		msg.setFuelLevel_State(fuelLevel_State);
		msg.setInstantFuelConsumption(instantFuelConsumption);
		msg.setExternalTemperature(externalTemperature);
		msg.setPrndl(prndl);
		msg.setTirePressure(tirePressure);
		msg.setOdometer(odometer);
		msg.setBeltStatus(beltStatus);
		msg.setBodyInformation(bodyInformation);
		msg.setDeviceStatus(deviceStatus);
		msg.setDriverBraking(driverBraking);
		msg.setCorrelationId(correlationId);
		
		return msg;
	}

	@SuppressWarnings("deprecation")
    public static UnsubscribeVehicleData buildUnsubscribeVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State,
																	 boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure,
																	 boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
																	 boolean driverBraking, Integer correlationId) 
	{
		UnsubscribeVehicleData msg = new UnsubscribeVehicleData();
		msg.setGps(gps);
		msg.setSpeed(speed);
		msg.setRpm(rpm);
		msg.setFuelLevel(fuelLevel);
		msg.setFuelLevel_State(fuelLevel_State);
		msg.setInstantFuelConsumption(instantFuelConsumption);
		msg.setExternalTemperature(externalTemperature);
		msg.setPrndl(prndl);
		msg.setTirePressure(tirePressure);
		msg.setOdometer(odometer);
		msg.setBeltStatus(beltStatus);
		msg.setBodyInformation(bodyInformation);
		msg.setDeviceStatus(deviceStatus);
		msg.setDriverBraking(driverBraking);
		msg.setCorrelationId(correlationId);
		
		return msg;		
	}
	
	@SuppressWarnings("deprecation")
    public static GetVehicleData buildGetVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State,
			 boolean instantFuelConsumption, boolean externalTemperature, boolean vin, boolean prndl, boolean tirePressure,
			 boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
			 boolean driverBraking, Integer correlationId)
	{
		GetVehicleData msg = new GetVehicleData();
		msg.setGps(gps);
		msg.setSpeed(speed);
		msg.setRpm(rpm);
		msg.setFuelLevel(fuelLevel);
		msg.setFuelLevel_State(fuelLevel_State);
		msg.setInstantFuelConsumption(instantFuelConsumption);
		msg.setExternalTemperature(externalTemperature);
		msg.setVin(vin);
		msg.setPrndl(prndl);
		msg.setTirePressure(tirePressure);
		msg.setOdometer(odometer);
		msg.setBeltStatus(beltStatus);
		msg.setBodyInformation(bodyInformation);
		msg.setDeviceStatus(deviceStatus);
		msg.setDriverBraking(driverBraking);
		msg.setCorrelationId(correlationId);
		
		
		return msg;
	}	
	
	public static ScrollableMessage buildScrollableMessage(String scrollableMessageBody, Integer timeout, Vector<SoftButton> softButtons, Integer correlationId)	
	{
		ScrollableMessage msg = new ScrollableMessage();
		msg.setCorrelationId(correlationId);
		msg.setScrollableMessageBody(scrollableMessageBody);
		msg.setTimeout(timeout);
		msg.setSoftButtons(softButtons);
		
		return msg;		
	}
	
	public static Slider buildSlider(Integer numTicks, Integer position, String sliderHeader, Vector<String> sliderFooter, Integer timeout, Integer correlationId)
	{
		Slider msg = new Slider();
		msg.setCorrelationId(correlationId);
		msg.setNumTicks(numTicks);
		msg.setPosition(position);
		msg.setSliderHeader(sliderHeader);
		msg.setSliderFooter(sliderFooter);
		msg.setTimeout(timeout);
		
		return msg;
	}
	
	public static ChangeRegistration buildChangeRegistration(Language language, Language hmiDisplayLanguage, Integer correlationId)
	{
		ChangeRegistration msg = new ChangeRegistration();
		msg.setCorrelationId(correlationId);
		msg.setLanguage(language);
		msg.setHmiDisplayLanguage(hmiDisplayLanguage);
		
		return msg;
	}
	
	public static SetDisplayLayout buildSetDisplayLayout(String displayLayout, Integer correlationId)
	{
		SetDisplayLayout msg = new SetDisplayLayout();
		msg.setCorrelationId(correlationId);
		msg.setDisplayLayout(displayLayout);
		
		return msg;
	}
	
	public static PerformAudioPassThru buildPerformAudioPassThru(String ttsText, String audioPassThruDisplayText1, String audioPassThruDisplayText2,
														  SamplingRate samplingRate, Integer maxDuration, BitsPerSample bitsPerSample,
														  AudioType audioType, Boolean muteAudio, Integer correlationId)
	{
		Vector<TtsChunk> chunks = TtsChunkFactory
				.createSimpleTtsChunks(ttsText);
		
		PerformAudioPassThru msg = buildPerformAudioPassThru(chunks, audioPassThruDisplayText1, audioPassThruDisplayText2,
															 samplingRate, maxDuration, bitsPerSample,audioType,  muteAudio, correlationId);
		
		return msg;
	}
	
	public static PerformAudioPassThru buildPerformAudioPassThru(Vector<TtsChunk> initialPrompt, String audioPassThruDisplayText1, String audioPassThruDisplayText2,
			  SamplingRate samplingRate, Integer maxDuration, BitsPerSample bitsPerSample,
			  AudioType audioType, Boolean muteAudio, Integer correlationId)
	{
		PerformAudioPassThru msg = new PerformAudioPassThru();
		msg.setCorrelationId(correlationId);
		msg.setInitialPrompt(initialPrompt);
		msg.setAudioPassThruDisplayText1(audioPassThruDisplayText1);
		msg.setAudioPassThruDisplayText2(audioPassThruDisplayText2);
		msg.setSamplingRate(samplingRate);
		msg.setMaxDuration(maxDuration);
		msg.setBitsPerSample(bitsPerSample);
		msg.setAudioType(audioType);
		msg.setMuteAudio(muteAudio);
				
		return msg;
	}
	
	public static EndAudioPassThru buildEndAudioPassThru(Integer correlationId)
	{
		EndAudioPassThru msg = new EndAudioPassThru();
		msg.setCorrelationId(correlationId);
		
		return msg;
	}
	
}
