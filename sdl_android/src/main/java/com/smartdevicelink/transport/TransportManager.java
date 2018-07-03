package com.smartdevicelink.transport;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.transport.enums.TransportType;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class TransportManager {
    private static final String TAG = "TransportManager";

    private final Object TRANSPORT_STATUS_LOCK;

    TransportBrokerImpl transport;
    final HashMap<TransportType, Boolean> transportStatus;
    final TransportEventListener transportListener;
    final WeakReference<Context> contextWeakReference;

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
            this.transportStatus = new HashMap<>();
            this.transportStatus.put(TransportType.BLUETOOTH, false);
            this.transportStatus.put(TransportType.USB, false);
            this.transportStatus.put(TransportType.TCP, false);
        }

        if(config.service == null) {
            config.service = SdlBroadcastReceiver.consumeQueuedRouterService();
        }

        contextWeakReference = new WeakReference<>(config.context);

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
    public boolean isConnected(TransportType transportType){
        if(transportType != null){
            return transportStatus.get(transportType);
        }
        return transportStatus.values().contains(true);
    }

    public boolean isHighBandwidthAvailable(){
        return transportStatus.get(TransportType.USB) ||  transportStatus.get(TransportType.TCP);
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

    private void resetTransports(){
        this.transportStatus.put(TransportType.BLUETOOTH, false);
        this.transportStatus.put(TransportType.USB, false);
        this.transportStatus.put(TransportType.TCP, false);

    }

    public void requestNewSession(TransportType transportType){
        if(transport!=null){
            transport.requestNewSession(transportType);
        }else if(legacyBluetoothTransport != null && !TransportType.BLUETOOTH.equals(transportType)){
            Log.w(TAG, "Session requested for non-bluetooth transport while in legacy mode");
        }
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
                    Log.d(TAG, "Transport connected: " + types[i].name());
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
            if(isLegacyModeEnabled()
                    && TransportType.BLUETOOTH.equals(type) //Make sure it's bluetooth that has be d/c
                    && legacyBluetoothTransport == null){ //Make sure we aren't already in legacy mode
                //Legacy mode has been enabled so we need to cycle
                enterLegacyMode("Router service has enabled legacy mode");
            }else{
                transportListener.onTransportDisconnected("",type);
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
            TransportManager.this.transportStatus.put(TransportType.BLUETOOTH,false);
        }
        transportListener.onTransportDisconnected(info, TransportType.BLUETOOTH);
    }

    public interface TransportEventListener{
        /** Called to indicate and deliver a packet received from transport */
        void onPacketReceived(SdlPacket packet);

        /** Called to indicate that transport connection was established */
        void onTransportConnected(TransportType[] transportTypes);

        /** Called to indicate that transport was disconnected (by either side) */
        void onTransportDisconnected(String info, TransportType type);

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
        static final TransportType[] FAUX_TRANSPORT_ARRAY = {TransportType.BLUETOOTH};

        final BroadcastReceiver disconnectReceiver = new BroadcastReceiver(){
            @Override
            @SuppressWarnings("MissingPermission")
            public void onReceive(Context context, Intent intent){
                String action = intent.getAction();
                if(action != null){
                    if(action.equalsIgnoreCase(BluetoothAdapter.ACTION_STATE_CHANGED)){
                        int bluetoothState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                        switch (bluetoothState) {
                            case BluetoothAdapter.STATE_TURNING_ON:
                            case BluetoothAdapter.STATE_ON:
                                //There is nothing to do in the case the adapter is turning on or just switched to on
                                return;
                            default:
                                break;
                        }
                    }
                    //Otherwise
                    if(provider.get() != null){
                        provider.get().exitLegacyMode("Bluetooth no longer available during legacy mode operation");
                    }
                }
            }
        };

        public LegacyBluetoothHandler(TransportManager provider){
            this.provider = new WeakReference<TransportManager>(provider);

            //Registering our receiver in case of BT disconnect  or shut off
            Context context = provider.contextWeakReference.get();
            if(context != null) {
                IntentFilter disconnectFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                disconnectFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
                disconnectFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
                context.registerReceiver(disconnectReceiver, disconnectFilter);
            }
        }

        protected void unregisterDisconnectReceiver(Context context){
            //Remove the disconnect receiver as it is no longer needed.
            try{
                if(context != null){
                    context.unregisterReceiver(disconnectReceiver);
                }
            }catch (Exception e){}
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
                                service.transportStatus.put(TransportType.BLUETOOTH,true);
                            }
                            service.transportListener.onTransportConnected(FAUX_TRANSPORT_ARRAY);
                            unregisterDisconnectReceiver(service.contextWeakReference.get());
                            break;
                        case MultiplexBaseTransport.STATE_CONNECTING:
                            // Currently attempting to connect - update UI?
                            break;
                        case MultiplexBaseTransport.STATE_LISTEN:
                            break;
                        case MultiplexBaseTransport.STATE_NONE:
                            // We've just lost the connection
                            unregisterDisconnectReceiver(service.contextWeakReference.get());
                            service.exitLegacyMode("Lost connection");
                            break;
                        case MultiplexBaseTransport.STATE_ERROR:
                            Log.d(TAG, "Bluetooth serial server error received, setting state to none, and clearing local copy");
                            unregisterDisconnectReceiver(service.contextWeakReference.get());
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
