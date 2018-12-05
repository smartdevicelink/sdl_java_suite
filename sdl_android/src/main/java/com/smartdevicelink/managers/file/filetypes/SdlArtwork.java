package com.smartdevicelink.managers.file.filetypes;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.rpc.enums.FileType;

/**
 * A class that extends SdlFile, representing artwork (JPEG, PNG, or BMP) to be uploaded to core
 */
public class SdlArtwork extends SdlFile {
    private boolean isTemplate;

    public SdlArtwork(){}

    public SdlArtwork(@NonNull String fileName, @NonNull FileType fileType, int id, boolean persistentFile) {
        super(fileName, fileType, id, persistentFile);
    }

    public SdlArtwork(@NonNull String fileName, @NonNull FileType fileType, Uri uri, boolean persistentFile) {
        super(fileName, fileType, uri, persistentFile);
    }

    public SdlArtwork(@NonNull String fileName, @NonNull FileType fileType, byte[] data, boolean persistentFile) {
        super(fileName, fileType, data, persistentFile);
    }

    /**
     * Set whether this SdlArtwork is a template image whose coloring should be decided by the HMI
     * @param isTemplate boolean that tells whether this SdlArtwork is a template image
     */
    public void setTemplateImage(boolean isTemplate){
        this.isTemplate = isTemplate;
    }

    /**
     * Get whether this SdlArtwork is a template image whose coloring should be decided by the HMI
     * @return boolean that tells whether this SdlArtwork is a template image
     */
    public boolean isTemplateImage(){
        return isTemplate;
    }

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