package com.smartdevicelink.api.choiceset;

import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mschwerz on 5/4/16.
 */
public class SdlChoice {
    private final String mChoiceName;
    private final String mMenuText;
    private String mSubText;
    private String mRightHandText;
    private final OnSelectedListener mListener;
    private ArrayList<Integer> mIds= new ArrayList<>();
    private SdlImage mSdlImage;
    private final Collection<String> mVoiceCommands;

    public SdlChoice(String choiceName, String menuText, Collection<String> manyVoiceCommands, OnSelectedListener listener){
        mChoiceName= choiceName;
        mMenuText = menuText;
        mListener = listener;
        mVoiceCommands= manyVoiceCommands;
    }

    public SdlChoice(String choiceName, String menuText, String singleVoiceCommand, OnSelectedListener listener){
        mChoiceName= choiceName;
        mMenuText = menuText;
        mListener = listener;
        mVoiceCommands= new ArrayList<>();
        mVoiceCommands.add(singleVoiceCommand);
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


    public OnSelectedListener getListener() {
        return mListener;
    }

    ArrayList<Integer> getId() {
        return mIds;
    }

    void addId(int id) {
        mIds.add(id);
    }

    void setRightHandText( String text ) {mRightHandText= text;}

    public String getRightHandText(){return mRightHandText;}

    void setSubText( String text ){mSubText= text;}

    public String getSubText(){return mSubText;}

    public interface OnSelectedListener {

        void onManualSelection();
        void onVoiceSelection();
    }

    public void addVoiceCommand(String tts){
        mVoiceCommands.add(tts);
    }

    public Collection<String> getVoiceCommands(){ return mVoiceCommands; }

}
