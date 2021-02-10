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
import android.view.Display;
import android.view.InputDevice;
import android.view.MotionEvent;

import androidx.annotation.RestrictTo;

import com.smartdevicelink.encoder.VirtualDisplayEncoder;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.StreamingStateMachine;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.managers.video.resolution.AspectRatio;
import com.smartdevicelink.managers.video.resolution.Resolution;
import com.smartdevicelink.managers.video.resolution.VideoStreamingRange;
import com.smartdevicelink.protocol.ISdlServiceListener;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.AppCapability;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.OnAppCapabilityUpdated;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.proxy.rpc.VideoStreamingCapability;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.TouchType;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingState;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.session.SdlSession;
import com.smartdevicelink.streaming.AbstractPacketizer;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.streaming.StreamPacketizer;
import com.smartdevicelink.streaming.video.IVideoStreamListener;
import com.smartdevicelink.streaming.video.RTPH264Packetizer;
import com.smartdevicelink.streaming.video.SdlRemoteDisplay;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.FutureTask;

@TargetApi(19)
public class VideoStreamManager extends BaseVideoStreamManager {
    private static final String TAG = "VideoStreamManager";

    private WeakReference<Context> context;
    private volatile VirtualDisplayEncoder virtualDisplayEncoder;
    private Class<? extends SdlRemoteDisplay> remoteDisplayClass = null;
    private SdlRemoteDisplay remoteDisplay;
    private final float[] touchScalar = {1.0f, 1.0f}; //x, y
    private HapticInterfaceManager hapticManager;
    private SdlMotionEvent sdlMotionEvent = null;
    private OnHMIStatus currentOnHMIStatus;
    private final StreamingStateMachine stateMachine;
    private VideoStreamingParameters parameters;
    private VideoStreamingCapability originalCapability;
    private IVideoStreamListener streamListener;
    private boolean isTransportAvailable = false;
    private Integer majorProtocolVersion;
    private List<VideoStreamingRange> listOfStreamingRanges;
    private boolean hasStarted;
    private String vehicleMake = null;
    private boolean isEncrypted = false;
    private boolean withPendingRestart = false;
    private AbstractPacketizer videoPacketizer;

    // INTERNAL INTERFACES

    private final ISdlServiceListener serviceListener = new ISdlServiceListener() {
        @Override
        public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted) {
            if (SessionType.NAV.equals(type)) {
                if (session != null && session.getAcceptedVideoParams() != null) {
                    parameters = session.getAcceptedVideoParams();
                    VideoStreamManager.this.streamListener = startVideoStream(session.getAcceptedVideoParams(), session);
                }

                if (VideoStreamManager.this.streamListener == null) {
                    DebugTool.logError(TAG, "Error starting video stream");
                    stateMachine.transitionToState(StreamingStateMachine.ERROR);
                    return;
                }
                VideoStreamingCapability capability = null;
                if (internalInterface.getSystemCapabilityManager() != null) {
                    capability = (VideoStreamingCapability) internalInterface.getSystemCapabilityManager().getCapability(SystemCapabilityType.VIDEO_STREAMING, null, false);
                }
                if (capability != null && Boolean.TRUE.equals(capability.getIsHapticSpatialDataSupported())) {
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
            if (SessionType.NAV.equals(type)) {
                if (remoteDisplay !=null){
                    stopStreaming(withPendingRestart);
                }
                stateMachine.transitionToState(StreamingStateMachine.NONE);
                transitionToState(SETTING_UP);

                if (withPendingRestart){
                    VideoStreamManager manager = VideoStreamManager.this;
                    manager.internalInterface.startVideoService(
                            manager.getLastCachedStreamingParameters(),
                            manager.isEncrypted,
                            withPendingRestart
                    );
                }
            }
        }

        @Override
        public void onServiceError(SdlSession session, SessionType type, String reason) {
            DebugTool.logError(TAG, "Unable to start video service: " + reason);
            stopVideoStream();
            stateMachine.transitionToState(StreamingStateMachine.ERROR);
            transitionToState(BaseSubManager.ERROR);
        }
    };

    private final OnRPCNotificationListener hmiListener = new OnRPCNotificationListener() {
        @Override
        public void onNotified(RPCNotification notification) {
            if (notification != null) {
                OnHMIStatus onHMIStatus = (OnHMIStatus) notification;
                if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                    return;
                }
                OnHMIStatus prevOnHMIStatus = currentOnHMIStatus;
                currentOnHMIStatus = onHMIStatus;
                if (!HMILevel.HMI_NONE.equals(currentOnHMIStatus.getHmiLevel()) && VideoStreamManager.this.parameters == null) {
                    getVideoStreamingParams();
                }
                checkState();
                if (hasStarted && (isHMIStateVideoStreamCapable(prevOnHMIStatus)) && (!isHMIStateVideoStreamCapable(currentOnHMIStatus))) {
                    stopVideoStream();
                }
            }
        }
    };

    private final OnRPCNotificationListener touchListener = new OnRPCNotificationListener() {
        @Override
        public void onNotified(RPCNotification notification) {
            if (notification != null && remoteDisplay != null) {
                List<MotionEvent> motionEventList = convertTouchEvent((OnTouchEvent) notification);
                if (motionEventList != null && !motionEventList.isEmpty()) {
                    for (MotionEvent motionEvent : motionEventList) {
                        remoteDisplay.handleMotionEvent(motionEvent);
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
            params.update(castedCapability, vehicleMake);//Streaming parameters are ready time to stream
            VideoStreamManager.this.parameters = params;

            VideoStreamManager.this.withPendingRestart = true;

            virtualDisplayEncoder.setStreamingParams(params);
            stopStreaming(true);
        }

        @Override
        public void onError(String info) {
            DebugTool.logInfo(TAG, "onError: " + info);
        }
    };

    // MANAGER APIs
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public VideoStreamManager(ISdl internalInterface) {
        super(internalInterface);

        if (internalInterface != null && internalInterface.getRegisterAppInterfaceResponse() != null &&
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
        internalInterface.getSystemCapabilityManager().addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, systemCapabilityListener);
        stateMachine = new StreamingStateMachine();
    }

    @Override
    public void start(CompletionListener listener) {
        isTransportAvailable = internalInterface.isTransportForServiceAvailable(SessionType.NAV);
        checkState();
        super.start(listener);
    }

    private synchronized void checkState() {
        if (this.getState() == SETTING_UP
                && isTransportAvailable
                && isHMIStateVideoStreamCapable(currentOnHMIStatus)
                && parameters != null) {
            stateMachine.transitionToState(StreamingStateMachine.READY);
            transitionToState(READY);
        }
    }

    boolean isHMIStateVideoStreamCapable(OnHMIStatus onHMIStatus) {
        HMILevel hmiLevel = (onHMIStatus != null && onHMIStatus.getHmiLevel() != null) ? onHMIStatus.getHmiLevel() : HMILevel.HMI_NONE;
        VideoStreamingState videoStreamingState = (onHMIStatus != null && onHMIStatus.getVideoStreamingState() != null) ? onHMIStatus.getVideoStreamingState() : VideoStreamingState.STREAMABLE;
        return (hmiLevel.equals(HMILevel.HMI_FULL) || hmiLevel.equals(HMILevel.HMI_LIMITED)) && videoStreamingState.equals(VideoStreamingState.STREAMABLE);
    }

    private void getVideoStreamingParams() {
        if (internalInterface.getProtocolVersion().getMajor() >= 5) {
            if (internalInterface.getSystemCapabilityManager() != null) {
                internalInterface.getSystemCapabilityManager().getCapability(SystemCapabilityType.VIDEO_STREAMING, new OnSystemCapabilityListener() {
                    @Override
                    public void onCapabilityRetrieved(Object capability) {
                        VideoStreamingParameters params = new VideoStreamingParameters();
                        VideoStreamingCapability castedCapability = ((VideoStreamingCapability)capability);
                        VideoStreamManager.this.originalCapability = castedCapability;
                        params.update(castedCapability, vehicleMake);//Streaming parameters are ready time to stream
                        VideoStreamManager.this.parameters = params;
                        checkState();
                        }

                    @Override
                    public void onError(String info) {
                        DebugTool.logError(TAG, "Error retrieving video streaming capability: " + info);
                        stateMachine.transitionToState(StreamingStateMachine.ERROR);
                        transitionToState(ERROR);
                    }
                }, false);
            }
        } else {
            //We just use default video streaming params
            VideoStreamingParameters params = new VideoStreamingParameters();
            DisplayCapabilities dispCap = null;
            if (internalInterface.getSystemCapabilityManager() != null) {
                dispCap = (DisplayCapabilities) internalInterface.getSystemCapabilityManager().getCapability(SystemCapabilityType.DISPLAY, null, false);
            }
            if (dispCap != null) {
                params.setResolution(dispCap.getScreenParams().getImageResolution());
            }

            this.parameters = params;
            checkState();
        }
    }

    /**
     * Starts streaming a remote display to the module if there is a connected session. This method of streaming requires the device to be on API level 19 or higher
     *
     * @param context            a context that can be used to create the remote display
     * @param remoteDisplayClass class object of the remote display. This class will be used to create an instance of the remote display and will be projected to the module
     * @param parameters         streaming parameters to be used when streaming. If null is sent in, the default/optimized options will be used.
     *                           If you are unsure about what parameters to be used it is best to just send null and let the system determine what
     *                           works best for the currently connected module.
     * @param encrypted         a flag of if the stream should be encrypted. Only set if you have a supplied encryption library that the module can understand.
     * @param landscapeRange    constraints for vehicle display : aspect ratio, min/max resolutions, max diagonal size.
     * @param portraitRange     constraints for vehicle display : aspect ratio, min/max resolutions, max diagonal size.
     */
    public void startRemoteDisplayStream(Context context, Class<? extends SdlRemoteDisplay> remoteDisplayClass, VideoStreamingParameters parameters, final boolean encrypted, VideoStreamingRange landscapeRange, VideoStreamingRange portraitRange) {
        Collections.addAll(listOfStreamingRanges, portraitRange, landscapeRange);
        configureGlobalParameters(context, remoteDisplayClass, isEncrypted, listOfStreamingRanges);
        if(majorProtocolVersion >= 5 && !internalInterface.getSystemCapabilityManager().isCapabilitySupported(SystemCapabilityType.VIDEO_STREAMING)){
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
     *
     * @param context            a context that can be used to create the remote display
     * @param remoteDisplayClass class object of the remote display. This class will be used to create an instance of the remote display and will be projected to the module
     * @param parameters         streaming parameters to be used when streaming. If null is sent in, the default/optimized options will be used.
     *                           If you are unsure about what parameters to be used it is best to just send null and let the system determine what
     *                           works best for the currently connected module.
     * @param encrypted          a flag of if the stream should be encrypted. Only set if you have a supplied encryption library that the module can understand.
     */
    @Deprecated
    public void startRemoteDisplayStream(Context context, Class<? extends SdlRemoteDisplay> remoteDisplayClass, VideoStreamingParameters parameters, final boolean encrypted){
        configureGlobalParameters(context, remoteDisplayClass, isEncrypted, null);
        boolean isCapabilitySupported = internalInterface.getSystemCapabilityManager() != null && internalInterface.getSystemCapabilityManager().isCapabilitySupported(SystemCapabilityType.VIDEO_STREAMING);
        if (majorProtocolVersion >= 5 && !isCapabilitySupported) {
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

    private void configureGlobalParameters(Context context, Class<? extends SdlRemoteDisplay> remoteDisplayClass, boolean encrypted, List<VideoStreamingRange> listOfStreamingRange) {
        this.context = new WeakReference<>(context);
        this.remoteDisplayClass = remoteDisplayClass;
        this.isEncrypted = encrypted;
        this.majorProtocolVersion = internalInterface.getProtocolVersion().getMajor();
        if (listOfStreamingRange != null) {
            this.listOfStreamingRanges = listOfStreamingRange;
        }
    }


    private void processCapabilitiesWithPendingStart(boolean encrypted, VideoStreamingParameters parameters) {
        final VideoStreamingParameters params = (parameters == null) ? new VideoStreamingParameters() : new VideoStreamingParameters(parameters);
        if (majorProtocolVersion >= 5) {
            if (internalInterface.getSystemCapabilityManager() != null) {
                internalInterface.getSystemCapabilityManager().getCapability(SystemCapabilityType.VIDEO_STREAMING, new OnSystemCapabilityListener() {
                    @Override
                    public void onCapabilityRetrieved(Object capability) {
                        VideoStreamingParameters params = new VideoStreamingParameters();
                        VideoStreamingCapability castedCapability = ((VideoStreamingCapability) capability);
                        VideoStreamManager.this.originalCapability = castedCapability;

                        params.update(castedCapability, vehicleMake);    //Streaming parameters are ready time to stream
                        VideoStreamManager.this.parameters = params;

                        if (listOfStreamingRanges != null) {
                            // filtering
                            castedCapability.setAdditionalVideoStreamingCapabilities(
                                    getSupportedCapabilities(
                                            listOfStreamingRanges,
                                            castedCapability.getAdditionalVideoStreamingCapabilities()
                                    )
                            );
                        }
                        AppCapability appCapability = new AppCapability();
                        appCapability.setVideoStreamingCapability(castedCapability);

                        OnAppCapabilityUpdated onAppCapabilityUpdated = new OnAppCapabilityUpdated();
                        internalInterface.sendRPC(onAppCapabilityUpdated);
                        startStreaming(params, isEncrypted);
                    }

                    @Override
                    public void onError(String info) {
                        stateMachine.transitionToState(StreamingStateMachine.ERROR);
                        DebugTool.logError(TAG, "Error retrieving video streaming capability: " + info);
                    }
                }, false);
            }
        } else {
            //We just use default video streaming params
            DisplayCapabilities dispCap = null;
            if (internalInterface.getSystemCapabilityManager() != null) {
                dispCap = (DisplayCapabilities) internalInterface.getSystemCapabilityManager().getCapability(SystemCapabilityType.DISPLAY, null, false);
            }
            if (dispCap != null) {
                params.setResolution(dispCap.getScreenParams().getImageResolution());
            }
            startStreaming(params, encrypted);
        }
    }


    /**
     * Starts video service, sets up encoder, haptic manager, and remote display. Begins streaming the remote display.
     *
     * @param parameters Video streaming parameters including: codec which will be used for streaming (currently, only
     *                   VideoStreamingCodec.H264 is accepted), height and width of the video in pixels.
     * @param encrypted  Specify true if packets on this service have to be encrypted
     */
    protected void startStreaming(VideoStreamingParameters parameters, boolean encrypted) {
        this.parameters = parameters;
        if (!isHMIStateVideoStreamCapable(currentOnHMIStatus)) {
            DebugTool.logError(TAG, "Cannot start video service in the current HMI status");
            return;
        }
        //Start the video service
        this.internalInterface.startVideoService(parameters, encrypted, false);
    }

    /**
     * Initializes and starts the virtual display encoder and creates the remote display
     */
    private void startEncoder() {
        try {
            if (remoteDisplay != null) {
                remoteDisplay.resizeView(parameters.getResolution().getResolutionWidth(), parameters.getResolution().getResolutionHeight());
            }

            virtualDisplayEncoder.init(this.context.get(), streamListener, parameters);
            //We are all set so we can start streaming at at this point
            virtualDisplayEncoder.start();
            //Encoder should be up and running
            createRemoteDisplay(virtualDisplayEncoder.getVirtualDisplay());

            stateMachine.transitionToState(StreamingStateMachine.STARTED);
            hasStarted = true;
        } catch (Exception e) {
            stateMachine.transitionToState(StreamingStateMachine.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Stops streaming from the remote display. To restart, call
     *
     * @see #resumeStreaming()
     */
    @Deprecated
    public void stopStreaming() {
        if (remoteDisplay != null) {
            remoteDisplay.stop();
        }
        if (virtualDisplayEncoder != null) {
            virtualDisplayEncoder.shutDown();
        }
        stateMachine.transitionToState(StreamingStateMachine.STOPPED);
    }

    /**
     * Stops streaming from the remote display. To restart, call
     *
     * @see #resumeStreaming()
     */
    public void stopStreaming(boolean withPendingRestart) {
        if (remoteDisplay != null && !withPendingRestart) {
            remoteDisplay.stop();
            this.withPendingRestart = false;
        }
        if (this.isStreaming()) {
            if (virtualDisplayEncoder != null) {
                virtualDisplayEncoder.shutDown(withPendingRestart);
            }
            stateMachine.transitionToState(StreamingStateMachine.PAUSED);
            stopVideoStream();
        }
    }

    /**
     * Resumes streaming after calling
     *
     * @see #startRemoteDisplayStream(android.content.Context, Class, com.smartdevicelink.streaming.video.VideoStreamingParameters, boolean)
     * followed by a call to
     * @see #stopStreaming(boolean withPendingRestart)
     */
    public void resumeStreaming() {
        int currentState = stateMachine.getState();
        if (currentState == StreamingStateMachine.STOPPED || currentState == StreamingStateMachine.PAUSED) {
            startEncoder();
        }
    }

    /**
     * Stops streaming, ends video streaming service and removes service listeners.
     */
    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void dispose() {
        stopStreaming(false);

        hapticManager = null;
        remoteDisplay = null;
        parameters = null;
        virtualDisplayEncoder = null;
        if (internalInterface != null) {
            // Remove listeners
            internalInterface.removeServiceListener(SessionType.NAV, serviceListener);
            internalInterface.removeOnRPCNotificationListener(FunctionID.ON_TOUCH_EVENT, touchListener);
            internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
        }

        stopVideoStream();


        stateMachine.transitionToState(StreamingStateMachine.NONE);
        super.dispose();
    }

    // PUBLIC METHODS FOR CHECKING STATE

    /**
     * Check if a video service is currently active
     *
     * @return boolean (true = active, false = inactive)
     */
    public boolean isServiceActive() {
        return (stateMachine.getState() == StreamingStateMachine.READY) ||
                (stateMachine.getState() == StreamingStateMachine.STARTED) ||
                (stateMachine.getState() == StreamingStateMachine.STOPPED) ||
                (stateMachine.getState() == StreamingStateMachine.PAUSED);
        }

    /**
     * Check if video is currently streaming and visible
     *
     * @return boolean (true = yes, false = no)
     */
    public boolean isStreaming() {
        return (stateMachine.getState() == StreamingStateMachine.STARTED) && (isHMIStateVideoStreamCapable(currentOnHMIStatus));
    }

    /**
     * Check if video streaming has been paused due to app moving to background or manually stopped
     *
     * @return boolean (true = not paused, false = paused)
     */
    public boolean isPaused() {
        return (hasStarted && stateMachine.getState() == StreamingStateMachine.STOPPED) || (!isHMIStateVideoStreamCapable(currentOnHMIStatus));
    }

    /**
     * Gets the current video streaming state as defined in @StreamingStateMachine
     *
     * @return int representing StreamingStateMachine.StreamingState
     */
    public @StreamingStateMachine.StreamingState
    int currentVideoStreamState() {
        return stateMachine.getState();
    }

    // HELPER METHODS

    private void createRemoteDisplay(final Display disp) {
        try {
            if (disp == null) {
                return;
            }

            // Dismiss the current presentation if the display has changed.
            if (remoteDisplay != null && remoteDisplay.getDisplay() != disp) {
                remoteDisplay.dismissPresentation();
            }

            FutureTask<Boolean> fTask = new FutureTask<>(new SdlRemoteDisplay.Creator(context.get(), disp, remoteDisplay, remoteDisplayClass, new SdlRemoteDisplay.Callback() {
                @Override
                public void onCreated(final SdlRemoteDisplay remoteDisplay) {
                    //Remote display has been created.
                    //Now is a good time to do parsing for spatial data
                    VideoStreamManager.this.remoteDisplay = remoteDisplay;
                    if (hapticManager != null) {
                        remoteDisplay.getMainView().post(new Runnable() {
                            @Override
                            public void run() {
                                hapticManager.refreshHapticData(remoteDisplay.getMainView());
                            }
                        });
                    }
                    //Get touch scalars
                    ImageResolution resolution = null;
                    if (internalInterface.getProtocolVersion().getMajor() >= 5) { //At this point we should already have the capability
                        VideoStreamingCapability capability = null;
                        if (internalInterface.getSystemCapabilityManager() != null) {
                            capability = (VideoStreamingCapability) internalInterface.getSystemCapabilityManager().getCapability(SystemCapabilityType.VIDEO_STREAMING, null, false);
                        }
                        if (capability != null) {
                            resolution = capability.getPreferredResolution();
                        }
                    }

                    if (resolution == null) { //Either the protocol version is too low to access video streaming caps, or they were null
                        DisplayCapabilities dispCap = null;
                        if (internalInterface.getSystemCapabilityManager() != null) {
                            dispCap = (DisplayCapabilities) internalInterface.getSystemCapabilityManager().getCapability(SystemCapabilityType.DISPLAY, null, false);
                        }
                        if (dispCap != null) {
                            resolution = (dispCap.getScreenParams().getImageResolution());
                        }
                    }

                    if (resolution != null) {
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        disp.getMetrics(displayMetrics);
                        createTouchScalar(resolution, displayMetrics);
                    }

                VideoStreamManager.this.remoteDisplay.resizeView(parameters.getResolution().getResolutionWidth(), parameters.getResolution().getResolutionHeight());
                }

                @Override
                public void onInvalidated(final SdlRemoteDisplay remoteDisplay) {
                    //Our view has been invalidated
                    //A good time to refresh spatial data
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    VideoStreamManager.this.remoteDisplay.getDisplay().getMetrics(displayMetrics);
                    displayMetrics.widthPixels =  (int) (parameters.getResolution().getResolutionWidth() * parameters.getScale());
                    displayMetrics.heightPixels =  (int) (parameters.getResolution().getResolutionHeight() * parameters.getScale());
                    createTouchScalar(parameters.getResolution(), displayMetrics);
                    if (hapticManager != null) {
                        remoteDisplay.getMainView().post(new Runnable() {
                            @Override
                            public void run() {
                                hapticManager.refreshHapticData(remoteDisplay.getMainView());
                            }
                        });
                    }
                }
            }));
            Thread showPresentation = new Thread(fTask);
            showPresentation.setName("RmtDispThread");

            showPresentation.start();
        } catch (Exception ex) {
            DebugTool.logError(TAG, "Unable to create Virtual Display.");
            if (DebugTool.isDebugEnabled()) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onTransportUpdate(List<TransportRecord> connectedTransports, boolean audioStreamTransportAvail, boolean videoStreamTransportAvail) {

        isTransportAvailable = videoStreamTransportAvail;

        if (internalInterface.getProtocolVersion().isNewerThan(new Version(5, 1, 0)) >= 0) {
            if (videoStreamTransportAvail) {
                checkState();
            }
        } else {
            //The protocol version doesn't support simultaneous transports.
            if (!videoStreamTransportAvail) {
                //If video streaming isn't available on primary transport then it is not possible to
                //use the video streaming manager until a complete register on a transport that
                //supports video
                transitionToState(ERROR);
            }
        }
    }

    void createTouchScalar(ImageResolution resolution, DisplayMetrics displayMetrics) {
        touchScalar[0] = ((float) displayMetrics.widthPixels) / resolution.getResolutionWidth();
        touchScalar[1] = ((float) displayMetrics.heightPixels) / resolution.getResolutionHeight();
    }

    List<MotionEvent> convertTouchEvent(OnTouchEvent onTouchEvent) {
        List<MotionEvent> motionEventList = new ArrayList<>();

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

    public VideoStreamingParameters getLastCachedStreamingParameters() {
        return parameters;
    }

    private List<VideoStreamingCapability> getSupportedCapabilities(
            List<VideoStreamingRange> ranges,
            List<VideoStreamingCapability> originalAdditionalCapabilities
    ){

        List<VideoStreamingCapability> validCapabilities = new ArrayList<>();

        VideoStreamingCapability preferredCapability = new VideoStreamingCapability();
        preferredCapability.setDiagonalScreenSize(parameters.getPreferredDiagonal());
        preferredCapability.setPreferredResolution(new ImageResolution(
                parameters.getResolution().getResolutionWidth(),
                parameters.getResolution().getResolutionHeight())
        );

        // get the first one - the Desired resolution to guarantee streaming will start
        validCapabilities.add(preferredCapability);
        for (VideoStreamingRange range: ranges) {

            Integer constraintHeightMax = range.getMaxResolution().getResolutionHeight();
            Integer constraintHeightMin = range.getMinResolution().getResolutionHeight();

            for (VideoStreamingCapability capability : originalAdditionalCapabilities) {
                double diagonal;
                if (capability.getPreferredResolution() == null
                        || capability.getPreferredResolution().getResolutionHeight() == null
                        || capability.getPreferredResolution().getResolutionWidth() == null) {
                    continue;
                }
                if (capability.getDiagonalScreenSize() == null) {
                    diagonal = parameters.getPreferredDiagonal();
                } else {
                    diagonal = capability.getDiagonalScreenSize();
                }

                if (range.getMinScreenDiagonal() > diagonal) {
                    continue;
                }

                if (!isAspectRatioInRange(range.getAspectRatio(), capability.getPreferredResolution())) {
                    if (constraintHeightMax == null && constraintHeightMin == null) {
                        continue;
                    }
                }

                if (!isImageResolutionInRange(range.getMinResolution(), range.getMaxResolution(), capability.getPreferredResolution())) {
                    continue;
                }

                validCapabilities.add(capability);
            }
        }

        return validCapabilities;
    }

    public Boolean isImageResolutionInRange(Resolution minResolution, Resolution maxResolution, ImageResolution currentResolution) {

        Integer constraintHeightMax = maxResolution.getResolutionHeight();
        Integer constraintHeightMin = minResolution.getResolutionHeight();
        Integer constraintWidthMax = maxResolution.getResolutionWidth();
        Integer constraintWidthMin = minResolution.getResolutionWidth();
        Integer resolutionHeight = currentResolution.getResolutionHeight();
        Integer resolutionWidth = currentResolution.getResolutionWidth();
        if (currentResolution.getResolutionHeight() > 0 && currentResolution.getResolutionWidth() > 0 && constraintHeightMax != null && constraintHeightMin != null) {
            if (!(resolutionHeight >= constraintHeightMin && resolutionHeight <= constraintHeightMax)) {
                return false;
            }

            if (!(resolutionWidth >= constraintWidthMin && resolutionWidth <= constraintWidthMax)) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public Boolean isAspectRatioInRange(AspectRatio aspectRatio, ImageResolution currentResolution) {
        Double aspectRatioMin = aspectRatio.getMinAspectRatio();
        Double aspectRatioMax = aspectRatio.getMaxAspectRatio();

        Double currentAspectRatio = Double.valueOf(currentResolution.getResolutionWidth()) / Double.valueOf(currentResolution.getResolutionHeight());

        if (aspectRatioMax > aspectRatioMin && aspectRatioMin > 0) {
            return currentAspectRatio >= aspectRatioMin && currentAspectRatio <= aspectRatioMax;
        }
        else {
            return false;
        }
    }

    /**
     * Keeps track of the current motion event for VPM
     */
    private static class SdlMotionEvent {
        class Pointer {
            final int id;
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

        private final CopyOnWriteArrayList<Pointer> pointers = new CopyOnWriteArrayList<>();
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
                    if (pointers.size() <= 1) {
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

    private VideoStreamingProtocol getAcceptedProtocol(VideoStreamingParameters params) {
        if (params != null) {
            VideoStreamingFormat format = params.getFormat();
            if (format != null && format.getProtocol() != null) {
                return format.getProtocol();
            }
        }
        //Returns default protocol if none are found
        return new VideoStreamingParameters().getFormat().getProtocol();

    }

    protected IVideoStreamListener startVideoStream(VideoStreamingParameters params, final SdlSession session) {
        VideoStreamingProtocol protocol = getAcceptedProtocol(params);

        IStreamListener iStreamListener = new IStreamListener() {
            @Override
            public void sendStreamPacket(ProtocolMessage pm) {
                session.sendMessage(pm);
            }
        };

        try {
            switch (protocol) {
                case RAW: {
                    videoPacketizer = new StreamPacketizer(iStreamListener, null, SessionType.NAV, (byte) session.getSessionId(), session);
                    videoPacketizer.start();
                    return (IVideoStreamListener) videoPacketizer;
                }
                case RTP: {
                    //FIXME why is this not an extension of StreamPacketizer?
                    videoPacketizer = new RTPH264Packetizer(iStreamListener, SessionType.NAV, (byte) session.getSessionId(), session);
                    videoPacketizer.start();
                    return (IVideoStreamListener) videoPacketizer;
                }
                default:
                    DebugTool.logError(TAG, "Protocol " + protocol + " is not supported.");
                    return null;
            }
        } catch (IOException e) {
            return null;
        }

    }

    protected boolean stopVideoStream() {
        if (videoPacketizer != null) {
            videoPacketizer.stop();
            return true;
        }
        return false;
    }

}
