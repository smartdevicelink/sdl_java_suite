package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.ScreenParams;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.MediaClockFormat;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.DisplayCapabilities}
 */
public class DisplayCapabilitiesTests extends TestCase {

    private DisplayCapabilities msg;

    @Override
    public void setUp() {
        msg = new DisplayCapabilities();

        msg.setGraphicSupported(TestValues.GENERAL_BOOLEAN);
        msg.setNumCustomPresetsAvailable(TestValues.GENERAL_INT);
        msg.setDisplayType(TestValues.GENERAL_DISPLAYTYPE);
        msg.setDisplayName(TestValues.GENERAL_STRING);
        msg.setImageFields(TestValues.GENERAL_IMAGEFIELD_LIST);
        msg.setTextFields(TestValues.GENERAL_TEXTFIELD_LIST);
        msg.setMediaClockFormats(TestValues.GENERAL_MEDIACLOCKFORMAT_LIST);
        msg.setScreenParams(TestValues.GENERAL_SCREENPARAMS);
        msg.setTemplatesAvailable(TestValues.GENERAL_STRING_LIST);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        boolean graphicSupported = msg.getGraphicSupported();
        int numPresets = msg.getNumCustomPresetsAvailable();
        DisplayType displayType = msg.getDisplayType();
        String displayName = msg.getDisplayName();
        ScreenParams screenParams = msg.getScreenParams();
        List<String> templatesAvailable = msg.getTemplatesAvailable();
        List<MediaClockFormat> mediaClock = msg.getMediaClockFormats();
        List<TextField> textFields = msg.getTextFields();
        List<ImageField> imageFields = msg.getImageFields();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, graphicSupported);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, numPresets);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DISPLAYTYPE, displayType);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, displayName);
        assertTrue(TestValues.TRUE, Validator.validateScreenParams(TestValues.GENERAL_SCREENPARAMS, screenParams));
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST.size(), templatesAvailable.size());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_MEDIACLOCKFORMAT_LIST.size(), mediaClock.size());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_TEXTFIELD_LIST.size(), textFields.size());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_IMAGEFIELD_LIST.size(), imageFields.size());

        for (int i = 0; i < TestValues.GENERAL_STRING_LIST.size(); i++) {
            assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST.get(i), templatesAvailable.get(i));
        }

        for (int i = 0; i < TestValues.GENERAL_MEDIACLOCKFORMAT_LIST.size(); i++) {
            assertEquals(TestValues.MATCH, TestValues.GENERAL_MEDIACLOCKFORMAT_LIST.get(i), mediaClock.get(i));
        }

        for (int i = 0; i < TestValues.GENERAL_TEXTFIELD_LIST.size(); i++) {
            assertTrue(TestValues.TRUE, Validator.validateTextFields(TestValues.GENERAL_TEXTFIELD_LIST.get(i), textFields.get(i)));
        }

        for (int i = 0; i < TestValues.GENERAL_IMAGEFIELD_LIST.size(); i++) {
            assertTrue(TestValues.TRUE, Validator.validateImageFields(TestValues.GENERAL_IMAGEFIELD_LIST.get(i), imageFields.get(i)));
        }


        // Invalid/Null Tests
        DisplayCapabilities msg = new DisplayCapabilities();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getDisplayType());
        assertNull(TestValues.NULL, msg.getDisplayName());
        assertNull(TestValues.NULL, msg.getGraphicSupported());
        assertNull(TestValues.NULL, msg.getImageFields());
        assertNull(TestValues.NULL, msg.getMediaClockFormats());
        assertNull(TestValues.NULL, msg.getNumCustomPresetsAvailable());
        assertNull(TestValues.NULL, msg.getScreenParams());
        assertNull(TestValues.NULL, msg.getTemplatesAvailable());
        assertNull(TestValues.NULL, msg.getTextFields());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(DisplayCapabilities.KEY_NUM_CUSTOM_PRESETS_AVAILABLE, TestValues.GENERAL_INT);
            reference.put(DisplayCapabilities.KEY_GRAPHIC_SUPPORTED, TestValues.GENERAL_BOOLEAN);
            reference.put(DisplayCapabilities.KEY_DISPLAY_TYPE, TestValues.GENERAL_DISPLAYTYPE);
            reference.put(DisplayCapabilities.KEY_DISPLAY_NAME, TestValues.GENERAL_STRING);
            reference.put(DisplayCapabilities.KEY_TEMPLATES_AVAILABLE, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
            reference.put(DisplayCapabilities.KEY_MEDIA_CLOCK_FORMATS, JsonUtils.createJsonArray(TestValues.GENERAL_MEDIACLOCKFORMAT_LIST));
            reference.put(DisplayCapabilities.KEY_TEXT_FIELDS, TestValues.JSON_TEXTFIELDS);
            reference.put(DisplayCapabilities.KEY_IMAGE_FIELDS, TestValues.JSON_IMAGEFIELDS);
            reference.put(DisplayCapabilities.KEY_SCREEN_PARAMS, TestValues.JSON_SCREENPARAMS);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(DisplayCapabilities.KEY_IMAGE_FIELDS)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(TestValues.TRUE, Validator.validateImageFields(new ImageField(hashReference), new ImageField(hashTest)));
                    }
                } else if (key.equals(DisplayCapabilities.KEY_TEXT_FIELDS)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(TestValues.TRUE, Validator.validateTextFields(new TextField(hashReference), new TextField(hashTest)));
                    }
                } else if (key.equals(DisplayCapabilities.KEY_TEMPLATES_AVAILABLE)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());
                    assertTrue(TestValues.TRUE, Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key), JsonUtils.readStringListFromJsonObject(underTest, key)));
                } else if (key.equals(DisplayCapabilities.KEY_SCREEN_PARAMS)) {
                    JSONObject referenceArray = JsonUtils.readJsonObjectFromJsonObject(reference, key);
                    JSONObject underTestArray = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray);

                    assertTrue(TestValues.TRUE, Validator.validateScreenParams(new ScreenParams(hashReference), new ScreenParams(hashTest)));
                } else if (key.equals(DisplayCapabilities.KEY_MEDIA_CLOCK_FORMATS)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        assertTrue(TestValues.TRUE, Validator.validateText(referenceArray.getString(i), underTestArray.getString(i)));// not a string?
                    }
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}