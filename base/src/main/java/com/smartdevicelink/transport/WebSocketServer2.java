package com.smartdevicelink.transport;

import android.util.Log;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class WebSocketServer2 extends WebSocketServer {
    private static final String TAG = "WebSocketServer2";
    com.smartdevicelink.transport.WebSocketServer2.Callback callback;
    WebSocketServerConfig config;
    WebSocket webSocket;
    SdlPsm psm;

    final TransportRecord record;

    public WebSocketServer2(WebSocketServerConfig config, com.smartdevicelink.transport.WebSocketServer2.Callback callback){
        super((new InetSocketAddress(config.port)));

        this.config = config;
        this.callback = callback;
        record = new TransportRecord(TransportType.WEB_SOCKET_SERVER,"127.0.0.1:" + config.port); //If changed, change in transport manager as well
        //This will set the connection lost timeout to not occur. So we might ping, but not pong
        this.setConnectionLostTimeout(config.connectionLostTimeout);

    }

    public void stop(){
        try {
            this.stop(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void write(SdlPacket packet){
        //Log.i(TAG, "Atttempt to write packet " + packet);
        if(packet != null
                && this.webSocket != null
                && this.webSocket.isOpen()) {
            this.webSocket.send(packet.constructPacket());
        }

    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        Log.i(TAG, "onOpen");
        this.webSocket = webSocket;

        if(callback!=null){
            callback.onConnectionEstablished();
        }
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        Log.i(TAG, "onClose");
        try{
            DebugTool.logInfo("Closing id - " + i);
            DebugTool.logInfo("Closing string - " + s);
            DebugTool.logInfo("Closing from remote?  " + b);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(callback!=null) {
            callback.onConnectionTerminated();
        }
    }


    @Override
    public void onWebsocketCloseInitiated(WebSocket conn, int code, String reason) {
        super.onWebsocketCloseInitiated(conn, code, reason);
        try{
            DebugTool.logInfo("Code - " + code + " Reason - " + reason);
        }catch (Exception e){}
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        Log.i(TAG, "on Message - String");

        //TODO

    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        super.onMessage(conn, message);
        //Log.i(TAG, "on Message - ByteBuffer");
        byte input;

        if(message != null){
            synchronized (WebSocketServer2.this) {
                boolean stateProgress;
                while (message.hasRemaining()) {
                    input = message.get();
                    stateProgress = psm.handleByte(input);
                    if (!stateProgress) {//We are trying to weed through the bad packet info until we get something

                        //Log.w(TAG, "Packet State Machine did not move forward from state - "+ psm.getState()+". PSM being Reset.");
                        psm.reset();
                    }

                    if (psm.getState() == SdlPsm.FINISHED_STATE) {
                        synchronized (com.smartdevicelink.transport.WebSocketServer2.this) {
                            SdlPacket packet = psm.getFormedPacket();
                            if (callback != null && packet != null) {
                               /// Log.i(TAG, "Read a packet: " + packet);
                                packet.setTransportRecord(record);
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
        Log.e(TAG, "bad", e);
        if(callback!=null) {
            callback.onError();
        }
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
        psm = new SdlPsm();

    }

    public interface Callback{
        void onConnectionEstablished();
        void onError();
        void onConnectionTerminated();
        void onStateChanged(int previousState, int newState); //TODO determine if this is needed
        void onPacketReceived(SdlPacket packet);
    }
}
