package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.DynamicUpdateCapabilities;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.DynamicUpdateCapabilities}
 */
public class DynamicUpdateCapabilitiesTests extends TestCase {

    private DynamicUpdateCapabilities msg;

    @Override
    protected void setUp() throws Exception {
        msg = new DynamicUpdateCapabilities();

        msg.setSupportsDynamicSubMenus(TestValues.GENERAL_BOOLEAN);
        msg.setSupportedDynamicImageFieldNames(Collections.singletonList(TestValues.GENERAL_IMAGEFIELDNAME));
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        boolean supportDynamicSubMenu = msg.getSupportsDynamicSubMenus();
        List<ImageFieldName> imageFieldNames = msg.getSupportedDynamicImageFieldNames();

        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, supportDynamicSubMenu);
        assertEquals(TestValues.MATCH, Collections.singletonList(TestValues.GENERAL_IMAGEFIELDNAME).size(), imageFieldNames.size());

        for (int i = 0; i < Collections.singletonList(TestValues.GENERAL_IMAGEFIELDNAME).size(); i++) {
            assertEquals(TestValues.MATCH, TestValues.GENERAL_IMAGEFIELDNAME, imageFieldNames.get(i));
        }

        DynamicUpdateCapabilities msg = new DynamicUpdateCapabilities();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getSupportsDynamicSubMenus());
        assertNull(TestValues.NULL, msg.getSupportedDynamicImageFieldNames());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(DynamicUpdateCapabilities.KEY_SUPPORTS_DYNAMIC_SUB_MENUS, TestValues.GENERAL_BOOLEAN);
            reference.put(DynamicUpdateCapabilities.KEY_SUPPORTED_DYNAMIC_IMAGE_FIELD_NAMES, JsonUtils.createJsonArray(Collections.singletonList(TestValues.GENERAL_IMAGEFIELDNAME)));

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if (key.equals(DynamicUpdateCapabilities.KEY_SUPPORTED_DYNAMIC_IMAGE_FIELD_NAMES)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    List<ImageFieldName> imageFieldNameListReference = new ArrayList<>();
                    List<ImageFieldName> imageFieldNameListTest = new ArrayList<>();

                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        imageFieldNameListReference.add((ImageFieldName) referenceArray.get(i));
                        imageFieldNameListTest.add((ImageFieldName) underTestArray.get(i));
                    }
                    assertTrue(TestValues.TRUE, imageFieldNameListReference.containsAll(imageFieldNameListTest) && imageFieldNameListTest.containsAll(imageFieldNameListReference));
                } else if (key.equals(DynamicUpdateCapabilities.KEY_SUPPORTS_DYNAMIC_SUB_MENUS)) {
                    boolean referenceBool = JsonUtils.readBooleanFromJsonObject(reference, key);
                    boolean underTestBool = JsonUtils.readBooleanFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceBool, underTestBool);
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
