package com.smartdevicelink.api.view;

import com.smartdevicelink.api.interfaces.SdlContext;


/**
 * Created by mschwerz on 4/21/16.
 */
public class SdlAlertDialog extends SdlAlertBase {
    private final String TAG = getClass().getSimpleName();

    protected SdlAlertDialog(Builder builder) {
        super(builder);
    }

    @Override
    protected boolean verifyRPCCanbeSent(SdlContext context) {
        //TODO: Check here if the activity is in foreground
        return super.verifyRPCCanbeSent(context) && true;
    }

    //Extends the common Builder for the Alerts
    public static class Builder extends SdlAlertBase.Builder<Builder>{

        public Builder() {

        }

        //Ensures that if we want to add other methods to this builder that we return
        //the concrete child instead of the abstract parent within the parent builder
        @Override
        protected Builder grabBuilder() {
            return this;
        }

        @Override
        public SdlAlertDialog build() throws IllegalAlertCreation{
            SdlAlertDialog alertDialog= new SdlAlertDialog(this);
            validateCommonAlert(alertDialog);
            return alertDialog;
        }
    }

}
