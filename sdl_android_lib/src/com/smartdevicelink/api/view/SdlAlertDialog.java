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

    /**
     * Method to send the built {@link SdlAlertDialog} to the module, while the app is in the foreground. If there is a {@link SdlAlertDialog.InteractionListener}
     * set to {@link SdlAlertDialog}, then the listener will be informed if the dialog fails, is cancelled or if
     * the interaction is able to be completed normally.
     * @param context The SdlActivity that the SdlAlertDialog will be sent from
     */
    @Override
    public final boolean send(@NonNull SdlContext context) {

        //TODO: Figure out how to inform the AlertDialog that it is in Foreground
        /*
        if(!isInForeground || mIsPending){
            Log.w(TAG, "SdlAlertDialog was attempted to be sent while the SdlActivity was not in the foreground, please try again");
            return false;
        }
        */
        SdlPermissionManager checkPermissions = context.getSdlPermissionManager();

        if (checkPermissions.isPermissionAvailable(SdlPermission.Alert)) {
            mIsButtonPressed=false;

            final SdlContext applicationContext= context.getSdlApplicationContext();

            if(!registerAllButtons(newAlert, applicationContext))
                return false;
            newAlert.setOnRPCResponseListener(new OnRPCResponseListener() {
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
            context.sendRpc(newAlert);
            mIsPending=true;
            return true;
        } else {
            return false;
        }
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
