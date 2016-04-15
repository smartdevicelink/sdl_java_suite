package com.smartdevicelink.api.view;

import com.smartdevicelink.proxy.rpc.Show;

public interface SdlView {

    boolean decorate(Show show);

    void redraw();

    void clear();

    void addChildView(SdlView view);

}
