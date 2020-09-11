package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.DriverDistractionCapability;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.DriverDistractionCapability}
 */
public class DriverDistractionCapabilityTest extends TestCase {

    private DriverDistractionCapability msg;

    @Override
    public void setUp() {
        msg = new DriverDistractionCapability();
        msg.setMenuLength(TestValues.GENERAL_INT);
        msg.setSubMenuDepth(TestValues.GENERAL_INT);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        int menuLength = msg.getMenuLength();
        int subMenuDepth = msg.getSubMenuDepth();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, menuLength);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, subMenuDepth);

        // Invalid/Null Tests
        DriverDistractionCapability msg = new DriverDistractionCapability();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getMenuLength());
        assertNull(TestValues.NULL, msg.getSubMenuDepth());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(DriverDistractionCapability.KEY_MENU_LENGTH, TestValues.GENERAL_INT);
            reference.put(DriverDistractionCapability.KEY_SUB_MENU_DEPTH, TestValues.GENERAL_INT);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
