package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.smartdevicelink.api.interfaces.SdlContext;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by mschwerz on 5/6/16.
 */
public class SdlChoiceSet {
    private final static String TAG= SdlChoiceSet.class.getSimpleName();

    private final SparseArray<SdlChoice> mChoices;
    private final Integer mChoiceSetId;
    private final SdlChoiceSetManager mManager;

    public SdlChoiceSet( @NonNull ArrayList<SdlChoice> choices, @NonNull SdlContext context){
        mManager= context.getSdlChoiceSetManager();
        SdlChoiceSet matchingSet = null;
        for(SdlChoiceSet setToCompare:mManager.getUploadedSets()){
            if(setToCompare.compareToSavedSet(choices)){
                matchingSet = setToCompare;
                break;
            }
        }
        if(matchingSet!=null) {
            mChoices= copyOverChoiceIds(choices,matchingSet.getChoices());
            mChoiceSetId= matchingSet.getChoiceSetId();
        }else {
            mChoices= populateChoicesWithIds(choices,context);
            mChoiceSetId = mManager.requestChoiceSetCount();
        }

    }

    private SdlChoiceSet(SdlChoiceSet set){
        mChoices= set.depopulatedListenerChoices(set.getChoices());
        mChoiceSetId= set.getChoiceSetId();
        mManager= set.mManager;
    }


    SparseArray<SdlChoice> getChoices(){return mChoices;}

    int getChoiceSetId(){
        return mChoiceSetId;
    }

    public boolean hasBeenUploaded(){return mManager.hasBeenUploaded(this);}

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

    private boolean compareToSavedSet(ArrayList<SdlChoice> choices){
        for(int i=0; i<mChoices.size();i++) {
            if(!mChoices.get(mChoices.keyAt(i)).compareModelData(choices.get(i))){
                return false;
            }
        }

        return true;
    }


    private SparseArray<SdlChoice> copyOverChoiceIds(ArrayList<SdlChoice> choicesToBePopulated, SparseArray<SdlChoice> previousSet){
        SparseArray<SdlChoice> cloneArray= new SparseArray<>();
        for(int i=0; i<previousSet.size(); i++){
            int key= previousSet.keyAt(i);
            SdlChoice choice=  previousSet.get(key);

            SdlChoice choiceReference= choicesToBePopulated.get(i);
            choiceReference.setIds(choice.getId());

            cloneArray.put(key,choiceReference);
        }
        return cloneArray;
    }

}
