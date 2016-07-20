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

    public SdlMenuOption(@NonNull String name, @NonNull SelectListener listener) {
        this(name, listener, null, -1);
    }

    public SdlMenuOption(@NonNull String name, @NonNull SelectListener listener, Integer index) {
        this(name, listener, null, index);
    }

    public SdlMenuOption(@NonNull String name, @NonNull SelectListener listener, SdlImage image){
        this(name, listener, image, -1);
    }

    public SdlMenuOption(@NonNull String name, @NonNull SelectListener listener, SdlImage image, Integer index){
        super(name);
        mListener = listener;
        mSdlImage = image;
    }

    @Override
    void update(SdlContext sdlContext, int index) {

    }

    @Override
    void remove(SdlContext sdlContext) {

    }

    private void sendDeleteCommand(SdlContext sdlContext) {
        DeleteCommand dc = new DeleteCommand();
        dc.setCmdID(mId);
        sdlContext.sendRpc(dc);
    }

    private void sendAddCommand(final SdlContext sdlContext, final SdlMenu rootMenu, final int index) {
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
                        update(sdlContext, index);
                    }

                    @Override
                    public void onFileError(SdlFile sdlFile) {

                    }
                });
            }
        }
        MenuParams mp = new MenuParams();
        mp.setMenuName(mName);
        mp.setParentID(rootMenu.getId());
        mp.setPosition(index);
        ac.setMenuParams(mp);
        sdlContext.sendRpc(ac);
    }

    public interface SelectListener{

        void onSelect(TriggerSource triggerSource);

    }
}
