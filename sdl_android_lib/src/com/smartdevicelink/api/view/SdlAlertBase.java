package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.interfaces.SdlInteractionResponseListener;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by mschwerz on 5/3/16.
 */
abstract class SdlAlertBase {
    private final String TAG = getClass().getSimpleName();

    protected static final int MIN_DURATION = 3000;
    protected static final int DEFAULT_DURATION = 5000;
    protected static final int MAX_DURATION = 10000;

    protected String[] mTextFields= new String[3];
    protected int mDuration;
    protected boolean mIsToneUsed;
    protected boolean mIsIndicatorUsed;
    protected TTSChunk mTtsChunk;
    protected final ArrayList<SdlButton> mButtons= new ArrayList<>();
    protected SdlInteractionSender mSender;
    protected SdlInteractionButtonManager mButtonManager;

    protected SdlAlertBase(Builder builder) {

        this.mTextFields=builder.mTextFields;
        this.mDuration= builder.mDuration;
        this.mIsToneUsed= builder.mIsToneUsed;
        this.mIsIndicatorUsed= builder.mIsIndicatorShown;
        this.mTtsChunk= builder.mTtsChunk;
        this.mButtons.addAll(builder.mButtons);
        mButtonManager= new SdlInteractionButtonManager(mButtons);
    }

    private Alert createAlert(SdlContext context){
        Alert newAlert = new Alert();
        newAlert.setAlertText1(mTextFields[0]);
        newAlert.setAlertText2(mTextFields[1]);
        newAlert.setAlertText3(mTextFields[2]);
        newAlert.setDuration(mDuration);
        newAlert.setProgressIndicator(mIsIndicatorUsed);
        newAlert.setPlayTone(mIsToneUsed);
        if(mTtsChunk!=null)
            newAlert.setTtsChunks(Collections.singletonList(mTtsChunk));
        try {
            newAlert.setSoftButtons(mButtonManager.registerAllButtons(context));
        }catch (SdlInteractionButtonManager.SdlImageNotReadyException exception){
            //notify the user about the image?
        }
        return newAlert;
    }

    protected SdlInteractionSender getSender(){
        if(mSender==null)
            mSender= new SdlInteractionSender(SdlPermission.Alert);
        return mSender;
    }


    /**
     * Method to send the built {@link SdlAlertBase} to the module, while the app is in the foreground. If there is a {@link SdlInteractionResponseListener}
     * set to {@link SdlAlertBase}, then the listener will be informed if the dialog fails, is cancelled or if
     * the interaction is able to be completed normally.
     * @param context The SdlActivity that the SdlCommonAlert will be sent from
     */
    public boolean send(@NonNull SdlContext context, @Nullable SdlInteractionResponseListener listener){
        return getSender().sendInteraction(context.getSdlApplicationContext(),createAlert(context),null,mButtonManager.getCleanUpListener(context.getSdlApplicationContext(),listener));
    }

    abstract static class Builder <T extends Builder> {

        private String[] mTextFields= new String[3];
        private int mDuration = DEFAULT_DURATION;
        private boolean mIsToneUsed;
        private boolean mIsIndicatorShown;
        private ArrayList<SdlButton> mButtons = new ArrayList<>();
        private TTSChunk mTtsChunk;

        public Builder(){

        }

        protected abstract T grabBuilder();

        /**
         * Sets a specific line of text for the built {@link SdlAlertBase}. If you need to leave the line
         * empty, please don't set the field or set it to null.
         * @param textField The string to be shown with the SdlCommonAlert.
         *                   Cannot be an empty string or all whitespace.
         * @return The builder for the {@link SdlAlertBase}
         */
        public T setTextField(String textField, int row){
            mTextFields[row] = textField;
            return grabBuilder();
        }


        /**
         * Convenience method to take in a string and set to the appropriate line items
         * based on the line breaks in the given string
         * @param textFields A string with optional linebreaks
         * @return The builder for the {@link SdlAlertBase}
         */
        public T setText(String textFields){
            String [] lines= textFields.split("\\r?\\n");
            for(int i=0;i<mTextFields.length;i++){
                if(i<lines.length){
                    mTextFields[i]=lines[i];
                }
            }
            return grabBuilder();
        }

        /**
         * Sets the duration that the {@link SdlAlertBase} will show up for.
         * The min value is 3000 and the max value is 10000
         * @param duration The amount of milliseconds the SdlCommonAlert should appear
         * @return The builder for the {@link SdlAlertBase}
         */
        public T setDuration(int duration){
            if(duration < MIN_DURATION) {
                mDuration = MIN_DURATION;
            } else if(duration < MAX_DURATION){
                mDuration = duration;
            } else {
                mDuration = MAX_DURATION;
            }
            return grabBuilder();
        }

        /**
         * Sets if a tone should sound when the {@link SdlAlertBase} appears.
         * @param isToneUsed Set to true if a tone should sound when the SdlCommonAlert appears.
         * @return The builder for the {@link SdlAlertBase}
         */
        public T setToneUsed(boolean isToneUsed){
            mIsToneUsed = isToneUsed;
            return grabBuilder();
        }

        /**
         *
         * @param isIndicatorShown
         * @return The builder for the {@link SdlAlertBase}
         */
        public T setProgressIndicatorShown(boolean isIndicatorShown){
            mIsIndicatorShown = isIndicatorShown;
            return grabBuilder();
        }

        /**
         * Sets the push buttons that the user can touch when the {@link SdlAlertBase}
         * appears. The buttons provided must contain text to be set, even if the button only needs
         * to provide an image. In case the image is not available at the time of the showing of
         * the dialog on the module, the text will be used instead.
         * @param buttons Collection of SdlButtons that describe what the buttons should look like
         * @return The builder for the {@link SdlAlertBase}
         */
        public T setButtons(@NonNull Collection<SdlButton> buttons){
            this.mButtons.clear();
            this.mButtons.addAll(buttons);
            return grabBuilder();
        }

        public T addButton(@NonNull SdlButton button){
            this.mButtons.add(button);
            return grabBuilder();
        }


        /**
         * Sets the TTS to be spoken when the {@link SdlAlertBase} appears.
         * @param ttsChunk The description of the Text To Speech to be read aloud
         * @return The builder for the {@link SdlAlertBase}
         */
        public T setSpeak(TTSChunk ttsChunk){
            mTtsChunk = ttsChunk;
            return grabBuilder();
        }

        /**
         * Convenience method for Text to Speech with the {@link SdlAlertBase} which
         * will speak the text provided aloud.
         * @param textToSpeak Text to be spoken aloud
         * @return The builder for the {@link SdlAlertBase}
         */
        public T setSpeak(String textToSpeak){
            TTSChunk newChunk= new TTSChunk();
            newChunk.setText(textToSpeak);
            newChunk.setType(SpeechCapabilities.TEXT);
            mTtsChunk= newChunk;
            return grabBuilder();
        }




        /**
         * Creates the {@link SdlAlertBase} object that will display a Dialog with text, sounds and buttons
         * as set in the builder. Use {@link SdlPushNotification} if you want to send
         * the dialog while the app is not visible on the module and have permission to do so.
         * @return SdlCommonAlert, call  in order to send the
         * built {@link SdlAlertBase}
         * @throws IllegalAlertCreation Exception will be called if the parameters set when building
         * are illegal
         */
        public abstract <T extends SdlAlertBase> T  build() throws IllegalAlertCreation;


        protected void validateCommonAlert(SdlAlertBase builtAlert) throws IllegalAlertCreation{
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

        }


    }
    public static class IllegalAlertCreation extends Exception{
        IllegalAlertCreation(String detailMessage){super(detailMessage);}
    }

    private static boolean checkStringIsValid(String checkString){
        return checkString.trim().length() > 0;
    }

}
