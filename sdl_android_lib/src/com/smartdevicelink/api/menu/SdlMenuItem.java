package com.smartdevicelink.api.menu;

import android.util.Log;

import com.smartdevicelink.api.file.SdlFile;
import com.smartdevicelink.api.file.SdlFileManager;
import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

public class SdlMenuItem extends SdlMenuEntry{

    private static final String TAG = SdlMenuItem.class.getSimpleName();

    private SdlImage mSdlImage;
    private SelectListener mListener;

    public SdlMenuItem(String name, SelectListener listener){
        super(name);
        mListener = listener;

    }

    public void setImage(SdlImage image){
        mSdlImage = image;
        isChanged = true;
        update();
    }

    SelectListener getListener() {
        return mListener;
    }

    @Override
    void update() {
        if(isChanged && mRootMenu != null) {
            if (isDisplayed) {
                isDisplayed = !sendDeleteCommand();
            } else {
                isDisplayed = sendAddCommand();
                isChanged = !isDisplayed;
            }
        }
    }

    @Override
    void remove() {
        if(isDisplayed){
            isDisplayed = !sendDeleteCommand();
        }
    }

    private boolean sendDeleteCommand() {
        if(mSdlContext != null) {
            DeleteCommand dc = new DeleteCommand();
            dc.setCmdID(mId);
            dc.setOnRPCResponseListener(mResponseListener);
            mSdlContext.sendRpc(dc);
            return true;
        } else {
            return false;
        }
    }

    private boolean sendAddCommand() {
        if(mSdlContext != null){
            AddCommand ac = new AddCommand();
            ac.setCmdID(mId);
            if(mSdlImage != null){
                Log.d(TAG, "Image is set for command: " + mName);
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
                            isChanged = true;
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
            ac.setOnRPCResponseListener(mResponseListener);
            mSdlContext.sendRpc(ac);
            return true;
        } else {
            return false;
        }
    }

    private OnRPCResponseListener mResponseListener = new OnRPCResponseListener() {
        @Override
        public void onResponse(int correlationId, RPCResponse response) {
            update();
        }
    };

    public interface SelectListener{

        void onTouchSelect();

        void onVoiceSelect();

    }
}
