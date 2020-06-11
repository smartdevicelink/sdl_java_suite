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

import android.support.annotation.NonNull;
import com.smartdevicelink.util.Log;

import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.FileManagerConfig;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.lifecycle.LifecycleConfigurationUpdate;
import com.smartdevicelink.managers.lifecycle.LifecycleManager;
import com.smartdevicelink.managers.permission.PermissionManager;
import com.smartdevicelink.managers.screen.ScreenManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.SystemCapabilityManager;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.ChangeRegistration;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.SetAppIcon;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.TemplateColorScheme;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;
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

	private SdlArtwork appIcon;
	private SdlManagerListener managerListener;
	private List<Class<? extends SdlSecurityBase>> sdlSecList;
	private ServiceEncryptionListener serviceEncryptionListener;
	private FileManagerConfig fileManagerConfig;

	// Managers
	private LifecycleManager lifecycleManager;
	private PermissionManager permissionManager;
	private FileManager fileManager;
    private ScreenManager screenManager;


	// INTERNAL INTERFACE
	/**
	 * This is from the LifeCycleManager directly. In the future if there is a reason to be a man in the middle
	 * the SdlManager could create it's own, however right now it was only a duplication of logic tied to the LCM.
	 */
	private ISdl _internalInterface;


	// Initialize proxyBridge with anonymous lifecycleListener
	private final LifecycleManager.LifecycleListener lifecycleListener = new LifecycleManager.LifecycleListener() {
		boolean initStarted = false;
		@Override
		public void onProxyConnected(LifecycleManager lifeCycleManager) {
			Log.i(TAG,"Proxy is connected. Now initializing.");
			synchronized (this){
				if(!initStarted){
					changeRegistrationRetry = 0;
					checkLifecycleConfiguration();
					initialize();
					initStarted = true;
				}
			}
		}
		@Override
		public void onServiceStarted(SessionType sessionType){

		}

		@Override
		public void onServiceEnded(SessionType sessionType){

		}

		@Override
		public void onProxyClosed(LifecycleManager lifeCycleManager, String info, Exception e, SdlDisconnectedReason reason) {
			Log.i(TAG,"Proxy is closed.");
			if(managerListener != null){
				managerListener.onDestroy(SdlManager.this);
			}

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

	@Override
	void checkState() {
		if (permissionManager != null && fileManager != null && screenManager != null ){
			if (permissionManager.getState() == BaseSubManager.READY && fileManager.getState() == BaseSubManager.READY && screenManager.getState() == BaseSubManager.READY){
				DebugTool.logInfo("Starting sdl manager, all sub managers are in ready state");
				transitionToState(BaseSubManager.READY);
				handleQueuedNotifications();
				notifyDevListener(null);
				onReady();
			} else if (permissionManager.getState() == BaseSubManager.ERROR && fileManager.getState() == BaseSubManager.ERROR && screenManager.getState() == BaseSubManager.ERROR){
				String info = "ERROR starting sdl manager, all sub managers are in error state";
				Log.e(TAG, info);
				transitionToState(BaseSubManager.ERROR);
				notifyDevListener(info);
			} else if (permissionManager.getState() == BaseSubManager.SETTING_UP || fileManager.getState() == BaseSubManager.SETTING_UP || screenManager.getState() == BaseSubManager.SETTING_UP) {
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
				managerListener.onError(this, info, null);
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

	@Override
	protected void checkLifecycleConfiguration(){
		final Language actualLanguage =  this.getRegisterAppInterfaceResponse().getLanguage();
		final Language actualHMILanguage =  this.getRegisterAppInterfaceResponse().getHmiDisplayLanguage();

		if ((actualLanguage != null && !actualLanguage.equals(language)) || (actualHMILanguage != null && !actualHMILanguage.equals(hmiLanguage))) {

			LifecycleConfigurationUpdate lcuNew = managerListener.managerShouldUpdateLifecycle(actualLanguage, actualHMILanguage);
			LifecycleConfigurationUpdate lcuOld = managerListener.managerShouldUpdateLifecycle(actualLanguage);
			final LifecycleConfigurationUpdate lcu;
			ChangeRegistration changeRegistration;
			if (lcuNew == null) {
				lcu = lcuOld;
				changeRegistration = new ChangeRegistration(actualLanguage, actualLanguage);
			} else {
				lcu = lcuNew;
				changeRegistration = new ChangeRegistration(actualLanguage, actualHMILanguage);
			}

			if (lcu != null) {
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
							Log.v(TAG, response.serializeJSON().toString());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onError(int correlationId, Result resultCode, String info) {
						Log.e(TAG, "Change Registration onError: " + resultCode + " | Info: " + info);
					}
				});
				_internalInterface.sendRPC(changeRegistration);
			}
		}
	}

	@Override
	protected void initialize(){
		// Instantiate sub managers
		this.permissionManager = new PermissionManager(_internalInterface);
		this.fileManager = new FileManager(_internalInterface, fileManagerConfig);
		this.screenManager = new ScreenManager(_internalInterface, this.fileManager);

		// Start sub managers
		this.permissionManager.start(subManagerListener);
		this.fileManager.start(subManagerListener);
		this.screenManager.start(subManagerListener);
	}

	@Override
	public void dispose() {
		if (this.permissionManager != null) {
			this.permissionManager.dispose();
		}

		if (this.fileManager != null) {
			this.fileManager.dispose();
		}

		if (this.screenManager != null) {
			this.screenManager.dispose();
		}

		if (this.lifecycleManager != null) {
			this.lifecycleManager.stop();
		}

		if(managerListener != null){
			managerListener.onDestroy(this);
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
	 * Gets the SystemCapabilityManager. <br>
	 * <strong>Note: SystemCapabilityManager should be used only after SdlManager.start() CompletionListener callback is completed successfully.</strong>
	 * @return a SystemCapabilityManager object
	 */
	public SystemCapabilityManager getSystemCapabilityManager(){
		return lifecycleManager.getSystemCapabilityManager(this);
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
		if(lifecycleManager != null){
			return lifecycleManager.getRegisterAppInterfaceResponse();
		}
		return null;
	}

	/**
	 * Get the current OnHMIStatus
	 * @return OnHMIStatus object represents the current OnHMIStatus
	 */
	@Override
	public OnHMIStatus getCurrentHMIStatus(){
		if(this.lifecycleManager !=null ){
			return lifecycleManager.getCurrentHMIStatus();
		}
		return null;
	}

	// PROTECTED GETTERS

	protected FileManagerConfig getFileManagerConfig() { return fileManagerConfig; }

	/**
	 * Retrieves the auth token, if any, that was attached to the StartServiceACK for the RPC
	 * service from the module. For example, this should be used to login to a user account.
	 * @return the string representation of the auth token
	 */
	@Override
	public String getAuthToken(){
		return this.lifecycleManager.getAuthToken();
	}

	// SENDING REQUESTS

	/**
	 * Send RPC Message <br>
	 * @param message RPCMessage
	 */
	@Override
	public void sendRPC(RPCMessage message) {
		_internalInterface.sendRPC(message);
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
			_internalInterface.sendSequentialRPCs(rpcRequestList, listener);
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
			_internalInterface.sendRequests(rpcRequestList,listener);
		}
	}

	/**
	 * Add an OnRPCNotificationListener
	 * @param listener listener that will be called when a notification is received
	 */
	@Override
	public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener){
		_internalInterface.addOnRPCNotificationListener(notificationId,listener);
	}

	/**
	 * Remove an OnRPCNotificationListener
	 * @param listener listener that was previously added
	 */
	@Override
	public void removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener){
		_internalInterface.removeOnRPCNotificationListener(notificationId, listener);
	}

	/**
	 * Add an OnRPCRequestListener
	 * @param listener listener that will be called when a request is received
	 */
	@Override
	public void addOnRPCRequestListener(FunctionID requestId, OnRPCRequestListener listener){
		_internalInterface.addOnRPCRequestListener(requestId,listener);
	}

	/**
	 * Remove an OnRPCRequestListener
	 * @param listener listener that was previously added
	 */
	@Override
	public void removeOnRPCRequestListener(FunctionID requestId, OnRPCRequestListener listener){
		_internalInterface.removeOnRPCRequestListener(requestId, listener);
	}

	// LIFECYCLE / OTHER

	// STARTUP

	/**
	 * Starts up a SdlManager, and calls provided callback called once all BaseSubManagers are done setting up
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void start(){

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				dispose();
			}
		});

		Log.i(TAG, "start");
		if (lifecycleManager == null) {
			if (transport != null
					&& (transport.getTransportType().equals(TransportType.WEB_SOCKET_SERVER) || transport.getTransportType().equals(TransportType.CUSTOM))) {
				//Do the thing

				LifecycleManager.AppConfig appConfig = new LifecycleManager.AppConfig();
				appConfig.setAppName(appName);
				//short app name
				appConfig.setMediaApp(isMediaApp);
				appConfig.setHmiDisplayLanguageDesired(hmiLanguage);
				appConfig.setLanguageDesired(hmiLanguage);
				appConfig.setAppType(hmiTypes);
				appConfig.setVrSynonyms(vrSynonyms);
				appConfig.setTtsName(ttsChunks);
				appConfig.setDayColorScheme(dayColorScheme);
				appConfig.setNightColorScheme(nightColorScheme);
				appConfig.setAppID(appId);
				appConfig.setMinimumProtocolVersion(minimumProtocolVersion);
				appConfig.setMinimumRPCVersion(minimumRPCVersion);

				lifecycleManager = new LifecycleManager(appConfig, transport, lifecycleListener);
				_internalInterface = lifecycleManager.getInternalInterface(SdlManager.this);

				if (sdlSecList != null && !sdlSecList.isEmpty()) {
					lifecycleManager.setSdlSecurity(sdlSecList, serviceEncryptionListener);
				}

				//Setup the notification queue
				initNotificationQueue();

				lifecycleManager.start();


			}else{
				throw new RuntimeException("No transport provided");
			}
		}
	}


	// BUILDER
	public static class Builder {
		SdlManager sdlManager;

		/**
		 * Builder for the SdlManager. Parameters in the constructor are required.
		 * @param appId the app's ID
		 * @param appName the app's name
		 * @param listener a SdlManagerListener object
		 */
		public Builder(@NonNull final String appId, @NonNull final String appName, @NonNull final SdlManagerListener listener){
			sdlManager = new SdlManager();
			setAppId(appId);
			setAppName(appName);
			setManagerListener(listener);
		}

		/**
		 * Sets the App ID
		 * @param appId String representation of the App ID retreived from the SDL Developer Portal
		 */
		public Builder setAppId(@NonNull final String appId){
			sdlManager.appId = appId;
			return this;
		}

		/**
		 * Sets the Application Name
		 * @param appName String that will be associated as the app's name
		 */
		public Builder setAppName(@NonNull final String appName){
			sdlManager.appName = appName;
			return this;
		}

		/**
		 * Sets the Short Application Name
		 * @param shortAppName a shorter representation of the app's name for smaller displays
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
		 * Sets the Language of the App
		 * @param hmiLanguage the desired language to be used on the display/HMI of the connected module
		 */
		public Builder setLanguage(final Language hmiLanguage) {
			sdlManager.hmiLanguage = hmiLanguage;
			sdlManager.language = hmiLanguage;
			return this;
		}

		/**
		 * Sets the TemplateColorScheme for daytime
		 * @param dayColorScheme color scheme that will be used (if supported) when the display is in a "Day Mode" or
		 *                       similar. Should comprise of colors that contrast well during the day under sunlight.
		 */
		public Builder setDayColorScheme(final TemplateColorScheme dayColorScheme){
			sdlManager.dayColorScheme = dayColorScheme;
			return this;
		}

		/**
		 * Sets the TemplateColorScheme for nighttime
		 * @param nightColorScheme color scheme that will be used (if supported) when the display is in a "Night Mode"
		 *                         or similar. Should comprise of colors that contrast well during the night and are not
		 *                         brighter than average.
		 */
		public Builder setNightColorScheme(final TemplateColorScheme nightColorScheme){
			sdlManager.nightColorScheme = nightColorScheme;
			return this;
		}

		/**
		 * Sets the icon for the app on head unit / In-Vehicle-Infotainment system <br>
		 * @param sdlArtwork the icon that will be used to represent this application on the connected module
		 */
		public Builder setAppIcon(final SdlArtwork sdlArtwork){
			sdlManager.appIcon = sdlArtwork;
			return this;
		}

		/**
		 * Sets the vector of AppHMIType <br>
		 * <strong>Note: This should be an ordered list from most -> least relevant</strong>
		 * @param hmiTypes HMI types that represent this application. For example, if the app is a music player, the
		 *                 MEDIA HMIType should be included.
		 */
		public Builder setAppTypes(final Vector<AppHMIType> hmiTypes){

			sdlManager.hmiTypes = hmiTypes;

			if (hmiTypes != null) {
				sdlManager.isMediaApp = hmiTypes.contains(AppHMIType.MEDIA);
			}

			return this;
		}

		/**
		 * Sets the FileManagerConfig for the session.<br>
		 * <strong>Note: If not set, the default configuration value of 1 will be set for
		 * artworkRetryCount and fileRetryCount in FileManagerConfig</strong>
		 * @param fileManagerConfig - configuration options
		 */
		public Builder setFileManagerConfig (final FileManagerConfig fileManagerConfig){
			sdlManager.fileManagerConfig = fileManagerConfig;
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
		public Builder setTransportType(BaseTransportConfig transport){
			sdlManager.transport = transport;
			return this;
		}

		/**
		 * Sets the Security libraries
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
			if(sdlManager.fileManagerConfig == null){
				//if FileManagerConfig is not set use default
				sdlManager.fileManagerConfig = new FileManagerConfig();
			}

			if (sdlManager.hmiLanguage == null) {
				sdlManager.hmiLanguage = Language.EN_US;
				sdlManager.language = Language.EN_US;
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
		if (lifecycleManager != null) {
			lifecycleManager.startRPCEncryption();
		}
	}
}
