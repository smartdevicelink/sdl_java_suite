package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.Choice}
 */
public class ChoiceTests extends TestCase{
	
    private Choice msg;

    @Override
    public void setUp(){
        msg = new Choice();

        msg.setChoiceID(TestValues.GENERAL_INT);
        msg.setMenuName(TestValues.GENERAL_STRING);
        msg.setImage(TestValues.GENERAL_IMAGE);
        msg.setSecondaryImage(TestValues.GENERAL_IMAGE);
        msg.setSecondaryText(TestValues.GENERAL_STRING);
        msg.setTertiaryText(TestValues.GENERAL_STRING);
        msg.setVrCommands(TestValues.GENERAL_STRING_LIST);
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
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, text3);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, text2);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, menuName);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, choiceId);
        assertTrue(TestValues.TRUE, Validator.validateStringList(TestValues.GENERAL_STRING_LIST, vrCommands));
        assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, image2));
        assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, image));
        
        // Invalid/Null Tests
        Choice msg = new Choice();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getChoiceID());
        assertNull(TestValues.NULL, msg.getImage());
        assertNull(TestValues.NULL, msg.getSecondaryImage());
        assertNull(TestValues.NULL, msg.getMenuName());
        assertNull(TestValues.NULL, msg.getSecondaryText());
        assertNull(TestValues.NULL, msg.getTertiaryText());
        assertNull(TestValues.NULL, msg.getVrCommands());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(Choice.KEY_CHOICE_ID, TestValues.GENERAL_INT);
            reference.put(Choice.KEY_MENU_NAME, TestValues.GENERAL_STRING);
            reference.put(Choice.KEY_SECONDARY_TEXT, TestValues.GENERAL_STRING);
            reference.put(Choice.KEY_TERTIARY_TEXT, TestValues.GENERAL_STRING);
            reference.put(Choice.KEY_IMAGE, TestValues.JSON_IMAGE);
            reference.put(Choice.KEY_SECONDARY_IMAGE, TestValues.JSON_IMAGE);
            reference.put(Choice.KEY_VR_COMMANDS, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
            
            JSONObject underTest = msg.serializeJSON();

            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                if(key.equals(Choice.KEY_VR_COMMANDS)){
                    assertTrue(TestValues.TRUE,
                            Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key),
                                    JsonUtils.readStringListFromJsonObject(underTest, key)));
                } else if(key.equals(Choice.KEY_IMAGE) || key.equals(Choice.KEY_SECONDARY_IMAGE)){
                	JSONObject objectEquals = JsonUtils.readJsonObjectFromJsonObject(reference, key);
                	JSONObject testEquals = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
                	Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                	Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                	
                    assertTrue(TestValues.TRUE, Validator.validateImage(new Image(hashReference), new Image(hashTest)));
                } else{
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
    }
}