package com.smartdevicelink.test.rpc.datatypes;

import java.util.Hashtable;
import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

public class VrHelpItemTest extends TestCase {
	
	private VrHelpItem msg;

	@Override
	public void setUp() {		
		msg = new VrHelpItem();
		
		msg.setText(Test.GENERAL_STRING);
		msg.setImage(Test.GENERAL_IMAGE);
		msg.setPosition(Test.GENERAL_INT);

	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		String text = msg.getText();
		Image image = msg.getImage();
		Integer position = msg.getPosition();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, text);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, position);
		assertTrue(Test.TRUE, Validator.validateImage(Test.GENERAL_IMAGE, image));
		
		// Invalid/Null Tests
		VrHelpItem msg = new VrHelpItem();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getImage());
		assertNull(Test.NULL, msg.getText());
		assertNull(Test.NULL, msg.getPosition());
	}
	
	public void testJson() {
		JSONObject reference = new JSONObject();

		try {	        
			reference.put(VrHelpItem.KEY_IMAGE, Test.JSON_IMAGE);
			reference.put(VrHelpItem.KEY_TEXT, Test.GENERAL_STRING);
			reference.put(VrHelpItem.KEY_POSITION, Test.GENERAL_INT);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if(key.equals(VrHelpItem.KEY_IMAGE)){
                	JSONObject objectEquals = JsonUtils.readJsonObjectFromJsonObject(reference, key);
                	JSONObject testEquals = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
                	Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                	Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                	
	                assertTrue(Test.TRUE, Validator.validateImage(new Image(hashReference), new Image(hashTest)));
	            } else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
	            }
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}