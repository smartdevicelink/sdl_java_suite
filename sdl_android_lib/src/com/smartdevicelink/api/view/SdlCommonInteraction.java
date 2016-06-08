package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.api.permission.SdlPermissionManager;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mschwerz on 5/3/16.
 */
abstract class SdlCommonInteraction {

    protected String[] mTextFields= new String[3];
    protected int mDuration;
    protected final Collection<SdlButton> mButtons;
    protected boolean mIsPending= false;
    protected boolean mIsButtonPressed= false;
    protected InteractionListener mListener;

    protected SdlCommonInteraction(Builder builder) {

        this.mTextFields=builder.mTextFields;
        this.mDuration= builder.mDuration;
        this.mButtons= builder.mButtons;
        this.mListener = builder.mListener;
    }

    protected abstract @NonNull
    IRPCRequestWithButtons getSettableButtonRPCMessage();

    /**
     * Method to send the built {@link SdlCommonInteraction} to the module, while the app is in the foreground. If there is a {@link SdlCommonInteraction.InteractionListener}
     * set to {@link SdlCommonInteraction}, then the listener will be informed if the dialog fails, is cancelled or if
     * the interaction is able to be completed normally.
     * @param context The SdlContext that the SdlCommonInteraction will be sent from
     */
    public boolean send(@NonNull SdlContext context){
        if(verifyRPCCanbeSent(context) && !mIsPending){
            mIsButtonPressed=false;
            final SdlContext applicationContext= context.getSdlApplicationContext();
            IRPCRequestWithButtons buttonMessage= getSettableButtonRPCMessage();
            try {
                List<SoftButton> buttons= registerAllButtons(applicationContext);
                if(buttons!=null)
                    buttonMessage.setButtons(buttons);
            }catch (SdlButtonRegistrationException e){
                return false;
            }
            RPCRequest request= buttonMessage.createRequest();
            request.setOnRPCResponseListener(new OnRPCResponseListener() {
                @Override
                public void onError(int correlationId, Result resultCode, String info) {
                    super.onError(correlationId, resultCode, info);
                    handleResultResponse(resultCode, info, applicationContext);
                }

                @Override
                public void onResponse(int correlationId, RPCResponse response) {
                    handleResultResponse(response.getResultCode(), response.getInfo(), applicationContext);
                }
            });
            context.sendRpc(request);
            mIsPending=true;
            return true;
        }else{
            return false;
        }
    }

    protected void handleResultResponse(Result response, String info, SdlContext context) {
        InteractionListener cleanUpListener= getCleanUpListener(context);
        switch (response) {
            case SUCCESS:
                if(!mIsButtonPressed){
                    cleanUpListener.onTimeout();
                }
                break;
            case ABORTED:
                cleanUpListener.onAborted();
                break;
            case INVALID_DATA:
                cleanUpListener.onError(InteractionListener.ErrorResponses.MALFORMED_INTERACTION, info);
                break;
            case DISALLOWED:
                cleanUpListener.onError(InteractionListener.ErrorResponses.PERMISSIONS_ERROR, info);
                break;
            case REJECTED:
                cleanUpListener.onError(InteractionListener.ErrorResponses.REJECTED, info);
                break;
            default:
                cleanUpListener.onError(InteractionListener.ErrorResponses.GENERIC_ERROR, info);
                break;
        }
        mIsPending = false;
    }

    protected InteractionListener getCleanUpListener(final SdlContext context){
        return new InteractionListener() {
            @Override
            public void onTimeout() {
                unregisterAllButtons(mButtons, context);
                if(mListener!=null)
                    mListener.onTimeout();
            }

            @Override
            public void onAborted() {
                unregisterAllButtons(mButtons, context);
                if(mListener!=null)
                    mListener.onAborted();
            }

            @Override
            public void onError(ErrorResponses responses, String moreInfo) {
                unregisterAllButtons(mButtons, context);
                if(mListener!=null)
                    mListener.onError(responses,moreInfo);
            }
        };
    }

    protected List<SoftButton> registerAllButtons(final SdlContext context) throws SdlButtonRegistrationException{
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
                softButtonFromSdlButton.setIsHighlighted(false);
                softButtonFromSdlButton.setSystemAction(SystemAction.DEFAULT_ACTION);
                if(button.getSdlImage()!=null){
                    if(context.getSdlFileManager().isFileOnModule(button.getSdlImage().getSdlName())){
                        Image image = new Image();
                        image.setValue(button.getSdlImage().getSdlName());
                        image.setImageType(ImageType.DYNAMIC);
                        softButtonFromSdlButton.setImage(image);
                        softButtonFromSdlButton.setType(SoftButtonType.SBT_IMAGE);
                    }else{
                        //The image with the SdlButton was not ready, therefore fail until
                        //the user provides an uploaded image
                        throw new SdlButtonRegistrationException("Image for button is not uploaded to the module");
                    }
                }
                int buttonID = context.registerButtonCallback(new SdlButton.OnPressListener() {
                    @Override
                    public void onButtonPress() {
                        unregisterAllButtons(mButtons,context);
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

    protected abstract @NonNull SdlPermission getRequiredPermission();

    protected boolean verifyRPCCanbeSent(SdlContext context){
        SdlPermissionManager checkPermissions = context.getSdlPermissionManager();
        return checkPermissions.isPermissionAvailable(getRequiredPermission());
    }


    protected void unregisterAllButtons(Collection<SdlButton> ids, SdlContext context){
        if (ids == null || context == null) {
            return;
        }
        for (SdlButton button : ids) {
            context.unregisterButtonCallback(button.getId());
        }
    }

    private class SdlButtonRegistrationException extends RuntimeException{
        SdlButtonRegistrationException(String detailMessage){super(detailMessage);}
    }

    protected abstract static class Builder <T extends Builder<T>> {

        protected String[] mTextFields= new String[3];
        protected int mDuration = getDefaultDuration();
        protected Collection<SdlButton> mButtons;
        protected InteractionListener mListener;

        public Builder(){

        }

        protected abstract int getMaxDuration();
        protected abstract int getMinDuration();
        protected abstract int getDefaultDuration();


        protected abstract T grabBuilder();

        public abstract T setText(String text);

        /**
         * Sets the duration that the {@link SdlCommonInteraction} will show up for.
         * The min value is 3000 and the max value is 10000
         * @param duration The amount of milliseconds the SdlCommonAlert should appear
         * @return The builder for the {@link SdlCommonInteraction}
         */
        public T setDuration(int duration){
            if(duration < getMinDuration()) {
                mDuration = getMinDuration();
            } else if(duration < getMaxDuration()){
                mDuration = duration;
            } else {
                mDuration = getMaxDuration();
            }
            return grabBuilder();
        }


        /**
         * Sets the push buttons that the user can touch when the {@link SdlCommonInteraction}
         * appears. The buttons provided must contain text to be set, even if the button only needs
         * to provide an image. In case the image is not available at the time of the showing of
         * the dialog on the module, the text will be used instead.
         * @param buttons Collection of SdlButtons that describe what the buttons should look like
         * @return The builder for the {@link SdlCommonInteraction}
         */
        public T setButtons(Collection<SdlButton> buttons){
            this.mButtons = buttons;
            return grabBuilder();
        }


        /**
         * Sets the listener for when the {@link SdlCommonInteraction} finishes with the interaction,
         * is interrupted by another interaction, or an error occurred.
         * @param listener The object to listen for the {@link SdlCommonInteraction} callbacks.
         * @return The builder for the {@link SdlCommonInteraction}
         */
        public T setListener(InteractionListener listener){
            this.mListener = listener;
            return grabBuilder();
        }

        public abstract <T extends SdlCommonInteraction> T  build() throws IllegalCreation;

    }
    public interface InteractionListener{
        enum ErrorResponses{
            MALFORMED_INTERACTION,
            GENERIC_ERROR,
            REJECTED,
            PERMISSIONS_ERROR
        }
        void onTimeout();
        void onAborted();
        void onError(ErrorResponses responses, String moreInfo);
    }

    public static class IllegalCreation extends RuntimeException{
        IllegalCreation(String detailMessage){super(detailMessage);}
    }

    interface IRPCRequestWithButtons {
        void setButtons(List<SoftButton> buttons);
        RPCRequest createRequest();
    }

}
