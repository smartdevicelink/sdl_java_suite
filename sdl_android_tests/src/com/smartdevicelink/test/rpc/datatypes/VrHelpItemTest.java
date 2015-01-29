package com.smartdevicelink.test.rpc.datatypes;

import java.util.Hashtable;
import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class VrHelpItemTest extends TestCase {

	private static final String TEXT = "text";
	private static final Image IMAGE = new Image();
	private static final Integer POSITION = 0;
	
	private VrHelpItem msg;

	@Override
	public void setUp() {
		IMAGE.setValue(Image.KEY_VALUE);
		IMAGE.setImageType(ImageType.DYNAMIC);
		
		msg = new VrHelpItem();
		
		msg.setText(TEXT);
		msg.setImage(IMAGE);
		msg.setPosition(POSITION);

	}

	public void testText() {
		String copy = msg.getText();
		
		assertEquals("Input value didn't match expected value.", TEXT, copy);
	}
	
	public void testImage () {
		Image copy = msg.getImage();
		
		assertNotSame("Image was not defensive copied", IMAGE, copy);
	    assertTrue("Input value didn't match expected value.", Validator.validateImage(IMAGE, copy));
	}
	
	public void testPosition () {
		Integer copy = msg.getPosition();
		
		assertEquals("Input value didn't match expected value.", POSITION, copy);
	}
	
	public void testJson() {
		JSONObject reference = new JSONObject();
		JSONObject image = new JSONObject();

		try {
			image.put(Image.KEY_IMAGE_TYPE, ImageType.DYNAMIC);
	        image.put(Image.KEY_VALUE, "value");
	        
			reference.put(VrHelpItem.KEY_IMAGE, image);
			reference.put(VrHelpItem.KEY_TEXT, TEXT);
			reference.put(VrHelpItem.KEY_POSITION, POSITION);

			JSONObject underTest = msg.serializeJSON();

			assertEquals("JSON size didn't match expected size.",
					reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if(key.equals(VrHelpItem.KEY_IMAGE)){
                	JSONObject objectEquals = JsonUtils.readJsonObjectFromJsonObject(reference, key);
                	JSONObject testEquals = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
                	Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                	Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                	
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
		VrHelpItem msg = new VrHelpItem();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Image wasn't set, but getter method returned an object.", msg.getImage());
		assertNull("Text wasn't set, but getter method returned an object.", msg.getText());
		assertNull("Position wasn't set, but getter method returned an object.", msg.getPosition());

	}
}
