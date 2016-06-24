package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.smartdevicelink.api.interfaces.SdlContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by mschwerz on 5/6/16.
 */
public class SdlChoiceSet {
    private final static String TAG= SdlChoiceSet.class.getSimpleName();

    private final SparseArray<SdlChoice> mChoices;
    private final String mChoiceSetName;
    private final Integer mChoiceSetId;
    private final SdlChoiceSetManager mManager;

    public SdlChoiceSet(@NonNull String name, @NonNull ArrayList<SdlChoice> choices, @NonNull SdlContext context){
        mChoiceSetName= name;
        mManager= context.getSdlChoiceSetManager();
        SdlChoiceSet potentialIdentical= mManager.grabUploadedChoiceSet(name);
        HashMap<String, SdlChoice> easyMappingForArrayList= null;

        if(potentialIdentical!=null)
            easyMappingForArrayList= potentialIdentical.compareToSavedSet(name,choices);

        if(easyMappingForArrayList!=null){
            mChoices= copyOverChoiceIds(easyMappingForArrayList,potentialIdentical.getChoices());
            mChoiceSetId= potentialIdentical.getChoiceSetId();
        }else {
            mChoices= populateChoicesWithIds(choices,context);
            mChoiceSetId = mManager.requestChoiceSetCount();
        }

    }

    private SdlChoiceSet(SdlChoiceSet set){
        mChoiceSetName= set.getSetName();
        mChoices= set.depopulatedListenerChoices(set.getChoices());
        mChoiceSetId= set.getChoiceSetId();
        mManager= set.mManager;
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
        return new SdlChoiceSet(this);
    }

    private SparseArray<SdlChoice> depopulatedListenerChoices(SparseArray<SdlChoice> choices){
        SparseArray<SdlChoice> cloneArray= new SparseArray<>();
        for(int j=0; j<choices.size();j++){
            SdlChoice cloneChoice= choices.get(choices.keyAt(j)).getListenerLessDeepCopy();
            cloneArray.append(choices.keyAt(j), cloneChoice);
        }
        return cloneArray;
    }

    //returns a mapping of the user provided choices keyed off of their choice name
    private HashMap<String,SdlChoice> compareToSavedSet(String name, ArrayList<SdlChoice> choices){
        boolean firstCheck= name.equals(mChoiceSetName);
        if(!firstCheck)
            return null;
        ArrayList<SdlChoice> copyList= new ArrayList<>(choices);
        HashMap<String,SdlChoice> positionInArray= new HashMap<>();
        for(int i=0; i<mChoices.size();i++) {
            boolean foundChoice=false;
            for (SdlChoice choice : copyList) {
                int key= mChoices.keyAt(i);
                foundChoice= mChoices.get(key).compareModelData(choice);
                if(foundChoice){
                    positionInArray.put(choice.getChoiceName(),choice);
                    copyList.remove(choice);
                    break;
                }
            }
            if(!foundChoice)
                return null;
        }
        return positionInArray;
    }


    private SparseArray<SdlChoice> copyOverChoiceIds(HashMap<String, SdlChoice> mapping, SparseArray<SdlChoice> choices){
        SparseArray<SdlChoice> cloneArray= new SparseArray<>();
        for(int i=0; i<choices.size(); i++){
            int key= choices.keyAt(i);
            SdlChoice choice=  choices.get(key);

            SdlChoice choiceReference= mapping.get(choice.getChoiceName());
            choiceReference.setIds(choice.getId());

            cloneArray.put(key,choiceReference);
        }
        return cloneArray;
    }

    /*
    SparseArray<SdlChoice> copyOverChoiceIds(ArrayList<SdlChoice> newChoices, SparseArray<SdlChoice> choices){
        SparseArray<SdlChoice> cloneArray= new SparseArray<>();
        for(int i=0; i<choices.size(); i++){
            SdlChoice choice= choices.get(choices.keyAt(i));
            newChoices.get(i).setIds(choice.getId());
            cloneArray.append(choices.keyAt(i),newChoices.get(i));
        }
        return cloneArray;
    }
    */
}
