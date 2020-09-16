/*
 * Copyright (c) 2019 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.managers.file.filetypes;

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.StaticIconName;
import com.smartdevicelink.util.DebugTool;

import java.net.URI;

/**
 * A class that extends SdlFile, representing artwork (JPEG, PNG, or BMP) to be uploaded to core
 */
public class SdlArtwork extends SdlFile implements Cloneable{
    private boolean isTemplate;
    private Image imageRPC;

    /**
     * Creates a new instance of SdlArtwork
     */
    public SdlArtwork(){}

    /**
     * Creates a new instance of SdlArtwork
     * @param fileName a String value representing the name that will be used to store the file in the head unit. You can pass null if you want the library to auto generate the name
     * @param fileType a FileType enum value representing the type of the file
     * @param filePath a String value representing the the location of the file
     * @param persistentFile a boolean value that indicates if the file is meant to persist between sessions / ignition cycles
     */
    public SdlArtwork(String fileName, @NonNull FileType fileType, String filePath, boolean persistentFile) {
        super(fileName, fileType, filePath, persistentFile);
    }

    /**
     * Creates a new instance of SdlArtwork
     * @param fileName a String value representing the name that will be used to store the file in the head unit. You can pass null if you want the library to auto generate the name
     * @param fileType a FileType enum value representing the type of the file
     * @param uri a URI value representing a file's location. Currently, it only supports local files
     * @param persistentFile a boolean value that indicates if the file is meant to persist between sessions / ignition cycles
     */
    public SdlArtwork(String fileName, @NonNull FileType fileType, URI uri, boolean persistentFile) {
        super(fileName, fileType, uri, persistentFile);
    }

    /**
     * Creates a new instance of SdlArtwork
     * @param fileName a String value representing the name that will be used to store the file in the head unit. You can pass null if you want the library to auto generate the name
     * @param fileType a FileType enum value representing the type of the file
     * @param data a byte array representing the data of the file
     * @param persistentFile a boolean value that indicates if the file is meant to persist between sessions / ignition cycles
     */
    public SdlArtwork(String fileName, @NonNull FileType fileType, byte[] data, boolean persistentFile) {
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
        if(fileType == null || fileType.equals(FileType.GRAPHIC_JPEG) || fileType.equals(FileType.GRAPHIC_PNG)
                || fileType.equals(FileType.GRAPHIC_BMP)){
            super.setType(fileType);
        }else{
            throw new IllegalArgumentException("Only JPEG, PNG, and BMP image types are supported.");
        }
    }

    /**
     * Gets the Image RPC representing this artwork. Generally for use internally, you should instead pass an artwork to a Screen Manager method
     * @return The Image RPC representing this artwork.
     */
    public Image getImageRPC() {
        if (imageRPC == null) {
            imageRPC = createImageRPC();
        }
        return imageRPC;
    }

    private Image createImageRPC(){
        Image image;
        if (isStaticIcon()) {
            image = new Image(getName(), ImageType.STATIC);
            image.setIsTemplate(true);
        } else {
            image = new Image(getName(), ImageType.DYNAMIC);
            image.setIsTemplate(isTemplate);
        }
        return image;
    }

    /**
     * Creates a deep copy of the object
     * @return deep copy of the object
     */
    @Override
    public SdlArtwork clone() {
        try{
            SdlArtwork artwork = (SdlArtwork) super.clone();
            if(artwork != null){
                artwork.imageRPC = artwork.createImageRPC();
            }
            return artwork;
        } catch (CloneNotSupportedException e) {
            if(DebugTool.isDebugEnabled()){
                throw new RuntimeException("Clone not supported by super class");
            }
        }
        return null;
    }
}