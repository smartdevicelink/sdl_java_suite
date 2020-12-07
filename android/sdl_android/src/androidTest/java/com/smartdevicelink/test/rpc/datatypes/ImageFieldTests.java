package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
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
 * {@link com.smartdevicelink.proxy.rpc.ImageField}
 */
public class ImageFieldTests extends TestCase {

    private ImageField msg;

    @Override
    public void setUp() {
        msg = new ImageField();

        msg.setImageResolution(TestValues.GENERAL_IMAGERESOLUTION);
        msg.setImageTypeSupported(TestValues.GENERAL_FILETYPE_LIST);
        msg.setName(TestValues.GENERAL_IMAGEFIELDNAME);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        ImageResolution imageRes = msg.getImageResolution();
        List<FileType> imageTypes = msg.getImageTypeSupported();
        ImageFieldName name = msg.getName();

        // Valid Tests
        assertTrue(TestValues.TRUE, Validator.validateImageResolution(TestValues.GENERAL_IMAGERESOLUTION, imageRes));
        assertEquals(TestValues.MATCH, TestValues.GENERAL_IMAGEFIELDNAME, name);
        assertTrue(TestValues.TRUE, Validator.validateFileTypes(TestValues.GENERAL_FILETYPE_LIST, imageTypes));

        // Invalid/Null Tests
        ImageField msg = new ImageField();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getImageResolution());
        assertNull(TestValues.NULL, msg.getImageTypeSupported());
        assertNull(TestValues.NULL, msg.getName());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(ImageField.KEY_IMAGE_RESOLUTION, TestValues.JSON_IMAGERESOLUTION);
            reference.put(ImageField.KEY_IMAGE_TYPE_SUPPORTED, JsonUtils.createJsonArray(TestValues.GENERAL_FILETYPE_LIST));
            reference.put(ImageField.KEY_NAME, TestValues.GENERAL_IMAGEFIELDNAME);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if (key.equals(ImageField.KEY_IMAGE_RESOLUTION)) {
                    JSONObject objectEquals = JsonUtils.readJsonObjectFromJsonObject(reference, key);
                    JSONObject testEquals = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateImageResolution(new ImageResolution(hashReference), new ImageResolution(hashTest)));
                } else if (key.equals(ImageField.KEY_IMAGE_TYPE_SUPPORTED)) {
                    JSONArray imageTypeArrayReference = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray imageTypeArrayTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    List<FileType> imageTypeListReference = new ArrayList<FileType>();
                    List<FileType> imageTypeListTest = new ArrayList<FileType>();

                    assertEquals(TestValues.MATCH, imageTypeArrayReference.length(), imageTypeArrayTest.length());

                    for (int index = 0; index < imageTypeArrayReference.length(); index++) {
                        imageTypeListReference.add((FileType) imageTypeArrayReference.get(index));
                        imageTypeListTest.add((FileType) imageTypeArrayTest.get(index));
                    }
                    assertTrue(TestValues.TRUE, imageTypeListReference.containsAll(imageTypeListTest) && imageTypeListTest.containsAll(imageTypeListReference));
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}