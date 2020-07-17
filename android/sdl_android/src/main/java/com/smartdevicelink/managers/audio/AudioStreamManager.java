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
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.media.MediaCodec;
import android.os.Message;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.StreamingStateMachine;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The AudioStreamManager class provides methods to start and stop an audio stream
 * to the connected device. Audio files can be pushed to the manager in order to
 * play them on the connected device. The manager uses the Android built-in MediaCodec.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class AudioStreamManager extends BaseAudioStreamManager {
    private static final String TAG = AudioStreamManager.class.getSimpleName();
    private static final int COMPLETION_TIMEOUT = 2000;

    private IAudioStreamListener sdlAudioStream;
    private int sdlSampleRate;
    private @SampleType int sdlSampleType;
    //Decoder queue
    private final Queue<Decoder> queue;
    //Audio streaming send thread
    private SendAudioStreamThread mSendAudioStreamThread;
    //Audio buffer list
    private ArrayList<SendAudioBuffer> mAudioBufferList = null;
    //Data transmission end time
    private long mEndTimeOfSendData = 0;
    //Lock data transmission thread
    private final Object LOCK_SENDTHREAD = new Object();
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
    public AudioStreamManager(@NonNull ISdl internalInterface, @NonNull Context context) {
        super(internalInterface);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
            this.queue = null;
            this.context = null;
            this.serviceCompletionHandler = null;
            this.streamingStateMachine = null;
            transitionToState(ERROR);
            return;
        }
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
                //Added to get default audio passthrough function
                audioStreamingCapabilities = getDefaultAudioPassThruCapabilities();
                checkState();

            }
        });
    }

    @Override
    public void dispose() {
        mEndTimeOfSendData = 0;
        cleanAudioStreamThread();
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

        if(capabilities == null){
            capabilities = getDefaultAudioPassThruCapabilities();
        }
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
        synchronized (LOCK_SENDTHREAD) {
            if (mSendAudioStreamThread != null) {
                mSendAudioStreamThread.stopAs();
                try {
                    mSendAudioStreamThread.join();
                } catch (InterruptedException e) {
                }
                mSendAudioStreamThread = null;
            }
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
    public void pushResource(int resourceId, final CompletionListener completionListener,boolean interrupt) {
        Context c = context.get();
        Resources r = c.getResources();
        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(r.getResourcePackageName(resourceId))
                .appendPath(r.getResourceTypeName(resourceId))
                .appendPath(r.getResourceEntryName(resourceId))
                .build();
        this.pushAudioSource(uri, completionListener,interrupt);
    }

    /**
     * Pushes the specified audio file to the playback queue.
     * The audio file will be played immediately. If another audio file is currently playing
     * the specified file will stay queued and automatically played when ready.
     * @param audioSource The specified audio file to be played.
     * @param completionListener A completion listener that informs when the audio file is played.
     */
    @SuppressWarnings("WeakerAccess")
    public void pushAudioSource(Uri audioSource, final CompletionListener completionListener,boolean interrupt) {
        // streaming state must be STARTED (starting the service is ready. starting stream is started)
        if (streamingStateMachine.getState() != StreamingStateMachine.STARTED) {
            return;
        }

        BaseAudioDecoder decoder;
        AudioDecoderListener decoderListener = new AudioDecoderListener() {
            @Override
            public void onAudioDataAvailable(ArrayList<SampleBuffer> sampleBufferList,int flags) {
                if(mSendAudioStreamThread != null){
                    ArrayList<SendAudioBuffer> sendBufferList = new ArrayList<>();
                    if(sampleBufferList == null){
                        sendBufferList.add(
                                new SendAudioBuffer(null, flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM? SendAudioBuffer.DECODER_FINISH_SUCCESS: SendAudioBuffer.DECODER_NOT_FINISH)
                        );
                    } else {
                        for(int i = 0 ; i < sampleBufferList.size() ;i++){
                            SampleBuffer buffer = sampleBufferList.get(i);
                            int iFlag = SendAudioBuffer.DECODER_NOT_FINISH;
                            if(flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM &&  sampleBufferList.size() == i+1){
                                iFlag = SendAudioBuffer.DECODER_FINISH_SUCCESS;
                            }
                            sendBufferList.add(
                                    new SendAudioBuffer(buffer, iFlag)
                            );
                        }
                    }
                    mSendAudioStreamThread.addAudioData(sendBufferList);
                }
            }

            @Override
            public void onDecoderFinish(boolean success) {

                // if the queue contains more items then start the first one (without removing it)
                if(mSendAudioStreamThread != null && !success){
                    ArrayList<SendAudioBuffer> sendBufferList = new ArrayList<>();
                    sendBufferList.add(
                            new SendAudioBuffer(null, SendAudioBuffer.DECODER_FINISH_FAILED)
                    );
                    mSendAudioStreamThread.addAudioData(sendBufferList);
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
        startAudioStreamThread(new Decoder(decoder,completionListener,interrupt));
    }

    private void startAudioStreamThread(final Decoder _decoder){
        DebugTool.logInfo(TAG, "startAudioStreamThread() queue.size():" + queue.size());
        if (streamingStateMachine.getState() != StreamingStateMachine.STARTED || sdlAudioStream == null) {
            cleanAudioStreamThread();
            return;
        }
        if(_decoder != null){
            if(_decoder.isInterrupt()){
                //Stop AS data
                DebugTool.logInfo(TAG, "Audio playback interrupted");
                finish(null,true);
                synchronized (queue) {
                    while (queue.size() > 0){
                        queue.element().getAudioDecoder().stop();
                        queue.remove();
                    }
                }
            }
        }
        synchronized (LOCK_SENDTHREAD) {
            if(mSendAudioStreamThread == null){
                mSendAudioStreamThread = new SendAudioStreamThread(new Runnable() {

                    @Override public void run() {
                        synchronized (queue) {
                            if(queue.size() > 0){
                                queue.element().getAudioDecoder().start();
                            }
                        }
                    }
                });
                synchronized (queue) {
                    if(_decoder != null){
                        queue.add(_decoder);
                    }
                }
                mSendAudioStreamThread.start();
            } else {
                synchronized (queue) {
                    if(_decoder != null){
                        queue.add(_decoder);
                    }
                }
            }
        }
    }


    private long getDelayStartAudioTime(){
        long nowTime = System.currentTimeMillis();
        long lDelay = mEndTimeOfSendData - nowTime;
        if(lDelay < 0){
            lDelay = 0;
        } else {
            if(lDelay < 1500){
                lDelay = 0;
            } else {
                lDelay -= 1500;
            }
        }
        return lDelay;
    }


    private void cleanAudioStreamThread(){
        if (mSendAudioStreamThread != null) {
            mSendAudioStreamThread.stopAs();
            try {
                mSendAudioStreamThread.join();
            } catch (InterruptedException e) {
            }
            mSendAudioStreamThread = null;
        }
        synchronized (queue) {
            while (queue.size() > 0){
                queue.element().getAudioDecoder().stop();
                queue.remove();
            }
        }
    }
    private class Decoder {
        private BaseAudioDecoder mAudioDecoder;
        private CompletionListener mCompletionListener;
        private boolean mInterrupt;
        public Decoder(BaseAudioDecoder decoder,CompletionListener listener,boolean interrupt){
            mAudioDecoder = decoder;
            mCompletionListener = listener;
            mInterrupt = interrupt;
        }
        public BaseAudioDecoder getAudioDecoder(){
            return mAudioDecoder;
        }

        public CompletionListener getCompletionListener(){
            return mCompletionListener;
        }

        public boolean isInterrupt(){
            return mInterrupt;
        }
    }
    private class SendAudioBuffer {
        private final static int DECODER_NOT_FINISH = 0;
        private final static int DECODER_FINISH_SUCCESS = 1;
        private final static int DECODER_FINISH_FAILED = 2;
        private ByteBuffer mByteBuffer;
        private long mPresentationTimeUs;
        private int mFinishFlag;

        public SendAudioBuffer(SampleBuffer _buff,int flag){

            mFinishFlag = flag;
            if(_buff != null){
                mPresentationTimeUs = _buff.getPresentationTimeUs();
                ByteBuffer buff = _buff.getByteBuffer();
                mByteBuffer = ByteBuffer.allocate(buff.remaining());
                mByteBuffer.put(buff);
                mByteBuffer.flip();
            }

        }

        public long getPresentationTimeUs(){
            return mPresentationTimeUs;
        }

        public ByteBuffer getByteBuffer(){
            return mByteBuffer;
        }

        public int getFinishFlag(){
            return mFinishFlag;
        }
    }
    private final class SendAudioStreamThread extends Thread{
        private static final int MSG_TICK = 1;
        private static final int MSG_ADD = 2;
        private static final int MSG_TERMINATE = -1;
        private boolean isFirst = true;
        private Handler mHandler;
        private Runnable mStartedCallback;
        private boolean mIsStopRequest = false;

        public SendAudioStreamThread(Runnable onStarted) {
            mStartedCallback = onStarted;
        }
        @Override
        public void run() {
            Looper.prepare();
            if(mHandler == null){
                mHandler = new Handler() {
                    long startTime = 0;
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case MSG_TICK: {
                                long delay = 0;
                                if(mAudioBufferList != null && mAudioBufferList.size() > 0){
                                    while (true){
                                        if (streamingStateMachine.getState() != StreamingStateMachine.STARTED || sdlAudioStream == null) {
                                            DebugTool.logError(TAG, "Streaming Status error:" + streamingStateMachine.getState() );
                                            Handler handler = new Handler(Looper.getMainLooper());
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    cleanAudioStreamThread();
                                                }
                                            });
                                            break;
                                        }

                                        if(mAudioBufferList.size() > 0){
                                            SendAudioBuffer sBuffer = mAudioBufferList.get(0);
                                            if(sBuffer.getByteBuffer() != null){
                                                long nowTime = System.currentTimeMillis();
                                                long AllowableTime = (nowTime - startTime + 1000) * 1000;
                                                if( AllowableTime  >  sBuffer.getPresentationTimeUs()){
                                                    long lSendDataTime = (Long.valueOf(sBuffer.getByteBuffer().limit()) * 1000 )/ (sdlSampleRate * 2);
                                                    mEndTimeOfSendData = startTime + ( sBuffer.getPresentationTimeUs()/1000) + lSendDataTime;
                                                    sdlAudioStream.sendAudio(sBuffer.getByteBuffer(), sBuffer.getPresentationTimeUs(), null);
                                                } else {
                                                    //Delay data transmission
                                                    DebugTool.logInfo(TAG, "Delay the call to sendAudio");
                                                    delay = 500;
                                                    break;
                                                }
                                            }
                                            mAudioBufferList.remove(0);

                                            if(sBuffer.getFinishFlag() != SendAudioBuffer.DECODER_NOT_FINISH) {
                                                final boolean isSuccess = sBuffer.getFinishFlag() == SendAudioBuffer.DECODER_FINISH_SUCCESS;

                                                Handler handler = new Handler(Looper.getMainLooper());
                                                long lDelay = getDelayStartAudioTime();
                                                DebugTool.logInfo(TAG, "Playback end notification. lDelay:" + lDelay);
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        synchronized (queue) {
                                                            if (queue.size() > 0) {
                                                                finish(queue.poll().getCompletionListener(),isSuccess);
                                                            } else {
                                                                DebugTool.logError(TAG, "There is no element of the queue");
                                                            }
                                                            if (queue.size() > 0) {
                                                                startAudioStreamThread(null);
                                                            }
                                                        }
                                                    }
                                                },lDelay);
                                                return;
                                            }
                                        } else {
                                            break;
                                        }
                                    }

                                }
                                sendMessageDelayed(mHandler.obtainMessage(MSG_TICK), delay);
                                break;
                            }
                            case MSG_ADD: {
                                if(mAudioBufferList == null){
                                    mAudioBufferList =  new ArrayList<>();
                                    startTime = System.currentTimeMillis();
                                    if(startTime < mEndTimeOfSendData){
                                        DebugTool.logInfo(TAG, "The playback end time exceeds the current time. EndTime:" + mEndTimeOfSendData);
                                        startTime = mEndTimeOfSendData;
                                    }
                                }
                                ArrayList<SendAudioBuffer> sendBufferList = (ArrayList<SendAudioBuffer>)msg.obj;
                                for(SendAudioBuffer buff: sendBufferList){
                                    mAudioBufferList.add(buff);
                                }
                                break;
                            }
                            case MSG_TERMINATE: {
                                removeCallbacksAndMessages(null);
                                if(mAudioBufferList != null){
                                    mAudioBufferList.clear();
                                    mAudioBufferList = null;
                                }
                                mHandler = null;
                                mIsStopRequest = false;
                                Looper looper = Looper.myLooper();
                                if (looper != null) {
                                    looper.quit();
                                }
                                break;
                            }
                            default:
                                break;
                        }
                    }
                };
            }
            if(mIsStopRequest){
                DebugTool.logInfo(TAG, "StopRequest is valid");
                return;
            }
            if (mStartedCallback != null) {
                mStartedCallback.run();
            }
            DebugTool.logInfo(TAG, "Starting SendAudioStreamThread");
            Looper.loop();
            DebugTool.logInfo(TAG, "Stopping SendAudioStreamThread");
        }

        public void addAudioData(final  ArrayList<SendAudioBuffer> sendBufferList){
            if (mHandler != null && sendBufferList != null && sendBufferList.size() > 0) {
                Message msg = Message.obtain();
                msg.what = MSG_ADD;
                msg.obj = sendBufferList;
                mHandler.sendMessage(msg);
                if(isFirst){
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_TICK));
                    isFirst = false;
                }
            } else {
                DebugTool.logInfo(TAG, "addAudioData mHandler is null");
            }
        }

        public void stopAs(){
            if (mHandler != null) {
                mHandler.sendMessage(mHandler.obtainMessage(MSG_TERMINATE));
            } else {
                mIsStopRequest = true;
                DebugTool.logInfo(TAG, "The thread has not started yet");
            }
        }
    }
    private AudioPassThruCapabilities getDefaultAudioPassThruCapabilities(){
        AudioPassThruCapabilities aptCapabilities = new AudioPassThruCapabilities();
        aptCapabilities.setAudioType(AudioType.PCM);
        aptCapabilities.setBitsPerSample(BitsPerSample._16_BIT);
        aptCapabilities.setSamplingRate(SamplingRate._16KHZ);
        return aptCapabilities;
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
