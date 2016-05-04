package com.smartdevicelink.api.choiceset;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;

import com.smartdevicelink.api.SdlApplication;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by mschwerz on 5/4/16.
 */
public class SdlChoiceSetManager {
    private static final String TAG = SdlChoiceSetManager.class.getSimpleName();

    private final SdlApplication mSdlApplication;

    private int Choice_Count=0;
    private int Choice_Set_Count=0;

    private HashSet<Integer> mIds = new HashSet<>();


    public SdlChoiceSetManager(SdlApplication sdlApplication){
        mSdlApplication = sdlApplication;
    }

    public boolean uploadChoiceSet(@NonNull final SdlChoiceSet choiceSet, @Nullable final IdReadyListener listener){

        if(mIds.contains(choiceSet.getChoiceId()))
            return true;

        CreateInteractionChoiceSet newRequest = new CreateInteractionChoiceSet();

        ArrayList<Choice> proxyChoices= new ArrayList<>();
        for(int i=0; i<choiceSet.getChoices().size();i++) {
            int choiceID= choiceSet.getChoices().keyAt(i);
            SdlChoice currentChoice = choiceSet.getChoices().get(choiceID);
            Choice convertToChoice= new Choice();
            convertToChoice.setMenuName(currentChoice.getMenuText());
            convertToChoice.setSecondaryText(currentChoice.getSubText());
            convertToChoice.setTertiaryText(currentChoice.getRightHandText());
            convertToChoice.setChoiceID(choiceID);
            convertToChoice.setVrCommands(currentChoice.getVoiceCommands());
            proxyChoices.add(convertToChoice);
        }
        newRequest.setChoiceSet(proxyChoices);
        newRequest.setInteractionChoiceSetID(choiceSet.getChoiceId());
        newRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Log.d(TAG,response.getResultCode().toString());
                mIds.add(choiceSet.getChoiceId());
                if(listener!=null)
                    listener.onIdAdded(choiceSet);
            }

            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                super.onError(correlationId, resultCode, info);
                if(listener!=null)
                    listener.onIdError(choiceSet);
            }
        });
        mSdlApplication.sendRpc(newRequest);
        return false;
    }

    public boolean deleteChoiceSet(SdlChoiceSet choiceSet){
        return deleteChoiceSet(choiceSet.getChoiceId());
    }

    private boolean deleteChoiceSet(int setId){
        mIds.remove(setId);
        DeleteInteractionChoiceSet deleteRequest = new DeleteInteractionChoiceSet();
        deleteRequest.setInteractionChoiceSetID(setId);
        return false;
    }

    public SdlChoiceSet createChoiceSet(@Nullable String choiceSetName, @NonNull Collection<SdlChoice> choices){
        final int choiceSetId=Choice_Set_Count++;
        return new SdlChoiceSet(choiceSetName,populateChoicesWithIds(choices),choiceSetId);
    }

    private SparseArray<SdlChoice> populateChoicesWithIds(Collection<SdlChoice> sdlChoices){
        SparseArray<SdlChoice> newIdArray= new SparseArray<>();
        for(SdlChoice choice:sdlChoices){
            choice.addId(Choice_Count++);
            newIdArray.append(Choice_Count,choice);
        }
        return newIdArray;
    }

    public interface IdReadyListener{

        void onIdAdded(SdlChoiceSet sdlIdCommand);

        void onIdError(SdlChoiceSet sdlIdCommand);

    }


}
