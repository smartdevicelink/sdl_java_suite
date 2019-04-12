package com.smartdevicelink.managers.file.filetypes;

import android.net.Uri;
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

    /**
     * Creates a new instance of SdlArtwork
     * @param fileName a String value representing the name that will be used to store the file in the head unit
     * @param fileType a FileType enum value representing the type of the file
     * @param id an int value representing the android resource id of the file
     * @param persistentFile a boolean value that indicates if the file is meant to persist between sessions / ignition cycles
     */
    public SdlArtwork(@NonNull String fileName, @NonNull FileType fileType, int id, boolean persistentFile) {
        super(fileName, fileType, id, persistentFile);
    }

    /**
     * Creates a new instance of SdlArtwork
     * @param fileName a String value representing the name that will be used to store the file in the head unit
     * @param fileType a FileType enum value representing the type of the file
     * @param uri a URI value representing a file's location. Currently, it only supports local files.
     * @param persistentFile a boolean value that indicates if the file is meant to persist between sessions / ignition cycles
     */
    public SdlArtwork(@NonNull String fileName, @NonNull FileType fileType, Uri uri, boolean persistentFile) {
        super(fileName, fileType, uri, persistentFile);
    }

    /**
     * Creates a new instance of SdlArtwork
     * @param fileName a String value representing the name that will be used to store the file in the head unit
     * @param fileType a FileType enum value representing the type of the file
     * @param data a byte array representing the data of the file
     * @param persistentFile a boolean value that indicates if the file is meant to persist between sessions / ignition cycles
     */
    public SdlArtwork(@NonNull String fileName, @NonNull FileType fileType, byte[] data, boolean persistentFile) {
        super(fileName, fileType, data, persistentFile);
    }

    /**
     * Creates a new instance of SdlArtwork
     * @param staticIconName a StaticIconName enum value representing the name of a static file that comes pre-shipped with the head unit
     */
    public SdlArtwork(@NonNull StaticIconName staticIconName) {
        super(staticIconName);
    }

    /**
     * Sets whether this SdlArtwork is a template image whose coloring should be decided by the HMI
     * @param isTemplate boolean that tells whether this SdlArtwork is a template image
     */
    public void setTemplateImage(boolean isTemplate){
        this.isTemplate = isTemplate;
    }

    /**
     * Gets whether this SdlArtwork is a template image whose coloring should be decided by the HMI
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
     * Gets the Image RPC representing this artwork. Generally for use internally, you should instead pass an artwork to a Screen Manager method.
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