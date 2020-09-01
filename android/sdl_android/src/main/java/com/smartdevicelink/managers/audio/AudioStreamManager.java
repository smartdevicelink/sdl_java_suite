/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
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
package com.smartdevicelink.managers.audio;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.StreamingStateMachine;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The AudioStreamManager class provides methods to start and stop an audio stream
 * to the connected device. Audio files can be pushed to the manager in order to
 * play them on the connected device. The manager uses the Android built-in MediaCodec.
 */
public class AudioStreamManager extends BaseAudioStreamManager {
    private static final String TAG = AudioStreamManager.class.getSimpleName();
    private static final int COMPLETION_TIMEOUT = 2000;

    private IAudioStreamListener sdlAudioStream;
    private int sdlSampleRate;
    private @SampleType int sdlSampleType;
    private final Queue<BaseAudioDecoder> queue;
    private final WeakReference<Context> context;
    private final StreamingStateMachine streamingStateMachine;
    private AudioPassThruCapabilities audioStreamingCapabilities;
    private HMILevel hmiLevel;
    private boolean isTransportAvailable = false;
    // This completion listener is used as a callback to the app developer when starting/stopping audio service
    private CompletionListener serviceCompletionListener;
    // As the internal interface does not provide timeout we need to use a future task
    private final Handler serviceCompletionHandler;

    private final Runnable serviceCompletionTimeoutCallback = new Runnable() {
        @Override
        public void run() {
            serviceListener.onServiceError(null, SessionType.PCM, "Service operation timeout reached");
        }
    };



    // INTERNAL INTERFACE

    private final ISdlServiceListener serviceListener = new ISdlServiceListener() {
        @Override
        public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted) {
            if (SessionType.PCM.equals(type)) {
                serviceCompletionHandler.removeCallbacks(serviceCompletionTimeoutCallback);

                sdlAudioStream = session.startAudioStream();
                streamingStateMachine.transitionToState(StreamingStateMachine.STARTED);

                if (serviceCompletionListener != null) {
                    CompletionListener completionListener = serviceCompletionListener;
                    serviceCompletionListener = null;
                    completionListener.onComplete(true);
                }
            }
        }

        @Override
        public void onServiceEnded(SdlSession session, SessionType type) {
            if (SessionType.PCM.equals(type)) {
                serviceCompletionHandler.removeCallbacks(serviceCompletionTimeoutCallback);

                session.stopAudioStream();
                sdlAudioStream = null;
                streamingStateMachine.transitionToState(StreamingStateMachine.NONE);

                if (serviceCompletionListener != null) {
                    CompletionListener completionListener = serviceCompletionListener;
                    serviceCompletionListener = null;
                    completionListener.onComplete(true);
                }
            }
        }

        @Override
        public void onServiceError(SdlSession session, SessionType type, String reason) {
            if (SessionType.PCM.equals(type)) {
                serviceCompletionHandler.removeCallbacks(serviceCompletionTimeoutCallback);

                streamingStateMachine.transitionToState(StreamingStateMachine.ERROR);
                DebugTool.logError(TAG, "OnServiceError: " + reason);
                streamingStateMachine.transitionToState(StreamingStateMachine.NONE);

                if (serviceCompletionListener != null) {
                    CompletionListener completionListener = serviceCompletionListener;
                    serviceCompletionListener = null;
                    completionListener.onComplete(false);
                }
            }
        }
    };

    private final OnRPCNotificationListener hmiListener = new OnRPCNotificationListener() {
        @Override
        public void onNotified(RPCNotification notification) {
            if(notification != null){
                OnHMIStatus onHMIStatus = (OnHMIStatus)notification;
                if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                    return;
                }
                hmiLevel = onHMIStatus.getHmiLevel();
                if(hmiLevel.equals(HMILevel.HMI_FULL) || hmiLevel.equals(HMILevel.HMI_LIMITED)){
                    checkState();
                }
            }
        }
    };

    /**
     * Creates a new object of AudioStreamManager
     * @param internalInterface The internal interface to the connected device.
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public AudioStreamManager(@NonNull ISdl internalInterface, @NonNull Context context) {
        super(internalInterface);
        this.queue = new LinkedList<>();
        this.context = new WeakReference<>(context);
        this.serviceCompletionHandler = new Handler(Looper.getMainLooper());

        internalInterface.addServiceListener(SessionType.PCM, serviceListener);

        // Listen for HMILevel changes
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

        streamingStateMachine = new StreamingStateMachine();

    }

    @Override
    public void start(CompletionListener listener) {
        isTransportAvailable = internalInterface.isTransportForServiceAvailable(SessionType.PCM);
        getAudioStreamingCapabilities();
        checkState();
        super.start(listener);
    }

    private void checkState(){
        if(audioStreamingCapabilities != null
                && isTransportAvailable
                && hmiLevel != null
                && (hmiLevel.equals(HMILevel.HMI_LIMITED) || hmiLevel.equals(HMILevel.HMI_FULL))){
            transitionToState(READY);
        }
    }

    private void getAudioStreamingCapabilities(){
        internalInterface.getCapability(SystemCapabilityType.PCM_STREAMING, new OnSystemCapabilityListener() {
            @Override
            public void onCapabilityRetrieved(Object capability) {
                if(capability != null && capability instanceof AudioPassThruCapabilities){
                    audioStreamingCapabilities = (AudioPassThruCapabilities) capability;
                    checkState();
                }
            }

            @Override
            public void onError(String info) {
                DebugTool.logError(TAG, "Error retrieving audio streaming capability: " + info);
                streamingStateMachine.transitionToState(StreamingStateMachine.ERROR);
                transitionToState(ERROR);
            }
        });
    }

    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void dispose() {
        stopAudioStream(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                internalInterface.removeServiceListener(SessionType.PCM, serviceListener);
            }
        });

        super.dispose();
    }

    /**
     * Starts the audio service and audio stream to the connected device.
     * The method is non-blocking.
     * @param encrypted Specify whether or not the audio stream should be encrypted.
     */
    public void startAudioStream(boolean encrypted, final CompletionListener completionListener) {
        // audio stream cannot be started without a connected internal interface
        if (!internalInterface.isConnected()) {
            DebugTool.logWarning(TAG, "startAudioStream called without being connected.");
            finish(completionListener, false);
            return;
        }

        // streaming state must be NONE (starting the service is ready. starting stream is started)
        if (streamingStateMachine.getState() != StreamingStateMachine.NONE) {
            DebugTool.logWarning(TAG, "startAudioStream called but streamingStateMachine is not in state NONE (current: " + streamingStateMachine.getState() + ")");
            finish(completionListener, false);
            return;
        }

        AudioPassThruCapabilities capabilities = (AudioPassThruCapabilities) internalInterface.getCapability(SystemCapabilityType.PCM_STREAMING);

        if (capabilities != null) {
            switch (capabilities.getSamplingRate()) {
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
                default:
                    finish(completionListener, false);
                    return;
            }

            switch (capabilities.getBitsPerSample()) {
                case _8_BIT:
                    sdlSampleType = SampleType.UNSIGNED_8_BIT;
                    break;
                case _16_BIT:
                    sdlSampleType = SampleType.SIGNED_16_BIT;
                    break;
                default:
                    finish(completionListener, false);
                    return;

            }
        } else {
            finish(completionListener, false);
            return;
        }

        streamingStateMachine.transitionToState(StreamingStateMachine.READY);
        serviceCompletionListener = completionListener;
        serviceCompletionHandler.postDelayed(serviceCompletionTimeoutCallback, COMPLETION_TIMEOUT);
        internalInterface.startAudioService(encrypted);
    }

    /**
     * Makes the callback to the listener
     * @param listener the listener to notify
     * @param isSuccess flag to notify
     */
    private void finish(CompletionListener listener, boolean isSuccess) {
        if (listener != null) {
            listener.onComplete(isSuccess);
        }
    }

    /**
     * Stops the audio service and audio stream to the connected device.
     * The method is non-blocking.
     */
    public void stopAudioStream(final CompletionListener completionListener) {
        if (!internalInterface.isConnected()) {
            DebugTool.logWarning(TAG, "stopAudioStream called without being connected");
            finish(completionListener, false);
            return;
        }

        // streaming state must be STARTED (starting the service is ready. starting stream is started)
        if (streamingStateMachine.getState() != StreamingStateMachine.STARTED) {
            DebugTool.logWarning(TAG, "stopAudioStream called but streamingStateMachine is not STARTED (current: " + streamingStateMachine.getState() + ")");
            finish(completionListener, false);
            return;
        }

        streamingStateMachine.transitionToState(StreamingStateMachine.STOPPED);
        serviceCompletionListener = completionListener;
        serviceCompletionHandler.postDelayed(serviceCompletionTimeoutCallback, COMPLETION_TIMEOUT);
        internalInterface.stopAudioService();
    }

    /**
     * Pushes the specified resource file to the playback queue.
     * The audio file will be played immediately. If another audio file is currently playing
     * the specified file will stay queued and automatically played when ready.
     * @param resourceId The specified resource file to be played.
     * @param completionListener A completion listener that informs when the audio file is played.
     */
    public void pushResource(int resourceId, final CompletionListener completionListener) {
        Context c = context.get();
        Resources r = c.getResources();
        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(r.getResourcePackageName(resourceId))
                .appendPath(r.getResourceTypeName(resourceId))
                .appendPath(r.getResourceEntryName(resourceId))
                .build();

        this.pushAudioSource(uri, completionListener);
    }

    /**
     * Pushes the specified audio file to the playback queue.
     * The audio file will be played immediately. If another audio file is currently playing
     * the specified file will stay queued and automatically played when ready.
     * @param audioSource The specified audio file to be played.
     * @param completionListener A completion listener that informs when the audio file is played.
     */
    @SuppressWarnings("WeakerAccess")
    public void pushAudioSource(Uri audioSource, final CompletionListener completionListener) {
        // streaming state must be STARTED (starting the service is ready. starting stream is started)
        if (streamingStateMachine.getState() != StreamingStateMachine.STARTED) {
            return;
        }

        BaseAudioDecoder decoder;
        AudioDecoderListener decoderListener = new AudioDecoderListener() {
            @Override
            public void onAudioDataAvailable(SampleBuffer buffer) {
                if (sdlAudioStream != null) {
                    sdlAudioStream.sendAudio(buffer.getByteBuffer(), buffer.getPresentationTimeUs(), null);
                }
            }

            @Override
            public void onDecoderFinish(boolean success) {
                finish(completionListener, true);

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
                DebugTool.logError(TAG, "decoder error", e);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            decoder = new AudioDecoder(audioSource, context.get(), sdlSampleRate, sdlSampleType, decoderListener);
        } else {
            // this BaseAudioDecoder subclass uses methods deprecated with api 21
            decoder = new AudioDecoderCompat(audioSource, context.get(), sdlSampleRate, sdlSampleType, decoderListener);
        }

        synchronized (queue) {
            queue.add(decoder);

            if (queue.size() == 1) {
                decoder.start();
            }
        }
    }

    /**
     * Pushes raw audio data to SDL Core.
     * The audio file will be played immediately. If another audio file is currently playing,
     * the specified file will stay queued and automatically played when ready.
     * @param data Audio raw data to send.
     * @param completionListener A completion listener that informs when the audio file is played.
     */
    public void pushBuffer(ByteBuffer data, CompletionListener completionListener) {
        // streaming state must be STARTED (starting the service is ready. starting stream is started)
        if (streamingStateMachine.getState() != StreamingStateMachine.STARTED) {
            DebugTool.logWarning(TAG, "AudioStreamManager is not ready!");
            return;
        }

        if (sdlAudioStream != null) {
            sdlAudioStream.sendAudio(data, -1, completionListener);
        }
    }

    @Override
    protected void onTransportUpdate(List<TransportRecord> connectedTransports, boolean audioStreamTransportAvail, boolean videoStreamTransportAvail){

        isTransportAvailable = audioStreamTransportAvail;

        if(internalInterface.getProtocolVersion().isNewerThan(new Version(5,1,0)) >= 0){
            if(audioStreamTransportAvail){
                checkState();
            }
        }else{
            //The protocol version doesn't support simultaneous transports.
            if(!audioStreamTransportAvail){
                //If video streaming isn't available on primary transport then it is not possible to
                //use the video streaming manager until a complete register on a transport that
                //supports video
                transitionToState(ERROR);
            }
        }
    }

    @IntDef({SampleType.UNSIGNED_8_BIT, SampleType.SIGNED_16_BIT, SampleType.FLOAT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SampleType {
        // ref https://developer.android.com/reference/android/media/AudioFormat "Encoding" section
        // The audio sample is a 8 bit unsigned integer in the range [0, 255], with a 128 offset for zero.
        // This is typically stored as a Java byte in a byte array or ByteBuffer. Since the Java byte is
        // signed, be careful with math operations and conversions as the most significant bit is inverted.
        //
        // The unsigned byte range is [0, 255] and should be converted to double [-1.0, 1.0]
        // The 8 bits of the byte are easily converted to int by using bitwise operator
        int UNSIGNED_8_BIT = Byte.SIZE >> 3;

        // ref https://developer.android.com/reference/android/media/AudioFormat "Encoding" section
        // The audio sample is a 16 bit signed integer typically stored as a Java short in a short array,
        // but when the short is stored in a ByteBuffer, it is native endian (as compared to the default Java big endian).
        // The short has full range from [-32768, 32767], and is sometimes interpreted as fixed point Q.15 data.
        //
        // the conversion is slightly easier from [-32768, 32767] to [-1.0, 1.0]
        int SIGNED_16_BIT = Short.SIZE >> 3;

        // ref https://developer.android.com/reference/android/media/AudioFormat "Encoding" section
        // Introduced in API Build.VERSION_CODES.LOLLIPOP, this encoding specifies that the audio sample
        // is a 32 bit IEEE single precision float. The sample can be manipulated as a Java float in a
        // float array, though within a ByteBuffer it is stored in native endian byte order. The nominal
        // range of ENCODING_PCM_FLOAT audio data is [-1.0, 1.0].
        int FLOAT = Float.SIZE >> 3;
    }
}
