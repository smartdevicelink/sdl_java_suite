package com.smartdevicelink.api.view;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.interfaces.SdlInteractionResponseListener;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by mschwerz on 6/20/16.
 */
class SdlInteractionButtonManager {

    private Collection<SdlButton> mButtons;
    private boolean mIsButtonPressed= false;


    public SdlInteractionButtonManager(Collection<SdlButton> buttons){
        mButtons= buttons;
    }


    protected ArrayList<SoftButton> registerAllButtons(final SdlContext context) throws SdlImageNotReadyException{
        ArrayList<SoftButton> createdSoftbuttons = new ArrayList<>();
        if (this.mButtons == null) {
            //should be ok since the user didn't provide buttons for this alert
            return null;
        }
        for (final SdlButton button : this.mButtons) {
            if (button.getListener() != null) {
                SoftButton softButtonFromSdlButton = new SoftButton();
                if(button.getText()!=null){
                    softButtonFromSdlButton.setText(button.getText());
                    softButtonFromSdlButton.setType(SoftButtonType.SBT_TEXT);
                }
                softButtonFromSdlButton.setIsHighlighted(button.isHighlighted());
                softButtonFromSdlButton.setSystemAction(button.getSystemAction());
                if(button.getSdlImage()!=null){
                    if(context.getSdlFileManager().isFileOnModule(button.getSdlImage().getSdlName())){
                        Image image = new Image();
                        image.setValue(button.getSdlImage().getSdlName());
                        image.setImageType(ImageType.DYNAMIC);
                        softButtonFromSdlButton.setImage(image);
                        softButtonFromSdlButton.setType(SoftButtonType.SBT_IMAGE);
                    }else{
                        //unregistering the buttons since we have an SdlButton that has an image
                        //that has not been uploaded to the module
                        unregisterAllButtons(context);
                        //The image with the SdlButton was not ready, therefore fail until
                        //the user provides an uploaded image
                        throw new SdlImageNotReadyException();
                    }
                }
                int buttonID = context.registerButtonCallback(new SdlButton.OnPressListener() {
                    @Override
                    public void onButtonPress() {
                        unregisterAllButtons(context);
                        mIsButtonPressed=true;
                        button.getListener().onButtonPress();
                    }
                });
                button.setId(buttonID);
                softButtonFromSdlButton.setSoftButtonID(buttonID);
                createdSoftbuttons.add(softButtonFromSdlButton);
            }
        }
        return createdSoftbuttons;
    }

    protected void unregisterAllButtons(SdlContext context){
        if (context == null) {
            return;
        }
        for (SdlButton button : mButtons) {
            context.unregisterButtonCallback(button.getId());
        }
    }

    public SdlInteractionResponseListener getCleanUpListener(SdlContext context, SdlInteractionResponseListener listener){
        return new SdlButtonCleanUpListener(context,listener);
    }

    public boolean getButtonIsPressed(){return  mIsButtonPressed;}

    private class SdlButtonCleanUpListener implements SdlInteractionResponseListener{
        final SdlContext mContext;
        final SdlInteractionResponseListener mClientListener;

        SdlButtonCleanUpListener(SdlContext context, SdlInteractionResponseListener clientListener) {
            mContext= context.getSdlApplicationContext();
            mClientListener= clientListener;
        }

        @Override
        public void onSuccess() {
            unregisterAllButtons(mContext);
            if(mClientListener!=null){
                if(!getButtonIsPressed())
                    mClientListener.onTimeout();
                else
                    mClientListener.onSuccess();
            }
            mIsButtonPressed= false;
        }

        @Override
        public void onError() {
            unregisterAllButtons(mContext);
            if(mClientListener!=null)
                mClientListener.onError();
            mIsButtonPressed= false;
        }

        @Override
        public void onTimeout() {
            unregisterAllButtons(mContext);
            if(mClientListener!=null)
                mClientListener.onTimeout();
            mIsButtonPressed= false;
        }

        @Override
        public void onAborted() {
            unregisterAllButtons(mContext);
            if(mClientListener!=null)
                mClientListener.onAborted();
            mIsButtonPressed= false;
        }

    }

    public class SdlImageNotReadyException extends RuntimeException{

    }
}
