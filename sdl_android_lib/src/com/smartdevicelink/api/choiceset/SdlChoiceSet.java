package com.smartdevicelink.api.choiceset;

import android.util.SparseArray;

import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;

import java.util.ArrayList;

/**
 * Created by mschwerz on 5/4/16.
 */
public class SdlChoiceSet {

    private SparseArray<SdlChoice> mChoices;
    private String mChoiceSetName;
    private int mChoiceId;

    SdlChoiceSet(String name, SparseArray<SdlChoice> choices, int choiceId){
        mChoiceSetName = name;
        mChoices = choices;
        mChoiceId = choiceId;
    }


    public String getSetName(){return mChoiceSetName;}

    SparseArray<SdlChoice> getChoices(){return mChoices;}

    int getChoiceId(){ return mChoiceId; }



}
