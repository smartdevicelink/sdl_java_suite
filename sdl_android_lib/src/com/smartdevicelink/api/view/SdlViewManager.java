package com.smartdevicelink.api.view;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.rpc.SetDisplayLayout;
import com.smartdevicelink.proxy.rpc.Show;

public class SdlViewManager {

    private static final String TAG = SdlViewManager.class.getSimpleName();

    private SdlView mRootView;
    private SdlContext mSdlContext;
    private Show mShow;
    private String mTemplateName = "";

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
        if(templateName != null && !mTemplateName.equals(templateName)){
            mTemplateName = templateName;
            SetDisplayLayout setDisplayLayout = new SetDisplayLayout();
            setDisplayLayout.setDisplayLayout(templateName);
            mSdlContext.sendRpc(setDisplayLayout);
        }
        mRootView.decorate(mShow);
        mSdlContext.sendRpc(mShow);
    }

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
