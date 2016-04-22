package com.smartdevicelink.api.view;

import com.smartdevicelink.proxy.rpc.Show;

public class SdlTextView implements SdlView{

    public void setTextField(int index, String text){
        // TODO: Method stub
    }

    public void setText(String text){
        // TODO: Method stub
    }

    public int getMaxTextFieldCount(){
        // TODO: Method stub
        return -1;
    }

    public int getTextFieldMaxLength(){
        // TODO: Method stub
        return -1;
    }

    public String getText(){
        // TODO: Method stub
        return null;
    }

    public String[] getTextFields(){
        // TODO: Method stub
        return null;
    }

    public String getTextField(int index){
        // TODO: Method stub
        return null;
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

    @Override
    public void addChildView(SdlView view) {
        // TODO: Method stub
    }
}
