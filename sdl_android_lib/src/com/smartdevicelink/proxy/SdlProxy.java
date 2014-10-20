package com.smartdevicelink.proxy;

import java.util.Vector;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.SdlExceptionCause;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.trace.SdlTrace;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;

@Deprecated
public class SdlProxy extends SdlProxyBase<IProxyListener> {
	
	private static final String SDL_LIB_TRACE_KEY = "42baba60-eb57-11df-98cf-0800200c9a66";
	@SuppressWarnings("unused")
    private static final String SDL_LIB_PRIVATE_TOKEN = "{DAE1A88C-6C16-4768-ACA5-6F1247EA01C2}";

	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL. 
	 * 
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @throws SdlException
	 */
	@Deprecated
	public SdlProxy(IProxyListener listener) throws SdlException {
		super(	listener, 
				/*application context*/null, 
				/*enable advanced lifecycle management*/false, 
				/*app name*/ null,
				/*TTS Name*/null,
				/*ngn media screen app name*/null,
				/*vr synonyms*/null,
				/*is media app*/ null,
				/*sdlMsgVersion*/null,
				/*language desired*/null,
				/*HMI Display Language Desired*/null,
				/*App Type*/null,
				/*App ID*/null,
				/*autoActivateID*/null,
				/*callbackToUIThread*/ true,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxy instance passing in: IProxyListener.", SDL_LIB_TRACE_KEY);
	}
	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL. 
	 * 
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param sAppName
	 * @param sAppID
	 * @throws SdlException
	 */
	@Deprecated
	public SdlProxy(IProxyListener listener, String sAppName, String sAppID) throws SdlException {
		super(	listener, 
				/*application context*/null, 
				/*enable advanced lifecycle management*/false, 
				/*app name*/ sAppName,
				/*TTS Name*/null,
				/*ngn media screen app name*/null,
				/*vr synonyms*/null,
				/*is media app*/ null,
				/*sdlMsgVersion*/null,
				/*language desired*/null,
				/*HMI Display Language Desired*/null,
				/*App Type*/null,
				/*App ID*/sAppID,
				/*autoActivateID*/null,
				/*callbackToUIThread*/ true,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxy instance passing in: IProxyListener.", SDL_LIB_TRACE_KEY);
	}	
	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL. 
	 * 
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param sdlProxyConfigurationResources
	 * @throws SdlException
	 */
	@Deprecated
	public SdlProxy(IProxyListener listener, SdlProxyConfigurationResources sdlProxyConfigurationResources) 
		throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources, 
				/*enable advanced lifecycle management*/false, 
				/*app name*/ null,
				/*TTS Name*/null,
				/*ngn media screen app name*/null,
				/*vr synonyms*/null,
				/*is media app*/ null,
				/*sdlMsgVersion*/null,
				/*language desired*/null,
				/*HMI Display Language Desired*/null,
				/*App Type*/null,
				/*App ID*/null,
				/*autoActivateID*/null,
				/*callbackToUIThread*/ true,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxy instance passing in: IProxyListener, SdlProxyConfigurationResources.", SDL_LIB_TRACE_KEY);
	}
	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL.
	 * 
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param callbackToUIThread - If true, all callbacks will occur on the UI thread.
	 * @throws SdlException
	 */
	@Deprecated
	public SdlProxy(IProxyListener listener, boolean callbackToUIThread) throws SdlException {
		super(	listener,  
				/*sdl proxy configuration resources*/null,
				/*enable advanced lifecycle management*/false, 
				/*app name*/ null,
				/*TTS Name*/null,
				/*ngn media screen app name*/null,
				/*vr synonyms*/null,
				/*is media app*/ null,
				/*sdlMsgVersion*/null,
				/*language desired*/null,
				/*HMI Display Language Desired*/null,
				/*App Type*/null,
				/*App ID*/null,
				/*autoActivateID*/null,
				callbackToUIThread,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxy instance passing in: IProxyListener, callBackToUIThread.", SDL_LIB_TRACE_KEY);
	}
	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL.
	 * 
	 * @param listener - Reference to the object in the App listening to callbacks from SDL.
	 * @param sdlProxyConfigurationResources 
	 * @param callbackToUIThread - If true, all callbacks will occur on the UI thread.
	 * @throws SdlException
	 */
	@Deprecated
	public SdlProxy(IProxyListener listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			boolean callbackToUIThread) throws SdlException {
		super(	listener,  
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/false, 
				/*app name*/ null,
				/*TTS Name*/null,
				/*ngn media screen app name*/null,
				/*vr synonyms*/null,
				/*is media app*/ null,
				/*sdlMsgVersion*/null,
				/*language desired*/null,
				/*HMI Display Language Desired*/null,
				/*App Type*/null,
				/*App ID*/null,
				/*autoActivateID*/null,
				callbackToUIThread,
				new BTTransportConfig());
		
		SdlTrace.logProxyEvent("Application constructed SdlProxy instance passing in: IProxyListener, callBackToUIThread.", SDL_LIB_TRACE_KEY);
	}
	
	/********************************************** TRANSPORT SWITCHING SUPPORT *****************************************/

	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL. 
	 * 
	 * @param listener - Reference to the object in the App listening to callbacks from SDL.
	 * @param transportConfig Initial configuration for transport.
	 * @throws SdlException
	 */
	@Deprecated
	public SdlProxy(IProxyListener listener, BaseTransportConfig transportConfig) throws SdlException {
		super(	listener, 
				/*application context*/null, 
				/*enable advanced lifecycle management*/false, 
				/*app name*/ null,
				/*TTS Name*/null,
				/*ngn media screen app name*/null,
				/*vr synonyms*/null,
				/*is media app*/ null,
				/*sdlMsgVersion*/null,
				/*language desired*/null,
				/*HMI Display Language Desired*/null,
				/*App Type*/null,
				/*App ID*/null,
				/*autoActivateID*/null,
				/*callbackToUIThread*/ true,
				transportConfig);
		
		SdlTrace.logProxyEvent("Application constructed SdlProxy instance passing in: IProxyListener.", SDL_LIB_TRACE_KEY);
	}
	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL. 
	 * 
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param sdlProxyConfigurationResources
	 * @param transportConfig Initial configuration for transport.
	 * @throws SdlException
	 */
	@Deprecated
	public SdlProxy(IProxyListener listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
					BaseTransportConfig transportConfig) 
		throws SdlException {
		super(	listener, 
				sdlProxyConfigurationResources, 
				/*enable advanced lifecycle management*/false, 
				/*app name*/ null,
				/*TTS Name*/null,
				/*ngn media screen app name*/null,
				/*vr synonyms*/null,
				/*is media app*/ null,
				/*sdlMsgVersion*/null,
				/*language desired*/null,
				/*HMI Display Language Desired*/null,
				/*App Type*/null,
				/*App ID*/null,
				/*autoActivateID*/null,
				/*callbackToUIThread*/ true,
				transportConfig);
		
		SdlTrace.logProxyEvent("Application constructed SdlProxy instance passing in: IProxyListener, SdlProxyConfigurationResources.", SDL_LIB_TRACE_KEY);
	}
	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL.
	 * 
	 * @param listener - Reference to the object in the App listening to callbacks from SDL. 
	 * @param callbackToUIThread - If true, all callbacks will occur on the UI thread.
	 * @param transportConfig Initial configuration for transport.
	 * @throws SdlException
	 */
	@Deprecated
	public SdlProxy(IProxyListener listener, boolean callbackToUIThread, BaseTransportConfig transportConfig) throws SdlException {
		super(	listener,  
				/*sdl proxy configuration resources*/null,
				/*enable advanced lifecycle management*/false, 
				/*app name*/ null,
				/*TTS Name*/null,
				/*ngn media screen app name*/null,
				/*vr synonyms*/null,
				/*is media app*/ null,
				/*sdlMsgVersion*/null,
				/*language desired*/null,
				/*HMI Display Language Desired*/null,
				/*App Type*/null,
				/*App ID*/null,
				/*autoActivateID*/null,
				callbackToUIThread,
				transportConfig);
		
		SdlTrace.logProxyEvent("Application constructed SdlProxy instance passing in: IProxyListener, callBackToUIThread.", SDL_LIB_TRACE_KEY);
	}
	
	/**
	 * Constructor for the SdlProxy object, the proxy for communicating between the App and SDL.
	 * 
	 * @param listener - Reference to the object in the App listening to callbacks from SDL.
	 * @param sdlProxyConfigurationResources 
	 * @param callbackToUIThread - If true, all callbacks will occur on the UI thread.
	 * @param transportConfig Initial configuration for transport.
	 * @throws SdlException
	 */
	@Deprecated
	public SdlProxy(IProxyListener listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			boolean callbackToUIThread, BaseTransportConfig transportConfig) throws SdlException {
		super(	listener,  
				sdlProxyConfigurationResources,
				/*enable advanced lifecycle management*/false, 
				/*app name*/ null,
				/*TTS Name*/null,
				/*ngn media screen app name*/null,
				/*vr synonyms*/null,
				/*is media app*/ null,
				/*sdlMsgVersion*/null,
				/*language desired*/null,
				/*HMI Display Language Desired*/null,
				/*App Type*/null,
				/*App ID*/null,
				/*autoActivateID*/null,
				callbackToUIThread,
				transportConfig);
		
		SdlTrace.logProxyEvent("Application constructed SdlProxy instance passing in: IProxyListener, callBackToUIThread.", SDL_LIB_TRACE_KEY);
	}
		
	/******************** Public Helper Methods *************************/
	
	
	/**
	 *  Sends a RegisterAppInterface RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *  
	 *  @param sdlMsgVersion
	 *  @param appName
	 *  @param ngnMediaScreenAppName
	 *  @param vrSynonyms
	 *  @param isMediaApp
	 *  @param languageDesired
	 *  @param autoActivateID
	 *  @param correlationID
	 *  
	 *  @throws SdlException
	 */
	@Deprecated
	public void registerAppInterface(
			SdlMsgVersion sdlMsgVersion, String appName, String ngnMediaScreenAppName,
			Vector<String> vrSynonyms, Boolean isMediaApp, Language languageDesired, Language hmiDisplayLanguageDesired,
			String appID, String autoActivateID, Integer correlationID) 
			throws SdlException {
		
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This SdlProxy object has been disposed, it is no long capable of sending requests.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		registerAppInterfacePrivate(
				sdlMsgVersion, 
				appName,
				null,
				ngnMediaScreenAppName,
				vrSynonyms,
				isMediaApp, 
				languageDesired,
				hmiDisplayLanguageDesired,
				null,
				appID,
				autoActivateID,
				correlationID);
	}
	
	/**
	 * Sends a RegisterAppInterface RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param appName
	 * @param isMediaApp
	 * @param autoActivateID
	 * @throws SdlException
	 */
	@Deprecated
	public void registerAppInterface(
			String appName, Boolean isMediaApp, String appID, String autoActivateID, Integer correlationID) 
			throws SdlException {
		
		registerAppInterface(
				/*sdlMsgVersion*/null, 
				appName,
				/*ngnMediaScreenAppName*/null,
				/*vrSynonyms*/null,
				isMediaApp, 
				/*languageDesired*/null,
				/*hmiDisplayLanguageDesired*/null,
				appID,
				autoActivateID,
				correlationID);
	}
	
	/**
	 * Sends a RegisterAppInterface RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param appName
	 * @throws SdlException
	 */
	@Deprecated
	public void registerAppInterface(String appName, String appID, Integer correlationID) 
			throws SdlException {
		
		registerAppInterface(appName, false, appID, "", correlationID);
	}
	
	/**
	 * Sends an UnregisterAppInterface RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param correlationID
	 * @throws SdlException
	 */
	@Deprecated
	public void unregisterAppInterface(Integer correlationID) 
			throws SdlException {		
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This SdlProxy object has been disposed, it is no long capable of executing methods.", 
										SdlExceptionCause.SDL_PROXY_DISPOSED);
		}		
				
		unregisterAppInterfacePrivate(correlationID);
	}
	
	/**
	 * Returns is isConnected state of the SDL transport.
	 * 
	 * @return Boolean isConnected
	 */
	@Deprecated
	public Boolean getIsConnected() {
		return super.getIsConnected();
	}

}
