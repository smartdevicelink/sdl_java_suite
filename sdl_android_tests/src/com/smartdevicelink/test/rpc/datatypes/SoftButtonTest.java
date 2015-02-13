package com.smartdevicelink.test.rpc.datatypes;

import java.util.Hashtable;
import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class SoftButtonTest extends TestCase {

	private static final SoftButtonType TYPE = SoftButtonType.SBT_TEXT;
	private static final String TEXT = "text";
	private static final SystemAction SYS_ACTION = SystemAction.DEFAULT_ACTION;
	private static final Image IMAGE = new Image();
	private static final ImageType IMAGE_TYPE = ImageType.DYNAMIC;
	private static final ImageType IMAGE_TYPE_CHANGED = ImageType.STATIC;
	private static final Boolean IS_HIGHLIGHTED = false;
	private static final Integer ID = 0;
	
	private SoftButton msg;

	@Override
	public void setUp() {
		IMAGE.setValue(Image.KEY_VALUE);
		IMAGE.setImageType(IMAGE_TYPE);
		
		msg = new SoftButton();
		
		msg.setType(TYPE);
		msg.setText(TEXT);
		msg.setSystemAction(SYS_ACTION);
		msg.setImage(IMAGE);
		msg.setIsHighlighted(IS_HIGHLIGHTED);
		msg.setSoftButtonID(ID);

	}

	public void testType() {
		SoftButtonType copy = msg.getType();
		
		assertEquals("Input value didn't match expected value.", TYPE, copy);
	}
	
	public void testText() {
		String copy = msg.getText();
		
		assertEquals("Input value didn't match expected value.", TEXT, copy);
	}
	
	public void testSysAction () {
		SystemAction copy = msg.getSystemAction();
		
		assertEquals("Input value didn't match expected value.", SYS_ACTION, copy);
	}
	
	public void testImage () {
		Image copy = msg.getImage();
		
	    assertTrue("Input value didn't match expected value.", Validator.validateImage(IMAGE, copy));
	}
	
    public void testGetImage(){
    	Image copy1 = msg.getImage();
    	copy1.setImageType(IMAGE_TYPE_CHANGED); 
    	Image copy2 = msg.getImage();
    	
    	assertNotSame("Image was not defensive copied", copy1, copy2);
    	assertFalse("Copies have the same values", Validator.validateImage(copy1, copy2));
    }
    
    public void testSetImage(){
    	Image copy1 = msg.getImage();   	
    	msg.setImage(copy1);
    	copy1.setImageType(IMAGE_TYPE_CHANGED);
    	Image copy2 = msg.getImage();
    	
    	assertNotSame("Image was not defensive copied", copy1, copy2);
    	assertFalse("Copies have the same values", Validator.validateImage(copy1, copy2));
    }
	
	public void testIsHighlighted () {
		Boolean copy = msg.getIsHighlighted();
		
		assertEquals("Input value didn't match expected value.", IS_HIGHLIGHTED, copy);
	}
	
	public void testId () {
		Integer copy = msg.getSoftButtonID();
		
		assertEquals("Input value didn't match expected value.", ID, copy);
	}

	public void testJson() {
		JSONObject reference = new JSONObject();
		JSONObject image = new JSONObject();

		try {
			image.put(Image.KEY_IMAGE_TYPE, ImageType.DYNAMIC);
			image.put(Image.KEY_VALUE, "value");
			
			reference.put(SoftButton.KEY_SOFT_BUTTON_ID, ID);
			reference.put(SoftButton.KEY_TYPE, TYPE);
			reference.put(SoftButton.KEY_TEXT, TEXT);
			reference.put(SoftButton.KEY_IMAGE, image);
			reference.put(SoftButton.KEY_SYSTEM_ACTION, SYS_ACTION);
			reference.put(SoftButton.KEY_IS_HIGHLIGHTED, IS_HIGHLIGHTED);

			JSONObject underTest = msg.serializeJSON();

			assertEquals("JSON size didn't match expected size.",
					reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if(key.equals(SoftButton.KEY_IMAGE)){
                    JSONObject referenceArray = JsonUtils.readJsonObjectFromJsonObject(reference, key);
                    JSONObject underTestArray = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
                	Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray);
                	Hashtable<String, Object> hashTest= JsonRPCMarshaller.deserializeJSONObject(underTestArray);
                	
	                assertTrue("JSON value didn't match expected value for key \"" + key + "\"",
	                        Validator.validateImage(new Image(hashReference), new Image(hashTest)));
	            } else {
					assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
							JsonUtils.readObjectFromJsonObject(reference, key),
							JsonUtils.readObjectFromJsonObject(underTest, key));
	            }
			}
		} catch (JSONException e) {
			//do nothing 
		}
	}
	

	public void testNull() {
		SoftButton msg = new SoftButton();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Id wasn't set, but getter method returned an object.", msg.getSoftButtonID());
		assertNull("Image wasn't set, but getter method returned an object.", msg.getImage());
		assertNull("Is highlighted wasn't set, but getter method returned an object.", msg.getIsHighlighted());
		assertNull("System action wasn't set, but getter method returned an object.", msg.getSystemAction());
		assertNull("Text wasn't set, but getter method returned an object.", msg.getText());
		assertNull("Type wasn't set, but getter method returned an object.", msg.getType());
	}
}
