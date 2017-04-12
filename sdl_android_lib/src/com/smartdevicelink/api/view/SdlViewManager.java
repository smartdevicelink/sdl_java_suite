package com.smartdevicelink.api.view;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.rpc.SetDisplayLayout;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.SoftButton;

import java.util.ArrayList;

public class SdlViewManager {

    private static final String TAG = SdlViewManager.class.getSimpleName();

    private SdlView mRootView;
    private SdlContext mSdlContext;
    private String mTemplateName = "";

    public SdlViewManager (SdlContext sdlContext){
        mSdlContext = sdlContext;
    }

    public void setRootView(SdlView view){
        mRootView = view;
        mRootView.setSdlViewManager(this);
        mRootView.setSdlContext(mSdlContext);
    }

    public SdlView getRootView(){
        return mRootView;
    }

    public void setCurrentTemplate(String templateName){
        mTemplateName = templateName;
    }

    public String getCurrentTemplate(){
        return mTemplateName;
    }

    public void updateView(){
        String templateName = mRootView.getTemplateName();
        if(templateName != null && !mTemplateName.equals(templateName)){
            mTemplateName = templateName;
            SetDisplayLayout setDisplayLayout = new SetDisplayLayout();
            setDisplayLayout.setDisplayLayout(templateName);
            mSdlContext.sendRpc(setDisplayLayout);
        }
        Show newShow = new Show();
        newShow.setMainField1("");
        newShow.setMainField2("");
        newShow.setMainField3("");
        newShow.setMainField4("");
        newShow.setSoftButtons(new ArrayList<SoftButton>());
        mRootView.decorate(newShow);
        mSdlContext.sendRpc(newShow);
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
