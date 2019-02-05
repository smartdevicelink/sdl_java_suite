package com.smartdevicelink.test.proxy;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.SdlExceptionCause;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.SdlProxyBase;
import com.smartdevicelink.proxy.SdlProxyBuilder;
import com.smartdevicelink.proxy.SdlProxyConfigurationResources;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.proxy.rpc.AddSubMenuResponse;
import com.smartdevicelink.proxy.rpc.AlertManeuverResponse;
import com.smartdevicelink.proxy.rpc.AlertResponse;
import com.smartdevicelink.proxy.rpc.ButtonPressResponse;
import com.smartdevicelink.proxy.rpc.ChangeRegistrationResponse;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSetResponse;
import com.smartdevicelink.proxy.rpc.DeleteCommandResponse;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSetResponse;
import com.smartdevicelink.proxy.rpc.DeleteSubMenuResponse;
import com.smartdevicelink.proxy.rpc.DiagnosticMessageResponse;
import com.smartdevicelink.proxy.rpc.DialNumberResponse;
import com.smartdevicelink.proxy.rpc.EndAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.GenericResponse;
import com.smartdevicelink.proxy.rpc.GetDTCsResponse;
import com.smartdevicelink.proxy.rpc.GetInteriorVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.GetSystemCapabilityResponse;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.GetWayPointsResponse;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.OnAudioPassThru;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnHashChange;
import com.smartdevicelink.proxy.rpc.OnInteriorVehicleData;
import com.smartdevicelink.proxy.rpc.OnKeyboardInput;
import com.smartdevicelink.proxy.rpc.OnLanguageChange;
import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.OnRCStatus;
import com.smartdevicelink.proxy.rpc.OnStreamRPC;
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.OnTBTClientState;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.OnVehicleData;
import com.smartdevicelink.proxy.rpc.OnWayPointChange;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.ReadDIDResponse;
import com.smartdevicelink.proxy.rpc.ResetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.ScrollableMessageResponse;
import com.smartdevicelink.proxy.rpc.SendHapticDataResponse;
import com.smartdevicelink.proxy.rpc.SendLocationResponse;
import com.smartdevicelink.proxy.rpc.SetAppIconResponse;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.SetInteriorVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimerResponse;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.ShowConstantTbtResponse;
import com.smartdevicelink.proxy.rpc.ShowResponse;
import com.smartdevicelink.proxy.rpc.SliderResponse;
import com.smartdevicelink.proxy.rpc.SpeakResponse;
import com.smartdevicelink.proxy.rpc.StreamRPCResponse;
import com.smartdevicelink.proxy.rpc.SubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.SubscribeWayPointsResponse;
import com.smartdevicelink.proxy.rpc.SystemRequestResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeWayPointsResponse;
import com.smartdevicelink.proxy.rpc.UpdateTurnListResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.streaming.video.SdlRemoteDisplay;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.test.streaming.video.SdlRemoteDisplayTest;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.streaming.audio.AudioStreamingCodec;
import com.smartdevicelink.streaming.audio.AudioStreamingParams;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.proxy.rpc.enums.TouchType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;

import junit.framework.Assert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.lang.reflect.Constructor;

public class SdlProxyBaseTests extends AndroidTestCase2 {
    public static final String TAG = "SdlProxyBaseTests";

    @Override
    protected void setUp() throws Exception{
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        //Nothing here for now
    }

    /**
     * Test SdlProxyBase for handling null SdlProxyConfigurationResources
     */
    public void testNullSdlProxyConfigurationResources() {
        SdlProxyALM proxy = null;
        SdlProxyBuilder.Builder builder = new SdlProxyBuilder.Builder(new ProxyListenerTest(), "appId", "appName", true, getContext());
        SdlProxyConfigurationResources config = new SdlProxyConfigurationResources("path", (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE));
        //Construct with a non-null SdlProxyConfigurationResources
        builder.setSdlProxyConfigurationResources(config);
        try {
            proxy = builder.build();
        } catch (Exception e) {
            Log.v(TAG, "Exception in testNullSdlProxyConfigurationResources, testing non null SdlProxyConfigurationResources");
            if (!(e instanceof SdlException) || !((SdlException) e).getSdlExceptionCause().equals(SdlExceptionCause.BLUETOOTH_ADAPTER_NULL)) {
                e.printStackTrace();
                Assert.fail("Exception in testNullSdlProxyConfigurationResources - \n" + e.toString());
            }
        }

        if (proxy != null) {
            try {
                proxy.dispose();
                proxy = null;
            }catch(SdlException e){
                e.printStackTrace();
            }
        }

        //Construct with a null SdlProxyConfigurationResources
        builder.setSdlProxyConfigurationResources(null);
        try {
            proxy = builder.build();
        } catch (Exception e) {
            Log.v(TAG, "Exception in testNullSdlProxyConfigurationResources, testing null SdlProxyConfigurationResources");
            if (!(e instanceof SdlException) || !((SdlException) e).getSdlExceptionCause().equals(SdlExceptionCause.BLUETOOTH_ADAPTER_NULL)) {
                e.printStackTrace();
                Assert.fail("Exception in testNullSdlProxyConfigurationResources, testing null SdlProxyConfigurationResources");
            }
        }
        if (proxy != null) {
            try {
                proxy.dispose();
                proxy = null;
            }catch(SdlException e){
                e.printStackTrace();
            }
        }

        //Construct with a non-null SdlProxyConfigurationResources and a null TelephonyManager
        config.setTelephonyManager(null);
        builder.setSdlProxyConfigurationResources(config);
        try {
            proxy = builder.build();
        } catch (Exception e) {
            Log.v(TAG, "Exception in testNullSdlProxyConfigurationResources, testing null TelephonyManager");
            if (!(e instanceof SdlException) || !((SdlException) e).getSdlExceptionCause().equals(SdlExceptionCause.BLUETOOTH_ADAPTER_NULL)) {
                Assert.fail("Exception in testNullSdlProxyConfigurationResources, testing null TelephonyManager");
            }
        }
        if (proxy != null) {
            try {
                proxy.dispose();
                proxy = null;
            }catch(SdlException e){
                e.printStackTrace();
            }
        }
    }

    public void testRemoteDisplayStreaming(){
        SdlProxyALM proxy = null;
        SdlProxyBuilder.Builder builder = new SdlProxyBuilder.Builder(new ProxyListenerTest(), "appId", "appName", true, getContext());
        try{
            proxy = builder.build();
            //	public void startRemoteDisplayStream(Context context, final Class<? extends SdlRemoteDisplay> remoteDisplay, final VideoStreamingParameters parameters, final boolean encrypted){
            Method m = SdlProxyALM.class.getDeclaredMethod("startRemoteDisplayStream", Context.class, SdlRemoteDisplay.class, VideoStreamingParameters.class, boolean.class);
            assertNotNull(m);
            m.setAccessible(true);
            m.invoke(proxy,getContext(), SdlRemoteDisplayTest.MockRemoteDisplay.class, (VideoStreamingParameters)null, false);
            assert true;

        }catch (Exception e){
            assert false;
        }
    }

    public void testMultipleRPCSendSynchronous() {

		List<RPCRequest> rpcs = new ArrayList<>();

		// rpc 1
		Show show = new Show();
		show.setMainField1("hey y'all");
		show.setMainField2("");
		show.setMainField3("");
		show.setMainField4("");
		rpcs.add(show);

		// rpc 2
		Show show2 = new Show();
		show2.setMainField1("");
		show2.setMainField2("It is Wednesday My Dudes");
		show2.setMainField3("");
		show2.setMainField4("");
		rpcs.add(show2);

		OnMultipleRequestListener mrl = new OnMultipleRequestListener() {
			@Override
			public void onUpdate(int remainingRequests) {

			}

			@Override
			public void onFinished() {

			}

			@Override
			public void onError(int correlationId, Result resultCode, String info) {
				assert false;
			}

			@Override
			public void onResponse(int correlationId, RPCResponse response) {

			}
		};
		try{
			// public void sendRequests(List<RPCRequest> rpcs, final OnMultipleRequestListener listener) throws SdlException {
			Method m = SdlProxyBase.class.getDeclaredMethod("sendRequests", SdlProxyBase.class);
			assertNotNull(m);
			m.setAccessible(true);
			m.invoke(rpcs,mrl);
			assert true;

		}catch (Exception e){
			assert false;
		}
	}

	public void testMultipleRPCSendAsynchronous() {

		List<RPCRequest> rpcs = new ArrayList<>();

		// rpc 1
		Show show = new Show();
		show.setMainField1("hey y'all");
		show.setMainField2("");
		show.setMainField3("");
		show.setMainField4("");
		rpcs.add(show);

		// rpc 2
		Show show2 = new Show();
		show2.setMainField1("");
		show2.setMainField2("It is Wednesday My Dudes");
		show2.setMainField3("");
		show2.setMainField4("");
		rpcs.add(show2);

		OnMultipleRequestListener mrl = new OnMultipleRequestListener() {
			@Override
			public void onUpdate(int remainingRequests) {

			}

			@Override
			public void onFinished() {

			}

			@Override
			public void onError(int correlationId, Result resultCode, String info) {
				assert false;
			}

			@Override
			public void onResponse(int correlationId, RPCResponse response) {

			}
		};
		try{
			// public void sendSequentialRequests(List<RPCRequest> rpcs, final OnMultipleRequestListener listener) throws SdlException {
			Method m = SdlProxyBase.class.getDeclaredMethod("sendSequentialRequests", SdlProxyBase.class);
			assertNotNull(m);
			m.setAccessible(true);
			m.invoke(rpcs,mrl);
			assert true;

		}catch (Exception e){
			assert false;
		}
	}

	public void testMultiTouchUpDown() {
        SdlProxyBuilder.Builder builder = new SdlProxyBuilder.Builder(new ProxyListenerTest(), "appId", "appName", true, getContext());
        try{
            ClassLoader loader = this.getContext().getClassLoader();
            Class videoStreamingManagerClass = loader.loadClass("com.smartdevicelink.proxy.SdlProxyBase$VideoStreamingManager");

            Class[] parameterTypes = new Class[]{SdlProxyBase.class, Context.class, ISdl.class};
            Constructor constructor = videoStreamingManagerClass.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);

            // Get VideoStreamingManager Instance
            Object target = constructor.newInstance((SdlProxyBase)builder.build(), null, _internalInterface);

            // Enable call convertTouchEvent
            Method method = videoStreamingManagerClass.getDeclaredMethod("convertTouchEvent", OnTouchEvent.class);
            method.setAccessible(true);

            // Initialize touch event (Touch ID:100)
            TouchEvent touchEvent = Test.GENERAL_TOUCHEVENT;

            // Initialize touch event (Touch ID:101)
            TouchEvent touchEvent2 = new TouchEvent();
            touchEvent2.setId(touchEvent.getId() + 1);
            touchEvent2.setTimestamps(Test.GENERAL_LONG_LIST);
            touchEvent2.setTouchCoordinates(new ArrayList<TouchCoord>(Arrays.asList(new TouchCoord(Test.GENERAL_TOUCHCOORD.getX() + 1, Test.GENERAL_TOUCHCOORD.getY() + 1))));

            // Touch one pointer (Touch ID:100)
            OnTouchEvent testOnTouchEvent = new OnTouchEvent();
            testOnTouchEvent.setEvent(Collections.singletonList(touchEvent));
            testOnTouchEvent.setType(Test.GENERAL_TOUCHTYPE);
            List<MotionEvent> events = (List<MotionEvent>)method.invoke(target, testOnTouchEvent);
            assertEquals(MotionEvent.ACTION_DOWN, events.get(0).getAction());

            // Touch another pointer (Touch ID:101)
            testOnTouchEvent.setEvent(Collections.singletonList(touchEvent2));
            testOnTouchEvent.setType(Test.GENERAL_TOUCHTYPE);
            events = (List<MotionEvent>)method.invoke(target, testOnTouchEvent);
            assertEquals(MotionEvent.ACTION_POINTER_DOWN | 1 << MotionEvent.ACTION_POINTER_INDEX_SHIFT, events.get(0).getAction());

            // Release one of the pointers (Touch ID:101)
            testOnTouchEvent.setEvent(Collections.singletonList(touchEvent2));
            testOnTouchEvent.setType(TouchType.END);
            events = (List<MotionEvent>)method.invoke(target, testOnTouchEvent);
            assertEquals(MotionEvent.ACTION_POINTER_UP | 1 << MotionEvent.ACTION_POINTER_INDEX_SHIFT, events.get(0).getAction());

            // Release the other pointer (Touch ID:100)
            testOnTouchEvent.setEvent(Collections.singletonList(touchEvent));
            testOnTouchEvent.setType(TouchType.END);
            events = (List<MotionEvent>)method.invoke(target, testOnTouchEvent);
            assertEquals(MotionEvent.ACTION_UP, events.get(0).getAction());

            assert true;

        }catch (Exception e){
            assert false;
        }
    }

    public void testMultiBeginTouch() {
        SdlProxyBuilder.Builder builder = new SdlProxyBuilder.Builder(new ProxyListenerTest(), "appId", "appName", true, getContext());
        try{
            ClassLoader loader = this.getContext().getClassLoader();
            Class videoStreamingManagerClass = loader.loadClass("com.smartdevicelink.proxy.SdlProxyBase$VideoStreamingManager");

            Class[] parameterTypes = new Class[]{SdlProxyBase.class, Context.class, ISdl.class};
            Constructor constructor = videoStreamingManagerClass.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);

            // Get VideoStreamingManager Instance
            Object target = constructor.newInstance((SdlProxyBase)builder.build(), null, _internalInterface);

            // Enable call convertTouchEvent
            Method method = videoStreamingManagerClass.getDeclaredMethod("convertTouchEvent", OnTouchEvent.class);
            method.setAccessible(true);

            // Initialize touch event (Touch ID:100)
            TouchEvent touchEvent = Test.GENERAL_TOUCHEVENT;

            // Initialize touch event (Touch ID:101)
            TouchEvent touchEvent2 = new TouchEvent();
            touchEvent2.setId(touchEvent.getId() + 1);
            touchEvent2.setTimestamps(Test.GENERAL_LONG_LIST);
            touchEvent2.setTouchCoordinates(new ArrayList<TouchCoord>(Arrays.asList(new TouchCoord(Test.GENERAL_TOUCHCOORD.getX() + 1, Test.GENERAL_TOUCHCOORD.getY() + 1))));

            // Touch multi pointer (Touch ID:100, 101)
            OnTouchEvent testOnTouchEvent = new OnTouchEvent();
            testOnTouchEvent.setType(Test.GENERAL_TOUCHTYPE);
            testOnTouchEvent.setEvent(Arrays.asList(touchEvent, touchEvent2));
            List<MotionEvent> events = (List<MotionEvent>)method.invoke(target, testOnTouchEvent);
            assertEquals(MotionEvent.ACTION_DOWN, events.get(0).getAction());
            assertEquals(MotionEvent.ACTION_POINTER_DOWN | 1 << MotionEvent.ACTION_POINTER_INDEX_SHIFT, events.get(1).getAction());

            assert true;

        }catch (Exception e){
            assert false;
        }
    }

    public void testMultiTouchOneFingerMove() {
        SdlProxyBuilder.Builder builder = new SdlProxyBuilder.Builder(new ProxyListenerTest(), "appId", "appName", true, getContext());
        try{
            ClassLoader loader = this.getContext().getClassLoader();
            Class videoStreamingManagerClass = loader.loadClass("com.smartdevicelink.proxy.SdlProxyBase$VideoStreamingManager");

            Class[] parameterTypes = new Class[]{SdlProxyBase.class, Context.class, ISdl.class};
            Constructor constructor = videoStreamingManagerClass.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);

            // Get VideoStreamingManager Instance
            Object target = constructor.newInstance((SdlProxyBase)builder.build(), null, _internalInterface);

            // Enable call convertTouchEvent
            Method method = videoStreamingManagerClass.getDeclaredMethod("convertTouchEvent", OnTouchEvent.class);
            method.setAccessible(true);

            // Initialize touch event (Touch ID:100)
            TouchEvent touchEvent = Test.GENERAL_TOUCHEVENT;

            // Initialize touch event (Touch ID:101)
            TouchEvent touchEvent2 = new TouchEvent();
            touchEvent2.setId(touchEvent.getId() + 1);
            touchEvent2.setTimestamps(Test.GENERAL_LONG_LIST);
            touchEvent2.setTouchCoordinates(new ArrayList<TouchCoord>(Arrays.asList(new TouchCoord(Test.GENERAL_TOUCHCOORD.getX() + 1, Test.GENERAL_TOUCHCOORD.getY() + 1))));

            // Touch multi pointer (Touch ID:100, 101)
            OnTouchEvent testOnTouchEvent = new OnTouchEvent();
            testOnTouchEvent.setType(Test.GENERAL_TOUCHTYPE);
            testOnTouchEvent.setEvent(Arrays.asList(touchEvent, touchEvent2));
            method.invoke(target, testOnTouchEvent);

            // Move pointer (Touch ID:101)
            testOnTouchEvent.setType(TouchType.MOVE);
            testOnTouchEvent.setEvent(Arrays.asList(touchEvent2));
            List<MotionEvent> events = (List<MotionEvent>)method.invoke(target, testOnTouchEvent);
            assertEquals(MotionEvent.ACTION_MOVE, events.get(0).getAction());
            assertEquals(2, events.get(0).getPointerCount());

            assert true;

        }catch (Exception e){
            assert false;
        }
    }

    private ISdl _internalInterface = new ISdl() {
        @Override
        public void start() { }

        @Override
        public void stop() { }

        @Override
        public boolean isConnected() { return false; }

        @Override
        public void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) { }

        @Override
        public void removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) { }

        @Override
        public void startVideoService(VideoStreamingParameters parameters, boolean encrypted) { }

        @Override
        public void stopVideoService() { }

        @Override
        public void stopAudioService() { }

        @Override
        public void sendRPCRequest(RPCRequest message){ }

        @Override
        public void sendRequests(List<? extends RPCRequest> rpcs, OnMultipleRequestListener listener) { }

        @Override
        public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {}

        @Override
        public boolean removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) { return false; }

        @Override
        public void addOnRPCListener(FunctionID responseId, OnRPCListener listener) { }

        @Override
        public boolean removeOnRPCListener(FunctionID responseId, OnRPCListener listener) { return false; }

        @Override
        public Object getCapability(SystemCapabilityType systemCapabilityType){ return null; }

        @Override
        public void getCapability(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener scListener) { }

        @Override
        public SdlMsgVersion getSdlMsgVersion(){ return null; }

        @Override
        public com.smartdevicelink.util.Version getProtocolVersion() { return null; }

        @Override
        public boolean isCapabilitySupported(SystemCapabilityType systemCapabilityType){ return false; }

        @Override
        public void addOnSystemCapabilityListener(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener listener) { }

        @Override
        public boolean removeOnSystemCapabilityListener(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener listener) { return false; }

        @Override
        public boolean isTransportForServiceAvailable(SessionType serviceType) { return false; }

        @Override
        public void startAudioService(boolean isEncrypted, AudioStreamingCodec codec, AudioStreamingParams params) { }

        @Override
        public void startAudioService(boolean encrypted) { }

        @Override
        public IVideoStreamListener startVideoStream(boolean isEncrypted, VideoStreamingParameters parameters){ return null; }

        @Override
        public IAudioStreamListener startAudioStream(boolean isEncrypted, AudioStreamingCodec codec, AudioStreamingParams params) { return null; }
    };

    public class ProxyListenerTest implements IProxyListenerALM {

        @Override
        public void onProxyClosed(String s, Exception e, SdlDisconnectedReason reason) {

        }

        @Override
        public void onOnHMIStatus(OnHMIStatus status) {

        }

        @Override
        public void onListFilesResponse(ListFilesResponse response) {
        }

        @Override
        public void onPutFileResponse(PutFileResponse response) {
        }

        @Override
        public void onOnLockScreenNotification(OnLockScreenStatus notification) {
        }

        @Override
        public void onOnCommand(OnCommand notification){
        }

        /**
         *  Callback method that runs when the add command response is received from SDL.
         */
        @Override
        public void onAddCommandResponse(AddCommandResponse response) {
        }

        @Override
        public void onOnPermissionsChange(OnPermissionsChange notification) {

        }

        @Override
        public void onSubscribeVehicleDataResponse(SubscribeVehicleDataResponse response) {
        }

        @Override
        public void onOnVehicleData(OnVehicleData notification) {
        }

        /**
         * Rest of the SDL callbacks from the head unit
         */

        @Override
        public void onAddSubMenuResponse(AddSubMenuResponse response) {
        }

        @Override
        public void onCreateInteractionChoiceSetResponse(CreateInteractionChoiceSetResponse response) {
        }

        @Override
        public void onAlertResponse(AlertResponse response) {
        }

        @Override
        public void onDeleteCommandResponse(DeleteCommandResponse response) {
        }

        @Override
        public void onDeleteInteractionChoiceSetResponse(DeleteInteractionChoiceSetResponse response) {
        }

        @Override
        public void onDeleteSubMenuResponse(DeleteSubMenuResponse response) {
        }

        @Override
        public void onPerformInteractionResponse(PerformInteractionResponse response) {
        }

        @Override
        public void onResetGlobalPropertiesResponse(
                ResetGlobalPropertiesResponse response) {
        }

        @Override
        public void onSetGlobalPropertiesResponse(SetGlobalPropertiesResponse response) {
        }

        @Override
        public void onSetMediaClockTimerResponse(SetMediaClockTimerResponse response) {
        }

        @Override
        public void onShowResponse(ShowResponse response) {
        }

        @Override
        public void onSpeakResponse(SpeakResponse response) {
            Log.i(TAG, "SpeakCommand response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
        }

        @Override
        public void onOnButtonEvent(OnButtonEvent notification) {
        }

        @Override
        public void onOnButtonPress(OnButtonPress notification) {
        }

        @Override
        public void onSubscribeButtonResponse(SubscribeButtonResponse response) {
        }

        @Override
        public void onUnsubscribeButtonResponse(UnsubscribeButtonResponse response) {
        }


        @Override
        public void onOnTBTClientState(OnTBTClientState notification) {
        }

        @Override
        public void onUnsubscribeVehicleDataResponse(
                UnsubscribeVehicleDataResponse response) {

        }

        @Override
        public void onGetVehicleDataResponse(GetVehicleDataResponse response) {

        }

        @Override
        public void onReadDIDResponse(ReadDIDResponse response) {

        }

        @Override
        public void onGetDTCsResponse(GetDTCsResponse response) {

        }


        @Override
        public void onPerformAudioPassThruResponse(PerformAudioPassThruResponse response) {

        }

        @Override
        public void onEndAudioPassThruResponse(EndAudioPassThruResponse response) {

        }

        @Override
        public void onOnAudioPassThru(OnAudioPassThru notification) {

        }

        @Override
        public void onDeleteFileResponse(DeleteFileResponse response) {

        }

        @Override
        public void onSetAppIconResponse(SetAppIconResponse response) {

        }

        @Override
        public void onScrollableMessageResponse(ScrollableMessageResponse response) {

        }

        @Override
        public void onChangeRegistrationResponse(ChangeRegistrationResponse response) {

        }

        @Override
        public void onSetDisplayLayoutResponse(SetDisplayLayoutResponse response) {

        }

        @Override
        public void onOnLanguageChange(OnLanguageChange notification) {

        }

        @Override
        public void onSliderResponse(SliderResponse response) {

        }


        @Override
        public void onOnHashChange(OnHashChange notification) {

        }

        @Override
        public void onOnSystemRequest(OnSystemRequest notification) {
        }

        @Override
        public void onSystemRequestResponse(SystemRequestResponse response) {

        }

        @Override
        public void onOnKeyboardInput(OnKeyboardInput notification) {

        }

        @Override
        public void onOnTouchEvent(OnTouchEvent notification) {

        }

        @Override
        public void onDiagnosticMessageResponse(DiagnosticMessageResponse response) {

        }

        @Override
        public void onOnStreamRPC(OnStreamRPC notification) {

        }

        @Override
        public void onStreamRPCResponse(StreamRPCResponse response) {

        }

        @Override
        public void onDialNumberResponse(DialNumberResponse response) {

        }

        @Override
        public void onSendLocationResponse(SendLocationResponse response) {
            Log.i(TAG, "SendLocation response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());

        }

        @Override
        public void onServiceEnded(OnServiceEnded serviceEnded) {

        }

        @Override
        public void onServiceNACKed(OnServiceNACKed serviceNACKed) {

        }

        @Override
        public void onShowConstantTbtResponse(ShowConstantTbtResponse response) {
            Log.i(TAG, "ShowConstantTbt response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());

        }

        @Override
        public void onAlertManeuverResponse(AlertManeuverResponse response) {
            Log.i(TAG, "AlertManeuver response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());

        }

        @Override
        public void onUpdateTurnListResponse(UpdateTurnListResponse response) {
            Log.i(TAG, "UpdateTurnList response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());

        }

        @Override
        public void onServiceDataACK(int dataSize) {
        }

        @Override
        public void onGetWayPointsResponse(GetWayPointsResponse response) {
            Log.i(TAG, "GetWayPoints response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
        }

        @Override
        public void onSubscribeWayPointsResponse(SubscribeWayPointsResponse response) {
            Log.i(TAG, "SubscribeWayPoints response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
        }

        @Override
        public void onUnsubscribeWayPointsResponse(UnsubscribeWayPointsResponse response) {
            Log.i(TAG, "UnsubscribeWayPoints response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
        }

        @Override
        public void onOnWayPointChange(OnWayPointChange notification) {
            Log.i(TAG, "OnWayPointChange notification from SDL: " + notification);
        }

        @Override
        public void onGetSystemCapabilityResponse(GetSystemCapabilityResponse response) {
            Log.i(TAG, "GetSystemCapability response from SDL: " + response);
        }

        @Override
        public void onGetInteriorVehicleDataResponse(GetInteriorVehicleDataResponse response) {
            Log.i(TAG, "GetInteriorVehicleData response from SDL: " + response);
        }

        @Override
        public void onButtonPressResponse(ButtonPressResponse response) {
            Log.i(TAG, "ButtonPress response from SDL: " + response);
        }

        @Override
        public void onSetInteriorVehicleDataResponse(SetInteriorVehicleDataResponse response) {
            Log.i(TAG, "SetInteriorVehicleData response from SDL: " + response);
        }

        @Override
        public void onOnInteriorVehicleData(OnInteriorVehicleData notification) {

        }

        @Override
        public void onOnDriverDistraction(OnDriverDistraction notification) {
            // Some RPCs (depending on region) cannot be sent when driver distraction is active.
        }

        @Override
        public void onError(String info, Exception e) {
        }

        @Override
        public void onGenericResponse(GenericResponse response) {
            Log.i(TAG, "Generic response from SDL: " + response.getResultCode().name() + " Info: " + response.getInfo());
        }

        @Override
		public void onSendHapticDataResponse(SendHapticDataResponse response) {
			Log.i(TAG, "SendHapticDataResponse response from SDL: " + response);
		}

		@Override
		public void onOnRCStatus(OnRCStatus notification) {
		}
	}
}
