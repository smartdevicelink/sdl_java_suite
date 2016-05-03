package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by mschwerz on 5/3/16.
 */
public abstract class SdlCommonAlert {
    private final String TAG = getClass().getSimpleName();

    protected static final int MIN_DURATION = 3000;
    protected static final int DEFAULT_DURATION = 5000;
    protected static final int MAX_DURATION = 10000;

    protected String[] mTextFields= new String[3];
    protected int mDuration;
    protected boolean mIsToneUsed;
    protected boolean mIsIndicatorUsed;
    protected TTSChunk mTtsChunk;
    protected final Collection<SdlButton> mButtons;
    protected boolean mIsPending= false;
    protected boolean mIsButtonPressed= false;
    protected InteractionListener mListener;
    protected final Alert newAlert;


    protected SdlCommonAlert(Builder builder) {

        this.mTextFields=builder.mTextFields;
        this.mDuration= builder.mDuration;
        this.mIsToneUsed= builder.mIsToneUsed;
        this.mIsIndicatorUsed= builder.mIsIndicatorShown;
        this.mTtsChunk= builder.mTtsChunk;
        this.mButtons= builder.mButtons;

        newAlert = new Alert();
        newAlert.setAlertText1(mTextFields[0]);
        newAlert.setAlertText2(mTextFields[1]);
        newAlert.setAlertText3(mTextFields[2]);
        newAlert.setDuration(mDuration);
        newAlert.setProgressIndicator(mIsIndicatorUsed);
        newAlert.setPlayTone(mIsToneUsed);
        if(mTtsChunk!=null)
            newAlert.setTtsChunks(Collections.singletonList(mTtsChunk));
        mListener = builder.mListener;
    }


    /**
     * Method to send the built {@link SdlCommonAlert} to the module, while the app is in the foreground. If there is a {@link SdlCommonAlert.InteractionListener}
     * set to {@link SdlCommonAlert}, then the listener will be informed if the dialog fails, is cancelled or if
     * the interaction is able to be completed normally.
     * @param context The SdlActivity that the SdlCommonAlert will be sent from
     */
    public abstract boolean send(@NonNull SdlContext context);

    protected void handleResultResponse(Result response, String info, SdlContext context) {
        InteractionListener cleanUpListener= getCleanUpListener(context);
        switch (response) {
            case SUCCESS:
                if(!mIsButtonPressed){
                    cleanUpListener.onTimeout();
                }
                break;
            case ABORTED:
                cleanUpListener.onAborted();
                break;
            case INVALID_DATA:
                cleanUpListener.onError(InteractionListener.ErrorResponses.MALFORMED_INTERACTION, info);
                break;
            case DISALLOWED:
                cleanUpListener.onError(InteractionListener.ErrorResponses.PERMISSIONS_ERROR, info);
                break;
            case REJECTED:
                cleanUpListener.onError(InteractionListener.ErrorResponses.REJECTED, info);
                break;
            default:
                cleanUpListener.onError(InteractionListener.ErrorResponses.GENERIC_ERROR, info);
                break;
        }
        mIsPending = false;
    }

    protected InteractionListener getCleanUpListener(final SdlContext context){
        return new InteractionListener() {
            @Override
            public void onTimeout() {
                unregisterAllButtons(mButtons, context);
                if(mListener!=null)
                    mListener.onTimeout();
            }

            @Override
            public void onAborted() {
                unregisterAllButtons(mButtons, context);
                if(mListener!=null)
                    mListener.onAborted();
            }

            @Override
            public void onError(ErrorResponses responses, String moreInfo) {
                unregisterAllButtons(mButtons, context);
                if(mListener!=null)
                    mListener.onError(responses,moreInfo);
            }
        };
    }

    protected boolean registerAllButtons(Alert alertToHaveButtons, final SdlContext context){
        ArrayList<SoftButton> createdSoftbuttons = new ArrayList<>();
        if (this.mButtons == null) {
            //should be ok since the user didn't provide buttons for this alert
            return true;
        }
        for (final SdlButton button : this.mButtons) {
            if (button.getListener() != null) {
                SoftButton softButtonFromSdlButton = new SoftButton();
                if(button.getText()!=null){
                    softButtonFromSdlButton.setText(button.getText());
                    softButtonFromSdlButton.setType(SoftButtonType.SBT_TEXT);
                }
                softButtonFromSdlButton.setIsHighlighted(false);
                softButtonFromSdlButton.setSystemAction(SystemAction.DEFAULT_ACTION);
                if(button.getSdlImage()!=null){
                    if(context.getSdlFileManager().isFileOnModule(button.getSdlImage().getSdlName())){
                        Image image = new Image();
                        image.setValue(button.getSdlImage().getSdlName());
                        image.setImageType(ImageType.DYNAMIC);
                        softButtonFromSdlButton.setImage(image);
                        softButtonFromSdlButton.setType(SoftButtonType.SBT_IMAGE);
                    }else{
                        //The image with the SdlButton was not ready, therefore fail until
                        //the user provides an uploaded image
                        return false;
                    }
                }
                int buttonID = context.registerButtonCallback(new SdlButton.OnPressListener() {
                    @Override
                    public void onButtonPress() {
                        unregisterAllButtons(mButtons,context);
                        mIsButtonPressed=true;
                        button.getListener().onButtonPress();
                    }
                });
                button.setId(buttonID);
                softButtonFromSdlButton.setSoftButtonID(buttonID);
                createdSoftbuttons.add(softButtonFromSdlButton);
            }
        }
        alertToHaveButtons.setSoftButtons(createdSoftbuttons);
        return true;
    }

    protected void unregisterAllButtons(Collection<SdlButton> ids, SdlContext context){
        if (ids == null || context == null) {
            return;
        }
        for (SdlButton button : ids) {
            context.unregisterButtonCallback(button.getId());
        }
    }


    abstract static class Builder <T extends Builder> {

        private String[] mTextFields= new String[3];
        private int mDuration = DEFAULT_DURATION;
        private boolean mIsToneUsed;
        private boolean mIsIndicatorShown;
        private Collection<SdlButton> mButtons;
        private TTSChunk mTtsChunk;
        private InteractionListener mListener;

        public Builder(){

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
         * @param textFields A string with optional linebreaks
         * @return The builder for the {@link SdlCommonAlert}
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
         * Sets the duration that the {@link SdlCommonAlert} will show up for.
         * The min value is 3000 and the max value is 10000
         * @param duration The amount of milliseconds the SdlCommonAlert should appear
         * @return The builder for the {@link SdlCommonAlert}
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
         * Sets the push buttons that the user can touch when the {@link SdlCommonAlert}
         * appears. The buttons provided must contain text to be set, even if the button only needs
         * to provide an image. In case the image is not available at the time of the showing of
         * the dialog on the module, the text will be used instead.
         * @param buttons Collection of SdlButtons that describe what the buttons should look like
         * @return The builder for the {@link SdlCommonAlert}
         */
        public T setButtons(Collection<SdlButton> buttons){
            this.mButtons = buttons;
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

        /**
         * Sets the listener for when the {@link SdlCommonAlert} finishes with the interaction,
         * is interrupted by another interaction, or an error occurred.
         * @param listener The object to listen for the {@link SdlCommonAlert} callbacks.
         * @return The builder for the {@link SdlCommonAlert}
         */
        public T setListener(InteractionListener listener){
            this.mListener = listener;
            return grabBuilder();
        }


        /**
         * Creates the {@link SdlCommonAlert} object that will display a Dialog with text, sounds and buttons
         * as set in the builder. Use {@link SdlPushNotification} if you want to send
         * the dialog while the app is not visible on the module and have permission to do so.
         * @return SdlCommonAlert, call  in order to send the
         * built {@link SdlCommonAlert}
         * @throws IllegalAlertCreation Exception will be called if the parameters set when building
         * are illegal
         */
        public abstract <T extends SdlCommonAlert> T  build() throws IllegalAlertCreation;


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

            List<TTSChunk> chunks= builtAlert.newAlert.getTtsChunks();
            if(chunks !=null){
                if(chunks.get(0)!=null){

                }
                //validate tts?
            }
        }


    }
    public static class IllegalAlertCreation extends Exception{
        IllegalAlertCreation(String detailMessage){super(detailMessage);}
    }

    private static boolean checkStringIsValid(String checkString){
        return checkString.trim().length() > 0;
    }

    public interface InteractionListener{
        enum ErrorResponses{
            MALFORMED_INTERACTION,
            GENERIC_ERROR,
            REJECTED,
            PERMISSIONS_ERROR
        }
        void onTimeout();
        void onAborted();
        void onError(ErrorResponses responses, String moreInfo);
    }
}
