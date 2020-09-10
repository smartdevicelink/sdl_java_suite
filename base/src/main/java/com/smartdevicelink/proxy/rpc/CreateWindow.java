package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.WindowType;

import java.util.Hashtable;

/**
 * The RPC CreateWindow allows an app to create a new window on the display.
 * The app needs to specify a window ID that is used for window manipulation e.g. with the RPC Show and the window type which can either be MAIN or WIDGET (see sub-section Window types).
 * @since 6.0
 */
public class CreateWindow extends RPCRequest {
    public static final String KEY_WINDOW_ID = "windowID";
    public static final String KEY_WINDOW_NAME = "windowName";
    public static final String KEY_TYPE = "type";
    public static final String KEY_ASSOCIATED_SERVICE_TYPE = "associatedServiceType";
    public static final String KEY_DUPLICATE_UPDATES_FROM_WINDOW_ID = "duplicateUpdatesFromWindowID";

    /**
     * Constructs a new CreateWindow object
     */
    public CreateWindow() {
        super(FunctionID.CREATE_WINDOW.toString());
    }

    /**
     * <p>Constructs a new CreateWindow object indicated by the Hashtable
     * parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public CreateWindow(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new CreateWindow object
     * @param windowID A unique ID to identify the window. The value of '0' will always be the default main window on the main display and should not be used in this context as it will already be created for the app. See PredefinedWindows enum. Creating a window with an ID that is already in use will be rejected with `INVALID_ID`.
     * @param windowName The window name to be used by the HMI. The name of the pre-created default window will match the app name.
     *        Multiple apps can share the same window name except for the default main window.
     *        {@code windowName.length() <= 100}
     * @param type The type of the window to be created. Main window or widget.
     */
    public CreateWindow(@NonNull Integer windowID, @NonNull String windowName, @NonNull WindowType type) {
        this();
        setWindowID(windowID);
        setWindowName(windowName);
        setType(type);
    }

    /**
     * Sets the windowID. It's a unique ID to identify the window.
     * The value of '0' will always be the default main window on the main display and should
     * not be used in this context as it will already be created for the app. See PredefinedWindows enum.
     * Creating a window with an ID that is already in use will be rejected with `INVALID_ID`.
     *
     * @param windowID A unique ID to identify the window. The value of '0' will always be the default main window on the main display and should not be used in this context as it will already be created for the app. See PredefinedWindows enum. Creating a window with an ID that is already in use will be rejected with `INVALID_ID`.
     */
    public CreateWindow setWindowID(@NonNull Integer windowID) {
        setParameters(KEY_WINDOW_ID, windowID);
        return this;
    }

    /**
     * Gets the windowID.
     *
     * @return int -an int value representing the windowID.
     */
    public Integer getWindowID() {
        return getInteger(KEY_WINDOW_ID);
    }

    /**
     * Sets a window name to be used by the HMI.
     * The name of the pre-created default window will match the app name.
     * Multiple apps can share the same window name except for the default main window.
     * Creating a window with a name which is already in use by the app will result in `DUPLICATE_NAME`.
     *
     * @param windowName The window name to be used by the HMI. The name of the pre-created default window will match the app name.
     *        Multiple apps can share the same window name except for the default main window.
     *        {@code windowName.length() <= 100}
     */
    public CreateWindow setWindowName(@NonNull String windowName) {
        setParameters(KEY_WINDOW_NAME, windowName);
        return this;
    }

    /**
     * Gets a window name to be used by the HMI.
     *
     * @return String -a String value representing the window name.
     */
    public String getWindowName() {
        return getString(KEY_WINDOW_NAME);
    }

    /**
     * Sets the type of the window to be created. Main window or widget.
     *
     * @param type The type of the window to be created. Main window or widget.
     */
    public CreateWindow setType(@NonNull WindowType type) {
        setParameters(KEY_TYPE, type);
        return this;
    }

    /**
     * Gets a WindowType value
     *
     * @return WindowType -a WindowType value
     */
    public WindowType getType() {
        return (WindowType) getObject(WindowType.class, KEY_TYPE);
    }

    /**
     * Sets the associatedServiceType. It allows an app to create a widget related to a specific service type.
     * As an example if a `MEDIA` app becomes active, this app becomes audible and is allowed to play audio.
     * Actions such as skip or play/pause will be directed to this active media app. In case of widgets, the system
     * can provide a single "media" widget which will act as a placeholder for the active media app. It is only allowed
     * to have one window per service type. This means that a media app can only have a single MEDIA widget. Still the
     * app can create widgets omitting this parameter. Those widgets would be available as app specific widgets that are
     * permanently included in the HMI. This parameter is related to widgets only. The default main window, which is
     * pre-created during app registration, will be created based on the HMI types specified in the app registration request.
     *
     * @param associatedServiceType Allows an app to create a widget related to a specific service type.
     *                              As an example if a `MEDIA` app becomes active, this app becomes audible and is allowed to play audio.
     */
    public CreateWindow setAssociatedServiceType( String associatedServiceType) {
        setParameters(KEY_ASSOCIATED_SERVICE_TYPE, associatedServiceType);
        return this;
    }

    /**
     * Gets the associatedServiceType.
     *
     * @return String -a String value representing the associatedServiceType.
     */
    public String getAssociatedServiceType() {
        return getString(KEY_ASSOCIATED_SERVICE_TYPE);
    }

    /**
     * Sets the duplicateUpdatesFromWindowID.
     * Its a Optional parameter. Specify whether the content sent to an existing window
     * should be duplicated to the created window. If there isn't a window with the ID,
     * the request will be rejected with `INVALID_DATA`.
     *
     * @param duplicateUpdatesFromWindowID Specify whether the content sent to an existing window should be duplicated to the created window.
     */
    public CreateWindow setDuplicateUpdatesFromWindowID( Integer duplicateUpdatesFromWindowID) {
        setParameters(KEY_DUPLICATE_UPDATES_FROM_WINDOW_ID, duplicateUpdatesFromWindowID);
        return this;
    }

    /**
     * Gets the duplicateUpdatesFromWindowID.
     *
     * @return int -an int value representing the duplicateUpdatesFromWindowID.
     */
    public Integer getDuplicateUpdatesFromWindowID() {
        return getInteger(KEY_DUPLICATE_UPDATES_FROM_WINDOW_ID);
    }
}
