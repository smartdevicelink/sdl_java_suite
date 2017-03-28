package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.TextField}
 */
public class TextFieldTest extends TestCase {
    
	private TextField msg;

	@Override
	public void setUp() {
		msg = new TextField();
		
		msg.setName(Test.GENERAL_TEXTFIELDNAME);
		msg.setCharacterSet(Test.GENERAL_CHARACTERSET);
		msg.setWidth(Test.GENERAL_INT);
		msg.setRows(Test.GENERAL_INT);
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
		assertEquals(Test.MATCH, Test.GENERAL_TEXTFIELDNAME, name);
		assertEquals(Test.MATCH, Test.GENERAL_CHARACTERSET, charSet);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, width);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, rows);
		
		// Invalid/Null Tests
		TextField msg = new TextField();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getName());
		assertNull(Test.NULL, msg.getWidth());
		assertNull(Test.NULL, msg.getRows());
		assertNull(Test.NULL, msg.getCharacterSet());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(TextField.KEY_CHARACTER_SET, Test.GENERAL_CHARACTERSET);
			reference.put(TextField.KEY_WIDTH, Test.GENERAL_INT);
			reference.put(TextField.KEY_ROWS, Test.GENERAL_INT);
			reference.put(TextField.KEY_NAME, Test.GENERAL_TEXTFIELDNAME);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}