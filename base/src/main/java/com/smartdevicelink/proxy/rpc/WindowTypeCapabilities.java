package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.WindowType;
import java.util.Hashtable;

/**
 * Used to inform an app how many window instances per type they can be created.
 * @since 6.0
 */
public class WindowTypeCapabilities extends RPCStruct {
    public static final String KEY_TYPE = "type";
    public static final String KEY_MAXIMUM_NUMBER_OF_WINDOWS = "maximumNumberOfWindows";

    /**
     * Constructs a newly allocated WindowTypeCapabilities object
     */
    public WindowTypeCapabilities() {
    }

    /**
     * Constructs a newly allocated WindowTypeCapabilities object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public WindowTypeCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated WindowTypeCapabilities object
     *
     * @param type
     * @param maximumNumberOfWindows
     */
    public WindowTypeCapabilities(@NonNull WindowType type, @NonNull Integer maximumNumberOfWindows) {
        this();
        setType(type);
        setMaximumNumberOfWindows(maximumNumberOfWindows);
    }

    /**
     * set the type of Window
     *
     * @param type
     */
    public void setType(@NonNull WindowType type) {
        setValue(KEY_TYPE, type);
    }

    /**
     * get the type of Window
     *
     * @return WindowType
     */
    public WindowType getType() {
        return (WindowType) getObject(WindowType.class, KEY_TYPE);
    }

    /**
     * set the maximumNumberOfWindows
     *
     * @param maximumNumberOfWindows
     */
    public void setMaximumNumberOfWindows(@NonNull Integer maximumNumberOfWindows) {
        setValue(KEY_MAXIMUM_NUMBER_OF_WINDOWS, maximumNumberOfWindows);
    }

    /**
     * get the maximumNumberOfWindows
     *
     * @return Integer
     */
    public Integer getMaximumNumberOfWindows() {
        return getInteger(KEY_MAXIMUM_NUMBER_OF_WINDOWS);
    }
}
