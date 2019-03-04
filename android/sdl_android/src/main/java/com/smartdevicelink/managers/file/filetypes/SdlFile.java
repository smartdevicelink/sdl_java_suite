package com.smartdevicelink.managers.file.filetypes;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.StaticIconName;

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
    private boolean     isStaticIcon;


    public SdlFile(){}

    public SdlFile(@NonNull String fileName, @NonNull FileType fileType, int id, boolean persistentFile){
        this.fileName = fileName;
        this.fileType = fileType;
        this.id = id;
        this.persistentFile = persistentFile;
    }

    public SdlFile(@NonNull String fileName, @NonNull FileType fileType, Uri uri, boolean persistentFile){
        this.fileName = fileName;
        this.fileType = fileType;
        this.uri = uri;
        this.persistentFile = persistentFile;
    }

    public SdlFile(@NonNull String fileName, @NonNull FileType fileType, byte[] data, boolean persistentFile){
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileData = data;
        this.persistentFile = persistentFile;
    }

    public SdlFile(@NonNull StaticIconName staticIconName){
        this.fileName = staticIconName.toString();
        this.fileData = staticIconName.toString().getBytes();
        this.persistentFile = false;
        this.isStaticIcon = true;
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

    public void setStaticIcon(boolean staticIcon) {
        isStaticIcon = staticIcon;
    }
    public boolean isStaticIcon() {
        return isStaticIcon;
    }
}