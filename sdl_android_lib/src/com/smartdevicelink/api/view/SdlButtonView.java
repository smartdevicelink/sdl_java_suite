package com.smartdevicelink.api.view;

import com.smartdevicelink.proxy.rpc.Show;

import java.util.List;

public class SdlButtonView implements SdlView {

    private boolean isTiles;

    public void setButtons(List<SdlButton> buttons){
        // TODO: Method stub
    }

    public List<SdlButton> getButtons(){
        // TODO: Method stub
        return null;
    }

    public boolean isGraphicButtonSupported(){
        // TODO: Method stub
        return false;
    }

    public int getMaxButtonCount(){
        // TODO: Method stub
        return -1;
    }

    public void addButton(SdlButton button){
        // TODO: Method stub
    }

    public void removeButton(SdlButton button){
        // TODO: Method stub
    }

    public void setIsTiles(boolean isTiles){
        this.isTiles = isTiles;
    }

    public boolean isTiles(){
        return isTiles;
    }

    @Override
    public boolean decorate(Show show) {
        // TODO: Method stub
        return false;
    }

    @Override
    public void redraw() {
        // TODO: Method stub

    }

    @Override
    public void clear() {
        // TODO: Method stub

    }
}
