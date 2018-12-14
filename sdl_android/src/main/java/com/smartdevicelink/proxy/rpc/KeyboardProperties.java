package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.AmbientLightStatus;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;

import java.util.Hashtable;
import java.util.List;

import static android.provider.Contacts.SettingsColumns.KEY;
import static com.smartdevicelink.proxy.rpc.HeadLampStatus.KEY_AMBIENT_LIGHT_SENSOR_STATUS;

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
        setValue(KEY_KEYPRESS_MODE, KEYPRESS_MODE_DEFAULT);
    }

    public KeyboardProperties(Hashtable<String, Object> hash) {
        super(hash);
        if (!store.containsKey(KEY_KEYPRESS_MODE)) {
            setValue(KEY_KEYPRESS_MODE, KEYPRESS_MODE_DEFAULT);
        }
    }

    public Language getLanguage() {
        return (Language) getObject(Language.class, KEY_LANGUAGE);
    }

    public void setLanguage(Language language) {
        setValue(KEY_LANGUAGE, language);
    }

    public KeyboardLayout getKeyboardLayout() {
        return (KeyboardLayout) getObject(KeyboardLayout.class, KEY_KEYBOARD_LAYOUT);
    }

    public void setKeyboardLayout(KeyboardLayout keyboardLayout) {
        setValue(KEY_KEYBOARD_LAYOUT, keyboardLayout);
    }

    public KeypressMode getKeypressMode() {
        KeypressMode kp = (KeypressMode) getObject(KeypressMode.class, KEY_KEYPRESS_MODE);
        if(kp == null){
            kp = KEYPRESS_MODE_DEFAULT;
        }
        return kp;
    }

    public void setKeypressMode(KeypressMode keypressMode) {
        if (keypressMode != null) {
            setValue(KEY_KEYPRESS_MODE, keypressMode);
        } else {
            setValue(KEY_KEYPRESS_MODE, KEYPRESS_MODE_DEFAULT);
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getLimitedCharacterList() {
        return (List<String>) getObject(String.class, KEY_LIMITED_CHARACTER_LIST);
    }

    public void setLimitedCharacterList(List<String> limitedCharacterList) {
        setValue(KEY_LIMITED_CHARACTER_LIST, limitedCharacterList);
    }

    public String getAutoCompleteText() {
        return (String) getObject(String.class, KEY_AUTO_COMPLETE_TEXT);
    }

    public void setAutoCompleteText(String autoCompleteText) {
        setValue(KEY_AUTO_COMPLETE_TEXT, autoCompleteText);
    }
}
