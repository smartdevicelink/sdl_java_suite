package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.MetadataTags;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
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
 * {@link MetadataTags}
 */

public class MetadataTagsTests extends TestCase {

	private MetadataTags msg;
	private List<MetadataType> exampleList;

	@Override
	public void setUp() {
		// Create List for Testing
		exampleList = new ArrayList<>();
		exampleList.add(0, MetadataType.currentTemperature);
		exampleList.add(1, MetadataType.mediaAlbum);
		exampleList.add(2, MetadataType.mediaArtist);

		msg = new MetadataTags();

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
		List<MetadataType> mainField1Types = msg.getMainField1();
		List<MetadataType> mainField2Types = msg.getMainField2();
		List<MetadataType> mainField3Types = msg.getMainField3();
		List<MetadataType> mainField4Types = msg.getMainField4();

		// Valid Tests
		assertEquals(Test.MATCH, exampleList, mainField1Types);
		assertEquals(Test.MATCH, exampleList, mainField2Types);
		assertEquals(Test.MATCH, exampleList, mainField3Types);
		assertEquals(Test.MATCH, exampleList, mainField4Types);

		// Invalid/Null Tests
		MetadataTags msg = new MetadataTags();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getMainField1());
		assertNull(Test.NULL, msg.getMainField2());
		assertNull(Test.NULL, msg.getMainField3());
		assertNull(Test.NULL, msg.getMainField4());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(MetadataTags.KEY_MAIN_FIELD_1_TYPE, Test.JSON_TEXTFIELDTYPES);
			reference.put(MetadataTags.KEY_MAIN_FIELD_2_TYPE, Test.JSON_TEXTFIELDTYPES);
			reference.put(MetadataTags.KEY_MAIN_FIELD_3_TYPE, Test.JSON_TEXTFIELDTYPES);
			reference.put(MetadataTags.KEY_MAIN_FIELD_4_TYPE, Test.JSON_TEXTFIELDTYPES);

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
