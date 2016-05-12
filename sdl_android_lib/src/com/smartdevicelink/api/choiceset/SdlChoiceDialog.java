package com.smartdevicelink.api.choiceset;

import android.support.annotation.NonNull;
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
import java.util.HashSet;

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
    private final HashSet<String> mNames = new HashSet<>();
    private final PerformInteraction mNewInteraction;
    private boolean mIsPending=false;

    SdlChoiceDialog(final Builder builder){
        mNewInteraction = new PerformInteraction();

        mInitialText = builder.mInitialText;
        mInitialPrompt = builder.mInitialPrompt;
        mHelpPrompt = builder.mHelpPrompt;

        if(builder.mManualInteraction!=null)
            mManualInteraction= builder.mManualInteraction.copy();
        else
            mManualInteraction= null;

        if(builder.mVoiceInteraction!=null)
            mVoiceInteraction= builder.mVoiceInteraction.copy();
        else
            mVoiceInteraction= null;

        ArrayList<Integer> choiceIds= new ArrayList<>();
        for(SdlChoiceSet set:builder.mChoiceSets){
            choiceIds.add(set.getChoiceId());
            SparseArray<SdlChoice.OnSelectedListener> choices = set.getChoices();
            //keep the names of the choice sets provided to ensure
            //that they have been uploaded before sending the Perform Interaction
            mNames.add(set.getSetName());
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
            LayoutMode mode= getCorrectLayoutMode(mManualInteraction.getType(), mManualInteraction.isUseSearch());
            if(mode !=null)
                mNewInteraction.setInteractionLayout(mode);
        }

        if(mVoiceInteraction!=null){
            if(mVoiceInteraction.getTimeoutPrompt() !=null)
                mNewInteraction.setTimeoutPrompt(Collections.singletonList(mVoiceInteraction.mTimeoutPrompt));
        }

        mNewInteraction.setInteractionMode(getCorrectInteractionMode(mManualInteraction,mVoiceInteraction));
        if(mHelpPrompt!=null)
            mNewInteraction.setHelpPrompt(Collections.singletonList(mHelpPrompt));

        mListener = createResponseDebugListener(builder.mListener);
    }

    public boolean send(SdlContext context){
        //check if we have permissions to show the Perform Interaction first
        if (context.getSdlPermissionManager().isPermissionAvailable(SdlPermission.PerformInteraction)&&!mIsPending) {
            //attach the help items to the perform interaction RPC
            //will return false if one of the images provided is not uploaded currently
            if(!addVrHelpItems(context))
                return false;
            //checking to see that the interaction choice sets are currently uploaded
            //to the module
            for(String name:mNames) {
                if (!context.getSdlChoiceSetManager().hasBeenUploaded(name))
                    return false;
            }
            mNewInteraction.setOnRPCResponseListener(new OnRPCResponseListener() {
                @Override
                public void onResponse(int correlationId, RPCResponse response) {
                    parseResponse(response);
                }

                @Override
                public void onError(int correlationId, Result resultCode, String info) {
                    super.onError(correlationId, resultCode, info);
                    handleResult(resultCode,info);
                }
            });

            context.sendRpc(mNewInteraction);
            mIsPending=true;
            return true;
        }
        return false;
    }

    private boolean addVrHelpItems(SdlContext context){
        if(mVoiceInteraction !=null && mVoiceInteraction.getVrHelpItems().size()>0) {
            ArrayList<VrHelpItem> vrItems = new ArrayList<>();
            for (Integer i=0; i<mVoiceInteraction.getVrHelpItems().size();i++){
                SdlVoiceInteraction.SdlVoiceHelpItem item= mVoiceInteraction.getVrHelpItems().get(i);
                VrHelpItem helpItem = new VrHelpItem();
                helpItem.setText(item.getDisplayText());
                helpItem.setPosition(i+1);
                vrItems.add(helpItem);
                if(item.getSdlImage()==null)
                    continue;
                if(context.getSdlFileManager().isFileOnModule(item.getSdlImage().getSdlName()))
                    return false;
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
        mIsPending=false;
    }

    private void handleResult(Result response, String info){
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

    //listener to debug out responses that are parsed to the set listener
    private ResponseListener createResponseDebugListener(final ResponseListener responseListener){
       return new ResponseListener() {
            @Override
            public void onTimeout() {
                Log.d(TAG,"Perform Interaction timed out");
                if(responseListener!=null)
                    responseListener.onTimeout();
            }

            @Override
            public void onAborted() {
                Log.d(TAG,"Perform Interaction was aborted");
                if(responseListener!=null)
                    responseListener.onAborted();
            }

            @Override
            public void onError() {
                Log.d(TAG,"Perform Interaction timed out");
                if(responseListener!=null)
                    responseListener.onError();
            }

            @Override
            public void onSearch(String searchString) {
                Log.d(TAG,"Perform Interaction search came back with: "+searchString);
                if(responseListener!=null)
                    responseListener.onSearch(searchString);
            }
        };
    }


    public static class Builder{
        private Collection<SdlChoiceSet> mChoiceSets = new ArrayList<>();
        private SdlManualInteraction mManualInteraction;
        private SdlVoiceInteraction mVoiceInteraction;
        private String mInitialText;
        private TTSChunk mInitialPrompt;
        private TTSChunk mHelpPrompt;
        private ResponseListener mListener;


        public Builder(){
        }


        public Builder setChoiceSets(@NonNull Collection<SdlChoiceSet> choiceSets) {
            this.mChoiceSets = choiceSets;
            return this;
        }

        public Builder addChoiceSet(SdlChoiceSet choiceSet){
            this.mChoiceSets.add(choiceSet);
            return this;
        }

        public Builder addChoiceSet(SdlChoiceSetCreation choiceSetCreation){
            this.mChoiceSets.add(choiceSetCreation.getRepresentativeChoiceSet());
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

        public Builder setInitialText(String initialText) {
            this.mInitialText = initialText;
            return this;
        }


        public Builder setInitialPrompt(TTSChunk initialPrompt) {
            this.mInitialPrompt = initialPrompt;
            return this;
        }

        public Builder setInitialPrompt(String initialPrompt){
            TTSChunk chunk= new TTSChunk();
            chunk.setText(initialPrompt);
            chunk.setType(SpeechCapabilities.TEXT);
            this.mInitialPrompt= chunk;
            return this;
        }

        public Builder setHelpPrompt(TTSChunk initialPrompt) {
            this.mHelpPrompt = initialPrompt;
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
