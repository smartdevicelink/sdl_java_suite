package com.smartdevicelink.api.choiceset;

import android.util.Log;
import android.util.SparseArray;

import com.smartdevicelink.api.SdlApplication;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by mschwerz on 5/4/16.
 */
public class SdlChoiceSetManager {
    private static final String TAG = SdlChoiceSetManager.class.getSimpleName();


    //keyed on the choice set name to the choice set id
    private final ArrayList<Integer> mIdSets;

    private final SdlApplication mSdlApplication;

    private int Choice_Count=0;
    private int Choice_Set_Count=0;


    public SdlChoiceSetManager(SdlApplication sdlApplication){
        mSdlApplication = sdlApplication;
        mIdSets = new ArrayList<>();
    }

    public boolean uploadIdSet(final String choiceSetName, final Collection<SdlChoice> choices, final IdReadyListener listener){

        final int choiceSetId=Choice_Set_Count++;
        final SdlChoiceSet newChoiceSet= new SdlChoiceSet(choiceSetName,populateChoicesWithIds(choices),choiceSetId);
        CreateInteractionChoiceSet newRequest = newChoiceSet.getRequest();
        newRequest.setInteractionChoiceSetID(choiceSetId);
        newRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Log.d(TAG,response.getResultCode().toString());
                mIdSets.add(choiceSetId);
                listener.onIdAdded(newChoiceSet);

                //On Invalid Id, try to delete old one?
            }
        });
        mSdlApplication.sendRpc(newRequest);
        return false;
    }

    public boolean deleteChoiceSet(SdlChoiceSet choiceSet){
        return deleteChoiceSet(choiceSet.getChoiceId());
    }

    private boolean deleteChoiceSet(int setId){
        return false;
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
