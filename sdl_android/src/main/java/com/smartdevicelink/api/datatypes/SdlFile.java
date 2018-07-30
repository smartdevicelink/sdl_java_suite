package com.smartdevicelink.api.datatypes;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.rpc.enums.FileType;

/**
 * A class representing data to be uploaded to core
 */
public class SdlFile{
    private String      fileName;
    private int         id = -1;
    private Uri         uri;
    private byte[]      fileData;
    private FileType    fileType;
    private boolean     persistentFile;

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

    public void setUri(Uri uri){
        this.uri = uri;
    }
    public Uri getUri(){
        return uri;
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
}