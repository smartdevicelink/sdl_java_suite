package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.DoorStatus;
import com.smartdevicelink.proxy.rpc.Grid;
import com.smartdevicelink.proxy.rpc.RoofStatus;
import com.smartdevicelink.proxy.rpc.enums.DoorStatusType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

public class DoorStatusTests extends TestCase {
    private DoorStatus msg;

    @Override
    public void setUp() {
        msg = new DoorStatus(TestValues.GENERAL_GRID, TestValues.GENERAL_DOOR_STATUS_TYPE);
    }

    public void testRpcValues() {
        // Test Values
        Grid location = msg.getLocation();
        DoorStatusType status = msg.getStatus();

        // Valid Tests
        assertTrue(Validator.validateGrid(msg.getLocation(), location));
        assertEquals(TestValues.MATCH, TestValues.GENERAL_GRID, location);
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(RoofStatus.KEY_STATUS, TestValues.GENERAL_DOOR_STATUS_TYPE);
            reference.put(RoofStatus.KEY_LOCATION, TestValues.JSON_GRID);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(RoofStatus.KEY_LOCATION)) {
                    Hashtable<String, Object> hs1 = JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(reference, key));
                    Hashtable<String, Object> hs2 = JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key));

                    assertTrue(Validator.validateGrid(new Grid(hs1), new Grid(hs2)));

                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
