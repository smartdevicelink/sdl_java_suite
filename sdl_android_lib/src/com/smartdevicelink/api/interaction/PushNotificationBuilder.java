package com.smartdevicelink.api.interaction;

import com.smartdevicelink.api.SdlApplication;
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
 * Created by mschwerz on 4/19/16.
 */
public class PushNotificationBuilder {

    private String mTextField1;
    private String mTextField2;
    private String mTextField3;
    private int mDuration;
    private boolean mIsToneUsed;
    private boolean mIsIndicatorShown;
    private Collection<String> mButtons;
    private TTSChunk mTtsChunk;
    private InteractionListener mListener;
    private final SdlContext mContext;


    public PushNotificationBuilder(SdlContext context){
        mContext = context;
    }

    public PushNotificationBuilder setTextField1(String textField1){
        mTextField1 = textField1;
        return this;
    }
    public PushNotificationBuilder setTextField2(String textField2){
        mTextField2 = textField2;
        return this;
    }
    public PushNotificationBuilder setTextField3(String textField3){
        mTextField3 = textField3;
        return this;
    }
    public PushNotificationBuilder setDuration(int duration){
        mDuration = duration;
        return this;
    }
    public PushNotificationBuilder setToneUsed(boolean isToneUsed){
        mIsToneUsed = isToneUsed;
        return this;
    }
    public PushNotificationBuilder setProgressIndicatorShown(boolean isIndicatorShown){
        mIsIndicatorShown = isIndicatorShown;
        return this;
    }
    public PushNotificationBuilder addPushButtons(Collection<String> buttons){
        mButtons = buttons;
        return this;
    }

    public PushNotificationBuilder setSpeak(TTSChunk chunkUsed){
        mTtsChunk = chunkUsed;
        return this;
    }

    public PushNotificationBuilder setListener(InteractionListener listener){
        mListener = listener;
        return this;
    }

    //validate the SdlAlert here?
    //verify there are 4 or less softbuttons
    //verify TTSChunk was created properly
    public SdlAlert build(){
        SdlAlert builtAlert = new SdlAlert(this);
        /*
        if(builtAlert.getButtons().size()>4){
            throw new IllegalStateException("More buttons were added then possible");
        }
        if(builtAlert.getSpeak() !=null){
            //validate tts?
        }
        */
        return new SdlAlert(this);
    }


    public class SdlAlert{

        //private final Collection<String> mButtons;
        //private final TTSChunk mTtsChunk;
        private InteractionListener mListener;
        private final SdlContext mContext;
        private Alert newAlert;

       //just build the alert instead of setting variables
       private SdlAlert(PushNotificationBuilder builder){

           newAlert = new Alert();
           newAlert.setAlertText1(builder.mTextField1);
           newAlert.setAlertText2(builder.mTextField2);
           newAlert.setAlertText3(builder.mTextField3);
           newAlert.setDuration(builder.mDuration);
           newAlert.setProgressIndicator(builder.mIsIndicatorShown);
           newAlert.setPlayTone(builder.mIsToneUsed);

            //mButtons = builder.mButtons;
            //mButtons = Collections.unmodifiableCollection(builder.mButtons);
            //mTtsChunk = builder.mTtsChunk;
            //mListener = builder.mListener;
            mContext = builder.mContext;
        }


        public void show(){
            SdlPermissionManager checkPermissions= mContext.getSdlPermissionManager();
            if(checkPermissions.isPermissionAvailable(SdlPermission.Alert)){

                newAlert.setOnRPCResponseListener(new OnRPCResponseListener() {
                    @Override
                    public void onError(int correlationId, Result resultCode, String info) {
                        super.onError(correlationId, resultCode, info);
                        if(mListener==null)
                            return;
                        mListener.onInteractionError();
                    }

                    @Override
                    public void onResponse(int correlationId, RPCResponse response) {
                        if(mListener==null)
                            return;
                        //Do response stuff
                        Result codedResponse = response.getResultCode();
                        switch (codedResponse) {
                            case SUCCESS:
                                mListener.onInteractionDone();
                                break;
                            case ABORTED:
                                mListener.onInteractionCancelled();
                                break;
                            default:
                                mListener.onInteractionError();
                                break;
                        }
                    }
                });
                //send rpc after
                mContext.sendRpc(newAlert);
            }else{
                if (mListener!=null)
                    mListener.onInteractionCancelled();
            }
        }



    }

    public interface InteractionListener{
        void onInteractionDone();
        void onInteractionCancelled();
        void onInteractionError();
    }



}
