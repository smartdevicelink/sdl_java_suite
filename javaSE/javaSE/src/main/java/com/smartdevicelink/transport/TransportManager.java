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


import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;

import java.util.Collections;
import java.util.List;

public class TransportManager extends TransportManagerBase {
    private static final String TAG = "TransportManager";

    TransportInterface transport;

    /**
     * Managing transports
     * List for status of all transports
     * If transport is not connected. Request Router service connect to it. Get connected message
     */

    public TransportManager(BaseTransportConfig config, TransportEventListener listener) {
        super(config, listener);

        //Start the new transport
        switch (config.getTransportType()) {
            case WEB_SOCKET_SERVER:
                transport = new WebSocketServer((WebSocketServerConfig) config, new SingleTransportCallbackImpl(new TransportRecord(TransportType.WEB_SOCKET_SERVER, "127.0.0.1:" + ((WebSocketServerConfig) config).port)));
                break;
            case CUSTOM:
                transport = ((CustomTransportConfig) config).getTransportInterface();
                transport.setCallback(new SingleTransportCallbackImpl(transport.getTransportRecord()));
                break;
        }

    }

    @Override
    public void start() {
        if (transport != null) {
            transport.start();
        } else {
            System.out.print("Unable to start transport.");
        }
    }

    @Override
    public void close(long sessionId) {
        if (transport != null) {
            transport.stop();
        }
    }

    /**
     * Check to see if a transport is connected.
     *
     * @param transportType the transport to have its connection status returned. If `null` is
     *                      passed in, all transports will be checked and if any are connected a
     *                      true value will be returned.
     * @param address       the address associated with the transport type. If null, the first transport
     *                      of supplied type will be used to return if connected.
     * @return if a transport is connected based on included variables
     */
    @Override
    public boolean isConnected(TransportType transportType, String address) {
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
     *
     * @param transportType the transport to have its connection status returned.
     * @param address       the address associated with the transport type. If null, the first transport
     *                      of supplied type will be returned.
     * @return the transport record for the transport type and address if supplied
     */
    @Override
    public TransportRecord getTransportRecord(TransportType transportType, String address) {
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


    @Override
    public void sendPacket(SdlPacket packet) {
        if (transport != null) {
            transport.write(packet);
        } else {

        }
    }

    class SingleTransportCallbackImpl implements TransportCallback {

        final List<TransportRecord> finalList;
        final TransportRecord record;

        protected SingleTransportCallbackImpl(TransportRecord transportRecord) {
            record = transportRecord;
            finalList = Collections.singletonList(record);
        }

        @Override
        public void onConnectionEstablished() {
            synchronized (TRANSPORT_STATUS_LOCK) {
                transportStatus.clear();
                transportStatus.addAll(finalList);
            }
            transportListener.onTransportConnected(finalList);
        }

        @Override
        public void onError() {
            DebugTool.logError(TAG, "Error in the transport manager from the web socket server");
            if (transportListener != null) {
                transportListener.onError("");
            }
        }

        @Override
        public void onConnectionTerminated(String reason) {
            if (record != null) {
                DebugTool.logInfo(TAG, "Transport disconnected - " + record);
            } else {
                DebugTool.logInfo(TAG, "Transport disconnected");

            }

            synchronized (TRANSPORT_STATUS_LOCK) {
                TransportManager.this.transportStatus.remove(record);
                //Might check connectedTransports vs transportStatus to ensure they are equal
            }
            //Inform the transport listener that a transport has disconnected
            transportListener.onTransportDisconnected(reason, record, Collections.EMPTY_LIST);
        }

        @Override
        public void onPacketReceived(SdlPacket packet) {
            if (packet != null) {
                transportListener.onPacketReceived(packet);
            }
        }
    }

}
