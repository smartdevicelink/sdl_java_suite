package com.smartdevicelink.test.rpc.datatypes;


import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.SeatOccupancy;
import com.smartdevicelink.proxy.rpc.SeatStatus;
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

public class SeatOccupancyTests extends TestCase {

    private SeatOccupancy msg;

    @Override
    public void setUp() {
        msg = new SeatOccupancy();

        msg.setSeatsOccupied(TestValues.GENERAL_SEATS_OCCUPIED);
        msg.setSeatsBelted(TestValues.GENERAL_SEATS_BELTED);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        List<SeatStatus> seatsBelted = msg.getSeatsBelted();
        List<SeatStatus> seatsOccupied = msg.getSeatsOccupied();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_SEATS_BELTED, seatsBelted);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_SEATS_OCCUPIED, seatsOccupied);

        // Invalid/Null Tests
        SeatOccupancy msg = new SeatOccupancy();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getSeatsBelted());
        assertNull(TestValues.NULL, msg.getSeatsOccupied());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(SeatOccupancy.KEY_SEATS_OCCUPIED, TestValues.JSON_GENERAL_SEATS_OCCUPIED);
            reference.put(SeatOccupancy.KEY_SEATS_BELTED, TestValues.JSON_GENERAL_SEATS_BELTED);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(SeatOccupancy.KEY_SEATS_OCCUPIED) || key.equals(SeatOccupancy.KEY_SEATS_BELTED)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(TestValues.TRUE, Validator.validateSeatStatus(new SeatStatus(hashReference), new SeatStatus(hashTest)));
                    }

                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
