package com.smartdevicelink.api.choiceset;

import android.util.SparseArray;

import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by mschwerz on 5/4/16.
 */
public class SdlChoiceSet {

    private final SparseArray<SdlChoice.OnSelectedListener> mChoices= new SparseArray<>();
    private final HashMap<String,Integer> mNameToId;
    private final String mChoiceSetName;
    private final int mChoiceId;

    SdlChoiceSet(String name, int choiceId, HashMap<String,Integer> choices){
        mChoiceSetName = name;
        mNameToId = choices;
        mChoiceId = choiceId;
    }

    SdlChoiceSet copy(){
        return new SdlChoiceSet(mChoiceSetName,mChoiceId,mNameToId);
    }


    public String getSetName(){return mChoiceSetName;}
    public Set<String> getChoiceNames(){return mNameToId.keySet();}

    SparseArray<SdlChoice.OnSelectedListener> getChoices(){return mChoices;}

    int getChoiceId(){ return mChoiceId; }

    public boolean setListenersForChoiceName(HashMap<String,SdlChoice.OnSelectedListener> relation){
        for(String choiceName:relation.keySet()){
            mChoices.put(mNameToId.get(choiceName),relation.get(choiceName));
        }
        return mNameToId.keySet().equals(relation.keySet());
    }

}
