package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.test.utils.JsonUtils;

public class TextFieldTest extends TestCase {

	private static final TextFieldName NAME = TextFieldName.ETA;
	private static final CharacterSet CHARACTER_SET = CharacterSet.CID1SET;
	private static final Integer WIDTH = 0;
	private static final Integer ROWS = 0;
    
	private TextField msg;

	@Override
	public void setUp() {
		msg = new TextField();
		
		msg.setName(NAME);
		msg.setCharacterSet(CHARACTER_SET);
		msg.setWidth(WIDTH);
		msg.setRows(ROWS);
	}

	public void testName() {
		TextFieldName copy = msg.getName();
		
		assertEquals("Input value didn't match expected value.", NAME, copy);
	}
	
	public void testCharacterSet () {
		CharacterSet copy = msg.getCharacterSet();
		
		assertEquals("Input value didn't match expected value.", CHARACTER_SET, copy);
	}
	
	public void testWidth () {
		Integer copy = msg.getWidth();
		
		assertEquals("Input value didn't match expected value.", WIDTH, copy);
	}
	
	public void testRows () {
		Integer copy = msg.getRows();
		
		assertEquals("Input value didn't match expected value.", ROWS, copy);
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(TextField.KEY_CHARACTER_SET, CHARACTER_SET);
			reference.put(TextField.KEY_WIDTH, WIDTH);
			reference.put(TextField.KEY_ROWS, ROWS);
			reference.put(TextField.KEY_NAME, NAME);

			JSONObject underTest = msg.serializeJSON();

			assertEquals("JSON size didn't match expected size.",
					reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				assertEquals(
						"JSON value didn't match expected value for key \""
								+ key + "\".",
						JsonUtils.readObjectFromJsonObject(reference, key),
						JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch (JSONException e) {
			/* do nothing */
		}
	}

	public void testNull() {
		TextField msg = new TextField();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Name wasn't set, but getter method returned an object.", msg.getName());
		assertNull("Width wasn't set, but getter method returned an object.", msg.getWidth());
		assertNull("Rows wasn't set, but getter method returned an object.", msg.getRows());
		assertNull("Character set wasn't set, but getter method returned an object.", msg.getCharacterSet());
	}
}
