package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Grid;
import com.smartdevicelink.proxy.rpc.WindowState;
import com.smartdevicelink.proxy.rpc.WindowStatus;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class WindowStatusTests extends TestCase {
    private WindowStatus msg;

    @Override
    protected void setUp() throws Exception {
        msg = new WindowStatus();
        msg.setWindowState(TestValues.GENERAL_WINDOW_STATE);
        msg.setLocation(TestValues.GENERAL_LOCATION_GRID);
    }

    public void testRpcValues() {
        WindowState state = msg.getWindowState();
        Grid locationGrid = msg.getLocation();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_WINDOW_STATE, state);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_LOCATION_GRID, locationGrid);
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(WindowStatus.KEY_WINDOW_STATE, TestValues.GENERAL_WINDOW_STATE);
            reference.put(WindowStatus.KEY_LOCATION, TestValues.GENERAL_LOCATION_GRID);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            assertTrue(Validator.validateWindowStatus(
                    new WindowStatus(JsonRPCMarshaller.deserializeJSONObject(reference)),
                    new WindowStatus(JsonRPCMarshaller.deserializeJSONObject(underTest)))
            );

        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
