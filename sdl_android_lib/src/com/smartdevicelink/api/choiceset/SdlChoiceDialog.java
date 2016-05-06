package com.smartdevicelink.api.choiceset;

import android.support.annotation.Nullable;
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
import java.util.Collection;
import java.util.Collections;

/**
 * Created by mschwerz on 5/4/16.
 */
public class SdlChoiceDialog {
    private static final String TAG = SdlChoiceDialog.class.getSimpleName();
    private static final LayoutMode DEFAULT_LAYOUT= LayoutMode.ICON_ONLY;

    private int mDuration;
    private String mInitialText;
    private boolean mUseVoiceInteraction;
    private TTSChunk mInitialPrompt;
    private ManualInteractionType mType;
    private boolean mUseSearch;
    private ResponseListener mListener;
    private SparseArray<SdlChoice.OnSelectedListener> mQuickListenerFind = new SparseArray<>();
    private final PerformInteraction mNewInteraction;

    SdlChoiceDialog(Builder builder){
        mNewInteraction = new PerformInteraction();

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
            SparseArray<SdlChoice.OnSelectedListener> choices = set.getChoices();
            for(int i=0; i<choices.size();i++){
                mQuickListenerFind.append(choices.keyAt(i),choices.get(choices.keyAt(i)));
            }
        }
        mNewInteraction.setInteractionChoiceSetIDList(choiceIds);
        mNewInteraction.setInitialPrompt(Collections.singletonList(mInitialPrompt));
        mNewInteraction.setInitialText(mInitialText);
        if(mDuration !=0)
            mNewInteraction.setTimeout(mDuration);
        LayoutMode newMode= getCorrectLayoutMode(mType, mUseSearch);
        if(newMode!=null)
            mNewInteraction.setInteractionLayout(newMode);
        mNewInteraction.setInteractionMode(getCorrectInteractionMode(mType,mUseVoiceInteraction));

    }

    public boolean send(SdlContext context){
        if (context.getSdlPermissionManager().isPermissionAvailable(SdlPermission.Alert)) {
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

    private LayoutMode getCorrectLayoutMode(ManualInteractionType type, boolean useSearch){

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

    private InteractionMode getCorrectInteractionMode(ManualInteractionType type, boolean useVoice){
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
        private final ManualInteractionType mType;
        private boolean mUseSearch= false;
        private ResponseListener mListener;


        public Builder(@Nullable ManualInteractionType type, boolean includeVoice){
            mType= type;
            mUseVoiceInteraction= includeVoice;
        }


        public Builder setChoiceSets(Collection<SdlChoiceSet> mChoiceSets) {
            this.mChoiceSets = mChoiceSets;
            return this;
        }


        public Builder setDuration(int mDuration) {
            this.mDuration = mDuration;
            return this;
        }

        public Builder setUseSearchWithManual(boolean useSearch){
            this.mUseSearch= useSearch;
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

   public enum ManualInteractionType {
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
