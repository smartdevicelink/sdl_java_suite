package com.smartdevicelink.api.menu;

public class SdlMenuItem {

    private SelectListener mListener;
    private int mId;
    private String mText;

    public interface SelectListener{

        void onTouchSelect();

        void onVoiceSelect();

    }
}
