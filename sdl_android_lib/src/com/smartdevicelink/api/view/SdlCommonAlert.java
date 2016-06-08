package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;

import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

import java.util.Collections;
import java.util.List;

/**
 * Created by mschwerz on 5/3/16.
 */
abstract class SdlCommonAlert extends SdlCommonInteraction{
    private final String TAG = getClass().getSimpleName();

    protected boolean mIsToneUsed;
    protected boolean mIsIndicatorUsed;
    protected TTSChunk mTtsChunk;

    protected SdlCommonAlert(Builder builder) {
        super(builder);
        mIsToneUsed= builder.mIsToneUsed;
        mIsIndicatorUsed= builder.mIsIndicatorShown;
        mTtsChunk= builder.mTtsChunk;
    }

    @Override
    protected @NonNull SdlPermission getRequiredPermission() {
        return SdlPermission.Alert;
    }

    @Override
    protected @NonNull IRPCRequestWithButtons getSettableButtonRPCMessage() {
        final Alert newAlert = new Alert();
        newAlert.setDuration(mDuration);
        newAlert.setAlertText1(mTextFields[0]);
        newAlert.setAlertText2(mTextFields[1]);
        newAlert.setAlertText3(mTextFields[2]);
        newAlert.setProgressIndicator(mIsIndicatorUsed);
        newAlert.setPlayTone(mIsToneUsed);
        if(mTtsChunk!=null)
            newAlert.setTtsChunks(Collections.singletonList(mTtsChunk));
        return new IRPCRequestWithButtons() {
            @Override
            public void setButtons(List<SoftButton> buttons) {
                newAlert.setSoftButtons(buttons);
            }

            @Override
            public RPCRequest createRequest() {
                return newAlert;
            }
        };
    }

    protected abstract static class Builder <T extends Builder<T>> extends SdlCommonInteraction.Builder<T> {

        private boolean mIsToneUsed;
        private boolean mIsIndicatorShown;
        private TTSChunk mTtsChunk;

        public Builder(){

        }

        @Override
        protected int getMaxDuration() {
            return 10000;
        }

        @Override
        protected int getMinDuration() {
            return 3000;
        }

        @Override
        protected int getDefaultDuration() {
            return 5000;
        }

        protected abstract T grabBuilder();

        /**
         * Sets a specific line of text for the built {@link SdlCommonAlert}. If you need to leave the line
         * empty, please don't set the field or set it to null.
         * @param textField The string to be shown with the SdlCommonAlert.
         *                   Cannot be an empty string or all whitespace.
         * @return The builder for the {@link SdlCommonAlert}
         */
        public T setTextField(String textField, int row){
            mTextFields[row] = textField;
            return grabBuilder();
        }

        /**
         * Convenience method to take in a string and set to the appropriate line items
         * based on the line breaks in the given string
         * @param text A string with optional linebreaks
         * @return The builder for the {@link SdlCommonAlert}
         */
        public T setText(String text){
            String [] lines= text.split("\\r?\\n");
            for(int i=0;i<mTextFields.length;i++){
                if(i<lines.length){
                    mTextFields[i]=lines[i];
                }
            }
            return grabBuilder();
        }

        /**
         * Sets if a tone should sound when the {@link SdlCommonAlert} appears.
         * @param isToneUsed Set to true if a tone should sound when the SdlCommonAlert appears.
         * @return The builder for the {@link SdlCommonAlert}
         */
        public T setToneUsed(boolean isToneUsed){
            mIsToneUsed = isToneUsed;
            return grabBuilder();
        }

        /**
         *
         * @param isIndicatorShown
         * @return The builder for the {@link SdlCommonAlert}
         */
        public T setProgressIndicatorShown(boolean isIndicatorShown){
            mIsIndicatorShown = isIndicatorShown;
            return grabBuilder();
        }



        /**
         * Sets the TTS to be spoken when the {@link SdlCommonAlert} appears.
         * @param ttsChunk The description of the Text To Speech to be read aloud
         * @return The builder for the {@link SdlCommonAlert}
         */
        public T setSpeak(TTSChunk ttsChunk){
            mTtsChunk = ttsChunk;
            return grabBuilder();
        }

        /**
         * Convenience method for Text to Speech with the {@link SdlCommonAlert} which
         * will speak the text provided aloud.
         * @param textToSpeak Text to be spoken aloud
         * @return The builder for the {@link SdlCommonAlert}
         */
        public T setSpeak(String textToSpeak){
            TTSChunk newChunk= new TTSChunk();
            newChunk.setText(textToSpeak);
            newChunk.setType(SpeechCapabilities.TEXT);
            mTtsChunk= newChunk;
            return grabBuilder();
        }



        protected void validateCommonAlert(SdlCommonAlert builtAlert) throws IllegalAlertCreation{
            if(builtAlert.mButtons !=null){
                if(builtAlert.mButtons.size()>4){
                    throw new IllegalAlertCreation("More buttons were added then possible for the AlertDialog");
                }
                for(SdlButton button:mButtons){
                    if(button==null){
                        throw new IllegalAlertCreation("One of the buttons provided is null, make sure the SdlButtons added are instantiated");
                    }
                }
            }
            boolean atLeastOneNotNull=false;
            for(int i=0;i<builtAlert.mTextFields.length;i++){
                if(builtAlert.mTextFields[i]!=null){
                    atLeastOneNotNull=true;
                    if(!checkStringIsValid(mTextFields[i]))
                        throw new IllegalAlertCreation("Invalid String was provided to TextField"+Integer.toString(i+1));
                }
            }
            if(!atLeastOneNotNull)
                throw new IllegalAlertCreation("All of the TextFields are null, please make sure at least one is set");

            if( builtAlert.mTtsChunk !=null){

                //validate tts?
            }
        }


    }
    public static class IllegalAlertCreation extends IllegalCreation{
        IllegalAlertCreation(String detailMessage){super(detailMessage);}
    }

    private static boolean checkStringIsValid(String checkString){
        return checkString.trim().length() > 0;
    }


}
