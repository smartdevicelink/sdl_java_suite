package com.smartdevicelink.api.interaction;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.api.permission.SdlPermissionManager;
import com.smartdevicelink.api.view.SdlButton;
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
import java.util.List;

/**
 * Created by mschwerz on 4/21/16.
 */
public class SdlPushNotification {
    private static final String TAG = SdlPushNotification.class.getSimpleName();

    //private final Collection<SdlButton> mButtons;
    private InteractionListener mListener;
    private Alert newAlert;

    //just build the alert instead of setting variables
    SdlPushNotification(Builder builder) {

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


    public void show(@NonNull SdlContext context) {
        SdlPermissionManager checkPermissions = context.getSdlPermissionManager();
        if (checkPermissions.isPermissionAvailable(SdlPermission.Alert)) {
            final SdlContext applicationContext = context.getSdlApplicationContext();
            //final ArrayList<Integer> unregisterIds= registerAllButtons(newAlert, context);
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
            context.sendRpc(newAlert);
        } else {
            if (mListener != null)
                mListener.onInteractionError(InteractionListener.ErrorResponses.PERMISSIONS_ERROR, "App does not have permissions to support SdlPushNotification fully, please use SdlAlertDialog");
                Log.w(TAG,"App does not have permissions to support SdlPushNotification fully, please use SdlAlertDialog");
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
                mListener.onInteractionError(InteractionListener.ErrorResponses.REJECTED, info);
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
            this.mButtons = buttons;
            return this;
        }
        */

        public Builder setSpeak(TTSChunk chunkUsed){
            mTtsChunk = chunkUsed;
            return this;
        }

        public Builder setListener(InteractionListener listener){
            this.mListener = listener;
            return this;
        }

        //validate the SdlPushNotification here?
        //verify there are 4 or less softbuttons
        //verify TTSChunk was created properly
        public SdlPushNotification build() throws IllegalPushNotificationCreation{
            SdlPushNotification builtAlert = new SdlPushNotification(this);
            /*
            if(builtAlert.mButtons !=null){
                if( builtAlert.mButtons.size()>4){
                    throw new IllegalPushNotificationCreation("More buttons were added then possible the ");
                }
            }
            */

            String[] arrayOfTextFields= builtAlert.getAlertTextAsArray();
            for(int i=0;i<arrayOfTextFields.length;i++){
                if(arrayOfTextFields[i]!=null){
                    if(!checkStringIsValid(arrayOfTextFields[i]))
                        throw new IllegalPushNotificationCreation("Invalid String was provided to TextField"+Integer.toString(i+1));
                }
            }

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
        void onInteractionDone();
        void onInteractionCancelled();
        void onInteractionError(ErrorResponses responses, String moreInfo);
    }
}
