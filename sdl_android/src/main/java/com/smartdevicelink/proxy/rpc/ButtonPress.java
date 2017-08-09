package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.ButtonPressMode;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;
import java.util.Hashtable;

public class ButtonPress extends RPCRequest {
	public static final String KEY_MODULE_TYPE = "moduleType";
    public static final String KEY_BUTTON_NAME = "buttonName";
    public static final String KEY_BUTTON_PRESS_MODE = "buttonPressMode";

    public ButtonPress() {
        super(FunctionID.BUTTON_PRESS.toString());
    }

    public ButtonPress(Hashtable<String, Object> hash) {
        super(hash);
    }

    public ModuleType getModuleType() {
        return (ModuleType) getObject(ModuleType.class, KEY_MODULE_TYPE);
    }

    public void setModuleType(ModuleType moduleType) {
        setParameters(KEY_MODULE_TYPE, moduleType);
    }

    public ButtonName getButtonName() {
        return (ButtonName) getObject(ButtonName.class, KEY_BUTTON_NAME);
    }

    public void setButtonName(ButtonName buttonName) {
        setParameters(KEY_BUTTON_NAME, buttonName);
    }

    public ButtonPressMode getButtonPressMode() {
        return (ButtonPressMode) getObject(ButtonPressMode.class, KEY_BUTTON_PRESS_MODE);
    }

    public void setButtonPressMode(ButtonPressMode buttonPressMode) {
        setParameters(KEY_BUTTON_PRESS_MODE, buttonPressMode);
    }
}
