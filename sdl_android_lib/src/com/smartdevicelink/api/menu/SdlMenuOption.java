package com.smartdevicelink.api.menu;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.api.file.SdlFile;
import com.smartdevicelink.api.file.SdlFileManager;
import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

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

    public SdlMenuOption(int index, @NonNull String name, @NonNull SelectListener listener) {
        this(index, name, listener, null);
    }

    public SdlMenuOption(int index, @NonNull String name, @NonNull SelectListener listener, SdlImage image){
        super(name, index);
        mListener = listener;
        mSdlImage = image;
    }

    @Override
    void update(SdlContext sdlContext, int subMenuId) {
        if(isVisible) sendReplaceCommand(sdlContext, subMenuId);
        if(!isRegistered) registerSelectListener(sdlContext);
        sendAddCommand(sdlContext, subMenuId);
    }

    @Override
    void remove(SdlContext sdlContext) {
        if(isVisible) sendDeleteCommand(sdlContext, null);
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

    private void sendDeleteCommand(SdlContext sdlContext, OnRPCResponseListener listener) {
        DeleteCommand dc = new DeleteCommand();
        dc.setCmdID(mId);
        dc.setOnRPCResponseListener(listener);
        sdlContext.sendRpc(dc);
        isVisible = false;
    }

    private void sendReplaceCommand(final SdlContext sdlContext, final int subMenuId){
        sendDeleteCommand(sdlContext, new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(response != null && response.getSuccess()){
                    sendAddCommand(sdlContext, subMenuId);
                }
            }
        });
    }

    private void sendAddCommand(final SdlContext sdlContext, final int rootMenuId) {
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
                        if(isVisible) update(sdlContext, rootMenuId);
                    }

                    @Override
                    public void onFileError(SdlFile sdlFile) {
                    }
                });
            }
        }

        MenuParams mp = new MenuParams();
        mp.setMenuName(mName);
        if(rootMenuId > 0) mp.setParentID(rootMenuId);
        if(mIndex >= 0) mp.setPosition(mIndex);
        ac.setMenuParams(mp);

        sdlContext.sendRpc(ac);
        isVisible = true;
    }

    public interface SelectListener{

        void onSelect(TriggerSource triggerSource);

    }

}
