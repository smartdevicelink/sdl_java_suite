package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.interfaces.SdlInteractionResponseListener;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Slider;
import com.smartdevicelink.proxy.rpc.SliderResponse;

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
    private final SdlSliderTickListener mListener;
    private SdlInteractionSender mSender= new SdlInteractionSender(SdlPermission.Slider);

    SdlSlideControl(Builder builder){
        this.mNumOfTicks= builder.mNumOfTicks;
        this.mDuration= builder.mDuration;
        this.mFooterText= builder.mFooterText;
        this.mHeaderText= builder.mHeaderText;
        this.mPosition= builder.mPosition;
        this.mListener= builder.mListener;
    }

    public boolean send(SdlContext context, @Nullable SdlInteractionResponseListener listener){
        return mSender.sendInteraction(context.getSdlApplicationContext(), createSlider(), new SdlInteractionSender.SdlDataReceiver() {
            @Override
            public void handleRPCResponse(RPCResponse response) {
                mListener.onTickSelected(((SliderResponse)response).getSliderPosition());
            }
        },listener);
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
        private SdlSliderTickListener mListener;

        /**
         * Builder that takes in static text for the Footer for the SdlSlideControl.
         * @param numOfTicks The number of tick marks to select from (max being 26)
         * @param headerText The static text that will appear above the slider
         * @param footerText The static text that will appear under the slider
         * @param listener Responds when the user finishes the interaction.
         */
        public Builder(int numOfTicks, @NonNull String headerText, @NonNull String footerText, SdlSliderTickListener listener){
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
        public Builder(@NonNull String headerText, @NonNull Collection<String> labels, SdlSliderTickListener listener){
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

    public interface SdlSliderTickListener{
        void onTickSelected(int tickMark);
    }

}
