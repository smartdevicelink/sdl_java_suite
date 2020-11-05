package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.DynamicUpdateCapabilities;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.WindowCapability}
 */
public class WindowCapabilityTests extends TestCase {

    private WindowCapability msg;

    @Override
    public void setUp() {
        msg = new WindowCapability();
        msg.setWindowID(TestValues.GENERAL_INT);
        msg.setTextFields(TestValues.GENERAL_TEXTFIELD_LIST);
        msg.setImageFields(TestValues.GENERAL_IMAGEFIELD_LIST);
        msg.setImageTypeSupported(TestValues.GENERAL_IMAGE_TYPE_LIST);
        msg.setTemplatesAvailable(TestValues.GENERAL_STRING_LIST);
        msg.setNumCustomPresetsAvailable(TestValues.GENERAL_INT);
        msg.setButtonCapabilities(TestValues.GENERAL_BUTTONCAPABILITIES_LIST);
        msg.setSoftButtonCapabilities(TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST);
        msg.setMenuLayoutsAvailable(TestValues.GENERAL_MENU_LAYOUT_LIST);
        msg.setDynamicUpdateCapabilities(TestValues.GENERAL_DYNAMICUPDATECAPABILITIES);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        int windowID = msg.getWindowID();
        List<TextField> textFields = msg.getTextFields();
        List<ImageField> imageFields = msg.getImageFields();
        List<ImageType> imageTypeSupported = msg.getImageTypeSupported();
        List<String> templatesAvailable = msg.getTemplatesAvailable();
        int numCustomPresetsAvailable = msg.getNumCustomPresetsAvailable();
        List<ButtonCapabilities> buttonCapabilities = msg.getButtonCapabilities();
        List<SoftButtonCapabilities> softButtonCapabilities = msg.getSoftButtonCapabilities();
        List<MenuLayout> menuLayouts = msg.getMenuLayoutsAvailable();
        DynamicUpdateCapabilities dynamicUpdateCapabilities = msg.getDynamicUpdateCapabilities();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, windowID);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_TEXTFIELD_LIST.size(), textFields.size());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_IMAGEFIELD_LIST.size(), imageFields.size());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_IMAGE_TYPE_LIST.size(), imageTypeSupported.size());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST.size(), templatesAvailable.size());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, numCustomPresetsAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BUTTONCAPABILITIES_LIST.size(), buttonCapabilities.size());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST.size(), softButtonCapabilities.size());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_MENU_LAYOUT_LIST.size(), menuLayouts.size());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DYNAMICUPDATECAPABILITIES, dynamicUpdateCapabilities);

        for (int i = 0; i < TestValues.GENERAL_TEXTFIELD_LIST.size(); i++) {
            assertTrue(TestValues.TRUE, Validator.validateTextFields(TestValues.GENERAL_TEXTFIELD_LIST.get(i), textFields.get(i)));
        }

        for (int i = 0; i < TestValues.GENERAL_IMAGEFIELD_LIST.size(); i++) {
            assertTrue(TestValues.TRUE, Validator.validateImageFields(TestValues.GENERAL_IMAGEFIELD_LIST.get(i), imageFields.get(i)));
        }

        for (int i = 0; i < TestValues.GENERAL_IMAGE_TYPE_LIST.size(); i++) {
            assertEquals(TestValues.MATCH, TestValues.GENERAL_IMAGE_TYPE_LIST.get(i), imageTypeSupported.get(i));
        }

        for (int i = 0; i < TestValues.GENERAL_STRING_LIST.size(); i++) {
            assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST.get(i), templatesAvailable.get(i));
        }

        for (int i = 0; i < TestValues.GENERAL_MENU_LAYOUT_LIST.size(); i++) {
            assertEquals(TestValues.MATCH, TestValues.GENERAL_MENU_LAYOUT_LIST.get(i), menuLayouts.get(i));
        }

        assertTrue(TestValues.TRUE, Validator.validateButtonCapabilities(TestValues.GENERAL_BUTTONCAPABILITIES_LIST, buttonCapabilities));
        assertTrue(TestValues.TRUE, Validator.validateSoftButtonCapabilities(TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST, softButtonCapabilities));

        // Invalid/Null Tests
        WindowCapability msg = new WindowCapability();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getWindowID());
        assertNull(TestValues.NULL, msg.getTextFields());
        assertNull(TestValues.NULL, msg.getImageFields());
        assertNull(TestValues.NULL, msg.getImageTypeSupported());
        assertNull(TestValues.NULL, msg.getTemplatesAvailable());
        assertNull(TestValues.NULL, msg.getNumCustomPresetsAvailable());
        assertNull(TestValues.NULL, msg.getButtonCapabilities());
        assertNull(TestValues.NULL, msg.getSoftButtonCapabilities());
        assertNull(TestValues.NULL, msg.getMenuLayoutsAvailable());
        assertNull(TestValues.NULL, msg.getDynamicUpdateCapabilities());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(WindowCapability.KEY_WINDOW_ID, TestValues.GENERAL_INT);
            reference.put(WindowCapability.KEY_TEXT_FIELDS, TestValues.JSON_TEXTFIELDS);
            reference.put(WindowCapability.KEY_IMAGE_FIELDS, TestValues.JSON_IMAGEFIELDS);
            reference.put(WindowCapability.KEY_IMAGE_TYPE_SUPPORTED, TestValues.JSON_IMAGE_TYPE_SUPPORTED);
            reference.put(WindowCapability.KEY_TEMPLATES_AVAILABLE, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
            reference.put(WindowCapability.KEY_NUM_CUSTOM_PRESETS_AVAILABLE, TestValues.GENERAL_INT);
            reference.put(WindowCapability.KEY_BUTTON_CAPABILITIES, TestValues.JSON_BUTTONCAPABILITIES);
            reference.put(WindowCapability.KEY_SOFT_BUTTON_CAPABILITIES, TestValues.JSON_SOFTBUTTONCAPABILITIES);
            reference.put(WindowCapability.KEY_MENU_LAYOUTS_AVAILABLE, JsonUtils.createJsonArray(TestValues.GENERAL_MENU_LAYOUT_LIST));
            reference.put(WindowCapability.KEY_DYNAMIC_UPDATE_CAPABILITIES, TestValues.JSON_DYNAMICUPDATECAPABILITIES);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if (key.equals(WindowCapability.KEY_IMAGE_FIELDS)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(TestValues.TRUE, Validator.validateImageFields(new ImageField(hashReference), new ImageField(hashTest)));
                    }
                } else if (key.equals(WindowCapability.KEY_TEXT_FIELDS)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(TestValues.TRUE, Validator.validateTextFields(new TextField(hashReference), new TextField(hashTest)));
                    }
                } else if (key.equals(WindowCapability.KEY_TEMPLATES_AVAILABLE)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());
                    assertTrue(TestValues.TRUE, Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key), JsonUtils.readStringListFromJsonObject(underTest, key)));
                } else if (key.equals(WindowCapability.KEY_BUTTON_CAPABILITIES)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    List<ButtonCapabilities> referenceList = new ArrayList<ButtonCapabilities>();
                    List<ButtonCapabilities> testList = new ArrayList<ButtonCapabilities>();
                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        referenceList.add(new ButtonCapabilities(hashReference));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        testList.add(new ButtonCapabilities(hashTest));
                    }
                    assertTrue(TestValues.TRUE, Validator.validateButtonCapabilities(referenceList, testList));
                } else if (key.equals(WindowCapability.KEY_SOFT_BUTTON_CAPABILITIES)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    List<SoftButtonCapabilities> referenceList = new ArrayList<SoftButtonCapabilities>();
                    List<SoftButtonCapabilities> testList = new ArrayList<SoftButtonCapabilities>();
                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        referenceList.add(new SoftButtonCapabilities(hashReference));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        testList.add(new SoftButtonCapabilities(hashTest));
                    }
                    assertTrue(TestValues.TRUE, Validator.validateSoftButtonCapabilities(referenceList, testList));
                } else if (key.equals(WindowCapability.KEY_IMAGE_TYPE_SUPPORTED) || (key.equals(WindowCapability.KEY_MENU_LAYOUTS_AVAILABLE))) {
                    List<String> referenceList = JsonUtils.readStringListFromJsonObject(reference, key);
                    List<String> underTestList = JsonUtils.readStringListFromJsonObject(underTest, key);

                    assertEquals(TestValues.MATCH, referenceList.size(), underTestList.size());
                    for (int i = 0; i < referenceList.size(); i++) {
                        assertEquals(TestValues.MATCH, referenceList.get(i), underTestList.get(i));
                    }
                } else if (key.equals(WindowCapability.KEY_DYNAMIC_UPDATE_CAPABILITIES)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference.getJSONObject(key), DynamicUpdateCapabilities.KEY_SUPPORTED_DYNAMIC_IMAGE_FIELD_NAMES);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest.getJSONObject(key), DynamicUpdateCapabilities.KEY_SUPPORTED_DYNAMIC_IMAGE_FIELD_NAMES);
                    List<ImageFieldName> imageFieldNameListReference = new ArrayList<>();
                    List<ImageFieldName> imageFieldNameListTest = new ArrayList<>();
                    boolean referenceBool = JsonUtils.readBooleanFromJsonObject(reference.getJSONObject(key), DynamicUpdateCapabilities.KEY_SUPPORTS_DYNAMIC_SUB_MENUS);
                    boolean underTestBool = JsonUtils.readBooleanFromJsonObject(underTest.getJSONObject(key), DynamicUpdateCapabilities.KEY_SUPPORTS_DYNAMIC_SUB_MENUS);

                    assertEquals(TestValues.MATCH, referenceBool, underTestBool);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        imageFieldNameListReference.add((ImageFieldName) referenceArray.get(i));
                        imageFieldNameListTest.add((ImageFieldName) underTestArray.get(i));
                    }
                    assertTrue(TestValues.TRUE, imageFieldNameListReference.containsAll(imageFieldNameListTest) && imageFieldNameListTest.containsAll(imageFieldNameListReference));
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}