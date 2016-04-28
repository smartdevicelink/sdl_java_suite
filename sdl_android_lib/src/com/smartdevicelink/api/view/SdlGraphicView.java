package com.smartdevicelink.api.view;

import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.proxy.rpc.Show;

import java.util.List;

public class SdlGraphicView extends SdlView {

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
    public void clear() {

    }

    @Override
    void decorate(Show show) {

    }

    @Override
    List<SdlImage> getRequiredImages() {
        return null;
    }
}
