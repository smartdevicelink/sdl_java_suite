package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.SeatLocation;
import com.smartdevicelink.proxy.rpc.SeatStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

public class SeatStatusTests extends TestCase {

    private SeatStatus msg;

    @Override
    public void setUp() {
        msg = new SeatStatus();

        msg.setSeatLocation(TestValues.GENERAL_SEAT_LOCATION);
        msg.setConditionActive(TestValues.GENERAL_BOOLEAN);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        SeatLocation location = msg.getSeatLocation();
        Boolean conditionActive = msg.getConditionActive();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_SEAT_LOCATION, location);
        assertEquals(TestValues.GENERAL_BOOLEAN, (boolean) conditionActive);

        // Invalid/Null Tests
        SeatStatus msg = new SeatStatus();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getSeatLocation());
        assertNull(TestValues.NULL, msg.getConditionActive());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(SeatStatus.KEY_CONDITION_ACTIVE, TestValues.GENERAL_BOOLEAN);
            reference.put(SeatStatus.KEY_SEAT_LOCATION, TestValues.GENERAL_SEAT_LOCATION.serializeJSON());

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(SeatStatus.KEY_SEAT_LOCATION)) {
                    Hashtable<String, Object> locationOne = JsonRPCMarshaller.deserializeJSONObject(JsonUtils.readJsonObjectFromJsonObject(reference, key));
                    Hashtable<String, Object> locationTwo = JsonRPCMarshaller.deserializeJSONObject(JsonUtils.readJsonObjectFromJsonObject(underTest, key));
                    assertTrue(TestValues.TRUE, Validator.validateSeatLocation(new SeatLocation(locationOne), new SeatLocation(locationTwo)));
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));

                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
