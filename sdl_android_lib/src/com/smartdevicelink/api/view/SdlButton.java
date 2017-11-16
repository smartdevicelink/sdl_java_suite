package com.smartdevicelink.api.view;

import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;

public class SdlButton {

    private final String mText;
    private final OnPressListener mListener;
    private int mId;
    private SdlImage mSdlImage;
    private boolean isGraphicOnly;
    private boolean isHighlighted;
    private SystemAction mSystemAction = SystemAction.DEFAULT_ACTION;

    public SdlButton(String text, OnPressListener listener){
        mText = text;
        mListener = listener;
    }

    public SdlImage getSdlImage() {
        return mSdlImage;
    }

    public void setSdlImage(SdlImage sdlImage) {
        mSdlImage = sdlImage;
    }

    public String getText() {
        return mText;
    }

    public OnPressListener getListener() {
        return mListener;
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

    public boolean isGraphicOnly() {
        return isGraphicOnly;
    }

    public void setGraphicOnly(boolean graphicOnly) {
        isGraphicOnly = graphicOnly;
    }

    public boolean isHighlighted() { return isHighlighted; }

    public void setHighlighted(boolean highlighted) {isHighlighted = highlighted;}

    public SystemAction getSystemAction(){return mSystemAction;}

    public void setSystemAction(SystemAction systemAction){mSystemAction = systemAction;}
}
