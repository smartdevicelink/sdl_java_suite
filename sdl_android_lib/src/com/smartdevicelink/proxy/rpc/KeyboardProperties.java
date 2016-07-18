package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
/** This mode causes the interaction to immediately display a keyboard entry through the HMI.
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Mandatory</th>
 *                 <th> Additional</th>
 * 			<th>Description</th>
 * 		</tr>
 * 		<tr>
 * 			<td>language</td>
 * 			<td>Language</td>
 * 			<td>false</td>
 *                 <td></td>
 * 			<td>The keyboard language. </td>
 * 		</tr>
 * 		<tr>
 * 			<td>keyboardLayout</td>
 * 			<td>KeyboardLayout</td>
 * 			<td>false</td>
 *                 <td></td>
 * 			<td>Desired keyboard layout.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>sendDynamicEntry</td>
 * 			<td>Boolean</td>
 * 			<td>false</td>
 *                 <td></td>
 * 			<td>In this mode, all keypresses will be sent as they occur. If disabled, entire string of text will be returned only once submitted by user.If omitted, this value will be set to FALSE.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>keypressMode</td>
 * 			<td>KeypressMode</td>
 * 			<td>False</td>
 *                 <td></td>
 * 			<td>Desired keypress mode.If omitted, this value will be set to RESEND_CURRENT_ENTRY. </td>
 * 		</tr>
 * 		<tr>
 * 			<td>limitedCharacterList</td>
 * 			<td>String</td>
 * 			<td>false</td>
 *                 <td>Array = true maxlength = 1 minsize = 1 maxsize = 100</td>
 * 			<td>Array of keyboard characters to enable. All omitted characters will be greyed out (disabled) on the keyboard. If omitted, the entire keyboard will be enabled.</td>
 * 		</tr>
 *
 * 		<tr>
 * 			<td>autoCompleteText</td>
 * 			<td>String</td>
 * 			<td>false</td>
 *                 <td>maxlength = 1000 </td>
 * 			<td>Allows an app to prepopulate the text field with a suggested or completed entry as the user types.</td>
 * 		</tr>
 *  </table>
 * 
 * @since SmartDeviceLink 3.0
 *@see ButtonName
 *
 */

public class KeyboardProperties extends RPCStruct {
    public static final String KEY_KEYPRESS_MODE = "keypressMode";
	public static final String KEY_KEYBOARD_LAYOUT = "keyboardLayout";
	public static final String KEY_LIMITED_CHARACTER_LIST = "limitedCharacterList";
	public static final String KEY_AUTO_COMPLETE_TEXT = "autoCompleteText";
	public static final String KEY_LANGUAGE = "language";
	
    private static final KeypressMode KEYPRESS_MODE_DEFAULT = KeypressMode.RESEND_CURRENT_ENTRY;

    public KeyboardProperties() {
        store.put(KEY_KEYPRESS_MODE, KEYPRESS_MODE_DEFAULT);
    }

    public KeyboardProperties(Hashtable<String, Object> hash) {
        super(hash);
        if (!store.containsKey(KEY_KEYPRESS_MODE)) {
            store.put(KEY_KEYPRESS_MODE, KEYPRESS_MODE_DEFAULT);
        }
    }

    public Language getLanguage() {
        Object obj = store.get(KEY_LANGUAGE);
        if (obj instanceof Language) {
            return (Language) obj;
        } else if (obj instanceof String) {
            return Language.valueForString((String) obj);
        }
        return null;
    }

    public void setLanguage(Language language) {
        if (language != null) {
            store.put(KEY_LANGUAGE, language);
        } else {
            store.remove(KEY_LANGUAGE);
        }
    }

    public KeyboardLayout getKeyboardLayout() {
        Object obj = store.get(KEY_KEYBOARD_LAYOUT);
        if (obj instanceof KeyboardLayout) {
            return (KeyboardLayout) obj;
        } else if (obj instanceof String) {
            return KeyboardLayout.valueForString((String) obj);
        }
        return null;
    }

    public void setKeyboardLayout(KeyboardLayout keyboardLayout) {
        if (keyboardLayout != null) {
            store.put(KEY_KEYBOARD_LAYOUT, keyboardLayout);
        } else {
            store.remove(KEY_KEYBOARD_LAYOUT);
        }
    }

    public KeypressMode getKeypressMode() {
        Object obj = store.get(KEY_KEYPRESS_MODE);
        if (obj instanceof KeypressMode) {
            return (KeypressMode) obj;
        } else if (obj instanceof String) {
            return KeypressMode.valueForString((String) obj);
        }
        return KEYPRESS_MODE_DEFAULT;
    }

    public void setKeypressMode(KeypressMode keypressMode) {
        if (keypressMode != null) {
            store.put(KEY_KEYPRESS_MODE, keypressMode);
        } else {
            store.put(KEY_KEYPRESS_MODE, KEYPRESS_MODE_DEFAULT);
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getLimitedCharacterList() {
        final Object listObj = store.get(KEY_LIMITED_CHARACTER_LIST);
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
            store.put(KEY_LIMITED_CHARACTER_LIST, limitedCharacterList);
        } else {
            store.remove(KEY_LIMITED_CHARACTER_LIST);
        }
    }

    public String getAutoCompleteText() {
        final Object obj = store.get(KEY_AUTO_COMPLETE_TEXT);
        if (obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    public void setAutoCompleteText(String autoCompleteText) {
        if (autoCompleteText != null) {
            store.put(KEY_AUTO_COMPLETE_TEXT, autoCompleteText);
        } else {
            store.remove(KEY_AUTO_COMPLETE_TEXT);
        }
    }
}
