package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.MassageCushionFirmness;
import com.smartdevicelink.proxy.rpc.enums.MassageCushion;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.MassageCushionFirmness}
 */
public class MassageCushionFirmnessTest extends TestCase {

    private MassageCushionFirmness msg;

    @Override
    public void setUp() {
        msg = new MassageCushionFirmness();

        msg.setCushion(TestValues.GENERAL_MASSAGECUSHION);
        msg.setFirmness(TestValues.GENERAL_INT);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        MassageCushion cushion = msg.getCushion();
        Integer firmness = msg.getFirmness();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_MASSAGECUSHION, cushion);
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, firmness);

        // Invalid/Null Tests
        MassageCushionFirmness msg = new MassageCushionFirmness();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getCushion());
        assertNull(TestValues.NULL, msg.getFirmness());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(MassageCushionFirmness.KEY_CUSHION, TestValues.GENERAL_MASSAGECUSHION);
            reference.put(MassageCushionFirmness.KEY_FIRMNESS, TestValues.GENERAL_INT);

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