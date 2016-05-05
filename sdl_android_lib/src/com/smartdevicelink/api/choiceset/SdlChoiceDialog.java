package com.smartdevicelink.api.choiceset;

import android.util.Log;
import android.util.SparseArray;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by mschwerz on 5/4/16.
 */
public class SdlChoiceDialog {
    private static final String TAG = SdlChoiceDialog.class.getSimpleName();

    private int mDuration;
    private String mInitialText;
    private boolean mUseVoiceInteraction;
    private TTSChunk mInitialPrompt;
    private InteractionType mType;
    private boolean mUseSearch;
    private ResponseListener mListener;

    //hold onto a sparse array of choices to avoid looping over the choiceSets
    //after a selection
    //TODO: hang onto just the listener?
    private SparseArray<SdlChoice> quickChoiceFind = new SparseArray<>();
    private final PerformInteraction newInteraction;

    SdlChoiceDialog(Builder builder){
        newInteraction = new PerformInteraction();

        mDuration = builder.mDuration;
        mInitialText = builder.mInitialText;
        mUseVoiceInteraction = builder.mUseVoiceInteraction;
        mInitialPrompt = builder.mInitialPrompt;
        mType = builder.mType;
        mUseSearch = builder.mUseSearch;
        mListener = builder.mListener;

        ArrayList<Integer> choiceIds= new ArrayList<>();
        for(SdlChoiceSet set:builder.mChoiceSets){
            choiceIds.add(set.getChoiceId());
            SparseArray<SdlChoice> choices = set.getChoices();
            for(int i=0; i<choices.size();i++){
                quickChoiceFind.append(choices.keyAt(i),choices.get(choices.keyAt(i)));
            }
        }
        newInteraction.setInteractionChoiceSetIDList(choiceIds);
        newInteraction.setInitialPrompt(Collections.singletonList(mInitialPrompt));
        newInteraction.setInitialText(mInitialText);
        if(mDuration !=0)
            newInteraction.setTimeout(mDuration);
        LayoutMode newMode= discernCorrectInteractionLayout(mType, mUseSearch);
        if(newMode!=null)
            newInteraction.setInteractionLayout(newMode);
        newInteraction.setInteractionMode(getCorrectInteractionMode(mType,mUseVoiceInteraction));

    }

    public boolean send(SdlContext context){
        if (context.getSdlPermissionManager().isPermissionAvailable(SdlPermission.Alert)) {
            newInteraction.setOnRPCResponseListener(new OnRPCResponseListener() {
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

            context.sendRpc(newInteraction);
            return true;
        }
        return false;
    }

    private void parseResponse(RPCResponse response){
        if(response.getResultCode()!=Result.SUCCESS)
            handleResult(response.getResultCode(),response.getInfo());
        else{
            PerformInteractionResponse piResponse = (PerformInteractionResponse) response;
            switch (piResponse.getTriggerSource()) {
                case TS_MENU:
                    quickChoiceFind.get(piResponse.getChoiceID()).getListener().onManualSelection();
                    break;
                case TS_VR:
                    quickChoiceFind.get(piResponse.getChoiceID()).getListener().onVoiceSelection();
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

    private LayoutMode discernCorrectInteractionLayout(InteractionType type, boolean useSearch){

        if(type==null)
            return null;

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

    private InteractionMode getCorrectInteractionMode(InteractionType type, boolean useVoice){
        if(useVoice){
            if(type!=null)
                return InteractionMode.BOTH;
            else
                return InteractionMode.VR_ONLY;

        }else
            return InteractionMode.MANUAL_ONLY;
    }


    public static class Builder{
        private Collection<SdlChoiceSet> mChoiceSets;
        private int mDuration;
        private String mInitialText;
        private final boolean mUseVoiceInteraction;
        private TTSChunk mInitialPrompt;
        private final InteractionType mType;
        private final boolean mUseSearch;
        private ResponseListener mListener;

        public Builder(){
            mUseVoiceInteraction= true;
            mType=null;
            mUseSearch=false;
        }

        public Builder(InteractionType type, boolean useSearch, boolean includeVoice){
            mType= type;
            mUseSearch= useSearch;
            mUseVoiceInteraction= includeVoice;
        }

        public Builder(InteractionType type, boolean useSearch){
            this(type,useSearch,false);
        }


        public Builder setChoiceSets(Collection<SdlChoiceSet> mChoiceSets) {
            this.mChoiceSets = mChoiceSets;
            return this;
        }


        public Builder setDuration(int mDuration) {
            this.mDuration = mDuration;
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
            mInitialPrompt= chunk;
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

   public enum InteractionType{
        Icon,
        List,
        Search_Only
    }

    public interface ResponseListener{
        void onTimeout();
        void onAborted();
        void onError();
        void onSearch(String searchString);
    }
}
