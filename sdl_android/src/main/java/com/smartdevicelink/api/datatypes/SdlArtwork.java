package com.smartdevicelink.api.datatypes;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.rpc.enums.FileType;

/**
 * A class that extends SdlFile, representing artwork (JPEG, PNG, or BMP) to be uploaded to core
 */
public class SdlArtwork extends SdlFile {
    @Override
    public void setType(@NonNull FileType fileType) {
        if(fileType.equals(FileType.GRAPHIC_JPEG) || fileType.equals(FileType.GRAPHIC_PNG)
                || fileType.equals(FileType.GRAPHIC_BMP)){
            super.setType(fileType);
        }else{
            throw new IllegalArgumentException("Only JPEG, PNG, and BMP image types are supported.");
        }
    }
}