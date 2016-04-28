package com.smartdevicelink.api.view;

import com.smartdevicelink.api.SdlApplication;
import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;

import java.util.ArrayList;
import java.util.List;

public class SdlButtonView extends SdlView {

    private boolean isTiles;
    private ArrayList<SdlButton> mSdlButtons = new ArrayList<>();
    private boolean containsBackButton = false;

    private static final SdlButton BACK_BUTTON = new SdlButton();
    static{
        BACK_BUTTON.setId(SdlApplication.BACK_BUTTON_ID);
        BACK_BUTTON.setText("Back");
    }

    public void setButtons(List<SdlButton> buttons){
        mSdlButtons = new ArrayList<>(buttons);
    }

    public List<SdlButton> getButtons(){
        return mSdlButtons;
    }

    public void addButton(SdlButton button){
        int id = mViewManager.registerButtonCallback(button.getListener());
        button.setId(id);
        mSdlButtons.add(button);
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

    @Override
    public void decorate(Show show) {
        ArrayList<SoftButton> softButtons = new ArrayList<>();
        for(SdlButton button: mSdlButtons) {
            SoftButton softButton = new SoftButton();
            softButton.setSoftButtonID(button.getId());
            softButton.setSystemAction(SystemAction.DEFAULT_ACTION);
            SoftButtonType type = SoftButtonType.SBT_BOTH;
            if(button.getText() != null){
                softButton.setText(button.getText());
            } else {
                type = SoftButtonType.SBT_IMAGE;
            }

            if(button.getSdlImage() != null){
                Image image = new Image();
                image.setImageType(ImageType.DYNAMIC);
                image.setValue(button.getSdlImage().getSdlName());
                softButton.setImage(image);
            } else {
                type = SoftButtonType.SBT_TEXT;
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
    public List<SdlImage> getRequiredImages() {
        ArrayList<SdlImage> images = new ArrayList<>();
        for(SdlButton button: mSdlButtons){
            if(button.getSdlImage() != null){
                images.add(button.getSdlImage());
            }
        }
        return images;
    }
}
