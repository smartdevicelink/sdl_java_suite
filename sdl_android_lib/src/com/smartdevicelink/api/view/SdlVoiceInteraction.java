package com.smartdevicelink.api.view;

import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mschwerz on 5/9/16.
 */
public class SdlVoiceInteraction {
    TTSChunk mTimeoutPrompt;
    List<SdlVoiceHelpItem> mVrHelpItems = new ArrayList<>();

    public SdlVoiceInteraction() {

    }

    SdlVoiceInteraction copy() {
        SdlVoiceInteraction inter = new SdlVoiceInteraction();
        inter.mTimeoutPrompt = mTimeoutPrompt;
        inter.mVrHelpItems = new ArrayList<>(mVrHelpItems);
        return inter;
    }

    public void addVrHelpItem(String text, SdlImage image) {
        mVrHelpItems.add(new SdlVoiceHelpItem(text, image));
    }

    public void addVrHelpItem(SdlVoiceHelpItem item) {
        mVrHelpItems.add(item);
    }

    public void setVrHelpItems(List<SdlVoiceHelpItem> items) {
        mVrHelpItems = items;
    }

    public void setTimeoutPrompt(TTSChunk timeoutPrompt) {
        mTimeoutPrompt = timeoutPrompt;
    }

    TTSChunk getTimeoutPrompt(){return mTimeoutPrompt;}

    List<SdlVoiceHelpItem> getVrHelpItems(){return mVrHelpItems;}

    public void setTimeoutPrompt(String timeoutText) {
        TTSChunk chunk = new TTSChunk();
        chunk.setText(timeoutText);
        chunk.setType(SpeechCapabilities.TEXT);
        mTimeoutPrompt = chunk;
    }

    public static class SdlVoiceHelpItem {
        private final String mDisplayText;
        private final SdlImage mSdlImage;

        public SdlVoiceHelpItem(String displayText, SdlImage image) {
            mDisplayText = displayText;
            mSdlImage = image;
        }

        public SdlImage getSdlImage(){return mSdlImage;}
        public String getDisplayText(){return mDisplayText;}
    }


}
