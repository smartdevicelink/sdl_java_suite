package com.smartdevicelink.transport;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.security.MessageDigest;

/**
 * Created by Joey Grover on 1/23/18.
 */

public class IpcSocketServer extends WebSocketServer {
    private static final String TAG = "IpcSocketServer";
    byte[] key;
    final String packageName;

    public IpcSocketServer(String packageName){
        super((new InetSocketAddress(1985)));
        this.packageName = packageName;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((System.currentTimeMillis()+"").getBytes());
            key = md.digest();
            Log.d(TAG, "Key: " + key);
        }catch (Exception e){

        }
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        Log.d(TAG, "onOpen");
        conn.send(packageName);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        Log.d(TAG, "onClose");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Log.d(TAG, "onMessage: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        Log.d(TAG, "onError");
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
    }

    public byte[] getKey(){
        return key;
    }
}
