package com.smartdevicelink.lifecycle;

import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.util.SparseArray;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.proxy.rpc.AddSubMenuResponse;
import com.smartdevicelink.proxy.rpc.AlertManeuverResponse;
import com.smartdevicelink.proxy.rpc.AlertResponse;
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
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.OnAudioPassThru;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnHashChange;
import com.smartdevicelink.proxy.rpc.OnKeyboardInput;
import com.smartdevicelink.proxy.rpc.OnLanguageChange;
import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.OnStreamRPC;
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.OnTBTClientState;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.OnVehicleData;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.ReadDIDResponse;
import com.smartdevicelink.proxy.rpc.RegisterAppInterface;
import com.smartdevicelink.proxy.rpc.ResetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.ScrollableMessageResponse;
import com.smartdevicelink.proxy.rpc.SendLocationResponse;
import com.smartdevicelink.proxy.rpc.SetAppIconResponse;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimerResponse;
import com.smartdevicelink.proxy.rpc.ShowConstantTbtResponse;
import com.smartdevicelink.proxy.rpc.ShowResponse;
import com.smartdevicelink.proxy.rpc.SliderResponse;
import com.smartdevicelink.proxy.rpc.SpeakResponse;
import com.smartdevicelink.proxy.rpc.StreamRPCResponse;
import com.smartdevicelink.proxy.rpc.SubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.SystemRequestResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.UpdateTurnListResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

import java.util.ArrayList;

public final class SdlManager implements IProxyListenerALM {

    private static final String TAG = "SdlManager";

    private static final String WAKELOCK_TAG = "SdlStartWakeLock";

    private static final int CONNECTION_TIMEOUT = 60 * 1000;

    private static int autoIncCorId = 10;

    private static SdlManager mInstance;
    private static SdlConnectionConfig mConnectionConfig;
    private static ArrayList<SdlLifecycleListener> mLifecycleListeners;
    private static Context mContext;
    // private static SdlProxyLCM mProxy;
    private static SdlProxyALM mProxy;
    private static Class<? extends SdlLifecycleService> serviceClass;
    private static ISdlLifecycleService mService;

    private static final Object CONNECTION_LOCK = new Object();

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    // Connection maintenance objects
    private boolean isConnected;
    private WakeLock mWakeLock;
    private boolean isTryingToConnect;

    private static SparseArray<OnRPCNotificationListener> mNotificationListeners = new SparseArray<>();

    private static final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "Service Bound");
            mService = ((SdlLifecycleBinder) service).getSdlPersistentService();
            if(mInstance.isConnected){
                mService.onSdlConnected();
            }
            RegisterAppInterface inter = null;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    public static synchronized SdlManager getInstance() {
        if(mInstance != null) {
            return mInstance;
        } else {
            throw new IllegalStateException("SDL not initialized before first access.");
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    private SdlManager() {
        mLifecycleListeners = new ArrayList<>();
    }

    public static synchronized void initialize(Context context, SdlConnectionConfig config){
        if(mInstance == null) {
            Log.d(TAG, "initialize()");
            mInstance = new SdlManager();
            mConnectionConfig = config;
            serviceClass = config.serviceClass;

            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            if(adapter.isEnabled() && !adapter.getBondedDevices().isEmpty()){
                mInstance.connect(context);
            }
        }
    }

    synchronized void connect(Context context){
        if(!isTryingToConnect) {
            isTryingToConnect = true;
            Log.d(TAG, "connect()");

            mContext = context;

            Intent serviceIntent = new Intent(mContext, serviceClass);
            mContext.bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

            AsyncTask task = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {

                    if (mWakeLock == null) {
                        PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
                        mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKELOCK_TAG);
                    }
                    mWakeLock.acquire();

                    if (mProxy == null) {
                        try {
                            Log.d(TAG, "Makin a proxy!");
                            // mProxy = new SdlProxyLCM(SdlManager.getInstance(), mConnectionConfig);
                            mProxy = new SdlProxyALM(SdlManager.getInstance(),
                                    mConnectionConfig.sdlProxyConfigurationResources,
                                    mConnectionConfig.appName,
                                    mConnectionConfig.ttsName,
                                    mConnectionConfig.ngnMediaScreenAppName,
                                    mConnectionConfig.vrSynonyms,
                                    mConnectionConfig.isMediaApp,
                                    mConnectionConfig.sdlMsgVersion,
                                    mConnectionConfig.languageDesired,
                                    mConnectionConfig.hmiDisplayLanguageDesired,
                                    mConnectionConfig.appType,
                                    mConnectionConfig.appID,
                                    mConnectionConfig.autoActivateID,
                                    mConnectionConfig.callbackToUIThread,
                                    mConnectionConfig.preRegister,
                                    mConnectionConfig.sHashID,
                                    mConnectionConfig.transportConfig);
                        } catch (SdlException e) {
                            e.printStackTrace();
                        }
                    }

                    mHandler.removeCallbacks(CHECK_CONNECTION_RUNNABLE);
                    mHandler.postDelayed(CHECK_CONNECTION_RUNNABLE, CONNECTION_TIMEOUT);
                    return null;
                }
            };

            task.execute();
        } else {
            mHandler.removeCallbacks(CHECK_CONNECTION_RUNNABLE);
            mHandler.postDelayed(CHECK_CONNECTION_RUNNABLE, CONNECTION_TIMEOUT);
        }

    }

    synchronized void disconnect(){
        Log.d(TAG, "disconnect()");
        if(mProxy != null){
            try {
                mProxy.dispose();
                mProxy = null;
            } catch (SdlException e) {
                e.printStackTrace();
            }
        }
    }

    public void addNotificationListener(FunctionID id, OnRPCNotificationListener listener){
        mNotificationListeners.append(id.getId(), listener);
        if(mProxy != null){
            mProxy.addOnRPCNotificationListener(id, listener);
        }
    }

    public void removeNotificationListener(FunctionID id, OnRPCNotificationListener listener){
        mNotificationListeners.remove(id.getId());
        if(mProxy != null){
            mProxy.removeOnRPCNotificationListener(id);
        }
    }

    public void addLifeCycleListener(SdlLifecycleListener listener){
        mLifecycleListeners.add(listener);
    }

    public void removeLifeCycleListener(FunctionID id, SdlLifecycleListener listener){
        mLifecycleListeners.remove(listener);
    }

    public void sendRpc(RPCRequest request){
        if(mProxy != null && isConnected){
            try {
                request.setCorrelationID(autoIncCorId++);
                mProxy.sendRPCRequest(request);
            } catch (SdlException e) {
                e.printStackTrace();
            }
        }
    }

    private final Runnable CHECK_CONNECTION_RUNNABLE = new Runnable() {
        @Override
        public void run() {
            if(!isConnected) {
                Log.d(TAG, "Connection Runnable");
                disconnect();
                if(mWakeLock.isHeld()){
                    mWakeLock.release();
                }
            }
        }
    };

    @Override
    synchronized public void onOnHMIStatus(OnHMIStatus notification) {
        if(!isConnected) {
            isConnected = true;
            mHandler.removeCallbacks(CHECK_CONNECTION_RUNNABLE);

            if(mService != null){
                mService.onSdlConnected();
            }

            if(mWakeLock.isHeld()){
                mWakeLock.release();
            }

            // TODO: Send AppIcon
        }

        for(SdlLifecycleListener listener: mLifecycleListeners){
            listener.onHmiStatusChanged(notification.getHmiLevel());
        }
    }

    @Override
    synchronized public void onProxyClosed(String info, Exception e, SdlDisconnectedReason reason) {
        for(SdlLifecycleListener listener: mLifecycleListeners){
            listener.onSdlDisconnected();
        }
        if(mService != null){
            mService.onSdlDisconnected();
            mContext.unbindService(mServiceConnection);
        }
        mInstance.isConnected = false;
        mInstance.isTryingToConnect = false;
    }

    @Override
    public void onError(String info, Exception e) {
        Log.w(TAG, "onError()");
        Log.w(TAG, info);
    }

    @Override
    public void onOnLockScreenNotification(OnLockScreenStatus notification) {
        for(SdlLifecycleListener listener: mLifecycleListeners){
            listener.onLockScreenStatusChanged(notification.getShowLockScreen());
        }
    }

    // TODO: Don't think these are needed
    @Override
    public void onServiceEnded(OnServiceEnded serviceEnded) {

    }

    @Override
    public void onServiceNACKed(OnServiceNACKed serviceNACKed) {

    }

    @Override
    public void onServiceDataACK() {

    }

    // Everything below here is covered by OnRPCNotificationListener
    @Override
    public void onOnStreamRPC(OnStreamRPC notification) {

    }

    @Override
    public void onOnCommand(OnCommand notification) {

    }

    @Override
    public void onOnButtonEvent(OnButtonEvent notification) {

    }

    @Override
    public void onOnButtonPress(OnButtonPress notification) {

    }

    @Override
    public void onOnLanguageChange(OnLanguageChange notification) {

    }

    @Override
    public void onOnHashChange(OnHashChange notification) {

    }

    @Override
    public void onOnPermissionsChange(OnPermissionsChange notification) {

    }

    @Override
    public void onOnVehicleData(OnVehicleData notification) {

    }

    @Override
    public void onOnAudioPassThru(OnAudioPassThru notification) {

    }

    @Override
    public void onOnDriverDistraction(OnDriverDistraction notification) {

    }

    @Override
    public void onOnTBTClientState(OnTBTClientState notification) {

    }

    @Override
    public void onOnSystemRequest(OnSystemRequest notification) {

    }

    @Override
    public void onOnKeyboardInput(OnKeyboardInput notification) {

    }

    @Override
    public void onOnTouchEvent(OnTouchEvent notification) {

    }

    // Everything below here is covered by OnRPCResponseListener
    @Override
    public void onStreamRPCResponse(StreamRPCResponse response) {

    }

    @Override
    public void onGenericResponse(GenericResponse response) {

    }

    @Override
    public void onAddCommandResponse(AddCommandResponse response) {

    }

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
    public void onResetGlobalPropertiesResponse(ResetGlobalPropertiesResponse response) {

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

    }

    @Override
    public void onSubscribeButtonResponse(SubscribeButtonResponse response) {

    }

    @Override
    public void onUnsubscribeButtonResponse(UnsubscribeButtonResponse response) {

    }

    @Override
    public void onSubscribeVehicleDataResponse(SubscribeVehicleDataResponse response) {

    }

    @Override
    public void onUnsubscribeVehicleDataResponse(UnsubscribeVehicleDataResponse response) {

    }

    @Override
    public void onGetVehicleDataResponse(GetVehicleDataResponse response) {

    }

    @Override
    public void onPerformAudioPassThruResponse(PerformAudioPassThruResponse response) {

    }

    @Override
    public void onEndAudioPassThruResponse(EndAudioPassThruResponse response) {

    }

    @Override
    public void onPutFileResponse(PutFileResponse response) {

    }

    @Override
    public void onDeleteFileResponse(DeleteFileResponse response) {

    }

    @Override
    public void onListFilesResponse(ListFilesResponse response) {

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
    public void onSliderResponse(SliderResponse response) {

    }

    @Override
    public void onSystemRequestResponse(SystemRequestResponse response) {

    }

    @Override
    public void onDiagnosticMessageResponse(DiagnosticMessageResponse response) {

    }

    @Override
    public void onReadDIDResponse(ReadDIDResponse response) {

    }

    @Override
    public void onGetDTCsResponse(GetDTCsResponse response) {

    }

    @Override
    public void onDialNumberResponse(DialNumberResponse response) {

    }

    @Override
    public void onSendLocationResponse(SendLocationResponse response) {

    }

    @Override
    public void onShowConstantTbtResponse(ShowConstantTbtResponse response) {

    }

    @Override
    public void onAlertManeuverResponse(AlertManeuverResponse response) {

    }

    @Override
    public void onUpdateTurnListResponse(UpdateTurnListResponse response) {

    }
}
