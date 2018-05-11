package com.smartdevicelink.transport;

import android.content.ComponentName;
import android.content.Context;
import android.os.Parcelable;
import android.util.Log;

import com.smartdevicelink.SdlConnection.SdlConnection;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.transport.enums.TransportType;

import java.util.HashMap;

public class TransportManager {
    private static final String TAG = "TransportManager";

    private final Object TRANSPORT_STATUS_LOCK;

    TransportBrokerImpl transport;
    final HashMap<TransportType, Boolean> transportStatus;
    final TransportEventListener transportListener;

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

    /**
     * Check to see if a transport is connected.
     * @param transportType the transport to have its connection status returned. If `null` is
     *                      passed in, all transports will be checked and if any are connected a
     *                      true value will be returned.
     * @return
     */
    public boolean isConnected(TransportType transportType){
        if(transportType != null){
            return transportStatus.get(transportType);
        }
        return transportStatus.values().contains(true);
    }

    public boolean isHighBandwidthAvailable(){
        return transportStatus.get(TransportType.USB) ||  transportStatus.get(TransportType.TCP);
    }

    public void sendPacket(SdlPacket packet){
        if(transport !=null){
            transport.sendPacketToRouterService(packet);
        }
    }

    private void resetTransports(){
        this.transportStatus.put(TransportType.BLUETOOTH, false);
        this.transportStatus.put(TransportType.USB, false);
        this.transportStatus.put(TransportType.TCP, false);

    }

    public void requestNewSession(){
        transport.requestNewSession();
    }

    private class TransportBrokerImpl extends TransportBroker{

        public TransportBrokerImpl(Context context, String appId, ComponentName routerService){
            super(context,appId,routerService);
        }

        @Override
        public boolean onHardwareConnected(TransportType[] types) {
            Log.d(TAG, "onHardwareConnected");
            super.onHardwareConnected(types);
            synchronized (TRANSPORT_STATUS_LOCK){
                resetTransports();
                for(int i = 0; i< types.length; i++){
                    TransportManager.this.transportStatus.put(types[i],true);

                }
            }
            transportListener.onTransportConnected(types);
            return true;
        }

        @Override
        public void onHardwareDisconnected(TransportType type) {
            if(type != null){
                Log.d(TAG, "Transport disconnected - " + type.name());
            }else{
                Log.d(TAG, "Transport disconnected");

            }
            super.onHardwareDisconnected(type);
            synchronized (TRANSPORT_STATUS_LOCK){
                TransportManager.this.transportStatus.put(type,false);
            }
            transportListener.onTransportDisconnected("",type);

            /*if(type.equals(primaryTransport)){
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
            }*/
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
        void onTransportConnected(TransportType[] transportTypes);
        //void onTransportConnected(TransportType transportType, boolean primary);

        // Called to indicate that transport was disconnected (by either side)
        void onTransportDisconnected(String info, TransportType type);
    }

}
