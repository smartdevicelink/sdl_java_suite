package com.smartdevicelink.proxy.rpc.enums;

/**
 * @since 6.0
 */
public enum PredefinedWindows {
    /**
     * The default window is a main window pre-created on behalf of the app.
     */
    DEFAULT_WINDOW,
    /**
     * The primary widget of the app.
     */
    PRIMARY_WIDGET;

    /**
     * Convert String to PredefinedWindows
     *
     * @param value String
     * @return PredefinedWindows
     */
    public static PredefinedWindows valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}
