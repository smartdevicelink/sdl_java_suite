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

import android.support.annotation.NonNull;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.StaticIconName;

/**
 * A class representing data to be uploaded to core
 */
public class SdlFile{
    private String      fileName;
    private int         id = -1;
    private String      filePath;
    private byte[]      fileData;
    private FileType    fileType;
    private boolean     persistentFile;
    private boolean     isStaticIcon;

    public SdlFile(){}

    public SdlFile(@NonNull StaticIconName staticIconName){
        this.fileName = staticIconName.toString();
        this.fileData = staticIconName.toString().getBytes();
        this.persistentFile = false;
        this.isStaticIcon = true;
    }

    public SdlFile(@NonNull String fileName, @NonNull FileType fileType, int id, boolean persistentFile){
        this.fileName = fileName;
        this.fileType = fileType;
        this.id = id;
        this.persistentFile = persistentFile;
    }

    public SdlFile(@NonNull String fileName, @NonNull FileType fileType, byte[] data, boolean persistentFile){
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileData = data;
        this.persistentFile = persistentFile;
    }

    public SdlFile(@NonNull String fileName, @NonNull FileType fileType, String filePath, boolean persistentFile){
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.persistentFile = persistentFile;
    }

    public void setName(@NonNull String fileName){
        this.fileName = fileName;
    }
    public String getName(){
        return fileName;
    }

    public void setResourceId(int id){
        this.id = id;
    }
    public int getResourceId(){
        return id;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public String getFilePath(){
        return this.filePath;
    }

    public void setFileData(byte[] data){
        this.fileData = data;
    }
    public byte[] getFileData(){
        return fileData;
    }

    public void setType(@NonNull FileType fileType){
        this.fileType = fileType;
    }
    public FileType getType(){
        return fileType;
    }

    public void setPersistent(boolean persistentFile){
        this.persistentFile = persistentFile;
    }
    public boolean isPersistent(){
        return this.persistentFile;
    }

    public void setStaticIcon(boolean staticIcon) {
        isStaticIcon = staticIcon;
    }
    public boolean isStaticIcon() {
        return isStaticIcon;
    }
}