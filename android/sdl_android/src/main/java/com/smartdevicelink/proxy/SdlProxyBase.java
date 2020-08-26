/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Display;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.Surface;

import androidx.annotation.NonNull;

import com.livio.taskmaster.Taskmaster;
import com.smartdevicelink.BuildConfig;
import com.smartdevicelink.Dispatcher.IDispatchingStrategy;
import com.smartdevicelink.Dispatcher.ProxyMessageDispatcher;
import com.smartdevicelink.SdlConnection.ISdlSessionListener;
import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.encoder.VirtualDisplayEncoder;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.SdlExceptionCause;
import com.smartdevicelink.managers.ServiceEncryptionListener;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.managers.lifecycle.RpcConverter;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.MessageType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.LockScreenManager.OnLockScreenIconDownloadedListener;
import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;
import com.smartdevicelink.proxy.callbacks.OnError;
import com.smartdevicelink.proxy.callbacks.OnProxyClosed;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.proxy.interfaces.IProxyListenerBase;
import com.smartdevicelink.proxy.interfaces.IPutFileResponseListener;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
//import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.*;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.AudioStreamingState;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.GlobalProperty;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.enums.SdlInterfaceAvailability;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.enums.TouchType;
import com.smartdevicelink.proxy.rpc.enums.UpdateMode;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.streaming.StreamRPCPacketizer;
import com.smartdevicelink.streaming.audio.AudioStreamingCodec;
import com.smartdevicelink.streaming.audio.AudioStreamingParams;
import com.smartdevicelink.streaming.video.SdlRemoteDisplay;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.trace.SdlTrace;
import com.smartdevicelink.trace.TraceDeviceInfo;
import com.smartdevicelink.trace.enums.InterfaceActivityDirection;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.SiphonServer;
import com.smartdevicelink.transport.TCPTransportConfig;
import com.smartdevicelink.transport.USBTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.CorrelationIdGenerator;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.FileUtls;
import com.smartdevicelink.util.Version;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;

//import com.smartdevicelink.managers.video.HapticInterfaceManager;


/**
 * @deprecated use {@link com.smartdevicelink.managers.SdlManager} instead.
 *
 * The guide created for the initial transition of SdlProxyBase to SdlManager can be found at
 * <a href="https://smartdevicelink.com/en/guides/android/migrating-to-newer-sdl-versions/updating-to-v47/">Migrating to SDL Manager</a>
 */
@SuppressWarnings({"WeakerAccess", "Convert2Diamond"})
@Deprecated
public abstract class SdlProxyBase<proxyListenerType extends IProxyListenerBase> {
	// Used for calls to Android Log class.
	public static final String TAG = "SdlProxy";
	private static final String SDL_LIB_TRACE_KEY = "42baba60-eb57-11df-98cf-0800200c9a66";
	private static final int PROX_PROT_VER_ONE = 1;
	private static final int RESPONSE_WAIT_TIME = 2000;

	public static final com.smartdevicelink.util.Version MAX_SUPPORTED_RPC_VERSION = new com.smartdevicelink.util.Version("6.0.0");

	private SdlSession sdlSession = null;
	private proxyListenerType _proxyListener = null;
	
	protected Service _appService = null;
	private Context _appContext;
	private String sPoliciesURL = ""; //for testing only

	// Protected Correlation IDs
	private final int 	REGISTER_APP_INTERFACE_CORRELATION_ID = 65529,
						UNREGISTER_APP_INTERFACE_CORRELATION_ID = 65530,
						POLICIES_CORRELATION_ID = 65535;
	
	// Sdl Synchronization Objects
	private static final Object CONNECTION_REFERENCE_LOCK = new Object(),
								INCOMING_MESSAGE_QUEUE_THREAD_LOCK = new Object(),
								OUTGOING_MESSAGE_QUEUE_THREAD_LOCK = new Object(),
								INTERNAL_MESSAGE_QUEUE_THREAD_LOCK = new Object(),
								ON_UPDATE_LISTENER_LOCK = new Object(),
								RPC_LISTENER_LOCK = new Object(),
								ON_NOTIFICATION_LISTENER_LOCK = new Object();
	
	private final Object APP_INTERFACE_REGISTERED_LOCK = new Object();
		
	private int iFileCount = 0;

	private boolean navServiceStartResponseReceived = false;
	private boolean navServiceStartResponse = false;
	private List<String> navServiceStartRejectedParams = null;
	private boolean pcmServiceStartResponseReceived = false;
	private boolean pcmServiceStartResponse = false;
	@SuppressWarnings("FieldCanBeLocal")
	private List<String> pcmServiceStartRejectedParams = null;
	private boolean navServiceEndResponseReceived = false;
	private boolean navServiceEndResponse = false;
	private boolean pcmServiceEndResponseReceived = false;
	private boolean pcmServiceEndResponse = false;
	private boolean rpcProtectedResponseReceived = false;
	private boolean rpcProtectedStartResponse = false;
	
	// Device Info for logging
	private TraceDeviceInfo _traceDeviceInterrogator = null;
		
	// Declare Queuing Threads
	private ProxyMessageDispatcher<ProtocolMessage> _incomingProxyMessageDispatcher;
	private ProxyMessageDispatcher<ProtocolMessage> _outgoingProxyMessageDispatcher;
	private ProxyMessageDispatcher<InternalProxyMessage> _internalProxyMessageDispatcher;
	
	// Flag indicating if callbacks should be called from UIThread
	private Boolean _callbackToUIThread = false;
	// UI Handler
	private Handler _mainUIHandler = null; 
	final int HEARTBEAT_CORRELATION_ID = 65531;
		
	// SdlProxy Advanced Lifecycle Management
	protected Boolean _advancedLifecycleManagementEnabled = false;
	// Parameters passed to the constructor from the app to register an app interface
	private String _applicationName = null;
	private final long instanceDateTime = System.currentTimeMillis();
	private String sConnectionDetails = "N/A";
	private Vector<TTSChunk> _ttsName = null;
	private String _ngnMediaScreenAppName = null;
	private Boolean _isMediaApp = null;
	private Language _sdlLanguageDesired = null;
	private Language _hmiDisplayLanguageDesired = null;
	private Vector<AppHMIType> _appType = null;
	private String _appID = null;
	private TemplateColorScheme _dayColorScheme = null;
	private TemplateColorScheme _nightColorScheme = null;
	@SuppressWarnings({"FieldCanBeLocal", "unused"}) //Need to understand what this is used for
	private String _autoActivateIdDesired = null;
	private String _lastHashID = null;
	private SdlMsgVersion _sdlMsgVersionRequest = null;
	private Vector<String> _vrSynonyms = null;
	private boolean _bAppResumeEnabled = false;
	private OnSystemRequest lockScreenIconRequest = null;
	private TelephonyManager telephonyManager = null;
	private DeviceInfo deviceInfo = null;
	private ISdlServiceListener navServiceListener;
	
	/**
	 * Contains current configuration for the transport that was selected during 
	 * construction of this object
	 */
	private BaseTransportConfig _transportConfig = null;
	// Proxy State Variables
	protected Boolean _appInterfaceRegisterd = false;
	protected Boolean _preRegisterd = false;
	@SuppressWarnings({"unused", "FieldCanBeLocal"})
    private Boolean _haveReceivedFirstNonNoneHMILevel = false;
	protected Boolean _haveReceivedFirstFocusLevel = false;
	protected Boolean _haveReceivedFirstFocusLevelFull = false;
	protected Boolean _proxyDisposed = false;
	//protected SdlConnectionState _sdlConnectionState = null;
	protected SdlInterfaceAvailability _sdlIntefaceAvailablity = null;
	protected HMILevel _hmiLevel = null;
	protected OnHMIStatus lastHmiStatus;
	protected AudioStreamingState _audioStreamingState = null;
	// Variables set by RegisterAppInterfaceResponse
	protected SdlMsgVersion _sdlMsgVersion = null;
	protected String _autoActivateIdReturned = null;
	protected Language _sdlLanguage = null;
	protected Language _hmiDisplayLanguage = null;
	protected List<PrerecordedSpeech> _prerecordedSpeech = null;
	protected VehicleType _vehicleType = null;
	protected String _systemSoftwareVersion = null;
	protected List<Integer> _diagModes = null;
	protected Boolean firstTimeFull = true;
	protected String _proxyVersionInfo = null;
	protected Boolean _bResumeSuccess = false;	
	protected List<Class<? extends SdlSecurityBase>> _secList = null;
	protected SystemCapabilityManager _systemCapabilityManager;
	protected Boolean _iconResumed = false;
	protected RegisterAppInterfaceResponse raiResponse = null;
	
	private final CopyOnWriteArrayList<IPutFileResponseListener> _putFileListenerList = new CopyOnWriteArrayList<IPutFileResponseListener>();

	protected com.smartdevicelink.util.Version protocolVersion = new com.smartdevicelink.util.Version(1,0,0);
	protected com.smartdevicelink.util.Version rpcSpecVersion;
	
	protected SparseArray<OnRPCResponseListener> rpcResponseListeners = null;
	protected SparseArray<CopyOnWriteArrayList<OnRPCNotificationListener>> rpcNotificationListeners = null;
	protected SparseArray<CopyOnWriteArrayList<OnRPCRequestListener>> rpcRequestListeners = null;
	protected SparseArray<CopyOnWriteArrayList<OnRPCListener>> rpcListeners = null;

	protected VideoStreamingManager manager; //Will move to SdlSession once the class becomes public

	protected String authToken;

	private Version minimumProtocolVersion;
	private Version minimumRPCVersion;

	private Set<String> encryptionRequiredRPCs = new HashSet<>();
	private boolean rpcSecuredServiceStarted;
	private ServiceEncryptionListener serviceEncryptionListener;
	private Taskmaster taskmaster;

	// Interface broker
	private SdlInterfaceBroker _interfaceBroker = null;
	//We create an easily passable anonymous class of the interface so that we don't expose the internal interface to developers
	private ISdl _internalInterface = new ISdl() {
		@Override
		public void start() {
			try{
				initializeProxy();
			}catch (SdlException e){
				e.printStackTrace();
			}
		}

		@Override
		public void stop() {
			try{
				dispose();
			}catch (SdlException e){
				e.printStackTrace();
			}
		}

		@Override
		public boolean isConnected() {
			return getIsConnected();
		}

		@Override
		public void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {
			SdlProxyBase.this.addServiceListener(serviceType,sdlServiceListener);
		}

		@Override
		public void removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {
			SdlProxyBase.this.removeServiceListener(serviceType,sdlServiceListener);
		}

		@Override
		public void startVideoService(VideoStreamingParameters parameters, boolean encrypted) {
			if(isConnected()){
				sdlSession.setDesiredVideoParams(parameters);
				sdlSession.startService(SessionType.NAV,encrypted);
				addNavListener();
			}
		}

		@Override
		public void stopVideoService() {
			if(isConnected()){
				sdlSession.endService(SessionType.NAV);
			}
		}

		@Override public void stopAudioService() {
			if(isConnected()){
				sdlSession.endService(SessionType.PCM);
			}
		}

		@Override
		public void sendRPCRequest(RPCRequest message){
			try {
				SdlProxyBase.this.sendRPCRequest(message);
			} catch (SdlException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void sendRPC(RPCMessage message) {
			try {
				SdlProxyBase.this.sendRPC(message);
			} catch (SdlException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void sendRequests(List<? extends RPCRequest> rpcs, OnMultipleRequestListener listener) {
			try {
				SdlProxyBase.this.sendRequests(rpcs, listener);
			} catch (SdlException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void sendRPCs(List<? extends RPCMessage> rpcs, OnMultipleRequestListener listener) {
			try {
				SdlProxyBase.this.sendRequests(rpcs, listener);
			} catch (SdlException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void sendSequentialRPCs(List<? extends RPCMessage> rpcs, OnMultipleRequestListener listener) {
			try{
				SdlProxyBase.this.sendSequentialRequests(rpcs,listener);
			}catch (SdlException e ){
				DebugTool.logError(TAG, "Issue sending sequential RPCs ", e);
			}
		}

		@Override
		public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {
			SdlProxyBase.this.addOnRPCNotificationListener(notificationId,listener);
		}

		@Override
		public boolean removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {
			return SdlProxyBase.this.removeOnRPCNotificationListener(notificationId,listener);
		}

		@Override
		public void addOnRPCRequestListener(FunctionID functionID, OnRPCRequestListener listener) {
			SdlProxyBase.this.addOnRPCRequestListener(functionID,listener);
		}

		@Override
		public boolean removeOnRPCRequestListener(FunctionID functionID, OnRPCRequestListener listener) {
			return SdlProxyBase.this.removeOnRPCRequestListener(functionID,listener);
		}

		@Override
		public void addOnRPCListener(FunctionID responseId, OnRPCListener listener) {
			SdlProxyBase.this.addOnRPCListener(responseId, listener);
		}

		@Override
		public boolean removeOnRPCListener(FunctionID responseId, OnRPCListener listener) {
			return SdlProxyBase.this.removeOnRPCListener(responseId, listener);
		}

		@Override
		public Object getCapability(SystemCapabilityType systemCapabilityType){
			return SdlProxyBase.this.getCapability(systemCapabilityType);
		}

		@Override
		public RegisterAppInterfaceResponse getRegisterAppInterfaceResponse() {
			return SdlProxyBase.this.getRegisterAppInterfaceResponse();
		}

		@Override
		public void getCapability(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener scListener) {
			SdlProxyBase.this.getCapability(systemCapabilityType, scListener);
		}

		@Override
		public Object getCapability(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener scListener, boolean forceUpdate) {
			if (_systemCapabilityManager != null) {
				return _systemCapabilityManager.getCapability(systemCapabilityType, scListener, forceUpdate);
			}
			return null;
		}

		@Override
		public SdlMsgVersion getSdlMsgVersion(){
			try {
				return SdlProxyBase.this.getSdlMsgVersion();
			} catch (SdlException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public com.smartdevicelink.util.Version getProtocolVersion() {
			return SdlProxyBase.this.protocolVersion;
		}

		@Override
		public boolean isCapabilitySupported(SystemCapabilityType systemCapabilityType){
			return SdlProxyBase.this.isCapabilitySupported(systemCapabilityType);
		}

		@Override
		public void addOnSystemCapabilityListener(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener listener) {
			SdlProxyBase.this.removeOnSystemCapabilityListener(systemCapabilityType, listener);
		}

		@Override
		public boolean removeOnSystemCapabilityListener(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener listener) {
			return SdlProxyBase.this.removeOnSystemCapabilityListener(systemCapabilityType, listener);
		}

		@Override
		public boolean isTransportForServiceAvailable(SessionType serviceType) {
			return SdlProxyBase.this.sdlSession != null
					&& SdlProxyBase.this.sdlSession.isTransportForServiceAvailable(serviceType);
		}

		@Override
		public void startAudioService(boolean isEncrypted, AudioStreamingCodec codec,
									  AudioStreamingParams params) {
			if(getIsConnected()){
				SdlProxyBase.this.startAudioStream(isEncrypted, codec, params);
			}
		}

		@Override
		public void startAudioService(boolean encrypted) {
			if(isConnected()){
				sdlSession.startService(SessionType.PCM,encrypted);
			}
		}

		@Override
		public IVideoStreamListener startVideoStream(boolean isEncrypted, VideoStreamingParameters parameters){
			return SdlProxyBase.this.startVideoStream(isEncrypted, parameters);
		}

		@Override
		public IAudioStreamListener startAudioStream(boolean isEncrypted, AudioStreamingCodec codec,
													 AudioStreamingParams params) {
			return SdlProxyBase.this.startAudioStream(isEncrypted, codec, params);
		}

		@Override
		public void startRPCEncryption() {
			SdlProxyBase.this.startProtectedRPCService();
		}

		@Override
		public Taskmaster getTaskmaster() {
			return SdlProxyBase.this.getTaskmaster();
		}
	};

	Taskmaster getTaskmaster() {
		if (taskmaster == null) {
			Taskmaster.Builder builder = new Taskmaster.Builder();
			builder.setThreadCount(2);
			builder.shouldBeDaemon(false);
			taskmaster = builder.build();
			taskmaster.start();
		}
		return taskmaster;
	}
	
	private void notifyPutFileStreamError(Exception e, String info)
	{
		for (IPutFileResponseListener _putFileListener : _putFileListenerList) {
			_putFileListener.onPutFileStreamError(e, info);
		}		
	}
	
	private void notifyPutFileStreamResponse(PutFileResponse msg)
	{
		for (IPutFileResponseListener _putFileListener : _putFileListenerList) {
			_putFileListener.onPutFileResponse(msg);
		}		
	}
	
	public void addPutFileResponseListener(IPutFileResponseListener _putFileListener)
	{
		_putFileListenerList.addIfAbsent(_putFileListener);
	}

	public void remPutFileResponseListener(IPutFileResponseListener _putFileListener)
	{
		_putFileListenerList.remove(_putFileListener);
	}
	
	// Private Class to Interface with SdlConnection
	private class SdlInterfaceBroker implements ISdlSessionListener {

		@Override
		public void onTransportDisconnected(String info, boolean altTransportAvailable, BaseTransportConfig transportConfig) {

			notifyPutFileStreamError(null, info);

			if (altTransportAvailable){
				SdlProxyBase.this._transportConfig = transportConfig;
				DebugTool.logInfo(TAG, "notifying RPC session ended, but potential primary transport available");
				cycleProxy(SdlDisconnectedReason.PRIMARY_TRANSPORT_CYCLE_REQUEST);
			}else{
				notifyProxyClosed(info, new SdlException("Transport disconnected.", SdlExceptionCause.SDL_UNAVAILABLE), SdlDisconnectedReason.TRANSPORT_DISCONNECT);
			}
		}


		@Override
		public void onRPCMessageReceived(RPCMessage rpcMessage) {

		}

		@Override
		public void onSessionStarted(int sessionID, Version version) {

		}

		@Override
		public void onSessionEnded(int sessionID) {

		}

		@Override
		public void onAuthTokenReceived(String authToken, int sessionID) {

		}


		public void onProtocolServiceDataACK(SessionType sessionType, final int dataSize,
				byte sessionID) {
			if (_callbackToUIThread) {
				// Run in UI thread
				_mainUIHandler.post(new Runnable() {
					@Override
					public void run() {
						_proxyListener.onServiceDataACK(dataSize);
					}
				});
			} else {
				_proxyListener.onServiceDataACK(dataSize);						
			}
		}

	}

	protected SdlProxyBase(){}

	/**
	 * Used by the SdlManager
	 *
	 * @param listener Type of listener for this proxy base.
	 * @param context Application context.
	 * @param appName Client application name.
	 * @param shortAppName Client short application name.
	 * @param isMediaApp Flag that indicates that client application if media application or not.
	 * @param languageDesired Desired language.
	 * @param hmiDisplayLanguageDesired Desired language for HMI.
	 * @param appType Type of application.
	 * @param appID Application identifier.
	 * @param dayColorScheme TemplateColorScheme for the day
	 * @param nightColorScheme TemplateColorScheme for the night
	 * @param transportConfig Configuration of transport to be used by underlying connection.
	 * @param vrSynonyms List of synonyms.
	 * @param ttsName TTS name.
	 * @throws SdlException
	 */
	public SdlProxyBase(proxyListenerType listener, Context context, String appName,String shortAppName, Boolean isMediaApp, Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String appID,
						BaseTransportConfig transportConfig, Vector<String> vrSynonyms, Vector<TTSChunk> ttsName, TemplateColorScheme dayColorScheme, TemplateColorScheme nightColorScheme) throws SdlException {
		performBaseCommon(listener, null, true, appName, ttsName, shortAppName, vrSynonyms, isMediaApp,
				null, languageDesired, hmiDisplayLanguageDesired, appType, appID, null, dayColorScheme,nightColorScheme, false, false, null, null,  transportConfig);
		_appContext = context;
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
	 * @param appID Application identifier.
	 * @param autoActivateID Auto activation identifier.
	 * @param callbackToUIThread Flag that indicates that this proxy should send callback to UI thread or not.
	 * @param transportConfig Configuration of transport to be used by underlying connection.
	 * @throws SdlException if there is an unrecoverable error class might throw an exception.
	 */
	protected SdlProxyBase(proxyListenerType listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			boolean enableAdvancedLifecycleManagement, String appName, Vector<TTSChunk> ttsName, 
			String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, 
			Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String appID, 
			String autoActivateID, boolean callbackToUIThread, BaseTransportConfig transportConfig) 
			throws SdlException {
				
			performBaseCommon(listener, sdlProxyConfigurationResources, enableAdvancedLifecycleManagement, appName, ttsName, ngnMediaScreenAppName, vrSynonyms, isMediaApp,
				sdlMsgVersion, languageDesired, hmiDisplayLanguageDesired, appType, appID, autoActivateID, null, null, callbackToUIThread, null, null, null, transportConfig);
	}
	
	@SuppressWarnings("ConstantConditions")
	private void performBaseCommon(proxyListenerType listener, SdlProxyConfigurationResources sdlProxyConfigurationResources,
								   boolean enableAdvancedLifecycleManagement, String appName, Vector<TTSChunk> ttsName,
								   String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion,
								   Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String appID,
								   String autoActivateID, TemplateColorScheme dayColorScheme, TemplateColorScheme nightColorScheme,
								   boolean callbackToUIThread, Boolean preRegister, String sHashID, Boolean bAppResumeEnab,
								   BaseTransportConfig transportConfig) throws SdlException
	{
		DebugTool.logInfo(TAG, "SDL_LIB_VERSION: " + BuildConfig.VERSION_NAME);
		setProtocolVersion(new Version(PROX_PROT_VER_ONE,0,0));
		
		if (preRegister != null && preRegister)
		{
			_appInterfaceRegisterd = preRegister;
			_preRegisterd = preRegister;
		}
		
		if (bAppResumeEnab != null && bAppResumeEnab)
		{
			_bAppResumeEnabled = true;
			_lastHashID = sHashID;
		}
		_interfaceBroker = new SdlInterfaceBroker();
		
		_callbackToUIThread = callbackToUIThread;
		
		if (_callbackToUIThread) {
			_mainUIHandler = new Handler(Looper.getMainLooper());
		}
		
		// Set variables for Advanced Lifecycle Management
		_advancedLifecycleManagementEnabled = enableAdvancedLifecycleManagement;
		_applicationName = appName;
		_ttsName = ttsName;
		_ngnMediaScreenAppName = ngnMediaScreenAppName;
		_isMediaApp = isMediaApp;
		_sdlMsgVersionRequest = sdlMsgVersion;
		_vrSynonyms = vrSynonyms; 
		_sdlLanguageDesired = languageDesired;
		_hmiDisplayLanguageDesired = hmiDisplayLanguageDesired;
		_appType = appType;
		_appID = appID;
		_autoActivateIdDesired = autoActivateID;
		_dayColorScheme = dayColorScheme;
		_nightColorScheme = nightColorScheme;
		_transportConfig = transportConfig;
				
		// Test conditions to invalidate the proxy
		if (listener == null) {
			throw new IllegalArgumentException("IProxyListener listener must be provided to instantiate SdlProxy object.");
		}
		if (_advancedLifecycleManagementEnabled) {
		/*	if (_applicationName == null ) {
				throw new IllegalArgumentException("To use SdlProxyALM, an application name, appName, must be provided");
			}
			if (_applicationName.length() < 1 || _applicationName.length() > 100) {
				throw new IllegalArgumentException("A provided application name, appName, must be between 1 and 100 characters in length.");
			}*/
			if (_isMediaApp == null) {
				throw new IllegalArgumentException("isMediaApp must not be null when using SdlProxyALM.");
			}
		}
		
		_proxyListener = listener;
		
		// Get information from sdlProxyConfigurationResources
		if (sdlProxyConfigurationResources != null) {
			telephonyManager = sdlProxyConfigurationResources.getTelephonyManager();
		} 
		
		// Use the telephonyManager to get and log phone info
		if (telephonyManager != null) {
			// Following is not quite thread-safe (because m_traceLogger could test null twice),
			// so we need to fix this, but vulnerability (i.e. two instances of listener) is
			// likely harmless.
			if (_traceDeviceInterrogator == null) {
				_traceDeviceInterrogator = new TraceDeviceInfo(telephonyManager);
			} // end-if
			
		} // end-if
		
		// Setup Internal ProxyMessage Dispatcher
		synchronized(INTERNAL_MESSAGE_QUEUE_THREAD_LOCK) {
			// Ensure internalProxyMessageDispatcher is null
			if (_internalProxyMessageDispatcher != null) {
				_internalProxyMessageDispatcher.dispose();
				_internalProxyMessageDispatcher = null;
			}
			
			_internalProxyMessageDispatcher = new ProxyMessageDispatcher<InternalProxyMessage>("INTERNAL_MESSAGE_DISPATCHER", new IDispatchingStrategy<InternalProxyMessage>() {

						@Override
						public void dispatch(InternalProxyMessage message) {
							dispatchInternalMessage(message);
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
			if (_incomingProxyMessageDispatcher != null) {
				_incomingProxyMessageDispatcher.dispose();
				_incomingProxyMessageDispatcher = null;
			}
			
			_incomingProxyMessageDispatcher = new ProxyMessageDispatcher<ProtocolMessage>("INCOMING_MESSAGE_DISPATCHER",new IDispatchingStrategy<ProtocolMessage>() {
						@Override
						public void dispatch(ProtocolMessage message) {
							dispatchIncomingMessage(message);
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
			if (_outgoingProxyMessageDispatcher != null) {
				_outgoingProxyMessageDispatcher.dispose();
				_outgoingProxyMessageDispatcher = null;
			}
			
			_outgoingProxyMessageDispatcher = new ProxyMessageDispatcher<ProtocolMessage>("OUTGOING_MESSAGE_DISPATCHER",new IDispatchingStrategy<ProtocolMessage>() {
						@Override
						public void dispatch(ProtocolMessage message) {
							dispatchOutgoingMessage(message);
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
		
		rpcResponseListeners = new SparseArray<OnRPCResponseListener>();
		rpcNotificationListeners = new SparseArray<CopyOnWriteArrayList<OnRPCNotificationListener>>();
		rpcRequestListeners = new SparseArray<CopyOnWriteArrayList<OnRPCRequestListener>>();
		rpcListeners = new SparseArray<CopyOnWriteArrayList<OnRPCListener>>();

		// Initialize the proxy
		try {
			initializeProxy();
		} catch (SdlException e) {
			// Couldn't initialize the proxy 
			// Dispose threads and then rethrow exception
			
			if (_internalProxyMessageDispatcher != null) {
				_internalProxyMessageDispatcher.dispose();
				_internalProxyMessageDispatcher = null;
			}
			if (_incomingProxyMessageDispatcher != null) {
				_incomingProxyMessageDispatcher.dispose();
				_incomingProxyMessageDispatcher = null;
			}
			if (_outgoingProxyMessageDispatcher != null) {
				_outgoingProxyMessageDispatcher.dispose();
				_outgoingProxyMessageDispatcher = null;
			}
			throw e;
		}

		addOnRPCNotificationListener(FunctionID.ON_PERMISSIONS_CHANGE, onPermissionsChangeListener);
		this._internalInterface.addServiceListener(SessionType.RPC, securedServiceListener);
		this._internalInterface.addServiceListener(SessionType.NAV, securedServiceListener);
		this._internalInterface.addServiceListener(SessionType.PCM, securedServiceListener);


		// Trace that ctor has fired
		SdlTrace.logProxyEvent("SdlProxy Created, instanceID=" + this.toString(), SDL_LIB_TRACE_KEY);		
	}
	
	protected SdlProxyBase(proxyListenerType listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, 
			boolean enableAdvancedLifecycleManagement, String appName, Vector<TTSChunk> ttsName, 
			String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, 
			Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String appID, 
			String autoActivateID, TemplateColorScheme dayColorScheme, TemplateColorScheme nightColorScheme,
		    boolean callbackToUIThread, boolean preRegister, String sHashID, Boolean bEnableResume, BaseTransportConfig transportConfig)
			throws SdlException 
	{
			performBaseCommon(listener, sdlProxyConfigurationResources, enableAdvancedLifecycleManagement, appName, ttsName, ngnMediaScreenAppName, vrSynonyms, isMediaApp,
				sdlMsgVersion, languageDesired, hmiDisplayLanguageDesired, appType, appID, autoActivateID, dayColorScheme, nightColorScheme, callbackToUIThread, preRegister, sHashID, bEnableResume, transportConfig);
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
	 * @param appID Application identifier.
	 * @param autoActivateID Auto activation identifier.
	 * @param callbackToUIThread Flag that indicates that this proxy should send callback to UI thread or not.
	 * @param preRegister Flag that indicates that this proxy should be pre-registerd or not.
	 * @param transportConfig Configuration of transport to be used by underlying connection.
	 * @throws SdlException if there is an unrecoverable error class might throw an exception.
	 */	
	protected SdlProxyBase(proxyListenerType listener, SdlProxyConfigurationResources sdlProxyConfigurationResources,
						   boolean enableAdvancedLifecycleManagement, String appName, Vector<TTSChunk> ttsName,
						   String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion,
						   Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String appID,
						   String autoActivateID, boolean callbackToUIThread, boolean preRegister, BaseTransportConfig transportConfig)
			throws SdlException 
	{
			performBaseCommon(listener, sdlProxyConfigurationResources, enableAdvancedLifecycleManagement, appName, ttsName, ngnMediaScreenAppName, vrSynonyms, isMediaApp,
				sdlMsgVersion, languageDesired, hmiDisplayLanguageDesired, appType, appID, autoActivateID, null, null, callbackToUIThread, preRegister, null, null, transportConfig);
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
	 * @param appID Application identifier.
	 * @param autoActivateID Auto activation identifier.
	 * @param dayColorScheme Day color scheme.
	 * @param dayColorScheme Night color scheme.
	 * @param callbackToUIThread Flag that indicates that this proxy should send callback to UI thread or not.
	 * @param preRegister Flag that indicates that this proxy should be pre-registerd or not.
	 * @param transportConfig Configuration of transport to be used by underlying connection.
	 * @throws SdlException if there is an unrecoverable error class might throw an exception.
	 */
	protected SdlProxyBase(proxyListenerType listener, SdlProxyConfigurationResources sdlProxyConfigurationResources,
						   boolean enableAdvancedLifecycleManagement, String appName, Vector<TTSChunk> ttsName,
						   String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion,
						   Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String appID,
						   String autoActivateID, TemplateColorScheme dayColorScheme, TemplateColorScheme nightColorScheme,
						   boolean callbackToUIThread, boolean preRegister, BaseTransportConfig transportConfig)
			throws SdlException
	{
		performBaseCommon(listener, sdlProxyConfigurationResources, enableAdvancedLifecycleManagement, appName, ttsName, ngnMediaScreenAppName, vrSynonyms, isMediaApp,
				sdlMsgVersion, languageDesired, hmiDisplayLanguageDesired, appType, appID, autoActivateID, dayColorScheme, nightColorScheme, callbackToUIThread, preRegister, null, null, transportConfig);
	}

	private Intent createBroadcastIntent()
	{
		Intent sendIntent = new Intent();
		sendIntent.setAction("com.smartdevicelink.broadcast");
		sendIntent.putExtra("APP_NAME", this._applicationName);
		sendIntent.putExtra("APP_ID", this._appID);
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
	
	private Service getService()
	{
		try {
			Service myService = null;
			if (_proxyListener != null && _proxyListener instanceof Service) {
				myService = (Service) _proxyListener;
			} else if (_appService != null) {
				myService = _appService;
			} else if (_appContext != null && _appContext instanceof Service) {
				myService = (Service) _appContext;
			}
			return myService;
		} catch (Exception ex){
			return null;
		}
	}
	
	private void sendBroadcastIntent(Intent sendIntent)
	{
		Service myService = null;
		if (_proxyListener != null && _proxyListener instanceof Service)
		{
			myService = (Service) _proxyListener;				
		}
		else if (_appService != null)
		{
			myService = _appService;
		}
		Context myContext;
		if (myService != null){
			myContext = myService.getApplicationContext();
		} else if (_appContext != null){
			myContext = _appContext;
		}
		else
		{
			return;
		}
		try
		{
			if (myContext != null) myContext.sendBroadcast(sendIntent);
		}
		catch(Exception ex)
		{
			//If the service or context has become unavailable unexpectedly, catch the exception and move on -- no broadcast log will occur. 
		}
	}
	
	private HttpURLConnection getURLConnection(Headers myHeader, String sURLString, int Timeout, int iContentLen)
	{		
		String sContentType = "application/json";
		int CONNECTION_TIMEOUT = Timeout * 1000;
		int READ_TIMEOUT = Timeout * 1000;
		boolean bDoOutput = true;
		boolean bDoInput = true;
		boolean bUsesCaches = false;
		String sRequestMeth = "POST";
		
		boolean bInstFolRed = false;
		String sCharSet = "utf-8";
		int iContentLength = iContentLen;

		URL url;
		HttpURLConnection urlConnection;
		
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
			CONNECTION_TIMEOUT = iTimeout*1000;
			READ_TIMEOUT = iReadTimeout*1000; 
			updateBroadcastIntent(sendIntent, "COMMENT2", "\nHeader Defined Content Length: " + iContentLength);
		}
				
		try 
		{
			url = new URL(sURLString);
			urlConnection = (HttpURLConnection) url.openConnection();				
			urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
			urlConnection.setDoOutput(bDoOutput);
			urlConnection.setDoInput(bDoInput);
			urlConnection.setRequestMethod(sRequestMeth);
			urlConnection.setReadTimeout(READ_TIMEOUT);						
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

		String sURLString;
		if (!getPoliciesURL().equals("")) {
			sURLString = sPoliciesURL;
		} else {
			sURLString = msg.getUrl();
		}
		sURLString = sURLString.replaceFirst("http://", "https://");

		Integer iTimeout = msg.getTimeout();

		if (iTimeout == null)
			iTimeout = 2000;

		Headers myHeader = msg.getHeader();

		RequestType requestType = msg.getRequestType();
		updateBroadcastIntent(sendIntent, "FUNCTION_NAME", "sendOnSystemRequestToUrl");
		updateBroadcastIntent(sendIntent, "COMMENT5", "\r\nCloud URL: " + sURLString);

		try
		{
			if (myHeader == null) {
				updateBroadcastIntent(sendIntent, "COMMENT7", "\r\nHTTPRequest Header is null");
			}

			String sBodyString = msg.getBody();

			JSONObject jsonObjectToSendToServer;
			String valid_json = "";
			int length;
			if (sBodyString == null)
			{
				if(requestType == RequestType.HTTP ){
					length = msg.getBulkData().length;
					Intent sendIntent3 = createBroadcastIntent();
					updateBroadcastIntent(sendIntent3, "FUNCTION_NAME", "replace");
					updateBroadcastIntent(sendIntent3, "COMMENT1", "Valid Json length before replace: " + length);
					sendBroadcastIntent(sendIntent3);

				}else{
					List<String> legacyData = msg.getLegacyData();
					JSONArray jsonArrayOfSdlPPackets = new JSONArray(legacyData);
					jsonObjectToSendToServer = new JSONObject();
					jsonObjectToSendToServer.put("data", jsonArrayOfSdlPPackets);
					bLegacy = true;
					updateBroadcastIntent(sendIntent, "COMMENT6", "\r\nLegacy SystemRequest: true");
					valid_json = jsonObjectToSendToServer.toString().replace("\\", "");
					length = valid_json.getBytes("UTF-8").length;
				}
			}
 			else
 			{
				Intent sendIntent3 = createBroadcastIntent();
				updateBroadcastIntent(sendIntent3, "FUNCTION_NAME", "replace");
				updateBroadcastIntent(sendIntent3, "COMMENT1", "Valid Json length before replace: " + sBodyString.getBytes("UTF-8").length);
				sendBroadcastIntent(sendIntent3);
				valid_json = sBodyString.replace("\\", "");
				length = valid_json.getBytes("UTF-8").length;
 			}

			urlConnection = getURLConnection(myHeader, sURLString, iTimeout, length);

			if (urlConnection == null)
			{
	            DebugTool.logInfo(TAG, "urlConnection is null, check RPC input parameters");
	            updateBroadcastIntent(sendIntent, "COMMENT2", "urlConnection is null, check RPC input parameters");
	            return;
			}

			DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
			if(requestType == RequestType.HTTP){
				wr.write(msg.getBulkData());
			}else{
				wr.writeBytes(valid_json);
			}

			wr.flush();
			wr.close();


			long BeforeTime = System.currentTimeMillis();
			long AfterTime = System.currentTimeMillis();
			final long roundtriptime = AfterTime - BeforeTime;

			updateBroadcastIntent(sendIntent, "COMMENT4", " Round trip time: " + roundtriptime);
			updateBroadcastIntent(sendIntent, "COMMENT1", "Received response from cloud, response code=" + urlConnection.getResponseCode() + " ");

			int iResponseCode = urlConnection.getResponseCode();

			if (iResponseCode != HttpURLConnection.HTTP_OK)
			{
	            DebugTool.logInfo(TAG, "Response code not HTTP_OK, returning from sendOnSystemRequestToUrl.");
	            updateBroadcastIntent(sendIntent, "COMMENT2", "Response code not HTTP_OK, aborting request. ");
	            return;
	        }

			InputStream is = urlConnection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    String line;
		    StringBuilder response = new StringBuilder();
		    while((line = rd.readLine()) != null)
		    {
		        response.append(line);
		        response.append('\r');
			}
		    rd.close();
		    //We've read the body
			if(requestType == RequestType.HTTP){
		    	// Create the SystemRequest RPC to send to module.
		    	PutFile putFile = new PutFile();
		    	putFile.setFileType(FileType.JSON);
		    	putFile.setCorrelationID(POLICIES_CORRELATION_ID);
		    	putFile.setSdlFileName("response_data");
		    	putFile.setFileData(response.toString().getBytes("UTF-8"));
		    	putFile.setCRC(response.toString().getBytes());
		    	updateBroadcastIntent(sendIntent, "DATA", "Data from cloud response: " + response.toString());
		    	
		    	sendRPCMessagePrivate(putFile);
		    	DebugTool.logInfo(TAG, "sendSystemRequestToUrl sent to sdl");

	    		updateBroadcastIntent(sendIntent2, "RPC_NAME", FunctionID.PUT_FILE.toString());
	    		updateBroadcastIntent(sendIntent2, "TYPE", RPCMessage.KEY_REQUEST);
	    		updateBroadcastIntent(sendIntent2, "CORRID", putFile.getCorrelationID());

		    }else{
		    	Vector<String> cloudDataReceived = new Vector<String>();
		    	final String dataKey = "data";
		    	// Convert the response to JSON
		    	JSONObject jsonResponse = new JSONObject(response.toString());
		    	if(jsonResponse.has(dataKey)){
		    		if (jsonResponse.get(dataKey) instanceof JSONArray)
		    		{
		    			JSONArray jsonArray = jsonResponse.getJSONArray(dataKey);
		    			for (int i=0; i<jsonArray.length(); i++)
		    			{
		    				if (jsonArray.get(i) instanceof String)
		    				{
		    					cloudDataReceived.add(jsonArray.getString(i));
		    					//Log.i("sendSystemRequestToUrl", "jsonArray.getString(i): " + jsonArray.getString(i));
		    				}
		    			}
		    		}
		    		else if (jsonResponse.get(dataKey) instanceof String)
		    		{
		    			cloudDataReceived.add(jsonResponse.getString(dataKey));
		    			//Log.i("sendSystemRequestToUrl", "jsonResponse.getString(data): " + jsonResponse.getString("data"));
		    		}
		    	}
		    	else
		    	{
		    		DebugTool.logError(TAG, "sendSystemRequestToUrl: Data in JSON Object neither an array nor a string.");
		    		//Log.i("sendSystemRequestToUrl", "sendSystemRequestToUrl: Data in JSON Object neither an array nor a string.");
		    		return;
		    	}

		    	String sResponse = cloudDataReceived.toString();

		    	if (sResponse.length() > 512)
		    	{
		    		sResponse = sResponse.substring(0, 511);
		    	}

		    	updateBroadcastIntent(sendIntent, "DATA", "Data from cloud response: " + sResponse);

		    	// Send new SystemRequest to SDL
		    	SystemRequest mySystemRequest = null;

		    	if (bLegacy){
					if(cloudDataReceived != null) {
						mySystemRequest = new SystemRequest(true);
						mySystemRequest.setCorrelationID(getPoliciesReservedCorrelationID());
						mySystemRequest.setLegacyData(cloudDataReceived);
					}
		    	}else{
					if (response != null) {
						mySystemRequest = new SystemRequest();
						mySystemRequest.setRequestType(RequestType.PROPRIETARY);
						mySystemRequest.setCorrelationID(getPoliciesReservedCorrelationID());
						mySystemRequest.setBulkData(response.toString().getBytes());
					}
		    	}

		    	if (getIsConnected())
		    	{			    	
		    		sendRPCMessagePrivate(mySystemRequest);
		    		DebugTool.logInfo(TAG, "sendSystemRequestToUrl sent to sdl");

		    		updateBroadcastIntent(sendIntent2, "RPC_NAME", FunctionID.SYSTEM_REQUEST.toString());
		    		updateBroadcastIntent(sendIntent2, "TYPE", RPCMessage.KEY_REQUEST);
		    		updateBroadcastIntent(sendIntent2, "CORRID", mySystemRequest.getCorrelationID());
		    	}
		    }
		}
		catch (SdlException e)
		{
			DebugTool.logError(TAG, "sendSystemRequestToUrl: Could not get data from JSONObject received.", e);
			updateBroadcastIntent(sendIntent, "COMMENT3", " SdlException encountered sendOnSystemRequestToUrl: "+ e);
			//Log.i("pt", "sendSystemRequestToUrl: Could not get data from JSONObject received."+ e);
		}
		catch (JSONException e)
		{
			DebugTool.logError(TAG, "sendSystemRequestToUrl: JSONException: ", e);
			updateBroadcastIntent(sendIntent, "COMMENT3", " JSONException encountered sendOnSystemRequestToUrl: "+ e);
			//Log.i("pt", "sendSystemRequestToUrl: JSONException: "+ e);
		}
		catch (UnsupportedEncodingException e)
		{
			DebugTool.logError(TAG, "sendSystemRequestToUrl: Could not encode string.", e);
			updateBroadcastIntent(sendIntent, "COMMENT3", " UnsupportedEncodingException encountered sendOnSystemRequestToUrl: "+ e);
			//Log.i("pt", "sendSystemRequestToUrl: Could not encode string."+ e);
		}
		catch (ProtocolException e)
		{
			DebugTool.logError(TAG, "sendSystemRequestToUrl: Could not set request method to post.", e);
			updateBroadcastIntent(sendIntent, "COMMENT3", " ProtocolException encountered sendOnSystemRequestToUrl: "+ e);
			//Log.i("pt", "sendSystemRequestToUrl: Could not set request method to post."+ e);
		}
		catch (MalformedURLException e)
		{
			DebugTool.logError(TAG, "sendSystemRequestToUrl: URL Exception when sending SystemRequest to an external server.", e);
			updateBroadcastIntent(sendIntent, "COMMENT3", " MalformedURLException encountered sendOnSystemRequestToUrl: "+ e);
			//Log.i("pt", "sendSystemRequestToUrl: URL Exception when sending SystemRequest to an external server."+ e);
		}
		catch (IOException e)
		{
			DebugTool.logError(TAG, "sendSystemRequestToUrl: IOException: ", e);
			updateBroadcastIntent(sendIntent, "COMMENT3", " IOException while sending to cloud: IOException: "+ e);
			//Log.i("pt", "sendSystemRequestToUrl: IOException: "+ e);
		}
		catch (Exception e)
		{
			DebugTool.logError(TAG, "sendSystemRequestToUrl: Unexpected Exception: ", e);
			updateBroadcastIntent(sendIntent, "COMMENT3", " Exception encountered sendOnSystemRequestToUrl: "+ e);
			//Log.i("pt", "sendSystemRequestToUrl: Unexpected Exception: " + e);
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

	private int getPoliciesReservedCorrelationID() {
		return POLICIES_CORRELATION_ID;
	}
	
	// Test correlationID
	private boolean isCorrelationIDProtected(Integer correlationID) {
		return correlationID != null &&
				(HEARTBEAT_CORRELATION_ID == correlationID
						|| REGISTER_APP_INTERFACE_CORRELATION_ID == correlationID
						|| UNREGISTER_APP_INTERFACE_CORRELATION_ID == correlationID
						|| POLICIES_CORRELATION_ID == correlationID);

	}
	
	// Protected isConnected method to allow legacy proxy to poll isConnected state
	public Boolean getIsConnected() {
		return sdlSession != null && sdlSession.getIsConnected();
	}

	
	/**
	 * Returns whether the application is registered in SDL. Note: for testing
	 * purposes, it's possible that the connection is established, but the
	 * application is not registered.
	 * 
	 * @return true if the application is registered in SDL
	 */
	public Boolean getAppInterfaceRegistered() {
		return _appInterfaceRegisterd;
	}

	// Function to initialize new proxy connection
	public void initializeProxy() throws SdlException {
		// Reset all of the flags and state variables
		_haveReceivedFirstNonNoneHMILevel = false;
		_haveReceivedFirstFocusLevel = false;
		_haveReceivedFirstFocusLevelFull = false;
		_appInterfaceRegisterd = _preRegisterd;
		
		_putFileListenerList.clear();
		
		_sdlIntefaceAvailablity = SdlInterfaceAvailability.SDL_INTERFACE_UNAVAILABLE;

	//Initialize _systemCapabilityManager here.
		//_systemCapabilityManager = new SystemCapabilityManager(_internalInterface);
		// Setup SdlConnection
		synchronized(CONNECTION_REFERENCE_LOCK) {

			//Handle legacy USB connections
			if (_transportConfig != null
					&& TransportType.USB.equals(_transportConfig.getTransportType())) {
				//A USB transport config was provided
				USBTransportConfig usbTransportConfig = (USBTransportConfig) _transportConfig;
				if (usbTransportConfig.getUsbAccessory() == null) {
					DebugTool.logInfo(TAG, "Legacy USB transport config was used, but received null for accessory. Attempting to connect with router service");
					//The accessory was null which means it came from a router service
					MultiplexTransportConfig multiplexTransportConfig = new MultiplexTransportConfig(usbTransportConfig.getUSBContext(), _appID);
					multiplexTransportConfig.setRequiresHighBandwidth(true);
					multiplexTransportConfig.setSecurityLevel(MultiplexTransportConfig.FLAG_MULTI_SECURITY_OFF);
					multiplexTransportConfig.setPrimaryTransports(Collections.singletonList(TransportType.USB));
					multiplexTransportConfig.setSecondaryTransports(new ArrayList<TransportType>());
					_transportConfig = multiplexTransportConfig;
				}
			}

			if (_transportConfig != null && _transportConfig.getTransportType().equals(TransportType.MULTIPLEX)) {
				this.sdlSession = new SdlSession(_interfaceBroker, (MultiplexTransportConfig) _transportConfig);
			}else if(_transportConfig != null &&_transportConfig.getTransportType().equals(TransportType.TCP)){
				this.sdlSession = new SdlSession(_interfaceBroker, (TCPTransportConfig) _transportConfig);
			}else {
				throw new SdlException(new UnsupportedOperationException("Unable to create session with supplied transport config"));
			}
		}
		
		synchronized(CONNECTION_REFERENCE_LOCK) {
			this.sdlSession.startSession();
				sendTransportBroadcast();
			}
	}
	/**
	 * This method will fake the multiplex connection event
	 */
	@SuppressWarnings("unused")
	public void forceOnConnected(){
		synchronized(CONNECTION_REFERENCE_LOCK) {
			if (sdlSession != null) {
				DebugTool.logInfo(TAG, "Forcing on connected.... might actually need this"); //FIXME
				/*if(sdlSession.getSdlConnection()==null){ //There is an issue when switching from v1 to v2+ where the connection is closed. So we restart the session during this call.
					try {
						sdlSession.startSession();
					} catch (SdlException e) {
						e.printStackTrace();
					}
				}
				sdlSession.getSdlConnection().forceHardwareConnectEvent(TransportType.BLUETOOTH);
				*/
			}
		}
	}
	
	public void sendTransportBroadcast()
	{
		if (sdlSession == null || _transportConfig == null) return;
		
		String sTransComment = "no";//sdlSession.getBroadcastComment(_transportConfig);
		
		if (sTransComment == null || sTransComment.equals("")) return;
		
		Intent sendIntent = createBroadcastIntent();
		updateBroadcastIntent(sendIntent, "FUNCTION_NAME", "initializeProxy");
		updateBroadcastIntent(sendIntent, "COMMENT1", sTransComment);
		sendBroadcastIntent(sendIntent);		
	}
	
	
	/**
	 *  Public method to enable the siphon transport
	 */
	@SuppressWarnings("unused")
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
	@SuppressWarnings("unused")
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
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public static boolean isDebugEnabled() {
		return DebugTool.isDebugEnabled();
	}


	/**
	 * Check to see if it a transport is available to perform audio streaming.
	 * <br><strong>NOTE:</strong> This is only for the audio streaming service, not regular
	 * streaming of media playback.
	 * @return true if there is either an audio streaming supported
	 *         transport currently connected or a transport is
	 *         available to connect with. false if there is no
	 *         transport connected to support audio streaming and
	 *          no possibility in the foreseeable future.
	 */
	public boolean isAudioStreamTransportAvailable(){
		return sdlSession!= null && sdlSession.isTransportForServiceAvailable(SessionType.PCM);
	}

	/**
	 * Check to see if it a transport is available to perform video streaming.

	 * @return true if there is either an video streaming supported
	 *         transport currently connected or a transport is
	 *         available to connect with. false if there is no
	 *         transport connected to support video streaming and
	 *          no possibility in the foreseeable future.
	 */
	public boolean isVideoStreamTransportAvailable(){
		return sdlSession!= null && sdlSession.isTransportForServiceAvailable(SessionType.NAV);
	}


	@SuppressWarnings("unused")
	@Deprecated
	public void close() throws SdlException {
		dispose();
	}
	
	@SuppressWarnings("UnusedParameters")
	private void cleanProxy(SdlDisconnectedReason disconnectedReason) throws SdlException {
		try {

			// ALM Specific Cleanup
			if (_advancedLifecycleManagementEnabled) {
				//_sdlConnectionState = SdlConnectionState.SDL_DISCONNECTED;
				
				firstTimeFull = true;
			
				// Should we wait for the interface to be unregistered?
				Boolean waitForInterfaceUnregistered = false;
				// Unregister app interface
				synchronized(CONNECTION_REFERENCE_LOCK) {
					if (getIsConnected() && getAppInterfaceRegistered()) {
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
			
			if(rpcResponseListeners != null){
				rpcResponseListeners.clear();
			}
			if(rpcNotificationListeners != null){
				rpcNotificationListeners.clear();
			}
			
			// Clean up SDL Connection
			synchronized(CONNECTION_REFERENCE_LOCK) {
				if (sdlSession != null) {
					sdlSession.close();
				}
			}		
		} finally {
			SdlTrace.logProxyEvent("SdlProxy cleaned.", SDL_LIB_TRACE_KEY);
		}
	}

	/**
	 * Check to see if the proxy has already been disposed
	 * @return if the proxy has been disposed
	 */
	public synchronized boolean isDisposed(){
		return _proxyDisposed;
	}

	/**
	 * Terminates the App's Interface Registration, closes the transport connection, ends the protocol session, and frees any resources used by the proxy.
	 */
	public void dispose() throws SdlException {
		SdlTrace.logProxyEvent("Application called dispose() method.", SDL_LIB_TRACE_KEY);
		disposeInternal(SdlDisconnectedReason.APPLICATION_REQUESTED_DISCONNECT);
		if (taskmaster != null) {
			taskmaster.shutdown();
		}
	}
	/**
	 * Terminates the App's Interface Registration, closes the transport connection, ends the protocol session, and frees any resources used by the proxy.
	 * @param sdlDisconnectedReason the reason the proxy should be disposed.
	 */
	private synchronized void disposeInternal(SdlDisconnectedReason sdlDisconnectedReason) throws SdlException
	{
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}
		
		_proxyDisposed = true;
		rpcSecuredServiceStarted = false;
		encryptionRequiredRPCs.clear();
		serviceEncryptionListener = null;

		try{
			// Clean the proxy
			cleanProxy(sdlDisconnectedReason);

			if (sdlDisconnectedReason == SdlDisconnectedReason.MINIMUM_PROTOCOL_VERSION_HIGHER_THAN_SUPPORTED
					|| sdlDisconnectedReason == SdlDisconnectedReason.MINIMUM_RPC_VERSION_HIGHER_THAN_SUPPORTED){
				//We want to notify listeners for this case before disposing the dispatchers
				notifyProxyClosed(sdlDisconnectedReason.toString(), null,  sdlDisconnectedReason);
			}
		
			// Close IncomingProxyMessageDispatcher thread
			synchronized(INCOMING_MESSAGE_QUEUE_THREAD_LOCK) {
				if (_incomingProxyMessageDispatcher != null) {
					_incomingProxyMessageDispatcher.dispose();
					_incomingProxyMessageDispatcher = null;
				}
			}
			
			// Close OutgoingProxyMessageDispatcher thread
			synchronized(OUTGOING_MESSAGE_QUEUE_THREAD_LOCK) {
				if (_outgoingProxyMessageDispatcher != null) {
					_outgoingProxyMessageDispatcher.dispose();
					_outgoingProxyMessageDispatcher = null;
				}
			}
			
			// Close InternalProxyMessageDispatcher thread
			synchronized(INTERNAL_MESSAGE_QUEUE_THREAD_LOCK) {
				if (_internalProxyMessageDispatcher != null) {
					_internalProxyMessageDispatcher.dispose();
					_internalProxyMessageDispatcher = null;
				}
			}
			
			_traceDeviceInterrogator = null;
			
			rpcResponseListeners = null;
			
		} finally {
			SdlTrace.logProxyEvent("SdlProxy disposed.", SDL_LIB_TRACE_KEY);
		}
	} // end-method

	
	private final static Object CYCLE_LOCK = new Object();
	
	private boolean _cycling = false;
	
	// Method to cycle the proxy, only called in ALM
	protected void cycleProxy(SdlDisconnectedReason disconnectedReason) {		
		if (_cycling) return;
		
		synchronized(CYCLE_LOCK)
		{
		try{			
				_cycling = true;
				cleanProxy(disconnectedReason);

				initializeProxy();
				if(!SdlDisconnectedReason.LEGACY_BLUETOOTH_MODE_ENABLED.equals(disconnectedReason)
						&& !SdlDisconnectedReason.PRIMARY_TRANSPORT_CYCLE_REQUEST.equals(disconnectedReason)){//We don't want to alert higher if we are just cycling for legacy bluetooth
					notifyProxyClosed("Sdl Proxy Cycled", new SdlException("Sdl Proxy Cycled", SdlExceptionCause.SDL_PROXY_CYCLED), disconnectedReason);							
				}
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
			_cycling = false;
		}
	}

	
	
	/************* Functions used by the Message Dispatching Queues ****************/
	private void dispatchIncomingMessage(ProtocolMessage message) {
		try{
			// Dispatching logic
			if (message.getSessionType().equals(SessionType.RPC)
					||message.getSessionType().equals(SessionType.BULK_DATA) ) {
				try {
					if (protocolVersion!= null && protocolVersion.getMajor() == 1 && message.getVersion() > 1) {
						if(sdlSession != null
								&& sdlSession.getProtocolVersion()!= null
								&& sdlSession.getProtocolVersion().getMajor() > 1){
							setProtocolVersion(sdlSession.getProtocolVersion());
						}else{
							setProtocolVersion(new Version(message.getVersion(),0,0));
						}
					}
					
					Hashtable<String, Object> hash = new Hashtable<String, Object>();
					if (protocolVersion!= null && protocolVersion.getMajor() > 1) {
						Hashtable<String, Object> hashTemp = new Hashtable<String, Object>();
						hashTemp.put(RPCMessage.KEY_CORRELATION_ID, message.getCorrID());
						if (message.getJsonSize() > 0) {
							final Hashtable<String, Object> mhash = JsonRPCMarshaller.unmarshall(message.getData());
							//hashTemp.put(Names.parameters, mhash.get(Names.parameters));
							if (mhash != null) {
								hashTemp.put(RPCMessage.KEY_PARAMETERS, mhash);
							}
						}

						String functionName = FunctionID.getFunctionName(message.getFunctionID());
						if (functionName != null) {
							hashTemp.put(RPCMessage.KEY_FUNCTION_NAME, functionName);
						} else {
							DebugTool.logWarning(TAG, "Dispatch Incoming Message - function name is null unknown RPC.  FunctionId: " + message.getFunctionID());
							return;
						}
						if (message.getRPCType() == 0x00) {
							hash.put(RPCMessage.KEY_REQUEST, hashTemp);
						} else if (message.getRPCType() == 0x01) {
							hash.put(RPCMessage.KEY_RESPONSE, hashTemp);
						} else if (message.getRPCType() == 0x02) {
							hash.put(RPCMessage.KEY_NOTIFICATION, hashTemp);
						}
						if (message.getBulkData() != null) hash.put(RPCStruct.KEY_BULK_DATA, message.getBulkData());
						if (message.getPayloadProtected()) hash.put(RPCStruct.KEY_PROTECTED, true);
					} else {
						hash = JsonRPCMarshaller.unmarshall(message.getData());
					}
					handleRPCMessage(hash);							
				} catch (final Exception excp) {
					DebugTool.logError(TAG, "Failure handling protocol message: " + excp.toString(), excp);
					passErrorToProxyListener("Error handing incoming protocol message.", excp);
				} // end-catch
			} //else { Handle other protocol message types here}
		} catch (final Exception e) {
			// Pass error to application through listener 
			DebugTool.logError(TAG, "Error handing proxy event.", e);
			passErrorToProxyListener("Error handing incoming protocol message.", e);
		}
	}

	/**
	 * Get the SDL protocol spec version being used
	 * @return Version of the protocol spec
	 */
	public @NonNull Version getProtocolVersion(){
	    if(this.protocolVersion == null){
	        this.protocolVersion = new Version(1,0,0);
        }
		return this.protocolVersion;
	}

	private void setProtocolVersion(@NonNull Version version) {
	    this.protocolVersion = version;
	}

	public String serializeJSON(RPCMessage msg)
	{
		try
		{
			return msg.serializeJSON((byte)this.getProtocolVersion().getMajor()).toString(2);
		}
		catch (final Exception e) 
		{
			DebugTool.logError(TAG, "Error handing proxy event.", e);
			passErrorToProxyListener("Error serializing message.", e);
			return null;
		}
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
			switch (message.getFunctionName()) {
				case InternalProxyMessage.OnProxyError: {
					final OnError msg = (OnError) message;
					if (_callbackToUIThread) {
						// Run in UI thread
						_mainUIHandler.post(new Runnable() {
							@Override
							public void run() {
								_proxyListener.onError(msg.getInfo(), msg.getException());
							}
						});
					} else {
						_proxyListener.onError(msg.getInfo(), msg.getException());
					}
					break;
				}
				case InternalProxyMessage.OnServiceEnded: {
					final OnServiceEnded msg = (OnServiceEnded) message;
					if (_callbackToUIThread) {
						// Run in UI thread
						_mainUIHandler.post(new Runnable() {
							@Override
							public void run() {
								_proxyListener.onServiceEnded(msg);
							}
						});
					} else {
						_proxyListener.onServiceEnded(msg);
					}
					break;
				}
				case InternalProxyMessage.OnServiceNACKed: {
					final OnServiceNACKed msg = (OnServiceNACKed) message;
					if (_callbackToUIThread) {
						// Run in UI thread
						_mainUIHandler.post(new Runnable() {
							@Override
							public void run() {
								_proxyListener.onServiceNACKed(msg);
							}
						});
					} else {
						_proxyListener.onServiceNACKed(msg);
					}

			/* *************Start Legacy Specific Call-backs************/
					break;
				}
				case InternalProxyMessage.OnProxyOpened:
					if (_callbackToUIThread) {
						// Run in UI thread
						_mainUIHandler.post(new Runnable() {
							@Override
							public void run() {
								((IProxyListener) _proxyListener).onProxyOpened();
							}
						});
					} else {
						((IProxyListener) _proxyListener).onProxyOpened();
					}
					break;
				case InternalProxyMessage.OnProxyClosed: {
					final OnProxyClosed msg = (OnProxyClosed) message;
					if (_callbackToUIThread) {
						// Run in UI thread
						_mainUIHandler.post(new Runnable() {
							@Override
							public void run() {
								_proxyListener.onProxyClosed(msg.getInfo(), msg.getException(), msg.getReason());
							}
						});
					} else {
						_proxyListener.onProxyClosed(msg.getInfo(), msg.getException(), msg.getReason());
					}
			/* ***************End Legacy Specific Call-backs************/
					break;
				}
				default:
					// Diagnostics
					SdlTrace.logProxyEvent("Unknown RPC Message encountered. Check for an updated version of the SDL Proxy.", SDL_LIB_TRACE_KEY);
					DebugTool.logError(TAG, "Unknown RPC Message encountered. Check for an updated version of the SDL Proxy.");
					break;
			}
			
		SdlTrace.logProxyEvent("Proxy fired callback: " + message.getFunctionName(), SDL_LIB_TRACE_KEY);
		} catch(final Exception e) {
			// Pass error to application through listener 
			DebugTool.logError(TAG, "Error handing proxy event.", e);
			if (_callbackToUIThread) {
				// Run in UI thread
				_mainUIHandler.post(new Runnable() {
					@Override
					public void run() {
						_proxyListener.onError("Error handing proxy event.", e);
					}
				});
			} else {
				_proxyListener.onError("Error handing proxy event.", e);
			}
		}
	}
	
	private void handleErrorsFromInternalMessageDispatcher(String info, Exception e) {
		DebugTool.logError(TAG, info, e);
		// This error cannot be passed to the user, as it indicates an error
		// in the communication between the proxy and the application.
		
		DebugTool.logError(TAG, "InternalMessageDispatcher failed.", e);
		
		// Note, this is the only place where the _proxyListener should be referenced asdlhronously,
		// with an error on the internalMessageDispatcher, we have no other reliable way of 
		// communicating with the application.
		notifyProxyClosed("Proxy callback dispatcher is down. Proxy instance is invalid.", e, SdlDisconnectedReason.GENERIC_ERROR);
		_proxyListener.onError("Proxy callback dispatcher is down. Proxy instance is invalid.", e);
	}
	/************* END Functions used by the Message Dispatching Queues ****************/


	private OnRPCNotificationListener onPermissionsChangeListener = new OnRPCNotificationListener() {
		@Override
		public void onNotified(RPCNotification notification) {
			List<PermissionItem> permissionItems = ((OnPermissionsChange) notification).getPermissionItem();
			Boolean requireEncryptionAppLevel = ((OnPermissionsChange) notification).getRequireEncryption();
			encryptionRequiredRPCs.clear();
			if (requireEncryptionAppLevel == null || requireEncryptionAppLevel) {
				if (permissionItems != null && !permissionItems.isEmpty()) {
					for (PermissionItem permissionItem : permissionItems) {
						if (permissionItem != null && Boolean.TRUE.equals(permissionItem.getRequireEncryption())) {
							String rpcName = permissionItem.getRpcName();
							if (rpcName != null) {
								encryptionRequiredRPCs.add(rpcName);
							}
						}
					}
				}
				checkStatusAndInitSecuredService();
			}
		}
	};

	private ISdlServiceListener securedServiceListener = new ISdlServiceListener() {
		@Override
		public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted) {
			if(_proxyDisposed){
				DebugTool.logInfo(TAG, "Ignoring start service packet, proxy is disposed");
				return;
			}
			if(SessionType.RPC.equals(type)){
				rpcSecuredServiceStarted = isEncrypted;
			}
			if (serviceEncryptionListener != null) {
				serviceEncryptionListener.onEncryptionServiceUpdated(type, isEncrypted, null);
			}
			DebugTool.logInfo(TAG, "onServiceStarted, session Type: " + type.getName() + ", isEncrypted: " + isEncrypted);
		}

		@Override
		public void onServiceEnded(SdlSession session, SessionType type) {
			if(_proxyDisposed){
				DebugTool.logInfo(TAG, "Ignoring end service packet, proxy is disposed");
				return;
			}
			if (SessionType.RPC.equals(type)) {
				rpcSecuredServiceStarted = false;
			}
			if (serviceEncryptionListener != null) {
				serviceEncryptionListener.onEncryptionServiceUpdated(type, false, null);
			}
			DebugTool.logInfo(TAG, "onServiceEnded, session Type: " + type.getName());
		}

		@Override
		public void onServiceError(SdlSession session, SessionType type, String reason) {
			if(_proxyDisposed){
				DebugTool.logInfo(TAG, "Ignoring start service error, proxy is disposed");
				return;
			}
			if (SessionType.RPC.equals(type)) {
				rpcSecuredServiceStarted = false;
			}
			if (serviceEncryptionListener != null) {
				serviceEncryptionListener.onEncryptionServiceUpdated(type, false, "onServiceError: " + reason);
			}
			DebugTool.logError(TAG, "onServiceError, session Type: " + type.getName() + ", reason: " + reason);
		}
	};

	/**
	 * Checks if an RPC requires encryption
	 *
	 * @param rpcName the rpc name (FunctionID) to check
	 * @return true if the given RPC requires encryption; false, otherwise
	 */
	public boolean getRPCRequiresEncryption(@NonNull FunctionID rpcName) {
		return encryptionRequiredRPCs.contains(rpcName.toString());
	}

	/**
	 * Gets the encryption requirement
	 * @return true if encryption is required; false otherwise
	 */
	public boolean getRequiresEncryption() {
		return !encryptionRequiredRPCs.isEmpty();
	}

	private void checkStatusAndInitSecuredService() {
		if ((_hmiLevel != null && _hmiLevel != HMILevel.HMI_NONE) && getRequiresEncryption() && !rpcSecuredServiceStarted) {
			startProtectedRPCService();
		}
	}

	// Private sendRPCMessagePrivate method. All RPCMessages are funneled through this method after error checking.
	protected void sendRPCMessagePrivate(RPCMessage message) throws SdlException {
		try {
			SdlTrace.logRPCEvent(InterfaceActivityDirection.Transmit, message, SDL_LIB_TRACE_KEY);

			//FIXME this is temporary until the next major release of the library where OK is removed
			if (message.getMessageType().equals(RPCMessage.KEY_REQUEST)) {
				RPCRequest request = (RPCRequest) message;
				if (FunctionID.SUBSCRIBE_BUTTON.toString().equals(request.getFunctionName())
						|| FunctionID.UNSUBSCRIBE_BUTTON.toString().equals(request.getFunctionName())
						|| FunctionID.BUTTON_PRESS.toString().equals(request.getFunctionName())) {

					ButtonName buttonName = (ButtonName) request.getObject(ButtonName.class, SubscribeButton.KEY_BUTTON_NAME);


					if (rpcSpecVersion != null) {
						if (rpcSpecVersion.getMajor() < 5) {

							if (ButtonName.PLAY_PAUSE.equals(buttonName)) {
								request.setParameters(SubscribeButton.KEY_BUTTON_NAME, ButtonName.OK);
							}
						} else { //Newer than version 5.0.0
							if (ButtonName.OK.equals(buttonName)) {
								RPCRequest request2 = new RPCRequest(request);
								request2.setParameters(SubscribeButton.KEY_BUTTON_NAME, ButtonName.PLAY_PAUSE);
								request2.setOnRPCResponseListener(request.getOnRPCResponseListener());
								sendRPCMessagePrivate(request2);
								return;
							}
						}
					}

				}
			}

			message.format(rpcSpecVersion,true);
			byte[] msgBytes = JsonRPCMarshaller.marshall(message, (byte)getProtocolVersion().getMajor());

			ProtocolMessage pm = new ProtocolMessage();
			pm.setData(msgBytes);
			pm.setMessageType(MessageType.RPC);
			pm.setSessionType(SessionType.RPC);
			pm.setFunctionID(FunctionID.getFunctionId(message.getFunctionName()));
			if (rpcSecuredServiceStarted && getRPCRequiresEncryption(message.getFunctionID())) {
				pm.setPayloadProtected(true);
			} else {
				pm.setPayloadProtected(message.isPayloadProtected());
			}
			if (pm.getPayloadProtected() && (!rpcSecuredServiceStarted || !rpcProtectedStartResponse)){
				String errorInfo = "Trying to send an encrypted message and there is no secured service";
				if (message.getMessageType().equals((RPCMessage.KEY_REQUEST))) {
					RPCRequest request = (RPCRequest) message;
					OnRPCResponseListener listener = ((RPCRequest) message).getOnRPCResponseListener();
					if (listener != null) {
						GenericResponse response = new GenericResponse(false, Result.REJECTED);
						response.setInfo(errorInfo);
						listener.onResponse(request.getCorrelationID(), response);
					}
				}
				DebugTool.logWarning(TAG, errorInfo);
				return;
			}

			if (sdlSession != null) {
				pm.setSessionID((byte)sdlSession.getSessionId());
			}

			if (message.getBulkData() != null) {
				pm.setBulkData(message.getBulkData());
			}


			if (message.getMessageType().equals(RPCMessage.KEY_REQUEST)) {  // Request Specifics
				pm.setRPCType((byte)0x00);
				RPCRequest request = (RPCRequest) message;
				if (request.getCorrelationID() == null) {
					//Log error here
					throw new SdlException("CorrelationID cannot be null. RPC: " + request.getFunctionName(), SdlExceptionCause.INVALID_ARGUMENT);
				} else {
					pm.setCorrID(request.getCorrelationID());
				}
				if (request.getFunctionName().equalsIgnoreCase(FunctionID.PUT_FILE.name())) {
					pm.setPriorityCoefficient(1);
				}
			} else if (message.getMessageType().equals(RPCMessage.KEY_RESPONSE)) {  // Response Specifics
				pm.setRPCType((byte)0x01);
				RPCResponse response = (RPCResponse) message;
				if (response.getCorrelationID() == null) {
					//Log error here
					throw new SdlException("CorrelationID cannot be null. RPC: " + response.getFunctionName(), SdlExceptionCause.INVALID_ARGUMENT);
				} else {
					pm.setCorrID(response.getCorrelationID());
				}
			} else if (message.getMessageType().equals(RPCMessage.KEY_NOTIFICATION)) { // Notification Specifics
				pm.setRPCType((byte)0x02);
			} else {
				//Log error here
				throw new SdlException("RPC message is not a valid type", SdlExceptionCause.INVALID_ARGUMENT);
			}

			// Queue this outgoing message
			synchronized(OUTGOING_MESSAGE_QUEUE_THREAD_LOCK) {
				if (_outgoingProxyMessageDispatcher != null) {
					_outgoingProxyMessageDispatcher.queueMessage(pm);
					//Since the message is queued we can add it's listener to our list, if it is a Request
					if (message.getMessageType().equals(RPCMessage.KEY_REQUEST)) {
						RPCRequest request = (RPCRequest) message;
						OnRPCResponseListener listener = request.getOnRPCResponseListener();
						addOnRPCResponseListener(listener, request.getCorrelationID(), msgBytes.length);
					}
				}
			}
		} catch (OutOfMemoryError e) {
			SdlTrace.logProxyEvent("OutOfMemory exception while sending message " + message.getFunctionName(), SDL_LIB_TRACE_KEY);
			throw new SdlException("OutOfMemory exception while sending message " + message.getFunctionName(), e, SdlExceptionCause.INVALID_ARGUMENT);
		}
	}
	
	/**
	 * Will provide callback to the listener either onFinish or onError depending on the RPCResponses result code,
	 * <p>Will automatically remove the listener for the list of listeners on completion. 
	 * @param msg The RPCResponse message that was received
	 * @return if a listener was called or not
	 */
	@SuppressWarnings("UnusedReturnValue")
	private boolean onRPCResponseReceived(RPCResponse msg){
		synchronized(ON_UPDATE_LISTENER_LOCK){
			int correlationId = msg.getCorrelationID();
			if(rpcResponseListeners !=null 
					&& rpcResponseListeners.indexOfKey(correlationId)>=0){
				OnRPCResponseListener listener = rpcResponseListeners.get(correlationId);
				listener.onResponse(correlationId, msg);
				rpcResponseListeners.remove(correlationId);
				return true;
			}
			return false;
		}
	}
	
/**
 * Add a listener that will receive the response to the specific RPCRequest sent with the corresponding correlation id
 * @param listener that will get called back when a response is received
 * @param correlationId of the RPCRequest that was sent
 * @param totalSize only include if this is an OnPutFileUpdateListener. Otherwise it will be ignored.
 */
	public void addOnRPCResponseListener(OnRPCResponseListener listener,int correlationId, int totalSize){
		synchronized(ON_UPDATE_LISTENER_LOCK){
			if(rpcResponseListeners!=null 
					&& listener !=null){
				listener.onStart(correlationId);
				rpcResponseListeners.put(correlationId, listener);
			}
		}
	}
	
	@SuppressWarnings("unused")
	public SparseArray<OnRPCResponseListener> getResponseListeners(){
		synchronized(ON_UPDATE_LISTENER_LOCK){
			return this.rpcResponseListeners;
		}
	}
	
	@SuppressWarnings("UnusedReturnValue")
	public boolean onRPCNotificationReceived(RPCNotification notification){
		synchronized(ON_NOTIFICATION_LISTENER_LOCK){
			CopyOnWriteArrayList<OnRPCNotificationListener> listeners = rpcNotificationListeners.get(FunctionID.getFunctionId(notification.getFunctionName()));
			if(listeners!=null && listeners.size()>0) {
				for (OnRPCNotificationListener listener : listeners) {
					listener.onNotified(notification);
				}
				return true;
			}
			return false;
		}
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean onRPCReceived(RPCMessage message){
		synchronized(RPC_LISTENER_LOCK){
			CopyOnWriteArrayList<OnRPCListener> listeners = rpcListeners.get(FunctionID.getFunctionId(message.getFunctionName()));
			if(listeners!=null && listeners.size()>0) {
				for (OnRPCListener listener : listeners) {
					listener.onReceived(message);
				}
				return true;
			}
			return false;
		}
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean onRPCRequestReceived(RPCRequest request){
		synchronized(ON_NOTIFICATION_LISTENER_LOCK){
			CopyOnWriteArrayList<OnRPCRequestListener> listeners = rpcRequestListeners.get(FunctionID.getFunctionId(request.getFunctionName()));
			if(listeners!=null && listeners.size()>0) {
				for (OnRPCRequestListener listener : listeners) {
					listener.onRequest(request);
				}
				return true;
			}
			return false;
		}
	}
	
	/**
	 * This will ad a listener for the specific type of notification. As of now it will only allow
	 * a single listener per notification function id
	 * @param notificationId The notification type that this listener is designated for
	 * @param listener The listener that will be called when a notification of the provided type is received
	 */
	@SuppressWarnings("unused")
	public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener){
		synchronized(ON_NOTIFICATION_LISTENER_LOCK){
			if(notificationId != null && listener != null){
				if(rpcNotificationListeners.indexOfKey(notificationId.getId()) < 0 ){
					rpcNotificationListeners.put(notificationId.getId(),new CopyOnWriteArrayList<OnRPCNotificationListener>());
				}
				rpcNotificationListeners.get(notificationId.getId()).add(listener);
			}
		}
	}

	/**
	 * This will add a listener for the specific type of message. As of now it will only allow
	 * a single listener per request function id
	 * @param messageId The message type that this listener is designated for
	 * @param listener The listener that will be called when a request of the provided type is received
	 */
	@SuppressWarnings("unused")
	public void addOnRPCListener(FunctionID messageId, OnRPCListener listener){
		synchronized(RPC_LISTENER_LOCK){
			if(messageId != null && listener != null){
				if(rpcListeners.indexOfKey(messageId.getId()) < 0 ){
					rpcListeners.put(messageId.getId(),new CopyOnWriteArrayList<OnRPCListener>());
				}
				rpcListeners.get(messageId.getId()).add(listener);
			}
		}
	}

	public boolean removeOnRPCListener(FunctionID messageId, OnRPCListener listener){
		synchronized(RPC_LISTENER_LOCK){
			if(rpcListeners!= null
					&& messageId != null
					&& listener != null
					&& rpcListeners.indexOfKey(messageId.getId()) >= 0){
				return rpcListeners.get(messageId.getId()).remove(listener);
			}
		}
		return false;
	}

	/**
	 * This will add a listener for the specific type of request. As of now it will only allow
	 * a single listener per request function id
	 * @param requestId The request type that this listener is designated for
	 * @param listener The listener that will be called when a request of the provided type is received
	 */
	@SuppressWarnings("unused")
	public void addOnRPCRequestListener(FunctionID requestId, OnRPCRequestListener listener){
		synchronized(ON_NOTIFICATION_LISTENER_LOCK){
			if(requestId != null && listener != null){
				if(rpcRequestListeners.indexOfKey(requestId.getId()) < 0 ){
					rpcRequestListeners.put(requestId.getId(),new CopyOnWriteArrayList<OnRPCRequestListener>());
				}
				rpcRequestListeners.get(requestId.getId()).add(listener);
			}
		}
	}

	/**
	 * This method is no longer valid and will not remove the listener for the supplied notificaiton id
	 * @param notificationId n/a
	 * @see #removeOnRPCNotificationListener(FunctionID, OnRPCNotificationListener)
	 */
	@SuppressWarnings("unused")
	@Deprecated
	public void removeOnRPCNotificationListener(FunctionID notificationId){
		synchronized(ON_NOTIFICATION_LISTENER_LOCK){
			//rpcNotificationListeners.delete(notificationId.getId());
		}
	}

	public boolean removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener){
		synchronized(ON_NOTIFICATION_LISTENER_LOCK){
			if(rpcNotificationListeners!= null
					&& notificationId != null
					&& listener != null
					&& rpcNotificationListeners.indexOfKey(notificationId.getId()) >= 0){
				return rpcNotificationListeners.get(notificationId.getId()).remove(listener);
			}
		}
		return false;
	}

	public boolean removeOnRPCRequestListener(FunctionID requestId, OnRPCRequestListener listener){
		synchronized(ON_NOTIFICATION_LISTENER_LOCK){
			if(rpcRequestListeners!= null
					&& requestId != null
					&& listener != null
					&& rpcRequestListeners.indexOfKey(requestId.getId()) >= 0){
				return rpcRequestListeners.get(requestId.getId()).remove(listener);
			}
		}
		return false;
	}
	
	private void processRaiResponse(RegisterAppInterfaceResponse rai)
	{
		if (rai == null) return;

		this.raiResponse = rai;
		
		VehicleType vt = rai.getVehicleType();
		if (vt == null) return;
		
		String make = vt.getMake();
		if (make == null) return;
		
		if (_secList == null) return;

		SdlSecurityBase sec;
		Service svc = getService();
		SdlSecurityBase.setAppService(svc);
		if (svc != null && svc.getApplicationContext() != null){
			SdlSecurityBase.setContext(svc.getApplicationContext());
		} else {
			SdlSecurityBase.setContext(_appContext);
		}

		for (Class<? extends SdlSecurityBase> cls : _secList)
		{
			try
			{
				sec = cls.newInstance();
			}
			catch (Exception e)
			{
				continue;
			}
			
			if ( (sec != null) && (sec.getMakeList() != null) )
			{
				if (sec.getMakeList().contains(make))
				{
					setSdlSecurity(sec);
						sec.setAppId(_appID);
						if (sdlSession != null)
							sec.handleSdlSession(sdlSession);
					return;
				}				
			}
		}
	}
	
	private void handleRPCMessage(Hashtable<String, Object> hash) {

		if (hash == null){
			DebugTool.logError(TAG, "handleRPCMessage: hash is null, returning.");
			return;
		}

		RPCMessage rpcMsg = RpcConverter.convertTableToRpc(hash);

		if (rpcMsg == null){
			DebugTool.logError(TAG, "handleRPCMessage: rpcMsg is null, returning.");
			return;
		}

		SdlTrace.logRPCEvent(InterfaceActivityDirection.Receive, rpcMsg, SDL_LIB_TRACE_KEY);

		String functionName = rpcMsg.getFunctionName();
		String messageType = rpcMsg.getMessageType();

		rpcMsg.format(rpcSpecVersion, true);

		onRPCReceived(rpcMsg); // Should only be called for internal use

		// Requests need to be listened for using the SDLManager's addOnRPCRequestListener method.
		// Requests are not supported by IProxyListenerBase
		if (messageType.equals(RPCMessage.KEY_REQUEST)) {

			onRPCRequestReceived((RPCRequest) rpcMsg);

		} else if (messageType.equals(RPCMessage.KEY_RESPONSE)) {
			// Check to ensure response is not from an internal message (reserved correlation ID)
			if (isCorrelationIDProtected((new RPCResponse(hash)).getCorrelationID())) {
				// This is a response generated from an internal message, it can be trapped here
				// The app should not receive a response for a request it did not send
				if ((new RPCResponse(hash)).getCorrelationID() == REGISTER_APP_INTERFACE_CORRELATION_ID 
						&& _advancedLifecycleManagementEnabled 
						&& functionName.equals(FunctionID.REGISTER_APP_INTERFACE.toString())) {
					final RegisterAppInterfaceResponse msg = new RegisterAppInterfaceResponse(hash);
					msg.format(rpcSpecVersion, true);
					if (msg.getSuccess()) {
						_appInterfaceRegisterd = true;
					}
					processRaiResponse(msg);

					//Populate the system capability manager with the RAI response
					//_systemCapabilityManager.parseRAIResponse(msg);
					
					Intent sendIntent = createBroadcastIntent();
					updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionID.REGISTER_APP_INTERFACE.toString());
					updateBroadcastIntent(sendIntent, "TYPE", RPCMessage.KEY_RESPONSE);
					updateBroadcastIntent(sendIntent, "SUCCESS", msg.getSuccess());
					updateBroadcastIntent(sendIntent, "COMMENT1", msg.getInfo());
					updateBroadcastIntent(sendIntent, "COMMENT2", msg.getResultCode().toString());
					updateBroadcastIntent(sendIntent, "DATA",serializeJSON(msg));
					updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationID());
					sendBroadcastIntent(sendIntent);
					
					//_autoActivateIdReturned = msg.getAutoActivateID();
					/*Place holder for legacy support*/ _autoActivateIdReturned = "8675309";
					_prerecordedSpeech = msg.getPrerecordedSpeech();
					_sdlLanguage = msg.getLanguage();
					_hmiDisplayLanguage = msg.getHmiDisplayLanguage();
					_sdlMsgVersion = msg.getSdlMsgVersion();
					if(_sdlMsgVersion != null){
						rpcSpecVersion = new com.smartdevicelink.util.Version(_sdlMsgVersion.getMajorVersion(),_sdlMsgVersion.getMinorVersion(), _sdlMsgVersion.getPatchVersion());
					}else{
						rpcSpecVersion = MAX_SUPPORTED_RPC_VERSION;
					}
					DebugTool.logInfo(TAG, "Negotiated RPC Spec version = " + rpcSpecVersion);

					_vehicleType = msg.getVehicleType();
					_systemSoftwareVersion = msg.getSystemSoftwareVersion();
					_proxyVersionInfo = BuildConfig.VERSION_NAME;
					_iconResumed = msg.getIconResumed();
					
					if (_iconResumed == null){
						_iconResumed = false;
					}

					if (_bAppResumeEnabled)
					{
						if ( (_sdlMsgVersion.getMajorVersion() > 2) && (_lastHashID != null) && (msg.getSuccess()) && (msg.getResultCode() != Result.RESUME_FAILED) )
							_bResumeSuccess = true;
						else
						{
							_bResumeSuccess = false;
							_lastHashID = null;
						}
					}
					_diagModes = msg.getSupportedDiagModes();
					
					String sVersionInfo = "SDL Proxy Version: " + _proxyVersionInfo;
													
					if (!isDebugEnabled()) 
					{
						enableDebugTool();
						DebugTool.logInfo(TAG, sVersionInfo, false);
						disableDebugTool();
					}					
					else
						DebugTool.logInfo(TAG, sVersionInfo, false);
					
					sendIntent = createBroadcastIntent();
					updateBroadcastIntent(sendIntent, "FUNCTION_NAME", "RAI_RESPONSE");
					updateBroadcastIntent(sendIntent, "COMMENT1", sVersionInfo);
					sendBroadcastIntent(sendIntent);
					
					// Send onSdlConnected message in ALM
					//_sdlConnectionState = SdlConnectionState.SDL_CONNECTED;
					
					// If registerAppInterface failed, exit with OnProxyUnusable
					if (!msg.getSuccess()) {
						notifyProxyClosed("Unable to register app interface. Review values passed to the SdlProxy constructor. RegisterAppInterface result code: ", 
								new SdlException("Unable to register app interface. Review values passed to the SdlProxy constructor. RegisterAppInterface result code: " + msg.getResultCode(), SdlExceptionCause.SDL_REGISTRATION_ERROR), SdlDisconnectedReason.SDL_REGISTRATION_ERROR);
					}

					//If the RPC version is too low we should simply dispose this proxy
					if (minimumRPCVersion != null && minimumRPCVersion.isNewerThan(rpcSpecVersion) == 1) {
						DebugTool.logWarning(TAG, String.format("Disconnecting from head unit, the configured minimum RPC version %s is greater than the supported RPC version %s", minimumRPCVersion, rpcSpecVersion));
						try {
							disposeInternal(SdlDisconnectedReason.MINIMUM_RPC_VERSION_HIGHER_THAN_SUPPORTED);
						} catch (SdlException e) {
							e.printStackTrace();
						}
						return;
					}


					if (_callbackToUIThread) {
						// Run in UI thread
						_mainUIHandler.post(new Runnable() {
							@Override
							public void run() {
								if (_proxyListener instanceof IProxyListener) {
									((IProxyListener)_proxyListener).onRegisterAppInterfaceResponse(msg);
								}
								onRPCResponseReceived(msg);
							}
						});
					} else {
						if (_proxyListener instanceof IProxyListener) {
							((IProxyListener)_proxyListener).onRegisterAppInterfaceResponse(msg);
						}
						onRPCResponseReceived(msg);
					}
				} else if ((new RPCResponse(hash)).getCorrelationID() == POLICIES_CORRELATION_ID 
						&& functionName.equals(FunctionID.ON_ENCODED_SYNC_P_DATA.toString())) {
						
					DebugTool.logInfo(TAG, "POLICIES_CORRELATION_ID SystemRequest Notification (Legacy)");
					
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
				else if ((new RPCResponse(hash)).getCorrelationID() == POLICIES_CORRELATION_ID 
						&& functionName.equals(FunctionID.ENCODED_SYNC_P_DATA.toString())) {

					DebugTool.logInfo(TAG, "POLICIES_CORRELATION_ID SystemRequest Response (Legacy)");
					final SystemRequestResponse msg = new SystemRequestResponse(hash);
					
					Intent sendIntent = createBroadcastIntent();
					updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionID.SYSTEM_REQUEST.toString());
					updateBroadcastIntent(sendIntent, "TYPE", RPCMessage.KEY_RESPONSE);
					updateBroadcastIntent(sendIntent, "SUCCESS", msg.getSuccess());
					updateBroadcastIntent(sendIntent, "COMMENT1", msg.getInfo());
					updateBroadcastIntent(sendIntent, "COMMENT2", msg.getResultCode().toString());
					updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationID());
					sendBroadcastIntent(sendIntent);
				}
				else if ((new RPCResponse(hash)).getCorrelationID() == POLICIES_CORRELATION_ID 
						&& functionName.equals(FunctionID.SYSTEM_REQUEST.toString())) {
					final SystemRequestResponse msg = new SystemRequestResponse(hash);
					
					Intent sendIntent = createBroadcastIntent();
					updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionID.SYSTEM_REQUEST.toString());
					updateBroadcastIntent(sendIntent, "TYPE", RPCMessage.KEY_RESPONSE);
					updateBroadcastIntent(sendIntent, "SUCCESS", msg.getSuccess());
					updateBroadcastIntent(sendIntent, "COMMENT1", msg.getInfo());
					updateBroadcastIntent(sendIntent, "COMMENT2", msg.getResultCode().toString());
					updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationID());
					updateBroadcastIntent(sendIntent, "DATA", serializeJSON(msg));
					sendBroadcastIntent(sendIntent);
				}
				else if (functionName.equals(FunctionID.UNREGISTER_APP_INTERFACE.toString())) {
						// UnregisterAppInterface					
						_appInterfaceRegisterd = false;
						synchronized(APP_INTERFACE_REGISTERED_LOCK) {
							APP_INTERFACE_REGISTERED_LOCK.notify();
						}
						final UnregisterAppInterfaceResponse msg = new UnregisterAppInterfaceResponse(hash);
						msg.format(rpcSpecVersion, true);
						Intent sendIntent = createBroadcastIntent();
						updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionID.UNREGISTER_APP_INTERFACE.toString());
						updateBroadcastIntent(sendIntent, "TYPE", RPCMessage.KEY_RESPONSE);
						updateBroadcastIntent(sendIntent, "SUCCESS", msg.getSuccess());
						updateBroadcastIntent(sendIntent, "COMMENT1", msg.getInfo());
						updateBroadcastIntent(sendIntent, "COMMENT2", msg.getResultCode().toString());
						updateBroadcastIntent(sendIntent, "DATA",serializeJSON(msg));
						updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationID());
						sendBroadcastIntent(sendIntent);
				}
				return;
			}
			
			if (functionName.equals(FunctionID.REGISTER_APP_INTERFACE.toString())) {
				final RegisterAppInterfaceResponse msg = new RegisterAppInterfaceResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (msg.getSuccess()) {
					_appInterfaceRegisterd = true;
				}
				processRaiResponse(msg);
				//Populate the system capability manager with the RAI response
				//_systemCapabilityManager.parseRAIResponse(msg);

				//_autoActivateIdReturned = msg.getAutoActivateID();
				/*Place holder for legacy support*/ _autoActivateIdReturned = "8675309";
				_prerecordedSpeech = msg.getPrerecordedSpeech();
				_sdlLanguage = msg.getLanguage();
				_hmiDisplayLanguage = msg.getHmiDisplayLanguage();
				_sdlMsgVersion = msg.getSdlMsgVersion();
				if(_sdlMsgVersion != null){
					rpcSpecVersion = new com.smartdevicelink.util.Version(_sdlMsgVersion.getMajorVersion(),_sdlMsgVersion.getMinorVersion(), _sdlMsgVersion.getPatchVersion());
				} else {
					rpcSpecVersion = MAX_SUPPORTED_RPC_VERSION;
				}
				DebugTool.logInfo(TAG, "Negotiated RPC Spec version = " + rpcSpecVersion);

				_vehicleType = msg.getVehicleType();
				_systemSoftwareVersion = msg.getSystemSoftwareVersion();
				_proxyVersionInfo = BuildConfig.VERSION_NAME;
				
				if (_bAppResumeEnabled)
				{
					if ( (_sdlMsgVersion.getMajorVersion() > 2) && (_lastHashID != null) && (msg.getSuccess()) && (msg.getResultCode() != Result.RESUME_FAILED) )
						_bResumeSuccess = true;
					else
					{
						_bResumeSuccess = false;
						_lastHashID = null;
					}
				}

				_diagModes = msg.getSupportedDiagModes();				
				
				if (!isDebugEnabled()) 
				{
					enableDebugTool();
					DebugTool.logInfo(TAG, "SDL Proxy Version: " + _proxyVersionInfo);
					disableDebugTool();
				}					
				else
					DebugTool.logInfo(TAG, "SDL Proxy Version: " + _proxyVersionInfo);
				
				// RegisterAppInterface
				if (_advancedLifecycleManagementEnabled) {
					
					// Send onSdlConnected message in ALM
					//_sdlConnectionState = SdlConnectionState.SDL_CONNECTED;
					
					// If registerAppInterface failed, exit with OnProxyUnusable
					if (!msg.getSuccess()) {
						notifyProxyClosed("Unable to register app interface. Review values passed to the SdlProxy constructor. RegisterAppInterface result code: ", 
								new SdlException("Unable to register app interface. Review values passed to the SdlProxy constructor. RegisterAppInterface result code: " + msg.getResultCode(), SdlExceptionCause.SDL_REGISTRATION_ERROR), SdlDisconnectedReason.SDL_REGISTRATION_ERROR);
					}
					//If the RPC version is too low we should simply dispose this proxy
					if (minimumRPCVersion != null && minimumRPCVersion.isNewerThan(rpcSpecVersion) == 1) {
						DebugTool.logWarning(TAG, String.format("Disconnecting from head unit, the configured minimum RPC version %s is greater than the supported RPC version %s", minimumRPCVersion, rpcSpecVersion));
						try {
							disposeInternal(SdlDisconnectedReason.MINIMUM_RPC_VERSION_HIGHER_THAN_SUPPORTED);
						} catch (SdlException e) {
							e.printStackTrace();
						}
						return;
					}
				} else {	
					if (_callbackToUIThread) {
						// Run in UI thread
						_mainUIHandler.post(new Runnable() {
							@Override
							public void run() {
								if (_proxyListener instanceof IProxyListener) {
									((IProxyListener)_proxyListener).onRegisterAppInterfaceResponse(msg);
								}
	                            onRPCResponseReceived(msg);
							}
						});
					} else {
						if (_proxyListener instanceof IProxyListener) {
							((IProxyListener)_proxyListener).onRegisterAppInterfaceResponse(msg);
						}
                        onRPCResponseReceived(msg);
					}
				}
			} else if (functionName.equals(FunctionID.SPEAK.toString())) {
				// SpeakResponse
				
				final SpeakResponse msg = new SpeakResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onSpeakResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onSpeakResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.ALERT.toString())) {
				// AlertResponse
				
				final AlertResponse msg = new AlertResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onAlertResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onAlertResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.SHOW.toString())) {
				// ShowResponse
				
				final ShowResponse msg = new ShowResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onShowResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onShowResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.ADD_COMMAND.toString())) {
				// AddCommand
				
				final AddCommandResponse msg = new AddCommandResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onAddCommandResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onAddCommandResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.DELETE_COMMAND.toString())) {
				// DeleteCommandResponse
				
				final DeleteCommandResponse msg = new DeleteCommandResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onDeleteCommandResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onDeleteCommandResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.ADD_SUB_MENU.toString())) {
				// AddSubMenu
				
				final AddSubMenuResponse msg = new AddSubMenuResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onAddSubMenuResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onAddSubMenuResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.DELETE_SUB_MENU.toString())) {
				// DeleteSubMenu
				
				final DeleteSubMenuResponse msg = new DeleteSubMenuResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onDeleteSubMenuResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onDeleteSubMenuResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.SUBSCRIBE_BUTTON.toString())) {
				// SubscribeButton
				
				final SubscribeButtonResponse msg = new SubscribeButtonResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onSubscribeButtonResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onSubscribeButtonResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.UNSUBSCRIBE_BUTTON.toString())) {
				// UnsubscribeButton
				
				final UnsubscribeButtonResponse msg = new UnsubscribeButtonResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onUnsubscribeButtonResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onUnsubscribeButtonResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.SET_MEDIA_CLOCK_TIMER.toString())) {
				// SetMediaClockTimer
				
				final SetMediaClockTimerResponse msg = new SetMediaClockTimerResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onSetMediaClockTimerResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onSetMediaClockTimerResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.ENCODED_SYNC_P_DATA.toString())) {
				
				final SystemRequestResponse msg = new SystemRequestResponse(hash);
				msg.format(rpcSpecVersion,true);
				Intent sendIntent = createBroadcastIntent();
				updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionID.SYSTEM_REQUEST.toString());
				updateBroadcastIntent(sendIntent, "TYPE", RPCMessage.KEY_RESPONSE);
				updateBroadcastIntent(sendIntent, "SUCCESS", msg.getSuccess());
				updateBroadcastIntent(sendIntent, "COMMENT1", msg.getInfo());
				updateBroadcastIntent(sendIntent, "COMMENT2", msg.getResultCode().toString());
				updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationID());
				sendBroadcastIntent(sendIntent);
			
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onSystemRequestResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onSystemRequestResponse(msg);
					onRPCResponseReceived(msg);
				}
			}  else if (functionName.equals(FunctionID.CREATE_INTERACTION_CHOICE_SET.toString())) {
				// CreateInteractionChoiceSet
				
				final CreateInteractionChoiceSetResponse msg = new CreateInteractionChoiceSetResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onCreateInteractionChoiceSetResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onCreateInteractionChoiceSetResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.DELETE_INTERACTION_CHOICE_SET.toString())) {
				// DeleteInteractionChoiceSet
				
				final DeleteInteractionChoiceSetResponse msg = new DeleteInteractionChoiceSetResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onDeleteInteractionChoiceSetResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onDeleteInteractionChoiceSetResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.PERFORM_INTERACTION.toString())) {
				// PerformInteraction
				
				final PerformInteractionResponse msg = new PerformInteractionResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onPerformInteractionResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onPerformInteractionResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.SET_GLOBAL_PROPERTIES.toString())) {
				// SetGlobalPropertiesResponse 
				
				final SetGlobalPropertiesResponse msg = new SetGlobalPropertiesResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
						// Run in UI thread
						_mainUIHandler.post(new Runnable() {
							@Override
							public void run() {
								_proxyListener.onSetGlobalPropertiesResponse(msg);
								onRPCResponseReceived(msg);
							}
						});
					} else {
						_proxyListener.onSetGlobalPropertiesResponse(msg);
						onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.RESET_GLOBAL_PROPERTIES.toString())) {
				// ResetGlobalProperties				
				
				final ResetGlobalPropertiesResponse msg = new ResetGlobalPropertiesResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onResetGlobalPropertiesResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onResetGlobalPropertiesResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.UNREGISTER_APP_INTERFACE.toString())) {
				// UnregisterAppInterface
				
				_appInterfaceRegisterd = false;
				synchronized(APP_INTERFACE_REGISTERED_LOCK) {
					APP_INTERFACE_REGISTERED_LOCK.notify();
				}
				
				final UnregisterAppInterfaceResponse msg = new UnregisterAppInterfaceResponse(hash);
				msg.format(rpcSpecVersion,true);
				Intent sendIntent = createBroadcastIntent();
				updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionID.UNREGISTER_APP_INTERFACE.toString());
				updateBroadcastIntent(sendIntent, "TYPE", RPCMessage.KEY_RESPONSE);
				updateBroadcastIntent(sendIntent, "SUCCESS", msg.getSuccess());
				updateBroadcastIntent(sendIntent, "COMMENT1", msg.getInfo());
				updateBroadcastIntent(sendIntent, "COMMENT2", msg.getResultCode().toString());
				updateBroadcastIntent(sendIntent, "DATA",serializeJSON(msg));
				updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationID());
				sendBroadcastIntent(sendIntent);							
				
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							if (_proxyListener instanceof IProxyListener) {
								((IProxyListener)_proxyListener).onUnregisterAppInterfaceResponse(msg);
							}
							onRPCResponseReceived(msg);
						}
					});
				} else {
					if (_proxyListener instanceof IProxyListener) {
						((IProxyListener)_proxyListener).onUnregisterAppInterfaceResponse(msg);
					}
					onRPCResponseReceived(msg);
				}
				
				notifyProxyClosed("UnregisterAppInterfaceResponse", null, SdlDisconnectedReason.APP_INTERFACE_UNREG);
			} else if (functionName.equals(FunctionID.GENERIC_RESPONSE.toString())) {
				// GenericResponse (Usually and error)
				final GenericResponse msg = new GenericResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onGenericResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onGenericResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.SLIDER.toString())) {
                // Slider
                final SliderResponse msg = new SliderResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onSliderResponse(msg);
                            onRPCResponseReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onSliderResponse(msg);
                    onRPCResponseReceived(msg);
                }
            } else if (functionName.equals(FunctionID.PUT_FILE.toString())) {
                // PutFile
                final PutFileResponse msg = new PutFileResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onPutFileResponse(msg);
                            onRPCResponseReceived(msg);
                            notifyPutFileStreamResponse(msg);
                        }
                    });
                } else {
                    _proxyListener.onPutFileResponse(msg);
                    onRPCResponseReceived(msg);
                    notifyPutFileStreamResponse(msg);                    
                }
            } else if (functionName.equals(FunctionID.DELETE_FILE.toString())) {
                // DeleteFile
                final DeleteFileResponse msg = new DeleteFileResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onDeleteFileResponse(msg);
                            onRPCResponseReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onDeleteFileResponse(msg);
                    onRPCResponseReceived(msg);
                }
            } else if (functionName.equals(FunctionID.LIST_FILES.toString())) {
                // ListFiles
                final ListFilesResponse msg = new ListFilesResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onListFilesResponse(msg);
                            onRPCResponseReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onListFilesResponse(msg);
                    onRPCResponseReceived(msg);
                }
            } else if (functionName.equals(FunctionID.SET_APP_ICON.toString())) {
                // SetAppIcon
                final SetAppIconResponse msg = new SetAppIconResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onSetAppIconResponse(msg);
                            onRPCResponseReceived(msg);
                        }
                    });
                } else {
                        _proxyListener.onSetAppIconResponse(msg);
                        onRPCResponseReceived(msg);
                }
            } else if (functionName.equals(FunctionID.SCROLLABLE_MESSAGE.toString())) {
                // ScrollableMessage
                final ScrollableMessageResponse msg = new ScrollableMessageResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onScrollableMessageResponse(msg);
                            onRPCResponseReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onScrollableMessageResponse(msg);
                    onRPCResponseReceived(msg);
                }
            } else if (functionName.equals(FunctionID.CHANGE_REGISTRATION.toString())) {
                // ChangeLanguageRegistration
                final ChangeRegistrationResponse msg = new ChangeRegistrationResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onChangeRegistrationResponse(msg);
                            onRPCResponseReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onChangeRegistrationResponse(msg);
                    onRPCResponseReceived(msg);
                }
            } else if (functionName.equals(FunctionID.SET_DISPLAY_LAYOUT.toString())) {
                // SetDisplayLayout
                final SetDisplayLayoutResponse msg = new SetDisplayLayoutResponse(hash);
				msg.format(rpcSpecVersion,true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onSetDisplayLayoutResponse(msg);
                            onRPCResponseReceived(msg);
                        }
                    });
                } else {
                        _proxyListener.onSetDisplayLayoutResponse(msg);
                        onRPCResponseReceived(msg);
                }
            } else if (functionName.equals(FunctionID.PERFORM_AUDIO_PASS_THRU.toString())) {
                // PerformAudioPassThru
                final PerformAudioPassThruResponse msg = new PerformAudioPassThruResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onPerformAudioPassThruResponse(msg);
                            onRPCResponseReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onPerformAudioPassThruResponse(msg);
                    onRPCResponseReceived(msg);       
                }
            } else if (functionName.equals(FunctionID.END_AUDIO_PASS_THRU.toString())) {
                // EndAudioPassThru
                final EndAudioPassThruResponse msg = new EndAudioPassThruResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onEndAudioPassThruResponse(msg);
                            onRPCResponseReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onEndAudioPassThruResponse(msg);
                    onRPCResponseReceived(msg);
                }
            } else if (functionName.equals(FunctionID.SUBSCRIBE_VEHICLE_DATA.toString())) {
            	// SubscribeVehicleData
                final SubscribeVehicleDataResponse msg = new SubscribeVehicleDataResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onSubscribeVehicleDataResponse(msg);
                            onRPCResponseReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onSubscribeVehicleDataResponse(msg);
                    onRPCResponseReceived(msg);       
                }
            } else if (functionName.equals(FunctionID.UNSUBSCRIBE_VEHICLE_DATA.toString())) {
            	// UnsubscribeVehicleData
                final UnsubscribeVehicleDataResponse msg = new UnsubscribeVehicleDataResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onUnsubscribeVehicleDataResponse(msg);
                            onRPCResponseReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onUnsubscribeVehicleDataResponse(msg);
                    onRPCResponseReceived(msg);   
                }
            } else if (functionName.equals(FunctionID.GET_VEHICLE_DATA.toString())) {
           		// GetVehicleData
                final GetVehicleDataResponse msg = new GetVehicleDataResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                           _proxyListener.onGetVehicleDataResponse(msg);
                           onRPCResponseReceived(msg);
                        }
                     });
                    } else {
                        _proxyListener.onGetVehicleDataResponse(msg);
                        onRPCResponseReceived(msg);   
                    }
            } else if (functionName.equals(FunctionID.SUBSCRIBE_WAY_POINTS.toString())) {
            	// SubscribeWayPoints
                final SubscribeWayPointsResponse msg = new SubscribeWayPointsResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onSubscribeWayPointsResponse(msg);
							onRPCResponseReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onSubscribeWayPointsResponse(msg);
					onRPCResponseReceived(msg);
                }
            } else if (functionName.equals(FunctionID.UNSUBSCRIBE_WAY_POINTS.toString())) {
            	// UnsubscribeWayPoints
                final UnsubscribeWayPointsResponse msg = new UnsubscribeWayPointsResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onUnsubscribeWayPointsResponse(msg);
							onRPCResponseReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onUnsubscribeWayPointsResponse(msg);
					onRPCResponseReceived(msg);
                }
            } else if (functionName.equals(FunctionID.GET_WAY_POINTS.toString())) {
           		// GetWayPoints
                final GetWayPointsResponse msg = new GetWayPointsResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                           _proxyListener.onGetWayPointsResponse(msg);
							onRPCResponseReceived(msg);
                        }
                     });
                    } else {
                        _proxyListener.onGetWayPointsResponse(msg);
                        onRPCResponseReceived(msg);   
                    }            	               
            } else if (functionName.equals(FunctionID.READ_DID.toString())) {
                final ReadDIDResponse msg = new ReadDIDResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onReadDIDResponse(msg);
                            onRPCResponseReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onReadDIDResponse(msg);
                    onRPCResponseReceived(msg);
                }            	            	
            } else if (functionName.equals(FunctionID.GET_DTCS.toString())) {
                final GetDTCsResponse msg = new GetDTCsResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onGetDTCsResponse(msg);
                            onRPCResponseReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onGetDTCsResponse(msg);
                    onRPCResponseReceived(msg);   
                }
            } else if (functionName.equals(FunctionID.DIAGNOSTIC_MESSAGE.toString())) {
                final DiagnosticMessageResponse msg = new DiagnosticMessageResponse(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onDiagnosticMessageResponse(msg);
                            onRPCResponseReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onDiagnosticMessageResponse(msg);
                    onRPCResponseReceived(msg);   
                }            	
            } 
            else if (functionName.equals(FunctionID.SYSTEM_REQUEST.toString())) {

   				final SystemRequestResponse msg = new SystemRequestResponse(hash);
   				msg.format(rpcSpecVersion, true);
   				if (_callbackToUIThread) {
   					// Run in UI thread
   					_mainUIHandler.post(new Runnable() {
   						@Override
   						public void run() {
   							_proxyListener.onSystemRequestResponse(msg);
                            onRPCResponseReceived(msg);
   						}
   					});
   				} else {
   					_proxyListener.onSystemRequestResponse(msg);
                    onRPCResponseReceived(msg);	
   				}
            }
            else if (functionName.equals(FunctionID.SEND_LOCATION.toString())) {

   				final SendLocationResponse msg = new SendLocationResponse(hash);
   				msg.format(rpcSpecVersion, true);
   				if (_callbackToUIThread) {
   					// Run in UI thread
   					_mainUIHandler.post(new Runnable() {
   						@Override
   						public void run() {
   							_proxyListener.onSendLocationResponse(msg);
   							onRPCResponseReceived(msg);
   						}
   					});
   				} else {
   					_proxyListener.onSendLocationResponse(msg);
   					onRPCResponseReceived(msg);
   				}
            }
            else if (functionName.equals(FunctionID.DIAL_NUMBER.toString())) {

   				final DialNumberResponse msg = new DialNumberResponse(hash);
   				msg.format(rpcSpecVersion, true);
   				if (_callbackToUIThread) {
   					// Run in UI thread
   					_mainUIHandler.post(new Runnable() {
   						@Override
   						public void run() {
   							_proxyListener.onDialNumberResponse(msg);
   							onRPCResponseReceived(msg);
   						}
   					});
   				} else {
   					_proxyListener.onDialNumberResponse(msg);
   					onRPCResponseReceived(msg);
   				}
            }
            else if (functionName.equals(FunctionID.SHOW_CONSTANT_TBT.toString())) {
				final ShowConstantTbtResponse msg = new ShowConstantTbtResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onShowConstantTbtResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onShowConstantTbtResponse(msg);
					onRPCResponseReceived(msg);
				}
			}
			else if (functionName.equals(FunctionID.ALERT_MANEUVER.toString())) {
				final AlertManeuverResponse msg = new AlertManeuverResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onAlertManeuverResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onAlertManeuverResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.UPDATE_TURN_LIST.toString())) {
				final UpdateTurnListResponse msg = new UpdateTurnListResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onUpdateTurnListResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onUpdateTurnListResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.SET_INTERIOR_VEHICLE_DATA.toString())) {
				final SetInteriorVehicleDataResponse msg = new SetInteriorVehicleDataResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onSetInteriorVehicleDataResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onSetInteriorVehicleDataResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.GET_INTERIOR_VEHICLE_DATA.toString())) {
				final GetInteriorVehicleDataResponse msg = new GetInteriorVehicleDataResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onGetInteriorVehicleDataResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onGetInteriorVehicleDataResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.CREATE_WINDOW.toString())) {
				final CreateWindowResponse msg = new CreateWindowResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onCreateWindowResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onCreateWindowResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.DELETE_WINDOW.toString())) {
				final DeleteWindowResponse msg = new DeleteWindowResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onDeleteWindowResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onDeleteWindowResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.GET_SYSTEM_CAPABILITY.toString())) {
				// GetSystemCapabilityResponse
				final GetSystemCapabilityResponse msg = new GetSystemCapabilityResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onGetSystemCapabilityResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onGetSystemCapabilityResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.BUTTON_PRESS.toString())) {
				final ButtonPressResponse msg = new ButtonPressResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onButtonPressResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onButtonPressResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.SEND_HAPTIC_DATA.toString())) {
				final SendHapticDataResponse msg = new SendHapticDataResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onSendHapticDataResponse( msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onSendHapticDataResponse( msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.SET_CLOUD_APP_PROPERTIES.toString())) {
				final SetCloudAppPropertiesResponse msg = new SetCloudAppPropertiesResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onSetCloudAppProperties(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onSetCloudAppProperties(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.GET_CLOUD_APP_PROPERTIES.toString())) {
				final GetCloudAppPropertiesResponse msg = new GetCloudAppPropertiesResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onGetCloudAppProperties(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onGetCloudAppProperties(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.PUBLISH_APP_SERVICE.toString())) {
				final PublishAppServiceResponse msg = new PublishAppServiceResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onPublishAppServiceResponse( msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onPublishAppServiceResponse( msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.GET_APP_SERVICE_DATA.toString())) {
				final GetAppServiceDataResponse msg = new GetAppServiceDataResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onGetAppServiceDataResponse( msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onGetAppServiceDataResponse( msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.GET_FILE.toString())) {
				final GetFileResponse msg = new GetFileResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onGetFileResponse( msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onGetFileResponse( msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.PERFORM_APP_SERVICES_INTERACTION.toString())) {
				final PerformAppServiceInteractionResponse msg = new PerformAppServiceInteractionResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onPerformAppServiceInteractionResponse( msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onPerformAppServiceInteractionResponse( msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.CLOSE_APPLICATION.toString())) {
				final CloseApplicationResponse msg = new CloseApplicationResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onCloseApplicationResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onCloseApplicationResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.CANCEL_INTERACTION.toString())) {
				final CancelInteractionResponse msg = new CancelInteractionResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onCancelInteractionResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onCancelInteractionResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.UNPUBLISH_APP_SERVICE.toString())) {
				final UnpublishAppServiceResponse msg = new UnpublishAppServiceResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onUnpublishAppServiceResponse( msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onUnpublishAppServiceResponse( msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.SHOW_APP_MENU.toString())) {
				final ShowAppMenuResponse msg = new ShowAppMenuResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onShowAppMenuResponse( msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onShowAppMenuResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.GET_INTERIOR_VEHICLE_DATA_CONSENT.toString())) {
				final GetInteriorVehicleDataConsentResponse msg = new GetInteriorVehicleDataConsentResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onGetInteriorVehicleDataConsentResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onGetInteriorVehicleDataConsentResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else if (functionName.equals(FunctionID.RELEASE_INTERIOR_VEHICLE_MODULE.toString())) {
				final ReleaseInteriorVehicleDataModuleResponse msg = new ReleaseInteriorVehicleDataModuleResponse(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onReleaseInteriorVehicleDataModuleResponse(msg);
							onRPCResponseReceived(msg);
						}
					});
				} else {
					_proxyListener.onReleaseInteriorVehicleDataModuleResponse(msg);
					onRPCResponseReceived(msg);
				}
			} else {
				if (_sdlMsgVersion != null) {
					DebugTool.logError(TAG, "Unrecognized response Message: " + functionName +
							" SDL Message Version = " + _sdlMsgVersion);
				} else {
					DebugTool.logError(TAG, "Unrecognized response Message: " + functionName);
				}
			} // end-if


		} else if (messageType.equals(RPCMessage.KEY_NOTIFICATION)) {
			if (functionName.equals(FunctionID.ON_HMI_STATUS.toString())) {
				// OnHMIStatus
				
				final OnHMIStatus msg = new OnHMIStatus(hash);

				//setup lockscreeninfo
				if (sdlSession != null)
				{
					//sdlSession.getLockScreenMan().setHMILevel(msg.getHmiLevel());
				}
				
				msg.setFirstRun(firstTimeFull);
				if (msg.getHmiLevel() == HMILevel.HMI_FULL) firstTimeFull = false;

				_hmiLevel = msg.getHmiLevel();
				if (_hmiLevel != HMILevel.HMI_NONE) {
					checkStatusAndInitSecuredService();
				}
				_audioStreamingState = msg.getAudioStreamingState();

				msg.format(rpcSpecVersion, true);
				lastHmiStatus = msg;

				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnHMIStatus(msg);
							//_proxyListener.onOnLockScreenNotification(sdlSession.getLockScreenMan().getLockObj());
							onRPCNotificationReceived(msg);
							}
						});
					} else {
						_proxyListener.onOnHMIStatus(msg);
						//_proxyListener.onOnLockScreenNotification(sdlSession.getLockScreenMan().getLockObj());
						onRPCNotificationReceived(msg);
					}
			} else if (functionName.equals(FunctionID.ON_COMMAND.toString())) {
				// OnCommand
				
				final OnCommand msg = new OnCommand(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnCommand(msg);
							onRPCNotificationReceived(msg);
						}
					});
				} else {
					_proxyListener.onOnCommand(msg);
					onRPCNotificationReceived(msg);
				}
			} else if (functionName.equals(FunctionID.ON_DRIVER_DISTRACTION.toString())) {
				// OnDriverDistration
				
				final OnDriverDistraction msg = new OnDriverDistraction(hash);
				
				//setup lockscreeninfo
				if (sdlSession != null)
				{
					DriverDistractionState drDist = msg.getState();
					//sdlSession.getLockScreenMan().setDriverDistStatus(drDist == DriverDistractionState.DD_ON);
				}
				
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnDriverDistraction(msg);
							//_proxyListener.onOnLockScreenNotification(sdlSession.getLockScreenMan().getLockObj());
							onRPCNotificationReceived(msg);
						}
					});
				} else {
					_proxyListener.onOnDriverDistraction(msg);
					//_proxyListener.onOnLockScreenNotification(sdlSession.getLockScreenMan().getLockObj());
					onRPCNotificationReceived(msg);
				}
			} else if (functionName.equals(FunctionID.ON_ENCODED_SYNC_P_DATA.toString())) {
				
				final OnSystemRequest msg = new OnSystemRequest(hash);

				Intent sendIntent = createBroadcastIntent();
				updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionID.ON_SYSTEM_REQUEST.toString());
				updateBroadcastIntent(sendIntent, "TYPE", RPCMessage.KEY_NOTIFICATION);
				
				// If url is null, then send notification to the app, otherwise, send to URL
				if (msg.getUrl() == null) {
					updateBroadcastIntent(sendIntent, "COMMENT1", "URL is a null value (received)");
					sendBroadcastIntent(sendIntent);
					msg.format(rpcSpecVersion, true);
					if (_callbackToUIThread) {
						// Run in UI thread
						_mainUIHandler.post(new Runnable() {
							@Override
							public void run() {
								_proxyListener.onOnSystemRequest(msg);
								onRPCNotificationReceived(msg);
							}
						});
					} else {
						_proxyListener.onOnSystemRequest(msg);
						onRPCNotificationReceived(msg);
					}
				} else {
					updateBroadcastIntent(sendIntent, "COMMENT1", "Sending to cloud: " + msg.getUrl());
					sendBroadcastIntent(sendIntent);				
					
					DebugTool.logInfo(TAG, "send to url");
					
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
			} else if (functionName.equals(FunctionID.ON_PERMISSIONS_CHANGE.toString())) {
				//OnPermissionsChange
				
				final OnPermissionsChange msg = new OnPermissionsChange(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnPermissionsChange(msg);
							onRPCNotificationReceived(msg);
						}
					});
				} else {
					_proxyListener.onOnPermissionsChange(msg);
					onRPCNotificationReceived(msg);
				}
			} else if (functionName.equals(FunctionID.ON_TBT_CLIENT_STATE.toString())) {
				// OnTBTClientState
				
				final OnTBTClientState msg = new OnTBTClientState(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnTBTClientState(msg);
							onRPCNotificationReceived(msg);
						}
					});
				} else {
					_proxyListener.onOnTBTClientState(msg);
					onRPCNotificationReceived(msg);
				}
			} else if (functionName.equals(FunctionID.ON_BUTTON_PRESS.toString())) {
				// OnButtonPress
				
				final OnButtonPress msg = new OnButtonPress(hash);
				msg.format(rpcSpecVersion, true);
				final OnButtonPress onButtonPressCompat = (OnButtonPress)handleButtonNotificationFormatting(msg);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnButtonPress(msg);
							onRPCNotificationReceived(msg);
							if(onButtonPressCompat != null){
								onRPCNotificationReceived(onButtonPressCompat);
								_proxyListener.onOnButtonPress(onButtonPressCompat);
							}
						}
					});
				} else {
					_proxyListener.onOnButtonPress(msg);
					onRPCNotificationReceived(msg);
					if(onButtonPressCompat != null){
						onRPCNotificationReceived(onButtonPressCompat);
						_proxyListener.onOnButtonPress(onButtonPressCompat);
					}
				}
			} else if (functionName.equals(FunctionID.ON_BUTTON_EVENT.toString())) {
				// OnButtonEvent
				
				final OnButtonEvent msg = new OnButtonEvent(hash);
				msg.format(rpcSpecVersion, true);
				final OnButtonEvent onButtonEventCompat = (OnButtonEvent)handleButtonNotificationFormatting(msg);

				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnButtonEvent(msg);
							onRPCNotificationReceived(msg);
							if(onButtonEventCompat != null){
								onRPCNotificationReceived(onButtonEventCompat);
								_proxyListener.onOnButtonEvent(onButtonEventCompat);
							}
						}
					});
				} else {
					_proxyListener.onOnButtonEvent(msg);
					onRPCNotificationReceived(msg);
					if(onButtonEventCompat != null){
						onRPCNotificationReceived(onButtonEventCompat);
						_proxyListener.onOnButtonEvent(onButtonEventCompat);
					}
				}
			} else if (functionName.equals(FunctionID.ON_LANGUAGE_CHANGE.toString())) {
				// OnLanguageChange
				
				final OnLanguageChange msg = new OnLanguageChange(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnLanguageChange(msg);
							onRPCNotificationReceived(msg);
						}
					});
				} else {
					_proxyListener.onOnLanguageChange(msg);
					onRPCNotificationReceived(msg);
				}
			} else if (functionName.equals(FunctionID.ON_HASH_CHANGE.toString())) {
				// OnLanguageChange
				
				final OnHashChange msg = new OnHashChange(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnHashChange(msg);
							onRPCNotificationReceived(msg);
							if (_bAppResumeEnabled)
							{
								_lastHashID = msg.getHashID();
							}
						}
					});
				} else {
					_proxyListener.onOnHashChange(msg);
					onRPCNotificationReceived(msg);
					if (_bAppResumeEnabled)
					{
						_lastHashID = msg.getHashID();
					}
				}
			} else if (functionName.equals(FunctionID.ON_SYSTEM_REQUEST.toString())) {
					// OnSystemRequest
					
					final OnSystemRequest msg = new OnSystemRequest(hash);
					msg.format(rpcSpecVersion,true);
					RequestType requestType = msg.getRequestType();
				if(msg.getUrl() != null) {
					if (((requestType == RequestType.PROPRIETARY) && (msg.getFileType() == FileType.JSON))
							|| ((requestType == RequestType.HTTP) && (msg.getFileType() == FileType.BINARY))) {
						Thread handleOffboardTransmissionThread = new Thread() {
							@Override
							public void run() {
								sendOnSystemRequestToUrl(msg);
							}
						};

						handleOffboardTransmissionThread.start();
					} else if (requestType == RequestType.LOCK_SCREEN_ICON_URL) {
						//Cache this for when the lockscreen is displayed
						lockScreenIconRequest = msg;
					} else if (requestType == RequestType.ICON_URL) {
						if (msg.getUrl() != null) {
							//Download the icon file and send SystemRequest RPC
							Thread handleOffBoardTransmissionThread = new Thread() {
								@Override
								public void run() {
									String urlHttps = msg.getUrl().replaceFirst("http://", "https://");
									byte[] file = FileUtls.downloadFile(urlHttps);
									if (file != null) {
										SystemRequest systemRequest = new SystemRequest();
										systemRequest.setFileName(msg.getUrl());
										systemRequest.setBulkData(file);
										systemRequest.setRequestType(RequestType.ICON_URL);
										try {
											sendRPCMessagePrivate(systemRequest);
										} catch (SdlException e) {
											e.printStackTrace();
										}
									} else {
										DebugTool.logError(TAG, "File was null at: " + urlHttps);
									}
								}
							};
							handleOffBoardTransmissionThread.start();
						}
					}
				}

					if (_callbackToUIThread) {
						// Run in UI thread
						_mainUIHandler.post(new Runnable() {
							@Override
							public void run() {
								_proxyListener.onOnSystemRequest(msg);
								onRPCNotificationReceived(msg);
							}
						});
					} else {
						_proxyListener.onOnSystemRequest(msg);
						onRPCNotificationReceived(msg);
					}
			} else if (functionName.equals(FunctionID.ON_AUDIO_PASS_THRU.toString())) {
				// OnAudioPassThru
				final OnAudioPassThru msg = new OnAudioPassThru(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
    						_proxyListener.onOnAudioPassThru(msg);
    						onRPCNotificationReceived(msg);
                        }
                    });
                } else {
					_proxyListener.onOnAudioPassThru(msg);
					onRPCNotificationReceived(msg);
                }				
			} else if (functionName.equals(FunctionID.ON_VEHICLE_DATA.toString())) {
				// OnVehicleData
                final OnVehicleData msg = new OnVehicleData(hash);
                msg.format(rpcSpecVersion, true);
                if (_callbackToUIThread) {
                    // Run in UI thread
                    _mainUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _proxyListener.onOnVehicleData(msg);
                            onRPCNotificationReceived(msg);
                        }
                    });
                } else {
                    _proxyListener.onOnVehicleData(msg);
                    onRPCNotificationReceived(msg);
                } 
			}
			else if (functionName.equals(FunctionID.ON_APP_INTERFACE_UNREGISTERED.toString())) {
				// OnAppInterfaceUnregistered
				
				_appInterfaceRegisterd = false;
				synchronized(APP_INTERFACE_REGISTERED_LOCK) {
					APP_INTERFACE_REGISTERED_LOCK.notify();
				}
				
				final OnAppInterfaceUnregistered msg = new OnAppInterfaceUnregistered(hash);
				msg.format(rpcSpecVersion,true);
								
				Intent sendIntent = createBroadcastIntent();
				updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionID.ON_APP_INTERFACE_UNREGISTERED.toString());
				updateBroadcastIntent(sendIntent, "TYPE", RPCMessage.KEY_NOTIFICATION);
				updateBroadcastIntent(sendIntent, "DATA",serializeJSON(msg));
				sendBroadcastIntent(sendIntent);

				if (_advancedLifecycleManagementEnabled) {
					// This requires the proxy to be cycled

					if(_mainUIHandler == null){
						_mainUIHandler = new Handler(Looper.getMainLooper());
					}

					//This needs to be ran on the main thread

					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							cycleProxy(SdlDisconnectedReason.convertAppInterfaceUnregisteredReason(msg.getReason()));
						}
					});
                } else {
					if (_callbackToUIThread) {
						// Run in UI thread
						_mainUIHandler.post(new Runnable() {
							@Override
							public void run() {
								((IProxyListener)_proxyListener).onOnAppInterfaceUnregistered(msg);
								onRPCNotificationReceived(msg);
							}
						});
					} else {
						((IProxyListener)_proxyListener).onOnAppInterfaceUnregistered(msg);
						onRPCNotificationReceived(msg);
					}					
					notifyProxyClosed("OnAppInterfaceUnregistered", null, SdlDisconnectedReason.APP_INTERFACE_UNREG);
				}
			} 
			else if (functionName.equals(FunctionID.ON_KEYBOARD_INPUT.toString())) {
				final OnKeyboardInput msg = new OnKeyboardInput(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnKeyboardInput(msg);
							onRPCNotificationReceived(msg);
						}
					});
				} else {
					_proxyListener.onOnKeyboardInput(msg);
					onRPCNotificationReceived(msg);
				}
			}
			else if (functionName.equals(FunctionID.ON_TOUCH_EVENT.toString())) {
				final OnTouchEvent msg = new OnTouchEvent(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnTouchEvent(msg);
							onRPCNotificationReceived(msg);
						}
					});
				} else {
					_proxyListener.onOnTouchEvent(msg);
					onRPCNotificationReceived(msg);
				}
			}
			else if (functionName.equals(FunctionID.ON_WAY_POINT_CHANGE.toString())) {
				final OnWayPointChange msg = new OnWayPointChange(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnWayPointChange(msg);
							onRPCNotificationReceived(msg);
						}
					});
				} else {
					_proxyListener.onOnWayPointChange(msg);
					onRPCNotificationReceived(msg);
				}
			}
			else if (functionName.equals(FunctionID.ON_INTERIOR_VEHICLE_DATA.toString())) {
				final OnInteriorVehicleData msg = new OnInteriorVehicleData(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnInteriorVehicleData(msg);
							onRPCNotificationReceived(msg);
						}
					});
				} else {
					_proxyListener.onOnInteriorVehicleData(msg);
					onRPCNotificationReceived(msg);
				}
			}
			else if (functionName.equals(FunctionID.ON_RC_STATUS.toString())) {
				final OnRCStatus msg = new OnRCStatus(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnRCStatus(msg);
							onRPCNotificationReceived(msg);
						}
					});
				} else {
					_proxyListener.onOnRCStatus(msg);
					onRPCNotificationReceived(msg);
				}
			} else if (functionName.equals(FunctionID.ON_APP_SERVICE_DATA.toString())) {
				final OnAppServiceData msg = new OnAppServiceData(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnAppServiceData(msg);
							onRPCNotificationReceived(msg);
						}
					});
				} else {
					_proxyListener.onOnAppServiceData(msg);
					onRPCNotificationReceived(msg);
				}
			} else if (functionName.equals(FunctionID.ON_SYSTEM_CAPABILITY_UPDATED.toString())) {
				final OnSystemCapabilityUpdated msg = new OnSystemCapabilityUpdated(hash);
				msg.format(rpcSpecVersion, true);
				if (_callbackToUIThread) {
					// Run in UI thread
					_mainUIHandler.post(new Runnable() {
						@Override
						public void run() {
							_proxyListener.onOnSystemCapabilityUpdated(msg);
							onRPCNotificationReceived(msg);
						}
					});
				} else {
					_proxyListener.onOnSystemCapabilityUpdated(msg);
					onRPCNotificationReceived(msg);
				}
			} else {
				if (_sdlMsgVersion != null) {
					DebugTool.logInfo(TAG, "Unrecognized notification Message: " + functionName +
							" connected to SDL using message version: " + _sdlMsgVersion.getMajorVersion() + "." + _sdlMsgVersion.getMinorVersion());
				} else {
					DebugTool.logInfo(TAG, "Unrecognized notification Message: " + functionName);
				}
			} // end-if
		} // end-if notification

		SdlTrace.logProxyEvent("Proxy received RPC Message: " + functionName, SDL_LIB_TRACE_KEY);
	}

	//FIXME
	/**
	 * Temporary method to bridge the new PLAY_PAUSE and OKAY button functionality with the old
	 * OK button name. This should be removed during the next major release
	 * @param notification
	 */
	private RPCNotification handleButtonNotificationFormatting(RPCNotification notification){
		if(FunctionID.ON_BUTTON_EVENT.toString().equals(notification.getFunctionName())
				|| FunctionID.ON_BUTTON_PRESS.toString().equals(notification.getFunctionName())){

			ButtonName buttonName = (ButtonName)notification.getObject(ButtonName.class, OnButtonEvent.KEY_BUTTON_NAME);
			ButtonName compatBtnName = null;

			if(rpcSpecVersion != null && rpcSpecVersion.getMajor() >= 5){
				if(ButtonName.PLAY_PAUSE.equals(buttonName)){
					compatBtnName =  ButtonName.OK;
				}
			}else{ // rpc spec version is either null or less than 5
				if(ButtonName.OK.equals(buttonName)){
					compatBtnName = ButtonName.PLAY_PAUSE;
				}
			}

			try {
				if (compatBtnName != null) { //There is a button name that needs to be swapped out
					RPCNotification notification2;
					//The following is done because there is currently no way to make a deep copy
					//of an RPC. Since this code will be removed, it's ugliness is borderline acceptable.
					if (notification instanceof OnButtonEvent) {
						OnButtonEvent onButtonEvent = new OnButtonEvent();
						onButtonEvent.setButtonEventMode(((OnButtonEvent) notification).getButtonEventMode());
						onButtonEvent.setCustomButtonID(((OnButtonEvent) notification).getCustomButtonID());
						notification2 = onButtonEvent;
					} else if (notification instanceof OnButtonPress) {
						OnButtonPress onButtonPress = new OnButtonPress();
						onButtonPress.setButtonPressMode(((OnButtonPress) notification).getButtonPressMode());
						onButtonPress.setCustomButtonID(((OnButtonPress) notification).getCustomButtonID());
						notification2 = onButtonPress;
					} else {
						return null;
					}

					notification2.setParameters(OnButtonEvent.KEY_BUTTON_NAME, compatBtnName);
					return notification2;
				}
			}catch (Exception e){
				//Should never get here
			}
		}
		return null;
	}

	/**
	 * Get SDL Message Version
	 * @return SdlMsgVersion
	 * @throws SdlException
	 */
	public SdlMsgVersion getSdlMsgVersion() throws SdlException{
		return _sdlMsgVersion;
	}

	/**
	 * Takes a list of RPCMessages and sends it to SDL in a synchronous fashion. Responses are captured through callback on OnMultipleRequestListener.
	 * For sending requests asynchronously, use sendRequests <br>
	 *
	 * <strong>NOTE: This will override any listeners on individual RPCs</strong>
	 *
	 * @param rpcs is the list of RPCMessages being sent
	 * @param listener listener for updates and completions
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void sendSequentialRequests(final List<? extends RPCMessage> rpcs, final OnMultipleRequestListener listener) throws SdlException {
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}

		SdlTrace.logProxyEvent("Application called sendSequentialRequests", SDL_LIB_TRACE_KEY);

		synchronized(CONNECTION_REFERENCE_LOCK) {
			if (!getIsConnected()) {
				SdlTrace.logProxyEvent("Application attempted to call sendSequentialRequests without a connected transport.", SDL_LIB_TRACE_KEY);
				throw new SdlException("There is no valid connection to SDL. sendSequentialRequests cannot be called until SDL has been connected.", SdlExceptionCause.SDL_UNAVAILABLE);
			}
		}

		if (rpcs == null){
			//Log error here
			throw new SdlException("You must send some RPCs", SdlExceptionCause.INVALID_ARGUMENT);
		}

		// Break out of recursion, we have finished the requests
		if (rpcs.size() == 0) {
			if(listener != null){
				listener.onFinished();
			}
			return;
		}

		RPCMessage rpc = rpcs.remove(0);

		// Request Specifics
		if (rpc.getMessageType().equals(RPCMessage.KEY_REQUEST)) {
			RPCRequest request = (RPCRequest) rpc;
			request.setCorrelationID(CorrelationIdGenerator.generateId());

			final OnRPCResponseListener devOnRPCResponseListener = request.getOnRPCResponseListener();

			request.setOnRPCResponseListener(new OnRPCResponseListener() {
				@Override
				public void onResponse(int correlationId, RPCResponse response) {
					if (devOnRPCResponseListener != null){
						devOnRPCResponseListener.onResponse(correlationId, response);
					}
					if (listener != null) {
						listener.onResponse(correlationId, response);
						listener.onUpdate(rpcs.size());

					}
					try {
						// recurse after onResponse
						sendSequentialRequests(rpcs, listener);
					} catch (SdlException e) {
						e.printStackTrace();
						if (listener != null) {
							GenericResponse genericResponse = new GenericResponse(false, Result.GENERIC_ERROR);
							genericResponse.setInfo(e.toString());
							listener.onResponse(correlationId, genericResponse);
						}
					}
				}
			});
			sendRPCMessagePrivate(request);
		} else {
			// Notifications and Responses
			sendRPCMessagePrivate(rpc);
			if (listener != null) {
				listener.onUpdate(rpcs.size());
			}
			// recurse after sending a notification or response as there is no response.
			try {
				sendSequentialRequests(rpcs, listener);
			} catch (SdlException e) {
				e.printStackTrace();
				if (listener != null) {
					GenericResponse response = new GenericResponse(false, Result.REJECTED);
					response.setInfo(e.toString());
					listener.onResponse(0, response);
				}
			}
		}


	}

	/**
	 * Takes a list of RPCMessages and sends it to SDL. Responses are captured through callback on OnMultipleRequestListener.
	 * For sending requests synchronously, use sendSequentialRequests <br>
	 *
	 * <strong>NOTE: This will override any listeners on individual RPCs</strong>
	 *
	 * @param rpcs is the list of RPCMessages being sent
	 * @param listener listener for updates and completions
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void sendRequests(List<? extends RPCMessage> rpcs, final OnMultipleRequestListener listener) throws SdlException {

		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}

		SdlTrace.logProxyEvent("Application called sendRequests", SDL_LIB_TRACE_KEY);

		synchronized(CONNECTION_REFERENCE_LOCK) {
			if (!getIsConnected()) {
				SdlTrace.logProxyEvent("Application attempted to call sendRequests without a connected transport.", SDL_LIB_TRACE_KEY);
				throw new SdlException("There is no valid connection to SDL. sendRequests cannot be called until SDL has been connected.", SdlExceptionCause.SDL_UNAVAILABLE);
			}
		}

		if (rpcs == null){
			//Log error here
			throw new SdlException("You must send some RPCs, the array is null", SdlExceptionCause.INVALID_ARGUMENT);
		}

		int arraySize = rpcs.size();

		if (arraySize == 0) {
			throw new SdlException("You must send some RPCs, the array is empty", SdlExceptionCause.INVALID_ARGUMENT);
		}

		for (int i = 0; i < arraySize; i++) {
			RPCMessage rpc = rpcs.get(i);
			// Request Specifics
			if (rpc.getMessageType().equals(RPCMessage.KEY_REQUEST)) {
				RPCRequest request = (RPCRequest) rpc;
				final OnRPCResponseListener devOnRPCResponseListener = request.getOnRPCResponseListener();
				request.setCorrelationID(CorrelationIdGenerator.generateId());
				if (listener != null) {
					listener.addCorrelationId(request.getCorrelationID());
					request.setOnRPCResponseListener(new OnRPCResponseListener() {
						@Override
						public void onResponse(int correlationId, RPCResponse response) {
							if (devOnRPCResponseListener != null){
								devOnRPCResponseListener.onResponse(correlationId, response);
							}
							if (listener.getSingleRpcResponseListener() != null) {
								listener.getSingleRpcResponseListener().onResponse(correlationId, response);
							}
						}
					});
				}
				sendRPCMessagePrivate(request);
			}else {
				// Notifications and Responses
				sendRPCMessagePrivate(rpc);
				if (listener != null){
					listener.onUpdate(rpcs.size());
					if (rpcs.size() == 0){
						listener.onFinished();
					}
				}
			}
		}
	}

	public void sendRPC(RPCMessage message) throws SdlException {
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}

		// Test if request is null
		if (message == null) {
			SdlTrace.logProxyEvent("Application called sendRPCRequest method with a null RPCRequest.", SDL_LIB_TRACE_KEY);
			throw new IllegalArgumentException("sendRPCRequest cannot be called with a null request.");
		}

		SdlTrace.logProxyEvent("Application called sendRPCRequest method for RPCRequest: ." + message.getFunctionName(), SDL_LIB_TRACE_KEY);

		// Test if SdlConnection is null
		synchronized(CONNECTION_REFERENCE_LOCK) {
			if (!getIsConnected()) {
				SdlTrace.logProxyEvent("Application attempted to send and RPCRequest without a connected transport.", SDL_LIB_TRACE_KEY);
				throw new SdlException("There is no valid connection to SDL. sendRPCRequest cannot be called until SDL has been connected.", SdlExceptionCause.SDL_UNAVAILABLE);
			}
		}

		// Test for illegal correlation ID
		if (message.getMessageType().equals(RPCMessage.KEY_REQUEST)) {
			RPCRequest request = (RPCRequest) message;
			if (isCorrelationIDProtected(request.getCorrelationID())) {

				SdlTrace.logProxyEvent("Application attempted to use the reserved correlation ID, " + request.getCorrelationID(), SDL_LIB_TRACE_KEY);
				throw new SdlException("Invalid correlation ID. The correlation ID, " + request.getCorrelationID()
						+ " , is a reserved correlation ID.", SdlExceptionCause.RESERVED_CORRELATION_ID);
			}
		}
		// Throw exception if RPCRequest is sent when SDL is unavailable
		if (!_appInterfaceRegisterd && !message.getFunctionName().equals(FunctionID.REGISTER_APP_INTERFACE.toString())) {

			SdlTrace.logProxyEvent("Application attempted to send an RPCRequest (non-registerAppInterface), before the interface was registerd.", SDL_LIB_TRACE_KEY);
			throw new SdlException("SDL is currently unavailable. RPC Requests cannot be sent.", SdlExceptionCause.SDL_UNAVAILABLE);
		}

		if (_advancedLifecycleManagementEnabled) {
			if (message.getFunctionName().equals(FunctionID.REGISTER_APP_INTERFACE.toString())
					|| message.getFunctionName().equals(FunctionID.UNREGISTER_APP_INTERFACE.toString())) {

				SdlTrace.logProxyEvent("Application attempted to send a RegisterAppInterface or UnregisterAppInterface while using ALM.", SDL_LIB_TRACE_KEY);
				throw new SdlException("The RPCRequest, " + message.getFunctionName() +
						", is un-allowed using the Advanced Lifecycle Management Model.", SdlExceptionCause.INCORRECT_LIFECYCLE_MODEL);
			}
		}

		sendRPCMessagePrivate(message);
	}
	
	/**
	 * Takes an RPCRequest and sends it to SDL.  Responses are captured through callback on IProxyListener.  
	 * 
	 * @param request is the RPCRequest being sent
	 * @throws SdlException if an unrecoverable error is encountered
	 * @deprecated - use sendRPC instead
	 */
	@Deprecated
	public void sendRPCRequest(RPCRequest request) throws SdlException {
		sendRPC(request);
	}
	
	protected void notifyProxyClosed(final String info, final Exception e, final SdlDisconnectedReason reason) {		
		SdlTrace.logProxyEvent("NotifyProxyClose", SDL_LIB_TRACE_KEY);
		DebugTool.logInfo(TAG, "notifyProxyClosed: " + info);
		OnProxyClosed message = new OnProxyClosed(info, e, reason);
		queueInternalMessage(message);
	}

	private void passErrorToProxyListener(final String info, final Exception e) {
				
		OnError message = new OnError(info, e);
		queueInternalMessage(message);
	}
	
	private void startRPCProtocolSession() {
		
		// Set Proxy Lifecyclek Available
		if (_advancedLifecycleManagementEnabled) {
			
			try {
				registerAppInterfacePrivate(
						_sdlMsgVersionRequest,
						_applicationName,
						_ttsName,
						_ngnMediaScreenAppName,
						_vrSynonyms,
						_isMediaApp, 
						_sdlLanguageDesired,
						_hmiDisplayLanguageDesired,
						_appType,
						_appID,
						_dayColorScheme,
						_nightColorScheme,
						REGISTER_APP_INTERFACE_CORRELATION_ID);
				
			} catch (Exception e) {
				notifyProxyClosed("Failed to register application interface with SDL. Check parameter values given to SdlProxy constructor.", e, SdlDisconnectedReason.SDL_REGISTRATION_ERROR);
			}
		} else {
			InternalProxyMessage message = new InternalProxyMessage(InternalProxyMessage.OnProxyOpened);
			queueInternalMessage(message);
		}
	}
	
	// Queue internal callback message
	private void queueInternalMessage(InternalProxyMessage message) {
		synchronized(INTERNAL_MESSAGE_QUEUE_THREAD_LOCK) {
			if (_internalProxyMessageDispatcher != null) {
				_internalProxyMessageDispatcher.queueMessage(message);
			}
		}
	}
	
	// Queue incoming ProtocolMessage
	private void queueIncomingMessage(ProtocolMessage message) {
		synchronized(INCOMING_MESSAGE_QUEUE_THREAD_LOCK) {
			if (_incomingProxyMessageDispatcher != null) {
				_incomingProxyMessageDispatcher.queueMessage(message);
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
	private RPCStreamController startRPCStream(String sLocalFile, PutFile request, SessionType sType, byte rpcSessionID, Version protocolVersion)
	{		
		if (sdlSession == null) return null;		
					
		FileInputStream is = getFileInputStream(sLocalFile);
		if (is == null) return null;
		
		Long lSize = getFileInputStreamSize(is);
		if (lSize == null)
		{	
			closeFileInputStream(is);
			return null;
		}

		try {
			StreamRPCPacketizer rpcPacketizer = null;//new StreamRPCPacketizer((SdlProxyBase<IProxyListenerBase>) this, sdlSession, is, request, sType, rpcSessionID, protocolVersion, rpcSpecVersion, lSize, sdlSession);
			rpcPacketizer.start();
			return new RPCStreamController(rpcPacketizer, request.getCorrelationID());
		} catch (Exception e) {
            DebugTool.logError(TAG, "SyncConnectionUnable to start streaming:", e);
            return null;
        }			
	}

	@SuppressWarnings({"unchecked", "UnusedReturnValue"})
	private RPCStreamController startRPCStream(InputStream is, PutFile request, SessionType sType, byte rpcSessionID, Version protocolVersion)
	{		
		if (sdlSession == null) return null;
		Long lSize = request.getLength();

		if (lSize == null)
		{
			return null;
		}		
		
		try {
			StreamRPCPacketizer rpcPacketizer = null;//new StreamRPCPacketizer((SdlProxyBase<IProxyListenerBase>) this, sdlSession, is, request, sType, rpcSessionID, protocolVersion, rpcSpecVersion, lSize, sdlSession);
			rpcPacketizer.start();
			return new RPCStreamController(rpcPacketizer, request.getCorrelationID());
		} catch (Exception e) {
			DebugTool.logError(TAG, "SyncConnection Unable to start streaming:", e);
            return null;
        }			
	}

	private RPCStreamController startPutFileStream(String sPath, PutFile msg) {
		if (sdlSession == null) return null;		
		return startRPCStream(sPath, msg, SessionType.RPC, (byte)sdlSession.getSessionId(), protocolVersion);
	}

	private RPCStreamController startPutFileStream(InputStream is, PutFile msg) {
		if (sdlSession == null) return null;		
		if (is == null) return null;
		return startRPCStream(is, msg, SessionType.RPC, (byte)sdlSession.getSessionId(), protocolVersion);
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean startRPCStream(InputStream is, RPCRequest msg) {
		if (sdlSession == null) return false;
		//sdlSession.startRPCStream(is, msg, SessionType.RPC, sdlSession.getSessionId(), (byte)getProtocolVersion().getMajor());
		return true;
	}
	
	public OutputStream startRPCStream(RPCRequest msg) {
		if (sdlSession == null) return null;		
		return null;//sdlSession.startRPCStream(msg, SessionType.RPC, sdlSession.getSessionId(), (byte)getProtocolVersion().getMajor());
	}
	
	public void endRPCStream() {
		if (sdlSession == null) return;		
		//sdlSession.stopRPCStream();
	}
	
	private class CallableMethod implements Callable<Void> {

	private final long waitTime;

		public CallableMethod(int timeInMillis){
		this.waitTime=timeInMillis;
		}
		@Override
		public Void call() {
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}		

	public FutureTask<Void> createFutureTask(CallableMethod callMethod){
			return new FutureTask<Void>(callMethod);
	}

	public ScheduledExecutorService createScheduler(){
		return  Executors.newSingleThreadScheduledExecutor();
	}

	@SuppressWarnings("unused")
	public void startService(SessionType serviceType, boolean isEncrypted){
		sdlSession.startService(serviceType, isEncrypted);
	}

	@SuppressWarnings("unused")
	public void endService(SessionType serviceType){
		sdlSession.endService(serviceType);
	}



	/**
	 * @deprecated
	 *Opens the video service (serviceType 11) and subsequently streams raw H264 video from an InputStream provided by the app
	 *@return true if service is opened successfully and stream is started, return false otherwise
	 * @see #startRemoteDisplayStream(Context, Class, VideoStreamingParameters, boolean) startRemoteDisplayStream
	 * @see #startVideoStream(boolean, VideoStreamingParameters) startVideoStream
	 * @see #createOpenGLInputSurface(int, int, int, int, int, boolean) createOpenGLInputSurface
	 */
	@SuppressWarnings("unused")
	@Deprecated
	public boolean startH264(InputStream is, boolean isEncrypted) {
		
		if (sdlSession == null) return false;		
				
		navServiceStartResponseReceived = false;
		navServiceStartResponse = false;
		navServiceStartRejectedParams = null;

		// When startH264() API is used, we will not send video format / width / height information
		// with StartService. (Reasons: InputStream does not provide timestamp information so RTP
		// cannot be used. startH264() does not provide with/height information.)
		VideoStreamingParameters emptyParam = new VideoStreamingParameters();
		emptyParam.setResolution(null);
		emptyParam.setFormat(null);
		sdlSession.setDesiredVideoParams(emptyParam);

		sdlSession.startService(SessionType.NAV, isEncrypted);
		addNavListener();
		FutureTask<Void> fTask =  createFutureTask(new CallableMethod(RESPONSE_WAIT_TIME));
		ScheduledExecutorService scheduler = createScheduler();
		scheduler.execute(fTask);

		//noinspection StatementWithEmptyBody
		while (!navServiceStartResponseReceived && !fTask.isDone());
		scheduler.shutdown();

		if (navServiceStartResponse) {
			try {
				//sdlSession.startStream(is, SessionType.NAV, sdlSession.getSessionId());
				return true;
			} catch (Exception e) {
				return false;
			}			
		} else {
			return false;
		}
	}	
	
	/**
	 * @deprecated
	 *Opens the video service (serviceType 11) and subsequently provides an OutputStream to the app to use for a raw H264 video stream
	 *@return OutputStream if service is opened successfully and stream is started, return null otherwise
	 * @see #startRemoteDisplayStream(Context, Class, VideoStreamingParameters, boolean) startRemoteDisplayStream
	 * @see #startVideoStream(boolean, VideoStreamingParameters) startVideoStream
	 * @see #createOpenGLInputSurface(int, int, int, int, int, boolean) createOpenGLInputSurface
	 */
	@SuppressWarnings("unused")
	@Deprecated
	public OutputStream startH264(boolean isEncrypted) {

		if (sdlSession == null) return null;		
		
		navServiceStartResponseReceived = false;
		navServiceStartResponse = false;
		navServiceStartRejectedParams = null;

		// When startH264() API is used, we will not send video format / width / height information
		// with StartService. (Reasons: OutputStream does not provide timestamp information so RTP
		// cannot be used. startH264() does not provide with/height information.)
		VideoStreamingParameters emptyParam = new VideoStreamingParameters();
		emptyParam.setResolution(null);
		emptyParam.setFormat(null);
		sdlSession.setDesiredVideoParams(emptyParam);

		sdlSession.startService(SessionType.NAV, isEncrypted);
		addNavListener();
		FutureTask<Void> fTask =  createFutureTask(new CallableMethod(RESPONSE_WAIT_TIME));
		ScheduledExecutorService scheduler = createScheduler();
		scheduler.execute(fTask);

		//noinspection StatementWithEmptyBody
		while (!navServiceStartResponseReceived  && !fTask.isDone());
		scheduler.shutdown();

		if (navServiceStartResponse) {
			try {
				return null;//sdlSession.startStream(SessionType.NAV, sdlSession.getSessionId());
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}	
	
	/**
	 *Closes the opened video service (serviceType 11)
	 *@return true if the video service is closed successfully, return false otherwise  
	 */	
	@SuppressWarnings("unused")
	@Deprecated
	public boolean endH264() {
		return endVideoStream();
	}
	/**
	 *Pauses the stream for the opened audio service (serviceType 10)
	 *@return true if the audio service stream is paused successfully, return false otherwise  
	 */		
	@SuppressWarnings("unused")
	@Deprecated
	public boolean pausePCM() {
		return pauseAudioStream();
	}

	/**
	 *Pauses the stream for the opened video service (serviceType 11)
	 *@return true if the video service stream is paused successfully, return false otherwise  
	 */	
	@SuppressWarnings("unused")
	@Deprecated
	public boolean pauseH264() {
		return pauseVideoStream();
	}

	/**
	 *Resumes the stream for the opened audio service (serviceType 10)
	 *@return true if the audio service stream is resumed successfully, return false otherwise  
	 */	
	@SuppressWarnings("unused")
	@Deprecated
	public boolean resumePCM() {
		return resumeAudioStream();
	}

	/**
	 *Resumes the stream for the opened video service (serviceType 11)
	 *@return true if the video service is resumed successfully, return false otherwise  
	 */	
	@SuppressWarnings("unused")
	@Deprecated
	public boolean resumeH264() {
		return resumeVideoStream();
	}

	
	/**
	 *Opens the audio service (serviceType 10) and subsequently streams raw PCM audio from an InputStream provided by the app
	 *@return true if service is opened successfully and stream is started, return false otherwise  
	 */
	@SuppressWarnings("unused")
	@Deprecated
	public boolean startPCM(InputStream is, boolean isEncrypted) {
		if (sdlSession == null) return false;		
		
		pcmServiceStartResponseReceived = false;
		pcmServiceStartResponse = false;
		sdlSession.startService(SessionType.PCM, isEncrypted);

		FutureTask<Void> fTask =  createFutureTask(new CallableMethod(RESPONSE_WAIT_TIME));
		ScheduledExecutorService scheduler = createScheduler();
		scheduler.execute(fTask);

		//noinspection StatementWithEmptyBody
		while (!pcmServiceStartResponseReceived  && !fTask.isDone());
		scheduler.shutdown();

		if (pcmServiceStartResponse) {
			try {
				//sdlSession.startStream(is, SessionType.PCM, sdlSession.getSessionId());
				return true;
			} catch (Exception e) {
				return false;
			}			
		} else {
			return false;
		}
	}
	
	/**
	 *Opens the audio service (serviceType 10) and subsequently provides an OutputStream to the app
	 *@return OutputStream if service is opened successfully and stream is started, return null otherwise  
	 */		
	@SuppressWarnings("unused")
	@Deprecated
	public OutputStream startPCM(boolean isEncrypted) {
		if (sdlSession == null) return null;		
		
		pcmServiceStartResponseReceived = false;
		pcmServiceStartResponse = false;
		sdlSession.startService(SessionType.PCM, isEncrypted);

		FutureTask<Void> fTask =  createFutureTask(new CallableMethod(RESPONSE_WAIT_TIME));
		ScheduledExecutorService scheduler = createScheduler();
		scheduler.execute(fTask);

		//noinspection StatementWithEmptyBody
		while (!pcmServiceStartResponseReceived && !fTask.isDone());
		scheduler.shutdown();

		if (pcmServiceStartResponse) {
			try {
				return null;
				//return sdlSession.startStream(SessionType.PCM, sdlSession.getSessionId());
			} catch (Exception e) {
				return null;
			}
		} else {
			if (pcmServiceStartRejectedParams != null) {
				StringBuilder builder = new StringBuilder();
				for (String paramName : pcmServiceStartRejectedParams) {
					if (builder.length() > 0) {
						builder.append(", ");
					}
					builder.append(paramName);
				}
				DebugTool.logWarning(TAG, "StartService for nav failed. Rejected params: " + builder.toString());

			} else {
				DebugTool.logWarning(TAG, "StartService for nav failed (rejected params not supplied)");
			}
			return null;
		}
	}
	
	/**
	 *Closes the opened audio service (serviceType 10)
	 *@return true if the audio service is closed successfully, return false otherwise  
	 */		
	@SuppressWarnings("unused")
	@Deprecated
	public boolean endPCM() {
		return endAudioStream();
	}

    /**
     * Opens a video service (service type 11) and subsequently provides an IVideoStreamListener
     * to the app to send video data. The supplied VideoStreamingParameters will be set as desired parameters
	 * that will be used to negotiate
	 *
	 * <br><br><b>NOTE: IF USING SECONDARY TRANSPORTS, THE VIDEO SERVICE MUST BE STARTED BEFORE CALLING THIS
	 * THIS METHOD. USE A `ISdlServiceListener` TO BE NOTIFIED THAT IT STARTS THEN CALL THIS METHOD TO
	 * START STREAMING. ADD A LISTENER USE {@link #addServiceListener(SessionType, ISdlServiceListener)}.</b>
     *
     * @param isEncrypted Specify true if packets on this service have to be encrypted
     * @param parameters  Video streaming parameters including: codec which will be used for streaming (currently, only
     *                    VideoStreamingCodec.H264 is accepted), height and width of the video in pixels.
     *
     * @return IVideoStreamListener interface if service is opened successfully and streaming is
     *         started, null otherwise
	 *
	 * @see ISdlServiceListener
     */
    @SuppressWarnings("unused")
    public IVideoStreamListener startVideoStream(boolean isEncrypted, VideoStreamingParameters parameters) {
        if (sdlSession == null) {
            DebugTool.logWarning(TAG, "SdlSession is not created yet.");
            return null;
        }
        if (!sdlSession.getIsConnected()) {
            DebugTool.logWarning(TAG, "Connection is not available.");
            return null;
        }

		sdlSession.setDesiredVideoParams(parameters);

		VideoStreamingParameters acceptedParams = tryStartVideoStream(isEncrypted, parameters);
        if (acceptedParams != null) {
            return null;// sdlSession.startVideoStream();
        } else {
            return null;
        }
    }

	/**
	 * This method will try to start the video service with the requested parameters.
	 * When it returns it will attempt to store the accepted parameters if available.
	 * @param isEncrypted if the service should be encrypted
	 * @param parameters the desiered video streaming parameters
	 */
	public void startVideoService(boolean isEncrypted, VideoStreamingParameters parameters) {
		if (sdlSession == null) {
			DebugTool.logWarning(TAG, "SdlSession is not created yet.");
			return;
		}
		if (!sdlSession.getIsConnected()) {
			DebugTool.logWarning(TAG, "Connection is not available.");
			return;
		}

		sdlSession.setDesiredVideoParams(parameters);

		tryStartVideoStream(isEncrypted, parameters);
	}

    /**
     *Closes the opened video service (serviceType 11)
     *@return true if the video service is closed successfully, return false otherwise
     */
    @SuppressWarnings("unused")
    public boolean endVideoStream() {
        if (sdlSession == null){ return false; }

        navServiceEndResponseReceived = false;
        navServiceEndResponse = false;
        //sdlSession.stopVideoStream();

        FutureTask<Void> fTask =  createFutureTask(new CallableMethod(RESPONSE_WAIT_TIME));
        ScheduledExecutorService scheduler = createScheduler();
        scheduler.execute(fTask);

        //noinspection StatementWithEmptyBody
        while (!navServiceEndResponseReceived && !fTask.isDone());
        scheduler.shutdown();

        return navServiceEndResponse;
    }

    /**
     *Pauses the stream for the opened video service (serviceType 11)
     *@return true if the video service stream is paused successfully, return false otherwise
     */
    @SuppressWarnings("unused")
    public boolean pauseVideoStream() {
        return false;//sdlSession != null && sdlSession.pauseVideoStream();
    }

    /**
     *Resumes the stream for the opened video service (serviceType 11)
     *@return true if the video service is resumed successfully, return false otherwise
     */
    @SuppressWarnings("unused")
    public boolean resumeVideoStream() {
        return false;//sdlSession != null && sdlSession.resumeVideoStream();
    }

	/**
	 * Opens the video service (serviceType 11) and creates a Surface (used for streaming video) with input parameters provided by the app
	 * @param frameRate - specified rate of frames to utilize for creation of Surface 
	 * @param iFrameInterval - specified interval to utilize for creation of Surface
	 * @param width - specified width to utilize for creation of Surface
	 * @param height - specified height to utilize for creation of Surface
	 * @param bitrate - specified bitrate to utilize for creation of Surface
	 *@return Surface if service is opened successfully and stream is started, return null otherwise
	 */
    @SuppressWarnings("unused")
	public Surface createOpenGLInputSurface(int frameRate, int iFrameInterval, int width,
											int height, int bitrate, boolean isEncrypted) {
        
        if (sdlSession == null || !sdlSession.getIsConnected()){
			return null;
        }

		VideoStreamingParameters desired = new VideoStreamingParameters();
		desired.setFrameRate(frameRate);
		desired.setInterval(iFrameInterval);
		ImageResolution resolution = new ImageResolution();
		resolution.setResolutionWidth(width);
		resolution.setResolutionHeight(height);
		desired.setResolution(resolution);
		desired.setBitrate(bitrate);

		VideoStreamingParameters acceptedParams = tryStartVideoStream(isEncrypted, desired);
        if (acceptedParams != null) {
            return null;//sdlSession.createOpenGLInputSurface(frameRate, iFrameInterval, width,
                    //height, bitrate, SessionType.NAV, sdlSession.getSessionId());
        } else {
            return null;
        }
    }

	/**
	 * Starts streaming a remote display to the module if there is a connected session. This method of streaming requires the device to be on API level 19 or higher
	 * @param context a context that can be used to create the remote display
	 * @param remoteDisplay class object of the remote display. This class will be used to create an instance of the remote display and will be projected to the module
	 * @param parameters streaming parameters to be used when streaming. If null is sent in, the default/optimized options will be used.
	 *                   If you are unsure about what parameters to be used it is best to just send null and let the system determine what
	 *                   works best for the currently connected module.
	 *
	 * @param encrypted a flag of if the stream should be encrypted. Only set if you have a supplied encryption library that the module can understand.
	 */
	@TargetApi(19)
	public void startRemoteDisplayStream(Context context, final Class<? extends SdlRemoteDisplay> remoteDisplay, final VideoStreamingParameters parameters, final boolean encrypted){
		if(protocolVersion!= null && protocolVersion.getMajor() >= 5 && !_systemCapabilityManager.isCapabilitySupported(SystemCapabilityType.VIDEO_STREAMING)){
			DebugTool.logError(TAG, "Video streaming not supported on this module");
			return;
		}
		//Create streaming manager
		if(manager == null){
			manager = new VideoStreamingManager(context,this._internalInterface);
		}

		if(parameters == null){
			if(protocolVersion!= null && protocolVersion.getMajor() >= 5) {
				_systemCapabilityManager.getCapability(SystemCapabilityType.VIDEO_STREAMING, new OnSystemCapabilityListener() {
					@Override
					public void onCapabilityRetrieved(Object capability) {
						VideoStreamingParameters params = new VideoStreamingParameters();
						params.update((VideoStreamingCapability)capability);	//Streaming parameters are ready time to stream
						sdlSession.setDesiredVideoParams(params);
						manager.startVideoStreaming(remoteDisplay, params, encrypted);
					}

					@Override
					public void onError(String info) {
						DebugTool.logError(TAG, "Error retrieving video streaming capability: " + info);

					}
				});
			}else{
				//We just use default video streaming params
				VideoStreamingParameters params = new VideoStreamingParameters();
				DisplayCapabilities dispCap = (DisplayCapabilities)_systemCapabilityManager.getCapability(SystemCapabilityType.DISPLAY);
				if(dispCap !=null){
					params.setResolution(dispCap.getScreenParams().getImageResolution());
				}
				sdlSession.setDesiredVideoParams(params);
				manager.startVideoStreaming(remoteDisplay,params, encrypted);
			}
		}else{
			sdlSession.setDesiredVideoParams(parameters);
			manager.startVideoStreaming(remoteDisplay,parameters, encrypted);
		}
	}

	/**
	 * Stops the remote display stream if one has been started
	 */
	public void stopRemoteDisplayStream(){
		if(manager!=null){
			manager.dispose();
		}
		manager = null;
	}

    /**
     * Try to open a video service by using the video streaming parameters supplied.
     *
     * Only information from codecs, width and height are used during video format negotiation.
     *
     * @param isEncrypted    Specify true if packets on this service have to be encrypted
	 * @param parameters VideoStreamingParameters that are desired. Does not guarantee this is what will be accepted.
     *
     * @return If the service is opened successfully, an instance of VideoStreamingParams is
     *         returned which contains accepted video format. If the service is opened with legacy
     *         mode (i.e. without any negotiation) then an instance of VideoStreamingParams is
     *         returned. If the service was not opened then null is returned.
     */
    @SuppressWarnings("unused")
	private VideoStreamingParameters tryStartVideoStream(boolean isEncrypted, VideoStreamingParameters parameters) {
        if (sdlSession == null) {
            DebugTool.logWarning(TAG, "SdlSession is not created yet.");
            return null;
        }
        if(protocolVersion!= null && protocolVersion.getMajor() >= 5 && !_systemCapabilityManager.isCapabilitySupported(SystemCapabilityType.VIDEO_STREAMING)){
			DebugTool.logWarning(TAG, "Module doesn't support video streaming.");
			return null;
		}
        if (parameters == null) {
            DebugTool.logWarning(TAG, "Video parameters were not supplied.");
            return null;
        }

		if(!navServiceStartResponseReceived || !navServiceStartResponse //If we haven't started the service before
				|| (navServiceStartResponse && isEncrypted && !sdlSession.isServiceProtected(SessionType.NAV))) { //Or the service has been started but we'd like to start an encrypted one
			sdlSession.setDesiredVideoParams(parameters);

			navServiceStartResponseReceived = false;
			navServiceStartResponse = false;
			navServiceStartRejectedParams = null;

			sdlSession.startService(SessionType.NAV, isEncrypted);
			addNavListener();
			FutureTask<Void> fTask = createFutureTask(new CallableMethod(RESPONSE_WAIT_TIME));
			ScheduledExecutorService scheduler = createScheduler();
			scheduler.execute(fTask);

			//noinspection StatementWithEmptyBody
			while (!navServiceStartResponseReceived && !fTask.isDone()) ;
			scheduler.shutdown();
		}

        if (navServiceStartResponse) {
			if(protocolVersion!= null && protocolVersion.getMajor() < 5){ //Versions 1-4 do not support streaming parameter negotiations
				sdlSession.setAcceptedVideoParams(parameters);
			}
			return sdlSession.getAcceptedVideoParams();
        }

        if (navServiceStartRejectedParams != null) {
			StringBuilder builder = new StringBuilder();
			for (String paramName : navServiceStartRejectedParams) {
				if (builder.length() > 0) {
					builder.append(", ");
				}
				builder.append(paramName);
			}

			DebugTool.logWarning(TAG, "StartService for nav failed. Rejected params: " + builder.toString());

        } else {
			DebugTool.logWarning(TAG, "StartService for nav failed (rejected params not supplied)");
		}

        return null;
    }

	/**
	 *Starts the MediaCodec encoder utilized in conjunction with the Surface returned via the createOpenGLInputSurface method
	 */
    @SuppressWarnings("unused")
	public void startEncoder () {
        if (sdlSession == null  || !sdlSession.getIsConnected()) return;

        //sdlSession.startEncoder();
    }
    
	/**
	 *Releases the MediaCodec encoder utilized in conjunction with the Surface returned via the createOpenGLInputSurface method
	 */
    @SuppressWarnings("unused")
	public void releaseEncoder() {
		if (sdlSession == null  || !sdlSession.getIsConnected()) return;

        //sdlSession.releaseEncoder();
    }
    
	/**
	 *Releases the MediaCodec encoder utilized in conjunction with the Surface returned via the createOpenGLInputSurface method
	 */
    @SuppressWarnings("unused")
	public void drainEncoder(boolean endOfStream) {
		if (sdlSession == null  || !sdlSession.getIsConnected()) return;

        //sdlSession.drainEncoder(endOfStream);
    }

    private void addNavListener(){

    	// videos may be started and stopped. Only add this once
    	if (navServiceListener == null){

    		navServiceListener = new ISdlServiceListener() {
				@Override
				public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted) { }

				@Override
				public void onServiceEnded(SdlSession session, SessionType type) {
					// reset nav flags so nav can start upon the next transport connection
					resetNavStartFlags();
					// propagate notification up to proxy listener so the developer will know that the service is ended
					if (_proxyListener != null) {
						_proxyListener.onServiceEnded(new OnServiceEnded(type));
					}
				}

				@Override
				public void onServiceError(SdlSession session, SessionType type, String reason) {
					// if there is an error reset the flags so that there is a chance to restart streaming
					resetNavStartFlags();
				}
			};
			this.sdlSession.addServiceListener(SessionType.NAV, navServiceListener);
		}
	}

    /**
     * Opens a audio service (service type 10) and subsequently provides an IAudioStreamListener
     * to the app to send audio data.
     *
     * Currently information passed by "params" are ignored, since Audio Streaming feature lacks
     * capability negotiation mechanism. App should configure audio stream data to align with
     * head unit's capability by checking (upcoming) pcmCapabilities. The default format is in
     * 16kHz and 16 bits.
     *
     * @param isEncrypted Specify true if packets on this service have to be encrypted
     * @param codec       Audio codec which will be used for streaming. Currently, only
     *                    AudioStreamingCodec.LPCM is accepted.
     * @param params      (Reserved for future use) Additional configuration information for each
     *                    codec. If "codec" is AudioStreamingCodec.LPCM, "params" must be an
     *                    instance of LPCMParams class.
     *
     * @return IAudioStreamListener interface if service is opened successfully and streaming is
     *         started, null otherwise
     */
    @SuppressWarnings("unused")
    public IAudioStreamListener startAudioStream(boolean isEncrypted, AudioStreamingCodec codec,
                                                 AudioStreamingParams params) {
        if (sdlSession == null) {
            DebugTool.logWarning(TAG, "SdlSession is not created yet.");
            return null;
        }
        if (!sdlSession.getIsConnected()) {
            DebugTool.logWarning(TAG, "Connection is not available.");
            return null;
        }
        if (codec != AudioStreamingCodec.LPCM) {
            DebugTool.logWarning(TAG, "Audio codec " + codec + " is not supported.");
            return null;
        }

        pcmServiceStartResponseReceived = false;
        pcmServiceStartResponse = false;
        sdlSession.startService(SessionType.PCM, isEncrypted);

        FutureTask<Void> fTask = createFutureTask(new CallableMethod(RESPONSE_WAIT_TIME));
        ScheduledExecutorService scheduler = createScheduler();
        scheduler.execute(fTask);

        //noinspection StatementWithEmptyBody
        while (!pcmServiceStartResponseReceived && !fTask.isDone());
        scheduler.shutdown();

        if (pcmServiceStartResponse) {
            DebugTool.logInfo(TAG, "StartService for audio succeeded");
            return null;//sdlSession.startAudioStream();
        } else {
            if (pcmServiceStartRejectedParams != null) {
                StringBuilder builder = new StringBuilder();
                for (String paramName : pcmServiceStartRejectedParams) {
                    if (builder.length() > 0) {
                        builder.append(", ");
                    }
                    builder.append(paramName);
                }
                DebugTool.logWarning(TAG, "StartService for audio failed. Rejected params: " + builder.toString());
            } else {
                DebugTool.logWarning(TAG, "StartService for audio failed (rejected params not supplied)");
            }
            return null;
        }
    }

    /**
     *Closes the opened audio service (serviceType 10)
     *@return true if the audio service is closed successfully, return false otherwise
     */
    @SuppressWarnings("unused")
    public boolean endAudioStream() {
		if (sdlSession == null  || !sdlSession.getIsConnected()) return false;

        pcmServiceEndResponseReceived = false;
        pcmServiceEndResponse = false;
        //sdlSession.stopAudioStream();

        FutureTask<Void> fTask =  createFutureTask(new CallableMethod(RESPONSE_WAIT_TIME));
        ScheduledExecutorService scheduler = createScheduler();
        scheduler.execute(fTask);

        //noinspection StatementWithEmptyBody
        while (!pcmServiceEndResponseReceived && !fTask.isDone());
        scheduler.shutdown();

        return pcmServiceEndResponse;
    }

    /**
     *Pauses the stream for the opened audio service (serviceType 10)
     *@return true if the audio service stream is paused successfully, return false otherwise
     */
    @SuppressWarnings("unused")
    public boolean pauseAudioStream() {
        return false;//sdlSession != null && sdlSession.pauseAudioStream();
    }

    /**
     *Resumes the stream for the opened audio service (serviceType 10)
     *@return true if the audio service stream is resumed successfully, return false otherwise
     */
    @SuppressWarnings("unused")
    public boolean resumeAudioStream() {
        return false;//sdlSession != null && sdlSession.resumeAudioStream();
    }

	private void NavServiceStarted() {
		navServiceStartResponseReceived = true;
		navServiceStartResponse = true;
	}
	
	private void NavServiceStartedNACK(List<String> rejectedParams) {
		navServiceStartResponseReceived = true;
		navServiceStartResponse = false;
		navServiceStartRejectedParams = rejectedParams;
	}
	
    private void AudioServiceStarted() {
		pcmServiceStartResponseReceived = true;
		pcmServiceStartResponse = true;
	}
    
    private void RPCProtectedServiceStarted() {
    	rpcProtectedResponseReceived = true;
    	rpcProtectedStartResponse = true;
	}
    private void AudioServiceStartedNACK(List<String> rejectedParams) {
		pcmServiceStartResponseReceived = true;
		pcmServiceStartResponse = false;
		pcmServiceStartRejectedParams = rejectedParams;
	}

	private void NavServiceEnded() {
		navServiceEndResponseReceived = true;
		navServiceEndResponse = true;
	}
	
	private void NavServiceEndedNACK() {
		navServiceEndResponseReceived = true;
		navServiceEndResponse = false;
	}
	
    private void AudioServiceEnded() {
		pcmServiceEndResponseReceived = true;
		pcmServiceEndResponse = true;
	}
	
    private void AudioServiceEndedNACK() {
		pcmServiceEndResponseReceived = true;
		pcmServiceEndResponse = false;
	}

	private void resetNavStartFlags() {
		navServiceStartResponseReceived = false;
		navServiceStartResponse = false;
		navServiceStartRejectedParams = null;
	}
	
	public void setAppService(Service mService)
	{
		_appService = mService;
	}
	
	@SuppressWarnings("unused")
	public boolean startProtectedRPCService() {
		rpcProtectedResponseReceived = false;
		rpcProtectedStartResponse = false;
		sdlSession.startService(SessionType.RPC, true);

		FutureTask<Void> fTask =  createFutureTask(new CallableMethod(RESPONSE_WAIT_TIME));
		ScheduledExecutorService scheduler = createScheduler();
		scheduler.execute(fTask);

		//noinspection StatementWithEmptyBody
		while (!rpcProtectedResponseReceived  && !fTask.isDone());
		scheduler.shutdown();

		return rpcProtectedStartResponse;
	}

	@SuppressWarnings("unused")
	public void getLockScreenIcon(final OnLockScreenIconDownloadedListener l){
	    if(lockScreenIconRequest == null){
            l.onLockScreenIconDownloadError(new SdlException("This version of SDL core may not support lock screen icons.", 
                    SdlExceptionCause.LOCK_SCREEN_ICON_NOT_SUPPORTED));
	        return;
	    }
	    
	    LockScreenManager lockMan = null;//sdlSession.getLockScreenMan();
	    Bitmap bitmap = lockMan.getLockScreenIcon();
	    
	    // read bitmap if it was already downloaded so we don't have to download it every time
	    if(bitmap != null){
	        l.onLockScreenIconDownloaded(bitmap);
	    }
	    else{
    	    String url = lockScreenIconRequest.getUrl().replaceFirst("http://", "https://");
    	    //sdlSession.getLockScreenMan().downloadLockScreenIcon(url, l);
	    }
	}

	/* ******************* Public Helper Methods *************************/
	
	/*Begin V1 Enhanced helper*/
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandID -Unique command ID of the command to add.
	 *@param menuText  -Menu text for optional sub value containing menu parameters.
	 *@param parentID  -Menu parent ID for optional sub value containing menu parameters.
	 *@param position  -Menu position for optional sub value containing menu parameters.
	 *@param vrCommands -VR synonyms for this AddCommand.
	 *@param IconValue -A static hex icon value or the binary image file name identifier (sent by the PutFile RPC).
	 *@param IconType -Describes whether the image is static or dynamic
	 *@param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("SameParameterValue")
	public void addCommand(@NonNull Integer commandID,
						   String menuText, Integer parentID, Integer position,
						   Vector<String> vrCommands, String IconValue, ImageType IconType, Integer correlationID)
			throws SdlException {


		AddCommand msg = new AddCommand(commandID);
		msg.setCorrelationID(correlationID);

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
		
		sendRPCRequest(msg);
	}
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandID -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param position -Menu position for optional sub value containing menu parameters.
	 *@param vrCommands -VR synonyms for this AddCommand.
	 *@param IconValue -A static hex icon value or the binary image file name identifier (sent by the PutFile RPC).
	 *@param IconType -Describes whether the image is static or dynamic
	 *@param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void addCommand(Integer commandID,
						   String menuText, Integer position,
						   Vector<String> vrCommands, String IconValue, ImageType IconType, Integer correlationID)
			throws SdlException {
		
		addCommand(commandID, menuText, null, position, vrCommands, IconValue, IconType, correlationID);
	}
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandID -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param position -Menu position for optional sub value containing menu parameters.
	 *@param IconValue -A static hex icon value or the binary image file name identifier (sent by the PutFile RPC).
	 *@param IconType -Describes whether the image is static or dynamic
	 *@param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void addCommand(Integer commandID,
						   String menuText, Integer position, String IconValue, ImageType IconType,
						   Integer correlationID)
			throws SdlException {
		
		addCommand(commandID, menuText, null, position, null, IconValue, IconType, correlationID);
	}
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandID -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param IconValue -A static hex icon value or the binary image file name identifier (sent by the PutFile RPC).
	 *@param IconType -Describes whether the image is static or dynamic
	 *@param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void addCommand(Integer commandID,
						   String menuText, String IconValue, ImageType IconType, Integer correlationID)
			throws SdlException {
		
		addCommand(commandID, menuText, null, null, null, IconValue, IconType, correlationID);
	}
	
	/**
	 * Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param commandID -Unique command ID of the command to add.
	 * @param menuText -Menu text for optional sub value containing menu parameters.
	 * @param vrCommands -VR synonyms for this AddCommand.
	 * @param IconValue -A static hex icon value or the binary image file name identifier (sent by the PutFile RPC).
	 * @param IconType -Describes whether the image is static or dynamic
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void addCommand(Integer commandID,
						   String menuText, Vector<String> vrCommands, String IconValue, ImageType IconType, Integer correlationID)
			throws SdlException {
		
		addCommand(commandID, menuText, null, null, vrCommands, IconValue, IconType, correlationID);
	}
	
	/**
	 * Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param commandID -Unique command ID of the command to add.
	 * @param vrCommands -VR synonyms for this AddCommand.
	 * @param IconValue -A static hex icon value or the binary image file name identifier (sent by the PutFile RPC).
	 * @param IconType -Describes whether the image is static or dynamic
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void addCommand(Integer commandID,
						   Vector<String> vrCommands, String IconValue, ImageType IconType, Integer correlationID)
			throws SdlException {
		
		addCommand(commandID, null, null, null, vrCommands, IconValue, IconType, correlationID);
	}

	/*End V1 Enhanced helper*/
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandID -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param parentID  -Menu parent ID for optional sub value containing menu parameters.
	 *@param position  -Menu position for optional sub value containing menu parameters.
	 *@param vrCommands -VR synonyms for this AddCommand.
	 *@param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("SameParameterValue")
	public void addCommand(@NonNull Integer commandID,
						   String menuText, Integer parentID, Integer position,
						   Vector<String> vrCommands, Integer correlationID)
			throws SdlException {

		AddCommand msg = new AddCommand(commandID);
		msg.setCorrelationID(correlationID);
		msg.setVrCommands(vrCommands);
		if(menuText != null || parentID != null || position != null) {
			MenuParams menuParams = new MenuParams();
			menuParams.setMenuName(menuText);
			menuParams.setPosition(position);
			menuParams.setParentID(parentID);
			msg.setMenuParams(menuParams);
		}
		
		sendRPCRequest(msg);
	}
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandID -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param position  -Menu position for optional sub value containing menu parameters.
	 *@param vrCommands -VR synonyms for this AddCommand.
	 *@param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void addCommand(Integer commandID,
						   String menuText, Integer position,
						   Vector<String> vrCommands, Integer correlationID)
			throws SdlException {
		
		addCommand(commandID, menuText, null, position, vrCommands, correlationID);
	}
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandID -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param position  -Menu position for optional sub value containing menu parameters.
	 *@param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void addCommand(Integer commandID,
						   String menuText, Integer position,
						   Integer correlationID)
			throws SdlException {
		
		addCommand(commandID, menuText, null, position, null, correlationID);
	}
	
	/**
	 *Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 *@param commandID -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void addCommand(Integer commandID,
						   String menuText, Integer correlationID)
			throws SdlException {
		addCommand(commandID, menuText, null, null, (Vector<String>)null, correlationID);
	}
	
	/**
	 * Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 *@param commandID -Unique command ID of the command to add.
	 *@param menuText -Menu text for optional sub value containing menu parameters.
	 *@param vrCommands -VR synonyms for this AddCommand.
	 *@param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void addCommand(Integer commandID,
						   String menuText, Vector<String> vrCommands, Integer correlationID)
			throws SdlException {
		
		addCommand(commandID, menuText, null, null, vrCommands, correlationID);
	}
	
	/**
	 * Sends an AddCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 *@param commandID -Unique command ID of the command to add.
	 *@param vrCommands -VR synonyms for this AddCommand.
	 *@param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void addCommand(Integer commandID,
						   Vector<String> vrCommands, Integer correlationID)
			throws SdlException {
		
		addCommand(commandID, null, null, null, vrCommands, correlationID);
	}

	/**
	 * Sends an AddSubMenu RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 * @param menuID -Unique ID of the sub menu to add.
	 * @param menuName -Text to show in the menu for this sub menu.
	 * @param position -Position within the items that are are at top level of the in application menu.
	 * @param menuIcon -Image to be be shown along with the submenu item
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("SameParameterValue")
	public void addSubMenu(@NonNull Integer menuID, @NonNull String menuName,
						   Integer position, Image menuIcon, Integer correlationID)
			throws SdlException {

		AddSubMenu msg = new AddSubMenu(menuID, menuName);
		msg.setCorrelationID(correlationID);
		msg.setPosition(position);
		msg.setMenuIcon(menuIcon);

		sendRPCRequest(msg);
	}

	/**
	 * Sends an AddSubMenu RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param menuID -Unique ID of the sub menu to add.
	 * @param menuName -Text to show in the menu for this sub menu.
	 * @param position -Position within the items that are are at top level of the in application menu.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@Deprecated
	@SuppressWarnings("SameParameterValue")
	public void addSubMenu(@NonNull Integer menuID, @NonNull String menuName,
						   Integer position, Integer correlationID)
			throws SdlException {

		addSubMenu(menuID, menuName, position, null, correlationID);
	}
	
	/**
	 * Sends an AddSubMenu RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param menuID -Unique ID of the sub menu to add.
	 * @param menuName -Text to show in the menu for this sub menu.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@Deprecated
	@SuppressWarnings("unused")
	public void addSubMenu(Integer menuID, String menuName,
						   Integer correlationID) throws SdlException {
		
		addSubMenu(menuID, menuName, null, null, correlationID);
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
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("SameParameterValue")
	public void alert(String ttsText, String alertText1,
					  String alertText2, String alertText3, Boolean playTone, Integer duration, Vector<SoftButton> softButtons,
					  Integer correlationID) throws SdlException {

		Vector<TTSChunk> chunks = TTSChunkFactory.createSimpleTTSChunks(ttsText);
		Alert msg = new Alert();
		msg.setCorrelationID(correlationID);
		msg.setAlertText1(alertText1);
		msg.setAlertText2(alertText2);
		msg.setAlertText3(alertText3);
		msg.setDuration(duration);
		msg.setPlayTone(playTone);
		msg.setTtsChunks(chunks);
		msg.setSoftButtons(softButtons);

		sendRPCRequest(msg);
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
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	public void alert(Vector<TTSChunk> ttsChunks,
			String alertText1, String alertText2, String alertText3, Boolean playTone,
			Integer duration, Vector<SoftButton> softButtons, Integer correlationID) throws SdlException {

		Alert msg = new Alert();
		msg.setCorrelationID(correlationID);
		msg.setAlertText1(alertText1);
		msg.setAlertText2(alertText2);
		msg.setAlertText3(alertText3);
		msg.setDuration(duration);
		msg.setPlayTone(playTone);
		msg.setTtsChunks(ttsChunks);
		msg.setSoftButtons(softButtons);

		sendRPCRequest(msg);
	}
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param ttsText -The text to speech message in the form of a string.
	 * @param playTone -Defines if tone should be played.
	 * @param softButtons -A list of App defined SoftButtons.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void alert(String ttsText, Boolean playTone, Vector<SoftButton> softButtons,
					  Integer correlationID) throws SdlException {
		
		alert(ttsText, null, null, null, playTone, null, softButtons, correlationID);
	}
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param chunks -A list of text/phonemes to speak in the form of ttsChunks.
	 * @param playTone -Defines if tone should be played.
	 * @param softButtons -A list of App defined SoftButtons.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void alert(Vector<TTSChunk> chunks, Boolean playTone, Vector<SoftButton> softButtons,
					  Integer correlationID) throws SdlException {
		
		alert(chunks, null, null, null, playTone, null, softButtons, correlationID);
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
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void alert(String alertText1, String alertText2, String alertText3,
					  Boolean playTone, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
			throws SdlException {
		
		alert((Vector<TTSChunk>)null, alertText1, alertText2, alertText3, playTone, duration, softButtons, correlationID);
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
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("SameParameterValue")
	public void alert(String ttsText, String alertText1,
					  String alertText2, Boolean playTone, Integer duration,
					  Integer correlationID) throws SdlException {

		Vector<TTSChunk> chunks = TTSChunkFactory.createSimpleTTSChunks(ttsText);
		Alert msg = new Alert();
		msg.setCorrelationID(correlationID);
		msg.setAlertText1(alertText1);
		msg.setAlertText2(alertText2);
		msg.setDuration(duration);
		msg.setPlayTone(playTone);
		msg.setTtsChunks(chunks);

		sendRPCRequest(msg);
	}
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param ttsChunks -A list of text/phonemes to speak in the form of ttsChunks.
	 * @param alertText1 -The first line of the alert text field.
	 * @param alertText2 -The second line of the alert text field.
	 * @param playTone -Defines if tone should be played.
	 * @param duration -Timeout in milliseconds.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	public void alert(Vector<TTSChunk> ttsChunks,
			String alertText1, String alertText2, Boolean playTone,
			Integer duration, Integer correlationID) throws SdlException {

		Alert msg = new Alert();
		msg.setCorrelationID(correlationID);
		msg.setAlertText1(alertText1);
		msg.setAlertText2(alertText2);
		msg.setDuration(duration);
		msg.setPlayTone(playTone);
		msg.setTtsChunks(ttsChunks);

		sendRPCRequest(msg);
	}
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param ttsText -The text to speech message in the form of a string.
	 * @param playTone -Defines if tone should be played.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void alert(String ttsText, Boolean playTone,
					  Integer correlationID) throws SdlException {
		
		alert(ttsText, null, null, playTone, null, correlationID);
	}
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param chunks -A list of text/phonemes to speak in the form of ttsChunks.
	 * @param playTone -Defines if tone should be played.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void alert(Vector<TTSChunk> chunks, Boolean playTone,
					  Integer correlationID) throws SdlException {
		
		alert(chunks, null, null, playTone, null, correlationID);
	}
	
	/**
	 * Sends an Alert RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param alertText1 -The first line of the alert text field.
	 * @param alertText2 -The second line of the alert text field.
	 * @param playTone -Defines if tone should be played.
	 * @param duration -Timeout in milliseconds.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void alert(String alertText1, String alertText2,
					  Boolean playTone, Integer duration, Integer correlationID)
			throws SdlException {
		
		alert((Vector<TTSChunk>)null, alertText1, alertText2, playTone, duration, correlationID);
	}
	
	/**
	 * Sends a CreateInteractionChoiceSet RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param choiceSet to be sent to the module
	 * @param interactionChoiceSetID to be used in reference to the supplied choiceSet
	 * @param correlationID to be set to the RPCRequest
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void createInteractionChoiceSet(
			@NonNull Vector<Choice> choiceSet, @NonNull Integer interactionChoiceSetID,
			Integer correlationID) throws SdlException {

		CreateInteractionChoiceSet msg = new CreateInteractionChoiceSet(interactionChoiceSetID, choiceSet);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	/**
	 * Sends a DeleteCommand RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param commandID -ID of the command(s) to delete.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void deleteCommand(@NonNull Integer commandID,
							  Integer correlationID) throws SdlException {

		DeleteCommand msg = new DeleteCommand(commandID);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	/**
	 * Sends a DeleteInteractionChoiceSet RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param interactionChoiceSetID -ID of the interaction choice set to delete.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void deleteInteractionChoiceSet(
			@NonNull Integer interactionChoiceSetID, Integer correlationID)
			throws SdlException {

		DeleteInteractionChoiceSet msg = new DeleteInteractionChoiceSet(interactionChoiceSetID);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	/**
	 * Sends a DeleteSubMenu RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param menuID -The menuID of the submenu to delete.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void deleteSubMenu(Integer menuID,
							  Integer correlationID) throws SdlException {

		DeleteSubMenu msg = new DeleteSubMenu(menuID);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	
	
	/*Begin V1 Enhanced helper*/
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initPrompt -Intial prompt spoken to the user at the start of an interaction.      	
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetID -Interaction choice set IDs to use with an interaction.
	 * @param vrHelp -Suggested VR Help Items to display on-screen during Perform Interaction.      	
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void performInteraction(String initPrompt,
								   @NonNull String displayText, @NonNull Integer interactionChoiceSetID, Vector<VrHelpItem> vrHelp,
								   Integer correlationID) throws SdlException {

		Vector<Integer> interactionChoiceSetIDs = new Vector<Integer>();
		interactionChoiceSetIDs.add(interactionChoiceSetID);
		Vector<TTSChunk> initChunks = TTSChunkFactory.createSimpleTTSChunks(initPrompt);
		PerformInteraction msg = new PerformInteraction(displayText, InteractionMode.BOTH, interactionChoiceSetIDs);
		msg.setInitialPrompt(initChunks);
		msg.setVrHelp(vrHelp);
		msg.setCorrelationID(correlationID);
		
		sendRPCRequest(msg);
	}
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initPrompt -Intial prompt spoken to the user at the start of an interaction.      	
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetID -Interaction choice set IDs to use with an interaction.
	 * @param helpPrompt -Help text that is spoken when a user speaks "help" during the interaction.
	 * @param timeoutPrompt -Timeout text that is spoken when a VR interaction times out.
	 * @param interactionMode - The method in which the user is notified and uses the interaction (Manual,VR,Both).
	 * @param timeout -Timeout in milliseconds.
	 * @param vrHelp -Suggested VR Help Items to display on-screen during Perform Interaction.      	
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void performInteraction(String initPrompt,
								   @NonNull String displayText, @NonNull Integer interactionChoiceSetID,
								   String helpPrompt, String timeoutPrompt,
								   @NonNull InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp,
								   Integer correlationID) throws SdlException {

		Vector<Integer> interactionChoiceSetIDs = new Vector<Integer>();
		interactionChoiceSetIDs.add(interactionChoiceSetID);
		Vector<TTSChunk> initChunks = TTSChunkFactory.createSimpleTTSChunks(initPrompt);
		Vector<TTSChunk> helpChunks = TTSChunkFactory.createSimpleTTSChunks(helpPrompt);
		Vector<TTSChunk> timeoutChunks = TTSChunkFactory.createSimpleTTSChunks(timeoutPrompt);
		PerformInteraction msg = new PerformInteraction(displayText, interactionMode, interactionChoiceSetIDs);
		msg.setInitialPrompt(initChunks);
		msg.setTimeout(timeout);
		msg.setHelpPrompt(helpChunks);
		msg.setTimeoutPrompt(timeoutChunks);
		msg.setVrHelp(vrHelp);
		msg.setCorrelationID(correlationID);
		
		sendRPCRequest(msg);
	}
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initPrompt -Intial prompt spoken to the user at the start of an interaction.      	
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetIDList -A list of interaction choice set IDs to use with an interaction.
	 * @param helpPrompt -Help text that is spoken when a user speaks "help" during the interaction.
	 * @param timeoutPrompt -Timeout text that is spoken when a VR interaction times out.
	 * @param interactionMode - The method in which the user is notified and uses the interaction (Manual,VR,Both).
	 * @param timeout -Timeout in milliseconds.
	 * @param vrHelp -Suggested VR Help Items to display on-screen during Perform Interaction.      	
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void performInteraction(String initPrompt,
								   @NonNull String displayText, @NonNull Vector<Integer> interactionChoiceSetIDList,
								   String helpPrompt, String timeoutPrompt,
								   @NonNull InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp,
								   Integer correlationID) throws SdlException {

		Vector<TTSChunk> initChunks = TTSChunkFactory.createSimpleTTSChunks(initPrompt);
		Vector<TTSChunk> helpChunks = TTSChunkFactory.createSimpleTTSChunks(helpPrompt);
		Vector<TTSChunk> timeoutChunks = TTSChunkFactory.createSimpleTTSChunks(timeoutPrompt);
		PerformInteraction msg = new PerformInteraction(displayText, interactionMode, interactionChoiceSetIDList);
		msg.setInitialPrompt(initChunks);
		msg.setTimeout(timeout);
		msg.setHelpPrompt(helpChunks);
		msg.setTimeoutPrompt(timeoutChunks);
		msg.setVrHelp(vrHelp);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initChunks -A list of text/phonemes to speak for the initial prompt in the form of ttsChunks.
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetIDList -A list of interaction choice set IDs to use with an interaction.
	 * @param helpChunks -A list of text/phonemes to speak for the help text that is spoken when a user speaks "help" during the interaction.
	 * @param timeoutChunks A list of text/phonems to speak for the timeout text that is spoken when a VR interaction times out.
	 * @param interactionMode - The method in which the user is notified and uses the interaction (Manual,VR,Both).
	 * @param timeout -Timeout in milliseconds.
	 * @param vrHelp -Suggested VR Help Items to display on-screen during Perform Interaction.      	
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void performInteraction(
			Vector<TTSChunk> initChunks, @NonNull String displayText,
			@NonNull Vector<Integer> interactionChoiceSetIDList,
			Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks,
			@NonNull InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp,
			Integer correlationID) throws SdlException {

		PerformInteraction msg = new PerformInteraction(displayText, interactionMode, interactionChoiceSetIDList);
		msg.setInitialPrompt(initChunks);
		msg.setTimeout(timeout);
		msg.setHelpPrompt(helpChunks);
		msg.setTimeoutPrompt(timeoutChunks);
		msg.setVrHelp(vrHelp);
		msg.setCorrelationID(correlationID);
		
		sendRPCRequest(msg);
	}
	
	/*End V1 Enhanced*/
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initPrompt -Intial prompt spoken to the user at the start of an interaction.      	
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetID -Interaction choice set IDs to use with an interaction.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void performInteraction(String initPrompt,
								   @NonNull String displayText, @NonNull Integer interactionChoiceSetID,
								   Integer correlationID) throws SdlException {

		Vector<Integer> interactionChoiceSetIDs = new Vector<Integer>();
		interactionChoiceSetIDs.add(interactionChoiceSetID);
		Vector<TTSChunk> initChunks = TTSChunkFactory.createSimpleTTSChunks(initPrompt);
		PerformInteraction msg = new PerformInteraction(displayText, InteractionMode.BOTH, interactionChoiceSetIDs);
		msg.setInitialPrompt(initChunks);
		msg.setCorrelationID(correlationID);
		
		sendRPCRequest(msg);
	}
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initPrompt -Intial prompt spoken to the user at the start of an interaction.      	
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetID -Interaction choice set IDs to use with an interaction.
	 * @param helpPrompt -Help text that is spoken when a user speaks "help" during the interaction. 
	 * @param timeoutPrompt -Timeout text that is spoken when a VR interaction times out.
	 * @param interactionMode - The method in which the user is notified and uses the interaction (Manual,VR,Both).
	 * @param timeout -Timeout in milliseconds.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void performInteraction(String initPrompt,
								   @NonNull String displayText, @NonNull Integer interactionChoiceSetID,
								   String helpPrompt, String timeoutPrompt,
								   @NonNull InteractionMode interactionMode, Integer timeout,
								   Integer correlationID) throws SdlException {

		Vector<Integer> interactionChoiceSetIDs = new Vector<Integer>();
		interactionChoiceSetIDs.add(interactionChoiceSetID);
		Vector<TTSChunk> initChunks = TTSChunkFactory.createSimpleTTSChunks(initPrompt);
		Vector<TTSChunk> helpChunks = TTSChunkFactory.createSimpleTTSChunks(helpPrompt);
		Vector<TTSChunk> timeoutChunks = TTSChunkFactory.createSimpleTTSChunks(timeoutPrompt);
		PerformInteraction msg = new PerformInteraction(displayText, interactionMode, interactionChoiceSetIDs);
		msg.setInitialPrompt(initChunks);
		msg.setTimeout(timeout);
		msg.setHelpPrompt(helpChunks);
		msg.setTimeoutPrompt(timeoutChunks);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initPrompt -Intial prompt spoken to the user at the start of an interaction.      	
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetIDList -A list of interaction choice set IDs to use with an interaction.
	 * @param helpPrompt -Help text that is spoken when a user speaks "help" during the interaction. 
	 * @param timeoutPrompt -Timeout text that is spoken when a VR interaction times out.
	 * @param interactionMode - The method in which the user is notified and uses the interaction (Manual,VR,Both).
	 * @param timeout -Timeout in milliseconds.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void performInteraction(String initPrompt,
								   @NonNull String displayText, @NonNull Vector<Integer> interactionChoiceSetIDList,
								   String helpPrompt, String timeoutPrompt,
								   @NonNull InteractionMode interactionMode, Integer timeout,
								   Integer correlationID) throws SdlException {

		Vector<TTSChunk> initChunks = TTSChunkFactory.createSimpleTTSChunks(initPrompt);
		Vector<TTSChunk> helpChunks = TTSChunkFactory.createSimpleTTSChunks(helpPrompt);
		Vector<TTSChunk> timeoutChunks = TTSChunkFactory.createSimpleTTSChunks(timeoutPrompt);
		PerformInteraction msg = new PerformInteraction(displayText, interactionMode, interactionChoiceSetIDList);
		msg.setInitialPrompt(initChunks);
		msg.setTimeout(timeout);
		msg.setHelpPrompt(helpChunks);
		msg.setTimeoutPrompt(timeoutChunks);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	/**
	 * Sends a PerformInteraction RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param initChunks -A list of text/phonemes to speak for the initial prompt in the form of ttsChunks.
	 * @param displayText -Text to be displayed first.
	 * @param interactionChoiceSetIDList -A list of interaction choice set IDs to use with an interaction.
	 * @param helpChunks -A list of text/phonemes to speak for the help text that is spoken when a user speaks "help" during the interaction.
	 * @param timeoutChunks A list of text/phonems to speak for the timeout text that is spoken when a VR interaction times out.
	 * @param interactionMode - The method in which the user is notified and uses the interaction (Manual,VR,Both).
	 * @param timeout -Timeout in milliseconds.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void performInteraction(
			Vector<TTSChunk> initChunks, @NonNull String displayText,
			@NonNull Vector<Integer> interactionChoiceSetIDList,
			Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks,
			@NonNull InteractionMode interactionMode, Integer timeout,
			Integer correlationID) throws SdlException {

		PerformInteraction msg = new PerformInteraction(displayText, interactionMode, interactionChoiceSetIDList);
		msg.setInitialPrompt(initChunks);
		msg.setTimeout(timeout);
		msg.setHelpPrompt(helpChunks);
		msg.setTimeoutPrompt(timeoutChunks);
		msg.setCorrelationID(correlationID);
		
		sendRPCRequest(msg);
	}

	// Protected registerAppInterface used to ensure only non-ALM applications call
	// reqisterAppInterface
	protected void registerAppInterfacePrivate(
			@NonNull SdlMsgVersion sdlMsgVersion, @NonNull String appName, Vector<TTSChunk> ttsName,
			String ngnMediaScreenAppName, Vector<String> vrSynonyms, @NonNull Boolean isMediaApp,
			@NonNull Language languageDesired, @NonNull Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType,
			@NonNull String appID, TemplateColorScheme dayColorScheme, TemplateColorScheme nightColorScheme, Integer correlationID)
			throws SdlException {
		String carrierName = null;
		if(telephonyManager != null){
			carrierName = telephonyManager.getNetworkOperatorName();
		}

		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setHardware(android.os.Build.MODEL);
		deviceInfo.setOs("Android");
		deviceInfo.setOsVersion(Build.VERSION.RELEASE);
		deviceInfo.setCarrier(carrierName);

		if (sdlMsgVersion == null) {
			sdlMsgVersion = new SdlMsgVersion();
			if(protocolVersion.getMajor() == 1) {
				DebugTool.logInfo(TAG, "Connected to an older module, must send 1.0.0 as RPC spec");
				sdlMsgVersion.setMajorVersion(1);
				sdlMsgVersion.setMinorVersion(0);
			}else {
				sdlMsgVersion.setMajorVersion(MAX_SUPPORTED_RPC_VERSION.getMajor());
				sdlMsgVersion.setMinorVersion(MAX_SUPPORTED_RPC_VERSION.getMinor());
			}
		}
		if (languageDesired == null) {
			languageDesired = Language.EN_US;
		}
		if (hmiDisplayLanguageDesired == null) {
			hmiDisplayLanguageDesired = Language.EN_US;
		}

		RegisterAppInterface msg = new RegisterAppInterface(sdlMsgVersion, appName, isMediaApp, languageDesired, hmiDisplayLanguageDesired, appID);

		if (correlationID != null) {
			msg.setCorrelationID(correlationID);
		}

		msg.setDeviceInfo(deviceInfo);

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

		msg.setAppHMIType(appType);

		msg.setDayColorScheme(dayColorScheme);
		msg.setNightColorScheme(nightColorScheme);

		if (_bAppResumeEnabled)
		{
			if (_lastHashID != null)
				msg.setHashID(_lastHashID);
		}
		
		Intent sendIntent = createBroadcastIntent();
		updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionID.REGISTER_APP_INTERFACE.toString());
		updateBroadcastIntent(sendIntent, "TYPE", RPCMessage.KEY_REQUEST);
		updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationID());
		updateBroadcastIntent(sendIntent, "DATA",serializeJSON(msg));
		sendBroadcastIntent(sendIntent);

		sendRPCMessagePrivate(msg);
	}
	
	/*Begin V1 Enhanced helper function*/

	/**
	 * Sends a SetGlobalProperties RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param helpPrompt that will be used for the VR screen
	 * @param timeoutPrompt string to be displayed after timeout
	 * @param vrHelpTitle string that may be displayed on VR prompt dialog
	 * @param vrHelp a list of VR synonyms that may be displayed to user
	 * @param correlationID to be attached to the request
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void setGlobalProperties(
			String helpPrompt, String timeoutPrompt, String vrHelpTitle, Vector<VrHelpItem> vrHelp, Integer correlationID) 
		throws SdlException {

		SetGlobalProperties req = new SetGlobalProperties();
		req.setCorrelationID(correlationID);
		req.setHelpPrompt(TTSChunkFactory.createSimpleTTSChunks(helpPrompt));
		req.setTimeoutPrompt(TTSChunkFactory.createSimpleTTSChunks(timeoutPrompt));
		req.setVrHelpTitle(vrHelpTitle);
		req.setVrHelp(vrHelp);
		
		sendRPCRequest(req);
	}
	
	/**
	 * Sends a SetGlobalProperties RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param helpChunks tts chunks that should be used when prompting the user
	 * @param timeoutChunks tts chunks that will be used when a timeout occurs
	 * @param vrHelpTitle string that may be displayed on VR prompt dialog
	 * @param vrHelp a list of VR synonyms that may be displayed to user
	 * @param correlationID ID to be attached to the RPCRequest that correlates the RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void setGlobalProperties(
			Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, String vrHelpTitle, Vector<VrHelpItem> vrHelp,
			Integer correlationID) throws SdlException {

		SetGlobalProperties req = new SetGlobalProperties();
		req.setCorrelationID(correlationID);
		req.setHelpPrompt(helpChunks);
		req.setTimeoutPrompt(timeoutChunks);
		req.setVrHelpTitle(vrHelpTitle);
		req.setVrHelp(vrHelp);

		sendRPCRequest(req);
	}

	/*End V1 Enhanced helper function*/	
	
	/**
	 * Sends a SetGlobalProperties RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 * @param helpPrompt that will be used for the VR screen
	 * @param timeoutPrompt string to be displayed after timeout
	 * @param correlationID ID to be attached to the RPCRequest that correlates the RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void setGlobalProperties(
			String helpPrompt, String timeoutPrompt, Integer correlationID) 
		throws SdlException {

		SetGlobalProperties req = new SetGlobalProperties();
		req.setCorrelationID(correlationID);
		req.setHelpPrompt(TTSChunkFactory.createSimpleTTSChunks(helpPrompt));
		req.setTimeoutPrompt(TTSChunkFactory.createSimpleTTSChunks(timeoutPrompt));
		
		sendRPCRequest(req);
	}
	
	/**
	 * Sends a SetGlobalProperties RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 *
	 * @param helpChunks tts chunks that should be used when prompting the user
	 * @param timeoutChunks tts chunks that will be used when a timeout occurs
	 * @param correlationID ID to be attached to the RPCRequest that correlates the RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void setGlobalProperties(
			Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks,
			Integer correlationID) throws SdlException {

		SetGlobalProperties req = new SetGlobalProperties();
		req.setCorrelationID(correlationID);
		req.setHelpPrompt(helpChunks);
		req.setTimeoutPrompt(timeoutChunks);

		sendRPCRequest(req);
	}
	
	@SuppressWarnings("unused")
	public void resetGlobalProperties(Vector<GlobalProperty> properties,
									  Integer correlationID) throws SdlException {
		
		ResetGlobalProperties req = new ResetGlobalProperties();
		
		req.setCorrelationID(correlationID);
		req.setProperties(properties);
		
		sendRPCRequest(req);
	}
	                                                        
	
	/**
	 * Sends a SetMediaClockTimer RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param hours integer for hours
	 * @param minutes integer for minutes
	 * @param seconds integer for seconds
	 * @param updateMode mode in which the media clock timer should be updated
	 * @param correlationID ID to be attached to the RPCRequest that correlates the RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void setMediaClockTimer(Integer hours,
								   Integer minutes, Integer seconds, @NonNull UpdateMode updateMode,
								   Integer correlationID) throws SdlException {

		SetMediaClockTimer msg = new SetMediaClockTimer(updateMode);
		if (hours != null || minutes != null || seconds != null) {
			StartTime startTime = new StartTime(hours, minutes, seconds);
			msg.setStartTime(startTime);
		}
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	/**
	 * Pauses the media clock. Responses are captured through callback on IProxyListener.
	 * 
	 * @param correlationID ID to be attached to the RPCRequest that correlates the RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void pauseMediaClockTimer(Integer correlationID)
			throws SdlException {

		SetMediaClockTimer msg = new SetMediaClockTimer(UpdateMode.PAUSE);
		StartTime startTime = new StartTime(0, 0, 0);
		msg.setStartTime(startTime);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	/**
	 * Resumes the media clock. Responses are captured through callback on IProxyListener.
	 * 
	 * @param correlationID ID to be attached to the RPCRequest that correlates the RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void resumeMediaClockTimer(Integer correlationID)
			throws SdlException {

		SetMediaClockTimer msg = new SetMediaClockTimer(UpdateMode.RESUME);
		StartTime startTime = new StartTime(0, 0, 0);
		msg.setStartTime(startTime);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	/**
	 * Clears the media clock. Responses are captured through callback on IProxyListener.
	 * 
	 * @param correlationID ID to be attached to the RPCRequest that correlates the RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void clearMediaClockTimer(Integer correlationID)
			throws SdlException {

		Show msg = new Show();
		msg.setCorrelationID(correlationID);
		msg.setMediaClock("     ");

		sendRPCRequest(msg);
	}
		
	/*Begin V1 Enhanced helper*/
	/**
	 * Sends a Show RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param mainText1 text displayed in a single or upper display line.
	 * @param mainText2 text displayed on the second display line.
	 * @param mainText3 text displayed on the second "page" first display line.
	 * @param mainText4 text displayed on the second "page" second display line.
	 * @param statusBar text is placed in the status bar area (Only valid for NAVIGATION apps)
	 * @param mediaClock text value for MediaClock field.
	 * @param mediaTrack text displayed in the track field.
	 * @param graphic image struct determining whether static or dynamic image to display in app.
	 * @param softButtons app defined SoftButtons.
	 * @param customPresets app labeled on-screen presets.
	 * @param alignment specifies how mainText1 and mainText2s texts should be aligned on display.
	 * @param correlationID ID to be attached to the RPCRequest that correlates the RPCResponse -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("SameParameterValue")
	public void show(String mainText1, String mainText2, String mainText3, String mainText4,
					 String statusBar, String mediaClock, String mediaTrack,
					 Image graphic, Vector<SoftButton> softButtons, Vector <String> customPresets,
					 TextAlignment alignment, Integer correlationID)
			throws SdlException {

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

		sendRPCRequest(msg);
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
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void show(String mainText1, String mainText2, String mainText3, String mainText4,
					 Image graphic, Vector<SoftButton> softButtons, Vector <String> customPresets,
					 TextAlignment alignment, Integer correlationID)
			throws SdlException {
		
		show(mainText1, mainText2, mainText3, mainText4, null, null, null, graphic, softButtons, customPresets, alignment, correlationID);
	}		
	/*End V1 Enhanced helper*/
	
	/**
	 * Sends a Show RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param mainText1 text displayed in a single or upper display line.
	 * @param mainText2 text displayed on the second display line.
	 * @param statusBar text is placed in the status bar area (Only valid for NAVIGATION apps)
	 * @param mediaClock text value for MediaClock field.
	 * @param mediaTrack text displayed in the track field.
	 * @param alignment specifies how mainText1 and mainText2s texts should be aligned on display.
	 * @param correlationID unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("SameParameterValue")
	public void show(String mainText1, String mainText2,
					 String statusBar, String mediaClock, String mediaTrack,
					 TextAlignment alignment, Integer correlationID)
			throws SdlException {

		Show msg = new Show();
		msg.setCorrelationID(correlationID);
		msg.setMainField1(mainText1);
		msg.setMainField2(mainText2);
		msg.setStatusBar(statusBar);
		msg.setMediaClock(mediaClock);
		msg.setMediaTrack(mediaTrack);
		msg.setAlignment(alignment);

		sendRPCRequest(msg);
	}
	
	/**
	 * Sends a Show RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param mainText1 -Text displayed in a single or upper display line.
	 * @param mainText2 -Text displayed on the second display line.
	 * @param alignment -Specifies how mainText1 and mainText2s texts should be aligned on display.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void show(String mainText1, String mainText2,
					 TextAlignment alignment, Integer correlationID)
			throws SdlException {
		
		show(mainText1, mainText2, null, null, null, alignment, correlationID);
	}
	
	/**
	 * Sends a Speak RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param ttsText -The text to speech message in the form of a string.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void speak(@NonNull String ttsText, Integer correlationID)
			throws SdlException {

		Speak msg = new Speak(TTSChunkFactory.createSimpleTTSChunks(ttsText));
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	/**
	 * Sends a Speak RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param ttsChunks -Text/phonemes to speak in the form of ttsChunks.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void speak(@NonNull Vector<TTSChunk> ttsChunks,
					  Integer correlationID) throws SdlException {

		Speak msg = new Speak(ttsChunks);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	/**
	 * Sends a SubscribeButton RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param buttonName -Name of the button to subscribe.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void subscribeButton(@NonNull ButtonName buttonName,
								Integer correlationID) throws SdlException {

		SubscribeButton msg = new SubscribeButton(buttonName);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	// Protected unregisterAppInterface used to ensure no non-ALM app calls
	// unregisterAppInterface.
	protected void unregisterAppInterfacePrivate(Integer correlationID) 
		throws SdlException {

		UnregisterAppInterface msg = new UnregisterAppInterface();
		msg.setCorrelationID(correlationID);

		Intent sendIntent = createBroadcastIntent();

		updateBroadcastIntent(sendIntent, "RPC_NAME", FunctionID.UNREGISTER_APP_INTERFACE.toString());
		updateBroadcastIntent(sendIntent, "TYPE", RPCMessage.KEY_REQUEST);
		updateBroadcastIntent(sendIntent, "CORRID", msg.getCorrelationID());
		updateBroadcastIntent(sendIntent, "DATA",serializeJSON(msg));
		sendBroadcastIntent(sendIntent);

		sendRPCMessagePrivate(msg);
	}
	
	/**
	 * Sends an UnsubscribeButton RPCRequest to SDL. Responses are captured through callback on IProxyListener.
	 * 
	 * @param buttonName -Name of the button to unsubscribe.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void unsubscribeButton(@NonNull ButtonName buttonName,
								  Integer correlationID) throws SdlException {

		UnsubscribeButton msg = new UnsubscribeButton(buttonName);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	/**
	 * Creates a choice to be added to a choiceset. Choice has both a voice and a visual menu component.
	 * 
	 * @param choiceID -Unique ID used to identify this choice (returned in callback).
	 * @param choiceMenuName -Text name displayed for this choice.
	 * @param choiceVrCommands -Vector of vrCommands used to select this choice by voice. Must contain
	 * 			at least one non-empty element.
	 * @return Choice created. 
	 */
	@SuppressWarnings("unused")
	public Choice createChoiceSetChoice(Integer choiceID, String choiceMenuName,
										Vector<String> choiceVrCommands) {
		Choice returnChoice = new Choice();
		
		returnChoice.setChoiceID(choiceID);
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
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void performaudiopassthru(String initialPrompt, String audioPassThruDisplayText1, String audioPassThruDisplayText2,
									 @NonNull SamplingRate samplingRate, @NonNull Integer maxDuration, @NonNull BitsPerSample bitsPerSample,
									 @NonNull AudioType audioType, Boolean muteAudio, Integer correlationID) throws SdlException {
		Vector<TTSChunk> chunks = TTSChunkFactory.createSimpleTTSChunks(initialPrompt);
		PerformAudioPassThru msg = new PerformAudioPassThru(samplingRate, maxDuration, bitsPerSample, audioType);
		msg.setCorrelationID(correlationID);
		msg.setInitialPrompt(chunks);
		msg.setAudioPassThruDisplayText1(audioPassThruDisplayText1);
		msg.setAudioPassThruDisplayText2(audioPassThruDisplayText2);
		msg.setMuteAudio(muteAudio);

		sendRPCRequest(msg);
	}

	/**
	 * Ends audio pass thru session. Responses are captured through callback on IProxyListener.
	 * 
	 * @param correlationID ID to be attached to the RPCRequest that correlates the RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void endaudiopassthru(Integer correlationID) throws SdlException
	{
		EndAudioPassThru msg = new EndAudioPassThru();
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	/**
	 *     Subscribes for specific published data items.  The data will be only sent if it has changed.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param gps -Subscribes to GPS data.
	 * @param speed -Subscribes to vehicle speed data in kilometers per hour.
	 * @param rpm -Subscribes to number of revolutions per minute of the engine.
	 * @param fuelLevel -Subscribes to fuel level in the tank (percentage).
	 * @param fuelLevel_State -Subscribes to fuel level state.
	 * @param instantFuelConsumption -Subscribes to instantaneous fuel consumption in microlitres.
	 * @param externalTemperature -Subscribes to the external temperature in degrees celsius.
	 * @param prndl -Subscribes to PRNDL data that houses the selected gear.
	 * @param tirePressure -Subscribes to the TireStatus data containing status and pressure of tires. 
	 * @param odometer -Subscribes to Odometer data in km.
	 * @param beltStatus -Subscribes to status of the seat belts.
	 * @param bodyInformation -Subscribes to body information including power modes.
	 * @param deviceStatus -Subscribes to device status including signal and battery strength.
	 * @param driverBraking -Subscribes to the status of the brake pedal.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	*/
	@SuppressWarnings("unused")
	@Deprecated
	public void subscribevehicledata(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State,
									 boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure,
									 boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
									 boolean driverBraking, Integer correlationID) throws SdlException
	{
		SubscribeVehicleData msg = new SubscribeVehicleData();
		msg.setGps(gps);
		msg.setSpeed(speed);
		msg.setRpm(rpm);
		msg.setFuelLevel(fuelLevel);
		msg.setFuelLevelState(fuelLevel_State);
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
		
		sendRPCRequest(msg);
	}


	/**
	 *     Subscribes for specific published data items.  The data will be only sent if it has changed.
	 *     Responses are captured through callback on IProxyListener.
	 *
	 * @param gps -Subscribes to GPS data.
	 * @param speed -Subscribes to vehicle speed data in kilometers per hour.
	 * @param rpm -Subscribes to number of revolutions per minute of the engine.
	 * @param fuelLevel -Subscribes to fuel level in the tank (percentage).
	 * @param fuelLevel_State -Subscribes to fuel level state.
	 * @param instantFuelConsumption -Subscribes to instantaneous fuel consumption in microlitres.
	 * @param externalTemperature -Subscribes to the external temperature in degrees celsius.
	 * @param prndl -Subscribes to PRNDL data that houses the selected gear.
	 * @param tirePressure -Subscribes to the TireStatus data containing status and pressure of tires.
	 * @param engineOilLife -Subscribes to Engine Oil Life data.
	 * @param odometer -Subscribes to Odometer data in km.
	 * @param beltStatus -Subscribes to status of the seat belts.
	 * @param bodyInformation -Subscribes to body information including power modes.
	 * @param deviceStatus -Subscribes to device status including signal and battery strength.
	 * @param driverBraking -Subscribes to the status of the brake pedal.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void subscribevehicledata(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State,
									 boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure,
									 boolean engineOilLife, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
									 boolean driverBraking, Integer correlationID) throws SdlException
	{
		SubscribeVehicleData msg = new SubscribeVehicleData();
		msg.setGps(gps);
		msg.setSpeed(speed);
		msg.setRpm(rpm);
		msg.setFuelLevel(fuelLevel);
		msg.setFuelLevelState(fuelLevel_State);
		msg.setInstantFuelConsumption(instantFuelConsumption);
		msg.setExternalTemperature(externalTemperature);
		msg.setPrndl(prndl);
		msg.setTirePressure(tirePressure);
		msg.setEngineOilLife(engineOilLife);
		msg.setOdometer(odometer);
		msg.setBeltStatus(beltStatus);
		msg.setBodyInformation(bodyInformation);
		msg.setDeviceStatus(deviceStatus);
		msg.setDriverBraking(driverBraking);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
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
	 * @throws SdlException if an unrecoverable error is encountered
	*/

	@SuppressWarnings("unused")
	@Deprecated
	public void unsubscribevehicledata(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State,
									   boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure,
									   boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
									   boolean driverBraking, Integer correlationID) throws SdlException
	{
		UnsubscribeVehicleData msg = new UnsubscribeVehicleData();
		msg.setGps(gps);
		msg.setSpeed(speed);
		msg.setRpm(rpm);
		msg.setFuelLevel(fuelLevel);
		msg.setFuelLevelState(fuelLevel_State);
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

		sendRPCRequest(msg);
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
	 * @param engineOilLife -Unsubscribes to Engine Oil Life data.
	 * @param odometer -Unsubscribes to Odometer data in km.
	 * @param beltStatus -Unsubscribes to status of the seat belts.
	 * @param bodyInformation -Unsubscribes to body information including power modes.
	 * @param deviceStatus -Unsubscribes to device status including signal and battery strength.
	 * @param driverBraking -Unsubscribes to the status of the brake pedal.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */

	@SuppressWarnings("unused")
	public void unsubscribevehicledata(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State,
									   boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure,
									   boolean engineOilLife, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
									   boolean driverBraking, Integer correlationID) throws SdlException
	{
		UnsubscribeVehicleData msg = new UnsubscribeVehicleData();
		msg.setGps(gps);
		msg.setSpeed(speed);
		msg.setRpm(rpm);
		msg.setFuelLevel(fuelLevel);
		msg.setFuelLevelState(fuelLevel_State);
		msg.setInstantFuelConsumption(instantFuelConsumption);
		msg.setExternalTemperature(externalTemperature);
		msg.setPrndl(prndl);
		msg.setTirePressure(tirePressure);
		msg.setEngineOilLife(engineOilLife);
		msg.setOdometer(odometer);
		msg.setBeltStatus(beltStatus);
		msg.setBodyInformation(bodyInformation);
		msg.setDeviceStatus(deviceStatus);
		msg.setDriverBraking(driverBraking);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}


	/**
	 *     Performs a Non periodic vehicle data read request.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param gps -Performs an ad-hoc request for GPS data.
	 * @param speed -Performs an ad-hoc request for vehicle speed data in kilometers per hour.
	 * @param rpm -Performs an ad-hoc request for number of revolutions per minute of the engine.
	 * @param fuelLevel -Performs an ad-hoc request for fuel level in the tank (percentage).
	 * @param fuelLevel_State -Performs an ad-hoc request for fuel level state.
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
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	*/
	@SuppressWarnings("unused")
	@Deprecated
	public void getvehicledata(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State,
							   boolean instantFuelConsumption, boolean externalTemperature, boolean vin, boolean prndl, boolean tirePressure,
							   boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
							   boolean driverBraking, Integer correlationID) throws SdlException
	{
		GetVehicleData msg = new GetVehicleData();
		msg.setGps(gps);
		msg.setSpeed(speed);
		msg.setRpm(rpm);
		msg.setFuelLevel(fuelLevel);
		msg.setFuelLevelState(fuelLevel_State);
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

		sendRPCRequest(msg);
	}


	/**
	 *     Performs a Non periodic vehicle data read request.
	 *     Responses are captured through callback on IProxyListener.
	 *
	 * @param gps -Performs an ad-hoc request for GPS data.
	 * @param speed -Performs an ad-hoc request for vehicle speed data in kilometers per hour.
	 * @param rpm -Performs an ad-hoc request for number of revolutions per minute of the engine.
	 * @param fuelLevel -Performs an ad-hoc request for fuel level in the tank (percentage).
	 * @param fuelLevel_State -Performs an ad-hoc request for fuel level state.
	 * @param instantFuelConsumption -Performs an ad-hoc request for instantaneous fuel consumption in microlitres.
	 * @param externalTemperature -Performs an ad-hoc request for the external temperature in degrees celsius.
	 * @param vin -Performs an ad-hoc request for the Vehicle identification number
	 * @param prndl -Performs an ad-hoc request for PRNDL data that houses the selected gear.
	 * @param tirePressure -Performs an ad-hoc request for the TireStatus data containing status and pressure of tires.
	 * @param engineOilLife -Performs an ad-hoc request for Engine Oil Life data.
	 * @param odometer -Performs an ad-hoc request for Odometer data in km.
	 * @param beltStatus -Performs an ad-hoc request for status of the seat belts.
	 * @param bodyInformation -Performs an ad-hoc request for  body information including power modes.
	 * @param deviceStatus -Performs an ad-hoc request for device status including signal and battery strength.
	 * @param driverBraking -Performs an ad-hoc request for the status of the brake pedal.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void getvehicledata(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State,
							   boolean instantFuelConsumption, boolean externalTemperature, boolean vin, boolean prndl, boolean tirePressure,
							   boolean engineOilLife, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus,
							   boolean driverBraking, Integer correlationID) throws SdlException
	{
		GetVehicleData msg = new GetVehicleData();
		msg.setGps(gps);
		msg.setSpeed(speed);
		msg.setRpm(rpm);
		msg.setFuelLevel(fuelLevel);
		msg.setFuelLevelState(fuelLevel_State);
		msg.setInstantFuelConsumption(instantFuelConsumption);
		msg.setExternalTemperature(externalTemperature);
		msg.setVin(vin);
		msg.setPrndl(prndl);
		msg.setTirePressure(tirePressure);
		msg.setEngineOilLife(engineOilLife);
		msg.setOdometer(odometer);
		msg.setBeltStatus(beltStatus);
		msg.setBodyInformation(bodyInformation);
		msg.setDeviceStatus(deviceStatus);
		msg.setDriverBraking(driverBraking);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}


	/**
	 *     Creates a full screen overlay containing a large block of formatted text that can be scrolled with up to 8 SoftButtons defined.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param scrollableMessageBody -Body of text that can include newlines and tabs.
	 * @param timeout -App defined timeout.  Indicates how long of a timeout from the last action.
	 * @param softButtons -App defined SoftButtons.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	*/		
	@SuppressWarnings("unused")
	public void scrollablemessage(@NonNull String scrollableMessageBody, Integer timeout, Vector<SoftButton> softButtons, Integer correlationID) throws SdlException
	{
		ScrollableMessage msg = new ScrollableMessage(scrollableMessageBody);
		msg.setCorrelationID(correlationID);
		msg.setTimeout(timeout);
		msg.setSoftButtons(softButtons);

		sendRPCRequest(msg);
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
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	*/	
	@SuppressWarnings("unused")
	public void slider(@NonNull Integer numTicks, @NonNull Integer position, @NonNull String sliderHeader, Vector<String> sliderFooter, Integer timeout, Integer correlationID) throws SdlException
	{
		Slider msg = new Slider(numTicks, position, sliderHeader);
		msg.setCorrelationID(correlationID);
		msg.setSliderFooter(sliderFooter);
		msg.setTimeout(timeout);

		sendRPCRequest(msg);		
	}

	/**
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param language requested SDL voice engine (VR+TTS) language registration
	 * @param hmiDisplayLanguage request display language registration.
	 * @param correlationID ID to be attached to the RPCRequest that correlates the RPCResponse
	 * @throws SdlException if an unrecoverable error is encountered
	*/	
	@SuppressWarnings("unused")
	public void changeregistration(@NonNull Language language, @NonNull Language hmiDisplayLanguage, Integer correlationID) throws SdlException
	{
		ChangeRegistration msg = new ChangeRegistration(language, hmiDisplayLanguage);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
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
	 * @throws SdlException if an unrecoverable error is encountered
	 * @see  #putFileStream(InputStream, String, Long, Long)
	*/
	@SuppressWarnings("unused")
	@Deprecated
	public void putFileStream(InputStream is, @NonNull String sdlFileName, Integer iOffset, Integer iLength) throws SdlException
	{
		PutFile msg = new PutFile(sdlFileName, FileType.BINARY);
		msg.setCorrelationID(10000);
		msg.setSystemFile(true);
		msg.setOffset(iOffset);
		msg.setLength(iLength);

		startRPCStream(is, msg);
	}
	
	/**
	 * Used to push a binary stream of file data onto the module from a mobile
	 * device. Responses are captured through callback on IProxyListener.
	 * 
	 * @param inputStream The input stream of byte data that will be read from.
	 * @param fileName The SDL file reference name used by the RPC.
	 * @param offset The data offset in bytes. A value of zero is used to
	 * indicate data starting from the beginning of the file and a value greater
	 * than zero is used for resuming partial data chunks.
	 * @param length The total length of the file being sent.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void putFileStream(InputStream inputStream, @NonNull String fileName, Long offset, Long length) throws SdlException {
		PutFile msg = new PutFile(fileName, FileType.BINARY);
		msg.setCorrelationID(10000);
		msg.setSystemFile(true);
		msg.setOffset(offset);
		msg.setLength(length);

		startRPCStream(inputStream, msg);
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
	 * @throws SdlException if an unrecoverable error is encountered
	 * @see #putFileStream(String, Long, Long)
	 */
	@SuppressWarnings("unused")
	@Deprecated
	public OutputStream putFileStream(@NonNull String sdlFileName, Integer iOffset, Integer iLength) throws SdlException
	{
		PutFile msg = new PutFile(sdlFileName, FileType.BINARY);
		msg.setCorrelationID(10000);
		msg.setSystemFile(true);
		msg.setOffset(iOffset);
		msg.setLength(iLength);

		return startRPCStream(msg);
	}	
	
	/**
	 * Used to push a binary stream of file data onto the module from a mobile
	 * device. Responses are captured through callback on IProxyListener.
	 * 
	 * @param fileName The SDL file reference name used by the RPC.
	 * @param offset The data offset in bytes. A value of zero is used to
	 * indicate data starting from the beginning of the file and a value greater
	 * than zero is used for resuming partial data chunks.
	 * @param length The total length of the file being sent.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public OutputStream putFileStream(@NonNull String fileName, Long offset, Long length) throws SdlException {
		PutFile msg = new PutFile(fileName, FileType.BINARY);
		msg.setCorrelationID(10000);
		msg.setSystemFile(true);
		msg.setOffset(offset);
		msg.setLength(length);

		return startRPCStream(msg);
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
	 * @throws SdlException if an unrecoverable error is encountered
	 * @see #putFileStream(InputStream, String, Long, Long, FileType, Boolean, Boolean, OnPutFileUpdateListener)
	 */
	@SuppressWarnings("unused")
	@Deprecated
	public void putFileStream(InputStream is, @NonNull String sdlFileName, Integer iOffset, Integer iLength, @NonNull FileType fileType, Boolean bPersistentFile, Boolean bSystemFile) throws SdlException
	{
		PutFile msg = new PutFile(sdlFileName, fileType);
		msg.setCorrelationID(10000);
		msg.setPersistentFile(bPersistentFile);
		msg.setSystemFile(bSystemFile);
		msg.setOffset(iOffset);
		msg.setLength(iLength);

		startRPCStream(is, msg);
	}
	
	/**
	 * Used to push a binary stream of file data onto the module from a mobile
	 * device. Responses are captured through callback on IProxyListener.
	 * 
	 * @param inputStream The input stream of byte data that will be read from.
	 * @param fileName The SDL file reference name used by the RPC.
	 * @param offset The data offset in bytes. A value of zero is used to
	 * indicate data starting from the beginning of the file and a value greater
	 * than zero is used for resuming partial data chunks.
	 * @param length The total length of the file being sent.
	 * @param fileType The selected file type. See the {@link FileType} enum for
	 * details.
	 * @param isPersistentFile Indicates if the file is meant to persist between
	 * sessions / ignition cycles.
	 * @param isSystemFile Indicates if the file is meant to be passed through
	 * core to elsewhere in the system.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
//	@SuppressWarnings("unused")
//	public void putFileStream(InputStream inputStream, @NonNull String fileName, Long offset, Long length, FileType fileType, Boolean isPersistentFile, Boolean isSystemFile, OnPutFileUpdateListener cb) throws SdlException {
//		PutFile msg = new PutFile(fileName, FileType.BINARY);
//		msg.setCorrelationID(10000);
//		msg.setSystemFile(true);
//		msg.setOffset(offset);
//		msg.setLength(length);
//		//msg.setOnPutFileUpdateListener(cb);
//		startRPCStream(inputStream, msg);
//	}
//
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
	 * @throws SdlException if an unrecoverable error is encountered
	 * @see #putFileStream(String, Long, Long, FileType, Boolean, Boolean, OnPutFileUpdateListener)
	 */
	@SuppressWarnings("unused")
	@Deprecated
	public OutputStream putFileStream(@NonNull String sdlFileName, Integer iOffset, Integer iLength, @NonNull FileType fileType, Boolean bPersistentFile, Boolean bSystemFile) throws SdlException
	{
		PutFile msg = new PutFile(sdlFileName, fileType);
		msg.setCorrelationID(10000);
		msg.setPersistentFile(bPersistentFile);
		msg.setSystemFile(bSystemFile);
		msg.setOffset(iOffset);
		msg.setLength(iLength);

		return startRPCStream(msg);
	}
	
	/**
	 * Used to push a binary stream of file data onto the module from a mobile
	 * device. Responses are captured through callback on IProxyListener.
	 * 
	 * @param fileName The SDL file reference name used by the RPC.
	 * @param offset The data offset in bytes. A value of zero is used to
	 * indicate data starting from the beginning of the file and a value greater
	 * than zero is used for resuming partial data chunks.
	 * @param length The total length of the file being sent.
	 * @param fileType The selected file type. See the {@link FileType} enum for
	 * details.
	 * @param isPersistentFile Indicates if the file is meant to persist between
	 * sessions / ignition cycles.
	 * @param isSystemFile Indicates if the file is meant to be passed through
	 * core to elsewhere in the system.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
//	@SuppressWarnings("unused")
//	public OutputStream putFileStream(@NonNull String fileName, Long offset, Long length, FileType fileType, Boolean isPersistentFile, Boolean isSystemFile, OnPutFileUpdateListener cb) throws SdlException {
//		PutFile msg = new PutFile(fileName, FileType.BINARY);
//		msg.setCorrelationID(10000);
//		msg.setSystemFile(true);
//		msg.setOffset(offset);
//		msg.setLength(length);
//		//msg.setOnPutFileUpdateListener(cb);
//
//		return startRPCStream(msg);
//	}
	
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
	 * @param iCorrelationID - A unique ID that correlates each RPCRequest and RPCResponse.
	 * @return RPCStreamController - If the putFileStream was not started successfully null is returned, otherwise a valid object reference is returned 
	 * @throws SdlException if an unrecoverable error is encountered
//	 * @see #putFileStream(String, String, Long, FileType, Boolean, Boolean, Boolean, Integer, OnPutFileUpdateListener)
	 */	
	@SuppressWarnings("unused")
	@Deprecated
	public RPCStreamController putFileStream(String sPath, @NonNull String sdlFileName, Integer iOffset, @NonNull FileType fileType, Boolean bPersistentFile, Boolean bSystemFile, Integer iCorrelationID) throws SdlException
	{
		PutFile msg = new PutFile(sdlFileName, fileType);
		msg.setCorrelationID(iCorrelationID);
		msg.setPersistentFile(bPersistentFile);
		msg.setSystemFile(bSystemFile);
		msg.setOffset(iOffset);
		msg.setLength(0);

		return startPutFileStream(sPath, msg);
	}
	
	/**
	 * Used to push a binary stream of file data onto the module from a mobile
	 * device. Responses are captured through callback on IProxyListener.
	 * 
	 * @param path The physical file path on the mobile device.
	 * @param fileName The SDL file reference name used by the RPC.
	 * @param offset The data offset in bytes. A value of zero is used to
	 * indicate data starting from the beginning of the file and a value greater
	 * than zero is used for resuming partial data chunks.
	 * @param fileType The selected file type. See the {@link FileType} enum for
	 * details.
	 * @param isPersistentFile Indicates if the file is meant to persist between
	 * sessions / ignition cycles.
	 * @param isSystemFile Indicates if the file is meant to be passed through
	 * core to elsewhere in the system.
	 * @param correlationId A unique id that correlates each RPCRequest and 
	 * RPCResponse.
	 * @return RPCStreamController If the putFileStream was not started 
	 * successfully null is returned, otherwise a valid object reference is 
	 * returned .
	 * @throws SdlException if an unrecoverable error is encountered
	 */
//	@SuppressWarnings("unused")
//	public RPCStreamController putFileStream(String path, @NonNull String fileName, Long offset, @NonNull FileType fileType, Boolean isPersistentFile, Boolean isSystemFile, Boolean isPayloadProtected, Integer correlationId, OnPutFileUpdateListener cb ) throws SdlException {
//		PutFile msg = new PutFile(fileName, fileType);
//		msg.setCorrelationID(correlationId);
//		msg.setPersistentFile(isPersistentFile);
//		msg.setSystemFile(isSystemFile);
//		msg.setOffset(offset);
//		msg.setLength(0L);
//		msg.setPayloadProtected(isPayloadProtected);
//		//msg.setOnPutFileUpdateListener(cb);
//
//		return startPutFileStream(path,msg);
//	}

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
	 * @param iCorrelationID - A unique ID that correlates each RPCRequest and RPCResponse.
	 * @return RPCStreamController - If the putFileStream was not started successfully null is returned, otherwise a valid object reference is returned 
	 * @throws SdlException if an unrecoverable error is encountered
	 * @see #putFileStream(InputStream, String, Long, Long, FileType, Boolean, Boolean, Boolean, Integer)
	 */	
	@SuppressWarnings("unused")
	@Deprecated
	public RPCStreamController putFileStream(InputStream is, @NonNull String sdlFileName, Integer iOffset, Integer iLength, @NonNull FileType fileType, Boolean bPersistentFile, Boolean bSystemFile, Integer iCorrelationID) throws SdlException
	{
		PutFile msg = new PutFile(sdlFileName, fileType);
		msg.setCorrelationID(iCorrelationID);
		msg.setPersistentFile(bPersistentFile);
		msg.setSystemFile(bSystemFile);
		msg.setOffset(Long.valueOf(iOffset));
		msg.setLength(Long.valueOf(iLength));

		return startPutFileStream(is, msg);
	}
	
	/**
	 * Used to push a binary stream of file data onto the module from a mobile
	 * device. Responses are captured through callback on IProxyListener.
	 * 
	 * @param inputStream The input stream of byte data that will be read from.
	 * @param fileName The SDL file reference name used by the RPC.
	 * @param offset The data offset in bytes. A value of zero is used to
	 * indicate data starting from the beginning of the file and a value greater
	 * than zero is used for resuming partial data chunks.
	 * @param length The total length of the file being sent.
	 * @param fileType The selected file type. See the {@link FileType} enum for
	 * details.
	 * @param isPersistentFile Indicates if the file is meant to persist between
	 * sessions / ignition cycles.
	 * @param isSystemFile Indicates if the file is meant to be passed through
	 * core to elsewhere in the system.
	 * @param correlationId A unique id that correlates each RPCRequest and 
	 * RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public RPCStreamController putFileStream(InputStream inputStream, @NonNull String fileName, Long offset, Long length, @NonNull FileType fileType, Boolean isPersistentFile, Boolean isSystemFile, Boolean isPayloadProtected, Integer correlationId) throws SdlException {
		PutFile msg = new PutFile(fileName, fileType);
		msg.setCorrelationID(correlationId);
		msg.setPersistentFile(isPersistentFile);
		msg.setSystemFile(isSystemFile);
		msg.setOffset(offset);
		msg.setLength(length);
		msg.setPayloadProtected(isPayloadProtected);

		return startPutFileStream(inputStream, msg);
	}

	/**
	 *
	 * Used to end an existing putFileStream that was previously initiated with any putFileStream method.
	 *
	 */
	@SuppressWarnings("unused")
	public void endPutFileStream()
	{
		endRPCStream();
	}
	
	
	/**
	 *     Used to push a binary data onto the SDL module from a mobile device, such as icons and album art.  Not supported on first generation SDL vehicles.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param sdlFileName -File reference name.
	 * @param fileType -Selected file type.
	 * @param persistentFile -Indicates if the file is meant to persist between sessions / ignition cycles.
	 * @param fileData byte array of data of the file that is to be sent
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	*/	
	@SuppressWarnings("unused")
	public void putfile(@NonNull String sdlFileName, @NonNull FileType fileType, Boolean persistentFile, byte[] fileData, Integer correlationID) throws SdlException
	{
		PutFile msg = new PutFile(sdlFileName, fileType);
		msg.setCorrelationID(correlationID);
		msg.setPersistentFile(persistentFile);
		msg.setBulkData(fileData);

		sendRPCRequest(msg);
	}
	
	/**
	 *     Used to delete a file resident on the SDL module in the app's local cache.  Not supported on first generation SDL vehicles.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param sdlFileName -File reference name.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	*/	
	@SuppressWarnings("unused")
	public void deletefile(@NonNull String sdlFileName, Integer correlationID) throws SdlException
	{
		DeleteFile msg = new DeleteFile(sdlFileName);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}
	
	/**
	 *     Requests the current list of resident filenames for the registered app.  Not supported on first generation SDL vehicles.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	*/	
	@SuppressWarnings("unused")
	public void listfiles(Integer correlationID) throws SdlException
	{
		ListFiles msg = new ListFiles();
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}

	/**
	 *     Used to set existing local file on SDL as the app's icon.  Not supported on first generation SDL vehicles.
	 *     Responses are captured through callback on IProxyListener.
	 * 
	 * @param sdlFileName -File reference name.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	*/	
	@SuppressWarnings("unused")
	public void setappicon(@NonNull String sdlFileName, Integer correlationID) throws SdlException
	{
		SetAppIcon msg = new SetAppIcon(sdlFileName);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}

	/**
	 *     Set an alternate display layout. If not sent, default screen for given platform will be shown.
	 *     Responses are captured through callback on IProxyListener.
	 *
	 * @param displayLayout -Predefined or dynamically created screen layout.
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void setdisplaylayout(@NonNull String displayLayout, Integer correlationID) throws SdlException
	{
		SetDisplayLayout msg = new SetDisplayLayout(displayLayout);
		msg.setCorrelationID(correlationID);

		sendRPCRequest(msg);
	}

	/**
	 *     Set an alternate display layout. If not sent, default screen for given platform will be shown.
	 *     Responses are captured through callback on IProxyListener.
	 *
	 * @param displayLayout -Predefined or dynamically created screen layout.
	 * @param dayColorScheme a TemplateColorScheme object representing the colors that will be used for day color scheme
     * @param nightColorScheme a TemplateColorScheme object representing the colors that will be used for night color scheme
	 * @param correlationID -A unique ID that correlates each RPCRequest and RPCResponse.
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	@SuppressWarnings("unused")
	public void setdisplaylayout(String displayLayout, TemplateColorScheme dayColorScheme, TemplateColorScheme nightColorScheme, Integer correlationID) throws SdlException
	{
		SetDisplayLayout msg = new SetDisplayLayout(displayLayout);
		msg.setCorrelationID(correlationID);
		msg.setDayColorScheme(dayColorScheme);
		msg.setNightColorScheme(nightColorScheme);
		sendRPCRequest(msg);
	}

    /**
     * Gets the SystemCapabilityManager. <br>
     * @return a SystemCapabilityManager object
     */
	public SystemCapabilityManager getSystemCapabilityManager() {
		return _systemCapabilityManager;
	}

	@SuppressWarnings("unused")
	public boolean isCapabilitySupported(SystemCapabilityType systemCapabilityType) {
		return _systemCapabilityManager != null && _systemCapabilityManager.isCapabilitySupported(systemCapabilityType);
	}

	@SuppressWarnings("unused")
	public void getCapability(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener scListener){
		if(_systemCapabilityManager != null){
			_systemCapabilityManager.getCapability(systemCapabilityType, scListener);
		}
	}

	@SuppressWarnings("unused")
	public Object getCapability(SystemCapabilityType systemCapabilityType){
		if(_systemCapabilityManager != null ){
			return _systemCapabilityManager.getCapability(systemCapabilityType);
		}else{
			return null;
		}
	}

	/**
	 * Add a listener to be called whenever a new capability is retrieved
	 * @param systemCapabilityType Type of capability desired
	 * @param listener callback to execute upon retrieving capability
	 */
	public void addOnSystemCapabilityListener(final SystemCapabilityType systemCapabilityType, final OnSystemCapabilityListener listener) {
		if(_systemCapabilityManager != null){
			_systemCapabilityManager.addOnSystemCapabilityListener(systemCapabilityType, listener);
		}
	}

	/**
	 * Remove an OnSystemCapabilityListener that was previously added
	 * @param systemCapabilityType Type of capability
	 * @param listener the listener that should be removed
	 */
	public boolean removeOnSystemCapabilityListener(final SystemCapabilityType systemCapabilityType, final OnSystemCapabilityListener listener){
		if(_systemCapabilityManager != null){
			return _systemCapabilityManager.removeOnSystemCapabilityListener(systemCapabilityType, listener);
		}
		return false;
	}

	/* ******************* END Public Helper Methods *************************/
	
	/**
	 * Gets type of transport currently used by this SdlProxy.
	 * 
	 * @return One of TransportType enumeration values.
	 * 
	 * @see TransportType
	 */
	@SuppressWarnings("unused")
	public TransportType getCurrentTransportType() throws IllegalStateException {
		if (sdlSession  == null) {
			throw new IllegalStateException("Incorrect state of SdlProxyBase: Calling for getCurrentTransportType() while connection is not initialized");
		}
			
		return sdlSession.getCurrentTransportType();
	}

	@Deprecated
	public void setSdlSecurityClassList(List<Class<? extends SdlSecurityBase>> list) {
		_secList = list;
	}

	/**
	 * Sets the security libraries and a callback to notify caller when there is update to encryption service
	 * @param secList The list of security class(es)
	 * @param listener The callback object
	 */
	public void setSdlSecurity(@NonNull List<Class<? extends SdlSecurityBase>> secList, ServiceEncryptionListener listener) {
		_secList = secList;
		serviceEncryptionListener = listener;
	}

	private void setSdlSecurity(SdlSecurityBase sec) {
		if (sdlSession != null)
		{
			sdlSession.setSdlSecurity(sec);
		}
	}

	/**
	 * Sets the minimum protocol version that will be permitted to connect.
	 * If the protocol version of the head unit connected is below this version,
	 * the app will disconnect with an EndService protocol message and will not register.
	 * @param minimumProtocolVersion
	 */
	public void setMinimumProtocolVersion(Version minimumProtocolVersion){
		this.minimumProtocolVersion = minimumProtocolVersion;
	}

	/**
	 * The minimum RPC version that will be permitted to connect.
	 * If the RPC version of the head unit connected is below this version, an UnregisterAppInterface will be sent.
	 * @param minimumRPCVersion
	 */
	public void setMinimumRPCVersion(Version minimumRPCVersion){
		this.minimumRPCVersion = minimumRPCVersion;
	}

	@SuppressWarnings("unused")
	public boolean isServiceTypeProtected(SessionType sType) {
		return sdlSession != null && sdlSession.isServiceProtected(sType);

	}


	public void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener){
		if(serviceType != null && sdlSession != null && sdlServiceListener != null){
			sdlSession.addServiceListener(serviceType, sdlServiceListener);
		}
	}

	public void removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener){
		if(serviceType != null && sdlSession != null && sdlServiceListener != null){
			sdlSession.removeServiceListener(serviceType, sdlServiceListener);
		}
	}

	@SuppressWarnings("unused")
	public VideoStreamingParameters getAcceptedVideoParams(){
		return sdlSession.getAcceptedVideoParams();
	}

	public IProxyListenerBase getProxyListener()
	{
		return _proxyListener;
	}
	
	@SuppressWarnings("unused")
	public String getAppName()
	{
		return _applicationName;
	}

	@SuppressWarnings("unused")
	public String getNgnAppName()
	{
		return _ngnMediaScreenAppName;
	}

	@SuppressWarnings("unused")
	public String getAppID()
	{
		return _appID;
	}
	@SuppressWarnings("unused")
	public DeviceInfo getDeviceInfo()
	{
		return deviceInfo;
	}
	@SuppressWarnings("unused")
	public long getInstanceDT()
	{
		return instanceDateTime;
	}
	@SuppressWarnings("unused")
	public void setConnectionDetails(String sDetails)
	{
		sConnectionDetails = sDetails;
	}
	@SuppressWarnings("unused")
	public String getConnectionDetails()
	{
		return sConnectionDetails;
	}
	//for testing only
	@SuppressWarnings("unused")
	public void setPoliciesURL(String sText)
	{
		sPoliciesURL = sText;
	}
	//for testing only
	public String getPoliciesURL()
	{
		return sPoliciesURL;
	}

	/**
	 * Tells developer whether or not their app icon has been resumed on core.
	 * @return boolean - true if icon was resumed, false if not
	 * @throws SdlException if proxy is disposed or app is not registered
	 */
	public boolean getIconResumed() throws SdlException {
		// Test if proxy has been disposed
		if (_proxyDisposed) {
			throw new SdlException("This object has been disposed, it is no long capable of executing methods.", SdlExceptionCause.SDL_PROXY_DISPOSED);
		}

		// Test SDL availability
		if (!_appInterfaceRegisterd) {
			throw new SdlException("SDL is not connected. Unable to determine if app icon was resumed.", SdlExceptionCause.SDL_UNAVAILABLE);
		}
		return _iconResumed;
	}

	/**
	 * Method to retrieve the RegisterAppInterface Response message that was sent back from the
	 * module. It contains various attributes about the connected module and can be used to adapt
	 * to different module types and their supported features.
	 *
	 * @return RegisterAppInterfaceResponse received from the module or null if the app has not yet
	 * registered with the module.
	 */
	public RegisterAppInterfaceResponse getRegisterAppInterfaceResponse(){
		return this.raiResponse;
	}

	/**
	 * Get the current OnHMIStatus
	 * @return OnHMIStatus object represents the current OnHMIStatus
	 */
	public OnHMIStatus getCurrentHMIStatus(){
		return lastHmiStatus;
	}

	/**
	 * Retrieves the auth token, if any, that was attached to the StartServiceACK for the RPC
	 * service from the module. For example, this should be used to login to a user account.
	 * @return the string representation of the auth token
	 */
	public String getAuthToken(){
		return this.authToken;
	}

	/**
	 * VideoStreamingManager houses all the elements needed to create a scoped, streaming manager for video projection. It is only a private, instance
	 * dependant class at the moment until it can become public. Once the class is public and API defined, it will be moved into the SdlSession class
	 */
	@TargetApi(19)
	private class VideoStreamingManager implements ISdlServiceListener{
		Context context;
		ISdl internalInterface;
		volatile VirtualDisplayEncoder encoder;
		private Class<? extends SdlRemoteDisplay> remoteDisplayClass = null;
		SdlRemoteDisplay remoteDisplay;
		IVideoStreamListener streamListener;
		float[] touchScalar = {1.0f,1.0f}; //x, y
		//private HapticInterfaceManager hapticManager;
		SdlMotionEvent sdlMotionEvent = null;
		VideoStreamingParameters videoStreamingParameters;

		public VideoStreamingManager(Context context,ISdl iSdl){
			this.context = context;
			this.internalInterface = iSdl;
			encoder = new VirtualDisplayEncoder();
			internalInterface.addServiceListener(SessionType.NAV,this);
			//Take care of the touch events
			internalInterface.addOnRPCNotificationListener(FunctionID.ON_TOUCH_EVENT, new OnRPCNotificationListener() {
				@Override
				public void onNotified(RPCNotification notification) {
					if (notification != null && remoteDisplay != null) {
						List<MotionEvent> events = convertTouchEvent((OnTouchEvent) notification);
						if (events != null && !events.isEmpty()) {
							for (MotionEvent ev : events) {
								remoteDisplay.handleMotionEvent(ev);
							}
						}
					}
				}
			});
		}

		/**
		 * Starts the video service, caches the supplied params and prepares for the stream to start.
		 * @param remoteDisplayClass the extension of SdlRemoteDisplay that will be streamed
		 * @param parameters desired video streaming params
		 * @param encrypted if the service is to be encrypted or not
		 */
		public void startVideoStreaming(Class<? extends SdlRemoteDisplay> remoteDisplayClass, VideoStreamingParameters parameters, boolean encrypted){
			this.remoteDisplayClass = remoteDisplayClass;
			this.videoStreamingParameters = parameters;
			//Make sure the service is started, allows multi transports to connect and register without timing out
			internalInterface.startVideoService(parameters, encrypted);
			//After this, look to the
		}

		/**
		 * The video service should already be started at this point. Once called, it will start
		 * the encoders and fire up the remote display supplied by the user
		 * @param parameters
		 * @param encrypted
		 */
		private void startStream(VideoStreamingParameters parameters, boolean encrypted){
			//Start the service first
			//streamListener = startVideoStream(encrypted,parameters);d
			//streamListener = sdlSession.startVideoStream();
			if(streamListener == null){
				DebugTool.logError(TAG, "Error starting video service");
				return;
			}
			VideoStreamingCapability capability = (VideoStreamingCapability)_systemCapabilityManager.getCapability(SystemCapabilityType.VIDEO_STREAMING);
			if(capability != null && Boolean.TRUE.equals(capability.getIsHapticSpatialDataSupported())){
				//hapticManager = new HapticInterfaceManager(internalInterface);
			}

			try {
				encoder.init(context, streamListener, parameters);
				//We are all set so we can start streaming at at this point
				encoder.start();
				//Encoder should be up and running
				createRemoteDisplay(encoder.getVirtualDisplay());
			} catch (Exception e) {
				e.printStackTrace();
			}
			DebugTool.logInfo(TAG, parameters.toString());
		}

		public void stopStreaming(){
			if(remoteDisplay!=null){
				remoteDisplay.stop();
				remoteDisplay = null;
			}
			if(encoder!=null){
				encoder.shutDown();
			}
			if(internalInterface!=null){
				internalInterface.stopVideoService();
			}
		}

		public void dispose(){
			stopStreaming();
			internalInterface.removeServiceListener(SessionType.NAV,this);
		}

		private void createRemoteDisplay(final Display disp){
			try{
				if (disp == null){
					return;
				}

				// Dismiss the current presentation if the display has changed.
				if (remoteDisplay != null && remoteDisplay.getDisplay() != disp) {
					remoteDisplay.dismissPresentation();
				}

				FutureTask<Boolean> fTask =  new FutureTask<Boolean>( new SdlRemoteDisplay.Creator(context, disp, remoteDisplay, remoteDisplayClass, new SdlRemoteDisplay.Callback(){
					@Override
					public void onCreated(final SdlRemoteDisplay remoteDisplay) {
						//Remote display has been created.
						//Now is a good time to do parsing for spatial data
						SdlProxyBase.VideoStreamingManager.this.remoteDisplay = remoteDisplay;
//						if(hapticManager != null) {
//							remoteDisplay.getMainView().post(new Runnable() {
//								@Override
//								public void run() {
//									hapticManager.refreshHapticData(remoteDisplay.getMainView());
//								}
//							});
//						}
						//Get touch scalars
						ImageResolution resolution = null;
						if(protocolVersion!= null && protocolVersion.getMajor()>=5){ //At this point we should already have the capability
							VideoStreamingCapability capability = (VideoStreamingCapability)_systemCapabilityManager.getCapability(SystemCapabilityType.VIDEO_STREAMING);
							if (capability != null) {
								resolution = capability.getPreferredResolution();
							}
						}

						if(resolution == null){ //Either the protocol version is too low to access video streaming caps, or they were null
							DisplayCapabilities dispCap = (DisplayCapabilities) internalInterface.getCapability(SystemCapabilityType.DISPLAY);
							if (dispCap != null) {
								resolution = (dispCap.getScreenParams().getImageResolution());
							}
						}

						if(resolution != null){
							DisplayMetrics displayMetrics = new DisplayMetrics();
							disp.getMetrics(displayMetrics);
							touchScalar[0] = ((float)displayMetrics.widthPixels) / resolution.getResolutionWidth();
							touchScalar[1] = ((float)displayMetrics.heightPixels) / resolution.getResolutionHeight();
						}

					}

					@Override
					public void onInvalidated(final SdlRemoteDisplay remoteDisplay) {
						//Our view has been invalidated
						//A good time to refresh spatial data
//						if(hapticManager != null) {
//							remoteDisplay.getMainView().post(new Runnable() {
//								@Override
//								public void run() {
//									hapticManager.refreshHapticData(remoteDisplay.getMainView());
//								}
//							});
//						}
					}
				} ));
				Thread showPresentation = new Thread(fTask);

				showPresentation.start();
				} catch (Exception ex) {
				DebugTool.logError(TAG, "Unable to create Virtual Display.");
			}
		}

		@Override
		public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted) {
			if(SessionType.NAV.equals(type) && session != null ){
				DebugTool.logInfo(TAG, "Video service has been started. Starting video stream from proxy");
				if(session.getAcceptedVideoParams() != null){
					videoStreamingParameters = session.getAcceptedVideoParams();
				}
				startStream(videoStreamingParameters, isEncrypted);
			}
		}

		@Override
		public void onServiceEnded(SdlSession session, SessionType type) {
			if(SessionType.NAV.equals(type)){
				if(remoteDisplay!=null){
					stopStreaming();
				}
			}
		}

		@Override
		public void onServiceError(SdlSession session, SessionType type, String reason) {

		}

		private List<MotionEvent> convertTouchEvent(OnTouchEvent onTouchEvent) {
			List<MotionEvent> motionEventList = new ArrayList<MotionEvent>();

			List<TouchEvent> touchEventList = onTouchEvent.getEvent();
			if (touchEventList == null || touchEventList.size() == 0) return null;

			TouchType touchType = onTouchEvent.getType();
			if (touchType == null) {
				return null;
			}

			if (sdlMotionEvent == null) {
				if (touchType == TouchType.BEGIN) {
					sdlMotionEvent = new SdlMotionEvent();
				} else {
					return null;
				}
			}

			SdlMotionEvent.Pointer pointer;
			MotionEvent motionEvent;

			for (TouchEvent touchEvent : touchEventList) {
				if (touchEvent == null || touchEvent.getId() == null) {
					continue;
				}

				List<TouchCoord> touchCoordList = touchEvent.getTouchCoordinates();
				if (touchCoordList == null || touchCoordList.size() == 0) {
					continue;
				}

				TouchCoord touchCoord = touchCoordList.get(touchCoordList.size() - 1);
				if (touchCoord == null) {
					continue;
				}

				int motionEventAction = sdlMotionEvent.getMotionEventAction(touchType, touchEvent);
				long downTime = sdlMotionEvent.downTime;
				long eventTime = sdlMotionEvent.eventTime;
				pointer = sdlMotionEvent.getPointerById(touchEvent.getId());
				if (pointer != null) {
					pointer.setCoords(touchCoord.getX() * touchScalar[0], touchCoord.getY() * touchScalar[1]);
				}

				MotionEvent.PointerProperties[] pointerProperties = new MotionEvent.PointerProperties[sdlMotionEvent.pointers.size()];
				MotionEvent.PointerCoords[] pointerCoords = new MotionEvent.PointerCoords[sdlMotionEvent.pointers.size()];

				for (int i = 0; i < sdlMotionEvent.pointers.size(); i++) {
					pointerProperties[i] = new MotionEvent.PointerProperties();
					pointerProperties[i].id = sdlMotionEvent.getPointerByIndex(i).id;
					pointerProperties[i].toolType = MotionEvent.TOOL_TYPE_FINGER;

					pointerCoords[i] = new MotionEvent.PointerCoords();
					pointerCoords[i].x = sdlMotionEvent.getPointerByIndex(i).x;
					pointerCoords[i].y = sdlMotionEvent.getPointerByIndex(i).y;
					pointerCoords[i].orientation = 0;
					pointerCoords[i].pressure = 1.0f;
					pointerCoords[i].size = 1;
				}

				motionEvent = MotionEvent.obtain(downTime, eventTime, motionEventAction,
						sdlMotionEvent.pointers.size(), pointerProperties, pointerCoords, 0, 0, 1,
						1, 0, 0, InputDevice.SOURCE_TOUCHSCREEN, 0);
				motionEventList.add(motionEvent);

				if (motionEventAction == MotionEvent.ACTION_UP || motionEventAction == MotionEvent.ACTION_CANCEL) {
					//If the motion event should be finished we should clear our reference
					sdlMotionEvent.pointers.clear();
					sdlMotionEvent = null;
					break;
				} else if ((motionEventAction & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP) {
					sdlMotionEvent.removePointerById(touchEvent.getId());
				}
			}

			return motionEventList;
		}
	}

	/**
	 * Keeps track of the current motion event for VPM
	 */
	private static class SdlMotionEvent {
		class Pointer {
			int id;
			float x;
			float y;

			Pointer(int id) {
				this.id = id;
				this.x = 0.0f;
				this.y = 0.0f;
			}

			void setCoords(float x, float y) {
				this.x = x;
				this.y = y;
			}
		}

		private CopyOnWriteArrayList<Pointer> pointers = new CopyOnWriteArrayList<>();
		private long downTime;
		private long downTimeOnHMI;
		private long eventTime;

		SdlMotionEvent() {
			downTimeOnHMI = 0;
		}

		/**
		 * Handles the SDL Touch Event to keep track of pointer status and returns the appropriate
		 * Android MotionEvent according to this events status
		 *
		 * @param touchType  The SDL TouchType that was received from the module
		 * @param touchEvent The SDL TouchEvent that was received from the module
		 * @return the correct native Android MotionEvent action to dispatch
		 */
		synchronized int getMotionEventAction(TouchType touchType, TouchEvent touchEvent) {
			eventTime = 0;
			int motionEventAction = -1;
			switch (touchType) {
				case BEGIN:
					if (pointers.size() == 0) {
						//The motion event has just begun
						motionEventAction = MotionEvent.ACTION_DOWN;
						downTime = SystemClock.uptimeMillis();
						downTimeOnHMI = touchEvent.getTimestamps().get(touchEvent.getTimestamps().size() - 1);
						eventTime = downTime;
					} else {
						motionEventAction = MotionEvent.ACTION_POINTER_DOWN | pointers.size() << MotionEvent.ACTION_POINTER_INDEX_SHIFT;
						eventTime = downTime + touchEvent.getTimestamps().get(touchEvent.getTimestamps().size() - 1) - downTimeOnHMI;
					}
					pointers.add(new Pointer(touchEvent.getId()));
					break;
				case MOVE:
					motionEventAction = MotionEvent.ACTION_MOVE;
					eventTime = downTime + touchEvent.getTimestamps().get(touchEvent.getTimestamps().size() - 1) - downTimeOnHMI;
					break;
				case END:
					if(pointers.size() <= 1){
						//The motion event has just ended
						motionEventAction = MotionEvent.ACTION_UP;
					} else {
						int pointerIndex = pointers.indexOf(getPointerById(touchEvent.getId()));
						if (pointerIndex != -1) {
							motionEventAction = MotionEvent.ACTION_POINTER_UP | pointerIndex << MotionEvent.ACTION_POINTER_INDEX_SHIFT;
						} else {
							motionEventAction = MotionEvent.ACTION_UP;
						}
					}
					eventTime = downTime + touchEvent.getTimestamps().get(touchEvent.getTimestamps().size() - 1) - downTimeOnHMI;
					break;
				case CANCEL:
					//Assuming this cancels the entire event
					motionEventAction = MotionEvent.ACTION_CANCEL;
					eventTime = downTime + touchEvent.getTimestamps().get(touchEvent.getTimestamps().size() - 1) - downTimeOnHMI;
					break;
				default:
					break;
			}
			return motionEventAction;
		}

		Pointer getPointerById(int id) {
			if (pointers != null && !pointers.isEmpty()) {
				for (Pointer pointer : pointers) {
					if (pointer.id == id) {
						return pointer;
					}
				}
			}
			return null;
		}

		Pointer getPointerByIndex(int index) {
			return pointers.get(index);
		}

		void removePointerById(int id) {
			pointers.remove(getPointerById(id));
		}
	}

} // end-class
