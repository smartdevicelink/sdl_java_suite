package com.smartdevicelink.proxy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.smartdevicelink.connection.SdlConnection;
import com.smartdevicelink.connection.SdlSession;
import com.smartdevicelink.connection.interfaces.ISdlConnectionListener;
import com.smartdevicelink.dispatch.IncomingProtocolMessageComparitor;
import com.smartdevicelink.dispatch.InternalProxyMessageComparitor;
import com.smartdevicelink.dispatch.OutgoingProtocolMessageComparitor;
import com.smartdevicelink.dispatch.ProxyMessageDispatcher;
import com.smartdevicelink.dispatch.interfaces.IDispatchingStrategy;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.enums.SdlExceptionCause;
import com.smartdevicelink.marshall.JsonRpcMarshaller;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.protocol.enums.MessageType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.protocol.heartbeat.HeartbeatMonitor;
import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;
import com.smartdevicelink.proxy.interfaces.IProxyListener;
import com.smartdevicelink.proxy.interfaces.IProxyListenerAlm;
import com.smartdevicelink.proxy.interfaces.IProxyListenerBase;
import com.smartdevicelink.proxy.interfaces.IPutFileResponseListener;
import com.smartdevicelink.rpc.datatypes.AudioPassThruCapabilities;
import com.smartdevicelink.rpc.datatypes.ButtonCapabilities;
import com.smartdevicelink.rpc.datatypes.Choice;
import com.smartdevicelink.rpc.datatypes.DisplayCapabilities;
import com.smartdevicelink.rpc.datatypes.Headers;
import com.smartdevicelink.rpc.datatypes.Image;
import com.smartdevicelink.rpc.datatypes.PresetBankCapabilities;
import com.smartdevicelink.rpc.datatypes.SdlMsgVersion;
import com.smartdevicelink.rpc.datatypes.SoftButton;
import com.smartdevicelink.rpc.datatypes.SoftButtonCapabilities;
import com.smartdevicelink.rpc.datatypes.TtsChunk;
import com.smartdevicelink.rpc.datatypes.VehicleType;
import com.smartdevicelink.rpc.datatypes.VrHelpItem;
import com.smartdevicelink.rpc.enums.AppHmiType;
import com.smartdevicelink.rpc.enums.AudioStreamingState;
import com.smartdevicelink.rpc.enums.AudioType;
import com.smartdevicelink.rpc.enums.BitsPerSample;
import com.smartdevicelink.rpc.enums.ButtonName;
import com.smartdevicelink.rpc.enums.DriverDistractionState;
import com.smartdevicelink.rpc.enums.FileType;
import com.smartdevicelink.rpc.enums.GlobalProperty;
import com.smartdevicelink.rpc.enums.HmiLevel;
import com.smartdevicelink.rpc.enums.HmiZoneCapabilities;
import com.smartdevicelink.rpc.enums.ImageType;
import com.smartdevicelink.rpc.enums.InteractionMode;
import com.smartdevicelink.rpc.enums.Language;
import com.smartdevicelink.rpc.enums.PrerecordedSpeech;
import com.smartdevicelink.rpc.enums.RequestType;
import com.smartdevicelink.rpc.enums.Result;
import com.smartdevicelink.rpc.enums.SamplingRate;
import com.smartdevicelink.rpc.enums.SdlConnectionState;
import com.smartdevicelink.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.rpc.enums.SdlInterfaceAvailability;
import com.smartdevicelink.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.rpc.enums.SystemContext;
import com.smartdevicelink.rpc.enums.TextAlignment;
import com.smartdevicelink.rpc.enums.UpdateMode;
import com.smartdevicelink.rpc.enums.VrCapabilities;
import com.smartdevicelink.rpc.notifications.OnAppInterfaceUnregistered;
import com.smartdevicelink.rpc.notifications.OnAudioPassThru;
import com.smartdevicelink.rpc.notifications.OnButtonEvent;
import com.smartdevicelink.rpc.notifications.OnButtonPress;
import com.smartdevicelink.rpc.notifications.OnCommand;
import com.smartdevicelink.rpc.notifications.OnDriverDistraction;
import com.smartdevicelink.rpc.notifications.OnError;
import com.smartdevicelink.rpc.notifications.OnHMIStatus;
import com.smartdevicelink.rpc.notifications.OnHashChange;
import com.smartdevicelink.rpc.notifications.OnKeyboardInput;
import com.smartdevicelink.rpc.notifications.OnLanguageChange;
import com.smartdevicelink.rpc.notifications.OnPermissionsChange;
import com.smartdevicelink.rpc.notifications.OnProxyClosed;
import com.smartdevicelink.rpc.notifications.OnSystemRequest;
import com.smartdevicelink.rpc.notifications.OnTbtClientState;
import com.smartdevicelink.rpc.notifications.OnTouchEvent;
import com.smartdevicelink.rpc.notifications.OnVehicleData;
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
import com.smartdevicelink.rpc.requests.ResetGlobalProperties;
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
import com.smartdevicelink.rpc.requests.SystemRequestResponse;
import com.smartdevicelink.rpc.requests.UnregisterAppInterface;
import com.smartdevicelink.rpc.requests.UnsubscribeButton;
import com.smartdevicelink.rpc.requests.UnsubscribeVehicleData;
import com.smartdevicelink.rpc.responses.AddCommandResponse;
import com.smartdevicelink.rpc.responses.AddSubMenuResponse;
import com.smartdevicelink.rpc.responses.AlertResponse;
import com.smartdevicelink.rpc.responses.ChangeRegistrationResponse;
import com.smartdevicelink.rpc.responses.CreateInteractionChoiceSetResponse;
import com.smartdevicelink.rpc.responses.DeleteCommandResponse;
import com.smartdevicelink.rpc.responses.DeleteFileResponse;
import com.smartdevicelink.rpc.responses.DeleteInteractionChoiceSetResponse;
import com.smartdevicelink.rpc.responses.DeleteSubMenuResponse;
import com.smartdevicelink.rpc.responses.DiagnosticMessageResponse;
import com.smartdevicelink.rpc.responses.DialNumberResponse;
import com.smartdevicelink.rpc.responses.EndAudioPassThruResponse;
import com.smartdevicelink.rpc.responses.GenericResponse;
import com.smartdevicelink.rpc.responses.GetDtcsResponse;
import com.smartdevicelink.rpc.responses.GetVehicleDataResponse;
import com.smartdevicelink.rpc.responses.ListFilesResponse;
import com.smartdevicelink.rpc.responses.PerformAudioPassThruResponse;
import com.smartdevicelink.rpc.responses.PerformInteractionResponse;
import com.smartdevicelink.rpc.responses.PutFileResponse;
import com.smartdevicelink.rpc.responses.ReadDidResponse;
import com.smartdevicelink.rpc.responses.RegisterAppInterfaceResponse;
import com.smartdevicelink.rpc.responses.ResetGlobalPropertiesResponse;
import com.smartdevicelink.rpc.responses.ScrollableMessageResponse;
import com.smartdevicelink.rpc.responses.SendLocationResponse;
import com.smartdevicelink.rpc.responses.SetAppIconResponse;
import com.smartdevicelink.rpc.responses.SetDisplayLayoutResponse;
import com.smartdevicelink.rpc.responses.SetGlobalPropertiesResponse;
import com.smartdevicelink.rpc.responses.SetMediaClockTimerResponse;
import com.smartdevicelink.rpc.responses.ShowResponse;
import com.smartdevicelink.rpc.responses.SliderResponse;
import com.smartdevicelink.rpc.responses.SpeakResponse;
import com.smartdevicelink.rpc.responses.SubscribeButtonResponse;
import com.smartdevicelink.rpc.responses.SubscribeVehicleDataResponse;
import com.smartdevicelink.rpc.responses.SystemRequest;
import com.smartdevicelink.rpc.responses.UnregisterAppInterfaceResponse;
import com.smartdevicelink.rpc.responses.UnsubscribeButtonResponse;
import com.smartdevicelink.rpc.responses.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.streaming.StreamRpcPacketizer;
import com.smartdevicelink.trace.SdlTrace;
import com.smartdevicelink.trace.TraceDeviceInfo;
import com.smartdevicelink.trace.enums.InterfaceActivityDirection;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.SiphonServer;
import com.smartdevicelink.transport.TransportType;
import com.smartdevicelink.util.DebugTool;

public abstract class SdlProxyBase<proxyListenerType extends IProxyListenerBase> {
	// Used for calls to Android Log class.
	public static final String TAG = "SdlProxy";
	private static final String SDL_LIB_TRACE_KEY = "42baba60-eb57-11df-98cf-0800200c9a66";
	private static final int PROX_PROT_VER_ONE = 1;
	
	private SdlSession sdlSession = null;
	private proxyListenerType proxyListener = null;
	
	protected Service appService = null;
	private String sPoliciesUrl = ""; //for testing only

	// Protected Correlation IDs
	private final int 	REGISTER_APP_INTERFACE_CORRELATION_ID = 65529,
						UNREGISTER_APP_INTERFACE_CORRELATION_ID = 65530,
						POLICIES_CORRELATION_ID = 65535;
	
	// Sdlhronization Objects
	private static final Object CONNECTION_REFERENCE_LOCK = new Object(),
								INCOMING_MESSAGE_QUEUE_THREAD_LOCK = new Object(),
								OUTGOING_MESSAGE_QUEUE_THREAD_LOCK = new Object(),
								INTERNAL_MESSAGE_QUEUE_THREAD_LOCK = new Object();
	
	private Object APP_INTERFACE_REGISTERED_LOCK = new Object();
		
	private int iFileCount = 0;

	private boolean navServiceResponseReceived = false;
	private boolean navServiceResponse = false;
	@SuppressWarnings("unused")
    private boolean pcmServiceResponseReceived = false;
	@SuppressWarnings("unused")
    private boolean pcmServiceResponse = false;
	
	// Device Info for logging
	private TraceDeviceInfo traceDeviceInterrogator = null;
		
	// Declare Queuing Threads
	private ProxyMessageDispatcher<ProtocolMessage> incomingProxyMessageDispatcher;
	private ProxyMessageDispatcher<ProtocolMessage> outgoingProxyMessageDispatcher;
	private ProxyMessageDispatcher<InternalProxyMessage> internalProxyMessageDispatcher;
	
	// Flag indicating if callbacks should be called from UIThread
	private Boolean callbackToUiThread = false;
	// UI Handler
	private Handler mainUiHandler = null; 
	final int HEARTBEAT_CORRELATION_ID = 65531;
		
	// SdlProxy Advanced Lifecycle Management
	protected Boolean advancedLifecycleManagementEnabled = false;
	// Parameters passed to the constructor from the app to register an app interface
	private String applicationName = null;
	private long instanceDateTime = System.currentTimeMillis();
	private String sConnectionDetails = "N/A";
	private Vector<TtsChunk> ttsName = null;
	private String ngnMediaScreenAppName = null;
	private Boolean isMediaApp = null;
	private Language sdlLanguageDesired = null;
	private Language hmiDisplayLanguageDesired = null;
	private Vector<AppHmiType> appType = null;
	private String appId = null;
	private String autoActivateIdDesired = null;
	private String lastHashId = null;	
	private SdlMsgVersion sdlMsgVersionRequest = null;
	private Vector<String> vrSynonyms = null;
	private boolean bAppResumeEnabled = false;
	
	/**
	 * Contains current configuration for the transport that was selected during 
	 * construction of this object
	 */
	private BaseTransportConfig transportConfig = null;
	// Proxy State Variables
	protected Boolean appInterfaceRegisterd = false;
	protected Boolean preRegisterd = false;
	@SuppressWarnings("unused")
    private Boolean haveReceivedFirstNonNoneHmiLevel = false;
	protected Boolean haveReceivedFirstFocusLevel = false;
	protected Boolean haveReceivedFirstFocusLevelFull = false;
	protected Boolean proxyDisposed = false;
	protected SdlConnectionState sdlConnectionState = null;
	protected SdlInterfaceAvailability sdlIntefaceAvailablity = null;
	protected HmiLevel hmiLevel = null;
	private HmiLevel priorHmiLevel = null;
	protected AudioStreamingState audioStreamingState = null;
	private AudioStreamingState priorAudioStreamingState = null;
	protected SystemContext systemContext = null;
	// Variables set by RegisterAppInterfaceResponse
	protected SdlMsgVersion sdlMsgVersion = null;
	protected String autoActivateIdReturned = null;
	protected Language sdlLanguage = null;
	protected Language hmiDisplayLanguage = null;
	protected DisplayCapabilities displayCapabilities = null;
	protected List<ButtonCapabilities> buttonCapabilities = null;
	protected List<SoftButtonCapabilities> softButtonCapabilities = null;
	protected PresetBankCapabilities presetBankCapabilities = null;
	protected List<HmiZoneCapabilities> hmiZoneCapabilities = null;
	protected List<SpeechCapabilities> speechCapabilities = null;
	protected List<PrerecordedSpeech> prerecordedSpeech = null;
	protected List<VrCapabilities> vrCapabilities = null;
	protected VehicleType vehicleType = null;
	protected List<AudioPassThruCapabilities> audioPassThruCapabilities = null;
	protected List<Integer> diagModes = null;
	protected Boolean firstTimeFull = true;
	protected String proxyVersionInfo = null;
	protected Boolean bResumeSuccess = false;	
	
	private CopyOnWriteArrayList<IPutFileResponseListener> putFileListenerList = new CopyOnWriteArrayList<IPutFileResponseListener>();
	
	protected byte wiproVersion = 1;
	
	// Interface broker
	private SdlInterfaceBroker interfaceBroker = null;
	
	private void notifyPutFileStreamError(Exception e, String info)
	{
		for (IPutFileResponseListener putFileListener : putFileListenerList) {
			putFileListener.onPutFileStreamError(e, info);
		}		
	}
	
	private void notifyPutFileStreamResponse(PutFileResponse msg)
	{
		for (IPutFileResponseListener putFileListener : putFileListenerList) {
			putFileListener.onPutFileResponse(msg);
		}		
	}
	
	public void addPutFileResponseListener(IPutFileResponseListener putFileListener)
	{
		putFileListenerList.addIfAbsent(putFileListener);
	}

	public void remPutFileResponseListener(IPutFileResponseListener putFileListener)
	{
		putFileListenerList.remove(putFileListener);
	}
	
	// Private Class to Interface with SdlConnection
	private class SdlInterfaceBroker implements ISdlConnectionListener {
		
		@Override
		public void onTransportDisconnected(String info) {
			// proxyOnTransportDisconnect is called to alert the proxy that a requested
			// disconnect has completed
			notifyPutFileStreamError(null, info);
			
			if (advancedLifecycleManagementEnabled) {
				// If ALM, nothing is required to be done here
			} else {
				// If original model, notify app the proxy is closed so it will delete and reinstanciate 
				notifyProxyClosed(info, new SdlException("Transport disconnected.", SdlExceptionCause.SDL_UNAVAILABLE), SdlDisconnectedReason.TRANSPORT_DISCONNECT);
			}
		}

		@Override
		public void onTransportError(String info, Exception e) {
			DebugTool.logError("Transport failure: " + info, e);
			
			notifyPutFileStreamError(e, info);
			
			if (advancedLifecycleManagementEnabled) {			
				// Cycle the proxy
				cycleProxy(SdlDisconnectedReason.TRANSPORT_ERROR);
			} else {
				notifyProxyClosed(info, e, SdlDisconnectedReason.TRANSPORT_ERROR);
			}
		}

		@Override
		public void onProtocolMessageReceived(ProtocolMessage msg) {

            // AudioPathThrough is coming WITH BulkData but WITHOUT JSON Data
            // Policy Snapshot is coming WITH BulkData and WITH JSON Data
            if ((msg.getData() != null && msg.getData().length > 0) ||
                    (msg.getBulkData() != null && msg.getBulkData().length > 0)) {
                queueIncomingMessage(msg);
            }
		}

		@Override
		public void onProtocolSessionStarted(SessionType sessionType,
				byte sessionId, byte version, String correlationId) {
			
			Intent sendIntent = createBroadcastIntent();
			updateBroadcastIntent(sendIntent, "FUNCTION_NAME", "onProtocolSessionStarted");
			updateBroadcastIntent(sendIntent, "COMMENT1", "SessionId: " + sessionId);
			updateBroadcastIntent(sendIntent, "COMMENT2", " SessionType: " + sessionType.getName());
			sendBroadcastIntent(sendIntent);	
			
			setWiProVersion(version);

			 if ( (transportConfig.getHeartBeatTimeout() != Integer.MAX_VALUE) && (version > 2) )
			 {
				 HeartbeatMonitor heartbeatMonitor = new HeartbeatMonitor();
				 heartbeatMonitor.setInterval(transportConfig.getHeartBeatTimeout());
	             sdlSession.setHeartbeatMonitor(heartbeatMonitor);
			 }			
			
			if (sessionType.eq(SessionType.RPC)) {			
				startRpcProtocolSession(sessionId, correlationId);
			} else if (sessionType.eq(SessionType.NAV)) {
				navServiceStarted();
			} else if (wiproVersion > 1) {
				//If version is 2 or above then don't need to specify a Session Type
				startRpcProtocolSession(sessionId, correlationId);
			}  else {
				// Handle other protocol session types here
			}
		}

		@Override
		public void onProtocolSessionNack(SessionType sessionType,
				byte sessionId, byte version, String correlationId) {
			if (sessionType.eq(SessionType.NAV)) {
				navServiceEnded();
			}
		}

		@Override
		public void onProtocolSessionEnded(SessionType sessionType,
				byte sessionId, String correlationId) {
			// How to handle protocol session ended?
				// How should protocol session management occur? 
		}

		@Override
		public void onProtocolError(String info, Exception e) {
			notifyPutFileStreamError(e, info);
			passErrorToProxyListener(info, e);
		}

		@Override
		public void onHeartbeatTimedOut(byte sessionId) {
            final String msg = "Heartbeat timeout";
            DebugTool.logInfo(msg);
            
			Intent sendIntent = createBroadcastIntent();
			updateBroadcastIntent(sendIntent, "FUNCTION_NAME", "onHeartbeatTimedOut");
			updateBroadcastIntent(sendIntent, "COMMENT1", "Heartbeat timeout for SessionID: " + sessionId);
			sendBroadcastIntent(sendIntent);	            
            
            notifyProxyClosed(msg, new SdlException(msg, SdlExceptionCause.HEARTBEAT_PAST_DUE), SdlDisconnectedReason.HB_TIMEOUT);
			
		}
	}
	
	/**
	 * Constructor.
	 * 
	 * @param listener Type of listener for this proxy base.
	 * @param sdlProxyConfigurationResources Configuration resources for this proxy.
	 * @param enableAdvancedLifecycleManagement Flag that ALM should be enabled or not.
	 * @param appName Client application name.
	 * @param ttsName TTS name.
	 * @param ngnMediaScreenAppName Media Screen Application name.
	 * @param vrSynonyms List of synonyms.
	 * @param isMediaApp Flag that indicates that client application if media application or not.
	 * @param sdlMsgVersion Version of Sdl Message.
	 * @param languageDesired Desired language.
	 * @param hmiDisplayLanguageDesired Desired language for HMI. 
	 * @param appType Type of application.
	 * @param appId Application identifier.
	 * @param autoActivateId Auto activation identifier.
	 * @param callbackToUiThread Flag that indicates that this proxy should send callback to UI thread or not.
	 * @param transportConfig Configuration of transport to be used by underlying connection.
	 * @throws SdlException
	 */
	protected SdlProxyBase(proxyListenerType listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			boolean enableAdvancedLifecycleManagement, String appName, Vector<TtsChunk> ttsName, 
			String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, 
			Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHmiType> appType, String appId, 
			String autoActivateId, boolean callbackToUiThread, BaseTransportConfig transportConfig) 
			throws SdlException {
				
			performBaseCommon(listener, sdlProxyConfigurationResources, enableAdvancedLifecycleManagement, appName, ttsName, ngnMediaScreenAppName, vrSynonyms, isMediaApp,
				sdlMsgVersion, languageDesired, hmiDisplayLanguageDesired, appType, appId, autoActivateId, callbackToUiThread, null, null, null, transportConfig);	
	}
	
	private void performBaseCommon(proxyListenerType listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			boolean enableAdvancedLifecycleManagement, String appName, Vector<TtsChunk> ttsName, 
			String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, 
			Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHmiType> appType, String appId, 
			String autoActivateId, boolean callbackToUiThread, Boolean preRegister, String sHashId, Boolean bAppResumeEnab,
			BaseTransportConfig transportConfig) throws SdlException
	{
		setWiProVersion((byte)PROX_PROT_VER_ONE);
		
		if (preRegister != null && preRegister)
		{
			appInterfaceRegisterd = preRegister;
			preRegisterd = preRegister;
		}
		
		if (bAppResumeEnab != null && bAppResumeEnab)
		{
			bAppResumeEnabled = true;
			lastHashId = sHashId;
		}
		interfaceBroker = new SdlInterfaceBroker();
		
		this.callbackToUiThread = callbackToUiThread;
		
		if (callbackToUiThread) {
			mainUiHandler = new Handler(Looper.getMainLooper());
		}
		
		// Set variables for Advanced Lifecycle Management
		advancedLifecycleManagementEnabled = enableAdvancedLifecycleManagement;
		applicationName = appName;
		this.ttsName = ttsName;
		this.ngnMediaScreenAppName = ngnMediaScreenAppName;
		this.isMediaApp = isMediaApp;
		sdlMsgVersionRequest = sdlMsgVersion;
		this.vrSynonyms = vrSynonyms; 
		sdlLanguageDesired = languageDesired;
		this.hmiDisplayLanguageDesired = hmiDisplayLanguageDesired;
		this.appType = appType;
		this.appId = appId;
		autoActivateIdDesired = autoActivateId;
		this.transportConfig = transportConfig;
				
		// Test conditions to invalidate the proxy
		if (listener == null) {
			throw new IllegalArgumentException("IProxyListener listener must be provided to instantiate SdlProxy object.");
		}
		if (advancedLifecycleManagementEnabled) {
		/*	if (_applicationName == null ) {
				throw new IllegalArgumentException("To use SdlProxyALM, an application name, appName, must be provided");
			}
			if (_applicationName.length() < 1 || _applicationName.length() > 100) {
				throw new IllegalArgumentException("A provided application name, appName, must be between 1 and 100 characters in length.");
			}*/
			if (isMediaApp == null) {
				throw new IllegalArgumentException("isMediaApp must not be null when using SdlProxyALM.");
			}
		}
		
		proxyListener = listener;
		
		// Get information from sdlProxyConfigurationResources
		TelephonyManager telephonyManager = null;
		if (sdlProxyConfigurationResources != null) {
			telephonyManager = sdlProxyConfigurationResources.getTelephonyManager();
		} 
		
		// Use the telephonyManager to get and log phone info
		if (telephonyManager != null) {
			// Following is not quite thread-safe (because m_traceLogger could test null twice),
			// so we need to fix this, but vulnerability (i.e. two instances of listener) is
			// likely harmless.
			if (traceDeviceInterrogator == null) {
				traceDeviceInterrogator = new TraceDeviceInfo(sdlProxyConfigurationResources.getTelephonyManager());
			} // end-if
		} // end-if
		
		// Setup Internal ProxyMessage Dispatcher
		synchronized(INTERNAL_MESSAGE_QUEUE_THREAD_LOCK) {
			// Ensure internalProxyMessageDispatcher is null
			if (internalProxyMessageDispatcher != null) {
				internalProxyMessageDispatcher.dispose();
				internalProxyMessageDispatcher = null;
			}
			
			internalProxyMessageDispatcher = new ProxyMessageDispatcher<InternalProxyMessage>("INTERNAL_MESSAGE_DISPATCHER",
					new InternalProxyMessageComparitor(),
					new IDispatchingStrategy<InternalProxyMessage>() {

						@Override
						public void dispatch(InternalProxyMessage message) {
							dispatchInternalMessage((InternalProxyMessage)message);
						}
	
						@Override
						public void handleDispatchingError(String info, Exception ex) {
							handleErrorsFromInternalMessageDispatcher(info, ex);
						}
	
						@Override
						public void handleQueueingError(String info, Exception ex) {
							handleErrorsFromInternalMessageDispatcher(info, ex);
						}			
			});
		}
		
		// Setup Incoming ProxyMessage Dispatcher
		synchronized(INCOMING_MESSAGE_QUEUE_THREAD_LOCK) {
			// Ensure incomingProxyMessageDispatcher is null
			if (incomingProxyMessageDispatcher != null) {
				incomingProxyMessageDispatcher.dispose();
				incomingProxyMessageDispatcher = null;
			}
			
			incomingProxyMessageDispatcher = new ProxyMessageDispatcher<ProtocolMessage>("INCOMING_MESSAGE_DISPATCHER",
					new IncomingProtocolMessageComparitor(),
					new IDispatchingStrategy<ProtocolMessage>() {
						@Override
						public void dispatch(ProtocolMessage message) {
							dispatchIncomingMessage((ProtocolMessage)message);
						}
	
						@Override
						public void handleDispatchingError(String info, Exception ex) {
							handleErrorsFromIncomingMessageDispatcher(info, ex);
						}
	
						@Override
						public void handleQueueingError(String info, Exception ex) {
							handleErrorsFromIncomingMessageDispatcher(info, ex);
						}			
			});
		}
		
		// Setup Outgoing ProxyMessage Dispatcher
		synchronized(OUTGOING_MESSAGE_QUEUE_THREAD_LOCK) {
			// Ensure outgoingProxyMessageDispatcher is null
			if (outgoingProxyMessageDispatcher != null) {
				outgoingProxyMessageDispatcher.dispose();
				outgoingProxyMessageDispatcher = null;
			}
			
			outgoingProxyMessageDispatcher = new ProxyMessageDispatcher<ProtocolMessage>("OUTGOING_MESSAGE_DISPATCHER",
					new OutgoingProtocolMessageComparitor(),
					new IDispatchingStrategy<ProtocolMessage>() {
						@Override
						public void dispatch(ProtocolMessage message) {
							dispatchOutgoingMessage((ProtocolMessage)message);
						}
	
						@Override
						public void handleDispatchingError(String info, Exception ex) {
							handleErrorsFromOutgoingMessageDispatcher(info, ex);
						}
	
						@Override
						public void handleQueueingError(String info, Exception ex) {
							handleErrorsFromOutgoingMessageDispatcher(info, ex);
						}
			});
		}
		
		// Initialize the proxy
		try {
			initializeProxy();
		} catch (SdlException e) {
			// Couldn't initialize the proxy 
			// Dispose threads and then rethrow exception
			
			if (internalProxyMessageDispatcher != null) {
				internalProxyMessageDispatcher.dispose();
				internalProxyMessageDispatcher = null;
			}
			if (incomingProxyMessageDispatcher != null) {
				incomingProxyMessageDispatcher.dispose();
				incomingProxyMessageDispatcher = null;
			}
			if (outgoingProxyMessageDispatcher != null) {
				outgoingProxyMessageDispatcher.dispose();
				outgoingProxyMessageDispatcher = null;
			}
			throw e;
		} 
		
		// Trace that ctor has fired
		SdlTrace.logProxyEvent("SdlProxy Created, instanceID=" + this.toString(), SDL_LIB_TRACE_KEY);		
	}
	
	protected SdlProxyBase(proxyListenerType listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			boolean enableAdvancedLifecycleManagement, String appName, Vector<TtsChunk> ttsName, 
			String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, 
			Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHmiType> appType, String appId, 
			String autoActivateId, boolean callbackToUiThread, boolean preRegister, String sHashId, Boolean bEnableResume, BaseTransportConfig transportConfig) 
			throws SdlException 
	{
			performBaseCommon(listener, sdlProxyConfigurationResources, enableAdvancedLifecycleManagement, appName, ttsName, ngnMediaScreenAppName, vrSynonyms, isMediaApp,
				sdlMsgVersion, languageDesired, hmiDisplayLanguageDesired, appType, appId, autoActivateId, callbackToUiThread, preRegister, sHashId, bEnableResume, transportConfig);
	}
	
	
	
	/**
	 * Constructor.
	 * 
	 * @param listener Type of listener for this proxy base.
	 * @param sdlProxyConfigurationResources Configuration resources for this proxy.
	 * @param enableAdvancedLifecycleManagement Flag that ALM should be enabled or not.
	 * @param appName Client application name.
	 * @param ttsName TTS name.
	 * @param ngnMediaScreenAppName Media Screen Application name.
	 * @param vrSynonyms List of synonyms.
	 * @param isMediaApp Flag that indicates that client application if media application or not.
	 * @param sdlMsgVersion Version of Sdl Message.
	 * @param languageDesired Desired language.
	 * @param hmiDisplayLanguageDesired Desired language for HMI. 
	 * @param appType Type of application.
	 * @param appId Application identifier.
	 * @param autoActivateId Auto activation identifier.
	 * @param callbackToUiThread Flag that indicates that this proxy should send callback to UI thread or not.
	 * @param preRegister Flag that indicates that this proxy should be pre-registerd or not.
	 * @param transportConfig Configuration of transport to be used by underlying connection.
	 * @throws SdlException
	 */	
	protected SdlProxyBase(proxyListenerType listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			boolean enableAdvancedLifecycleManagement, String appName, Vector<TtsChunk> ttsName, 
			String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, 
			Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHmiType> appType, String appId, 
			String autoActivateId, boolean callbackToUiThread, boolean preRegister, BaseTransportConfig transportConfig) 
			throws SdlException 
	{
			performBaseCommon(listener, sdlProxyConfigurationResources, enableAdvancedLifecycleManagement, appName, ttsName, ngnMediaScreenAppName, vrSynonyms, isMediaApp,
				sdlMsgVersion, languageDesired, hmiDisplayLanguageDesired, appType, appId, autoActivateId, callbackToUiThread, preRegister, null, null, transportConfig);
	}

	private Intent createBroadcastIntent()
	{
		Intent sendIntent = new Intent();
		sendIntent.setAction("com.smartdevicelink.broadcast");
		sendIntent.putExtra("APP_NAME", this.applicationName);
		sendIntent.putExtra("APP_ID", this.appId);
		sendIntent.putExtra("RPC_NAME", "");
		sendIntent.putExtra("TYPE", "");
		sendIntent.putExtra("SUCCESS", true);
		sendIntent.putExtra("CORRID", 0);
		sendIntent.putExtra("FUNCTION_NAME", "");
		sendIntent.putExtra("COMMENT1", "");
		sendIntent.putExtra("COMMENT2", "");
		sendIntent.putExtra("COMMENT3", "");
		sendIntent.putExtra("COMMENT4", "");
		sendIntent.putExtra("COMMENT5", "");
		sendIntent.putExtra("COMMENT6", "");
		sendIntent.putExtra("COMMENT7", "");
		sendIntent.putExtra("COMMENT8", "");
		sendIntent.putExtra("COMMENT9", "");
		sendIntent.putExtra("COMMENT10", "");
		sendIntent.putExtra("DATA", "");
		sendIntent.putExtra("SHOW_ON_UI", true);
		return sendIntent;
	}
	private void updateBroadcastIntent(Intent sendIntent, String sKey, String sValue)
	{
		if (sValue == null) sValue = "";
		sendIntent.putExtra(sKey, sValue);		
	}
	private void updateBroadcastIntent(Intent sendIntent, String sKey, boolean bValue)
	{
		sendIntent.putExtra(sKey, bValue);		
	}
	private void updateBroadcastIntent(Intent sendIntent, String sKey, int iValue)
	{
		sendIntent.putExtra(sKey, iValue);		
	}
	
	private void sendBroadcastIntent(Intent sendIntent)
	{
		Service myService = null;		
		if (proxyListener != null && proxyListener instanceof Service)
		{
			myService = (Service) proxyListener;				
		}
		else if (appService != null)
		{
			myService = appService;
		}
		else
		{
			return;
		}
		try
		{
			Context myContext = myService.getApplicationContext();
			if (myContext != null) myContext.sendBroadcast(sendIntent);
		}
		catch(Exception ex)
		{
			//If the service or context has become unavailable unexpectedly, catch the exception and move on -- no broadcast log will occur. 
		}
	}

	private void writeToFile(Object writeME, String fileName) {
		Intent sendIntent = createBroadcastIntent();
		try {
			updateBroadcastIntent(sendIntent,"FUNCTION_NAME", "writeToFile");
			updateBroadcastIntent(sendIntent, "SHOW_ON_UI", false);

			String sFileName = fileName + "_" + iFileCount + ".txt";
			String outFile = Environment.getExternalStorageDirectory().getPath() + "/" + sFileName;	
			File out = new File(outFile);
			FileWriter writer = new FileWriter(out);
			writer.flush();
			writer.write(writeME.toString());
			writer.close();
			updateBroadcastIntent(sendIntent, "COMMENT1", outFile);
		} catch (FileNotFoundException e) {
			updateBroadcastIntent(sendIntent, "COMMENT2", "writeToFile FileNotFoundException " + e);
			Log.i("sdlp", "FileNotFoundException: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			updateBroadcastIntent(sendIntent, "COMMENT2", "writeToFile IOException " + e);
			Log.i("sdlp", "IOException: " + e);
			e.printStackTrace();
		}
		finally
		{
			sendBroadcastIntent(sendIntent);
		}
	}

	private void logHeader(String sType, final String myObject, String sFuncName)
	{
		Intent sendIntent = createBroadcastIntent();

		updateBroadcastIntent(sendIntent, "FUNCTION_NAME", sFuncName);
		
		updateBroadcastIntent(sendIntent, "COMMENT1", sType + "\r\n");
		updateBroadcastIntent(sendIntent, "DATA", myObject);
		sendBroadcastIntent(sendIntent);		
	}

	
	private HttpURLConnection getUrlConnection(Headers myHeader, String sUrlString, int Timeout, int iContentLen)
	{		
		String sContentType = "application/json";
		int connectionTimeout = Timeout * 1000;
		int readTimeout = Timeout * 1000;
		boolean bDoOutput = true;
		boolean bDoInput = true;
		boolean bUsesCaches = false;
		String sRequestMeth = "POST";
		
		boolean bInstFolRed = false;
		String sCharSet = "utf-8";
		int iContentLength = iContentLen;

		URL url = null;
		HttpURLConnection urlConnection = null;
		
		Intent sendIntent = createBroadcastIntent();
		updateBroadcastIntent(sendIntent, "FUNCTION_NAME", "getURLConnection");
		updateBroadcastIntent(sendIntent, "COMMENT1", "Actual Content Length: " + iContentLen);

		if (myHeader != null)
		{		
			//if the header isn't null, use it and replace the hardcoded params
			int iTimeout;
			int iReadTimeout;
			sContentType = myHeader.getContentType();
			iTimeout 		= myHeader.getConnectTimeout();
			bDoOutput	= myHeader.getDoOutput();
			bDoInput	= myHeader.getDoInput();
			bUsesCaches = myHeader.getUseCaches();
			sRequestMeth	= myHeader.getRequestMethod();
			iReadTimeout	= myHeader.getReadTimeout();
			bInstFolRed	= myHeader.getInstanceFollowRedirects();
			sCharSet		= myHeader.getCharset();
			iContentLength	= myHeader.getContentLength();	
			connectionTimeout = iTimeout*1000;
			readTimeout = iReadTimeout*1000; 
			updateBroadcastIntent(sendIntent, "COMMENT2", "\nHeader Defined Content Length: " + iContentLength);
		}
				
		try 
		{
			url = new URL(sUrlString);
			urlConnection = (HttpURLConnection) url.openConnection();				
			urlConnection.setConnectTimeout(connectionTimeout);
			urlConnection.setDoOutput(bDoOutput);
			urlConnection.setDoInput(bDoInput);
			urlConnection.setRequestMethod(sRequestMeth);
			urlConnection.setReadTimeout(readTimeout);						
			urlConnection.setInstanceFollowRedirects(bInstFolRed); 			
			urlConnection.setRequestProperty("Content-Type", sContentType); 
			urlConnection.setRequestProperty("charset", sCharSet);
			urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(iContentLength));
			urlConnection.setUseCaches(bUsesCaches);
			return urlConnection; 
		}
		catch (Exception e)
		{
			return null;
		}
		finally
		{
			sendBroadcastIntent(sendIntent);
		}
	}
	
	
	private void sendOnSystemRequestToUrl(OnSystemRequest msg)
	{		
		Intent sendIntent = createBroadcastIntent();
		Intent sendIntent2 = createBroadcastIntent();

		HttpURLConnection urlConnection = null;
		boolean bLegacy = false;
		
		String sUrlString;
		if (!getPoliciesUrl().equals(""))
			sUrlString = sPoliciesUrl;
		else
			sUrlString = msg.getUrl();

		Integer iTimeout = msg.getTimeout();

		if (iTimeout == null)
			iTimeout = 2000;
		
		Headers myHeader = msg.getHeader();
		String sFunctionName = "SYSTEM_REQUEST";			
		
		updateBroadcastIntent(sendIntent, "FUNCTION_NAME", "sendOnSystemRequestToUrl");		
		updateBroadcastIntent(sendIntent, "COMMENT5", "\r\nCloud URL: " + sUrlString);	
		
		try 
		{
			if (myHeader == null)
				updateBroadcastIntent(sendIntent, "COMMENT7", "\r\nHTTPRequest Header is null");
			
			String sBodyString = msg.getBody();			
			
			JSONObject jsonObjectToSendToServer;
			String validJson;
			
			if (sBodyString == null)
			{				
				List<String> legacyData = msg.getLegacyData();
				JSONArray jsonArrayOfSdlPPackets = new JSONArray(legacyData);
				jsonObjectToSendToServer = new JSONObject();
				jsonObjectToSendToServer.put("data", jsonArrayOfSdlPPackets);
				bLegacy = true;
				sFunctionName = "SYSTEM_REQUEST_LEGACY";
				updateBroadcastIntent(sendIntent, "COMMENT6", "\r\nLegacy SystemRequest: true");
				validJson = jsonObjectToSendToServer.toString().replace("\\", "");
			}
 			else
 			{		
				Intent sendIntent3 = createBroadcastIntent();
				updateBroadcastIntent(sendIntent3, "FUNCTION_NAME", "replace");
				updateBroadcastIntent(sendIntent3, "COMMENT1", "Valid Json length before replace: " + sBodyString.getBytes("UTF-8").length);				
				sendBroadcastIntent(sendIntent3);
 				validJson = sBodyString.replace("\\", "");
 			}
												
			writeToFile(validJson, "requestToCloud");
			logHeader("Cloud Request", validJson, sFunctionName);
			
			urlConnection = getUrlConnection(myHeader, sUrlString, iTimeout, validJson.getBytes("UTF-8").length);
			
			if (urlConnection == null)
			{
	            Log.i(TAG, "urlConnection is null, check RPC input parameters");
	            updateBroadcastIntent(sendIntent, "COMMENT2", "urlConnection is null, check RPC input parameters");
	            return;
			}

			DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
			wr.writeBytes(validJson);
			wr.flush();
			wr.close();
			
			
			long BeforeTime = System.currentTimeMillis();			
			@SuppressWarnings("unused")
            String sResponseMsg = urlConnection.getResponseMessage();			
			long AfterTime = System.currentTimeMillis();
			final long roundtriptime = AfterTime - BeforeTime;
			
			updateBroadcastIntent(sendIntent, "COMMENT4", " Round trip time: " + roundtriptime);
			updateBroadcastIntent(sendIntent, "COMMENT1", "Received response from cloud, response code=" + urlConnection.getResponseCode() + " ");		
			
			int iResponseCode = urlConnection.getResponseCode();
			
			if (iResponseCode != HttpURLConnection.HTTP_OK)
			{
	            Log.i(TAG, "Response code not HTTP_OK, returning from sendOnSystemRequestToUrl.");
	            updateBroadcastIntent(sendIntent, "COMMENT2", "Response code not HTTP_OK, aborting request. ");
	            return;
	        }
			
			InputStream is = urlConnection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    String line;
		    StringBuffer response = new StringBuffer(); 
		    while((line = rd.readLine()) != null) 
		    {
		        response.append(line);
		        response.append('\r');
			}
		    rd.close();
		    Log.i(TAG, "response: " + response.toString());			    		    
		    
		    writeToFile(response.toString(), "responseFromCloud");

		    logHeader("Cloud Response", response.toString(), sFunctionName);

			Vector<String> cloudDataReceived = new Vector<String>();			
				
			// Convert the response to JSON
			JSONObject jsonResponse = new JSONObject(response.toString());				
			if (jsonResponse.get("data") instanceof JSONArray) 
			{
				JSONArray jsonArray = jsonResponse.getJSONArray("data");
				for (int i=0; i<jsonArray.length(); i++) 
				{
					if (jsonArray.get(i) instanceof String) 
					{
						cloudDataReceived.add(jsonArray.getString(i));
						//Log.i("sendOnSystemRequestToUrl", "jsonArray.getString(i): " + jsonArray.getString(i));
					}
				}
			} 
			else if (jsonResponse.get("data") instanceof String) 
			{
				cloudDataReceived.add(jsonResponse.getString("data"));
				//Log.i("sendOnSystemRequestToUrl", "jsonResponse.getString(data): " + jsonResponse.getString("data"));
			} 
			else 
			{
				DebugTool.logError("sendOnSystemRequestToUrl: Data in JSON Object neither an array nor a string.");
				//Log.i("sendOnSystemRequestToUrl", "sendOnSystemRequestToUrl: Data in JSON Object neither an array nor a string.");
				return;
			}
				
			String sResponse = cloudDataReceived.toString();
				
			if (sResponse.length() > 512)
			{
				sResponse = sResponse.substring(0, 511);
			}
								
			updateBroadcastIntent(sendIntent, "DATA", "Data from cloud response: " + sResponse);
				
			// Send new SystemRequest to SDL
			SystemRequest mySystemRequest;
			
			if (bLegacy)
				mySystemRequest = RpcRequestFactory.buildSystemRequestLegacy(cloudDataReceived, getPoliciesReservedCorrelationId());
			else
				mySystemRequest = RpcRequestFactory.buildSystemRequest(response.toString(), getPoliciesReservedCorrelationId());
			   
			if (getIsConnected()) 
			{			    	
				sendRpcRequestPrivate(mySystemRequest);
				Log.i("sendOnSystemRequestToUrl", "sent to sdl");											
										
				updateBroadcastIntent(sendIntent2, "RPC_NAME", FunctionId.SYSTEM_REQUEST.toString());
				updateBroadcastIntent(sendIntent2, "TYPE", RpcMessage.KEY_REQUEST);
				updateBroadcastIntent(sendIntent2, "CORRID", mySystemRequest.getCorrelationId());
			}
		}
		catch (SdlException e) 
		{
			DebugTool.logError("sendOnSystemRequestToUrl: Could not get data from JSONObject received.", e);
			updateBroadcastIntent(sendIntent, "COMMENT3", " SdlException encountered sendOnSystemRequestToUrl: "+ e);
			//Log.i("pt", "sendOnSystemRequestToUrl: Could not get data from JSONObject received."+ e);
		} 
		catch (JSONException e) 
		{
			DebugTool.logError("sendOnSystemRequestToUrl: JSONException: ", e);
			updateBroadcastIntent(sendIntent, "COMMENT3", " JSONException encountered sendOnSystemRequestToUrl: "+ e);
			//Log.i("pt", "sendOnSystemRequestToUrl: JSONException: "+ e);
		} 
		catch (UnsupportedEncodingException e) 
		{
			DebugTool.logError("sendOnSystemRequestToUrl: Could not encode string.", e);
			updateBroadcastIntent(sendIntent, "COMMENT3", " UnsupportedEncodingException encountered sendOnSystemRequestToUrl: "+ e);
			//Log.i("pt", "sendOnSystemRequestToUrl: Could not encode string."+ e);
		} 
		catch (ProtocolException e) 
		{
			DebugTool.logError("sendOnSystemRequestToUrl: Could not set request method to post.", e);
			updateBroadcastIntent(sendIntent, "COMMENT3", " ProtocolException encountered sendOnSystemRequestToUrl: "+ e);
			//Log.i("pt", "sendOnSystemRequestToUrl: Could not set request method to post."+ e);
		} 
		catch (MalformedURLException e) 
		{
			DebugTool.logError("sendOnSystemRequestToUrl: URL Exception when sending SystemRequest to an external server.", e);
			updateBroadcastIntent(sendIntent, "COMMENT3", " MalformedURLException encountered sendOnSystemRequestToUrl: "+ e);
			//Log.i("pt", "sendOnSystemRequestToUrl: URL Exception when sending SystemRequest to an external server."+ e);
		} 
		catch (IOException e) 
		{
			DebugTool.logError("sendOnSystemRequestToUrl: IOException: ", e);
			updateBroadcastIntent(sendIntent, "COMMENT3", " IOException while sending to cloud: IOException: "+ e);
			//Log.i("pt", "sendOnSystemRequestToUrl: IOException: "+ e);
		} 
		catch (Exception e) 
		{
			DebugTool.logError("sendOnSystemRequestToUrl: Unexpected Exception: ", e);
			updateBroadcastIntent(sendIntent, "COMMENT3", " Exception encountered sendOnSystemRequestToUrl: "+ e);
			//Log.i("pt", "sendOnSystemRequestToUrl: Unexpected Exception: " + e);
		}
		finally
		{
			sendBroadcastIntent(sendIntent);
			sendBroadcastIntent(sendIntent2);

			if (iFileCount < 10)
				iFileCount++;
			else
				iFileCount = 0;

			if(urlConnection != null) 
			{
				urlConnection.disconnect(); 
			}
		}
	}

	private int getPoliciesReservedCorrelationId() {
		return POLICIES_CORRELATION_ID;
	}
	
	// Test correlationID
	private boolean isCorrelationIdProtected(Integer correlationId) {
		if (correlationId != null && 
						(HEARTBEAT_CORRELATION_ID == correlationId
						|| REGISTER_APP_INTERFACE_CORRELATION_ID == correlationId
						|| UNREGISTER_APP_INTERFACE_CORRELATION_ID == correlationId
						|| POLICIES_CORRELATION_ID == correlationId)) {
			return true;
		}
		
		return false;
	}
	
	// Protected isConnected method to allow legacy proxy to poll isConnected state
	public Boolean getIsConnected() {
		if (sdlSession == null) return false;
		
		return sdlSession.getIsConnected();
	}
	
	/**
	 * Returns whether the application is registered in SDL. Note: for testing
	 * purposes, it's possible that the connection is established, but the
	 * application is not registered.
	 * 
	 * @return true if the application is registered in SDL
	 */
	public Boolean getAppInterfaceRegistered() {
		return appInterfaceRegisterd;
	}

	// Function to initialize new proxy connection
	private void initializeProxy() throws SdlException {		
		// Reset all of the flags and state variables
		haveReceivedFirstNonNoneHmiLevel = false;
		haveReceivedFirstFocusLevel = false;
		haveReceivedFirstFocusLevelFull = false;
		if (preRegisterd) 
			appInterfaceRegisterd = true;
		else
			appInterfaceRegisterd = false;
		
		putFileListenerList.clear();
		
		sdlIntefaceAvailablity = SdlInterfaceAvailability.SDL_INTERFACE_UNAVAILABLE;
				
		// Setup SdlConnection
		synchronized(CONNECTION_REFERENCE_LOCK) {
			this.sdlSession = SdlSession.createSession(wiproVersion,interfaceBroker, transportConfig);	
		}
		
		synchronized(CONNECTION_REFERENCE_LOCK) {
			this.sdlSession.startSession();
				sendTransportBroadcast();
			}
	}
	
	public void sendTransportBroadcast()
	{
		if (sdlSession == null || transportConfig == null) return;
		
		String sTransComment = sdlSession.getBroadcastComment(transportConfig);
		
		if (sTransComment == null || sTransComment.equals("")) return;
		
		Intent sendIntent = createBroadcastIntent();
		updateBroadcastIntent(sendIntent, "FUNCTION_NAME", "initializeProxy");
		updateBroadcastIntent(sendIntent, "COMMENT1", sTransComment);
		sendBroadcastIntent(sendIntent);		
	}
	
	
	/**
	 *  Public method to enable the siphon transport
	 */
	public void enableSiphonDebug() {

		short enabledPortNumber = SiphonServer.enableSiphonServer();
		String sSiphonPortNumber = "Enabled Siphon Port Number: " + enabledPortNumber;
		Intent sendIntent = createBroadcastIntent();
		updateBroadcastIntent(sendIntent, "FUNCTION_NAME", "enableSiphonDebug");
		updateBroadcastIntent(sendIntent, "COMMENT1", sSiphonPortNumber);
		sendBroadcastIntent(sendIntent);
	}


	
	/**
	 *  Public method to disable the Siphon Trace Server
	 */
	public void disableSiphonDebug() {

		short disabledPortNumber = SiphonServer.disableSiphonServer();
		if (disabledPortNumber != -1) {
		    String sSiphonPortNumber = "Disabled Siphon Port Number: " + disabledPortNumber;
		    Intent sendIntent = createBroadcastIntent();
		    updateBroadcastIntent(sendIntent, "FUNCTION_NAME", "disableSiphonDebug");
		    updateBroadcastIntent(sendIntent, "COMMENT1", sSiphonPortNumber);
		    sendBroadcastIntent(sendIntent);
		}
	}

	
	
	/**
	 *  Public method to enable the Debug Tool
	 */
	public static void enableDebugTool() {
		DebugTool.enableDebugTool();
	}
	
	/**
	 *  Public method to disable the Debug Tool
	 */
	public static void disableDebugTool() {
		DebugTool.disableDebugTool();
	}	

	/**
	* Public method to determine Debug Tool enabled
	*/
	public static boolean isDebugEnabled() {
		return DebugTool.isDebugEnabled();
	}	
	
	
	@Deprecated
	public void close() throws SdlException {
		dispose();
	}
	
	private void cleanProxy(SdlDisconnectedReason disconnectedReason) throws SdlException {
		try {
			
			// ALM Specific Cleanup
			if (advancedLifecycleManagementEnabled) {
				sdlConnectionState = SdlConnectionState.SDL_DISCONNECTED;
				
				firstTimeFull = true;
			
				// Should we wait for the interface to be unregistered?
				Boolean waitForInterfaceUnregistered = false;
				// Unregister app interface
				synchronized(CONNECTION_REFERENCE_LOCK) {
					if (sdlSession != null && sdlSession.getIsConnected() && getAppInterfaceRegistered()) {
						waitForInterfaceUnregistered = true;
						unregisterAppInterfacePrivate(UNREGISTER_APP_INTERFACE_CORRELATION_ID);
					}
				}
				
				// Wait for the app interface to be unregistered
				if (waitForInterfaceUnregistered) {
					synchronized(APP_INTERFACE_REGISTERED_LOCK) {
						try {
							APP_INTERFACE_REGISTERED_LOCK.wait(3000);
						} catch (InterruptedException e) {
							// Do nothing
						}
					}
				}
			}
			
			// Clean up SDL Connection
			synchronized(CONNECTION_REFERENCE_LOCK) {
				if (sdlSession != null) sdlSession.close();
			}		
		} catch (SdlException e) {
			throw e;
		} finally {
			SdlTrace.logProxyEvent("SdlProxy cleaned.", SDL_LIB_TRACE_KEY);
		}
	}
	
	/**
	 * Terminates the App's Interface Registration, closes the transport connection, ends the protocol session, and frees any resources used by the proxy.
	 */
	public void dispose() throws SdlException
	{		
		if (proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		proxyDisposed = true;
		
		SdlTrace.logProxyEvent("Application called dispose() method.", SDL_LIB_TRACE_KEY);
		
		try{
			// Clean the proxy
			cleanProxy(SdlDisconnectedReason.APPLICATION_REQUESTED_DISCONNECT);
		
			// Close IncomingProxyMessageDispatcher thread
			synchronized(INCOMING_MESSAGE_QUEUE_THREAD_LOCK) {
				if (incomingProxyMessageDispatcher != null) {
					incomingProxyMessageDispatcher.dispose();
					incomingProxyMessageDispatcher = null;
				}
			}
			
			// Close OutgoingProxyMessageDispatcher thread
			synchronized(OUTGOING_MESSAGE_QUEUE_THREAD_LOCK) {
				if (outgoingProxyMessageDispatcher != null) {
					outgoingProxyMessageDispatcher.dispose();
					outgoingProxyMessageDispatcher = null;
				}
			}
			
			// Close InternalProxyMessageDispatcher thread
			synchronized(INTERNAL_MESSAGE_QUEUE_THREAD_LOCK) {
				if (internalProxyMessageDispatcher != null) {
					internalProxyMessageDispatcher.dispose();
					internalProxyMessageDispatcher = null;
				}
			}
			
			traceDeviceInterrogator = null;
		} catch (SdlException e) {
			throw e;
		} finally {
			SdlTrace.logProxyEvent("SdlProxy disposed.", SDL_LIB_TRACE_KEY);
		}
	} // end-method

	
	private static Object cycleLock = new Object();
	
	private boolean cycling = false;
	
	// Method to cycle the proxy, only called in ALM
	protected void cycleProxy(SdlDisconnectedReason disconnectedReason) {		
		if (cycling) return;
		
		synchronized(cycleLock)
		{
		try{			
				cycling = true;
				cleanProxy(disconnectedReason);
				initializeProxy();
				notifyProxyClosed("Sdl Proxy Cycled", new SdlException("Sdl Proxy Cycled", SdlExceptionCause.SDL_PROXY_CYCLED), disconnectedReason);							
			}
		 catch (SdlException e) {
			Intent sendIntent = createBroadcastIntent();
			updateBroadcastIntent(sendIntent, "FUNCTION_NAME", "cycleProxy");
			updateBroadcastIntent(sendIntent, "COMMENT1", "Proxy cycled, exception cause: " + e.getSdlExceptionCause());
			sendBroadcastIntent(sendIntent);

			switch(e.getSdlExceptionCause()) {
				case BLUETOOTH_DISABLED:
					notifyProxyClosed("Bluetooth is disabled. Bluetooth must be enabled to connect to SDL. Reattempt a connection once Bluetooth is enabled.", 
							new SdlException("Bluetooth is disabled. Bluetooth must be enabled to connect to SDL. Reattempt a connection once Bluetooth is enabled.", SdlExceptionCause.BLUETOOTH_DISABLED), SdlDisconnectedReason.BLUETOOTH_DISABLED);
					break;
				case BLUETOOTH_ADAPTER_NULL:
					notifyProxyClosed("Cannot locate a Bluetooth adapater. A SDL connection is impossible on this device until a Bluetooth adapter is added.", 
							new SdlException("Cannot locate a Bluetooth adapater. A SDL connection is impossible on this device until a Bluetooth adapter is added.", SdlExceptionCause.BLUETOOTH_ADAPTER_NULL), SdlDisconnectedReason.BLUETOOTH_ADAPTER_ERROR);
					break;
				default :
					notifyProxyClosed("Cycling the proxy failed.", e, SdlDisconnectedReason.GENERIC_ERROR);
					break;
			}
		} catch (Exception e) { 
			notifyProxyClosed("Cycling the proxy failed.", e, SdlDisconnectedReason.GENERIC_ERROR);
		}
			cycling = false;
		}
	}

	
	
	/************* Functions used by the Message Dispatching Queues ****************/
	private void dispatchIncomingMessage(ProtocolMessage message) {
		try{
			// Dispatching logic
			if (message.getSessionType().equals(SessionType.RPC)) {
				try {
					if (wiproVersion == 1) {
						if (message.getVersion() > 1) setWiProVersion(message.getVersion());
					}
					
					Hashtable<String, Object> hash = new Hashtable<String, Object>();
					if (wiproVersion > 1) {
						Hashtable<String, Object> hashTemp = new Hashtable<String, Object>();
						hashTemp.put(RpcMessage.KEY_CORRELATION_ID, message.getCorrId());
						if (message.getJsonSize() > 0) {
							final Hashtable<String, Object> mhash = JsonRpcMarshaller.unmarshall(message.getData());
							//hashTemp.put(Names.parameters, mhash.get(Names.parameters));
							hashTemp.put(RpcMessage.KEY_PARAMETERS, mhash);
						}

						String functionName = FunctionId.getFunctionName(message.getFunctionId());
						if (functionName != null) {
							hashTemp.put(RpcMessage.KEY_FUNCTION_NAME, functionName);
						} else {
							DebugTool.logWarning("Dispatch Incoming Message - function name is null unknown RPC.  FunctionId: " + message.getFunctionId());
							return;
						}
						if (message.getRpcType() == 0x00) {
							hash.put(RpcMessage.KEY_REQUEST, hashTemp);
						} else if (message.getRpcType() == 0x01) {
							hash.put(RpcMessage.KEY_RESPONSE, hashTemp);
						} else if (message.getRpcType() == 0x02) {
							hash.put(RpcMessage.KEY_NOTIFICATION, hashTemp);
						}
						if (message.getBulkData() != null) hash.put(RpcStruct.KEY_BULK_DATA, message.getBulkData());
					} else {
						final Hashtable<String, Object> mhash = JsonRpcMarshaller.unmarshall(message.getData());
						hash = mhash;
					}
					handleRpcMessage(hash);							
				} catch (final Exception excp) {
					DebugTool.logError("Failure handling protocol message: " + excp.toString(), excp);
					passErrorToProxyListener("Error handing incoming protocol message.", excp);
				} // end-catch
			} else {
				// Handle other protocol message types here
			}
		} catch (final Exception e) {
			// Pass error to application through listener 
			DebugTool.logError("Error handing proxy event.", e);
			passErrorToProxyListener("Error handing incoming protocol message.", e);
		}
	}
	
	private byte getWiProVersion() {
		return this.wiproVersion;
	}
	
	private void setWiProVersion(byte version) {
		this.wiproVersion = version;
	}
	
	public String serializeJson(RpcMessage msg)
	{
		String sReturn = null;		
		try
		{
			sReturn = msg.serializeJson(getWiProVersion()).toString(2);
		}
		catch (final Exception e) 
		{
			DebugTool.logError("Error handing proxy event.", e);
			passErrorToProxyListener("Error serializing message.", e);
			return null;
		}
		return sReturn;
	}

	private void handleErrorsFromIncomingMessageDispatcher(String info, Exception e) {
		passErrorToProxyListener(info, e);
	}
	
	private void dispatchOutgoingMessage(ProtocolMessage message) {
		synchronized(CONNECTION_REFERENCE_LOCK) {
			if (sdlSession != null) {
				sdlSession.sendMessage(message);
			}
		}		
		SdlTrace.logProxyEvent("SdlProxy sending Protocol Message: " + message.toString(), SDL_LIB_TRACE_KEY);
	}
	
	private void handleErrorsFromOutgoingMessageDispatcher(String info, Exception e) {
		passErrorToProxyListener(info, e);
	}
	
	void dispatchInternalMessage(final InternalProxyMessage message) {
		try{
			if (message.getFunctionName().equals(InternalProxyMessage.ON_PROXY_ERROR)) {
				final OnError msg = (OnError)message;			
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onError(msg.getInfo(), msg.getException());
						}
					});
				} else {
					proxyListener.onError(msg.getInfo(), msg.getException());
				}
			/**************Start Legacy Specific Call-backs************/
			} else if (message.getFunctionName().equals(InternalProxyMessage.ON_PROXY_OPENED)) {
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							((IProxyListener)proxyListener).onProxyOpened();
						}
					});
				} else {
					((IProxyListener)proxyListener).onProxyOpened();
				}
			} else if (message.getFunctionName().equals(InternalProxyMessage.ON_PROXY_CLOSED)) {
				final OnProxyClosed msg = (OnProxyClosed)message;
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onProxyClosed(msg.getInfo(), msg.getException(), msg.getReason());
						}
					});
				} else {
					proxyListener.onProxyClosed(msg.getInfo(), msg.getException(), msg.getReason());
				}
			/****************End Legacy Specific Call-backs************/
			} else {
				// Diagnostics
				SdlTrace.logProxyEvent("Unknown RPC Message encountered. Check for an updated version of the SDL Proxy.", SDL_LIB_TRACE_KEY);
				DebugTool.logError("Unknown RPC Message encountered. Check for an updated version of the SDL Proxy.");
			}
			
		SdlTrace.logProxyEvent("Proxy fired callback: " + message.getFunctionName(), SDL_LIB_TRACE_KEY);
		} catch(final Exception e) {
			// Pass error to application through listener 
			DebugTool.logError("Error handing proxy event.", e);
			if (callbackToUiThread) {
				// Run in UI thread
				mainUiHandler.post(new Runnable() {
					@Override
					public void run() {
						proxyListener.onError("Error handing proxy event.", e);
					}
				});
			} else {
				proxyListener.onError("Error handing proxy event.", e);
			}
		}
	}
	
	private void handleErrorsFromInternalMessageDispatcher(String info, Exception e) {
		DebugTool.logError(info, e);
		// This error cannot be passed to the user, as it indicates an error
		// in the communication between the proxy and the application.
		
		DebugTool.logError("InternalMessageDispatcher failed.", e);
		
		// Note, this is the only place where the _proxyListener should be referenced asdlhronously,
		// with an error on the internalMessageDispatcher, we have no other reliable way of 
		// communicating with the application.
		notifyProxyClosed("Proxy callback dispatcher is down. Proxy instance is invalid.", e, SdlDisconnectedReason.GENERIC_ERROR);
		proxyListener.onError("Proxy callback dispatcher is down. Proxy instance is invalid.", e);
	}
	/************* END Functions used by the Message Dispatching Queues ****************/
	
	// Private sendPRCRequest method. All RPCRequests are funneled through this method after
		// error checking. 
	private void sendRpcRequestPrivate(RpcRequest request) throws SdlException {
			try {
			SdlTrace.logRpcEvent(InterfaceActivityDirection.Transmit, request, SDL_LIB_TRACE_KEY);
						
			byte[] msgBytes = JsonRpcMarshaller.marshall(request, wiproVersion);
	
			ProtocolMessage pm = new ProtocolMessage();
			pm.setData(msgBytes);
			if (sdlSession != null)
				pm.setSessionId(sdlSession.getSessionId());
			pm.setMessageType(MessageType.RPC);
			pm.setSessionType(SessionType.RPC);
			pm.setFunctionId(FunctionId.getFunctionId(request.getFunctionName()));
			if (request.getCorrelationId() == null)
			{
				//Log error here
				throw new SdlException("CorrelationID cannot be null. RPC: " + request.getFunctionName(), SdlExceptionCause.INVALID_ARGUMENT);
			}
			pm.setCorrId(request.getCorrelationId());
			if (request.getBulkData() != null) 
				pm.setBulkData(request.getBulkData());
			
			// Queue this outgoing message
			synchronized(OUTGOING_MESSAGE_QUEUE_THREAD_LOCK) {
				if (outgoingProxyMessageDispatcher != null) {
					outgoingProxyMessageDispatcher.queueMessage(pm);
				}
			}
		} catch (OutOfMemoryError e) {
			SdlTrace.logProxyEvent("OutOfMemory exception while sending request " + request.getFunctionName(), SDL_LIB_TRACE_KEY);
			throw new SdlException("OutOfMemory exception while sending request " + request.getFunctionName(), e, SdlExceptionCause.INVALID_ARGUMENT);
		}
	}
	
	private void handleRpcMessage(Hashtable<String, Object> hash) {
		RpcMessage rpcMsg = new RpcMessage(hash);
		String functionName = rpcMsg.getFunctionName();
		String messageType = rpcMsg.getMessageType();
		
		if (messageType.equals(RpcMessage.KEY_RESPONSE)) {			
			SdlTrace.logRpcEvent(InterfaceActivityDirection.Receive, new RpcResponse(rpcMsg), SDL_LIB_TRACE_KEY);

			// Check to ensure response is not from an internal message (reserved correlation ID)
			if (isCorrelationIdProtected((new RpcResponse(hash)).getCorrelationId())) {
				// This is a response generated from an internal message, it can be trapped here
				// The app should not receive a response for a request it did not send
				if ((new RpcResponse(hash)).getCorrelationId() == REGISTER_APP_INTERFACE_CORRELATION_ID 
						&& advancedLifecycleManagementEnabled 
						&& functionName.equals(FunctionId.REGISTER_APP_INTERFACE.toString())) {
					final RegisterAppInterfaceResponse msg = new RegisterAppInterfaceResponse(hash);
					if (msg.getSuccess()) {
						appInterfaceRegisterd = true;
					}
					
					Intent sendIntent = createBroadcastIntent();
					updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionId.REGISTER_APP_INTERFACE.toString());
					updateBroadcastIntent(sendIntent, "TYPE", RpcMessage.KEY_RESPONSE);
					updateBroadcastIntent(sendIntent, "SUCCESS", msg.getSuccess());
					updateBroadcastIntent(sendIntent, "COMMENT1", msg.getInfo());
					updateBroadcastIntent(sendIntent, "COMMENT2", msg.getResultCode().toString());
					updateBroadcastIntent(sendIntent, "DATA",serializeJson(msg));
					updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationId());
					sendBroadcastIntent(sendIntent);
					
					//_autoActivateIdReturned = msg.getAutoActivateID();
					/*Place holder for legacy support*/ autoActivateIdReturned = "8675309";
					buttonCapabilities = msg.getButtonCapabilities();
					displayCapabilities = msg.getDisplayCapabilities();
					softButtonCapabilities = msg.getSoftButtonCapabilities();
					presetBankCapabilities = msg.getPresetBankCapabilities();
					hmiZoneCapabilities = msg.getHmiZoneCapabilities();
					speechCapabilities = msg.getSpeechCapabilities();
					prerecordedSpeech = msg.getPrerecordedSpeech();
					sdlLanguage = msg.getLanguage();
					hmiDisplayLanguage = msg.getHmiDisplayLanguage();
					sdlMsgVersion = msg.getSdlMsgVersion();
					vrCapabilities = msg.getVrCapabilities();
					vehicleType = msg.getVehicleType();
					audioPassThruCapabilities = msg.getAudioPassThruCapabilities();
					proxyVersionInfo = msg.getProxyVersionInfo();																			

					if (bAppResumeEnabled)
					{
						if ( (msg.getResultCode() == Result.RESUME_FAILED) || (msg.getResultCode() != Result.SUCCESS) )
						{
							bResumeSuccess = false;
							lastHashId = null;
						}
						else if ( (sdlMsgVersion.getMajorVersion() > 2) && (lastHashId != null) && (msg.getResultCode() == Result.SUCCESS) )
							bResumeSuccess = true;				
					}
					diagModes = msg.getSupportedDiagModes();
					
					String sVersionInfo = "SDL Proxy Version: " + proxyVersionInfo;
													
					if (!isDebugEnabled()) 
					{
						enableDebugTool();
						DebugTool.logInfo(sVersionInfo, false);
						disableDebugTool();
					}					
					else
						DebugTool.logInfo(sVersionInfo, false);
					
					sendIntent = createBroadcastIntent();
					updateBroadcastIntent(sendIntent, "FUNCTION_NAME", "RAI_RESPONSE");
					updateBroadcastIntent(sendIntent, "COMMENT1", sVersionInfo);
					sendBroadcastIntent(sendIntent);
					
					// Send onSdlConnected message in ALM
					sdlConnectionState = SdlConnectionState.SDL_CONNECTED;
					
					// If registerAppInterface failed, exit with OnProxyUnusable
					if (!msg.getSuccess()) {
						notifyProxyClosed("Unable to register app interface. Review values passed to the SdlProxy constructor. RegisterAppInterface result code: ", 
								new SdlException("Unable to register app interface. Review values passed to the SdlProxy constructor. RegisterAppInterface result code: " + msg.getResultCode(), SdlExceptionCause.SDL_REGISTRATION_ERROR), SdlDisconnectedReason.SDL_REGISTRATION_ERROR);
					}
					
					if (callbackToUiThread) {
						// Run in UI thread
						mainUiHandler.post(new Runnable() {
							@Override
							public void run() {
								if (proxyListener instanceof IProxyListener) {
									((IProxyListener)proxyListener).onRegisterAppInterfaceResponse(msg);
								} else if (proxyListener instanceof IProxyListenerAlm) {
									//((IProxyListenerALM)_proxyListener).onRegisterAppInterfaceResponse(msg);
								}
							}
						});
					} else {
						if (proxyListener instanceof IProxyListener) {
							((IProxyListener)proxyListener).onRegisterAppInterfaceResponse(msg);
						} else if (proxyListener instanceof IProxyListenerAlm) {
							//((IProxyListenerALM)_proxyListener).onRegisterAppInterfaceResponse(msg);
						}
					}
				} else if ((new RpcResponse(hash)).getCorrelationId() == POLICIES_CORRELATION_ID 
						&& functionName.equals(FunctionId.ON_ENCODED_SYNC_P_DATA.toString())) {
						
					Log.i("pt", "POLICIES_CORRELATION_ID SystemRequest Notification (Legacy)");
					
					final OnSystemRequest msg = new OnSystemRequest(hash);
					
					// If url is not null, then send to URL
					if ( (msg.getUrl() != null) )
					{
						// URL has data, attempt to post request to external server
						Thread handleOffboardTransmissionThread = new Thread() {
							@Override
							public void run() {
								sendOnSystemRequestToUrl(msg);
							}
						};

						handleOffboardTransmissionThread.start();
					}					
				}
				else if ((new RpcResponse(hash)).getCorrelationId() == POLICIES_CORRELATION_ID 
						&& functionName.equals(FunctionId.ENCODED_SYNC_P_DATA.toString())) {

					Log.i("pt", "POLICIES_CORRELATION_ID SystemRequest Response (Legacy)");
					final SystemRequestResponse msg = new SystemRequestResponse(hash);
					
					Intent sendIntent = createBroadcastIntent();
					updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionId.SYSTEM_REQUEST.toString());
					updateBroadcastIntent(sendIntent, "TYPE", RpcMessage.KEY_RESPONSE);
					updateBroadcastIntent(sendIntent, "SUCCESS", msg.getSuccess());
					updateBroadcastIntent(sendIntent, "COMMENT1", msg.getInfo());
					updateBroadcastIntent(sendIntent, "COMMENT2", msg.getResultCode().toString());
					updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationId());
					sendBroadcastIntent(sendIntent);
				}
				else if ((new RpcResponse(hash)).getCorrelationId() == POLICIES_CORRELATION_ID 
						&& functionName.equals(FunctionId.SYSTEM_REQUEST.toString())) {
					final SystemRequestResponse msg = new SystemRequestResponse(hash);
					
					Intent sendIntent = createBroadcastIntent();
					updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionId.SYSTEM_REQUEST.toString());
					updateBroadcastIntent(sendIntent, "TYPE", RpcMessage.KEY_RESPONSE);
					updateBroadcastIntent(sendIntent, "SUCCESS", msg.getSuccess());
					updateBroadcastIntent(sendIntent, "COMMENT1", msg.getInfo());
					updateBroadcastIntent(sendIntent, "COMMENT2", msg.getResultCode().toString());
					updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationId());
					updateBroadcastIntent(sendIntent, "DATA", serializeJson(msg));
					sendBroadcastIntent(sendIntent);
				}
				else if (functionName.equals(FunctionId.UNREGISTER_APP_INTERFACE.toString())) {
						// UnregisterAppInterface					
						appInterfaceRegisterd = false;
						synchronized(APP_INTERFACE_REGISTERED_LOCK) {
							APP_INTERFACE_REGISTERED_LOCK.notify();
						}
						final UnregisterAppInterfaceResponse msg = new UnregisterAppInterfaceResponse(hash);
						Intent sendIntent = createBroadcastIntent();
						updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionId.UNREGISTER_APP_INTERFACE.toString());
						updateBroadcastIntent(sendIntent, "TYPE", RpcMessage.KEY_RESPONSE);
						updateBroadcastIntent(sendIntent, "SUCCESS", msg.getSuccess());
						updateBroadcastIntent(sendIntent, "COMMENT1", msg.getInfo());
						updateBroadcastIntent(sendIntent, "COMMENT2", msg.getResultCode().toString());
						updateBroadcastIntent(sendIntent, "DATA",serializeJson(msg));
						updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationId());
						sendBroadcastIntent(sendIntent);
				}
				return;
			}
			
			if (functionName.equals(FunctionId.REGISTER_APP_INTERFACE.toString())) {
				final RegisterAppInterfaceResponse msg = new RegisterAppInterfaceResponse(hash);
				if (msg.getSuccess()) {
					appInterfaceRegisterd = true;
				}

				//_autoActivateIdReturned = msg.getAutoActivateID();
				/*Place holder for legacy support*/ autoActivateIdReturned = "8675309";
				buttonCapabilities = msg.getButtonCapabilities();
				displayCapabilities = msg.getDisplayCapabilities();
				softButtonCapabilities = msg.getSoftButtonCapabilities();
				presetBankCapabilities = msg.getPresetBankCapabilities();
				hmiZoneCapabilities = msg.getHmiZoneCapabilities();
				speechCapabilities = msg.getSpeechCapabilities();
				prerecordedSpeech = msg.getPrerecordedSpeech();
				sdlLanguage = msg.getLanguage();
				hmiDisplayLanguage = msg.getHmiDisplayLanguage();
				sdlMsgVersion = msg.getSdlMsgVersion();
				vrCapabilities = msg.getVrCapabilities();
				vehicleType = msg.getVehicleType();
				audioPassThruCapabilities = msg.getAudioPassThruCapabilities();
				proxyVersionInfo = msg.getProxyVersionInfo();
				
				if (bAppResumeEnabled)
				{
					if ( (msg.getResultCode() == Result.RESUME_FAILED) || (msg.getResultCode() != Result.SUCCESS) )
					{
						bResumeSuccess = false;
						lastHashId = null;
					}
					else if ( (sdlMsgVersion.getMajorVersion() > 2) && (lastHashId != null) && (msg.getResultCode() == Result.SUCCESS) )
						bResumeSuccess = true;				
				}						
				
				diagModes = msg.getSupportedDiagModes();				
				
				if (!isDebugEnabled()) 
				{
					enableDebugTool();
					DebugTool.logInfo("SDL Proxy Version: " + proxyVersionInfo);
					disableDebugTool();
				}					
				else
					DebugTool.logInfo("SDL Proxy Version: " + proxyVersionInfo);				
				
				// RegisterAppInterface
				if (advancedLifecycleManagementEnabled) {
					
					// Send onSdlConnected message in ALM
					sdlConnectionState = SdlConnectionState.SDL_CONNECTED;
					
					// If registerAppInterface failed, exit with OnProxyUnusable
					if (!msg.getSuccess()) {
						notifyProxyClosed("Unable to register app interface. Review values passed to the SdlProxy constructor. RegisterAppInterface result code: ", 
								new SdlException("Unable to register app interface. Review values passed to the SdlProxy constructor. RegisterAppInterface result code: " + msg.getResultCode(), SdlExceptionCause.SDL_REGISTRATION_ERROR), SdlDisconnectedReason.SDL_REGISTRATION_ERROR);
					}
				} else {	
					if (callbackToUiThread) {
						// Run in UI thread
						mainUiHandler.post(new Runnable() {
							@Override
							public void run() {
								if (proxyListener instanceof IProxyListener) {
									((IProxyListener)proxyListener).onRegisterAppInterfaceResponse(msg);
								} else if (proxyListener instanceof IProxyListenerAlm) {
									//((IProxyListenerALM)_proxyListener).onRegisterAppInterfaceResponse(msg);
								}
							}
						});
					} else {
						if (proxyListener instanceof IProxyListener) {
							((IProxyListener)proxyListener).onRegisterAppInterfaceResponse(msg);
						} else if (proxyListener instanceof IProxyListenerAlm) {
							//((IProxyListenerALM)_proxyListener).onRegisterAppInterfaceResponse(msg);
						}
					}
				}
			} else if (functionName.equals(FunctionId.SPEAK.toString())) {
				// SpeakResponse
				
				final SpeakResponse msg = new SpeakResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onSpeakResponse(msg);
						}
					});
				} else {
					proxyListener.onSpeakResponse(msg);						
				}
			} else if (functionName.equals(FunctionId.ALERT.toString())) {
				// AlertResponse
				
				final AlertResponse msg = new AlertResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onAlertResponse(msg);
						}
					});
				} else {
					proxyListener.onAlertResponse(msg);						
				}
			} else if (functionName.equals(FunctionId.SHOW.toString())) {
				// ShowResponse
				
				final ShowResponse msg = new ShowResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onShowResponse((ShowResponse)msg);
						}
					});
				} else {
					proxyListener.onShowResponse((ShowResponse)msg);						
				}
			} else if (functionName.equals(FunctionId.ADD_COMMAND.toString())) {
				// AddCommand
				
				final AddCommandResponse msg = new AddCommandResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onAddCommandResponse((AddCommandResponse)msg);
						}
					});
				} else {
					proxyListener.onAddCommandResponse((AddCommandResponse)msg);					
				}
			} else if (functionName.equals(FunctionId.DELETE_COMMAND.toString())) {
				// DeleteCommandResponse
				
				final DeleteCommandResponse msg = new DeleteCommandResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onDeleteCommandResponse((DeleteCommandResponse)msg);
						}
					});
				} else {
					proxyListener.onDeleteCommandResponse((DeleteCommandResponse)msg);					
				}
			} else if (functionName.equals(FunctionId.ADD_SUB_MENU.toString())) {
				// AddSubMenu
				
				final AddSubMenuResponse msg = new AddSubMenuResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onAddSubMenuResponse((AddSubMenuResponse)msg);
						}
					});
				} else {
					proxyListener.onAddSubMenuResponse((AddSubMenuResponse)msg);					
				}
			} else if (functionName.equals(FunctionId.DELETE_SUB_MENU.toString())) {
				// DeleteSubMenu
				
				final DeleteSubMenuResponse msg = new DeleteSubMenuResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onDeleteSubMenuResponse((DeleteSubMenuResponse)msg);
						}
					});
				} else {
					proxyListener.onDeleteSubMenuResponse((DeleteSubMenuResponse)msg);					
				}
			} else if (functionName.equals(FunctionId.SUBSCRIBE_BUTTON.toString())) {
				// SubscribeButton
				
				final SubscribeButtonResponse msg = new SubscribeButtonResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onSubscribeButtonResponse((SubscribeButtonResponse)msg);
						}
					});
				} else {
					proxyListener.onSubscribeButtonResponse((SubscribeButtonResponse)msg);				
				}
			} else if (functionName.equals(FunctionId.UNSUBSCRIBE_BUTTON.toString())) {
				// UnsubscribeButton
				
				final UnsubscribeButtonResponse msg = new UnsubscribeButtonResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onUnsubscribeButtonResponse((UnsubscribeButtonResponse)msg);
						}
					});
				} else {
					proxyListener.onUnsubscribeButtonResponse((UnsubscribeButtonResponse)msg);			
				}
			} else if (functionName.equals(FunctionId.SET_MEDIA_CLOCK_TIMER.toString())) {
				// SetMediaClockTimer
				
				final SetMediaClockTimerResponse msg = new SetMediaClockTimerResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onSetMediaClockTimerResponse((SetMediaClockTimerResponse)msg);
						}
					});
				} else {
					proxyListener.onSetMediaClockTimerResponse((SetMediaClockTimerResponse)msg);		
				}
			} else if (functionName.equals(FunctionId.ENCODED_SYNC_P_DATA.toString())) {
				
				final SystemRequestResponse msg = new SystemRequestResponse(hash);
				
				Intent sendIntent = createBroadcastIntent();
				updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionId.SYSTEM_REQUEST.toString());
				updateBroadcastIntent(sendIntent, "TYPE", RpcMessage.KEY_RESPONSE);
				updateBroadcastIntent(sendIntent, "SUCCESS", msg.getSuccess());
				updateBroadcastIntent(sendIntent, "COMMENT1", msg.getInfo());
				updateBroadcastIntent(sendIntent, "COMMENT2", msg.getResultCode().toString());
				updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationId());
				sendBroadcastIntent(sendIntent);
			
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onSystemRequestResponse(msg); 
						}
					});
				} else {
					proxyListener.onSystemRequestResponse(msg); 		
				}
			}  else if (functionName.equals(FunctionId.CREATE_INTERACTION_CHOICE_SET.toString())) {
				// CreateInteractionChoiceSet
				
				final CreateInteractionChoiceSetResponse msg = new CreateInteractionChoiceSetResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onCreateInteractionChoiceSetResponse((CreateInteractionChoiceSetResponse)msg);
						}
					});
				} else {
					proxyListener.onCreateInteractionChoiceSetResponse((CreateInteractionChoiceSetResponse)msg);		
				}
			} else if (functionName.equals(FunctionId.DELETE_INTERACTION_CHOICE_SET.toString())) {
				// DeleteInteractionChoiceSet
				
				final DeleteInteractionChoiceSetResponse msg = new DeleteInteractionChoiceSetResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onDeleteInteractionChoiceSetResponse((DeleteInteractionChoiceSetResponse)msg);
						}
					});
				} else {
					proxyListener.onDeleteInteractionChoiceSetResponse((DeleteInteractionChoiceSetResponse)msg);		
				}
			} else if (functionName.equals(FunctionId.PERFORM_INTERACTION.toString())) {
				// PerformInteraction
				
				final PerformInteractionResponse msg = new PerformInteractionResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onPerformInteractionResponse((PerformInteractionResponse)msg);
						}
					});
				} else {
					proxyListener.onPerformInteractionResponse((PerformInteractionResponse)msg);		
				}
			} else if (functionName.equals(FunctionId.SET_GLOBAL_PROPERTIES.toString())) {
				// SetGlobalPropertiesResponse 
				
				final SetGlobalPropertiesResponse msg = new SetGlobalPropertiesResponse(hash);
				if (callbackToUiThread) {
						// Run in UI thread
						mainUiHandler.post(new Runnable() {
							@Override
							public void run() {
								proxyListener.onSetGlobalPropertiesResponse((SetGlobalPropertiesResponse)msg);
							}
						});
					} else {
						proxyListener.onSetGlobalPropertiesResponse((SetGlobalPropertiesResponse)msg);		
				}
			} else if (functionName.equals(FunctionId.RESET_GLOBAL_PROPERTIES.toString())) {
				// ResetGlobalProperties				
				
				final ResetGlobalPropertiesResponse msg = new ResetGlobalPropertiesResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onResetGlobalPropertiesResponse((ResetGlobalPropertiesResponse)msg);
						}
					});
				} else {
					proxyListener.onResetGlobalPropertiesResponse((ResetGlobalPropertiesResponse)msg);		
				}
			} else if (functionName.equals(FunctionId.UNREGISTER_APP_INTERFACE.toString())) {
				// UnregisterAppInterface
				
				appInterfaceRegisterd = false;
				synchronized(APP_INTERFACE_REGISTERED_LOCK) {
					APP_INTERFACE_REGISTERED_LOCK.notify();
				}
				
				final UnregisterAppInterfaceResponse msg = new UnregisterAppInterfaceResponse(hash);
				
				Intent sendIntent = createBroadcastIntent();
				updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionId.UNREGISTER_APP_INTERFACE.toString());
				updateBroadcastIntent(sendIntent, "TYPE", RpcMessage.KEY_RESPONSE);
				updateBroadcastIntent(sendIntent, "SUCCESS", msg.getSuccess());
				updateBroadcastIntent(sendIntent, "COMMENT1", msg.getInfo());
				updateBroadcastIntent(sendIntent, "COMMENT2", msg.getResultCode().toString());
				updateBroadcastIntent(sendIntent, "DATA",serializeJson(msg));
				updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationId());
				sendBroadcastIntent(sendIntent);							
				
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							if (proxyListener instanceof IProxyListener) {
								((IProxyListener)proxyListener).onUnregisterAppInterfaceResponse(msg);
							} else if (proxyListener instanceof IProxyListenerAlm) {
								//((IProxyListenerALM)_proxyListener).onUnregisterAppInterfaceResponse(msg);
							}
						}
					});
				} else {
					if (proxyListener instanceof IProxyListener) {
						((IProxyListener)proxyListener).onUnregisterAppInterfaceResponse(msg);
					} else if (proxyListener instanceof IProxyListenerAlm) {
						//((IProxyListenerALM)_proxyListener).onUnregisterAppInterfaceResponse(msg);
					}
				}
				
				notifyProxyClosed("UnregisterAppInterfaceResponse", null, SdlDisconnectedReason.APP_INTERFACE_UNREG);
			} else if (functionName.equals(FunctionId.GENERIC_RESPONSE.toString())) {
				// GenericResponse (Usually and error)
				final GenericResponse msg = new GenericResponse(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onGenericResponse((GenericResponse)msg);
						}
					});
				} else {
					proxyListener.onGenericResponse((GenericResponse)msg);	
				}
			} else if (functionName.equals(FunctionId.SLIDER.toString())) {
                // Slider
                final SliderResponse msg = new SliderResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onSliderResponse((SliderResponse)msg);
                        }
                    });
                } else {
                    proxyListener.onSliderResponse((SliderResponse)msg);   
                }
            } else if (functionName.equals(FunctionId.PUT_FILE.toString())) {
                // PutFile
                final PutFileResponse msg = new PutFileResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onPutFileResponse((PutFileResponse)msg);
                            notifyPutFileStreamResponse(msg);
                        }
                    });
                } else {
                    proxyListener.onPutFileResponse((PutFileResponse)msg);
                    notifyPutFileStreamResponse(msg);                    
                }
            } else if (functionName.equals(FunctionId.DELETE_FILE.toString())) {
                // DeleteFile
                final DeleteFileResponse msg = new DeleteFileResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onDeleteFileResponse((DeleteFileResponse)msg);
                        }
                    });
                } else {
                    proxyListener.onDeleteFileResponse((DeleteFileResponse)msg);   
                }
            } else if (functionName.equals(FunctionId.LIST_FILES.toString())) {
                // ListFiles
                final ListFilesResponse msg = new ListFilesResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onListFilesResponse((ListFilesResponse)msg);
                        }
                    });
                } else {
                    proxyListener.onListFilesResponse((ListFilesResponse)msg);     
                }
            } else if (functionName.equals(FunctionId.SET_APP_ICON.toString())) {
                // SetAppIcon
                final SetAppIconResponse msg = new SetAppIconResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onSetAppIconResponse((SetAppIconResponse)msg);
                        }
                    });
                } else {
                        proxyListener.onSetAppIconResponse((SetAppIconResponse)msg);   
                }
            } else if (functionName.equals(FunctionId.SCROLLABLE_MESSAGE.toString())) {
                // ScrollableMessage
                final ScrollableMessageResponse msg = new ScrollableMessageResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onScrollableMessageResponse((ScrollableMessageResponse)msg);
                        }
                    });
                } else {
                    proxyListener.onScrollableMessageResponse((ScrollableMessageResponse)msg);     
                }
            } else if (functionName.equals(FunctionId.CHANGE_REGISTRATION.toString())) {
                // ChangeLanguageRegistration
                final ChangeRegistrationResponse msg = new ChangeRegistrationResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onChangeRegistrationResponse((ChangeRegistrationResponse)msg);
                        }
                    });
                } else {
                    proxyListener.onChangeRegistrationResponse((ChangeRegistrationResponse)msg);   
                }
            } else if (functionName.equals(FunctionId.SET_DISPLAY_LAYOUT.toString())) {
                // SetDisplayLayout
                final SetDisplayLayoutResponse msg = new SetDisplayLayoutResponse(hash);
                
                // successfully changed display layout - update layout capabilities
                if(msg.getSuccess()){
                    displayCapabilities = msg.getDisplayCapabilities();
                    buttonCapabilities = msg.getButtonCapabilities();
                    presetBankCapabilities = msg.getPresetBankCapabilities();
                    softButtonCapabilities = msg.getSoftButtonCapabilities();
                }
                
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onSetDisplayLayoutResponse((SetDisplayLayoutResponse)msg);
                        }
                    });
                } else {
                        proxyListener.onSetDisplayLayoutResponse((SetDisplayLayoutResponse)msg);
                }
            } else if (functionName.equals(FunctionId.PERFORM_AUDIO_PASS_THRU.toString())) {
                // PerformAudioPassThru
                final PerformAudioPassThruResponse msg = new PerformAudioPassThruResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onPerformAudioPassThruResponse((PerformAudioPassThruResponse)msg);
                        }
                    });
                } else {
                    proxyListener.onPerformAudioPassThruResponse((PerformAudioPassThruResponse)msg);       
                }
            } else if (functionName.equals(FunctionId.END_AUDIO_PASS_THRU.toString())) {
                // EndAudioPassThru
                final EndAudioPassThruResponse msg = new EndAudioPassThruResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onEndAudioPassThruResponse((EndAudioPassThruResponse)msg);
                        }
                    });
                } else {
                    proxyListener.onEndAudioPassThruResponse((EndAudioPassThruResponse)msg);
                }
            } else if (functionName.equals(FunctionId.SUBSCRIBE_VEHICLE_DATA.toString())) {
            	// SubscribeVehicleData
                final SubscribeVehicleDataResponse msg = new SubscribeVehicleDataResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onSubscribeVehicleDataResponse((SubscribeVehicleDataResponse)msg);
                        }
                    });
                } else {
                    proxyListener.onSubscribeVehicleDataResponse((SubscribeVehicleDataResponse)msg);       
                }
            } else if (functionName.equals(FunctionId.UNSUBSCRIBE_VEHICLE_DATA.toString())) {
            	// UnsubscribeVehicleData
                final UnsubscribeVehicleDataResponse msg = new UnsubscribeVehicleDataResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onUnsubscribeVehicleDataResponse((UnsubscribeVehicleDataResponse)msg);
                        }
                    });
                } else {
                    proxyListener.onUnsubscribeVehicleDataResponse((UnsubscribeVehicleDataResponse)msg);   
                }
            } else if (functionName.equals(FunctionId.GET_VEHICLE_DATA.toString())) {
           		// GetVehicleData
                final GetVehicleDataResponse msg = new GetVehicleDataResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                           proxyListener.onGetVehicleDataResponse((GetVehicleDataResponse)msg);
                        }
                     });
                    } else {
                        proxyListener.onGetVehicleDataResponse((GetVehicleDataResponse)msg);   
                    }            	               
            } else if (functionName.equals(FunctionId.READ_DID.toString())) {
                final ReadDidResponse msg = new ReadDidResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onReadDIDResponse((ReadDidResponse)msg);
                        }
                    });
                } else {
                    proxyListener.onReadDIDResponse((ReadDidResponse)msg);   
                }            	            	
            } else if (functionName.equals(FunctionId.GET_DTCS.toString())) {
                final GetDtcsResponse msg = new GetDtcsResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onGetDTCsResponse((GetDtcsResponse)msg);
                        }
                    });
                } else {
                    proxyListener.onGetDTCsResponse((GetDtcsResponse)msg);   
                }
            } else if (functionName.equals(FunctionId.DIAGNOSTIC_MESSAGE.toString())) {
                final DiagnosticMessageResponse msg = new DiagnosticMessageResponse(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onDiagnosticMessageResponse((DiagnosticMessageResponse)msg);
                        }
                    });
                } else {
                    proxyListener.onDiagnosticMessageResponse((DiagnosticMessageResponse)msg);   
                }            	
            } 
            else if (functionName.equals(FunctionId.SYSTEM_REQUEST.toString())) {

   				final SystemRequestResponse msg = new SystemRequestResponse(hash);
   				if (callbackToUiThread) {
   					// Run in UI thread
   					mainUiHandler.post(new Runnable() {
   						@Override
   						public void run() {
   							proxyListener.onSystemRequestResponse((SystemRequestResponse)msg);
   						}
   					});
   				} else {
   					proxyListener.onSystemRequestResponse((SystemRequestResponse)msg);	
   				}
            }
            else if (functionName.equals(FunctionId.SEND_LOCATION.toString())) {

   				final SendLocationResponse msg = new SendLocationResponse(hash);
   				if (callbackToUiThread) {
   					// Run in UI thread
   					mainUiHandler.post(new Runnable() {
   						@Override
   						public void run() {
   							proxyListener.onSendLocationResponse(msg);
   						}
   					});
   				} else {
   					proxyListener.onSendLocationResponse(msg);	
   				}
            }
            else if (functionName.equals(FunctionId.DIAL_NUMBER.toString())) {

   				final DialNumberResponse msg = new DialNumberResponse(hash);
   				if (callbackToUiThread) {
   					// Run in UI thread
   					mainUiHandler.post(new Runnable() {
   						@Override
   						public void run() {
   							proxyListener.onDialNumberResponse(msg);
   						}
   					});
   				} else {
   					proxyListener.onDialNumberResponse(msg);	
   				}
            }
			else {
				if (sdlMsgVersion != null) {
					DebugTool.logError("Unrecognized response Message: " + functionName.toString() + 
							"SDL Message Version = " + sdlMsgVersion);
				} else {
					DebugTool.logError("Unrecognized response Message: " + functionName.toString());
				}
			} // end-if
		} else if (messageType.equals(RpcMessage.KEY_NOTIFICATION)) {
			SdlTrace.logRpcEvent(InterfaceActivityDirection.Receive, new RpcNotification(rpcMsg), SDL_LIB_TRACE_KEY);
			if (functionName.equals(FunctionId.ON_HMI_STATUS.toString())) {
				// OnHMIStatus
				
				final OnHMIStatus msg = new OnHMIStatus(hash);

				//setup lockscreeninfo
				if (sdlSession != null)
				{
					sdlSession.getLockScreenMan().setHMILevel(msg.getHmiLevel());
				}
				
				msg.setFirstRun(Boolean.valueOf(firstTimeFull));
				if (msg.getHmiLevel() == HmiLevel.HMI_FULL) firstTimeFull = false;
				
				if (msg.getHmiLevel() != priorHmiLevel && msg.getAudioStreamingState() != priorAudioStreamingState) {
					if (callbackToUiThread) {
						// Run in UI thread
						mainUiHandler.post(new Runnable() {
							@Override
							public void run() {
								proxyListener.onOnHMIStatus((OnHMIStatus)msg);
								proxyListener.onOnLockScreenNotification(sdlSession.getLockScreenMan().getLockObj());
							}
						});
					} else {
						proxyListener.onOnHMIStatus((OnHMIStatus)msg);
						proxyListener.onOnLockScreenNotification(sdlSession.getLockScreenMan().getLockObj());
					}
				}				
			} else if (functionName.equals(FunctionId.ON_COMMAND.toString())) {
				// OnCommand
				
				final OnCommand msg = new OnCommand(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onOnCommand((OnCommand)msg);
						}
					});
				} else {
					proxyListener.onOnCommand((OnCommand)msg);
				}
			} else if (functionName.equals(FunctionId.ON_DRIVER_DISTRACTION.toString())) {
				// OnDriverDistration
				
				final OnDriverDistraction msg = new OnDriverDistraction(hash);
				
				//setup lockscreeninfo
				if (sdlSession != null)
				{
					DriverDistractionState drDist = msg.getState();
					boolean bVal = false;
					if (drDist == DriverDistractionState.DD_ON)
						bVal = true;
					else
						bVal = false;
					sdlSession.getLockScreenMan().setDriverDistStatus(bVal);
				}
				
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onOnDriverDistraction(msg);
							proxyListener.onOnLockScreenNotification(sdlSession.getLockScreenMan().getLockObj());
						}
					});
				} else {
					proxyListener.onOnDriverDistraction(msg);
					proxyListener.onOnLockScreenNotification(sdlSession.getLockScreenMan().getLockObj());
				}
			} else if (functionName.equals(FunctionId.ON_ENCODED_SYNC_P_DATA.toString())) {
				
				final OnSystemRequest msg = new OnSystemRequest(hash);

				Intent sendIntent = createBroadcastIntent();
				updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionId.ON_SYSTEM_REQUEST.toString());
				updateBroadcastIntent(sendIntent, "TYPE", RpcMessage.KEY_NOTIFICATION);
				
				// If url is null, then send notification to the app, otherwise, send to URL
				if (msg.getUrl() == null) {
					updateBroadcastIntent(sendIntent, "COMMENT1", "URL is a null value (received)");
					sendBroadcastIntent(sendIntent);					
					if (callbackToUiThread) {
						// Run in UI thread
						mainUiHandler.post(new Runnable() {
							@Override
							public void run() {
								proxyListener.onOnSystemRequest(msg);
							}
						});
					} else {
						proxyListener.onOnSystemRequest(msg);
					}
				} else {
					updateBroadcastIntent(sendIntent, "COMMENT1", "Sending to cloud: " + msg.getUrl());
					sendBroadcastIntent(sendIntent);				
					
					Log.i("pt", "send to url");
					
					if ( (msg.getUrl() != null) )
					{
						Thread handleOffboardTransmissionThread = new Thread() {
							@Override
							public void run() {
								sendOnSystemRequestToUrl(msg);
							}
						};

						handleOffboardTransmissionThread.start();
					}					
				}
			} else if (functionName.equals(FunctionId.ON_PERMISSIONS_CHANGE.toString())) {
				//OnPermissionsChange
				
				final OnPermissionsChange msg = new OnPermissionsChange(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onOnPermissionsChange(msg);
						}
					});
				} else {
					proxyListener.onOnPermissionsChange(msg);
				}
			} else if (functionName.equals(FunctionId.ON_TBT_CLIENT_STATE.toString())) {
				// OnTBTClientState
				
				final OnTbtClientState msg = new OnTbtClientState(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onOnTBTClientState(msg);
						}
					});
				} else {
					proxyListener.onOnTBTClientState(msg);
				}
			} else if (functionName.equals(FunctionId.ON_BUTTON_PRESS.toString())) {
				// OnButtonPress
				
				final OnButtonPress msg = new OnButtonPress(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onOnButtonPress((OnButtonPress)msg);
						}
					});
				} else {
					proxyListener.onOnButtonPress((OnButtonPress)msg);
				}
			} else if (functionName.equals(FunctionId.ON_BUTTON_EVENT.toString())) {
				// OnButtonEvent
				
				final OnButtonEvent msg = new OnButtonEvent(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onOnButtonEvent((OnButtonEvent)msg);
						}
					});
				} else {
					proxyListener.onOnButtonEvent((OnButtonEvent)msg);
				}
			} else if (functionName.equals(FunctionId.ON_LANGUAGE_CHANGE.toString())) {
				// OnLanguageChange
				
				final OnLanguageChange msg = new OnLanguageChange(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onOnLanguageChange((OnLanguageChange)msg);
						}
					});
				} else {
					proxyListener.onOnLanguageChange((OnLanguageChange)msg);
				}
			} else if (functionName.equals(FunctionId.ON_HASH_CHANGE.toString())) {
				// OnLanguageChange
				
				final OnHashChange msg = new OnHashChange(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onOnHashChange((OnHashChange)msg);
							if (bAppResumeEnabled)
							{
								lastHashId = msg.getHashID();
							}
						}
					});
				} else {
					proxyListener.onOnHashChange((OnHashChange)msg);
					if (bAppResumeEnabled)
					{
						lastHashId = msg.getHashID();
					}
				}
			} else if (functionName.equals(FunctionId.ON_SYSTEM_REQUEST.toString())) {
					// OnSystemRequest
					
					final OnSystemRequest msg = new OnSystemRequest(hash);
					
					if ( (msg.getUrl() != null) &&
						 (msg.getRequestType() == RequestType.PROPRIETARY) &&
						 (msg.getFileType() == FileType.JSON) )
					{
						Thread handleOffboardTransmissionThread = new Thread() {
							@Override
							public void run() {
								sendOnSystemRequestToUrl(msg);
							}
						};

						handleOffboardTransmissionThread.start();
					}
					
					
					if (callbackToUiThread) {
						// Run in UI thread
						mainUiHandler.post(new Runnable() {
							@Override
							public void run() {
								proxyListener.onOnSystemRequest((OnSystemRequest)msg);
							}
						});
					} else {
						proxyListener.onOnSystemRequest((OnSystemRequest)msg);
					}
			} else if (functionName.equals(FunctionId.ON_AUDIO_PASS_THRU.toString())) {
				// OnAudioPassThru
				final OnAudioPassThru msg = new OnAudioPassThru(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
    						proxyListener.onOnAudioPassThru((OnAudioPassThru)msg);
                        }
                    });
                } else {
					proxyListener.onOnAudioPassThru((OnAudioPassThru)msg);
                }				
			} else if (functionName.equals(FunctionId.ON_VEHICLE_DATA.toString())) {
				// OnVehicleData
                final OnVehicleData msg = new OnVehicleData(hash);
                if (callbackToUiThread) {
                    // Run in UI thread
                    mainUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            proxyListener.onOnVehicleData((OnVehicleData)msg);
                        }
                    });
                } else {
                    proxyListener.onOnVehicleData((OnVehicleData)msg);
                } 
			}
			else if (functionName.equals(FunctionId.ON_APP_INTERFACE_UNREGISTERED.toString())) {
				// OnAppInterfaceUnregistered
				
				appInterfaceRegisterd = false;
				synchronized(APP_INTERFACE_REGISTERED_LOCK) {
					APP_INTERFACE_REGISTERED_LOCK.notify();
				}
				
				final OnAppInterfaceUnregistered msg = new OnAppInterfaceUnregistered(hash);
								
				Intent sendIntent = createBroadcastIntent();
				updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionId.ON_APP_INTERFACE_UNREGISTERED.toString());
				updateBroadcastIntent(sendIntent, "TYPE", RpcMessage.KEY_NOTIFICATION);
				updateBroadcastIntent(sendIntent, "DATA",serializeJson(msg));
				sendBroadcastIntent(sendIntent);

				if (advancedLifecycleManagementEnabled) {
					// This requires the proxy to be cycled
                    cycleProxy(SdlDisconnectedReason.convertAppInterfaceUnregisteredReason(msg.getReason()));
                } else {
					if (callbackToUiThread) {
						// Run in UI thread
						mainUiHandler.post(new Runnable() {
							@Override
							public void run() {
								((IProxyListener)proxyListener).onOnAppInterfaceUnregistered(msg);
							}
						});
					} else {
						((IProxyListener)proxyListener).onOnAppInterfaceUnregistered(msg);
					}					
					notifyProxyClosed("OnAppInterfaceUnregistered", null, SdlDisconnectedReason.APP_INTERFACE_UNREG);
				}
			} 
			else if (functionName.equals(FunctionId.ON_KEYBOARD_INPUT.toString())) {
				final OnKeyboardInput msg = new OnKeyboardInput(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onOnKeyboardInput((OnKeyboardInput)msg);
						}
					});
				} else {
					proxyListener.onOnKeyboardInput((OnKeyboardInput)msg);
				}
			}
			else if (functionName.equals(FunctionId.ON_TOUCH_EVENT.toString())) {
				final OnTouchEvent msg = new OnTouchEvent(hash);
				if (callbackToUiThread) {
					// Run in UI thread
					mainUiHandler.post(new Runnable() {
						@Override
						public void run() {
							proxyListener.onOnTouchEvent((OnTouchEvent)msg);
						}
					});
				} else {
					proxyListener.onOnTouchEvent((OnTouchEvent)msg);
				}
			}
			else {
				if (sdlMsgVersion != null) {
					DebugTool.logInfo("Unrecognized notification Message: " + functionName.toString() + 
							" connected to SDL using message version: " + sdlMsgVersion.getMajorVersion() + "." + sdlMsgVersion.getMinorVersion());
				} else {
					DebugTool.logInfo("Unrecognized notification Message: " + functionName.toString());
				}
			} // end-if
		} // end-if notification
		
		SdlTrace.logProxyEvent("Proxy received RPC Message: " + functionName, SDL_LIB_TRACE_KEY);
	}
	
	/**
	 * Takes an RPCRequest and sends it to SDL.  Responses are captured through callback on IProxyListener.  
	 * 
	 * @param request
	 * @throws SdlException
	 */
	public void sendRpcRequest(RpcRequest request) throws SdlException {
		if (proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		// Test if request is null
		if (request == null) {
			SdlTrace.logProxyEvent("Application called sendRPCRequest method with a null RPCRequest.", SDL_LIB_TRACE_KEY);
			throw new IllegalArgumentException("sendRPCRequest cannot be called with a null request.");
		}
		
		SdlTrace.logProxyEvent("Application called sendRPCRequest method for RPCRequest: ." + request.getFunctionName(), SDL_LIB_TRACE_KEY);
			
		// Test if SdlConnection is null
		synchronized(CONNECTION_REFERENCE_LOCK) {
			if (sdlSession == null || !sdlSession.getIsConnected()) {
				SdlTrace.logProxyEvent("Application attempted to send and RPCRequest without a connected transport.", SDL_LIB_TRACE_KEY);
				throw new SdlException("There is no valid connection to SDL. sendRPCRequest cannot be called until SDL has been connected.", SdlExceptionCause.SDL_UNAVAILABLE);
			}
		}
		
		// Test for illegal correlation ID
		if (isCorrelationIdProtected(request.getCorrelationId())) {
			
			SdlTrace.logProxyEvent("Application attempted to use the reserved correlation ID, " + request.getCorrelationId(), SDL_LIB_TRACE_KEY);
			throw new SdlException("Invalid correlation ID. The correlation ID, " + request.getCorrelationId()
					+ " , is a reserved correlation ID.", SdlExceptionCause.RESERVED_CORRELATION_ID);
		}
		
		// Throw exception if RPCRequest is sent when SDL is unavailable 
		if (!appInterfaceRegisterd && !request.getFunctionName().equals(FunctionId.REGISTER_APP_INTERFACE.toString())) {
			
			SdlTrace.logProxyEvent("Application attempted to send an RPCRequest (non-registerAppInterface), before the interface was registerd.", SDL_LIB_TRACE_KEY);
			throw new SdlException("SDL is currently unavailable. RPC Requests cannot be sent.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
				
		if (advancedLifecycleManagementEnabled) {
			if (request.getFunctionName().equals(FunctionId.REGISTER_APP_INTERFACE.toString())
					|| request.getFunctionName().equals(FunctionId.UNREGISTER_APP_INTERFACE.toString())) {
				
				SdlTrace.logProxyEvent("Application attempted to send a RegisterAppInterface or UnregisterAppInterface while using ALM.", SDL_LIB_TRACE_KEY);
				throw new SdlException("The RPCRequest, " + request.getFunctionName() + 
						", is unallowed using the Advanced Lifecycle Management Model.", SdlExceptionCause.INCORRECT_LIFECYCLE_MODEL);
			}
		}
		
		sendRpcRequestPrivate(request);
	} // end-method
	
	protected void notifyProxyClosed(final String info, final Exception e, final SdlDisconnectedReason reason) {		
		SdlTrace.logProxyEvent("NotifyProxyClose", SDL_LIB_TRACE_KEY);
		OnProxyClosed message = new OnProxyClosed(info, e, reason);
		queueInternalMessage(message);
	}

	private void passErrorToProxyListener(final String info, final Exception e) {
				
		OnError message = new OnError(info, e);
		queueInternalMessage(message);
	}
	
	private void startRpcProtocolSession(byte sessionID, String correlationID) {
		
		// Set Proxy Lifecyclek Available
		if (advancedLifecycleManagementEnabled) {
			
			try {
				registerAppInterfacePrivate(
						sdlMsgVersionRequest,
						applicationName,
						ttsName,
						ngnMediaScreenAppName,
						vrSynonyms,
						isMediaApp, 
						sdlLanguageDesired,
						hmiDisplayLanguageDesired,
						appType,
						appId,
						autoActivateIdDesired,						
						REGISTER_APP_INTERFACE_CORRELATION_ID);
				
			} catch (Exception e) {
				notifyProxyClosed("Failed to register application interface with SDL. Check parameter values given to SdlProxy constructor.", e, SdlDisconnectedReason.SDL_REGISTRATION_ERROR);
			}
		} else {
			InternalProxyMessage message = new InternalProxyMessage(InternalProxyMessage.ON_PROXY_OPENED);
			queueInternalMessage(message);
		}
	}
	
	// Queue internal callback message
	private void queueInternalMessage(InternalProxyMessage message) {
		synchronized(INTERNAL_MESSAGE_QUEUE_THREAD_LOCK) {
			if (internalProxyMessageDispatcher != null) {
				internalProxyMessageDispatcher.queueMessage(message);
			}
		}
	}
	
	// Queue incoming ProtocolMessage
	private void queueIncomingMessage(ProtocolMessage message) {
		synchronized(INCOMING_MESSAGE_QUEUE_THREAD_LOCK) {
			if (incomingProxyMessageDispatcher != null) {
				incomingProxyMessageDispatcher.queueMessage(message);
			}
		}
	}
		
	private FileInputStream getFileInputStream(String sLocalFile)
	{
		FileInputStream is = null;
		try 
		{
			is = new FileInputStream(sLocalFile);
		}
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		return is;
	}

	private Long getFileInputStreamSize(FileInputStream is)
	{
		Long lSize = null;
		
		try 
		{
			lSize = is.getChannel().size();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return lSize;
	}
	
	private void closeFileInputStream(FileInputStream is)
	{
		try
		{
			is.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private RpcStreamController startRpcStream(String sLocalFile, PutFile request, SessionType sType, byte rpcSessionId, byte wiproVersion)
	{		
		if (sdlSession == null) return null;		
		SdlConnection sdlConn = sdlSession.getSdlConnection();		
		if (sdlConn == null) return null;
					
		FileInputStream is = getFileInputStream(sLocalFile);
		if (is == null) return null;
		
		Integer iSize = Integer.valueOf(getFileInputStreamSize(is).intValue());
		if (iSize == null)
		{	
			closeFileInputStream(is);
			return null;
		}

		try {
			StreamRpcPacketizer rpcPacketizer = new StreamRpcPacketizer((SdlProxyBase<IProxyListenerBase>) this, sdlConn, is, request, sType, rpcSessionId, wiproVersion, iSize);
			rpcPacketizer.start();
			RpcStreamController streamController = new RpcStreamController(rpcPacketizer, request.getCorrelationId());
			return streamController;
		} catch (Exception e) {
            Log.e("SyncConnection", "Unable to start streaming:" + e.toString());  
            return null;
        }			
	}

	@SuppressWarnings("unchecked")
	private RpcStreamController startRpcStream(InputStream is, PutFile request, SessionType sType, byte rpcSessionId, byte wiproVersion)
	{		
		if (sdlSession == null) return null;		
		SdlConnection sdlConn = sdlSession.getSdlConnection();		
		if (sdlConn == null) return null;
		Long iSize = request.getLength();

		if (request.getLength() == null)
		{
			return null;
		}		
		
		try {
			StreamRpcPacketizer rpcPacketizer = new StreamRpcPacketizer((SdlProxyBase<IProxyListenerBase>) this, sdlConn, is, request, sType, rpcSessionId, wiproVersion, iSize);
			rpcPacketizer.start();
			RpcStreamController streamController = new RpcStreamController(rpcPacketizer, request.getCorrelationId());
			return streamController;
		} catch (Exception e) {
            Log.e("SyncConnection", "Unable to start streaming:" + e.toString());  
            return null;
        }			
	}

	private RpcStreamController startPutFileStream(String sPath, PutFile msg) {
		if (sdlSession == null) return null;		
		SdlConnection sdlConn = sdlSession.getSdlConnection();		
		if (sdlConn == null) return null;
		return startRpcStream(sPath, msg, SessionType.RPC, sdlSession.getSessionId(), wiproVersion);		
	}

	private RpcStreamController startPutFileStream(InputStream is, PutFile msg) {
		if (sdlSession == null) return null;		
		SdlConnection sdlConn = sdlSession.getSdlConnection();		
		if (sdlConn == null) return null;
		if (is == null) return null;
		startRpcStream(is, msg, SessionType.RPC, sdlSession.getSessionId(), wiproVersion);
		return null;
	}
	
	public boolean startRpcStream(InputStream is, RpcRequest msg) {
		if (sdlSession == null) return false;		
		SdlConnection sdlConn = sdlSession.getSdlConnection();		
		if (sdlConn == null) return false;
		sdlConn.startRpcStream(is, msg, SessionType.RPC, sdlSession.getSessionId(), wiproVersion);
		return true;
	}
	
	public OutputStream startRpcStream(RpcRequest msg) {
		if (sdlSession == null) return null;		
		SdlConnection sdlConn = sdlSession.getSdlConnection();		
		if (sdlConn == null) return null;
		return sdlConn.startRpcStream(msg, SessionType.RPC, sdlSession.getSessionId(), wiproVersion);				
	}
	
	public void endRpcStream() {
		if (sdlSession == null) return;		
		SdlConnection sdlConn = sdlSession.getSdlConnection();		
		if (sdlConn == null) return;
		sdlConn.stopRpcStream();
	}
	
	public boolean startH264(InputStream is) {
		
		if (sdlSession == null) return false;		
		SdlConnection sdlConn = sdlSession.getSdlConnection();		
		if (sdlConn == null) return false;
				
		navServiceResponseReceived = false;
		navServiceResponse = false;
		sdlConn.startService(SessionType.NAV, sdlSession.getSessionId());
		int infiniteLoopKiller = 0;
		while (!navServiceResponseReceived && infiniteLoopKiller<2147483647) {
			infiniteLoopKiller++;
		}
		if (navServiceResponse) {
			sdlConn.startStream(is, SessionType.NAV, sdlSession.getSessionId());
			return true;
		} else {
			return false;
		}
	}
	
	public OutputStream startH264() {

		if (sdlSession == null) return null;		
		SdlConnection sdlConn = sdlSession.getSdlConnection();		
		if (sdlConn == null) return null;
		
		navServiceResponseReceived = false;
		navServiceResponse = false;
		sdlConn.startService(SessionType.NAV, sdlSession.getSessionId());
		int infiniteLoopKiller = 0;
		while (!navServiceResponseReceived && infiniteLoopKiller<2147483647) {
			infiniteLoopKiller++;
		}
		if (navServiceResponse) {
			return sdlConn.startStream(SessionType.NAV, sdlSession.getSessionId());
		} else {
			return null;
		}
	}	
	
	public void endH264() {
		if (sdlSession == null) return;		
		SdlConnection sdlConn = sdlSession.getSdlConnection();		
		if (sdlConn == null) return;
		sdlConn.endService(SessionType.NAV, sdlSession.getSessionId());
	}

	public boolean startPcm(InputStream is) {
		if (sdlSession == null) return false;		
		SdlConnection sdlConn = sdlSession.getSdlConnection();		
		if (sdlConn == null) return false;		
		
		navServiceResponseReceived = false;
		navServiceResponse = false;

		sdlConn.startService(SessionType.PCM, sdlSession.getSessionId());
		int infiniteLoopKiller = 0;
		while (!navServiceResponseReceived && infiniteLoopKiller<2147483647) {
			infiniteLoopKiller++;
		}
		if (navServiceResponse) {
			sdlConn.startStream(is, SessionType.PCM, sdlSession.getSessionId());
			return true;
		} else {
			return false;
		}
	}
	
	public OutputStream startPcm() {
		if (sdlSession == null) return null;		
		SdlConnection sdlConn = sdlSession.getSdlConnection();		
		if (sdlConn == null) return null;		
		
		navServiceResponseReceived = false;
		navServiceResponse = false;
		sdlConn.startService(SessionType.PCM, sdlSession.getSessionId());
		int infiniteLoopKiller = 0;
		while (!navServiceResponseReceived && infiniteLoopKiller<2147483647) {
			infiniteLoopKiller++;
		}
		if (navServiceResponse) {
			return sdlConn.startStream(SessionType.PCM, sdlSession.getSessionId());
		} else {
			return null;
		}
	}
	public void endPCM() {
		if (sdlSession == null) return;		
		SdlConnection sdlConn = sdlSession.getSdlConnection();		
		if (sdlConn == null) return;
		
		sdlConn.endService(SessionType.PCM, sdlSession.getSessionId());
	}	
	
	private void navServiceStarted() {
		navServiceResponseReceived = true;
		navServiceResponse = true;
	}
	
	private void navServiceEnded() {
		navServiceResponseReceived = true;
		navServiceResponse = false;
	}
	
	@SuppressWarnings("unused")
    private void audioServiceStarted() {
		pcmServiceResponseReceived = true;
		pcmServiceResponse = true;
	}
	
	@SuppressWarnings("unused")
    private void audioServiceEnded() {
		pcmServiceResponseReceived = true;
		pcmServiceResponse = false;
	}	
	
	public void setAppService(Service mService)
	{
		appService = mService;
	}

	/******************** Public Helper Methods *************************/
	
	/*Begin V1 Enhanced helper*/
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandId -Unique command ID of the command to add.
	 *@param menuText  -Menu text for optional sub value containing menu parameters.
	 *@param parentId  -Menu parent ID for optional sub value containing menu parameters.
	 *@param position  -Menu position for optional sub value containing menu parameters.
	 *@param vrCommands -VR synonyms for this AddCommand.
	 *@param IconValue -A static hex icon value or the binary image file name identifier (sent by the PutFile RPC).
	 *@param IconType -Describes whether the image is static or dynamic
	 *@param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 *@throws SdlException
	 */
	public void addCommand(Integer commandId,
			String menuText, Integer parentId, Integer position,
			Vector<String> vrCommands, String IconValue, ImageType IconType, Integer correlationId) 
			throws SdlException {
		
		AddCommand msg = RpcRequestFactory.buildAddCommand(commandId, menuText, parentId, position,
			vrCommands, IconValue, IconType, correlationId);
		
		sendRpcRequest(msg);
	}
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandId -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param position -Menu position for optional sub value containing menu parameters.
	 *@param vrCommands -VR synonyms for this AddCommand.
	 *@param IconValue -A static hex icon value or the binary image file name identifier (sent by the PutFile RPC).
	 *@param IconType -Describes whether the image is static or dynamic
	 *@param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 *@throws SdlException
	 */
	public void addCommand(Integer commandId,
			String menuText, Integer position,
			Vector<String> vrCommands, String IconValue, ImageType IconType, Integer correlationId) 
			throws SdlException {
		
		addCommand(commandId, menuText, null, position, vrCommands, IconValue, IconType, correlationId);
	}
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandId -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param position -Menu position for optional sub value containing menu parameters.
	 *@param IconValue -A static hex icon value or the binary image file name identifier (sent by the PutFile RPC).
	 *@param IconType -Describes whether the image is static or dynamic
	 *@param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 *@throws SdlException
	 */
	public void addCommand(Integer commandId,
			String menuText, Integer position, String IconValue, ImageType IconType,
			Integer correlationId) 
			throws SdlException {
		
		addCommand(commandId, menuText, null, position, null, IconValue, IconType, correlationId);
	}
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandId -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param IconValue -A static hex icon value or the binary image file name identifier (sent by the PutFile RPC).
	 *@param IconType -Describes whether the image is static or dynamic
	 *@param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 *@throws SdlException
	 */
	public void addCommand(Integer commandId,
			String menuText, String IconValue, ImageType IconType, Integer correlationId) 
			throws SdlException {
		
		addCommand(commandId, menuText, null, null, null, IconValue, IconType, correlationId);
	}
	
	/**
	 * Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param commandId -Unique command ID of the command to add.
	 * @param menuText -Menu text for optional sub value containing menu parameters.
	 * @param vrCommands -VR synonyms for this AddCommand.
	 * @param IconValue -A static hex icon value or the binary image file name identifier (sent by the PutFile RPC).
	 * @param IconType -Describes whether the image is static or dynamic
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void addCommand(Integer commandId,
			String menuText, Vector<String> vrCommands, String IconValue, ImageType IconType, Integer correlationId) 
			throws SdlException {
		
		addCommand(commandId, menuText, null, null, vrCommands, IconValue, IconType, correlationId);
	}
	
	/**
	 * Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param commandId -Unique command ID of the command to add.
	 * @param vrCommands -VR synonyms for this AddCommand.
	 * @param IconValue -A static hex icon value or the binary image file name identifier (sent by the PutFile RPC).
	 * @param IconType -Describes whether the image is static or dynamic
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void addCommand(Integer commandId,
			Vector<String> vrCommands, String IconValue, ImageType IconType, Integer correlationId) 
			throws SdlException {
		
		addCommand(commandId, null, null, null, vrCommands, IconValue, IconType, correlationId);
	}

	/*End V1 Enhanced helper*/
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandId -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param parentId  -Menu parent ID for optional sub value containing menu parameters.
	 *@param position  -Menu position for optional sub value containing menu parameters.
	 *@param vrCommands -VR synonyms for this AddCommand.
	 *@param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 *@throws SdlException
	 */
	public void addCommand(Integer commandId,
			String menuText, Integer parentId, Integer position,
			Vector<String> vrCommands, Integer correlationId) 
			throws SdlException {
		
		AddCommand msg = RpcRequestFactory.buildAddCommand(commandId, menuText, parentId, position,
			vrCommands, correlationId);
		
		sendRpcRequest(msg);
	}
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandId -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param position  -Menu position for optional sub value containing menu parameters.
	 *@param vrCommands -VR synonyms for this AddCommand.
	 *@param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 *@throws SdlException
	 */
	public void addCommand(Integer commandId,
			String menuText, Integer position,
			Vector<String> vrCommands, Integer correlationId) 
			throws SdlException {
		
		addCommand(commandId, menuText, null, position, vrCommands, correlationId);
	}
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandId -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param position  -Menu position for optional sub value containing menu parameters.
	 *@param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 *@throws SdlException
	 */
	public void addCommand(Integer commandId,
			String menuText, Integer position,
			Integer correlationId) 
			throws SdlException {
		
		addCommand(commandId, menuText, null, position, null, correlationId);
	}
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandId -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 *@throws SdlException
	 */
	public void addCommand(Integer commandId,
			String menuText, Integer correlationId) 
			throws SdlException {
		Vector<String> vrCommands = null;
		
		addCommand(commandId, menuText, null, null, vrCommands, correlationId);
	}
	
	/**
	 * Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 *@param commandId -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param vrCommands -VR synonyms for this AddCommand.
	 *@param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 *@throws SdlException
	 */
	public void addCommand(Integer commandId,
			String menuText, Vector<String> vrCommands, Integer correlationId) 
			throws SdlException {
		
		addCommand(commandId, menuText, null, null, vrCommands, correlationId);
	}
	
	/**
	 * Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 *@param commandId -Unique command ID of the command to add.
	 *@param vrCommands -VR synonyms for this AddCommand.
	 *@param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 *@throws SdlException
	 */
	public void addCommand(Integer commandId,
			Vector<String> vrCommands, Integer correlationId) 
			throws SdlException {
		
		addCommand(commandId, null, null, null, vrCommands, correlationId);
	}
		
	
	/**
	 * Sends an AddSubMenu RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param menuId -Unique ID of the sub menu to add.
	 * @param menuName -Text to show in the menu for this sub menu.
	 * @param position -Position within the items that are are at top level of the in application menu.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void addSubMenu(Integer menuId, String menuName,
			Integer position, Integer correlationId) 
			throws SdlException {
		
		AddSubMenu msg = RpcRequestFactory.buildAddSubMenu(menuId, menuName,
				position, correlationId);
		
		sendRpcRequest(msg);
	}
	
	/**
	 * Sends an AddSubMenu RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param menuId -Unique ID of the sub menu to add.
	 * @param menuName -Text to show in the menu for this sub menu.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void addSubMenu(Integer menuId, String menuName,
			Integer correlationId) throws SdlException {
		
		addSubMenu(menuId, menuName, null, correlationId);
	}
	
	/*Begin V1 Enhanced helper*/	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param ttsText -The text to speech message in the form of a string.
	 * @param alertText1 -The first line of the alert text field.
	 * @param alertText2 -The second line of the alert text field.
	 * @param alertText3 -The optional third line of the alert text field.
	 * @param playTone -Defines if tone should be played.
	 * @param duration -Timeout in milliseconds.
	 * @param softButtons -A list of App defined SoftButtons.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void alert(String ttsText, String alertText1,
			String alertText2, String alertText3, Boolean playTone, Integer duration, Vector<SoftButton> softButtons,
			Integer correlationId) throws SdlException {

		Alert msg = RpcRequestFactory.buildAlert(ttsText, alertText1, alertText2, alertText3, playTone, duration, softButtons, correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param ttsChunks -Text/phonemes to speak in the form of ttsChunks.
	 * @param alertText1 -The first line of the alert text field.
	 * @param alertText2 -The second line of the alert text field.
	 * @param alertText3 -The optional third line of the alert text field.
	 * @param playTone -Defines if tone should be played.
	 * @param duration -Timeout in milliseconds.
	 * @param softButtons -A list of App defined SoftButtons.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void alert(Vector<TtsChunk> ttsChunks,
			String alertText1, String alertText2, String alertText3, Boolean playTone,
			Integer duration, Vector<SoftButton> softButtons, Integer correlationId) throws SdlException {
		
		Alert msg = RpcRequestFactory.buildAlert(ttsChunks, alertText1, alertText2, alertText3, playTone, duration, softButtons, correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param ttsText -The text to speech message in the form of a string.
	 * @param playTone -Defines if tone should be played.
	 * @param softButtons -A list of App defined SoftButtons.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void alert(String ttsText, Boolean playTone, Vector<SoftButton> softButtons,
			Integer correlationId) throws SdlException {
		
		alert(ttsText, null, null, null, playTone, null, softButtons, correlationId);
	}
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param chunks -A list of text/phonemes to speak in the form of ttsChunks.
	 * @param playTone -Defines if tone should be played.
	 * @param softButtons -A list of App defined SoftButtons.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void alert(Vector<TtsChunk> chunks, Boolean playTone, Vector<SoftButton> softButtons,
			Integer correlationId) throws SdlException {
		
		alert(chunks, null, null, null, playTone, null, softButtons, correlationId);
	}
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param alertText1 -The first line of the alert text field.
	 * @param alertText2 -The second line of the alert text field.
	 * @param alertText3 -The optional third line of the alert text field.
	 * @param playTone -Defines if tone should be played.
	 * @param duration -Timeout in milliseconds.
	 * @param softButtons -A list of App defined SoftButtons.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void alert(String alertText1, String alertText2, String alertText3,
			Boolean playTone, Integer duration, Vector<SoftButton> softButtons, Integer correlationId) 
			throws SdlException {
		
		alert((Vector<TtsChunk>)null, alertText1, alertText2, alertText3, playTone, duration, softButtons, correlationId);
	}
		
	/*End V1 Enhanced helper*/
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param ttsText -The text to speech message in the form of a string.
	 * @param alertText1 -The first line of the alert text field.
	 * @param alertText2 -The second line of the alert text field.
	 * @param playTone -Defines if tone should be played.
	 * @param duration -Timeout in milliseconds.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void alert(String ttsText, String alertText1,
			String alertText2, Boolean playTone, Integer duration,
			Integer correlationId) throws SdlException {

		Alert msg = RpcRequestFactory.buildAlert(ttsText, alertText1, alertText2, 
				playTone, duration, correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param ttsChunks -A list of text/phonemes to speak in the form of ttsChunks.
	 * @param alertText1 -The first line of the alert text field.
	 * @param alertText2 -The second line of the alert text field.
	 * @param playTone -Defines if tone should be played.
	 * @param duration -Timeout in milliseconds.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void alert(Vector<TtsChunk> ttsChunks,
			String alertText1, String alertText2, Boolean playTone,
			Integer duration, Integer correlationId) throws SdlException {
		
		Alert msg = RpcRequestFactory.buildAlert(ttsChunks, alertText1, alertText2, playTone,
				duration, correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param ttsText -The text to speech message in the form of a string.
	 * @param playTone -Defines if tone should be played.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void alert(String ttsText, Boolean playTone,
			Integer correlationId) throws SdlException {
		
		alert(ttsText, null, null, playTone, null, correlationId);
	}
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param chunks -A list of text/phonemes to speak in the form of ttsChunks.
	 * @param playTone -Defines if tone should be played.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void alert(Vector<TtsChunk> chunks, Boolean playTone,
			Integer correlationId) throws SdlException {
		
		alert(chunks, null, null, playTone, null, correlationId);
	}
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param alertText1 -The first line of the alert text field.
	 * @param alertText2 -The second line of the alert text field.
	 * @param playTone -Defines if tone should be played.
	 * @param duration -Timeout in milliseconds.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void alert(String alertText1, String alertText2,
			Boolean playTone, Integer duration, Integer correlationId) 
			throws SdlException {
		
		alert((Vector<TtsChunk>)null, alertText1, alertText2, playTone, duration, correlationId);
	}
	
	/**
	 * Sends a CreateInteractionChoiceSet RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param choiceSet
	 * @param interactionChoiceSetId
	 * @param correlationId
	 * @throws SdlException
	 */
	public void createInteractionChoiceSet(
			Vector<Choice> choiceSet, Integer interactionChoiceSetId,
			Integer correlationId) throws SdlException {
		
		CreateInteractionChoiceSet msg = RpcRequestFactory.buildCreateInteractionChoiceSet(
				choiceSet, interactionChoiceSetId, correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Sends a DeleteCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param commandId -ID of the command(s) to delete.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void deleteCommand(Integer commandId,
			Integer correlationId) throws SdlException {
		
		DeleteCommand msg = RpcRequestFactory.buildDeleteCommand(commandId, correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Sends a DeleteInteractionChoiceSet RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param interactionChoiceSetId -ID of the interaction choice set to delete.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void deleteInteractionChoiceSet(
			Integer interactionChoiceSetId, Integer correlationId) 
			throws SdlException {
		
		DeleteInteractionChoiceSet msg = RpcRequestFactory.buildDeleteInteractionChoiceSet(
				interactionChoiceSetId, correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Sends a DeleteSubMenu RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param menuId -The menuID of the submenu to delete.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException
	 */
	public void deleteSubMenu(Integer menuId,
			Integer correlationId) throws SdlException {
		
		DeleteSubMenu msg = RpcRequestFactory.buildDeleteSubMenu(menuId, correlationId);

		sendRpcRequest(msg);
	}
	
	
	
	/*Begin V1 Enhanced helper*/
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initPrompt -Intial prompt spoken to the user at the start of an interaction.      	
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetId -Interaction choice set IDs to use with an interaction.
	 * @param vrHelp -Suggested VR Help Items to display on-screen during Perform Interaction.      	
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void performInteraction(String initPrompt,
			String displayText, Integer interactionChoiceSetId, Vector<VrHelpItem> vrHelp,
			Integer correlationId) throws SdlException {
		
		PerformInteraction msg = RpcRequestFactory.buildPerformInteraction(initPrompt,
				displayText, interactionChoiceSetId, vrHelp, correlationId);
		
		sendRpcRequest(msg);
	}
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initPrompt -Intial prompt spoken to the user at the start of an interaction.      	
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetId -Interaction choice set IDs to use with an interaction.
	 * @param helpPrompt -Help text that is spoken when a user speaks "help" during the interaction.
	 * @param timeoutPrompt -Timeout text that is spoken when a VR interaction times out.
	 * @param interactionMode - The method in which the user is notified and uses the interaction (Manual,VR,Both).
	 * @param timeout -Timeout in milliseconds.
	 * @param vrHelp -Suggested VR Help Items to display on-screen during Perform Interaction.      	
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void performInteraction(String initPrompt,
			String displayText, Integer interactionChoiceSetId,
			String helpPrompt, String timeoutPrompt,
			InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp,
			Integer correlationId) throws SdlException {
		
		PerformInteraction msg = RpcRequestFactory.buildPerformInteraction(
				initPrompt, displayText, interactionChoiceSetId,
				helpPrompt, timeoutPrompt, interactionMode, 
				timeout, vrHelp, correlationId);
		
		sendRpcRequest(msg);
	}
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initPrompt -Intial prompt spoken to the user at the start of an interaction.      	
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetIdList -A list of interaction choice set IDs to use with an interaction.
	 * @param helpPrompt -Help text that is spoken when a user speaks "help" during the interaction.
	 * @param timeoutPrompt -Timeout text that is spoken when a VR interaction times out.
	 * @param interactionMode - The method in which the user is notified and uses the interaction (Manual,VR,Both).
	 * @param timeout -Timeout in milliseconds.
	 * @param vrHelp -Suggested VR Help Items to display on-screen during Perform Interaction.      	
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void performInteraction(String initPrompt,
			String displayText, Vector<Integer> interactionChoiceSetIdList,
			String helpPrompt, String timeoutPrompt,
			InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp,
			Integer correlationId) throws SdlException {
		
		PerformInteraction msg = RpcRequestFactory.buildPerformInteraction(initPrompt,
				displayText, interactionChoiceSetIdList,
				helpPrompt, timeoutPrompt, interactionMode, timeout, vrHelp,
				correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initChunks -A list of text/phonemes to speak for the initial prompt in the form of ttsChunks.
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetIdList -A list of interaction choice set IDs to use with an interaction.
	 * @param helpChunks -A list of text/phonemes to speak for the help text that is spoken when a user speaks "help" during the interaction.
	 * @param timeoutChunks A list of text/phonems to speak for the timeout text that is spoken when a VR interaction times out.
	 * @param interactionMode - The method in which the user is notified and uses the interaction (Manual,VR,Both).
	 * @param timeout -Timeout in milliseconds.
	 * @param vrHelp -Suggested VR Help Items to display on-screen during Perform Interaction.      	
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void performInteraction(
			Vector<TtsChunk> initChunks, String displayText,
			Vector<Integer> interactionChoiceSetIdList,
			Vector<TtsChunk> helpChunks, Vector<TtsChunk> timeoutChunks,
			InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp,
			Integer correlationId) throws SdlException {
		
		PerformInteraction msg = RpcRequestFactory.buildPerformInteraction(
				initChunks, displayText, interactionChoiceSetIdList,
				helpChunks, timeoutChunks, interactionMode, timeout,vrHelp,
				correlationId);
		
		sendRpcRequest(msg);
	}
	
	/*End V1 Enhanced*/
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initPrompt -Intial prompt spoken to the user at the start of an interaction.      	
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetId -Interaction choice set IDs to use with an interaction.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void performInteraction(String initPrompt,
			String displayText, Integer interactionChoiceSetId,
			Integer correlationId) throws SdlException {
		
		PerformInteraction msg = RpcRequestFactory.buildPerformInteraction(initPrompt,
				displayText, interactionChoiceSetId, correlationId);
		
		sendRpcRequest(msg);
	}
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initPrompt -Intial prompt spoken to the user at the start of an interaction.      	
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetId -Interaction choice set IDs to use with an interaction.
	 * @param helpPrompt -Help text that is spoken when a user speaks "help" during the interaction. 
	 * @param timeoutPrompt -Timeout text that is spoken when a VR interaction times out.
	 * @param interactionMode - The method in which the user is notified and uses the interaction (Manual,VR,Both).
	 * @param timeout -Timeout in milliseconds.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void performInteraction(String initPrompt,
			String displayText, Integer interactionChoiceSetId,
			String helpPrompt, String timeoutPrompt,
			InteractionMode interactionMode, Integer timeout,
			Integer correlationId) throws SdlException {
		
		PerformInteraction msg = RpcRequestFactory.buildPerformInteraction(
				initPrompt, displayText, interactionChoiceSetId,
				helpPrompt, timeoutPrompt, interactionMode, 
				timeout, correlationId);
		
		sendRpcRequest(msg);
	}
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initPrompt -Intial prompt spoken to the user at the start of an interaction.      	
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetIdList -A list of interaction choice set IDs to use with an interaction.
	 * @param helpPrompt -Help text that is spoken when a user speaks "help" during the interaction. 
	 * @param timeoutPrompt -Timeout text that is spoken when a VR interaction times out.
	 * @param interactionMode - The method in which the user is notified and uses the interaction (Manual,VR,Both).
	 * @param timeout -Timeout in milliseconds.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void performInteraction(String initPrompt,
			String displayText, Vector<Integer> interactionChoiceSetIdList,
			String helpPrompt, String timeoutPrompt,
			InteractionMode interactionMode, Integer timeout,
			Integer correlationId) throws SdlException {
		
		PerformInteraction msg = RpcRequestFactory.buildPerformInteraction(initPrompt,
				displayText, interactionChoiceSetIdList,
				helpPrompt, timeoutPrompt, interactionMode, timeout,
				correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initChunks -A list of text/phonemes to speak for the initial prompt in the form of ttsChunks.
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetIdList -A list of interaction choice set IDs to use with an interaction.
	 * @param helpChunks -A list of text/phonemes to speak for the help text that is spoken when a user speaks "help" during the interaction.
	 * @param timeoutChunks A list of text/phonems to speak for the timeout text that is spoken when a VR interaction times out.
	 * @param interactionMode - The method in which the user is notified and uses the interaction (Manual,VR,Both).
	 * @param timeout -Timeout in milliseconds.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void performInteraction(
			Vector<TtsChunk> initChunks, String displayText,
			Vector<Integer> interactionChoiceSetIdList,
			Vector<TtsChunk> helpChunks, Vector<TtsChunk> timeoutChunks,
			InteractionMode interactionMode, Integer timeout,
			Integer correlationId) throws SdlException {
		
		PerformInteraction msg = RpcRequestFactory.buildPerformInteraction(
				initChunks, displayText, interactionChoiceSetIdList,
				helpChunks, timeoutChunks, interactionMode, timeout,
				correlationId);
		
		sendRpcRequest(msg);
	}
	
	// Protected registerAppInterface used to ensure only non-ALM applications call
	// reqisterAppInterface
	protected void registerAppInterfacePrivate(
			SdlMsgVersion sdlMsgVersion, String appName, Vector<TtsChunk> ttsName,
			String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, 
			Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHmiType> appType,
			String appId, String autoActivateId, Integer correlationId) 
			throws SdlException {
		
		RegisterAppInterface msg = RpcRequestFactory.buildRegisterAppInterface(
				sdlMsgVersion, appName, ttsName, ngnMediaScreenAppName, vrSynonyms, isMediaApp, 
				languageDesired, hmiDisplayLanguageDesired, appType, appId, correlationId);
		
		if (bAppResumeEnabled)
		{
			if (lastHashId != null)
				msg.setHashId(lastHashId);
		}
		
		Intent sendIntent = createBroadcastIntent();
		updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionId.REGISTER_APP_INTERFACE.toString());
		updateBroadcastIntent(sendIntent, "TYPE", RpcMessage.KEY_REQUEST);
		updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationId());
		updateBroadcastIntent(sendIntent, "DATA",serializeJson(msg));
		sendBroadcastIntent(sendIntent);		
		
		sendRpcRequestPrivate(msg);
	}
	
	/*Begin V1 Enhanced helper function*/

	/**
	 * Sends a SetGlobalProperties RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param helpPrompt
	 * @param timeoutPrompt
	 * @param vrHelpTitle
	 * @param vrHelp
	 * @param correlationId
	 * @throws SdlException
	 */
	public void setGlobalProperties(
			String helpPrompt, String timeoutPrompt, String vrHelpTitle, Vector<VrHelpItem> vrHelp, Integer correlationId) 
		throws SdlException {
		
		SetGlobalProperties req = RpcRequestFactory.buildSetGlobalProperties(helpPrompt, 
				timeoutPrompt, vrHelpTitle, vrHelp, correlationId);
		
		sendRpcRequest(req);
	}
	
	/**
	 * Sends a SetGlobalProperties RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param helpChunks
	 * @param timeoutChunks
	 * @param vrHelpTitle
	 * @param vrHelp
	 * @param correlationId
	 * @throws SdlException
	 */
	public void setGlobalProperties(
			Vector<TtsChunk> helpChunks, Vector<TtsChunk> timeoutChunks, String vrHelpTitle, Vector<VrHelpItem> vrHelp,
			Integer correlationId) throws SdlException {
		
		SetGlobalProperties req = RpcRequestFactory.buildSetGlobalProperties(
				helpChunks, timeoutChunks, vrHelpTitle, vrHelp, correlationId);

		sendRpcRequest(req);
	}

	/*End V1 Enhanced helper function*/	
	
	/**
	 * Sends a SetGlobalProperties RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param helpPrompt
	 * @param timeoutPrompt
	 * @param correlationId
	 * @throws SdlException
	 */
	public void setGlobalProperties(
			String helpPrompt, String timeoutPrompt, Integer correlationId) 
		throws SdlException {
		
		SetGlobalProperties req = RpcRequestFactory.buildSetGlobalProperties(helpPrompt, 
				timeoutPrompt, correlationId);
		
		sendRpcRequest(req);
	}
	
	/**
	 * Sends a SetGlobalProperties RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param helpChunks
	 * @param timeoutChunks
	 * @param correlationId
	 * @throws SdlException
	 */
	public void setGlobalProperties(
			Vector<TtsChunk> helpChunks, Vector<TtsChunk> timeoutChunks,
			Integer correlationId) throws SdlException {
		
		SetGlobalProperties req = RpcRequestFactory.buildSetGlobalProperties(
				helpChunks, timeoutChunks, correlationId);

		sendRpcRequest(req);
	}
	
	public void resetGlobalProperties(Vector<GlobalProperty> properties,
			Integer correlationId) throws SdlException {
		
		ResetGlobalProperties req = new ResetGlobalProperties();
		
		req.setCorrelationId(correlationId);
		req.setProperties(properties);
		
		sendRpcRequest(req);
	}
	                                                        
	
	/**
	 * Sends a SetMediaClockTimer RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @param updateMode
	 * @param correlationId
	 * @throws SdlException
	 */
	public void setMediaClockTimer(Integer hours,
			Integer minutes, Integer seconds, UpdateMode updateMode,
			Integer correlationId) throws SdlException {

		SetMediaClockTimer msg = RpcRequestFactory.buildSetMediaClockTimer(hours,
				minutes, seconds, updateMode, correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Pauses the media clock. Responses are captured through callback on IProxyListener.
	 * 
	 * @param correlationId
	 * @throws SdlException
	 */
	public void pauseMediaClockTimer(Integer correlationId) 
			throws SdlException {

		SetMediaClockTimer msg = RpcRequestFactory.buildSetMediaClockTimer(0,
				0, 0, UpdateMode.PAUSE, correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Resumes the media clock. Responses are captured through callback on IProxyListener.
	 * 
	 * @param correlationId
	 * @throws SdlException
	 */
	public void resumeMediaClockTimer(Integer correlationId) 
			throws SdlException {

		SetMediaClockTimer msg = RpcRequestFactory.buildSetMediaClockTimer(0,
				0, 0, UpdateMode.RESUME, correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Clears the media clock. Responses are captured through callback on IProxyListener.
	 * 
	 * @param correlationId
	 * @throws SdlException
	 */
	public void clearMediaClockTimer(Integer correlationId) 
			throws SdlException {

		Show msg = RpcRequestFactory.buildShow(null, null, null, "     ", null, null, correlationId);

		sendRpcRequest(msg);
	}
		
	/*Begin V1 Enhanced helper*/
	/**
	 * Sends a Show RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param mainText1 -Text displayed in a single or upper display line.
	 * @param mainText2 -Text displayed on the second display line.
	 * @param mainText3 -Text displayed on the second "page" first display line.
	 * @param mainText4 -Text displayed on the second "page" second display line.
	 * @param statusBar
	 * @param mediaClock -Text value for MediaClock field.
	 * @param mediaTrack -Text displayed in the track field.
	 * @param graphic -Image struct determining whether static or dynamic image to display in app.
	 * @param softButtons -App defined SoftButtons.
	 * @param customPresets -App labeled on-screen presets.
	 * @param alignment -Specifies how mainText1 and mainText2s texts should be aligned on display.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void show(String mainText1, String mainText2, String mainText3, String mainText4,
			String statusBar, String mediaClock, String mediaTrack,
			Image graphic, Vector<SoftButton> softButtons, Vector <String> customPresets,
			TextAlignment alignment, Integer correlationId) 
			throws SdlException {
		
		Show msg = RpcRequestFactory.buildShow(mainText1, mainText2, mainText3, mainText4,
				statusBar, mediaClock, mediaTrack, graphic, softButtons, customPresets,
				alignment, correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Sends a Show RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param mainText1 -Text displayed in a single or upper display line.
	 * @param mainText2 -Text displayed on the second display line.
	 * @param mainText3 -Text displayed on the second "page" first display line.
	 * @param mainText4 -Text displayed on the second "page" second display line.
	 * @param graphic -Image struct determining whether static or dynamic image to display in app.
	 * @param softButtons -App defined SoftButtons.
	 * @param customPresets -App labeled on-screen presets.
	 * @param alignment -Specifies how mainText1 and mainText2s texts should be aligned on display.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void show(String mainText1, String mainText2, String mainText3, String mainText4,
			Image graphic, Vector<SoftButton> softButtons, Vector <String> customPresets,
			TextAlignment alignment, Integer correlationId) 
			throws SdlException {
		
		show(mainText1, mainText2, mainText3, mainText4, null, null, null, graphic, softButtons, customPresets, alignment, correlationId);
	}		
	/*End V1 Enhanced helper*/
	
	/**
	 * Sends a Show RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param mainText1 -Text displayed in a single or upper display line.
	 * @param mainText2 -Text displayed on the second display line.
	 * @param statusBar
	 * @param mediaClock -Text value for MediaClock field.
	 * @param mediaTrack -Text displayed in the track field.
	 * @param alignment -Specifies how mainText1 and mainText2s texts should be aligned on display.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void show(String mainText1, String mainText2,
			String statusBar, String mediaClock, String mediaTrack,
			TextAlignment alignment, Integer correlationId) 
			throws SdlException {
		
		Show msg = RpcRequestFactory.buildShow(mainText1, mainText2,
				statusBar, mediaClock, mediaTrack,
				alignment, correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Sends a Show RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param mainText1 -Text displayed in a single or upper display line.
	 * @param mainText2 -Text displayed on the second display line.
	 * @param alignment -Specifies how mainText1 and mainText2s texts should be aligned on display.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void show(String mainText1, String mainText2,
			TextAlignment alignment, Integer correlationId) 
			throws SdlException {
		
		show(mainText1, mainText2, null, null, null, alignment, correlationId);
	}
	
	/**
	 * Sends a Speak RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param ttsText -The text to speech message in the form of a string.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void speak(String ttsText, Integer correlationId) 
			throws SdlException {
		
		Speak msg = RpcRequestFactory.buildSpeak(TtsChunkFactory.createSimpleTtsChunks(ttsText),
				correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Sends a Speak RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param ttsChunks -Text/phonemes to speak in the form of ttsChunks.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void speak(Vector<TtsChunk> ttsChunks,
			Integer correlationId) throws SdlException {

		Speak msg = RpcRequestFactory.buildSpeak(ttsChunks, correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Sends a SubscribeButton RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param buttonName -Name of the button to subscribe.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void subscribeButton(ButtonName buttonName,
			Integer correlationId) throws SdlException {

		SubscribeButton msg = RpcRequestFactory.buildSubscribeButton(buttonName,
				correlationId);

		sendRpcRequest(msg);
	}
	
	// Protected unregisterAppInterface used to ensure no non-ALM app calls
	// unregisterAppInterface.
	protected void unregisterAppInterfacePrivate(Integer correlationId) 
		throws SdlException {

		UnregisterAppInterface msg = 
				RpcRequestFactory.buildUnregisterAppInterface(correlationId);
		Intent sendIntent = createBroadcastIntent();

		updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionId.UNREGISTER_APP_INTERFACE.toString());
		updateBroadcastIntent(sendIntent, "TYPE", RpcMessage.KEY_REQUEST);
		updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationId());
		updateBroadcastIntent(sendIntent, "DATA",serializeJson(msg));
		sendBroadcastIntent(sendIntent);
		
		sendRpcRequestPrivate(msg);
	}
	
	/**
	 * Sends an UnsubscribeButton RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param buttonName -Name of the button to unsubscribe.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	 */
	public void unsubscribeButton(ButtonName buttonName, 
			Integer correlationId) throws SdlException {

		UnsubscribeButton msg = RpcRequestFactory.buildUnsubscribeButton(
				buttonName, correlationId);

		sendRpcRequest(msg);
	}
	
	/**
	 * Creates a choice to be added to a choiceset. Choice has both a voice and a visual menu component.
	 * 
	 * @param choiceId -Unique ID used to identify this choice (returned in callback).
	 * @param choiceMenuName -Text name displayed for this choice.
	 * @param choiceVrCommands -Vector of vrCommands used to select this choice by voice. Must contain
	 * 			at least one non-empty element.
	 * @return Choice created. 
	 * @throws SdlException 
	 */
	public Choice createChoiceSetChoice(Integer choiceId, String choiceMenuName,
			Vector<String> choiceVrCommands) {		
		Choice returnChoice = new Choice();
		
		returnChoice.setChoiceId(choiceId);
		returnChoice.setMenuName(choiceMenuName);
		returnChoice.setVrCommands(choiceVrCommands);
		
		return returnChoice;
	}
	
	/**
	 * Starts audio pass thru session. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initialPrompt -SDL will speak this prompt before opening the audio pass thru session.
	 * @param audioPassThruDisplayText1 -First line of text displayed during audio capture.
	 * @param audioPassThruDisplayText2 -Second line of text displayed during audio capture.
	 * @param samplingRate -Allowable values of 8 khz or 16 or 22 or 44 khz.
	 * @param maxDuration -The maximum duration of audio recording in milliseconds.
	 * @param bitsPerSample -Specifies the quality the audio is recorded. Currently 8 bit or 16 bit.
	 * @param audioType -Specifies the type of audio data being requested.
	 * @param muteAudio -Defines if the current audio source should be muted during the APT session.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException 
	 */
	public void performAudioPassThru(String initialPrompt, String audioPassThruDisplayText1, String audioPassThruDisplayText2,
			  SamplingRate samplingRate, Integer maxDuration, BitsPerSample bitsPerSample,
			  AudioType audioType, Boolean muteAudio, Integer correlationId) throws SdlException {		

		PerformAudioPassThru msg = RpcRequestFactory.buildPerformAudioPassThru(initialPrompt, audioPassThruDisplayText1, audioPassThruDisplayText2, 
																				samplingRate, maxDuration, bitsPerSample, audioType, muteAudio, correlationId);
		sendRpcRequest(msg);
	}

	/**
	 * Ends audio pass thru session. Responses are captured through callback on IProxyListener.
	 * 
	 * @param correlationId
	 * @throws SdlException 
	 */
	public void endAudioPassThru(Integer correlationId) throws SdlException 
	{
		EndAudioPassThru msg = RpcRequestFactory.buildEndAudioPassThru(correlationId);		
		sendRpcRequest(msg);
	}
	
	/**
	 *     Subscribes for specific published data items.  The data will be only sent if it has changed.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param gps -Subscribes to GPS data.
	 * @param speed -Subscribes to vehicle speed data in kilometers per hour.
	 * @param rpm -Subscribes to number of revolutions per minute of the engine.
	 * @param fuelLevel -Subscribes to fuel level in the tank (percentage).
	 * @param fuelLevelState -Subscribes to fuel level state.
	 * @param instantFuelConsumption -Subscribes to instantaneous fuel consumption in microlitres.
	 * @param externalTemperature -Subscribes to the external temperature in degrees celsius.
	 * @param prndl -Subscribes to PRNDL data that houses the selected gear.
	 * @param tirePressure -Subscribes to the TireStatus data containing status and pressure of tires. 
	 * @param odometer -Subscribes to Odometer data in km.
	 * @param beltStatus -Subscribes to status of the seat belts.
	 * @param bodyInformation -Subscribes to body information including power modes.
	 * @param deviceStatus -Subscribes to device status including signal and battery strength.
	 * @param driverBraking -Subscribes to the status of the brake pedal.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	*/
	public void subscribeVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevelState,
									 boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure,						
									 boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
									 boolean driverBraking, Integer correlationId) throws SdlException
	{
		SubscribeVehicleData msg = RpcRequestFactory.buildSubscribeVehicleData(gps, speed, rpm, fuelLevel, fuelLevelState, instantFuelConsumption, externalTemperature, prndl, tirePressure, 
																				odometer, beltStatus, bodyInformation, deviceStatus, driverBraking, correlationId);
		
		sendRpcRequest(msg);
	}
	
	/**
	 *     Unsubscribes for specific published data items.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param gps -Unsubscribes to GPS data.
	 * @param speed -Unsubscribes to vehicle speed data in kilometers per hour.
	 * @param rpm -Unsubscribes to number of revolutions per minute of the engine.
	 * @param fuelLevel -Unsubscribes to fuel level in the tank (percentage).
	 * @param fuelLevel_State -Unsubscribes to fuel level state.
	 * @param instantFuelConsumption -Unsubscribes to instantaneous fuel consumption in microlitres.
	 * @param externalTemperature -Unsubscribes to the external temperature in degrees celsius.
	 * @param prndl -Unsubscribes to PRNDL data that houses the selected gear.
	 * @param tirePressure -Unsubscribes to the TireStatus data containing status and pressure of tires. 
	 * @param odometer -Unsubscribes to Odometer data in km.
	 * @param beltStatus -Unsubscribes to status of the seat belts.
	 * @param bodyInformation -Unsubscribes to body information including power modes.
	 * @param deviceStatus -Unsubscribes to device status including signal and battery strength.
	 * @param driverBraking -Unsubscribes to the status of the brake pedal.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	*/

	public void unsubscribeVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State,
			 						   boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure,
			 						   boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
			 						   boolean driverBraking, Integer correlationID) throws SdlException
	{
		UnsubscribeVehicleData msg = RpcRequestFactory.buildUnsubscribeVehicleData(gps, speed, rpm, fuelLevel, fuelLevel_State, instantFuelConsumption, externalTemperature, prndl, tirePressure,
																					odometer, beltStatus, bodyInformation, deviceStatus, driverBraking, correlationID);
		sendRpcRequest(msg);
	}


	/**
	 *     Performs a Non periodic vehicle data read request.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param gps -Performs an ad-hoc request for GPS data.
	 * @param speed -Performs an ad-hoc request for vehicle speed data in kilometers per hour.
	 * @param rpm -Performs an ad-hoc request for number of revolutions per minute of the engine.
	 * @param fuelLevel -Performs an ad-hoc request for fuel level in the tank (percentage).
	 * @param fuelLevelState -Performs an ad-hoc request for fuel level state.
	 * @param instantFuelConsumption -Performs an ad-hoc request for instantaneous fuel consumption in microlitres.
	 * @param externalTemperature -Performs an ad-hoc request for the external temperature in degrees celsius.
	 * @param vin -Performs an ad-hoc request for the Vehicle identification number
	 * @param prndl -Performs an ad-hoc request for PRNDL data that houses the selected gear.
	 * @param tirePressure -Performs an ad-hoc request for the TireStatus data containing status and pressure of tires. 
	 * @param odometer -Performs an ad-hoc request for Odometer data in km.
	 * @param beltStatus -Performs an ad-hoc request for status of the seat belts.
	 * @param bodyInformation -Performs an ad-hoc request for  body information including power modes.
	 * @param deviceStatus -Performs an ad-hoc request for device status including signal and battery strength.
	 * @param driverBraking -Performs an ad-hoc request for the status of the brake pedal.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	*/
	public void getVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevelState,
			 				   boolean instantFuelConsumption, boolean externalTemperature, boolean vin, boolean prndl, boolean tirePressure,
			 				   boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
			 				   boolean driverBraking, Integer correlationId) throws SdlException
	{
	
		GetVehicleData msg = RpcRequestFactory.buildGetVehicleData(gps, speed, rpm, fuelLevel, fuelLevelState, instantFuelConsumption, externalTemperature, vin, prndl, tirePressure, odometer,
																   beltStatus, bodyInformation, deviceStatus, driverBraking, correlationId);
		sendRpcRequest(msg);
	}


	/**
	 *     Creates a full screen overlay containing a large block of formatted text that can be scrolled with up to 8 SoftButtons defined.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param scrollableMessageBody -Body of text that can include newlines and tabs.
	 * @param timeout -App defined timeout.  Indicates how long of a timeout from the last action.
	 * @param softButtons -App defined SoftButtons.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	*/		
	public void scrollableMessage(String scrollableMessageBody, Integer timeout, Vector<SoftButton> softButtons, Integer correlationId) throws SdlException
	{
		ScrollableMessage msg = RpcRequestFactory.buildScrollableMessage(scrollableMessageBody, timeout, softButtons, correlationId);		
		sendRpcRequest(msg);
	}


	/**
	 *     Creates a full screen or pop-up overlay (depending on platform) with a single user controlled slider.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param numTicks -Number of selectable items on a horizontal axis.
	 * @param position -Initial position of slider control (cannot exceed numTicks).
	 * @param sliderHeader -Text header to display.
	 * @param sliderFooter - Text footer to display (meant to display min/max threshold descriptors).
	 * @param timeout -App defined timeout.  Indicates how long of a timeout from the last action.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	*/	
	public void slider(Integer numTicks, Integer position, String sliderHeader, Vector<String> sliderFooter, Integer timeout, Integer correlationId) throws SdlException
	{
		Slider msg = RpcRequestFactory.buildSlider(numTicks, position, sliderHeader, sliderFooter, timeout, correlationId);		
		sendRpcRequest(msg);		
	}

	/**
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param language
	 * @param hmiDisplayLanguage
	 * @param correlationId
	 * @throws SdlException
	*/	
	public void changeRegistration(Language language, Language hmiDisplayLanguage, Integer correlationId) throws SdlException
	{
		ChangeRegistration msg = RpcRequestFactory.buildChangeRegistration(language, hmiDisplayLanguage, correlationId);
		sendRpcRequest(msg);
	}
	
	/** 
	 * Used to push a binary stream of file data onto the module from a mobile device.
	 * Responses are captured through callback on IProxyListener.
	 * 
	 * @param is - The input stream of byte data that putFileStream will read from 
	 * @param sdlFileName - The file reference name used by the putFile RPC.
	 * @param iOffset - The data offset in bytes, a value of zero is used to indicate data starting from the beginging of the file.
	 * A value greater than zero is used for resuming partial data chunks.
	 * @param iLength - The total length of the file being sent.
	 * @throws SdlException
	*/
	public void putFileStream(InputStream is, String sdlFileName, Integer iOffset, Integer iLength) throws SdlException 
	{
		PutFile msg = RpcRequestFactory.buildPutFile(sdlFileName, iOffset, iLength);		
		startRpcStream(is, msg);
	}
	
	/** 
	 * Used to push a binary stream of file data onto the module from a mobile device.
	 * Responses are captured through callback on IProxyListener.
	 * 
	 * @param sdlFileName - The file reference name used by the putFile RPC.
	 * @param iOffset - The data offset in bytes, a value of zero is used to indicate data starting from the beginging of a file.
	 * A value greater than zero is used for resuming partial data chunks.
	 * @param iLength - The total length of the file being sent.
	 * 
	 * @return OutputStream - The output stream of byte data that is written to by the app developer
	 * @throws SdlException
	*/
	public OutputStream putFileStream(String sdlFileName, Integer iOffset, Integer iLength) throws SdlException 
	{
		PutFile msg = RpcRequestFactory.buildPutFile(sdlFileName, iOffset, iLength);		
		return startRpcStream(msg);
	}	

	/**
	 * Used to push a binary stream of file data onto the module from a mobile device.
	 * Responses are captured through callback on IProxyListener.
	 *
	 * @param is - The input stream of byte data that PutFileStream will read from
	 * @param sdlFileName - The file reference name used by the putFile RPC.
	 * @param iOffset - The data offset in bytes, a value of zero is used to indicate data starting from the beginging of the file.
	 * A value greater than zero is used for resuming partial data chunks.
	 * @param iLength - The total length of the file being sent.
	 * @param fileType - The selected file type -- see the FileType enumeration for details
	 * @param bPersistentFile - Indicates if the file is meant to persist between sessions / ignition cycles.
	 * @param  bSystemFile - Indicates if the file is meant to be passed thru core to elsewhere on the system.
	 * @throws SdlException
	*/
	public void putFileStream(InputStream is, String sdlFileName, Integer iOffset, Integer iLength, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile) throws SdlException
	{
		PutFile msg = RpcRequestFactory.buildPutFile(sdlFileName, iOffset, iLength, fileType, bPersistentFile, bSystemFile);
		startRpcStream(is, msg);
	}
	
	/**
	 * Used to push a binary stream of file data onto the module from a mobile device.
	 * Responses are captured through callback on IProxyListener.
	 *
	 * @param sdlFileName - The file reference name used by the putFile RPC.
	 * @param iOffset - The data offset in bytes, a value of zero is used to indicate data starting from the beginging of a file.
	 * A value greater than zero is used for resuming partial data chunks.
	 * @param iLength - The total length of the file being sent.
	 * @param fileType - The selected file type -- see the FileType enumeration for details
	 * @param bPersistentFile - Indicates if the file is meant to persist between sessions / ignition cycles.
	 * @param  bSystemFile - Indicates if the file is meant to be passed thru core to elsewhere on the system.
	 * @return OutputStream - The output stream of byte data that is written to by the app developer
	 * @throws SdlException
	*/
	public OutputStream putFileStream(String sdlFileName, Integer iOffset, Integer iLength, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile) throws SdlException
	{
		PutFile msg = RpcRequestFactory.buildPutFile(sdlFileName, iOffset, iLength, fileType, bPersistentFile, bSystemFile);
		return startRpcStream(msg);
	}
	
	/**
	 * Used to push a stream of putfile RPC's containing binary data from a mobile device to the module.
	 * Responses are captured through callback on IProxyListener.
	 *
	 * @param sPath - The physical file path on the mobile device.
	 * @param sdlFileName - The file reference name used by the putFile RPC.
	 * @param iOffset - The data offset in bytes, a value of zero is used to indicate data starting from the beginging of a file.
	 * A value greater than zero is used for resuming partial data chunks.
	 * @param fileType - The selected file type -- see the FileType enumeration for details
	 * @param bPersistentFile - Indicates if the file is meant to persist between sessions / ignition cycles.
	 * @param  bSystemFile - Indicates if the file is meant to be passed thru core to elsewhere on the system.
	 * @param CORRELATION_ID - A unique ID that correlates each RPCRequest and RPCResponse.
	 * @return RPCStreamController - If the putFileStream was not started successfully null is returned, otherwise a valid object reference is returned 
	 * @throws SdlException
	*/	
	public RpcStreamController putFileStream(String sPath, String sdlFileName, Integer iOffset, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile, Integer iCorrelationId) throws SdlException 
	{
		PutFile msg = RpcRequestFactory.buildPutFile(sdlFileName, iOffset, 0, fileType, bPersistentFile, bSystemFile, iCorrelationId);
		return startPutFileStream(sPath, msg);
	}

	/**
	 * Used to push a stream of putfile RPC's containing binary data from a mobile device to the module.
	 * Responses are captured through callback on IProxyListener.
	 *
	 * @param is - The input stream of byte data that putFileStream will read from.
	 * @param sdlFileName - The file reference name used by the putFile RPC.
	 * @param iOffset - The data offset in bytes, a value of zero is used to indicate data starting from the beginging of a file.
	 * A value greater than zero is used for resuming partial data chunks.
	 * @param fileType - The selected file type -- see the FileType enumeration for details
	 * @param bPersistentFile - Indicates if the file is meant to persist between sessions / ignition cycles.
	 * @param  bSystemFile - Indicates if the file is meant to be passed thru core to elsewhere on the system.
	 * @param CORRELATION_ID - A unique ID that correlates each RPCRequest and RPCResponse.
	 * @return RPCStreamController - If the putFileStream was not started successfully null is returned, otherwise a valid object reference is returned 
	 * @throws SdlException
	*/	
	public RpcStreamController putFileStream(InputStream is, String sdlFileName, Integer iOffset, Integer iLength, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile, Integer iCorrelationId) throws SdlException 
	{
		PutFile msg = RpcRequestFactory.buildPutFile(sdlFileName, iOffset, iLength, fileType, bPersistentFile, bSystemFile, iCorrelationId);
		return startPutFileStream(is, msg);
	}

	/**
	 *
	 * Used to end an existing putFileStream that was previously initiated with any putFileStream method.
	 *
	 */
	public void endPutFileStream()
	{
		endRpcStream();
	}
	
	
	/**
	 *     Used to push a binary data onto the SDL module from a mobile device, such as icons and album art.  Not supported on first generation SDL vehicles.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param sdlFileName -File reference name.
	 * @param fileType -Selected file type.
	 * @param persistentFile -Indicates if the file is meant to persist between sessions / ignition cycles.
	 * @param fileData
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	*/	
	public void putFile(String sdlFileName, FileType fileType, Boolean persistentFile, byte[] fileData, Integer correlationId) throws SdlException 
	{
		PutFile msg = RpcRequestFactory.buildPutFile(sdlFileName, fileType, persistentFile, fileData, correlationId);
		sendRpcRequest(msg);
	}
	
	/**
	 *     Used to delete a file resident on the SDL module in the app's local cache.  Not supported on first generation SDL vehicles.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param sdlFileName -File reference name.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	*/	
	public void deleteFile(String sdlFileName, Integer correlationId) throws SdlException 
	{
		DeleteFile msg = RpcRequestFactory.buildDeleteFile(sdlFileName, correlationId);
		sendRpcRequest(msg);
	}
	
	/**
	 *     Requests the current list of resident filenames for the registered app.  Not supported on first generation SDL vehicles.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	*/	
	public void listFiles(Integer correlationId) throws SdlException
	{
		ListFiles msg = RpcRequestFactory.buildListFiles(correlationId);
		sendRpcRequest(msg);
	}

	/**
	 *     Used to set existing local file on SDL as the app's icon.  Not supported on first generation SDL vehicles.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param sdlFileName -File reference name.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	*/	
	public void setAppIcon(String sdlFileName, Integer correlationId) throws SdlException 
	{
		SetAppIcon msg = RpcRequestFactory.buildSetAppIcon(sdlFileName, correlationId);
		sendRpcRequest(msg);
	}
	
	/**
	 *     Set an alternate display layout. If not sent, default screen for given platform will be shown.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param displayLayout -Predefined or dynamically created screen layout.
	 * @param correlationId -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException
	*/	
	public void setDisplayLayout(String displayLayout, Integer correlationId) throws SdlException
	{
		SetDisplayLayout msg = RpcRequestFactory.buildSetDisplayLayout(displayLayout, correlationId);
		sendRpcRequest(msg);
	}
	
	/******************** END Public Helper Methods *************************/
	
	/**
	 * Gets type of transport currently used by this SdlProxy.
	 * 
	 * @return One of TransportType enumeration values.
	 * 
	 * @see TransportType
	 */
	public TransportType getCurrentTransportType() throws IllegalStateException {
		if (sdlSession  == null) {
			throw new IllegalStateException("Incorrect state of SdlProxyBase: Calling for getCurrentTransportType() while connection is not initialized");
		}
			
		return sdlSession.getCurrentTransportType();
	}
	
	public IProxyListenerBase getProxyListener()
	{
		return proxyListener;
	}
	
	public String getAppName()
	{
		return applicationName;
	}

	public String getNgnAppName()
	{
		return ngnMediaScreenAppName;
	}

	public String getAppId()
	{
		return appId;
	}

	public long getInstanceDt()
	{
		return instanceDateTime;
	}
	public void setConnectionDetails(String sDetails)
	{
		sConnectionDetails = sDetails;
	}
	public String getConnectionDetails()
	{
		return sConnectionDetails;
	}
	//for testing only
	public void setPoliciesUrl(String sText)
	{
		sPoliciesUrl = sText;
	}
	//for testing only
	public String getPoliciesUrl()
	{
		return sPoliciesUrl;
	}
	
} // end-class
