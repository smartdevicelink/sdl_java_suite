// New version

package com.smartdevicelink.SdlConnection;

import android.os.Bundle;
import android.util.Log;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.ISdlProtocol;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.SdlProtocol;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.protocol.heartbeat.IHeartbeatMonitor;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class SdlSession2 extends SdlSession implements ISdlProtocol{
    private static final String TAG = "SdlSession2";


    protected SdlProtocol sdlProtocol;

    @Deprecated
    public static SdlSession2 createSession(byte wiproVersion, ISdlConnectionListener listener, BaseTransportConfig btConfig) {
        return null;
    }

    public SdlSession2(ISdlConnectionListener listener, MultiplexTransportConfig config){
        Log.d(TAG, "SdlSession created");
        this.transportConfig = config;
        this.sessionListener = listener;

        this.sdlProtocol = new SdlProtocol(this,config);

    }

    @Deprecated
    @Override
    public SdlConnection getSdlConnection() {
        return null;
    }

    @Override
    public int getMtu(){
        if(this.sdlProtocol!=null){
            return this.sdlProtocol.getMtu();
        }else{
            return 0;
        }
    }

    @Override
    public long getMtu(SessionType type) {
        if (this.sdlProtocol != null) {
            return this.sdlProtocol.getMtu(type);
        } else {
            return 0;
        }
    }

    public void close() {
        if (sdlSecurity != null)
        {
            sdlSecurity.resetParams();
            sdlSecurity.shutDown();
        }
        if(sdlProtocol != null){
            sdlProtocol.endSession(sessionId, sessionHashId);
            //FIXME add a shutdown to sdlProtocol.
            //transportManager.close(sessionId);
        }
    }


    @Override
    public void startService (SessionType serviceType, byte sessionID, boolean isEncrypted) {
        if (isEncrypted){
            if (sdlSecurity != null){
                List<SessionType> serviceList = sdlSecurity.getServiceList();
                if (!serviceList.contains(serviceType))
                    serviceList.add(serviceType);

                sdlSecurity.initialize();
            }
            return;
        }
        sdlProtocol.startService(serviceType, sessionID, isEncrypted);
    }

    @Override
    public void endService (SessionType serviceType, byte sessionID) {
        if (sdlProtocol == null) {
            return;
        }
        sdlProtocol.endService(serviceType,sessionID);
    }


    public String getBroadcastComment(BaseTransportConfig myTransport) {
        return "Multiplexing";
    }


    @Override
    public void startSession() throws SdlException {
        sdlProtocol.start();
    }


    @Override
    public void sendMessage(ProtocolMessage msg) {
        if (sdlProtocol == null){
            return;
        }
        sdlProtocol.sendMessage(msg);
    }

    @Override
    public TransportType getCurrentTransportType() {
        return TransportType.MULTIPLEX;
    }

    @Override
    public boolean getIsConnected() {
        return sdlProtocol != null && sdlProtocol.isConnected();
    }


    public void shutdown(String info){
        Log.d(TAG, "Shutdown - " + info);
        this.sessionListener.onTransportDisconnected(info);

    }

    @Override
    public void onTransportDisconnected(String info, boolean altTransportAvailable, MultiplexTransportConfig transportConfig) {
        this.sessionListener.onTransportDisconnected(info, altTransportAvailable, (MultiplexTransportConfig)this.transportConfig);
    }


    /* ***********************************************************************************************************************************************************************
     * *****************************************************************  IProtocol Listener  ********************************************************************************
     *************************************************************************************************************************************************************************/

    @Override
    public void onProtocolMessageBytesToSend(SdlPacket packet) {
        //Log.d(TAG, "onProtocolMessageBytesToSend - " + packet.getTransportType());
        sdlProtocol.sendPacket(packet);
    }

    @Override
    public void sendHeartbeat(IHeartbeatMonitor monitor) {
        Log.d(TAG, "Asked to send heartbeat");
        if (sdlProtocol != null) {
            //FIXME sdlProtocol.sendHeartBeat(sessionId);
        }
    }

    @Override
    public void heartbeatTimedOut(IHeartbeatMonitor monitor) {
        close();
    }

    public void onHeartbeatTimedOut(byte sessionId){
        //TODO
    }

    public void onProtocolSessionStartedNACKed(SessionType sessionType, byte sessionID, byte version, String correlationID, List<String> rejectedParams){
        onProtocolSessionNACKed(sessionType,sessionID,version,correlationID,rejectedParams);
    }

    @Override
    public void onProtocolSessionNACKed(SessionType sessionType, byte sessionID, byte version, String correlationID, List<String> rejectedParams) {
        this.sessionListener.onProtocolSessionStartedNACKed(sessionType,
                sessionID, version, correlationID, rejectedParams);
        if(serviceListeners != null && serviceListeners.containsKey(sessionType)){
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            for(ISdlServiceListener listener:listeners){
                listener.onServiceError(this, sessionType, "Start "+ sessionType.toString() +" Service NACK'ed");
            }
        }
    }



    @Override
    public void onProtocolHeartbeat(SessionType sessionType, byte sessionID) {
        //TODO
    }

    @Override
    public void onProtocolHeartbeatACK(SessionType sessionType, byte sessionID) {
        //TODO
    }


    @Override
    public void onResetOutgoingHeartbeat(SessionType sessionType, byte sessionID) {
        //TODO
    }

    @Override
    public void onResetIncomingHeartbeat(SessionType sessionType, byte sessionID) {
        //TODO
    }

    /* ***********************************************************************************************************************************************************************
     * *****************************************************************  Security Listener  *********************************************************************************
     *************************************************************************************************************************************************************************/


    @Override
    public void onSecurityInitialized() {

        if (sdlProtocol != null && sdlSecurity != null)
        {
            List<SessionType> list = sdlSecurity.getServiceList();

            SessionType service;
            ListIterator<SessionType> iter = list.listIterator();

            while (iter.hasNext()) {
                service = iter.next();

                if (service != null)
                    sdlProtocol.startService(service, getSessionId(), true);

                iter.remove();
            }
        }
    }

    @Deprecated
    public void clearConnection(){
        //TODO reset SdlProtocol
    }

    @Deprecated
    public static boolean removeConnection(SdlConnection connection){
        return false;
    }

    @Deprecated
    @Override
    public void checkForOpenMultiplexConnection(SdlConnection connection){
    }

    @Override
    public void stopStream(SessionType serviceType) {
        if(SessionType.NAV.equals(serviceType)){
            stopVideoStream();
        }else if(SessionType.PCM.equals(serviceType)){
            stopAudioStream();
        }

    }

}
