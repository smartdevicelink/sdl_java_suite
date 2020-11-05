package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.MetadataTags;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

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
    private MetadataTags msg2;
    private List<MetadataType> exampleList;

    @Override
    public void setUp() {
        // Create List for Testing
        exampleList = new ArrayList<>();
        exampleList.add(0, MetadataType.CURRENT_TEMPERATURE);
        exampleList.add(1, MetadataType.MEDIA_ALBUM);
        exampleList.add(2, MetadataType.MEDIA_ARTIST);

        msg = new MetadataTags();
        msg.setMainField1(exampleList);
        msg.setMainField2(exampleList);
        msg.setMainField3(exampleList);
        msg.setMainField4(exampleList);

        // Setup without a list
        msg2 = new MetadataTags();
        msg2.setMainField1(MetadataType.CURRENT_TEMPERATURE);
        msg2.setMainField2(MetadataType.HUMIDITY);
        msg2.setMainField3(MetadataType.MAXIMUM_TEMPERATURE);
        msg2.setMainField4(MetadataType.MEDIA_ALBUM);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        List<MetadataType> mainField1Types = msg.getMainField1();
        List<MetadataType> mainField2Types = msg.getMainField2();
        List<MetadataType> mainField3Types = msg.getMainField3();
        List<MetadataType> mainField4Types = msg.getMainField4();

        // Valid Tests
        assertEquals(TestValues.MATCH, exampleList, mainField1Types);
        assertEquals(TestValues.MATCH, exampleList, mainField2Types);
        assertEquals(TestValues.MATCH, exampleList, mainField3Types);
        assertEquals(TestValues.MATCH, exampleList, mainField4Types);

        // Test metadata set without a list
        mainField1Types = msg2.getMainField1();
        mainField2Types = msg2.getMainField2();
        mainField3Types = msg2.getMainField3();
        mainField4Types = msg2.getMainField4();

        // Valid Tests
        assertEquals(TestValues.MATCH, MetadataType.CURRENT_TEMPERATURE, mainField1Types.get(0));
        assertEquals(TestValues.MATCH, MetadataType.HUMIDITY, mainField2Types.get(0));
        assertEquals(TestValues.MATCH, MetadataType.MAXIMUM_TEMPERATURE, mainField3Types.get(0));
        assertEquals(TestValues.MATCH, MetadataType.MEDIA_ALBUM, mainField4Types.get(0));

        // Invalid/Null Tests
        MetadataTags msg = new MetadataTags();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getMainField1());
        assertNull(TestValues.NULL, msg.getMainField2());
        assertNull(TestValues.NULL, msg.getMainField3());
        assertNull(TestValues.NULL, msg.getMainField4());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(MetadataTags.KEY_MAIN_FIELD_1_TYPE, TestValues.JSON_TEXTFIELDTYPES);
            reference.put(MetadataTags.KEY_MAIN_FIELD_2_TYPE, TestValues.JSON_TEXTFIELDTYPES);
            reference.put(MetadataTags.KEY_MAIN_FIELD_3_TYPE, TestValues.JSON_TEXTFIELDTYPES);
            reference.put(MetadataTags.KEY_MAIN_FIELD_4_TYPE, TestValues.JSON_TEXTFIELDTYPES);

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
