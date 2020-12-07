/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;

import java.util.Hashtable;
import java.util.List;

/**
 * This mode causes the interaction to immediately display a keyboard entry through the HMI.
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
 * 			<td>In this mode, all key presses will be sent as they occur. If disabled, entire string of text will be returned only once submitted by user.If omitted, this value will be set to FALSE.</td>
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
 *                 <td>Array = true maxLength = 1 minsize = 1 maxsize = 100</td>
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
 * 		<tr>
 * 			<td>autoCompleteList</td>
 * 			<td>String</td>
 * 			<td>false</td>
 *                 <td>Array = true maxlength = 1000 minsize = 0 maxsize = 100</td>
 * 			<td>Allows an app to prepopulate the text field with a list of suggested or completed entry as the user types. Set to an empty array to remove the auto-complete list from the screen</td>
 * 		</tr>
 *  </table>
 *
 * @since SmartDeviceLink 3.0
 */

public class KeyboardProperties extends RPCStruct {
    public static final String KEY_KEYPRESS_MODE = "keypressMode";
    public static final String KEY_KEYBOARD_LAYOUT = "keyboardLayout";
    public static final String KEY_LIMITED_CHARACTER_LIST = "limitedCharacterList";
    @Deprecated
    public static final String KEY_AUTO_COMPLETE_TEXT = "autoCompleteText";
    public static final String KEY_AUTO_COMPLETE_LIST = "autoCompleteList";
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

    public KeyboardProperties setLanguage(Language language) {
        setValue(KEY_LANGUAGE, language);
        return this;
    }

    public KeyboardLayout getKeyboardLayout() {
        return (KeyboardLayout) getObject(KeyboardLayout.class, KEY_KEYBOARD_LAYOUT);
    }

    public KeyboardProperties setKeyboardLayout(KeyboardLayout keyboardLayout) {
        setValue(KEY_KEYBOARD_LAYOUT, keyboardLayout);
        return this;
    }

    public KeypressMode getKeypressMode() {
        KeypressMode kp = (KeypressMode) getObject(KeypressMode.class, KEY_KEYPRESS_MODE);
        if (kp == null) {
            kp = KEYPRESS_MODE_DEFAULT;
        }
        return kp;
    }

    public KeyboardProperties setKeypressMode(KeypressMode keypressMode) {
        if (keypressMode != null) {
            setValue(KEY_KEYPRESS_MODE, keypressMode);
        } else {
            setValue(KEY_KEYPRESS_MODE, KEYPRESS_MODE_DEFAULT);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public List<String> getLimitedCharacterList() {
        return (List<String>) getObject(String.class, KEY_LIMITED_CHARACTER_LIST);
    }

    public KeyboardProperties setLimitedCharacterList(List<String> limitedCharacterList) {
        setValue(KEY_LIMITED_CHARACTER_LIST, limitedCharacterList);
        return this;
    }

    /**
     * Gets the text that allows an app to prepopulate the text field with a suggested entry as the user types
     *
     * @return String representing the suggestions text
     */
    @Deprecated
    public String getAutoCompleteText() {
        return (String) getObject(String.class, KEY_AUTO_COMPLETE_TEXT);
    }

    /**
     * Sets the text that allows an app to prepopulate the text field with a suggested entry as the user types
     *
     * @param autoCompleteText String representing the suggestions text
     * @deprecated use {@link #setAutoCompleteList(List <String>)} instead
     */
    @Deprecated
    public KeyboardProperties setAutoCompleteText(String autoCompleteText) {
        setValue(KEY_AUTO_COMPLETE_TEXT, autoCompleteText);
        return this;
    }

    /**
     * Gets the list that allows an app to prepopulate the text field with a list of suggested or
     * completed entries as the user types.
     *
     * @return List<String> representing the suggestions list
     */
    public List<String> getAutoCompleteList() {
        return (List<String>) getObject(String.class, KEY_AUTO_COMPLETE_LIST);
    }

    /**
     * Sets the lists that allows an app to prepopulate the text field with a list of suggested or
     * completed entries as the user types. Set to an empty array to remove the auto-complete list from the screen
     *
     * @param autoCompleteList List<String> representing the suggestions list
     */
    public KeyboardProperties setAutoCompleteList(List<String> autoCompleteList) {
        setValue(KEY_AUTO_COMPLETE_LIST, autoCompleteList);
        return this;
    }
}
