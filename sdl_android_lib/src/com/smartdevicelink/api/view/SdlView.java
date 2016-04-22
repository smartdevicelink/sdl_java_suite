package com.smartdevicelink.api.view;

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

    final void setDisplayCapabilities(DisplayCapabilities displayCapabilities){
        mDisplayCapabilities = displayCapabilities;
    }

    final void setSdlViewManager(SdlViewManager sdlViewManager){
        mViewManager = sdlViewManager;
    }

    public abstract void clear();

    abstract void decorate(Show show);

    abstract List<SdlImage> getRequiredImages();

}
