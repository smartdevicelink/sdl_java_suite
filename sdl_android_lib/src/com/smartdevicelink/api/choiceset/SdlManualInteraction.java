package com.smartdevicelink.api.choiceset;

/**
 * Created by mschwerz on 5/9/16.
 */
public class SdlManualInteraction {
    final ManualInteractionType mType;
    boolean mUseSearch;
    Integer mTimeout;

    public SdlManualInteraction(ManualInteractionType type) {
        mType = type;
    }

    SdlManualInteraction copy() {
        SdlManualInteraction inter = new SdlManualInteraction(mType);
        inter.mUseSearch = mUseSearch;
        inter.mTimeout = mTimeout;
        return inter;
    }

    public void setUseSearch(boolean useSearch) {
        mUseSearch = useSearch;
    }

    public void setDuration(Integer duration) {
        mTimeout = duration;
    }

    ManualInteractionType getType() {
        return mType;
    }

    boolean isUseSearch() {
        return mUseSearch;
    }

    Integer getTimeout() {
        return mTimeout;
    }

    public enum ManualInteractionType {
        Icon,
        List,
        Search_Only
    }

}
