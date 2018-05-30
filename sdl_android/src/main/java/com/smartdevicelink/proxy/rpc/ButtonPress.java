package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.ButtonPressMode;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;
import java.util.Hashtable;

/**
 * This function allows a remote control type mobile application
 * simulate a hardware button press event.
 */
public class ButtonPress extends RPCRequest {
	public static final String KEY_MODULE_TYPE = "moduleType";
    public static final String KEY_BUTTON_NAME = "buttonName";
    public static final String KEY_BUTTON_PRESS_MODE = "buttonPressMode";

    /**
     * Constructs a new ButtonPress object
     */
    public ButtonPress() {
        super(FunctionID.BUTTON_PRESS.toString());
    }

    /**
     * <p>Constructs a new ButtonPress object indicated by the
     * Hashtable parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public ButtonPress(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new ButtonPress object
     * @param moduleType Represents module where the button should be pressed
     * @param buttonName Represents name of supported RC climate or radio button
     * @param buttonPressMode Indicates whether this is a LONG or SHORT button press event.
     */
    public ButtonPress(@NonNull ModuleType moduleType, @NonNull ButtonName buttonName, @NonNull ButtonPressMode buttonPressMode) {
        this();
        setModuleType(moduleType);
        setButtonName(buttonName);
        setButtonPressMode(buttonPressMode);
    }

    /**
     * Gets the ModuleType
     *
     * @return ModuleType - The module where the button should be pressed
     */
    public ModuleType getModuleType() {
        return (ModuleType) getObject(ModuleType.class, KEY_MODULE_TYPE);
    }

    /**
     * Sets a ModuleType
     *
     * @param moduleType
     * Represents module where the button should be pressed
     */
    public void setModuleType(@NonNull ModuleType moduleType) {
        setParameters(KEY_MODULE_TYPE, moduleType);
    }

    /**
     * Gets the ButtonName
     *
     * @return ButtonName - The name of supported RC climate or radio button
     */
    public ButtonName getButtonName() {
        return (ButtonName) getObject(ButtonName.class, KEY_BUTTON_NAME);
    }

    /**
     * Sets a ButtonName
     *
     * @param buttonName
     * Represents name of supported RC climate or radio button
     */
    public void setButtonName(@NonNull ButtonName buttonName) {
        setParameters(KEY_BUTTON_NAME, buttonName);
    }

    /**
     * Gets the ButtonPressMode
     *
     * @return ButtonPressMode - Indicates whether this is a LONG or SHORT button press event.
     */
    public ButtonPressMode getButtonPressMode() {
        return (ButtonPressMode) getObject(ButtonPressMode.class, KEY_BUTTON_PRESS_MODE);
    }

    /**
     * Sets a ButtonPressMode
     *
     * @param buttonPressMode
     * Indicates whether this is a LONG or SHORT button press event.
     */
    public void setButtonPressMode(@NonNull ButtonPressMode buttonPressMode) {
        setParameters(KEY_BUTTON_PRESS_MODE, buttonPressMode);
    }
}
