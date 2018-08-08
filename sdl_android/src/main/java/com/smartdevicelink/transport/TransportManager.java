package com.smartdevicelink.transport;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransportManager {
    private static final String TAG = "TransportManager";

    private final Object TRANSPORT_STATUS_LOCK;

    TransportBrokerImpl transport;
    //final HashMap<TransportType, Boolean> transportStatus;
    final List<TransportRecord> transportStatus;
    final TransportEventListener transportListener;

    //Legacy Transport
    MultiplexBluetoothTransport legacyBluetoothTransport;
    LegacyBluetoothHandler legacyBluetoothHandler;


    /**
     * Managing transports
     * List for status of all transports
     * If transport is not connected. Request Router service connect to it. Get connected message
     */

    public TransportManager(MultiplexTransportConfig config, TransportEventListener listener){

        this.transportListener = listener;
        this.TRANSPORT_STATUS_LOCK = new Object();
        synchronized (TRANSPORT_STATUS_LOCK){
            this.transportStatus = new ArrayList<>();
           // this.transportStatus = new HashMap<>();
           // this.transportStatus.put(TransportType.BLUETOOTH, false);
           // this.transportStatus.put(TransportType.USB, false);
           // this.transportStatus.put(TransportType.TCP, false);
        }

        if(config.service == null) {
            config.service = SdlBroadcastReceiver.consumeQueuedRouterService();
        }

        RouterServiceValidator validator = new RouterServiceValidator(config.context,config.service);
        if(validator.validate()){
            transport = new TransportBrokerImpl(config.context, config.appId,config.service);
        }else{
            enterLegacyMode("Router service is not trusted. Entering legacy mode");
            //throw new SecurityException("Unable to trust router service");
        }
    }

    public void start(){
        if(transport != null){
            transport.start();
        }else if(legacyBluetoothTransport != null){
            legacyBluetoothTransport.start();
        }
    }

    public void close(long sessionId){
        if(transport != null) {
            transport.removeSession(sessionId);
            transport.stop();
        }else if(legacyBluetoothTransport != null){
            legacyBluetoothTransport.stop();
            legacyBluetoothTransport = null;
        }
    }

    /**
     * Check to see if a transport is connected.
     * @param transportType the transport to have its connection status returned. If `null` is
     *                      passed in, all transports will be checked and if any are connected a
     *                      true value will be returned.
     * @return
     */
    public boolean isConnected(TransportType transportType, String address){
        synchronized (TRANSPORT_STATUS_LOCK) {
            if (transportType == null) {
                return !transportStatus.isEmpty();
            }
            for (TransportRecord record : transportStatus) {
                if (record.getType().equals(transportType)) {
                    if (address != null) {
                        if (address.equals(record.getAddress())) {
                            return true;
                        } // Address doesn't match, move forward
                    } else {
                        //If no address is included, assume any transport of correct type is acceptable
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public boolean isHighBandwidthAvailable(){
        synchronized (TRANSPORT_STATUS_LOCK) {
            for (TransportRecord record : transportStatus) {
                if (record.getType().equals(TransportType.USB)
                        || record.getType().equals(TransportType.TCP)) {
                    return true;
                }
            }
            return false;
        }
    }

    public ComponentName getRouterService(){
        if(transport != null) {
            return transport.getRouterService();
        }
        return null;
    }

    public void sendPacket(SdlPacket packet){
        if(transport !=null){
            transport.sendPacketToRouterService(packet);
        }else if(legacyBluetoothTransport != null){
            byte[] data = packet.constructPacket();
            legacyBluetoothTransport.write(data, 0, data.length);
        }
    }

    public void requestNewSession(TransportType transportType){
        if(transport!=null){
            transport.requestNewSession(transportType);
        }else if(legacyBluetoothTransport != null && !TransportType.BLUETOOTH.equals(transportType)){
            Log.w(TAG, "Session requested for non-bluetooth transport while in legacy mode");
        }
    }

    public void requestSecondaryTransportConnection(byte sessionId, Bundle params){
    	transport.requestSecondaryTransportConnection(sessionId, params);
    }

    private class TransportBrokerImpl extends TransportBroker{

        public TransportBrokerImpl(Context context, String appId, ComponentName routerService){
            super(context,appId,routerService);
        }

        @Override
        public boolean onHardwareConnected(TransportType transportType){
            return false;
        }

        @Override
        public boolean onHardwareConnected(List<TransportRecord> transports) {
            Log.d(TAG, "onHardwareConnected - " +transports.size());
            super.onHardwareConnected(transports);
            synchronized (TRANSPORT_STATUS_LOCK){
                transportStatus.clear();
                transportStatus.addAll(transports);
                for(TransportRecord record: transportStatus){
                    Log.d(TAG, "Transport connected: " + record.getType().name());
                }
            }
            transportListener.onTransportConnected(transports);
            return true;
        }


        @Override
        public void onHardwareDisconnected(TransportRecord record, List<TransportRecord> connectedTransports) {
            if(record != null){
                Log.d(TAG, "Transport disconnected - " + record);
            }else{
                Log.d(TAG, "Transport disconnected");

            }
//            if(connectedTransports == null || connectedTransports.isEmpty()){
//                //There are no more transports to use so we can unbind
//                super.onHardwareDisconnected(record,connectedTransports);
//            }

            synchronized (TRANSPORT_STATUS_LOCK){
                TransportManager.this.transportStatus.remove(record);
                //Might check connectedTransports vs transportStatus to ensure they are equal
            }

            if(isLegacyModeEnabled()
                    && TransportType.BLUETOOTH.equals(record.getType()) //Make sure it's bluetooth that has be d/c
                    && legacyBluetoothTransport == null){ //Make sure we aren't already in legacy mode
                //Legacy mode has been enabled so we need to cycle
                enterLegacyMode("Router service has enabled legacy mode");
            }else{
                //Inform the transport listener that a transport has disconnected
                transportListener.onTransportDisconnected("", record, connectedTransports);
            }
        }

        @Override
        public void onPacketReceived(Parcelable packet) {
            if(packet!=null){
                transportListener.onPacketReceived((SdlPacket)packet);
            }
        }
    }

    private synchronized void enterLegacyMode(final String info){
        if(legacyBluetoothTransport != null && legacyBluetoothHandler != null){
            return; //Already in legacy mode
        }

        if(transportListener.onLegacyModeEnabled(info)) {
            if(Looper.myLooper() == null){
                Looper.prepare();
            }
            legacyBluetoothHandler = new LegacyBluetoothHandler(this);
            legacyBluetoothTransport = new MultiplexBluetoothTransport(legacyBluetoothHandler);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    transportListener.onError(info + " - Legacy mode unacceptable; shutting down.");

                }
            },500);
        }
    }

    private synchronized void exitLegacyMode(String info ){
        if(legacyBluetoothTransport != null){
            legacyBluetoothTransport.stop();
            legacyBluetoothTransport = null;
        }
        legacyBluetoothHandler = null;
        synchronized (TRANSPORT_STATUS_LOCK){
            TransportManager.this.transportStatus.clear();
        }
        transportListener.onTransportDisconnected(info, new TransportRecord(TransportType.BLUETOOTH,null),null);
    }

    public interface TransportEventListener{
        /** Called to indicate and deliver a packet received from transport */
        void onPacketReceived(SdlPacket packet);

        /** Called to indicate that transport connection was established */
        void onTransportConnected(List<TransportRecord> transports);

        /** Called to indicate that transport was disconnected (by either side) */
        void onTransportDisconnected(String info, TransportRecord type, List<TransportRecord> connectedTransports);

        // Called when the transport manager experiences an unrecoverable failure
        void onError(String info);
        /**
         * Called when the transport manager has determined it needs to move towards a legacy style
         * transport connection. It will always be bluetooth.
         * @param info
         * @return if the listener is ok with entering legacy mode
         */
        boolean onLegacyModeEnabled(String info);
    }



    private static class LegacyBluetoothHandler extends Handler{

        final WeakReference<TransportManager> provider;

        public LegacyBluetoothHandler(TransportManager provider){
            this.provider = new WeakReference<TransportManager>(provider);
        }
        @Override
        public void handleMessage(Message msg) {
            if(this.provider.get() == null){
                return;
            }
            TransportManager service = this.provider.get();
            if(service.transportListener == null){
                return;
            }
            switch (msg.what) {
                case SdlRouterService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case MultiplexBaseTransport.STATE_CONNECTED:
                            synchronized (service.TRANSPORT_STATUS_LOCK){
                                service.transportStatus.clear();
                                service.transportStatus.add(service.legacyBluetoothTransport.getTransportRecord());
                            }
                            service.transportListener.onTransportConnected(service.transportStatus);
                            break;
                        case MultiplexBaseTransport.STATE_CONNECTING:
                            // Currently attempting to connect - update UI?
                            break;
                        case MultiplexBaseTransport.STATE_LISTEN:
                            break;
                        case MultiplexBaseTransport.STATE_NONE:
                            // We've just lost the connection
                            service.exitLegacyMode("Lost connection");
                            break;
                        case MultiplexBaseTransport.STATE_ERROR:
                            Log.d(TAG, "Bluetooth serial server error received, setting state to none, and clearing local copy");
                            service.exitLegacyMode("Transport error");
                            break;
                    }
                    break;

                case SdlRouterService.MESSAGE_READ:
                    service.transportListener.onPacketReceived((SdlPacket) msg.obj);
                    break;
            }
        }
    }

}
