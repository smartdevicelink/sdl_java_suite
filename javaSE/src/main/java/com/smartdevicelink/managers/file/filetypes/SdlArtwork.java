package com.smartdevicelink.managers.file.filetypes;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.StaticIconName;

/**
 * A class that extends SdlFile, representing artwork (JPEG, PNG, or BMP) to be uploaded to core
 */
public class SdlArtwork extends SdlFile {
    private boolean isTemplate;
    private Image imageRPC;

    public SdlArtwork(){}

    public SdlArtwork(@NonNull StaticIconName staticIconName) {
        super(staticIconName);
    }

    public SdlArtwork(@NonNull String fileName, @NonNull FileType fileType, int id, boolean persistentFile) {
        super(fileName, fileType, id, persistentFile);
    }

    public SdlArtwork(@NonNull String fileName, @NonNull FileType fileType, String filePath, boolean persistentFile) {
        super(fileName, fileType, filePath, persistentFile);
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

    /**
     * Get the Image RPC representing this artwork. Generally for use internally, you should instead pass an artwork to a Screen Manager method.
     * @return The Image RPC representing this artwork.
     */
    public Image getImageRPC() {
        if (imageRPC == null) {
            if (isStaticIcon()) {
                imageRPC = new Image(getName(), ImageType.STATIC);
                imageRPC.setIsTemplate(true);
            } else {
                imageRPC = new Image(getName(), ImageType.DYNAMIC);
                imageRPC.setIsTemplate(isTemplate);
            }
        }
        return imageRPC;
    }
}