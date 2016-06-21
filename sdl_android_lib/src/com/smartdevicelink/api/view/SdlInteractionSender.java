package com.smartdevicelink.api.view;

import com.smartdevicelink.api.interfaces.SdlContext;
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

    protected boolean sendInteraction(SdlContext context, RPCRequest request, final SdlInteractionResponseHandler handler){
        if(isAbleToSendInteraction(mSdlPermission, context)){
            if(request.getOnRPCResponseListener()==null){
                final SdlInteractionSender finalSelf= this;
                request.setOnRPCResponseListener(new OnRPCResponseListener() {
                    @Override
                    public void onResponse(int correlationId, RPCResponse response) {
                        handler.handleRPCResponse(finalSelf,response);
                    }

                    @Override
                    public void onError(int correlationId, Result resultCode, String info) {
                        super.onError(correlationId, resultCode, info);
                        handler.handleResultResponse(finalSelf,resultCode);
                    }
                });
            }
            context.sendRpc(request);
            mIsPending=true;
            return true;
        }else
            return false;
    }

    public void notifyResponseReceived(){
        mIsPending=false;
    }


}
