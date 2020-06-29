package com.smartdevicelink.managers.screen;

import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;

/**
 * OnButtonListener is a listener used for notifying when events have happened with SubscribeButtons
 */
public interface OnButtonListener {

    /**
     * Returns when a Subscribed button is pressed
     * @param buttonName  - Name of Button
     * @param buttonPress - OnButtonPress
     */
    void onPress(ButtonName buttonName, OnButtonPress buttonPress);

    /**
     * Returns when a Subscribed button Event has occurred
     * @param buttonName  - Name of Button
     * @param buttonEvent - OnButtonEvent
     */
    void onEvent(ButtonName buttonName, OnButtonEvent buttonEvent);

    /**
     * Returns when there is an error with subscribing to button
     * @param info - Error info
     */
    void onError(String info);
}

