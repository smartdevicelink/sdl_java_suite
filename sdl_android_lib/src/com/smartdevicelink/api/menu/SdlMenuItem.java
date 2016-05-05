package com.smartdevicelink.api.menu;

import com.smartdevicelink.api.file.SdlFile;
import com.smartdevicelink.api.file.SdlFileManager;
import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.enums.ImageType;

public class SdlMenuItem extends SdlMenuEntry{

    private SdlImage mSdlImage;

    public SdlMenuItem(SdlContext sdlContext, String name, SelectListener listener){
        super(sdlContext, name);
        mSdlContext.registerMenuCallback(mId, listener);
    }

    public void setImage(SdlImage image){
        mSdlImage = image;
        isChanged = true;
    }

    @Override
    void update() {
        if(isChanged) {
            if (isDisplayed) {
                sendDeleteCommand();
            }
            sendAddCommand();
            isDisplayed = true;
            isChanged = false;
        }
    }

    @Override
    void remove() {
        if(isDisplayed){
            sendDeleteCommand();
        }
        mSdlContext.unregisterButtonCallback(mId);
    }

    private void sendDeleteCommand() {
        DeleteCommand dc = new DeleteCommand();
        dc.setCmdID(mId);
        mSdlContext.sendRpc(dc);
    }

    private void sendAddCommand() {
        AddCommand ac = new AddCommand();
        ac.setCmdID(mId);
        if(mSdlImage != null){
            SdlFileManager fileManager = mSdlContext.getSdlFileManager();
            if(fileManager.isFileOnModule(mSdlImage.getSdlName())) {
                Image image = new Image();
                image.setImageType(ImageType.DYNAMIC);
                image.setValue(mSdlImage.getSdlName());
                ac.setCmdIcon(image);
            } else {
                fileManager.uploadSdlImage(mSdlImage, new SdlFileManager.FileReadyListener() {
                    @Override
                    public void onFileReady(SdlFile sdlFile) {
                        update();
                    }

                    @Override
                    public void onFileError(SdlFile sdlFile) {

                    }
                });
            }
        }
        MenuParams mp = new MenuParams();
        mp.setMenuName(mName);
        mp.setParentID(mRootMenu.getId());
        ac.setMenuParams(mp);
        mSdlContext.sendRpc(ac);
    }

    public interface SelectListener{

        void onTouchSelect();

        void onVoiceSelect();

    }
}
