package com.smartdevicelink.api.view;

import android.util.Log;

import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.Show;

import java.util.List;

public abstract class SdlView {

    protected SdlViewManager mViewManager;
    protected DisplayCapabilities mDisplayCapabilities;

    public final void redraw(){
        mViewManager.updateView();
    }

    public void setDisplayCapabilities(DisplayCapabilities displayCapabilities){
        mDisplayCapabilities = displayCapabilities;
    }

    final public boolean isGraphicsSupported() {
        return mDisplayCapabilities != null &&
                mDisplayCapabilities.getGraphicSupported() != null &&
                mDisplayCapabilities.getGraphicSupported();
    }

    final public int getTextFieldCount(){
        return mDisplayCapabilities.getTextFields() != null ?
                mDisplayCapabilities.getTextFields().size(): 0;
    }

    public void setSdlViewManager(SdlViewManager sdlViewManager){
        Log.i(this.getClass().getSimpleName(), "Setting SdlViewManager: " + sdlViewManager);
        mViewManager = sdlViewManager;
    }

    public abstract void clear();

    abstract void decorate(Show show);

    abstract List<SdlImage> getRequiredImages();

    public String getTemplateName(){
        return null;
    }

}
