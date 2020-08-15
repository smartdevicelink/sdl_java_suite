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
import com.smartdevicelink.transport.utl.SSLWebSocketFactoryGenerator;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketServerFactory;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class WebSocketServer extends org.java_websocket.server.WebSocketServer implements TransportInterface{
    private static final String TAG = "WebSocketServer";
    TransportCallback callback;
    WebSocketServerConfig config;
    WebSocket webSocket;
    SdlPsm psm;

    final TransportRecord transportRecord;

    public WebSocketServer(WebSocketServerConfig config, TransportCallback callback){
        super((new InetSocketAddress(config.port)));

        this.config = config;
        this.callback = callback;
        transportRecord = new TransportRecord(TransportType.WEB_SOCKET_SERVER,"127.0.0.1:" + config.port); //If changed, change in transport manager as well
        //This will set the connection lost timeout to not occur. So we might ping, but not pong
        this.setConnectionLostTimeout(config.connectionLostTimeout);
        if(config.getSslConfig() != null){
            WebSocketServerFactory factory = SSLWebSocketFactoryGenerator.generateWebSocketServer(config.getSslConfig());
            if(factory!=null){
                this.setWebSocketFactory(factory);
            }else{
                DebugTool.logError(TAG, "WebSocketServer: Unable to generate SSL Web Socket Server Factory");
            }
        }

    }

    public TransportRecord getTransportRecord(){
        return this.transportRecord;
    }

    @Override
    public void stop(){
        try {
            this.stop(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(SdlPacket packet){
        //DebugTool.logInfo(TAG, "Atttempt to write packet " + packet);
        if(packet != null
                && this.webSocket != null
                && this.webSocket.isOpen()) {
            this.webSocket.send(packet.constructPacket());
        }

    }

    @Override
    public void setCallback(TransportCallback callback) {

    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        DebugTool.logInfo(TAG, "onOpen");
        this.webSocket = webSocket;

        if(callback!=null){
            callback.onConnectionEstablished();
        }
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        DebugTool.logInfo(TAG, "onClose");
        try{
            DebugTool.logInfo(TAG, "Closing id - " + i);
            DebugTool.logInfo(TAG, "Closing string - " + s);
            DebugTool.logInfo(TAG, "Closing from remote?  " + b);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(callback!=null) {
            callback.onConnectionTerminated(s);
        }
    }


    @Override
    public void onWebsocketCloseInitiated(WebSocket conn, int code, String reason) {
        super.onWebsocketCloseInitiated(conn, code, reason);
        try{
            DebugTool.logInfo(TAG, "Code - " + code + " Reason - " + reason);
        }catch (Exception e){}
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        DebugTool.logError(TAG, "Incorrect message type received, dropping. - String: " + s);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        super.onMessage(conn, message);
        //DebugTool.logInfo(TAG, "on Message - ByteBuffer");
        byte input;

        if(message != null){
            synchronized (WebSocketServer.this) {
                boolean stateProgress;
                while (message.hasRemaining()) {
                    input = message.get();
                    stateProgress = psm.handleByte(input);
                    if (!stateProgress) {//We are trying to weed through the bad packet info until we get something

                        //DebugTool.logWarning("Packet State Machine did not move forward from state - "+ psm.getState()+". PSM being Reset.");
                        psm.reset();
                    }

                    if (psm.getState() == SdlPsm.FINISHED_STATE) {
                        synchronized (WebSocketServer.this) {
                            SdlPacket packet = psm.getFormedPacket();
                            if (callback != null && packet != null) {
                               /// DebugTool.logInfo(TAG, "Read a packet: " + packet);
                                packet.setTransportRecord(transportRecord);
                                callback.onPacketReceived(packet);
                            }
                        }
                        //We put a trace statement in the message read so we can avoid all the extra bytes
                        psm.reset();
                    }
                }
            }

        }

    }



    @Override
    public void onError(WebSocket webSocket, Exception e) {
        DebugTool.logError(TAG, "bad", e);
        if(callback!=null) {
            callback.onError();
        }
    }

    @Override
    public void onStart() {
        DebugTool.logInfo(TAG, "onStart");
        psm = new SdlPsm();

    }

}
