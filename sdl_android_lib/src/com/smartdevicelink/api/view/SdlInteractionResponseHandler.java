package com.smartdevicelink.api.view;

import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * Created by mschwerz on 6/17/16.
 */
public class SdlInteractionResponseHandler {
    private final SdlInteractionResponseListener mResponseListener;

    public SdlInteractionResponseHandler(@Nullable SdlInteractionResponseListener listener) {
        mResponseListener = listener;
    }

    @CallSuper
    protected void handleRPCResponse(SdlInteractionSender sender, RPCResponse response) {
        handleResultResponse(sender, response.getResultCode());
    }

    @CallSuper
    protected void handleResultResponse(SdlInteractionSender sender, Result response) {
        switch (response) {
            case SUCCESS:
                if(mResponseListener!=null)
                    mResponseListener.onSuccess();
                break;
            case TIMED_OUT:
                if(mResponseListener!=null)
                    mResponseListener.onTimeout();
                break;
            case ABORTED:
                if(mResponseListener!=null)
                    mResponseListener.onAborted();
                break;
            case INVALID_DATA:
                if(mResponseListener!=null)
                    mResponseListener.onError();
                break;
            case DISALLOWED:
                if(mResponseListener!=null)
                    mResponseListener.onError();
                break;
            case REJECTED:
                if(mResponseListener!=null)
                    mResponseListener.onError();
                break;
            default:
                if(mResponseListener!=null)
                    mResponseListener.onError();
                break;
        }
        sender.notifyResponseReceived();
    }

}
