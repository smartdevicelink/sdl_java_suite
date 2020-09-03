/*
 * Copyright (c) 2018 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartdevicelink.transport;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.enums.ControlFrameTags;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.SdlDeviceListener;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.List;

@SuppressWarnings("unused")
public class TransportManager extends TransportManagerBase{
    private static final String TAG = "TransportManager";

    TransportBrokerImpl transport;

    //Legacy Transport
    MultiplexBluetoothTransport legacyBluetoothTransport;
    LegacyBluetoothHandler legacyBluetoothHandler;

    final WeakReference<Context> contextWeakReference;
    final MultiplexTransportConfig mConfig;


    /**
     * Managing transports
     * List for status of all transports
     * If transport is not connected. Request Router service connect to it. Get connected message
     */

    public TransportManager(MultiplexTransportConfig config, TransportEventListener listener){
        super(config,listener);

        this.mConfig = config;

        if(config.service == null) {
            config.service = SdlBroadcastReceiver.consumeQueuedRouterService();
        }

        contextWeakReference = new WeakReference<>(config.context);
    }

    /**
     * start internally validates the target ROuterService, which was done in ctor before.
     */
    @Override
    public void start() {
        final RouterServiceValidator validator = new RouterServiceValidator(mConfig);
        validator.validateAsync(new RouterServiceValidator.ValidationStatusCallback() {
            @Override
            public void onFinishedValidation(boolean valid, ComponentName name) {
                DebugTool.logInfo(TAG, "onFinishedValidation valid=" + valid + "; name=" + ((name == null)? "null" : name.getPackageName()));
                if (valid) {
                    mConfig.service = name;
                    transport = new TransportBrokerImpl(contextWeakReference.get(), mConfig.appId, mConfig.service);
                    DebugTool.logInfo(TAG, "TransportManager start was called; transport=" + transport);
                    if(transport != null){
                        transport.start();
                    }
                } else {
                    enterLegacyMode("Router service is not trusted. Entering legacy mode");
                    if(legacyBluetoothTransport != null){
                        legacyBluetoothTransport.start();
                    }
                }
            }
        });
    }

    @Override
    public void close(long sessionId){
        if(transport != null) {
            transport.removeSession(sessionId);
            transport.stop();
        }else if(legacyBluetoothTransport != null){
            legacyBluetoothTransport.stop();
            legacyBluetoothTransport = null;
        }
    }

    @Override
    @Deprecated
    public void resetSession(){
        if(transport != null){
            transport.resetSession();
        }
    }

    /**
     * Check to see if a transport is connected.
     * @param transportType the transport to have its connection status returned. If `null` is
     *                      passed in, all transports will be checked and if any are connected a
     *                      true value will be returned.
     * @param address the address associated with the transport type. If null, the first transport
     *                of supplied type will be used to return if connected.
     * @return if a transport is connected based on included variables
     */
    @Override
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
    /**
     * Retrieve a transport record with the supplied params
     * @param transportType the transport to have its connection status returned.
     * @param address the address associated with the transport type. If null, the first transport
     *                of supplied type will be returned.
     * @return the transport record for the transport type and address if supplied
     */
    @Override
    public TransportRecord getTransportRecord(TransportType transportType, String address){
        synchronized (TRANSPORT_STATUS_LOCK) {
            if (transportType == null) {
                return null;
            }
            for (TransportRecord record : transportStatus) {
                if (record.getType().equals(transportType)) {
                    if (address != null) {
                        if (address.equals(record.getAddress())) {
                            return record;
                        } // Address doesn't match, move forward
                    } else {
                        //If no address is included, assume any transport of correct type is acceptable
                        return record;
                    }
                }
            }
            return null;
        }
    }

    /**
     * Retrieves the currently connected transports
     * @return the currently connected transports
     */
    @Override
    public List<TransportRecord> getConnectedTransports(){
        return this.transportStatus;
    }

    @Override
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

    @Override
    public BaseTransportConfig updateTransportConfig(BaseTransportConfig config){
        if(transport != null && TransportType.MULTIPLEX.equals(config.getTransportType())){
            ((MultiplexTransportConfig)config).setService(transport.getRouterService());
        }
        return config;
    }

    @Deprecated
    public ComponentName getRouterService(){
        if(transport != null) {
            return transport.getRouterService();
        }
        return null;
    }

    @Override
    public void sendPacket(SdlPacket packet){
        if(transport !=null){
            transport.sendPacketToRouterService(packet);
        }else if(legacyBluetoothTransport != null){
            byte[] data = packet.constructPacket();
            legacyBluetoothTransport.write(data, 0, data.length);
        }
    }

    @Override
    public void requestNewSession(TransportRecord transportRecord){
        if(transport != null){
            transport.requestNewSession(transportRecord);
        }else if(legacyBluetoothTransport != null){
            DebugTool.logWarning(TAG, "Session requested for non-bluetooth transport while in legacy mode");
        }
    }

    @Deprecated
    public void requestSecondaryTransportConnection(byte sessionId, Bundle params){
        transport.requestSecondaryTransportConnection(sessionId, (Bundle)params);
    }

    @Override
    public void requestSecondaryTransportConnection(byte sessionId, TransportRecord transportRecord){
        if(transportRecord != null){
            Bundle bundle = new Bundle();
            bundle.putString(TransportConstants.TRANSPORT_TYPE, transportRecord.getType().name());
            if(transportRecord.getType().equals(TransportType.TCP)) {
                String address =  transportRecord.getAddress();
                if(address.contains(":")){
                    String[] split = address.split(":");
                    if(split.length == 2) {
                        bundle.putString(ControlFrameTags.RPC.TransportEventUpdate.TCP_IP_ADDRESS, split[0]);
                        bundle.putInt(ControlFrameTags.RPC.TransportEventUpdate.TCP_PORT, Integer.valueOf(split[1]));
                    } //else {something went wrong;}
                }else{
                    bundle.putString(ControlFrameTags.RPC.TransportEventUpdate.TCP_IP_ADDRESS, address);
                }


            }
            transport.requestSecondaryTransportConnection(sessionId, bundle);
        }
    }

    protected class TransportBrokerImpl extends TransportBroker{

        boolean shuttingDown = false;
        public TransportBrokerImpl(Context context, String appId, ComponentName routerService){
            super(context,appId,routerService);
        }

        @Override
        public synchronized boolean onHardwareConnected(List<TransportRecord> transports) {
            super.onHardwareConnected(transports);
            DebugTool.logInfo(TAG, "OnHardwareConnected");
            if(shuttingDown){
                return false;
            }
            synchronized (TRANSPORT_STATUS_LOCK){
                transportStatus.clear();
                transportStatus.addAll(transports);
            }
            //If a bluetooth device has connected, make sure to save the mac address in the case
            //this app is asked to host the router service, the app knows to do so immediately on connection.
            if(transports != null && transports.size() > 0) {
                for (TransportRecord record : transports) {
                    if(record != null && TransportType.BLUETOOTH.equals(record.getType())) {
                        SdlDeviceListener.setSDLConnectedStatus(contextWeakReference.get(), record.getAddress(),true);
                    }
                }
            }

            transportListener.onTransportConnected(transports);
            return true;
        }


        @Override
        public synchronized void onHardwareDisconnected(TransportRecord record, List<TransportRecord> connectedTransports) {
            if(record != null){
                DebugTool.logInfo(TAG, "Transport disconnected - " + record);
            }else{
                DebugTool.logInfo(TAG, "Transport disconnected");

            }
            if(shuttingDown){
                return;
            }
            synchronized (TRANSPORT_STATUS_LOCK){
                boolean wasRemoved = TransportManager.this.transportStatus.remove(record);
                //Might check connectedTransports vs transportStatus to ensure they are equal

                //If the transport wasn't removed, check RS version for corner case
                if(!wasRemoved && record != null &&getRouterServiceVersion() == 8){
                    boolean foundMatch = false;
                    //There is an issue in the first gen of multi transport router services that
                    //will remove certain extras from messages to the TransportBroker if older apps
                    //are connected that do not support the multi transport messages. Because of
                    //that, we check the records we have and if the transport matches we assume it
                    //was the original transport that was received regardless of the address.
                    TransportType disconnectedTransportType = record.getType();
                    if(disconnectedTransportType != null) {
                        for (TransportRecord transportRecord : TransportManager.this.transportStatus) {
                            if (disconnectedTransportType.equals(transportRecord.getType())) {
                                //The record stored in the TM will contain the actual record the
                                //protocol layer used during the transport connection event
                                record = transportRecord;
                                foundMatch = true;
                                break;
                            }
                        }

                        if (foundMatch) { //Remove item after the loop to avoid concurrent modifications
                            TransportManager.this.transportStatus.remove(record);
                            DebugTool.logInfo(TAG, "Handling corner case of transport disconnect mismatch");
                        }
                    }
                }
            }

            if(isLegacyModeEnabled()
                    && record != null
                    && TransportType.BLUETOOTH.equals(record.getType())){ //Make sure it's bluetooth that has be d/c
                    //&& legacyBluetoothTransport == null){ //Make sure we aren't already in legacy mode
                if(legacyBluetoothTransport == null) {
                    //Legacy mode has been enabled so we need to cycle
                    enterLegacyModeAndStart("Router service has enabled legacy mode");
                }
            }else{
                //Inform the transport listener that a transport has disconnected
                transportListener.onTransportDisconnected("", record, connectedTransports);
            }
        }

        @Override
        public synchronized void onLegacyModeEnabled() {
            if(shuttingDown){
                return;
            }
            if( legacyBluetoothTransport == null){
                //First remove the connected bluetooth transport if one exists
                TransportRecord toBeRemoved = null;
                for (TransportRecord transportRecord : TransportManager.this.transportStatus) {
                    if (TransportType.BLUETOOTH.equals(transportRecord.getType())) {
                        //There was a previously connected bluetooth transport through the router service
                        toBeRemoved = transportRecord;
                        break;
                    }
                }

                if(toBeRemoved != null){ //Remove item after the loop to avoid concurrent modifications
                    TransportManager.this.transportStatus.remove(toBeRemoved);
                }

                enterLegacyModeAndStart("Router service has enabled legacy mode");
            }
        }

        @Override
        public void onPacketReceived(Parcelable packet) {
            if(packet!=null){
                transportListener.onPacketReceived((SdlPacket)packet);
            }
        }

        @Override
        public synchronized void stop() {
            shuttingDown = true;
            super.stop();
        }
    }

    void enterLegacyModeAndStart(final String info){
        enterLegacyMode(info);
        if(legacyBluetoothTransport != null
                && legacyBluetoothTransport.getState() == MultiplexBaseTransport.STATE_NONE){
            legacyBluetoothTransport.start();
        }
    }


    @Override
    synchronized void enterLegacyMode(final String info){
        if(legacyBluetoothTransport != null && legacyBluetoothHandler != null){
            return; //Already in legacy mode
        }

        if(transportListener.onLegacyModeEnabled(info)) {
            if(Looper.myLooper() == null){
                Looper.prepare();
            }
            legacyBluetoothHandler = new LegacyBluetoothHandler(this);
            legacyBluetoothTransport = new MultiplexBluetoothTransport(legacyBluetoothHandler);
            if(contextWeakReference.get() != null){
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
                intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
                contextWeakReference.get().registerReceiver(legacyDisconnectReceiver, intentFilter );
            }
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    transportListener.onError(info + " - Legacy mode unacceptable; shutting down.");

                }
            },500);
        }
    }

    @Override
    synchronized void exitLegacyMode(String info ){
        TransportRecord legacyTransportRecord = null;
        if(legacyBluetoothTransport != null){
            legacyTransportRecord = legacyBluetoothTransport.getTransportRecord();
            legacyBluetoothTransport.stop();
            legacyBluetoothTransport = null;
        }
        legacyBluetoothHandler = null;
        synchronized (TRANSPORT_STATUS_LOCK){
            TransportManager.this.transportStatus.clear();
        }
        if(contextWeakReference !=null){
            try{
                contextWeakReference.get().unregisterReceiver(legacyDisconnectReceiver);
            }catch (Exception e){}
        }
        transportListener.onTransportDisconnected(info, legacyTransportRecord,null);
    }


    private BroadcastReceiver legacyDisconnectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
                    exitLegacyMode("Bluetooth disconnected");
                }else if(action.equalsIgnoreCase(BluetoothAdapter.ACTION_STATE_CHANGED)){
                    int bluetoothState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                    if(bluetoothState == BluetoothAdapter.STATE_TURNING_OFF || bluetoothState == BluetoothAdapter.STATE_OFF){
                        DebugTool.logInfo(TAG, "Bluetooth is shutting off, exiting legacy mode.");
                        exitLegacyMode("Bluetooth adapter shutting off");
                    }
                }
            }
        }
    };

    protected static class LegacyBluetoothHandler extends Handler{

        final WeakReference<TransportManager> provider;

        public LegacyBluetoothHandler(TransportManager provider){
            this.provider = new WeakReference<>(provider);
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
                            if(service.transport != null){
                                service.transport.stop();
                                service.transport = null;
                            }
                            break;
                        case MultiplexBaseTransport.STATE_NONE:
                            // We've just lost the connection
                            service.exitLegacyMode("Lost connection");
                            break;
                        case MultiplexBaseTransport.STATE_ERROR:
                            DebugTool.logInfo(TAG, "Bluetooth serial server error received, setting state to none, and clearing local copy");
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
