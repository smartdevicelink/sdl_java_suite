package com.smartdevicelink.proxy;

import java.util.List;
import java.util.Vector;

import android.app.Service;
import android.content.Context;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.SdlExceptionCause;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.HMICapabilities;
import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.VrCapabilities;
import com.smartdevicelink.trace.SdlTrace;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;


public class SdlProxyALM extends SdlProxyBase<IProxyListenerALM> {
	
	private static final String SDL_LIB_TRACE_KEY = "42baba60-eb57-11df-98cf-0800200c9a66";
	@SuppressWarnings("unused")
    private static final String SDL_LIB_PRIVATE_TOKEN = "{DAE1A88C-6C16-4768-ACA5-6F1247EA01C2}";
	/**
	 * @deprecated
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL
	 * 
	 * Takes advantage of the advanced lifecycle management.
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName - Name of the application displayed on SDL. 
	 * @param isMediaApp - Indicates if the app is a media application.
	 */
	@Deprecated
	public SdlProxyALM(IProxyListenerALM listener, String appName, Boolean isMediaApp, 
			Language languageDesired, Language hmiDisplayLanguageDesired, String appID) throws SdlException {
		super(	listener, 
				/*sdl proxy configuration resources*/null, 
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				/*ngn media app*/null,
				/*vr synonyms*/null,
				/*is media app*/isMediaApp,
				/*sdlMsgVersion*/null,
				/*language desired*/languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				/*autoActivateID*/null,
				/*callbackToUIThread*/ false,
				new BTTransportConfig());
	}
	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL
	 * 
	 * Takes advantage of the advanced lifecycle management.
	 * @param context - Used to create a multiplexing transport config
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName - Name of the application displayed on SDL. 
	 * @param isMediaApp - Indicates if the app is a media application.
	 */
	public SdlProxyALM(Context context,IProxyListenerALM listener, String appName, Boolean isMediaApp, 
			Language languageDesired, Language hmiDisplayLanguageDesired, String appID) throws SdlException {
		super(	listener, 
				/*sdl proxy configuration resources*/null, 
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				/*ngn media app*/null,
				/*vr synonyms*/null,
				/*is media app*/isMediaApp,
				/*sdlMsgVersion*/null,
				/*language desired*/languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				/*autoActivateID*/null,
				/*callbackToUIThread*/ false,
				new MultiplexTransportConfig(context,appID));
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, appName, and isMediaApp.", SDL_LIB_TRACE_KEY);
	}
	/**
	 * @deprecated
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName - Name of the application displayed on SDL. 
	 * @param ngnMediaScreenAppName - Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters. 
	 * @param vrSynonyms - A vector of strings, all of which can be used as voice commands to 
	 * @param isMediaApp - Indicates if the app is a media application.
	 * @param sdlMsgVersion - Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle. 
	 * @param languageDesired - Indicates the language desired for the SDL interface.
	 * @param autoActivateID - ID used to re-register previously registered application.
	 * @throws SdlException
	 */
	@Deprecated
	public SdlProxyALM(IProxyListenerALM listener, String appName, String ngnMediaScreenAppName, 
			Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, 
			Language languageDesired, Language hmiDisplayLanguageDesired, String appID, 
			String autoActivateID) throws SdlException {
		super(	listener, 
				/*sdl proxy configuration resources*/null, 
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				/*callbackToUIThread*/ false,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, appName, ngnMediaScreenAppName, " +
				"vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, and autoActivateID.", SDL_LIB_TRACE_KEY);
	}
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param context - Used to create a multiplexing transport config
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName - Name of the application displayed on SDL. 
	 * @param ngnMediaScreenAppName - Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters. 
	 * @param vrSynonyms - A vector of strings, all of which can be used as voice commands to 
	 * @param isMediaApp - Indicates if the app is a media application.
	 * @param sdlMsgVersion - Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle. 
	 * @param languageDesired - Indicates the language desired for the SDL interface.
	 * @param autoActivateID - ID used to re-register previously registered application.
	 * @throws SdlException
	 */
	public SdlProxyALM(Context context,IProxyListenerALM listener, String appName, String ngnMediaScreenAppName, 
			Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, 
			Language languageDesired, Language hmiDisplayLanguageDesired, String appID, 
			String autoActivateID) throws SdlException {
		super(	listener, 
				/*sdl proxy configuration resources*/null, 
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				/*callbackToUIThread*/ false,
				new MultiplexTransportConfig(context,appID));
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, appName, ngnMediaScreenAppName, " +
				"vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, and autoActivateID.", SDL_LIB_TRACE_KEY);
	}
	/**
	 * @deprecated
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName - Name of the application displayed on SDL. 
	 * @param ngnMediaScreenAppName - Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters. 
	 * @param vrSynonyms - A vector of strings, all of which can be used as voice commands to 
	 * @param isMediaApp - Indicates if the app is a media application.
	 * @param sdlMsgVersion - Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle. 
	 * @param languageDesired - Indicates the language desired for the SDL interface.
	 * @param autoActivateID - ID used to re-register previously registered application.
	 * @throws SdlException
	 */
	@Deprecated
	public SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, 
			Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, 
			Language hmiDisplayLanguageDesired, String appID, String autoActivateID) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources, 
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				/*callbackToUIThread*/ false,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, and autoActivateID.", SDL_LIB_TRACE_KEY);
	}
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param context - Used to create a multiplexing transport config
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName - Name of the application displayed on SDL. 
	 * @param ngnMediaScreenAppName - Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters. 
	 * @param vrSynonyms - A vector of strings, all of which can be used as voice commands to 
	 * @param isMediaApp - Indicates if the app is a media application.
	 * @param sdlMsgVersion - Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle. 
	 * @param languageDesired - Indicates the language desired for the SDL interface.
	 * @param autoActivateID - ID used to re-register previously registered application.
	 * @throws SdlException
	 */
	public SdlProxyALM(Context context,IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, 
			Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, 
			Language hmiDisplayLanguageDesired, String appID, String autoActivateID) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources, 
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				/*callbackToUIThread*/ false,
				new MultiplexTransportConfig(context,appID));
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, and autoActivateID.", SDL_LIB_TRACE_KEY);
	}
	/**
	 * @deprecated
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName - Name of the application displayed on SDL. 
	 * @param ngnMediaScreenAppName - Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters. 
	 * @param vrSynonyms - A vector of strings, all of which can be used as voice commands to 
	 * @param isMediaApp - Indicates if the app is a media application.
	 * @param sdlMsgVersion - Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle. 
	 * @param languageDesired - Indicates the language desired for the SDL interface.
	 * @param autoActivateID - ID used to re-register previously registered application.
	 * @param callbackToUIThread - If true, all callbacks will occur on the UI thread.
	 * @throws SdlException
	 */
	@Deprecated
	public SdlProxyALM(IProxyListenerALM listener, String appName, String ngnMediaScreenAppName, 
			Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, 
			Language languageDesired, Language hmiDisplayLanguageDesired, String appID, 
			String autoActivateID, boolean callbackToUIThread) throws SdlException {
		super(	listener, 
				/*sdl proxy configuration resources*/null,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, autoActivateID, " +
				"and callbackToUIThread", SDL_LIB_TRACE_KEY);
	}
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param context - Used to create a multiplexing transport config
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName - Name of the application displayed on SDL. 
	 * @param ngnMediaScreenAppName - Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters. 
	 * @param vrSynonyms - A vector of strings, all of which can be used as voice commands to 
	 * @param isMediaApp - Indicates if the app is a media application.
	 * @param sdlMsgVersion - Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle. 
	 * @param languageDesired - Indicates the language desired for the SDL interface.
	 * @param autoActivateID - ID used to re-register previously registered application.
	 * @param callbackToUIThread - If true, all callbacks will occur on the UI thread.
	 * @throws SdlException
	 */
	public SdlProxyALM(Context context,IProxyListenerALM listener, String appName, String ngnMediaScreenAppName, 
			Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, 
			Language languageDesired, Language hmiDisplayLanguageDesired, String appID, 
			String autoActivateID, boolean callbackToUIThread) throws SdlException {
		super(	listener, 
				/*sdl proxy configuration resources*/null,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				new MultiplexTransportConfig(context,appID));
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, autoActivateID, " +
				"and callbackToUIThread", SDL_LIB_TRACE_KEY);
	}
	/**
	 * @deprecated
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName - Name of the application displayed on SDL. 
	 * @param ngnMediaScreenAppName - Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters. 
	 * @param vrSynonyms - A vector of strings, all of which can be used as voice commands to 
	 * @param isMediaApp - Indicates if the app is a media application.
	 * @param sdlMsgVersion - Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle. 
	 * @param languageDesired - Indicates the language desired for the SDL interface.
	 * @param autoActivateID - ID used to re-register previously registered application.
	 * @param callbackToUIThread - If true, all callbacks will occur on the UI thread.
	 * @throws SdlException
	 */
	@Deprecated
	public SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, 
			String appID, String autoActivateID, 
			boolean callbackToUIThread) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, autoActivateID, " +
				"and callbackToUIThread", SDL_LIB_TRACE_KEY);
	}
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param context - Used to create a multiplexing transport config
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName - Name of the application displayed on SDL. 
	 * @param ngnMediaScreenAppName - Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters. 
	 * @param vrSynonyms - A vector of strings, all of which can be used as voice commands to 
	 * @param isMediaApp - Indicates if the app is a media application.
	 * @param sdlMsgVersion - Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle. 
	 * @param languageDesired - Indicates the language desired for the SDL interface.
	 * @param autoActivateID - ID used to re-register previously registered application.
	 * @param callbackToUIThread - If true, all callbacks will occur on the UI thread.
	 * @throws SdlException
	 */
	public SdlProxyALM(Context context,IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, 
			String appID, String autoActivateID, 
			boolean callbackToUIThread) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				new MultiplexTransportConfig(context,appID));
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, autoActivateID, " +
				"and callbackToUIThread", SDL_LIB_TRACE_KEY);
	}
	
	@Deprecated
	public SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, 
			String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				preRegister,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, autoActivateID, " +
				"callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}
	
	public SdlProxyALM(Context context,IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, 
			String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				preRegister,
				new MultiplexTransportConfig(context,appID));
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, autoActivateID, " +
				"callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}
	
	/********************************************** TRANSPORT SWITCHING SUPPORT *****************************************/

	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management.
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName Name of the application displayed on SDL. 
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param transportConfig Initial configuration for transport.
	 * @throws SdlException
	 */
	public SdlProxyALM(IProxyListenerALM listener, String appName, Boolean isMediaApp, 
			Language languageDesired, Language hmiDisplayLanguageDesired, String appID,
			BaseTransportConfig transportConfig) throws SdlException {
		super(	listener, 
				/*sdl proxy configuration resources*/null, 
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				/*ngn media app*/null,
				/*vr synonyms*/null,
				/*is media app*/isMediaApp,
				/*sdlMsgVersion*/null,
				/*language desired*/languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				/*autoActivateID*/null,
				/*callbackToUIThread*/ false,
				transportConfig);
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using new constructor with specified transport) instance passing in: IProxyListener, appName, and isMediaApp.", SDL_LIB_TRACE_KEY);
	}
	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName Name of the application displayed on SDL. 
	 * @param ngnMediaScreenAppName Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters. 
	 * @param vrSynonyms A vector of strings, all of which can be used as voice commands to 
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param sdlMsgVersion Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle. 
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param autoActivateID ID used to re-register previously registered application.
	 * @param transportConfig Initial configuration for transport. 
	 * @throws SdlException
	 */
	public SdlProxyALM(IProxyListenerALM listener, String appName, String ngnMediaScreenAppName, 
			Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, 
			Language languageDesired, Language hmiDisplayLanguageDesired, String appID, 
			String autoActivateID, TransportType transportType, BaseTransportConfig transportConfig) throws SdlException {
		super(	listener, 
				/*sdl proxy configuration resources*/null, 
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				/*callbackToUIThread*/ false,
				transportConfig);
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using new constructor with specified transport) instance passing in: IProxyListener, appName, ngnMediaScreenAppName, " +
				"vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, and autoActivateID.", SDL_LIB_TRACE_KEY);
	}
	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName Name of the application displayed on SDL. 
	 * @param ngnMediaScreenAppName Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters. 
	 * @param vrSynonyms A vector of strings, all of which can be used as voice commands to 
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param sdlMsgVersion Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle. 
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param autoActivateID ID used to re-register previously registered application.
	 * @param transportConfig Initial configuration for transport. 
	 * @throws SdlException
	 */
	public SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, 
			Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, 
			Language hmiDisplayLanguageDesired, String appID, String autoActivateID,
			BaseTransportConfig transportConfig) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources, 
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				/*callbackToUIThread*/ false,
				transportConfig);
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using new constructor with specified transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, and autoActivateID.", SDL_LIB_TRACE_KEY);
	}
	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName Name of the application displayed on SDL. 
	 * @param ngnMediaScreenAppName Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters. 
	 * @param vrSynonyms A vector of strings, all of which can be used as voice commands to 
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param sdlMsgVersion Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle. 
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param autoActivateID ID used to re-register previously registered application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param transportConfig Initial configuration for transport. 
	 * @throws SdlException
	 */
	public SdlProxyALM(IProxyListenerALM listener, String appName, String ngnMediaScreenAppName, 
			Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, 
			Language languageDesired, Language hmiDisplayLanguageDesired, String appID, 
			String autoActivateID, boolean callbackToUIThread, 
			BaseTransportConfig transportConfig) throws SdlException {
		super(	listener, 
				/*sdl proxy configuration resources*/null,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				transportConfig);
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using new constructor with specified transport) instance passing in: IProxyListener, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, autoActivateID, " +
				"and callbackToUIThread", SDL_LIB_TRACE_KEY);
	}
	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL. 
	 * @param appName Name of the application displayed on SDL. 
	 * @param ngnMediaScreenAppName Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters.
	 * @param vrSynonyms A vector of strings, all of which can be used as voice commands too
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param sdlMsgVersion Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle. 
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param autoActivateID ID used to re-register previously registered application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param transportConfig Initial configuration for transport. 
	 * @throws SdlException
	 */
	public SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, 
			String appID, String autoActivateID, 
			boolean callbackToUIThread, BaseTransportConfig transportConfig) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				transportConfig);
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using new constructor with specified transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, autoActivateID, " +
				"and callbackToUIThread", SDL_LIB_TRACE_KEY);
	}

	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param sdlProxyConfigurationResources Proxy configuration resources.
	 * @param appName Name of the application displayed on SDL.
	 * @param ngnMediaScreenAppName Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters.
	 * @param vrSynonyms A vector of strings, all of which can be used as voice commands too
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param sdlMsgVersion Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle.
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param hmiDisplayLanguageDesired Desired language in HMI.
	 * @param appID Identifier of the client application.
	 * @param autoActivateID ID used to re-register previously registered application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param preRegister Flag that indicates that client should be pre-registred or not
	 * @param transportConfig Initial configuration for transport. 
	 * @throws SdlException
	 */
	public SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, 
			String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister,
			BaseTransportConfig transportConfig) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*TTS Name*/null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				preRegister,
				transportConfig);
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using new constructor with specified transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, autoActivateID, " +
				"callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}

	/**
	 * @deprecated
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param appName Name of the application displayed on SDL.
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param hmiDisplayLanguageDesired Desired language in HMI.
	 * @param appID Identifier of the client application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param preRegister Flag that indicates that client should be pre-registred or not
	 * @throws SdlException
	 */	
	@Deprecated
	public SdlProxyALM(IProxyListenerALM listener, String appName, Boolean isMediaApp,Language languageDesired, Language hmiDisplayLanguageDesired,
						String appID, boolean callbackToUIThread, boolean preRegister) throws SdlException 
	{
		super(	listener, 
			/*sdlProxyConfigurationResources*/null,
			/*enable advanced lifecycle management*/true, 
			appName,
			/*ttsName*/null,
			/*ngnMediaScreenAppName*/null,
			/*vrSynonyms*/null,
			isMediaApp,
			/*sdlMsgVersion*/null,
			languageDesired,
			hmiDisplayLanguageDesired,
			/*App Type*/null,
			/*App ID*/appID,
			/*autoActivateID*/null,
			callbackToUIThread,
			preRegister,
			new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, " +
			"appName, isMediaApp, languageDesired, hmiDisplayLanguageDesired" + "callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param context - Used to create a multiplexing transport config
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param appName Name of the application displayed on SDL.
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param hmiDisplayLanguageDesired Desired language in HMI.
	 * @param appID Identifier of the client application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param preRegister Flag that indicates that client should be pre-registred or not
	 * @throws SdlException
	 */	
	public SdlProxyALM(Context context,IProxyListenerALM listener, String appName, Boolean isMediaApp,Language languageDesired, Language hmiDisplayLanguageDesired,
						String appID, boolean callbackToUIThread, boolean preRegister) throws SdlException 
	{
		super(	listener, 
			/*sdlProxyConfigurationResources*/null,
			/*enable advanced lifecycle management*/true, 
			appName,
			/*ttsName*/null,
			/*ngnMediaScreenAppName*/null,
			/*vrSynonyms*/null,
			isMediaApp,
			/*sdlMsgVersion*/null,
			languageDesired,
			hmiDisplayLanguageDesired,
			/*App Type*/null,
			/*App ID*/appID,
			/*autoActivateID*/null,
			callbackToUIThread,
			preRegister,
			new MultiplexTransportConfig(context,appID));
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, " +
			"appName, isMediaApp, languageDesired, hmiDisplayLanguageDesired" + "callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}
	
	/**
	 * @deprecated
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param appName Name of the application displayed on SDL.
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param appID Identifier of the client application.
	 * @throws SdlException
	 */	
	@Deprecated
	public SdlProxyALM(IProxyListenerALM listener, String appName, Boolean isMediaApp,String appID) throws SdlException {
		super(	listener, 
				/*sdlProxyConfigurationResources*/null,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*ttsName*/null,
				/*ngnMediaScreenAppName*/null,
				/*vrSynonyms*/null,
				isMediaApp,
				/*sdlMsgVersion*/null,
				/*languageDesired*/null,
				/*hmiDisplayLanguageDesired*/null,
				/*App Type*/null,
				/*App ID*/appID,
				/*autoActivateID*/null,
				false,
				false,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, " +
				"appName, isMediaApp, appID", SDL_LIB_TRACE_KEY);
	}
	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param context - Used to create a multiplexing transport config
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param appName Name of the application displayed on SDL.
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param appID Identifier of the client application.
	 * @throws SdlException
	 */		
	public SdlProxyALM(Context context,IProxyListenerALM listener, String appName, Boolean isMediaApp,String appID) throws SdlException {
		super(	listener, 
				/*sdlProxyConfigurationResources*/null,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*ttsName*/null,
				/*ngnMediaScreenAppName*/null,
				/*vrSynonyms*/null,
				isMediaApp,
				/*sdlMsgVersion*/null,
				/*languageDesired*/null,
				/*hmiDisplayLanguageDesired*/null,
				/*App Type*/null,
				/*App ID*/appID,
				/*autoActivateID*/null,
				false,
				false,
				new MultiplexTransportConfig(context,appID));
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, " +
				"appName, isMediaApp, appID", SDL_LIB_TRACE_KEY);
	}

	/**
	 * @deprecated
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param sdlProxyConfigurationResources Proxy configuration resources.
	 * @param appName Name of the application displayed on SDL.
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param appID Identifier of the client application.
	 * @throws SdlException
	 */	
	@Deprecated
	public SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, Boolean isMediaApp,String appID) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*ttsName*/null,
				/*ngnMediaScreenAppName*/null,
				/*vrSynonyms*/null,
				isMediaApp,
				/*sdlMsgVersion*/null,
				/*languageDesired*/null,
				/*hmiDisplayLanguageDesired*/null,
				/*App Type*/null,
				/*App ID*/appID,
				/*autoActivateID*/null,
				false,
				false,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, " +
				"sdlProxyConfigurationResources, appName, isMediaApp, appID", SDL_LIB_TRACE_KEY);
	}

	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param context - Used to create a multiplexing transport config
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param sdlProxyConfigurationResources Proxy configuration resources.
	 * @param appName Name of the application displayed on SDL.
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param appID Identifier of the client application.
	 * @throws SdlException
	 */		
	public SdlProxyALM(Context context, IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, Boolean isMediaApp,String appID) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*ttsName*/null,
				/*ngnMediaScreenAppName*/null,
				/*vrSynonyms*/null,
				isMediaApp,
				/*sdlMsgVersion*/null,
				/*languageDesired*/null,
				/*hmiDisplayLanguageDesired*/null,
				/*App Type*/null,
				/*App ID*/appID,
				/*autoActivateID*/null,
				false,
				false,
				new MultiplexTransportConfig(context,appID));
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, " +
				"sdlProxyConfigurationResources, appName, isMediaApp, appID", SDL_LIB_TRACE_KEY);
	}
	
	public SdlProxyALM(IProxyListenerALM listener, String appName, Boolean isMediaApp,String appID,BaseTransportConfig transportConfig) throws SdlException {
		super(	listener, 
				/*sdlProxyConfigurationResources*/null,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*ttsName*/null,
				/*ngnMediaScreenAppName*/null,
				/*vrSynonyms*/null,
				isMediaApp,
				/*sdlMsgVersion*/null,
				/*languageDesired*/null,
				/*hmiDisplayLanguageDesired*/null,
				/*App Type*/null,
				/*App ID*/appID,
				/*autoActivateID*/null,
				false,
				false,
				transportConfig);
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, " +
				"appName, isMediaApp, appID", SDL_LIB_TRACE_KEY);
	}

	/**
	 * @deprecated
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param appName Name of the application displayed on SDL.
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param appID Identifier of the client application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param preRegister Flag that indicates that client should be pre-registred or not
	 * @throws SdlException
	 */		
	@Deprecated
	public SdlProxyALM(IProxyListenerALM listener, String appName, Boolean isMediaApp,String appID, 
						 boolean callbackToUIThread, boolean preRegister) throws SdlException {
		super(	listener, 
				/*sdlProxyConfigurationResources*/null,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*ttsName*/null,
				/*ngnMediaScreenAppName*/null,
				/*vrSynonyms*/null,
				isMediaApp,
				/*sdlMsgVersion*/null,
				/*languageDesired*/null,
				/*hmiDisplayLanguageDesired*/null,
				/*App Type*/null,
				/*App ID*/appID,
				/*autoActivateID*/null,
				callbackToUIThread,
				preRegister,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, " +
				"appName, isMediaApp, " + "callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param context - Used to create a multiplexing transport config
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param appName Name of the application displayed on SDL.
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param appID Identifier of the client application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param preRegister Flag that indicates that client should be pre-registred or not
	 * @throws SdlException
	 */		
	public SdlProxyALM(Context context,IProxyListenerALM listener, String appName, Boolean isMediaApp,String appID, 
						 boolean callbackToUIThread, boolean preRegister) throws SdlException {
		super(	listener, 
				/*sdlProxyConfigurationResources*/null,
				/*enable advanced lifecycle management*/true, 
				appName,
				/*ttsName*/null,
				/*ngnMediaScreenAppName*/null,
				/*vrSynonyms*/null,
				isMediaApp,
				/*sdlMsgVersion*/null,
				/*languageDesired*/null,
				/*hmiDisplayLanguageDesired*/null,
				/*App Type*/null,
				/*App ID*/appID,
				/*autoActivateID*/null,
				callbackToUIThread,
				preRegister,
				new MultiplexTransportConfig(context,appID));
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, " +
				"appName, isMediaApp, " + "callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}

	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * @param appService Reference to the apps service object. 
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param sdlProxyConfigurationResources Proxy configuration resources.
	 * @param appName Name of the application displayed on SDL.
	 * @param ttsName TTS name.
	 * @param ngnMediaScreenAppName Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters.
	 * @param vrSynonyms A vector of strings, all of which can be used as voice commands too
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param sdlMsgVersion Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle.
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param hmiDisplayLanguageDesired Desired language in HMI.
	 * @param appID Identifier of the client application.
	 * @param autoActivateID ID used to re-register previously registered application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param preRegister Flag that indicates that client should be pre-registred or not
	 * @throws SdlException
	 */	
	public SdlProxyALM(Service appService, IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, 
			String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				ttsName,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				preRegister,
				new MultiplexTransportConfig(appService.getBaseContext(),appID));
		
				this.setAppService(appService);
				this.sendTransportBroadcast();
				
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, autoActivateID, " +
				"callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}
	
	
	public SdlProxyALM(Service appService, IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, 
			String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister, BaseTransportConfig transportConfig) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				ttsName,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				preRegister,
				transportConfig);
		
				this.setAppService(appService);
				this.sendTransportBroadcast();
				
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, autoActivateID, " +
				"callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}
	
	
	
	/**
	 * @deprecated
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param sdlProxyConfigurationResources Proxy configuration resources.
	 * @param appName Name of the application displayed on SDL.
	 * @param ttsName TTS name.
	 * @param ngnMediaScreenAppName Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters.
	 * @param vrSynonyms A vector of strings, all of which can be used as voice commands too
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param sdlMsgVersion Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle.
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param hmiDisplayLanguageDesired Desired language in HMI.
	 * @param appID Identifier of the client application.
	 * @param autoActivateID ID used to re-register previously registered application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param preRegister Flag that indicates that client should be pre-registred or not
	 * @throws SdlException
	 */	
	@Deprecated
	public SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, 
			String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				ttsName,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				preRegister,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, autoActivateID, " +
				"callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param context - Used to create a multiplexing transport config
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param sdlProxyConfigurationResources Proxy configuration resources.
	 * @param appName Name of the application displayed on SDL.
	 * @param ttsName TTS name.
	 * @param ngnMediaScreenAppName Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters.
	 * @param vrSynonyms A vector of strings, all of which can be used as voice commands too
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param sdlMsgVersion Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle.
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param hmiDisplayLanguageDesired Desired language in HMI.
	 * @param appID Identifier of the client application.
	 * @param autoActivateID ID used to re-register previously registered application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param preRegister Flag that indicates that client should be pre-registred or not
	 * @throws SdlException
	 */	
	public SdlProxyALM(Context context,IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, 
			String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				ttsName,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				preRegister,
				new MultiplexTransportConfig(context,appID));
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, autoActivateID, " +
				"callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}

		
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param sdlProxyConfigurationResources Proxy configuration resources.
	 * @param appName Name of the application displayed on SDL.
	 * @param ttsName TTS name.
	 * @param ngnMediaScreenAppName Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters.
	 * @param vrSynonyms A vector of strings, all of which can be used as voice commands too
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param sdlMsgVersion Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle.
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param hmiDisplayLanguageDesired Desired language in HMI.
	 * @param appID Identifier of the client application.
	 * @param autoActivateID ID used to re-register previously registered application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param preRegister Flag that indicates that client should be pre-registred or not
	 * @param transportConfig Initial configuration for transport. 
	 * @throws SdlException
	 */
	public SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, 
			String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister,
			BaseTransportConfig transportConfig) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				ttsName,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/null,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				preRegister,
				transportConfig);
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using new constructor with specified transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, autoActivateID, " +
				"callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}		
	/**
	 * @deprecated
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param sdlProxyConfigurationResources Proxy configuration resources.
	 * @param appName Name of the application displayed on SDL.
	 * @param ttsName TTS name.
	 * @param ngnMediaScreenAppName Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters.
	 * @param vrSynonyms A vector of strings, all of which can be used as voice commands too
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param sdlMsgVersion Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle.
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param hmiDisplayLanguageDesired Desired language in HMI. 
	 * @param appType Type of application. 
	 * @param appID Identifier of the client application.
	 * @param autoActivateID ID used to re-register previously registered application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param preRegister Flag that indicates that client should be pre-registred or not
	 * @throws SdlException
	 */	
	@Deprecated
	public SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, 
			Vector<AppHMIType> appType, String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				ttsName,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/appType,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				preRegister,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, appType, appID, autoActivateID, " +
				"callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param context - Used to create a multiplexing transport config
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param sdlProxyConfigurationResources Proxy configuration resources.
	 * @param appName Name of the application displayed on SDL.
	 * @param ttsName TTS name.
	 * @param ngnMediaScreenAppName Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters.
	 * @param vrSynonyms A vector of strings, all of which can be used as voice commands too
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param sdlMsgVersion Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle.
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param hmiDisplayLanguageDesired Desired language in HMI. 
	 * @param appType Type of application. 
	 * @param appID Identifier of the client application.
	 * @param autoActivateID ID used to re-register previously registered application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param preRegister Flag that indicates that client should be pre-registred or not
	 * @throws SdlException
	 */	
	public SdlProxyALM(Context context,IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, 
			Vector<AppHMIType> appType, String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				ttsName,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/appType,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				preRegister,
				new MultiplexTransportConfig(context,appID));
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using legacy constructor for BT transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, appType, appID, autoActivateID, " +
				"callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param sdlProxyConfigurationResources Proxy configuration resources.
	 * @param appName Name of the application displayed on SDL.
	 * @param ttsName TTS name.
	 * @param ngnMediaScreenAppName Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters.
	 * @param vrSynonyms A vector of strings, all of which can be used as voice commands too
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param sdlMsgVersion Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle.
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param hmiDisplayLanguageDesired Desired language in HMI.
	 * @param appType Type of application.
	 * @param appID Identifier of the client application.
	 * @param autoActivateID ID used to re-register previously registered application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param preRegister Flag that indicates that client should be pre-registred or not
	 * @param transportConfig Initial configuration for transport. 
	 * @throws SdlException
	 */
	public SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, 
			String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister,
			BaseTransportConfig transportConfig) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				ttsName,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/appType,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				preRegister,
				transportConfig);
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using new constructor with specified transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, appType, appID, autoActivateID, " +
				"callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}
	public SdlProxyALM(Service appService, IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, 
			String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister,
			BaseTransportConfig transportConfig) throws SdlException {
		super(	listener,
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true,
				appName,
				ttsName,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/appType,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				preRegister,
				transportConfig);
		
				this.setAppService(appService);
				this.sendTransportBroadcast();

		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using new constructor with specified transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, appType, appID, autoActivateID, " +
				"callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL via specified transport.
	 * 
	 * Takes advantage of the advanced lifecycle management. 
	 * 
	 * @param listener Reference to the object in the App listening to callbacks from SDL.
	 * @param sdlProxyConfigurationResources Proxy configuration resources.
	 * @param appName Name of the application displayed on SDL.
	 * @param ttsName TTS name.
	 * @param ngnMediaScreenAppName Name of the application displayed on SDL for Navigation equipped 
	 * vehicles. Limited to five characters.
	 * @param vrSynonyms A vector of strings, all of which can be used as voice commands too
	 * @param isMediaApp Indicates if the app is a media application.
	 * @param sdlMsgVersion Indicates the version of SDL SmartDeviceLink Messages desired. Must be less than
	 * or equal to the version of SDL SmartDeviceLink running on the vehicle.
	 * @param languageDesired Indicates the language desired for the SDL interface.
	 * @param hmiDisplayLanguageDesired Desired language in HMI.
	 * @param appType Type of application.
	 * @param appID Identifier of the client application.
	 * @param autoActivateID ID used to re-register previously registered application.
	 * @param callbackToUIThread If true, all callbacks will occur on the UI thread.
	 * @param preRegister Flag that indicates that client should be pre-registred or not
	 * @param sHashID HashID used for app resumption
	 * @param transportConfig Initial configuration for transport. 
	 * @throws SdlException
	 */
	public SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, 
			String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister, String sHashID,
			BaseTransportConfig transportConfig) throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true, 
				appName,
				ttsName,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/appType,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				preRegister,
				/*sHashID*/sHashID,
				true,
				transportConfig);
		
		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using new constructor with specified transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, appType, appID, autoActivateID, " +
				"callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}
	public SdlProxyALM(Service appService, IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, 
			String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister, String sHashID,
			BaseTransportConfig transportConfig) throws SdlException {
		super(	listener,
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/true,
				appName,
				ttsName,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp,
				sdlMsgVersion,
				languageDesired,
				/*HMI Display Language Desired*/hmiDisplayLanguageDesired,
				/*App Type*/appType,
				/*App ID*/appID,
				autoActivateID,
				callbackToUIThread,
				preRegister,
				/*sHashID*/sHashID,
				/*bEnableResume*/true,
				transportConfig);

				this.setAppService(appService);
				this.sendTransportBroadcast();

		SdlTrace.logProxyEvent("Application constructed SdlProxyALM (using new constructor with specified transport) instance passing in: IProxyListener, sdlProxyConfigurationResources, " +
				"appName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, sdlMsgVersion, languageDesired, appType, appID, autoActivateID, " +
				"callbackToUIThread and version", SDL_LIB_TRACE_KEY);
	}	
	/***************************************** END OF TRANSPORT SWITCHING SUPPORT ***************************************/
	
	// Allow applications using ALM to reset the proxy (dispose and reinstantiate)
	/**
	 * Disconnects the application from SDL, then recreates the transport such that
	 * the next time a SDL unit discovers applications, this application will be
	 * available.
	 */
	public void resetProxy() throws SdlException {
		super.cycleProxy(SdlDisconnectedReason.APPLICATION_REQUESTED_DISCONNECT);
	}
	
	/********* Getters for values returned by RegisterAppInterfaceResponse **********/
	
	/**
	 * Gets buttonCapabilities set when application interface is registered.
	 * 
	 * @return buttonCapabilities
	 * @throws SdlException
	 */
	public List<ButtonCapabilities> getButtonCapabilities() throws SdlException{
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test SDL availability 
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is unavailable. Unable to get the buttonCapabilities.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _buttonCapabilities;
	}
	
	/**
	 * Gets getSoftButtonCapabilities set when application interface is registered.
	 * 
	 * @return softButtonCapabilities 
	 * @throws SdlException
	 */
	public List<SoftButtonCapabilities> getSoftButtonCapabilities() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test SDL availability 
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is not connected. Unable to get the softButtonCapabilities.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _softButtonCapabilities;
	}
	
	/**
	 * Gets getPresetBankCapabilities set when application interface is registered.
	 * 
	 * @return presetBankCapabilities 
	 * @throws SdlException
	 */
	public PresetBankCapabilities getPresetBankCapabilities() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test SDL availability 
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is not connected. Unable to get the presetBankCapabilities.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _presetBankCapabilities;
	}
	
	/**
	 * Gets the current version information of the proxy.
	 * 
	 * @return String
	 * @throws SdlException
	 */
	public String getProxyVersionInfo() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}		

		if (Version.VERSION != null)
				return  Version.VERSION;
		
		return null;
	}

	
	
	/**
	 * Gets displayCapabilities set when application interface is registered.
	 * 
	 * @return displayCapabilities
	 * @throws SdlException
	 */
	public DisplayCapabilities getDisplayCapabilities() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test SDL availability 
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is unavailable. Unable to get the displayCapabilities.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _displayCapabilities;
	}
	
	/**
	 * Gets hmiZoneCapabilities set when application interface is registered.
	 * 
	 * @return hmiZoneCapabilities
	 * @throws SdlException
	 */
	public List<HmiZoneCapabilities> getHmiZoneCapabilities() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test SDL availability 
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is unavailable. Unable to get the hmiZoneCapabilities.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _hmiZoneCapabilities;
	}
	
	/**
	 * Gets speechCapabilities set when application interface is registered.
	 * 
	 * @return speechCapabilities
	 * @throws SdlException
	 */
	public List<SpeechCapabilities> getSpeechCapabilities() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test SDL availability 
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is unavailable. Unable to get the speechCapabilities.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		
		return _speechCapabilities;
	}
	/**
	 * Gets PrerecordedSpeech set when application interface is registered.
	 * 
	 * @return PrerecordedSpeech
	 * @throws SdlException
	 */
	public List<PrerecordedSpeech> getPrerecordedSpeech() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test SDL availability 
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is unavailable. Unable to get the PrerecordedSpeech.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		
		return _prerecordedSpeech;
	}
	/**
	 * Gets sdlLanguage set when application interface is registered.
	 * 
	 * @return sdlLanguage
	 * @throws SdlException
	 */
	public Language getSdlLanguage() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test SDL availability 
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is unavailable. Unable to get the sdlLanguage.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _sdlLanguage;
	}
	
	/**
	 * Gets getHmiDisplayLanguage set when application interface is registered.
	 * 
	 * @return hmiDisplayLanguage 
	 * @throws SdlException
	 */
	public Language getHmiDisplayLanguage() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test SDL availability 
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is not connected. Unable to get the hmiDisplayLanguage.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _hmiDisplayLanguage;
	}
	
	/**
	 * Gets sdlMsgVersion set when application interface is registered.
	 * 
	 * @return sdlMsgVersion
	 * @throws SdlException
	 */
	public SdlMsgVersion getSdlMsgVersion() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test SDL availability 
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is unavailable. Unable to get the sdlMsgVersion.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _sdlMsgVersion;
	}
	
	/**
	 * Gets vrCapabilities set when application interface is registered.
	 * 
	 * @return vrCapabilities
	 * @throws SdlException
	 */
	public List<VrCapabilities> getVrCapabilities() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test SDL availability 
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is unavailable. Unable to get the vrCapabilities.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _vrCapabilities;
	}
	
	/**
	 * Gets getVehicleType set when application interface is registered.
	 * 
	 * @return vehicleType 
	 * @throws SdlException
	 */
	public VehicleType getVehicleType() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test SDL availability 
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is not connected. Unable to get the vehicleType.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _vehicleType;
	}
	
	/**
	* Gets AudioPassThruCapabilities set when application interface is registered.
	*
	* @return AudioPassThruCapabilities
	* @throws SdlException
	*/
	public List<AudioPassThruCapabilities> getAudioPassThruCapabilities() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}

		// Test SDL availability
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is not connected. Unable to get the vehicleType.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _audioPassThruCapabilities;
	}

	public List<Integer> getSupportedDiagModes() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test SDL availability 
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is not connected. Unable to get SupportedDiagModes.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _diagModes;
	}	
	
	public HMICapabilities getHmiCapabilities() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}

		// Test SDL availability
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is not connected. Unable to get the HMICapabilities.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _hmiCapabilities;
	}	
	
	
	public String getSystemSoftwareVersion() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}

		// Test SDL availability
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is not connected. Unable to get the SystemSoftwareVersion.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _systemSoftwareVersion;
	}	
	
	public boolean isAppResumeSuccess() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test SDL availability 
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is not connected. Unable to get isResumeSuccess.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _bResumeSuccess;
	}	
	
}
