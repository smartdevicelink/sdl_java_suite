package com.smartdevicelink.api.interaction;

import android.util.Log;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.api.permission.SdlPermissionManager;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by mschwerz on 4/21/16.
 */
public class SdlPushNotification {
    private static final String TAG = SdlPushNotification.class.getSimpleName();

    //temporarily putting buttons in as strings until the SDLButton is merged in
    private final Collection<String> mButtons;
    private final TTSChunk mTtsChunk;
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
        newAlert.setTtsChunks(Collections.singletonList(builder.mTtsChunk));
        this.mButtons = builder.mButtons;
        this.mTtsChunk = builder.mTtsChunk;
        mListener = builder.mListener;
    }


    public void show(SdlContext context) {
        SdlPermissionManager checkPermissions = context.getSdlPermissionManager();
        if (checkPermissions.isPermissionAvailable(SdlPermission.Alert)) {

            newAlert.setOnRPCResponseListener(new OnRPCResponseListener() {
                @Override
                public void onError(int correlationId, Result resultCode, String info) {
                    super.onError(correlationId, resultCode, info);
                    if (mListener != null)
                        handleResultResponse(resultCode, info);
                }

                @Override
                public void onResponse(int correlationId, RPCResponse response) {
                    if (mListener != null)
                        handleResultResponse(response.getResultCode(), response.getInfo());
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
            default:
                mListener.onInteractionError(InteractionListener.ErrorResponses.GENERIC_ERROR, info);
                break;
        }
    }

    public static class Builder {

        private String mTextField1;
        private String mTextField2;
        private String mTextField3;
        private int mDuration;
        private boolean mIsToneUsed;
        private boolean mIsIndicatorShown;
        private Collection<String> mButtons;
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
        public Builder addPushButtons(Collection<String> buttons){
            mButtons = buttons;
            return this;
        }

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
        public SdlPushNotification build() throws IllegalStateException{
            SdlPushNotification builtAlert = new SdlPushNotification(this);
            if(builtAlert.mButtons !=null){
                if( builtAlert.mButtons.size()>4){
                    throw new IllegalStateException("More buttons were added then possible");
                }
            }

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
            PERMISSIONS_ERROR
        }
        void onInteractionDone();
        void onInteractionCancelled();
        void onInteractionError(ErrorResponses responses, String moreInfo);
    }
}
