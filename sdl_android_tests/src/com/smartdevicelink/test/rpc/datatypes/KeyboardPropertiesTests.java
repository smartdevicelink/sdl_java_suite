package com.smartdevicelink.test.rpc.datatypes;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class KeyboardPropertiesTests extends TestCase{

    private static final String         AUTO_COMPLETE_TEXT 			= "ducking";
    private static final Language       LANGUAGE           			= Language.AR_SA;
    private static final KeyboardLayout KEYBOARD_LAYOUT    			= KeyboardLayout.QWERTY;
    private static final KeypressMode   KEYPRESS_MODE      			= KeypressMode.SINGLE_KEYPRESS;
    private final List<String>   LIMITED_CHAR_LIST  			= Arrays.asList(new String[] { "R", "S", "T", "L", "N", "E" });
    private static final String 		LIMITED_CHAR_ITEM_CHANGED 	= "O";

    private KeyboardProperties          msg;

    @Override
    public void setUp(){
        msg = new KeyboardProperties();

        msg.setAutoCompleteText(AUTO_COMPLETE_TEXT);
        msg.setKeyboardLayout(KEYBOARD_LAYOUT);
        msg.setKeypressMode(KEYPRESS_MODE);
        msg.setLanguage(LANGUAGE);
        msg.setLimitedCharacterList(LIMITED_CHAR_LIST);
    }

    public void testAutoCompleteText(){
        String copy = msg.getAutoCompleteText();
        assertEquals("Input value didn't match expected value.", AUTO_COMPLETE_TEXT, copy);
    }

    public void testKeyboardLayout(){
        KeyboardLayout copy = msg.getKeyboardLayout();
        assertEquals("Input value didn't match expected value.", KEYBOARD_LAYOUT, copy);
    }

    public void testKeypressMode(){
        KeypressMode copy = msg.getKeypressMode();
        assertEquals("Input value didn't match expected value.", KEYPRESS_MODE, copy);
    }

    public void testLanguage(){
        Language copy = msg.getLanguage();
        assertEquals("Input value didn't match expected value.", LANGUAGE, copy);
    }

    public void testLimitedCharacterList(){
        List<String> copy = msg.getLimitedCharacterList();

        assertEquals("Input value size didn't match expected size.", LIMITED_CHAR_LIST.size(), copy.size());
        assertTrue("Input value didn't match expected value.", Validator.validateStringList(LIMITED_CHAR_LIST, copy));
    }
    
    public void testGetLimitedCharacterList() {
    	List<String> copy1 = msg.getLimitedCharacterList();
    	copy1.set(0, LIMITED_CHAR_ITEM_CHANGED);
    	List<String> copy2 = msg.getLimitedCharacterList();
    	
    	assertNotSame("Limited character list was not defensive copied", copy1, copy2);
    	assertFalse("Copies have the same values", Validator.validateStringList(copy1, copy2));
    }
    
    public void testSetLimitedCharacterList() {
    	List<String> copy1 = msg.getLimitedCharacterList();
    	msg.setLimitedCharacterList(copy1);
    	copy1.set(0, LIMITED_CHAR_ITEM_CHANGED);
    	List<String> copy2 = msg.getLimitedCharacterList();
    	
    	assertNotSame("Limited character list was not defensive copied", copy1, copy2);
    	assertFalse("Copies have the same values", Validator.validateStringList(copy1, copy2));
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(KeyboardProperties.KEY_AUTO_COMPLETE_TEXT, AUTO_COMPLETE_TEXT);
            reference.put(KeyboardProperties.KEY_KEYBOARD_LAYOUT, KEYBOARD_LAYOUT);
            reference.put(KeyboardProperties.KEY_KEYPRESS_MODE, KEYPRESS_MODE);
            reference.put(KeyboardProperties.KEY_LANGUAGE, LANGUAGE);
            reference.put(KeyboardProperties.KEY_LIMITED_CHARACTER_LIST,
                    JsonUtils.createJsonArray(LIMITED_CHAR_LIST));

            JSONObject underTest = msg.serializeJSON();

            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                if(key.equals(KeyboardProperties.KEY_LIMITED_CHARACTER_LIST)){
                    assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
                            Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key),
                                    JsonUtils.readStringListFromJsonObject(underTest, key)));
                }
                else{
                    assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
                            JsonUtils.readObjectFromJsonObject(reference, key),
                            JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        }catch(JSONException e){
            /* do nothing */
        }
    }

    public void testNull(){
        KeyboardProperties msg = new KeyboardProperties();
        assertNotNull("Null object creation failed.", msg);

        assertNull("AutoComplete text wasn't set, but getter method returned an object.", msg.getAutoCompleteText());
        assertNull("Language wasn't set, but getter method returned an object.", msg.getLanguage());
        assertNull("Keyboard layout wasn't set, but getter method returned an object.", msg.getKeyboardLayout());
        //KeypressMode is set in the default constructor
        assertNotNull("Keypress mode is set, but getter method didn't return an object.", msg.getKeypressMode());
        assertNull("Limited character set wasn't set, but getter method returned an object.",
                msg.getLimitedCharacterList());
    }
}
