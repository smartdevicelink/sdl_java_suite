package com.smartdevicelink.api.audio;

import android.net.rtp.AudioStream;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class AudioStreamManager implements ISdlServiceListener {
    private static final String TAG = AudioStreamManager.class.getSimpleName();

    private ISdl sdlInterface;
    private IAudioStreamListener sdlAudioStream;
    private int sdlSampleRate;
    private SampleType sdlSampleType;

    private final Queue<BaseAudioDecoder> queue;
    private boolean didRequestShutdown = false;

    public AudioStreamManager(@NonNull ISdl sdlInterface, @NonNull SamplingRate sampleRate, @NonNull BitsPerSample sampleType) {
        this.sdlInterface = sdlInterface;
        this.queue = new LinkedList<>();

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
        if (sdlInterface != null && sdlInterface.isConnected()) {
            sdlInterface.addServiceListener(SessionType.PCM, this);
            sdlInterface.startAudioService(encrypted);
        }
    }

    public void stopAudioService() {
        if (sdlInterface != null && sdlInterface.isConnected()) {
            didRequestShutdown = true;
            sdlInterface.stopAudioService();
        }
    }

    public void pushAudioFile(File audioFile) {
        BaseAudioDecoder decoder;
        AudioDecoderListener listener = new AudioDecoderListener() {
            @Override
            public void onAudioDataAvailable(SampleBuffer buffer) {
                sdlAudioStream.sendAudio(buffer.getByteBuffer(), buffer.getPresentationTimeUs());
            }

            @Override
            public void onDecoderFinish() {
                synchronized (queue) {
                    // remove throws an exception if the queue is empty. The decoder of this listener
                    // should still be in this queue so we should be fine by just removing it
                    // if the queue is empty than we have a bug somewhere in the code
                    // and we deserve the crash...
                    queue.remove();

                    // if the queue contains more items then start the first one (without removing it)
                    if (queue.size() > 0) {
                        queue.element().start();
                    }
                }
            }

            @Override
            public void onDecoderError(Exception e) {

            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            decoder = new AudioDecoder(audioFile, sdlSampleRate, sdlSampleType, listener);
        } else {
            // this BaseAudioDecoder subclass uses methods deprecated with api 21
            decoder = new AudioDecoderCompat(audioFile, sdlSampleRate, sdlSampleType, listener);
        }

        synchronized (queue) {
            queue.add(decoder);

            if (queue.size() == 1) {
                decoder.start();
            }
        }
    }

    @Override
    public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted) {
        this.sdlAudioStream = session.startAudioStream();
    }

    @Override
    public void onServiceEnded(SdlSession session, SessionType type) {
        if (didRequestShutdown && sdlInterface != null) {
            session.stopAudioStream();
            sdlAudioStream = null;
            sdlInterface.removeServiceListener(SessionType.PCM, this);
        }
    }

    @Override
    public void onServiceError(SdlSession session, SessionType type, String reason) {
        Log.e(TAG, "OnServiceError: " + reason);
    }
}
