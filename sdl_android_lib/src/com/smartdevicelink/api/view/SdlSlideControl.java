package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Slider;
import com.smartdevicelink.proxy.rpc.SliderResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by mschwerz on 5/18/16.
 */
public class SdlSlideControl {

    private final static int MAX_DURATION= 65535;
    private final static int MIN_DURATION= 0;
    private final static int DEFAULT_DURATION= 10000;

    private final int mNumOfTicks;
    private final ArrayList<String> mFooterText;
    private final String mHeaderText;
    private final int mDuration;
    private final int mPosition;
    private boolean mIsPending =false;
    private final InteractionListener mListener;

    SdlSlideControl(Builder builder){
        this.mNumOfTicks= builder.mNumOfTicks;
        this.mDuration= builder.mDuration;
        this.mFooterText= builder.mFooterText;
        this.mHeaderText= builder.mHeaderText;
        this.mPosition= builder.mPosition;
        this.mListener= builder.mListener;
    }

    public boolean send(SdlContext context){
        if(context.getSdlPermissionManager().isPermissionAvailable(SdlPermission.Slider) && !mIsPending){
            Slider sliderRPC= createSlider();
            sliderRPC.setOnRPCResponseListener(new OnRPCResponseListener() {
                @Override
                public void onResponse(int correlationId, RPCResponse response) {
                    if(response.getSuccess())
                        mListener.onTickSelected(((SliderResponse)response).getSliderPosition());
                    else
                        handleResultResponse(response.getResultCode(),response.getInfo());
                }

                @Override
                public void onError(int correlationId, Result resultCode, String info) {
                    super.onError(correlationId, resultCode, info);
                    handleResultResponse(resultCode,info);
                }
            });
            context.getSdlApplicationContext().sendRpc(sliderRPC);
            return true;
        }
        return false;
    }

    private void handleResultResponse(Result response, String info) {
        switch (response) {
            case TIMED_OUT:
                mListener.onTimeout();
                break;
            case ABORTED:
                mListener.onAborted();
                break;
            case INVALID_DATA:
                mListener.onError(info);
                break;
            case DISALLOWED:
                mListener.onError(info);
                break;
            case REJECTED:
                mListener.onError(info);
                break;
            default:
                mListener.onError(info);
                break;
        }
        mIsPending = false;
    }
    private Slider createSlider(){
        Slider newSlider= new Slider();
        newSlider.setNumTicks(mNumOfTicks);
        newSlider.setPosition(mPosition);
        newSlider.setSliderHeader(mHeaderText);
        newSlider.setSliderFooter(mFooterText);
        newSlider.setTimeout(mDuration);
        return newSlider;
    }

    public static class Builder{

        private final int mNumOfTicks;
        private final ArrayList<String> mFooterText = new ArrayList<>();
        private final String mHeaderText;
        private int mDuration= DEFAULT_DURATION;
        private int mPosition=1;
        private InteractionListener mListener;

        /**
         * Builder that takes in static text for the Footer for the SdlSlideControl.
         * @param numOfTicks The number of tick marks to select from (max being 26)
         * @param headerText The static text that will appear above the slider
         * @param footerText The static text that will appear under the slider
         * @param listener Responds when the user finishes the interaction.
         */
        public Builder(int numOfTicks, @NonNull String headerText, @NonNull String footerText, @NonNull InteractionListener listener){
            mNumOfTicks= numOfTicks;
            mHeaderText= headerText;
            mFooterText.add(footerText);
            mListener= listener;

        }

        /**
         * Builder that takes in a collection of labels for each of the ticks.
         * @param headerText The static text that will appear above the slider
         * @param labels The dynamic collection of strings that will show under the slider
         *               when that tick is selected. (Max of 26 labels)
         * @param listener Responds when the user finishes the interaction.
         */
        public Builder(@NonNull String headerText, @NonNull Collection<String> labels, @NonNull InteractionListener listener){
            mHeaderText= headerText;
            mNumOfTicks= labels.size();
            mFooterText.addAll(labels);
            mListener= listener;
        }


        /**
         * Set the timeout for the SdlSlideControl.
         * @param duration How long in milliseconds the slider should appear
         * @return The builder for the SdlSlideControl
         */
        public Builder setDuration(int duration){
            if(duration < MIN_DURATION) {
                mDuration = MIN_DURATION;
            } else if(duration < MAX_DURATION){
                mDuration = duration;
            } else {
                mDuration = MAX_DURATION;
            }
            return this;
        }


        /**
         * Creates the SdlSlideControl from the set information from the builder.
         * @return The SdlSlideControl to show the slider on the Sdl module
         */
        public SdlSlideControl build(){
            return new SdlSlideControl(this);
        }
    }

    public interface InteractionListener{
        void onTickSelected(int tickMark);
        void onTimeout();
        void onAborted();
        void onError(String moreInfo);
    }
}
