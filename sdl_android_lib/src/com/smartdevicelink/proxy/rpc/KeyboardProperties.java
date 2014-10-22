package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.util.DebugTool;

public class KeyboardProperties extends RPCStruct {
    private static final KeypressMode KEYPRESS_MODE_DEFAULT =
            KeypressMode.RESEND_CURRENT_ENTRY;
    public static final String keypressMode = "keypressMode";
	public static final String keyboardLayout = "keyboardLayout";
	public static final String limitedCharacterList = "limitedCharacterList";
	public static final String autoCompleteText = "autoCompleteText";
	public static final String language = "language";

    public KeyboardProperties() {
        store.put(KeyboardProperties.keypressMode, KEYPRESS_MODE_DEFAULT);
    }

    public KeyboardProperties(Hashtable hash) {
        super(hash);
        if (!store.containsKey(KeyboardProperties.keypressMode)) {
            store.put(KeyboardProperties.keypressMode, KEYPRESS_MODE_DEFAULT);
        }
    }

    public Language getLanguage() {
        Object obj = store.get(KeyboardProperties.language);
        if (obj instanceof Language) {
            return (Language) obj;
        } else if (obj instanceof String) {
            Language theCode = null;
            try {
                theCode = Language.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError(
                        "Failed to parse " + getClass().getSimpleName() + "." +
                        		KeyboardProperties.language, e);
            }
            return theCode;
        }
        return null;
    }

    public void setLanguage(Language language) {
        if (language != null) {
            store.put(KeyboardProperties.language, language);
        } else {
            store.remove(KeyboardProperties.language);
        }
    }

    public KeyboardLayout getKeyboardLayout() {
        Object obj = store.get(KeyboardProperties.keyboardLayout);
        if (obj instanceof KeyboardLayout) {
            return (KeyboardLayout) obj;
        } else if (obj instanceof String) {
            KeyboardLayout theCode = null;
            try {
                theCode = KeyboardLayout.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError(
                        "Failed to parse " + getClass().getSimpleName() + "." +
                        		KeyboardProperties.keyboardLayout, e);
            }
            return theCode;
        }
        return null;
    }

    public void setKeyboardLayout(KeyboardLayout keyboardLayout) {
        if (keyboardLayout != null) {
            store.put(KeyboardProperties.keyboardLayout, keyboardLayout);
        } else {
            store.remove(KeyboardProperties.keyboardLayout);
        }
    }

    public KeypressMode getKeypressMode() {
        Object obj = store.get(KeyboardProperties.keypressMode);
        if (obj instanceof KeypressMode) {
            return (KeypressMode) obj;
        } else if (obj instanceof String) {
            KeypressMode theCode = null;
            try {
                theCode = KeypressMode.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError(
                        "Failed to parse " + getClass().getSimpleName() + "." +
                        		KeyboardProperties.keypressMode, e);
            }
            return theCode;
        }
        return KEYPRESS_MODE_DEFAULT;
    }

    public void setKeypressMode(KeypressMode keypressMode) {
        if (keypressMode != null) {
            store.put(KeyboardProperties.keypressMode, keypressMode);
        } else {
            store.put(KeyboardProperties.keypressMode, KEYPRESS_MODE_DEFAULT);
        }
    }

    public List<String> getLimitedCharacterList() {
        final Object listObj = store.get(KeyboardProperties.limitedCharacterList);
        if (listObj instanceof List<?>) {
        	List<?> list = (List<?>) listObj;
            if (list != null && list.size() > 0) {
                Object obj = list.get(0);
                if (obj instanceof String) {
                    return (List<String>) list;
                }
            }
        }
        return null;
    }

    public void setLimitedCharacterList(List<String> limitedCharacterList) {
        if (limitedCharacterList != null) {
            store.put(KeyboardProperties.limitedCharacterList, limitedCharacterList);
        } else {
            store.remove(KeyboardProperties.limitedCharacterList);
        }
    }

    public String getAutoCompleteText() {
        final Object obj = store.get(KeyboardProperties.autoCompleteText);
        if (obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    public void setAutoCompleteText(String autoCompleteText) {
        if (autoCompleteText != null) {
            store.put(KeyboardProperties.autoCompleteText, autoCompleteText);
        } else {
            store.remove(KeyboardProperties.autoCompleteText);
        }
    }
}
