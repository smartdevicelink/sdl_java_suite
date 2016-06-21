package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.interfaces.SdlInteractionResponseListener;
import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

/**
 * Created by mschwerz on 6/17/16.
 */
public class SdlInteractionSender {
    boolean mIsPending;
    private final SdlPermission mSdlPermission;

    public SdlInteractionSender(SdlPermission permission){
        mSdlPermission= permission;
    }

    protected boolean isAbleToSendInteraction(SdlPermission permission, SdlContext context){
        return context.getSdlPermissionManager().isPermissionAvailable(permission) && !mIsPending;
    }

    boolean sendInteraction(@NonNull SdlContext context, @NonNull RPCRequest request, @Nullable final SdlDataReceiver receiver, @Nullable final SdlInteractionResponseListener listener){
        if(isAbleToSendInteraction(mSdlPermission, context)){
            if(request.getOnRPCResponseListener()==null){
                request.setOnRPCResponseListener(new OnRPCResponseListener() {
                    @Override
                    public void onResponse(int correlationId, RPCResponse response) {
                        if(receiver!=null)
                            receiver.handleRPCResponse(response);
                        handleResultResponse(response.getResultCode(),listener);
                    }

                    @Override
                    public void onError(int correlationId, Result resultCode, String info) {
                        super.onError(correlationId, resultCode, info);
                        handleResultResponse(resultCode, listener);
                    }
                });
            }
            context.sendRpc(request);
            mIsPending=true;
            return true;
        }else
            return false;
    }



    private void handleResultResponse(Result response, SdlInteractionResponseListener listener){
        if(listener!=null) {
            switch (response) {
                case SUCCESS:
                    listener.onSuccess();
                    break;
                case TIMED_OUT:
                    listener.onTimeout();
                    break;
                case ABORTED:
                    listener.onAborted();
                    break;
                case INVALID_DATA:
                    listener.onError();
                    break;
                case DISALLOWED:
                    listener.onError();
                    break;
                case REJECTED:
                    listener.onError();
                    break;
                default:
                    listener.onError();
                    break;
            }
        }
        mIsPending=false;
    }


    interface SdlDataReceiver{
        void handleRPCResponse(RPCResponse response);
    }


}
