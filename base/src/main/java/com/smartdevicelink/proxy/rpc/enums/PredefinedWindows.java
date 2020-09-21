package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

/**
 * specifies what windows and IDs are predefined and pre-created on behalf of the app.
 * <p>
 * The default window is always available and represents the app window on the main display.
 * It's an equivalent to today's app window.
 * For backward compatibility, this will ensure the app always has at least the default window on the main display.
 * The app can choose to use this predefined enum element to specifically address app's main window or to duplicate window content.
 * It is not possible to duplicate another window to the default window.
 * <p>
 * The primary widget is a special widget, that can be associated with a service type, which is used by the HMI whenever a single widget needs to represent the whole app.
 * The primary widget should be named as the app and can be pre-created by the HMI
 *
 * @since 6.0
 */
public enum PredefinedWindows {
    /**
     * The default window is a main window pre-created on behalf of the app.
     */
    DEFAULT_WINDOW(0),
    /**
     * The primary widget of the app.
     */
    PRIMARY_WIDGET(1),

    ;


    final int VALUE;

    /**
     * Private constructor
     */
    PredefinedWindows(int value) {
        this.VALUE = value;
    }

    public static PredefinedWindows valueForInt(int value) {

        for (PredefinedWindows anEnum : EnumSet.allOf(PredefinedWindows.class)) {
            if (anEnum.getValue() == value) {
                return anEnum;
            }
        }
        return null;
    }

    public int getValue() {
        return VALUE;
    }
}
