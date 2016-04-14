package com.smartdevicelink.api.file;

import java.io.File;
import java.net.URL;

public class SdlImage implements SdlFile {

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

    @Override
    public String getSdlName() {
        return mSdlName;
    }

    @Override
    public Integer getResId() {
        return mResId;
    }

    @Override
    public URL getURL() {
        return mURL;
    }

    @Override
    public File getPath() {
        return mPath;
    }

    @Override
    public boolean isPersistent() {
        return isPersistent;
    }

    @Override
    public boolean isForceReplace() {
        return forceReplace;
    }
}
