package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.RGBColor;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.RGBColor}
 */
public class RGBColorTest extends TestCase {

    private RGBColor msg;

    @Override
    public void setUp() {
        msg = new RGBColor(TestValues.GENERAL_INT, TestValues.GENERAL_INT, TestValues.GENERAL_INT);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        Integer red = msg.getRed();
        Integer green = msg.getGreen();
        Integer blue = msg.getBlue();

        // Valid Tests
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, red);
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, green);
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, blue);

        // Invalid/Null Tests
        RGBColor msg = new RGBColor();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertEquals(TestValues.MATCH, msg.getRed(), (Integer) 0);
        assertEquals(TestValues.MATCH, msg.getGreen(), (Integer) 0);
        assertEquals(TestValues.MATCH, msg.getBlue(), (Integer) 0);
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(RGBColor.KEY_RED, TestValues.GENERAL_INT);
            reference.put(RGBColor.KEY_GREEN, TestValues.GENERAL_INT);
            reference.put(RGBColor.KEY_BLUE, TestValues.GENERAL_INT);

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