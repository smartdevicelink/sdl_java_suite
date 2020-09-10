/*
 * Copyright (c) 2019 Livio, Inc.
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

import java.util.ArrayList;
import java.util.List;

public abstract class TransportManagerBase {
    private static final String TAG = "TransportManagerBase";

    final Object TRANSPORT_STATUS_LOCK;

    final List<TransportRecord> transportStatus;
    final TransportEventListener transportListener;

    public TransportManagerBase(BaseTransportConfig config,TransportEventListener listener){
        transportListener = listener;
        this.TRANSPORT_STATUS_LOCK = new Object();
        synchronized (TRANSPORT_STATUS_LOCK){
            this.transportStatus = new ArrayList<>();
        }
    }

    public abstract void start();

    public abstract void close(long sessionId);

    /**
     * Check to see if a transport is connected.
     * @param transportType the transport to have its connection status returned. If `null` is
     *                      passed in, all transports will be checked and if any are connected a
     *                      true value will be returned.
     * @param address the address associated with the transport type. If null, the first transport
     *                of supplied type will be used to return if connected.
     * @return if a transport is connected based on included variables
     */
    public abstract boolean isConnected(TransportType transportType, String address);
    /**
     * Retrieve a transport record with the supplied params
     * @param transportType the transport to have its connection status returned.
     * @param address the address associated with the transport type. If null, the first transport
     *                of supplied type will be returned.
     * @return the transport record for the transport type and address if supplied
     */
    public abstract TransportRecord getTransportRecord(TransportType transportType, String address);


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
                        || record.getType().equals(TransportType.TCP)
                        || record.getType().equals(TransportType.WEB_SOCKET_SERVER)) {
                    return true;
                }
            }
            return false;
        }
    }

    public  BaseTransportConfig updateTransportConfig(BaseTransportConfig config){
        return config;
    }

    public abstract void sendPacket(SdlPacket packet);

    /**
     * Base implementation does nothing and assumes it is not necssary. This method should be
     * overridden in children classes that need to add a prerequest to their transports to make
     * space ready for a new session.
     * @param transportRecord the transport that the new session should be assigned to
     */
    public void requestNewSession(TransportRecord transportRecord){
        //Base implementation does nothing
    }

    public void requestSecondaryTransportConnection(byte sessionId, TransportRecord transportRecord){
        //Base implementation does nothing
    }

    synchronized void enterLegacyMode(final String info){
        //Base implementation does nothing
    }

    synchronized void exitLegacyMode(String info ){
        //Base implementation does nothing
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
