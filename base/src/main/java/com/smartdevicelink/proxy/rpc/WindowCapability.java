package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;

import java.util.Hashtable;
import java.util.List;

/**
 * @since 6.0
 */
public class WindowCapability extends RPCStruct {
    public static final String KEY_WINDOW_ID = "windowID";
    public static final String KEY_TEXT_FIELDS = "textFields";
    public static final String KEY_IMAGE_FIELDS = "imageFields";
    public static final String KEY_IMAGE_TYPE_SUPPORTED = "imageTypeSupported";
    public static final String KEY_TEMPLATES_AVAILABLE = "templatesAvailable";
    public static final String KEY_NUM_CUSTOM_PRESETS_AVAILABLE = "numCustomPresetsAvailable";
    public static final String KEY_BUTTON_CAPABILITIES = "buttonCapabilities";
    public static final String KEY_SOFT_BUTTON_CAPABILITIES = "softButtonCapabilities";
    public static final String KEY_MENU_LAYOUTS_AVAILABLE = "menuLayoutsAvailable";
    public static final String KEY_DYNAMIC_UPDATE_CAPABILITIES = "dynamicUpdateCapabilities";
    /**
     * @since SmartDeviceLink 7.1.0
     */
    public static final String KEY_KEYBOARD_CAPABILITIES = "keyboardCapabilities";

    public WindowCapability() {
    }

    public WindowCapability(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the windowID. The specified ID of the window. Can be set to a predefined window, or omitted for the main window on the main display.
     *
     * @param windowID A unique ID to identify the window. The value of '0' will always be the default main window on the main display and should not be used in this context as it will already be created for the app. See PredefinedWindows enum. Creating a window with an ID that is already in use will be rejected with `INVALID_ID`.
     */
    public WindowCapability setWindowID(Integer windowID) {
        setValue(KEY_WINDOW_ID, windowID);
        return this;
    }

    /**
     * Gets the windowID.
     *
     * @return Integer
     */
    public Integer getWindowID() {
        return getInteger(KEY_WINDOW_ID);
    }

    /**
     * Get an array of TextField structures.
     *
     * @return the List of textFields
     */
    @SuppressWarnings("unchecked")
    public List<TextField> getTextFields() {
        return (List<TextField>) getObject(TextField.class, KEY_TEXT_FIELDS);
    }

    /**
     * Set an array of TextField structures. It's set of all fields that support text data.
     * {@code 1<= textFields.size() <= 100}
     *
     * @param textFields the List of textFields
     */
    public WindowCapability setTextFields(List<TextField> textFields) {
        setValue(KEY_TEXT_FIELDS, textFields);
        return this;
    }

    /**
     * Get an array of ImageField structures.
     *
     * @return the List of imageFields
     */
    @SuppressWarnings("unchecked")
    public List<ImageField> getImageFields() {
        return (List<ImageField>) getObject(ImageField.class, KEY_IMAGE_FIELDS);
    }

    /**
     * Set an array of ImageField structures. A set of all fields that support images.
     * {@code 1<= ImageFields.size() <= 100}
     *
     * @param imageFields the List of imageFields
     */
    public WindowCapability setImageFields(List<ImageField> imageFields) {
        setValue(KEY_IMAGE_FIELDS, imageFields);
        return this;
    }

    /**
     * Get an array of ImageType elements.
     *
     * @return the List of imageTypeSupported
     */
    @SuppressWarnings("unchecked")
    public List<ImageType> getImageTypeSupported() {
        return (List<ImageType>) getObject(ImageType.class, KEY_IMAGE_TYPE_SUPPORTED);
    }

    /**
     * Set an array of ImageType elements.
     * {@code 0<= imageTypeSupported.size() <= 1000}
     *
     * @param imageTypeSupported the List of ImageType
     */
    public WindowCapability setImageTypeSupported(List<ImageType> imageTypeSupported) {
        setValue(KEY_IMAGE_TYPE_SUPPORTED, imageTypeSupported);
        return this;
    }

    /**
     * Get an array of templatesAvailable.
     *
     * @return the List of templatesAvailable
     */
    @SuppressWarnings("unchecked")
    public List<String> getTemplatesAvailable() {
        return (List<String>) getObject(String.class, KEY_TEMPLATES_AVAILABLE);
    }

    /**
     * Set an array of templatesAvailable.
     * {@code 0<= templatesAvailable.size() <= 100}
     *
     * @param templatesAvailable the List of String
     */
    public WindowCapability setTemplatesAvailable(List<String> templatesAvailable) {
        setValue(KEY_TEMPLATES_AVAILABLE, templatesAvailable);
        return this;
    }

    /**
     * Gets the numCustomPresetsAvailable.
     *
     * @return Integer
     */
    public Integer getNumCustomPresetsAvailable() {
        return getInteger(KEY_NUM_CUSTOM_PRESETS_AVAILABLE);
    }

    /**
     * Sets the numCustomPresetsAvailable. The number of on-window custom presets available (if any); otherwise omitted.
     * {@code 1<= numCustomPresetsAvailable.size() <= 100}
     *
     * @param numCustomPresetsAvailable
     */
    public WindowCapability setNumCustomPresetsAvailable(Integer numCustomPresetsAvailable) {
        setValue(KEY_NUM_CUSTOM_PRESETS_AVAILABLE, numCustomPresetsAvailable);
        return this;
    }

    /**
     * Sets the buttonCapabilities portion of the WindowCapability class.
     * {@code 1<= buttonCapabilities.size() <= 100}
     *
     * @param buttonCapabilities It refers to number of buttons and the capabilities of each on-window button.
     */
    public WindowCapability setButtonCapabilities(List<ButtonCapabilities> buttonCapabilities) {
        setValue(KEY_BUTTON_CAPABILITIES, buttonCapabilities);
        return this;
    }

    /**
     * Gets the buttonCapabilities portion of the WindowCapability class
     *
     * @return List<ButtonCapabilities>
     * It refers to number of buttons and the capabilities of each on-window button.
     */
    @SuppressWarnings("unchecked")
    public List<ButtonCapabilities> getButtonCapabilities() {
        return (List<ButtonCapabilities>) getObject(ButtonCapabilities.class, KEY_BUTTON_CAPABILITIES);
    }

    /**
     * Sets the softButtonCapabilities portion of the WindowCapability class.
     * {@code 1<= softButtonCapabilities.size() <= 100}
     *
     * @param softButtonCapabilities It refers to number of soft buttons available on-window and the capabilities for each button.
     */
    public WindowCapability setSoftButtonCapabilities(List<SoftButtonCapabilities> softButtonCapabilities) {
        setValue(KEY_SOFT_BUTTON_CAPABILITIES, softButtonCapabilities);
        return this;
    }

    /**
     * Gets the softButtonCapabilities portion of the WindowCapability class
     *
     * @return List<SoftButtonCapabilities>
     * It refers to number of soft buttons available on-window and the capabilities for each button.
     */
    @SuppressWarnings("unchecked")
    public List<SoftButtonCapabilities> getSoftButtonCapabilities() {
        return (List<SoftButtonCapabilities>) getObject(SoftButtonCapabilities.class, KEY_SOFT_BUTTON_CAPABILITIES);
    }

    /**
     * An array of available menu layouts. If this parameter is not provided, only the `LIST` layout
     * is assumed to be available
     *
     * @param menuLayout - An array of MenuLayouts
     */
    public WindowCapability setMenuLayoutsAvailable(List<MenuLayout> menuLayout) {
        setValue(KEY_MENU_LAYOUTS_AVAILABLE, menuLayout);
        return this;
    }

    /**
     * An array of available menu layouts. If this parameter is not provided, only the `LIST` layout
     * is assumed to be available
     *
     * @return MenuLayout[]
     */
    @SuppressWarnings("unchecked")
    public List<MenuLayout> getMenuLayoutsAvailable() {
        return (List<MenuLayout>) getObject(MenuLayout.class, KEY_MENU_LAYOUTS_AVAILABLE);
    }

    /**
     * Sets the dynamicUpdateCapabilities.
     *
     * @param dynamicUpdateCapabilities Contains the head unit's capabilities for dynamic updating features declaring if the
     *                                  module will send dynamic update RPCs.
     * @since SmartDeviceLink 7.0.0
     */
    public WindowCapability setDynamicUpdateCapabilities(DynamicUpdateCapabilities dynamicUpdateCapabilities) {
        setValue(KEY_DYNAMIC_UPDATE_CAPABILITIES, dynamicUpdateCapabilities);
        return this;
    }

    /**
     * Gets the dynamicUpdateCapabilities.
     *
     * @return DynamicUpdateCapabilities Contains the head unit's capabilities for dynamic updating features declaring if the
     * module will send dynamic update RPCs.
     * @since SmartDeviceLink 7.0.0
     */
    public DynamicUpdateCapabilities getDynamicUpdateCapabilities() {
        return (DynamicUpdateCapabilities) getObject(DynamicUpdateCapabilities.class, KEY_DYNAMIC_UPDATE_CAPABILITIES);
    }

    /**
     * Sets the keyboardCapabilities.
     *
     * @param keyboardCapabilities See KeyboardCapabilities
     * @since SmartDeviceLink 7.1.0
     */
    public WindowCapability setKeyboardCapabilities(KeyboardCapabilities keyboardCapabilities) {
        setValue(KEY_KEYBOARD_CAPABILITIES, keyboardCapabilities);
        return this;
    }

    /**
     * Gets the keyboardCapabilities.
     *
     * @return KeyboardCapabilities See KeyboardCapabilities
     * @since SmartDeviceLink 7.1.0
     */
    public KeyboardCapabilities getKeyboardCapabilities() {
        return (KeyboardCapabilities) getObject(KeyboardCapabilities.class, KEY_KEYBOARD_CAPABILITIES);
    }
}
