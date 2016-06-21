package com.smartdevicelink.api.view;

/**
 * Created by mschwerz on 5/9/16.
 */
public class SdlManualInteraction {
    final ManualInteractionType mType;
    boolean mUseSearch;
    Integer mTimeout;
    private final static Integer MIN_VALUE= 5000;
    private final static Integer MAX_VALUE= 100000;

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
        if(duration<MIN_VALUE)
            mTimeout= MIN_VALUE;
        else if(duration<MAX_VALUE)
            mTimeout= duration;
        else
            mTimeout = MAX_VALUE;
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
