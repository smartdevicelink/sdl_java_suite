package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.KeyboardProperties}
 */
public class KeyboardPropertiesTests extends TestCase{

    private KeyboardProperties msg;

    @Override
    public void setUp(){
        msg = new KeyboardProperties();

        msg.setAutoCompleteText(Test.GENERAL_STRING);
        msg.setKeyboardLayout(Test.GENERAL_KEYBOARDLAYOUT);
        msg.setKeypressMode(Test.GENERAL_KEYPRESSMODE);
        msg.setLanguage(Test.GENERAL_LANGUAGE);
        msg.setLimitedCharacterList(Test.GENERAL_STRING_LIST);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        String autoComplete = msg.getAutoCompleteText();
        KeyboardLayout keyboardLayout = msg.getKeyboardLayout();
        KeypressMode keypressMode = msg.getKeypressMode();
        Language language = msg.getLanguage();
        List<String> limitedChars = msg.getLimitedCharacterList();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STRING, autoComplete);
        assertEquals(Test.MATCH, Test.GENERAL_KEYBOARDLAYOUT, keyboardLayout);
        assertEquals(Test.MATCH, Test.GENERAL_KEYPRESSMODE, keypressMode);
        assertEquals(Test.MATCH, Test.GENERAL_LANGUAGE, language);
        assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST.size(), limitedChars.size());
        assertTrue(Test.TRUE, Validator.validateStringList(Test.GENERAL_STRING_LIST, limitedChars));
        
        // Invalid/Null Tests
        KeyboardProperties msg = new KeyboardProperties();
        assertNotNull(Test.NOT_NULL, msg);
        
        // Keypress mode is created in the object constructor
        assertNotNull(Test.NOT_NULL, msg.getKeypressMode());
        
        assertNull(Test.NULL, msg.getAutoCompleteText());
        assertNull(Test.NULL, msg.getLanguage());
        assertNull(Test.NULL, msg.getKeyboardLayout());        
        assertNull(Test.NULL, msg.getLimitedCharacterList());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(KeyboardProperties.KEY_AUTO_COMPLETE_TEXT, Test.GENERAL_STRING);
            reference.put(KeyboardProperties.KEY_KEYBOARD_LAYOUT, Test.GENERAL_KEYBOARDLAYOUT);
            reference.put(KeyboardProperties.KEY_KEYPRESS_MODE, Test.GENERAL_KEYPRESSMODE);
            reference.put(KeyboardProperties.KEY_LANGUAGE, Test.GENERAL_LANGUAGE);
            reference.put(KeyboardProperties.KEY_LIMITED_CHARACTER_LIST, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                if(key.equals(KeyboardProperties.KEY_LIMITED_CHARACTER_LIST)){
                    assertTrue(Test.TRUE, Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key), JsonUtils.readStringListFromJsonObject(underTest, key)));
                } else{
                    assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}