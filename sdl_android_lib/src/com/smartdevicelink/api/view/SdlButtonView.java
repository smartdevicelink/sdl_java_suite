package com.smartdevicelink.api.view;

import android.util.Log;

import com.smartdevicelink.api.SdlApplication;
import com.smartdevicelink.api.file.SdlFile;
import com.smartdevicelink.api.file.SdlFileManager;
import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SdlButtonView extends SdlView {

    private static final String TAG = SdlButtonView.class.getSimpleName();
    private boolean isTiles;
    private ArrayList<SdlButton> mSdlButtons = new ArrayList<>();
    private boolean containsBackButton = false;
    private HashMap<String, SdlButtonImageRecord> mImageStatusRegister;

    private final SdlButton BACK_BUTTON = new SdlButton("Back", null);

    public SdlButtonView(){
        BACK_BUTTON.setId(SdlApplication.BACK_BUTTON_ID);
        mImageStatusRegister = new HashMap<>();
    }

    public void setButtons(List<SdlButton> buttons){
        mSdlButtons = new ArrayList<>(buttons);
    }

    public List<SdlButton> getButtons(){
        return mSdlButtons;
    }

    public void addButton(SdlButton button){
        mSdlButtons.add(button);
        SdlImage image = button.getSdlImage();
        SdlFileManager fileManager = mSdlContext.getSdlFileManager();
        if(image != null){
            Log.d(TAG, "Adding " + image.getSdlName() + " to ImageStatusRegister");
            mImageStatusRegister.put(image.getSdlName(),
                    new SdlButtonImageRecord(image, fileManager.isFileOnModule(image.getSdlName())));
        }
        if(mViewManager != null){
            int id = mViewManager.registerButtonCallback(button.getListener());
            button.setId(id);
        }
    }

    public void removeButton(SdlButton button){
        mViewManager.unregisterButtonCallBack(button.getId());
        mSdlButtons.remove(button);
    }

    public void setIsTiles(boolean isTiles){
        this.isTiles = isTiles;
    }

    public boolean isTiles(){
        return isTiles;
    }

    public void includeBackButton(boolean isIncluded){
        if(!containsBackButton && isIncluded){
            mSdlButtons.add(0, BACK_BUTTON);
        } else if(containsBackButton && !isIncluded){
            mSdlButtons.remove(0);
        }
        containsBackButton = isIncluded;
    }

    public void setBackButtonIcon(SdlImage sdlImage){
        BACK_BUTTON.setSdlImage(sdlImage);
        if(sdlImage != null){
            SdlFileManager fileManager = mSdlContext.getSdlFileManager();
            mImageStatusRegister.put(sdlImage.getSdlName(), new SdlButtonImageRecord(sdlImage,
                    fileManager.isFileOnModule(sdlImage.getSdlName())));
        }
    }

    public void setBackButtonGraphicOnly(boolean graphicOnly){
        BACK_BUTTON.setGraphicOnly(graphicOnly);
    }

    @Override
    public void setSdlViewManager(SdlViewManager sdlViewManager) {
        if(mViewManager == null) {
            super.setSdlViewManager(sdlViewManager);
            for(SdlButton button: mSdlButtons){
                int id = mViewManager.registerButtonCallback(button.getListener());
                button.setId(id);
            }
        }
    }

    @Override
    public void decorate(Show show) {
        ArrayList<SoftButton> softButtons = new ArrayList<>();
        for(SdlButton button: mSdlButtons) {
            SoftButton softButton = new SoftButton();
            softButton.setSoftButtonID(button.getId());
            softButton.setSystemAction(SystemAction.DEFAULT_ACTION);
            SoftButtonType type = SoftButtonType.SBT_TEXT;
            softButton.setText(button.getText());

            SdlImage sdlImage = button.getSdlImage();
            if(sdlImage != null) {
                SdlButtonImageRecord bir = mImageStatusRegister.get(sdlImage.getSdlName());
                if (bir != null && bir.isReady) {
                    Image image = new Image();
                    image.setImageType(ImageType.DYNAMIC);
                    image.setValue(sdlImage.getSdlName());
                    softButton.setImage(image);
                    if(button.isGraphicOnly()){
                        type = SoftButtonType.SBT_IMAGE;
                    } else {
                        type = SoftButtonType.SBT_BOTH;
                    }
                }
            }

            softButton.setType(type);
            softButtons.add(softButton);
        }
        show.setSoftButtons(softButtons);
    }

    @Override
    public void clear() {
        mSdlButtons.clear();
    }

    @Override
    public void uploadRequiredImages() {
        SdlFileManager fileManager = mSdlContext.getSdlFileManager();
        for(SdlButton button: mSdlButtons){
            SdlImage image = button.getSdlImage();
            if(image != null && !mImageStatusRegister.get(image.getSdlName()).isReady){
                fileManager.uploadSdlImage(image, mFileReadyListener);
            }
        }
    }

    private SdlFileManager.FileReadyListener mFileReadyListener = new SdlFileManager.FileReadyListener() {
        @Override
        public void onFileReady(SdlFile sdlFile) {
            Log.d(TAG, "Graphic " + sdlFile.getSdlName() + " ready.");
            mImageStatusRegister.get(sdlFile.getSdlName()).isReady = true;
            if(isVisible) {
                redraw();
            }
        }

        @Override
        public void onFileError(SdlFile sdlFile) {

        }
    };

    private class SdlButtonImageRecord{
        final SdlImage sdlImage;
        boolean isReady;

        SdlButtonImageRecord(SdlImage image, boolean isReady){
            this.sdlImage = image;
            this.isReady = isReady;
        }
    }
}
