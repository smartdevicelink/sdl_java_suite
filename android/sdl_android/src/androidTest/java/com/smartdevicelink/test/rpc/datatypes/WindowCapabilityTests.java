package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
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
        msg.setWindowID(Test.GENERAL_INT);
        msg.setTextFields(Test.GENERAL_TEXTFIELD_LIST);
        msg.setImageFields(Test.GENERAL_IMAGEFIELD_LIST);
        msg.setImageTypeSupported(Test.GENERAL_IMAGE_TYPE_LIST);
        msg.setTemplatesAvailable(Test.GENERAL_STRING_LIST);
        msg.setNumCustomPresetsAvailable(Test.GENERAL_INT);
        msg.setButtonCapabilities(Test.GENERAL_BUTTONCAPABILITIES_LIST);
        msg.setSoftButtonCapabilities(Test.GENERAL_SOFTBUTTONCAPABILITIES_LIST);
        msg.setMenuLayoutsAvailable(Test.GENERAL_MENU_LAYOUT_LIST);
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

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_INT, windowID);
        assertEquals(Test.MATCH, Test.GENERAL_TEXTFIELD_LIST.size(), textFields.size());
        assertEquals(Test.MATCH, Test.GENERAL_IMAGEFIELD_LIST.size(), imageFields.size());
        assertEquals(Test.MATCH, Test.GENERAL_IMAGE_TYPE_LIST.size(), imageTypeSupported.size());
        assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST.size(), templatesAvailable.size());
        assertEquals(Test.MATCH, Test.GENERAL_INT, numCustomPresetsAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BUTTONCAPABILITIES_LIST.size(), buttonCapabilities.size());
        assertEquals(Test.MATCH, Test.GENERAL_SOFTBUTTONCAPABILITIES_LIST.size(), softButtonCapabilities.size());
        assertEquals(Test.MATCH, Test.GENERAL_MENU_LAYOUT_LIST.size(), menuLayouts.size());

        for (int i = 0; i < Test.GENERAL_TEXTFIELD_LIST.size(); i++) {
            assertTrue(Test.TRUE, Validator.validateTextFields(Test.GENERAL_TEXTFIELD_LIST.get(i), textFields.get(i)));
        }

        for (int i = 0; i < Test.GENERAL_IMAGEFIELD_LIST.size(); i++) {
            assertTrue(Test.TRUE, Validator.validateImageFields(Test.GENERAL_IMAGEFIELD_LIST.get(i), imageFields.get(i)));
        }

        for (int i = 0; i < Test.GENERAL_IMAGE_TYPE_LIST.size(); i++) {
            assertEquals(Test.MATCH, Test.GENERAL_IMAGE_TYPE_LIST.get(i), imageTypeSupported.get(i));
        }

        for (int i = 0; i < Test.GENERAL_STRING_LIST.size(); i++) {
            assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST.get(i), templatesAvailable.get(i));
        }

        for(int i = 0; i < Test.GENERAL_MENU_LAYOUT_LIST.size(); i++){
            assertEquals(Test.MATCH, Test.GENERAL_MENU_LAYOUT_LIST.get(i), menuLayouts.get(i));
        }

        assertTrue(Test.TRUE, Validator.validateButtonCapabilities(Test.GENERAL_BUTTONCAPABILITIES_LIST, buttonCapabilities));
        assertTrue(Test.TRUE, Validator.validateSoftButtonCapabilities(Test.GENERAL_SOFTBUTTONCAPABILITIES_LIST, softButtonCapabilities));

        // Invalid/Null Tests
        WindowCapability msg = new WindowCapability();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getWindowID());
        assertNull(Test.NULL, msg.getTextFields());
        assertNull(Test.NULL, msg.getImageFields());
        assertNull(Test.NULL, msg.getImageTypeSupported());
        assertNull(Test.NULL, msg.getTemplatesAvailable());
        assertNull(Test.NULL, msg.getNumCustomPresetsAvailable());
        assertNull(Test.NULL, msg.getButtonCapabilities());
        assertNull(Test.NULL, msg.getSoftButtonCapabilities());
        assertNull(Test.NULL, msg.getMenuLayoutsAvailable());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(WindowCapability.KEY_WINDOW_ID, Test.GENERAL_INT);
            reference.put(WindowCapability.KEY_TEXT_FIELDS, Test.JSON_TEXTFIELDS);
            reference.put(WindowCapability.KEY_IMAGE_FIELDS, Test.JSON_IMAGEFIELDS);
            reference.put(WindowCapability.KEY_IMAGE_TYPE_SUPPORTED, Test.JSON_IMAGE_TYPE_SUPPORTED);
            reference.put(WindowCapability.KEY_TEMPLATES_AVAILABLE, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));
            reference.put(WindowCapability.KEY_NUM_CUSTOM_PRESETS_AVAILABLE, Test.GENERAL_INT);
            reference.put(WindowCapability.KEY_BUTTON_CAPABILITIES, Test.JSON_BUTTONCAPABILITIES);
            reference.put(WindowCapability.KEY_SOFT_BUTTON_CAPABILITIES, Test.JSON_SOFTBUTTONCAPABILITIES);
            reference.put(WindowCapability.KEY_MENU_LAYOUTS_AVAILABLE, JsonUtils.createJsonArray(Test.GENERAL_MENU_LAYOUT_LIST));

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if (key.equals(WindowCapability.KEY_IMAGE_FIELDS)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(Test.TRUE, Validator.validateImageFields(new ImageField(hashReference), new ImageField(hashTest)));
                    }
                } else if (key.equals(WindowCapability.KEY_TEXT_FIELDS)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(Test.TRUE, Validator.validateTextFields(new TextField(hashReference), new TextField(hashTest)));
                    }
                } else if (key.equals(WindowCapability.KEY_TEMPLATES_AVAILABLE)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());
                    assertTrue(Test.TRUE, Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key), JsonUtils.readStringListFromJsonObject(underTest, key)));
                } else if (key.equals(WindowCapability.KEY_BUTTON_CAPABILITIES)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());

                    List<ButtonCapabilities> referenceList = new ArrayList<ButtonCapabilities>();
                    List<ButtonCapabilities> testList = new ArrayList<ButtonCapabilities>();
                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        referenceList.add(new ButtonCapabilities(hashReference));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        testList.add(new ButtonCapabilities(hashTest));
                    }
                    assertTrue(Test.TRUE, Validator.validateButtonCapabilities(referenceList, testList));
                } else if (key.equals(WindowCapability.KEY_SOFT_BUTTON_CAPABILITIES)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());

                    List<SoftButtonCapabilities> referenceList = new ArrayList<SoftButtonCapabilities>();
                    List<SoftButtonCapabilities> testList = new ArrayList<SoftButtonCapabilities>();
                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        referenceList.add(new SoftButtonCapabilities(hashReference));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        testList.add(new SoftButtonCapabilities(hashTest));
                    }
                    assertTrue(Test.TRUE, Validator.validateSoftButtonCapabilities(referenceList, testList));
                } else if (key.equals(WindowCapability.KEY_IMAGE_TYPE_SUPPORTED) || (key.equals(WindowCapability.KEY_MENU_LAYOUTS_AVAILABLE))) {
                    List<String> referenceList = JsonUtils.readStringListFromJsonObject(reference, key);
                    List<String> underTestList = JsonUtils.readStringListFromJsonObject(underTest, key);

                    assertEquals(Test.MATCH, referenceList.size(), underTestList.size());
                    for (int i = 0; i < referenceList.size(); i++) {
                        assertEquals(Test.MATCH, referenceList.get(i), underTestList.get(i));
                    }
                } else {
                    assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(Test.JSON_FAIL);
        }
    }
}