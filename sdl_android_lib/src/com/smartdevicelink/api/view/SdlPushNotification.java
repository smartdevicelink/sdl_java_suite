package com.smartdevicelink.api.view;

/**
 * Created by mschwerz on 4/21/16.
 */
public class SdlPushNotification extends SdlAlertBase {
    private static final String TAG = SdlPushNotification.class.getSimpleName();

    protected SdlPushNotification(Builder builder) {
        super(builder);
    }


    public static class Builder extends SdlAlertBase.Builder<Builder>{

        public Builder() {

        }

        @Override
        protected Builder grabBuilder() {
            return this;
        }

        @Override
        public SdlPushNotification build() throws IllegalAlertCreation{
            SdlPushNotification pushNotification= new SdlPushNotification(this);
            validateCommonAlert(pushNotification);
            return pushNotification;
        }
    }

}
