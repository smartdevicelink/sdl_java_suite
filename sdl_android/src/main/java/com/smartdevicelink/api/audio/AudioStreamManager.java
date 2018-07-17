package com.smartdevicelink.api.audio;

import android.net.rtp.AudioStream;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AudioStreamManager implements ISdlServiceListener {
    private ISdl internalInterface;
    private boolean didRequestShutdown = false;
    private BlockingQueue<SdlAudioFile> queue;

    public AudioStreamManager(ISdl internalInterface) {
        this.internalInterface = internalInterface;
        this.queue = new ArrayBlockingQueue<SdlAudioFile>(20);
    }

    public void start(boolean encrypted) {
        if(internalInterface != null && internalInterface.isConnected()){
            internalInterface.addServiceListener(SessionType.PCM, this);
            internalInterface.startAudioService(encrypted);
        }
    }

    public void stop() {
        if(internalInterface != null && internalInterface.isConnected()){
            didRequestShutdown = true;
            internalInterface.stopAudioService();
        }
    }

    @Override
    public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted) {

    }

    @Override
    public void onServiceEnded(SdlSession session, SessionType type) {
        if(didRequestShutdown && internalInterface != null){
            internalInterface.removeServiceListener(SessionType.PCM, this);
        }
    }

    @Override
    public void onServiceError(SdlSession session, SessionType type, String reason) {

    }
}
