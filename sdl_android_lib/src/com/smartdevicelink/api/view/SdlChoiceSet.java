package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.smartdevicelink.api.interfaces.SdlContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by mschwerz on 5/6/16.
 */
public class SdlChoiceSet {
    private final static String TAG= SdlChoiceSet.class.getSimpleName();

    private SparseArray<SdlChoice> mChoices;
    private String mChoiceSetName;
    private final Integer mChoiceSetId;
    private SdlChoiceSetManager mManager;

    public SdlChoiceSet(@NonNull String name, @NonNull ArrayList<SdlChoice> choices, @NonNull SdlContext context){
        mChoices= populateChoicesWithIds(choices,context);
        mChoiceSetName= name;
        mManager= context.getSdlChoiceSetManager();
        mChoiceSetId = mManager.requestChoiceSetCount();
    }

    private SdlChoiceSet(Integer id){
        mChoiceSetId= id;
    }

    public String getSetName(){return mChoiceSetName;}

    SparseArray<SdlChoice> getChoices(){return mChoices;}

    int getChoiceSetId(){
        return mChoiceSetId;
    }

    public boolean hasBeenUploaded(){return mManager.hasBeenUploaded(getSetName());}

    private SparseArray<SdlChoice> populateChoicesWithIds(Collection<SdlChoice> sdlChoices, SdlContext context){
        SparseArray<SdlChoice> newIdArray= new SparseArray<>();
        for(SdlChoice choice:sdlChoices){
            Integer newId= context.getSdlChoiceSetManager().requestChoiceCount();
            choice.addId(newId);
            newIdArray.append(newId,choice);
        }
        return newIdArray;
    }

    SdlChoiceSet deepCopy(){
        SdlChoiceSet set= new SdlChoiceSet(mChoiceSetId);
        set.mChoiceSetName= mChoiceSetName;
        set.mManager= mManager;
        set.mChoices = depopulatedListenerChoices(mChoices);
        return set;
    }

    SparseArray<SdlChoice> depopulatedListenerChoices(SparseArray<SdlChoice> choices){
        SparseArray<SdlChoice> cloneArray= new SparseArray<>();
        for(int j=0; j<choices.size();j++){
            SdlChoice cloneChoice= choices.get(choices.keyAt(j)).getListenerLessDeepCopy();
            cloneArray.append(choices.keyAt(j), cloneChoice);
        }
        return cloneArray;
    }
    public void setListenersForChoices(HashMap<String,SdlChoice.OnSelectedListener> relation){
        for(int i=0; i<mChoices.size();i++) {
            SdlChoice choice= mChoices.get(mChoices.keyAt(i));
            choice.setOnSelectedListener(relation.get(choice.getChoiceName()));
        }
    }

}
