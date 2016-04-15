package com.smartdevicelink.api.view;

import com.smartdevicelink.proxy.rpc.Show;

import java.util.List;

public class SdlLayoutView implements SdlView {

    void setLayout(String layoutName){
        // TODO: Method stub
    }

    public List<String> getSupportedLayouts(){
        // TODO: Method stub
        return null;
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
