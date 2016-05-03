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
public class SdlPushNotification extends SdlCommonAlert{
    private static final String TAG = SdlPushNotification.class.getSimpleName();

    protected SdlPushNotification(Builder builder) {
        super(builder);
    }

    /**
     * Method to send the built {@link SdlPushNotification} to the module. If there is a {@link SdlAlertDialog.InteractionListener}
     * set to {@link SdlAlertDialog}, then the listener will be informed if the dialog fails, is cancelled or if
     * the interaction is able to be completed normally. Permissions must be granted to use
     * push notification while not in foreground. If you do not have these permissions, please use
     * {@link SdlAlertDialog} while in the foreground.
     * @param context The SdlContext that the {@link SdlPushNotification} will be sent from
     */
    @Override
    public boolean send(@NonNull SdlContext context) {
        SdlPermissionManager checkPermissions = context.getSdlPermissionManager();
        if (checkPermissions.isPermissionAvailable(SdlPermission.Alert) && !mIsPending) {
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

    public static class Builder extends SdlCommonAlert.Builder<Builder>{

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
