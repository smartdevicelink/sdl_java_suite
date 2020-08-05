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

package com.smartdevicelink.managers.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.InputDevice;
import android.view.MotionEvent;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.encoder.VirtualDisplayEncoder;
import com.smartdevicelink.haptic.HapticInterfaceManager;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.StreamingStateMachine;
import com.smartdevicelink.managers.video.resolution.AspectRatio;
import com.smartdevicelink.managers.video.resolution.Resolution;
import com.smartdevicelink.managers.video.resolution.VideoStreamingRange;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.AppCapability;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.OnAppCapabilityUpdated;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.proxy.rpc.VideoStreamingCapability;
import com.smartdevicelink.proxy.rpc.enums.AppCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.TouchType;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingState;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.streaming.video.SdlRemoteDisplay;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.FutureTask;

@TargetApi(19)
public class VideoStreamManager extends BaseVideoStreamManager {
	private static String TAG = "VideoStreamManager";

	private WeakReference<Context> context;
	private volatile VirtualDisplayEncoder virtualDisplayEncoder;
	private Class<? extends SdlRemoteDisplay> remoteDisplayClass = null;
	private SdlRemoteDisplay sdlRemoteDisplay;
	private float[] touchScalar = {1.0f,1.0f}; //x, y
	private HapticInterfaceManager hapticManager;
	private SdlMotionEvent sdlMotionEvent = null;
	private OnHMIStatus currentOnHMIStatus;
	private StreamingStateMachine stateMachine;
	private VideoStreamingParameters parameters;
	private VideoStreamingCapability originalCapability;
	private IVideoStreamListener streamListener;
	private boolean isTransportAvailable = false;
	private Integer majorProtocolVersion;
	private VideoStreamingRange streamingRange;
	private boolean hasStarted;
	private String vehicleMake = null;
	private boolean isEncrypted = false;
	private boolean withPendingRestart = false;

	// INTERNAL INTERFACES

	private final ISdlServiceListener serviceListener = new ISdlServiceListener() {
		@Override
		public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted) {
			if(SessionType.NAV.equals(type)){
				if (session != null && session.getAcceptedVideoParams() != null) {
					parameters = session.getAcceptedVideoParams();
					VideoStreamManager.this.streamListener = session.startVideoStream();
				}

				if (VideoStreamManager.this.streamListener == null) {
					DebugTool.logError(TAG, "Error starting video stream");
					stateMachine.transitionToState(StreamingStateMachine.ERROR);
					return;
				}
				VideoStreamingCapability capability = (VideoStreamingCapability) internalInterface.getCapability(SystemCapabilityType.VIDEO_STREAMING);
				if(capability != null && Boolean.TRUE.equals(capability.getIsHapticSpatialDataSupported())){
					hapticManager = new HapticInterfaceManager(internalInterface);
				}
				checkState();
				startEncoder();
				stateMachine.transitionToState(StreamingStateMachine.STARTED);
				hasStarted = true;
			}
		}

		@Override
		public void onServiceEnded(SdlSession session, SessionType type) {
			if(SessionType.NAV.equals(type)){
				if(sdlRemoteDisplay !=null){
				    // TODO set withPendingRestart to default in proper place
					stopStreaming(withPendingRestart);
				}
				stateMachine.transitionToState(StreamingStateMachine.NONE);
				transitionToState(SETTING_UP);

				if (withPendingRestart){
					VideoStreamManager manager = VideoStreamManager.this;
					manager.internalInterface.startVideoService(
							manager.getLastCachedStreamingParameters(),
							manager.isEncrypted
					);
				}
			}
		}

		@Override
		public void onServiceError(SdlSession session, SessionType type, String reason) {
			DebugTool.logError(TAG, "Unable to start video service: " + reason);
			stateMachine.transitionToState(StreamingStateMachine.ERROR);
			transitionToState(BaseSubManager.ERROR);
		}
	};

	private final OnRPCNotificationListener hmiListener = new OnRPCNotificationListener() {
		@Override
		public void onNotified(RPCNotification notification) {
			if(notification != null){
				OnHMIStatus onHMIStatus = (OnHMIStatus) notification;
				if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
					return;
				}
				OnHMIStatus prevOnHMIStatus = currentOnHMIStatus;
				currentOnHMIStatus = onHMIStatus;

				if (hasStarted && (isHMIStateVideoStreamCapable(prevOnHMIStatus)) && (!isHMIStateVideoStreamCapable(currentOnHMIStatus))) {
					internalInterface.stopVideoService();
				}
			}
		}
	};

	private final OnRPCNotificationListener touchListener = new OnRPCNotificationListener() {
		@Override
		public void onNotified(RPCNotification notification) {
			if(notification != null && sdlRemoteDisplay != null){
				List<MotionEvent> motionEventList = convertTouchEvent((OnTouchEvent)notification);
				if (motionEventList != null && !motionEventList.isEmpty()) {
					for (MotionEvent motionEvent : motionEventList) {
						sdlRemoteDisplay.handleMotionEvent(motionEvent);
					}
				}
			}
		}
	};

	private final OnSystemCapabilityListener systemCapabilityListener = new OnSystemCapabilityListener() {
		@Override
		public void onCapabilityRetrieved(Object capability) {
			VideoStreamingParameters params = new VideoStreamingParameters();

			VideoStreamingCapability castedCapability = ((VideoStreamingCapability)capability);

			// means only scale received
			if (castedCapability.getPreferredResolution() == null &&
					castedCapability.getScale() != null &&
					castedCapability.getScale() != 0 &&
					VideoStreamManager.this.parameters != null
					&& VideoStreamManager.this.parameters.getResolution() != null) {
				// set cached resolution
				castedCapability.setPreferredResolution(originalCapability.getPreferredResolution());
			}
			params.update(castedCapability, vehicleMake);	//Streaming parameters are ready time to stream
			VideoStreamManager.this.parameters = params;

			VideoStreamManager.this.withPendingRestart = true;

			virtualDisplayEncoder.setStreamingParams(params);
			stopStreaming(true);
		}

		@Override
		public void onError(String info) {
			Log.d("MyTagLogInfo", info);
		}
	};

	// MANAGER APIs
	public VideoStreamManager(ISdl internalInterface){
		super(internalInterface);

		if(internalInterface != null && internalInterface.getRegisterAppInterfaceResponse() != null &&
				internalInterface.getRegisterAppInterfaceResponse().getVehicleType() != null) {
			vehicleMake = internalInterface.getRegisterAppInterfaceResponse().getVehicleType().getMake();
		}
		virtualDisplayEncoder = new VirtualDisplayEncoder();

		// Listen for video service events
		internalInterface.addServiceListener(SessionType.NAV, serviceListener);
		// Take care of the touch events
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_TOUCH_EVENT, touchListener);
		// Listen for HMILevel changes
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
		// Listen for SystemCapabilityType VIDEO_STREAMING
		internalInterface.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, systemCapabilityListener);
		stateMachine = new StreamingStateMachine();
	}

	@Override
	public void start(CompletionListener listener) {
		isTransportAvailable = internalInterface.isTransportForServiceAvailable(SessionType.NAV);
		checkState();
		super.start(listener);
	}

	private synchronized void checkState(){
		if(this.getState() == SETTING_UP
				&& isTransportAvailable
				&& isHMIStateVideoStreamCapable(currentOnHMIStatus)
				&& parameters != null){
			stateMachine.transitionToState(StreamingStateMachine.READY);
			transitionToState(READY);
		}
	}

	boolean isHMIStateVideoStreamCapable(OnHMIStatus onHMIStatus) {
		HMILevel hmiLevel = (onHMIStatus != null && onHMIStatus.getHmiLevel() != null) ? onHMIStatus.getHmiLevel() : HMILevel.HMI_NONE;
		VideoStreamingState videoStreamingState = (onHMIStatus != null && onHMIStatus.getVideoStreamingState() != null) ? onHMIStatus.getVideoStreamingState() : VideoStreamingState.STREAMABLE;
		return (hmiLevel.equals(HMILevel.HMI_FULL) || hmiLevel.equals(HMILevel.HMI_LIMITED)) && videoStreamingState.equals(VideoStreamingState.STREAMABLE);
	}

	private void getVideoStreamingParams(){
		if(internalInterface.getProtocolVersion().getMajor() >= 5) {
			internalInterface.getCapability(SystemCapabilityType.VIDEO_STREAMING, new OnSystemCapabilityListener() {
				@Override
				public void onCapabilityRetrieved(Object capability) {
					VideoStreamingParameters params = new VideoStreamingParameters();
					VideoStreamingCapability castedCapability = ((VideoStreamingCapability)capability);
					VideoStreamManager.this.originalCapability = castedCapability;
					params.update(castedCapability, vehicleMake);	//Streaming parameters are ready time to stream
					VideoStreamManager.this.parameters = params;
					// castedCapability.setAdditionalVideoStreamingCapabilities(getMockedAdditionalCapabilities());
					checkState();
				}

				@Override
				public void onError(String info) {
					DebugTool.logError(TAG, "Error retrieving video streaming capability: " + info);
					stateMachine.transitionToState(StreamingStateMachine.ERROR);
					transitionToState(ERROR);
				}
			});
		}else{
			//We just use default video streaming params
			VideoStreamingParameters params = new VideoStreamingParameters();
			DisplayCapabilities dispCap = (DisplayCapabilities)internalInterface.getCapability(SystemCapabilityType.DISPLAY);
			if(dispCap !=null){
				params.setResolution(dispCap.getScreenParams().getImageResolution());
			}

			this.parameters = params;
			checkState();
		}
	}

	/**
	 * Starts streaming a remote display to the module if there is a connected session. This method of streaming requires the device to be on API level 19 or higher
	 * @param context a context that can be used to create the remote display
	 * @param remoteDisplayClass class object of the remote display. This class will be used to create an instance of the remote display and will be projected to the module
	 * @param parameters streaming parameters to be used when streaming. If null is sent in, the default/optimized options will be used.
	 *                   If you are unsure about what parameters to be used it is best to just send null and let the system determine what
	 *                   works best for the currently connected module.
	 *
	 * @param encrypted a flag of if the stream should be encrypted. Only set if you have a supplied encryption library that the module can understand.
	 * @param streamingRange constraints for vehicle display : aspect ratio, min/max resolutions, max diagonal size.
	 */
	public void startRemoteDisplayStream(Context context, Class<? extends SdlRemoteDisplay> remoteDisplayClass, VideoStreamingParameters parameters, final boolean encrypted, VideoStreamingRange streamingRange) {
		configureGlobalParameters(context, remoteDisplayClass, isEncrypted, streamingRange);
		if(majorProtocolVersion >= 5 && !internalInterface.isCapabilitySupported(SystemCapabilityType.VIDEO_STREAMING)){
			stateMachine.transitionToState(StreamingStateMachine.ERROR);
			return;
		}
		if (!HMILevel.HMI_NONE.equals(currentOnHMIStatus.getHmiLevel()) && VideoStreamManager.this.parameters == null) {
			getVideoStreamingParams();
		}
		checkState();
		processCapabilitiesWithPendingStart(encrypted, parameters);
	}
	/**
	 * Starts streaming a remote display to the module if there is a connected session. This method of streaming requires the device to be on API level 19 or higher
	 * @param context a context that can be used to create the remote display
	 * @param remoteDisplayClass class object of the remote display. This class will be used to create an instance of the remote display and will be projected to the module
	 * @param parameters streaming parameters to be used when streaming. If null is sent in, the default/optimized options will be used.
	 *                   If you are unsure about what parameters to be used it is best to just send null and let the system determine what
	 *                   works best for the currently connected module.
	 *
	 * @param encrypted a flag of if the stream should be encrypted. Only set if you have a supplied encryption library that the module can understand.
	 */
	@Deprecated
	public void startRemoteDisplayStream(Context context, Class<? extends SdlRemoteDisplay> remoteDisplayClass, VideoStreamingParameters parameters, final boolean encrypted){
		configureGlobalParameters(context, remoteDisplayClass, isEncrypted);
		if(majorProtocolVersion >= 5 && !internalInterface.isCapabilitySupported(SystemCapabilityType.VIDEO_STREAMING)){
			DebugTool.logError(TAG, "Video streaming not supported on this module");
			stateMachine.transitionToState(StreamingStateMachine.ERROR);
			return;
		}
		if (!HMILevel.HMI_NONE.equals(currentOnHMIStatus.getHmiLevel()) && VideoStreamManager.this.parameters == null) {
			getVideoStreamingParams();
		}
		checkState();
		processCapabilitiesWithPendingStart(encrypted, parameters);
	}

	private void configureGlobalParameters(Context context, Class<? extends SdlRemoteDisplay> remoteDisplayClass, boolean encrypted) {
		this.context = new WeakReference<>(context);
		this.remoteDisplayClass = remoteDisplayClass;
		this.isEncrypted = encrypted;
		this.majorProtocolVersion = internalInterface.getProtocolVersion().getMajor();
	}

	private void configureGlobalParameters(Context context, Class<? extends SdlRemoteDisplay> remoteDisplayClass, boolean encrypted, VideoStreamingRange streamingRange) {
		this.context = new WeakReference<>(context);
		this.remoteDisplayClass = remoteDisplayClass;
		this.isEncrypted = encrypted;
		this.majorProtocolVersion = internalInterface.getProtocolVersion().getMajor();
		this.streamingRange = streamingRange;
	}

	private void processCapabilitiesWithPendingStart(boolean encrypted, VideoStreamingParameters parameters){
		if(parameters == null){
			if(majorProtocolVersion >= 5) {
				internalInterface.getCapability(SystemCapabilityType.VIDEO_STREAMING, new OnSystemCapabilityListener() {
					@Override
					public void onCapabilityRetrieved(Object capability) {
						VideoStreamingParameters params = new VideoStreamingParameters();
						VideoStreamingCapability castedCapability = ((VideoStreamingCapability)capability);
						VideoStreamManager.this.originalCapability = castedCapability;

						// Mocks data here
						// castedCapability.setAdditionalVideoStreamingCapabilities(getMockedAdditionalCapabilities());
						params.update(castedCapability, vehicleMake);	//Streaming parameters are ready time to stream
						VideoStreamManager.this.parameters = params;

						if (streamingRange != null) {
							// filtering
							castedCapability.setAdditionalVideoStreamingCapabilities(
									getSupportedCapabilities(
											streamingRange.getMinSupportedResolution(),
											streamingRange.getMaxSupportedResolution(),
											streamingRange.getMaxScreenDiagonal(),
											streamingRange.getAspectRatio(),
											castedCapability.getAdditionalVideoStreamingCapabilities()
									)
							);
						} else {
							// TODO handle??
						}

						OnAppCapabilityUpdated onAppCapabilityUpdated = new OnAppCapabilityUpdated(new AppCapability(castedCapability, AppCapabilityType.VIDEO_STREAMING));
						internalInterface.sendRPC(onAppCapabilityUpdated);
						startStreaming(params, isEncrypted);
					}

					@Override
					public void onError(String info) {
						stateMachine.transitionToState(StreamingStateMachine.ERROR);
						DebugTool.logError(TAG, "Error retrieving video streaming capability: " + info);
					}
				});
			}else{
				//We just use default video streaming params
				VideoStreamingParameters params = new VideoStreamingParameters();
				DisplayCapabilities dispCap = (DisplayCapabilities)internalInterface.getCapability(SystemCapabilityType.DISPLAY);
				if(dispCap !=null){
					params.setResolution(dispCap.getScreenParams().getImageResolution());
				}
				startStreaming(params, encrypted);
			}
		}else{
			startStreaming(parameters, encrypted);
		}
	}


	/**
	 * Starts video service, sets up encoder, haptic manager, and remote display. Begins streaming the remote display.
	 * @param parameters Video streaming parameters including: codec which will be used for streaming (currently, only
	 *                    VideoStreamingCodec.H264 is accepted), height and width of the video in pixels.
	 * @param encrypted Specify true if packets on this service have to be encrypted
	 */
	protected void startStreaming(VideoStreamingParameters parameters, boolean encrypted){
		this.parameters = parameters;
		if (!isHMIStateVideoStreamCapable(currentOnHMIStatus)) {
			DebugTool.logError(TAG, "Cannot start video service in the current HMI status");
			return;
		}
		//Start the video service
		this.internalInterface.startVideoService(parameters, encrypted);
	}

	/**
	 * Initializes and starts the virtual display encoder and creates the remote display
	 */
	private void startEncoder(){
		try {
			if (sdlRemoteDisplay != null) {
				sdlRemoteDisplay.resizeView(parameters.getResolution().getResolutionWidth(), parameters.getResolution().getResolutionHeight());
			}

			virtualDisplayEncoder.init(this.context.get(), streamListener, parameters);
			//We are all set so we can start streaming at at this point
			virtualDisplayEncoder.start();
			//Encoder should be up and running
			Display display = virtualDisplayEncoder.getDisplay();
			createRemoteDisplay(display);

			stateMachine.transitionToState(StreamingStateMachine.STARTED);
			hasStarted = true;
		} catch (Exception e) {
			stateMachine.transitionToState(StreamingStateMachine.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Stops streaming from the remote display. To restart, call
	 * @see #resumeStreaming()
	 */
	public void stopStreaming(boolean withPendingRestart){
		if(sdlRemoteDisplay!=null && !withPendingRestart){
			sdlRemoteDisplay.stop();
			this.withPendingRestart = false;
		}
		if (this.isStreaming()) {
			if(virtualDisplayEncoder!=null){
				virtualDisplayEncoder.shutDown(withPendingRestart);
			}
			stateMachine.transitionToState(StreamingStateMachine.PAUSED);

			this.internalInterface.stopVideoService();
		}
	}

	/**
	 * Resumes streaming after calling
	 * @see #startRemoteDisplayStream(android.content.Context, Class, com.smartdevicelink.streaming.video.VideoStreamingParameters, boolean)
	 * followed by a call to
	 * @see #stopStreaming(boolean withPendingRestart)
	 */
	public void resumeStreaming(){
		int currentState = stateMachine.getState();
		if (currentState == StreamingStateMachine.STOPPED || currentState == StreamingStateMachine.PAUSED) {
			startEncoder();
		}
	}

	/**
	 * Stops streaming, ends video streaming service and removes service listeners.
	 */
	@Override
	public void dispose(){
		stopStreaming(false);

		hapticManager = null;
		sdlRemoteDisplay = null;
		parameters = null;
		virtualDisplayEncoder = null;
		if (internalInterface != null) {
			internalInterface.stopVideoService();
			// Remove listeners
			internalInterface.removeServiceListener(SessionType.NAV, serviceListener);
			internalInterface.removeOnRPCNotificationListener(FunctionID.ON_TOUCH_EVENT, touchListener);
			internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
		}



		stateMachine.transitionToState(StreamingStateMachine.NONE);
		super.dispose();
	}

	// PUBLIC METHODS FOR CHECKING STATE

	/**
	 * Check if a video service is currently active
	 * @return boolean (true = active, false = inactive)
	 */
	public boolean isServiceActive(){
		return (stateMachine.getState() == StreamingStateMachine.READY) ||
				(stateMachine.getState() == StreamingStateMachine.STARTED) ||
				(stateMachine.getState() == StreamingStateMachine.STOPPED) ||
				(stateMachine.getState() == StreamingStateMachine.PAUSED);
	}

	/**
	 * Check if video is currently streaming and visible
	 * @return boolean (true = yes, false = no)
	 */
	public boolean isStreaming(){
		boolean state = (stateMachine.getState() == StreamingStateMachine.STARTED);
		boolean capable = isHMIStateVideoStreamCapable(currentOnHMIStatus);
		return state && capable;
	}

	/**
	 * Check if video streaming has been paused due to app moving to background or manually stopped
	 * @return boolean (true = not paused, false = paused)
	 */
	public boolean isPaused(){
		return (hasStarted && stateMachine.getState() == StreamingStateMachine.STOPPED) || (!isHMIStateVideoStreamCapable(currentOnHMIStatus));
	}

	/**
	 * Gets the current video streaming state as defined in @StreamingStateMachine
	 * @return int representing StreamingStateMachine.StreamingState
	 */
	public @StreamingStateMachine.StreamingState int currentVideoStreamState(){
		return stateMachine.getState();
	}

	// HELPER METHODS

	private void createRemoteDisplay(final Display disp){
		try{
			if (disp == null){
				return;
			}

			// Dismiss the current presentation if the display has changed.
			if (sdlRemoteDisplay != null && sdlRemoteDisplay.getDisplay() != disp) {
				sdlRemoteDisplay.dismissPresentation();
			}

			FutureTask<Boolean> fTask =  new FutureTask<Boolean>( new SdlRemoteDisplay.Creator(context.get(), disp, sdlRemoteDisplay, remoteDisplayClass, new SdlRemoteDisplay.Callback(){
				@Override
				public void onCreated(final SdlRemoteDisplay remoteDisplay) {
					//Remote display has been created.
					//Now is a good time to do parsing for spatial data
					VideoStreamManager.this.sdlRemoteDisplay = remoteDisplay;
					if(hapticManager != null) {
						remoteDisplay.getMainView().post(new Runnable() {
							@Override
							public void run() {
								hapticManager.refreshHapticData(remoteDisplay.getMainView());
							}
						});
					}
					//Get touch scalars
					ImageResolution resolution = null;
					if(internalInterface.getProtocolVersion().getMajor() >= 5){ //At this point we should already have the capability
						VideoStreamingCapability capability = (VideoStreamingCapability) internalInterface.getCapability(SystemCapabilityType.VIDEO_STREAMING);
						if(capability != null){
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
						createTouchScalar(resolution, displayMetrics);
                    }

					sdlRemoteDisplay.resizeView(parameters.getResolution().getResolutionWidth(), parameters.getResolution().getResolutionHeight());
				}

				@Override
				public void onInvalidated(final SdlRemoteDisplay remoteDisplay) {
					//Our view has been invalidated
					//A good time to refresh spatial data
					DisplayMetrics displayMetrics = new DisplayMetrics();
					sdlRemoteDisplay.getDisplay().getMetrics(displayMetrics);
					displayMetrics.widthPixels =  (int) (parameters.getResolution().getResolutionWidth() * parameters.getScale());
					displayMetrics.heightPixels =  (int) (parameters.getResolution().getResolutionHeight() * parameters.getScale());
					createTouchScalar(parameters.getResolution(), displayMetrics);
					if(hapticManager != null) {
						remoteDisplay.getMainView().post(new Runnable() {
							@Override
							public void run() {
								hapticManager.refreshHapticData(remoteDisplay.getMainView());
							}
						});
					}
				}
			} ));
			Thread showPresentation = new Thread(fTask);
			showPresentation.setName("RmtDispThread");

			showPresentation.start();
		} catch (Exception ex) {
			DebugTool.logError(TAG, "Unable to create Virtual Display.");
			if(DebugTool.isDebugEnabled()){
				ex.printStackTrace();
			}
		}
	}

	@Override
	protected void onTransportUpdate(List<TransportRecord> connectedTransports, boolean audioStreamTransportAvail, boolean videoStreamTransportAvail){

		isTransportAvailable = videoStreamTransportAvail;

		if(internalInterface.getProtocolVersion().isNewerThan(new Version(5,1,0)) >= 0){
			if(videoStreamTransportAvail){
				checkState();
			}
		}else{
			//The protocol version doesn't support simultaneous transports.
			if(!videoStreamTransportAvail){
				//If video streaming isn't available on primary transport then it is not possible to
				//use the video streaming manager until a complete register on a transport that
				//supports video
				transitionToState(ERROR);
			}
		}
	}

    void createTouchScalar(ImageResolution resolution, DisplayMetrics displayMetrics) {
        touchScalar[0] = ((float)displayMetrics.widthPixels) / resolution.getResolutionWidth();
        touchScalar[1] = ((float)displayMetrics.heightPixels) / resolution.getResolutionHeight();
    }

	List<MotionEvent> convertTouchEvent(OnTouchEvent onTouchEvent){
		List<MotionEvent> motionEventList = new ArrayList<MotionEvent>();

		List<TouchEvent> touchEventList = onTouchEvent.getEvent();
		if (touchEventList == null || touchEventList.size() == 0) return null;

		TouchType touchType = onTouchEvent.getType();
		if (touchType == null) { return null; }

		if(sdlMotionEvent == null) {
			if (touchType == TouchType.BEGIN) {
				sdlMotionEvent = new SdlMotionEvent();
			} else{
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
				pointer.setCoords(touchCoord.getX() / touchScalar[0], touchCoord.getY() / touchScalar[1]);
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

			if(motionEventAction == MotionEvent.ACTION_UP || motionEventAction == MotionEvent.ACTION_CANCEL){
				//If the motion event should be finished we should clear our reference
				sdlMotionEvent.pointers.clear();
				sdlMotionEvent = null;
				break;
			} else if((motionEventAction & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP){
				sdlMotionEvent.removePointerById(touchEvent.getId());
			}
		}

		return motionEventList;
	}

	public VideoStreamingParameters getLastCachedStreamingParameters() {
		return parameters;
	}

	public boolean getLastCachedIsEncrypted() {
		return isEncrypted;
	}

	private List<VideoStreamingCapability> getSupportedCapabilities(
			Resolution minResolution,
			Resolution maxResolution,
			Double constraintDiagonalMax,
			AspectRatio ratioRange,
			List<VideoStreamingCapability> originalAdditionalCapabilities
	){
		Integer constraintHeightMax = maxResolution.getResolutionHeight();
		Integer constraintHeightMin = minResolution.getResolutionHeight();
		Integer constraintWidthMax = maxResolution.getResolutionWidth();
		Integer constraintWidthMin = minResolution.getResolutionWidth();
		Double aspectRationMin = ratioRange.getMinAspectRatio();
		Double aspectRationMax = ratioRange.getMaxAspectRatio();

		List<VideoStreamingCapability> validCapabilities = new ArrayList<>();

		VideoStreamingCapability preferredCapability = new VideoStreamingCapability();
		preferredCapability.setDiagonalScreenSize(parameters.getPreferredDiagonal());
		preferredCapability.setPreferredResolution(new ImageResolution(
				parameters.getResolution().getResolutionWidth(),
				parameters.getResolution().getResolutionHeight())
		);

		// get the first one - the Desired resolution to guarantee streaming will start
		validCapabilities.add(preferredCapability);

		for (VideoStreamingCapability capability : originalAdditionalCapabilities) {
			double diagonal;
			int resolutionHeight;
			int resolutionWidth;
			// TODO refactor
			if (capability.getPreferredResolution() == null || capability.getPreferredResolution().getResolutionHeight() == null) {
				continue;
			} else {
				resolutionHeight = capability.getPreferredResolution().getResolutionHeight();
			}
			if (capability.getPreferredResolution() == null || capability.getPreferredResolution().getResolutionWidth() == null) {
				continue;
			} else {
				resolutionWidth = capability.getPreferredResolution().getResolutionWidth();
			}
			if (capability.getDiagonalScreenSize() == null ) {
				diagonal = parameters.getPreferredDiagonal();
			} else {
				diagonal = capability.getDiagonalScreenSize();
			}

			if (constraintDiagonalMax < diagonal) {
				continue;
			}

			if (!isAspectRatioInRange(streamingRange, capability.getPreferredResolution())) {
				if (constraintHeightMax == null && constraintHeightMin == null) {
					continue;
				}
			}

			if (!isImageResolutionInRange(streamingRange, capability.getPreferredResolution())) {
				continue;
			}

			validCapabilities.add(capability);
		}

		return validCapabilities;
	}

	public Boolean isImageResolutionInRange(VideoStreamingRange range, ImageResolution currentResolution) {

		Integer constraintHeightMax = range.getMaxSupportedResolution().getResolutionHeight();
		Integer constraintHeightMin = range.getMinSupportedResolution().getResolutionHeight();
		Integer constraintWidthMax = range.getMaxSupportedResolution().getResolutionWidth();
		Integer constraintWidthMin = range.getMinSupportedResolution().getResolutionWidth();
		Integer resolutionHeight = currentResolution.getResolutionHeight();
		Integer resolutionWidth = currentResolution.getResolutionWidth();
		if (currentResolution.getResolutionHeight() > 0 && currentResolution.getResolutionWidth() > 0 && constraintHeightMax != null && constraintHeightMin != null)
		{
			if (!(resolutionHeight >= constraintHeightMin && resolutionHeight <= constraintHeightMax)) {
				return false;
			}

			if (!(resolutionWidth >= constraintWidthMin && resolutionWidth <= constraintWidthMax)) {
				return false;
			}
		}

		// TODO check what if dev provided invalid constraints
		return true;
	}

	public Boolean isAspectRatioInRange(VideoStreamingRange range, ImageResolution currentResolution) {
		Double aspectRatioMin = range.getAspectRatio().getMinAspectRatio();
		Double aspectRatioMax = range.getAspectRatio().getMaxAspectRatio();

		Double currentAspectRatio = Double.valueOf(currentResolution.getResolutionWidth()) / Double.valueOf(currentResolution.getResolutionHeight());

		if (!(aspectRatioMax > aspectRatioMin && aspectRatioMin > 0)) {
			if ((currentAspectRatio >= aspectRatioMin && currentAspectRatio <= aspectRatioMax)) {
				return false;
			}
		}
		// TODO check what if dev provided invalid constraints

		return true;
	}

	/**
	 * Keeps track of the current motion event for VPM
	 */
	private static class SdlMotionEvent {
		class Pointer {
			int id;
			float x;
			float y;
			Pointer (int id) {
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

		SdlMotionEvent(){
			downTimeOnHMI = 0;
		}

		/**
		 * Handles the SDL Touch Event to keep track of pointer status and returns the appropriate
		 * Android MotionEvent according to this events status
		 * @param touchType The SDL TouchType that was received from the module
		 * @param touchEvent The SDL TouchEvent that was received from the module
		 * @return the correct native Android MotionEvent action to dispatch
		 */
		synchronized int getMotionEventAction(TouchType touchType, TouchEvent touchEvent){
			eventTime = 0;
			int motionEventAction = -1;
			switch (touchType){
				case BEGIN:
					if(pointers.size() == 0){
						//The motion event has just begun
						motionEventAction = MotionEvent.ACTION_DOWN;
						downTime = SystemClock.uptimeMillis();
						downTimeOnHMI = touchEvent.getTimestamps().get(touchEvent.getTimestamps().size() - 1);
						eventTime = downTime;
					} else{
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

		Pointer getPointerById(int id){
			if (pointers != null && !pointers.isEmpty()){
				for (Pointer pointer : pointers){
					if (pointer.id == id){
						return pointer;
					}
				}
			}
			return null;
		}

		Pointer getPointerByIndex(int index){
			return pointers.get(index);
		}

		void removePointerById(int id){
			pointers.remove(getPointerById(id));
		}
	}

}
