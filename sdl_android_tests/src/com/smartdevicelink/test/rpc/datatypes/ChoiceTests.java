package com.smartdevicelink.test.rpc.datatypes;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;


public class ChoiceTests extends TestCase{

    //private static final String TAG = "ChoiceTests";
    
    private static final ImageType IMAGE_TYPE     		= ImageType.DYNAMIC;
    private static final ImageType IMAGE_TYPE_CHANGED    = ImageType.STATIC;
    private static final String    IMAGE_NAME     		= "image1.png";
    private static final ImageType IMAGE2_TYPE    		= ImageType.DYNAMIC;
    private static final ImageType IMAGE2_TYPE_CHANGED  = ImageType.STATIC;
    private static final String    IMAGE2_NAME    		= "image2.png";
    private static final String    VR_COMMAND1    		= "Say a command";
    private static final String    VR_COMMAND2    		= "Voice Rec";
    private static final String    VR_COMMAND3    		= "PERFORM COMMAND";
    private static final String    VR_COMMAND_CHANGED   = "Unexpected command";
    private static final int       CHOICE_ID     		= 16135;
    private static final String    MENU_NAME      		= "Choice Line #1";
    private static final String    SECONDARY_TEXT 		= "Choice Line #2";
    private static final String    TERTIARY_TEXT  		= "Choice Line #3";

    private Choice                 msg;

    private Image                  image1, image2;
    private List<String>           vrCommands;

    @Override
    public void setUp(){
        msg = new Choice();

        image1 = new Image();
        image1.setImageType(IMAGE_TYPE);
        image1.setValue(IMAGE_NAME);

        image2 = new Image();
        image2.setImageType(IMAGE2_TYPE);
        image2.setValue(IMAGE2_NAME);

        vrCommands = new ArrayList<String>(3);
        vrCommands.add(VR_COMMAND1);
        vrCommands.add(VR_COMMAND2);
        vrCommands.add(VR_COMMAND3);

        msg.setChoiceID(CHOICE_ID);
        msg.setMenuName(MENU_NAME);
        msg.setImage(image1);
        msg.setSecondaryImage(image2);
        msg.setSecondaryText(SECONDARY_TEXT);
        msg.setTertiaryText(TERTIARY_TEXT);
        msg.setVrCommands(vrCommands);
    }

    public void testImage1(){
        Image copy = msg.getImage();
        
        assertTrue("Input value didn't match expected value.", Validator.validateImage(image1, copy));
    }
    
    public void testGetImage1(){
    	Image copy1 = msg.getImage();
    	copy1.setImageType(IMAGE_TYPE_CHANGED); 
    	Image copy2 = msg.getImage();
    	
    	assertNotSame("Image was not defensive copied", copy1, copy2);
    	assertFalse("Copies have the same values", Validator.validateImage(copy1, copy2));
    }
    
    public void testSetImage1(){
    	Image copy1 = msg.getImage();   	
    	msg.setImage(copy1);
    	copy1.setImageType(IMAGE_TYPE_CHANGED);
    	Image copy2 = msg.getImage();
    	
    	assertNotSame("Image was not defensive copied", copy1, copy2);
    	assertFalse("Copies have the same values", Validator.validateImage(copy1, copy2));
    }

    public void testImage2(){
        Image copy = msg.getSecondaryImage();
        
        assertTrue("Input value didn't match expected value.", Validator.validateImage(image2, copy));
    }
    
	public void testGetImage2(){
		Image copy1 = msg.getSecondaryImage();
		copy1.setImageType(IMAGE2_TYPE_CHANGED);
		Image copy2 = msg.getSecondaryImage();
		
		assertNotSame("Secondary image was not defensive copied", copy1, copy2);
		assertFalse("Copies have the same values", Validator.validateImage(copy1, copy2));
	}
	
	public void testSetImage2(){
		Image copy1 = msg.getSecondaryImage();
		msg.setSecondaryImage(copy1);
		copy1.setImageType(IMAGE2_TYPE_CHANGED);
		Image copy2 = msg.getSecondaryImage();
		
		assertNotSame("Secondary image was not defensive copied", copy1, copy2);
		assertFalse("Copies have the same values", Validator.validateImage(copy1, copy2));
	}

    public void testVrCommands(){
        List<String> copy = msg.getVrCommands();
        assertTrue("Input value didn't match expected value.", Validator.validateStringList(vrCommands, copy));
    }
    
	public void testGetVrCommands(){
		List<String> copy1 = msg.getVrCommands();
		copy1.set(0, VR_COMMAND_CHANGED);
		List<String> copy2 = msg.getVrCommands();
		
		assertNotSame("VR commands were not defensive copied", copy1, copy2);
		assertFalse("Copies have the same values", Validator.validateStringList(copy1, copy2));
	}
	
	public void testSetVrCommands(){
		List<String> copy1 = msg.getVrCommands();
		msg.setVrCommands(copy1);
		copy1.set(0, VR_COMMAND_CHANGED);
		List<String> copy2 = msg.getVrCommands();
		
		assertNotSame("VR commands were not defensive copied", copy1, copy2);
		assertFalse("Copies have the same values", Validator.validateStringList(copy1, copy2));
	}

    public void testChoiceID(){
        int copy = msg.getChoiceID();
        assertEquals("Input value didn't match expected value.", CHOICE_ID, copy);
    }

    public void testMenuName(){
        String copy = msg.getMenuName();
        assertEquals("Input value didn't match expected value.", MENU_NAME, copy);
    }

    public void testSecondaryText(){
        String copy = msg.getSecondaryText();
        assertEquals("Input value didn't match expected value.", SECONDARY_TEXT, copy);
    }

    public void testTertiaryText(){
        String copy = msg.getTertiaryText();
        assertEquals("Input value didn't match expected value.", TERTIARY_TEXT, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();
        JSONObject image1 = new JSONObject(), image2 = new JSONObject();

        try{
            image1.put(Image.KEY_IMAGE_TYPE, IMAGE_TYPE);
            image1.put(Image.KEY_VALUE, IMAGE_NAME);

            image2.put(Image.KEY_IMAGE_TYPE, IMAGE2_TYPE);
            image2.put(Image.KEY_VALUE, IMAGE2_NAME);

            reference.put(Choice.KEY_CHOICE_ID, CHOICE_ID);
            reference.put(Choice.KEY_MENU_NAME, MENU_NAME);
            reference.put(Choice.KEY_SECONDARY_TEXT, SECONDARY_TEXT);
            reference.put(Choice.KEY_TERTIARY_TEXT, TERTIARY_TEXT);
            reference.put(Choice.KEY_IMAGE, image1);
            reference.put(Choice.KEY_SECONDARY_IMAGE, image2);
            reference.put(Choice.KEY_VR_COMMANDS, JsonUtils.createJsonArray(vrCommands));
            
            //log("reference object created:  " + reference.toString());

            JSONObject underTest = msg.serializeJSON();
            //log("object under test created: " + underTest.toString());

            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                if(key.equals(Choice.KEY_VR_COMMANDS)){
                    assertTrue("JSON value didn't match expected value for key \"" + key + "\"",
                            Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key),
                                    JsonUtils.readStringListFromJsonObject(underTest, key)));
                }
                else if(key.equals(Choice.KEY_IMAGE) || key.equals(Choice.KEY_SECONDARY_IMAGE)){
                	JSONObject objectEquals = JsonUtils.readJsonObjectFromJsonObject(reference, key);
                	JSONObject testEquals = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
                	Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                	Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                	
                    assertTrue("JSON value didn't match expected value for key \"" + key + "\"",
                            Validator.validateImage(new Image(hashReference), new Image(hashTest)));
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
        Choice msg = new Choice();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Choice ID wasn't set, but getter method returned an object.", msg.getChoiceID());
        assertNull("Image wasn't set, but getter method returned an object.", msg.getImage());
        assertNull("Secondary image wasn't set, but getter method returned an object.", msg.getSecondaryImage());
        assertNull("Menu name wasn't set, but getter method returned an object.", msg.getMenuName());
        assertNull("Secondary text wasn't set, but getter method returned an object.", msg.getSecondaryText());
        assertNull("Tertiary text wasn't set, but getter method returned an object.", msg.getTertiaryText());
        assertNull("VR commands weren't set, but getter method returned an object.", msg.getVrCommands());
    }
}
