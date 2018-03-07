package com.smartdevicelink.test.proxy;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.test.AndroidTestCase;
import android.util.Log;

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
import com.smartdevicelink.streaming.video.SdlRemoteDisplay;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.test.streaming.video.SdlRemoteDisplayTest;

import junit.framework.Assert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class SdlProxyBaseTests extends AndroidTestCase{
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
        SdlProxyConfigurationResources config = new SdlProxyConfigurationResources("path", (TelephonyManager)getContext().getSystemService(Context.TELEPHONY_SERVICE));
        //Construct with a non-null SdlProxyConfigurationResources
        builder.setSdlProxyConfigurationResources(config);
        try {
            proxy = builder.build();
        } catch (Exception e) {
            Log.v(TAG, "Exception in testNullSdlProxyConfigurationResources, testing non null SdlProxyConfigurationResources");
            if (!(e instanceof SdlException) || !((SdlException) e).getSdlExceptionCause().equals(SdlExceptionCause.BLUETOOTH_ADAPTER_NULL)) {
                Assert.fail("Exception in testNullSdlProxyConfigurationResources");
            }
        }

        //Construct with a null SdlProxyConfigurationResources
        builder.setSdlProxyConfigurationResources(null);
        try {
            proxy = builder.build();
        } catch (Exception e) {
            Log.v(TAG, "Exception in testNullSdlProxyConfigurationResources, testing null SdlProxyConfigurationResources");
            if (!(e instanceof SdlException) || !((SdlException) e).getSdlExceptionCause().equals(SdlExceptionCause.BLUETOOTH_ADAPTER_NULL)) {
                Assert.fail("Exception in testNullSdlProxyConfigurationResources, testing null SdlProxyConfigurationResources");
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
    }
}
