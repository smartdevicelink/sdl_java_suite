package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.GearStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.TransmissionType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class GearStatusTests extends TestCase {
    private GearStatus msg;

    @Override
    public void setUp() {
        msg = new GearStatus();

        msg.setUserSelectedGear(TestValues.GENERAL_USER_SELECTED_GEAR);
        msg.setActualGear(TestValues.GENERAL_ACTUAL_GEAR);
        msg.setTransmissionType(TestValues.GENERAL_TRANSMISSION_TYPE);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        PRNDL userSelectedGear = msg.getUserSelectedGear();
        PRNDL actualGear = msg.getActualGear();
        TransmissionType transmissionType = msg.getTransmissionType();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_USER_SELECTED_GEAR, userSelectedGear);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_ACTUAL_GEAR, actualGear);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_TRANSMISSION_TYPE, transmissionType);

        // Invalid/Null Tests
        GearStatus msg = new GearStatus();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getActualGear());
        assertNull(TestValues.NULL, msg.getUserSelectedGear());
        assertNull(TestValues.NULL, msg.getTransmissionType());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(GearStatus.KEY_ACTUAL_GEAR, TestValues.GENERAL_ACTUAL_GEAR);
            reference.put(GearStatus.KEY_USER_SELECTED_GEAR, TestValues.GENERAL_USER_SELECTED_GEAR);
            reference.put(GearStatus.KEY_TRANSMISSION_TYPE, TestValues.GENERAL_TRANSMISSION_TYPE);

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
