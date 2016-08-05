package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.smartdevicelink.api.file.SdlImage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by mschwerz on 5/4/16.
 */
public class SdlChoice {
    private final String mChoiceName;
    private final String mMenuText;
    private String mSubText;
    private String mRightHandText;
    private final OnSelectedListener mListener;
    private final ArrayList<Integer> mIds= new ArrayList<>();
    private SdlImage mSdlImage;
    private final HashSet<String> mVoiceCommands= new HashSet<>();

    public SdlChoice(@NonNull String choiceName, @NonNull String menuText, @NonNull Collection<String> manyVoiceCommands, @Nullable OnSelectedListener listener){
        mChoiceName= choiceName;
        mMenuText = menuText;
        mListener = listener;
        mVoiceCommands.addAll(manyVoiceCommands);
    }

    public SdlChoice(@NonNull String choiceName, @NonNull String menuText, @NonNull final String singleVoiceCommand, @Nullable OnSelectedListener listener){
        this(choiceName,menuText, new ArrayList<>(Collections.singletonList(singleVoiceCommand)),listener);
    }

    public String getChoiceName(){ return mChoiceName; }

    public SdlImage getSdlImage() {
        return mSdlImage;
    }

    public void setSdlImage(SdlImage sdlImage) {
        mSdlImage = sdlImage;
    }

    public String getMenuText() {
        return mMenuText;
    }


    OnSelectedListener getListener() {
        return mListener;
    }

    ArrayList<Integer> getId() {
        return mIds;
    }

    void addId(int id) {
        mIds.add(id);
    }

    void setIds(ArrayList<Integer> ids){
        mIds.clear();
        mIds.addAll(ids);
    }

    public void setRightHandText( String text ) {mRightHandText= text;}

    public String getRightHandText(){return mRightHandText;}

    public void setSubText( String text ){mSubText= text;}

    public String getSubText(){return mSubText;}

    SdlChoice getListenerLessDeepCopy(){
        SdlChoice copyChoice = new SdlChoice(mChoiceName, mMenuText,mVoiceCommands,null);
        copyChoice.mSubText =mSubText;
        copyChoice.mRightHandText= mRightHandText;
        copyChoice.setIds(mIds);
        copyChoice.mSdlImage= mSdlImage;
        return copyChoice;
    }

    boolean compareModelData(SdlChoice choice){
        //TODO: should compare the image name as well
        boolean checker=  compareStrings(choice.mChoiceName,mChoiceName);
        checker= checker&& compareStrings(choice.mMenuText,mMenuText);
        checker= checker&& compareStrings(choice.mSubText,mSubText);
        checker= checker&& compareStrings(choice.mRightHandText,mRightHandText);
        checker= checker&& choice.mVoiceCommands.size()== mVoiceCommands.size() && choice.mVoiceCommands.containsAll(mVoiceCommands);
        return checker;
    }


    public interface OnSelectedListener {

        void onManualSelection();
        void onVoiceSelection();
    }

    public void addVoiceCommand(String tts){
        mVoiceCommands.add(tts);
    }

    public Collection<String> getVoiceCommands(){ return mVoiceCommands; }

    private boolean compareStrings(String string1, String string2){
        if(string1!=null){
            return string1.equals(string2);
        }else
            return string2==null;

    }

}
