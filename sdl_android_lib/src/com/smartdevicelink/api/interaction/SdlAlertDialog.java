package com.smartdevicelink.api.interaction;

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
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by mschwerz on 4/21/16.
 */
public class SdlAlertDialog {
    private static final String TAG = SdlPushNotification.class.getSimpleName();

    //temporarily putting buttons in as strings until the SDLButton is merged in
    //private final Collection<SdlButton> mButtons;
    private final TTSChunk mTtsChunk;
    private InteractionListener mListener;
    private final Alert newAlert;
    private Object BUTTON_LOCK;

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
        this.mTtsChunk = builder.mTtsChunk;
        mListener = builder.mListener;
    }


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
                mListener.onInteractionError(InteractionListener.ErrorResponses.PERMISSIONS_ERROR, "You are unable to send SdlAlertDialogs");
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


        public Builder setTextField1(String textField1){
            mTextField1 = textField1;
            return this;
        }
        public Builder setTextField2(String textField2){
            mTextField2 = textField2;
            return this;
        }
        public Builder setTextField3(String textField3){
            mTextField3 = textField3;
            return this;
        }
        public Builder setDuration(int duration){
            mDuration = duration;
            return this;
        }
        public Builder setToneUsed(boolean isToneUsed){
            mIsToneUsed = isToneUsed;
            return this;
        }
        public Builder setProgressIndicatorShown(boolean isIndicatorShown){
            mIsIndicatorShown = isIndicatorShown;
            return this;
        }
        /*
        public Builder addPushButtons(Collection<SdlButton> buttons){
            mButtons = buttons;
            return this;
        }
        */
        public Builder setSpeak(TTSChunk chunkUsed){
            mTtsChunk = chunkUsed;
            return this;
        }

        public Builder setListener(InteractionListener listener){
            mListener = listener;
            return this;
        }

        //validate the SdlPushNotification here?
        //verify there are 4 or less softbuttons
        //verify TTSChunk was created properly
        public SdlAlertDialog build() throws IllegalStateException{
            SdlAlertDialog builtAlert = new SdlAlertDialog(this);
            /*
            if(builtAlert.mButtons !=null){
                if(builtAlert.mButtons.size()>4){
                    throw new IllegalStateException("More buttons were added then possible");
                }
            }
            */

            if(builtAlert.mTtsChunk !=null){
                //validate tts?
            }

            return builtAlert;
        }


    }
    public interface InteractionListener{
        enum ErrorResponses{
            MALFORMED_INTERACTION,
            GENERIC_ERROR,
            PERMISSIONS_ERROR,

        }
        void onInteractionDone();
        void onInteractionCancelled();
        void onInteractionError(ErrorResponses responses, String moreInfo);
    }
}
