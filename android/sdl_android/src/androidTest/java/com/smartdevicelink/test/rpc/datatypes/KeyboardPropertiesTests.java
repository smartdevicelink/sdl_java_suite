package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.KeyboardProperties}
 */
public class KeyboardPropertiesTests extends TestCase{

    private KeyboardProperties msg;

    @Override
    public void setUp(){
        msg = new KeyboardProperties();

        msg.setAutoCompleteText(TestValues.GENERAL_STRING);
        msg.setAutoCompleteList(TestValues.GENERAL_STRING_LIST);
        msg.setKeyboardLayout(TestValues.GENERAL_KEYBOARDLAYOUT);
        msg.setKeypressMode(TestValues.GENERAL_KEYPRESSMODE);
        msg.setLanguage(TestValues.GENERAL_LANGUAGE);
        msg.setLimitedCharacterList(TestValues.GENERAL_STRING_LIST);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        String autoCompleteText = msg.getAutoCompleteText();
        List<String> autoCompleteList = msg.getAutoCompleteList();
        KeyboardLayout keyboardLayout = msg.getKeyboardLayout();
        KeypressMode keypressMode = msg.getKeypressMode();
        Language language = msg.getLanguage();
        List<String> limitedChars = msg.getLimitedCharacterList();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, autoCompleteText);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST.size(), autoCompleteList.size());
        assertTrue(TestValues.TRUE, Validator.validateStringList(TestValues.GENERAL_STRING_LIST, autoCompleteList));
        assertEquals(TestValues.MATCH, TestValues.GENERAL_KEYBOARDLAYOUT, keyboardLayout);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_KEYPRESSMODE, keypressMode);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_LANGUAGE, language);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST.size(), limitedChars.size());
        assertTrue(TestValues.TRUE, Validator.validateStringList(TestValues.GENERAL_STRING_LIST, limitedChars));
        
        // Invalid/Null Tests
        KeyboardProperties msg = new KeyboardProperties();
        assertNotNull(TestValues.NOT_NULL, msg);
        
        // Keypress mode is created in the object constructor
        assertNotNull(TestValues.NOT_NULL, msg.getKeypressMode());
        assertNull(TestValues.NULL, msg.getAutoCompleteText());
        assertNull(TestValues.NULL, msg.getAutoCompleteList());
        assertNull(TestValues.NULL, msg.getLanguage());
        assertNull(TestValues.NULL, msg.getKeyboardLayout());
        assertNull(TestValues.NULL, msg.getLimitedCharacterList());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(KeyboardProperties.KEY_AUTO_COMPLETE_TEXT, TestValues.GENERAL_STRING);
            reference.put(KeyboardProperties.KEY_AUTO_COMPLETE_LIST, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
            reference.put(KeyboardProperties.KEY_KEYBOARD_LAYOUT, TestValues.GENERAL_KEYBOARDLAYOUT);
            reference.put(KeyboardProperties.KEY_KEYPRESS_MODE, TestValues.GENERAL_KEYPRESSMODE);
            reference.put(KeyboardProperties.KEY_LANGUAGE, TestValues.GENERAL_LANGUAGE);
            reference.put(KeyboardProperties.KEY_LIMITED_CHARACTER_LIST, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                if(key.equals(KeyboardProperties.KEY_LIMITED_CHARACTER_LIST) || key.equals(KeyboardProperties.KEY_AUTO_COMPLETE_LIST)){
                    assertTrue(TestValues.TRUE, Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key), JsonUtils.readStringListFromJsonObject(underTest, key)));
                } else{
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
    }
}