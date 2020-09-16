package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.SoftButton}
 */
public class SoftButtonTest extends TestCase {
	
	private SoftButton msg;

	@Override
	public void setUp() {
		msg = new SoftButton();
		
		msg.setType(TestValues.GENERAL_SOFTBUTTONTYPE);
		msg.setText(TestValues.GENERAL_STRING);
		msg.setSystemAction(TestValues.GENERAL_SYSTEMACTION);
		msg.setImage(TestValues.GENERAL_IMAGE);
		msg.setIsHighlighted(TestValues.GENERAL_BOOLEAN);
		msg.setSoftButtonID(TestValues.GENERAL_INT);
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
		assertEquals(TestValues.MATCH, TestValues.GENERAL_SOFTBUTTONTYPE, type);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, text);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_SYSTEMACTION, sysAction);
	    assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, image));
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, isHighlighted);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, id);
		
		// Invalid/Null Tests
		SoftButton msg = new SoftButton();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getSoftButtonID());
		assertNull(TestValues.NULL, msg.getImage());
		assertNull(TestValues.NULL, msg.getIsHighlighted());
		assertNull(TestValues.NULL, msg.getSystemAction());
		assertNull(TestValues.NULL, msg.getText());
		assertNull(TestValues.NULL, msg.getType());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {			
			reference.put(SoftButton.KEY_SOFT_BUTTON_ID, TestValues.GENERAL_INT);
			reference.put(SoftButton.KEY_TYPE, TestValues.GENERAL_SOFTBUTTONTYPE);
			reference.put(SoftButton.KEY_TEXT, TestValues.GENERAL_STRING);
			reference.put(SoftButton.KEY_IMAGE, TestValues.JSON_IMAGE);
			reference.put(SoftButton.KEY_SYSTEM_ACTION, TestValues.GENERAL_SYSTEMACTION);
			reference.put(SoftButton.KEY_IS_HIGHLIGHTED, TestValues.GENERAL_BOOLEAN);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if(key.equals(SoftButton.KEY_IMAGE)){
                    JSONObject referenceArray = JsonUtils.readJsonObjectFromJsonObject(reference, key);
                    JSONObject underTestArray = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
                	Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray);
                	Hashtable<String, Object> hashTest= JsonRPCMarshaller.deserializeJSONObject(underTestArray);
                	assertTrue(TestValues.TRUE, Validator.validateImage(new Image(hashReference), new Image(hashTest)));
	            } else {
					assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
	            }
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}