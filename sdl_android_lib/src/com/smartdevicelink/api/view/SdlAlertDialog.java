package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.api.permission.SdlPermissionManager;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;


/**
 * Created by mschwerz on 4/21/16.
 */
public class SdlAlertDialog extends SdlCommonAlert{
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
    public static class Builder extends SdlCommonAlert.Builder<Builder>{

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
