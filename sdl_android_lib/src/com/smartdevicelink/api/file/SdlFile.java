package com.smartdevicelink.api.file;

import java.io.File;
import java.net.URL;

public interface SdlFile {

    String getSdlName();
    Integer getResId();
    URL getURL();
    File getPath();
    boolean isPersistent();
    boolean isForceReplace();

}
