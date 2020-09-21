package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

public class VrHelpItemTest extends TestCase {

    private VrHelpItem msg;

    @Override
    public void setUp() {
        msg = new VrHelpItem();

        msg.setText(TestValues.GENERAL_STRING);
        msg.setImage(TestValues.GENERAL_IMAGE);
        msg.setPosition(TestValues.GENERAL_INT);

    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        String text = msg.getText();
        Image image = msg.getImage();
        Integer position = msg.getPosition();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, text);
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, position);
        assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, image));

        // Invalid/Null Tests
        VrHelpItem msg = new VrHelpItem();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getImage());
        assertNull(TestValues.NULL, msg.getText());
        assertNull(TestValues.NULL, msg.getPosition());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(VrHelpItem.KEY_IMAGE, TestValues.JSON_IMAGE);
            reference.put(VrHelpItem.KEY_TEXT, TestValues.GENERAL_STRING);
            reference.put(VrHelpItem.KEY_POSITION, TestValues.GENERAL_INT);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if (key.equals(VrHelpItem.KEY_IMAGE)) {
                    JSONObject objectEquals = JsonUtils.readJsonObjectFromJsonObject(reference, key);
                    JSONObject testEquals = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);

                    assertTrue(TestValues.TRUE, Validator.validateImage(new Image(hashReference), new Image(hashTest)));
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}