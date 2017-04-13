package com.smartdevicelink.api.menu;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.api.file.SdlFile;
import com.smartdevicelink.api.file.SdlFileManager;
import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.enums.ImageType;

public class SdlMenuOption extends SdlMenuItem {

    private static final String TAG = SdlMenuOption.class.getSimpleName();

    private SdlImage mSdlImage;
    private final SdlSyncCommand mCommand;

    public SdlMenuOption(@NonNull String name, @NonNull SelectListener listener) {
        this(name, listener, null);
    }

    public SdlMenuOption(@NonNull String name, @NonNull SelectListener listener, SdlImage image){
        super(name);
        mSdlImage = image;
        mCommand = new SdlSyncCommand(listener);
    }

    public SdlMenuOption(int index, @NonNull String name, @NonNull SelectListener listener) {
        this(index, name, listener, null);
    }

    public SdlMenuOption(int index, @NonNull String name, @NonNull SelectListener listener, SdlImage image){
        super(name, index);
        mSdlImage = image;
        mCommand = new SdlSyncCommand(listener);
    }

    @Override
    void update(SdlContext sdlContext, int subMenuId) {
        mCommand.update(getId(), sdlContext, generateAddCommand(sdlContext,subMenuId));
    }

    @Override
    void remove(SdlContext sdlContext) {
        mCommand.remove(getId(), sdlContext);
    }

    @Override
    void registerSelectListener(SdlContext sdlContext) {
        mCommand.registerSelectListener(getId(), sdlContext);
    }

    @Override
    void unregisterSelectListener(SdlContext sdlContext) {
        mCommand.unregisterSelectListener(getId(), sdlContext);
    }

    private AddCommand generateAddCommand(final SdlContext sdlContext, final int rootMenuId) {
        AddCommand ac = new AddCommand();
        ac.setCmdID(getId());

        if(mSdlImage != null){
            Log.d(TAG, "Image is set for command: " + getName());
            SdlFileManager fileManager = sdlContext.getSdlFileManager();
            if(fileManager.isFileOnModule(mSdlImage.getSdlName())) {
                Image image = new Image();
                image.setImageType(ImageType.DYNAMIC);
                image.setValue(mSdlImage.getSdlName());
                ac.setCmdIcon(image);
            } else {
                fileManager.uploadSdlImage(mSdlImage, new SdlFileManager.FileReadyListener() {
                    @Override
                    public void onFileReady(SdlFile sdlFile) {
                        mCommand.imagePrepared(getId(), sdlContext, generateAddCommand(sdlContext,rootMenuId));
                    }

                    @Override
                    public void onFileError(SdlFile sdlFile) {
                    }
                });
            }
        }

        MenuParams mp = new MenuParams();
        mp.setMenuName(getName());
        if(rootMenuId > 0) mp.setParentID(rootMenuId);
        if(mIndex >= 0) mp.setPosition(mIndex);
        ac.setMenuParams(mp);
        return ac;
    }

}
