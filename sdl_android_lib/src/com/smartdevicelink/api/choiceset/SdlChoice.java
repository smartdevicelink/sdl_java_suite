package com.smartdevicelink.api.choiceset;

import com.smartdevicelink.api.file.SdlImage;

import java.util.ArrayList;

/**
 * Created by mschwerz on 5/4/16.
 */
public class SdlChoice {
    private final String mMenuText;
    private String mSubText;
    private String mRightHandText;
    private final OnSelectedListener mListener;
    private ArrayList<Integer> mIds;
    private SdlImage mSdlImage;
    private boolean isGraphicOnly;

    public SdlChoice(String menuText, OnSelectedListener listener){
        mMenuText = menuText;
        mListener = listener;
    }

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

        void OnChoiceSelected();

    }

    public boolean isGraphicOnly() {
        return isGraphicOnly;
    }

    public void setGraphicOnly(boolean graphicOnly) {
        isGraphicOnly = graphicOnly;
    }

}
