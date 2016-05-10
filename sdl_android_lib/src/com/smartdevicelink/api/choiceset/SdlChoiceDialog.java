package com.smartdevicelink.api.choiceset;

import android.util.Log;
import android.util.SparseArray;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by mschwerz on 5/4/16.
 */
public class SdlChoiceDialog {
    private static final String TAG = SdlChoiceDialog.class.getSimpleName();
    private static final LayoutMode DEFAULT_LAYOUT= LayoutMode.ICON_ONLY;

    private final SdlManualInteraction mManualInteraction;
    private final SdlVoiceInteraction mVoiceInteraction;
    private final String mInitialText;
    private final TTSChunk mInitialPrompt;
    private final TTSChunk mHelpPrompt;
    private final ResponseListener mListener;
    private final SparseArray<SdlChoice.OnSelectedListener> mQuickListenerFind = new SparseArray<>();
    private final PerformInteraction mNewInteraction;

    SdlChoiceDialog(Builder builder){
        mNewInteraction = new PerformInteraction();

        mInitialText = builder.mInitialText;
        mInitialPrompt = builder.mInitialPrompt;
        mHelpPrompt = builder.mHelpPrompt;
        mListener = builder.mListener;
        mManualInteraction= builder.mManualInteraction.copy();
        mVoiceInteraction= builder.mVoiceInteraction.copy();

        ArrayList<Integer> choiceIds= new ArrayList<>();
        for(SdlChoiceSet set:builder.mChoiceSets){
            choiceIds.add(set.getChoiceId());
            SparseArray<SdlChoice.OnSelectedListener> choices = set.getChoices();
            for(int i=0; i<choices.size();i++){
                mQuickListenerFind.append(choices.keyAt(i),choices.get(choices.keyAt(i)));
            }
        }
        mNewInteraction.setInteractionChoiceSetIDList(choiceIds);
        mNewInteraction.setInitialPrompt(Collections.singletonList(mInitialPrompt));
        mNewInteraction.setInitialText(mInitialText);
        if(mManualInteraction!=null){
            if(mManualInteraction.getTimeout()!=null)
                mNewInteraction.setTimeout(mManualInteraction.getTimeout());
            LayoutMode mode= getCorrectLayoutMode(mManualInteraction.mType, mManualInteraction.isUseSearch());
            if(mode !=null)
                mNewInteraction.setInteractionLayout(mode);
        }

        if(mVoiceInteraction!=null){
            if(mVoiceInteraction.mTimeoutPrompt !=null)
                mNewInteraction.setTimeoutPrompt(Collections.singletonList(mVoiceInteraction.mTimeoutPrompt));
        }

        mNewInteraction.setInteractionMode(getCorrectInteractionMode(mManualInteraction,mVoiceInteraction));
        mNewInteraction.setHelpPrompt(Collections.singletonList(mHelpPrompt));

    }

    public boolean send(SdlContext context){
        if (context.getSdlPermissionManager().isPermissionAvailable(SdlPermission.Alert)) {
            if(!addVrHelpItems(context))
                return false;
            mNewInteraction.setOnRPCResponseListener(new OnRPCResponseListener() {
                @Override
                public void onResponse(int correlationId, RPCResponse response) {
                    PerformInteractionResponse interactionResponse = (PerformInteractionResponse) response;
                    Integer choiceID = ((PerformInteractionResponse) response).getChoiceID();
                    Log.d(TAG, Integer.toString(choiceID));
                    parseResponse(response);
                }

                @Override
                public void onError(int correlationId, Result resultCode, String info) {
                    super.onError(correlationId, resultCode, info);
                    Log.e(TAG, resultCode.toString()+" "+info);
                    handleResult(resultCode,info);
                }
            });

            context.sendRpc(mNewInteraction);
            return true;
        }
        return false;
    }

    private boolean addVrHelpItems(SdlContext context){
        if(mVoiceInteraction !=null && mVoiceInteraction.mVrHelpItems.size()>0) {
            ArrayList<VrHelpItem> vrItems = new ArrayList<>();
            for (SdlVoiceInteraction.SdlVoiceHelpItem item : mVoiceInteraction.mVrHelpItems) {
                if(context.getSdlFileManager().isFileOnModule(item.getSdlImage().getSdlName()))
                    return false;
                VrHelpItem helpItem = new VrHelpItem();
                helpItem.setText(item.getDisplayText());
                Image helpImage = new Image();
                helpImage.setImageType(ImageType.DYNAMIC);
                helpImage.setValue(item.getSdlImage().getSdlName());
            }
            mNewInteraction.setVrHelp(vrItems);
        }
        return true;
    }

    private void parseResponse(RPCResponse response){
        if(response.getResultCode()!=Result.SUCCESS)
            handleResult(response.getResultCode(),response.getInfo());
        else{
            PerformInteractionResponse piResponse = (PerformInteractionResponse) response;
            switch (piResponse.getTriggerSource()) {
                case TS_MENU:
                    mQuickListenerFind.get(piResponse.getChoiceID()).onManualSelection();
                    break;
                case TS_VR:
                    mQuickListenerFind.get(piResponse.getChoiceID()).onVoiceSelection();
                    break;
                case TS_KEYBOARD:
                    mListener.onSearch(piResponse.getManualTextEntry());
                    break;
                default:
                    handleResult(response.getResultCode(),response.getInfo());
            }
        }
    }

    private void handleResult(Result response, String info){

        if(mListener==null)
            return;
        switch (response) {
            case SUCCESS:
                mListener.onTimeout();
                break;
            case ABORTED:
                mListener.onAborted();
                break;
            case INVALID_DATA:
                mListener.onError();
                break;
            case DISALLOWED:
                mListener.onError();
                break;
            case REJECTED:
                mListener.onError();
                break;
            default:
                mListener.onError();
                break;
        }
    }

    private LayoutMode getCorrectLayoutMode(SdlManualInteraction.ManualInteractionType type, boolean useSearch){

        if(type==null)
            return DEFAULT_LAYOUT;

        switch (type) {
            case Icon:
                if(useSearch)
                    return LayoutMode.ICON_WITH_SEARCH;
                else
                    return LayoutMode.ICON_ONLY;
            case List:
                if(useSearch)
                    return LayoutMode.LIST_WITH_SEARCH;
                else
                    return LayoutMode.LIST_ONLY;
            case Search_Only:
                return LayoutMode.KEYBOARD;
        }
        //should never reach this
        return null;
    }

    private InteractionMode getCorrectInteractionMode(SdlManualInteraction manualInteraction, SdlVoiceInteraction voiceInteraction){
        if(voiceInteraction!=null){
            if(manualInteraction!=null)
                return InteractionMode.BOTH;
            else
                return InteractionMode.VR_ONLY;

        }else
            return InteractionMode.MANUAL_ONLY;
    }


    public static class Builder{
        private Collection<SdlChoiceSet> mChoiceSets;
        private SdlManualInteraction mManualInteraction;
        private SdlVoiceInteraction mVoiceInteraction;
        private String mInitialText;
        private TTSChunk mInitialPrompt;
        private TTSChunk mHelpPrompt;
        private ResponseListener mListener;


        public Builder(){
        }


        public Builder setChoiceSets(Collection<SdlChoiceSet> mChoiceSets) {
            this.mChoiceSets = mChoiceSets;
            return this;
        }


        public Builder setManualInteraction( SdlManualInteraction interaction){
            this.mManualInteraction= interaction;
            return this;
        }

        public Builder setVoiceInteraction(SdlVoiceInteraction interaction){
            this.mVoiceInteraction= interaction;
            return this;
        }

        public Builder setInitialText(String mInitialText) {
            this.mInitialText = mInitialText;
            return this;
        }


        public Builder setInitialPrompt(TTSChunk mInitialPrompt) {
            this.mInitialPrompt = mInitialPrompt;
            return this;
        }

        public Builder setInitialPrompt(String initialPrompt){
            TTSChunk chunk= new TTSChunk();
            chunk.setText(initialPrompt);
            chunk.setType(SpeechCapabilities.TEXT);
            this.mInitialPrompt= chunk;
            return this;
        }

        public Builder setHelpPrompt(TTSChunk mInitialPrompt) {
            this.mHelpPrompt = mInitialPrompt;
            return this;
        }

        public Builder setHelpPrompt(String initialPrompt){
            TTSChunk chunk= new TTSChunk();
            chunk.setText(initialPrompt);
            chunk.setType(SpeechCapabilities.TEXT);
            this.mHelpPrompt= chunk;
            return this;
        }


        public Builder setListener(ResponseListener listener){
            this.mListener = listener;
            return this;
        }

        public SdlChoiceDialog build(){
            return new SdlChoiceDialog(this);
        }
    }


    public interface ResponseListener{
        void onTimeout();
        void onAborted();
        void onError();
        void onSearch(String searchString);
    }

}
