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

    protected CreateInteractionChoiceSet getRequest() {
        CreateInteractionChoiceSet choiceSet = new CreateInteractionChoiceSet();
        ArrayList<Choice> proxyChoices= new ArrayList<>();
        for(int i=0; i<mChoices.size();i++) {
            SdlChoice currentChoice = mChoices.get(mChoices.keyAt(i));
            Choice convertToChoice= new Choice();
            convertToChoice.setMenuName(currentChoice.getMenuText());
            convertToChoice.setSecondaryText(currentChoice.getSubText());
            convertToChoice.setTertiaryText(currentChoice.getRightHandText());
            proxyChoices.add(convertToChoice);
        }
        choiceSet.setChoiceSet(proxyChoices);
        return choiceSet;
    }

    public String getSetName(){return mChoiceSetName;}

    SparseArray<SdlChoice> getChoices(){return mChoices;}

    int getChoiceId(){ return mChoiceId; }



}
