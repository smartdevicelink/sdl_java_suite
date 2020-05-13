package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

/**
 * This RPC deletes the window created by the CreateWindow RPC
 * @see CreateWindow
 * @since 6.0
 */
public class DeleteWindow extends RPCRequest {
    public static final String KEY_WINDOW_ID = "windowID";

    /**
     * Constructs a new DeleteWindow object
     */
    public DeleteWindow() {
        super(FunctionID.DELETE_WINDOW.toString());
    }

    /**
     * <p>Constructs a new DeleteWindow object indicated by the Hashtable
     * parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public DeleteWindow(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new DeleteWindow object
     *
     * @param windowID A unique ID to identify the window. The value of '0' will always be the default main window on the main display and cannot be deleted.
     *       See PredefinedWindows enum.
     */
    public DeleteWindow(@NonNull Integer windowID) {
        this();
        setWindowID(windowID);
    }

    /**
     * Sets the windowID. It's a unique ID to identify the window.
     * The value of '0' will always be the default main window on the main display and cannot be deleted.
     * See PredefinedWindows enum.
     *
     * @param windowID A unique ID to identify the window. The value of '0' will always be the default main window on the main display and should not be used in this context as it will already be created for the app. See PredefinedWindows enum. Creating a window with an ID that is already in use will be rejected with `INVALID_ID`.
     */
    public void setWindowID(@NonNull Integer windowID) {
        setParameters(KEY_WINDOW_ID, windowID);
    }

    /**
     * Gets the windowID.
     *
     * @return int -an int value representing the windowID.
     */
    public Integer getWindowID() {
        return getInteger(KEY_WINDOW_ID);
    }
}
