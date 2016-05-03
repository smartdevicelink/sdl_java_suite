package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.api.permission.SdlPermissionManager;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//import com.smartdevicelink.api.view.SdlButton;

/**
 * Created by mschwerz on 4/21/16.
 */
public class SdlPushNotification {
    private static final String TAG = SdlPushNotification.class.getSimpleName();

    private String[] mTextFields= new String[3];
    private int mDuration;
    boolean mIsToneUsed;
    boolean mIsIndicatorUsed;
    private TTSChunk mTtsChunk;
    private final Collection<SdlButton> mButtons;
    private boolean mIsPending= false;
    private boolean mIsButtonPressed= false;
    private InteractionListener mListener;
    private final Alert newAlert;

    //just build the alert instead of setting variables
    SdlPushNotification(Builder builder) {

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
     * Method to send the built {@link SdlPushNotification} to the module. If there is a {@link SdlAlertDialog.InteractionListener}
     * set to {@link SdlAlertDialog}, then the listener will be informed if the dialog fails, is cancelled or if
     * the interaction is able to be completed normally. Permissions must be granted to use
     * push notification while not in foreground. If you do not have these permissions, please use
     * {@link SdlAlertDialog} while in the foreground.
     * @param context The SdlContext that the {@link SdlPushNotification} will be sent from
     */
    public boolean show(@NonNull SdlContext context) {
        SdlPermissionManager checkPermissions = context.getSdlPermissionManager();
        if (checkPermissions.isPermissionAvailable(SdlPermission.Alert) && !mIsPending) {
            mIsButtonPressed=false;

            final SdlContext applicationContext= context.getSdlApplicationContext();
            if(!registerAllButtons(newAlert, applicationContext))
                return false;
            newAlert.setOnRPCResponseListener(new OnRPCResponseListener() {
                @Override
                public void onError(int correlationId, Result resultCode, String info) {
                    super.onError(correlationId, resultCode, info);
                    handleResultResponse(resultCode, info, applicationContext);
                }

                @Override
                public void onResponse(int correlationId, RPCResponse response) {
                    handleResultResponse(response.getResultCode(), response.getInfo(), applicationContext);
                }
            });
            context.sendRpc(newAlert);
            mIsPending=true;
            return true;
        } else {
            return false;
        }
    }

    private void handleResultResponse(Result response, String info, SdlContext context) {
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

    private InteractionListener getCleanUpListener(final SdlContext context){
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

    private boolean registerAllButtons(Alert alertToHaveButtons, final SdlContext context){
        ArrayList<SoftButton> createdSoftbuttons = new ArrayList<>();
        if (this.mButtons == null) {
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
                    }else {
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

    private void unregisterAllButtons(Collection<SdlButton> ids, SdlContext context){
        if (ids == null || context == null) {
            return;
        }
        for (SdlButton button : ids) {
            context.unregisterButtonCallback(button.getId());
        }
    }

    public static class Builder {

        private String[] mTextFields= new String[3];
        private int mDuration;
        private boolean mIsToneUsed;
        private boolean mIsIndicatorShown;
        private Collection<SdlButton> mButtons;
        private TTSChunk mTtsChunk;
        private InteractionListener mListener;


        public Builder(){

        }

        /**
         * Sets the top line of text for the built {@link SdlPushNotification}. If you need to leave the line
         * empty, please don't set the field or set it to null.
         * @param textField1 The string to be shown with the SdlPushNotification.
         *                   Cannot be an empty string or all whitespace.
         * @return The builder for the {@link SdlPushNotification}
         */
        public Builder setTextField1(String textField1){
            mTextFields[0] = textField1;
            return this;
        }

        /**
         * Sets the middle line of text for the built {@link SdlPushNotification}. The text
         * cannot be empty or only contain whitespace. If you need to leave the line
         * empty, please don't set the field or set it to null.
         * @param textField2 The string to be shown with the SdlPushNotification.
         *                   Cannot be an empty string or all whitespace.
         * @return The builder for the {@link SdlPushNotification}
         */
        public Builder setTextField2(String textField2){
            mTextFields[1] = textField2;
            return this;
        }

        /**
         * Sets the bottom line of text for the built {@link SdlPushNotification}. If you need to leave the line
         * empty, please don't set the field or set it to null.
         * @param textField3 The string to be shown with the SdlPushNotification.
         *                   Cannot be an empty string or all whitespace.
         * @return The builder for the {@link SdlPushNotification}
         */
        public Builder setTextField3(String textField3){
            mTextFields[2] = textField3;
            return this;
        }

        /**
         * Convenience method to take in a string and set to the appropriate line items
         * based on the line breaks in the given string
         * @param textFields A string with optional linebreaks
         * @return The builder for the {@link SdlAlertDialog}
         */
        public Builder setText(String textFields){
            String [] lines= textFields.split("\\r?\\n");
            for(int i=0;i<mTextFields.length;i++){
                if(i<lines.length){
                    mTextFields[i]=lines[i];
                }
            }
            return this;
        }

        /**
         * Sets the duration that the {@link SdlPushNotification} will show up for.
         * The min value is 3000 and the max value is 10000
         * @param duration The amount of seconds the SdlPushNotification should appear
         * @return The builder for the {@link SdlPushNotification}
         */
        public Builder setDuration(int duration){
            mDuration = duration;
            return this;
        }

        /**
         * Sets if a tone should sound when the {@link SdlPushNotification} appears.
         * @param isToneUsed Set to true if a tone should sound when the SdlPushNotification appears.
         * @return The builder for the {@link SdlPushNotification}
         */
        public Builder setToneUsed(boolean isToneUsed){
            mIsToneUsed = isToneUsed;
            return this;
        }

        /**
         *
         * @param isIndicatorShown
         * @return The builder for the {@link SdlPushNotification}
         */
        public Builder setProgressIndicatorShown(boolean isIndicatorShown){
            mIsIndicatorShown = isIndicatorShown;
            return this;
        }

        /**
         * Sets the push buttons that the user can touch when the {@link SdlPushNotification}
         * appears
         * @param buttons Collection of SdlButtons that describe what the buttons should look like
         * @return The builder for the {@link SdlPushNotification}
         */
        public Builder addPushButtons(Collection<SdlButton> buttons){
            this.mButtons = buttons;
            return this;
        }


        /**
         * Sets the TTS to be spoken when the {@link SdlPushNotification} appears.
         * @param ttsChunk The description of the Text To Speech to be read aloud
         * @return The builder for the {@link SdlPushNotification}
         */
        public Builder setSpeak(TTSChunk ttsChunk){
            mTtsChunk = ttsChunk;
            return this;
        }

        /**
         * Convenience method for Text to Speech with the {@link SdlPushNotification} which
         * will speak the text provided aloud.
         * @param textToSpeak Text to be spoken aloud
         * @return The builder for the {@link SdlPushNotification}
         */
        public Builder setSpeak(String textToSpeak){
            TTSChunk newChunk= new TTSChunk();
            newChunk.setText(textToSpeak);
            newChunk.setType(SpeechCapabilities.TEXT);
            mTtsChunk= newChunk;
            return this;
        }

        /**
         * Sets the listener for when the {@link SdlPushNotification} finishes with the interaction,
         * is interrupted by another interaction, or an error occurred.
         * @param listener The object to listen for the {@link SdlPushNotification} callbacks.
         * @return The builder for the {@link SdlPushNotification}
         */
        public Builder setListener(InteractionListener listener){
            this.mListener = listener;
            return this;
        }

        //validate the SdlPushNotification here?
        //verify there are 4 or less softbuttons
        //verify TTSChunk was created properly
        /**
         * Creates the {@link SdlPushNotification} object that will display a Dialog with text, sounds and buttons
         * as set in the builder.
         * @return SdlPushNotification, call {@link #show(SdlContext)} in order to send the
         * built {@link SdlPushNotification}
         * @throws IllegalPushNotificationCreation Exception will be called if the parameters set when building
         * are illegal
         */
        public SdlPushNotification build() throws IllegalPushNotificationCreation{
            SdlPushNotification builtAlert = new SdlPushNotification(this);
            if(builtAlert.mButtons !=null){
                if(builtAlert.mButtons.size()>4){
                    throw new IllegalPushNotificationCreation("More buttons were added then possible for the AlertDialog");
                }
                for(SdlButton button:mButtons){
                    if(button==null){
                        throw new IllegalPushNotificationCreation("One of the buttons provided is null, make sure the SdlButtons added are instantiated");
                    }
                }
            }
            boolean atLeastOneNotNull=false;
            for(int i=0;i<builtAlert.mTextFields.length;i++){
                if(builtAlert.mTextFields[i]!=null){
                    atLeastOneNotNull=true;
                    if(!checkStringIsValid(mTextFields[i]))
                        throw new IllegalPushNotificationCreation("Invalid String was provided to TextField"+Integer.toString(i+1));
                }
            }
            if(!atLeastOneNotNull)
                throw new IllegalPushNotificationCreation("All of the TextFields are null, please make sure at least one is set");

            List<TTSChunk> chunks= builtAlert.newAlert.getTtsChunks();
            if(chunks !=null){
                if(chunks.get(0)!=null){

                }
                //validate tts?
            }

            return builtAlert;
        }


        private static boolean checkStringIsValid(String checkString){
            return checkString.trim().length() > 0;
        }

        //for enforcing certain parameters are not set incorrectly
        public class IllegalPushNotificationCreation extends Exception{
            IllegalPushNotificationCreation(String detailMessage){super(detailMessage);}
        }
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
