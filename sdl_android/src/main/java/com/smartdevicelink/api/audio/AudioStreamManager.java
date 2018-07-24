package com.smartdevicelink.api.audio;

import android.net.rtp.AudioStream;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class AudioStreamManager implements ISdlServiceListener {
    private ISdl internalInterface;
    private int sdlSampleRate;
    private SampleType sdlSampleType;
    private boolean didRequestShutdown = false;
    private Queue<AudioDecoder> queue;

    public AudioStreamManager(@NonNull ISdl internalInterface, @NonNull SamplingRate sampleRate, @NonNull BitsPerSample sampleType) {
        this.internalInterface = internalInterface;
        this.queue = new ArrayBlockingQueue<AudioDecoder>(20);

        switch (sampleRate) {
            case _8KHZ:
                sdlSampleRate = 8000;
                break;
            case _16KHZ:
                sdlSampleRate = 16000;
                break;
            case _22KHZ:
                // common sample rate is 22050, not 22000
                // see https://en.wikipedia.org/wiki/Sampling_(signal_processing)#Audio_sampling
                sdlSampleRate = 22050;
                break;
            case _44KHZ:
                // 2x 22050 is 44100
                // see https://en.wikipedia.org/wiki/Sampling_(signal_processing)#Audio_sampling
                sdlSampleRate = 44100;
                break;
        }

        switch (sampleType) {
            case _8_BIT:
                sdlSampleType = SampleType.UNSIGNED_8_BIT;
                break;
            case _16_BIT:
                sdlSampleType = SampleType.SIGNED_16_BIT;
                break;
        }
    }

    public void startAudioService(boolean encrypted) {
        if (internalInterface != null && internalInterface.isConnected()) {
            internalInterface.addServiceListener(SessionType.PCM, this);
            internalInterface.startAudioService(encrypted);
        }
    }

    public void stopAudioService() {
        if (internalInterface != null && internalInterface.isConnected()) {
            didRequestShutdown = true;
            internalInterface.stopAudioService();
        }
    }


    public void pushAudioFile(File audioFile) {
        AudioDecoder decoder = new AudioDecoder(audioFile, sdlSampleRate, sdlSampleType);


    }

    @Override
    public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted) {

    }

    @Override
    public void onServiceEnded(SdlSession session, SessionType type) {
        if (didRequestShutdown && internalInterface != null) {
            internalInterface.removeServiceListener(SessionType.PCM, this);
        }
    }

    @Override
    public void onServiceError(SdlSession session, SessionType type, String reason) {

    }
}
