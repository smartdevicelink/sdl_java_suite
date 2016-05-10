package com.smartdevicelink.api.choiceset;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.smartdevicelink.api.interfaces.SdlContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by mschwerz on 5/6/16.
 */
public class SdlChoiceSetCreation {
    private final static String TAG= SdlChoiceSet.class.getSimpleName();

    private SparseArray<SdlChoice> mChoices;
    private String mChoiceSetName;
    private final Integer mChoiceSetId;
    private SdlChoiceSetManager mManager;

    public SdlChoiceSetCreation(@NonNull String name, @NonNull ArrayList<SdlChoice> choices, @NonNull SdlContext context){
        mChoices= populateChoicesWithIds(choices,context);
        mChoiceSetName= name;
        mManager= context.getSdlChoiceSetManager();
        mChoiceSetId = mManager.requestChoiceSetCount();
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

    //allows us to hang onto the ids for both the
    //choice set and associated choices without their listeners
    //and extra data
    SdlChoiceSet createChoiceSet(){
        return createChoiceSet(false);
    }

    //makes it easier to grab a SdlChoiceSet
    public SdlChoiceSet getRepresentativeChoiceSet(){
        if(hasBeenUploaded())
            return createChoiceSet(true);
        else
            return null;
    }

    private SdlChoiceSet createChoiceSet(boolean includeListeners){
        HashMap<String, Integer> choiceNames= new HashMap<>();
        HashMap<String,SdlChoice.OnSelectedListener> choiceNameToListener= new HashMap<>();
        for(int i=0; i<getChoices().size();i++){
            Integer position= getChoices().keyAt(i);
            SdlChoice currChoice= getChoices().get(position);
            choiceNames.put(currChoice.getChoiceName(), position);
            if(includeListeners){
                choiceNameToListener.put(currChoice.getChoiceName(),currChoice.getListener());
            }
        }
        SdlChoiceSet createdSet= new SdlChoiceSet(getSetName(), getChoiceSetId(),choiceNames);
        if(includeListeners)
            createdSet.setListenersForChoiceName(choiceNameToListener);
        return createdSet;
    }



}
