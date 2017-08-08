package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.enums.TextFieldType;
import com.smartdevicelink.proxy.rpc.MetadataStruct;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * This is a unit test class for the SmartDeviceLink library project class:
 * {@link com.smartdevicelink.proxy.rpc.MetadataStruct}
 */

public class MetadataStructTests extends TestCase {

	private MetadataStruct msg;
	private List<TextFieldType> exampleList;

	@Override
	public void setUp() {
		// Create List for Testing
		exampleList = new ArrayList<>();
		exampleList.add(0, TextFieldType.CURRENT_TEMPERATURE);
		exampleList.add(1, TextFieldType.MEDIA_ALBUM);
		exampleList.add(2, TextFieldType.MEDIA_ARTIST);

		msg = new MetadataStruct();

		msg.setMainField1(exampleList);
		msg.setMainField2(exampleList);
		msg.setMainField3(exampleList);
		msg.setMainField4(exampleList);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		List<TextFieldType> mainField1Types = msg.getMainField1();
		List<TextFieldType> mainField2Types = msg.getMainField2();
		List<TextFieldType> mainField3Types = msg.getMainField3();
		List<TextFieldType> mainField4Types = msg.getMainField4();

		// Valid Tests
		assertEquals(Test.MATCH, exampleList, mainField1Types);
		assertEquals(Test.MATCH, exampleList, mainField2Types);
		assertEquals(Test.MATCH, exampleList, mainField3Types);
		assertEquals(Test.MATCH, exampleList, mainField4Types);

		// Invalid/Null Tests
		MetadataStruct msg = new MetadataStruct();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getMainField1());
		assertNull(Test.NULL, msg.getMainField2());
		assertNull(Test.NULL, msg.getMainField3());
		assertNull(Test.NULL, msg.getMainField4());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(MetadataStruct.KEY_MAIN_FIELD_1_TYPE, Test.JSON_TEXTFIELDTYPES);
			reference.put(MetadataStruct.KEY_MAIN_FIELD_2_TYPE, Test.JSON_TEXTFIELDTYPES);
			reference.put(MetadataStruct.KEY_MAIN_FIELD_3_TYPE, Test.JSON_TEXTFIELDTYPES);
			reference.put(MetadataStruct.KEY_MAIN_FIELD_4_TYPE, Test.JSON_TEXTFIELDTYPES);

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
