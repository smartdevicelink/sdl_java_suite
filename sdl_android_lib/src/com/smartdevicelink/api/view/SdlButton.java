package com.smartdevicelink.api.view;

import com.smartdevicelink.api.file.SdlImage;

public class SdlButton {

    private SdlImage mSdlImage;
    private String mText;
    private OnPressListener mListener;
    private int mId;

    public SdlImage getSdlImage() {
        return mSdlImage;
    }

    public void setSdlImage(SdlImage sdlImage) {
        mSdlImage = sdlImage;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public OnPressListener getListener() {
        return mListener;
    }

    public void setListener(OnPressListener listener) {
        mListener = listener;
    }

    int getId() {
        return mId;
    }

    void setId(int id) {
        mId = id;
    }

    public interface OnPressListener {

        void onButtonPress();

    }
}
