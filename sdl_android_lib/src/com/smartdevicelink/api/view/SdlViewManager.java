package com.smartdevicelink.api.view;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

public class SdlViewManager {

    private SdlView mRootView;
    private SdlProxyALM mProxyALM;
    private Show mShow;

    SdlViewManager (SdlProxyALM proxy){
        mProxyALM = proxy;
        mShow = new Show();
    }

    void setRootView(SdlView view){
        mRootView = view;
    }

    void updateView(){
        mRootView.decorate(mShow);
        try {
            mProxyALM.sendRPCRequest(mShow);
        } catch (SdlException e) {
            // TODO: auto generated exception clause
            e.printStackTrace();
        }
    }

    private OnRPCResponseListener mShowListener = new OnRPCResponseListener() {
        @Override
        public void onResponse(int correlationId, RPCResponse response) {
            // TODO: Method Stub
        }

        @Override
        public void onError(int correlationId, Result resultCode, String info) {
            // TODO: Method Stub
            super.onError(correlationId, resultCode, info);
        }
    };

}
