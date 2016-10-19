package com.smartdevicelink.test.rpc.datatypes;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.Choice}
 */
public class ChoiceTests extends TestCase{
	
    private Choice msg;

    @Override
    public void setUp(){
        msg = new Choice();

        msg.setChoiceID(Test.GENERAL_INT);
        msg.setMenuName(Test.GENERAL_STRING);
        msg.setImage(Test.GENERAL_IMAGE);
        msg.setSecondaryImage(Test.GENERAL_IMAGE);
        msg.setSecondaryText(Test.GENERAL_STRING);
        msg.setTertiaryText(Test.GENERAL_STRING);
        msg.setVrCommands(Test.GENERAL_STRING_LIST);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        String text3 = msg.getTertiaryText();
        String text2 = msg.getSecondaryText();
        String menuName = msg.getMenuName();
        int choiceId = msg.getChoiceID();
        List<String> vrCommands = msg.getVrCommands();
        Image image2 = msg.getSecondaryImage();
        Image image = msg.getImage();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STRING, text3);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, text2);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, menuName);
        assertEquals(Test.MATCH, Test.GENERAL_INT, choiceId);
        assertTrue(Test.TRUE, Validator.validateStringList(Test.GENERAL_STRING_LIST, vrCommands));
        assertTrue(Test.TRUE, Validator.validateImage(Test.GENERAL_IMAGE, image2));
        assertTrue(Test.TRUE, Validator.validateImage(Test.GENERAL_IMAGE, image));
        
        // Invalid/Null Tests
        Choice msg = new Choice();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getChoiceID());
        assertNull(Test.NULL, msg.getImage());
        assertNull(Test.NULL, msg.getSecondaryImage());
        assertNull(Test.NULL, msg.getMenuName());
        assertNull(Test.NULL, msg.getSecondaryText());
        assertNull(Test.NULL, msg.getTertiaryText());
        assertNull(Test.NULL, msg.getVrCommands());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(Choice.KEY_CHOICE_ID, Test.GENERAL_INT);
            reference.put(Choice.KEY_MENU_NAME, Test.GENERAL_STRING);
            reference.put(Choice.KEY_SECONDARY_TEXT, Test.GENERAL_STRING);
            reference.put(Choice.KEY_TERTIARY_TEXT, Test.GENERAL_STRING);
            reference.put(Choice.KEY_IMAGE, Test.JSON_IMAGE);
            reference.put(Choice.KEY_SECONDARY_IMAGE, Test.JSON_IMAGE);
            reference.put(Choice.KEY_VR_COMMANDS, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));
            
            JSONObject underTest = msg.serializeJSON();

            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                if(key.equals(Choice.KEY_VR_COMMANDS)){
                    assertTrue(Test.TRUE,
                            Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key),
                                    JsonUtils.readStringListFromJsonObject(underTest, key)));
                } else if(key.equals(Choice.KEY_IMAGE) || key.equals(Choice.KEY_SECONDARY_IMAGE)){
                	JSONObject objectEquals = JsonUtils.readJsonObjectFromJsonObject(reference, key);
                	JSONObject testEquals = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
                	Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                	Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                	
                    assertTrue(Test.TRUE, Validator.validateImage(new Image(hashReference), new Image(hashTest)));
                } else{
                    assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}