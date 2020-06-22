package com.smartdevicelink.managers.screen;

import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;

public interface OnButtonListener {
    void onPress(ButtonName buttonName, OnButtonPress buttonPress);
    void onEvent(ButtonName buttonName, OnButtonEvent buttonEvent);
    void onError(String info);
}
