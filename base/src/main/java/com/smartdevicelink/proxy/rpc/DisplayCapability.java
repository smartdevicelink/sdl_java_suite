package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
import java.util.List;

/**
 * Contain the display related information and all windows related to that display.
 * @since 6.0
 */
public class DisplayCapability extends RPCStruct {
    public static final String KEY_DISPLAY_NAME = "displayName";
    public static final String KEY_WINDOW_TYPE_SUPPORTED = "windowTypeSupported";
    public static final String KEY_WINDOW_CAPABILITIES = "windowCapabilities";

    public DisplayCapability() {
    }

    public DisplayCapability(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Get the name of the display
     *
     * @return the name of the display
     */
    public String getDisplayName() {
        return getString(KEY_DISPLAY_NAME);
    }

    /**
     * Set the name of the display
     * {@code displayName.length() > 1}
     *
     * @param displayName the name of the display
     */
    public void setDisplayName(String displayName) {
        setValue(KEY_DISPLAY_NAME, displayName);
    }

    /**
     * Sets the windowTypeSupported portion of the DisplayCapability class.
     * {@code windowTypeSupported.size()>=1}
     *
     * @param windowTypeSupported It informs the application how many windows the app is allowed to create per type.
     */
    public void setWindowTypeSupported(List<WindowTypeCapabilities> windowTypeSupported) {
        setValue(KEY_WINDOW_TYPE_SUPPORTED, windowTypeSupported);
    }

    /**
     * Gets the windowTypeSupported portion of the DisplayCapability class
     *
     * @return List<WindowTypeCapabilities>
     * It informs the application how many windows the app is allowed to create per type.
     */
    @SuppressWarnings("unchecked")
    public List<WindowTypeCapabilities> getWindowTypeSupported() {
        return (List<WindowTypeCapabilities>) getObject(WindowTypeCapabilities.class, KEY_WINDOW_TYPE_SUPPORTED);
    }

    /**
     * Sets the windowCapabilities portion of the DisplayCapability class.
     *
     * @param windowCapabilities Contains a list of capabilities of all windows related to the app.
     *                           Once the app has registered the capabilities of all windows are provided.
     *                           GetSystemCapability still allows requesting window capabilities of all windows.
     *                           After registration, only windows with capabilities changed will be included.
     *                           Following cases will cause only affected windows to be included:
     *                           1. App creates a new window. After the window is created, a system capability notification will be sent related only to the created window.
     *                           2. App sets a new template to the window. The new template changes window capabilties. The notification will reflect those changes to the single window.
     */
    public void setWindowCapabilities(List<WindowCapability> windowCapabilities) {
        setValue(KEY_WINDOW_CAPABILITIES, windowCapabilities);
    }

    /**
     * Gets the windowCapabilities portion of the DisplayCapability class
     *
     * @return List<WindowCapability>
     * Contains a list of capabilities of all windows related to the app.
     */
    @SuppressWarnings("unchecked")
    public List<WindowCapability> getWindowCapabilities() {
        return (List<WindowCapability>) getObject(WindowCapability.class, KEY_WINDOW_CAPABILITIES);
    }
}
