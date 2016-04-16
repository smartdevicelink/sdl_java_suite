package com.smartdevicelink.api.view;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;

public class SdlViewManager {

    public enum LayoutTemplate{
        DEFAULT,
        GRAPHIC_WITH_TEXT,
        TEXT_WITH_GRAPHIC,
        TILES_ONLY,
        GRAPHIC_WITH_TILES,
        TILES_WITH_GRAPHIC,
        GRAPHIC_WITH_TEXT_AND_SOFTBUTTONS,
        TEXT_AND_SOFTBUTTONS_WITH_GRAPHIC,
        GRAPHIC_WITH_TEXTBUTTONS,
        DOUBLE_GRAPHIC_WITH_SOFTBUTTONS,
        TEXTBUTTONS_WITH_GRAPHIC,
        TEXTBUTTONS_ONLY,
        LARGE_GRAPHIC_WITH_SOFTBUTTONS,
        LARGE_GRAPHIC_ONLY,
        MEDIA,
        ONSCREEN_PRESETS
    }

    private SdlView rootView;
    private SdlProxyALM mProxyALM;

    SdlViewManager (SdlProxyALM proxy){
        mProxyALM = proxy;
        try {
            DisplayCapabilities dc = mProxyALM.getDisplayCapabilities();
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }

    void redraw(){
        // TODO: Method stub
    }

    void setLayout(String layoutName){
        // TODO: Method stub
    }

    SdlView findView(Class<? extends SdlView> viewClass){
        // TODO: Method stub
        return null;
    }

}
