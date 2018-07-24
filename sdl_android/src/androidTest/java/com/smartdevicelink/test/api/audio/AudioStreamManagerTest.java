package com.smartdevicelink.test.api.audio;

import com.smartdevicelink.api.audio.AudioStreamManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;

import junit.framework.TestCase;

public class AudioStreamManagerTest extends TestCase {

    ISdl sdlInterface = new ISdl() {
        @Override
        public void start() {

        }

        @Override
        public void stop() {

        }

        @Override
        public boolean isConnected() {
            return false;
        }

        @Override
        public void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {

        }

        @Override
        public void removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {

        }

        @Override
        public void startVideoService(VideoStreamingParameters parameters, boolean encrypted) {

        }

        @Override
        public void stopVideoService() {

        }

        @Override
        public void startAudioService(boolean encrypted) {

        }

        @Override
        public void stopAudioService() {

        }

        @Override
        public void sendRPCRequest(RPCRequest message) {

        }

        @Override
        public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {

        }

        @Override
        public boolean removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {
            return false;
        }
    };


    public void testCreatingAudioStreamManager() {
        AudioStreamManager manager = new AudioStreamManager(sdlInterface, SamplingRate._16KHZ, BitsPerSample._16_BIT);
    }

    public void testStartAudioStreamManager() {
        AudioStreamManager manager = new AudioStreamManager(sdlInterface, SamplingRate._16KHZ, BitsPerSample._16_BIT);
        manager.startAudioService(false);
    }

}
