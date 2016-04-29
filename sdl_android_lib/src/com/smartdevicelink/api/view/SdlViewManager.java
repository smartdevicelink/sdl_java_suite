package com.smartdevicelink.api.view;

import android.util.Log;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.SetDisplayLayout;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import org.json.JSONException;

public class SdlViewManager {

    private static final String TAG = SdlViewManager.class.getSimpleName();

    private SdlView mRootView;
    private SdlContext mSdlContext;
    private Show mShow;

    public SdlViewManager (SdlContext sdlContext){
        mSdlContext = sdlContext;
        mShow = new Show();
    }

    public void setRootView(SdlView view){
        mRootView = view;
        mRootView.setSdlViewManager(this);
        mRootView.setSdlContext(mSdlContext);
    }

    public SdlView getRootView(){
        return mRootView;
    }

    public void updateView(){
        String templateName = mRootView.getTemplateName();
        if(templateName != null){
            SetDisplayLayout setDisplayLayout = new SetDisplayLayout();
            setDisplayLayout.setDisplayLayout(templateName);
            mSdlContext.sendRpc(setDisplayLayout);
        }
        mRootView.decorate(mShow);
        mShow.setOnRPCResponseListener(mShowListener);
        try {
            Log.d(TAG, "Muh show luk lik dis.\n" + mShow.serializeJSON().toString(3));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSdlContext.sendRpc(mShow);
    }

    private OnRPCResponseListener mShowListener = new OnRPCResponseListener() {
        @Override
        public void onResponse(int correlationId, RPCResponse response) {
            try {
                Log.v(TAG, response.serializeJSON().toString(3));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(int correlationId, Result resultCode, String info) {
            // TODO: Method Stub
            Log.v(TAG, resultCode + " - " + info);
            super.onError(correlationId, resultCode, info);
        }
    };

    int registerButtonCallback(SdlButton.OnPressListener listener){
        return mSdlContext.registerButtonCallback(listener);
    }

    void unregisterButtonCallBack(int id){
        mSdlContext.unregisterButtonCallback(id);
    }

    public void prepareImages(){
        mRootView.uploadRequiredImages();
    }

}
