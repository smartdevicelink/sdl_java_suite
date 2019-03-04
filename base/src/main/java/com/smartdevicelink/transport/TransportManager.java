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


import android.util.Log;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class TransportManager {
    private static final String TAG = "TransportManager";

    private final Object TRANSPORT_STATUS_LOCK;

    WebSocketServer2 transport;
    final List<TransportRecord> transportStatus;
    final TransportEventListener transportListener;



    /**
     * Managing transports
     * List for status of all transports
     * If transport is not connected. Request Router service connect to it. Get connected message
     */

    public TransportManager(WebSocketServerConfig config, TransportEventListener listener){

        this.transportListener = listener;
        this.TRANSPORT_STATUS_LOCK = new Object();
        synchronized (TRANSPORT_STATUS_LOCK){
            this.transportStatus = new ArrayList<>();
        }
        final TransportRecord record = new TransportRecord(TransportType.WEB_SOCKET_SERVER,"127.0.0.1:"+config.port);
        final List<TransportRecord> finalList = Collections.singletonList(record);
        //Start the new transport
        transport = new WebSocketServer2(config, new WebSocketServer2.Callback() {

            @Override
            public void onConnectionEstablished() {
                synchronized (TRANSPORT_STATUS_LOCK){
                    transportStatus.clear();
                    transportStatus.addAll(finalList);
                }
                transportListener.onTransportConnected(finalList);
            }

            @Override
            public void onError() {
                Log.e(TAG, "Error in the transport manager from the web socket server");
                if(transportListener != null){
                    transportListener.onError("");
                }
            }

            @Override
            public void onConnectionTerminated() {
                if(record != null){
                    Log.d(TAG, "Transport disconnected - " + record);
                }else{
                    Log.d(TAG, "Transport disconnected");

                }

                synchronized (TRANSPORT_STATUS_LOCK){
                    TransportManager.this.transportStatus.remove(record);
                    //Might check connectedTransports vs transportStatus to ensure they are equal
                }
                //Inform the transport listener that a transport has disconnected
                transportListener.onTransportDisconnected("", record, new ArrayList<>()); //FIXME
            }

            @Override
            public void onStateChanged(int previousState, int newState) {

            }

            @Override
            public void onPacketReceived(SdlPacket packet) {
                if(packet!=null){
                    transportListener.onPacketReceived(packet);
                }
            }
        });

    }

    public void start(){
        if(transport != null){
            transport.start();
        }else{
            System.out.print("Unable to start transport.");
        }
    }

    public void close(long sessionId){
        if(transport != null) {
            transport.stop();
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
    public List<TransportRecord> getConnectedTransports(){
        return this.transportStatus;
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

    //FIXME
    public Object getRouterService(){
        return null;
    }

    public void sendPacket(SdlPacket packet){
        if(transport !=null){
            transport.write(packet);
        }else {

        }
    }

    public void requestNewSession(TransportRecord transportRecord){
        //FIXME do nothing
    }

    public void requestSecondaryTransportConnection(byte sessionId, Object params){
    	//FIXME do nothing
    }

    private synchronized void enterLegacyMode(final String info){
        //FIXME do nothing
    }

    protected synchronized void exitLegacyMode(String info ){
        //FIXME do nothing
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
         * @param info simple info string about the situation
         * @return if the listener is ok with entering legacy mode
         */
        boolean onLegacyModeEnabled(String info);
    }




}
