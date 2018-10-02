package com.smartdevicelink.managers.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.InputDevice;
import android.view.MotionEvent;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.encoder.VirtualDisplayEncoder;
import com.smartdevicelink.haptic.HapticInterfaceManager;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.StreamingStateMachine;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.proxy.rpc.VideoStreamingCapability;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.TouchType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.streaming.video.SdlRemoteDisplay;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.Version;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.FutureTask;

@TargetApi(19)
public class VideoStreamManager extends BaseSubManager {
	private static String TAG = "VideoStreamManager";

	private WeakReference<Context> context;
	private volatile VirtualDisplayEncoder virtualDisplayEncoder;
	private Class<? extends SdlRemoteDisplay> remoteDisplayClass = null;
	private SdlRemoteDisplay remoteDisplay;
	private float[] touchScalar = {1.0f,1.0f}; //x, y
	private HapticInterfaceManager hapticManager;
	private SdlMotionEvent sdlMotionEvent = null;
	private HMILevel hmiLevel;
	private StreamingStateMachine stateMachine;
	private VideoStreamingParameters parameters;
	private IVideoStreamListener streamListener;
	private boolean isTransportAvailable = false;

	// INTERNAL INTERFACES

	private final ISdlServiceListener serviceListener = new ISdlServiceListener() {
		@Override
		public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted) {
			if(SessionType.NAV.equals(type)){
				stateMachine.transitionToState(StreamingStateMachine.READY);
			}
		}

		@Override
		public void onServiceEnded(SdlSession session, SessionType type) {
			if(SessionType.NAV.equals(type)){
				stateMachine.transitionToState(StreamingStateMachine.NONE);
				if(remoteDisplay!=null){
					stopStreaming();
				}
			}
		}

		@Override
		public void onServiceError(SdlSession session, SessionType type, String reason) {
			stateMachine.transitionToState(StreamingStateMachine.ERROR);
			transitionToState(BaseSubManager.ERROR);
		}
	};

	private final OnRPCNotificationListener hmiListener = new OnRPCNotificationListener() {
		@Override
		public void onNotified(RPCNotification notification) {
			if(notification != null){
				hmiLevel = ((OnHMIStatus)notification).getHmiLevel();
				if(hmiLevel.equals(HMILevel.HMI_FULL)){
					checkState();
				}
			}
		}
	};

	private final OnRPCNotificationListener touchListener = new OnRPCNotificationListener() {
		@Override
		public void onNotified(RPCNotification notification) {
			if(notification != null && remoteDisplay != null){
				MotionEvent event = convertTouchEvent((OnTouchEvent)notification);
				if(event!=null){
					remoteDisplay.handleMotionEvent(event);
				}
			}
		}
	};

	// MANAGER APIs

	public VideoStreamManager(ISdl internalInterface){
		super(internalInterface);

		virtualDisplayEncoder = new VirtualDisplayEncoder();
		hmiLevel = HMILevel.HMI_NONE;

		// Listen for video service events
		internalInterface.addServiceListener(SessionType.NAV, serviceListener);
		// Take care of the touch events
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_TOUCH_EVENT, touchListener);
		// Listen for HMILevel changes
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

		stateMachine = new StreamingStateMachine();
	}

	@Override
	public void start(CompletionListener listener) {
		isTransportAvailable = internalInterface.isTransportForServiceAvailable(SessionType.NAV);
		getVideoStreamingParams();
		checkState();
		super.start(listener);
	}

	private synchronized void checkState(){
		if(this.getState() == SETTING_UP
				&& isTransportAvailable
				&& hmiLevel != null
				&& hmiLevel.equals(HMILevel.HMI_FULL)
				&& parameters != null){
			transitionToState(READY);
		}
	}

	private void getVideoStreamingParams(){
		if(internalInterface.getProtocolVersion().getMajor() >= 5) {
			internalInterface.getCapability(SystemCapabilityType.VIDEO_STREAMING, new OnSystemCapabilityListener() {
				@Override
				public void onCapabilityRetrieved(Object capability) {
					VideoStreamingParameters params = new VideoStreamingParameters();
					params.update((VideoStreamingCapability)capability);	//Streaming parameters are ready time to stream
					VideoStreamManager.this.parameters = params;

					checkState();

				}

				@Override
				public void onError(String info) {
					Log.e(TAG, "Error retrieving video streaming capability: " + info);
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
	 */
	public void startRemoteDisplayStream(Context context, Class<? extends SdlRemoteDisplay> remoteDisplayClass, VideoStreamingParameters parameters, final boolean encrypted){
		this.context = new WeakReference<>(context);
		this.remoteDisplayClass = remoteDisplayClass;
		int majorProtocolVersion = internalInterface.getProtocolVersion().getMajor();
		if(majorProtocolVersion >= 5 && !internalInterface.isCapabilitySupported(SystemCapabilityType.VIDEO_STREAMING)){
			Log.e(TAG, "Video streaming not supported on this module");
			stateMachine.transitionToState(StreamingStateMachine.ERROR);
			return;
		}
		if(parameters == null){
			if(majorProtocolVersion >= 5) {
				internalInterface.getCapability(SystemCapabilityType.VIDEO_STREAMING, new OnSystemCapabilityListener() {
					@Override
					public void onCapabilityRetrieved(Object capability) {
						VideoStreamingParameters params = new VideoStreamingParameters();
						params.update((VideoStreamingCapability)capability);	//Streaming parameters are ready time to stream
						startStreaming(params, encrypted);
					}

					@Override
					public void onError(String info) {
						stateMachine.transitionToState(StreamingStateMachine.ERROR);
						Log.e(TAG, "Error retrieving video streaming capability: " + info);
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
	 * Opens a video service (service type 11) and subsequently provides an IVideoStreamListener
	 * to the app to send video data. The supplied VideoStreamingParameters will be set as desired paramaters
	 * that will be used to negotiate
	 *
	 * @param parameters  Video streaming parameters including: codec which will be used for streaming (currently, only
	 *                    VideoStreamingCodec.H264 is accepted), height and width of the video in pixels.
	 * @param encrypted Specify true if packets on this service have to be encrypted
	 *
	 * @return IVideoStreamListener interface if service is opened successfully and streaming is
	 *         started, null otherwise
	 */
	protected IVideoStreamListener startVideoService(VideoStreamingParameters parameters, boolean encrypted){
		if(hmiLevel != HMILevel.HMI_FULL){
			Log.e(TAG, "Cannot start video service if HMILevel is not FULL.");
			return null;
		}
		IVideoStreamListener listener = internalInterface.startVideoStream(encrypted, parameters);
		if(listener != null){
			stateMachine.transitionToState(StreamingStateMachine.STARTED);
		}else{
			stateMachine.transitionToState(StreamingStateMachine.ERROR);
		}
		return listener;
	}

	/**
	 * Starts video service, sets up encoder, haptic manager, and remote display. Begins streaming the remote display.
	 * @param parameters Video streaming parameters including: codec which will be used for streaming (currently, only
	 *                    VideoStreamingCodec.H264 is accepted), height and width of the video in pixels.
	 * @param encrypted Specify true if packets on this service have to be encrypted
	 */
	private void startStreaming(VideoStreamingParameters parameters, boolean encrypted){
		this.parameters = parameters;
		this.streamListener = startVideoService(parameters, encrypted);
		if(streamListener == null){
			Log.e(TAG, "Error starting video service");
			stateMachine.transitionToState(StreamingStateMachine.ERROR);
			return;
		}
		VideoStreamingCapability capability = (VideoStreamingCapability) internalInterface.getCapability(SystemCapabilityType.VIDEO_STREAMING);
		if(capability != null && capability.getIsHapticSpatialDataSupported()){
			hapticManager = new HapticInterfaceManager(internalInterface);
		}
		startEncoder();
	}

	/**
	 * Initializes and starts the virtual display encoder and creates the remote display
	 */
	private void startEncoder(){
		try {
			virtualDisplayEncoder.init(this.context.get(), streamListener, parameters);
			//We are all set so we can start streaming at at this point
			virtualDisplayEncoder.start();
			//Encoder should be up and running
			createRemoteDisplay(virtualDisplayEncoder.getVirtualDisplay());
			stateMachine.transitionToState(StreamingStateMachine.STARTED);
		} catch (Exception e) {
			stateMachine.transitionToState(StreamingStateMachine.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Stops streaming from the remote display. To restart, call
	 * @see #resumeStreaming()
	 */
	public void stopStreaming(){
		if(remoteDisplay!=null){
			remoteDisplay.stop();
		}
		if(virtualDisplayEncoder!=null){
			virtualDisplayEncoder.shutDown();
		}
		stateMachine.transitionToState(StreamingStateMachine.STOPPED);
	}

	/**
	 * Resumes streaming after calling
	 * @see #startRemoteDisplayStream(android.content.Context, Class, com.smartdevicelink.streaming.video.VideoStreamingParameters, boolean)
	 * followed by a call to
	 * @see #stopStreaming()
	 */
	public void resumeStreaming(){
		if(stateMachine.getState() != StreamingStateMachine.STOPPED){
			return;
		}
		startEncoder();
	}

	/**
	 * Stops streaming, ends video streaming service and removes service listeners.
	 */
	@Override
	public void dispose(){
		stopStreaming();

		hapticManager = null;
		remoteDisplay = null;
		parameters = null;
		virtualDisplayEncoder = null;
		if(internalInterface!=null){
			internalInterface.stopVideoService();
		}

		// Remove listeners
		internalInterface.removeServiceListener(SessionType.NAV, serviceListener);
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_TOUCH_EVENT, touchListener);
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

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
				(stateMachine.getState() == StreamingStateMachine.STOPPED);
	}

	/**
	 * Check if video is currently streaming and visible
	 * @return boolean (true = yes, false = no)
	 */
	public boolean isStreaming(){
		return (stateMachine.getState() == StreamingStateMachine.STARTED) ||
				(hmiLevel == HMILevel.HMI_FULL);
	}

	/**
	 * Check if video streaming has been paused due to app moving to background or manually stopped
	 * @return boolean (true = not paused, false = paused)
	 */
	public boolean isPaused(){
		return (stateMachine.getState() == StreamingStateMachine.STARTED) ||
				(hmiLevel != HMILevel.HMI_FULL);
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
			if (remoteDisplay != null && remoteDisplay.getDisplay() != disp) {
				remoteDisplay.dismissPresentation();
			}

			FutureTask<Boolean> fTask =  new FutureTask<Boolean>( new SdlRemoteDisplay.Creator(context.get(), disp, remoteDisplay, remoteDisplayClass, new SdlRemoteDisplay.Callback(){
				@Override
				public void onCreated(final SdlRemoteDisplay remoteDisplay) {
					//Remote display has been created.
					//Now is a good time to do parsing for spatial data
					VideoStreamManager.this.remoteDisplay = remoteDisplay;
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
						resolution = capability.getPreferredResolution();
					}else {
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

			showPresentation.start();
		} catch (Exception ex) {
			Log.e(TAG, "Unable to create Virtual Display.");
		}
	}

	protected MotionEvent convertTouchEvent(OnTouchEvent touchEvent){
		List<TouchEvent> eventList = touchEvent.getEvent();
		if (eventList == null || eventList.size() == 0) return null;

		TouchType touchType = touchEvent.getType();
		if (touchType == null){ return null;}

		int eventListSize = eventList.size();

		MotionEvent.PointerProperties[] pointerProperties = new MotionEvent.PointerProperties[eventListSize];
		MotionEvent.PointerCoords[] pointerCoords = new MotionEvent.PointerCoords[eventListSize];

		TouchEvent event;
		MotionEvent.PointerProperties properties;
		MotionEvent.PointerCoords coords;
		TouchCoord touchCoord;

		for(int i = 0; i < eventListSize; i++){
			event = eventList.get(i);
			if(event == null || event.getId() == null || event.getTouchCoordinates() == null){
				continue;
			}

			properties = new MotionEvent.PointerProperties();
			properties.id = event.getId();
			properties.toolType = MotionEvent.TOOL_TYPE_FINGER;


			List<TouchCoord> coordList = event.getTouchCoordinates();
			if (coordList == null || coordList.size() == 0){ continue; }

			touchCoord = coordList.get(coordList.size() -1);
			if(touchCoord == null){ continue; }

			coords = new MotionEvent.PointerCoords();
			coords.x = touchCoord.getX() * touchScalar[0];
			coords.y = touchCoord.getY() * touchScalar[1];
			coords.orientation = 0;
			coords.pressure = 1.0f;
			coords.size = 1;

			//Add the info to lists only after we are sure we have all available info
			pointerProperties[i] = properties;
			pointerCoords[i] = coords;

		}


		if(sdlMotionEvent == null) {
			if (touchType == TouchType.BEGIN) {
				sdlMotionEvent = new SdlMotionEvent();
			}else{
				return  null;
			}
		}

		int eventAction = sdlMotionEvent.getMotionEvent(touchType, pointerProperties);
		long startTime = sdlMotionEvent.startOfEvent;

		//If the motion event should be finished we should clear our reference
		if(eventAction == MotionEvent.ACTION_UP || eventAction == MotionEvent.ACTION_CANCEL){
			sdlMotionEvent = null;
		}

		return MotionEvent.obtain(startTime, SystemClock.uptimeMillis(), eventAction, eventListSize, pointerProperties, pointerCoords, 0, 0,1,1,0,0, InputDevice.SOURCE_TOUCHSCREEN,0);
	}

	/**
	 * Keeps track of the current motion event for VPM
	 */
	private static class SdlMotionEvent{
		long startOfEvent;
		SparseIntArray pointerStatuses = new SparseIntArray();

		SdlMotionEvent(){
			startOfEvent = SystemClock.uptimeMillis();
		}

		/**
		 * Handles the SDL Touch Event to keep track of pointer status and returns the appropirate
		 * Android MotionEvent according to this events status
		 * @param touchType The SDL TouchType that was received from the module
		 * @param pointerProperties the parsed pointer properties built from the OnTouchEvent RPC
		 * @return the correct native Andorid MotionEvent action to dispatch
		 */
		synchronized int  getMotionEvent(TouchType touchType, MotionEvent.PointerProperties[] pointerProperties){
			int motionEvent = MotionEvent.ACTION_DOWN;
			switch (touchType){
				case BEGIN:
					if(pointerStatuses.size() == 0){
						//The motion event has just begun
						motionEvent = MotionEvent.ACTION_DOWN;
					}else{
						motionEvent = MotionEvent.ACTION_POINTER_DOWN;
					}
					setPointerStatuses(motionEvent, pointerProperties);
					break;
				case MOVE:
					motionEvent = MotionEvent.ACTION_MOVE;
					setPointerStatuses(motionEvent, pointerProperties);

					break;
				case END:
					//Clears out pointers that have ended
					setPointerStatuses(MotionEvent.ACTION_UP, pointerProperties);

					if(pointerStatuses.size() == 0){
						//The motion event has just ended
						motionEvent = MotionEvent.ACTION_UP;
					}else{
						motionEvent = MotionEvent.ACTION_POINTER_UP;
					}
					break;
				case CANCEL:
					//Assuming this cancels the entire event
					motionEvent = MotionEvent.ACTION_CANCEL;
					pointerStatuses.clear();
					break;
				default:
					break;
			}
			return motionEvent;
		}

		private void setPointerStatuses(int motionEvent, MotionEvent.PointerProperties[] pointerProperties){

			for(int i = 0; i < pointerProperties.length; i ++){
				MotionEvent.PointerProperties properties = pointerProperties[i];
				if(properties != null){
					if(motionEvent == MotionEvent.ACTION_UP || motionEvent == MotionEvent.ACTION_POINTER_UP){
						pointerStatuses.delete(properties.id);
					}else if(motionEvent == MotionEvent.ACTION_DOWN && properties.id == 0){
						pointerStatuses.put(properties.id, MotionEvent.ACTION_DOWN);
					}else{
						pointerStatuses.put(properties.id, motionEvent);
					}

				}
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

}