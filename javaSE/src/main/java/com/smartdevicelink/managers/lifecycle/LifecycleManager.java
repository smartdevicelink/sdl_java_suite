package com.smartdevicelink.managers.lifecycle;

import android.support.annotation.NonNull;
import android.util.Log;
import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.MessageType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.*;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.interfaces.*;
import com.smartdevicelink.proxy.rpc.*;
import com.smartdevicelink.proxy.rpc.enums.*;
import com.smartdevicelink.proxy.rpc.listeners.*;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.streaming.StreamRPCPacketizer;
import com.smartdevicelink.streaming.audio.AudioStreamingCodec;
import com.smartdevicelink.streaming.audio.AudioStreamingParams;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.WebSocketServerConfig;
import com.smartdevicelink.util.CorrelationIdGenerator;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class LifecycleManager extends BaseLifecycleManager {

    private static final String TAG = "Lifecycle Manager";

    public static final Version MAX_SUPPORTED_RPC_VERSION = new Version("5.0.0");

    // Protected Correlation IDs
    private final int 	REGISTER_APP_INTERFACE_CORRELATION_ID = 65529;


    // Sdl Synchronization Objects
    private static final Object  RPC_LISTENER_LOCK = new Object(),
                                 ON_UPDATE_LISTENER_LOCK = new Object(),
                                 ON_NOTIFICATION_LISTENER_LOCK = new Object();



    SdlSession session;
    AppConfig appConfig;

    //protected Version protocolVersion = new Version(1,0,0);
    protected Version rpcSpecVersion = MAX_SUPPORTED_RPC_VERSION;

    //FIXME these were sparse arrays in android
    protected final  HashMap<Integer,CopyOnWriteArrayList<OnRPCListener>> rpcListeners;
    protected final HashMap<Integer, OnRPCResponseListener> rpcResponseListeners;
    protected final HashMap<Integer, CopyOnWriteArrayList<OnRPCNotificationListener>> rpcNotificationListeners;

    protected final SystemCapabilityManager systemCapabilityManager;

    protected RegisterAppInterfaceResponse raiResponse = null;

    private OnHMIStatus currentHMIStatus;
    protected boolean firstTimeFull = true;

    final LifecycleListener lifecycleListener;

    private List<Class<? extends SdlSecurityBase>> _secList = null;


    public LifecycleManager(AppConfig appConfig, WebSocketServerConfig config, LifecycleListener listener){

        this.lifecycleListener = listener;

        this.rpcListeners = new HashMap<>();
        this.rpcResponseListeners = new HashMap<>();
        this.rpcNotificationListeners = new HashMap<>();

        this.appConfig = appConfig;
        this.session = new SdlSession(this, config);

        this.systemCapabilityManager = new SystemCapabilityManager(internalInterface);


    }

    public void start(){
        try {
            setupInternalRpcListeners();
            session.startSession();
        } catch (SdlException e) {
            e.printStackTrace();
        }

    }

    public void stop(){
        //TODO stop
    }

    public Version getProtocolVersion(){
        if (session != null){
            return session.getProtocolVersion();
        }
        return new Version(1,0,0);
    }
    public Version getRpcSepcVersion(){
        return rpcSpecVersion;
    }

    public void sendRpc(RPCMessage message){
        this.sendRPCMessagePrivate(message);
    }

    public void sendRpcs(List<? extends RPCMessage> messages, OnMultipleRequestListener listener){
        if(messages != null ){
            for(RPCMessage message : messages){
                if(message instanceof RPCRequest){
                    RPCRequest request = ((RPCRequest) message);
                    request.setCorrelationID(CorrelationIdGenerator.generateId());
                    if(listener != null){
                        listener.addCorrelationId(request.getCorrelationID());
                        request.setOnRPCResponseListener(listener.getSingleRpcResponseListener());
                    }
                    this.sendRPCMessagePrivate(request);

                }else{
                    this.sendRPCMessagePrivate(message);
                }
            }
        }
    }

    //Sequentially
    public void sendRpcsSequentially(List<? extends RPCMessage> messages, OnMultipleRequestListener listener){
       //FIXME yea
        sendRpcs(messages, listener);
    }

    public SystemCapabilityManager getSystemCapabilityManager(){
        return systemCapabilityManager;
    }

    public boolean isConnected(){
        if(session != null){
            return session.getIsConnected();
        }else{
            return false;
        }
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
        return this.raiResponse;
    }


    /**
     * Get the current OnHMIStatus
     * @return OnHMIStatus object represents the current OnHMIStatus
	 */
    public OnHMIStatus getCurrentHMIStatus() {
        return currentHMIStatus;
    }

    private void onClose(String info, Exception e){
        Log.i(TAG, "onClose");
        if(lifecycleListener != null){
            lifecycleListener.onProxyClosed(this, info,e,null);
        }
    }


    /* *******************************************************************************************************
     ********************************** INTERNAL - RPC LISTENERS !! START !! *********************************
     *********************************************************************************************************/

    private void setupInternalRpcListeners(){
        addRpcListener(FunctionID.REGISTER_APP_INTERFACE, rpcListener);
        addRpcListener(FunctionID.ON_HMI_STATUS, rpcListener);
        addRpcListener(FunctionID.ON_HASH_CHANGE, rpcListener);
        addRpcListener(FunctionID.ON_SYSTEM_REQUEST, rpcListener);
        addRpcListener(FunctionID.ON_APP_INTERFACE_UNREGISTERED, rpcListener);
        addRpcListener(FunctionID.UNREGISTER_APP_INTERFACE, rpcListener);
    }


    private OnRPCListener rpcListener = new OnRPCListener() {
        @Override
        public void onReceived(RPCMessage message) {
            //Make sure this is a response as expected
            FunctionID functionID = message.getFunctionID();
            if (functionID != null) {
                switch (functionID) {
                    case REGISTER_APP_INTERFACE:
                        //We have begun
                        Log.i(TAG, "RAI Response");
                        raiResponse = (RegisterAppInterfaceResponse) message;
                        SdlMsgVersion rpcVersion = ((RegisterAppInterfaceResponse) message).getSdlMsgVersion();
                        if (rpcVersion != null) {
                            LifecycleManager.this.rpcSpecVersion = new Version(rpcVersion.getMajorVersion(), rpcVersion.getMinorVersion(), rpcVersion.getPatchVersion());
                        } else {
                            LifecycleManager.this.rpcSpecVersion = MAX_SUPPORTED_RPC_VERSION;
                        }
                        processRaiResponse(raiResponse);
                        systemCapabilityManager.parseRAIResponse(raiResponse);
                        break;
                    case ON_HMI_STATUS:
                        Log.i(TAG, "on hmi status");
                        boolean shouldInit = currentHMIStatus == null;
                        currentHMIStatus = (OnHMIStatus) message;
                        if (lifecycleListener != null && shouldInit) {
                            lifecycleListener.onProxyConnected(LifecycleManager.this);
                        }
                        break;
                    case ON_HASH_CHANGE:
                        break;
                    case ON_SYSTEM_REQUEST:
                        OnSystemRequest onSystemRequest = (OnSystemRequest) message;
                        if ((onSystemRequest.getUrl() != null) &&
                                (((onSystemRequest.getRequestType() == RequestType.PROPRIETARY) && (onSystemRequest.getFileType() == FileType.JSON))
                                        || ((onSystemRequest.getRequestType() == RequestType.HTTP) && (onSystemRequest.getFileType() == FileType.BINARY)))) {
                            Thread handleOffboardTransmissionThread = new Thread() {
                                @Override
                                public void run() {
                                    RPCRequest request = PoliciesFetcher.fetchPolicies(onSystemRequest);
                                    if (request != null && isConnected()) {
                                        sendRPCMessagePrivate(request);
                                    }
                                }
                            };
                            handleOffboardTransmissionThread.start();
                        }
                        break;
                    case ON_APP_INTERFACE_UNREGISTERED:
                        Log.v(TAG, "on app interface unregistered");
                        cleanProxy();
                        break;
                    case UNREGISTER_APP_INTERFACE:
                        Log.v(TAG, "unregister app interface");
                        cleanProxy();
                        break;
                }
            }
        }



    };

    /* *******************************************************************************************************
     ********************************** INTERNAL - RPC LISTENERS !! END !! *********************************
     *********************************************************************************************************/


    /* *******************************************************************************************************
     ********************************** METHODS - RPC LISTENERS !! START !! **********************************
     *********************************************************************************************************/

    public boolean onRPCReceived(final RPCMessage message){
        synchronized(RPC_LISTENER_LOCK){
            if(message == null || message.getFunctionID() == null){
                return false;
            }

            final int id = message.getFunctionID().getId();
            CopyOnWriteArrayList<OnRPCListener> listeners = rpcListeners.get(id);
            if(listeners!=null && listeners.size()>0) {
                for (OnRPCListener listener : listeners) {
                    listener.onReceived(message);
                }
                return true;
            }
            return false;
        }
    }

    //FIXME check if we need this to be public
    public void addRpcListener(FunctionID id, OnRPCListener listener){
        synchronized(RPC_LISTENER_LOCK){
            if (id != null && listener != null) {
                if (!rpcListeners.containsKey(id.getId())) {
                    rpcListeners.put(id.getId(), new CopyOnWriteArrayList<OnRPCListener>());
                }

                rpcListeners.get(id.getId()).add(listener);
            }
        }
    }

    public boolean removeOnRPCListener(FunctionID id, OnRPCListener listener){
        synchronized(RPC_LISTENER_LOCK){
            if(rpcListeners!= null
                    && id != null
                    && listener != null
                    && rpcListeners.containsKey(id.getId())){
                return rpcListeners.get(id.getId()).remove(listener);
            }
        }
        return false;
    }

    /**
     * Only call this method for a PutFile response. It will cause a class cast exception if not.
     * @param correlationId correlation id of the packet being updated
     * @param bytesWritten how many bytes were written
     * @param totalSize the total size in bytes
     */
    @SuppressWarnings("unused")
    public void onPacketProgress(int correlationId, long bytesWritten, long totalSize){
        synchronized(ON_UPDATE_LISTENER_LOCK){
            if(rpcResponseListeners !=null
                    && rpcResponseListeners.containsKey(correlationId)){
                ((OnPutFileUpdateListener)rpcResponseListeners.get(correlationId)).onUpdate(correlationId, bytesWritten, totalSize);
            }
        }

    }

    /**
     * Will provide callback to the listener either onFinish or onError depending on the RPCResponses result code,
     * <p>Will automatically remove the listener for the list of listeners on completion.
     * @param msg The RPCResponse message that was received
     * @return if a listener was called or not
     */
    @SuppressWarnings("UnusedReturnValue")
    private boolean onRPCResponseReceived(RPCResponse msg){
        synchronized(ON_UPDATE_LISTENER_LOCK){
            int correlationId = msg.getCorrelationID();
            if(rpcResponseListeners !=null
                    && rpcResponseListeners.containsKey(correlationId)){
                OnRPCResponseListener listener = rpcResponseListeners.get(correlationId);
                if(msg.getSuccess()){
                    listener.onResponse(correlationId, msg);
                }else{
                    listener.onError(correlationId, msg.getResultCode(), msg.getInfo());
                }
                rpcResponseListeners.remove(correlationId);
                return true;
            }
            return false;
        }
    }

    /**
     * Add a listener that will receive the response to the specific RPCRequest sent with the corresponding correlation id
     * @param listener that will get called back when a response is received
     * @param correlationId of the RPCRequest that was sent
     * @param totalSize only include if this is an OnPutFileUpdateListener. Otherwise it will be ignored.
     */
    public void addOnRPCResponseListener(OnRPCResponseListener listener,int correlationId, int totalSize){
        synchronized(ON_UPDATE_LISTENER_LOCK){
            if(rpcResponseListeners!=null
                    && listener !=null){
                if(listener.getListenerType() == OnRPCResponseListener.UPDATE_LISTENER_TYPE_PUT_FILE){
                    ((OnPutFileUpdateListener)listener).setTotalSize(totalSize);
                }
                listener.onStart(correlationId);
                rpcResponseListeners.put(correlationId, listener);
            }
        }
    }

    @SuppressWarnings("unused")
    public HashMap<Integer, OnRPCResponseListener> getResponseListeners(){
        synchronized(ON_UPDATE_LISTENER_LOCK){
            return this.rpcResponseListeners;
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean onRPCNotificationReceived(RPCNotification notification){
        if(notification == null){
            DebugTool.logError("onRPCNotificationReceived - Notification was null");
            return false;
        }
        DebugTool.logInfo("onRPCNotificationReceived - " + notification.getFunctionName() );

        //Before updating any listeners, make sure to do any final updates to the notification RPC now
        if(FunctionID.ON_HMI_STATUS.toString().equals(notification.getFunctionName())){
            OnHMIStatus onHMIStatus = (OnHMIStatus) notification;
            onHMIStatus.setFirstRun(firstTimeFull);
            if (onHMIStatus.getHmiLevel() == HMILevel.HMI_FULL) {
                firstTimeFull = false;
            }
        }

        synchronized(ON_NOTIFICATION_LISTENER_LOCK){
            CopyOnWriteArrayList<OnRPCNotificationListener> listeners = rpcNotificationListeners.get(FunctionID.getFunctionId(notification.getFunctionName()));
            if(listeners!=null && listeners.size()>0) {
                for (OnRPCNotificationListener listener : listeners) {
                    listener.onNotified(notification);
                }
                return true;
            }
            return false;
        }
    }

    /**
     * This will ad a listener for the specific type of notification. As of now it will only allow
     * a single listener per notification function id
     * @param notificationId The notification type that this listener is designated for
     * @param listener The listener that will be called when a notification of the provided type is received
     */
    @SuppressWarnings("unused")
    public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener){
        synchronized(ON_NOTIFICATION_LISTENER_LOCK){
            if(notificationId != null && listener != null){
                if(!rpcNotificationListeners.containsKey(notificationId.getId())){
                    rpcNotificationListeners.put(notificationId.getId(),new CopyOnWriteArrayList<OnRPCNotificationListener>());
                }
                rpcNotificationListeners.get(notificationId.getId()).add(listener);
            }
        }
    }

    /**
     * This method is no longer valid and will not remove the listener for the supplied notificaiton id
     * @param notificationId n/a
     * @see #removeOnRPCNotificationListener(FunctionID, OnRPCNotificationListener)
     */
    @SuppressWarnings("unused")
    @Deprecated
    public void removeOnRPCNotificationListener(FunctionID notificationId){
        synchronized(ON_NOTIFICATION_LISTENER_LOCK){
            //rpcNotificationListeners.delete(notificationId.getId());
        }
    }

    public boolean removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener){
        synchronized(ON_NOTIFICATION_LISTENER_LOCK){
            if(rpcNotificationListeners!= null
                    && notificationId != null
                    && listener != null
                    && rpcNotificationListeners.containsKey(notificationId.getId())){
                return rpcNotificationListeners.get(notificationId.getId()).remove(listener);
            }
        }
        return false;
    }



    /* *******************************************************************************************************
     **************************************** RPC LISTENERS !! END !! ****************************************
     *********************************************************************************************************/



    //FIXME move to SdlSession
    private void sendRPCMessagePrivate(RPCMessage message){
        try {

            message.format(rpcSpecVersion,true);
            byte[] msgBytes = JsonRPCMarshaller.marshall(message, (byte)getProtocolVersion().getMajor());

            ProtocolMessage pm = new ProtocolMessage();
            pm.setData(msgBytes);
            if (session != null){
                pm.setSessionID(session.getSessionId());
            }

            pm.setMessageType(MessageType.RPC);
            pm.setSessionType(SessionType.RPC);
            pm.setFunctionID(FunctionID.getFunctionId(message.getFunctionName()));
            pm.setPayloadProtected(message.isPayloadProtected());
            if(RPCMessage.KEY_REQUEST.equals(message.getMessageType())){
                Integer corrId = ((RPCRequest)message).getCorrelationID();
                   if( corrId== null) {

                       //FIXME Log error here
                       //throw new SdlException("CorrelationID cannot be null. RPC: " + message.getFunctionName(), SdlExceptionCause.INVALID_ARGUMENT);
                       Log.e(TAG, "No correlation ID attatched to request. Not sending");
                   }else{
                       pm.setCorrID(corrId);

                       OnRPCResponseListener listener = ((RPCRequest)message).getOnRPCResponseListener();
                       if(listener != null){
                           addOnRPCResponseListener(listener, corrId, msgBytes.length);
                       }
                   }
            }

            if (message.getBulkData() != null){
                pm.setBulkData(message.getBulkData());
            }

            if(message.getFunctionName().equalsIgnoreCase(FunctionID.PUT_FILE.name())){
                pm.setPriorityCoefficient(1);
            }

            session.sendMessage(pm);

        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }



    /* *******************************************************************************************************
     *************************************** ISdlConnectionListener START ************************************
     *********************************************************************************************************/


    @Override
    public void onTransportDisconnected(String info) {
        onClose(info,null);

    }

    @Override
    public void onTransportDisconnected(String info, boolean availablePrimary, BaseTransportConfig transportConfig) {
        if(!availablePrimary){
            onClose(info, null);
        }

    }

    @Override
    public void onTransportError(String info, Exception e) {
        onClose(info,e);

    }

    @Override
    public void onProtocolMessageReceived(ProtocolMessage msg) {
        //Incoming message
        if(SessionType.RPC.equals(msg.getSessionType())
                || SessionType.BULK_DATA.equals(msg.getSessionType()) ){

                RPCMessage rpc = RpcConverter.extractRpc(msg,session.getProtocolVersion());
                if(rpc != null){
                    String messageType = rpc.getMessageType();
                    Log.v(TAG, "RPC received - " + messageType);

                    rpc.format(rpcSpecVersion,true);

                    FunctionID functionID = rpc.getFunctionID();
                    if (functionID != null && (functionID.equals(FunctionID.ON_BUTTON_PRESS.toString()) || functionID.equals(FunctionID.ON_BUTTON_EVENT.toString())) ) {
                        rpc = handleButtonNotificationFormatting(rpc);
                    }

                    onRPCReceived(rpc);

                    if(RPCMessage.KEY_RESPONSE.equals(messageType)){

                        onRPCResponseReceived((RPCResponse)rpc);

                    }else if(RPCMessage.KEY_NOTIFICATION.equals(messageType)){

                        onRPCNotificationReceived((RPCNotification)rpc);
                    }
                }else{
                    Log.w(TAG, "Shouldn't be here");
                }
        }

    }

    @Override
    public void onProtocolSessionStartedNACKed(SessionType sessionType, byte sessionID, byte version, String correlationID, List<String> rejectedParams) {
        Log.w(TAG, "onProtocolSessionStartedNACKed " + sessionID);
    }

    @Override
    public void onProtocolSessionStarted(SessionType sessionType, byte sessionID, byte version, String correlationID, int hashID, boolean isEncrypted) {

        Log.i(TAG, "on protocol session started");
        if (sessionType != null) {
            if (sessionType.equals(SessionType.RPC)) {
                if(appConfig != null){

                    appConfig.prepare();

                    SdlMsgVersion sdlMsgVersion = new SdlMsgVersion();
                    sdlMsgVersion.setMajorVersion(MAX_SUPPORTED_RPC_VERSION.getMajor());
                    sdlMsgVersion.setMinorVersion(MAX_SUPPORTED_RPC_VERSION.getMinor());
                    sdlMsgVersion.setPatchVersion(MAX_SUPPORTED_RPC_VERSION.getPatch());

                    RegisterAppInterface rai = new RegisterAppInterface(sdlMsgVersion,
                            appConfig.appName,appConfig.isMediaApp, appConfig.languageDesired,
                            appConfig.hmiDisplayLanguageDesired,appConfig.appID);
                    rai.setCorrelationID(REGISTER_APP_INTERFACE_CORRELATION_ID);

                    rai.setTtsName(appConfig.ttsName);
                    rai.setNgnMediaScreenAppName(appConfig.ngnMediaScreenAppName);
                    rai.setVrSynonyms(appConfig.vrSynonyms);
                    rai.setAppHMIType(appConfig.appType);
                    rai.setDayColorScheme(appConfig.dayColorScheme);
                    rai.setNightColorScheme(appConfig.nightColorScheme);

                    //TODO Previous versions have set device info
                    //TODO attach previous hash id

                    sendRPCMessagePrivate(rai);
                }else{
                    Log.e(TAG, "App config was null, soo...");
                }


            } else if (sessionType.eq(SessionType.NAV)) {
                //FIXME NavServiceStarted();
            } else if (sessionType.eq(SessionType.PCM)) {
               //FIXME  AudioServiceStarted();
            }

        }
    }

    @Override
    public void onProtocolSessionEnded(SessionType sessionType, byte sessionID, String correlationID) {

    }

    @Override
    public void onProtocolSessionEndedNACKed(SessionType sessionType, byte sessionID, String correlationID) {

    }

    @Override
    public void onProtocolError(String info, Exception e) {
        //FIXME
    }

    @Override
    public void onHeartbeatTimedOut(byte sessionID) {
        //FIXME
    }

    @Override
    public void onProtocolServiceDataACK(SessionType sessionType, int dataSize, byte sessionID) {

    }

    /* *******************************************************************************************************
     *************************************** ISdlConnectionListener END ************************************
     *********************************************************************************************************/


    /* *******************************************************************************************************
     ******************************************** ISdl - START ***********************************************
     *********************************************************************************************************/

    final ISdl internalInterface = new ISdl() {
        @Override
        public void start() {
            LifecycleManager.this.start();
        }

        @Override
        public void stop() {
            LifecycleManager.this.stop();
        }

        @Override
        public boolean isConnected() {
            return LifecycleManager.this.session.getIsConnected();
        }

        @Override
        public void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {

        }

        @Override
        public void removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {

        }

        @Override
        public void startVideoService(VideoStreamingParameters parameters, boolean encrypted) {

        }

        @Override
        public void stopVideoService() {

        }

        @Override
        public IVideoStreamListener startVideoStream(boolean isEncrypted, VideoStreamingParameters parameters) {
            return null;
        }

        @Override
        public void startAudioService(boolean encrypted, AudioStreamingCodec codec, AudioStreamingParams params) {

        }

        @Override
        public void startAudioService(boolean encrypted) {

        }

        @Override
        public void stopAudioService() {

        }

        @Override
        public IAudioStreamListener startAudioStream(boolean isEncrypted, AudioStreamingCodec codec, AudioStreamingParams params) {
            return null;
        }

        @Override
        public void sendRPCRequest(RPCRequest message) {
            LifecycleManager.this.sendRPCMessagePrivate(message);

        }

        @Override
        public void sendRPC(RPCMessage message) {
            LifecycleManager.this.sendRPCMessagePrivate(message);
        }

        @Override
        public void sendRequests(List<? extends RPCRequest> rpcs, OnMultipleRequestListener listener) {
            //FIXME
        }

        @Override
        public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {
            LifecycleManager.this.addOnRPCNotificationListener(notificationId,listener);
        }

        @Override
        public boolean removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {
            return LifecycleManager.this.removeOnRPCNotificationListener(notificationId,listener);
        }

        @Override
        public void addOnRPCListener(FunctionID responseId, OnRPCListener listener) {
            LifecycleManager.this.addRpcListener(responseId,listener);
        }

        @Override
        public boolean removeOnRPCListener(FunctionID responseId, OnRPCListener listener) {
            return LifecycleManager.this.removeOnRPCListener(responseId,listener);
        }

        @Override
        public Object getCapability(SystemCapabilityType systemCapabilityType) {
            return LifecycleManager.this.systemCapabilityManager.getCapability(systemCapabilityType);
        }

        @Override
        public void getCapability(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener scListener) {
            LifecycleManager.this.systemCapabilityManager.getCapability(systemCapabilityType,scListener);

        }

        @Override
        public boolean isCapabilitySupported(SystemCapabilityType systemCapabilityType) {
            return LifecycleManager.this.systemCapabilityManager.isCapabilitySupported(systemCapabilityType);
        }

        @Override
        public void addOnSystemCapabilityListener(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener listener) {
            LifecycleManager.this.systemCapabilityManager.addOnSystemCapabilityListener(systemCapabilityType,listener);

        }

        @Override
        public boolean removeOnSystemCapabilityListener(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener listener) {
            return LifecycleManager.this.systemCapabilityManager.removeOnSystemCapabilityListener(systemCapabilityType,listener);
        }

        @Override
        public boolean isTransportForServiceAvailable(SessionType serviceType) {
            return LifecycleManager.this.session.isTransportForServiceAvailable(serviceType);
        }

        @Override
        public SdlMsgVersion getSdlMsgVersion() {
            return null;    //FIXME should probably be rpc spec version
        }

        @Override
        public Version getProtocolVersion() {
            return LifecycleManager.this.getProtocolVersion();
        }
    };

    /* *******************************************************************************************************
     ********************************************* ISdl - END ************************************************
     *********************************************************************************************************/

    public interface LifecycleListener{
        void onProxyConnected(LifecycleManager lifeCycleManager);
        void onProxyClosed(LifecycleManager lifeCycleManager, String info, Exception e, SdlDisconnectedReason reason);
        void onServiceEnded(LifecycleManager lifeCycleManager, OnServiceEnded serviceEnded);
        void onServiceNACKed(LifecycleManager lifeCycleManager, OnServiceNACKed serviceNACKed);
        void onError(LifecycleManager lifeCycleManager, String info, Exception e);
    }

    public static class AppConfig{
        //FIXME change these from public
        public String appID, appName, ngnMediaScreenAppName;
        public Vector<TTSChunk> ttsName;
        public Vector<String> vrSynonyms;
        public boolean isMediaApp = false;
        public Language languageDesired,  hmiDisplayLanguageDesired;
        public Vector<AppHMIType> appType;
        public TemplateColorScheme dayColorScheme, nightColorScheme;

        private void prepare(){
            if (ngnMediaScreenAppName == null) {
                ngnMediaScreenAppName = appName;
            }

            if (languageDesired == null) {
                languageDesired = Language.EN_US;
            }

            if (hmiDisplayLanguageDesired == null) {
                hmiDisplayLanguageDesired = Language.EN_US;
            }

            if (vrSynonyms == null) {
                vrSynonyms = new Vector<>();
                vrSynonyms.add(appName);
            }
        }
    }


    //FIXME
    /**
     * Temporary method to bridge the new PLAY_PAUSE and OKAY button functionality with the old
     * OK button name. This should be removed during the next major release
     * @param notification
     */
    private RPCNotification handleButtonNotificationFormatting(RPCMessage notification){
        if(FunctionID.ON_BUTTON_EVENT.toString().equals(notification.getFunctionName())
                || FunctionID.ON_BUTTON_PRESS.toString().equals(notification.getFunctionName())){

            ButtonName buttonName = (ButtonName)notification.getObject(ButtonName.class, OnButtonEvent.KEY_BUTTON_NAME);
            ButtonName compatBtnName = null;

            if(rpcSpecVersion != null && rpcSpecVersion.getMajor() >= 5){
                if(ButtonName.PLAY_PAUSE.equals(buttonName)){
                    compatBtnName =  ButtonName.OK;
                }
            }else{ // rpc spec version is either null or less than 5
                if(ButtonName.OK.equals(buttonName)){
                    compatBtnName = ButtonName.PLAY_PAUSE;
                }
            }

            try {
                if (compatBtnName != null) { //There is a button name that needs to be swapped out
                    RPCNotification notification2;
                    //The following is done because there is currently no way to make a deep copy
                    //of an RPC. Since this code will be removed, it's ugliness is borderline acceptable.
                    if (notification instanceof OnButtonEvent) {
                        OnButtonEvent onButtonEvent = new OnButtonEvent();
                        onButtonEvent.setButtonEventMode(((OnButtonEvent) notification).getButtonEventMode());
                        onButtonEvent.setCustomButtonID(((OnButtonEvent) notification).getCustomButtonID());
                        notification2 = onButtonEvent;
                    } else if (notification instanceof OnButtonPress) {
                        OnButtonPress onButtonPress = new OnButtonPress();
                        onButtonPress.setButtonPressMode(((OnButtonPress) notification).getButtonPressMode());
                        onButtonPress.setCustomButtonName(((OnButtonPress) notification).getCustomButtonName());
                        notification2 = onButtonPress;
                    } else {
                        return null;
                    }

                    notification2.setParameters(OnButtonEvent.KEY_BUTTON_NAME, compatBtnName);
                    return notification2;
                }
            }catch (Exception e){
                //Should never get here
            }
        }
        return null;
    }

    private void cleanProxy(){
        if (rpcListeners != null) {
            rpcListeners.clear();
        }
        if (rpcResponseListeners != null) {
            rpcResponseListeners.clear();
        }
        if (rpcNotificationListeners != null) {
            rpcNotificationListeners.clear();
        }
        if (session != null && session.getIsConnected()) {
            session.close();
        }
    }

    public void setSdlSecurityClassList(List<Class<? extends SdlSecurityBase>> list) {
        _secList = list;
    }

    private void processRaiResponse(RegisterAppInterfaceResponse rai) {
        if (rai == null) return;

        this.raiResponse = rai;

        VehicleType vt = rai.getVehicleType();
        if (vt == null) return;

        String make = vt.getMake();
        if (make == null) return;

        if (_secList == null) return;

        SdlSecurityBase sec;

        for (Class<? extends SdlSecurityBase> cls : _secList) {
            try {
                sec = cls.newInstance();
            } catch (Exception e) {
                continue;
            }

            if ((sec != null) && (sec.getMakeList() != null)) {
                if (sec.getMakeList().contains(make)) {
                    sec.setAppId(appConfig.appID);
                    if (session != null) {
                        session.setSdlSecurity(sec);
                        sec.handleSdlSession(session);
                    }
                    return;
                }
            }
        }
    }


    /* *******************************************************************************************************
     ************************************* FileStream Methods - START ****************************************
     *********************************************************************************************************/

    /**
     * Used to push a binary stream of file data onto the module from a mobile device.
     *
     * @param inputStream The input stream of byte data that will be read from.
     * @param fileName    The SDL file reference name used by the RPC.
     * @param offset      The data offset in bytes. A value of zero is used to
     *                    indicate data starting from the beginning of the file and a value greater
     *                    than zero is used for resuming partial data chunks.
     * @param length      The total length of the file being sent.
     */
    public void putFileStream(InputStream inputStream, @NonNull String fileName, Long offset, Long length) {
        PutFile msg = new PutFile(fileName, FileType.BINARY);
        msg.setCorrelationID(10000);
        msg.setSystemFile(true);
        msg.setOffset(offset);
        msg.setLength(length);

        startRPCStream(inputStream, msg);
    }

    /**
     * Used to push a binary stream of file data onto the module from a mobile device.
     *
     * @param inputStream      The input stream of byte data that will be read from.
     * @param fileName         The SDL file reference name used by the RPC.
     * @param offset           The data offset in bytes. A value of zero is used to
     *                         indicate data starting from the beginning of the file and a value greater
     *                         than zero is used for resuming partial data chunks.
     * @param length           The total length of the file being sent.
     * @param fileType         The selected file type. See the {@link FileType} enum for
     *                         details.
     * @param isPersistentFile Indicates if the file is meant to persist between
     *                         sessions / ignition cycles.
     * @param isSystemFile     Indicates if the file is meant to be passed through
     *                         core to elsewhere in the system.
     */
    public void putFileStream(InputStream inputStream, @NonNull String fileName, Long offset, Long length, FileType fileType, Boolean isPersistentFile, Boolean isSystemFile, OnPutFileUpdateListener cb) {
        PutFile msg = new PutFile(fileName, FileType.BINARY);
        msg.setCorrelationID(10000);
        msg.setSystemFile(true);
        msg.setOffset(offset);
        msg.setLength(length);
        msg.setOnPutFileUpdateListener(cb);
        startRPCStream(inputStream, msg);
    }


    /**
     * Used to push a binary stream of file data onto the module from a mobile device.
     *
     * @param fileName The SDL file reference name used by the RPC.
     * @param offset   The data offset in bytes. A value of zero is used to
     *                 indicate data starting from the beginning of the file and a value greater
     *                 than zero is used for resuming partial data chunks.
     * @param length   The total length of the file being sent.
     */
    public OutputStream putFileStream(@NonNull String fileName, Long offset, Long length) {
        PutFile msg = new PutFile(fileName, FileType.BINARY);
        msg.setCorrelationID(10000);
        msg.setSystemFile(true);
        msg.setOffset(offset);
        msg.setLength(length);

        return startRPCStream(msg);
    }

    /**
     * Used to push a binary stream of file data onto the module from a mobile device.
     *
     * @param fileName         The SDL file reference name used by the RPC.
     * @param offset           The data offset in bytes. A value of zero is used to
     *                         indicate data starting from the beginning of the file and a value greater
     *                         than zero is used for resuming partial data chunks.
     * @param length           The total length of the file being sent.
     * @param fileType         The selected file type. See the {@link FileType} enum for
     *                         details.
     * @param isPersistentFile Indicates if the file is meant to persist between
     *                         sessions / ignition cycles.
     * @param isSystemFile     Indicates if the file is meant to be passed through
     *                         core to elsewhere in the system.
     */
    public OutputStream putFileStream(@NonNull String fileName, Long offset, Long length, FileType fileType, Boolean isPersistentFile, Boolean isSystemFile, OnPutFileUpdateListener cb) {
        PutFile msg = new PutFile(fileName, FileType.BINARY);
        msg.setCorrelationID(10000);
        msg.setSystemFile(true);
        msg.setOffset(offset);
        msg.setLength(length);
        msg.setOnPutFileUpdateListener(cb);

        return startRPCStream(msg);
    }

    /**
     * Used to push a binary stream of file data onto the module from a mobile device.
     *
     * @param path             The physical file path on the mobile device.
     * @param fileName         The SDL file reference name used by the RPC.
     * @param offset           The data offset in bytes. A value of zero is used to
     *                         indicate data starting from the beginning of the file and a value greater
     *                         than zero is used for resuming partial data chunks.
     * @param fileType         The selected file type. See the {@link FileType} enum for
     *                         details.
     * @param isPersistentFile Indicates if the file is meant to persist between
     *                         sessions / ignition cycles.
     * @param isSystemFile     Indicates if the file is meant to be passed through
     *                         core to elsewhere in the system.
     * @param correlationId    A unique id that correlates each RPCRequest and
     *                         RPCResponse.
     * @return RPCStreamController If the putFileStream was not started
     * successfully null is returned, otherwise a valid object reference is
     * returned .
     */
    public RPCStreamController putFileStream(String path, @NonNull String fileName, Long offset, @NonNull FileType fileType, Boolean isPersistentFile, Boolean isSystemFile, Boolean isPayloadProtected, Integer correlationId, OnPutFileUpdateListener cb) {
        PutFile msg = new PutFile(fileName, fileType);
        msg.setCorrelationID(correlationId);
        msg.setPersistentFile(isPersistentFile);
        msg.setSystemFile(isSystemFile);
        msg.setOffset(offset);
        msg.setLength(0L);
        msg.setPayloadProtected(isPayloadProtected);
        msg.setOnPutFileUpdateListener(cb);

        if (session == null) return null;

        FileInputStream is = null;
        try {
            is = new FileInputStream(path);
        } catch (IOException e1) {
            e1.printStackTrace();
        }


        if (is == null) return null;

        Long lSize = null;

        try {
            lSize = is.getChannel().size();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (lSize == null) {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        try {
            StreamRPCPacketizer rpcPacketizer = new StreamRPCPacketizer(this, session, is, msg, SessionType.RPC, session.getSessionId(), getProtocolVersion(), rpcSpecVersion, lSize, session);
            rpcPacketizer.start();
            return new RPCStreamController(rpcPacketizer, msg.getCorrelationID());
        } catch (Exception e) {
            Log.e("SyncConnection", "Unable to start streaming:" + e.toString());
            return null;
        }
    }

    /**
     * Used to push a binary stream of file data onto the module from a mobile device.
     *
     * @param inputStream      The input stream of byte data that will be read from.
     * @param fileName         The SDL file reference name used by the RPC.
     * @param offset           The data offset in bytes. A value of zero is used to
     *                         indicate data starting from the beginning of the file and a value greater
     *                         than zero is used for resuming partial data chunks.
     * @param length           The total length of the file being sent.
     * @param fileType         The selected file type. See the {@link FileType} enum for
     *                         details.
     * @param isPersistentFile Indicates if the file is meant to persist between
     *                         sessions / ignition cycles.
     * @param isSystemFile     Indicates if the file is meant to be passed through
     *                         core to elsewhere in the system.
     * @param correlationId    A unique id that correlates each RPCRequest and
     *                         RPCResponse.
     */
    public RPCStreamController putFileStream(InputStream inputStream, @NonNull String fileName, Long offset, Long length, @NonNull FileType fileType, Boolean isPersistentFile, Boolean isSystemFile, Boolean isPayloadProtected, Integer correlationId) {
        PutFile msg = new PutFile(fileName, fileType);
        msg.setCorrelationID(correlationId);
        msg.setPersistentFile(isPersistentFile);
        msg.setSystemFile(isSystemFile);
        msg.setOffset(offset);
        msg.setLength(length);
        msg.setPayloadProtected(isPayloadProtected);

        if (session == null) return null;
        Long lSize = msg.getLength();

        if (lSize == null) {
            return null;
        }

        try {
            StreamRPCPacketizer rpcPacketizer = new StreamRPCPacketizer(this, session, inputStream, msg, SessionType.RPC, session.getSessionId(), getProtocolVersion(), rpcSpecVersion, lSize, session);
            rpcPacketizer.start();
            return new RPCStreamController(rpcPacketizer, msg.getCorrelationID());
        } catch (Exception e) {
            Log.e("SyncConnection", "Unable to start streaming:" + e.toString());
            return null;
        }
    }

    /**
     * Used to end an existing putFileStream that was previously initiated with any putFileStream method.
     */
    public void endPutFileStream() {
        endRPCStream();
    }

    public void endRPCStream() {
        if (this.session == null) return;
        session.stopRPCStream();
    }

    public boolean startRPCStream(InputStream is, RPCRequest msg) {
        if (session == null) return false;
        session.startRPCStream(is, msg, SessionType.RPC, session.getSessionId(), (byte) getProtocolVersion().getMajor());
        return true;
    }

    public OutputStream startRPCStream(RPCRequest msg) {
        if (session == null) return null;
        return session.startRPCStream(msg, SessionType.RPC, session.getSessionId(), (byte) getProtocolVersion().getMajor());
    }

    /* *******************************************************************************************************
     ************************************** FileStream Methods - END *****************************************
     *********************************************************************************************************/

}
