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
