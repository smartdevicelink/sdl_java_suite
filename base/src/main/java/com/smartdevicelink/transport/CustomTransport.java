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
import com.smartdevicelink.util.DebugTool;

import java.nio.ByteBuffer;

public abstract class CustomTransport implements TransportInterface{
    private static final String TAG = "CustomTransport";

    final TransportRecord transportRecord;
    final SdlPsm psm;
    TransportCallback transportCallback;



    public CustomTransport(String address) {
        //Creates a callback for when packets
        psm = new SdlPsm();
        transportRecord = new TransportRecord(TransportType.CUSTOM,address);
    }

    public TransportRecord getTransportRecord(){
        return this.transportRecord;
    }


    /**
     * Call this method when reading a byte array off the transport
     * @param bytes the bytes read off the transport
     */
    public synchronized void onByteArrayReceived (byte[] bytes, int offset, int length) {

        if(bytes != null && bytes.length > 0){
            boolean stateProgress;
            for(int i = 0; i < length; i++){
                stateProgress = psm.handleByte(bytes[i]);
                if (!stateProgress) {//We are trying to weed through the bad packet info until we get something
                    //Log.w(TAG, "Packet State Machine did not move forward from state - "+ psm.getState()+". PSM being Reset.");
                    psm.reset();
                }

                if (psm.getState() == SdlPsm.FINISHED_STATE) {
                    SdlPacket packet = psm.getFormedPacket();
                    if (transportCallback != null && packet != null) {
                        packet.setTransportRecord(transportRecord);
                        transportCallback.onPacketReceived(packet);
                    }
                    //We put a trace statement in the message read so we can avoid all the extra bytes
                    psm.reset();
                }
            }

        }
    }

    /**
     * Call this method when reading a ByteBuffer off the transport
     * @param message the byte buffer that was read off the transport
     */
    public synchronized void onByteBufferReceived (ByteBuffer message) {
        if(message != null){
            boolean stateProgress;
            while (message.hasRemaining()) {
                stateProgress = psm.handleByte(message.get());
                if (!stateProgress) {//We are trying to weed through the bad packet info until we get something

                    //Log.w(TAG, "Packet State Machine did not move forward from state - "+ psm.getState()+". PSM being Reset.");
                    psm.reset();
                }

                if (psm.getState() == SdlPsm.FINISHED_STATE) {
                    SdlPacket packet = psm.getFormedPacket();
                    if (transportCallback != null && packet != null) {
                        packet.setTransportRecord(transportRecord);
                        transportCallback.onPacketReceived(packet);
                    }
                    //We put a trace statement in the message read so we can avoid all the extra bytes
                    psm.reset();
                }
            }

        }
    }

    @Override
    public void start() {
        if (transportCallback != null) {
            transportCallback.onConnectionEstablished();
        }
    }

    @Override
    public void stop() {
        if (transportCallback != null) {
            transportCallback.onConnectionTerminated("Transport told to stop");
        }
    }

    @Override
    public void write(SdlPacket packet) {
        byte[] bytes = packet.constructPacket();
        if(bytes != null && bytes.length > 0) {
            try {
                onWrite(bytes, 0, bytes.length);
            } catch (Exception exc) {
                DebugTool.logError("Error attempting to write packet", exc);
            }
        }
    }

    @Override
    public void setCallback(TransportCallback transportCallback) {
        this.transportCallback = transportCallback;
    }

    public void onError(){
        if (transportCallback != null) {
            transportCallback.onError();
        }
    }


    /**
     * Integrator should write out these bytes to whatever actual transport there is. This will be called from the
     * internals of the library.
     * @param bytes a deconstructed packet into a byte array that needs to be written out
     * @param offset in bytes
     * @param length in bytes
     */
    public abstract void onWrite(byte[] bytes, int offset, int length);





}
