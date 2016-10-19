package com.smartdevicelink.proxy;

import java.util.Vector;

import android.os.Build;

import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.ChangeRegistration;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.DeleteFile;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;
import com.smartdevicelink.proxy.rpc.DeviceInfo;
import com.smartdevicelink.proxy.rpc.EndAudioPassThru;
import com.smartdevicelink.proxy.rpc.GetVehicleData;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThru;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.RegisterAppInterface;
import com.smartdevicelink.proxy.rpc.ScrollableMessage;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SetAppIcon;
import com.smartdevicelink.proxy.rpc.SetDisplayLayout;
import com.smartdevicelink.proxy.rpc.SetGlobalProperties;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimer;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.Slider;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.Speak;
import com.smartdevicelink.proxy.rpc.StartTime;
import com.smartdevicelink.proxy.rpc.SubscribeButton;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleData;
import com.smartdevicelink.proxy.rpc.SystemRequest;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.UnregisterAppInterface;
import com.smartdevicelink.proxy.rpc.UnsubscribeButton;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleData;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.enums.UpdateMode;

public class RPCRequestFactory {

	public static final int SDL_MSG_MAJOR_VERSION = 1;
	public static final int SDL_MSG_MINOR_VERSION = 0;
	
	public static SystemRequest buildSystemRequest(
			String data, Integer correlationID) {
		
		if(data == null) return null;
		
		SystemRequest msg = new SystemRequest();
		msg.setRequestType(RequestType.PROPRIETARY);
		msg.setCorrelationID(correlationID);
		msg.setBulkData(data.getBytes());
		return msg;
	}	
	
	public static SystemRequest buildSystemRequestLegacy(
			Vector<String> data, Integer correlationID) {
		
		if(data == null) return null;
		
		SystemRequest msg = new SystemRequest(true);
		msg.setCorrelationID(correlationID);
		msg.setLegacyData(data);
		return msg;
	}	

	public static AddCommand buildAddCommand(Integer commandID,
			String menuText, Integer parentID, Integer position,
			Vector<String> vrCommands, Image cmdIcon, Integer correlationID) {
		AddCommand msg = new AddCommand();
		msg.setCorrelationID(correlationID);
		msg.setCmdID(commandID);
		msg.setVrCommands(vrCommands);
		
		if (cmdIcon != null) msg.setCmdIcon(cmdIcon);
		
		if(menuText != null || parentID != null || position != null) {
			MenuParams menuParams = new MenuParams();
			menuParams.setMenuName(menuText);
			menuParams.setPosition(position);
			menuParams.setParentID(parentID);
			msg.setMenuParams(menuParams);
		}
		
		return msg;
	}
	
	public static AddCommand buildAddCommand(Integer commandID,
			String menuText, Integer parentID, Integer position,
			Vector<String> vrCommands, String IconValue, ImageType IconType, 
			Integer correlationID) {
		AddCommand msg = new AddCommand();
		msg.setCorrelationID(correlationID);
		msg.setCmdID(commandID);
		
		if (vrCommands != null) msg.setVrCommands(vrCommands);
		
		Image cmdIcon = null;
		
		if (IconValue != null && IconType != null)
		{
			cmdIcon = new Image();
			cmdIcon.setValue(IconValue);			
			cmdIcon.setImageType(IconType);			
		}				
		
		if (cmdIcon != null) msg.setCmdIcon(cmdIcon);
		
		if(menuText != null || parentID != null || position != null) {
			MenuParams menuParams = new MenuParams();
			menuParams.setMenuName(menuText);
			menuParams.setPosition(position);
			menuParams.setParentID(parentID);
			msg.setMenuParams(menuParams);
		}
		
		return msg;
	}
		
	public static AddCommand buildAddCommand(Integer commandID,
			String menuText, Integer parentID, Integer position,
			Vector<String> vrCommands, Integer correlationID) {
		AddCommand msg = new AddCommand();
		msg.setCorrelationID(correlationID);
		msg.setCmdID(commandID);
		msg.setVrCommands(vrCommands);
		
		if(menuText != null || parentID != null || position != null) {
			MenuParams menuParams = new MenuParams();
			menuParams.setMenuName(menuText);
			menuParams.setPosition(position);
			menuParams.setParentID(parentID);
			msg.setMenuParams(menuParams);
		}
		
		return msg;
	}
	
	public static AddCommand buildAddCommand(Integer commandID,
			String menuText, Vector<String> vrCommands, Integer correlationID) {
		AddCommand msg = buildAddCommand(commandID, menuText, null, null,
				vrCommands, correlationID);
		return msg;
	}
	
	public static AddCommand buildAddCommand(Integer commandID,
			Vector<String> vrCommands, Integer correlationID) {
		AddCommand msg = new AddCommand();
		msg.setCorrelationID(correlationID);
		msg.setCmdID(commandID);
		msg.setVrCommands(vrCommands);

		return msg;
	}

	public static AddSubMenu buildAddSubMenu(Integer menuID, String menuName,
			Integer correlationID) {
		AddSubMenu msg = buildAddSubMenu(menuID, menuName, null, correlationID);
		return msg;
	}

	public static AddSubMenu buildAddSubMenu(Integer menuID, String menuName,
			Integer position, Integer correlationID) {
		AddSubMenu msg = new AddSubMenu();
		msg.setCorrelationID(correlationID);
		msg.setMenuName(menuName);
		msg.setMenuID(menuID);
		msg.setPosition(position);

		return msg;
	}	

	public static Alert buildAlert(String ttsText, Boolean playTone, 
			Vector<SoftButton> softButtons, Integer correlationID) {
		Vector<TTSChunk> chunks = TTSChunkFactory
				.createSimpleTTSChunks(ttsText);
		Alert msg = buildAlert(chunks, null, null, null, playTone, null, softButtons,
				correlationID);
		return msg;
	}
	
	public static Alert buildAlert(String alertText1, String alertText2, 
			String alertText3, Integer duration, Vector<SoftButton> softButtons,
			Integer correlationID) {
		Alert msg = buildAlert((Vector<TTSChunk>) null, alertText1, alertText2, 
				alertText3, null, duration, softButtons, correlationID);
		return msg;
	}
	
	public static Alert buildAlert(String ttsText, String alertText1,
			String alertText2, String alertText3, Boolean playTone, 
			Integer duration, Vector<SoftButton> softButtons, Integer correlationID) {
		Vector<TTSChunk> chunks = TTSChunkFactory
				.createSimpleTTSChunks(ttsText);
		Alert msg = buildAlert(chunks, alertText1, alertText2, alertText3, 
				playTone, duration, softButtons, correlationID);
		return msg;
	}
	
	public static Alert buildAlert(Vector<TTSChunk> chunks, Boolean playTone, 
			Vector<SoftButton> softButtons, Integer correlationID) {
		Alert msg = buildAlert(chunks, null, null, null, playTone, null, softButtons, correlationID);
		return msg;
	}
	
	public static Alert buildAlert(Vector<TTSChunk> ttsChunks,
			String alertText1, String alertText2, String alertText3, 
			Boolean playTone, Integer duration, Vector<SoftButton> softButtons,
			Integer correlationID) {
		Alert msg = new Alert();
		msg.setCorrelationID(correlationID);
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
			Integer correlationID) {
		Vector<TTSChunk> chunks = TTSChunkFactory
				.createSimpleTTSChunks(ttsText);
		Alert msg = buildAlert(chunks, null, null, playTone, null,
				correlationID);
		return msg;
	}
	
	public static Alert buildAlert(String alertText1, String alertText2,
			Integer duration, Integer correlationID) {
		Alert msg = buildAlert((Vector<TTSChunk>) null, alertText1, alertText2,
				null, duration, correlationID);
		return msg;
	}
	
	public static Alert buildAlert(String ttsText, String alertText1,
			String alertText2, Boolean playTone, Integer duration,
			Integer correlationID) {
		Vector<TTSChunk> chunks = TTSChunkFactory
				.createSimpleTTSChunks(ttsText);
		Alert msg = buildAlert(chunks, alertText1, alertText2, playTone,
				duration, correlationID);
		return msg;
	}
	
	public static Alert buildAlert(Vector<TTSChunk> chunks, Boolean playTone,
			Integer correlationID) {
		Alert msg = buildAlert(chunks, null, null, playTone, null,
				correlationID);
		return msg;
	}
	
	public static Alert buildAlert(Vector<TTSChunk> ttsChunks,
			String alertText1, String alertText2, Boolean playTone,
			Integer duration, Integer correlationID) {
		Alert msg = new Alert();
		msg.setCorrelationID(correlationID);
		msg.setAlertText1(alertText1);
		msg.setAlertText2(alertText2);
		msg.setDuration(duration);
		msg.setPlayTone(playTone);
		msg.setTtsChunks(ttsChunks);

		return msg;
	}
	
	public static CreateInteractionChoiceSet buildCreateInteractionChoiceSet(
			Vector<Choice> choiceSet, Integer interactionChoiceSetID,
			Integer correlationID) {
		CreateInteractionChoiceSet msg = new CreateInteractionChoiceSet();
		msg.setChoiceSet(choiceSet);
		msg.setInteractionChoiceSetID(interactionChoiceSetID);
		msg.setCorrelationID(correlationID);
		return msg;
	}
	
	public static DeleteCommand buildDeleteCommand(Integer commandID,
			Integer correlationID) {
		DeleteCommand msg = new DeleteCommand();
		msg.setCmdID(commandID);
		msg.setCorrelationID(correlationID);
		return msg;
	}
	
	public static DeleteFile buildDeleteFile(String sdlFileName,
			Integer correlationID) {
		DeleteFile deleteFile = new DeleteFile();
		deleteFile.setCorrelationID(correlationID);
		deleteFile.setSdlFileName(sdlFileName);
		return deleteFile;
	}
	
	public static DeleteInteractionChoiceSet buildDeleteInteractionChoiceSet(
			Integer interactionChoiceSetID, Integer correlationID) {
		DeleteInteractionChoiceSet msg = new DeleteInteractionChoiceSet();
		msg.setInteractionChoiceSetID(interactionChoiceSetID);
		msg.setCorrelationID(correlationID);

		return msg;
	}
	
	public static DeleteSubMenu buildDeleteSubMenu(Integer menuID,
			Integer correlationID) {
		DeleteSubMenu msg = new DeleteSubMenu();
		msg.setCorrelationID(correlationID);
		msg.setMenuID(menuID);

		return msg;
	}
	
	public static ListFiles buildListFiles(Integer correlationID) {
		ListFiles listFiles = new ListFiles();
		listFiles.setCorrelationID(correlationID);
		return listFiles;
	}
	
	public static PerformInteraction buildPerformInteraction(
			Vector<TTSChunk> initChunks, String displayText,
			Vector<Integer> interactionChoiceSetIDList,
			Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks,
			InteractionMode interactionMode, Integer timeout, 
			Vector<VrHelpItem> vrHelp, Integer correlationID) {
		PerformInteraction msg = new PerformInteraction();
		msg.setInitialPrompt(initChunks);
		msg.setInitialText(displayText);
		msg.setInteractionChoiceSetIDList(interactionChoiceSetIDList);
		msg.setInteractionMode(interactionMode);
		msg.setTimeout(timeout);
		msg.setHelpPrompt(helpChunks);
		msg.setTimeoutPrompt(timeoutChunks);
		msg.setVrHelp(vrHelp);
		msg.setCorrelationID(correlationID);
		
		return msg;
	}

	public static PerformInteraction buildPerformInteraction(
			String initPrompt, 	String displayText, 
			Vector<Integer> interactionChoiceSetIDList,
			String helpPrompt, String timeoutPrompt,
			InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp,
			Integer correlationID) {
		Vector<TTSChunk> initChunks = TTSChunkFactory
				.createSimpleTTSChunks(initPrompt);
		Vector<TTSChunk> helpChunks = TTSChunkFactory
				.createSimpleTTSChunks(helpPrompt);
		Vector<TTSChunk> timeoutChunks = TTSChunkFactory
				.createSimpleTTSChunks(timeoutPrompt);
		return buildPerformInteraction(initChunks,
				displayText, interactionChoiceSetIDList, helpChunks,
				timeoutChunks, interactionMode, timeout, vrHelp, correlationID);
	}
	
	public static PerformInteraction buildPerformInteraction(
			String initPrompt, 	String displayText, 
			Integer interactionChoiceSetID,
			String helpPrompt, String timeoutPrompt,
			InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp,
			Integer correlationID) {
		Vector<Integer> interactionChoiceSetIDs = new Vector<Integer>();
			interactionChoiceSetIDs.add(interactionChoiceSetID);
		
		return buildPerformInteraction(
				initPrompt, displayText, interactionChoiceSetIDs, 
				helpPrompt, timeoutPrompt, interactionMode, 
				timeout, vrHelp, correlationID);
	}
	
	public static PerformInteraction buildPerformInteraction(String initPrompt,
			String displayText, Integer interactionChoiceSetID, Vector<VrHelpItem> vrHelp,
			Integer correlationID) {

		return buildPerformInteraction(initPrompt, displayText, 
				interactionChoiceSetID, null, null,
				InteractionMode.BOTH, null, vrHelp, correlationID);
	}	
	
	public static PerformInteraction buildPerformInteraction(
			Vector<TTSChunk> initChunks, String displayText,
			Vector<Integer> interactionChoiceSetIDList,
			Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks,
			InteractionMode interactionMode, Integer timeout,
			Integer correlationID) {
		PerformInteraction msg = new PerformInteraction();
		msg.setInitialPrompt(initChunks);
		msg.setInitialText(displayText);
		msg.setInteractionChoiceSetIDList(interactionChoiceSetIDList);
		msg.setInteractionMode(interactionMode);
		msg.setTimeout(timeout);
		msg.setHelpPrompt(helpChunks);
		msg.setTimeoutPrompt(timeoutChunks);
		msg.setCorrelationID(correlationID);
		
		return msg;
	}

	public static PerformInteraction buildPerformInteraction(
			String initPrompt, 	String displayText, 
			Vector<Integer> interactionChoiceSetIDList,
			String helpPrompt, String timeoutPrompt,
			InteractionMode interactionMode, Integer timeout,
			Integer correlationID) {
		Vector<TTSChunk> initChunks = TTSChunkFactory
				.createSimpleTTSChunks(initPrompt);
		Vector<TTSChunk> helpChunks = TTSChunkFactory
				.createSimpleTTSChunks(helpPrompt);
		Vector<TTSChunk> timeoutChunks = TTSChunkFactory
				.createSimpleTTSChunks(timeoutPrompt);
		return buildPerformInteraction(initChunks,
				displayText, interactionChoiceSetIDList, helpChunks,
				timeoutChunks, interactionMode, timeout, correlationID);
	}
	
	public static PerformInteraction buildPerformInteraction(
			String initPrompt, 	String displayText, 
			Integer interactionChoiceSetID,
			String helpPrompt, String timeoutPrompt,
			InteractionMode interactionMode, Integer timeout,
			Integer correlationID) {
		Vector<Integer> interactionChoiceSetIDs = new Vector<Integer>();
			interactionChoiceSetIDs.add(interactionChoiceSetID);
		
		return buildPerformInteraction(
				initPrompt, displayText, interactionChoiceSetIDs, 
				helpPrompt, timeoutPrompt, interactionMode, 
				timeout, correlationID);
	}
	
	public static PerformInteraction buildPerformInteraction(String initPrompt,
			String displayText, Integer interactionChoiceSetID,
			Integer correlationID) {

		return buildPerformInteraction(initPrompt, displayText, 
				interactionChoiceSetID, null, null,
				InteractionMode.BOTH, null, correlationID);
	}
	
	@Deprecated
	public static PerformInteraction buildPerformInteraction(
			Vector<TTSChunk> initChunks, String displayText,
			Vector<Integer> interactionChoiceSetIDList,
			Vector<TTSChunk> helpChunks, InteractionMode interactionMode,
			Integer timeout, Integer correlationID) {
		PerformInteraction msg = new PerformInteraction();
		msg.setInitialPrompt(initChunks);
		msg.setInitialText(displayText);
		msg.setInteractionChoiceSetIDList(interactionChoiceSetIDList);
		msg.setInteractionMode(interactionMode);
		msg.setTimeout(timeout);
		msg.setHelpPrompt(helpChunks);
		msg.setCorrelationID(correlationID);
		return msg;
	}
	
	@Deprecated
	public static PerformInteraction buildPerformInteraction(String initPrompt,
			String displayText, Vector<Integer> interactionChoiceSetIDList,
			String helpPrompt, InteractionMode interactionMode,
			Integer timeout, Integer correlationID) {
		Vector<TTSChunk> initChunks = TTSChunkFactory
				.createSimpleTTSChunks(initPrompt);
		Vector<TTSChunk> helpChunks = TTSChunkFactory
				.createSimpleTTSChunks(helpPrompt);
		PerformInteraction msg = buildPerformInteraction(initChunks,
				displayText, interactionChoiceSetIDList, helpChunks,
				interactionMode, timeout, correlationID);
		return msg;
	}
	
	public static PutFile buildPutFile(String sdlFileName, FileType fileType,
			Boolean persistentFile, byte[] fileData, Integer correlationID) {
		PutFile putFile = new PutFile();
		putFile.setCorrelationID(correlationID);
		putFile.setSdlFileName(sdlFileName);
		putFile.setFileType(fileType);
		putFile.setPersistentFile(persistentFile);
		putFile.setBulkData(fileData);
		return putFile;
	}
	
	@Deprecated
	public static PutFile buildPutFile(String sdlFileName, Integer iOffset, Integer iLength) {
		PutFile putFile = new PutFile();
		putFile.setCorrelationID(10000);
		putFile.setSdlFileName(sdlFileName);		
		putFile.setFileType(FileType.BINARY);
		putFile.setSystemFile(true);
		putFile.setOffset(iOffset);
		putFile.setLength(iLength);
		return putFile;
	}
	
	public static PutFile buildPutFile(String sdlFileName, Long iOffset, Long iLength) {
		PutFile putFile = new PutFile();
		putFile.setCorrelationID(10000);
		putFile.setSdlFileName(sdlFileName);		
		putFile.setFileType(FileType.BINARY);
		putFile.setSystemFile(true);
		putFile.setOffset(iOffset);
		putFile.setLength(iLength);
		return putFile;
	}

	@Deprecated
	public static PutFile buildPutFile(String syncFileName, Integer iOffset, Integer iLength, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile) {
		PutFile putFile = new PutFile();
		putFile.setCorrelationID(10000);
		putFile.setSdlFileName(syncFileName);
		putFile.setFileType(fileType);
		putFile.setPersistentFile(bPersistentFile);
		putFile.setSystemFile(bSystemFile);
		putFile.setOffset(iOffset);
		putFile.setLength(iLength);
		return putFile;
	}
	
	public static PutFile buildPutFile(String syncFileName, Long iOffset, Long iLength, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile) {
		PutFile putFile = new PutFile();
		putFile.setCorrelationID(10000);
		putFile.setSdlFileName(syncFileName);
		putFile.setFileType(fileType);
		putFile.setPersistentFile(bPersistentFile);
		putFile.setSystemFile(bSystemFile);
		putFile.setOffset(iOffset);
		putFile.setLength(iLength);
		return putFile;
	}
	
	@Deprecated
	public static PutFile buildPutFile(String sdlFileName, Integer iOffset, Integer iLength, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile, Integer iCorrelationID) {
		PutFile putFile = new PutFile();
		putFile.setCorrelationID(iCorrelationID);
		putFile.setSdlFileName(sdlFileName);
		putFile.setFileType(fileType);
		putFile.setPersistentFile(bPersistentFile);
		putFile.setSystemFile(bSystemFile);
		putFile.setOffset(iOffset);
		putFile.setLength(iLength);
		return putFile;
	}	
	
	public static PutFile buildPutFile(String sdlFileName, Long iOffset, Long iLength, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile, Boolean isPayloadProtected, Integer iCorrelationID) {
		PutFile putFile = new PutFile();
		putFile.setCorrelationID(iCorrelationID);
		putFile.setSdlFileName(sdlFileName);
		putFile.setFileType(fileType);
		putFile.setPersistentFile(bPersistentFile);
		putFile.setSystemFile(bSystemFile);
		putFile.setOffset(iOffset);
		putFile.setLength(iLength);
		putFile.setPayloadProtected(isPayloadProtected);
		return putFile;
	}
		
	public static RegisterAppInterface buildRegisterAppInterface(String appName, String appID) {
		return buildRegisterAppInterface(appName, false, appID);
	}
		
	public static RegisterAppInterface buildRegisterAppInterface(
			String appName, Boolean isMediaApp, String appID) {
		
		return buildRegisterAppInterface(null, appName, null, null, null, isMediaApp, 
				null, null, null, appID, null, null);
	}	
		
	public static RegisterAppInterface buildRegisterAppInterface(
			SdlMsgVersion sdlMsgVersion, String appName, Vector<TTSChunk> ttsName, 
			String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType,
			String appID, Integer correlationID, DeviceInfo deviceInfo) {
		RegisterAppInterface msg = new RegisterAppInterface();
		
		if (correlationID == null) {
			correlationID = 1;
		}
		msg.setCorrelationID(correlationID);
		
		if (sdlMsgVersion == null) {
			sdlMsgVersion = new SdlMsgVersion();
			sdlMsgVersion.setMajorVersion(Integer.valueOf(SDL_MSG_MAJOR_VERSION));
			sdlMsgVersion.setMinorVersion(Integer.valueOf(SDL_MSG_MINOR_VERSION));
		} 
		msg.setSdlMsgVersion(sdlMsgVersion);
		msg.setDeviceInfo(deviceInfo);
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
		
		msg.setAppHMIType(appType);
		
		msg.setAppID(appID);

		return msg;
	}
	
	public static SetAppIcon buildSetAppIcon(String sdlFileName,
			Integer correlationID) {
		SetAppIcon setAppIcon = new SetAppIcon();
		setAppIcon.setCorrelationID(correlationID);
		setAppIcon.setSdlFileName(sdlFileName);
		return setAppIcon;
	}
	
	public static SetGlobalProperties buildSetGlobalProperties(
			String helpPrompt, String timeoutPrompt, Integer correlationID) {
		return buildSetGlobalProperties(TTSChunkFactory
				.createSimpleTTSChunks(helpPrompt), TTSChunkFactory
				.createSimpleTTSChunks(timeoutPrompt), correlationID);
	}
	
	public static SetGlobalProperties buildSetGlobalProperties(
			Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks,
			Integer correlationID) {
		SetGlobalProperties req = new SetGlobalProperties();
		req.setCorrelationID(correlationID);

		req.setHelpPrompt(helpChunks);
		req.setTimeoutPrompt(timeoutChunks);

		return req;
	}
	
	public static SetGlobalProperties buildSetGlobalProperties(
			String helpPrompt, String timeoutPrompt, String vrHelpTitle, 
			Vector<VrHelpItem> vrHelp, Integer correlationID) {
		return buildSetGlobalProperties(TTSChunkFactory
				.createSimpleTTSChunks(helpPrompt), TTSChunkFactory
				.createSimpleTTSChunks(timeoutPrompt), vrHelpTitle, vrHelp, correlationID);
	}

	public static SetGlobalProperties buildSetGlobalProperties(
			Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, 
			String vrHelpTitle, Vector<VrHelpItem> vrHelp,
			Integer correlationID) {
		SetGlobalProperties req = new SetGlobalProperties();
		req.setCorrelationID(correlationID);

		req.setHelpPrompt(helpChunks);
		req.setTimeoutPrompt(timeoutChunks);
		
		req.setVrHelpTitle(vrHelpTitle);		
		req.setVrHelp(vrHelp);

		return req;
	}
	
	public static SetMediaClockTimer buildSetMediaClockTimer(Integer hours,
			Integer minutes, Integer seconds, UpdateMode updateMode,
			Integer correlationID) {

		SetMediaClockTimer msg = new SetMediaClockTimer();
		if (hours != null || minutes != null || seconds != null) {
			StartTime startTime = new StartTime();
			msg.setStartTime(startTime);
			startTime.setHours(hours);
			startTime.setMinutes(minutes);
			startTime.setSeconds(seconds);
		}

		msg.setUpdateMode(updateMode);
		msg.setCorrelationID(correlationID);

		return msg;
	}
	
	@Deprecated
	public static SetMediaClockTimer buildSetMediaClockTimer(
			UpdateMode updateMode, Integer correlationID) {
		Integer hours = null;
		Integer minutes = null;
		Integer seconds = null;

		SetMediaClockTimer msg = buildSetMediaClockTimer(hours, minutes,
				seconds, updateMode, correlationID);
		return msg;
	}
	
	@SuppressWarnings("deprecation")
    public static Show buildShow(String mainText1, String mainText2,
			String mainText3, String mainText4,
			String statusBar, String mediaClock, String mediaTrack,
			Image graphic, Vector<SoftButton> softButtons, Vector <String> customPresets,
			TextAlignment alignment, Integer correlationID) {
		Show msg = new Show();
		msg.setCorrelationID(correlationID);
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
			TextAlignment alignment, Integer correlationID) {
		Show msg = buildShow(mainText1, mainText2, mainText3, mainText4, null, null, null, null, null, null, alignment,
				correlationID);
		return msg;
	}
		
	@SuppressWarnings("deprecation")
    public static Show buildShow(String mainText1, String mainText2,
			String statusBar, String mediaClock, String mediaTrack,
			TextAlignment alignment, Integer correlationID) {
		Show msg = new Show();
		msg.setCorrelationID(correlationID);
		msg.setMainField1(mainText1);
		msg.setMainField2(mainText2);
		msg.setStatusBar(statusBar);
		msg.setMediaClock(mediaClock);
		msg.setMediaTrack(mediaTrack);
		msg.setAlignment(alignment);

		return msg;
	}
	
	public static Show buildShow(String mainText1, String mainText2,
			TextAlignment alignment, Integer correlationID) {
		Show msg = buildShow(mainText1, mainText2, null, null, null, alignment,
				correlationID);
		return msg;
	}
	
	public static Speak buildSpeak(String ttsText, Integer correlationID) {
		Speak msg = buildSpeak(TTSChunkFactory.createSimpleTTSChunks(ttsText),
				correlationID);
		return msg;
	}
	
	public static Speak buildSpeak(Vector<TTSChunk> ttsChunks,
			Integer correlationID) {

		Speak msg = new Speak();
		msg.setCorrelationID(correlationID);

		msg.setTtsChunks(ttsChunks);

		return msg;
	}
	
	public static SubscribeButton buildSubscribeButton(ButtonName buttonName,
			Integer correlationID) {

		SubscribeButton msg = new SubscribeButton();
		msg.setCorrelationID(correlationID);
		msg.setButtonName(buttonName);

		return msg;
	}
	
	public static UnregisterAppInterface buildUnregisterAppInterface(
			Integer correlationID) {
		UnregisterAppInterface msg = new UnregisterAppInterface();
		msg.setCorrelationID(correlationID);

		return msg;
	}
	
	public static UnsubscribeButton buildUnsubscribeButton(
			ButtonName buttonName, Integer correlationID) {

		UnsubscribeButton msg = new UnsubscribeButton();
		msg.setCorrelationID(correlationID);
		msg.setButtonName(buttonName);

		return msg;
	}	
	
	@SuppressWarnings("deprecation")
    public static SubscribeVehicleData BuildSubscribeVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State,
																 boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure,
																 boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
																 boolean driverBraking, Integer correlationID) 
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
		msg.setCorrelationID(correlationID);
		
		return msg;
	}

	@SuppressWarnings("deprecation")
    public static UnsubscribeVehicleData BuildUnsubscribeVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State,
																	 boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure,
																	 boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
																	 boolean driverBraking, Integer correlationID) 
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
		msg.setCorrelationID(correlationID);
		
		return msg;		
	}
	
	@SuppressWarnings("deprecation")
    public static GetVehicleData BuildGetVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State,
			 boolean instantFuelConsumption, boolean externalTemperature, boolean vin, boolean prndl, boolean tirePressure,
			 boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
			 boolean driverBraking, Integer correlationID)
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
		msg.setCorrelationID(correlationID);
				
		return msg;
	}	
	
	public static ScrollableMessage BuildScrollableMessage(String scrollableMessageBody, Integer timeout, Vector<SoftButton> softButtons, Integer correlationID)	
	{
		ScrollableMessage msg = new ScrollableMessage();
		msg.setCorrelationID(correlationID);
		msg.setScrollableMessageBody(scrollableMessageBody);
		msg.setTimeout(timeout);
		msg.setSoftButtons(softButtons);
		
		return msg;		
	}
	
	public static Slider BuildSlider(Integer numTicks, Integer position, String sliderHeader, Vector<String> sliderFooter, Integer timeout, Integer correlationID)
	{
		Slider msg = new Slider();
		msg.setCorrelationID(correlationID);
		msg.setNumTicks(numTicks);
		msg.setPosition(position);
		msg.setSliderHeader(sliderHeader);
		msg.setSliderFooter(sliderFooter);
		msg.setTimeout(timeout);
		
		return msg;
	}
	
	public static ChangeRegistration BuildChangeRegistration(Language language, Language hmiDisplayLanguage, Integer correlationID)
	{
		ChangeRegistration msg = new ChangeRegistration();
		msg.setCorrelationID(correlationID);
		msg.setLanguage(language);
		msg.setHmiDisplayLanguage(hmiDisplayLanguage);
		
		return msg;
	}
	
	public static SetDisplayLayout BuildSetDisplayLayout(String displayLayout, Integer correlationID)
	{
		SetDisplayLayout msg = new SetDisplayLayout();
		msg.setCorrelationID(correlationID);
		msg.setDisplayLayout(displayLayout);
		
		return msg;
	}
	
	public static PerformAudioPassThru BuildPerformAudioPassThru(String ttsText, String audioPassThruDisplayText1, String audioPassThruDisplayText2,
														  SamplingRate samplingRate, Integer maxDuration, BitsPerSample bitsPerSample,
														  AudioType audioType, Boolean muteAudio, Integer correlationID)
	{
		Vector<TTSChunk> chunks = TTSChunkFactory
				.createSimpleTTSChunks(ttsText);
		
		PerformAudioPassThru msg = BuildPerformAudioPassThru(chunks, audioPassThruDisplayText1, audioPassThruDisplayText2,
															 samplingRate, maxDuration, bitsPerSample,audioType,  muteAudio, correlationID);
		
		return msg;
	}
	
	public static PerformAudioPassThru BuildPerformAudioPassThru(Vector<TTSChunk> initialPrompt, String audioPassThruDisplayText1, String audioPassThruDisplayText2,
			  SamplingRate samplingRate, Integer maxDuration, BitsPerSample bitsPerSample,
			  AudioType audioType, Boolean muteAudio, Integer correlationID)
	{
		PerformAudioPassThru msg = new PerformAudioPassThru();
		msg.setCorrelationID(correlationID);
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
	
	public static EndAudioPassThru BuildEndAudioPassThru(Integer correlationID)
	{
		EndAudioPassThru msg = new EndAudioPassThru();
		msg.setCorrelationID(correlationID);
		
		return msg;
	}

	public static DeviceInfo BuildDeviceInfo(String carrierName)
	{
		DeviceInfo msg = new DeviceInfo();
		msg.setHardware(android.os.Build.MODEL);
		msg.setOs(DeviceInfo.DEVICE_OS);
		msg.setOsVersion(Build.VERSION.RELEASE);
		msg.setCarrier(carrierName);
		return msg;
	}
}
