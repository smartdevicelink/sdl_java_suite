package com.smartdevicelink.managers;

import android.support.annotation.NonNull;
import android.util.Log;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.lifecycle.LifecycleManager;
import com.smartdevicelink.managers.permission.PermissionManager;
import com.smartdevicelink.managers.screen.ScreenManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.SystemCapabilityManager;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.interfaces.*;
import com.smartdevicelink.proxy.rpc.*;
import com.smartdevicelink.proxy.rpc.enums.*;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnPutFileUpdateListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.streaming.audio.AudioStreamingCodec;
import com.smartdevicelink.streaming.audio.AudioStreamingParams;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.WebSocketServerConfig;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * <strong>SDLManager</strong> <br>
 *
 * This is the main point of contact between an application and SDL <br>
 *
 * It is broken down to these areas: <br>
 *
 * 1. SDLManagerBuilder <br>
 * 2. ISdl Interface along with its overridden methods - This can be passed into attached managers <br>
 * 3. Sending Requests <br>
 * 4. Helper methods
 */
public class SdlManager extends BaseSdlManager{

	private static final String TAG = "SdlManager";
	private LifecycleManager proxy;
	private String appId, appName, shortAppName;
	private boolean isMediaApp;
	private Language hmiLanguage;
	private SdlArtwork appIcon;
	private Vector<AppHMIType> hmiTypes;
	private BaseTransportConfig transport;
	//FIXME private Context context;
	private Vector<String> vrSynonyms;
	private Vector<TTSChunk> ttsChunks;
	private TemplateColorScheme dayColorScheme, nightColorScheme;
	private SdlManagerListener managerListener;
	private Map<FunctionID, OnRPCNotificationListener> onRPCNotificationListeners;
	private int state = -1;
	private List<Class<? extends SdlSecurityBase>> sdlSecList;
	//FIXME private LockScreenConfig lockScreenConfig;
	private final Object STATE_LOCK = new Object();


	// Managers
	private PermissionManager permissionManager;
	private FileManager fileManager;
	//private LockScreenManager lockScreenManager;
    private ScreenManager screenManager;
	//private VideoStreamManager videoStreamManager;
	//private AudioStreamManager audioStreamManager;


	// Initialize proxyBridge with anonymous lifecycleListener
	//FIXME changed from proxy bridge
	private final LifecycleManager.LifecycleListener lifecycleListener = new LifecycleManager.LifecycleListener() {
		boolean initStarted = false;
		@Override
		public void onProxyConnected(LifecycleManager lifeCycleManager) {
			Log.i(TAG,"Proxy is connected. Now initializing.");
			synchronized (this){
				if(!initStarted){
					initialize();
					initStarted = true;
				}
			}
		}

		@Override
		public void onProxyClosed(LifecycleManager lifeCycleManager, String info, Exception e, SdlDisconnectedReason reason) {
			Log.i(TAG,"Proxy is closed.");
			if(managerListener != null){
				managerListener.onDestroy(SdlManager.this);
			}

		}

		@Override
		public void onServiceEnded(LifecycleManager lifeCycleManager, OnServiceEnded serviceEnded) {

		}

		@Override
		public void onServiceNACKed(LifecycleManager lifeCycleManager, OnServiceNACKed serviceNACKed) {

		}

		@Override
		public void onError(LifecycleManager lifeCycleManager, String info, Exception e) {

		}
	};

	// Sub manager listener
	private final CompletionListener subManagerListener = new CompletionListener() {
		@Override
		public synchronized void onComplete(boolean success) {
			if(!success){
				Log.e(TAG, "Sub manager failed to initialize");
			}
			checkState();
		}
	};

	void checkState() {
		if (permissionManager != null && fileManager != null && screenManager != null ){//FIXME && (!lockScreenConfig.isEnabled() || lockScreenManager != null)) {
			if (permissionManager.getState() == BaseSubManager.READY && fileManager.getState() == BaseSubManager.READY && screenManager.getState() == BaseSubManager.READY){ //FIXME && (!lockScreenConfig.isEnabled() || lockScreenManager.getState() == BaseSubManager.READY)) {
				DebugTool.logInfo("Starting sdl manager, all sub managers are in ready state");
				transitionToState(BaseSubManager.READY);
				notifyDevListener(null);
				onReady();
			} else if (permissionManager.getState() == BaseSubManager.ERROR && fileManager.getState() == BaseSubManager.ERROR && screenManager.getState() == BaseSubManager.ERROR){ //FIXME && (!lockScreenConfig.isEnabled() || lockScreenManager.getState() == BaseSubManager.ERROR)) {
				String info = "ERROR starting sdl manager, all sub managers are in error state";
				Log.e(TAG, info);
				transitionToState(BaseSubManager.ERROR);
				notifyDevListener(info);
			} else if (permissionManager.getState() == BaseSubManager.SETTING_UP || fileManager.getState() == BaseSubManager.SETTING_UP || screenManager.getState() == BaseSubManager.SETTING_UP){//FIXME || (lockScreenConfig.isEnabled() && lockScreenManager != null && lockScreenManager.getState() == BaseSubManager.SETTING_UP)) {
				DebugTool.logInfo("SETTING UP sdl manager, some sub managers are still setting up");
				transitionToState(BaseSubManager.SETTING_UP);
				// No need to notify developer here!
			} else {
				Log.w(TAG, "LIMITED starting sdl manager, some sub managers are in error or limited state and the others finished setting up");
				transitionToState(BaseSubManager.LIMITED);
				notifyDevListener(null);
				onReady();
			}
		} else {
			// We should never be here, but somehow one of the sub-sub managers is null
			String info = "ERROR one of the sdl sub managers is null";
			Log.e(TAG, info);
			transitionToState(BaseSubManager.ERROR);
			notifyDevListener(info);
		}
	}

	private void notifyDevListener(String info) {
		if (managerListener != null) {
			if (getState() == BaseSubManager.ERROR){
				managerListener.onError(this,info, null);
			} else {
				managerListener.onStart(this);
			}
		}
	}

	private void onReady(){
		// Set the app icon
		 if (SdlManager.this.appIcon != null && SdlManager.this.appIcon.getName() != null) {
			if (fileManager != null && fileManager.getState() == BaseSubManager.READY && !fileManager.hasUploadedFile(SdlManager.this.appIcon)) {
				fileManager.uploadArtwork(SdlManager.this.appIcon, new CompletionListener() {
					@Override
					public void onComplete(boolean success) {
						if (success) {
							SetAppIcon msg = new SetAppIcon(SdlManager.this.appIcon.getName());
							_internalInterface.sendRPCRequest(msg);
						}
					}
				});
			} else {
				SetAppIcon msg = new SetAppIcon(SdlManager.this.appIcon.getName());
				_internalInterface.sendRPCRequest(msg);
			}
		}
	}

	protected void initialize(){
		// Instantiate sub managers
		this.permissionManager = new PermissionManager(_internalInterface);
		this.fileManager = new FileManager(_internalInterface);		//FIXME ,context);
		/* FIXME if (lockScreenConfig.isEnabled()) {
			this.lockScreenManager = new LockScreenManager(lockScreenConfig, context, _internalInterface);
		}*/
		this.screenManager = new ScreenManager(_internalInterface, this.fileManager);
		/* FIXME if(getAppTypes().contains(AppHMIType.NAVIGATION) || getAppTypes().contains(AppHMIType.PROJECTION)){
			this.videoStreamManager = new VideoStreamManager(_internalInterface);
		} else {
			this.videoStreamManager = null;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
				&& (getAppTypes().contains(AppHMIType.NAVIGATION) || getAppTypes().contains(AppHMIType.PROJECTION)) ) {
			this.audioStreamManager = new AudioStreamManager(_internalInterface, context);
		} else {
			this.audioStreamManager = null;
		}*/

		// Start sub managers
		this.permissionManager.start(subManagerListener);
		this.fileManager.start(subManagerListener);
		/*if (lockScreenConfig.isEnabled()){
			this.lockScreenManager.start(subManagerListener);
		}*/
		this.screenManager.start(subManagerListener);

	}

	/**
	 * Get the current state for the SdlManager
	 * @return int value that represents the current state
	 * @see BaseSubManager
	 */
	public int getState() {
		synchronized (STATE_LOCK) {
			return state;
		}
	}

	protected void transitionToState(int state) {
		synchronized (STATE_LOCK) {
			this.state = state;
		}
	}

	//FIXME @SuppressLint("NewApi")
	public void dispose() {
		if (this.permissionManager != null) {
			this.permissionManager.dispose();
		}

		if (this.fileManager != null) {
			this.fileManager.dispose();
		}

		/*FIXME if (this.lockScreenManager != null) {
			this.lockScreenManager.dispose();
		}*/

		if (this.screenManager != null) {
			this.screenManager.dispose();
		}

		/* FIXME if(this.videoStreamManager != null) {
			this.videoStreamManager.dispose();
		}

		// SuppressLint("NewApi") is used because audioStreamManager is only available on android >= jelly bean
		if (this.audioStreamManager != null) {
			this.audioStreamManager.dispose();
		}*/

		if(managerListener != null){
			managerListener.onDestroy(this);
			managerListener = null;
		}
	}


	private void checkSdlManagerState(){
		if (getState() != BaseSubManager.READY && getState() != BaseSubManager.LIMITED){
			Log.e(TAG, "SdlManager is not ready for use, be sure to initialize with start() method, implement callback, and use SubManagers in the SdlManager's callback");
		}
	}

	// MANAGER GETTERS

	/**
	 * Gets the PermissionManager. <br>
	 * <strong>Note: PermissionManager should be used only after SdlManager.start() CompletionListener callback is completed successfully.</strong>
	 * @return a PermissionManager object
	 */
	public PermissionManager getPermissionManager() {
		if (permissionManager.getState() != BaseSubManager.READY && permissionManager.getState() != BaseSubManager.LIMITED){
			Log.e(TAG,"PermissionManager should not be accessed because it is not in READY/LIMITED state");
		}
		checkSdlManagerState();
		return permissionManager;
	}

	/**
	 * Gets the FileManager. <br>
	 * <strong>Note: FileManager should be used only after SdlManager.start() CompletionListener callback is completed successfully.</strong>
	 * @return a FileManager object
	 */
	public FileManager getFileManager() {
		if (fileManager.getState() != BaseSubManager.READY && fileManager.getState() != BaseSubManager.LIMITED){
			Log.e(TAG, "FileManager should not be accessed because it is not in READY/LIMITED state");
		}
		checkSdlManagerState();
		return fileManager;
	}

    /**
     * Gets the VideoStreamManager. <br>
	 * The VideoStreamManager returned will only be not null if the registered app type is
	 * either NAVIGATION or PROJECTION. Once the VideoStreamManager is retrieved, its start()
	 * method will need to be called before use.
     * <br><br><strong>Note: VideoStreamManager should be used only after SdlManager.start() CompletionListener callback is completed successfully.</strong>
     * @return a VideoStreamManager object attached to shit SdlManager instance
     */
    /* FIXME
	public @Nullable
    VideoStreamManager getVideoStreamManager() {
		checkSdlManagerState();
		return videoStreamManager;
	}*/

    /**
     * Gets the AudioStreamManager. <br>
	 * The AudioStreamManager returned will only be not null if the registered app type is
	 * either NAVIGATION or PROJECTION. Once the AudioStreamManager is retrieved, its start()
	 * method will need to be called before use.
     * <br><strong>Note: AudioStreamManager should be used only after SdlManager.start() CompletionListener callback is completed successfully.</strong>
     * @return a AudioStreamManager object
     */
	/* FIXME public @Nullable AudioStreamManager getAudioStreamManager() {
		checkSdlManagerState();
		return audioStreamManager;
	}*/

	/**
	 * Gets the ScreenManager. <br>
	 * <strong>Note: ScreenManager should be used only after SdlManager.start() CompletionListener callback is completed successfully.</strong>
	 * @return a ScreenManager object
	 */
	public ScreenManager getScreenManager() {
		if (screenManager.getState() != BaseSubManager.READY && screenManager.getState() != BaseSubManager.LIMITED){
			Log.e(TAG, "ScreenManager should not be accessed because it is not in READY/LIMITED state");
		}
		checkSdlManagerState();
		return screenManager;
	}

	/**
	 * Gets the LockScreenManager. <br>
	 * <strong>Note: LockScreenManager should be used only after SdlManager.start() CompletionListener callback is completed successfully.</strong>
	 * @return a LockScreenManager object
	 */
	/* FIXME public LockScreenManager getLockScreenManager() {
		if (lockScreenManager.getState() != BaseSubManager.READY && lockScreenManager.getState() != BaseSubManager.LIMITED){
			Log.e(TAG, "LockScreenManager should not be accessed because it is not in READY/LIMITED state");
		}
		checkSdlManagerState();
		return lockScreenManager;
	}*/

	/**
	 * Gets the SystemCapabilityManager. <br>
	 * <strong>Note: SystemCapabilityManager should be used only after SdlManager.start() CompletionListener callback is completed successfully.</strong>
	 * @return a SystemCapabilityManager object
	 */
	public SystemCapabilityManager getSystemCapabilityManager(){
		return proxy.getSystemCapabilityManager();
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
		if(proxy != null){
			return proxy.getRegisterAppInterfaceResponse();
		}
		return null;
	}

	/**
	 * Get the current OnHMIStatus
	 * @return OnHMIStatus object represents the current OnHMIStatus
	 */
	public OnHMIStatus getCurrentHMIStatus(){
		if(this.proxy !=null ){
			return proxy.getCurrentHMIStatus();
		}
		return null;
	}

	// PROTECTED GETTERS
	protected String getAppName() { return appName; }

	protected String getAppId() { return appId; }

	protected String getShortAppName() { return shortAppName; }

	protected Language getHmiLanguage() { return hmiLanguage; }

	protected TemplateColorScheme getDayColorScheme() { return dayColorScheme; }

	protected TemplateColorScheme getNightColorScheme() { return nightColorScheme; }

	protected Vector<AppHMIType> getAppTypes() { return hmiTypes; }

	protected Vector<String> getVrSynonyms() { return vrSynonyms; }

	protected Vector<TTSChunk> getTtsChunks() { return ttsChunks; }

	protected BaseTransportConfig getTransport() { return transport; }

	//FIXME protected LockScreenConfig getLockScreenConfig() { return lockScreenConfig; }

	// SENDING REQUESTS

	/**
	 * Send RPC Message <br>
	 * <strong>Note: Only takes type of RPCRequest for now, notifications and responses will be thrown out</strong>
	 * @param message RPCMessage
	 */
	public void sendRPC(RPCMessage message) {

		if (message instanceof RPCRequest){
			proxy.sendRpc(message);
		}
	}

	/**
	 * Takes a list of RPCMessages and sends it to SDL in a synchronous fashion. Responses are captured through callback on OnMultipleRequestListener.
	 * For sending requests asynchronously, use sendRequests <br>
	 *
	 * <strong>NOTE: This will override any listeners on individual RPCs</strong><br>
	 *
	 * <strong>ADDITIONAL NOTE: This only takes the type of RPCRequest for now, notifications and responses will be thrown out</strong>
	 *
	 * @param rpcs is the list of RPCMessages being sent
	 * @param listener listener for updates and completions
	 */
	public void sendSequentialRPCs(final List<? extends RPCMessage> rpcs, final OnMultipleRequestListener listener){

		List<RPCRequest> rpcRequestList = new ArrayList<>();
		for (int i = 0; i < rpcs.size(); i++) {
			if (rpcs.get(i) instanceof RPCRequest){
				rpcRequestList.add((RPCRequest)rpcs.get(i));
			}
		}

		if (rpcRequestList.size() > 0) {
			proxy.sendRpcsSequentially(rpcRequestList, listener);
		}
	}

	/**
	 * Takes a list of RPCMessages and sends it to SDL. Responses are captured through callback on OnMultipleRequestListener.
	 * For sending requests synchronously, use sendSequentialRPCs <br>
	 *
	 * <strong>NOTE: This will override any listeners on individual RPCs</strong> <br>
	 *
	 * <strong>ADDITIONAL NOTE: This only takes the type of RPCRequest for now, notifications and responses will be thrown out</strong>
	 *
	 * @param rpcs is the list of RPCMessages being sent
	 * @param listener listener for updates and completions
	 */
	public void sendRPCs(List<? extends RPCMessage> rpcs, final OnMultipleRequestListener listener) {

		List<RPCRequest> rpcRequestList = new ArrayList<>();
		for (int i = 0; i < rpcs.size(); i++) {
			if (rpcs.get(i) instanceof RPCRequest){
				rpcRequestList.add((RPCRequest)rpcs.get(i));
			}
		}

		if (rpcRequestList.size() > 0) {
			proxy.sendRpcs(rpcRequestList, listener);
		}
	}

	private void handleSdlException(SdlException exception){
		if(exception != null){
			DebugTool.logError("Caught SdlException: " + exception.getSdlExceptionCause());
			// In the future this should handle logic to dispose the manager if it is an unrecoverable error
		}else{
			DebugTool.logError("Caught SdlException" );
		}
	}

	/**
	 * Add an OnRPCNotificationListener
	 * @param listener listener that will be called when a notification is received
	 */
	public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener){
		proxy.addOnRPCNotificationListener(notificationId,listener);
	}

	/**
	 * Remove an OnRPCNotificationListener
	 * @param listener listener that was previously added
	 */
	public void removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener){
		proxy.removeOnRPCNotificationListener(notificationId, listener);
	}

	// LIFECYCLE / OTHER

	// STARTUP

	/**
	 * Starts up a SdlManager, and calls provided callback called once all BaseSubManagers are done setting up
	 */
	@SuppressWarnings("unchecked")
	public void start(){
		Log.i(TAG, "start");
		if (proxy == null) {
			//FIXME if(transport!= null  && transport.getTransportType() == TransportType.MULTIPLEX){
			if (transport != null && transport.getTransportType().equals(TransportType.WEB_SOCKET_SERVER)) {
				//Do the thing

					/*FIXME MultiplexTransportConfig multiplexTransportConfig = (MultiplexTransportConfig)(transport);
					final MultiplexTransportConfig.TransportListener devListener = multiplexTransportConfig.getTransportListener();
					multiplexTransportConfig.setTransportListener(new MultiplexTransportConfig.TransportListener() {
						@Override
						public void onTransportEvent(List<TransportRecord> connectedTransports, boolean audioStreamTransportAvail, boolean videoStreamTransportAvail) {

							//Pass to submanagers that need it
							if(videoStreamManager != null){
								videoStreamManager.handleTransportUpdated(connectedTransports, audioStreamTransportAvail, videoStreamTransportAvail);
							}

							if(audioStreamManager != null){
								audioStreamManager.handleTransportUpdated(connectedTransports, audioStreamTransportAvail, videoStreamTransportAvail);
							}
							//If the developer supplied a listener to start, it is time to call that
							if(devListener != null){
								devListener.onTransportEvent(connectedTransports,audioStreamTransportAvail,videoStreamTransportAvail);
							}
						}
					});
				}*/

				LifecycleManager.AppConfig appConfig = new LifecycleManager.AppConfig();
				appConfig.appName = appName;
				//short app name
				appConfig.isMediaApp = isMediaApp;
				appConfig.hmiDisplayLanguageDesired = hmiLanguage;
				appConfig.languageDesired = hmiLanguage;
				appConfig.appType = hmiTypes;
				appConfig.vrSynonyms = vrSynonyms;
				appConfig.ttsName = ttsChunks;
				appConfig.dayColorScheme = dayColorScheme;
				appConfig.nightColorScheme = nightColorScheme;
				appConfig.appID = appId;


				proxy = new LifecycleManager(appConfig, (WebSocketServerConfig)transport, lifecycleListener);
				proxy.start();
				if (sdlSecList != null && !sdlSecList.isEmpty()) {
					proxy.setSdlSecurityClassList(sdlSecList);
				}
				for (FunctionID functionID: onRPCNotificationListeners.keySet()) {
					proxy.addOnRPCNotificationListener(functionID, onRPCNotificationListeners.get(functionID));
				}

			}else{
				throw new RuntimeException("No transport provided");
			}
		}
	}

	/* FIXME protected void setProxy(SdlProxyBase proxy){
		this.proxy = proxy;
	}*/

	// INTERNAL INTERFACE
	private ISdl _internalInterface = new ISdl() {
		@Override
		public void start() {
			proxy.start();
		}

		@Override
		public void stop() {
			proxy.stop();
		}

		@Override
		public boolean isConnected() {
			return proxy.isConnected();
		}

		@Override
		public void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {
			//FIXME proxy.addServiceListener(serviceType,sdlServiceListener);
		}

		@Override
		public void removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {
			//FIXME proxy.removeServiceListener(serviceType,sdlServiceListener);
		}

		@Override
		public void startVideoService(VideoStreamingParameters parameters, boolean encrypted) {
			if(proxy.isConnected()){
				//FIXME proxy.startVideoStream(encrypted,parameters);
			}
		}

		@Override
		public IVideoStreamListener startVideoStream(boolean isEncrypted, VideoStreamingParameters parameters){
			return null; //FIXME  proxy.startVideoStream(isEncrypted, parameters);
		}

		@Override
		public void stopVideoService() {
			if(proxy.isConnected()){
				//FIXME proxy.endVideoStream();
			}
		}

		@Override
		public void startAudioService(boolean isEncrypted, AudioStreamingCodec codec,
		                              AudioStreamingParams params) {
			if(proxy.isConnected()){
				//FIXME proxy.startAudioStream(isEncrypted, codec, params);
			}
		}

		@Override
		public void startAudioService(boolean encrypted) {
			if(proxy.isConnected()){
				//FIXME proxy.startService(SessionType.PCM, encrypted);
			}
		}

		@Override
		public IAudioStreamListener startAudioStream(boolean isEncrypted, AudioStreamingCodec codec,
		                                             AudioStreamingParams params) {
			return null; //FIXME proxy.startAudioStream(isEncrypted, codec, params);
		}

		@Override
		public void stopAudioService() {
			if(proxy.isConnected()){
				//FIXME proxy.endAudioStream();
			}
		}

		@Override
		public void sendRPCRequest(RPCRequest message){
			if(message != null){
				proxy.sendRpc(message);
			}
		}

		@Override
		public void sendRPC(RPCMessage message) {
			if(message != null){
				proxy.sendRpc(message);
			}
		}

		@Override
		public void sendRequests(List<? extends RPCRequest> rpcs, OnMultipleRequestListener listener) {
			proxy.sendRpcs(rpcs, listener);
		}

		@Override
		public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {
			proxy.addOnRPCNotificationListener(notificationId,listener);
		}

		@Override
		public boolean removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {
			return proxy.removeOnRPCNotificationListener(notificationId,listener);
		}

		@Override
		public void addOnRPCListener(final FunctionID responseId, final OnRPCListener listener) {
			proxy.addRpcListener(responseId, listener);
		}

		@Override
		public boolean removeOnRPCListener(final FunctionID responseId, final OnRPCListener listener) {
			return proxy.removeOnRPCListener(responseId, listener);
		}

		@Override
		public Object getCapability(SystemCapabilityType systemCapabilityType){
			return proxy.getSystemCapabilityManager().getCapability(systemCapabilityType);
		}

		@Override
		public void getCapability(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener scListener) {
			proxy.getSystemCapabilityManager().getCapability(systemCapabilityType, scListener);
		}

		@Override
		public boolean isCapabilitySupported(SystemCapabilityType systemCapabilityType){
			return proxy.getSystemCapabilityManager().isCapabilitySupported(systemCapabilityType);
		}

		@Override
		public void addOnSystemCapabilityListener(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener listener) {
			proxy.getSystemCapabilityManager().addOnSystemCapabilityListener(systemCapabilityType, listener);
		}

		@Override
		public boolean removeOnSystemCapabilityListener(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener listener) {
			return proxy.getSystemCapabilityManager().removeOnSystemCapabilityListener(systemCapabilityType, listener);
		}

		@Override
		public boolean isTransportForServiceAvailable(SessionType serviceType) {
			/* FIXME if(SessionType.NAV.equals(serviceType)){
				return proxy.isVideoStreamTransportAvailable();
			}else if(SessionType.PCM.equals(serviceType)){
				return proxy.isAudioStreamTransportAvailable();
			} */
			return false;
		}

		@Override
		public SdlMsgVersion getSdlMsgVersion(){
			//FIXME this should be a breaking change to support our version
			 Version rpcSepcVersion =  proxy.getRpcSpecVersion();
			 if(rpcSepcVersion != null){
				 SdlMsgVersion sdlMsgVersion = new SdlMsgVersion();
				 sdlMsgVersion.setMajorVersion(rpcSepcVersion.getMajor());
				 sdlMsgVersion.setMinorVersion(rpcSepcVersion.getMinor());
				 sdlMsgVersion.setPatchVersion(rpcSepcVersion.getPatch());
				 return sdlMsgVersion;
			 }

			return null;
		}

		@Override
		public @NonNull Version getProtocolVersion() {
			if(proxy.getProtocolVersion() != null){
				return proxy.getProtocolVersion();
			}else{
				return new Version(1,0,0);
			}
		}

	};


	// BUILDER
	public static class Builder {
		SdlManager sdlManager;

		/**
		 * Builder for the SdlManager. Parameters in the constructor are required.
		 * @param appId the app's ID
		 * @param appName the app's name
		 * @param listener a SdlManagerListener object
		 */
		//FIXME public Builder(@NonNull Context context, @NonNull final String appId, @NonNull final String appName, @NonNull final SdlManagerListener listener){
		public Builder(@NonNull final String appId, @NonNull final String appName, @NonNull final SdlManagerListener listener){
			sdlManager = new SdlManager();
			//FIXME setContext(context);
			setAppId(appId);
			setAppName(appName);
			setManagerListener(listener);
		}

		/**
		 * Sets the App ID
		 * @param appId
		 */
		public Builder setAppId(@NonNull final String appId){
			sdlManager.appId = appId;
			return this;
		}

		/**
		 * Sets the Application Name
		 * @param appName
		 */
		public Builder setAppName(@NonNull final String appName){
			sdlManager.appName = appName;
			return this;
		}

		/**
		 * Sets the Short Application Name
		 * @param shortAppName
		 */
		public Builder setShortAppName(final String shortAppName) {
			sdlManager.shortAppName = shortAppName;
			return this;
		}

		/**
		 * Sets the Language of the App
		 * @param hmiLanguage
		 */
		public Builder setLanguage(final Language hmiLanguage){
			sdlManager.hmiLanguage = hmiLanguage;
			return this;
		}

		/**
		 * Sets the TemplateColorScheme for daytime
		 * @param dayColorScheme
		 */
		public Builder setDayColorScheme(final TemplateColorScheme dayColorScheme){
			sdlManager.dayColorScheme = dayColorScheme;
			return this;
		}

		/**
		 * Sets the TemplateColorScheme for nighttime
		 * @param nightColorScheme
		 */
		public Builder setNightColorScheme(final TemplateColorScheme nightColorScheme){
			sdlManager.nightColorScheme = nightColorScheme;
			return this;
		}

		/**
		 * Sets the LockScreenConfig for the session. <br>
		 * <strong>Note: If not set, the default configuration will be used.</strong>
		 * @param lockScreenConfig - configuration options
		 */
		/*FIXME public Builder setLockScreenConfig (final LockScreenConfig lockScreenConfig){
			sdlManager.lockScreenConfig = lockScreenConfig;
			return this;
		}*/

		/**
		 * Sets the icon for the app on HU <br>
		 * @param sdlArtwork
		 */
		public Builder setAppIcon(final SdlArtwork sdlArtwork){
			sdlManager.appIcon = sdlArtwork;
			return this;
		}

		/**
		 * Sets the vector of AppHMIType <br>
		 * <strong>Note: This should be an ordered list from most -> least relevant</strong>
		 * @param hmiTypes
		 */
		public Builder setAppTypes(final Vector<AppHMIType> hmiTypes){

			sdlManager.hmiTypes = hmiTypes;

			if (hmiTypes != null) {
				sdlManager.isMediaApp = hmiTypes.contains(AppHMIType.MEDIA);
			}

			return this;
		}

		/**
		 * Sets the vector of vrSynonyms
		 * @param vrSynonyms
		 */
		public Builder setVrSynonyms(final Vector<String> vrSynonyms) {
			sdlManager.vrSynonyms = vrSynonyms;
			return this;
		}

		/**
		 * Sets the TTS Name
		 * @param ttsChunks
		 */
		public Builder setTtsName(final Vector<TTSChunk> ttsChunks) {
			sdlManager.ttsChunks = ttsChunks;
			return this;
		}

		/**
		 * This Object type may change with the transport refactor
		 * Sets the BaseTransportConfig
		 * @param transport
		 */
		public Builder setTransportType(BaseTransportConfig transport){
			sdlManager.transport = transport;
			return this;
		}

		/**
		 * Sets the Context
		 * @param context
		 */
		/* FIXME public Builder setContext(Context context){
			sdlManager.context = context;
			return this;
		}*/

		/**
		 * Sets the Security library
		 * @param secList The list of security class(es)
		 */
		public Builder setSdlSecurity(List<Class<? extends SdlSecurityBase>> secList) {
			sdlManager.sdlSecList = secList;
			return this;
		}

		/**
		 * Set the SdlManager Listener
		 * @param listener the listener
		 */
		public Builder setManagerListener(@NonNull final SdlManagerListener listener){
			sdlManager.managerListener = listener;
			return this;
		}

		/**
		 * Set RPCNotification listeners. SdlManager will preload these listeners before any RPCs are sent/received.
		 * @param listeners a map of listeners that will be called when a notification is received.
		 * Key represents the FunctionID of the notification and value represents the listener
		 */
		public Builder setRPCNotificationListeners(Map<FunctionID, OnRPCNotificationListener> listeners){
			sdlManager.onRPCNotificationListeners = listeners;
			return this;
		}

		public SdlManager build() {

			if (sdlManager.appName == null) {
				throw new IllegalArgumentException("You must specify an app name by calling setAppName");
			}

			if (sdlManager.appId == null) {
				throw new IllegalArgumentException("You must specify an app ID by calling setAppId");
			}

			if (sdlManager.managerListener == null) {
				throw new IllegalArgumentException("You must set a SdlManagerListener object");
			}

			if (sdlManager.hmiTypes == null) {
				Vector<AppHMIType> hmiTypesDefault = new Vector<>();
				hmiTypesDefault.add(AppHMIType.DEFAULT);
				sdlManager.hmiTypes = hmiTypesDefault;
				sdlManager.isMediaApp = false;
			}

			/* if (sdlManager.lockScreenConfig == null){
				// if lock screen params are not set, use default
				sdlManager.lockScreenConfig = new LockScreenConfig();
			}*/

			if (sdlManager.hmiLanguage == null){
				sdlManager.hmiLanguage = Language.EN_US;
			}

			sdlManager.transitionToState(BaseSubManager.SETTING_UP);

			return sdlManager;
		}
	}

}
