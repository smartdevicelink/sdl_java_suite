package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Grid;
import com.smartdevicelink.proxy.rpc.SeatLocation;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class SeatLocationTests extends TestCase {

    private SeatLocation msg;

    @Override
    public void setUp() {
        msg = new SeatLocation();
        msg.setGrid(TestValues.GENERAL_GRID);
    }

    public void testRpcValues() {
        Grid grid = msg.getGrid();

        //valid test
        assertTrue(Validator.validateGrid(TestValues.GENERAL_GRID, grid));

        //null test
        SeatLocation msg = new SeatLocation();
        assertNull(TestValues.NULL, msg.getGrid());
    }

    public void testJson() {
        JSONObject original = new JSONObject();
        try {
            original.put(SeatLocation.KEY_GRID, TestValues.GENERAL_GRID);

            JSONObject serialized = msg.serializeJSON();
            assertEquals(serialized.length(), original.length());
            assertTrue(TestValues.TRUE, Validator.validateSeatLocation(new SeatLocation(JsonRPCMarshaller.deserializeJSONObject(original)),
                    new SeatLocation(JsonRPCMarshaller.deserializeJSONObject(serialized))));
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
