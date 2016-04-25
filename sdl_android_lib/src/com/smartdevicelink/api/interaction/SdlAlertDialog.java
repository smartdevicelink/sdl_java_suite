package com.smartdevicelink.api.interaction;

import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.api.SdlActivity;
import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.api.permission.SdlPermissionManager;
//import com.smartdevicelink.api.view.SdlButton;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * Created by mschwerz on 4/21/16.
 */
public class SdlAlertDialog {
    private static final String TAG = SdlAlertDialog.class.getSimpleName();

    //temporarily putting buttons in as strings until the SDLButton is merged in
    //private final Collection<SdlButton> mButtons;
    private InteractionListener mListener;
    private final Alert newAlert;

    //just build the alert instead of setting variables
    SdlAlertDialog(Builder builder) {

        newAlert = new Alert();
        newAlert.setAlertText1(builder.mTextField1);
        newAlert.setAlertText2(builder.mTextField2);
        newAlert.setAlertText3(builder.mTextField3);
        newAlert.setDuration(builder.mDuration);
        newAlert.setProgressIndicator(builder.mIsIndicatorShown);
        newAlert.setPlayTone(builder.mIsToneUsed);
        if(builder.mTtsChunk!=null)
            newAlert.setTtsChunks(Collections.singletonList(builder.mTtsChunk));
        //this.mButtons = builder.mButtons;
        mListener = builder.mListener;
    }


    /**
     * Method to send the built {@link SdlAlertDialog} to the module, while the app is in the foreground. If there is a {@link com.smartdevicelink.api.interaction.SdlAlertDialog.InteractionListener}
     * set to {@link SdlAlertDialog}, then the listener will be informed if the dialog fails, is cancelled or if
     * the interaction is able to be completed normally.
     * @param callingActivity The SdlActivity that the SdlAlertDialog will be sent from
     */
    public final void show(@NonNull final SdlActivity callingActivity) {

        if(!callingActivity.isAbleToSendAlertDialog()){
            Log.w(TAG, "SdlAlertDialog was attempted to be sent while the SdlActivity was not in the foreground, please try again");
            return;
        }
        SdlPermissionManager checkPermissions = callingActivity.getSdlPermissionManager();

        if (checkPermissions.isPermissionAvailable(SdlPermission.Alert)) {

            //grabbing the application context for the anon listener to unregister the SdlButtons
            final SdlContext applicationContext = callingActivity.getSdlApplicationContext();

            //giving the listener an array of ids so that the buttons can be unregistered once we are done with them
            //not giving the collection of buttons since two calls to show in a row for the alert would cause the
            //2nd set of ids created to be unregistered instead of the first
            //final ArrayList<Integer> unregisterIds= registerAllButtons(newAlert,callingActivity.getSdlApplicationContext());
            newAlert.setOnRPCResponseListener(new OnRPCResponseListener() {
                @Override
                public void onError(int correlationId, Result resultCode, String info) {
                    super.onError(correlationId, resultCode, info);
                    if (mListener != null)
                        handleResultResponse(resultCode, info);
                    //unregisterAllButtons(unregisterIds,applicationContext);
                }

                @Override
                public void onResponse(int correlationId, RPCResponse response) {
                    if (mListener != null)
                        handleResultResponse(response.getResultCode(), response.getInfo());
                    //unregisterAllButtons(unregisterIds,applicationContext);
                }
            });
            callingActivity.sendRpc(newAlert);
        } else {
            if (mListener != null)
                mListener.onInteractionError(InteractionListener.ErrorResponses.PERMISSIONS_ERROR, "You are unable to send SdlAlertDialogs at this time");
        }
    }

    private void handleResultResponse(Result response, String info) {
        switch (response) {
            case SUCCESS:
                mListener.onInteractionDone();
                break;
            case ABORTED:
                mListener.onInteractionCancelled();
                break;
            case INVALID_DATA:
                mListener.onInteractionError(InteractionListener.ErrorResponses.MALFORMED_INTERACTION, info);
                break;
            case DISALLOWED:
                mListener.onInteractionError(InteractionListener.ErrorResponses.PERMISSIONS_ERROR, info);
                break;
            case REJECTED:
                mListener.onInteractionError(InteractionListener.ErrorResponses.REJECTED,info);
                break;
            default:
                mListener.onInteractionError(InteractionListener.ErrorResponses.GENERIC_ERROR, info);
                break;
        }
    }
/*
    private ArrayList<Integer> registerAllButtons(Alert alertToHaveButtons, SdlContext context){
            ArrayList<SoftButton> createdSoftbuttons = new ArrayList<>();
            ArrayList<Integer> idsToTrack = new ArrayList<>();
            if (this.mButtons == null) {
                return null;
            }
            for (SdlButton button : this.mButtons) {
                if (button.getListener() != null) {
                    SoftButton softButtonFromSdlButton = new SoftButton();
                    softButtonFromSdlButton.setText(button.getText());
                    softButtonFromSdlButton.setType(SoftButtonType.SBT_TEXT);
                    softButtonFromSdlButton.setIsHighlighted(false);
                    softButtonFromSdlButton.setSystemAction(SystemAction.DEFAULT_ACTION);
                    //SdlImage to set image?
                    //softButtonFromSdlButton.setSoftButtonID(button.getId());
                    int buttonID = context.registerButtonCallback(button.getListener());
                    idsToTrack.add(buttonID);
                    softButtonFromSdlButton.setSoftButtonID(buttonID);
                    createdSoftbuttons.add(softButtonFromSdlButton);
                }
            }
            alertToHaveButtons.setSoftButtons(createdSoftbuttons);
            return idsToTrack;
    }

    private void unregisterAllButtons(ArrayList<Integer> ids, SdlContext context){
            if (ids == null || context == null) {
                return;
            }
            for (Integer id : ids) {
                context.unregisterButtonCallback(id);
            }
    }
    */
    private String[] getAlertTextAsArray(){
        return new String[]{newAlert.getAlertText1(),newAlert.getAlertText2(),newAlert.getAlertText3()};
    }

    public static class Builder {

        private String mTextField1;
        private String mTextField2;
        private String mTextField3;
        private int mDuration;
        private boolean mIsToneUsed;
        private boolean mIsIndicatorShown;
        //private Collection<SdlButton> mButtons;
        private TTSChunk mTtsChunk;
        private InteractionListener mListener;

        public Builder(){

        }

        /**
         * Sets the top line of text for the built {@link SdlAlertDialog}. If you need to leave the line
         * empty, please don't set the field or set it to null.
         * @param textField1 The string to be shown with the SdlAlertDialog.
         *                   Cannot be an empty string or all whitespace.
         * @return The builder for the {@link SdlAlertDialog}
         */
        public Builder setTextField1(String textField1){
            mTextField1 = textField1;
            return this;
        }

        /**
         * Sets the middle line of text for the built {@link SdlAlertDialog}. The text
         * cannot be empty or only contain whitespace. If you need to leave the line
         * empty, please don't set the field or set it to null.
         * @param textField2 The string to be shown with the SdlAlertDialog.
         *                   Cannot be an empty string or all whitespace.
         * @return The builder for the {@link SdlAlertDialog}
         */
        public Builder setTextField2(String textField2){
            mTextField2 = textField2;
            return this;
        }

        /**
         * Sets the bottom line of text for the built {@link SdlAlertDialog}. If you need to leave the line
         * empty, please don't set the field or set it to null.
         * @param textField3 The string to be shown with the SdlAlertDialog.
         *                   Cannot be an empty string or all whitespace.
         * @return The builder for the {@link SdlAlertDialog}
         */
        public Builder setTextField3(String textField3){
            mTextField3 = textField3;
            return this;
        }

        /**
         * Sets the duration that the {@link SdlAlertDialog} will show up for.
         * The min value is 3000 and the max value is 10000
         * @param duration The amount of seconds the SdlAlertDialog should appear
         * @return The builder for the {@link SdlAlertDialog}
         */
        public Builder setDuration(int duration){
            mDuration = duration;
            return this;
        }

        /**
         * Sets if a tone should sound when the {@link SdlAlertDialog} appears.
         * @param isToneUsed Set to true if a tone should sound when the SdlAlertDialog appears.
         * @return The builder for the {@link SdlAlertDialog}
         */
        public Builder setToneUsed(boolean isToneUsed){
            mIsToneUsed = isToneUsed;
            return this;
        }

        /**
         *
         * @param isIndicatorShown
         * @return The builder for the {@link SdlAlertDialog}
         */
        public Builder setProgressIndicatorShown(boolean isIndicatorShown){
            mIsIndicatorShown = isIndicatorShown;
            return this;
        }

        /**
         * Sets the push buttons that the user can touch when the {@link SdlAlertDialog}
         * appears
         * @param buttons Collection of SdlButtons that describe what the buttons should look like
         * @return The builder for the {@link SdlAlertDialog}
         */
        /*
        public Builder setPushButtons(Collection<SdlButton> buttons){
            this.mButtons = buttons;
            return this;
        }
        */

        /**
         * Sets the TTS to be spoken when the {@link SdlAlertDialog} appears.
         * @param ttsChunk The description of the Text To Speech to be read aloud
         * @return The builder for the {@link SdlAlertDialog}
         */
        public Builder setSpeak(TTSChunk ttsChunk){
            mTtsChunk = ttsChunk;
            return this;
        }

        /**
         * Convenience method for Text to Speech with the {@link SdlAlertDialog} which
         * will speak the text provided aloud.
         * @param textToSpeak Text to be spoken aloud
         * @return The builder for the {@link SdlAlertDialog}
         */
        public Builder setSpeak(String textToSpeak){
            TTSChunk newChunk= new TTSChunk();
            newChunk.setText(textToSpeak);
            newChunk.setType(SpeechCapabilities.TEXT);
            mTtsChunk= newChunk;
            return this;
        }

        /**
         * Sets the listener for when the {@link SdlAlertDialog} finishes with the interaction,
         * is interrupted by another interaction, or an error occurred.
         * @param listener The object to listen for the {@link SdlAlertDialog} callbacks.
         * @return The builder for the {@link SdlAlertDialog}
         */
        public Builder setListener(InteractionListener listener){
            this.mListener = listener;
            return this;
        }

        //validate the SdlAlertDialog here?
        //verify there are 4 or less softbuttons
        //verify TTSChunk was created properly
        /**
         * Creates the {@link SdlAlertDialog} object that will display a Dialog with text, sounds and buttons
         * as set in the builder. Use {@link SdlPushNotification} if you want to send
         * the dialog while the app is not visible on the module and have permission to do so.
         * @return SdlAlertDialog, call {@link #show(SdlActivity)} in order to send the
         * built {@link SdlAlertDialog}
         * @throws IllegalAlertDialogCreation Exception will be called if the parameters set when building
         * are illegal
         */
        public SdlAlertDialog build() throws IllegalAlertDialogCreation {
            SdlAlertDialog builtAlert = new SdlAlertDialog(this);
            /*
            if(builtAlert.mButtons !=null){
                if(builtAlert.mButtons.size()>4){
                    throw new IllegalAlertDialogCreation("More buttons were added then possible the ");
                }
            }
            */
            String[] arrayOfTextFields= builtAlert.getAlertTextAsArray();
            boolean atLeastOneNotNull=false;
            for(int i=0;i<arrayOfTextFields.length;i++){
                if(arrayOfTextFields[i]!=null){
                    atLeastOneNotNull=true;
                    if(!checkStringIsValid(arrayOfTextFields[i]))
                        throw new IllegalAlertDialogCreation("Invalid String was provided to TextField"+Integer.toString(i+1));
                }
            }
            if(!atLeastOneNotNull)
                throw new IllegalAlertDialogCreation("All of the TextFields are null, please make sure at least one is set");

            List<TTSChunk> chunks= builtAlert.newAlert.getTtsChunks();
            if(chunks !=null){
                if(chunks.get(0)!=null){

                }
                //validate tts?
            }

            return builtAlert;
        }

        public class IllegalAlertDialogCreation extends Exception{
            IllegalAlertDialogCreation(String detailMessage){super(detailMessage);}
        }

    }

    private static boolean checkStringIsValid(String checkString){
            return checkString.trim().length() > 0;
    }

    public interface InteractionListener{
        enum ErrorResponses{
            MALFORMED_INTERACTION,
            GENERIC_ERROR,
            REJECTED,
            PERMISSIONS_ERROR,

        }
        void onInteractionDone();
        void onInteractionCancelled();
        void onInteractionError(ErrorResponses responses, String moreInfo);
    }
}
