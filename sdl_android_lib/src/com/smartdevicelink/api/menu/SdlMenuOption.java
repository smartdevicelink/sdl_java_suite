package com.smartdevicelink.api.menu;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.api.file.SdlFile;
import com.smartdevicelink.api.file.SdlFileManager;
import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;

public class SdlMenuOption extends SdlMenuItem {

    private static final String TAG = SdlMenuOption.class.getSimpleName();

    private SdlImage mSdlImage;
    private SelectListener mListener;
    private boolean isVisible;
    private boolean isRegistered;

    public SdlMenuOption(@NonNull String name, @NonNull SelectListener listener) {
        this(name, listener, null);
    }

    public SdlMenuOption(@NonNull String name, @NonNull SelectListener listener, SdlImage image){
        super(name);
        mListener = listener;
        mSdlImage = image;
    }

    @Override
    void update(SdlContext sdlContext, int subMenuId, int index) {
        if(isVisible) sendDeleteCommand(sdlContext);
        if(!isRegistered) registerSelectListener(sdlContext);
        sendAddCommand(sdlContext, subMenuId, index);
    }

    @Override
    void remove(SdlContext sdlContext) {
        if(isVisible) sendDeleteCommand(sdlContext);
        if(isRegistered) unregisterSelectListener(sdlContext);
    }

    @Override
    void registerSelectListener(SdlContext sdlContext) {
        if(!isRegistered) {
            sdlContext.registerMenuCallback(mId, mListener);
            isRegistered = true;
        }
    }

    @Override
    void unregisterSelectListener(SdlContext sdlContext) {
        if(isRegistered) {
            sdlContext.unregisterMenuCallback(mId);
            isRegistered = false;
        }
    }

    private void sendDeleteCommand(SdlContext sdlContext) {
        DeleteCommand dc = new DeleteCommand();
        dc.setCmdID(mId);
        sdlContext.sendRpc(dc);
        isVisible = false;
    }

    private void sendAddCommand(final SdlContext sdlContext, final int rootMenuId, final int index) {
        AddCommand ac = new AddCommand();
        ac.setCmdID(mId);

        if(mSdlImage != null){
            Log.d(TAG, "Image is set for command: " + mName);
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
                        if(isVisible) update(sdlContext, rootMenuId, index);
                    }

                    @Override
                    public void onFileError(SdlFile sdlFile) {
                    }
                });
            }
        }

        MenuParams mp = new MenuParams();
        mp.setMenuName(mName);
        mp.setParentID(rootMenuId);
        mp.setPosition(index);
        ac.setMenuParams(mp);

        sdlContext.sendRpc(ac);
        isVisible = true;
    }

    public interface SelectListener{

        void onSelect(TriggerSource triggerSource);

    }

}
