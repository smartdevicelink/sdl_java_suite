package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.util.JsonUtils;

public class KeyboardProperties extends RPCObject {
    public static final String KEY_KEYPRESS_MODE = "keypressMode";
	public static final String KEY_KEYBOARD_LAYOUT = "keyboardLayout";
	public static final String KEY_LIMITED_CHARACTER_LIST = "limitedCharacterList";
	public static final String KEY_AUTO_COMPLETE_TEXT = "autoCompleteText";
	public static final String KEY_LANGUAGE = "language";
	
    private static final String KEYPRESS_MODE_DEFAULT = 
            KeypressMode.RESEND_CURRENT_ENTRY.getJsonName(sdlVersion);

    private String language; // represents Language enum
    private String keyboardLayout; // represents KeyboardLayout enum
    private String keypressMode; // represents KeypressMode enum
    private String autoCompleteText;
    private List<String> limitedCharacterList;
    
    public KeyboardProperties() {
        this.keypressMode = KEYPRESS_MODE_DEFAULT;
    }

    /**
     * Creates a KeyboardProperties object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public KeyboardProperties(JSONObject jsonObject) {
        String temp = JsonUtils.readStringFromJsonObject(jsonObject, KEY_KEYPRESS_MODE);
        this.keypressMode = (temp == null) ? KEYPRESS_MODE_DEFAULT : temp;
        
        this.language = JsonUtils.readStringFromJsonObject(jsonObject, KEY_LANGUAGE);
        this.keyboardLayout = JsonUtils.readStringFromJsonObject(jsonObject, KEY_KEYBOARD_LAYOUT);
        this.autoCompleteText = JsonUtils.readStringFromJsonObject(jsonObject, KEY_AUTO_COMPLETE_TEXT);
        this.limitedCharacterList = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_LIMITED_CHARACTER_LIST);
    }

    public Language getLanguage() {
        return Language.valueForJsonName(this.language, sdlVersion);
    }

    public void setLanguage(Language language) {
        this.language = language.getJsonName(sdlVersion);
    }

    public KeyboardLayout getKeyboardLayout() {
        return KeyboardLayout.valueForJsonName(this.keyboardLayout, sdlVersion);
    }

    public void setKeyboardLayout(KeyboardLayout keyboardLayout) {
        this.keyboardLayout = keyboardLayout.getJsonName(sdlVersion);
    }

    public KeypressMode getKeypressMode() {
        return KeypressMode.valueForJsonName(this.keypressMode, sdlVersion);
    }

    public void setKeypressMode(KeypressMode keypressMode) {
        this.keypressMode = keypressMode.getJsonName(sdlVersion);
    }

    public List<String> getLimitedCharacterList() {
        return this.limitedCharacterList;
    }

    public void setLimitedCharacterList(List<String> limitedCharacterList) {
        this.limitedCharacterList = limitedCharacterList;
    }

    public String getAutoCompleteText() {
        return this.autoCompleteText;
    }

    public void setAutoCompleteText(String autoCompleteText) {
        this.autoCompleteText = autoCompleteText;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_AUTO_COMPLETE_TEXT, this.autoCompleteText);
            JsonUtils.addToJsonObject(result, KEY_KEYBOARD_LAYOUT, this.keyboardLayout);
            JsonUtils.addToJsonObject(result, KEY_KEYPRESS_MODE, this.keypressMode);
            JsonUtils.addToJsonObject(result, KEY_LANGUAGE, this.language);
            JsonUtils.addToJsonObject(result, KEY_LIMITED_CHARACTER_LIST, 
                    (this.limitedCharacterList == null) ? null : JsonUtils.createJsonArray(this.limitedCharacterList));
            break;
        }
        
        return result;
    }
}
