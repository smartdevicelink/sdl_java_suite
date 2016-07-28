package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.interfaces.SdlInteractionResponseListener;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
    private final ArrayList<Integer> mChoiceSets= new ArrayList<>();
    private final HashMap<Integer,SdlChoice.OnSelectedListener> mQuickListenerFind = new HashMap<>();
    protected SdlChoiceDialogSender mSender= new SdlChoiceDialogSender(SdlPermission.PerformInteraction);

    SdlChoiceDialog(final Builder builder){
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

        for(SdlChoiceSet set:builder.mChoiceSets){
            mChoiceSets.add(set.getChoiceSetId());
            SparseArray<SdlChoice> choices = set.getChoices();
            //keep the names of the choice sets provided to ensure
            //that they have been uploaded before sending the Perform Interaction
            for(int i=0; i<choices.size();i++){
                mQuickListenerFind.put(choices.keyAt(i),choices.get(choices.keyAt(i)).getListener());
            }
        }

    }

    private PerformInteraction createPerformInteraction(SdlContext context){
        PerformInteraction newPerformInteraction= addVrHelpItems(context, new PerformInteraction());
        if(newPerformInteraction!=null) {
            newPerformInteraction.setInitialText(mInitialText);
            newPerformInteraction.setInitialPrompt(Collections.singletonList(mInitialPrompt));
            if(mManualInteraction!=null){
                if(mManualInteraction.getTimeout()!=null)
                    newPerformInteraction.setTimeout(mManualInteraction.getTimeout());
                LayoutMode mode= getCorrectLayoutMode(mManualInteraction.getType(), mManualInteraction.isUseSearch());
                if(mode !=null)
                    newPerformInteraction.setInteractionLayout(mode);
            }
            if(mVoiceInteraction!=null){
                if(mVoiceInteraction.getTimeoutPrompt() !=null)
                    newPerformInteraction.setTimeoutPrompt(Collections.singletonList(mVoiceInteraction.mTimeoutPrompt));
            }
            newPerformInteraction.setInteractionMode(getCorrectInteractionMode(mManualInteraction,mVoiceInteraction));
            if(mHelpPrompt!=null)
                newPerformInteraction.setHelpPrompt(Collections.singletonList(mHelpPrompt));

            if(mChoiceSets.size()>0)
                newPerformInteraction.setInteractionChoiceSetIDList(mChoiceSets);
            else
                //A choice set was not given by the developer
                Log.d(TAG, "The user has not specified a choice set...");
        }
        return newPerformInteraction;

    }

    private PerformInteraction addVrHelpItems(SdlContext context, PerformInteraction performInteraction){
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
                    return null;
                Image helpImage = new Image();
                helpImage.setImageType(ImageType.DYNAMIC);
                helpImage.setValue(item.getSdlImage().getSdlName());
            }

            performInteraction.setVrHelp(vrItems);
        }
        return performInteraction;
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

    public boolean send(SdlContext context, final SdlChoiceResponseListener listener){
        PerformInteraction performInteraction= createPerformInteraction(context);
        //should give a more definitive way to indicate the cause of the PerformInteraction not being sent
        return performInteraction!=null&& mSender.sendInteraction(context, performInteraction, new SdlInteractionSender.SdlDataReceiver() {
            @Override
            public void handleRPCResponse(RPCResponse response) {
                PerformInteractionResponse piResponse = (PerformInteractionResponse) response;
                switch (piResponse.getTriggerSource()) {
                    case TS_MENU:
                        mQuickListenerFind.get(piResponse.getChoiceID()).onManualSelection();
                        break;
                    case TS_VR:
                        mQuickListenerFind.get(piResponse.getChoiceID()).onVoiceSelection();
                        break;
                    case TS_KEYBOARD:
                        if(listener!=null)
                            listener.onSearch(piResponse.getManualTextEntry());
                        break;
                }
            }
        }, listener);
    }

    private class SdlChoiceDialogSender extends SdlInteractionSender{

        protected SdlChoiceDialogSender(SdlPermission permission) {
            super(permission);
        }

        @Override
        protected boolean isAbleToSendInteraction(SdlPermission permission, SdlContext context) {
            boolean checkAllAreUploaded = true;
            for (Integer choiceId:mChoiceSets){
                if(!context.getSdlChoiceSetManager().hasBeenUploaded(choiceId)){
                    checkAllAreUploaded = false;
                    break;
                }
            }
            return super.isAbleToSendInteraction(permission, context)&&checkAllAreUploaded;
        }

        @Override
        boolean sendInteraction(@NonNull SdlContext context, @NonNull RPCRequest request, @Nullable SdlDataReceiver receiver, @Nullable SdlInteractionResponseListener listener) {
            return super.sendInteraction(context, request, receiver, listener);
        }
    }

    public static class Builder{
        private HashSet<SdlChoiceSet> mChoiceSets = new HashSet<>();
        private SdlManualInteraction mManualInteraction;
        private SdlVoiceInteraction mVoiceInteraction;
        private String mInitialText;
        private TTSChunk mInitialPrompt;
        private TTSChunk mHelpPrompt;


        public Builder(){
        }


        public Builder setChoiceSets(@NonNull Collection<SdlChoiceSet> choiceSets) {
            this.mChoiceSets.clear();
            this.mChoiceSets.addAll(choiceSets);
            return this;
        }

        public Builder addChoiceSet(@NonNull SdlChoiceSet choiceSet){
            this.mChoiceSets.add(choiceSet);
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



        public SdlChoiceDialog build(){
            return new SdlChoiceDialog(this);
        }
    }


    public interface SdlChoiceResponseListener extends SdlInteractionResponseListener{
        void onSearch(String searchString);
    }

}
