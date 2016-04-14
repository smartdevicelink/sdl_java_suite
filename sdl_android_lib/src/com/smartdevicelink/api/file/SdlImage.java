package com.smartdevicelink.api.file;

import java.io.File;
import java.net.URL;

public class SdlImage {

    private final String mSdlName;
    private final Integer mResId;
    private final URL mURL;
    private final File mPath;
    private final boolean isPersistent;
    private final boolean forceReplace;


    public SdlImage(String sdlName, Integer resId, boolean isPersistent, boolean forceReplace){
        this.mSdlName = sdlName;
        this.mResId = resId;
        this.mURL = null;
        this.mPath = null;
        this .isPersistent = isPersistent;
        this.forceReplace = forceReplace;
    }

    public SdlImage(String sdlName, URL url, boolean isPersistent, boolean forceReplace){
        this.mSdlName = sdlName;
        this.mResId = null;
        this.mURL = url;
        this.mPath = null;
        this .isPersistent = isPersistent;
        this.forceReplace = forceReplace;
    }

    public SdlImage(String sdlName, File path, boolean isPersistent, boolean forceReplace){
        this.mSdlName = sdlName;
        this.mResId = null;
        this.mURL = null;
        this.mPath = path;
        this .isPersistent = isPersistent;
        this.forceReplace = forceReplace;
    }

    public String getSdlName() {
        return mSdlName;
    }

    public Integer getResId() {
        return mResId;
    }

    public URL getURL() {
        return mURL;
    }

    public File getPath() {
        return mPath;
    }

    public boolean isPersistent() {
        return isPersistent;
    }

    public boolean isForceReplace() {
        return forceReplace;
    }
}
