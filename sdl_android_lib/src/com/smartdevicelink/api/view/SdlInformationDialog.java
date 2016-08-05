package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.interfaces.SdlInteractionResponseListener;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.proxy.rpc.ScrollableMessage;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by mschwerz on 5/3/16.
 */
public class SdlInformationDialog {
    protected static final int MIN_DURATION = 0;
    protected static final int DEFAULT_DURATION = 30000;
    protected static final int MAX_DURATION = 65535;
    protected final String mTextFields;
    protected final int mDuration;
    protected final ArrayList<SdlButton> mButtons= new ArrayList<>();
    protected SdlInteractionSender mSender= new SdlInteractionSender(SdlPermission.ScrollableMessage);
    protected SdlInteractionButtonManager mButtonManager;

    protected SdlInformationDialog(Builder builder) {
        this.mTextFields= builder.mTextFields;
        this.mDuration= builder.mDuration;
        this.mButtons.addAll(builder.mButtons);
        mButtonManager= new SdlInteractionButtonManager(this.mButtons);
    }

    protected @NonNull ScrollableMessage createScrollableMessage(SdlContext context) {
        final ScrollableMessage newScrollableMessage= new ScrollableMessage();
        newScrollableMessage.setScrollableMessageBody(mTextFields);
        newScrollableMessage.setTimeout(mDuration);
        newScrollableMessage.setSoftButtons(mButtonManager.registerAllButtons(context));
        return newScrollableMessage;
    }

    public boolean send(SdlContext context, SdlInteractionResponseListener listener){
        SdlContext applicationContext= context.getSdlApplicationContext();
        return mSender.sendInteraction(applicationContext,createScrollableMessage(applicationContext), null, mButtonManager.getCleanUpListener(applicationContext,listener));
    }

    public static class Builder{
        protected String mTextFields;
        protected int mDuration = DEFAULT_DURATION;
        protected Collection<SdlButton> mButtons = new ArrayList<>();

        /**
         * Sets the duration that the {@link SdlAlertBase} will show up for.
         * The min value is 3000 and the max value is 10000
         * @param duration The amount of milliseconds the SdlCommonAlert should appear
         * @return The builder for the {@link SdlAlertBase}
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

        public Builder setText(String text) {
            mTextFields= text;
            return this;
        }

        public Builder setButtons(@NonNull Collection<SdlButton> buttons){
            mButtons.clear();
            mButtons.addAll(buttons);
            return this;
        }

        public Builder addButton(@NonNull SdlButton button){
            mButtons.add(button);
            return this;
        }

        public SdlInformationDialog build() {
                return new SdlInformationDialog(this);
            }
        }

}
