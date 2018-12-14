package com.smartdevicelink.test.rpc.datatypes;

import java.util.Hashtable;
import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SoftButton}
 */
public class SoftButtonTest extends TestCase {
	
	private SoftButton msg;

	@Override
	public void setUp() {
		msg = new SoftButton();
		
		msg.setType(Test.GENERAL_SOFTBUTTONTYPE);
		msg.setText(Test.GENERAL_STRING);
		msg.setSystemAction(Test.GENERAL_SYSTEMACTION);
		msg.setImage(Test.GENERAL_IMAGE);
		msg.setIsHighlighted(Test.GENERAL_BOOLEAN);
		msg.setSoftButtonID(Test.GENERAL_INT);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		SoftButtonType type = msg.getType();
		String text = msg.getText();
		SystemAction sysAction = msg.getSystemAction();
		Image image = msg.getImage();
		Boolean isHighlighted = msg.getIsHighlighted();
		Integer id = msg.getSoftButtonID();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_SOFTBUTTONTYPE, type);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, text);
		assertEquals(Test.MATCH, Test.GENERAL_SYSTEMACTION, sysAction);
	    assertTrue(Test.TRUE, Validator.validateImage(Test.GENERAL_IMAGE, image));
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, isHighlighted);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, id);
		
		// Invalid/Null Tests
		SoftButton msg = new SoftButton();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getSoftButtonID());
		assertNull(Test.NULL, msg.getImage());
		assertNull(Test.NULL, msg.getIsHighlighted());
		assertNull(Test.NULL, msg.getSystemAction());
		assertNull(Test.NULL, msg.getText());
		assertNull(Test.NULL, msg.getType());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {			
			reference.put(SoftButton.KEY_SOFT_BUTTON_ID, Test.GENERAL_INT);
			reference.put(SoftButton.KEY_TYPE, Test.GENERAL_SOFTBUTTONTYPE);
			reference.put(SoftButton.KEY_TEXT, Test.GENERAL_STRING);
			reference.put(SoftButton.KEY_IMAGE, Test.JSON_IMAGE);
			reference.put(SoftButton.KEY_SYSTEM_ACTION, Test.GENERAL_SYSTEMACTION);
			reference.put(SoftButton.KEY_IS_HIGHLIGHTED, Test.GENERAL_BOOLEAN);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if(key.equals(SoftButton.KEY_IMAGE)){
                    JSONObject referenceArray = JsonUtils.readJsonObjectFromJsonObject(reference, key);
                    JSONObject underTestArray = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
                	Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray);
                	Hashtable<String, Object> hashTest= JsonRPCMarshaller.deserializeJSONObject(underTestArray);
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