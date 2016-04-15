package com.smartdevicelink.api.view;

import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.proxy.rpc.Show;

public class SdlGraphicView implements SdlView {

    public void setGraphic(SdlImage graphic){
        // TODO: Method stub
    }

    public SdlImage getGraphic(){
        // TODO: Method stub
        return null;
    }

    public void setGraphic(String name){
        // TODO: Method stub
    }

    @Override
    public boolean decorate(Show show) {
        return false;
    }

    @Override
    public void redraw() {

    }

    @Override
    public void clear() {

    }

    @Override
    public void addChildView(SdlView view) {

    }
}
