/*
 * Copyright (c) 2019 Livio, Inc.
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
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
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

package com.smartdevicelink.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.managers.audio.AudioStreamManager;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.lifecycle.LifecycleConfigurationUpdate;
import com.smartdevicelink.managers.lockscreen.LockScreenConfig;
import com.smartdevicelink.managers.lockscreen.LockScreenManager;
import com.smartdevicelink.managers.permission.PermissionManager;
import com.smartdevicelink.managers.screen.ScreenManager;
import com.smartdevicelink.managers.video.VideoStreamManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.SdlProxyBase;
import com.smartdevicelink.proxy.SystemCapabilityManager;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.ChangeRegistration;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SetAppIcon;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.TemplateColorScheme;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.streaming.audio.AudioStreamingCodec;
import com.smartdevicelink.streaming.audio.AudioStreamingParams;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import org.json.JSONException;

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
	private SdlProxyBase proxy;
	private SdlArtwork appIcon;
	private Context context;
	private SdlManagerListener managerListener;
	private List<Class<? extends SdlSecurityBase>> sdlSecList;
	private LockScreenConfig lockScreenConfig;
	private ServiceEncryptionListener serviceEncryptionListener;

	// Managers
	private PermissionManager permissionManager;
	private FileManager fileManager;
	private LockScreenManager lockScreenManager;
    private ScreenManager screenManager;
	private VideoStreamManager videoStreamManager;
	private AudioStreamManager audioStreamManager;


	// Initialize proxyBridge with anonymous lifecycleListener
	private final ProxyBridge proxyBridge = new ProxyBridge(new ProxyBridge.LifecycleListener() {
		@Override
		public void onProxyConnected() {
			DebugTool.logInfo("Proxy is connected. Now initializing.");
			changeRegistrationRetry = 0;
			checkLifecycleConfiguration();
			initialize();
		}

		@Override
		public void onProxyClosed(String info, Exception e, SdlDisconnectedReason reason){
			if (!reason.equals(SdlDisconnectedReason.LANGUAGE_CHANGE)){
				dispose();
			}
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
	});

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

	@Override
	void checkState() {
		if (permissionManager != null && fileManager != null && screenManager != null && (!lockScreenConfig.isEnabled() || lockScreenManager != null)) {
			if (permissionManager.getState() == BaseSubManager.READY && fileManager.getState() == BaseSubManager.READY && screenManager.getState() == BaseSubManager.READY && (!lockScreenConfig.isEnabled() || lockScreenManager.getState() == BaseSubManager.READY)) {
				DebugTool.logInfo("Starting sdl manager, all sub managers are in ready state");
				transitionToState(BaseSubManager.READY);
				handleQueuedNotifications();
				notifyDevListener(null);
				onReady();
			} else if (permissionManager.getState() == BaseSubManager.ERROR && fileManager.getState() == BaseSubManager.ERROR && screenManager.getState() == BaseSubManager.ERROR && (!lockScreenConfig.isEnabled() || lockScreenManager.getState() == BaseSubManager.ERROR)) {
				String info = "ERROR starting sdl manager, all sub managers are in error state";
				Log.e(TAG, info);
				transitionToState(BaseSubManager.ERROR);
				notifyDevListener(info);
			} else if (permissionManager.getState() == BaseSubManager.SETTING_UP || fileManager.getState() == BaseSubManager.SETTING_UP || screenManager.getState() == BaseSubManager.SETTING_UP || (lockScreenConfig.isEnabled() && lockScreenManager != null && lockScreenManager.getState() == BaseSubManager.SETTING_UP)) {
				DebugTool.logInfo("SETTING UP sdl manager, some sub managers are still setting up");
				transitionToState(BaseSubManager.SETTING_UP);
				// No need to notify developer here!
			} else {
				Log.w(TAG, "LIMITED starting sdl manager, some sub managers are in error or limited state and the others finished setting up");
				transitionToState(BaseSubManager.LIMITED);
				handleQueuedNotifications();
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
				managerListener.onError(info, null);
			} else {
				managerListener.onStart();
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

	@Override
	protected void checkLifecycleConfiguration(){
		final Language actualLanguage =  this.getRegisterAppInterfaceResponse().getLanguage();
		final Language actualHMILanguage =  this.getRegisterAppInterfaceResponse().getHmiDisplayLanguage();

		if ((actualLanguage != null && !actualLanguage.equals(language)) || (actualHMILanguage != null && !actualHMILanguage.equals(hmiLanguage))) {

			final LifecycleConfigurationUpdate lcu = managerListener.managerShouldUpdateLifecycle(actualLanguage, actualHMILanguage);

			if (lcu != null) {
				ChangeRegistration changeRegistration = new ChangeRegistration(actualLanguage, actualHMILanguage);
				changeRegistration.setAppName(lcu.getAppName());
				changeRegistration.setNgnMediaScreenAppName(lcu.getShortAppName());
				changeRegistration.setTtsName(lcu.getTtsName());
				changeRegistration.setVrSynonyms(lcu.getVoiceRecognitionCommandNames());
				changeRegistration.setOnRPCResponseListener(new OnRPCResponseListener() {
					@Override
					public void onResponse(int correlationId, RPCResponse response) {
						if (response.getSuccess()){
							// go through and change sdlManager properties that were changed via the LCU update
							hmiLanguage = actualHMILanguage;
							language = actualLanguage;

							if (lcu.getAppName() != null) {
								appName = lcu.getAppName();
							}

							if (lcu.getShortAppName() != null) {
								shortAppName = lcu.getShortAppName();
							}

							if (lcu.getTtsName() != null) {
								ttsChunks = lcu.getTtsName();
							}

							if (lcu.getVoiceRecognitionCommandNames() != null) {
								vrSynonyms = lcu.getVoiceRecognitionCommandNames();
							}
						}
						try {
							DebugTool.logInfo(response.serializeJSON().toString());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onError(int correlationId, Result resultCode, String info) {
						DebugTool.logError("Change Registration onError: " + resultCode + " | Info: " + info);
						changeRegistrationRetry++;
						if (changeRegistrationRetry < MAX_RETRY) {
							final Handler handler = new Handler(Looper.getMainLooper());
							handler.postDelayed(new Runnable() {
								@Override
								public void run() {
									checkLifecycleConfiguration();
									DebugTool.logInfo("Retry Change Registration Count: " + changeRegistrationRetry);
								}
							}, 3000);
						}
					}
				});
				this.sendRPC(changeRegistration);
			}
		}
	}

	@Override
	protected void initialize(){
		// Instantiate sub managers
		this.permissionManager = new PermissionManager(_internalInterface);
		this.fileManager = new FileManager(_internalInterface, context);
		if (lockScreenConfig.isEnabled()) {
			this.lockScreenManager = new LockScreenManager(lockScreenConfig, context, _internalInterface);
		}
		this.screenManager = new ScreenManager(_internalInterface, this.fileManager);
		if(getAppTypes().contains(AppHMIType.NAVIGATION) || getAppTypes().contains(AppHMIType.PROJECTION)){
			this.videoStreamManager = new VideoStreamManager(_internalInterface);
		} else {
			this.videoStreamManager = null;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
				&& (getAppTypes().contains(AppHMIType.NAVIGATION) || getAppTypes().contains(AppHMIType.PROJECTION)) ) {
			this.audioStreamManager = new AudioStreamManager(_internalInterface, context);
		} else {
			this.audioStreamManager = null;
		}

		// Start sub managers
		this.permissionManager.start(subManagerListener);
		this.fileManager.start(subManagerListener);
		if (lockScreenConfig.isEnabled()){
			this.lockScreenManager.start(subManagerListener);
		}
		this.screenManager.start(subManagerListener);
	}

	/** Dispose SdlManager and clean its resources
	 * <strong>Note: new instance of SdlManager should be created on every connection. SdlManager cannot be reused after getting disposed.</strong>
	 */
	@SuppressLint("NewApi")
	@Override
	public void dispose() {
		if (this.permissionManager != null) {
			this.permissionManager.dispose();
		}

		if (this.fileManager != null) {
			this.fileManager.dispose();
		}

		if (this.lockScreenManager != null) {
			this.lockScreenManager.dispose();
		}

		if (this.screenManager != null) {
			this.screenManager.dispose();
		}

		if(this.videoStreamManager != null) {
			this.videoStreamManager.dispose();
		}

		// SuppressLint("NewApi") is used because audioStreamManager is only available on android >= jelly bean
		if (this.audioStreamManager != null) {
			this.audioStreamManager.dispose();
		}

		if (this.proxy != null && !proxy.isDisposed()) {
			try {
				this.proxy.dispose();
			} catch (SdlException e) {
				DebugTool.logError("Issue disposing proxy in SdlManager", e);
			}
		}

		if(managerListener != null){
			managerListener.onDestroy();
			managerListener = null;
		}

		transitionToState(BaseSubManager.SHUTDOWN);
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
     * @return a VideoStreamManager object attached to this SdlManager instance
     */
	public @Nullable
    VideoStreamManager getVideoStreamManager() {
		checkSdlManagerState();
		return videoStreamManager;
	}

    /**
     * Gets the AudioStreamManager. <br>
	 * The AudioStreamManager returned will only be not null if the registered app type is
	 * either NAVIGATION or PROJECTION. Once the AudioStreamManager is retrieved, its start()
	 * method will need to be called before use.
     * <br><strong>Note: AudioStreamManager should be used only after SdlManager.start() CompletionListener callback is completed successfully.</strong>
     * @return a AudioStreamManager object
     */
	public @Nullable AudioStreamManager getAudioStreamManager() {
		checkSdlManagerState();
		return audioStreamManager;
	}

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
	public LockScreenManager getLockScreenManager() {
		if (lockScreenManager.getState() != BaseSubManager.READY && lockScreenManager.getState() != BaseSubManager.LIMITED){
			Log.e(TAG, "LockScreenManager should not be accessed because it is not in READY/LIMITED state");
		}
		checkSdlManagerState();
		return lockScreenManager;
	}

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
	@Override
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
	@Override
	public OnHMIStatus getCurrentHMIStatus(){
		if(this.proxy !=null ){
			return proxy.getCurrentHMIStatus();
		}
		return null;
	}

	/**
	 * Retrieves the auth token, if any, that was attached to the StartServiceACK for the RPC
	 * service from the module. For example, this should be used to login to a user account.
	 * @return the string representation of the auth token
	 */
	@Override
	public String getAuthToken(){
		return this.proxy.getAuthToken();
	}

	// PROTECTED GETTERS

	protected LockScreenConfig getLockScreenConfig() { return lockScreenConfig; }

	// SENDING REQUESTS

	/**
	 * Send RPC Message
	 * @param message RPCMessage
	 */
	@Override
	public void sendRPC(RPCMessage message) {
		try{
			proxy.sendRPC(message);
		}catch (SdlException exception){
			handleSdlException(exception);
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
	@Override
	public void sendSequentialRPCs(final List<? extends RPCMessage> rpcs, final OnMultipleRequestListener listener){

		List<RPCRequest> rpcRequestList = new ArrayList<>();
		for (int i = 0; i < rpcs.size(); i++) {
			if (rpcs.get(i) instanceof RPCRequest){
				rpcRequestList.add((RPCRequest)rpcs.get(i));
			}
		}

		if (rpcRequestList.size() > 0) {
			try{
				proxy.sendSequentialRequests(rpcRequestList, listener);
			}catch (SdlException exception){
				handleSdlException(exception);
			}
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
	@Override
	public void sendRPCs(List<? extends RPCMessage> rpcs, final OnMultipleRequestListener listener) {

		List<RPCRequest> rpcRequestList = new ArrayList<>();
		for (int i = 0; i < rpcs.size(); i++) {
			if (rpcs.get(i) instanceof RPCRequest){
				rpcRequestList.add((RPCRequest)rpcs.get(i));
			}
		}

		if (rpcRequestList.size() > 0) {
			try{
				proxy.sendRequests(rpcRequestList, listener);
			}catch (SdlException exception){
				handleSdlException(exception);
			}
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
	@Override
	public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener){
		proxy.addOnRPCNotificationListener(notificationId,listener);
	}

	/**
	 * Remove an OnRPCNotificationListener
	 * @param listener listener that was previously added
	 */
	@Override
	public void removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener){
		proxy.removeOnRPCNotificationListener(notificationId, listener);
	}

	/**
	 * Add an OnRPCRequestListener
	 * @param listener listener that will be called when a request is received
	 */
	@Override
	public void addOnRPCRequestListener(FunctionID requestId, OnRPCRequestListener listener){
		proxy.addOnRPCRequestListener(requestId,listener);
	}

	/**
	 * Remove an OnRPCRequestListener
	 * @param listener listener that was previously added
	 */
	@Override
	public void removeOnRPCRequestListener(FunctionID requestId, OnRPCRequestListener listener){
		proxy.removeOnRPCRequestListener(requestId, listener);
	}

	// LIFECYCLE / OTHER

	// STARTUP

	/**
	 * Starts up a SdlManager, and calls provided callback called once all BaseSubManagers are done setting up
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void start(){
		if (proxy == null) {
			try {
				if(transport!= null  && transport.getTransportType() == TransportType.MULTIPLEX){
					//Do the thing
					MultiplexTransportConfig multiplexTransportConfig = (MultiplexTransportConfig)(transport);
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

					//If the requires audio support has not been set, it should be set to true if the
					//app is a media app, and false otherwise
					if(multiplexTransportConfig.requiresAudioSupport() == null){
						multiplexTransportConfig.setRequiresAudioSupport(isMediaApp);
					}
				}

				proxy = new SdlProxyBase(proxyBridge, context, appName, shortAppName, isMediaApp, language,
						hmiLanguage, hmiTypes, appId, transport, vrSynonyms, ttsChunks, dayColorScheme,
						nightColorScheme) {};
				proxy.setMinimumProtocolVersion(minimumProtocolVersion);
				proxy.setMinimumRPCVersion(minimumRPCVersion);
				if (sdlSecList != null && !sdlSecList.isEmpty()) {
					proxy.setSdlSecurity(sdlSecList, serviceEncryptionListener);
				}
				//Setup the notification queue
				initNotificationQueue();

			} catch (SdlException e) {
				transitionToState(BaseSubManager.ERROR);
				if (managerListener != null) {
					managerListener.onError("Unable to start manager", e);
				}
			}
		}
	}

	protected void setProxy(SdlProxyBase proxy){
		this.proxy = proxy;
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
				proxy.startVideoService(encrypted,parameters);
			}
		}

		@Override
		public IVideoStreamListener startVideoStream(boolean isEncrypted, VideoStreamingParameters parameters){
			if(proxy.getIsConnected()){
				return proxy.startVideoStream(isEncrypted, parameters);
			}else{
				DebugTool.logError("Unable to start video stream, proxy not connected");
				return null;
			}
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
				proxy.sendRPC(message);
			} catch (SdlException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void sendRPC(RPCMessage message) {
			try {
				proxy.sendRPC(message);
			} catch (SdlException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void sendRequests(List<? extends RPCRequest> rpcs, OnMultipleRequestListener listener) {
			try {
				proxy.sendRequests(rpcs, listener);
			} catch (SdlException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void sendSequentialRPCs(List<? extends RPCMessage> rpcs, OnMultipleRequestListener listener) {
			try {
				proxy.sendSequentialRequests(rpcs,listener);
			} catch (SdlException e) {
				DebugTool.logError("Issue sending sequential RPCs ", e);
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
		public void addOnRPCRequestListener(FunctionID functionID, OnRPCRequestListener listener) {
			proxy.addOnRPCRequestListener(functionID, listener);
		}

		@Override
		public boolean removeOnRPCRequestListener(FunctionID functionID, OnRPCRequestListener listener) {
			return proxy.removeOnRPCRequestListener(functionID, listener);
		}

		@Override
		public void addOnRPCListener(final FunctionID responseId, final OnRPCListener listener) {
			proxyBridge.addRpcListener(responseId, listener);
		}

		@Override
		public boolean removeOnRPCListener(final FunctionID responseId, final OnRPCListener listener) {
			return proxyBridge.removeOnRPCListener(responseId, listener);
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
		public void addOnSystemCapabilityListener(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener listener) {
			proxy.addOnSystemCapabilityListener(systemCapabilityType, listener);
		}

		@Override
		public boolean removeOnSystemCapabilityListener(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener listener) {
			return proxy.removeOnSystemCapabilityListener(systemCapabilityType, listener);
		}

		@Override
		public boolean isTransportForServiceAvailable(SessionType serviceType) {
			if(SessionType.NAV.equals(serviceType)){
				return proxy.isVideoStreamTransportAvailable();
			}else if(SessionType.PCM.equals(serviceType)){
				return proxy.isAudioStreamTransportAvailable();
			}
			return false;
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

		@Override
		public @NonNull Version getProtocolVersion() {
			if(proxy.getProtocolVersion() != null){
				return proxy.getProtocolVersion();
			}else{
				return new Version(1,0,0);
			}
		}

		@Override
		public void startRPCEncryption() {
			if (proxy != null) {
				proxy.startProtectedRPCService();
			}
		}

	};


	// BUILDER
	public static class Builder {
		SdlManager sdlManager;

		/**
		 * Builder for the SdlManager. Parameters in the constructor are required.
		 * @param context the current context
		 * @param appId the app's ID
		 * @param appName the app's name
		 * @param listener a SdlManagerListener object
		 */
		public Builder(@NonNull Context context, @NonNull final String appId, @NonNull final String appName, @NonNull final SdlManagerListener listener){
			sdlManager = new SdlManager();
			setContext(context);
			setAppId(appId);
			setAppName(appName);
			setManagerListener(listener);
		}
		/**
		 * Builder for the SdlManager. Parameters in the constructor are required.
		 * @param context the current context
		 * @param appId the app's ID
		 * @param appName the app's name
		 * @param listener a SdlManagerListener object
		 */
		public Builder(@NonNull Context context, @NonNull final String appId, @NonNull final String appName, @NonNull BaseTransportConfig transport, @NonNull final SdlManagerListener listener){
			sdlManager = new SdlManager();
			setContext(context);
			setAppId(appId);
			setAppName(appName);
			setTransportType(transport);
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
		 * Sets the minimum protocol version that will be permitted to connect.
		 * If the protocol version of the head unit connected is below this version,
		 * the app will disconnect with an EndService protocol message and will not register.
		 * @param minimumProtocolVersion the minimum Protocol spec version that should be accepted
		 */
		public Builder setMinimumProtocolVersion(final Version minimumProtocolVersion) {
			sdlManager.minimumProtocolVersion = minimumProtocolVersion;
			return this;
		}

		/**
		 * The minimum RPC version that will be permitted to connect.
		 * If the RPC version of the head unit connected is below this version, an UnregisterAppInterface will be sent.
		 * @param minimumRPCVersion the minimum RPC spec version that should be accepted
		 */
		public Builder setMinimumRPCVersion(final Version minimumRPCVersion) {
			sdlManager.minimumRPCVersion = minimumRPCVersion;
			return this;
		}

		/**
		 * Sets the VR+TTS Language of the App
		 * @param language the desired language to be used on the VR+TTS of the connected module
		 */
		public Builder setLanguage(final Language language){
			sdlManager.language = language;
			return this;
		}

		/**
		 * Sets the display/HMI Language of the App
		 * @param hmiLanguage the desired language to be used on the display/HMI of the connected module
		 */
		public Builder setHMILanguage(final Language hmiLanguage){
			sdlManager.hmiLanguage = hmiLanguage;
			return this;
		}

		/**
		 * Sets the TemplateColorScheme for daytime
		 * @param dayColorScheme color scheme that will be used (if supported) when the display is
		 *                       in a "Day Mode" or similar. Should comprise of colors that contrast
		 *                       well during the day under sunlight.
		 */
		public Builder setDayColorScheme(final TemplateColorScheme dayColorScheme){
			sdlManager.dayColorScheme = dayColorScheme;
			return this;
		}

		/**
		 * Sets the TemplateColorScheme for nighttime
		 * @param nightColorScheme color scheme that will be used (if supported) when the display is
		 *                         in a "Night Mode" or similar. Should comprise of colors that
		 *                         contrast well during the night and are not brighter than average.
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
		public Builder setLockScreenConfig (final LockScreenConfig lockScreenConfig){
			sdlManager.lockScreenConfig = lockScreenConfig;
			return this;
		}

		/**
		 * Sets the icon for the app on head unit / In-Vehicle-Infotainment system <br>
		 * @param sdlArtwork the icon that will be used to represent this application on the
		 *                         connected module
		 */
		public Builder setAppIcon(final SdlArtwork sdlArtwork){
			sdlManager.appIcon = sdlArtwork;
			return this;
		}

		/**
		 * Sets the vector of AppHMIType <br>
		 * <strong>Note: This should be an ordered list from most -> least relevant</strong>
		 * @param hmiTypes HMI types that represent this application. For example, if the app is a
		 *                 music player, the MEDIA HMIType should be included.
		 */
		public Builder setAppTypes(final Vector<AppHMIType> hmiTypes){

			sdlManager.hmiTypes = hmiTypes;

			if (hmiTypes != null) {
				sdlManager.isMediaApp = hmiTypes.contains(AppHMIType.MEDIA);
			}

			return this;
		}

		/**
		 * Sets the voice recognition synonyms that can be used to identify this application.
		 * @param vrSynonyms a vector of Strings that can be associated with this app. For example the app's name should
		 *                   be included as well as any phonetic spellings of the app name that might help the on-board
		 *                   VR system associated a users spoken word with the supplied synonyms.
		 */
		public Builder setVrSynonyms(final Vector<String> vrSynonyms) {
			sdlManager.vrSynonyms = vrSynonyms;
			return this;
		}

		/**
		 * Sets the Text-To-Speech Name of the application. These TTSChunks might be used by the module as an audio
		 * representation of the app's name.
		 * @param ttsChunks the TTS chunks that can represent this app's name
		 */
		public Builder setTtsName(final Vector<TTSChunk> ttsChunks) {
			sdlManager.ttsChunks = ttsChunks;
			return this;
		}

		/**
		 * This Object type may change with the transport refactor
		 * Sets the BaseTransportConfig
		 * @param transport the type of transport that should be used for this SdlManager instance.
		 */
		public Builder setTransportType(@NonNull BaseTransportConfig transport){
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

		/**
		 * Sets the Security library
		 * @param secList The list of security class(es)
		 */
		@Deprecated
		public Builder setSdlSecurity(List<Class<? extends SdlSecurityBase>> secList) {
			sdlManager.sdlSecList = secList;
			return this;
		}

		/**
		 * Sets the security libraries and a callback to notify caller when there is update to encryption service
		 * @param secList The list of security class(es)
		 * @param listener The callback object
		 */
		public Builder setSdlSecurity(@NonNull List<Class<? extends SdlSecurityBase>> secList, ServiceEncryptionListener listener) {
			sdlManager.sdlSecList = secList;
			sdlManager.serviceEncryptionListener = listener;
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

		/**
		 * Build SdlManager ang get it ready to be started
		 * <strong>Note: new instance of SdlManager should be created on every connection. SdlManager cannot be reused after getting disposed.</strong>
		 * @return SdlManager instance that is ready to be started
		 */
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

			if (sdlManager.transport == null) {
				throw new IllegalArgumentException("You must set a transport type object");
			}

			if (sdlManager.hmiTypes == null) {
				Vector<AppHMIType> hmiTypesDefault = new Vector<>();
				hmiTypesDefault.add(AppHMIType.DEFAULT);
				sdlManager.hmiTypes = hmiTypesDefault;
				sdlManager.isMediaApp = false;
			}

			if (sdlManager.lockScreenConfig == null){
				// if lock screen params are not set, use default
				sdlManager.lockScreenConfig = new LockScreenConfig();
			}

			if (sdlManager.hmiLanguage == null && sdlManager.language == null){
				sdlManager.hmiLanguage = Language.EN_US;
				sdlManager.language = Language.EN_US;
			} else if (sdlManager.hmiLanguage == null && sdlManager.language != null) {
				sdlManager.hmiLanguage = sdlManager.language;
			} else if (sdlManager.hmiLanguage != null && sdlManager.language == null) {
				sdlManager.language = sdlManager.hmiLanguage;
			} else {
				// do nothing when language and hmiLanguage are set
			}

			if (sdlManager.minimumProtocolVersion == null){
				sdlManager.minimumProtocolVersion = new Version("1.0.0");
			}

			if (sdlManager.minimumRPCVersion == null){
				sdlManager.minimumRPCVersion = new Version("1.0.0");
			}

			sdlManager.transitionToState(BaseSubManager.SETTING_UP);

			return sdlManager;
		}
	}

	/**
	 * Start a secured RPC service
	 */
	public void startRPCEncryption() {
		if (proxy != null) {
			 proxy.startProtectedRPCService();
		}
	}
}
