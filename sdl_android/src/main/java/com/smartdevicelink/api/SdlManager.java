package com.smartdevicelink.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.SdlProxyBase;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.TemplateColorScheme;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.streaming.audio.AudioStreamingCodec;
import com.smartdevicelink.streaming.audio.AudioStreamingParams;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.BaseTransportConfig;

import java.util.ArrayList;
import java.util.List;
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
public class SdlManager implements ProxyBridge.LifecycleListener {

	private static String TAG = "Sdl Manager";
	private SdlProxyBase proxy;

	// Required parameters for builder
	private String appId, appName, shortAppName;
	private boolean isMediaApp;
	private Language hmiLanguage;
	private Vector<AppHMIType> hmiTypes;
	private BaseTransportConfig transport;
	private Context context;
	private Vector<String> vrSynonyms;
	private Vector<TTSChunk> ttsChunks;
	private TemplateColorScheme dayColorScheme, nightColorScheme;

	private final ProxyBridge proxyBridge= new ProxyBridge(this);
	//public LockScreenConfig lockScreenConfig;

	// Managers
    /*
    private FileManager fileManager;
    private VideoStreamingManager videoStreamingManager;
    private AudioStreamManager audioStreamManager;
    private LockscreenManager lockscreenManager;
    private ScreenManager screenManager;
    private PermissionManager permissionManager;
    */

	private void initialize(){
		// instantiate managers

		/*
		this.fileManager = new FileManager(_internalInterface, context);
		this.lockscreenManager = new LockscreenManager(lockScreenConfig, context, _internalInterface);
		this.screenManager = new ScreenManager(_internalInterface, this.fileManager);
		this.permissionManager = new PermissionManager(_internalInterface);
		this.videoStreamingManager = new VideoStreamingManager(context, _internalInterface);
		this.audioStreamManager = new AudioStreamManager(_internalInterface);
		*/
	}

	private void dispose() {
		/*
		this.fileManager.dispose();
		this.lockscreenManager.dispose();
		this.audioStreamManager.dispose();
		this.screenManager.dispose();
		this.permissionManager.dispose();
		this.videoStreamingManager.dispose();
		this.audioStreamManager.dispose();
		*/
	}

	// BUILDER

	public static class Builder {
		SdlManager sdlManager;

		/**
		 * Main Builder for SDL Manager<br>
		 *
		 * The following setters are <strong>REQUIRED:</strong><br>
		 *
		 * • setAppId <br>
		 * • setAppName
		 */
		public Builder(){
			sdlManager = new SdlManager();
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

		/*
		public Builder setLockScreenConfig (final LockScreenConfig lockScreenConfig){
			sdlManager.lockScreenConfig = lockScreenConfig;
			return this;
		}*/

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
		public Builder setContext(Context context){
			sdlManager.context = context;
			return this;
		}

		@SuppressWarnings("unchecked")
		public SdlManager build() {
			try {

				if (sdlManager.appName == null) {
					throw new IllegalArgumentException("You must specify an app name by calling setAppName");
				}

				if (sdlManager.appId == null) {
					throw new IllegalArgumentException("You must specify an app ID by calling setAppId");
				}

				if (sdlManager.hmiTypes == null) {
					Vector<AppHMIType> hmiTypesDefault = new Vector<>();
					hmiTypesDefault.add(AppHMIType.DEFAULT);
					sdlManager.hmiTypes = hmiTypesDefault;
					sdlManager.isMediaApp = false;
				}

				if (sdlManager.hmiLanguage == null){
					sdlManager.hmiLanguage = Language.EN_US;
				}

				sdlManager.initialize();
				sdlManager.proxy = new SdlProxyBase(sdlManager.proxyBridge, sdlManager.appName, sdlManager.shortAppName, sdlManager.isMediaApp, sdlManager.hmiLanguage, sdlManager.hmiLanguage, sdlManager.hmiTypes, sdlManager.appId, sdlManager.transport, sdlManager.vrSynonyms, sdlManager.ttsChunks, sdlManager.dayColorScheme, sdlManager.nightColorScheme) {};
			} catch (SdlException e) {
				e.printStackTrace();
			}
			return sdlManager;
		}
	}

	// MANAGER GETTERS

	protected String getAppName() {
		return appName;
	}

	/*
	public FileManager getFileManager() {
		return fileManager;
	}

	public VideoStreamingManager getVideoStreamingManager() {
		return videoStreamingManager;
	}

	public AudioStreamManager getAudioStreamManager() {
		return audioStreamManager;
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public LockscreenManager getLockscreenManager() {
		return lockscreenManager;
	}

	public PermissionManager getPermissionManager() {
		return permissionManager;
	}*/

	// SENDING REQUESTS

	/**
	 * Send RPC Message <br>
	 * <strong>Note: Only takes type of RPCRequest for now, notifications and responses will be thrown out</strong>
	 * @param message RPCMessage
	 * @throws SdlException
	 */
	public void sendRPC(RPCMessage message) throws SdlException {

		if (message instanceof RPCRequest){
			proxy.sendRPCRequest((RPCRequest)message);
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
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	public void sendSequentialRPCs(final List<? extends RPCMessage> rpcs, final OnMultipleRequestListener listener) throws SdlException {

		List<RPCRequest> rpcRequestList = new ArrayList<>();
		for (int i = 0; i < rpcs.size(); i++) {
			if (rpcs.get(i) instanceof RPCRequest){
				rpcRequestList.add((RPCRequest)rpcs.get(i));
			}
		}

		if (rpcRequestList.size() > 0) {
			proxy.sendSequentialRequests(rpcRequestList, listener);
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
	 * @throws SdlException if an unrecoverable error is encountered
	 */
	public void sendRPCs(List<? extends RPCMessage> rpcs, final OnMultipleRequestListener listener) throws SdlException {

		List<RPCRequest> rpcRequestList = new ArrayList<>();
		for (int i = 0; i < rpcs.size(); i++) {
			if (rpcs.get(i) instanceof RPCRequest){
				rpcRequestList.add((RPCRequest)rpcs.get(i));
			}
		}

		if (rpcRequestList.size() > 0) {
			proxy.sendRequests(rpcRequestList, listener);
		}
	}

	// LIFECYCLE / OTHER

	@Override
	public void onProxyClosed(String info, Exception e, SdlDisconnectedReason reason){
		this.dispose();
	}

	@Override
	public void onServiceEnded(OnServiceEnded serviceEnded){

	}

	@Override
	public void onServiceNACKed(OnServiceNACKed serviceNACKed){

	}

	@Override
	public void onError(String info, Exception e){

	}

	// INTERNAL INTERFACE

	private ISdl _internalInterface = new ISdl() {
		@Override
		public void start() {
			try{
				proxy.initializeProxy();
			}catch (SdlException e){
				e.printStackTrace();
			}
		}

		@Override
		public void stop() {
			try{
				proxy.dispose();
			}catch (SdlException e){
				e.printStackTrace();
			}
		}

		@Override
		public boolean isConnected() {
			return proxy.getIsConnected();
		}

		@Override
		public void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {
			proxy.addServiceListener(serviceType,sdlServiceListener);
		}

		@Override
		public void removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {
			proxy.removeServiceListener(serviceType,sdlServiceListener);
		}

		@Override
		public void startVideoService(VideoStreamingParameters parameters, boolean encrypted) {
			if(proxy.getIsConnected()){
				proxy.startVideoStream(encrypted,parameters);
			}
		}

		@Override
		public IVideoStreamListener startVideoStream(boolean isEncrypted, VideoStreamingParameters parameters){
			return proxy.startVideoStream(isEncrypted, parameters);
		}

		@Override
		public void stopVideoService() {
			if(proxy.getIsConnected()){
				proxy.endVideoStream();
			}
		}

		@Override
		public void startAudioService(boolean isEncrypted, AudioStreamingCodec codec,
									  AudioStreamingParams params) {
			if(proxy.getIsConnected()){
				proxy.startAudioStream(isEncrypted, codec, params);
			}
		}

		@Override
		public void startAudioService(boolean encrypted) {
			if(isConnected()){
				proxy.startService(SessionType.PCM, encrypted);
			}
		}

		@Override
		public IAudioStreamListener startAudioStream(boolean isEncrypted, AudioStreamingCodec codec,
													 AudioStreamingParams params) {
			return proxy.startAudioStream(isEncrypted, codec, params);
		}

		@Override
		public void stopAudioService() {
			if(proxy.getIsConnected()){
				proxy.endAudioStream();
			}
		}

		@Override
		public void sendRPCRequest(RPCRequest message){
			try {
				proxy.sendRPCRequest(message);
			} catch (SdlException e) {
				e.printStackTrace();
			}
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
		public Object getCapability(SystemCapabilityType systemCapabilityType){
			return proxy.getCapability(systemCapabilityType);
		}

		@Override
		public void getCapability(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener scListener) {
			proxy.getCapability(systemCapabilityType, scListener);
		}

		@Override
		public boolean isCapabilitySupported(SystemCapabilityType systemCapabilityType){
			return proxy.isCapabilitySupported(systemCapabilityType);
		}

		@Override
		public SdlMsgVersion getSdlMsgVersion(){
			try {
				return proxy.getSdlMsgVersion();
			} catch (SdlException e) {
				e.printStackTrace();
			}
			return null;
		}
	};

}
