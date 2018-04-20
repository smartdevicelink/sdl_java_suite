package com.smartdevicelink.transport;

import android.content.ComponentName;
import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseBooleanArray;

import com.smartdevicelink.SdlConnection.SdlConnection;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.transport.enums.TransportType;

import java.util.HashMap;

public class TransportManager {
    private static final String TAG = "TransportManager";

    private final Object TRANSPORT_STATUS_LOCK;

    TransportBrokerImpl transport;
    final HashMap<TransportType, Boolean> transportStatus;
    final TransportEventListener transportListener;
    TransportType primaryTransport, secondaryTransport;

    /**
     * Managing transports
     * List for status of all transports
     * If transport is not connected. Request Router service connect to it. Get connected message
     */

    public TransportManager(MultiplexTransportConfig config, TransportEventListener listener){
        if(config.service == null) {
            config.service = SdlBroadcastReceiver.consumeQueuedRouterService();
        }
        RouterServiceValidator validator = new RouterServiceValidator(config.context,config.service);
        if(validator.validate()){
            transport = new TransportBrokerImpl(config.context, config.appId,config.service);
        }else{
            //FIXME start up legacy
            throw new SecurityException("Unable to trust router service");
        }
        this.transportListener = listener;
        this.TRANSPORT_STATUS_LOCK = new Object();
        synchronized (TRANSPORT_STATUS_LOCK){
            this.transportStatus = new HashMap<>();
            this.transportStatus.put(TransportType.BLUETOOTH, false);
            this.transportStatus.put(TransportType.USB, false);
            this.transportStatus.put(TransportType.TCP, false);
        }
    }

    public void start(){
        if(transport != null){
            transport.start();
        }
    }

    public void close(long sessionId){
        transport.removeSession(sessionId);
        transport.stop();
    }

    public TransportType getPrimaryTransport(){
        return primaryTransport;
    }

    public boolean isConnected(){
        return primaryTransport != null;
    }
    public void sendPacket(SdlPacket packet){
        if(transport !=null){
            transport.sendPacketToRouterService(packet);
        }
    }

    public void registerSecondaryTransport(TransportType transportType){
        //Request from router service
    }

    private class TransportBrokerImpl extends TransportBroker{

        public TransportBrokerImpl(Context context, String appId, ComponentName routerService){
            super(context,appId,routerService);
        }

        @Override
        public boolean onHardwareConnected(TransportType type) {
            Log.d(TAG, "onHardwareConnected");
            super.onHardwareConnected(type);
            synchronized (TRANSPORT_STATUS_LOCK){
                TransportManager.this.transportStatus.put(type,true);
                if(primaryTransport == null){
                    primaryTransport = type;
                    this.requestNewSession();
                }
            }
            transportListener.onTransportConnected(type);
            return true;
        }

        @Override
        public void onHardwareDisconnected(TransportType type) {
            super.onHardwareDisconnected(type);
            synchronized (TRANSPORT_STATUS_LOCK){
                TransportManager.this.transportStatus.put(type,true);
            }

            if(type.equals(primaryTransport)){
               primaryTransport = null;
                SdlConnection.enableLegacyMode(isLegacyModeEnabled(), TransportType.BLUETOOTH);
                if(isLegacyModeEnabled()){
                    Log.d(TAG, "Handle transport disconnect, legacy mode enabled");
                    this.stop();
                    transportListener.onTransportDisconnected("",type);
                    //TODO perform legacy mode. Don't just disconnect
                }else{
                    transportListener.onTransportDisconnected("",type);
                    this.stop();
                }
            }
        }

        @Override
        public void onPacketReceived(Parcelable packet) {
            if(packet!=null){
                transportListener.onPacketReceived((SdlPacket)packet);
            }
        }
    }

    public interface TransportEventListener{
        // Called to indicate and deliver a packet received from transport
        void onPacketReceived(SdlPacket packet);

        // Called to indicate that transport connection was established
        void onTransportConnected(TransportType transportType);
        //void onTransportConnected(TransportType transportType, boolean primary);

        // Called to indicate that transport was disconnected (by either side)
        void onTransportDisconnected(String info, TransportType type);
    }

}
