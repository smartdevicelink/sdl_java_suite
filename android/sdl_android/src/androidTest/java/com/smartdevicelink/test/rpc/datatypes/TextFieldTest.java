package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.TextField}
 */
public class TextFieldTest extends TestCase {
    
	private TextField msg;

	@Override
	public void setUp() {
		msg = new TextField();
		
		msg.setName(TestValues.GENERAL_TEXTFIELDNAME);
		msg.setCharacterSet(TestValues.GENERAL_CHARACTERSET);
		msg.setWidth(TestValues.GENERAL_INT);
		msg.setRows(TestValues.GENERAL_INT);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		TextFieldName name = msg.getName();
		CharacterSet charSet = msg.getCharacterSet();
		Integer width = msg.getWidth();
		Integer rows = msg.getRows();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_TEXTFIELDNAME, name);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_CHARACTERSET, charSet);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, width);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, rows);
		
		// Invalid/Null Tests
		TextField msg = new TextField();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getName());
		assertNull(TestValues.NULL, msg.getWidth());
		assertNull(TestValues.NULL, msg.getRows());
		assertNull(TestValues.NULL, msg.getCharacterSet());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(TextField.KEY_CHARACTER_SET, TestValues.GENERAL_CHARACTERSET);
			reference.put(TextField.KEY_WIDTH, TestValues.GENERAL_INT);
			reference.put(TextField.KEY_ROWS, TestValues.GENERAL_INT);
			reference.put(TextField.KEY_NAME, TestValues.GENERAL_TEXTFIELDNAME);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}