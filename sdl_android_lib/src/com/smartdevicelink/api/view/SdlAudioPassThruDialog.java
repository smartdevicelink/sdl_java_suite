package com.smartdevicelink.api.view;

import android.support.annotation.Nullable;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.interfaces.SdlInteractionResponseListener;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.EndAudioPassThru;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThru;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

import java.util.Collections;

/**
 * Created by mschwerz on 6/3/16.
 */
public class SdlAudioPassThruDialog {

    protected static final int MIN_DURATION = 1;
    //TODO: No default duration was given in the spec
    protected static final int DEFAULT_DURATION = 5000;
    protected static final int MAX_DURATION = 1000000;

    final TTSChunk mInitialPrompt;
    final String[] mDisplayLines;
    final int mDuration;
    final boolean mAudioMuted;
    final SamplingRate mSamplingRate;
    final BitsPerSample mBitsPerSample;
    final AudioType mAudioType;
    final ReceiveDataListener mDataListener;
    final SdlInteractionSender mAPTSender= new SdlInteractionSender(SdlPermission.PerformAudioPassThru);
    final SdlEndAPTInteractionSender mEndAPTSender= new SdlEndAPTInteractionSender(SdlPermission.EndAudioPassThru);

    SdlAudioPassThruDialog(Builder builder){
        this.mInitialPrompt=builder.mInitialPrompt;
        this.mDisplayLines= builder.mDisplayLines;
        this.mDuration= builder.mDuration;
        this.mAudioMuted= builder.mAudioMuted;
        this.mSamplingRate= builder.mSamplingRate;
        this.mBitsPerSample= builder.mBitsPerSample;
        this.mAudioType= builder.mAudioType;
        this.mDataListener= builder.mDataListener;
    }

    public boolean send(SdlContext context, @Nullable SdlInteractionResponseListener listener){

        final SdlContext applicationContext= context.getSdlApplicationContext();
        boolean result= mAPTSender.sendInteraction(context.getSdlApplicationContext(), createAudioPassThru(), new SdlInteractionSender.SdlDataReceiver() {
            @Override
            public void handleRPCResponse(RPCResponse response) {
                applicationContext.unregisterAudioPassThruListener(mDataListener);
            }
        }, listener);
        if(result)
            applicationContext.registerAudioPassThruListener(mDataListener);
        return result;

    }

    public boolean stopAudioPassThru(SdlContext context, @Nullable SdlInteractionResponseListener listener){
        return mEndAPTSender.sendInteraction(context.getSdlApplicationContext() , new EndAudioPassThru(), null, listener);
    }

    private PerformAudioPassThru createAudioPassThru(){
        PerformAudioPassThru passThru= new PerformAudioPassThru();
        passThru.setAudioType(mAudioType);
        passThru.setAudioPassThruDisplayText1(mDisplayLines[0]);
        passThru.setAudioPassThruDisplayText2(mDisplayLines[1]);
        passThru.setBitsPerSample(mBitsPerSample);
        passThru.setSamplingRate(mSamplingRate);
        if(mInitialPrompt!=null)
            passThru.setInitialPrompt(Collections.singletonList(mInitialPrompt));
        passThru.setMaxDuration(mDuration);
        passThru.setMuteAudio(mAudioMuted);
        return passThru;
    }


    public static class Builder{
        TTSChunk mInitialPrompt;
        String[] mDisplayLines= new String[2];
        int mDuration= DEFAULT_DURATION;
        boolean mAudioMuted=true;
        SamplingRate mSamplingRate;
        BitsPerSample mBitsPerSample;
        AudioType mAudioType= AudioType.PCM;
        ReceiveDataListener mDataListener;

        public Builder(SamplingRate samplingRate, BitsPerSample bitsPerSample, AudioType audioType){
            mSamplingRate= samplingRate;
            mBitsPerSample= bitsPerSample;
            mAudioType= audioType;
        }

        public Builder setSpeak(TTSChunk chunk){
            mInitialPrompt= chunk;
            return this;
        }

        public Builder setSpeak(String chunk){
            mInitialPrompt= new TTSChunk();
            mInitialPrompt.setText(chunk);
            mInitialPrompt.setType(SpeechCapabilities.TEXT);
            return this;
        }

        public Builder setDisplayLine1(String line1){
            mDisplayLines[0]=line1;
            return this;
        }

        public Builder setDisplayLine2(String line2){
            mDisplayLines[1]=line2;
            return this;
        }

        public Builder setDisplayLines(String[] lines){
            mDisplayLines=lines;
            return this;
        }

        public Builder setDuration(int duration){
            if(duration < MIN_DURATION) {
                mDuration = MIN_DURATION;
            } else if(duration < MAX_DURATION){
                mDuration = duration;
            } else {
                mDuration = MAX_DURATION;
            }
            return this;
        }

        public Builder setMuteAudio(boolean willAudioBeMuted){
            mAudioMuted= willAudioBeMuted;
            return this;
        }

        public Builder setSamplingRate(SamplingRate rate){
            mSamplingRate=rate;
            return this;
        }

        public Builder setBitsPerSample(BitsPerSample bitsPerSample){
            mBitsPerSample= bitsPerSample;
            return this;
        }

        public Builder setAudioType(AudioType type){
            mAudioType= type;
            return this;
        }

        public Builder setDataListener(ReceiveDataListener listener){
            mDataListener= listener;
            return this;
        }

        public SdlAudioPassThruDialog build(){
            return new SdlAudioPassThruDialog(this);
        }


    }

    private class SdlEndAPTInteractionSender extends SdlInteractionSender{

        public SdlEndAPTInteractionSender(SdlPermission permission) {
            super(permission);
        }

        @Override
        protected boolean isAbleToSendInteraction(SdlPermission permission, SdlContext context) {
            return super.isAbleToSendInteraction(permission, context) && mAPTSender.mIsPending;
        }
    }

    public interface ReceiveDataListener{
        void receiveData(byte[] data);

    }

}
